package com.pduleba.jira.action.override;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.issue.comment.CommentService;
import com.atlassian.jira.bc.issue.worklog.WorklogService;
import com.atlassian.jira.config.FeatureManager;
import com.atlassian.jira.datetime.DateTimeFormatterFactory;
import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutManager;
import com.atlassian.jira.security.roles.ProjectRoleManager;
import com.atlassian.jira.security.xsrf.RequiresXsrfCheck;
import com.atlassian.jira.user.util.UserUtil;
import com.atlassian.jira.util.JiraDurationUtils;
import com.atlassian.jira.web.FieldVisibilityManager;
import com.atlassian.jira.web.action.issue.CreateWorklog;

public class ExtendedCreateWorklog extends CreateWorklog {

	private static final long serialVersionUID = -5051846286913262135L;

	private static final Logger LOG = LoggerFactory.getLogger(ExtendedCreateWorklog.class);

	private ExternalServiceApi externalService;
	
	public ExtendedCreateWorklog(WorklogService worklogService, CommentService commentService,
			ProjectRoleManager projectRoleManager, JiraDurationUtils jiraDurationUtils,
			DateTimeFormatterFactory dateTimeFormatterFactory, FieldVisibilityManager fieldVisibilityManager,
			FieldLayoutManager fieldLayoutManager, RendererManager rendererManager, UserUtil userUtil,
			FeatureManager featureManager) {
		super(worklogService, commentService, projectRoleManager, jiraDurationUtils, dateTimeFormatterFactory,
				fieldVisibilityManager, fieldLayoutManager, rendererManager, userUtil, featureManager);
		this.externalService = new ExternalServiceImpl();
	}
	
    @Override
    public String doDefault() throws Exception {
        return super.doDefault();
    }
 
    @Override
    protected void doValidation() {
        super.doValidation();
    }
 
    @Override
    @RequiresXsrfCheck
    protected String doExecute() throws Exception {
    	String baseResult = super.doExecute();
    	
    	ExternalServiceResult result = this.externalService.call(this.getWorklog());
    	if (result.isError()) {
    		LOG.error("External service execution error");
    		// 1. 
        	addErrorMessage(result.getMessage());
        	// 2. 
        	return returnMsgToUser(getReturnUrl(), result.getMessage(), MessageType.ERROR, true, null);
    	} else {
    		LOG.info("External service executed successfully");
        	return baseResult;
    	}
    }
}
