package com.goldenrace.tickets.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ErrorMessage {

	public static final String NEW = "NEW";
	public static final String UPDATE = "UPDATE";

	private String codMessage;
	private List<Map<String, String>> messages;

	public ErrorMessage(String codMessage) {
		this.codMessage = codMessage;
		this.messages = Lists.newArrayList();
	}

	public String getMensaje(BindingResult result) {
		String resultMsn = "";
		
		if(null != result) {
			this.messages = result.getFieldErrors().stream().map(err -> {
				Map<String, String> error = new HashMap<>();
				error.put(err.getField(), err.getDefaultMessage());
				return error;
			}).collect(Collectors.toList());
			resultMsn = formatJson();
		}
		return resultMsn;
	}

	public String formatJson() {
		String jsonString = "";
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			jsonString = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			jsonString = "Error in formatJson ";
		}
		return jsonString;
	}

}
