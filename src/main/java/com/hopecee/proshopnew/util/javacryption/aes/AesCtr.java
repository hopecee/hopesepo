/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.util.javacryption.aes;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.codec.binary.Base64;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
//import org.apache.commons.codec.binary.Base64;
//import java.util.Base64;
//import java.util.Base64.Decoder;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author hope
 */
public class AesCtr implements Serializable {

    private static final long serialVersionUID = 9049769337247077163L;
    private static final Charset CHAR_SET = StandardCharsets.UTF_8;
    private static final byte[] salted = "Salted__".getBytes(CHAR_SET);
    private static final int DECRYPT_MODE = Cipher.DECRYPT_MODE;
    private static final int ENCRYPT_MODE = Cipher.ENCRYPT_MODE;

    public String encrypt(String text, String password) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = random.generateSeed(8);
        byte[] pass = password.getBytes(CHAR_SET);

        Cipher cipher = createCipher(ENCRYPT_MODE, pass, salt);
        byte[] encrypted = cipher.doFinal(text.getBytes(CHAR_SET));
        byte[] saltedAndSalt = concat(salted, salt);
        byte[] saltedAndSaltAndEncrypted = concat(saltedAndSalt, encrypted);
        //return new String(Base64.encodeBase64(saltedAndSaltAndEncrypted));
        return //new String(
                DatatypeConverter.printBase64Binary(saltedAndSaltAndEncrypted);
    }

    public String decrypt(String text, String password) throws Exception {
        System.out.println(text + "  text ========hh0 : " + text.toString());

        // byte[] data = Base64.decodeBase64(text);
        byte[] data = DatatypeConverter.parseBase64Binary(text);
       
        byte[] salt = Arrays.copyOfRange(data, 8, 16);
        byte[] ct = Arrays.copyOfRange(data, 16, data.length);

        byte[] pass = password.getBytes(CHAR_SET);

        byte[] shouldBeMagic = Arrays.copyOfRange(data, 0, salted.length);
        if (!Arrays.equals(shouldBeMagic, salted)) {
            System.out.println("Bad magic number");
            return null;
        }

        Cipher cipher = createCipher(DECRYPT_MODE, pass, salt);
        byte[] decrypted = cipher.doFinal(ct);
        String clearText = new String(decrypted, CHAR_SET);
        return clearText;
    }

    private Cipher createCipher(int cipherMode, byte[] pass, byte[] salt)
            throws Exception {

        byte[] passAndSalt = concat(pass, salt);

        byte[] hash = new byte[0];
        byte[] keyAndIv = new byte[0];
        for (int i = 0; i < 3; i++) {
            byte[] data_1 = concat(hash, passAndSalt);
            final MessageDigest md = MessageDigest.getInstance("MD5");
            hash = md.digest(data_1);
            keyAndIv = concat(keyAndIv, hash);
        }

        byte[] keyValue = Arrays.copyOfRange(keyAndIv, 0, 32);
        byte[] iv = Arrays.copyOfRange(keyAndIv, 32, 48);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(keyValue, "AES");
        cipher.init(cipherMode, key, new IvParameterSpec(iv));

        return cipher;
    }

    public void encryptMap(HttpServletRequest req, Map<Object, Object> map) throws Exception {
        String k = req.getSession().getServletContext().getAttribute("jCryptionKey").toString();
        Map<Object, Object> holdMap = new LinkedHashMap<>();
        Map<Object, Object> holdMap_2 = new LinkedHashMap<>();
        Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> obj = it.next();
            String o = obj.getKey().toString();
            Object val = obj.getValue();
            if (val instanceof Map) {
                Map<Object, Object> v = (Map<Object, Object>) obj.getValue();
                Iterator<Map.Entry<Object, Object>> it_2 = v.entrySet().iterator();
                while (it_2.hasNext()) {
                    Map.Entry<Object, Object> obj_2 = it_2.next();
                    String o_2 = obj_2.getKey().toString();
                    Object val_2 = obj_2.getValue();
                    if (val_2 instanceof Map) {
                        //TODO NOT USED YET.
                    } else {
                        String enc_2 = encrypt(val_2.toString(), k);
                        holdMap_2.put(o_2, enc_2);
                        holdMap.put(o, holdMap_2);
                    }
                }
            } else if (val instanceof List) {
                List customersArr = new ArrayList();
                customersArr.clear();  //Clear First.

                List list = (List) obj.getValue();
                Iterator it3 = list.iterator();
                while (it3.hasNext()) {

                    Map<Object, Object> holdMap_3 = new LinkedHashMap<>();
                    holdMap_3.clear();//Clear First.


                    Map<Object, Object> custMap = (Map<Object, Object>) it3.next();
                    Iterator<Map.Entry<Object, Object>> itc = custMap.entrySet().iterator();
                    while (itc.hasNext()) {
                        Map.Entry<Object, Object> objc = itc.next();
                        String oc = objc.getKey().toString();
                        Object valc = objc.getValue();
                        if (valc instanceof Map) {
                            // System.out.println(oc + "  valc  :objc: map ");
                        } else {
                            String encc = encrypt(String.valueOf(valc), k);
                            holdMap_3.put(oc, encc);
                        }
                    }
                    customersArr.add(holdMap_3);
                }
                holdMap.put(o, customersArr);
            } else {
                String enc = encrypt(val.toString(), k);
                holdMap.put(o, enc);
            }
        }
        //Clear and add the map from holdMap.
        map.clear();
        map.putAll(holdMap);
    }

    private byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
}