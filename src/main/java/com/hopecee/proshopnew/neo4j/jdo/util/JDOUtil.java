/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 *
 * @author hope
 */
/**
* This is a simple class used to contain the static reference to the
* JDO PersistenceManagerFactory object shared throughout the code.
*/
public class JDOUtil {
  
  
  /**
* The one and only instance of the PersistenceManagerFactory.
*/
  public static final PersistenceManagerFactory persistenceManagerFactory =
    JDOHelper.getPersistenceManagerFactory("Neo4j_PU");
  
  
} // End class.
