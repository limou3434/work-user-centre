package com.work.workusercentre.controller.request;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
public class UserAddRequest implements Serializable {
    private String userAccount;

    private String userPasswd;

    private String userAvatar;

    private String userRole;

    @Serial
    private static final long serialVersionUID = 1L;
}