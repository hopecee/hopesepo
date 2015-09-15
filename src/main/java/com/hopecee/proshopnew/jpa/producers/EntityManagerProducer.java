/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.jpa.producers;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author hope
 */
public class EntityManagerProducer {

  private  EntityManagerFactory emf;

    @Produces
    //@RequestScoped
    @ConversationScoped
    public EntityManager produceEntityManager() {
        System.out.println(">>>> EMFProducer: EntityManager created.");
        emf = Persistence.createEntityManagerFactory("PU");

        return emf.createEntityManager();
    }
}
