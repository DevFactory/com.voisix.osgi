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

install -s mvn:com.voisix.osgi/com.voisix.osgi.jmx/1.0.0-SNAPSHOT/war


Apache CXF Features
===================

repo-add mvn:org.apache.cxf.karaf/apache-cxf/2.7.3/xml/features

feature:install cxf

install -s mvn:org.codehaus.jackson/jackson-core-asl/1.9.12

install -s mvn:org.codehaus.jackson/jackson-mapper-asl/1.9.12

install -s mvn:org.codehaus.jackson/jackson-jaxrs/1.9.12

install -s mvn:org.codehaus.jackson/jackson-xc/1.9.12


Batch Features
==============

install -s mvn:com.h2database/h2/1.3.170

install -s mvn:javax.xml.stream/com.springsource.javax.xml.stream/1.0.1

install -s mvn:org.xmlpull/com.springsource.org.xmlpull/1.1.4.c

install -s mvn:com.thoughtworks.xstream/com.springsource.com.thoughtworks.xstream/1.3.1

install -s mvn:javax.xml.stream/com.springsource.javax.xml.stream/1.0.1

install -s mvn:org.springframework.batch/spring-batch-core/2.1.9.RELEASE

install -s mvn:org.springframework.batch/spring-batch-infrastructure/2.1.9.RELEASE

install -s mvn:com.voisix.osgi.batch/com.voisix.osgi.batch.core/1.0.0-SNAPSHOT


Quartz Scheduler
================

install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.quartz/2.1.6_1
