package com.work.workusercentre.controller.request;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
public class UserUpdateRequest implements Serializable {

    private Long id;

    private String userName;

    private String userAvatar;

    private String userProfile;

    private Integer userRole;

    @Serial
    private static final long serialVersionUID = 1L;

}