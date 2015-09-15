/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.jpa.services;

import com.hopecee.proshopnew.servlets.TuJoinEditorServlet;
import com.hopecee.proshopnew.util.CryptoService;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.idm.api.IdentitySearchCriteria;
import javax.enterprise.context.RequestScoped;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.api.Attribute;
import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.Role;
import org.picketlink.idm.api.RoleManager;
import org.picketlink.idm.api.RoleType;
import org.picketlink.idm.api.UnsupportedCriterium;
import org.picketlink.idm.api.User;
import org.picketlink.idm.common.exception.FeatureNotSupportedException;
import org.picketlink.idm.common.exception.IdentityException;
import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.SimpleAttribute;

/**
 *
 * @author hope
 */
@Named
//@Model
//@ViewScoped
//@SessionScoped
@RequestScoped
//@ConversationScoped  
public class UserJpaService implements Serializable {

    private static final long serialVersionUID = -8461206544493480767L;
    //@Inject Identity identity;
    @Inject
    IdentitySession identitySession;
    @Inject
    CryptoService cryptoService;

    public void createUser(String usersEmailAddress) throws IdentityException {
        User user = identitySession.getPersistenceManager().createUser(usersEmailAddress);
        //System.out.println("User w: " + identitySession.getPersistenceManager().findUser(usersEmailAddress));
    }

    public void updateUserPassword(String usersEmailAddress, String usersPassword) {
        try {
            // System.out.println("User : " + usersEmailAddress);
            User user = identitySession.getPersistenceManager().findUser(usersEmailAddress);
            //System.out.println("User : " + user);
            //CryptoService cryptoService = new CryptoService();
            String generatedSecuredPasswordHash = cryptoService.generateStorngPasswordHash(usersPassword);
            // System.out.println(generatedSecuredPasswordHash);
             PasswordCredential credential = new PasswordCredential(usersPassword);
           
             System.out.println("credential.getEncodedValue() : "+credential.getEncodedValue());
               
            identitySession.getAttributesManager().updatePassword(user, generatedSecuredPasswordHash
                  // credential.getEncodedValue().toString() );
            // credential.getValue()
                     );
 
        } catch (IdentityException ex) {
            Logger.getLogger(UserJpaService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserJpaService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(UserJpaService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<User> findAll() throws IdentityException, UnsupportedCriterium {
        IdentitySearchCriteria criteria = identitySession.getPersistenceManager().createIdentitySearchCriteria();
        List<User> results = (List<User>) identitySession.getPersistenceManager().findUser(criteria.page(0, 100));
        return results;
    }

    public void deleteUser(String usersEmailAddress) throws IdentityException {
        identitySession.getPersistenceManager().removeUser(usersEmailAddress, true);
        // System.out.println("User (" + usersEmailAddress + "): Deleted.");
    }

    public void createUserAttribute(String usersEmailAddress, String attrName, String attrValue) throws IdentityException {
        // System.out.println("User (" + usersEmailAddress + "): Deleted.d");
        User user = identitySession.getPersistenceManager().findUser(usersEmailAddress);
        //System.out.println("User (" + user + "): Deleted.");
        identitySession.getAttributesManager().updateAttributes(user,
                new Attribute[]{new SimpleAttribute(attrName, attrValue)});
    }

    public void deleteUserAttribute(String usersEmailAddress, String[] attrNames) throws IdentityException {
        identitySession.getAttributesManager().removeAttributes(usersEmailAddress, attrNames);
    }

    public void createGroup(String groupName, String groupType) throws IdentityException {
        identitySession.getPersistenceManager().createGroup(groupName, groupType);

    }

    public void deleteGroup(String groupName, boolean force) throws IdentityException {
        identitySession.getPersistenceManager().removeGroup(groupName, force);

    }
 public void createRoleType(String roleName) throws FeatureNotSupportedException, IdentityException {
        identitySession.getRoleManager().createRoleType(roleName);
   }
  public void deleteRoleType(String roleName) throws FeatureNotSupportedException, IdentityException {
        identitySession.getRoleManager().removeRoleType(roleName);
   }
    public void createRole(String roleName, String usersEmailAddress, String groupName,String groupType) throws FeatureNotSupportedException, IdentityException {
        // RoleManager roleManager = identitySession.getRoleManager();
        //RoleType adminRT = identitySession.getRoleManager().createRoleType("staff");
        identitySession.getRoleManager().createRole(
                identitySession.getRoleManager().getRoleType(roleName),
                identitySession.getPersistenceManager().findUser(usersEmailAddress),
                identitySession.getPersistenceManager().findGroup(groupName, groupType));
    }
     public void deleteRole(String roleName, String usersEmailAddress, String groupName,String groupType) throws FeatureNotSupportedException, IdentityException {
        // RoleManager roleManager = identitySession.getRoleManager();
        // identitySession.getRelationshipManager().associateGroups(null, null);
        //RoleType adminRT = identitySession.getRoleManager().createRoleType("staff");
        identitySession.getRoleManager().removeRole(
                identitySession.getRoleManager().getRoleType(roleName),
                identitySession.getPersistenceManager().findUser(usersEmailAddress),
                identitySession.getPersistenceManager().findGroup(groupName, groupType));
    }
     public void associateUser( String usersEmailAddress, String groupName,String groupType) throws FeatureNotSupportedException, IdentityException {
        // RoleManager roleManager = identitySession.getRoleManager();
        // identitySession.getRelationshipManager().associateGroups(null, null);
        //RoleType adminRT = identitySession.getRoleManager().createRoleType("staff");
       User user = identitySession.getPersistenceManager().findUser(usersEmailAddress);
           
      // System.out.println("identity.getUser() : "+identity.);
       
               identitySession.getRelationshipManager().associateUser(
                identitySession.getPersistenceManager().findGroup(groupName, groupType), 
                identitySession.getPersistenceManager().findUser(usersEmailAddress));
         }
}
