/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.model;

//import com.hopecee.proshopnew.neo4j.jdo.model.Configuration.PK;
import java.io.Serializable;
import java.util.Date;
import java.util.StringTokenizer;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 *
 * @author hope
 */
@PersistenceCapable(//objectIdClass = PK.class, 
        identityType = IdentityType.APPLICATION, cacheable = "false")
public class Configuration implements Serializable {

    private static final long serialVersionUID = -2794143028751488751L;
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long id;
    
    
    /*
    @PrimaryKey
    @Persistent//(defaultFetchGroup = "true")
    private ConfigurationGroup configuration_group;// = null;
    */
    
     @Persistent
     private long configurationGroupID;
    
    
    // @Index(name = "TITLE_INDEX", unique = "true") //TODO Not supported yet.
    @Persistent
    private String configuration_title = null;
    @Persistent
    private String configuration_value = null;
    @Persistent
    private String configuration_key = null;
    @Persistent
    private String configuration_description = null;
    @Persistent
    private long sort_order = 0;
    @Persistent
    private Date last_modified = null;
    @Persistent
    private Date date_added = null;
    @Persistent
    private String use_function = null;
    @Persistent
    private String set_function = null;

    public String getConfiguration_title() {
        return configuration_title;
    }

    public void setConfiguration_title(String configuration_title) {
        this.configuration_title = configuration_title;
    }

    public String getConfiguration_value() {
        return configuration_value;
    }

    public void setConfiguration_value(String configuration_value) {
        this.configuration_value = configuration_value;
    }

    public String getConfiguration_key() {
        return configuration_key;
    }

    public void setConfiguration_key(String configuration_key) {
        this.configuration_key = configuration_key;
    }

    public String getConfiguration_description() {
        return configuration_description;
    }

    public void setConfiguration_description(String configuration_description) {
        this.configuration_description = configuration_description;
    }

   

    public Date getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(Date last_modified) {
        this.last_modified = last_modified;
    }

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }

    public String getUse_function() {
        return use_function;
    }

    public void setUse_function(String use_function) {
        this.use_function = use_function;
    }

    public String getSet_function() {
        return set_function;
    }

    public void setSet_function(String set_function) {
        this.set_function = set_function;
    }

    public long getSort_order() {
        return sort_order;
    }

    public void setSort_order(long sort_order) {
        this.sort_order = sort_order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConfigurationGroupID() {
        return configurationGroupID;
    }

    public void setConfigurationGroupID(long configurationGroupID) {
        this.configurationGroupID = configurationGroupID;
    }

    @Override
    public String toString() {
        return "Configuration{" + "id=" + id + ", configurationGroupID=" + configurationGroupID + ", configuration_title=" + configuration_title + ", configuration_value=" + configuration_value + ", configuration_key=" + configuration_key + ", configuration_description=" + configuration_description + ", sort_order=" + sort_order + ", last_modified=" + last_modified + ", date_added=" + date_added + ", use_function=" + use_function + ", set_function=" + set_function + '}';
    }

  

    /**
     * ========================================= Inner class representing
     * Primary Key
     * /
     public static class PK implements Serializable
    {
        private static final long serialVersionUID = 106334957256958395L;
        public long id; // Same name as real field above
        public ConfigurationGroup.PK configuration_group; // Use same name as the real field above

        public PK()
        {
        }

        public PK(String s)
        {
            StringTokenizer token = new StringTokenizer(s,"::");
            this.id = Long.valueOf(token.nextToken()).longValue();
            this.configuration_group = new ConfigurationGroup.PK(token.nextToken());
        }

        @Override
        public String toString()
        {
            return "" + id + "::" + this.configuration_group.toString();
        }

        @Override
        public int hashCode()
        {
            return (int)id ^ configuration_group.hashCode();
        }

        @Override
        public boolean equals(Object other)
        {
            if (other != null && (other instanceof PK))
            {
                PK otherPK = (PK)other;
                return otherPK.id == this.id && this.configuration_group.equals(otherPK.configuration_group);
            }
            return false;
        }
        
    }
     */
}
