/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.seam;

//import org.jboss.solder.core.Veto;
//import com.hopecee.proshopnew.seam.security.Admin;
//import com.hopecee.proshopnew.spring.SpringBean1;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
//import org.eclipse.jetty.plus.jndi.NamingEntry;
//import org.jboss.seam.faces.context.RenderScoped;
//import org.springframework.transaction.annotation.Transactional;

//import org.jboss.seam.solder.core.Veto;
/**
 *
 * @author hope
 */
//@Veto
//@Admin
@Named
@SessionScoped
//@RenderScoped
@PersistenceContext(unitName = "PU", name = "persistence/PU")
public class SeamBean implements Serializable {

    private static final long serialVersionUID = 3723552335163650583L;
    // @Inject
    // private SpringBean1 springBean1;
    @Inject
    private SeamBean2 seamBean2;
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String getWelcome() throws NamingException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        // NamingEntry.bindToENC(Transaction.USER_TRANSACTION,Transaction.USER_TRANSACTION, Transaction.class);


        /*
        
         Enumeration<String>  results = null;
         InitialContext initContext = new InitialContext();
         // Object beanMan = initContext.lookup("java:comp/UserTransaction");
         results =  (Enumeration<String>) initContext.getEnvironment().keys();
          
         List list =  Collections.list(results) ;
         Iterator i=  list.iterator();
         while (i.hasNext()) {
            
         System.out.println("ooo : "+i.next().getClass().getSimpleName().toString());
         }
                 
         InitialContext ictx = new InitialContext();
         javax.transaction.TransactionManager ctx1 = (javax.transaction.TransactionManager)ictx.lookup("java:comp/env/TransactionManager");
         System. out.println("java: = " + ctx1.getClass().getName());
         Object ctx2 = ictx.lookup("java:comp/UserTransaction");
         System. out.println("java: = " + ctx2.getClass().getName());
               
         Context ctx = (Context) ictx.lookup("java:comp/env");        
         NamingEnumeration en = ctx.listBindings("");
         while (en.hasMore()) {
         Binding b = (Binding) en.next();
         int indent = 0;
         char[] tabs = new char[indent];
         Arrays.fill(tabs, '\t');
         System. out.println("== | "+new String(tabs) + b.getName() + " = " + b.getClassName());  
         }    
        
      
        
         //ctx1.begin();
        
         System. out.println("ctx1.begin() | ");  
            
         // ctx1.commit();
        
         //System.out.println("beanMan: "+beanMan.toString());
       
         // NamingEnumeration bindings = initContext.list("ou=People");
          
         //while (bindings.hasMore()) {
         //Binding bd = (Binding)bindings.next();
         // System.out.println(bd.getName() + ": " + bd.getObject());

         new JNDITree().printJNDITree("");
         // setEnv();
         * 
         */
        return "Seam CDI22 : + " + seamBean2.getWelcome();
    }
    private Context context = null;
    /*
     public static void main(String[] args) throws Exception {
     new JNDITree().printJNDITree("");
     System.out.println("DONE");
     }

     public JNDITree() throws NamingException {
     setEnv();
     }

     Please modify this method or comment and use jndi.properties
     */

    public void setEnv() throws NamingException {
        Hashtable env = new Hashtable();
//OC4J
//  env.put(Context.INITIAL_CONTEXT_FACTORY, "com.evermind.server.rmi.RMIInitialContextFactory");
//  env.put(Context.PROVIDER_URL, "ormi://172.16.x.x:12404");
//  env.put(Context.SECURITY_PRINCIPAL, "admin");
//  env.put(Context.SECURITY_CREDENTIALS, "welcome");
//JBOSS
//  env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
//  env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
//  env.put(Context.PROVIDER_URL, "jnp://172.16.x.x:1099");
//WEBLOGIC
//  env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
//  env.put(Context.PROVIDER_URL, "t3://172.16.x.x:7001");


        context = new InitialContext(env);
    }

    public void printJNDITree(String ct) {
        try {
            printNE(context.list(ct), ct);
        } catch (NamingException e) {
            //ignore leaf node exception
        }
    }

    private void printNE(NamingEnumeration ne, String parentctx) throws NamingException {
        while (ne.hasMoreElements()) {
            NameClassPair next = (NameClassPair) ne.nextElement();
            printEntry(next);
            increaseIndent();
            printJNDITree((parentctx.length() == 0) ? next.getName() : parentctx + "/" + next.getName());
            decreaseIndent();
        }
    }

    private void printEntry(NameClassPair next) {
        System.out.println(printIndent() + "-->" + next);
    }
    private int indentLevel = 0;

    private void increaseIndent() {
        indentLevel += 4;
    }

    private void decreaseIndent() {
        indentLevel -= 4;
    }

    private String printIndent() {
        StringBuffer buf = new StringBuffer(indentLevel);
        for (int i = 0; i < indentLevel; i++) {
            buf.append(" ");
        }
        return buf.toString();
    }
}
