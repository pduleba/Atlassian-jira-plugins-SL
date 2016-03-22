package com.consultingtoolsmiths.jira.samples.aui;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import java.util.*;

public class AUISampleAction extends JiraWebActionSupport {

    public String doSample1() throws Exception {
        log.debug("Entering doSample1");        
        return SUCCESS;
    } 
}
