package com.voisix.osgi.web.wicket.application;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.ws.jetty.Jetty7WebSocketFilter;
import org.ops4j.pax.wicket.api.SuperFilter;
import org.ops4j.pax.wicket.api.support.SimpleWebApplicationFactory;

/**
 * See https://ops4j1.jira.com/browse/PAXWICKET-408
 * @author assen
 *
 */
@SuperFilter(filter = Jetty7WebSocketFilter.class)
public class CustomWebApplicationFactory extends SimpleWebApplicationFactory<WebApplication> {

}
