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
    @Inject
    private StoreCopyRevert storeCopyRevert;
    @Inject
    private AesCtr aesCtr;
    
    User user = null;
    Map<Object, Object> userMap = new LinkedHashMap<>();

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
            System.out.println("DDDDDDDDD = : d " + searchType);

            if ("searchUser".equals(method) && ("atTushopCom".equals(searchType) || "emailAddress".equals(searchType))) {
                System.out.println("DDDDDDDDD = : op");

                try {
                    findUser(req, resp);
                } catch (Exception ex) {
                    Logger.getLogger(TuUsersSearchServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if ("searchUser".equals(method) && "allFriends".equals(searchType)) {
                System.out.println("DDDDDDDDD = : u");
                try {
                    findAllCustomers(req, resp);
                } catch (Exception ex) {
                    Logger.getLogger(TuUsersSearchServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        long time = req.getSession().getCreationTime();
        int ctime = req.getSession().getMaxInactiveInterval();
        System.out.println("time : " + time + " : " + ctime);
           
        
       
        try {
        //  Map<Object, Object> userMap = new LinkedHashMap<>();
        userMap.clear(); //Clear First.
//System.out.println("identity.isLoggedIn() : "+identity.getUser().getId());
        System.out.println("isLoggedIn() : " + identity.isLoggedIn());

        userMap.put("isLoggedIn", identity.isLoggedIn());//Check login.

        // List customersArr = new ArrayList();
        //customersArr.clear();  //Clear First.

        Map<Object, Object> customersMap = new LinkedHashMap<>();
        customersMap.clear();//Clear First.

        //=======neo4j==============================//
        //User user = null;

        //find Userfriendship  from neo4j DB. 
        //Find User by Name
        if ("atTushopCom".equals(searchType)) {
            System.out.println("atTushopCom");
            user = userNeo4jService.findByUsersName(searchValue);
        }
        //Find User by usersEmailAddress
        if ("emailAddress".equals(searchType)) {
            System.out.println("emailAddress");
            user = userNeo4jService.findByName(searchValue);
           System.out.println("user : " + user);

        }

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
        
           userMap.put("findUser", customersMap);
        }else{
              userMap.put("findUser", "null"); 
        }
       
        //customersArr.add(customersMap);
        //userMap.put("findUser", customersArr);
        
        
       // userMap.put("findUser", customersMap);
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

    protected void findAllCustomers(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String searchValue = req.getParameter("searchValue");
        // String searchType = req.getParameter("searchType");//TODO NOT NEEDED.
        String userNeo4jIdString = req.getParameter("userSession[userNeo4jIdString]");

        System.out.println(searchValue);
        // System.out.println(searchType);
        System.out.println(" v  :" + userNeo4jIdString);

        userMap.clear(); //Clear First.

        try {
            System.out.println("isLoggedIn() : " + identity.isLoggedIn());

            userMap.put("isLoggedIn", identity.isLoggedIn());//Check login.


            if (identity.isLoggedIn()) {
                List customersArr = new ArrayList();
                customersArr.clear();  //Clear First.

                List<User> users = userNeo4jService.findAll();
                Iterator<User> iterR = users.iterator();

                while (iterR.hasNext()) {
                    User customer = iterR.next();//User is now refered as customer.
                    //create a new instance of HashMap each time you want to add it to the 
                    //ArrayList to avoid changing its values.
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

                    // System.out.println(customersArr);

                }
                userMap.put("findAllCustomers", customersArr);
            }
            // System.out.println(customersArr);

            aesCtr.encryptMap(req, userMap);//encrypt the Map data.
            String json = new Gson().toJson(userMap);
            resp.setContentType(JSON);
            resp.getWriter().print(json);

        } catch (IOException ex) {
            Logger.getLogger(TuUsersSearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}