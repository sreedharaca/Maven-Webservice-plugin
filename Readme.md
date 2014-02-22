================================================
Weblogic Maven Plugin for Generating Webservices
================================================

Inspired by Open Source Developer spirit, I somehow developed this plugin to generate webservices for replicating JAXRPC and JAXWS generation ant task in Maven.

To use this plugin we need weblogic server libraries, that can be obtained from Oracle website.

Or download the same from http://www.codejacks.com/wls1036_dev.zip

Extract to C: drive

and Run these below commands

mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.rmi.client -Dversion=1.11.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.rmi.client_1.11.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=javax.enterprise.deploy -Dversion=1.2 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\javax.enterprise.deploy_1.2.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.management.core.binding -Dversion=2.9.0.1 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.management.core.binding_2.9.0.1.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.management.core -Dversion=2.9.0.1 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.management.core_2.9.0.1.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.security.identity -Dversion=1.2.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.security.identity_1.2.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.security.auth -Dversion=1.1.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.security.auth_1.1.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.security.digest -Dversion=1.0.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.security.digest_1.0.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.security.identity -Dversion=1.2.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.security.identity_1.2.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.security.logger -Dversion=1.6.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.security.logger_1.6.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.security.psm -Dversion=1.1.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.security.psm_1.1.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.security.ssl -Dversion=1.0.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.security.ssl_1.0.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.security.wls -Dversion=1.0.0.0_6-2-0-0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.security.wls_1.0.0.0_6-2-0-0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.security -Dversion=1.0.0.0_6-2-0-0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.security_1.0.0.0_6-2-0-0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.security.wls -Dversion=1.0.0.0_6-2-0-0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.security.wls_1.0.0.0_6-2-0-0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.workmanager -Dversion=1.11.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.workmanager_1.11.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.transaction -Dversion=2.7.1.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.transaction_2.7.1.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.logging -Dversion=1.9.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.logging_1.9.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.utils.classloaders -Dversion=2.0.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.utils.classloaders_2.0.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor.j2ee.binding -Dversion=1.6.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor.j2ee.binding_1.6.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor.j2ee -Dversion=1.6.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor.j2ee_1.6.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor.settable.binding -Dversion=1.7.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor.settable.binding_1.7.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor.wl.binding -Dversion=1.4.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor.wl.binding_1.4.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor.wl -Dversion=1.4.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor.wl_1.4.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor -Dversion=1.10.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor_1.10.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.timers -Dversion=1.7.1.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.timers_1.7.1.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.socket.api -Dversion=1.3.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.socket.api_1.3.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.common.security.api -Dversion=1.0.0.0_6-2-0-0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.common.security.api_1.0.0.0_6-2-0-0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.security.digest -Dversion=1.0.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.security.digest_1.0.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.lifecycle -Dversion=1.5.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.lifecycle_1.5.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.workarea -Dversion=1.8.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.workarea_1.8.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.utils.wrapper -Dversion=1.4.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.utils.wrapper_1.4.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.store.admintool -Dversion=1.3.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.store.admintool_1.3.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.store.gxa -Dversion=1.7.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.store.gxa_1.7.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.store.jdbc -Dversion=1.3.1.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.store.jdbc_1.3.1.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.store -Dversion=1.8.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.store_1.8.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.management.jmx -Dversion=1.4.2.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.management.jmx_1.4.2.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor.wl.binding -Dversion=1.4.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor.wl.binding_1.4.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor.wl -Dversion=1.4.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor.wl_1.4.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=javax.jws -Dversion=2.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\javax.jws_2.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.annogen -Dversion=1.4.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.annogen_1.4.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor.j2ee.binding -Dversion=1.6.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor.j2ee.binding_1.6.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.xml.staxb.runtime -Dversion=1.8.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.xml.staxb.runtime_1.8.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.xml.beaxmlbeans -Dversion=2.5.0.0_2-5-1 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.xml.beaxmlbeans_2.5.0.0_2-5-1.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor.j2ee.binding -Dversion=1.6.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor.j2ee.binding_1.6.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor.j2ee -Dversion=1.6.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor.j2ee_1.6.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=javax.ejb -Dversion=3.0.1 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\javax.ejb_3.0.1.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.xml.xmlbeans -Dversion=2.2.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.xml.xmlbeans_2.2.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.xml.xmlbeans -Dversion=2.2.0.0_2-5-1 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.xml.xmlbeans_2.2.0.0_2-5-1.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.stax -Dversion=1.10.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.stax_1.10.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=javax.xml.rpc -Dversion=1.1 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\javax.xml.rpc_1.1.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=javax.xml.rpc -Dversion=1.2.1 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\javax.xml.rpc_1.2.1.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.xml.staxb.buildtime -Dversion=1.6.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.xml.staxb.buildtime_1.6.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=glassfish.jaxws.rt -Dversion=1.3.0.0_2-1-5 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\glassfish.jaxws.rt_1.3.0.0_2-1-5.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor.wl.binding -Dversion=1.4.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor.wl.binding_1.4.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.descriptor.settable.binding -Dversion=1.7.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.descriptor.settable.binding_1.7.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.weblogic.saaj -Dversion=1.8.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.weblogic.saaj_1.8.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.i18n.generator -Dversion=1.5.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.i18n.generator_1.5.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.i18n.tools -Dversion=1.4.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.i18n.tools_1.4.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=com.bea.core.i18n -Dversion=1.9.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\com.bea.core.i18n_1.9.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=ws.api -Dversion=1.1.0.0 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\ws.api_1.1.0.0.jar
mvn install:install-file -DgroupId=weblogic -DartifactId=glassfish.jaxws.tools -Dversion=1.2.0.0_2-1-5 -Dpackaging=jar -Dfile=C:\wls1036_dev\modules\glassfish.jaxws.tools_1.2.0.0_2-1-5.jar


Dont forget to install the other 3 main jar files in server/lib folder to local  maven with groupid = weblogic and version 10.3.6

weblogic.jar
wseeclient.jar
webservices.jar 

Then checkout this maven project from github and compile and install.

Now write any webservice and use the sample-pom.xml given and change webservice type and  the includedsource filename to your specific webservice name.

run mvn compile to generate the webservice.

