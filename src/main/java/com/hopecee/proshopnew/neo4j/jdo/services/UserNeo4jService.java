/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services;

import com.hopecee.proshopnew.neo4j.jdo.model.User;

/**
 *
 * @author hope
 */
public interface UserNeo4jService extends DefaultDAO<User, Long> {

    public User findByName(String name);

    public User findByUsersName(String usersName);
}
