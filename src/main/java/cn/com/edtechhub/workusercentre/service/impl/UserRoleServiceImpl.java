package cn.com.edtechhub.workusercentre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.com.edtechhub.workusercentre.mapper.UserRoleMapper;
import cn.com.edtechhub.workusercentre.model.entity.UserRole;
import cn.com.edtechhub.workusercentre.service.UserRoleService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * 用户权限服务层实现
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@Service
@DubboService
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements UserRoleService {

}




