/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.support.node;

/**
 *
 * @author hope
 */

//import org.neo4j.graphdb.*;
//import org.neo4j.graphdb.index.IndexManager;
//import org.neo4j.tooling.GlobalGraphOperations;
//import org.springframework.data.neo4j.support.Neo4jTemplate;

import java.util.HashMap;
import java.util.Map;
import javax.resource.ResourceException;

public class Neo4jHelper {
/*
     public  void cleanDb(GraphDatabaseService graphDatabaseService) {
        cleanDb(graphDatabaseService, false);
    }

    public  void cleanDb( GraphDatabaseService graphDatabaseService, boolean includeReferenceNode ) {
        Transaction tx = graphDatabaseService.beginTx();
        try {
            removeNodes(graphDatabaseService, includeReferenceNode);
            clearIndex(graphDatabaseService);
            tx.success();
        } catch(Throwable t) {
            tx.failure();
          //  throw new org.springframework.data.neo4j.core.UncategorizedGraphStoreException("Error cleaning database ",t);
        } finally {
            tx.finish();
        }
    }


    public  void dumpDb(GraphDatabaseService gds) {
        final GlobalGraphOperations globalGraphOperations = GlobalGraphOperations.at(gds);
        for (Node node : globalGraphOperations.getAllNodes()) {
            System.out.println(dump(node));
        }
        for (Node node : globalGraphOperations.getAllNodes()) {
            for (Relationship rel : node.getRelationships(Direction.OUTGOING)) {
                System.out.println(node +"-[:"+rel.getType().name() +" "+dump(rel)+"]->"+rel.getEndNode());
            }
        }
    }

    private  String dump(PropertyContainer pc) {
        final long id = pc instanceof Node ? ((Node) pc).getId() : ((Relationship) pc).getId();
        try {
            Map<String, Object> props = new HashMap<String, Object>();
            for (String prop : pc.getPropertyKeys()) {
                props.put(prop, pc.getProperty(prop));
            }
            return String.format("(%d) %s ", id, props);
        } catch (Exception e) {
            return "(" + id + ") " + e.getMessage();
        }
    }


    private  void removeNodes(GraphDatabaseService graphDatabaseService, boolean includeReferenceNode) {
        final GlobalGraphOperations globalGraphOperations = GlobalGraphOperations.at(graphDatabaseService);
        for (Node node : globalGraphOperations.getAllNodes()) {
            for (Relationship rel : node.getRelationships(Direction.OUTGOING)) {
                rel.delete();
            }
        }
        for (Node node : globalGraphOperations.getAllNodes()) {
            //if (includeReferenceNode || !graphDatabaseService.getReferenceNode().equals(node)) {
             if (includeReferenceNode //|| !graphDatabaseService.getReferenceNode().equals(node)
                     ) {
               node.delete();
            }
        }
    }

    private void clearIndex(GraphDatabaseService gds) {
        IndexManager indexManager = gds.index();
        for (String ix : indexManager.nodeIndexNames()) {
            indexManager.forNodes(ix).delete();
        }
        for (String ix : indexManager.relationshipIndexNames()) {
            indexManager.forRelationships(ix).delete();
        }
    }
    */
   /* 
    public  void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
                                 System.out.println(" *** graphDb shutdown*** ");
			}
		});
	}
    */
    /*
     public  void graphDbShutdown( GraphDatabaseService graphDb) {
		
				graphDb.shutdown();
                                 System.out.println(" *** graphDb shutdown*** ");
			
		
	}
     */
     
}
