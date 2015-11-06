/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.servlets;

import com.google.gson.Gson;
import com.hopecee.proshopnew.events.ExceptionEventBadToken;
import com.hopecee.proshopnew.events.Testype;
import com.hopecee.proshopnew.jpa.services.UserJpaService;
import com.hopecee.proshopnew.neo4j.jdo.model.AddressBook;
import com.hopecee.proshopnew.neo4j.jdo.model.Configuration;
import com.hopecee.proshopnew.neo4j.jdo.model.ConfigurationGroup;
import com.hopecee.proshopnew.neo4j.jdo.model.Country;
import com.hopecee.proshopnew.neo4j.jdo.model.User;
import com.hopecee.proshopnew.neo4j.jdo.model.UserQuestion;
import com.hopecee.proshopnew.neo4j.jdo.services.AddressBookNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.ConfigurationGroupNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.ConfigurationNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.CountryNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.services.UserNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.UserQuestionNeo4jService;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Event;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.jdo.JDOFatalUserException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.transaction.ExceptionEventRollback;
import org.jboss.seam.transaction.Transactional;
import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.Role;
import org.picketlink.idm.common.exception.FeatureNotSupportedException;
import org.picketlink.idm.common.exception.IdentityException;
import org.picketlink.idm.impl.api.PasswordCredential;

/**
 *
 * @author hope
 */
@WebServlet(name = "tuJoinEditorServlet", value = "/tuJoinEditorServlet")
public class TuJoinEditorServlet extends HttpServlet {

    private static final long serialVersionUID = -1446212266979570505L;
    public static final String JSON = "application/json";
    /*
     private static final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : \"success\", \"id\" : \"id\"}";
     private static final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";
     
     public static final int BUF_SIZE = 2 * 1024;
     private String inputImageDstDir;
     private String outputImageDstDir;
     private int chunk;
     private int chunks;
     private String name;
     // private String user;
     private String time;
     */
    private ResourceBundle bundle = ResourceBundle.getBundle("constants");
    private final String CONFIGURATION_GROUP_TITTLE = bundle.getString("userSecurityQuestions");
    private final String SIMPLE_DATE_FORMAT = bundle.getString("simpleDateFormat");
    private final String USER_DATE_FORMAT = bundle.getString("userDateFormat");
    private final String BAD_TOKEN = "BadToken";
    @Inject
    private Event<ExceptionEventRollback> exceptionEvent;
    @Inject
    private Event<ExceptionEventBadToken> exceptionEventBadToken;
    @Inject
    private IdentitySession identitySession;
    @Inject
    private ExternalContext extContext;
    @Inject
    private UserJpaService userJpaService;
    @Inject
    private UserNeo4jService userNeo4jService;
    @Inject
    private CountryNeo4jService countryNeo4jService;
    @Inject
    private AddressBookNeo4jService addressBookNeo4jService;
    @Inject
    private UserQuestionNeo4jService userQuestionNeo4jService;
    @Inject
    private ConfigurationNeo4jService configurationNeo4jService;
    @Inject
    private ConfigurationGroupNeo4jService configurationGroupNeo4jService;
    @Inject
    private Identity identity;
    @Inject
    private Credentials credentials;
    // @Inject
    //private UserBean userBean;
    //
    /* =========================*/
    /* userStatus
     * new = 1,
     * new2 = 2,
     * new3  = 3,
     * confirmed = 4,
     * baned = 0,
     */
    //@Inject
    //HttpServletRequest request;
    //@Inject
    //HttpServletResponse response;
    private final String USERS_EMAILADDRESS = "usersEmailAddress";
    private final String USER_STATUS = "userStatus";
    private int newUsersStatus = 1;
    private int newUsersStatus2 = 2;
    private int newUsersStatus3 = 3;
    /* =========================*/
    // @Inject
    //@DefaultTransaction
    //@QualifierType(QualifierType.ServiceType.DefaultSeamTransaction)
    // private SeamTransaction tx;
    Map<Object, Object> map = new LinkedHashMap<Object, Object>();

    /**
     * Handles an HTTP POST request from TuJoinEditorServlet.
     *
     * @param req The HTTP request
     * @param resp The HTTP response
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String token = "";
        try {
            token = req.getAttribute("CheckRequestVerificationToken").toString();
        } catch (NullPointerException e) {
            Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        if (token.equals(BAD_TOKEN)) {
            //getBadToken(req, resp);
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
        } else {
            //  String RequestVerificationToken = req.getParameter("RequestVerificationToken");
            System.out.println("DDDDDDDDD = ,,,,,,,,,,,,,,,,,,,,,,,,");
            String method = req.getParameter("method");

            /* try {*/
            if ("joinEditor".equals(method)) {

                System.out.println("DDDDDDDDD = : ");

                createUser(req, resp);
            }
            if ("joinEditor2".equals(method)) {
                createAddressBook(req, resp);
            }
            if ("joinEditor3".equals(method)) {
                createNewUserQuestion(req, resp);
            }

            //return badToken if Token is bad.
            /*  if (isBadToken == true) {
             * System.out.println("isBadToken 2:2r2 = " + isBadToken);
             * getBadToken(req, resp);
             * }*/
        }



        //updateUserPassword(usersEmailAddress, usersPassword);
        //  createGroup(groupName, groupType);
        //  deleteGroup(groupName,  force);
        // createRoleType( roleName);
        // deleteRoleType(roleName);
        // createRole(roleName, usersEmailAddress, groupName, groupType);
        // deleteRole(  roleName,   usersEmailAddress,   groupName,  groupType);
        // associateUser(   usersEmailAddress,   groupName,  groupType);
        // createCountry(req, resp, usersEmailAddress, usersFirstname, usersLastname, usersPassword);
        // deleteCountry( req,  resp,  usersEmailAddress,  usersFirstname,  usersLastname,  usersPassword);
        // createConfigurationGroup(req, resp, usersEmailAddress, usersFirstname, usersLastname, usersPassword);
        // createConf(req, resp, usersEmailAddress, usersFirstname, usersLastname, usersPassword);




        /*  } catch (Exception ex) {
         * isBadToken = true;
         * Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
         * }*/



    }
//@Testype

    protected void createUser(HttpServletRequest req, HttpServletResponse resp) {
        String usersEmailAddress = req.getParameter("usersEmailAddress");
        String usersName = req.getParameter("usersName");
        String usersFirstname = req.getParameter("usersFirstname");
        String usersLastname = req.getParameter("usersLastname");
        String usersPassword = req.getParameter("usersPassword");
        String passwordConfirm = req.getParameter("passwordConfirm");
        String agree = req.getParameter("agree");

        /*
         String groupName = "Administration";
         String groupType = "DEPARTMENT";
         boolean force = false;
         String attrName = null;
         String attrValue = null;
         String[] attrNames = null; //{""};
         String roleName = "Admin Manager";
         */
        /*
         //Convert String numbers to Integer
         int x1 = Integer.parseInt(stringX1);
         int x2 = Integer.parseInt(stringX2);
         int y1 = Integer.parseInt(stringY1);
         int y2 = Integer.parseInt(stringY2);
         int scaledImgWidth = Integer.parseInt(stringScaledImgWidth);
         int scaledImgHeight = Integer.parseInt(stringScaledImgHeight);
         int outSizeWidth = Integer.parseInt(stringOutSizeWidth);
         int outSizeHeight = Integer.parseInt(stringOutSizeHeight);
         */


        System.out.println(usersEmailAddress);
        System.out.println(usersName);
        System.out.println(usersFirstname);
        System.out.println(usersLastname);
        System.out.println(usersPassword);
        System.out.println(passwordConfirm);
        System.out.println(agree);


        try {

            //=======MYSQL==============================//
            //create Userfriendship  for  Jpa MYSQL.
            // System.out.println("usersEmailAddress 2: = " + usersEmailAddress);
            userJpaService.createUser(usersEmailAddress);
            //create userPassword for  Jpa MYSQL.
            //System.out.println("usersPassword : = " + usersPassword);
            // usersEmailAddress = "hgnj";
            userJpaService.updateUserPassword(usersEmailAddress, usersPassword);
            //  System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            // userJpaService.findAll();
            // System.out.println("IException >>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            //=======neo4j==============================//
            //create Userfriendship  for neo4j DB. 
            User user = new User();
            user.setName(usersEmailAddress);
            user.setUsersName(usersName);
            user.setUsersFirstname(usersFirstname);
            user.setUsersLastname(usersLastname);
            user.setUsersStatus(newUsersStatus);
            //convert to appropriate SIMPLE_DATE_FORMAT.
            String newDateString = new SimpleDateFormat(SIMPLE_DATE_FORMAT).format(new Date()); // 2001-07-04T12:08:56.235-0700
            user.setUsersDor(newDateString);
            user.setLast_modified(newDateString);

            userNeo4jService.save(user);





            //set session and userStatus for new user = 0.
            //ExternalContext extCt = FacesContext.getCurrentInstance().getExternalContext();
            //           HttpSession session = req.getSession();
            //           session.setAttribute(USER_STATUS, getNewUsersStatus());
            //          session.setAttribute(USERS_EMAILADDRESS, usersEmailAddress);

            //String usersEmailAddress = req.getParameter("usersEmailAddressMenu");
            //String usersPassword = req.getParameter("usersPasswordMenu");

            //Logout any Userfriendship that may be hanging arround.
            identity.logout();

            credentials.setUsername(usersEmailAddress);
            credentials.setCredential(new PasswordCredential(usersPassword));

            identity.login();
            //Map<Object, Object> map = new LinkedHashMap<Object, Object>();
            if (identity.isLoggedIn()) {

                //get user id (Mysql). 
                //System.out.println(identity.getUser() + "===d==h================= : " + identity.isLoggedIn());
                String userId = identity.getUser().getId();
                //System.out.println(identity.getUser() + "===d==h================= : " + userId);

                //Get UserNeo4jId.
                long userNeo4jId = userNeo4jService.findByName(userId).getId();
                // convert long to String type
                String userNeo4jIdString = Long.toString(userNeo4jId);
                //System.out.println(userNeo4jIdString);

                //System.out.println(userNeo4jId + " ==jj=====nn== : " + userId);



                //Check user Image exist.
                //String userPhotoFile = OUTPUT_USERS_IMAGE_DIR + userNeo4jId + File.separator + userNeo4jId + ".jpg";
                // boolean isUserImg = touchFile(userPhotoFile);

                /* //Not used now.
                 Set<Group> groups = identity.getGroups();
                 Map<Object, Object> groupNameMap = new LinkedHashMap<Object, Object>();
                 Iterator<Group> iterG = groups.iterator();
                 while (iterG.hasNext()) {
                 Group grp = iterG.next();

                 groupNameMap.put(grp.getGroupType(), grp.getName()); // TODO which one is needed here, ID or name?
                 System.out.println("GroupType nn : " + grp.getGroupType() + " Name : " + grp.getName());
                 }
                 */


                Set<Role> roles = identity.getRoles();
                Map<Object, Object> roleInfoMap = new LinkedHashMap<Object, Object>();
                Iterator<Role> iterR = roles.iterator();
                // List list = new ArrayList();
                int i = 0;
                while (iterR.hasNext()) {
                    Role role = iterR.next();
                    //int i = 0;
                    //list.add(i++,1);

                    String roleInfo = role.getGroup().getGroupType() + "," + role.getGroup().getName() + "," + role.getRoleType().getName();
                    //System.out.println(i++);
                    int j = i++;
                    roleInfoMap.clear();//clear first
                    roleInfoMap.put(j, roleInfo); // TODO which one is needed here, ID or name?
                    System.out.println(j + ": RoleName : " + role.getRoleType().getName() + " GroupType : " + role.getGroup().getGroupType() + " GroupName : " + role.getGroup().getName());
                }
                map.clear(); //Clear First.
                map.put("userId", userId);
                //map.put("isUserImg", isUserImg);
                map.put("userNeo4jIdString", userNeo4jIdString);
                // map.put("groupsData", groupNameMap);
                map.put("rolesData", roleInfoMap);
            }
            map.put("isLoggedIn", identity.isLoggedIn());
            String json = new Gson().toJson(map);



            //for (Map.Entry<Object, Object> entry : map.entrySet()) {
            //   System.out.println(entry.getKey() + " / " + entry.getValue());
            // }




            resp.setContentType(JSON);
            resp.getWriter().print(json);


            // throw new IllegalStateException();


        } catch (IllegalArgumentException ex) {
            exceptionEvent.fire(new ExceptionEventRollback());
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
            Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IdentityException ex) {
            exceptionEvent.fire(new ExceptionEventRollback());
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
            Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            exceptionEvent.fire(new ExceptionEventRollback());
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
            Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOFatalUserException ex) {
            exceptionEvent.fire(new ExceptionEventRollback());
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
            Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            exceptionEvent.fire(new ExceptionEventRollback());
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
            Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            exceptionEvent.fire(new ExceptionEventRollback());
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
            Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void createAddressBook(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("createAddressBook = ,,,,,,,,,,f,,,,,,,,,,,,,,");
        String usersEmailAddress = req.getParameter("usersEmailAddress");
        String street = req.getParameter("street");
        String street2 = req.getParameter("street2");
        String city = req.getParameter("city");
        String state = req.getParameter("state");
        String zip = req.getParameter("zip");
        String postedCountry = req.getParameter("postedCountry");
        String fax = req.getParameter("fax");
        String url = req.getParameter("url");
        String phoneDefault = req.getParameter("phoneDefault");
        String phone2 = req.getParameter("phone2");
        String occupation = req.getParameter("occupation");

        System.out.println(street);
        System.out.println(street2);
        System.out.println(city);
        System.out.println(state);
        System.out.println(zip);
        System.out.println(postedCountry);
        System.out.println(fax);
        System.out.println(url);
        System.out.println(phoneDefault);
        System.out.println(phone2);
        System.out.println(occupation);



        try {

            //create Userfriendship AddressBook for neo4j DB. 
            AddressBook addressBook = new AddressBook();
            addressBook.setEntry_street_address(street);
            addressBook.setEntry_street_address2(street2);
            addressBook.setEntry_city(city);
            addressBook.setEntry_state(state);
            addressBook.setEntry_postcode(zip);
            addressBook.setEntry_country_id(countryNeo4jService.findByAlpha_3(postedCountry).getId());
            addressBook.setEntry_fax(fax);
            addressBook.setEntry_url(url);
            addressBook.setEntry_phone_default(phoneDefault);
            addressBook.setEntry_phone2(phone2);
            addressBook.setEntry_occupation(occupation);


            User user = userNeo4jService.findByName(usersEmailAddress);
            //long userId = userNeo4jService.findByName(sessionUserEmailAdd).getId();
            System.out.println("userId : " + user.getId());
            addressBook.setUsers_id(user.getId());

            addressBookNeo4jService.save(addressBook);

            // update userStatus.
            user.setUsersStatus(getNewUsersStatus2());
            //convert to appropriate SIMPLE_DATE_FORMAT.
            String newDateString = new SimpleDateFormat(SIMPLE_DATE_FORMAT).format(new Date()); // 2001-07-04T12:08:56.235-0700
            user.setLast_modified(newDateString);

            userNeo4jService.update(user);

            //set session userStatus for new2 user = 2.
            // session.setAttribute(USER_STATUS, getNewUsersStatus2());
            // session.setAttribute(USERS_EMAILADDRESS, sessionUserEmailAdd);



            ///=======test============////
            Integer i = 2795;
            Long l = new Long(i);
            // addressBookNeo4jService.delete(l);

            Iterator<AddressBook> iter = addressBookNeo4jService.findAll().iterator();
            while (iter.hasNext()) {
                AddressBook p = iter.next();
                System.out.println(p);
            }

            Iterator<User> iterw = userNeo4jService.findAll().iterator();
            while (iterw.hasNext()) {
                User p = iterw.next();
                System.out.println(p);
            }

            /*
             * //get current user's EmailAddress from session.
             // HttpSession session = req.getSession();
             //String sessionUserEmailAdd = (String) session.getAttribute(USERS_EMAILADDRESS);
             // System.out.println(USERS_EMAILADDRESS + " : " + session.getAttribute(USERS_EMAILADDRESS));
             //System.out.println(USER_STATUS + " : " + session.getAttribute(USER_STATUS));
             Enumeration e = session.getAttributeNames();
             * while (e.hasMoreElements()) {
             * String attr = (String) e.nextElement();
             * System.err.println(" attr = " + attr);
             * Object value = session.getAttribute(attr);
             * System.err.println(" value = " + value);
             * }*/

            // return "index?faces-redirect=true";

            map.clear();//Clear First.
            map.put("none", "none"); //TODO Is there anything to respond here?
            String json = new Gson().toJson(map);

            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " // " + entry.getValue());
            }
            resp.setContentType(JSON);
            resp.getWriter().print(json);

            //throw new DAOException();


        } catch (DAOException ex) {
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
            Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
            Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createNewUserQuestion(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("hhhhhh = ,,,,,,,,,,f,,,,,,,,,,,,,,");
        String usersEmailAddress = req.getParameter("usersEmailAddress");
        String dob = req.getParameter("dob");
        String selectedQuestion1 = req.getParameter("selectedQuestion1");
        String answer1 = req.getParameter("answer1");
        String selectedQuestion2 = req.getParameter("selectedQuestion2");
        String answer2 = req.getParameter("answer2");
        String pin = req.getParameter("pin");




        /*
         //Convert String numbers to Integer
         int x1 = Integer.parseInt(stringX1);
         int x2 = Integer.parseInt(stringX2);
         int y1 = Integer.parseInt(stringY1);
         int y2 = Integer.parseInt(stringY2);
         int scaledImgWidth = Integer.parseInt(stringScaledImgWidth);
         int scaledImgHeight = Integer.parseInt(stringScaledImgHeight);
         int outSizeWidth = Integer.parseInt(stringOutSizeWidth);
         int outSizeHeight = Integer.parseInt(stringOutSizeHeight);
         */


        System.out.println(dob);
        System.out.println(selectedQuestion1);
        System.out.println(answer1);
        System.out.println(selectedQuestion2);
        System.out.println(answer2);
        System.out.println(pin);


        try {
            //create UserQuestion for neo4j DB. 
            UserQuestion userQuestion = new UserQuestion();
            userQuestion.setSelectedQuestion1(selectedQuestion1);
            userQuestion.setAnswer1(answer1);
            userQuestion.setSelectedQuestion2(selectedQuestion2);
            userQuestion.setAnswer2(answer2);
            //convert to appropriate SIMPLE_DATE_FORMAT.
            String newDateString = new SimpleDateFormat(SIMPLE_DATE_FORMAT).format(new Date()); // 2001-07-04T12:08:56.235-0700
            userQuestion.setDate_added(newDateString);
            userQuestion.setLast_modified(newDateString);

            //get current user's EmailAddress from session.
            // HttpSession session = req.getSession();
            //String userEmailAdd = (String) session.getAttribute(USERS_EMAILADDRESS);
            // System.out.println("cid tm : " + userEmailAdd);
            //System.out.println("newDateString 2: " + newDateString);

            User user = userNeo4jService.findByName(usersEmailAddress);
            userQuestion.setUsers_id(user.getId());

            userQuestionNeo4jService.save(userQuestion);




            //update Userfriendship for neo4j DB. 
            //convert USER_DATE_FORMAT to Date.
            SimpleDateFormat formatter = new SimpleDateFormat(USER_DATE_FORMAT); // 2001-07-04T12:08:56.235-0700
            Date date = formatter.parse(dob);

            //convert to appropriate SIMPLE_DATE_FORMAT.
            String normalNewDateString = new SimpleDateFormat(SIMPLE_DATE_FORMAT).format(date); // 2001-07-04T12:08:56.235-0700
            //System.out.println("mk : " + normalNewDateString);
            user.setUsersDob(normalNewDateString);
            user.setLast_modified(newDateString);

            // System.out.println(USERS_EMAILADDRESS + " : " + session.getAttribute(USERS_EMAILADDRESS));
            // System.out.println(USER_STATUS + " : " + session.getAttribute(USER_STATUS));

            // update userStatus.
            user.setUsersStatus(newUsersStatus3);

            userNeo4jService.update(user);


            ////////=======test=========///////////
            Iterator<User> iterw = userNeo4jService.findAll().iterator();
            while (iterw.hasNext()) {
                User p = iterw.next();
                System.out.println(p);
            }

            Iterator<UserQuestion> iter = userQuestionNeo4jService.findAll().iterator();
            while (iter.hasNext()) {
                UserQuestion p = iter.next();
                System.out.println(p);
            }


            //set session for new2 user = 1.
            //  session.setAttribute("userStatus", getNewUsersStatus());
            // session.setAttribute("usersEmailAddress", usersEmailAddress);




            /*
             Enumeration e = session.getAttributeNames();
             while (e.hasMoreElements()) {
             String attr = (String) e.nextElement();
             System.err.println(" attr = " + attr);
             Object value = session.getAttribute(attr);
             System.err.println(" value = " + value);
             }
             */
            map.clear();//Clear First.
            map.put("none", "none"); //TODO Is there anything to respond here?
            String json = new Gson().toJson(map);
            resp.setContentType(JSON);
            resp.getWriter().print(json);



            // return "index?faces-redirect=true";


        } catch (DAOException ex) {
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
            Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
            Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
            Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    @Transactional
    public void deleteUser(HttpServletRequest req, HttpServletResponse resp, String usersEmailAddress, String usersFirstname, String usersLastname, String usersPassword) throws IdentityException, Exception {
        //delete one.
        // userJpaService.deleteUser(usersEmailAddress);
        Iterator<org.picketlink.idm.api.User> iterj = userJpaService.findAll().iterator();
        while (iterj.hasNext()) {
            org.picketlink.idm.api.User p = iterj.next();
            System.out.println(p);
        }

        //delete all userNeo4j.
        Iterator<User> iterw = userNeo4jService.findAll().iterator();
        while (iterw.hasNext()) {
            User p = iterw.next();
            System.out.println(p);
            // userNeo4jService.delete(p);
            System.out.println(p.getId() + " : Deleted  ");
        }

        //delete one userNeo4j.
        Integer i = 5041;
        Long l = new Long(i);
        System.out.println("Long : " + l);
        // userNeo4jService.delete(l);

    }

    @Transactional
    public void updateUserPassword(String usersEmailAddress, String usersPassword) {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        userJpaService.updateUserPassword(usersEmailAddress, usersPassword);
        System.out.println("User : (" + usersEmailAddress + ") updated to : (" + usersPassword + ")");
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Transactional
    public void createUserAttribute(String usersEmailAddress, String attrName, String attrValue) throws IdentityException {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        userJpaService.createUserAttribute(usersEmailAddress, attrName, attrValue);
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Transactional
    public void deleteUserAttribute(String usersEmailAddress, String[] attrNames) throws IdentityException {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        userJpaService.deleteUserAttribute(usersEmailAddress, attrNames);
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Transactional
    public void createGroup(String groupName, String groupType) throws IdentityException {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        userJpaService.createGroup(groupName, groupType);
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Transactional
    public void deleteGroup(String groupName, boolean force) throws IdentityException {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        userJpaService.deleteGroup(groupName, force);
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Transactional
    public void createRoleType(String roleName) throws FeatureNotSupportedException, IdentityException {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        userJpaService.createRoleType(roleName);
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Transactional
    public void deleteRoleType(String roleName) throws FeatureNotSupportedException, IdentityException {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        userJpaService.deleteRoleType(roleName);
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Transactional
    public void createRole(String roleName, String usersEmailAddress, String groupName, String groupType) throws FeatureNotSupportedException, IdentityException {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        userJpaService.createRole(roleName, usersEmailAddress, groupName, groupType);
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Transactional
    public void deleteRole(String roleName, String usersEmailAddress, String groupName, String groupType) throws FeatureNotSupportedException, IdentityException {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        userJpaService.deleteRole(roleName, usersEmailAddress, groupName, groupType);
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Transactional
    public void associateUser(String usersEmailAddress, String groupName, String groupType) throws FeatureNotSupportedException, IdentityException {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        userJpaService.associateUser(usersEmailAddress, groupName, groupType);
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Transactional
    public void createCountry(HttpServletRequest req, HttpServletResponse resp, String usersEmailAddress, String usersFirstname, String usersLastname, String usersPassword) throws IdentityException, Exception {
        //create Country  for neo4j DB.  
        Country country = new Country();
        country.setCountry_name("Nigeria4");
        country.setCountry_iso_code_2("NG4");
        country.setCountry_iso_code_3("NGA4");
        country.setAddress_format_id(1);
        countryNeo4jService.save(country);


        Iterator<Country> iter = countryNeo4jService.findAll().iterator();
        System.out.println("<<<<<<<<<<<<<<<  Country   : >>>>>>>>>>>>>>>>>>");

        while (iter.hasNext()) {
            Country p = iter.next();
            System.out.println(p);
        }

    }

    @Transactional
    public void deleteCountry(HttpServletRequest req, HttpServletResponse resp, String usersEmailAddress, String usersFirstname, String usersLastname, String usersPassword) throws IdentityException, Exception {

        //delete all .
        Iterator<Country> iter = countryNeo4jService.findAll().iterator();
        while (iter.hasNext()) {
            Country p = iter.next();
            System.out.println(p);
            //countryNeo4jService.delete(p);
        }

        //delete one .
        Integer i = 17;
        Long l = new Long(i);
        System.out.println(" Id : " + l);
        //Country cont = countryNeo4jService.findById(l);
        // countryNeo4jService.delete(cont);
        // System.out.println(cont.getId() + " : Deleted  ================");

    }

    @Transactional
    public void createConfigurationGroup(HttpServletRequest req, HttpServletResponse resp, String usersEmailAddress, String usersFirstname, String usersLastname, String usersPassword) throws IdentityException, Exception {


        Integer i = 3;
        Long l = new Long(i);

        System.out.println("configurationNeo4jService   : q");
//System.out.println("configurationNeo4jService   : " + configurationNeo4jService.findByName("E-Mail From"));

        String configuration_group_title = "My Wbcs";
        ConfigurationGroup confGroup = // configurationGroupNeo4jService.findByName(configuration_group_title);
                new ConfigurationGroup();


        confGroup.setConfiguration_group_title("User Security Questions");
        confGroup.setConfiguration_group_description("Security Questions");
        //confGroup.getConfigurations().add(configurationNeo4jService.findById(l));
        // confGroup.getConfigurations().add(conf); 
        confGroup.setSort_order(2);
        confGroup.setVisible(1);


        System.out.println("ConfigurationGroup   : ry9");

// configurationGroupNeo4jService.save(confGroup);
        Integer i3 = 8;
        Long l3 = new Long(i3);
        // configurationGroupNeo4jService.delete(l3);

        //configurationGroupNeo4jService.update(confGroup);

        Integer confGpI = 8;
        Long confGpL = new Long(confGpI);
        Integer confI = 6;
        Long confL = new Long(confI);
        // configurationGroupNeo4jService.addConfiguration(confGpL, confL);
        //configurationGroupNeo4jService.removeConfiguration(confGpL, confL);
        // configurationGroupNeo4jService.addConfiguration(confGpL, confL);
        Integer s = 1;
        Long cs = new Long(s);
        //  ConfigurationGroup kio = configurationGroupNeo4jService.findById(cs); //update(confGroup);
        //   System.out.println("ConfigurationGroup   : "+kio);

        Integer i1 = 12;
        Long cGId = new Long(i1);
        Integer i2 = 14;
        Long cId = new Long(i2);
        System.out.println("ConfigurationGroup : hhhh");
        // configurationGroupNeo4jService.addConfiguration(cGId,cId);//update(confGroup);

        System.out.println("ConfigurationGroup   : w=====23xxxxx");



        Iterator<ConfigurationGroup> iter2 = configurationGroupNeo4jService.findAll().iterator();

        while (iter2.hasNext()) {
            ConfigurationGroup p = iter2.next();
            System.out.println(p);

            Iterator<Configuration> iter3 = p.getConfigurations().iterator();
            while (iter3.hasNext()) {
                Configuration p1 = iter3.next();
                System.out.println(p1);
            }

        }


        Iterator<Configuration> iter1 = configurationNeo4jService.findAll().iterator();
        System.out.println("Configuration   : ");

        while (iter1.hasNext()) {
            Configuration p = iter1.next();
            System.out.println(p);
        }

    }

    @Transactional
    public void createConf(HttpServletRequest req, HttpServletResponse resp, String usersEmailAddress, String usersFirstname, String usersLastname, String usersPassword) throws IdentityException, Exception {

        Configuration conf = new Configuration();

        //conf.setConfiguration_group(configurationGroupNeo4jService.findByName("My WebSite"));

        Integer i2 = 12;
        Long cGId2 = new Long(i2);
        Date date = new Date();
        conf.setConfiguration_title("User Questions 2");
        conf.setConfiguration_description("User Questions for scurity purpose 2");
        conf.setConfiguration_value("What is your maternal grandmother's maiden name?");
        conf.setConfiguration_key("USER_QUESTION_2");
        conf.setDate_added(date);
        conf.setConfigurationGroupID(configurationGroupNeo4jService.findById(cGId2).getId());
        conf.setLast_modified(date);
        conf.setSet_function(null);
        conf.setSort_order(2);
        conf.setUse_function(null);


        // configurationNeo4jService.save(conf);
        Integer i = 6;
        Long cG = new Long(i);
        configurationNeo4jService.delete(cG);
        //  configurationNeo4jService.findById(cGId2);
//JDOHelper.getObjectId(cust1);

        Integer i1 = 3;
        Long cGId = new Long(i1);
        // ConfigurationGroup g = configurationGroupNeo4jService.findById(cGId);
        System.out.println("ConfigurationGroup   : d :");
        //  System.out.println("ConfigurationGroup   : d :"+g);


        Iterator<Configuration> iter1 = configurationNeo4jService.findAll().iterator();
        System.out.println("Configuration   : d");

        while (iter1.hasNext()) {
            Configuration p = iter1.next();
            System.out.println(p);
        }

        Iterator<ConfigurationGroup> iter2 = configurationGroupNeo4jService.findAll().iterator();

        while (iter2.hasNext()) {
            ConfigurationGroup p = iter2.next();
            System.out.println(p);

            Iterator<Configuration> iter3 = p.getConfigurations().iterator();
            while (iter3.hasNext()) {
                Configuration p1 = iter3.next();
                System.out.println(p1);
            }

        }
    }

    /*  public void getBadToken(HttpServletRequest req, HttpServletResponse resp) throws IOException {
     * System.out.println(": resp.getWriter().print(json);");
     * map.clear();//Clear First.
     * map.put(BAD_TOKEN, BAD_TOKEN);
     * String json = new Gson().toJson(map);
     * resp.setContentType(JSON);
     * System.out.println(": resp.getWriter().print(json);  : " + req.getParameter("method"));
     * 
     * resp.getWriter().print(json);
     * }*/
    public UserJpaService getUserJpaService() {
        return userJpaService;
    }

    public void setUserJpaService(UserJpaService userJpaService) {
        this.userJpaService = userJpaService;
    }

    public UserNeo4jService getUserNeo4jService() {
        return userNeo4jService;
    }

    public void setUserNeo4jService(UserNeo4jService userNeo4jService) {
        this.userNeo4jService = userNeo4jService;
    }

    public CountryNeo4jService getCountryNeo4jService() {
        return countryNeo4jService;
    }

    public void setCountryNeo4jService(CountryNeo4jService countryNeo4jService) {
        this.countryNeo4jService = countryNeo4jService;
    }

    public AddressBookNeo4jService getAddressBookNeo4jService() {
        return addressBookNeo4jService;
    }

    public void setAddressBookNeo4jService(AddressBookNeo4jService addressBookNeo4jService) {
        this.addressBookNeo4jService = addressBookNeo4jService;
    }

    public ExternalContext getExtContext() {
        return extContext;
    }

    public void setExtContext(ExternalContext extContext) {
        this.extContext = extContext;
    }

    public int getNewUsersStatus() {
        return newUsersStatus;
    }

    public void setNewUsersStatus(int newUsersStatus) {
        this.newUsersStatus = newUsersStatus;
    }

    public int getNewUsersStatus2() {
        return newUsersStatus2;
    }

    public void setNewUsersStatus2(int newUsersStatus2) {
        this.newUsersStatus2 = newUsersStatus2;
    }
}
