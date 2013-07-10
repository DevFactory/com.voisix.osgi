package com.voisix.osgi.jdbc.test;

import static org.apache.karaf.tooling.exam.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.CoreOptions.maven;

import java.io.File;
import java.text.MessageFormat;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.karaf.tooling.exam.options.KarafDistributionBaseConfigurationOption;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public abstract class AbstractKarafIntegrationTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Inject
	protected BundleContext bundleContext;
	
	protected KarafDistributionBaseConfigurationOption getKarafDistributionBaseConfigurationOption() {
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
	
	protected <T> void assertServiceAvailable(Class<T> serviceClass, String filter, long timeout) {
		Assert.assertTrue("Timeout must be positive", timeout>0);
		final long timestamp = System.currentTimeMillis();
		try {			
			do {
				final Collection<ServiceReference<T>> serviceReferences = bundleContext.getServiceReferences(serviceClass, filter);
				if (!serviceReferences.isEmpty()) {
					logger.info(MessageFormat.format("Found {0} service, filter: {1} in {2} ms.", serviceClass.getName(), filter, System.currentTimeMillis()-timestamp));
					return;				
				}				
				Thread.sleep(1000);				
			} while (timestamp + timeout > System.currentTimeMillis());			
			Assert.fail(MessageFormat.format("Cannot find {0} service, filter: {1} in {2} ms.", serviceClass.getName(), filter, timeout));
		} catch (InvalidSyntaxException e) {
			Assert.fail(e.getMessage());
		} catch (InterruptedException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	protected void assertBundleActive(String bundleName) {
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
