package com.premiere.common;

import java.util.Random;

public class MassageCode {

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    public static String getCode() {
        //需要按照一定的规则opt验证码
        Random random = new Random();
        int anInt = random.nextInt(9999999);
        String code = anInt + "";
        code = code.substring(0, 6);
        return code;
    }
}
