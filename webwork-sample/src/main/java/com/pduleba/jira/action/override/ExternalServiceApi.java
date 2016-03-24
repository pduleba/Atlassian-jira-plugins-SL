package com.pduleba.jira.action.override;

import com.atlassian.jira.issue.worklog.Worklog;

public interface ExternalServiceApi {

	public ExternalServiceResult call(Worklog worklog);
	
}
