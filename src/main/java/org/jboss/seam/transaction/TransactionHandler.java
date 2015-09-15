/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.seam.transaction;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import org.jboss.logging.Logger;
import org.jboss.seam.transaction.TransactionServletListener.TransactionEvent;
import org.jboss.solder.exception.control.ExceptionToCatch;

/**
 *
 * @author hope
 */
public class TransactionHandler implements Serializable {

    private static final long serialVersionUID = 8498396573173976009L;
    private static final Logger log = Logger.getLogger(TransactionHandler.class);
    // @SuppressWarnings("CdiInjectionPointsInspection")//add hope
    @Inject
    @DefaultTransaction
    //@QualifierType(QualifierType.ServiceType.DefaultSeamTransaction)
    private SeamTransaction tx;
    @Inject
    Event<ExceptionToCatch> txException;

    /*
     //ServletRequestEvent sre;
     public void listen(@Observes TransactionEventDestr event) {
     // event.init();
     }
     */
    public void getRequestDestroyed(@Observes TransactionEvent event)  {
         try {  
            System.out.println("tx 22pp===========================: = " + tx.getStatus());

            switch (tx.getStatus()) {
                case Status.STATUS_ACTIVE:
                    System.out.println("STATUS_ACTIVE===========================: = " + txException.toString());

                    log.debugf("Committing a transaction for request %s");
                    tx.commit();
                    break;
                case Status.STATUS_MARKED_ROLLBACK:
                case Status.STATUS_PREPARED:
                case Status.STATUS_PREPARING:
                    log.debugf("Rolling back a transaction for request %s");
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
        } catch (SystemException e) {
            log.warn("Error rolling back the transaction", e);
            txException.fire(new ExceptionToCatch(e));
        } catch (HeuristicRollbackException e) {
            log.warn("Error committing the transaction", e);
            txException.fire(new ExceptionToCatch(e));
        } catch (RollbackException e) {
            log.warn("Error committing the transaction", e);
            txException.fire(new ExceptionToCatch(e));
        } catch (HeuristicMixedException e) {
            log.warn("Error committing the transaction", e);
            txException.fire(new ExceptionToCatch(e));

        }

    }

    public void getRequestInitialized(@Observes TransactionEvent event) throws ServletException {
        try {
            if (tx.getStatus() == Status.STATUS_ACTIVE) {
                log.warn("Transaction was already started before the listener");
            } else {
                log.debugf("Beginning transaction for request ");
                tx.begin();
            }

        } catch (SystemException se) {
            log.warn("Error starting the transaction, or checking status", se);
            txException.fire(new ExceptionToCatch(se));
        } catch (NotSupportedException e) {
            log.warn("Error starting the transaction", e);
            txException.fire(new ExceptionToCatch(e));
        }
    }
    /*
     // @PostConstruct
     public void init() throws SystemException {
     System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkktx.isActive() == false returned ");
     }
     */
    //@PreDestroy

    public void destroy() {
    }
}
