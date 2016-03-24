package com.pduleba.jira.action.custom;

public class ExecuteSuccessAction extends AbstractExecutableAction {

	private static final long serialVersionUID = 664150488859185547L;
	private String templateMessage;
	
	public ExecuteSuccessAction() {
		super("success", false);
		this.templateMessage = "Execution SUCCESSFULL !!!";
	}
	
	@Override
	protected String getTemplateMessage() {
		return templateMessage;
	}

}