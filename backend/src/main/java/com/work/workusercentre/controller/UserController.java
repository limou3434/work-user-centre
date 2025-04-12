package com.work.workusercentre.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaIgnore;
import com.work.workusercentre.model.dto.UserStatus;
import com.work.workusercentre.model.entity.User;
import com.work.workusercentre.request.*;
import com.work.workusercentre.response.BaseResponse;
import com.work.workusercentre.enums.CodeBindMessage;
import com.work.workusercentre.response.TheResult;
import com.work.workusercentre.service.UserService;
import com.work.workusercentre.model.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户控制层
 * 1. 登录认证、角色认证、权限认证注解
 * 2. 解析请求
 * 3. 调用服务
 * 4. 封装响应
 * 默认只返回 200, 某些特殊的错误交给前端响应, 比如 403、404 以及对应的页面, 详细错误 code-message 在响应 JSON 中体现
 */
@RestController // 返回值默认为 json 类型
@RequestMapping("/user")
public class UserController { // 通常控制层有服务层中的所有方法, 并且还有组合而成的方法, 如果组合的方法开始变得复杂就会封装到服务层内部

    /**
     * 映射对象
     */
    @Resource
    private UserService userService;

    // NOTE: CRUD Module
    /**
     * 添加用户网络接口
     */
    @SaCheckLogin
    @SaCheckRole("admin")
    @PostMapping("/add")
    public BaseResponse<UserVO> userAdd(@RequestBody UserAddRequest userAddRequest) {
        User user = userService.userAdd(userAddRequest);
        UserVO userVo = UserVO.removeSensitiveData(user);
        return TheResult.success(CodeBindMessage.SUCCESS, userVo);
    }

    /**
     * 删除用户网络接口
     */
    @SaCheckLogin
    @SaCheckRole("admin")
    @PostMapping("/delete")
    public BaseResponse<Boolean> userDelete(@RequestBody UserDeleteRequest userDeleteRequest) {
        Boolean result = userService.userDelete(userDeleteRequest);
        return TheResult.success(CodeBindMessage.SUCCESS, result);
    }

    /**
     * 修改用户网络接口
     */
    @SaCheckLogin
    @SaCheckRole("admin")
    @PostMapping("/update")
    public BaseResponse<UserVO> userUpdate(@RequestBody UserUpdateRequest userUpdateRequest) {
        User user = userService.userUpdate(userUpdateRequest);
        UserVO userVo = UserVO.removeSensitiveData(user);
        return TheResult.success(CodeBindMessage.SUCCESS, userVo);
    }

    /**
     * 查询用户网络接口
     */
    @SaCheckLogin
    @SaCheckRole("admin")
    @PostMapping("/search")
    public BaseResponse<List<UserVO>> userSearch(@RequestBody UserSearchRequest userSearchRequest) {
        List<User> users = userService.userSearch(userSearchRequest);
        List<UserVO> userVoList = users.stream()
                .map(UserVO::removeSensitiveData)
                .collect(Collectors.toList());
        return TheResult.success(CodeBindMessage.SUCCESS, userVoList);
    }

    // NOTE: Authentication Module
    /**
     * 用户注册网络接口
     */
    @SaIgnore
    @PostMapping("/register")
    public BaseResponse<Boolean> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        Boolean result = userService.userRegister(userRegisterRequest.getAccount(), userRegisterRequest.getPasswd(), userRegisterRequest.getCheckPasswd());
        return TheResult.success(CodeBindMessage.SUCCESS, result);
    }

    /**
     * 用户登入网络接口
     */
    @SaIgnore
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        User user = userService.userLogin(userLoginRequest.getAccount(), userLoginRequest.getPasswd());
        UserVO userVo = UserVO.removeSensitiveData(user);
        return TheResult.success(CodeBindMessage.SUCCESS, userVo);
    }

    /**
     * 用户登出网络接口
     */
    @SaCheckLogin
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout() {
        Boolean result = userService.userLogout();
        return TheResult.success(CodeBindMessage.SUCCESS, result);
    }

    /**
     * 获取状态网络接口
     */
    @SaIgnore
    @GetMapping("/status")
    public BaseResponse<UserStatus> userStatus() {
        UserStatus userStatus = userService.userStatus();
        return TheResult.success(CodeBindMessage.SUCCESS, userStatus);
    }

}
