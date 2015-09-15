/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.servlets;

import com.google.gson.Gson;
import com.hopecee.proshopnew.neo4j.jdo.model.Configuration;
import com.hopecee.proshopnew.neo4j.jdo.model.ConfigurationGroup;
import com.hopecee.proshopnew.neo4j.jdo.services.ConfigurationGroupNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.DAOException;
import static com.hopecee.proshopnew.servlets.TuJoinEditorServlet.JSON;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hope
 */
@WebServlet(value = "/tuQuestionListServlet", name = "tuQuestionListServlet")
public class TuQuestionListServlet extends HttpServlet {

    private static final long serialVersionUID = -5387247532833892667L;
    private ResourceBundle bundle = ResourceBundle.getBundle("constants");
    private final String CONFIGURATION_GROUP_TITTLE = bundle.getString("userSecurityQuestions");
    private final String BAD_TOKEN = "BadToken";
    @Inject
    private ConfigurationGroupNeo4jService configurationGroupNeo4jService;
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
            map.put(BAD_TOKEN, BAD_TOKEN);
            String json = new Gson().toJson(map);
            resp.setContentType(JSON);
            resp.getWriter().print(json);
        } else {
            //  String RequestVerificationToken = req.getParameter("RequestVerificationToken");
            String method = req.getParameter("method");

            try {
                if ("getAllQuestions".equals(method)) {
                    System.out.println("====getAllQuestions==== : ");
                    getAllQuestions(req, resp);
                } else {
                    
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




            } catch (Exception ex) {
                System.out.println("====njmk=  map.put(BAD_TOKEN, BAD_TOKEN); ");

                map.put(BAD_TOKEN, BAD_TOKEN);
                String json = new Gson().toJson(map);
                resp.setContentType(JSON);
                resp.getWriter().print(json);

                Logger.getLogger(TuJoinEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getAllQuestions(HttpServletRequest req, HttpServletResponse resp) {
        //System.out.println("hhhhhh = ,,,,,,,,,,f,,,,,,,,,,,,,,");
        try {
            List<Configuration> questions = getQuestionList();

            Map<Object, Object> questionIdNameMap = new LinkedHashMap<Object, Object>();
            Iterator<Configuration> iter = questions.iterator();
            while (iter.hasNext()) {
                Configuration c = iter.next();
                questionIdNameMap.put(c.getId(), c.getConfiguration_value());  // TODO which one is needed here, ID or tittle?
                System.out.println("Name        : " + c.getConfiguration_value() + "ID: " + c.getId());
            }
            // Map<Object,Object> sessionData = new LinkedHashMap<Object, Object>();
            //sessionData.put("name", "Hope");
            // sessionData.put("name1", "Hope1");
            // sessionData.put("name2", "Hope2");
            //Map<Object, Object> map = new LinkedHashMap<Object, Object>();
            map.put("data", questionIdNameMap);
            // map.put("sessionData", sessionData);

            String json = new Gson().toJson(map);
            resp.setContentType("application/json");
            resp.getWriter().print(json);


        } catch (IOException ex) {
            //exceptionEvent.fire(new ExceptionEventRollback());
            Logger.getLogger(TuQuestionListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    // @Transactional
    public List<Configuration> getQuestionList() {
        ConfigurationGroup confGrp = configurationGroupNeo4jService.findByName(CONFIGURATION_GROUP_TITTLE);
        List<Configuration> questionList = confGrp.getConfigurations();
        return questionList;
    }

    public ConfigurationGroupNeo4jService getConfigurationGroupNeo4jService() {
        return configurationGroupNeo4jService;
    }

    public void setConfigurationGroupNeo4jService(ConfigurationGroupNeo4jService configurationGroupNeo4jService) {
        this.configurationGroupNeo4jService = configurationGroupNeo4jService;
    }
}
