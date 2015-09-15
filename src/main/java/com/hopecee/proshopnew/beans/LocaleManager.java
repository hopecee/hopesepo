/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.beans;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.core.Context;
/**
 *
 * @author hope
 */
//@SessionScoped
@Named
public class LocaleManager implements Serializable {
//LocaleManager

    private static final long serialVersionUID = 7528635444472853709L;
//Locale locale = LocaleContextHolder.getLocale();
   // @Inject
    //@Context
    //private HttpServletRequest httpRequest;
    //@Inject
   // @Context
   // private HttpServletResponse httpResponse;
    private String language = "en";
    private Locale locale;

    //@PostConstruct
    //public void init() {//TODO Not needed.
    //  locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    // System.out.println("locale ========= : " + locale.getLanguage());
    //locale = new Locale(language);
    ///FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    //}
    public void reDirect() {
         FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest req = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse resp = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        //String t = request.getRemoteAddr();
        //System.out.println("getRemoteAddr ==== : " + t);
        try {
            if (req.getAttribute("language").toString() != null) {
                language = req.getAttribute("language").toString();
                System.out.println("language ==== : " + language);
            }
        } catch (Exception e) {
        }
        System.out.println("accept_language ==== : " );
//Locale lcl=H4Object.locale_get(accept_language);//routine to get Locale from ACCEPT-LANGUAGE header
//String language=lcl.getLanguage();


        locale = new Locale(language);
        //FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        resp.setLocale(locale);
        //resp.addHeader("language",language);
        resp.encodeRedirectURL("indexredirect");

        //page redirect.
        //return "indexredirect?faces-redirect=true";

    }

    public String getFromAcceptLanguage() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        String accept_language = req.getHeader("ACCEPT-LANGUAGE");
        //System.out.println("accept_language ==== : " + accept_language);
        String[] strArr = accept_language.split(",");
        return strArr[0];
    }

    public Locale getLocale() {

        return locale;
    }

    public String getLanguage() {
        System.out.println("language : " + language);
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);

    }
}
