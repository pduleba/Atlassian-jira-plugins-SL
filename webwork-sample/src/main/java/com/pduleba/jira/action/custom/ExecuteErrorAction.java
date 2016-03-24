package com.pduleba.jira.action.custom;

public class ExecuteErrorAction extends AbstractExecutableAction {

	private static final long serialVersionUID = 664150488859185546L;
	private String templateMessage;

	public ExecuteErrorAction() {
		super("error", true);
		this.templateMessage = "Execution problem";
	}

	@Override
	protected String getTemplateMessage() {
		return templateMessage;
	}

}