/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services;

import com.hopecee.proshopnew.neo4j.jdo.model.AddressBook;

/**
 *
 * @author hope
 */
public interface AddressBookNeo4jService extends DefaultDAO<AddressBook, Long> {

    public AddressBook findByName(String name);

    public AddressBook findByUserId(long id);
}
