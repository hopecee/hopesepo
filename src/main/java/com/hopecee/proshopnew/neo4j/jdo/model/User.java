/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 *
 * @author hope
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "false", detachable = "true")
/*
 @FetchGroup(name = "friendshipsGroup", members = {
 @Persistent(name = "friendshipsTO"),
 @Persistent(name = "friendshipsFROM")})
 */
//@Unique(name="MY_COMPOSITE_IDX", members={"name"})
public class User implements Serializable {

    private static final long serialVersionUID = 1233766699444710651L;
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    // private 
    long id;
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
    private String usersDor = null;
    @Persistent
    private String last_modified = null;
    @Persistent
    private String usersDob = null;
    @Persistent(defaultFetchGroup = "true", mappedBy = "userId")
    // @Element(dependent = "true") //can not exist without parent
    private Set<UserFriendship> friendshipsTO = new HashSet<>();
    //@Persistent(defaultFetchGroup = "true", mappedBy = "friendId")
    //private Set<UserFriendship> friendshipsFROM = new HashSet<>();

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

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

    public Set<UserFriendship> getFriendshipsTO() {
        return friendshipsTO;
    }

    public void setFriendshipsTO(Set<UserFriendship> friendshipsTO) {
        this.friendshipsTO = friendshipsTO;
    }
/*
    public Set<UserFriendship> getFriendshipsFROM() {
        return friendshipsFROM;
    }

    public void setFriendshipsFROM(Set<UserFriendship> friendshipsFROM) {
        this.friendshipsFROM = friendshipsFROM;
    }
*/
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
        return "User{" + "id=" + id + ", name=" + name + ", usersName=" + usersName + ", usersFirstname=" + usersFirstname + ", usersLastname=" + usersLastname + ", usersStatus=" + usersStatus + ", usersDor=" + usersDor + ", last_modified=" + last_modified + ", usersDob=" + usersDob + ", friendshipsTO=" + friendshipsTO + '}';
    }

    //========Extra methods============
    public void addFriendshipsTO(UserFriendship friendshipTO) {
        friendshipsTO.add(friendshipTO);
    }

    public void removeFriendshipsTO(UserFriendship friendshipTO) {
        friendshipsTO.remove(friendshipTO);
    }
/*
    public void addFriendshipsFROM(UserFriendship friendshipFROM) {
        friendshipsFROM.add(friendshipFROM);
    }

    public void removeFriendshipsFROM(UserFriendship friendshipFROM) {
        friendshipsFROM.remove(friendshipFROM);
    }
*/
    public int getNumberOfFriendshipsTO() {
        return friendshipsTO.size();
    }

   
    /**
     * Primary-Key for User. Made up of user and friend.
     */
    /*  public static class PK implements Serializable {

     private static final long serialVersionUID = -7627839657086827484L;
     public long id;

     public PK() {
     }

     public PK(String str) {
     StringTokenizer token = new StringTokenizer(str, "::");
     token.nextToken(); // Class name
     this.id = Long.valueOf(token.nextToken());
     }

     @Override
     public String toString() {
     return User.class.getName() + "::" + String.valueOf(this.id);
     }

     @Override
     public int hashCode() {
     int hash = 3;
     hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
     return hash;
     }

     @Override
     public boolean equals(Object obj) {
     if (obj == null) {
     return false;
     }
     if (getClass() != obj.getClass()) {
     return false;
     }
     final PK other = (PK) obj;
     if (this.id != other.id) {
     return false;
     }
     return true;
     }
     }
    
     */
}
