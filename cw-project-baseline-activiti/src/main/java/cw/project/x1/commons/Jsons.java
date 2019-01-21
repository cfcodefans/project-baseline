package cw.project.x1.commons;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.InputStream;
import java.util.Map;

public final class Jsons {
	private Jsons() {
	}

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private static final ObjectMapper _MAPPER = new ObjectMapper();

	static {
		MAPPER.configure(Feature.AUTO_CLOSE_SOURCE, true);
		MAPPER.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		MAPPER.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		MAPPER.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		MAPPER.setSerializationInclusion(Include.NON_NULL);
		MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
		MAPPER.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
		MAPPER.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);

		_MAPPER.configure(Feature.AUTO_CLOSE_SOURCE, true);
		_MAPPER.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		_MAPPER.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		_MAPPER.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		_MAPPER.setSerializationInclusion(Include.NON_NULL);

		_MAPPER.configure(SerializationFeature.INDENT_OUTPUT, false);
		_MAPPER.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
		_MAPPER.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
	}

	/*This method is unused except the tests*/
	public static JsonNode read(final InputStream input) {
		if (input == null) {
			throw new NullPointerException("reading json however the input stream is empty");
		}
		try {
			return MAPPER.readValue(input, JsonNode.class);
		} catch (Exception e) {
			throw new RuntimeException("reading json stream",e);
		}
	}

	public static JsonNode read(final String raw) {
		if (raw == null) {
			throw new NullPointerException("reading json however the json raw is empty");
		}
		try {
			return MAPPER.readValue(raw, JsonNode.class);
		} catch (Exception e) {
			throw new RuntimeException("reading json raw",e);
		}
	}
	
	public static <T> T read(final String raw, final Class<T> cls) {
		if (raw == null) {
			throw new NullPointerException("reading json however the json raw is empty");
		}
		try {
			return MAPPER.readValue(raw, cls);
		} catch (Exception e) {
			throw new RuntimeException("reading json raw",e);
		}
	}

	public static String toString(final Object obj) {
		try {
			return MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("deserialize json to string",e);
		}
	}

	public static String toLine(final Object obj) {
		try {
			return _MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("deserialize json to string",e);
		}
	}

	public static JsonNode toJson(Map<String, Object> map) {
		try {
			ObjectNode jn = MAPPER.createObjectNode();
			map.forEach(jn::putPOJO);
			return jn;
		} catch (Exception e) {
			throw new RuntimeException("deserialize json to string", e);
		}
	}

	public static JsonNode toLine(Map<String, Object> map) {
		try {
			ObjectNode jn = _MAPPER.createObjectNode();
			map.forEach(jn::putPOJO);
			return jn;
		} catch (Exception e) {
			throw new RuntimeException("deserialize json to string", e);
		}
	}
}

