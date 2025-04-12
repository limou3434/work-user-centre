package com.work.workusercentre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.workusercentre.model.dto.UserStatus;
import com.work.workusercentre.model.entity.User;
import com.work.workusercentre.request.*;

import java.util.List;

/**
 * 用户服务层声明
 */
public interface UserService extends IService<User> {

    /**
     * 用户添加服务
     */
    User userAdd(UserAddRequest userAddRequest);

    /**
     * 用户删除服务
     */
    Boolean userDelete(UserDeleteRequest userDeleteRequest);

    /**
     * 用户更新服务
     */
    User userUpdate(UserUpdateRequest userUpdateRequest);

    /**
     * 用户查询服务
     */
    List<User> userSearch(UserSearchRequest userSearchRequest);

    /**
     * 用户注册服务
     */
    Boolean userRegister(String account, String passwd, String checkPasswd);

    /**
     * 用户登入服务
     */
    User userLogin(String account, String passwd);

    /**
     * 用户登出服务
     */
    Boolean userLogout();

    /**
     * 用户状态服务
     */
    UserStatus userStatus();

}
