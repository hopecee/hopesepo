/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services.impl;

import com.hopecee.proshopnew.neo4j.jdo.model.AddressBook;
import com.hopecee.proshopnew.neo4j.jdo.util.JDOUtil;
import com.hopecee.proshopnew.neo4j.jdo.services.AddressBookNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import java.io.Serializable;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

/**
 *
 * @author hope
 */
public class AddressBookNeo4jServiceImpl implements AddressBookNeo4jService, Serializable {

    private static final long serialVersionUID = -6036424175718560749L;

    @Override
    public List<AddressBook> findAll() {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();

        /*  
         CSVReader csvReader = null;
         try { 
         csvReader.readNext();
         } catch (IOException ex) {
         Logger.getLogger(CountryNeo4jServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
         Transaction tx = pm.currentTransaction();
        List<AddressBook> addresses = null;
       
        try {
            tx.begin();

            Query q = pm.newQuery(AddressBook.class);
            q.setOrdering("users_id asc");
            addresses = (List<AddressBook>) q.execute();
            tx.commit();

        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return addresses;
    }

    @Override
    public AddressBook findById(Long id) {
        if (id == null) {
            return null;
        }
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        AddressBook addressBook = null;
        try {
            System.out.println("findById AddressBook: " + id);

            addressBook = (AddressBook) pm.getObjectById(AddressBook.class, id);
            System.out.println("findById AddressBook : ");

        } finally {
            pm.close();
        }
        return addressBook;
    }

    @Override
    public void save(AddressBook addressBook) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            // System.out.println("idvv :  ") ;
            if (findByUserId(addressBook.getUsers_id()) == null) {
                tx.begin();
                pm.makePersistent(addressBook);
                tx.commit();
            } else {
                throw new DAOException(addressBook.getUsers_id() + " exist.");
            }

        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public void update(AddressBook addressBook) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            // 1. Check required information has been set.
            if (addressBook.getId() < 0) {
                throw new DAOException("The AddressBook Id has not been set!!");
            }
            // 2. Check the user HAS BEEN created before.
            Query q = pm.newQuery("SELECT  FROM " + AddressBook.class.getName() + " WHERE id == " + addressBook.getId());
            List results = (List) q.execute();
            System.out.println(addressBook.getUsers_id() + " " + results.size());

            if (results.isEmpty()) {
                throw new DAOException("The AddressBook does not exist!!");
            }
            // 3. Update the user
            AddressBook addressBookToUpdate = pm.getObjectById(AddressBook.class, addressBook.getId());

            if (!(addressBook.getUsers_id() < 0)) {
                addressBookToUpdate.setUsers_id(addressBook.getUsers_id());
            }
            if (!(addressBook.getEntry_street_address() == null)) {
                addressBookToUpdate.setEntry_street_address(addressBook.getEntry_street_address());
            }
            if (!(addressBook.getEntry_street_address2() == null)) {
                addressBookToUpdate.setEntry_street_address2(addressBook.getEntry_street_address2());
            }

            if (!(addressBook.getEntry_city() == null)) {
                addressBookToUpdate.setEntry_city(addressBook.getEntry_city());
            }

            if (!(addressBook.getEntry_state() == null)) {
                addressBookToUpdate.setEntry_state(addressBook.getEntry_state());
            }

            if (!(addressBook.getEntry_country_id() < 0)) {
                addressBookToUpdate.setEntry_country_id(addressBook.getEntry_country_id());
            }
            if (!(addressBook.getEntry_postcode() == null)) {
                addressBookToUpdate.setEntry_postcode(addressBook.getEntry_postcode());
            }

            if (!(addressBook.getEntry_phone_default() == null)) {
                addressBookToUpdate.setEntry_phone_default(addressBook.getEntry_phone_default());
            }

            if (!(addressBook.getEntry_phone2() == null)) {
                addressBookToUpdate.setEntry_phone2(addressBook.getEntry_phone2());
            }
            if (!(addressBook.getEntry_fax() == null)) {
                addressBookToUpdate.setEntry_fax(addressBook.getEntry_fax());
            }
            if (!(addressBook.getEntry_occupation() == null)) {
                addressBookToUpdate.setEntry_occupation(addressBook.getEntry_occupation());
            }

            if (!(addressBook.getEntry_url() == null)) {
                addressBookToUpdate.setEntry_url(addressBook.getEntry_url());
            }

            tx.commit();

        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public void delete(AddressBook addressBook) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            AddressBook addressBookE = pm.getObjectById(AddressBook.class, addressBook.getId());
            pm.deletePersistent(addressBookE);
            tx.commit();

        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try {
            tx.begin();
            AddressBook addressBook = pm.getObjectById(AddressBook.class, id);

            pm.deletePersistent(addressBook);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public AddressBook findByUserId(long id) {
        if (id < 0) {
            return null;
        }
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        AddressBook addressBook = null;
        System.out.println("id : " + id);

        try {
            tx.begin();
            // Query q = pm.newQuery("SELECT  FROM " + AddressBook.class.getName() + " WHERE users_id  == value PARAMETERS double value");
            Query q = pm.newQuery(AddressBook.class);
            q.setFilter("users_id == value");
            q.declareParameters("long value");
            List<AddressBook> results = (List<AddressBook>) q.execute(id);

            if (results.iterator().hasNext()) {
                addressBook = (AddressBook) results.get(0);
            }
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return addressBook;
    }

    @Override
    public AddressBook findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
