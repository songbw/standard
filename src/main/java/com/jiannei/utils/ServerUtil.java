package com.jiannei.utils;

/**
 * Created by sbw22 on 2017/9/26.
 */
public class ServerUtil {

    public static String randomCode() {
        String sRand = "";
        for (int i = 0; i < 6; i++) {
            String rand = getRandomChar();
            sRand = sRand.concat(rand);
        }
        return sRand;
    }

    private static String getRandomChar() {
        String randChar = "";
        randChar = String.valueOf(Math.round(Math.random() * 9));
        return randChar;
    }
}
