package com.work.workusercentre.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.work.workusercentre.entity.User;
import com.work.workusercentre.mapper.UserMapper;
import com.work.workusercentre.request.UserLoginRequest;
import com.work.workusercentre.request.UserRegisterRequest;
import com.work.workusercentre.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.work.workusercentre.contant.UserConstant.ADMIN_ROLE;
import static com.work.workusercentre.contant.UserConstant.USER_LOGIN_STA;

@RestController // 返回值默认为 json 类型
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    /**
     * 用户注册接口
     *
     * @param userRegisterRequest
     * @return 用户 id
     */
    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // @RequestBody 将前端 HTTP 请求的 body 解析成 Java 对象
        // 参数校验
        if (userRegisterRequest == null) { // TODO: 可以在控制层中再次校验, 倾向请求参数本身的简单校验(例如非空)
            return null;
        }

        return userService.userRegister(userRegisterRequest.getUserAccount(), userRegisterRequest.getUserPasswd(), userRegisterRequest.getCheckPasswd());
    }

    /**
     * 用户登入接口
     *
     * @param userLoginRequest
     * @return 脱敏后的用户信息
     */
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 参数校验
        if (userLoginRequest == null) { // TODO: 可以在控制层中再次校验, 倾向请求参数本身的简单校验(例如非空)
            return null;
        }

        return userService.userLogin(userLoginRequest.getUserAccount(), userLoginRequest.getUserPasswd(), request);
    }

    /**
     * 用户登出接口
     *
     * @return 是否登出成功
     */
    @PostMapping("/logout")
    public boolean userLogout(HttpServletRequest request) {
        return userService.userLogout(request);
    }

    /**
     * 模糊查询搜索用户列表接口
     *
     * @param userName 用户名可以为空
     * @return 用户列表
     */
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam(required = false) String userName, HttpServletRequest request) {
        // TODO: 这里的权限需要修改为 AOP
        // 权限校验
        if (isNotAdmin(request)) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            lambdaQueryWrapper.like(User::getUserName, userName);
        }

        List<User> userList = userService.list(lambdaQueryWrapper);
        return userList.stream().map(user -> {
            user.setUserPasswd(null); // TODO: 需要保持和前面的脱敏一致
            return user;
        }).collect(Collectors.toList());
    }

    /**
     * 删除用户接口
     *
     * @param id
     * @return 是否删除成功
     */
    // TODO: 只有管理员权限才可以调用
    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request) {
        // TODO: 这里的权限需要修改为 AOP
        // 权限校验
        if (isNotAdmin(request) || id <= 0) {
            return false;
        }
        return userService.removeById(id); // 这里 MyBatisPlus 会自动转化为逻辑删除
    }

    /**
     * 非管理员方法
     *
     * @param request
     * @return
     */
    private boolean isNotAdmin(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STA);
        return user == null || user.getUserRole() != ADMIN_ROLE;
    }
}
