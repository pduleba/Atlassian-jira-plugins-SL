package com.company.soft.mycompany.connector.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class WorkLogResponse {
	@Getter private HttpStatus statusCode;
	@Getter private String message;
	
	public boolean isOk() {
		return HttpStatus.OK.equals(statusCode);
	}
	
}
