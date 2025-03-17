package com.work.workusercentre.controller.vo;

import com.work.workusercentre.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginUserVO implements Serializable {
    /**
     * 本用户唯一标识(业务层需要考虑使用雪花算法用户标识的唯一性)
     */
    private Long id;

    /**
     * 账户号(业务层需要决定某一种或多种登录方式, 因此这里不限死为非空)
     */
    private String userAccount;

    /**
     * 微信号
     */
    private String userWxUnion;

    /**
     * 公众号
     */
    private String userMpOpen;

    /**
     * 邮箱号
     */
    private String userEmail;

    /**
     * 电话号
     */
    private String userPhone;

    /**
     * 用户头像(业务层需要考虑默认头像使用 cos 对象存储)
     */
    private String userAvatar;

    /**
     * 用户标签(业务层需要 json 数组格式存储用户标签数组)
     */
    private String userTags;

    /**
     * 用户昵称
     */
    private String userNick;

    /**
     * 用户名字
     */
    private String userName;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户生日
     */
    private String userBirthday;

    /**
     * 用户国家
     */
    private String userCountry;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 用户角色(业务层需知 0 为用户, 1 为管理, 2 为封号, ...)
     */
    private Integer userRole;

    /**
     * 用户等级(业务层需知 0 为普通, 1 为高级, 2 为特权, 3 为企业, ...)
     */
    private Integer userLevel;

    /**
     * 用户性别(业务层需知 0 为未知, 1 为男性, 2 为女性)
     */
    private Integer userGender;

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 脱敏方法
     *
     * @param user 用户信息
     * @return 脱敏后的用户信息
     */
    static public LoginUserVO removeSensitiveData(User user) {
        if (user == null) {
            return null;
        }
        var userVO = new LoginUserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}