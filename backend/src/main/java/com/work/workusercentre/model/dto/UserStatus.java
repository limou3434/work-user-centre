package com.work.workusercentre.model.dto;

import lombok.Data;

@Data
public class UserStatus {

    /**
     * 是否登录
     */
    Boolean isLogin;

    /**
     * Token 名称
     */
    String tokenName;

    /**
     * Token 有效时间
     */
    String tokenTimeout;

}
