/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.events;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hope
 */
public class ExceptionEventBadToken implements Serializable {

    private static final long serialVersionUID = 410803969690989324L;
    public static final String JSON = "application/json";
    private final String BAD_TOKEN = "BadToken";
    @Inject
    HttpServletRequest req;
    @Inject
    HttpServletResponse resp;

    public ExceptionEventBadToken(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    public void getBadToken() throws IOException {
        Map<Object, Object> map = new LinkedHashMap<Object, Object>();
        map.put(BAD_TOKEN, BAD_TOKEN);
        //map.put("BAD", "BAD");
        String json = new Gson().toJson(map);
        resp.setContentType(JSON);
        System.out.println("getBadToken ======");
        resp.getWriter().print(json);
    }
}
