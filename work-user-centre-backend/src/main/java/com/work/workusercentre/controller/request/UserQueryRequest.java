package com.work.workusercentre.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true) // TODO: 可以研究一下
@Data
public class UserQueryRequest extends PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String userAccount;

    private Integer userRole;

    private Integer userLevel;

}
