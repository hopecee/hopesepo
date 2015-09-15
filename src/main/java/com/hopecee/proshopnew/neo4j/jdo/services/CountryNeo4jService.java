/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services;

import com.hopecee.proshopnew.neo4j.jdo.model.Country;

/**
 *
 * @author hope
 */
public interface  CountryNeo4jService extends DefaultDAO<Country, Long> {

    public Country findByName( String name );
    public Country findByAlpha_3( String code );//country_iso_code_3

}
