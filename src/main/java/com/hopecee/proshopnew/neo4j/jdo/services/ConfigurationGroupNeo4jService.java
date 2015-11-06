/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services;

import com.hopecee.proshopnew.neo4j.jdo.model.ConfigurationGroup;

/**
 *
 * @author hope
 */
public interface ConfigurationGroupNeo4jService extends DefaultDAO<ConfigurationGroup, Long> {

    public ConfigurationGroup findByName(String name);

    public void addConfiguration(long confGrpId, long confId) throws DAOException;

    public void removeConfiguration(long confGrpId, long confId) throws DAOException;
}
