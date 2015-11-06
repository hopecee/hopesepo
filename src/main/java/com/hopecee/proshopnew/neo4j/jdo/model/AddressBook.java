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
@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "false")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = -4987779338342833410L;
    @PrimaryKey
   @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long id;
    //@Index(name = "USER_ID_INDEX", unique = "true")
     @Persistent
    private long users_id = 0;
    @Persistent
    private String entry_occupation = null;
    @Persistent
    private String entry_street_address = null;
    @Persistent
    private String entry_street_address2 = null;
    @Persistent
    private String entry_city = null;
    @Persistent
    private String entry_state = null;
    @Persistent
    private String entry_postcode = null;
    @Persistent
    private long entry_country_id = 0;
    @Persistent
    private String entry_fax = null;
    @Persistent
    private String entry_url = null;
    @Persistent
    private String entry_phone_default = null;
    @Persistent
    private String entry_phone2 = null;

    
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUsers_id() {
        return users_id;
    }

    public void setUsers_id(long users_id) {
        this.users_id = users_id;
    }

    public String getEntry_occupation() {
        return entry_occupation;
    }

    public void setEntry_occupation(String entry_occupation) {
        this.entry_occupation = entry_occupation;
    }

    public String getEntry_street_address() {
        return entry_street_address;
    }

    public void setEntry_street_address(String entry_street_address) {
        this.entry_street_address = entry_street_address;
    }

    public String getEntry_street_address2() {
        return entry_street_address2;
    }

    public void setEntry_street_address2(String entry_street_address2) {
        this.entry_street_address2 = entry_street_address2;
    }

    public String getEntry_city() {
        return entry_city;
    }

    public void setEntry_city(String entry_city) {
        this.entry_city = entry_city;
    }

    public String getEntry_state() {
        return entry_state;
    }

    public void setEntry_state(String entry_state) {
        this.entry_state = entry_state;
    }

    public String getEntry_postcode() {
        return entry_postcode;
    }

    public void setEntry_postcode(String entry_postcode) {
        this.entry_postcode = entry_postcode;
    }

    public long getEntry_country_id() {
        return entry_country_id;
    }

    public void setEntry_country_id(long entry_country_id) {
        this.entry_country_id = entry_country_id;
    }

    public String getEntry_fax() {
        return entry_fax;
    }

    public void setEntry_fax(String entry_fax) {
        this.entry_fax = entry_fax;
    }

    public String getEntry_url() {
        return entry_url;
    }

    public void setEntry_url(String entry_url) {
        this.entry_url = entry_url;
    }

    public String getEntry_phone_default() {
        return entry_phone_default;
    }

    public void setEntry_phone_default(String entry_phone_default) {
        this.entry_phone_default = entry_phone_default;
    }

    public String getEntry_phone2() {
        return entry_phone2;
    }

    public void setEntry_phone2(String entry_phone2) {
        this.entry_phone2 = entry_phone2;
    }

    @Override
    public String toString() {
        return "AddressBook{" + "id=" + id + ", users_id=" + users_id + ", entry_occupation=" + entry_occupation + ", entry_street_address=" + entry_street_address + ", entry_street_address2=" + entry_street_address2 + ", entry_city=" + entry_city + ", entry_state=" + entry_state + ", entry_postcode=" + entry_postcode + ", entry_country_id=" + entry_country_id + ", entry_fax=" + entry_fax + ", entry_url=" + entry_url + ", entry_phone_default=" + entry_phone_default + ", entry_phone2=" + entry_phone2 + '}';
    }

    

    
    
    
   
    
}
