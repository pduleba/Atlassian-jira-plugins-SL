package com.pduleba.jira.action.override;

import java.text.MessageFormat;
import java.util.Objects;

import com.atlassian.jira.issue.worklog.Worklog;

public class ExternalServiceResult {

	private String message;
	private boolean error;
	
	public ExternalServiceResult(Worklog workLog) {
		super();
		this.error = true;
		this.message = MessageFormat.format("MyPGS Connection error : time passed {0}",
				Objects.isNull(workLog) ? null : workLog.getTimeSpent());
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
