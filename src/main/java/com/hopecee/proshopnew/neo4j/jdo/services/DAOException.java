/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services;

/**
 *
 * @author hope
 */
/**
 * Created with IntelliJ IDEA. User: cyril Date: 12/6/12 Time: 1:52 PM To change
 * this template use File | Settings | File Templates.
 */
public class DAOException extends Exception {

    private static final long serialVersionUID = -2720524925697703873L;

    public DAOException() {
        this(null);
    }

    public DAOException(String message) {
        this(message, null);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
