package com.atlassian.plugins.tutorial.refapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyPluginServlet extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(MyPluginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException

    {
		resp.setContentType("text/html");
		resp.getWriter().write("<html><body>Hello! You did it.</body></html>");

    }

}