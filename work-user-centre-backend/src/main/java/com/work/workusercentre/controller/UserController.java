package com.work.workusercentre.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.work.workusercentre.common.BaseResponse;
import com.work.workusercentre.entity.User;
import com.work.workusercentre.request.UserLoginRequest;
import com.work.workusercentre.request.UserRegisterRequest;
import com.work.workusercentre.service.UserService;
import com.work.workusercentre.utils.ResultUtils;
import com.work.workusercentre.vo.UserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.work.workusercentre.contant.UserConstant.ADMIN_ROLE;
import static com.work.workusercentre.contant.UserConstant.USER_LOGIN_STA;

// TODO: 返回值 json 是没有处理过的, 等后续处理

@RestController // 返回值默认为 json 类型
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // 允许前端域访问 // TODO: 实际部署需要代理, 所以这里的注解去除更加安全
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册接口
     *
     * @param userRegisterRequest
     * @return 用户 id
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // 参数校验
        if (userRegisterRequest == null) {
            return null; // TODO: 这里的返回值需要修改为异常
        }

        // 处理请求
        long id = userService.userRegister(userRegisterRequest.getUserAccount(), userRegisterRequest.getUserPasswd(), userRegisterRequest.getCheckPasswd());

        // 返回响应
        return ResultUtils.success(id);
    }

    /**
     * 用户登入接口
     *
     * @param userLoginRequest
     * @return 脱敏后的用户信息
     */
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 参数校验
        if (userLoginRequest == null) {
            return null; // TODO: 这里的返回值需要修改为异常
        }
        if (request == null) {
            return null; // TODO: 这里的返回值需要修改为异常
        }

        // 处理请求
        UserVO userVO = userService.userLogin(userLoginRequest.getUserAccount(), userLoginRequest.getUserPasswd(), request);

        // 返回响应
        return ResultUtils.success(userVO);
    }

    /**
     * 用户登出接口
     *
     * @return 是否登出成功
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            return null; // TODO: 这里的返回值需要修改为异常
        }

        // 返回响应
        return ResultUtils.success(userService.userLogout(request));
    }

    /**
     * 模糊查询搜索用户列表接口
     *
     * @param userName 用户名可以为空
     * @return 用户列表, 如果没有查询 id 就会得到所有用户
     */
    @GetMapping("/search")
    public BaseResponse<List<UserVO>> searchUsers(@RequestParam(required = false) String userName, HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            return null; // TODO: 这里的返回值需要修改为异常
        }

        // 权限校验
        if (isNotAdmin(request)) { // TODO: 这里的权限需要修改为 AOP
            return null; // TODO: 这里的返回值需要修改为异常
        }

        // 处理请求
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            lambdaQueryWrapper.like(User::getUserName, userName);
        }

        List<User> userList = userService.list(lambdaQueryWrapper);

        // 返回响应
        List<UserVO> userVOList = userList
                .stream() // 转化操作, 将 userList 转换为一个流
                .map(UserVO::removeSensitiveData) // 中间操作, 将流中的每个元素都通过指定的函数进行转换
                .toList();
        return ResultUtils.success(userVOList); // 终端操作, 收集器 Collectors.toList() 用来将流中的元素收集到一个新的 List 中
    }

    /**
     * 删除用户接口
     *
     * @param id
     * @return 是否删除成功
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            return null; // TODO: 这里的返回值需要修改为异常
        }

        // 权限校验
        if (isNotAdmin(request) || id <= 0) { // TODO: 这里的权限需要修改为 AOP
            return null;
        }

        // 返回响应
        return ResultUtils.success(userService.removeById(id)); // 这里 MyBatisPlus 会自动转化为逻辑删除
    }

    /**
     * 判断是否为管理员方法
     *
     * @param request
     * @return
     */
    private Boolean isNotAdmin(HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            return null; // TODO: 这里的返回值需要修改为异常
        }

        // 处理请求
        var user = (UserVO) request.getSession().getAttribute(USER_LOGIN_STA);

        // 返回响应
        return user == null || user.getUserRole() != ADMIN_ROLE;
    }
}
