package com.nge.util;


import java.security.Key;

import javax.crypto.Cipher;

public class EncryptionDecryption {

    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }


    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public static Key generateKey(String keyStr) {
       // Security.addProvider(new SunJCE());
        Key key = getKey(keyStr.getBytes());
        return key;

    }


    public static byte[] encrypt(String keyStr, byte[] arrB) {
        Cipher encryptCipher = null;
        try {
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, generateKey(keyStr));
            return encryptCipher.doFinal(arrB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public static String encrypt(String keyStr, String strIn) {
        return byteArr2HexStr(encrypt(keyStr, strIn.getBytes()));
    }


    public static byte[] decrypt(String keyStr, byte[] arrB) {
        Cipher decryptCipher = null;
        try {
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, generateKey(keyStr));
            return decryptCipher.doFinal(arrB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public static String decrypt(String keyStr, String strIn) {
        try {
            return new String(decrypt(keyStr, hexStr2ByteArr(strIn)));
        } catch (Exception e) {
            return "";
        }
    }


    public static Key getKey(byte[] arrBTmp) {
        byte[] arrB = new byte[8];

        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }
}
