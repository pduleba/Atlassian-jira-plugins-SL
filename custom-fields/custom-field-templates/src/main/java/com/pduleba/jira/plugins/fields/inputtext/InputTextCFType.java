package com.pduleba.jira.plugins.fields.inputtext;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.customfields.impl.GenericTextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.TextFieldCharacterLengthValidator;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

@Scanned
public class InputTextCFType extends GenericTextCFType {

	private static final Logger log = LoggerFactory.getLogger(InputTextCFType.class);

	public InputTextCFType(@ComponentImport JiraAuthenticationContext ctx) {
        super(ComponentAccessor.getComponent(CustomFieldValuePersister.class),
                ComponentAccessor.getComponent(GenericConfigManager.class),
                ComponentAccessor.getComponent(TextFieldCharacterLengthValidator.class),
                ctx);
	}

	@Override
	public Map<String, Object> getVelocityParameters(final Issue issue, final CustomField field,
			final FieldLayoutItem fieldLayoutItem) {
		final Map<String, Object> map = super.getVelocityParameters(issue, field, fieldLayoutItem);

		Object defaultValue = null;
		if (issue == null) {
			defaultValue = "";
			return map;
		} else {
			FieldConfig fieldConfig = field.getRelevantConfig(issue);
			// add what you need to the map here
			defaultValue = fieldConfig.getDescription();
			
			log.debug(MessageFormat.format("Field config :: {0}",
					(Objects.isNull(fieldConfig) ? "No config found" : fieldConfig.getDescription())));
		}

		map.put("myDefault", defaultValue);

		return map;
	}
}