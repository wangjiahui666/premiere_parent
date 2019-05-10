package com.premiere.common;

import lombok.Data;

@Data
public class StatusCode {

    //成功
    public static final Integer OK = 20000;
    //失败
    public static final Integer ERROR = 20001;
    //用户名或密码错误
    public static final Integer LOGINERROR = 20002;
    //无权访问
    public static final Integer AUTOROLES = 20003;
    //已经添加此好
    public static final Integer REPERROR = 20004;
}
