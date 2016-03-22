package com.atlassian.jira.web.action.admin;

import java.util.Collection;

import com.atlassian.core.util.collection.EasyList;
import com.atlassian.jira.config.properties.APKeys;
import com.atlassian.jira.config.properties.ApplicationProperties;
import com.atlassian.jira.issue.fields.option.TextOption;
import com.atlassian.jira.web.action.JiraWebActionSupport;

/** 
 * This class is a copy of
 * com.atlassian.jira.web.action.admin.EditAnnouncementBanner but
 * could just as well extend that class if so desired.
 *
 * To enable logging for this class, add this to log4j.properties:
 *
 * log4j.logger.com.atlassian.jira.web.action.custom.EditAnnouncementBanner = DEBUG, console, filelog
 * log4j.additivity.com.atlassian.jira.web.action..custom.EditAnnouncementBanner = false
 */
public class EditAnnouncementBanner extends JiraWebActionSupport
{
    public static final String ANNOUNCEMENT_PREVIEW = "announcement_preview_banner_st";

    private String announcement;
    private String bannerVisibility;
    private ApplicationProperties applicationProperties;
    public static final String PUBLIC_BANNER = "public";
    public static final String PRIVATE_BANNER = "private";

    public EditAnnouncementBanner(ApplicationProperties applicationProperties)
    {
        this.applicationProperties = applicationProperties;
    }

    public String doDefault() throws Exception
    {
        log.debug("Entering doDefault");
        String preview = request.getParameter(ANNOUNCEMENT_PREVIEW);
        announcement = (preview == null) ? applicationProperties.getDefaultBackedText(APKeys.JIRA_ALERT_HEADER) : preview;
        this.bannerVisibility = applicationProperties.getDefaultBackedString(APKeys.JIRA_ALERT_HEADER_VISIBILITY);
        return INPUT;
    }

    protected String doExecute() throws Exception
    {
        log.debug("Entering doExecute");
        applicationProperties.setText(APKeys.JIRA_ALERT_HEADER, announcement);
        applicationProperties.setString(APKeys.JIRA_ALERT_HEADER_VISIBILITY, bannerVisibility);
        return INPUT;
    }

    public Collection getVisibilityModes()
    {
        return EasyList.build(new TextOption(PUBLIC_BANNER, getText("admin.menu.optionsandsettings.announcement.banner.visibility.public")),
                              new TextOption(PRIVATE_BANNER, getText("admin.menu.optionsandsettings.announcement.banner.visibility.private")));
    }

    public String getAnnouncement()
    {
        return announcement;
    }

    public void setAnnouncement(String announcement)
    {
        // Changed to add a prefix once to every announcement
        String modifiedAnnouncement = announcement;
        String prefix = "Webwork sample: ";
        if (!announcement.startsWith(prefix)) {
            modifiedAnnouncement = prefix + announcement;
        }
        log.debug("Setting the announcement to: " + modifiedAnnouncement);
        this.announcement = modifiedAnnouncement;
    }

    public boolean isPreview()
    {
        return request.getParameter(ANNOUNCEMENT_PREVIEW) != null;
    }

    public String getBannerVisibility()
    {
        return bannerVisibility;
    }

    public void setBannerVisibility(String bannerVisibility)
    {
        this.bannerVisibility = bannerVisibility;
    }
}