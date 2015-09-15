/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.servlets;

import com.hopecee.proshopnew.neo4j.jdo.model.User;
import com.hopecee.proshopnew.neo4j.jdo.model.UserQuestion;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import com.hopecee.proshopnew.neo4j.jdo.services.UserNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.UserQuestionNeo4jService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Event;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jboss.seam.transaction.ExceptionEventRollback;
import org.jboss.seam.transaction.Transactional;
import org.picketlink.idm.common.exception.IdentityException;

/**
 *
 * @author hope
 */
@WebServlet(value = "/joinEditor3Servlet", name = "joinEditor3Servlet")
public class JoinEditor3Servlet extends HttpServlet {

    private static final long serialVersionUID = 937096710183005490L;
    private ResourceBundle bundle = ResourceBundle.getBundle("constants");
    private final String SIMPLE_DATE_FORMAT = bundle.getString("simpleDateFormat");
    private final String USER_DATE_FORMAT = bundle.getString("userDateFormat");
    private String usersEmailAddress = "usersEmailAddress";
    private String time;
    
    
    
    ////=================//
    @Inject
    Event<ExceptionEventRollback> exceptionEvent;
    
    @Inject
    private ExternalContext extContext;
    @Inject
    private UserNeo4jService userNeo4jService;
    @Inject
    private UserQuestionNeo4jService userQuestionNeo4jService;
    //private Calendar  calendar = null;
   /* =========================*/
    /* userStatus
     * new = 1,
     * new2 = 2,
     * new3  = 3,
     * confirmed = 4,
     * baned = 0,
     */
    private int newUsersStatus = 3;
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


        // this parses the json

        /*
         // 1. get received JSON data from request
         BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
         String json = "";
         if(br != null){
         json = br.readLine();
         }
         */
        System.out.println("hhhhhh = ,,,,,,,,,,f,,,,,,,,,,,,,,");

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

        ////==============/

        createNewUserQuestion(req, resp, dob, selectedQuestion1, answer1, selectedQuestion2, answer2, pin);



    }

    //@Transactional
    public void createNewUserQuestion(HttpServletRequest req, HttpServletResponse resp, String dob, String selectedQuestion1, String answer1, String selectedQuestion2, String answer2, String pin) {
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
            HttpSession session = req.getSession();
            String userEmailAdd = (String) session.getAttribute(usersEmailAddress);
            // System.out.println("cid tm : " + userEmailAdd);
            //System.out.println("newDateString 2: " + newDateString);

            User user = userNeo4jService.findByName(userEmailAdd);
            userQuestion.setUsers_id(user.getId());

            userQuestionNeo4jService.save(userQuestion);




            //update User for neo4j DB. 
            //convert USER_DATE_FORMAT to Date.
            SimpleDateFormat formatter = new SimpleDateFormat(USER_DATE_FORMAT); // 2001-07-04T12:08:56.235-0700
            Date date = formatter.parse(dob);

            //convert to appropriate SIMPLE_DATE_FORMAT.
            String normalNewDateString = new SimpleDateFormat(SIMPLE_DATE_FORMAT).format(date); // 2001-07-04T12:08:56.235-0700
            //System.out.println("mk : " + normalNewDateString);
            user.setUsersDob(normalNewDateString);
            user.setLast_modified(newDateString);
            // update userStatus.
            user.setUsersStatus(newUsersStatus);

            userNeo4jService.update(user);


            ////////================///////////



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
            Logger.getLogger(JoinEditor3Servlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            exceptionEvent.fire(new ExceptionEventRollback());
            Logger.getLogger(JoinEditor3Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public UserNeo4jService getUserNeo4jService() {
        return userNeo4jService;
    }

    public void setUserNeo4jService(UserNeo4jService userNeo4jService) {
        this.userNeo4jService = userNeo4jService;
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
