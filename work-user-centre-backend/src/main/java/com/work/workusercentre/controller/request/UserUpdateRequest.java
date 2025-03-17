package com.work.workusercentre.controller.request;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
public class UserUpdateRequest implements Serializable {

    private Long id;

    private String userAccount;

    private String userAvatar;

    private String userProfile;

    private Integer userRole;

    private Integer userLevel;

    private Integer userGender;

    @Serial
    private static final long serialVersionUID = 1L;

}