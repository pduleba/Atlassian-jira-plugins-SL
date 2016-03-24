package com.pduleba.jira.action.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.web.action.JiraWebActionSupport;

abstract class AbstractExecutableAction extends JiraWebActionSupport {

	private static final long serialVersionUID = -1656197535650275806L;

	private static final Logger LOG = LoggerFactory.getLogger(ExecuteSuccessAction.class);

	private String status;
	private boolean failureResult;

	public AbstractExecutableAction(String status, boolean failureResult) {
		super();
		this.status = status;
		this.failureResult = failureResult;
	}
	
	@Override
	public String doDefault() throws Exception {
		LOG.debug("{} action executed", status);
		return getStatus();
	}

	protected abstract String getTemplateMessage();

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return getTemplateMessage();
	}

	public boolean isFailureResult() {
		return failureResult;
	}
}
