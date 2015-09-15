/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services;

import com.hopecee.proshopnew.neo4j.jdo.model.Configuration;

/**
 *
 * @author hope
 */
public interface ConfigurationNeo4jService extends DefaultDAO<Configuration, Long> {
   
    public Configuration findByName( String name ); 
}

