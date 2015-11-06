/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services.impl;

import com.hopecee.proshopnew.neo4j.jdo.model.User;
import com.hopecee.proshopnew.neo4j.jdo.model.UserFriendship;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.services.UserFriendshipNeo4jService;
import java.io.Serializable;
import java.util.List;
import com.hopecee.proshopnew.neo4j.jdo.util.JDOUtil;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.services.UserNeo4jService;
import com.hopecee.proshopnew.servlets.TuFriendServlet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jdo.FetchGroup;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

/**
 *
 * @author hope
 */
public class UserFriendshipNeo4jServiceImpl implements UserFriendshipNeo4jService, Serializable {

    private static final long serialVersionUID = -970978603812839882L;

    @Override
    public void createFriendship(String userId, String friendId) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();

        long userIdL = Long.parseLong(userId.toString());
        long friendIdL = Long.parseLong(friendId.toString());

        UserFriendship friendshipTO = new UserFriendship(userIdL, friendIdL);
        UserFriendship friendshipFROM = new UserFriendship(friendIdL, userIdL);

        Transaction tx = pm.currentTransaction();

        try {
            tx.begin();

            pm.makePersistent(friendshipTO);
            pm.makePersistent(friendshipFROM);

            // We have javax.jdo.option.DetachAllOnCommit set, so all get detached at this point
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(UserFriendshipNeo4jServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        pm = JDOUtil.persistenceManagerFactory.getPersistenceManager();
        pm.getFetchPlan().setGroup(FetchGroup.ALL);
        tx = pm.currentTransaction();

        try {
            tx.begin();

            User user = (User) pm.getObjectById(User.class, userIdL);
            User friend = (User) pm.getObjectById(User.class, friendIdL);
/*
            Iterator<UserFriendship> iterR = user.getFriendshipsTO().iterator();
            while (iterR.hasNext()) {
                UserFriendship rell = iterR.next();//User is now refered as customer.
                System.out.println(rell.getUserId() + " : " + rell.getFriendId());
            }



            Query q = pm.newQuery(User.class);
           // q.setOrdering("userId asc");
           // q.declareParameters("long id");
            q.setFilter("id == "+userIdL);
          //User userFr = (User) q.execute();
          List results = (List)q.execute();
Iterator iter = results.iterator();
User mu = (User)iter.next();

            System.out.println("========= : "+mu);
*/





            // Establish the relation,  user to friend.
            user.addFriendshipsTO(friendshipTO);
            // Establish the relation,  friend to user.
            friend.addFriendshipsTO(friendshipFROM);

            tx.commit();

        } catch (Exception e) {
            Logger.getLogger(UserFriendshipNeo4jServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public List<UserFriendship> findAllTO() {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();

        List<UserFriendship> userFriendships = null;
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            Query q = pm.newQuery(UserFriendship.class);
            q.setOrdering("userId asc");
            userFriendships = (List<UserFriendship>) q.execute();
            System.out.println("==============user================");
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return userFriendships;
    }

    @Override
    public List<UserFriendship> findAllFROM() {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();

        List<UserFriendship> userFriendships = null;
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            Query q = pm.newQuery(UserFriendship.class);
            q.setOrdering("friendId asc");
            userFriendships = (List<UserFriendship>) q.execute();
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return userFriendships;
    }

    @Override
    public List<UserFriendship> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserFriendship findById(Long objectId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(UserFriendship object) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(UserFriendship object) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(UserFriendship object) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long objectId) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
