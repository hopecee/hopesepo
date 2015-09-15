package com.hopecee.proshopnew.events;

import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import javax.inject.Inject;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.AuthorizationException;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;
import javax.servlet.http.HttpServletResponse;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.solder.core.Veto;
import org.jboss.solder.servlet.WebRequest;
import javax.servlet.ServletResponse;

/**
 * Handles user authorization exceptions
 *
 * @author Shane Bryzak
 */
@HandlesExceptions
//@Veto
public class ExceptionHandler_NOOOOT {

    //@Inject
    //private Messages messages;
    /*    @Inject
     * private BeanManager beanManager;
     * //@ExtensionManaged
     * //@Produces
     * //@ApplicationScoped
     * 
     * public BeanManager getBeanManager() throws NamingException {
     * if (beanManager == null) {
     * //If the BeanManager is not injected, need to use JNDI lookup
     * InitialContext initContext = new InitialContext();
     * beanManager = (BeanManager) initContext.lookup("java:/comp/env/BeanManager");
     * java.lang.System.out.println("BeanManagerProducer ::::: " + beanManager.toString());
     * return beanManager;
     * } else {
     * java.lang.System.out.println("BeanManagerProducer NULL::::: " + beanManager.toString());
     * return beanManager;
     * }
     * }*/

    //public void handleAuthorizationException(@Handles CaughtException<AuthorizationException> evt) {
    public void handleAuthorizationException(@Handles @Testype CaughtException<DAOException> evt)  {
       // messages.error("You do not have the necessary permissions to perform that operation", "");
       // evt.handled();
        System.out.println(": tx.setRollbackOnly(); ===== 12;  : "// + response.getClass().toString()
                );
       
        
    }
}
