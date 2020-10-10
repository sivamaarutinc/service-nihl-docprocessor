package com.wsib.nihl.util;

import java.io.IOException;
import java.util.Base64;

public class CommonUtils {

    public static String applyMasking(String sin) {
        if(sin != null && !sin.isEmpty() && sin.length() > 8) {
            String maskedSin = new String();
            for (int i = 0; i < sin.length() - 4; i++) {
                if ('-' == sin.charAt(i) || '(' == sin.charAt(i) || ')' == sin.charAt(i) || '+' == sin.charAt(i)) {
                    maskedSin = maskedSin + sin.charAt(i);
                } else {
                    maskedSin = maskedSin + "X";
                }
            }
            return maskedSin + sin.substring(sin.length() - 4, sin.length());
        }
        return sin;
    }

    public static String encodePassword(String password){
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static String decodePassword(String encryptedPassword) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        return new String(decodedBytes);
    }

}
