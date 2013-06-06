package com.voisix.osgi.web.wicket.application;



import java.io.Serializable;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;
import org.ops4j.pax.wicket.api.PaxWicketBean;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	@PaxWicketBean(injectionSource=PaxWicketBean.INJECTION_SOURCE_SPRING, name="randomIntegerModel")
	private IModel<Serializable> randomIntegerModel;
	
	@PaxWicketBean(injectionSource=PaxWicketBean.INJECTION_SOURCE_SPRING, name="sumIntegerModel")
	private IModel<Serializable> sumIntegerModel;
	
	@PaxWicketBean(injectionSource=PaxWicketBean.INJECTION_SOURCE_SPRING, name="avgIntegerModel")
	private IModel<Serializable> avgIntegerModel;
	
	public HomePage() {
		final Label randomIntegerLabel 	= new Label("randomIntegerLabel", 	randomIntegerModel);
		final Label sumIntegerLabel 	= new Label("sumIntegerLabel", 		sumIntegerModel);
		final Label avgIntegerLabel 	= new Label("avgIntegerLabel", 		avgIntegerModel);
		
		randomIntegerLabel.setOutputMarkupId(true);
		sumIntegerLabel.setOutputMarkupId(true);
		avgIntegerLabel.setOutputMarkupId(true);
				
		add(randomIntegerLabel);
		add(sumIntegerLabel);
		add(avgIntegerLabel);
		
		add(new AbstractAjaxTimerBehavior(Duration.seconds(1)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onTimer(AjaxRequestTarget target) {
				target.add(randomIntegerLabel);
				target.add(sumIntegerLabel);
				target.add(avgIntegerLabel);
			}
		});
	}
}
