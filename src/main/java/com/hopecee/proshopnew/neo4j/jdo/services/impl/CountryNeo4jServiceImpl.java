/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services.impl;

//import au.com.bytecode.opencsv.CSVReader;
import com.hopecee.proshopnew.neo4j.jdo.model.Country;
import com.hopecee.proshopnew.neo4j.jdo.util.JDOUtil;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.services.CountryNeo4jService;
import java.io.Serializable;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

/**
 *
 * @author hope
 */
//@Named
//@SessionScoped
//@RequestScoped
public class CountryNeo4jServiceImpl implements CountryNeo4jService, Serializable {

    private static final long serialVersionUID = 6332057676727764285L;

    @Override
    public List<Country> findAll() {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        List<Country> countries = null;
        try {
            tx.begin();

            /*
             * 
             Query q = pm.newQuery(pm.getExtent(Country.class, true));
             //q.setFilter("country_name.equals(value)");
             //q.declareParameters("String value");
             //q.setResultClass(Country.class);
             q.setOrdering("country_name asc");
             countries = (List<Country>) q.execute();
             */

            Query q = pm.newQuery(Country.class);
            q.setOrdering("country_name asc");

            countries = (List<Country>) q.execute();

            tx.commit();

        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return countries;
    }

    @Override
    public Country findByName(String name) {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        try {
            Query q = pm.newQuery(Country.class);
            q.setFilter("country_name.equals(value)");
            q.declareParameters("String value");
            //q.setUnique(true);
            List<Country> countries = (List<Country>) q.execute(name);
            if (countries != null && !countries.isEmpty()) {
                return countries.get(0);
            }
        } finally {
            pm.close();
        }
        return null;
    }

    @Override
    public Country findById(Long id) {
        if (id == null) {
            return null;
        }
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Country country = null;

        try {
            country = (Country) pm.getObjectById(Country.class, id);
        } finally {
            pm.close();
        }
        return country;
    }

    @Override
    public Country findByAlpha_3(String code) {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        try {
            Query q = pm.newQuery(Country.class);
            q.setFilter("country_iso_code_3.equals(value)");
            q.declareParameters("String value");
            //q.setUnique(true);
            List<Country> countries = (List<Country>) q.execute(code);
            if (countries != null && !countries.isEmpty()) {
                return countries.get(0);
            }
        } finally {
            pm.close();
        }
        return null;
    }

    @Override
    public void save(Country country) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            if (findByName(country.getCountry_name()) == null) {
                tx.begin();
                pm.makePersistent(country);
                tx.commit();
            } else {
                System.out.println(country.getCountry_name() + " exist. hhhdv==hhhhhhhhhhhhhh");
            }
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public void update(Country country) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            // 1. Check required information has been set.
            if (country.getId() < 0) {
                throw new DAOException("The Country Id has not been set!!");
            }
            // 2. Check the user HAS BEEN created before.
            Query q = pm.newQuery("SELECT  FROM " + Country.class.getName() + " WHERE id == " + country.getId());
            List results = (List) q.execute();
            if (results.isEmpty()) {
                throw new DAOException("The Country does not exist!!");
            }
            // 3. Update the user
            Country userToUpdate = pm.getObjectById(Country.class, country.getId());

            if (!(country.getCountry_name() == null)) {
                userToUpdate.setCountry_name(country.getCountry_name());
            }
            if (!(country.getCountry_iso_code_2() == null)) {
                userToUpdate.setCountry_iso_code_2(country.getCountry_iso_code_2());
            }
            if (!(country.getCountry_iso_code_3() == null)) {
                userToUpdate.setCountry_iso_code_3(country.getCountry_iso_code_3());
            }

            if (!(country.getAddress_format_id() < 0)) {
                userToUpdate.setAddress_format_id(country.getAddress_format_id());
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
    public void delete(Country country) throws DAOException {
        PersistenceManager pm =
                JDOUtil.persistenceManagerFactory.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Country countryE = pm.getObjectById(Country.class, country.getId());
            pm.deletePersistent(countryE);
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
            Country country = pm.getObjectById(Country.class, id);
            if (country == null) {
                return;
            }
            pm.deletePersistent(country);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }
}
