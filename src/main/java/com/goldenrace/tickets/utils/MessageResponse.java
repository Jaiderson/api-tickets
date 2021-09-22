package com.goldenrace.tickets.utils;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MessageResponse {

	public static final String ALREADY_EXIST = "Record already exist. ";
	public static final String NO_EXIST = "Record no exist. ";
	public static final String SQL_ERROR = "Error in data base process. ";
	public static final String CREATED_OK = "Record created. ";
	public static final String PROCESS_OK = "Processed correctly. ";
	public static final String NOT_AUTHORIZED = "Application rejected, no access to the resource. ";

	@ApiModelProperty(position=1, dataType="String", value="Status of a request. <br>", 
	                  example="Record created.", required=false)
	private String status;

	@ApiModelProperty(position=2, dataType="String", value="Inconsistencies list of a request.", 
			example="['SQL Exception Record already exist','Application rejected, no access to the resource']",
			required=false)
	private List<String> inconsistencies = Lists.newArrayList();

	@JsonIgnore
	public HttpStatus generateHttpStatus() {
		HttpStatus result = null; 
		
		if(this.status.equals(ALREADY_EXIST)) {
			result = HttpStatus.CONFLICT;
		}
		else if(this.status.equals(SQL_ERROR)) {
			result = HttpStatus.FORBIDDEN;
		}
		else if(this.status.equals(NO_EXIST)) {
			result = HttpStatus.NOT_FOUND;
		}
		else if(this.status.equals(CREATED_OK)) {
			result = HttpStatus.CREATED;
		}
		else if(this.status.equals(PROCESS_OK)) {
			result = HttpStatus.OK;
		}
		else if(this.status.equals(NOT_AUTHORIZED)) {
			result = HttpStatus.UNAUTHORIZED;
		}
		return result;
	}

}
