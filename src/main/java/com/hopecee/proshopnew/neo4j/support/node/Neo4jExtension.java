/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.support.node;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
//import org.neo4j.graphdb.GraphDatabaseService;
//import org.neo4j.graphdb.Node;
//import org.neo4j.graphdb.factory.GraphDatabaseFactory;
//import org.neo4j.graphdb.index.Index;
//import org.neo4j.graphdb.GraphDatabaseService;

/**
 *
 * @author hope
 */
public class Neo4jExtension implements Extension {
/*
    public static final String DATABASE_PATH = "data/graph";
    private GraphDatabaseService graphDb = null;
    //@QualifierType(QualifierType.ServiceType.GraphDataBaseProducer)
    @Inject
    private Neo4jHelper neo4jHelper;

    // Creates GraphDatabase afterBeanDiscovery.
    public void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {
       // GraphDatabaseFactory gdf = new GraphDatabaseFactory();
       // graphDb = gdf.newEmbeddedDatabase(DATABASE_PATH);
       // System.out.println(" *** GraphDatabase created *** : ");



        //createGraphDatabase();
        //createNodeIndex();

        //registerShutdownHook(graphDb);
    }
    */
    /*
     public GraphDatabaseService createGraphDatabase() {
     //GraphDatabaseService graphDatabase = new EmbeddedGraphDatabase(DATABASE_PATH);

     //File f = new File("path/to/file.txt").isFile();

     System.out.println(" *** shutdowfff *** " + getClass().getResource("/keystore").getPath().toString());

 
     GraphDatabaseFactory gdf = new GraphDatabaseFactory();
     //gdf.setIndexProviders(null)
     graphDb = gdf.newEmbeddedDatabase(DATABASE_PATH);

     System.out.println(" *** GraphDatabase created *** : ");
       

        
     return graphDb;
     }
     */
    /*
     public Index<Node> createNodeIndex() {
     Index<Node> nodeIndex = createGraphDatabase().index().forNodes("nodes");
     System.out.println(" *** NodeIndex created *** ");
     //neo4jHelper.
     //    registerShutdownHook(createGraphDatabase());
     // createGraphDatabase().shutdown();
     return nodeIndex;
     }
     */
    // START SNIPPET: shutdownHook

    /*
    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
                System.out.println(" *** graphDb shutdown*** ");

            }
        });
    }
    */
}
