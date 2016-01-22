/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.hopecee.proshopnew.events.ExceptionEventBadToken;
import com.hopecee.proshopnew.jpa.services.UserJpaService;
import com.hopecee.proshopnew.neo4j.jdo.model.Country;
import com.hopecee.proshopnew.neo4j.jdo.model.User;
import com.hopecee.proshopnew.neo4j.jdo.services.AddressBookNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.CountryNeo4jService;
import com.hopecee.proshopnew.neo4j.jdo.services.UserNeo4jService;
import com.hopecee.proshopnew.util.javacryption.aes.AesCtr;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.jboss.seam.transaction.Transactional;
import org.picketlink.idm.common.exception.IdentityException;

/**
 *
 * @author hope
 */
@WebServlet(value = "/tuCountryListServlet", name = "tuCountryListServlet")
public class TuCountryListServlet extends HttpServlet {

    private static final long serialVersionUID = 6803110328493486633L;
    public static final String JSON = "application/json";
    private final String BAD_TOKEN = "BadToken";
    @Inject
    Event<ExceptionEventBadToken> exceptionEventBadToken;
    @Inject
    private CountryNeo4jService countryNeo4jService;
    @Inject
    private AesCtr aesCtr;
    Map<Object, Object> map = new LinkedHashMap<>();

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
            token = req.getAttribute("CheckRequestVerificationToken").toString();
        } catch (NullPointerException e) {
            Logger.getLogger(TuCountryListServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        if (token.equals(BAD_TOKEN)) {
            //getBadToken(req, resp);
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
        } else {
            //  String RequestVerificationToken = req.getParameter("RequestVerificationToken");
            System.out.println("DDDDDDDDD = ,,,,,,,,,,,,,,,,,,,,,,,,");
            String method = req.getParameter("method");

            /* try {*/
            if ("countryList".equals(method)) {
                try {
                    supplyCountryList(req, resp);
                } catch (Exception ex) {
                    Logger.getLogger(TuCountryListServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // @Transactional
    public void supplyCountryList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        try {
            Map<Object, Object> countryIdNameMap = new LinkedHashMap<>();

            countryIdNameMap.clear(); //Clear First.
            for (Iterator<Country> it = getCountryList().iterator(); it.hasNext();) {
                Country c = it.next();
                countryIdNameMap.put(c.getCountry_iso_code_3(), c.getCountry_name()); // TODO which one is needed here, ID or name?
                System.out.println("ID       : " + c.getId() + "Name : " + c.getCountry_name()
                        + "Name2 : " + c.getCountry_iso_code_2()
                        + "Name3 : " + c.getCountry_iso_code_3());
            }
            map.clear(); //Clear First.
            map.put("countryData", countryIdNameMap);
            aesCtr.encryptMap(req, map);//encrypt the Map data.

            String json = new Gson().toJson(map);
            resp.setContentType(JSON);
            resp.getWriter().print(json);

        } catch (IllegalArgumentException | IllegalStateException | IOException ex) {
            Logger.getLogger(TuCountryListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Country> getCountryList() {
        List<Country> list = countryNeo4jService.findAll();
        return list;
    }

    public CountryNeo4jService getCountryNeo4jService() {
        return countryNeo4jService;
    }

    public void setCountryNeo4jService(CountryNeo4jService countryNeo4jService) {
        this.countryNeo4jService = countryNeo4jService;
    }
}
