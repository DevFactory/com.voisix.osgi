package com.voisix.osgi.jdbc.test;

import static org.apache.karaf.tooling.exam.options.KarafDistributionOption.logLevel;
import static org.junit.Assert.fail;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.scanFeatures;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.karaf.tooling.exam.options.LogLevelOption.LogLevel;
import org.junit.Assert;
import org.junit.Test;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationAdmin;

public class DataSourceManagedServiceFactoryTest extends AbstractKarafIntegrationTest {
	
	@Inject
	private ConfigurationAdmin configurationAdmin;
	
	private final String filter = "(name=commons-dbcp)";
		
	@Configuration
	public final Option[] configuration() {
		return options(
				getKarafDistributionBaseConfigurationOption(),
				scanFeatures("mvn:org.apache.karaf.features/standard/3.0.0.RC1/xml/features", "eventadmin"),	
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
				logLevel(LogLevel.INFO)
				// debugConfiguration()
				// keepRuntimeFolder()
				);
	}
	
	@Test
	public void testConfigurationProvision() {			
		provisionJdbcConfiguration();
		assertServiceAvailable(DataSource.class, filter, 100000);				
		testConnection();				
	}
	
	public void testConnection() {
		try {
			final Collection<ServiceReference<DataSource>> dataSourceServiceReferences = bundleContext.getServiceReferences(DataSource.class, filter);
			final DataSource dataSource = bundleContext.getService(dataSourceServiceReferences.iterator().next());
			final Connection connection = dataSource.getConnection();
			logger.info("Successfuly connected to " + connection.getCatalog() );
			connection.close();			
		} catch (InvalidSyntaxException e) {
			Assert.fail(e.getMessage());
		} catch (SQLException e) {
			Assert.fail(e.getMessage());
		}
	}

	private final void provisionJdbcConfiguration() {
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
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			fail(e.getMessage());
		}
	}
}
