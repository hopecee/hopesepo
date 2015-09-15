/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 *
 * @author hope
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "false")
//@Unique(name="MY_COMPOSITE_IDX", members={"name"})
public class User implements Serializable {

    private static final long serialVersionUID = 1233766699444710651L;
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long id;
    //@PrimaryKey
    // @Index(name="NAME_INDEX", unique="true")
    // @Unique(name="MYFIELD1_IDX")
    @Persistent
    private String name = null; //usersEmailAddress
    @Persistent
    private String usersName = null;
    @Persistent
    private String usersFirstname = null;
    @Persistent
    private String usersLastname = null;
    @Persistent
    private int usersStatus = 0;
    @Persistent
    //private Date usersDor = null;
    private String usersDor = null;
    @Persistent
    private String last_modified = null;
    @Persistent
    private String usersDob = null;
    @Persistent
    private Set<User> customers = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getUsersFirstname() {
        return usersFirstname;
    }

    public void setUsersFirstname(String usersFirstname) {
        this.usersFirstname = usersFirstname;
    }

    public String getUsersLastname() {
        return usersLastname;
    }

    public void setUsersLastname(String usersLastname) {
        this.usersLastname = usersLastname;
    }

    public int getUsersStatus() {
        return usersStatus;
    }

    public void setUsersStatus(int usersStatus) {
        this.usersStatus = usersStatus;
    }

    public String getUsersDor() {
        return usersDor;
    }

    public void setUsersDor(String usersDor) {
        this.usersDor = usersDor;
    }

    public Set<User> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<User> customers) {
        this.customers = customers;
    }

    public String getUsersDob() {
        return usersDob;
    }

    public void setUsersDob(String usersDob) {
        this.usersDob = usersDob;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", usersFirstname=" + usersFirstname + ", usersLastname=" + usersLastname + ", usersStatus=" + usersStatus + ", usersDor=" + usersDor + ", last_modified=" + last_modified + ", usersDob=" + usersDob + ", customers=" + customers + '}';
    }
}
