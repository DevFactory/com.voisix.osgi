package com.voisix.osgi.web.wicket.application;


import java.io.Serializable;
import java.text.MessageFormat;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.ops4j.pax.wicket.api.PaxWicketBean;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	//@PaxWicketBean(name="label.randomInteger")
	@Inject
	private IModel<Serializable> labelRandomInteger;
	
	private String applicationName;
	private String sessionId;
	private Integer pageId;

	private static final Logger log = LoggerFactory.getLogger(HomePage.class);
	
	public HomePage() {				
		final Label label = new Label("label.randomInteger", labelRandomInteger);
		add(label);
		add(new WebSocketBehavior() {			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onConnect(ConnectedMessage message) {
				applicationName = message.getApplication().getName();
				sessionId = message.getSessionId();
				pageId = message.getPageId();
				
				log.info(MessageFormat.format("[WebSocket:onConnect][applicationName: {0}][sessionId: {1}][pageId: {2}]", applicationName, sessionId, pageId));
			}
		});
	}
}
