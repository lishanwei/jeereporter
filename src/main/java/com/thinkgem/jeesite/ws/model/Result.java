package com.thinkgem.jeesite.ws.model;

import java.io.Serializable;

/**
 * Created by jjh on 16/1/28.
 */
public class Result implements Serializable{

    public static final String SUCCESS = "SUCCESS";

    public static final String ERROR = "ERROR";

    public static final String USERNAME_INVALID = "USERNAME_INVALID";
    public static final String USER_EXIST = "USER_EXIST";
    public static final String USER_NOT_EXIST = "USER_NOT_EXIST";

    public static final String DB_ERROR = "DB_ERROR";

    public static final String DATA_ERROR = "DATA_ERROR";

    public static final String CODE_NOT_EXIST = "CODE_NOT_EXIST";
    public static final String ROLE_NOT_EXIST = "ROLE_NOT_EXIST";

    public static final String PHONE_EXIST = "PHONE_EXIST";
    public static final String PHONE_NOT_EXIST = "PHONE_NOT_EXIST";

    // 结果码
    private String code;

    // 消息描述
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
