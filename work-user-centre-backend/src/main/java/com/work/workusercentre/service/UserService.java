package com.work.workusercentre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.workusercentre.entity.User;
import com.work.workusercentre.vo.LoginUserVO;
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
     * @param userAccount 账号
     * @param userPasswd 密码
     * @param checkPasswd 确认密码
     * @return 用户 id
     */
    Long userRegister(String userAccount, String userPasswd, String checkPasswd);

    /**
     * 用户登入服务
     *
     * @param userAccount 账号
     * @param userPasswd 密码
     * @param request 请求体
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPasswd, HttpServletRequest request);

    /**
     * 用户登出服务
     *
     * @param request 请求体
     * @return 是否登出成功
     */
    Boolean userLogout(HttpServletRequest request);

    /**
     * 获取当前登录用户信息
     * @param request 请求体
     * @return 当前登录用户的脱敏信息
     */
    LoginUserVO getLoginUserState(HttpServletRequest request);
}
