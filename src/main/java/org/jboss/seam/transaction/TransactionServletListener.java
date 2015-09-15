/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.seam.transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;
//import static org.jboss.seam.transaction.TransactionServletListener_1.DISABLE_LISTENER_PARAM;
import org.jboss.solder.exception.control.ExceptionToCatch;

/**
 *
 * @author hope
 */
// THIS IS A WORKARROUNG WHEN Normal TransactionServletListener can not be injected with CDI Bean
// as in TransactionServletListener_1.java. revert to normal, when Jetty Injects CDI Bean into ServletRequestListener.
@WebListener
public class TransactionServletListener implements ServletRequestListener {

    private final Logger log = Logger.getLogger(TransactionServletListener.class);
    public static String DISABLE_LISTENER_PARAM = "org.jboss.seam.transaction.disableListener";
    private static final String EXCLUDES_KEY = "org.jboss.seam.transaction.excludes";//add hope
    private static final String INCLUDES_KEY = "org.jboss.seam.transaction.includes";//add hope
   
    // CDi Injection not surported here.
    //@Inject
    //@DefaultTransaction
    //private SeamTransaction tx;
    //@Inject
    // Event<ExceptionToCatch> txException;
    // @Inject
    //@Credit
    //  Event<TransactionEventDestr> txEventDestr;
    // @Inject
    //@Credit
    // Event<TransactionEventInit> txEventInit;

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        final String listenerDisabledParam = sre.getServletContext().getInitParameter(DISABLE_LISTENER_PARAM);
        if (listenerDisabledParam != null && "true".equals(listenerDisabledParam.trim().toLowerCase())) {
            System.out.println(" listenerDisabledParam : = true");
            return;
        }
        final HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        final boolean include = matches(request, getIncludes(sre));
        final boolean exclude = matches(request, getExcludes(sre));
        this.log.debugv("Request destroyed: {3} | include:{0}, exclude:{1}, url:{2}", include, exclude, request.getServletPath(), request.getRequestURI());

        if (include && !exclude) {
            //  txEventDestr.fire(new TransactionEventDestr());
            if (beanManager() != null) {
                beanManager().fireEvent(new TransactionEvent());
                log.info("beanManager fired TransactionEvent.Destr.");
            } else {
                log.error("beanManager is null.  Cannot fire startup event.");
            }
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        final String listenerDisabledParam = sre.getServletContext().getInitParameter(DISABLE_LISTENER_PARAM);
        if (listenerDisabledParam != null && "true".equals(listenerDisabledParam.trim().toLowerCase())) {
            System.out.println(" listenerDisabledParam : = true");
            return;
        }
        final HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        final boolean include = matches(request, getIncludes(sre));
        final boolean exclude = matches(request, getExcludes(sre));
        this.log.debugv("Request initialized: {3} | include:{0}, exclude:{1}, url:{2}", include, exclude, request.getServletPath(), request.getRequestURI());

        if (include && !exclude) {
            //txEventInit.fire(new TransactionEventInit());
            if (beanManager() != null) {
                beanManager().fireEvent(new TransactionEvent());
                log.info("beanManager fired TransactionEvent.Init.");
            } else {
                log.error("beanManager is null.  Cannot fire startup event.");
            }
        }
    }

    private BeanManager beanManager() {
        try {
            // See reference below about how I came up with this
            InitialContext iniCtx = new InitialContext();
            BeanManager result = (BeanManager) iniCtx.lookup("java:comp/env/BeanManager");
            return result;
        } catch (NamingException e) {
            log.error("Could not construct BeanManager.", e);
            return null;
        }
    }

    private List<String> getExcludes(ServletRequestEvent sre) {
        return getPatterns(sre, EXCLUDES_KEY);
    }

    private List<String> getIncludes(ServletRequestEvent sre) {
        return getPatterns(sre, INCLUDES_KEY);
    }

    private List<String> getPatterns(ServletRequestEvent sre, String key) {
        final Object attribute = sre.getServletContext().getAttribute(key);
        final List<String> patterns;
        if (null == attribute || !(attribute instanceof List)) {
            final String initParameter = sre.getServletContext().getInitParameter(key);
            patterns = new ArrayList<String>();
            if (!StringUtils.isEmpty(initParameter)) {
                final StringTokenizer tokenizer = new StringTokenizer(initParameter, ",");
                while (tokenizer.hasMoreElements()) {
                    patterns.add(tokenizer.nextToken());
                }
            }
            sre.getServletContext().setAttribute(key, patterns);
        } else {
            //noinspection unchecked
            patterns = (List<String>) attribute;
        }
        return patterns;
    }

    private boolean matches(HttpServletRequest servletRequest, List<String> patterns) {
        final String servletPath = servletRequest.getServletPath();
       
        for (String pattern : patterns) {
            /*
             // Create a Pattern object /tu\\w*(Servlet)$
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(servletPath);
            if (m.matches()) {
                System.out.println("matches in : " + m.matches());
                return true;
            }
            */
             if (servletPath.matches(pattern)) {
              return true;
             }
           
        }
        return patterns.isEmpty();
    }

    public static class TransactionEvent implements Serializable {

        private static final long serialVersionUID = 3723552335163650585L;

        public void requestDestroyed() {
        }

        public void requestInitialized() {
        }
    }
//=========================================================
    /**
     * context-param to disable the listener.
     */
    /*
     public static String DISABLE_LISTENER_PARAM = "org.jboss.seam.transaction.disableListener";
     private static final String EXCLUDES_KEY = "org.jboss.seam.transaction.excludes";//add hope
     private static final String INCLUDES_KEY = "org.jboss.seam.transaction.includes";//add hope
     private final Logger log = Logger.getLogger(TransactionServletListener_1.class);
     @SuppressWarnings("CdiInjectionPointsInspection")//add hope
     @Inject
     @DefaultTransaction
     //@QualifierType(QualifierType.ServiceType.DefaultSeamTransaction)
     private SeamTransaction tx;
     @Inject
     Event<ExceptionToCatch> txException;

     @Override
     public void requestDestroyed(ServletRequestEvent sre) {
     final String listenerDisabledParam = sre.getServletContext().getInitParameter(DISABLE_LISTENER_PARAM);
     if (listenerDisabledParam != null && "true".equals(listenerDisabledParam.trim().toLowerCase())) {
     System.out.println(" listenerDisabledParam : = true");

     return;
     }
     final HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
     final boolean include = matches(request, getIncludes(sre));
     final boolean exclude = matches(request, getExcludes(sre));
     this.log.debugv("Request destroyed: {3} | include:{0}, exclude:{1}, url:{2}", include, exclude, request.getServletPath(), request.getRequestURI());

     if (include && !exclude) {
     try {
     if (tx.isActive() == false) {
     return;
     }
     if (this.tx.getStatus() == Status.STATUS_ACTIVE) {

     this.log.warn("Transaction was already started before the listener");

     } else {
     this.log.debugf("Beginning transaction for request %s", sre.getServletRequest());

     // should a tx be started only if the current status is not STATUS_ACTIVE? Do we need more checks or clean up here?

     switch (this.tx.getStatus()) //switch (Status.STATUS_ACTIVE)
     {
     case Status.STATUS_ACTIVE:
     this.log.debugf("Committing a transaction for request %s", sre.getServletRequest());
     tx.commit();
     break;
     case Status.STATUS_MARKED_ROLLBACK:
     case Status.STATUS_PREPARED:
     case Status.STATUS_PREPARING:
     this.log.debugf("Rolling back a transaction for request %s", sre.getServletRequest());
     tx.rollback();
     break;
     case Status.STATUS_COMMITTED:
     case Status.STATUS_COMMITTING:
     case Status.STATUS_ROLLING_BACK:
     case Status.STATUS_UNKNOWN:
     case Status.STATUS_ROLLEDBACK:
     case Status.STATUS_NO_TRANSACTION:
     break;
     }
     }
     } catch (SystemException e) {
     this.log.warn("Error rolling back the transaction", e);
     this.txException.fire(new ExceptionToCatch(e));
     } catch (HeuristicRollbackException e) {
     this.log.warn("Error committing the transaction", e);
     this.txException.fire(new ExceptionToCatch(e));
     } catch (RollbackException e) {
     this.log.warn("Error committing the transaction", e);
     this.txException.fire(new ExceptionToCatch(e));
     } catch (HeuristicMixedException e) {
     this.log.warn("Error committing the transaction", e);
     this.txException.fire(new ExceptionToCatch(e));
     }

     }

     }

     @Override
     public void requestInitialized(ServletRequestEvent sre) {
     final String listenerDisabledParam = sre.getServletContext().getInitParameter(DISABLE_LISTENER_PARAM);
     if (listenerDisabledParam != null && "true".equals(listenerDisabledParam.trim().toLowerCase())) {
     return;
     }
     final HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
     final boolean include = matches(request, getIncludes(sre));
     final boolean exclude = matches(request, getExcludes(sre));
     this.log.debugv("Request initialized: {3} | include:{0}, exclude:{1}, url:{2}", include, exclude, request.getServletPath(), request.getRequestURI());
     if (include && !exclude) {
     System.out.println("this.tx.toString() : = requestInitialized");


     try {
     int status = this.tx.getStatus();
     if (status == Status.STATUS_MARKED_ROLLBACK || status == Status.STATUS_ROLLEDBACK) {
     this.log.warn("Transaction was already started before the listener and is marked for rollback or rolled back from other thread,"
     + " so doing rollback to disassociate it with current thread");
     tx.rollback();
     } else if (status != Status.STATUS_NO_TRANSACTION) {
     this.log.warnv("Transaction was already started before the listener. Transaction status: {0}", status);
     }

     status = this.tx.getStatus();
     if (status == Status.STATUS_ACTIVE) {
     this.log.warn("Transaction was already started before the listener");
     } else {
     this.log.debugf("Beginning transaction for request %s", sre.getServletRequest());
     this.tx.begin();
     }
     } catch (SystemException se) {
     this.log.warn("Error starting the transaction, or checking status", se);
     this.txException.fire(new ExceptionToCatch(se));
     } catch (NotSupportedException e) {
     this.log.warn("Error starting the transaction", e);
     this.txException.fire(new ExceptionToCatch(e));
     }

     }
     }

     private List<String> getExcludes(ServletRequestEvent sre) {
     return getPatterns(sre, EXCLUDES_KEY);
     }

     private List<String> getIncludes(ServletRequestEvent sre) {
     return getPatterns(sre, INCLUDES_KEY);
     }

     private List<String> getPatterns(ServletRequestEvent sre, String key) {
     final Object attribute = sre.getServletContext().getAttribute(key);
     final List<String> patterns;
     if (null == attribute || !(attribute instanceof List)) {
     final String initParameter = sre.getServletContext().getInitParameter(key);
     patterns = new ArrayList<String>();
     if (!StringUtils.isEmpty(initParameter)) {
     final StringTokenizer tokenizer = new StringTokenizer(initParameter, ",");
     while (tokenizer.hasMoreElements()) {
     patterns.add(tokenizer.nextToken());
     }
     }
     sre.getServletContext().setAttribute(key, patterns);
     } else {
     //noinspection unchecked
     patterns = (List<String>) attribute;
     }
     return patterns;
     }

     private boolean matches(HttpServletRequest servletRequest, List<String> patterns) {
     final String servletPath = servletRequest.getServletPath();
     for (String pattern : patterns) {
     if (servletPath.matches(pattern)) {
     return true;
     }
     }
     return patterns.isEmpty();
     }
    
     */
}
