package com.consultingtoolsmiths.jira.samples.webwork;

import java.util.Enumeration;

import com.atlassian.jira.web.action.JiraWebActionSupport;

/**
 * Another sample Action class.
 */
public class MessageAction extends JiraWebActionSupport {

    public MessageAction() {
    }

    /**
     * Set up default values, if any. If you don't have default
     * values, this is not needed.
     *
     * If you want to have default values in your form's fields when it
     * is loaded, then first call this method (or one with some other
     * such name as doInit) and set the local variables. Then return
     * "input" to use the input form view, and in the form use
     * ${myfirstparameter} to call getMyfirstparameter() to load the
     * local variables.
     */
    public String doMessage() throws Exception {
        log.debug("Entering doMessage");        

        // If any of these parameter names match public set methods for local
        // variables, then the local variable will have been set before this
        // method is entered.
        for (Enumeration e =  request.getParameterNames(); e.hasMoreElements() ;) {
            String n = (String)e.nextElement();
            String[] vals = request.getParameterValues(n);
            log.debug("Parameter " + n + "=" + vals[0]);
        }

        // You could also set a local variable to have a different value
        // every time the Action is invoked here, e.g. a timestamp.

        // This should be "input". If no input view exists, that's an error.
//        addErrorMessage("Error message 1", Reason.SERVER_ERROR);
		String result =  returnMsgToUser(getReturnUrl(), "Error message 2", MessageType.SUCCESS, true, null);

		log.debug("Exiting doMessage with a result of: " + result);
        return result;
    }


}