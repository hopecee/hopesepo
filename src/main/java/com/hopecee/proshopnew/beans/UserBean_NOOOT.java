/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.beans;

import com.hopecee.proshopnew.jpa.services.UserJpaService;
import com.hopecee.proshopnew.neo4j.jdo.model.AddressBook;
import com.hopecee.proshopnew.neo4j.jdo.model.Country;
import com.hopecee.proshopnew.neo4j.jdo.model.User;
import com.hopecee.proshopnew.neo4j.jdo.services.AddressBookNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.CountryNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.services.UserNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.impl.AddressBookNeo4jServiceImpl;
import com.hopecee.proshopnew.neo4j.jdo.services.impl.UserNeo4jServiceImpl;
import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.jboss.seam.transaction.Transactional;
import org.picketlink.idm.common.exception.IdentityException;

/**
 *
 * @author hope
 */
@Named
//@Model
//@ViewScoped
@SessionScoped
//@RequestScoped
public class UserBean_NOOOT implements Serializable {

    private static final long serialVersionUID = 6919448014076356901L;
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
    private String usersEmailAddress = null;
    private String usersFirstname = null;
    private String usersLastname = null;
    private String usersPassword = null;
    private String passwordConfirm = null;
    private boolean agree = false;
    private Country selectedCountry =null;
     //private String usersGender1 = null;
    private List<Country> countries = null;
    //--------------------
    //@NotNull
    // @NotBlank
    private String usersDob = null;
    // @NotNull
    //@NotBlank
    private String entry_street_address = null;
    // @NotNull
    //@NotBlank
    private String entry_street_address2 = null;
    // @NotNull
    // @NotBlank
    private String entry_city = null;
    // @NotNull
    // @NotBlank
    private String entry_state = null;
    // @NotNull
    // @NotBlank
    private String entry_postcode = null;
    //@NotNull
    // @NotBlank
    private String entry_country_id = null;
    //@NotNull
    //@NotBlank
    private String entry_fax = null;
    //@NotNull
    // @NotBlank
    private String entry_url = null;
    //@NotNull
    // @NotBlank
    private String entry_phone_default = null;
    //@NotNull
    //@NotBlank
    private String entry_phone2 = null;
    private String entry_occupation = null;
    private String usersGender = null;
    //--------------------
    /*
     int USER_ROLE_ID = 1;//Default User Role Id.
     String ROLE_USER = "ROLE_ADMIN";//Default User Role Id.
   
 
     @NotNull
     @NotBlank
     private String usersCompany = null;
 
     ////
     @NotNull
     @Pattern(regexp = "^((\\+\\d{1,3}(-| )?\\(?\\d\\)?(-| )?\\d{1,5})|(\\(?\\d{2,6}\\)?))(-| )?(\\d{3,4})(-| )?(\\d{4})(( x| ext)\\d{1,5}){0,1}$")
     private String defualtPhone = null;
     // @NotNull
     //private Countries country = null;
     //private List<Countries> countries = null;
     private String usersGender = null;
     private boolean readTerms = false;
     ///
     private String entryStreetAddress = null;
     private String entryCity = null;
     private String entryState = null;
     */
    //private 
            Date date = new Date();
    /* userStatus
     * new = 0,
     * unconfirmed = 1,
     * confirmed = 2,
     * baned = 3,
     */
    private int newUsersStatus = 0;

//@Transactional
    public String createTest() throws IdentityException, Exception {
        Iterator<Country> iter0 = countryNeo4jService.findAll().iterator();
        System.out.println("Country Name2 [[[[[[[[[[[[       : ");

        while (iter0.hasNext()) {
            Country p = iter0.next();
            System.out.println("Country Name2        : " + p.getCountry_name() + "  Description : " + p.getCountry_iso_code_3());
        }

        Iterator<Country> iter1 = countryNeo4jService.findAll().iterator();
        System.out.println("Country Name2 [[[[[[[[[[[[       : ");

        while (iter1.hasNext()) {
            Country p = iter1.next();
            System.out.println("Country Name2        : " + p.getCountry_name() + "  Description : " + p.getCountry_iso_code_3());
        }

        return "index?faces-redirect=true";

    }

    @Transactional
    public String createUser() throws IdentityException, Exception {


        //create userId.
        System.out.println("usersEmailAddress : = " + usersEmailAddress);
        userJpaService.createUser(usersEmailAddress);
        //create userPassword.
        System.out.println("usersPassword : = " + usersPassword);
        userJpaService.updateUserPassword(usersEmailAddress, usersPassword);


        System.out.println("usersEmailAddress : = " + usersEmailAddress + " : " + usersFirstname + " : " + usersLastname);
 
  

        //create User  for neo4j DB. 
        User user = new User();
        user.setName(usersEmailAddress);
        user.setUsersFirstname(usersFirstname);
        user.setUsersLastname(usersLastname);
        user.setUsersStatus(newUsersStatus);
        user.setUsersDor(date.toString());//TODO

        userNeo4jService.save(user);
     
       
        //create Country  for neo4j DB.  
        Country country = new Country();
        country.setCountry_name("Nigeria4");
        country.setCountry_iso_code_2("NG");
        country.setCountry_iso_code_3("NGA");
        country.setAddress_format_id(1);
        countryNeo4jService.save(country);
        
      
 Iterator<Country> iter =  countryNeo4jService.findAll().iterator();
         System.out.println("Country Name2   : ");

         while (iter.hasNext())
         {
         Country p = iter.next();
         System.out.println("Country Name2        : "+p.getCountry_name()+  "Description : "+p.getCountry_iso_code_3());
         }
        
        
        ///////////==========
/*
         countryNeo4jService.createCountry("Nigeria7", "NG", "NGA",1);
         //System.out.println("countryNeo4jService : = " + countryNeo4jService.selectByName( "Nigeria2") );
         List<Country> list = countryNeo4jService.findAll();
         Iterator<Country> iter = list.iterator();
         System.out.println("Country Name2        : ");

         while (iter.hasNext())
         {
         Country p = iter.next();
         System.out.println("Country Name2        : "+p.getCountry_name()+  "Description : "+p.getCountry_iso_code_3());
         }
         */


        System.out.println("/////////////////////////////// : = ");
        System.out.println("////////////////////////////////// : = ");
        /*
         //System.out.println("countryNeo4jService : = " + countryNeo4jService.findByName("Nigeria6"));

         //System.out.println("countryNeo4jService : = " + countryNeo4jService.findAll());
         List<Country> list = countryNeo4jService.findAll();
         Iterator<Country> iter = list.iterator();
         System.out.println("Country Name2        : ");

         while (iter.hasNext()) {
         Country p = iter.next();
         System.out.println("Country Name2        : " + p.getCountry_name() + "  Description : " + p.getCountry_iso_code_3());
         }
         */

        /////===========

        //ExternalContext extCt = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extContext.getSession(false);
        //set session for new user = 0.
        session.setAttribute("userStatus", getNewUsersStatus());
        session.setAttribute("usersEmailAddress", getUsersEmailAddress());





        Enumeration e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String attr = (String) e.nextElement();
            System.err.println(" attr = " + attr);
            Object value = session.getAttribute(attr);
            System.err.println(" value = " + value);
        }





        /*  if (null == session.getAttribute("loggedIn")) {
         return "false";
         } else {
         return "true";
         }
         */






        /*
         * //clean database.
         neo4jDatabaseCleaner.cleanDb();
         long count = usersServiceNeo4j.getNumberOfUsers();
         System.out.println("users count: = " + count);
         * */


        /*
         System.out.println("passAndConfpassEqual: = " + passAndConfpassEqual());
         ///
         int count = (int) usersServiceNeo4j.getNumberOfUsers();
         System.out.println("users count: = " + count);
         Users user = new Users();
         System.out.println("usersEmailAddress: = " + getUsersEmailAddress());
         //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date date = new Date();
         //System.out.println(dateFormat.format(date));
         user.setUsersId(count+1);
         user.setRoles(ROLE_USER);
         user.setUsersEmailAddress(getUsersEmailAddress());
         user.setUsersFirstname(usersFirstname);
         user.setUsersLastname(usersLastname);
         user.setUsersCompany(usersCompany);
         user.setUsersDor((date));
         String encodedPass = cryptoService.encodePassword(usersPassword);
         user.setUsersPassword(encodedPass);
         // usersServiceNeo4j.insertSelective(user);
         //usersServiceNeo4j.createUser(6, usersEmailAddress, encodedPass, ROLE_USER);
         usersServiceNeo4j.saveUser(user);
         System.out.println("old user name: = " + usersServiceNeo4j.findUserByUsersEmailAddress(usersEmailAddress).getUsersEmailAddress());
         System.out.println("old user UsersId: = " + usersServiceNeo4j.findUserByUsersEmailAddress(usersEmailAddress).getUsersId());
         System.out.println("old user NodeId: = " + usersServiceNeo4j.findUserByUsersEmailAddress(usersEmailAddress).getNodeId());
         System.out.println("old user UsersCompany: = " + usersServiceNeo4j.findUserByUsersEmailAddress(usersEmailAddress).getUsersCompany());
         Iterable<Users> list = usersServiceNeo4j.getAllUsers();
         while (list.iterator().hasNext()) {
         Users u = list.iterator().next();
         System.out.println("NodeId: = " + u.getNodeId());
         System.out.println("UsersId: = " + u.getUsersId());
         System.out.println("UsersEmailAddress: = " + u.getUsersEmailAddress());
         System.out.println("UsersPassword: = " + u.getUsersPassword());
         System.out.println("UsersCompany: = " + u.getUsersCompany());
         System.out.println("Roles: = " + u.getRoles());
         }

         */


        return "index?faces-redirect=true";
        //////

        /*    
         if ("true".equals(passAndConfpassEqual())) {
         ExternalContext extCt = FacesContext.getCurrentInstance().getExternalContext();
         HttpSession session = (HttpSession) extCt.getSession(false);
         //remove these attr if they esixt.
         session.removeAttribute("usersEmailAddress");
         session.removeAttribute("emailError");
         int count = usersService.countByUsersEmailAddress(usersEmailAddress);
         if (count > 0) {
         // System.out.println("count1: = " + count);
         // session.setAttribute("usersEmailAddress", getUsersEmailAddress());
         session.setAttribute("emailError", "emailError");
         //System.out.println("emailError: = " + userCheck.getUsersEmailAddress());
         // System.out.println("emailErrormm: = " + session.getAttribute("emailError"));

         // return "index?faces-redirect=true&emailError=emailError";
         return "index?faces-redirect=true";
         } else {
         System.out.println("count: = " + count);
         Users user = new Users();
         System.out.println("usersEmailAddress: = " + getUsersEmailAddress());
         //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date date = new Date();
         //System.out.println(dateFormat.format(date));
         user.setRolesId(USER_ROLE_ID);
         user.setUsersEmailAddress(getUsersEmailAddress());
         user.setUsersFirstname(usersFirstname);
         user.setUsersLastname(usersLastname);
         user.setUsersCompany(usersCompany);
         user.setUsersDor((date));
         String encodedPass = cryptoService.encodePassword(usersPassword);
         user.setUsersPassword(encodedPass);
         usersService.insertSelective(user);

         session.setAttribute("newUser", usersFirstname);
         session.setAttribute("usersEmailAddress", getUsersEmailAddress());
         return "create_account?faces-redirect=true";
         }
         } else {  
         return null;
         }
         */
    }

    @Transactional
    public String createUserAddress() throws DAOException {
        System.out.println("usersEmailAddress: = : ");
 
       // System.out.println("getSelectedCountry() : " + getUsersGender1());
        System.out.println("getSelectedCountry() : "
                + ""
                + ""
                + ""
                + ""
                + "" + getSelectedCountry());
 System.out.println("ZIP  : " + getEntry_postcode());
  
        //Country country = (Country)getSelectedCountry();
        // ExternalContext extCt = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extContext.getSession(false);
      
  
        Enumeration e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String attr = (String) e.nextElement();
            System.err.println(" attr = " + attr);
            Object value = session.getAttribute(attr);
            System.err.println(" value = " + value);
        }
        //get user name through session.
        String usersEmailAddr = (String) session.getAttribute("usersEmailAddress");
        System.out.println("usersEmailAddress: = : " + usersEmailAddr);

        System.out.println(getEntry_street_address());
        System.out.println(getEntry_city());
        System.out.println(getEntry_phone_default());
        System.out.println(getEntry_phone2());
        System.out.println(getUsersGender());

        //get userId.
 //hope  long userId = getUserNeo4jService().findByName(usersEmailAddr).getId();
  //hope        System.out.println("userId: = : " + userId);
        AddressBook addressBook = new AddressBook();
    //hope     addressBook.setUsers_id(userId);
        addressBook.setEntry_street_address(getEntry_street_address());
        addressBook.setEntry_street_address2(getEntry_street_address2());
        addressBook.setEntry_city(getEntry_city());
        addressBook.setEntry_state(getEntry_state());
        addressBook.setEntry_postcode(getEntry_postcode());
        addressBook.setEntry_country_id(getSelectedCountry().getId());
        addressBook.setEntry_url(getEntry_url());
        addressBook.setEntry_occupation(getEntry_occupation());
        addressBook.setEntry_phone_default(getEntry_phone_default());
        addressBook.setEntry_phone2(getEntry_phone2());

        getAddressBookNeo4jService().save(addressBook);
        List<AddressBook> all = getAddressBookNeo4jService().findAll();
        Iterator<AddressBook> iter = all.iterator();
        while (iter.hasNext()) {
            AddressBook a = iter.next();   
            System.out.println("AddressBook Name address 1      : " + a.getId() + "  : " + a.getEntry_street_address() + "  : " + a.getEntry_state() + "  : " + a.getEntry_country_id());
        }  
        
        
        
 Iterator<Country> iter1 =  countryNeo4jService.findAll().iterator();
         System.out.println("Country Name2   : ");

         while (iter1.hasNext())
         {
         Country p = iter1.next();
         System.out.println("Country Name2        : "+ p.getId()+ " : "+p.getCountry_name()+  "  Description : "+p.getCountry_iso_code_3());
         }
        

        return "index?faces-redirect=true";

    }

    /*
     public List<Country> countryList() throws Exception {
     List<Country> list = countryNeo4jService.findAll();
     return list;
     }
     */
    public List<Country> getCountries() {
        return countryNeo4jService.findAll();
    }

    @NotNull
    @NotBlank
    @Pattern(regexp = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", message = "Bad Email Address")
    public String getUsersEmailAddress() {
        return usersEmailAddress;
    }

    public void setUsersEmailAddress(String usersEmailAddress) {
        this.usersEmailAddress = usersEmailAddress;
    }

    @NotNull
    @NotBlank
    //@Size(min = 6, message = "At least 6 characters!")
    public String getUsersPassword() {
        return usersPassword;
    }

    public void setUsersPassword(String usersPassword) {
        this.usersPassword = usersPassword;
    }

    @NotNull
    @NotBlank
    // @Size(min = 6, message = "At least 6 characters!")
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @NotNull
    @NotBlank
    @Size(min = 1, message = "At least 1 character!")
    public String getUsersFirstname() {
        return usersFirstname;
    }

    public void setUsersFirstname(String usersFirstname) {
        this.usersFirstname = usersFirstname;
    }

    @NotNull
    @NotBlank
    @Size(min = 1, message = "At least 1 character!")
    public String getUsersLastname() {
        return usersLastname;
    }

    public void setUsersLastname(String usersLastname) {
        this.usersLastname = usersLastname;
    }

    @AssertTrue
    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }
  
    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }
    /*
     public List<Country> getCountries() throws Exception {
     List<Country> list = countryNeo4jService.findAll();
     return list;
     }
     */

    public void setCountries(List<Country> countries) {
        this.countries = countries;
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

    public void setUserNeo4jService(UserNeo4jServiceImpl userNeo4jService) {
        this.userNeo4jService = userNeo4jService;
    }

    public CountryNeo4jService getCountryNeo4jService() {
        return countryNeo4jService;
    }

    public void setCountryNeo4jService(CountryNeo4jService countryNeo4jService) {
        this.countryNeo4jService = countryNeo4jService;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNewUsersStatus() {
        return newUsersStatus;
    }

    public void setNewUsersStatus(int newUsersStatus) {
        this.newUsersStatus = newUsersStatus;
    }

    public String getUsersDob() {
        return usersDob;
    }

    public void setUsersDob(String usersDob) {
        this.usersDob = usersDob;
    }

    public String getEntry_street_address() {
        return entry_street_address;
    }

    public void setEntry_street_address(String entry_street_address) {
        this.entry_street_address = entry_street_address;
    }

    public String getEntry_street_address2() {
        return entry_street_address2;
    }

    public void setEntry_street_address2(String entry_street_address2) {
        this.entry_street_address2 = entry_street_address2;
    }

    public String getEntry_city() {
        return entry_city;
    }

    public void setEntry_city(String entry_city) {
        this.entry_city = entry_city;
    }

    public String getEntry_state() {
        return entry_state;
    }

    public void setEntry_state(String entry_state) {
        this.entry_state = entry_state;
    }

    public String getEntry_postcode() {
        return entry_postcode;
    }

    public void setEntry_postcode(String entry_postcode) {
        this.entry_postcode = entry_postcode;
    }

    public String getEntry_country_id() {
        return entry_country_id;
    }

    public void setEntry_country_id(String entry_country_id) {
        this.entry_country_id = entry_country_id;
    }

    public String getEntry_fax() {
        return entry_fax;
    }

    public void setEntry_fax(String entry_fax) {
        this.entry_fax = entry_fax;
    }

    public String getEntry_url() {
        return entry_url;
    }

    public void setEntry_url(String entry_url) {
        this.entry_url = entry_url;
    }

    public String getEntry_phone_default() {
        return entry_phone_default;
    }

    public void setEntry_phone_default(String entry_phone_default) {
        this.entry_phone_default = entry_phone_default;
    }

    public String getEntry_phone2() {
        return entry_phone2;
    }

    public void setEntry_phone2(String entry_phone2) {
        this.entry_phone2 = entry_phone2;
    }

    public AddressBookNeo4jService getAddressBookNeo4jService() {
        return addressBookNeo4jService;
    }

    public void setAddressBookNeo4jService(AddressBookNeo4jService addressBookNeo4jService) {
        this.addressBookNeo4jService = addressBookNeo4jService;
    }

    public String getEntry_occupation() {
        return entry_occupation;
    }

    public void setEntry_occupation(String entry_occupation) {
        this.entry_occupation = entry_occupation;
    }

    public String getUsersGender() {
        return usersGender;
    }

    public void setUsersGender(String usersGender) {
        this.usersGender = usersGender;
    }

    
}
