/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.model;

//import com.hopecee.proshopnew.neo4j.jdo.model.ConfigurationGroup.PK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.annotations.Element;
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
@PersistenceCapable(//objectIdClass = PK.class,
        identityType = IdentityType.APPLICATION, cacheable = "false")
public class ConfigurationGroup implements Serializable {

    private static final long serialVersionUID = -5962027012213982359L;
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long id;
    // @Index(name = "ID_INDEX", unique = "true")
    //private long configuration_group_id = 0;
    //  @Index(name = "TITLE_INDEX", unique = "true") //TODO Not supported yet.
    //@PrimaryKey
    @Persistent
    private String configuration_group_title = null;
    @Persistent
    private String configuration_group_description = null;
    @Persistent
    private long sort_order = 0;
    @Persistent
    private long visible = 0;
    @Persistent(//mappedBy = "configuration_group",
            defaultFetchGroup = "true")
    @Element(dependent = "true") //can not exist without parent
    // @Order(extensions =@Extension(vendorName = "datanucleus", key = "list-ordering", value = "sort_order asc")) //TODO check wetherit works.
    private List<Configuration> configurations = new ArrayList<Configuration>();

    /**
     * Inner class representing Primary Key / public static class PK implements
     * Serializable { private static final long serialVersionUID =
     * -2505294906552920789L; public long id;
     *
     * public PK() { }
     *
     * public PK(String s) { this.id = Long.valueOf(s).longValue(); }
     *
     * @Override public String toString() { return "" + id; }
     *
     * @Override public int hashCode() { return (int)id; }
     *
     * @Override public boolean equals(Object other) { if (other != null &&
     * (other instanceof PK)) { PK otherPK = (PK)other; return otherPK.id ==
     * this.id; } return false; } }
     */
    public String getConfiguration_group_title() {
        return configuration_group_title;
    }

    public void setConfiguration_group_title(String configuration_group_title) {
        this.configuration_group_title = configuration_group_title;
    }

    public String getConfiguration_group_description() {
        return configuration_group_description;
    }

    public void setConfiguration_group_description(String configuration_group_description) {
        this.configuration_group_description = configuration_group_description;
    }

    public long getSort_order() {
        return sort_order;
    }

    public void setSort_order(long sort_order) {
        this.sort_order = sort_order;
    }

    public long getVisible() {
        return visible;
    }

    public void setVisible(long visible) {
        this.visible = visible;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }
 
    @Override
    public String toString() {
        return "ConfigurationGroup{" + "id=" + id + ", configuration_group_title=" + configuration_group_title + ", configuration_group_description=" + configuration_group_description + ", sort_order=" + sort_order + ", visible=" + visible + ", configurations=" + configurations + '}';
    }
}
