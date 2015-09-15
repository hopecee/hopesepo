/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.jpa.actions;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jboss.seam.security.Identity;
import org.jboss.seam.transaction.Transactional;
import org.picketlink.idm.api.Group;
import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.User;
import org.picketlink.idm.impl.api.model.SimpleGroup;

/**
 *
 * @author hope
 */
public @Model class MemberRegistration {

    @Inject
    Identity identity;
    @Inject
    IdentitySession identitySession;

     
    
   
    @Inject
   EntityManager entityManager;
    //@Inject
   // PersistenceManagerFactory pmf;
    //@PersistenceContext(unitName = "PU") 
    //EntityManager entityManager;
    // PersistenceManagerFactory pmf =
       //     JDOHelper.getPersistenceManagerFactory("Neo4j_PU");

     
    @Transactional
    public void register() throws Exception {

        
       
        
      
       // PersistenceManager pm = identitySession.getPersistenceManager();
        //pm.findUser("testuser");
        
        User user = identitySession.getPersistenceManager().findUser("demo4");
        //identitySession.getAttributesManager().addAttribute(user.getId(), "colour","yellow");
        Group group = new  SimpleGroup("Head Office","GROUP");
        //identitySession.getRoleManager().createRole("manager", "Hope1", group.getKey());
        //identitySession.getPersistenceManager().createGroup("Usher", "GROUP");
        
         
        
         String  originalPassword = "Hoppass";
        
        String generatedSecuredPasswordHash = generateStorngPasswordHash(originalPassword);
        System.out.println(generatedSecuredPasswordHash);
   
        
       // PasswordCredential password =new PasswordCredential("Hoppass");
        identitySession.getAttributesManager().updatePassword(user, generatedSecuredPasswordHash);
     
        
       // boolean  v= identitySession.getAttributesManager().validatePassword(user, password.getEncodedValue().toString());
       // System.out.println("validatePassword = : " +v);
       
          

       //System.out.println(" *** shutdowfff *** " + getClass().getResource("/keystore").getPath().toString());
  
          
        // String  originalPassword = "password";
        // generatedSecuredPasswordHash = generateStorngPasswordHash(originalPassword);
        // System.out.println(generatedSecuredPasswordHash);

        String newPassword = "Hoppass";
        boolean matched = validatePassword(newPassword, generatedSecuredPasswordHash);
        System.out.println("matched = " + matched);
        System.out.println("matched = " + matched);
        System.out.println("matched = " + matched);

        //matched = validatePassword("password1", generatedSecuredPasswordHash);
        //System.out.println(matched);

        
        
        
         
         
         
         
    /*     
         
          // Create an EntityManagerFactory for this "persistence-unit"
        // See the file "META-INF/persistence.xml"
       // EntityManagerFactory emf = Persistence.createEntityManagerFactory("Neo4j_PU");
//PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Neo4j_PU");
    
javax.jdo.PersistenceManager pm = pmf.getPersistenceManager();


        System.out.println("DataNucleus Tutorial with JDO");
        System.out.println("=============================");

        // Persistence of a Product and a Book.
        Transaction tx=pm.currentTransaction();
try
{
    
    
   tx.begin();
    Inventory inv = new Inventory("My Inventory2");
    Product product = new Product("Sony Discman2", "A standard discman from Sony2", 49.99);
    inv.getProducts().add(product);
    pm.makePersistent(inv);
    tx.commit();
           // inventoryId = pm.getObjectId(inv);
           // System.out.println("Inventory, Product and Book have been persisted");
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        System.out.println("");
*/        
    
   ///=================== 
      
 /*
 javax.jdo.PersistenceManager
       // pm = pmf.getPersistenceManager();
pm =  pmf.getPersistenceManager();
 Transaction 
        tx = pm.currentTransaction();
try
{
    tx.begin();

    Query q = pm.newQuery("SELECT FROM " + Product.class.getName() + 
                          " WHERE price < 150.00 ORDER BY price ASC");
    List<Product> products = (List<Product>)q.execute();
    Iterator<Product> iter = products.iterator();
    while (iter.hasNext())
    {
        Product p = iter.next();
 System.out.println("Product Name        : "+p.getName());
 System.out.println("Product Description : "+p.getDescription());
        
    }

    tx.commit();
}
finally
{
    if (tx.isActive())
    {
        tx.rollback();
    }

    pm.close();
}




// javax.jdo.PersistenceManager
       // pm = pmf.getPersistenceManager();
pm =  pmf.getPersistenceManager();
 // Transaction 
        tx = pm.currentTransaction();

        try
{
    tx.begin();

    Query q = pm.newQuery("SELECT FROM " + Product.class.getName() + 
                          " WHERE price < 150.00 ORDER BY price ASC");
    List<Product> products = (List<Product>)q.execute();
    Iterator<Product> iter = products.iterator();
    while (iter.hasNext())
    {
        Product p = iter.next();
 System.out.println("Product Name        : "+p.getName());
 System.out.println("Product Description : "+p.getDescription());
        
    }

    tx.commit();
}
finally
{
    if (tx.isActive())
    {
        tx.rollback();
    }

    pm.close();
}

 */        
         
         
         
         
         
        
        
        /*
     if (entityManager != null) {
            System.out.println("Entity manager created successfully");
        } else {
            System.out.println("Entity Manager is null !");
        }
     
       // Roles
        IdentityRoleName admin = new IdentityRoleName();
        admin.setName("admin");
        entityManager.persist(admin);
        
        IdentityRoleName manager = new IdentityRoleName();
        manager.setName("manager");
        entityManager.persist(manager);
        
        // Object types
        IdentityObjectType USER = new IdentityObjectType();
        USER.setName("USER");
        entityManager.persist(USER);
        
        IdentityObjectType GROUP = new IdentityObjectType();
        GROUP.setName("GROUP");
        entityManager.persist(GROUP);
        
        // Objects
        IdentityObject user = new IdentityObject();
        user.setName("Hope");
        user.setType(USER);
        entityManager.persist(user);
        
        IdentityObject demo = new IdentityObject();
        demo.setName("demo");
        demo.setType(USER);
        entityManager.persist(demo);
        
        IdentityObject headOffice = new IdentityObject();
        headOffice.setName("Head Office");
        headOffice.setType(GROUP);
        entityManager.persist(headOffice);
        
        IdentityObject foo = new IdentityObject();
        foo.setName("foo");
        foo.setType(USER);
        entityManager.persist(foo);
        
        // Credential types
        IdentityObjectCredentialType PASSWORD = new IdentityObjectCredentialType();
        PASSWORD.setName("PASSWORD");
        entityManager.persist(PASSWORD);
        
        // Credentials
        IdentityObjectCredential userPassword = new IdentityObjectCredential();
        userPassword.setIdentityObject(user);
        userPassword.setType(PASSWORD);
        userPassword.setValue("Hope");
        entityManager.persist(userPassword);
        
        IdentityObjectCredential demoPassword = new IdentityObjectCredential();
        demoPassword.setIdentityObject(demo);
        demoPassword.setType(PASSWORD);
        demoPassword.setValue("demo");
        entityManager.persist(demoPassword);
        
        // Object relationship types
        IdentityObjectRelationshipType jbossIdentityMembership = new IdentityObjectRelationshipType();
        jbossIdentityMembership.setName("JBOSS_IDENTITY_MEMBERSHIP");
        entityManager.persist(jbossIdentityMembership);
        
        IdentityObjectRelationshipType jbossIdentityRole = new IdentityObjectRelationshipType();
        jbossIdentityRole.setName("JBOSS_IDENTITY_ROLE");
        entityManager.persist(jbossIdentityRole);
        
        // Object relationships
        IdentityObjectRelationship demoAdminRole = new IdentityObjectRelationship();
        demoAdminRole.setName("admin");
        demoAdminRole.setRelationshipType(jbossIdentityRole);
        demoAdminRole.setFrom(headOffice);
        demoAdminRole.setTo(demo);
        entityManager.persist(demoAdminRole); 
        
       */ 
        
    }
    
     private static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt().getBytes();
         
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

      private static String getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

        private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
        
        
    
         
    }
  //=========================  
     private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);
         
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();
         
        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    } 
     
   /*  
  IdentitySessionFactory identitySessionFactory = new IdentityConfigurationImpl().
         configure(new File("src/test/resources/example-db-config.xml")).buildIdentitySessionFactory();

      IdentitySession identitySession = identitySessionFactory.createIdentitySession("realm://JBossIdentityExample/SampleRealm");
      identitySession.beginTransaction();

      Collection<User> users = identitySession
         .getPersistenceManager()
         .findUser((IdentitySearchControl[])null);

      String ORGANIZATION = "ORGANIZATION";
      String GROUP = "GROUP";

      User johnDoe = identitySession.getPersistenceManager().createUser("John Doe");
      User alice = identitySession.getPersistenceManager().createUser("Alice");
      User eva = identitySession.getPersistenceManager().createUser("Eva");

      Group acmeOrg = identitySession.getPersistenceManager().createGroup("ACME", ORGANIZATION);

      Group itGroup = identitySession.getPersistenceManager().createGroup("IT", GROUP);
      Group hrGroup = identitySession.getPersistenceManager().createGroup("HR", GROUP);

      identitySession.getRelationshipManager().associateGroups(acmeOrg, itGroup);
      identitySession.getRelationshipManager().associateGroups(acmeOrg, hrGroup);

      identitySession.getRelationshipManager().associateUser(itGroup, johnDoe);
      identitySession.getRelationshipManager().associateUser(itGroup, alice);

      identitySession.getRelationshipManager().associateUser(hrGroup, eva);

      RoleType managerRT = identitySession.getRoleManager().createRoleType("manager");

      identitySession.getRoleManager().createRole(managerRT, johnDoe, itGroup);

      // John belongs to IT and not HR
      assertTrue(identitySession.getRelationshipManager().isAssociated(itGroup, johnDoe));
      assertFalse(identitySession.getRelationshipManager().isAssociated(hrGroup, johnDoe));

      // John is manager of IT and not HR
      assertTrue(identitySession.getRoleManager().hasRole(johnDoe, itGroup, managerRT));
      assertFalse(identitySession.getRoleManager().hasRole(johnDoe, hrGroup, managerRT));

      // Check that binary attribute picture is mapped

      AttributeDescription attributeDescription = identitySession.getAttributesManager().getAttributeDescription(johnDoe, "picture");
      assertNotNull(attributeDescription);
      assertEquals("binary", attributeDescription.getType());


      // Generate random binary data for binary attribute
      Random random = new Random();

      byte[] picture = new byte[5120];
      random.nextBytes(picture);

      identitySession.getAttributesManager().addAttributes(johnDoe, new Attribute[] {new SimpleAttribute("picture", new byte[][]{picture})});

      // Assert picture

      Map<String, Attribute> attributes = identitySession.getAttributesManager().getAttributes(johnDoe);
      assertEquals(1, attributes.keySet().size());
      assertTrue(Arrays.equals((byte[])attributes.get("picture").getValue(), picture));


      User xUser = identitySession.getPersistenceManager().createUser("x");
      Group someGroup = identitySession.getPersistenceManager().createGroup("someGroup", GROUP);

      identitySession.getRoleManager().createRole(managerRT, xUser, someGroup);

      assertEquals(0, identitySession.getRelationshipManager().findAssociatedGroups(xUser, GROUP).size());
      assertEquals(1, identitySession.getRoleManager().findGroupsWithRelatedRole(xUser, GROUP, null).size());
      assertEquals(1, identitySession.getRoleManager().findGroupsWithRelatedRole(xUser, null).size());

      Group otherGroup = identitySession.getPersistenceManager().createGroup("otherGroup", GROUP);

      identitySession.getRelationshipManager().associateUser(otherGroup, xUser);

      assertEquals(1, identitySession.getRelationshipManager().findAssociatedGroups(xUser, GROUP).size());
      assertEquals(1, identitySession.getRoleManager().findGroupsWithRelatedRole(xUser, GROUP, null).size());
      assertEquals(1, identitySession.getRoleManager().findGroupsWithRelatedRole(xUser, null).size());


      identitySession.getTransaction().commit();
      identitySession.close();


   }
*/
     
     
}
