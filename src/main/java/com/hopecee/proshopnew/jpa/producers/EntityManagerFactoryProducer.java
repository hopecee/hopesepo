package com.hopecee.proshopnew.jpa.producers;



import java.io.Serializable;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import org.jboss.solder.core.ExtensionManaged;

/**
 * @author Shane Bryzak
 */
//@Named  
//@ViewScoped 
//@Singleton 

public class EntityManagerFactoryProducer //implements Serializable  
{  
  //private static final long serialVersionUID = -2825518020404347295L; 

  //@Resource(mappedName = "java:EntityManagerFactory")
    //@PersistenceUnit
    //@RequestScoped
    //@Produces
    //@ExtensionManaged
    //@ApplicationScoped
    //EntityManagerFactory emf;

 
   //@SuppressWarnings("unused")
  
  @PersistenceUnit(unitName = "PU",name = "persistence/PU")
  private static EntityManagerFactory emf;
 //private static EntityManagerFactory factory;
 
    @ExtensionManaged
    //@Produces
    @ApplicationScoped
    @EntityManagerFactoryType
    public EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            System.out.println(">>>> emf: null");
            emf = Persistence.createEntityManagerFactory("PU");
        }
        return emf;
    }

    
    
  
   
    
    
/* 

    private static EntityManagerFactory factory;  
   
    @Produces   
    public EntityManagerFactory getEntityManagerFactory() {  
        if (factory == null) {  
            factory = Persistence.createEntityManagerFactory("PU");  
        
        }  
        return factory;  
    }  
  
    
    @Produces  
   // @PartsDatabase 
    @ExtensionManaged
    @ConversationScoped  
    public EntityManager produceEntityManager() {  
        return getEntityManagerFactory().createEntityManager();  
    }  
   */
  /*  
     
   private EntityManager entityManager;  
   
   @Produces  
   @ExtensionManaged
    @PersistenceUnit  
   EntityManagerFactory emf; 
     
   
   @PostConstruct  
   public void init(){  
     entityManager = emf.createEntityManager();  
   }  
  
   
   @Produces  
   //@ViewScopedEntityManager 
   @ExtensionManaged
   @ConversationScoped 
   public EntityManager produceEntityManager()  
   {  
   // entityManager = emf.createEntityManager();
      return entityManager;  
   }     
   
   / * create ViewScopedEntityManager if u want to use as ViewScoped
     @Qualifier  
    @Target({ TYPE, METHOD, PARAMETER, FIELD })  
    @Retention(RUNTIME)  
    @Documented  
    public @interface ViewScopedEntityManager  
    {  
      
    } 
    * use as follows
    @Inject  
@ViewScopedEntityManager  
EntityManager entityManager;
     */
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
  
  
}
