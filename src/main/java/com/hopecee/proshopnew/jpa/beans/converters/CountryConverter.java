/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.jpa.beans.converters;

import com.hopecee.proshopnew.neo4j.jdo.model.Country;
import com.hopecee.proshopnew.neo4j.jdo.services.CountryNeo4jService;
import java.io.Serializable;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author hope
 */
//@Named
//@ManagedBean(name = "countryConverter")
//@RequestScoped
//@Named
//@RequestScoped
//@SessionScoped
//@ApplicationScoped
@SessionScoped
//@FacesConverter("convert.country")
public class CountryConverter implements Converter, Serializable {

    private static final long serialVersionUID = -3314514380035171084L;
    // private static final Logger log = LoggerFactory.getLogger(TestConverter.class);
    //@Inject
    //UserBean userBean;
    @Inject
    CountryNeo4jService countryNeo4jService;
    //private String name = null;
    //@Inject
    // FacesContext context;
private static HashMap map = new HashMap();


    @PostConstruct
    public void setup() {

        System.out.println("CountryConverter started up");

    }

    @PreDestroy
    public void shutdown() {

        System.out.println("CountryConverter shutting down");

    }
    /*
     @Override
     public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue) {

     // log.info(getClass().getSimpleName() + ".getAsObject: " + name);
     //return countryNeo4jService.getCountriesService().selectByName(name);
     if (submittedValue == null || submittedValue.isEmpty()) {
     System.out.println("submittedValue: = : null");

     return null;
     }
     // return userBean.getCountryNeo4jService().findByName(String.valueOf(submittedValue));
     Country sValue = getCountryNeo4jService().findByName(String.valueOf(submittedValue));
     System.out.println("submittedValue: ======= : " + sValue.getId());
   
     return sValue.getId();   
     }
   
     @Override
     public String getAsString(FacesContext arg0, UIComponent arg1, Object modelValue) {
     //log.info(getClass().getSimpleName() + ".getAsString: " + obj);
     if (modelValue == null) {
     System.out.println("modelValue: = : null" );
   
     return "";
     }
     String str = "";
     if (modelValue instanceof Country) {
     str = "" + ((Country) modelValue).getCountry_name();
     System.out.println("modelValue: = : " + str);
     return str;
 
     } else {
     throw new ConverterException(new FacesMessage(String.format("%s is not a valid User", modelValue)));
     }
     }
     */

    @Override 
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        
        System.out.println("submittedVmap: ==== : " + map.get(value));
 
        // log.info(getClass().getSimpleName() + ".getAsObject: " + name);
        //return countryNeo4jService.getCountriesService().selectByName(name);
 System.out.println("submittedValue: ==== : " + value);
  System.out.println("submittedValue: ==== : " + Long.valueOf(value));
  
      Country sValue = getCountryNeo4jService().findByName(String.valueOf(value));
     System.out.println("submittedValue: ======= : " + sValue.getId());
   
        
       Long id = Long.valueOf(value);
         System.out.println("submittedValue: ======iu : " + id);
         
        if (value == null || value.isEmpty()) {
            System.out.println("value : = : null");
            return null;
        }   
        // return userBean.getCountryNeo4jService().findByName(String.valueOf(submittedValue));
        // Country sValue = getCountryNeo4jService().findByName(String.valueOf(submittedValue));
        System.out.println("submittedValue: ======iu : " + id);
        //System.out.println("getAsObject - Context is: " + context);
        // return sValue.getId();  
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        //log.info(getClass().getSimpleName() + ".getAsString: " + obj);
        if (modelValue == null) {
            System.out.println("modelValue: = : null");

            return "";
        }
        return modelValue.toString();

        /*
         String str = "";
         if (modelValue instanceof Country) {
         str = "" + ((Country) modelValue).getCountry_name();
         System.out.println("modelValue: = : " + str);
         return str;

         } else {
         throw new ConverterException(new FacesMessage(String.format("%s is not a valid User", modelValue)));
         }
        
         */
    }

    public CountryNeo4jService getCountryNeo4jService() {
        return countryNeo4jService;
    }

    public void setCountryNeo4jService(CountryNeo4jService countryNeo4jService) {
        this.countryNeo4jService = countryNeo4jService;
    }
}
