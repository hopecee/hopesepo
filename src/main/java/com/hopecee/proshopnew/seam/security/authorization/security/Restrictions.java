package com.hopecee.proshopnew.seam.security.authorization.security;

import com.hopecee.proshopnew.seam.security.authorization.annotations.Admin;
import com.hopecee.proshopnew.seam.security.authorization.annotations.Foo;
import com.hopecee.proshopnew.seam.security.authorization.annotations.User;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

/**
 * @author Shane Bryzak
 */
public class Restrictions {
    public
    @Secures
    @Admin
    boolean isAdmin(Identity identity) {
        return identity.hasRole("admin", "USERS", "GROUP");
    }

    public
    @Secures
    @Foo(bar = "abc")
    boolean isFooAbc() {
        return true;
    }

    public
    @Secures
    @Foo(bar = "def")
    boolean isFooDef() {
        return false;
    }

    public
    @Secures
    @User
    boolean isUser(Identity identity) {
        return identity.inGroup("USERS", "GROUP");
    }
    
    public @Secures @Foo(bar = "demo") boolean isDemoUser(Identity identity) {
        return identity.hasPermission("foo", "execute");
    }
    
    public @Secures @Foo(bar = "user") boolean isInUserGroup(Identity identity) {
        return identity.hasPermission("bar", "execute");
    }
    
}
