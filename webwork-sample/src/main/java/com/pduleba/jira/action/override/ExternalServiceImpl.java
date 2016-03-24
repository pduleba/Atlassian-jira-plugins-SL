package com.pduleba.jira.action.override;

import com.atlassian.jira.issue.worklog.Worklog;

public class ExternalServiceImpl implements ExternalServiceApi {

	@Override
	public ExternalServiceResult call(Worklog workLog) {
		return new ExternalServiceResult();
	}

}
