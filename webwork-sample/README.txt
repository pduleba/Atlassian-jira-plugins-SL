
                       WebWork Sample Plugin for JIRA


Matt Doar, mdoar@pobox.com
Consulting Toolsmiths


Summary and Usage
=================

This plugin contains working samples to demonstrate how JIRA web pages
are generated. It assumes some basic familiarity with HTML and Java.

The plugin assumes that you have the source code to the plugin available.

To install
----------

0. Use a development instance of JIRA. Nothing will be changed
   permanently by this plugin but production servers aren't the place to
   learn new things!

   JIRA_INSTALL is the top-level directory of your JIRA installation

   JIRA_HOME is the JIRA home directory as defined in
   $JIRA_INSTALL/atlassian-jira/WEB-INF/classes/jira-application.properties

1. Check out the source code for the plugin from Subversion with

   git clone ssh://git@bitbucket.org/mdoar/webwork-sample.git

2. Build the plugin using the Atlassian SDK

   cd webwork-sample
   atlas-package

3. Deploy the plugin jar file 

   Admin, Manage Add-addons, Upload plugin

   or

   cp target/webwork-sample-5.2.jar $JIRA_HOME/plugins/installed-plugins
   and restart JIRA

4. Create a new directory in your JIRA installation

   mkdir -p $JIRA_INSTALL/atlassian-jira/secure/custom/com/consultingtoolsmiths/jira/samples/webwork

   and copy the updatededitannouncement.jsp file to the new
   directory. This is an additional jsp file that will not affect any
   other jsp files in JIRA.

   cp src/main/resources/secure/custom/com/consultingtoolsmiths/jira/samples/webwork/updatededitannouncement.jsp $JIRA_INSTALL/atlassian-jira/secure/custom/com/consultingtoolsmiths/jira/samples/webwork

4. Add the following lines to the end of $JIRA_INSTALL/atlassian-jira/WEB-INF/classes/log4j.properties

log4j.category.com.consultingtoolsmiths.jira.samples.webwork.ActionAlpha = Debug, console, filelog
log4j.additivity.com.consultingtoolsmiths.jira.samples.webwork.ActionAlpha = false
log4j.category.com.consultingtoolsmiths.jira.samples.webwork.ActionBeta = DEBUG, console, filelog
log4j.additivity.com.consultingtoolsmiths.jira.samples.webwork.ActionBeta = false
log4j.logger.com.atlassian.jira.web.action.custom.EditAnnouncementBanner = DEBUG, console, filelog
log4j.additivity.com.atlassian.jira.web.action.custom.EditAnnouncementBanner = false

Without these log settings you won't see anything in the
atlassian-jira.log file when you run the samples. The properties can also be added in Admin, Logging

5. Restart JIRA

You can also use the usual Atlassian PDK commands such as "atlas-run",
but the jsp file deploymentand logging configuration will still need
to be done manually.

To uninstall
------------

1. Uninstall the plugin at Admin, Manage Add-ons

2. Remove the jsp file and the directory (optional) 

   rm -rf $JIRA_INSTALL/atlassian-jira/secure/custom/com/consultingtoolsmiths/jira/samples/webwork
   Restart JIRA

Usage
-----

1. Log into the development JIRA as a user with jira admin privileges
and go to the Admin page

2. Under the Plugins you should see a section named "Other" with links
to each of the sample actions.

3. Read the sample source code, starting with
src/main/resources/atlassian-plugin.xml and look at the output in
atlassian-jira.log when each of the links in Samples is clicked.
More detailed descriptions of what happens for each example action can
be found below.

Rebuilding the plugin
---------------------

cd webwork-sample
rm -rf target
atlas-package

and deploy the file target/webwork-sample-5.2.jar as usual. If you
change a jsp file, don't forget to deploy that too. The -o option for
atlas-package is for Maven's offline mode and is slightly faster once
you have all the necessary jar files downloaded.


Introduction

JIRA is deployed as a web application in a Web Application Server such as Tomcat. When an HTTP request is made by a client-side browser for a URL, the request is passed to the JIRA web app which returns the HTML. The HTML could be static, say read from a file on the server-side, but is more likely to be dynamically generated each time that it is requested. Generating the contents of the HTTP response could be done by writing HTML in Java code, but that's hard to maintain. So template files are used. These are files that contain HTML snippets and other text to be dynamically replaced when the template is used. Velocity and JSP are both template languages.

For each URL requested, JIRA works out which Java class is handling that URL and which template file to use for the response. The template file is populated with the dynamic part of the HTML content. This is the job of the web framework. In the case of JIRA, the web framework is WebWork1.

For a high-level overview of Web Frameworks the Wikipedia pages [http://en.wikipedia.org/wiki/Comparison_of_web_application_frameworks] and [http://en.wikipedia.org/wiki/Java:_View_Technologies_and_Frameworks] are one place to start.

JSP files are converted in Java files, which are compiled to Java class files. Velocity templates are passed to a velocity engine code for evaluation. In production mode, both jsp and vm templates are cached so don't expect to be able to make changes to them and have the changes affect JIRA.


 - What Does That Link Do?

To work out what each link does, the general procedure is:

1. Look for the text of the link in a properties file. For this plugin, see src/main/resources/com/consultingtoolsmiths/jira/samples/mypluginstrings.properties
For example, "First Action" is defined as having an i18n key com.consultingtoolsmiths.jira.samples.webwork.label1 by the line
com.consultingtoolsmiths.jira.samples.webwork.label1=First Action

2. Search in atlassian-plugin.xml for the label with the same key, i.e. a key ending in "label1"


<label key="com.consultingtoolsmiths.jira.samples.webwork.label1"/>
      <!-- The name of the jspa here is the alias of the action below.
           The linkId is optional -->
      <link linkId="com.consultingtoolsmiths.jira.samples.ww1">/secure/FirstNewAction.jspa</link>


This shows that the link named "First Action" will use the FirstNewAction.jspa URL. 

3. Elsewhere in atlassian-plugin.xml the alias "FirstNewAction" is defined as using the ActionAlpha Java class, e.g.

 <action name="com.consultingtoolsmiths.jira.samples.webwork.ActionAlpha" alias="FirstNewAction">


 - The Sample Actions


 -- First Action

Calls the ActionAlpha doExecute method with no parameters passed. The URL is /secure/FirstNewAction.jspa

 -- First Action default

Calls the doDefault method in the ActionAlpha class with no parameters passed. The URL is /secure/FirstNewAction!default.jspa

 -- First Action default with parameters

Calls the doDefault method in the ActionAlpha class with two parameters named "myfirstparameter" and "param2". The URL is /secure/FirstNewAction?myfirstparameter=some%20text&amp;param2=foopy

 -- First Action default with an error

Calls the doDefault method in the ActionAlpha class with a parameter that triggers an error. The URL is /secure/FirstNewAction!default.jspa?myfirstparameter=bob

 -- First Action with an error

Calls the ActionAlpha doExecute method with a parameter that triggers an error. The URL is /secure/FirstNewAction.jspa?myfirstparameter=bob

 -- Second Action

Calls the doExecute method in the ActionSupport parent class with no parameters passed. The URL is /secure/SecondNewAction.jspa

 -- Second Action with a non-command function

Calls the doMyfunction method in the ActionBeta class. The URL is {/secure/SecondNewAction!myfunction.jspa

 -- Second Action with a command

Calls the doMyfunction() method as a command in the ActionBeta class. The URL is /secure/SecondNewAction!myfunction.jspa

 -- Second Action with a different command and i18n

This command reuses the same ActionBeta method as CommandOne via a different URL. The URL is /secure/CommandTwo.jspa. The use of i18n is in the Velocity .vm template file.

 -- Second Action with command and view name

This command reuses the same Action method as CommandOne but with a view name other than "input", "error" or "success". The URL is /secure/CommandThree.jspa

 -- Second Action with a redirect

This sample shows how to redirect to a different URL than expected.
The URL is /secure/SecondNewAction!sampleRedirect.jspa and the redirect URL is "/secure/FirstNewAction!default.jspa?myfirstparameter=A redirect was used to reach this screen"

 -- Admin, Options and Setting, Announcement Banner

This plugin also changes the behavior of the EditAnnouncementBanner.jspa alias to use a custom EditAnnouncementBanner class. The change is that every announcement is prefixed with the string "Webwork sample: ".

There is also a commented-out action in atlassian-plugin.xml that shows how to change just the appearance of a page via a copy of its template file, but still use the same Java class. This approach avoids modifying installed jsp files.

 - Typical Usage in JIRA

dosomethingconfirm.jsp is a screen with Confirm, Cancel and a space to display any errors. "DoSomething" is a webwork action alias in actions.xml that refers to a class named DoSomething.java. The web page is invoked with a URL that looks something like http://localhost:8080/secure/DoSomething!default.jspa?param1=val1. This calls the doDefault() method in the DoSomething class with param1=val1 in the HTTPServletRequest object named "request".  More information about that class can be found [here|http://java.sun.com/products/servlet/2.3/javadoc/javax/servlet/http/HttpServletRequest.html]

The doDefault method returns the string "input", which was configured to refer to a view with a name such as somescreen.jsp or somescreen.vm. So the somescreen template is used to generate the HTML in the response. If the "default" command is not specified, then the doValidation and doExecute methods are called. In DoSomething.java, the method doValidation() can be used to check that something is set as required. If not, then addErrorMessage() is used to add an error message and the "error" view is used. This error view can be the same as the input view, but now the new error messages will be shown. If no errors were found, then doExecute() is called and success or error is returned. The method getResult() does this by checking if any errors are set. The return string "success" often refers to a common homepage screen or such like.

 - Tips

 -- Hidden Variables

If you want to enter some values on one page and then go to another page that uses a different Action object, you can pass the values using hidden parameters.  The HTML for this looks like:

    <input type="hidden" name="myvariable" value="some value needed later on">

In JSP files you'll see component elements doing the same thing. For example:

    <ui:component name="'myvariable'" template="hidden.jsp" theme="'single'" />

which calls getMyvariable() to populate the hidden.jsp template with the id, name and a value or default value.

 -- Validating Fields

Even though parameters and their values from a form submission are present in the request variable, don't use them for validation. Use the local variable of a related name that was set when the form was submitted and have the form set the value of fields using the get method for that variable.

If you don't do this, you'll forget to repopulate valid values that users have already entered and they'll have to reenter them when they make a mistake in just one field.

 - WebWork

The source code for WebWork1 is available at:

[https://svn.atlassian.com/svn/public/atlassian/vendor/webwork-1.4/trunk]

There are also some (mostly empty) samples in

[https://svn.atlassian.com/svn/public/atlassian/vendor/webwork-1.4/trunk/src/resources]

and some slightly more useful examples in

[https://svn.atlassian.com/svn/public/atlassian/vendor/webwork-1.4/trunk/src/main/webwork/examples]

