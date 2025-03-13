package com.work.workusercentre.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.work.workusercentre.annotation.AuthCheck;
import com.work.workusercentre.entity.User;
import com.work.workusercentre.exception.ArgumentException;
import com.work.workusercentre.request.*;
import com.work.workusercentre.response.BaseResponse;
import com.work.workusercentre.response.ErrorCodeBindMessage;
import com.work.workusercentre.response.TheResult;
import com.work.workusercentre.service.UserService;
import com.work.workusercentre.vo.LoginUserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.work.workusercentre.contant.ConfigConstant.SALT;

/**
 * 用户控制层
 */
@RestController // 返回值默认为 json 类型
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // 允许前端域访问 // TODO: 实际部署需要代理, 所以这里的注解去除更加安全
@RequestMapping("/user")
@Slf4j
public class UserController { // 通常控制层有服务层中的所有方法, 并且还有组合而成的方法, 如果组合的方法开始变得复杂就会封装到服务层内部
    @Resource
    private UserService userService;

    /**
     * 用户注册接口
     *
     * @param userRegisterRequest 用户注册请求数据
     * @return 用户 id
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // 参数校验
        if (userRegisterRequest == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求体为空");
        }

        // 处理请求
        Long id = userService.userRegister(userRegisterRequest.getUserAccount(), userRegisterRequest.getUserPasswd(), userRegisterRequest.getCheckPasswd());

        // 返回响应
        return TheResult.success(id);
    }

    /**
     * 用户登入接口
     *
     * @param userLoginRequest 用户登入请求数据
     * @param request          请求体
     * @return 脱敏后的用户信息
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 参数校验
        if (userLoginRequest == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求体为空");
        }
        if (request == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求为空");
        }

        // 处理请求
        LoginUserVO loginUserVO = userService.userLogin(userLoginRequest.getUserAccount(), userLoginRequest.getUserPasswd(), request);

        // 返回响应
        return TheResult.success(loginUserVO);
    }

    /**
     * 用户登出接口
     *
     * @return 是否登出成功
     */
    @PostMapping("/logout")
    @AuthCheck()
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求为空");
        }

        // 返回响应
        return TheResult.success(userService.userLogout(request));
    }

    /**
     * 获取当前登录用户信息接口
     *
     * @param request 请求体
     * @return 脱敏后的用户信息
     */
    @GetMapping("/get/login")
    @AuthCheck()
    public BaseResponse<LoginUserVO> userGetLoginState(HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求为空");
        }

        // 返回响应
        return TheResult.success(userService.getLoginUserState(request));
    }

    /**
     * 添加用户接口
     *
     * @param userAddRequest 用户添加请求数据
     * @param request        请求体
     * @return 是否添加成功
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Long> userAdd(@RequestBody UserAddRequest userAddRequest, HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求为空");
        }

        // 设置默认密码
        var user = new User();
        String defaultPassword = "12345678";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + defaultPassword).getBytes());
        user.setUserPasswd(encryptPassword);

        // 设置默认角色
        user.setUserRole(0);

        // 处理请求
        BeanUtils.copyProperties(userAddRequest, user);
        boolean result = userService.save(user);
        if (!result) {
            throw new ArgumentException(ErrorCodeBindMessage.SYSTEM_ERROR, "参数用户 id 不能为空");
        }

        // 返回响应
        return TheResult.success(user.getId());
    }

    /**
     * 修改用户接口
     *
     * @param userUpdateRequest 用户修改请求数据
     * @param request        请求体
     * @return 是否修改成功
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> userUpdate(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求为空");
        }

        if (userUpdateRequest.getId() == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "参数用户 id 不能为空");
        }

        // 处理请求
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        if (!result) {
            throw new ArgumentException(ErrorCodeBindMessage.SYSTEM_ERROR, "需要指定参数用户 id 才能修改");
        }

        // 返回响应
        return TheResult.success(true);
    }

    /**
     * 删除用户接口
     *
     * @param userDeleteRequest 用户删除请求数据
     * @param request           请求体
     * @return 是否删除成功
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> userDelete(@RequestBody UserDeleteRequest userDeleteRequest, HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求为空");
        }

        if (userDeleteRequest.getId() <= 0) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "参数用户 id 不能为空");
        }

        // 返回响应
        return TheResult.success(userService.removeById(userDeleteRequest.getId())); // 这里 MyBatisPlus 会自动转化为逻辑删除
    }

    /**
     * 模糊查询搜索用户列表接口
     *
     * @param userName 用户名可以为空
     * @param request  请求体
     * @return 用户列表, 如果没有查询 id 就会得到所有用户
     */
    @GetMapping("/search")
    @AuthCheck(mustRole = "admin") // TODO: 不要使用字符串而使用常量
    public BaseResponse<List<LoginUserVO>> userSearch(@RequestParam(required = false) String userName, HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求为空");
        }

        // 处理请求
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            lambdaQueryWrapper.like(User::getUserName, userName);
        }

        List<User> userList = userService.list(lambdaQueryWrapper);

        // 返回响应
        List<LoginUserVO> loginUserVOList = userList
                .stream() // 转化操作, 将 userList 转换为一个流
                .map(LoginUserVO::removeSensitiveData) // 中间操作, 将流中的每个元素都通过指定的函数进行转换
                .toList();
        return TheResult.success(loginUserVOList); // 终端操作, 收集器 Collectors.toList() 用来将流中的元素收集到一个新的 List 中
    }

}
