package com.pduleba.jira.plugins.fields.creatoroutputtext;

import java.util.Map;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.customfields.impl.GenericTextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.TextFieldCharacterLengthValidator;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.security.JiraAuthenticationContext;

public class CreatorOutputTextCFType extends GenericTextCFType {

	private final JiraAuthenticationContext jiraCtx;
	
	public CreatorOutputTextCFType() {
		this(ComponentAccessor.getJiraAuthenticationContext());
	}
	
	private CreatorOutputTextCFType(JiraAuthenticationContext jiraCtx) {
        super(ComponentAccessor.getComponent(CustomFieldValuePersister.class),
                ComponentAccessor.getComponent(GenericConfigManager.class),
                ComponentAccessor.getComponent(TextFieldCharacterLengthValidator.class),
                jiraCtx);
		this.jiraCtx = jiraCtx;
	}

	public Map<String, Object> getVelocityParameters(Issue issue, CustomField field, FieldLayoutItem fieldLayoutItem) {
		Map<String, Object> params = super.getVelocityParameters(issue, field, fieldLayoutItem);
		params.put("currentUser", jiraCtx.getLoggedInUser().getName());
		return params;
	}
}