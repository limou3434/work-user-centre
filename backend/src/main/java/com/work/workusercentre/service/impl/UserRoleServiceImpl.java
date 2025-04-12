package com.work.workusercentre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.workusercentre.model.entity.UserRole;
import com.work.workusercentre.service.UserRoleService;
import com.work.workusercentre.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* 用户权限服务层实现
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




