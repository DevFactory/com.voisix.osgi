package com.voisix.osgi.examples.cxf.jaxrs;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;

public class AbstractTypeDeserializer extends JsonDeserializer<AbstractType> {
		
	@Override
	public AbstractType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		final ObjectMapper mapper 	= (ObjectMapper) jsonParser.getCodec();
        final JsonNode root 		= mapper.readTree(jsonParser);
        final String className 		= root.get("className").getTextValue();
        
        try {
			final Class<?> valueType = Class.forName(className);
			final AbstractType type = (AbstractType) mapper.readValue(root, valueType);
			
			return type;
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		} catch (Exception e) {
			throw new IOException(e);
		}		
	}
}
