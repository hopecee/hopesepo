/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.servlets;

import com.google.gson.Gson;
import com.hopecee.proshopnew.beans.UserBean_NOOOT;
import com.hopecee.proshopnew.jpa.services.UserJpaService;
import com.hopecee.proshopnew.neo4j.jdo.model.User;
import com.hopecee.proshopnew.neo4j.jdo.services.AddressBookNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.ConfigurationGroupNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.ConfigurationNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.CountryNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.services.UserNeo4jService;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.transaction.ExceptionEventRollback;
import org.picketlink.idm.api.Group;
import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.Role;
import org.picketlink.idm.api.UnsupportedCriterium;
import org.picketlink.idm.common.exception.IdentityException;
import org.picketlink.idm.impl.api.PasswordCredential;

/**
 *
 * @author hope
 */
@WebServlet(name = "tuLogInOutServlet", value = "/tuLogInOutServlet",
        initParams = {
    @WebInitParam(name = "outputImageDstDir", value = "E:/proShopDir")})
//@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TuLogInOutServlet extends HttpServlet {

    private static final long serialVersionUID = 1497819023102678427L;
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
    private final String OUTPUT_IMAGE_DIR = bundle.getString("outputImageDstDir");
    private final String OUTPUT_USERS_IMAGE_DIR = OUTPUT_IMAGE_DIR + "u/";
    private final String BAD_TOKEN = "BadToken";
    //private final String USER_DATE_FORMAT = bundle.getString("userDateFormat");
    @Inject
    Event<ExceptionEventRollback> exceptionEvent;
    @Inject
    IdentitySession identitySession;
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
    private ConfigurationNeo4jService configurationNeo4jService;
    @Inject
    private ConfigurationGroupNeo4jService configurationGroupNeo4jService;
    @Inject
    private UserBean_NOOOT userBean;
    @Inject
    private Identity identity;
    @Inject
    private Credentials credentials;
    //
    /* =========================*/
    /* userStatus
     * new = 1,
     * new2 = 2,
     * new3  = 3,
     * confirmed = 4,
     * baned = 0,
     */
    private int newUsersStatus = 1;
    /* =========================*/
    // @Inject
    //@DefaultTransaction
    //@QualifierType(QualifierType.ServiceType.DefaultSeamTransaction)
    // private SeamTransaction tx;
    Map<Object, Object> map = new LinkedHashMap<Object, Object>();

    @Override
    public void init() throws ServletException {
    }

    /**
     * Handles an HTTP POST request from Plupload.
     *
     * @param req The HTTP request
     * @param resp The HTTP response
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = "";
        try {
            token = ((ServletRequest) req).getAttribute("CheckRequestVerificationToken").toString();
        } catch (Exception e) {
        }
        if (token.equals(BAD_TOKEN)) {
            map.put("isLoggedIn", "false");//TO_DO is it necessry?
             map.put(BAD_TOKEN, BAD_TOKEN);
            String json = new Gson().toJson(map);
            resp.setContentType(JSON);
            resp.getWriter().print(json);
        } else {
            //String RequestVerificationToken = req.getParameter("RequestVerificationToken");
            String method = req.getParameter("method");
            
            try {
                if ("login".equals(method)) {
                    login(req, resp);
                }

            } catch (IllegalStateException ex) {
                Logger.getLogger(TuLogInOutServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(TuLogInOutServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) throws IllegalStateException, SystemException, IOException// throws IdentityException, UnsupportedCriterium, DAOException, NamingException, SystemException, NotSupportedException, RollbackException, HeuristicMixedException, HeuristicRollbackException 
    {
String usersEmailAddress = req.getParameter("usersEmailAddressMenu");
            String usersPassword = req.getParameter("usersPasswordMenu");

        //Logout any User that may be hanging arround.
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
            String userPhotoFile = OUTPUT_USERS_IMAGE_DIR + userNeo4jId + File.separator + userNeo4jId + ".jpg";
            boolean isUserImg = touchFile(userPhotoFile);

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
                roleInfoMap.put(j, roleInfo); // TODO which one is needed here, ID or name?
                System.out.println(j + ": RoleName : " + role.getRoleType().getName() + " GroupType : " + role.getGroup().getGroupType() + " GroupName : " + role.getGroup().getName());
            }
            //System.out.println(userNeo4jIdString + "nnnnnnnnn");

            map.put("userId", userId);
            map.put("isUserImg", isUserImg);
            map.put("userNeo4jIdString", userNeo4jIdString);

            // map.put("groupsData", groupNameMap);
            map.put("rolesData", roleInfoMap);
        }
        map.put("isLoggedIn", identity.isLoggedIn());
        String json = new Gson().toJson(map);
        //System.out.println(identity.getUser() + "===d==h================= : ");

        // JsonArray jsonArray = gsonString.getAsJsonArray();
        resp.setContentType(JSON);
        resp.getWriter().print(json);

    }

//Checks if image exist.
    public boolean touchFile(String filePath) {


        // ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();



        System.out.println("===d==: ");


        // String c = externalContext.getRealPath(filePath);
        File file = new File(filePath);
        return file.exists() ? true : false;
    }
    //File dstFile = new File(outputDIR + File.separator + userNeo4jIdStr);
}
