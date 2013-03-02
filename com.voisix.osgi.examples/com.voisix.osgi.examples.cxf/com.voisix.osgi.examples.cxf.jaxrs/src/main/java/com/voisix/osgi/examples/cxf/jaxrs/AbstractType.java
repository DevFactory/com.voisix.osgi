package com.voisix.osgi.examples.cxf.jaxrs;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;


//@JsonDeserialize(using = AbstractTypeDeserializer.class)
@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
public abstract class AbstractType {
	
	private String abstractProperty = "Hello World";

	public String getAbstractProperty() {
		return abstractProperty;
	}

	public void setAbstractProperty(String abstractProperty) {
		this.abstractProperty = abstractProperty;
	}
}
