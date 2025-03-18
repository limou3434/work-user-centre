package com.work.workusercentre.controller;

import com.work.workusercentre.annotation.AuthCheck;
import com.work.workusercentre.controller.request.*;
import com.work.workusercentre.entity.User;
import com.work.workusercentre.controller.exception.ArgumentException;
import com.work.workusercentre.controller.response.BaseResponse;
import com.work.workusercentre.controller.response.ErrorCodeBindMessage;
import com.work.workusercentre.controller.response.TheResult;
import com.work.workusercentre.service.UserService;
import com.work.workusercentre.controller.vo.LoginUserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.work.workusercentre.contant.ConfigConstant.SALT;

/**
 * 用户控制层
 *
 * 1. 控制层只做简单的参数校验, 实际控制使用封装好的 Server
 * 2. 所有接口默认只返回 200, 某些特殊的错误交给前端响应(比如 403、404 以及对应的页面), 详细错误(code-message)在响应 JSON 中体现
 * 3. 控制层的方法本身最好不要改动, 这样前端代码就可以利用这个方法名称来无缝导入, 但是 HTTP 接口可以随时修改, 前端导入接口文档时会自动修改且依旧使用之前的方法
 * @author ljp
 */
@RestController // 返回值默认为 json 类型
@RequestMapping("/user")
@Slf4j
public class UserController { // 通常控制层有服务层中的所有方法, 并且还有组合而成的方法, 如果组合的方法开始变得复杂就会封装到服务层内部

    @Resource
    private UserService userService;

    // NOTE: Conventional CRUD Module()
    /**
     * 添加用户网络接口
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
     * 删除用户网络接口
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
     * 修改用户网络接口
     *
     * @param userUpdateRequest 用户修改请求数据
     * @param request        请求体
     * @return 是否修改成功
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<LoginUserVO> userUpdate(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求为空");
        }

        if (userUpdateRequest.getId() == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "参数用户 id 不能为空");
        }


        // 处理请求
        User user = new User();

        BeanUtils.copyProperties(userUpdateRequest, user); // TODO: 添加内部方法

        boolean result = userService.updateById(user);
        if (!result) {
            throw new ArgumentException(ErrorCodeBindMessage.SYSTEM_ERROR, "需要指定参数用户 id 才能修改");
        }

        LoginUserVO loginUserVO = LoginUserVO.removeSensitiveData(user);

        // 返回响应
        return TheResult.success(loginUserVO);
    }

    /**
     * 查询用户网络接口
     *
     * @param userSearchRequest 用户查询请求数据
     * @param request 请求体
     * @return 用户列表, 如果没有查询 id 就会得到所有用户
     */
    @PostMapping("/search")
    @AuthCheck(mustRole = "admin") // TODO: 身份权限不要使用字符串而使用常量
    public BaseResponse<List<LoginUserVO>> userSearch(@RequestBody UserSearchRequest userSearchRequest, HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求为空");
        }

        // 处理请求
        List<User> userList = userService.list(userService.getLambdaQueryWrapper(userSearchRequest));

        // 返回响应
        List<LoginUserVO> loginUserVOList = userList
                .stream() // 转化操作, 将 userList 转换为一个流
                .map(LoginUserVO::removeSensitiveData) // 中间操作, 将流中的每个元素都通过指定的函数进行转换
                .toList();
        return TheResult.success(loginUserVOList); // 终端操作, 收集器 Collectors.toList() 用来将流中的元素收集到一个新的 List 中
    }

    // NOTE: Extended CRUD Module
    // TODO: 修改自己的信息
    /**
     * 修改用户网络接口(自己)
     *
     * @param userUpdateRequest 用户修改请求数据
     * @param request        请求体
     * @return 是否修改成功
     */
    @PostMapping("/update/self")
    @AuthCheck(mustRole = "user")
    public BaseResponse<Boolean> userUpdateSelf(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        return null;
    }

    // TODO: 分页查询
    /**
     * 查询用户网络接口(分页)
     *
     * @param userSearchRequest 用户查询请求数据
     * @param request 请求体
     * @return 用户列表, 如果没有查询 id 就会得到所有用户
     */
    @PostMapping("/search/page")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<LoginUserVO>> userSearchPage(@RequestBody UserSearchRequest userSearchRequest, HttpServletRequest request) {
    return null;
    }

    // NOTE: Authentication Module
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
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) {
        // 参数校验
        if (userLoginRequest == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求体为空");
        }
        if (request == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求为空");
        }

        // 处理请求
        LoginUserVO loginUserVO = userService.userLogin(userLoginRequest.getUserAccount(), userLoginRequest.getUserPasswd(), request, response);

        // 返回响应
        return TheResult.success(loginUserVO);
    }

    /**
     * 获取当前登录用户信息接口
     *
     * @param request 请求体
     * @return 脱敏后的用户信息
     */
    @GetMapping("/login/get")
    @AuthCheck()
    public BaseResponse<LoginUserVO> userLoginGet(HttpServletRequest request) {
        // 参数校验
        if (request == null) {
            throw new ArgumentException(ErrorCodeBindMessage.PARAMS_ERROR, "请求为空");
        }

        // 返回响应
        return TheResult.success(userService.getLoginUserState(request));
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
}
