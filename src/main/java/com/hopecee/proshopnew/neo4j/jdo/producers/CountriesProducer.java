/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.producers;

/**
 *
 * @author hope
 */
import com.hopecee.proshopnew.neo4j.jdo.model.Country;
import com.hopecee.proshopnew.neo4j.jdo.services.CountryNeo4jService;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

public class CountriesProducer //implements Serializable 
{

    @Inject
    private CountryNeo4jService countryNeo4jService;

    @Produces
    @Named("countries")
    @ApplicationScoped
    public List<Country> getCountries() {
        return countryNeo4jService.findAll();
    }

    public CountryNeo4jService getCountryNeo4jService() {
        return countryNeo4jService;
    }

    public void setCountryNeo4jService(CountryNeo4jService countryNeo4jService) {
        this.countryNeo4jService = countryNeo4jService;
    }
    
}
