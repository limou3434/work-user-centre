package com.work.workusercentre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.workusercentre.entity.User;
import com.work.workusercentre.mapper.UserMapper;
import com.work.workusercentre.service.UserService;
import com.work.workusercentre.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.work.workusercentre.contant.ConfigConstant.SALT;
import static com.work.workusercentre.contant.UserConstant.USER_LOGIN_STA;

/**
 * @author ljp
 * @description 针对表【user(用户信息表)】的数据库操作 Service 实现
 * @createDate 2025-03-06 10:25:51
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public long userRegister(String userAccount, String userPasswd, String checkPasswd) {
        // 1. 参数校验
        // 判断传入的所有字符串是否都是空白(null、空字符串、仅包含空格）
        if (StringUtils.isAllBlank(userAccount, userPasswd, checkPasswd)) {
            return -1; // TODO: 这里的返回值需要修改为异常
        }

        // 判断账户和密码的长度是否符合要求
        if (userAccount.length() < 4 || userPasswd.length() < 6 || checkPasswd.length() < 6) {
            return -1;
        }

        // 避免账户中的非法字符
        String validPattern = "^[a-zA-Z0-9$_-]+$";
        if (!userPasswd.matches(validPattern)) {
            return -1;
        }

        // 判断两次输入的密码是否一致
        if (!userPasswd.equals(checkPasswd)) {
            return -1;
        }

        // 避免重复注册用户
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserAccount, userAccount);
        if (this.count(lambdaQueryWrapper) > 0) {
            return -1;
        }

        // 2. 密码加密
        String newUserPasswd = DigestUtils.md5DigestAsHex((SALT + userPasswd).getBytes(StandardCharsets.UTF_8));

        // 3. 创建用户
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPasswd(newUserPasswd);

        // 4. 注册用户
        boolean saveResult = this.save(user);
        if (!saveResult) { // 避免拆箱错误
            return -1;
        }

        return user.getId();
    }

    @Override
    public UserVO userLogin(String userAccount, String userPasswd, HttpServletRequest request) {
        // 1. 参数校验
        // 判断传入的所有字符串是否都是空白(null、空字符串、仅包含空格）
        if (StringUtils.isAllBlank(userAccount, userPasswd)) {
            return null; // TODO: 这里的返回值需要修改为异常
        }

        // 判断账户和密码的长度是否符合要求
        if (userAccount.length() < 4 || userPasswd.length() < 6) {
            return null;
        }

        // 避免账户中的非法字符
        String validPattern = "^[a-zA-Z0-9$_-]+$";
        if (!userPasswd.matches(validPattern)) {
            return null;
        }

        // 2. 密码加密
        String newUserPasswd = DigestUtils.md5DigestAsHex((SALT + userPasswd).getBytes(StandardCharsets.UTF_8));

        // 3. 查询用户
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserAccount, userAccount).eq(User::getUserPasswd, newUserPasswd);
        User user = this.getOne(lambdaQueryWrapper);
        if (user == null) {
            log.info("用户名或密码错误");
            return null; // TODO: 这里的返回值需要修改为异常
        }

        // 4. 脱敏信息
        UserVO safetUser = UserVO.removeSensitiveData(user);

        // 5. 创建会话
        // session 数据存储在应用服务器 Tomcat 中, 以后可以通过 request.getSession().getAttribute(USER_LOGIN_STA) 取出用户登陆自己浏览器中本应用的 session 信息 safetUser, 不过类型变成了 Object, 可以后续强转恢复
        request.getSession().setAttribute(USER_LOGIN_STA, safetUser); // TODO: 改为 Redis 分布式存储

        return safetUser;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STA); // TODO: 改为 Redis 分布式存储
        return true;
    }
}
