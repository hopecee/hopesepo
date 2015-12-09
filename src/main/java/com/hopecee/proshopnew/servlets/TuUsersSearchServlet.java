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
import com.hopecee.proshopnew.neo4j.jdo.services.UserNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.util.JDOUtil;
import static com.hopecee.proshopnew.servlets.TuJoinEditorServlet.JSON;
import com.hopecee.proshopnew.util.javacryption.aes.AesCtr;
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
import javax.servlet.http.HttpSession;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.transaction.ExceptionEventRollback;
import org.neo4j.tool.StoreCopyRevert;
import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.Role;
import org.picketlink.idm.common.exception.IdentityException;
import org.picketlink.idm.impl.api.PasswordCredential;

/**
 *
 * @author hope
 */
@WebServlet(name = "tuUsersSearchServlet", urlPatterns = {"/tuUsersSearchServlet"})
public class TuUsersSearchServlet extends HttpServlet {

    private static final long serialVersionUID = 1668280781030491205L;
    public static final String JSON = "application/json";
    private final String BAD_TOKEN = "BadToken";
    @Inject
    private Event<ExceptionEventRollback> exceptionEvent;
    @Inject
    private Event<ExceptionEventBadToken> exceptionEventBadToken;
    @Inject
    private UserNeo4jService userNeo4jService;
    @Inject
    private IdentitySession identitySession;
    @Inject
    private Identity identity;
    @Inject
    private Credentials credentials;
    Map<Object, Object> userMap = new LinkedHashMap<>();
    @Inject
    private StoreCopyRevert storeCopyRevert;
    @Inject
    private AesCtr aesCtr;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        
      
                
        String token = "";
        try {
            token = req.getAttribute("CheckRequestVerificationToken").toString();
        } catch (NullPointerException e) {
            Logger.getLogger(TuUsersSearchServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        if (token.equals(BAD_TOKEN)) {
            //getBadToken(req, resp);
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
        } else {
            //  String RequestVerificationToken = req.getParameter("RequestVerificationToken");
            System.out.println("DDDDDDDDD = ,,,,,,,,,,,,,,,,,,,,,,,,");
            String method = req.getParameter("method");
            String searchType = req.getParameter("searchType");

            System.out.println("DDDDDDDDD = : " + method);
            System.out.println("DDDDDDDDD = : ");

            if ("searchUser".equals(method) && "@tushop.com".equals(searchType)) {

                System.out.println("DDDDDDDDD = : ");
                try {
                    findUser(req, resp);
                } catch (Exception ex) {
                    Logger.getLogger(TuUsersSearchServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if ("searchUser".equals(method) && "all friends".equals(searchType)) {

                System.out.println("DDDDDDDDD = : ");

                findAllCustomers(req, resp);
                //storeCopyRevert();
            }

        }
    }

    
    
    //NOT part of it.
    protected void storeCopyRevert() {
        // Usage: StoryCopy source:version target:version [rel,types,to,ignore] [properties,to,ignore]
        // String path = getServletContext().getRealPath("/data/");
        // System.out.println(">>>> StoreCopyRevert: " + path);

        String[] args = {"data/graph:2.1.0-M01", "data/fixed:2.0.0"};
        try {
            storeCopyRevert.copyRevert(args);
        } catch (Exception ex) {
            Logger.getLogger(TuUsersSearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(">>>> StoreCopyRevert: Converting Neo4j database.");
        System.out.println(">>>> StoreCopyRevert: Converting Neo4j database.");

    }

    protected void findUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userNeo4jIdString = req.getParameter("userSession[userNeo4jIdString]");
        String searchValue = req.getParameter("searchValue");
        String searchType = req.getParameter("searchType");

        System.out.println("hdthh- : " + searchValue);
        System.out.println(searchType);
        //System.out.println(req.getParameter("userSession"));
        if (userNeo4jIdString != null) {
            // for (int i = 0; i < userSession; i++) {
            System.out.print(userNeo4jIdString);
            // }  
        } else {
            System.out.println("Action is null");
        }
        long time = req.getSession().getLastAccessedTime();
 System.out.println("time : "+time);
        try {
            //  Map<Object, Object> userMap = new LinkedHashMap<>();
            userMap.clear(); //Clear First.
System.out.println("identity.isLoggedIn() : "+identity.getUser().getId());

            userMap.put("isLoggedIn", identity.isLoggedIn());//Check login.

            List customersArr = new ArrayList();
            customersArr.clear();  //Clear First.

            Map<Object, Object> customersMap = new LinkedHashMap<>();
            customersMap.clear();//Clear First.

            //=======neo4j==============================//
            //find Userfriendship  from neo4j DB. 
            User user = userNeo4jService.findByUsersName(searchValue);
            if (user != null) {
                boolean friend = false;
                //req.getSession().
                user.getFriendshipsTO().iterator().next().getUserId();
                identity.getUser().getId();
                System.out.println("identity.getUser().getId() : " + identity.getUser().getId());

                customersMap.put("userId", user.getId());
                customersMap.put("name", user.getName());
                customersMap.put("usersName", user.getUsersName());
                customersMap.put("usersFirstname", user.getUsersFirstname());
                customersMap.put("usersLastname", user.getUsersLastname());
                customersMap.put("usersStatus", user.getUsersStatus());
                customersMap.put("friendshipsTO", user.getFriendshipsTO());
                customersMap.put("friendship", friend);
                customersMap.put("numberOfFriendshipsTO", user.getNumberOfFriendshipsTO());

                System.out.println("userId : " + user.getId());
                System.out.println("userName : " + user.getName());
            }

            //customersArr.add(customersMap);

            //userMap.put("findUser", customersArr);
             userMap.put("findUser", customersMap);
            // ArrayList arr = (ArrayList) userMap.get("array");
            aesCtr.encryptMap(req, userMap);//encrypt the Map data.

            String json = new Gson().toJson(userMap);

            resp.setContentType(JSON);
            resp.getWriter().print(json);

            // throw new IllegalStateException();
        } catch (IllegalArgumentException | JDOFatalUserException | IllegalStateException | IOException ex) {
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
            Logger.getLogger(TuUsersSearchServlet.class.getName()).log(Level.SEVERE, null, ex);
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
                customersMap.put("friendshipsTO", customer.getFriendshipsTO());
                customersMap.put("numberOfFriendshipsTO", customer.getNumberOfFriendshipsTO());

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