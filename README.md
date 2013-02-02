com.voisix.osgi
===============

Apache Karaf Features

feature:install -v webconsole
feature:install -v war
feature:install -v aries-annotation 
feature:install -v spring-dm
feature:install -v spring-jdbc 

feature:repo-add mvn:com.voisix.osgi.jdbc/com.voisix.osgi.jdbc.features/1.0.0-SNAPSHOT/xml/features

feature:install -v com.voisix.osgi.jdbc.features 
