/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services;

import com.hopecee.proshopnew.neo4j.jdo.model.UserFriendship;
import java.util.List;

/**
 *
 * @author hope
 */
public interface UserFriendshipNeo4jService extends DefaultDAO<UserFriendship, Long> {

    public void createFriendship(String userId, String friendId) throws DAOException;

    public List<UserFriendship> findAllTO();

    public List<UserFriendship> findAllFROM();
}
