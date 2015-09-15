/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.servlets;

/**
 *
 * @author hope
 */
import com.hopecee.proshopnew.jpa.services.UserJpaService;
import com.hopecee.proshopnew.neo4j.jdo.model.AddressBook;
import com.hopecee.proshopnew.neo4j.jdo.model.Country;
import com.hopecee.proshopnew.neo4j.jdo.model.User;
import com.hopecee.proshopnew.neo4j.jdo.services.AddressBookNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.CountryNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.services.UserNeo4jService;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Event;
import javax.faces.context.ExternalContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.jboss.seam.transaction.ExceptionEventRollback;
import org.jboss.seam.transaction.Transactional;
import org.picketlink.idm.common.exception.IdentityException;

/**
 *
 * @author hope
 */
@WebServlet(value = "/joinEditor2Servlet", name = "joinEditor2Servlet")
public class JoinEditor2Servlet_NOOOT extends HttpServlet {

    private static final long serialVersionUID = 2776991657605248028L;
    private static final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : \"success\", \"id\" : \"id\"}";
    private static final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";
    public static final String JSON = "application/json";
    public static final int BUF_SIZE = 2 * 1024;
    private String inputImageDstDir;
    private String outputImageDstDir;
    private int chunk;
    private int chunks;
    private ResourceBundle bundle = ResourceBundle.getBundle("constants");
    private final String SIMPLE_DATE_FORMAT = bundle.getString("simpleDateFormat");
    private String name;
    // private String user;
    private String usersEmailAddress = "usersEmailAddress";
    private String time;
    ////=================//
    @Inject
    Event<ExceptionEventRollback> exceptionEvent;
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
    /* =========================*/
    /* userStatus
     * new = 1,
     * new2 = 2,
     * new3  = 3,
     * confirmed = 4,
     * baned = 0,
     */
    private int newUsersStatus = 2;
    /* =========================*/

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

        System.out.println("DDDDDDDDD = ,,,,,,,,,,f,,,,,,,,,,,,,,");
        String street = req.getParameter("street");
        String street2 = req.getParameter("street2");
        String city = req.getParameter("city");
        String state = req.getParameter("state");
        String zip = req.getParameter("zip");
        String selectedCountry = req.getParameter("selectedCountry");
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
        System.out.println(selectedCountry);
        System.out.println(fax);
        System.out.println(url);
        System.out.println(phoneDefault);
        System.out.println(phone2);
        System.out.println(occupation);

        createAddressBook(req, resp, street, street2, city, state, zip, selectedCountry, fax, url, phoneDefault, phone2, occupation);


    }

    //@Transactional
    public void createAddressBook(HttpServletRequest req, HttpServletResponse resp, String street, String street2, String city, String state, String zip, String selectedCountry, String fax, String url, String phoneDefault, String phone2, String occupation) {
        try {
            // System.out.println("cid t : "+cid);
            // System.out.println("cid t : "+cid);

            //create User AddressBook for neo4j DB. 
            AddressBook addressBook = new AddressBook();
            addressBook.setEntry_street_address(street);
            addressBook.setEntry_street_address2(street2);
            addressBook.setEntry_city(city);
            addressBook.setEntry_state(state);
            addressBook.setEntry_postcode(zip);
            addressBook.setEntry_country_id(countryNeo4jService.findByName(selectedCountry).getId());
            addressBook.setEntry_fax(fax);
            addressBook.setEntry_url(url);
            addressBook.setEntry_phone_default(phoneDefault);
            addressBook.setEntry_phone2(phone2);
            addressBook.setEntry_occupation(occupation);

            //get current user's EmailAddress from session.
            HttpSession session = req.getSession();
            String sessionUserEmailAdd = (String) session.getAttribute(usersEmailAddress);
            System.out.println("sessionUserEmailAdd : " + sessionUserEmailAdd);
            User user = userNeo4jService.findByName(sessionUserEmailAdd);
            //long userId = userNeo4jService.findByName(sessionUserEmailAdd).getId();
            System.out.println("userId : " + user.getId());
            addressBook.setUsers_id(user.getId());

            addressBookNeo4jService.save(addressBook);

             // update userStatus.
            user.setUsersStatus(newUsersStatus);
            //convert to appropriate SIMPLE_DATE_FORMAT.
            String newDateString = new SimpleDateFormat(SIMPLE_DATE_FORMAT).format(new Date()); // 2001-07-04T12:08:56.235-0700
            user.setLast_modified(newDateString);

            userNeo4jService.update(user);

            //set session userStatus for new2 user = 2.
            session.setAttribute("userStatus", getNewUsersStatus());
            // session.setAttribute("usersEmailAddress", usersEmailAddress);

              
            
            ///===================////
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


            Enumeration e = session.getAttributeNames();
            while (e.hasMoreElements()) {
                String attr = (String) e.nextElement();
                System.err.println(" attr = " + attr);
                Object value = session.getAttribute(attr);
                System.err.println(" value = " + value);
            }




            // return "index?faces-redirect=true";
        } catch (DAOException ex) {
            exceptionEvent.fire(new ExceptionEventRollback());
            Logger.getLogger(JoinEditor2Servlet_NOOOT.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

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
}
