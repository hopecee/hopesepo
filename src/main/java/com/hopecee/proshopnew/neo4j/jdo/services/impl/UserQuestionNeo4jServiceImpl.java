/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services.impl;

import com.hopecee.proshopnew.neo4j.jdo.model.AddressBook;
import com.hopecee.proshopnew.neo4j.jdo.model.User;
import com.hopecee.proshopnew.neo4j.jdo.model.UserQuestion;
import com.hopecee.proshopnew.neo4j.jdo.services.AddressBookNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.services.UserQuestionNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.util.JDOUtil;
import java.io.Serializable;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

/**
 *
 * @author hope
 */
public class UserQuestionNeo4jServiceImpl implements UserQuestionNeo4jService, Serializable {

    private static final long serialVersionUID = 6895844935806216845L;

    @Override
    public List<UserQuestion> findAll() {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        List<UserQuestion> userQuestions = null;
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            Query q = pm.newQuery(UserQuestion.class);
            q.setOrdering("users_id asc");
            userQuestions = (List<UserQuestion>) q.execute();
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return userQuestions;
    }

    @Override
    public UserQuestion findById(Long id) {
        if (id == null) {
            return null;
        }
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        UserQuestion userQuestion = null;

        try {
            userQuestion = (UserQuestion) pm.getObjectById(UserQuestion.class, id);
        } finally {
            pm.close();
        }
        return userQuestion;
    }

    @Override
    public void save(UserQuestion userQuestion) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
           // System.out.println("idvvg :  ");
            if (findByUserId(userQuestion.getUsers_id()) == null) {
                tx.begin();
                pm.makePersistent(userQuestion);
                tx.commit();
            } else {
                throw new DAOException(userQuestion.getUsers_id() + " exist.");
            }
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public void update(UserQuestion object) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(UserQuestion userQuestion) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            UserQuestion userQuestionE = pm.getObjectById(UserQuestion.class, userQuestion.getId());
            pm.deletePersistent(userQuestionE);
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
            UserQuestion userQuestion = pm.getObjectById(UserQuestion.class, id);
            if (userQuestion == null) {
                return;
            }
            pm.deletePersistent(userQuestion);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public UserQuestion findByUserId(long id) {
        if (id < 0) {
            return null;
        }
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        UserQuestion userQuestion = null;
        System.out.println("id : " + id);

        try {
            tx.begin();
            Query q = pm.newQuery(UserQuestion.class);
            q.setFilter("users_id == value");
            q.declareParameters("long value");
            List<UserQuestion> results = (List<UserQuestion>) q.execute(id);

            if (results.iterator().hasNext()) {
                userQuestion = (UserQuestion) results.get(0);
            }
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return userQuestion;
    }
}
