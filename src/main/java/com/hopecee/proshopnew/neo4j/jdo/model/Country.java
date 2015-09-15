/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.model;

import java.io.Serializable;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 *
 * @author hope
 */
@PersistenceCapable( identityType = IdentityType.APPLICATION, cacheable = "false")
public class Country implements Serializable {

    private static final long serialVersionUID = 1429494278009243826L;
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long id;
    @Persistent
    //@Index(name = "COUNTRY_NAME_INDEX", unique = "true")
    private String country_name = null;
    @Persistent
    private String country_iso_code_2 = null;//Alpha-2
    @PrimaryKey
    @Persistent
    private String country_iso_code_3 = null;//Alpha-3
    @Persistent
    private int address_format_id = 0;

    public Country() {
    }

    public Country(String country_name,
            String country_iso_code_2,
            String country_iso_code_3,
            int address_format_id) {
        this.country_name = country_name;
        this.country_iso_code_2 = country_iso_code_2;
        this.country_iso_code_3 = country_iso_code_3;
        this.address_format_id = address_format_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_iso_code_2() {
        return country_iso_code_2;
    }

    public void setCountry_iso_code_2(String country_iso_code_2) {
        this.country_iso_code_2 = country_iso_code_2;
    }

    public String getCountry_iso_code_3() {
        return country_iso_code_3;
    }

    public void setCountry_iso_code_3(String country_iso_code_3) {
        this.country_iso_code_3 = country_iso_code_3;
    }

    public int getAddress_format_id() {
        return address_format_id;
    }

    public void setAddress_format_id(int address_format_id) {
        this.address_format_id = address_format_id;
    }

    @Override
    public String toString() {
       return "Country{" + "id=" + id + ", country_name=" + country_name + ", country_iso_code_2=" + country_iso_code_2 + ", country_iso_code_3=" + country_iso_code_3 + ", address_format_id=" + address_format_id + '}';
   
  //  return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
  
       
}
}
