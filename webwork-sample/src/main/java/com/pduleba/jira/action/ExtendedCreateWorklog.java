package com.pduleba.jira.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.issue.comment.CommentService;
import com.atlassian.jira.bc.issue.worklog.WorklogService;
import com.atlassian.jira.config.FeatureManager;
import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutManager;
import com.atlassian.jira.security.roles.ProjectRoleManager;
import com.atlassian.jira.security.xsrf.RequiresXsrfCheck;
import com.atlassian.jira.user.util.UserUtil;
import com.atlassian.jira.util.JiraDurationUtils;
import com.atlassian.jira.web.FieldVisibilityManager;
import com.atlassian.jira.web.action.issue.CreateWorklog;
import com.atlassian.jira.web.util.OutlookDateManager;

public class ExtendedCreateWorklog extends CreateWorklog {

	private static final long serialVersionUID = -5051846286913262135L;

	private static final Logger LOG = LoggerFactory.getLogger(ExtendedCreateWorklog.class);

	public ExtendedCreateWorklog(WorklogService worklogService, CommentService commentService,
			ProjectRoleManager projectRoleManager, JiraDurationUtils jiraDurationUtils,
			OutlookDateManager outlookDateManager, FieldVisibilityManager fieldVisibilityManager,
			FieldLayoutManager fieldLayoutManager, RendererManager rendererManager, UserUtil userUtil,
			FeatureManager featureManager) {
		super(worklogService, commentService, projectRoleManager, jiraDurationUtils, outlookDateManager, fieldVisibilityManager,
				fieldLayoutManager, rendererManager, userUtil, featureManager);
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
//    	super.doExecute();
    	addErrorMessage("");
    	return returnMsgToUser(getReturnUrl(), "Super Hiper Message", MessageType.SUCCESS, true, null);
//    	return super.doExecute();
    }
	
}
