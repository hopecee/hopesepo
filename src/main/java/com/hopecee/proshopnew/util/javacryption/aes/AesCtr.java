/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.util.javacryption.aes;

import com.hopecee.proshopnew.util.javacryption.exception.CryptoException_NOOT;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.codec.binary.Base64;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;
import org.apache.commons.codec.Decoder;
import org.apache.commons.codec.binary.Base64;
//import java.util.Base64;
//import java.util.Base64.Decoder;

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

        /*
         byte[] passAndSalt = concat(pass, salt);

         byte[] hash = new byte[0];
         byte[] keyAndIv = new byte[0];
         for (int i = 0; i < 3; i++) {
         byte[] data = concat(hash, passAndSalt);
         MessageDigest md = MessageDigest.getInstance("MD5");
         hash = md.digest(data);
         keyAndIv = concat(keyAndIv, hash);
         }
         //Salt the key(32) and iv(16) = 48
         byte[] keyValue = Arrays.copyOfRange(keyAndIv, 0, 32);
         byte[] iv = Arrays.copyOfRange(keyAndIv, 32, 48);
         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
         SecretKeySpec key = new SecretKeySpec(keyValue, "AES");
         cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
         */
        Cipher cipher = createCipher(ENCRYPT_MODE, pass, salt);
        byte[] encrypted = cipher.doFinal(text.getBytes(CHAR_SET));
        byte[] saltedAndSalt = concat(salted, salt);
        byte[] saltedAndSaltAndEncrypted = concat(saltedAndSalt, encrypted);
        return new String(Base64.encodeBase64(saltedAndSaltAndEncrypted));
    }

    public String decrypt(String text, String password) throws Exception {
        //System.out.println("data ========  : " + text);

        byte[] data = Base64.decodeBase64(text);
       // System.out.println(text + "  data ========  : " + data.toString());
       // System.out.println("data ========h  : " + data.length);

        byte[] salt = Arrays.copyOfRange(data, 8, 16);
        byte[] ct = Arrays.copyOfRange(data, 16, data.length);

        byte[] pass = password.getBytes(CHAR_SET);

        byte[] shouldBeMagic = Arrays.copyOfRange(data, 0, salted.length);
        if (!Arrays.equals(shouldBeMagic, salted)) {
            System.out.println("Bad magic number");
            return null;
        }


        /*
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
         cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
         */
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
        //byte[] result = cipher.doFinal(data);

        return cipher;


    }

    ;
/*
 public static String encryptz(String plaintext, String password, int nBits) {
 Rijndael aes = new Rijndael();

 if ((nBits != 128) && (nBits != 192) && (nBits != 256)) {
 throw new CryptoException_NOOT("Invalid key size (" + nBits + " bits)");
 }

 int nBytes = nBits / 8;
 byte[] pwBytes = new byte[nBytes];

 for (int i = 0; i < nBytes; i++) {
 pwBytes[i] = (byte) password.charAt(i);
 }

 aes.makeKey(pwBytes, 256, 1);
 byte[] key = aes.encryptBlock(pwBytes, new byte[16]);
 aes.finalize();

 if (nBytes > 16) {
 byte[] keySlice = new byte[nBytes - 16];
 for (int i = 0; i < nBytes - 16; i++) {
 keySlice[i] = key[i];
 }
 key = Util.addByteArrays(key, keySlice);
 }

 byte[] counterBlock = new byte[16];

 long nonce = new Date().getTime();
 int nonceMs = (int) nonce % 1000;
 int nonceSec = (int) Math.floor(nonce / 1000L);
 int nonceRnd = (int) Math.floor(Math.random() * 65535.0D);

 for (int i = 0; i < 2; i++) {
 counterBlock[i] = (byte) (nonceMs >>> i * 8 & 0xFF);
 }
 for (int i = 0; i < 2; i++) {
 counterBlock[(i + 2)] = (byte) (nonceRnd >>> i * 8 & 0xFF);
 }
 for (int i = 0; i < 4; i++) {
 counterBlock[(i + 4)] = (byte) (nonceSec >>> i * 8 & 0xFF);
 }

 byte[] ctrTxt = new byte[8];
 for (int i = 0; i < 8; i++) {
 ctrTxt[i] = counterBlock[i];
 }

 aes.makeKey(key, 256, 1);

 int blockCount = (int) Math.ceil(new Float(plaintext.length()).floatValue()
 / 16.0F);

 byte[] ciphertxt = new byte[plaintext.length()];

 for (int b = 0; b < blockCount; b++) {
 for (int c = 0; c < 4; c++) {
 counterBlock[(15 - c)] = (byte) (b >>> c * 8 & 0xFF);
 }
 for (int c = 0; c < 4; c++) {
 counterBlock[(15 - c - 4)] = 0;
 }

 byte[] cipherCntr = aes.encryptBlock(counterBlock,
 new byte[16]);

 int blockLength = b < blockCount - 1 ? 16
 : (plaintext.length() - 1) % 16 + 1;

 for (int i = 0; i < blockLength; i++) {
 ciphertxt[(b * 16 + i)] =
 (byte) (cipherCntr[i] ^ plaintext
 .charAt(b * 16 + i));
 }

 }

 aes.finalize();

 byte[] ciphertext = Util.addByteArrays(ctrTxt, ciphertxt);

 String ciphertext64 = new String(Base64.encodeBase64(ciphertext));

 return ciphertext64;
 }

 public static String decryptz(String ciphertext, String password, int nBits) {
 Rijndael aes = new Rijndael();

 if ((nBits != 128) && (nBits != 192) && (nBits != 256)) {
 return null;
 }

 byte[] cipherByte = Base64.decodeBase64(ciphertext.getBytes());

 int nBytes = nBits / 8;
 byte[] pwBytes = new byte[nBytes];

 for (int i = 0; i < nBytes; i++) {
 pwBytes[i] = (byte) password.charAt(i);
 }

 aes.makeKey(pwBytes, 256, 1);
 byte[] key = aes.encryptBlock(pwBytes, new byte[16]);
 aes.finalize();

 if (nBytes > 16) {
 byte[] keySlice = new byte[nBytes - 16];
 for (int i = 0; i < nBytes - 16; i++) {
 keySlice[i] = key[i];
 }
 key = Util.addByteArrays(key, keySlice);
 }

 byte[] counterBlock = new byte[16];
 for (int i = 0; i < 8; i++) {
 counterBlock[i] = cipherByte[i];
 }

 aes.makeKey(key, 256, 1);

 int blockCount = (int) Math.ceil(new Float(cipherByte.length - 8).floatValue()
 / 16.0F);

 byte[] plaintxt = new byte[cipherByte.length - 8];

 for (int b = 0; b < blockCount; b++) {
 for (int c = 0; c < 4; c++) {
 counterBlock[(15 - c)] = (byte) (b >>> c * 8 & 0xFF);
 }
 for (int c = 0; c < 4; c++) {
 counterBlock[(15 - c - 4)] = 0;
 }

 byte[] cipherCntr = aes.encryptBlock(counterBlock,
 new byte[16]);

 int blockLength = b < blockCount - 1 ? 16
 : (cipherByte.length - 9) % 16 + 1;

 for (int i = 0; i < blockLength; i++) {
 plaintxt[(b * 16 + i)] =
 (byte) (cipherCntr[i] ^ cipherByte[
 (8
 + b * 16 + i)]);
 }

 }

 aes.finalize();

 String plaintext = new String(plaintxt);

 return plaintext;
 }
    
 */
    
    
    private byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
}