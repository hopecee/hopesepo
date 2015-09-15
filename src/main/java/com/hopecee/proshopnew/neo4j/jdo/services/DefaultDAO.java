/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services;

/**
 *
 * @author hope
 */
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: cyril Date: 12/5/12 Time: 5:29 PM To change
 * this template use File | Settings | File Templates.
 */
public interface DefaultDAO<T, V> {

    public List<T> findAll();

    public T findById(V objectId);

    public void save(T object) throws DAOException;

    public void update(T object) throws DAOException;

    public void delete(T object) throws DAOException;

    public void delete(Long objectId) throws DAOException;
}
