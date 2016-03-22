package com.pduleba.jira.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.web.action.JiraWebActionSupport;

public class MessageAction extends JiraWebActionSupport {

	private static final long serialVersionUID = 664150488859185546L;

	private static final Logger log = LoggerFactory.getLogger(MessageAction.class);

	public String doMessage() throws Exception {
		log.debug("Entering doMessage");

		HttpServletRequest request = getHttpRequest();
		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
			String n = (String) e.nextElement();
			log.debug("Parameter " + n + "=" + request.getParameterValues(n));
		}

		addErrorMessage("Error message 1", Reason.SERVER_ERROR);
		String result = returnMsgToUser(getReturnUrl(), "Error message 2", MessageType.SUCCESS, true, null);

		log.debug("Exiting doMessage with a result of: " + result);
		return result;
	}

}