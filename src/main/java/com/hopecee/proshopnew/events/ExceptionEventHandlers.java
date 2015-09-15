/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.events;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;
import org.jboss.solder.servlet.event.Initialized;

/**
 *
 * @author hope
 */
//@Named
//@RequestScoped
public class ExceptionEventHandlers implements Serializable {
    // @SuppressWarnings("CdiInjectionPointsInspection")//add hope
    private static final long serialVersionUID = 3158449993966953439L;

    public void getInitBadToken(@Observes ExceptionEventBadToken event) throws IOException {
        event.getBadToken();
    }
}
