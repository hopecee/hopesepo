/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.support.node;

/**
 *
 * @author hope
 */
import java.io.Serializable;
//import org.springframework.data.neo4j.support.Neo4jTemplate;

//import com.byteslounge.cdi.bean.Service;

//@Singleton
//@Startup
public class GraphDataBaseProducer implements Serializable {
private static final long serialVersionUID = 2878618702953550593L;
 
  //@Inject
  //private Test test;
 //public static final String NEO4J_NAME = "java:/eis/Neo4j"; 
 public static final String DATABASE_PATH = "data/graph.db"; 
    
  
      // @Resource(mappedName = NEO4J_NAME)
    //@Inject
    //Neo4JConnectionFactory neo4jConnectionFactory;
//@Inject
 //   private Neo4JConnectionTemplate template;
    //@PostConstruct
   //public void init() {      
   // }
  /*  
    //@Produces
    //@ExtensionManaged
    //@QualifierType(QualifierType.ServiceType.GraphDataBaseProducer)
    public GraphDatabaseService createGraphDatabase()  {
        GraphDatabaseService graphDatabase = new EmbeddedGraphDatabase(DATABASE_PATH);
        System.out.println("*** GraphDatabase created *** : "+DATABASE_PATH);

//return neo4jConnectionFactory.getConnection();
        return  graphDatabase;
    }

    public void createGraphDatabase() {
      try {
          String db = neo4jConnectionFactory.getConnection().createNode().getGraphDatabase().toString();
             System.out.println("*** GraphDatabase = "+db);
       //neo4jConnectionFactory.getConnection().
      } catch (ResourceException ex) {
          Logger.getLogger(GraphDBServiceProducer.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      
        GraphDatabaseService graphDb = new EmbeddedGraphDatabase("data/graph.db");


        Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
        registerShutdownHook(graphDb); 

        System.out.println("nodeIndex created = true" + nodeIndex.getName());
 Neo4jHelper df;
     }
 
 
  */   
    
     
       
  
 /*   
   public Neo4JConnectionFactory getConnectionFactory() {
return neo4jConnectionFactory;
}

public void setConnectionFactory(Neo4JConnectionFactory neo4jConnectionFactory) {
this.neo4jConnectionFactory = neo4jConnectionFactory;
}

public GraphDBServiceController() throws ResourceException {
initialize();
}

private void initialize() throws ResourceException {
Node referenceNode = neo4jConnectionFactory.getConnection().getReferenceNode();
System.out.println("*** Reference Node ***" + referenceNode.getId());
}  
    
*/    
    
}

