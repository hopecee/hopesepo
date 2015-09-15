/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.servlets;

import com.ocpsoft.pretty.PrettyContext;
import com.hopecee.proshopnew.events.ExceptionEventBadToken;
import com.hopecee.proshopnew.beans.LocaleManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import javax.faces.context.ExternalContext;

/**
 *
 * @author hope
 */
@WebServlet(name = "tuLocaleManagerServlet", urlPatterns = {"/tuLocaleManagerServlet"})
public class TuLocaleManagerServlet extends HttpServlet {

    private static final long serialVersionUID = 8294400128059530120L;
    public static final String JSON = "application/json";
    public static final String LANGUAGE = "en";
    private final String BAD_TOKEN = "BadToken";
    @Inject
    private Event<ExceptionEventBadToken> exceptionEventBadToken;
    private Locale locale;
    private String control = "control";

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = "";
        try {
            token = req.getAttribute("CheckRequestVerificationToken").toString();
        } catch (NullPointerException e) {
            Logger.getLogger(TuLocaleManagerServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        if (token.equals(BAD_TOKEN)) {
            //getBadToken(req, resp);
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
        } else {
            //  String RequestVerificationToken = req.getParameter("RequestVerificationToken");
            System.out.println("DDDDDDDDD = ,,,,,,,,,,,,language,,,,,,,,,,,,");
            String method = req.getParameter("method");

            Enumeration e = req.getParameterNames();
            while (e.hasMoreElements()) {
                String attr = (String) e.nextElement();
                System.out.println(" attr = " + attr);
                Object value = req.getParameter(attr);
                System.out.println(" value = " + value);
            }

            /* try {*/
            if ("localeManager".equals(method)) {
               reDirect(req, resp);

            }

        }
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        getLocale(req, resp);
    }

    public void reDirect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String language = LANGUAGE;

        try {
            if (req.getParameter("language") != null) {
                language = req.getParameter("language");
                control = language;
            }
        } catch (Exception ex) {
            Logger.getLogger(TuLocaleManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        locale = new Locale(language);
        resp.setLocale(locale);

        // resp.sendRedirect("index");//TODO Why this is not working?
        resp.sendRedirect("/");
        //return "indexredirect?faces-redirect=true";

    }

    public void getLocale(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String language = LANGUAGE;

        try {
            if (req.getParameter("language") != null) {
                language = req.getParameter("language");
            }
        } catch (Exception ex) {
            Logger.getLogger(TuLocaleManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        locale = new Locale(language);
        resp.setLocale(locale);
        resp.addHeader("control", control);
        control = "control";// Reset control. 
    }
}
