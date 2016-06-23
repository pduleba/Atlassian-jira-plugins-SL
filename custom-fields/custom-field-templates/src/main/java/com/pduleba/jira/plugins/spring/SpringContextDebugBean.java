package com.pduleba.jira.plugins.spring;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.customfields.CustomFieldType;
import com.pduleba.jira.plugins.fields.schema.SchemaCFTypes;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringContextDebugBean implements InitializingBean, DisposableBean, ApplicationContextAware {
	
	@Setter
	private SchemaCFTypes schema;
	private ApplicationContext applicationContext;
	private CustomFieldManager cfm = ComponentAccessor.getComponent(CustomFieldManager.class);
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logDetails("START");
		log.debug("-----------------");
		List<CustomFieldType<?, ?>> fields = cfm.getCustomFieldTypes();
		for (CustomFieldType<?, ?> customFieldType : fields) {
			log.debug("custom field type : " + customFieldType.getName() + " " + customFieldType.getKey());
		}
		log.debug("-----------------");
		CustomFieldType<? , ?> customFieldType = cfm.getCustomFieldType(schema.getInputTextKey());
		log.debug("custom field " + customFieldType);
	}
	
	@Override
	public void destroy() throws Exception {
		logDetails("STOP");
	}

	private void logDetails(String event) {
		log.debug("EVENT " + event);
		log.debug("CTX = " + applicationContext);
		if (Objects.nonNull(applicationContext)) {
			for (String beanName : applicationContext.getBeanDefinitionNames()) {
				log.debug("Bean " + beanName);
			}
		}
		log.debug("cfm = " + cfm);
	}
}
