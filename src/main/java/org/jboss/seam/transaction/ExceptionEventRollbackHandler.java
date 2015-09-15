/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.seam.transaction;

import java.io.Serializable;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.SystemException;

/**
 *
 * @author hope
 */
public class ExceptionEventRollbackHandler implements Serializable {

    private static final long serialVersionUID = 7526969996472052266L;
    // @SuppressWarnings("CdiInjectionPointsInspection")//add hope
    @Inject
    @DefaultTransaction
    //@QualifierType(QualifierType.ServiceType.DefaultSeamTransaction)
    private SeamTransaction tx;

    public void getInitialized(@Observes ExceptionEventRollback event) throws IllegalStateException, SystemException {
        tx.setRollbackOnly();
    }
}
