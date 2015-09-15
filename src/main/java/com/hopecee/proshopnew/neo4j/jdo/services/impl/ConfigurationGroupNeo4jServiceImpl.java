/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services.impl;

import com.hopecee.proshopnew.neo4j.jdo.model.Configuration;
//import com.hopecee.proshopnew.neo4j.jdo.model.Configuration.PK;
import com.hopecee.proshopnew.neo4j.jdo.model.ConfigurationGroup;
import com.hopecee.proshopnew.neo4j.jdo.services.ConfigurationGroupNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.util.JDOUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

/**
 *
 * @author hope
 */
public class ConfigurationGroupNeo4jServiceImpl implements ConfigurationGroupNeo4jService, Serializable {

    private static final long serialVersionUID = 7474869383121427850L;

    @Override
    public List<ConfigurationGroup> findAll() {
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

        List<ConfigurationGroup> confGrp = null;
        try {
            tx.begin();
            Query q = pm.newQuery(ConfigurationGroup.class);
            q.setOrdering("configuration_group_title asc");
            confGrp = (List<ConfigurationGroup>) q.execute();
            tx.commit();

        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return confGrp;
    }

    @Override
    public ConfigurationGroup findById(Long id) {

        if (id == null) {
            return null;
        }

        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        ConfigurationGroup confGrp = null;

        try {
            /*
             Query q = pm.newQuery(ConfigurationGroup.class);
             q.setFilter("configuration_group_title.equals(value)");
             q.declareParameters("String value");
             q.execute("");
             */
            //String strId = Long.toString(id);

            confGrp = pm.getObjectById(ConfigurationGroup.class, id);
            System.out.println(" ddddddddddddddddddddddddddddddd : ");
        } finally {
            pm.close();
        }
        return confGrp;
    }

    @Override
    public void save(ConfigurationGroup confGrp) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            if (findByName(confGrp.getConfiguration_group_title()) == null) {

                tx.begin();
                pm.makePersistent(confGrp);

                tx.commit();

            } else {
                System.out.println(confGrp.getConfiguration_group_title() + " exist.dddddddddddddddddddddddddddddddddddddddd");

            }
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public void delete(ConfigurationGroup confGrp) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try {
            tx.begin();
            ConfigurationGroup confGrpE = pm.getObjectById(ConfigurationGroup.class, confGrp.getId());
            pm.deletePersistent(confGrpE);
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
            ConfigurationGroup confGrp = pm.getObjectById(ConfigurationGroup.class, objectId);
            pm.deletePersistent(confGrp);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public ConfigurationGroup findByName(String name) {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        try {
            // Integer i1 = 0; 
            // Long cGId = new long(0); 

            System.out.println("Long 888: " + name);
            //String id2 = "1";
            //  System.out.println("l =====: " +  pm.getObjectById(ConfigurationGroup.class,  id2));
            // System.out.println("Long : " + pm.getObjectById(cGId));
            ///pm.newQuery(ConfigurationGroup.class);
            //String id2 = "0";
            //System.out.println("l =====: " +  pm.getObjectById(ConfigurationGroup.class,  id2.toString()));
            //   System.out.println("l =====: " +  pm.getObjectById(ConfigurationGroup.class,  "1"));
            //   ConfigurationGroup.PK pk = new ConfigurationGroup.PK("1");
            ///   System.out.println("l =====60q1: " + pk.toString());
            // Object obj1 = pm.getObjectById( ConfigurationGroup.class, pk.toString());
            // pm.refresh(obj1);
            // System.out.println("l =====601: " +  obj1);
            // Query q1 = pm.newQuery(ConfigurationGroup.class);
            // q1.execute(name);
            //  System.out.println("l =====63q: " + pm.getObjectById(ConfigurationGroup.class, "1"));

            Query q = pm.newQuery(ConfigurationGroup.class);
            System.out.println("Long : 1" + name);
            q.setFilter("configuration_group_title.equals(value)");
            q.declareParameters("String value");
            // q.setUnique(true);
            //  System.out.println("Long : 2" + name);

            List<ConfigurationGroup> confg = (List<ConfigurationGroup>) q.execute(name);
            System.out.println("Long : " + confg.size());

            if (confg != null && !confg.isEmpty()) {
                //   Object id = pm.getObjectId(confg.get(0));
                //  ConfigurationGroup obj = pm.makePersistent(confg.get(0));
                //  long l = confg.get(0).getId();
                //  System.out.println("l =====: " + l);
                //  Object i = confg.get(0).getId();

                // System.out.println("i.getClass() ===k== : " + pk.toString());
                // PK id2 = new PK(id.toString());
                //   PK tmp = new PK("0");
                //  ConfigurationGroup.PK key = tmp.configuration_group;
                // long l2 = Long.valueOf("0").longValue();
                // Object yourObject = new Integer((String) id);
                // String id2 = "0";
                // System.out.println("l =====: " + pm.getObjectById(ConfigurationGroup.class, pk.toString()));
                //

                //System.out.println("pm.getObjectId =====: " +  l2);
//   System.out.println( "findById "+ findById(l));

                return confg.get(0);
            }
        } finally {
            pm.close();
        }
        System.out.println("Long null: ");

        return null;
    }

    @Override
    public void update(ConfigurationGroup confGrp) throws DAOException {

        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            // 1. Check required information has been set.
            if (confGrp.getId() < 0) {
                throw new DAOException("The ConfigurationGroup Id has not been set!!");
            }
            // 2. Check the ConfigurationGroup HAS BEEN created before.
            Query countQuery = pm.newQuery("SELECT  FROM " + ConfigurationGroup.class.getName() + " WHERE id == " + confGrp.getId());
            List results = (List) countQuery.execute();
            if (results.isEmpty()) {
                throw new DAOException("The ConfigurationGroup does not exist!!");
            }

            // 3. Update the ConfigurationGroup.
            ConfigurationGroup confGrpToUpdate = pm.getObjectById(ConfigurationGroup.class, confGrp.getId());

            if (!(confGrp.getConfiguration_group_title() == null)) {
                confGrpToUpdate.setConfiguration_group_title(confGrp.getConfiguration_group_title());

            }
            if (!(confGrp.getConfiguration_group_description() == null)) {
                confGrpToUpdate.setConfiguration_group_description(confGrp.getConfiguration_group_description());
            }

            /*
             System.out.println("getConfigurations(): !isE");
                
             if (!(confGrp.getConfigurations().isEmpty())) {
             System.out.println("getConfigurations(): !isEmpty");
             System.out.println("getConfigurations(): !null : " + confGrp.getConfigurations());
 
             //Check if the configurations exist.
             List<Configuration> configurations = new ArrayList<Configuration>();

             // Iterator<Configuration> confGrpToUpdateIter = confGrpToUpdate.getConfigurations().iterator();

             //  Iterator<Configuration> iter = confGrp.getConfigurations().iterator();
             //List<Configuration> configurations = new ArrayList<Configuration>();
             Configuration conf = pm.getObjectById(Configuration.class, confGrp.getConfigurations().iterator().next().getId());
             System.out.println(" one gurations   : "+conf);
             if (!(confGrpToUpdate.getConfigurations().contains(conf))) {
             System.out.println(" one configurations   : ");
                
             configurations.add(conf);
             configurations.addAll(confGrpToUpdate.getConfigurations());
                  
             confGrpToUpdate.setConfigurations(configurations);
             // confGrpToUpdateID = JDOHelper.getObjectId(confGrpToUpdate);
             System.out.println(" one configurations =========added  : "+ configurations);
             }  
                
             // confGrpToUpdate.getConfigurations().addAll(configurations);
              
             //confGrpToUpdate.addConfiguration(conf);
             // confGrpToUpdate.setConfigurations(confGrpToUpdate.getConfigurations());
             //  System.out.println(" configurations : "+confGrpToUpdate.getConfigurations());
               
             }
            
             */
            if (!(confGrp.getSort_order() < 0)) {
                confGrpToUpdate.setSort_order(confGrp.getSort_order());
            }

            if (!(confGrp.getVisible() < 0)) {
                confGrpToUpdate.setVisible(confGrp.getVisible());
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
    public void addConfiguration(long confGrpId, long confId) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            ConfigurationGroup confGrp = pm.getObjectById(ConfigurationGroup.class, confGrpId);
            Configuration conf = pm.getObjectById(Configuration.class, confId);

            if ((confGrp != null) && (conf != null)) {
                List<Configuration> configurations = new ArrayList<Configuration>();

                if (!(confGrp.getConfigurations().contains(conf))) {
                    configurations.add(conf);
                }

                confGrp.getConfigurations().addAll(configurations);
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
    public void removeConfiguration(long confGrpId, long confId) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            ConfigurationGroup confGrp = pm.getObjectById(ConfigurationGroup.class, confGrpId);
            Configuration conf = pm.getObjectById(Configuration.class, confId);

            if ((confGrp != null) && (conf != null)) {

                if (confGrp.getConfigurations().contains(conf)) {
                    confGrp.getConfigurations().remove(conf);
                }

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
