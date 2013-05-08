package com.voisix.osgi.jdbc.test;

import static org.apache.karaf.tooling.exam.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.apache.karaf.tooling.exam.options.KarafDistributionOption.logLevel;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.scanFeatures;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.karaf.tooling.exam.options.KarafDistributionBaseConfigurationOption;
import org.apache.karaf.tooling.exam.options.LogLevelOption.LogLevel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;


@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class DataSourceManagedServiceFactoryTest {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@Inject
	BundleContext bundleContext;
	
	@Inject
	ConfigurationAdmin configurationAdmin;
	
	@Configuration
	public static Option[] configuration() {
		return options(
				getKaraf(),
				
				scanFeatures("mvn:org.apache.karaf.features/standard/3.0.0.RC1/xml/features", "spring-dm"),				
				scanFeatures("mvn:org.apache.karaf.features/spring/3.0.0.RC1/xml/features", "spring-jdbc"),
				scanFeatures("mvn:org.apache.karaf.features/spring/3.0.0.RC1/xml/features", "spring-tx"),
				
				scanFeatures("mvn:com.voisix.osgi.jdbc/com.voisix.osgi.jdbc.features/1.0.0-SNAPSHOT/xml/features", "com.voisix.osgi.jdbc.features"),
								
//				new KarafDistributionConfigurationFileReplacementOption(
//						"deploy/javax.sql.DataSource-BasicDataSource.cfg", 
//						new File("src/test/resources/javax.sql.DataSource-BasicDataSource.cfg")),
//				new KarafDistributionConfigurationFileReplacementOption(
//						"deploy/javax.sql.DataSource-ComboPooledDataSource.cfg", 
//						new File("src/test/resources/javax.sql.DataSource-ComboPooledDataSource.cfg")),
//				new KarafDistributionConfigurationFileReplacementOption(
//						"deploy/javax.sql.DataSource-SimpleDriverDataSource.cfg", 
//						new File("src/test/resources/javax.sql.DataSource-SimpleDriverDataSource.cfg")),
				logLevel(LogLevel.WARN)
				);
	}
	
	@Test
	public void test2() {
		try {			
			org.osgi.service.cm.Configuration configuration = configurationAdmin.createFactoryConfiguration("javax.sql.DataSource", null );
			Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put("name", 				"commons-dbcp");
			properties.put("factoryClass", 		"org.apache.commons.dbcp.BasicDataSource");
			properties.put("driverClassName", 	"org.h2.Driver");		// "com.mysql.jdbc.Driver");
			properties.put("url", 				"jdbc:h2:mem:test"); 	// "jdbc:mysql://localhost:3306/test");
			properties.put("username",			"root");
			properties.put("password",			"");
			configuration.update(properties);
			Thread.sleep(3000);			
			test();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			fail(e.getMessage());
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
			fail(e.getMessage());
		}
	}

	//@Test
	public void test() throws InterruptedException {			
		ServiceTracker<DataSource, ServiceRegistration<DataSource>> tracker = new ServiceTracker<DataSource, ServiceRegistration<DataSource>>(bundleContext, DataSource.class, null);
		tracker.open();
		int count = tracker.getTrackingCount();
		logger.info(count);
		for (ServiceReference<DataSource> serviceReference : tracker.getServiceReferences()) {
			final DataSource dataSource = bundleContext.getService(serviceReference);		
			assertNotNull(dataSource);
			try {
				final Connection connection = dataSource.getConnection();
				assertNotNull(connection);				
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				Assert.fail(e.getMessage());
			}
		}
		tracker.close();			
	}
	
	public static KarafDistributionBaseConfigurationOption getKaraf() {
		return karafDistributionConfiguration()
				.frameworkUrl(
						maven()
							.groupId("org.apache.karaf")
							.artifactId("apache-karaf")
							.type("zip")
							.versionAsInProject())
			.name("apache-karaf")
			.unpackDirectory(new File("target/pax/exam/"));
	}
	
	private void assertBundleActive(String bundleName) {
		Bundle[] bundles = bundleContext.getBundles();
		boolean found = false;
		boolean active = false;

		for (Bundle bundle : bundles) {
			if (bundle.getSymbolicName().equals(bundleName)) {
				found = true;
				if (bundle.getState() == Bundle.ACTIVE) {
					active = true;
				}
				break;
			}
		}
		Assert.assertTrue(bundleName + " not found in container", found);
		Assert.assertTrue(bundleName + " not active", active);
	}
}
