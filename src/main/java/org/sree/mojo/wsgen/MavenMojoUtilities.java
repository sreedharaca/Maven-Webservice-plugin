package org.sree.mojo.wsgen;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;

public class MavenMojoUtilities {

	/**
     * Creates a new WeblogicMojoUtilities object.
     */
    private MavenMojoUtilities()
    {
        super();
    }

    
    /**
     * This method will make sure there is a type appended to the file name and if it is the appropriate type for the
     * project packaging. If the project packaging is ear the artifact must end in .ear. If the project packaging is war
     * then the artifact must end in .war. If the project packaging is ejb then the artifact must end in .jar.
     *
     * @param inName             The name of the artifact.
     * @param inProjectPackaging The type of packaging for this project.
     * @return The updated artifact name.
     */
    public static String updateArtifactName( final String inName, final String inProjectPackaging )
    {
        String newName = inName;
        // If project type is ear then artifact name must end in .ear
        if ( inProjectPackaging.equalsIgnoreCase( "ear" ) )
        {
            if ( !inName.endsWith( ".ear" ) )
            {
                newName = inName.concat( ".ear" );
            }
        }
        // If project type is war then artifact name must end in .war
        else if ( inProjectPackaging.equalsIgnoreCase( "war" ) )
        {
            if ( !inName.endsWith( ".war" ) )
            {
                newName = inName.concat( ".war" );
            }
        }

        // If project type is ejb then artifact name must end in .jar
        else if ( inProjectPackaging.equalsIgnoreCase( "ejb" ) )
        {
            if ( inName.endsWith( ".ejb" ) )
            {
                newName = inName.replaceAll( "\\.ejb", ".jar" );
            }
            else if ( !inName.endsWith( ".jar" ) )
            {
                newName = inName.concat( ".jar" );
            }
        }
        // Unsupported project type
        else
        {
            throw new IllegalArgumentException( "Unsupported project packaging " + inProjectPackaging );
        }
        return newName;

    }

    /**
     * This method will get the dependencies from the pom and construct a classpath string to be used to run a mojo
     * where a classpath is required.
     *
     * @param inArtifacts The Set of artifacts for the pom being run.
     * @return A string representing the current classpath for the pom.
     */
    public static String getDependencies( final Set inArtifacts )
    {

        if ( inArtifacts == null || inArtifacts.isEmpty() )
        {
            return "";
        }
        // Loop over all the artifacts and create a classpath string.
        Iterator iter = inArtifacts.iterator();

        StringBuffer buffer = new StringBuffer();
        if ( iter.hasNext() )
        {
            Artifact artifact = (Artifact) iter.next();
            buffer.append( artifact.getFile() );

            while ( iter.hasNext() )
            {
                artifact = (Artifact) iter.next();
                buffer.append( System.getProperty( "path.separator" ) );
                buffer.append( artifact.getFile() );
            }
        }

        return buffer.toString();
    }

    /**
     * Returns the fully qualified path to an ear file int the artifact list.
     *
     * @param inArtifacts - the set of artifacts
     * @return the fully qualified path to an ear file int the artifact list.
     */
    public static File getEarFileName( final Set inArtifacts )
    {
        if ( inArtifacts == null || inArtifacts.isEmpty() )
        {
            throw new IllegalArgumentException( "EAR not found in artifact list." );
        }

        final Iterator iter = inArtifacts.iterator();
        while ( iter.hasNext() )
        {
            Artifact artifact = (Artifact) iter.next();
            if ( "ear".equals( artifact.getType() ) )
            {
                return artifact.getFile();
            }
        }
        throw new IllegalArgumentException( "EAR not found in artifact list." );
    }

    /**
     * Returns the fully qualified path to a war file in the artifact list.
     *
     * @param inArtifacts - the set of artifacts
     * @return the fully qualified path to an war file in the artifact list.
     * @throws IllegalArgumentException - when a war is not found
     */
    public static File getWarFileName( final Set inArtifacts )
    {
        if ( inArtifacts == null || inArtifacts.isEmpty() )
        {
            throw new IllegalArgumentException( "WAR not found in artifact list." );
        }

        final Iterator iter = inArtifacts.iterator();
        while ( iter.hasNext() )
        {
            Artifact artifact = (Artifact) iter.next();
            if ( "war".equals( artifact.getType() ) )
            {
                return artifact.getFile();
            }
        }
        throw new IllegalArgumentException( "WAR not found in artifact list." );
    }

    /**
     * Returns the fully qualified path to an war file in the artifact list.
     *
     * @param inArtifacts - the set of artifacts
     * @param fileName    - the file name we are looking for in the aftifact list
     * @return the fully qualified path to an war file in the artifact list.
     */
    public static File getWarFileName( final Set inArtifacts, String fileName )
    {
        if ( inArtifacts == null || inArtifacts.isEmpty() )
        {
            throw new IllegalArgumentException( "WAR not found in artifact list." );
        }

        final Iterator iter = inArtifacts.iterator();
        while ( iter.hasNext() )
        {
            Artifact artifact = (Artifact) iter.next();

            if ( "war".equals( artifact.getType() ) && artifact.getFile().getName().contains( fileName ) )
            {
                return artifact.getFile();
            }
        }
        throw new IllegalArgumentException( "WAR not found in artifact list." );
    }

    /**
     * Returns the ejb file type from the artifact list
     *
     * @param inArtifacts - the dependency artifacts
     * @return the File object corresponding to the ejb jar type from the artifact list
     */
    public static File getEjbJarFileName( final Set inArtifacts )
    {
        if ( inArtifacts == null || inArtifacts.isEmpty() )
        {
            throw new IllegalArgumentException( "EJB jar not found in artifact list." );
        }

        final Iterator iter = inArtifacts.iterator();
        while ( iter.hasNext() )
        {
            Artifact artifact = (Artifact) iter.next();
            if ( "ejb".equals( artifact.getType() ) )
            {
                return artifact.getFile();
            }
        }
        throw new IllegalArgumentException( "EJB jar not found in artifact list." );
    }


    /**
     * Get Dependencies
     *
     * @param artifacts
     * @param pluginArtifacts
     * @param files
     * @return
     */
    public static String getDependencies( final Set artifacts, final List pluginArtifacts, File[] files ) {
        StringBuilder dependencies = new StringBuilder( getDependencies( artifacts, pluginArtifacts ) );
        //now append the rest of the files
        if ( files != null ) {
            for ( File f : files ) {
                dependencies.append( System.getProperty( "path.separator" ) );
                dependencies.append( f.getAbsolutePath() );
            }
        }
        return dependencies.toString();
    }

    /**
     * Return the dependencies as a list of URL
     *
     * @param artifacts       the artifacts
     * @param pluginArtifacts the plugin artifacts
     * @return the list
     * @throws MojoExecutionException when there are issues
     */
    public static URI[] getDependenciesAsUri( final Set artifacts, final List pluginArtifacts )
        throws MojoExecutionException
    {
        final File[] files = getDependenciesAsFile( artifacts, pluginArtifacts );
        final URI[] uris = new URI[files.length];
        for ( int i = 0; i < files.length; i++ )
        {
            uris[i] = files[i].toURI();
        }
        return uris;
    }

    /**
     * Return the dependencies as a list of URL
     *
     * @param artifacts       the artifacts
     * @param pluginArtifacts the plugin artifacts
     * @return the list
     * @throws MojoExecutionException when there are issues
     */
    public static File[] getDependenciesAsFile( final Set artifacts, final List pluginArtifacts )
        throws MojoExecutionException
    {
        if ( ( artifacts == null || artifacts.isEmpty() ) && ( pluginArtifacts == null
            || pluginArtifacts.size() == 0 ) )
        {
            return new File[0];
        }

        final List urls = new ArrayList( 16 );
        final Iterator pluginIter = pluginArtifacts.iterator();
        while ( pluginIter.hasNext() )
        {
            Artifact artifact = (Artifact) pluginIter.next();
            urls.add( artifact.getFile() );
        }

        // Loop over all the artifacts and create a classpath string.
        final Iterator iter = artifacts.iterator();

        while ( iter.hasNext() )
        {
            Artifact artifact = (Artifact) iter.next();
            urls.add( artifact.getFile() );
        }

        return (File[]) urls.toArray( new File[urls.size()] );
    }

    /**
     * This method will get the PLUGIN dependencies from the pom and construct a
     * classpath string to be used to run a mojo where a classpath is required.
     *
     * @param artifacts       The Set of artifacts for the pom being run.
     * @param pluginArtifacts the plugin artifacts
     * @return A string representing the current classpath for the pom.
     */
    public static String getDependencies( final Set artifacts, final List pluginArtifacts ) {

        if ( ( artifacts == null || artifacts.isEmpty() ) &&
                ( pluginArtifacts == null || pluginArtifacts.size() == 0 ) ) {
            return "";
        }

        final StringBuffer buffer = new StringBuffer( 1024 );

        final Iterator pluginIter = pluginArtifacts.iterator();
        if ( pluginIter.hasNext() ) {
            if ( buffer.length() > 0 ) {
                buffer.append( System.getProperty( "path.separator" ) );
            }
            Artifact artifact = (Artifact) pluginIter.next();
            if ( buffer.length() > 0 ) {
                buffer.append( System.getProperty( "path.separator" ) );
            }
            buffer.append( artifact.getFile() );

            while ( pluginIter.hasNext() ) {
                artifact = (Artifact) pluginIter.next();
                buffer.append( System.getProperty( "path.separator" ) );
                buffer.append( artifact.getFile() );
            }
        }

        // Loop over all the artifacts and create a classpath string.
      final Iterator iter = artifacts.iterator();


        if ( iter.hasNext() ) {
            if ( buffer.length() > 0 ) {
                buffer.append( System.getProperty( "path.separator" ) );
            }
            Artifact artifact = (Artifact) iter.next();
            buffer.append( artifact.getFile() );
            if ( buffer.length() > 0 ) {
                buffer.append( System.getProperty( "path.separator" ) );
            }
            while ( iter.hasNext() ) {
                artifact = (Artifact) iter.next();
                buffer.append( System.getProperty( "path.separator" ) );
                buffer.append( artifact.getFile() );
            }
        } 

        return buffer.toString();
    }

    /**
     * Convert to classpath
     *
     * @param urls the urls to process
     * @return the separated classpath
     */
    public static String toClassPath( URL[] urls ) {
        StringBuilder buffer = new StringBuilder();
        final String SEP = System.getProperty( "path.separator" );
        if ( urls != null ) {
            for ( URL url : urls ) {
                if ( buffer.length() > 0 ) {
                    buffer.append( SEP );
                }
                buffer.append( url.getPath() );
            }
        }

        return buffer.toString();
    }


    
	public static BuildListener getDefaultLogger() {
		 final DefaultLogger antLogger = new DefaultLogger();
	        antLogger.setOutputPrintStream(System.out);
	        antLogger.setErrorPrintStream(System.err);
	        antLogger
	                .setMessageOutputLevel(Project.MSG_INFO);
	        return antLogger;
	}
}
