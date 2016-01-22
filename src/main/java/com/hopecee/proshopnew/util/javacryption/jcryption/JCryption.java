/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.util.javacryption.jcryption;

import com.hopecee.proshopnew.util.javacryption.exception.CryptoException_NOOT;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

//import org.apache.commons.codec.binary.Base64;
/**
 *
 * @author hope
 */
public class JCryption implements Serializable {

    private static final long serialVersionUID = -7309454762625393691L;
    private KeyPair keyPair = null;
    private int keyLength = 1024;

    public void generateKeyPair() {
        try {
            SecureRandom random = new SecureRandom();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(this.keyLength, random);
            this.keyPair = kpg.generateKeyPair();
            //this.keyLength = keyLength;
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException_NOOT("Error obtaining RSA algorithm", e);
        }
    }
    /*
     public JCryption() {
     generateKeyPair();
     }

     public JCryption(int keyLength) {
     generateKeyPair();
     }

     public JCryption(KeyPair keyPair) {
     setKeyPair(keyPair);
     }

     public KeyPair getKeyPair() {
     return this.keyPair;
     }

     public void setKeyPair(KeyPair keyPair) {
     this.keyPair = keyPair;
     this.keyLength = ((RSAPublicKey) keyPair.getPublic()).getModulus()
     .bitLength();
     }

     public int getKeyLength() {
     return this.keyLength;
     }
     */

    public String getPrivateKeyPEM() throws NoSuchAlgorithmException, InvalidKeySpecException, FileNotFoundException, IOException {
        //PrivateKey privateKey = this.keyPair.getPrivate();
        RSAPrivateKey privateKey = (RSAPrivateKey) this.keyPair.getPrivate();

        BigInteger modulus = privateKey.getModulus();
        BigInteger expo = privateKey.getPrivateExponent();

        KeyFactory f = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec spec = new RSAPrivateKeySpec(modulus, expo);
        RSAPrivateKey pvt = (RSAPrivateKey) f.generatePrivate(spec);
        byte[] data = pvt.getEncoded();
        String base64encoded = DatatypeConverter.printBase64Binary(data);
        System.out.println(base64encoded);

        String privatePEM = //"-----BEGIN RSA PRIVATE KEY-----\n" +
                DatatypeConverter.printBase64Binary(privateKey.getEncoded())// +
                //"\n-----END RSA PRIVATE KEY-----\n"
                ;
        /*
         FileOutputStream fos1 = new FileOutputStream("privateKey");
         StringWriter stringWriter = new StringWriter();
         stringWriter.write(privatePEM);
         // PEMWriter pemFormatWriter = new PEMWriter(stringWriter);
         // pemFormatWriter.writeObject(keyPair.getPublic());

         fos1.write(stringWriter.toString().getBytes());
         System.out.println("hop  pub: " + stringWriter.toString());

         */
        return privatePEM;
    }

    public String getPublicKeyPEM() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        BigInteger modulus = publicKey.getModulus();
        BigInteger expo = publicKey.getPublicExponent();

        KeyFactory f = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, expo);
        RSAPublicKey pub = (RSAPublicKey) f.generatePublic(spec);
        byte[] data = pub.getEncoded();
        String base64encoded = DatatypeConverter.printBase64Binary(data);
        //new String(Base64.encodeBase64(data));
        //System.out.println(base64encoded);


        String publicPEM = //"-----BEGIN PUBLIC KEY-----\n" +
                DatatypeConverter.printBase64Binary(publicKey.getEncoded())// +
                //"\n-----END PUBLIC KEY-----\n"
                ;
        /*
         //JDKKeyPairGenerator.RSA keyPairGen = new JDKKeyPairGenerator.RSA();
         //keyPairGen.initialize(RSA_KEY_STRENGTH);
         //KeyPair keyPair = keyPairGen.generateKeyPair();
         FileOutputStream fos = new FileOutputStream("publicKey");
         StringWriter stringWriter = new StringWriter();
         stringWriter.write(publicPEM);
         // PEMWriter pemFormatWriter = new PEMWriter(stringWriter);
         // pemFormatWriter.writeObject(keyPair.getPublic());

         fos.write(stringWriter.toString().getBytes());
         System.out.println("hop  publict===: " + stringWriter.toString());

         */

        return publicPEM;
    }

    /*
     public String getKeyModulus() {
     RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
     return publicKey.getModulus().toString(16);
     }

     public String getPublicExponent() {
     RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
     return publicKey.getPublicExponent().toString(16);
     }

     public int getMaxDigits() {
     return this.keyLength * 2 / 16 + 3;
     }
     */
    public String decrypt(String encrypted) {
        RSAPrivateKey privateKey = (RSAPrivateKey) this.keyPair.getPrivate();
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            String str = new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(encrypted) //Base64.decodeBase64(encrypted)
                    ), "UTF-8");

            return str;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            Logger.getLogger(JCryption.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public int getKeyLength() {
        return keyLength;
    }

    public void setKeyLength(int keyLength) {
        this.keyLength = keyLength;
    }
}