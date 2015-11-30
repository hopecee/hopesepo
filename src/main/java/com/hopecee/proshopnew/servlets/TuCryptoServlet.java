/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.servlets;

/**
 *
 * @author hope
 */
import com.google.gson.Gson;
import com.hopecee.proshopnew.util.javacryption.aes.AesCtr;
import com.hopecee.proshopnew.util.javacryption.jcryption.JCryption;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedHashMap;
import java.util.Map;
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
 * Servlet example for jCryption
 *
 * @author Gabriel Andery
 * @version 1.0
 */
@WebServlet(name = "tuCryptoServlet", urlPatterns = {"/tuCryptoServlet"})
public class TuCryptoServlet extends HttpServlet {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4510110365995157499L;
    public static final String JSON = "application/json";
    private final String BAD_TOKEN = "BadToken";
    Map<Object, Object> map = new LinkedHashMap<>();
    Map<Object, Object> objMap = new LinkedHashMap<>();
    @Inject
    private AesCtr aesCtr;

    /**
     * Handles a POST request
     *
     * @see HttpServlet
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException, FileNotFoundException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String token = "";
        try {
            token = ((ServletRequest) req).getAttribute("CheckRequestVerificationToken").toString();
        } catch (Exception e) {
        }
        if (token.equals(BAD_TOKEN)) {
            map.put("isLoggedIn", "false");//TO_DO is it necessry?
            map.put(BAD_TOKEN, BAD_TOKEN);
            String json = new Gson().toJson(map);
            res.setContentType(JSON);
            res.getWriter().print(json);

        } else {
           // String method = req.getParameter("method");

            /*
             if (req.getParameter("hope1") != null) {
             try {
             String text = req.getParameter("hope1");
             //System.out.println("hopewd: " + text);
             String key =  request.getSession().getServletContext().getAttribute("jCryptionKey").toString();
             String decrypted = aesCtr.decrypt(key, text);
             System.out.println("text : " + decrypted);
                   
                    
             } catch (Exception ex) {
             Logger.getLogger(TuCryptoServlet.class.getName()).log(Level.SEVERE, null, ex);
             }
             }
             */
            /**
             * Generates a KeyPair for RSA *
             */
            if (req.getParameter("generateKeyPair") != null && req.getParameter("generateKeyPair").equals("true")) {
                JCryption jc = new JCryption();
                jc.generateKeyPair();
                KeyPair keys = jc.getKeyPair();
                request.getSession().getServletContext().setAttribute("jCryptionKeys", keys);
                String pub = null;
                try {
                    pub = jc.getPublicKeyPEM();
                } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    Logger.getLogger(TuCryptoServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                /**
                 * Sends response *
                 */
                objMap.clear();//Clear First.
                objMap.put("publickey", pub);

                String json = new Gson().toJson(objMap);
                response.setContentType(JSON);
                PrintWriter out = response.getWriter();
                out.print(json);

            } else if (req.getParameter("handshake") != null && req.getParameter("handshake").equals("true")) {

                /**
                 * Decrypts password using private key *
                 */
                JCryption jc = new JCryption((KeyPair) request.getSession()
                        .getServletContext().getAttribute("jCryptionKeys"));
                String key = jc.decrypt(req.getParameter("key"));

                // System.out.println("DDDDDDDDDhmhj =jc.decrypt : " + key);

                request.getSession().getServletContext()
                        .removeAttribute("jCryptionKeys");
                request.getSession().getServletContext()
                        .setAttribute("jCryptionKey", key);

                /**
                 * Encrypts password using AES *
                 */
                String ct = null;
                try {
                    ct = aesCtr.encrypt(key, key);
                } catch (Exception ex) {
                    Logger.getLogger(TuCryptoServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                /**
                 * Sends response *
                 */
                // PrintWriter out = response.getWriter();
                // out.print("{\"challenge\":\"" + ct + "\"}");
                // return;
                //bject obj = new Object();
                //map.clear(); //Clear First.
                objMap.clear();//Clear First.
                objMap.put("challenge", ct);
                // map.put("maxdigits", md);
                
                String json = new Gson().toJson(objMap);
                response.setContentType(JSON);
                PrintWriter out = response.getWriter();
                out.print(json);
                //  out.print("{\"e\":\"" + e + "\",\"n\":\"" + n
                //        + "\",\"maxdigits\":\"" + md + "\"}");

            }






            /**
             * jCryption handshake *
             */
            /*
             else if (req.getParameter("handshake") != null
             && req.getParameter("handshake").equals("true")) {

             /**
             * Decrypts password using private key *
             */
            /*
             JCryption jc = new JCryption((KeyPair) request.getSession()
             .getServletContext().getAttribute("jCryptionKeys"));
             String key = jc.decrypt(req.getParameter("key"));

             request.getSession().getServletContext()
             .removeAttribute("jCryptionKeys");
             request.getSession().getServletContext()
             .setAttribute("jCryptionKey", key);
             */
            /**
             * Encrypts password using AES *
             */
            /*
             String ct = AesCtr.encrypt(key, key, 256);
             */
            /**
             * Sends response * / response.setContentType(JSON); PrintWriter out
             * = response.getWriter(); out.print("{\"challenge\":\"" + ct +
             * "\"}");
             *
             * //return; } / ** jCryption request to decrypt a String * / else
             * if (req.getParameter("decryptData") != null &&
             * req.getParameter("decryptData").equals("true") &&
             * req.getParameter("jCryption") != null) {
             *
             * / **
             * Decrypts the request using password * / String key = (String)
             * request.getSession().getServletContext()
             * .getAttribute("jCryptionKey");
             *
             * String pt = AesCtr.decrypt(req.getParameter("jCryption"), key,
             * 256);
             *
             * / **
             * Sends response * / response.setContentType(JSON); PrintWriter out
             * = response.getWriter(); out.print("{\"data\":\"" + pt + "\"}");
             * //return; } / ** jCryption request to encrypt a String * / else
             * if (req.getParameter("encryptData") != null &&
             * req.getParameter("encryptData").equals("true") &&
             * req.getParameter("jCryption") != null) {
             *
             * / **
             * Encrypts the request using password * / String key = (String)
             * request.getSession().getServletContext()
             * .getAttribute("jCryptionKey");
             *
             * String ct = AesCtr.encrypt(req.getParameter("jCryption"), key,
             * 256);
             *
             * / **
             * Sends response * / response.setContentType(JSON); PrintWriter out
             * = response.getWriter(); out.print("{\"data\":\"" + ct + "\"}");
             * //return; } / ** A test request from jCryption * / else if
             * (req.getParameter("decryptTest") != null &&
             * req.getParameter("decryptTest").equals("true")) {
             *
             * / **
             * Encrypts a timestamp * / String key = (String)
             * request.getSession().getServletContext()
             * .getAttribute("jCryptionKey");
             *
             * String date = DateFormat.getInstance().format(new Date());
             *
             * String ct = AesCtr.encrypt(date, key, 256);
             *
             * / **
             * Sends response * / response.setContentType(JSON); PrintWriter out
             * = response.getWriter(); out.print("{\"encrypted\":\"" + ct + "\",
             * \"unencrypted\":\"" + date + "\"}"); //return; }
             *
             * }* /
             * }
             */
        }
    }

    /**
     * Handles a GET request
     *
     * @see HttpServlet
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        
       // doPost(req, res);
    }
}
