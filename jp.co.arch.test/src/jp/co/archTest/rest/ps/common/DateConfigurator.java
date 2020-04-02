package jp.co.archTest.rest.ps.common;

import java.text.SimpleDateFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
@Produces("application/json")
@Consumes("application/json")
public class DateConfigurator implements ContextResolver<ObjectMapper> {
	final ObjectMapper defaultObjectMapper;

	public DateConfigurator() {
		defaultObjectMapper = createDefaultMapper();
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return defaultObjectMapper;
	}

	private static ObjectMapper createDefaultMapper() {
		final ObjectMapper result = new ObjectMapper();
		result.setDateFormat(new SimpleDateFormat("yyyyMMddHHmmssSSS"));
		return result;
	}

}
