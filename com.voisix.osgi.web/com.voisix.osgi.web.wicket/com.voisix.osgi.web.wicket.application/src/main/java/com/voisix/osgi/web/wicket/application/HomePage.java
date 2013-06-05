package com.voisix.osgi.web.wicket.application;


import java.io.Serializable;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.ops4j.pax.wicket.api.PaxWicketBean;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	@PaxWicketBean(name="labelModel")
	@Inject
	private IModel<Serializable> labelModel;
	
	public HomePage() {				
		final Label label = new Label("labelId", labelModel);
		add(label);
	}
}
