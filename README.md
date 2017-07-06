# dev-repo
	Develop Repository

# Project: MusicApp
	Author: David Janicki
	Version: 1.0
	Date: 06.07.2017
=======================================================================

# Eclipse 
	Used Eclipse Plugins:
	Eclipse Marketplace - SpringIDE 3.8.4.RELEASE 
		      	    - JBoss Tools 4.4.4.Final
		            - SonarLint 3.20

	Used Java version: jdk1.8.0_131

# Git
 	Eclipse - Open Perspektive - Git
	Clone Git Repository: https://github.com/djdavid1/dev-repo.git
	
	git clone https://github.com/djdavid1/dev-repo.git

# Import Project
	Switch to Spring Perspektive
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
	<value>file:C:\Users\djdav\MusicApp\config.properties<value> (example for windows)

	2) copy config.properties and modify/create input/output-directory
	src/main/resources/config_WIN.properties     (example for windows)
	to file:C:\Users\djdav\MusicApp\config.properties

	2) modify log4j for logging on webserver (example for tomcat)
	src/main/resources/log4j.properties
	#log4j.appender.file.File=./jetty/logs/app.log			 (eclipse mvn:jetty)
	log4j.appender.file.File=${catalina.base}/logs/MusicApp.log      (tomcat) 
	#log4j.appender.file.File=${jboss.server.log.dir}/MusicApp.log   (wildfly)

	3) Maven
	mvn clean install
	/target/MusicApp-x.x.x-SNAPSHOT.war

	4) deploy to any webserver (example for tomcat)
	copy /target/MusicApp-x.x.x-SNAPSHOT.war to %CATALINA_HOME%/webapps


