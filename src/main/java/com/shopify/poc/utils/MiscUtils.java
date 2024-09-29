package com.shopify.poc.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MiscUtils {
    static String HMAC_ALGORITHM = "HmacSHA256";

    private static String calculateHmac(String message, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmac = Mac.getInstance(HMAC_ALGORITHM);
        SecretKeySpec key = new SecretKeySpec(secret.getBytes(), HMAC_ALGORITHM);
        hmac.init(key);

        return Base64.encodeBase64String(hmac.doFinal(message.getBytes()));
    }

    public static Boolean checkHmac(String message, String hmac, String secret) throws InvalidKeyException, NoSuchAlgorithmException {
        return hmac.equals(calculateHmac(message, secret));
    }
}
