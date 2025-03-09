package com.work.workusercentre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.workusercentre.entity.User;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author ljp
 * @description 针对表【user(用户信息表)】的数据库操作 Service
 * @createDate 2025-03-06 10:25:51
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册服务
     *
     * @param userAccount
     * @param userPasswd
     * @param checkPasswd
     * @return 用户 id
     */
    long userRegister(String userAccount, String userPasswd, String checkPasswd);

    /**
     * 用户登入服务
     *
     * @param userAccount
     * @param userPasswd
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPasswd, HttpServletRequest request);

    /**
     * 用户登出服务
     *
     * @param request
     * @return 是否登出成功
     */
    boolean userLogout(HttpServletRequest request);
}
