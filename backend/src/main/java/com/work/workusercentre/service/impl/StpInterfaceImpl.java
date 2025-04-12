package com.work.workusercentre.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.work.workusercentre.enums.UserRoleEnum;
import com.work.workusercentre.mapper.UserMapper;
import com.work.workusercentre.model.entity.User;
import com.work.workusercentre.model.entity.UserRole;
import com.work.workusercentre.request.UserSearchRequest;
import com.work.workusercentre.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限认证服务层实现
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    /**
     * 映射对象
     */
    @Resource
    private UserService userService;


    /**
     * 返回一个账号所拥有的权限码值集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) { // loginType 可以用来区分不同客户端
        Long userId = Long.valueOf(loginId.toString());
        List<String> list = new ArrayList<String>();
        list.add("*"); // 有需要再实现
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) { // loginType 可以用来区分不同客户端
        Long userId = Long.valueOf(loginId.toString());
        UserSearchRequest userSearchRequest = new UserSearchRequest();
        userSearchRequest.setId(userId);
        List<User> users = userService.userSearch(userSearchRequest);
        String userRole = UserRoleEnum.getUserDescription(users.get(0).getRole());
        System.out.println(userRole + "114514");

        List<String> list = new ArrayList<String>();
        list.add(userRole);
        return list;
    }

}
