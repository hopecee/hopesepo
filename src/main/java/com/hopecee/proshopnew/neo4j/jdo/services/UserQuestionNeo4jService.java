/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.neo4j.jdo.services;

import com.hopecee.proshopnew.neo4j.jdo.model.UserQuestion;

/**
 *
 * @author hope
 */

 public interface UserQuestionNeo4jService extends DefaultDAO<UserQuestion, Long> {
  public UserQuestion findByUserId(long id);  
}
