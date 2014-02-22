/**
 * 
 */
package org.sree.mojo.wsgen;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.PatternSet;
import org.codehaus.classworlds.ClassRealm;
import org.codehaus.classworlds.ClassWorld; 

import weblogic.wsee.tools.anttasks.JwsFileSet;
import weblogic.wsee.tools.anttasks.JwscTask;
import weblogic.wsee.tools.anttasks.MultipleJwsModule;
import weblogic.wsee.tools.anttasks.JwsModule.Descriptor;

/**
 * Runs the JWSC compiler task for web service enabled code.
 * 
 * @author <a href="mailto:shreedhara.ca@gmail.com">Shreedhara C A</a>
 * @version $Id: WSGenMojo.java
 * @description This mojo will run the JSWC
 * @goal jwsc
 * @requiresDependencyResolution compile
 */
public class WSGenMojo extends AbstractMojo {

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		if ( getLog().isInfoEnabled() )
        {
            getLog().info( "Weblogic jwsc beginning for output " + this.outputName );
        }
        if ( getLog().isDebugEnabled() )
        {
            getLog().debug( "inputDir=" + this.inputDir + "  contextPath=" + this.contextPath );
        }
        if ( this.contextPath == null )
        {
            getLog().warn( "Context path is null. It will be required if " + "more than one web service is present." );
        }
        
        try
        {
            Iterator iter = getDependencies().iterator();
            while ( iter.hasNext() )
            {
                getLog().debug( iter.next().toString() );
            }
            
            getLog().debug( "Project Type ="+this.jwscTaskType);
            
            getLog().debug( "getProject().getArtifacts() Path= " + this.getMvnp().getArtifacts());
            final JwscTask task = new JwscTask();
            addToolsJar( ClassLoader.getSystemClassLoader() );
            final Project project = new Project();
            project.addBuildListener( MavenMojoUtilities.getDefaultLogger() );
            
            
            project.setName( "jwsc" );
            final Path path = new Path( project, MavenMojoUtilities
                .getDependencies( this.getMvnp().getArtifacts(), this.getPluginArtifacts() ) );
            
            URL[] urls = new URL[this.getMvnp().getArtifacts().size()];
            
            ClassWorld world = new ClassWorld();
            ClassRealm realm = world.newRealm("plugin.jetty.container", Thread.currentThread().getContextClassLoader());
            ClassRealm jspRealm = realm.createChildRealm("jsp");
            
            Iterator itor = this.getMvnp().getArtifacts().iterator();
            while ( itor.hasNext() ) {
            	Artifact artifact = (Artifact) itor.next();
            	jspRealm.addConstituent(artifact.getFile().toURL());
            }
            
            Thread.currentThread().setContextClassLoader(jspRealm.getClassLoader());
            
          
            
            project.createClassLoader(path);
            
            FileSet localFileSet = new FileSet();
            localFileSet.setDir(new File("C:\\Users\\SIRI\\workspaceb\\MISGen\\bin"));
            
             //path.addFileset(localFileSet);
            
             
             if ( getLog().isDebugEnabled() )
             {
                 getLog().debug( "Path=" + path.toString() );
             }
             
             
            task.setProject( project );
            task.setTaskName( project.getName() );
            task.setNowarn( false );
            // Set the class path
            task.setClasspath( path );
            task.setDestdir( new File( this.outputDir ) );
            task.setVerbose( this.verbose );
            task.setOptimize( this.optimize );
            task.setDebug( this.debug );
            task.setSrcdir( new File( this.inputDir ) );
            task.setKeepGenerated( this.keepGenerated );
            if ( this.jwscTaskType != null && this.jwscTaskType.trim().length() > 0 ) {
                task.setTaskType(this.jwscTaskType);
            }
            
            
            final MultipleJwsModule module = task.createModule();
            final JwsFileSet jwsFileSet = module.createJwsFileSet();
            jwsFileSet.setProject( project );
            if ( this.jwscTaskType != null && this.jwscTaskType.trim().length() > 0 ) {
                jwsFileSet.setType(this.jwscTaskType);
            }
            jwsFileSet.setSrcdir( new Path( project, this.inputDir ) );
            PatternSet ps = null;
            if ( this.sourceIncludes != null && this.sourceIncludes.length() > 0 ) {
                if ( getLog().isDebugEnabled() ) {
                    getLog().debug("Using source includes " + this.sourceIncludes );
                }
                ps = jwsFileSet.createPatternSet();
                ps.setIncludes( this.sourceIncludes );
            }
            if ( this.sourceExcludes != null && this.sourceExcludes.length() > 0 ) {
                if ( getLog().isDebugEnabled() ) {
                    getLog().debug("Using source excludes " + this.sourceExcludes );
                }
                if ( ps == null ) {
                    ps = jwsFileSet.createPatternSet();
                }
                ps.setExcludes( this.sourceExcludes );
            }
            if ( getLog().isInfoEnabled() )
            {
                getLog().info( "fileset=" + jwsFileSet.getSrcdir().toString() );
            }
            if ( this.descriptor != null )
            {
                final String[] descriptors = this.descriptor.split( "," );
                for ( int i=0;i<descriptors.length;i++ )  {
                    final File file = new File( descriptors[i] );
                    if ( file.exists() ) {
                        final Descriptor d = module.createDescriptor();
                        d.setFile( file );
                    } else {
                        getLog().warn( "Descriptor file " + file + " does not exist. Ignoring this file." );
                    }
                }
            }
            module.setName( this.outputName );
            module.setExplode( this.explode );
            module.setContextPath( this.contextPath );
            
            task.getProject().createClassLoader(path);
            task.execute();
        }
        catch ( Exception ex )
        {
            getLog().error( "Exception encountered during jwsc", ex );
            throw new MojoExecutionException( "Exception encountered during jwsc", ex );
        }
        finally
        {
            
        }
	}
	
	 
    
    
	/**
     * Load tools jar into the selected classloader. We need to do it this way
     * because the addURL method is protected on the classloader. This is for a bug in 1.6
     * tools.jar implementations
     *
     * @param classLoader the classloader to load
     * @throws Exception
     */
    protected void addToolsJar(ClassLoader classLoader) throws Exception {
        if (this.toolsJar == null ||
                this.toolsJar.trim().length() == 0) {
            throw new MojoExecutionException(
                    "toolsJar is required for this mojo.");
        }
        final File f = new File(this.toolsJar);
        if (!f.exists()) {
            throw new MojoExecutionException(
                    "toolsJar was supplied but not found. was java.home correct?");
        }
        try {
            final URL u = f.toURI().toURL();
            final Class<URLClassLoader> urlClass = URLClassLoader.class;
            final Method method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            method.invoke(classLoader, new Object[]{u});
        } catch (Exception e) {
            throw new MojoExecutionException("Exceptions while setting up the classloader", e);
        }
    }
	
	/**
	 * The directory to output the generated code to.
	 * 
	 * @parameter default-value="${project.build.directory}"
	 */
	private String outputDir;

	/**
	 * The directory to find the jwsc enabled files.
	 * 
	 * @parameter default-value="${basedir}/src/main/java"
	 */
	private String inputDir;

	/**
	 * The name of the war to use when evaluating the ear file.
	 * 
	 * @required
	 * @parameter default-value="${project.artifactId}-${project.version}"
	 */
	private String outputName;

	/**
	 * The flag to set when desiring verbose output
	 * 
	 * @parameter default-value="false"
	 */
	private boolean verbose;

	/**
	 * The flag to set when debugging the process
	 * 
	 * @parameter default-value="false"
	 */
	private boolean debug;

	/**
	 * The flag to set when wanting exploded output. Defaults to true.
	 * 
	 * @parameter default-value="true"
	 */
	private boolean explode;

	/**
	 * The flag to set when requiring optimization. Defaults to true.
	 * 
	 * @parameter default-value="true"
	 */
	private boolean optimize;

	/**
	 * The deployed context of the web service.
	 * 
	 * @parameter
	 * @required
	 */
	private String contextPath;

	/**
	 * The web.xml descriptor to use if a new one should not be generated. The
	 * path should be fully qualified.
	 * <p>
	 * If there is more than one descriptor, use a comma ',' separated list.
	 * </p>
	 * 
	 * @parameter
	 */
	private String descriptor;

	/**
	 * The explicit includes list for the file set
	 * 
	 * @parameter 
	 *            default-value="**\/*.java" expression="${weblogic.jwsc.includes
	 *            }"
	 */
	private String sourceIncludes;

	/**
	 * The explicit includes list for the file set
	 * 
	 * @parameter expression="${weblogic.jwsc.excludes}"
	 */
	private String sourceExcludes;

	/**
	 * The keep generated flag for the mojo
	 * 
	 * @parameter default-value="false"
	 *            expression="${weblogic.jwsc.keepGenerated}"
	 */
	private boolean keepGenerated;

	/**
	 * The task type JAXRPC JAXWS
	 * 
	 * @parameter default-value="JAXRPC" expression="${jwscTaskType}"
	 */
	private String jwscTaskType;

	 /**
     * This is the set of dependencies that are defined as part of this project's
     * pom which are active for the scope. You should not need to
     * override this unless your pom file is incomplete.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject mvnp;
    

    /**
     * These are the plugin artifacts for the weblogic mojo
     *
     * @parameter expression="${plugin.artifacts}"
     */
    private List pluginArtifacts;

    /**
     * This is the output directory for the artifacts. It defaults to
     * $project.build.directory.
     *
     * @parameter expression="${project.build.directory}"
     */
    private String outputDirectory;

    /**
     * The artifact factory
     *
     * @component
     */
    private ArtifactFactory artifactFactory;

    /**
     * @component
     */
    private ArtifactResolver resolver;

    /**
     * The local repository
     *
     * @parameter expression="${localRepository}"
     */
    private ArtifactRepository localRepository;

    /**
     * The list of remote repositories
     *
     * @parameter expression="${project.remoteArtifactRepositories}"
     */
    private List remoteRepositories;

    /**
     * The the location of tools.jar file. this file must be dynamically added
     * to the classloader due to some issues with the way maven works and a bug
     * in jdk 1.6<br/>
     * <i>Note that the java.home location should be to the jre not the jdk</i>
     *
     * @parameter expression="${java.home}/../lib/tools.jar"
     */
    private String toolsJar;
    
    
    /**
     * This is the set of dependencies that are defined as part of this project's
     * pom which are active for the scope. You should not need to
     * override this unless your pom file is incomplete.
     *
     * @parameter expression="${project.dependencies}"
     * @required
     * @readonly
     */
    private List dependencies;
    
    

	/**
	 * @return the outputDir
	 */
	public String getOutputDir() {
		return outputDir;
	}

	/**
	 * @param outputDir the outputDir to set
	 */
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	/**
	 * @return the inputDir
	 */
	public String getInputDir() {
		return inputDir;
	}

	/**
	 * @param inputDir the inputDir to set
	 */
	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}

	/**
	 * @return the outputName
	 */
	public String getOutputName() {
		return outputName;
	}

	/**
	 * @param outputName the outputName to set
	 */
	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	/**
	 * @return the verbose
	 */
	public boolean isVerbose() {
		return verbose;
	}

	/**
	 * @param verbose the verbose to set
	 */
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @return the explode
	 */
	public boolean isExplode() {
		return explode;
	}

	/**
	 * @param explode the explode to set
	 */
	public void setExplode(boolean explode) {
		this.explode = explode;
	}

	/**
	 * @return the optimize
	 */
	public boolean isOptimize() {
		return optimize;
	}

	/**
	 * @param optimize the optimize to set
	 */
	public void setOptimize(boolean optimize) {
		this.optimize = optimize;
	}

	/**
	 * @return the contextPath
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * @param contextPath the contextPath to set
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	/**
	 * @return the descriptor
	 */
	public String getDescriptor() {
		return descriptor;
	}

	/**
	 * @param descriptor the descriptor to set
	 */
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * @return the sourceIncludes
	 */
	public String getSourceIncludes() {
		return sourceIncludes;
	}

	/**
	 * @param sourceIncludes the sourceIncludes to set
	 */
	public void setSourceIncludes(String sourceIncludes) {
		this.sourceIncludes = sourceIncludes;
	}

	/**
	 * @return the sourceExcludes
	 */
	public String getSourceExcludes() {
		return sourceExcludes;
	}

	/**
	 * @param sourceExcludes the sourceExcludes to set
	 */
	public void setSourceExcludes(String sourceExcludes) {
		this.sourceExcludes = sourceExcludes;
	}

	/**
	 * @return the keepGenerated
	 */
	public boolean isKeepGenerated() {
		return keepGenerated;
	}

	/**
	 * @param keepGenerated the keepGenerated to set
	 */
	public void setKeepGenerated(boolean keepGenerated) {
		this.keepGenerated = keepGenerated;
	}

	/**
	 * @return the jwscTaskType
	 */
	public String getJwscTaskType() {
		return jwscTaskType;
	}

	/**
	 * @param jwscTaskType the jwscTaskType to set
	 */
	public void setJwscTaskType(String jwscTaskType) {
		this.jwscTaskType = jwscTaskType;
	}

	/**
	 * @return the mvnp
	 */
	public MavenProject getMvnp() {
		return mvnp;
	}

	/**
	 * @param mvnp the mvnp to set
	 */
	public void setMvnp(MavenProject mvnp) {
		this.mvnp = mvnp;
	}

	/**
	 * @return the pluginArtifacts
	 */
	public List getPluginArtifacts() {
		return pluginArtifacts;
	}

	/**
	 * @param pluginArtifacts the pluginArtifacts to set
	 */
	public void setPluginArtifacts(List pluginArtifacts) {
		this.pluginArtifacts = pluginArtifacts;
	}

	/**
	 * @return the outputDirectory
	 */
	public String getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * @param outputDirectory the outputDirectory to set
	 */
	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	/**
	 * @return the artifactFactory
	 */
	public ArtifactFactory getArtifactFactory() {
		return artifactFactory;
	}

	/**
	 * @param artifactFactory the artifactFactory to set
	 */
	public void setArtifactFactory(ArtifactFactory artifactFactory) {
		this.artifactFactory = artifactFactory;
	}

	/**
	 * @return the resolver
	 */
	public ArtifactResolver getResolver() {
		return resolver;
	}

	/**
	 * @param resolver the resolver to set
	 */
	public void setResolver(ArtifactResolver resolver) {
		this.resolver = resolver;
	}

	/**
	 * @return the localRepository
	 */
	public ArtifactRepository getLocalRepository() {
		return localRepository;
	}

	/**
	 * @param localRepository the localRepository to set
	 */
	public void setLocalRepository(ArtifactRepository localRepository) {
		this.localRepository = localRepository;
	}

	/**
	 * @return the remoteRepositories
	 */
	public List getRemoteRepositories() {
		return remoteRepositories;
	}

	/**
	 * @param remoteRepositories the remoteRepositories to set
	 */
	public void setRemoteRepositories(List remoteRepositories) {
		this.remoteRepositories = remoteRepositories;
	}

	/**
	 * @return the toolsJar
	 */
	public String getToolsJar() {
		return toolsJar;
	}

	/**
	 * @param toolsJar the toolsJar to set
	 */
	public void setToolsJar(String toolsJar) {
		this.toolsJar = toolsJar;
	}

	/**
	 * @return the dependencies
	 */
	public List getDependencies() {
		return dependencies;
	}

	/**
	 * @param dependencies the dependencies to set
	 */
	public void setDependencies(List dependencies) {
		this.dependencies = dependencies;
	}

}
