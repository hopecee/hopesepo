/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.servlets;

import com.google.gson.Gson;
import com.hopecee.proshopnew.events.ExceptionEventBadToken;
import com.hopecee.proshopnew.neo4j.jdo.model.User;
import com.hopecee.proshopnew.neo4j.jdo.model.UserFriendship;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.services.UserFriendshipNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.UserNeo4jService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jdo.JDOFatalUserException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.seam.transaction.ExceptionEventRollback;
import org.picketlink.idm.api.Role;
import org.picketlink.idm.common.exception.IdentityException;
import org.picketlink.idm.impl.api.PasswordCredential;

/**
 *
 * @author hope
 */
@WebServlet(name = "tuFriendServlet", urlPatterns = {"/tuFriendServlet"})
public class TuFriendServlet extends HttpServlet {

    private static final long serialVersionUID = 3045632562463132734L;
    public static final String JSON = "application/json";
    private final String BAD_TOKEN = "BadToken";
    @Inject
    private Event<ExceptionEventRollback> exceptionEvent;
    @Inject
    private Event<ExceptionEventBadToken> exceptionEventBadToken;
    @Inject
    private UserNeo4jService userNeo4jService;
    @Inject
    private UserFriendshipNeo4jService UserFriendshipNeo4jService;
    Map<Object, Object> userMap = new LinkedHashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String token = "";
        try {
            token = req.getAttribute("CheckRequestVerificationToken").toString();
        } catch (NullPointerException e) {
            Logger.getLogger(TuFriendServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        if (token.equals(BAD_TOKEN)) {
            //getBadToken(req, resp);
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
        } else {
            //  String RequestVerificationToken = req.getParameter("RequestVerificationToken");
            System.out.println("DDDDDDDDD = ,,,,,,,,,,,,,,,,,,,,,,,,");
            String method = req.getParameter("method");
            // String searchType = req.getParameter("searchType");

            System.out.println("DDDDDDDDD = : " + method);
            System.out.println("DDDDDDDDD = : ");

            if ("addFriend".equals(method) && true) {

                System.out.println("DDDDDDDDD = : ");

                addFriendship(req, resp);
            }

            if ("searchUser".equals(method) && true) {

                System.out.println("DDDDDDDDD = : ");

                findAllCustomers(req, resp);
            }

        }
    }

    protected void addFriendship(HttpServletRequest req, HttpServletResponse resp) {
        String userNeo4jIdString = req.getParameter("userNeo4jIdString");
        String friendNeo4jIdString = req.getParameter("userId");//userId becomes friendId.

        System.out.println("friendNeo4jIdString : " + friendNeo4jIdString);
        System.out.println("userNeo4jIdString : " + userNeo4jIdString);


        try {
            UserFriendshipNeo4jService.createFriendship(userNeo4jIdString, friendNeo4jIdString);
/*
            List<UserFriendship> friendships = UserFriendshipNeo4jService.findAllTO();
            Iterator<UserFriendship> iterR = friendships.iterator();
            // List list = new ArrayList();
            //int i = 0;
            while (iterR.hasNext()) {
                UserFriendship friendship = iterR.next();//User is now refered as customer.
                System.out.println("==============================");
                System.out.println(friendship);
                System.out.println("==============================");

            }
*/
        } catch (DAOException e) {
            Logger.getLogger(TuFriendServlet.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    protected void findAllCustomers(HttpServletRequest req, HttpServletResponse resp) {

        String searchValue = req.getParameter("searchValue");
        String searchType = req.getParameter("searchType");//TODO NOT NEEDED.
        String userNeo4jIdString = req.getParameter("userNeo4jIdString");

        System.out.println(searchValue);
        System.out.println(searchType);
        System.out.println(userNeo4jIdString);


        userMap.clear(); //Clear First.


        //allCustomersMap.clear();//Clear First.

        List customersArr = new ArrayList();
        customersArr.clear();  //Clear First.

        try {
            userMap.put("isLoggedIn", true);//Check login.


            // allCustomers.add(userMap);



            //================
            List<User> users = userNeo4jService.findAll();
            Iterator<User> iterR = users.iterator();
            // List list = new ArrayList();
            //int i = 0;
            while (iterR.hasNext()) {
                User customer = iterR.next();//User is now refered as customer.

                Map<Object, Object> customersMap = new LinkedHashMap<>();
                customersMap.clear();//Clear First.

                customersMap.put("userId", customer.getId());
                customersMap.put("name", customer.getName());
                customersMap.put("usersName", customer.getUsersName());
                customersMap.put("usersFirstname", customer.getUsersFirstname());
                customersMap.put("usersLastname", customer.getUsersLastname());
                customersMap.put("usersStatus", customer.getUsersStatus());

                customersArr.add(customersMap);


                System.out.println(customer.getId() + " : " + customer.getName() + " : " + customer.getUsersName() + " : " + customer.getUsersStatus());
                if (customer.getFriendshipsTO() != null) {
                    Iterator<UserFriendship> iterRc = customer.getFriendshipsTO().iterator();
                    while (iterRc.hasNext()) {
                        UserFriendship rel = iterRc.next();
                        System.out.println(rel.getUserId() + " : " + rel.getFriendId());

                    }
                }
            }
            /*
             //=======neo4j==============================//
             //convert String to long.
             long userNeo4jId = Long.parseLong(userNeo4jIdString.trim());
             //find Userfriendship  from neo4j DB. 
             Userfriendship user = userNeo4jService.findById(userNeo4jId);

             //  Map<Object, Object> userMap = new LinkedHashMap<>();
             userMap.clear(); //Clear First.

             if (user != null) {
             userMap.put("isLoggedIn", true);//Check login.
             allCustomers.add(userMap);

             Iterator<User> iterR = user.getCustomers().iterator();
             // List list = new ArrayList();
             //int i = 0;
             while (iterR.hasNext()) {
             Userfriendship customer = iterR.next();//User is now refered as customer.

             userMap.put("userId", customer.getId());
             userMap.put("name", customer.getName());
             userMap.put("usersName", customer.getUsersName());
             userMap.put("usersFirstname", customer.getUsersFirstname());
             userMap.put("usersLastname", customer.getUsersLastname());
             userMap.put("usersStatus", customer.getUsersStatus());

             System.out.println("userId : " + customer.getId());
             System.out.println("userName : " + customer.getName());
                    
             allCustomers.add(userMap);
             }

    
             }
             */
            userMap.put("findAllCustomers", customersArr);
            // ArrayList arr = (ArrayList) userMap.get("array");

            // System.out.println("====" + arr);
            // System.out.println("=================================");
           /* for (int i = 0; i < arr.size(); i++) {
             Map<Object, Object> tmpData = (Map<Object, Object>) arr.get(i);
             Set<String> key = (Set<String>) ((Map) tmpData).keySet();

             Iterator<String> it = key.iterator();
             while (it.hasNext()) {
             String hmKey =  it.next();
             Object hmData =  tmpData.get(hmKey);

             System.out.println("Key: " + hmKey + " & Data: " + hmData);
             it.remove(); // avoids a ConcurrentModificationException
             }

             }
             */









            String json = new Gson().toJson(userMap);

            resp.setContentType(JSON);
            resp.getWriter().print(json);

            // throw new IllegalStateException();
    /*
             } catch (IllegalArgumentException | JDOFatalUserException | IllegalStateException | IOException ex) {
             exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
             Logger.getLogger(TuUsersSearchServlet.class.getName()).log(Level.SEVERE, null, ex);
             }
             */
        } catch (IOException ex) {
            Logger.getLogger(TuUsersSearchServlet.class.getName()).log(Level.SEVERE, null, ex);

            /*
             } catch (IllegalArgumentException | JDOFatalUserException | IllegalStateException | IOException ex) {
             exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
             Logger.getLogger(TuUsersSearchServlet.class.getName()).log(Level.SEVERE, null, ex);
             }
             }*/
        }
    }
}