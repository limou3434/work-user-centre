package com.work.workusercentre.controller.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String userAccount;

    private String userPasswd;

    private String checkPasswd;
}
