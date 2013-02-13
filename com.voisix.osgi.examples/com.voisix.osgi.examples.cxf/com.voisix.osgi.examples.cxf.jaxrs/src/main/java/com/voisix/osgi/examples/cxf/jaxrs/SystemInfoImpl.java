package com.voisix.osgi.examples.cxf.jaxrs;

import java.util.Calendar;

public class SystemInfoImpl implements SystemInfo {

	@Override
	public InfoBean getSystemInfo() {
		final InfoBean info = new InfoBean();
		info.setSystemProperties(System.getProperties());
		info.setDate(Calendar.getInstance().getTime());
		info.getMap().put("hello", "world");
		return info;
	}

}
