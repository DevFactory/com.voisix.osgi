com.voisix.osgi
===============

Apache Karaf Features

feature:install -v webconsole

feature:install -v war

feature:install -v aries-annotation
 
feature:install -v spring-dm

feature:install -v spring-jdbc 

feature:install -v eventadmin

feature:repo-add mvn:com.voisix.osgi.jdbc/com.voisix.osgi.jdbc.features/1.0.0-SNAPSHOT/xml/features

feature:install -v com.voisix.osgi.jdbc.features

feature:repo-add mvn:com.voisix.osgi.cache/com.voisix.osgi.cache.features/1.0.0-SNAPSHOT/xml/features

feature:install -v com.voisix.osgi.cache.features  

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

install -s mvn:com.voisix.osgi.examples.batch/com.voisix.osgi.examples.batch.job.helloworld/1.0.0-SNAPSHOT

Quartz Scheduler
================

install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.quartz/2.1.6_1

Active MQ
=========
feature:repo-add mvn:org.apache.activemq/activemq-karaf/5.8.0/xml/features
feature:install -v activemq-client
feature:install -v activemq-web-console 


Camel EI
========


feature:repo-add mvn:org.apache.camel.karaf/apache-camel/2.11.0/xml/features
feature:install -v camel-jms
feature:install -v camel-spring
feature:install -v camel-script
feature:install -v camel-script-javascript
feature:install -v camel-groovy
feature:install -v camel-script-groovy 

#install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-collections/3.2.1_3
install -s mvn:com.voisix.osgi.ei/com.voisix.osgi.ei.test/1.0.0-SNAPSHOT

PAX-Wicket
==========

feature:repo-add mvn:org.ops4j.pax.wicket/features/3.0.0-SNAPSHOT/xml/features
feature:repo-add mvn:org.ops4j.pax.wicket.samples/features/3.0.0-SNAPSHOT/xml/features

feature:install -v wicket
feature:install -v pax-wicket
feature:install -v pax-wicket-spring

install -s mvn:org.apache.wicket/wicket-native-websocket-core/0.6
install -s mvn:org.apache.wicket/wicket-native-websocket-jetty/0.6

install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.javax-inject/1_2

install -s mvn:org.ops4j.pax.wicket.samples.springdm/org.ops4j.pax.wicket.samples.springdm.simple/2.1.0

install -s mvn:com.voisix.osgi.web.wicket/com.voisix.osgi.web.wicket.application/1.0.0-SNAPSHOT


Restlet Framework
=================

install -s mvn:org.restlet.jee/org.restlet.ext.servlet/2.0.15
install -s mvn:org.restlet.jee/org.restlet.ext.spring/2.0.15
