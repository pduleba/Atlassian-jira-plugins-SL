package com.company.soft.mycompany.connector.impl;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import com.company.soft.mycompany.connector.dto.WorkLogResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorkLogResponseFactory {
	
	static final String INVALID_AUTH_MESSAGE = "Unexpected authentication problem.";
	
	public static WorkLogResponse createInvalidAuth(HttpStatusCodeException ex) {
		return new WorkLogResponse(ex.getStatusCode(), INVALID_AUTH_MESSAGE );
	}
	
	//if you get response its mean it's status OK - otherwise HttpClientErrorException should occurs
	public static WorkLogResponse create(ResponseEntity<String> responseEntity) {
		final String message = getMessageFromJSON( responseEntity.getBody() );
		WorkLogResponse response = new WorkLogResponse(responseEntity.getStatusCode(), message);
		log.debug("Created: " + response.toString());
		return response;
	}
	
	public static WorkLogResponse create(HttpStatusCodeException ex) {
		final String message = getMessageFromJSON( ex.getResponseBodyAsString() );
		WorkLogResponse response = new WorkLogResponse(ex.getStatusCode(), message );
		log.debug("Created: " + response.toString());
		return response;
	}
	
	private static String getMessageFromJSON(String str) {
		JSONObject jsonObject = new JSONObject();
		Object messageObj = jsonObject.get("message");
		if (messageObj == null) {
			return null;
		}
		return messageObj.toString();
	}
}
