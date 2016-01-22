/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services.impl;

import com.hopecee.proshopnew.neo4j.jdo.model.User;
import com.hopecee.proshopnew.neo4j.jdo.util.JDOUtil;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.services.UserNeo4jService;
import java.io.Serializable;
import java.util.List;
import javax.jdo.FetchGroup;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

/**
 *
 * @author hope
 */
public class UserNeo4jServiceImpl implements UserNeo4jService, Serializable {

    private static final long serialVersionUID = 7206644336784682444L;

    /*

     public void createUser(
     String usersEmailAddress,
     String usersFirstname,
     String usersLastname,
     int usersStatus,
     Date usersDor) throws IdentityException {
     PersistenceManager pm =
     JDOUtil.persistenceManagerFactory.getPersistenceManager();

     Transaction tx = pm.currentTransaction();
     try {


     tx.begin();


     Userfriendship user = new Userfriendship(usersEmailAddress, usersFirstname, usersLastname, usersStatus, usersDor);
     //Product product = new Product("Sony Discman2", "A standard discman from Sony2", 49.99);
     //inv.getProducts().add(product);
     pm.makePersistent(user);
     tx.commit();
     } finally {
     if (tx.isActive()) {
     tx.rollback();
     }
     pm.close();
     }


     // javax.jdo.PersistenceManager
     // pm = pmf.getPersistenceManager();
     // PersistenceManager
     // pm = getPM();

     //Transaction 
     tx = pm.currentTransaction();
     try {
     tx.begin();

     Query q = pm.newQuery("SELECT FROM " + Userfriendship.class.getName());
     List<User> users = (List<User>) q.execute();
     Iterator<User> iter = users.iterator();
     while (iter.hasNext()) {
     Userfriendship u = iter.next();
     System.out.println("Userfriendship Name        : " + u.getId() + " " + u.getName() + " : " + u.getUsersFirstname() + " : " + u.getUsersLastname() + " : " + u.getUsersStatus() + " : " + u.getUsersDor());

     }

     tx.commit();
     } finally {
     if (tx.isActive()) {
     tx.rollback();
     }

     if (pm != null) {
     pm.close();
     }
     }
     
     }
     */
    @Override
    public List<User> findAll() {
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
        List<User> users = null;
        pm.getFetchPlan().setGroup(FetchGroup.ALL);
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            Query q = pm.newQuery(User.class);
            q.setOrdering("name asc");
            users = (List<User>) q.execute();
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return users;
    }

    @Override
    public User findByName(String name) {
        try (PersistenceManager pm = JDOUtil.persistenceManagerFactory.getPersistenceManager()) {
            Query q = pm.newQuery(User.class);
            q.setFilter("name.equals(value)");
            q.declareParameters("String value");
            //q.setUnique(true);
            List<User> users = (List<User>) q.execute(name);
            if (users != null && !users.isEmpty()) {
                return users.get(0);
            }
        }
        return null;
    }

    @Override
    public User findByUsersName(String usersName) {
        try (PersistenceManager pm = JDOUtil.persistenceManagerFactory.getPersistenceManager()) {
            Query q = pm.newQuery(User.class);
            q.setFilter("usersName.equals(value)");
            q.declareParameters("String value");
            //q.setUnique(true);
            List<User> users = (List<User>) q.execute(usersName);
            if (users != null && !users.isEmpty()) {
                return users.get(0);
            }
        }
        return null;
    }

    @Override
    public User findById(Long objectId) {
        if (objectId == null) {
            return null;
        }
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        User user = null;

        try {
            user = (User) pm.getObjectById(User.class, objectId);
        } finally {
            pm.close();
        }
        return user;
    }

    @Override
    public void save(User user) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try {
            if (findByName(user.getName()) == null) {
                tx.begin();
                pm.makePersistent(user);
                tx.commit();
            } else {
                throw new DAOException(user.getName() + " exist.");
            }
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public void update(User user) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            // 1. Check required information has been set.
            if (user.getId() < 0) {
                throw new DAOException("The User Id has not been set!!");
            }
            // 2. Check the user HAS BEEN created before.
            Query countQuery = pm.newQuery("SELECT  FROM " + User.class.getName() + " WHERE id == " + user.getId());
            List results = (List) countQuery.execute();
            System.out.println(user.getName() + " " + results.size());

            if (results.isEmpty()) {
                throw new DAOException("The User does not exist!!");
            }
            // 3. Update the user
            User userToUpdate = pm.getObjectById(User.class, user.getId());

            if (!(user.getName() == null)) {
                userToUpdate.setName(user.getName());
            }
            if (!(user.getUsersName() == null)) {
                userToUpdate.setUsersName(user.getName());
            }
            if (!(user.getUsersDob() == null)) {
                userToUpdate.setUsersDob(user.getUsersDob());
            }
            if (!(user.getUsersFirstname() == null)) {
                userToUpdate.setUsersFirstname(user.getUsersFirstname());
            }

            if (!(user.getUsersLastname() == null)) {
                userToUpdate.setUsersLastname(user.getUsersLastname());
            }

            if (!(user.getUsersDor() == null)) {
                userToUpdate.setUsersDor(user.getUsersDor());
            }

            if (!(user.getUsersStatus() < 0)) {
                userToUpdate.setUsersStatus(user.getUsersStatus());
            }
            if (!(user.getLast_modified() == null)) {
                userToUpdate.setLast_modified(user.getLast_modified());
            }
            if (!(user.getFriendshipsTO() == null)) {
                userToUpdate.setFriendshipsTO(user.getFriendshipsTO());
            }
            //if (!(user.getFriendshipsFROM() == null)) {
             //   userToUpdate.setFriendshipsFROM(user.getFriendshipsFROM());
            //}
            tx.commit();

        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public void delete(User user) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            User userE = pm.getObjectById(User.class, user.getId());
            pm.deletePersistent(userE);
            tx.commit();

        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public void delete(Long objectId) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try {
            tx.begin();
            User user = pm.getObjectById(User.class, objectId);
            if (user == null) {
                return;
            }
            pm.deletePersistent(user);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }
}
