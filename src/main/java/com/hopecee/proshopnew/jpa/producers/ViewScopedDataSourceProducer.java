/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.jpa.producers;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hope
 */
@Named
@ViewScoped
public class ViewScopedDataSourceProducer implements Serializable {

    private static final long serialVersionUID = -2825518020404347295L;
    
  
    //@Inject
    private EntityManagerFactory emf;
    
    private EntityManager entityManager;
/*
    @PostConstruct
    public void init() {
         //System.out.println("emf : "+emf.toString());
        //if (emf == null) { 
             //System.out.println("emf : NULL");
           
          //  emf = Persistence.createEntityManagerFactory("PU");  
       // } 
        entityManager = emf.createEntityManager();
    }
*/
    
    
    

   //@Produces
    @ViewScopedEntityManager
    public EntityManager getEntityManager() {
        entityManager = emf.createEntityManager();
        return entityManager;
    }
  
}
