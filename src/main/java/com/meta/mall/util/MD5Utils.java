package com.meta.mall.util;

import com.meta.mall.common.Constant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MD5Utils {


    private MD5Utils() {
    }

    public static String getMD5String(String from) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return Base64.getEncoder().encodeToString(md5.digest((from + Constant.SALT).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            return "";
        }

    }

}
