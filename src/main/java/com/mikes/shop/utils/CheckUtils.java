package com.mikes.shop.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class CheckUtils {

    private static final String token = "Wzy25257758";

    public static boolean checkSignatrue(String signature,String timestamp,String nonce){
        //1.定义数组存放tooken，timestamp,nonce

        String[] arr = {token,timestamp,nonce};

        //2.对数组进行排序

        Arrays.sort(arr);

        //3.生成字符串

        StringBuilder sb = new StringBuilder();

        for(String s : arr) {
            sb.append(s);
        }
        //4.sha1加密,网上均有现成代码

        String temp = getSha1(sb.toString());

        //5.将加密后的字符串，与微信传来的加密签名比较，返回结果

        return temp.equals(signature);
    }

    private static String getSha1(String str){

        if(str==null||str.length()==0){

            return null;

        }

        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9',

                'a','b','c','d','e','f'};

        try {

            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");

            mdTemp.update(str.getBytes(StandardCharsets.UTF_8));



            byte[] md = mdTemp.digest();

            int j = md.length;

            char[] buf = new char[j*2];

            int k = 0;

            for (byte byte0 : md) {

                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];

                buf[k++] = hexDigits[byte0 & 0xf];

            }

            return new String(buf);

        } catch (Exception e) {

            // TODO: handle exception

            return null;

        }

    }
}
