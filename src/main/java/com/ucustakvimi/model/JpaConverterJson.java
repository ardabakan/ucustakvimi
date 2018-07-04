package com.ucustakvimi.model;

import javax.persistence.AttributeConverter;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JpaConverterJson implements AttributeConverter<Object, String> {

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Object meta) {
		try {
			return objectMapper.writeValueAsString(meta);
		} catch (JsonProcessingException ex) {
			return null;
			// or throw an error
		}
	}

	@Override
	public JSONObject convertToEntityAttribute(String dbData) {
		try {
			return new JSONObject(dbData);
		} catch (Exception ex) {
			// logger.error("Unexpected IOEx decoding json from database: "
			// + dbData);
			return null;
		}
	}

}
