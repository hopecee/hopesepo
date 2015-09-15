/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.jpa.actions;

/**
 *
 * @author hope
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.byteslounge.cdi.bean.Service;

//@WebServlet(name = "testServlet", urlPatterns = {"/"})
public class TestServlet extends HttpServlet {

  private static final long serialVersionUID = 2638127270022516617L;
  
  @Inject
  private Test test;
  
  @Override
  protected void doGet(HttpServletRequest request,
    HttpServletResponse response)
    throws ServletException, IOException {
    
    int a = 2;
    int b = 3;
    test.text();
    PrintWriter out = response.getWriter();
    out.println("Hello World: " );
    
    out.close();
  }

}

