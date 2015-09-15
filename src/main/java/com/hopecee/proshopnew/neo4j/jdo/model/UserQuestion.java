/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.model;

import java.util.Date;
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
public class UserQuestion {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long id;
    @Persistent
    private long users_id = 0;
    @Persistent
    private String selectedQuestion1 = null;
    @Persistent
    private String answer1 = null;
    @Persistent
    private String selectedQuestion2 = null;
    @Persistent
    private String answer2 = null;
    @Persistent
    //private Date date_added = null;
    private String date_added = null;
     @Persistent
     //private Date last_modified = null;
     private String last_modified = null;

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

    public String getSelectedQuestion1() {
        return selectedQuestion1;
    }

    public void setSelectedQuestion1(String selectedQuestion1) {
        this.selectedQuestion1 = selectedQuestion1;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getSelectedQuestion2() {
        return selectedQuestion2;
    }

    public void setSelectedQuestion2(String selectedQuestion2) {
        this.selectedQuestion2 = selectedQuestion2;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    /*
    public Date getDate_added() {
    return date_added;
    }
    public void setDate_added(Date date_added) {
    this.date_added = date_added;
    }
    public Date getLast_modified() {
    return last_modified;
    }
    public void setLast_modified(Date last_modified) {
    this.last_modified = last_modified;
    }
     */
    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }
    
    
    @Override
    public String toString() {
        return "UserQuestion{" + "id=" + id + ", users_id=" + users_id + ", selectedQuestion1=" + selectedQuestion1 + ", answer1=" + answer1 + ", selectedQuestion2=" + selectedQuestion2 + ", answer2=" + answer2 + ", date_added=" + date_added + ", last_modified=" + last_modified + '}';
    }
    
    
}
