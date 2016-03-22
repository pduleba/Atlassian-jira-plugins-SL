package com.consultingtoolsmiths.jira.samples.webwork;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import java.util.*;
import webwork.action.ServletActionContext;

/**
 * Another sample Action class.
 */
public class ActionBeta extends JiraWebActionSupport {

    public ActionBeta() {
    }

    // There are no doValidation or doExecute methods in this class,
    // so nothing is done, and success or error is returned by the
    // ActionSupport methods of the same name.

    /**
     * Example of using a function that is not defined as a command
     * for an Action
     */
    public String doMyfunction() throws Exception {
        log.debug("Entering doMyfunction");        
        for (Enumeration e =  request.getParameterNames(); e.hasMoreElements() ;) {
            String n = (String)e.nextElement();
            String[] vals = request.getParameterValues(n);
            log.debug("Parameter " + n + "=" + vals[0]);
        }
        return SUCCESS;
    }

    /**
     * This has to be public or you get a "No command in action" exception
     */
    public String doMycommand() throws Exception {
        log.debug("Entering doMycommand");        
        for (Enumeration e =  request.getParameterNames(); e.hasMoreElements() ;) {
            String n = (String)e.nextElement();
            String[] vals = request.getParameterValues(n);
            log.debug("Parameter " + n + "=" + vals[0]);
        }

        // The action name is ActionBeta
        log.debug("Current action name is " + getActionName());

        // The command name is mycommand
        // JRADEV-88 getCommandName() will be available in a future release 
        log.debug("Current command name is " + command);

        // The path contains the alias used for this command
        log.debug("Current path with the alias is " + request.getServletPath());

        return SUCCESS;
    }

    /**
     * Show a command returning something other that input, error or success. 
     *
     * If the casing doesn't match the command name with the first
     * letter uppercase, you get a "No command in action" exception
     */
    public String doNonstandardview() throws Exception {
        log.debug("Entering doNonstandardview");        

        // This is the name of the view to use for the response HTML
        return "foobar";
    }

    /**
     * Show a command returning to a different URL than expected. 
     *
     * Note that the name of the method is the string used in the URL
     * with the first letter uppercase.
     */
    public String doSampleRedirect() throws Exception {
        log.debug("Entering doSampleRedirect");        

        // If you get this string wrong, you will go to a default page
        // and may not see the left-hand menu.
        String nextUrl = "/secure/FirstNewAction!default.jspa?myfirstparameter=A redirect was used to reach this screen";
        String contextPath = ServletActionContext.getRequest().getContextPath();
        if (contextPath != null) {
            nextUrl = contextPath + nextUrl;
        }
        log.debug("Redirecting to: " + nextUrl);
        ServletActionContext.getResponse().sendRedirect(nextUrl);
        return NONE;

        // The returned string is the name of the view to use for the
        // response HTML but can also be the name of an alias in the
        // current context. This gets resolved to the class name.
        // See getRedirect() and forceRedirect() in
        // JiraWebActionSupport for more details.
 
        // Tip: if you are returning to an Admin page and it is missing 
        // menu left-hand column, then make sure that your vm template file
        // has  <meta content="admin" name="decorator" /> in the head element
   }

    /**
     * Show a command returning to a different URL than expected
     * using the getRedirect() method in JiraWebActionSupport.
     */
    public String doSampleAnotherRedirect() throws Exception {
        log.debug("Entering doSampleAnotherRedirect");        

        // The URL must start with a slash to get the context prepended
        setReturnUrl("/FirstNewAction.jspa");
        return getRedirect("not used");
    }

}