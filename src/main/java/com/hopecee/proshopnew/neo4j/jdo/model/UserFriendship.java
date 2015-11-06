/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.identity.LongIdentity;

/**
 *
 * @author hope
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, cacheable = "false", detachable = "true",
        objectIdClass = UserFriendship.PK.class)
//@Unique(name="MY_COMPOSITE_IDX", members={"name"})
public class UserFriendship implements Serializable {

    private static final long serialVersionUID = -5595467564377951018L;
    @PrimaryKey
    private long userId; // PK
    @PrimaryKey
    private long friendId; // PK
    @Persistent
    private String relationStart = null;
    @Persistent
    private String relationLevel = null;
    @Persistent
    private String meetingLocation = null;
    @Persistent
    private int relationStatus = 0;

    public UserFriendship() {
    }

    public UserFriendship(long userId, long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    public String getRelationStart() {
        return relationStart;
    }

    public void setRelationStart(String relationStart) {
        this.relationStart = relationStart;
    }

    public String getRelationLevel() {
        return relationLevel;
    }

    public void setRelationLevel(String relationLevel) {
        this.relationLevel = relationLevel;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public int getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(int relationStatus) {
        this.relationStatus = relationStatus;
    }

    /**
     * Primary-Key for UserFriendship. Made up of user and friend.
     */
    /*
     * public static class PK implements Serializable {

     private static final long serialVersionUID = -2090216333556951760L;
     public long userId; // Use same name as field above
     public long friendId; // Use same name as field above

     public PK() {
     }

     public PK(String s) {
     StringTokenizer token = new StringTokenizer(s, "::");
     this.userId = Long.parseLong(token.nextToken());
     this.friendId = Long.parseLong(token.nextToken());
     }

     @Override
     public String toString() {
     return (String.valueOf(this.userId) + "::" + String.valueOf(this.friendId));
     }

     @Override
     public int hashCode() {
     int hash = 3;
     hash = 79 * hash + (int) (this.userId ^ (this.userId >>> 32));
     hash = 79 * hash + (int) (this.friendId ^ (this.friendId >>> 32));
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
     if (this.userId != other.userId) {
     return false;
     }
     if (this.friendId != other.friendId) {
     return false;
     }
     return true;
     }

       
     }
    
     */
    public static class PK implements Serializable {

        private static final long serialVersionUID = -2090216333556951760L;
        public long friendId;
        public long userId;

        public PK() {
        }

        public PK(String str) {
            StringTokenizer tokeniser = new StringTokenizer(str, "::");
            String token0 = tokeniser.nextToken();
            this.friendId = Long.valueOf(token0).longValue();
            String token1 = tokeniser.nextToken();
            this.userId = Long.valueOf(token1).longValue();
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append(this.friendId).append("::").append(this.userId);
            return str.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PK)) {
                return false;
            }
            PK other = (PK) obj;
            return (this.friendId == other.friendId) && (this.userId == other.userId);
        }

        @Override
        public int hashCode() {
            return (int) this.friendId ^ (int) this.userId;
        }
    }
}
