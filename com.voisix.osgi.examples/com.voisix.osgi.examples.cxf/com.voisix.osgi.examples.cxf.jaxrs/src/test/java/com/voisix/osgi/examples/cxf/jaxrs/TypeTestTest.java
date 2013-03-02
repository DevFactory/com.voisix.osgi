package com.voisix.osgi.examples.cxf.jaxrs;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration
public class TypeTestTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	TypeTest typeTest;
	
	//@Test
	public void testTypeTest() {
		final TypeA typeA = new TypeA();
		final TypeB typeB = new TypeB();
		
		typeA.setStringProperty("stringProperty");
		typeB.setIntegerProperty(42);
		
		typeTest.log(typeA);
		typeTest.log(typeB);
	}
	
	@Test
	public void testListTypeTest() {
		final TypeA typeA = new TypeA();
		final TypeB typeB = new TypeB();
		final List<AbstractType> list = new ArrayList<AbstractType>();
		typeA.setStringProperty("stringProperty");
		typeB.setIntegerProperty(42);
		list.add(typeA);
		list.add(typeB);
		
		typeTest.logList(list);
		
	}
}
