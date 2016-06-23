package com.pduleba.jira.plugins.fields.outputtext;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.customfields.impl.GenericTextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.fields.TextFieldCharacterLengthValidator;

public class OutputTextCFType extends GenericTextCFType {

	protected OutputTextCFType() {
        super(ComponentAccessor.getComponent(CustomFieldValuePersister.class),
                ComponentAccessor.getComponent(GenericConfigManager.class),
                ComponentAccessor.getComponent(TextFieldCharacterLengthValidator.class),
                ComponentAccessor.getJiraAuthenticationContext());
	}

	
	
}
