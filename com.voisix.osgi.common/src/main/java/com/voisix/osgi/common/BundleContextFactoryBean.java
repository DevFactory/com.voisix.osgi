package com.voisix.osgi.common;

import org.osgi.framework.BundleContext;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.osgi.context.BundleContextAware;

public class BundleContextFactoryBean implements BundleContextAware, FactoryBean<BundleContext> {
	private BundleContext bundleContext;
	
	@Override
	public BundleContext getObject() throws Exception {
		return bundleContext;
	}

	@Override
	public Class<?> getObjectType() {
		return BundleContext.class;
	}

	@Override
	public boolean isSingleton() {		
		return true;
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;		
	}
}
