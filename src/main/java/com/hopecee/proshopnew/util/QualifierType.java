/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
//import javax.inject.Qualifier;

/**
 *
 * @author hope
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE, ElementType.METHOD})
public @interface QualifierType {
    ServiceType value();
	 
    public enum ServiceType {

        TransactionManagerSynchronizations,
        SimpleTransactionExceptionHandler,
        TransactionServletListener,
        TransactionPhaseListener,
        EntityTransaction,
        DefaultSeamTransaction,
        BeanManager,
        RedirectBuilderImp, ImplicitHttpServlet, ImplicitHttpServletHttpSession;//TODO ANOTHER is not declared yet.
    }
}
