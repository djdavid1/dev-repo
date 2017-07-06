# dev-repo
Develop Repository

# Project: MusicApp
Author: David Janicki
Version: 1.0
Date: 06.07.2017
=======================================================================

# Used Eclipse Plugin
Eclipse Marketplace - SpringIDE 3.8.4.RELEASE 
		      JBoss Tools 4.4.4.Final
		      SonarLint 3.20

# Used Java version - jdk1.8.0_131

# Eclipse - Open Perspektive - Git
Clone Git Repository: https://github.com/djdavid1/dev-repo.git

# Switch to Spring Perspektive
Import > Git > Projects from Git > Existing local repository 
	> Select dev-repo > Import existing projects > Next > Select Project "MusicApp"

=======================================================================

# Maven run
mvn clean install jetty:run

# Test
 /input   -  for input xml files
 /output  -  get output result as xml file

# Logs
/jetty/logs/app.log

# Configurations
src/main/resources
	log4j.properties  -> log4j configuration
	config.properties -> MusicApp configuration

=======================================================================

# Deployment War
1) /src/main/webapp/WEB-INF/spring/applicationContext.xml
change following line:
<value>file:${APP_CONFIG_ROOT}#{servletContext.contextPath}/config.properties
to <value>file:C:\Users\Olaf\MusicApp\config.properties<value>

2) copy config.properties and modify input/output-directory
src/main/resources/config.properties
to file:C:\Users\Olaf\MusicApp\config.properties

3) Maven
mvn clean install
/target/MusicApp-x.x.x-SNAPSHOT.war

4) deploy to any servlet engine: Tomcat, Jetty, Wildfly


