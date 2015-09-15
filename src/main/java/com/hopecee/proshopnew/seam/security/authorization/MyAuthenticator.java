package com.hopecee.proshopnew.seam.security.authorization;

//import com.hopecee.proshopnew.jpa.actions.UserAction;
import com.hopecee.proshopnew.jpa.model.IdentityObject;
import com.hopecee.proshopnew.jpa.producers.ViewScopedEntityManager;
import com.hopecee.proshopnew.seam.messages.DefaultBundleKey;
import com.hopecee.proshopnew.util.CryptoService;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.jboss.logging.Logger;
import org.jboss.seam.international.status.Messages;

import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.management.IdmAuthenticator;
import org.jboss.seam.security.management.PasswordHash;
import org.jboss.seam.security.management.PasswordHashEncoder;
import org.jboss.seam.security.management.picketlink.JpaIdentityStore;
import org.jboss.seam.transaction.TransactionPropagation;
import org.jboss.seam.transaction.TransactionServletListener;
import org.jboss.seam.transaction.Transactional;
import org.picketlink.idm.api.AttributesManager;
import org.picketlink.idm.api.Credential;
//import org.jboss.seam.transaction.Transactional;
import org.picketlink.idm.api.Group;
import org.picketlink.idm.api.IdentitySearchCriteria;
import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.PersistenceManager;
import org.picketlink.idm.api.Role;
import org.picketlink.idm.api.RoleManager;
import org.picketlink.idm.api.RoleType;
import org.picketlink.idm.api.UnsupportedCriterium;
//import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.User;
import org.picketlink.idm.common.exception.FeatureNotSupportedException;
import org.picketlink.idm.common.exception.IdentityException;
import org.picketlink.idm.impl.api.IdentitySearchCriteriaImpl;
import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.model.SimpleUser;
import org.picketlink.idm.impl.api.session.IdentitySessionImpl;

/**
 * @author Shane Bryzak
 */
@Named(value = "myAuthenticator")
@Transactional
public class MyAuthenticator extends BaseAuthenticator implements Authenticator {

    private final Logger log = Logger.getLogger(TransactionServletListener.class);
    @Inject
    private Identity identity;
    @Inject
    private Credentials credentials;
   // @Inject
   // private Messages messages;
    //@Inject
    //UserAction  userAction ;
    @Inject
    IdentitySession identitySession;
   // @Inject
   // private CryptoService cryptoService;
    // @Inject
    //@ViewScopedEntityManager
    // private EntityManager entityManager;
   // Collection<Role> roles;

    @Override
    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void authenticate() {

        if (identitySession != null) {
            User u = new SimpleUser(credentials.getUsername());

            try {
                
                
                
             //  PasswordHash passwordHash =new PasswordHash();
              // passwordHash.setHashAlgorithm("PBKDF2WithHmacSHA1");
              // passwordHash.setSaltLength(16);
               
         // PasswordHashEncoder passwordHashEncoder =new      PasswordHashEncoder();
         // passwordHashEncoder.setPasswordHash(null); 
         // passwordHashEncoder.setPasswordIterations(10);
              //          passwordHashEncoder.encode(null, null);
                        
                boolean success = identitySession.getAttributesManager().validateCredentials(
                        //u, new Credential[]{credentials.getCredential()});
  u, new Credential[]{credentials.getCredential()}
                        );
  
                if (success) {
                    Collection<RoleType> roleTypes = identitySession.getRoleManager()
                            .findUserRoleTypes(u);

                    for (RoleType roleType : roleTypes) {
                        for (Role role : identitySession.getRoleManager().findRoles(u, roleType)) {
                            identity.addRole(role.getRoleType().getName(),
                                    role.getGroup().getName(), role.getGroup().getGroupType());
                        }
                    }

                    for (Group g : identitySession.getRelationshipManager().findAssociatedGroups(u)) {
                        identity.addGroup(g.getName(), g.getGroupType());
                    }

                    setUser(u);
                    setStatus(AuthenticationStatus.SUCCESS);
                    return;
                } else {
                    log.info("Authentication failed for user '" + credentials.getUsername() + "'");
                }
            } catch (IdentityException ex) {
                log.error("Authentication error", ex);
            } catch (FeatureNotSupportedException ex) {
                log.error("Authentication error", ex);
            }
        }

        setStatus(AuthenticationStatus.FAILURE);
    }
}
