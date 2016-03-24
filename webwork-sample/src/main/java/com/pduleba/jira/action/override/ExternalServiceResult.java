package com.pduleba.jira.action.override;

public class ExternalServiceResult {

	private String message;
	private boolean error;
	
	public ExternalServiceResult() {
		super();
		this.error = true;
		this.message = "Default error message";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

}
