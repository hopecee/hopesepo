/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services.impl;

import com.hopecee.proshopnew.neo4j.jdo.model.Configuration;
import com.hopecee.proshopnew.neo4j.jdo.model.ConfigurationGroup;
import com.hopecee.proshopnew.neo4j.jdo.model.User;
import com.hopecee.proshopnew.neo4j.jdo.services.ConfigurationNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.util.JDOUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

/**
 *
 * @author hope
 */
public class ConfigurationNeo4jServiceImpl implements ConfigurationNeo4jService, Serializable {

    private static final long serialVersionUID = 1142536118784517456L;

    @Override
    public List<Configuration> findAll() {
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

        List<Configuration> confs = null;
        try {
            tx.begin();
            
            Query q = pm.newQuery(Configuration.class);
            // q.setOrdering("configuration_title asc");
           
            confs = (List<Configuration>) q.execute();
            tx.commit();
           } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return confs;
    }

    @Override
    public Configuration findById(Long id) {

        System.out.println("ConfigurationGroup   : id :" + id);

        if (id == null) {
            return null;
        }
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Configuration conf = null;

        try {

            //  Object gh = JDOHelper.getObjectId(id);
            // System.out.println("ConfigurationGroup   : gh :"+gh);

            conf = (Configuration) pm.getObjectById(Configuration.class, id);
        } finally {
            pm.close();
        }
        return conf;
    }

    @Override
    public void save(Configuration conf) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();

        Transaction tx = pm.currentTransaction();

        try {
            if (findByName(conf.getConfiguration_title()) == null) {
                tx.begin();
                pm.makePersistent(conf);
                tx.commit();
            } else {
                System.out.println(conf.getConfiguration_title() + " exist. hhhdv==hhhhhhhhhhhhhh");
            }
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public void delete(Configuration conf) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try {
            tx.begin();
            Configuration confE = pm.getObjectById(Configuration.class, conf.getId());
            pm.deletePersistent(confE);
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
            Configuration conf = pm.getObjectById(Configuration.class, objectId);
            pm.deletePersistent(conf);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public Configuration findByName(String name) {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        System.out.println(" findByName ");
        try {
            Query q = pm.newQuery(Configuration.class);
            q.setFilter("configuration_title.equals(value)");
            q.declareParameters("String value");
            System.out.println(" findByNamev " + name);

            List<Configuration> confs = (List<Configuration>) q.execute(name);
            System.out.println(" findByName b");
            if (confs != null && !confs.isEmpty()) {
                // System.out.println(" findByName " + confs.get(0));

                return confs.get(0);
            }

        } finally {

            pm.close();
        }
        return null;
    }

    @Override
    public void update(Configuration conf) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            System.out.println("ghfhg ttttttttttttttt");

            // 1. Check required information has been set.
            if (conf.getId() < 0) {
                throw new DAOException("The Configuration Id has not been set!!");
            }
            // 2. Check the user HAS BEEN created before.
            Query countQuery = pm.newQuery("SELECT  FROM " + Configuration.class.getName() + " WHERE key == " + conf.getId());
            List results = (List) countQuery.execute();
            System.out.println(conf.getConfiguration_title() + " " + results.size());

            if (results.isEmpty()) {
                throw new DAOException("The Configuration does not exist!!");
            }
            // 3. Update the user
            Configuration confToUpdate = pm.getObjectById(Configuration.class, conf.getId());

            if (!(conf.getConfiguration_title() == null)) {
                confToUpdate.setConfiguration_title(conf.getConfiguration_title());
            }
            if (!(conf.getConfiguration_value() == null)) {
                confToUpdate.setConfiguration_value(conf.getConfiguration_value());
            }
            if (!(conf.getConfiguration_key() == null)) {
                confToUpdate.setConfiguration_key(conf.getConfiguration_key());
            }
            if (!(conf.getConfiguration_description() == null)) {
                confToUpdate.setConfiguration_description(conf.getConfiguration_description());
            }
            if (!(conf.getConfigurationGroupID() < 0)) {
                confToUpdate.setConfigurationGroupID(conf.getConfigurationGroupID());
            }


            if (!(conf.getSort_order() < 0)) {
                confToUpdate.setSort_order(conf.getSort_order());
            }

            if (!(conf.getDate_added() == null)) {
                confToUpdate.setDate_added(conf.getDate_added());
            }
            if (!(conf.getLast_modified() == null)) {
                confToUpdate.setLast_modified(conf.getLast_modified());
            }
            if (!(conf.getSet_function() == null)) {
                confToUpdate.setSet_function(conf.getSet_function());
            }
            if (!(conf.getUse_function() == null)) {
                confToUpdate.setUse_function(conf.getUse_function());
            }
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }
}
