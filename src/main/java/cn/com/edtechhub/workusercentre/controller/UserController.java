package cn.com.edtechhub.workusercentre.controller;

import cn.com.edtechhub.workusercentre.enums.CodeBindMessage;
import cn.com.edtechhub.workusercentre.model.dto.UserStatus;
import cn.com.edtechhub.workusercentre.model.entity.User;
import cn.com.edtechhub.workusercentre.model.vo.UserVO;
import cn.com.edtechhub.workusercentre.request.*;
import cn.com.edtechhub.workusercentre.response.BaseResponse;
import cn.com.edtechhub.workusercentre.response.TheResult;
import cn.com.edtechhub.workusercentre.service.UserService;
import cn.com.edtechhub.workusercentre.utils.DeviceUtils;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.exception.NotLoginException;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户控制层
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@RestController // 返回值默认为 json 类型
@RequestMapping("/user")
public class UserController { // 通常控制层有服务层中的所有方法, 并且还有组合而成的方法, 如果组合的方法开始变得复杂就会封装到服务层内部

    /**
     * 注入用户服务实例
     */
    @Resource
    private UserService userService;

    /**
     * 流量控制异常处理方法
     */
    public static BaseResponse<?> blockExceptionHandle(BlockException ex) {
        // 流量控制异常
        if (ex instanceof FlowException) {
            return TheResult.error(CodeBindMessage.TOO_MANY_REQUESTS, "请求频繁，请稍后重试");
        }
        // 熔断降级异常
        else if (ex instanceof DegradeException) {
            return TheResult.error(CodeBindMessage.SERVICE_DEGRADED, "服务退化，请稍后重试");
        }
        // 热点参数异常
        else if (ex instanceof ParamFlowException) {
            return TheResult.error(CodeBindMessage.PARAM_LIMIT, "请求繁忙，请稍后重试");
        }
        // 系统保护异常
        else {
            return TheResult.error(CodeBindMessage.SYSTEM_BUSY, "系统繁忙，请稍后重试");
        }
    }

    /**
     * 添加用户网络接口
     */
    @SaCheckLogin @SaCheckRole("admin")
    @SentinelResource(value = "userAdd", blockHandler = "blockExceptionHandle")
    @PostMapping("/add")
    public BaseResponse<UserVO> userAdd(@RequestBody UserAddRequest userAddRequest) {
        User user = userService.userAdd(userAddRequest);
        UserVO userVo = UserVO.removeSensitiveData(user);
        return TheResult.success(CodeBindMessage.SUCCESS, userVo);
    }

    /**
     * 删除用户网络接口
     */
    @SaCheckLogin @SaCheckRole("admin")
    @PostMapping("/delete")
    @SentinelResource(value = "userDelete", blockHandler = "blockExceptionHandle")
    public BaseResponse<Boolean> userDelete(@RequestBody UserDeleteRequest userDeleteRequest) {
        Boolean result = userService.userDelete(userDeleteRequest);
        return TheResult.success(CodeBindMessage.SUCCESS, result);
    }

    /**
     * 修改用户网络接口
     */
    @SaCheckLogin @SaCheckRole("admin")
    @SentinelResource(value = "userUpdate", blockHandler = "blockExceptionHandle")
    @PostMapping("/update")
    public BaseResponse<UserVO> userUpdate(@RequestBody UserUpdateRequest userUpdateRequest) {
        User user = userService.userUpdate(userUpdateRequest);
        UserVO userVo = UserVO.removeSensitiveData(user);
        return TheResult.success(CodeBindMessage.SUCCESS, userVo);
    }

    /**
     * 查询用户网络接口
     */
    @SaCheckLogin @SaCheckRole("admin")
    @SentinelResource(value = "userSearch", blockHandler = "blockExceptionHandle")
    @PostMapping("/search")
    public BaseResponse<List<UserVO>> userSearch(@RequestBody UserSearchRequest userSearchRequest) {
        List<User> userList = userService.userSearch(userSearchRequest);
        List<UserVO> userVoList = userList.stream()
                .map(UserVO::removeSensitiveData)
                .collect(Collectors.toList());
        return TheResult.success(CodeBindMessage.SUCCESS, userVoList);
    }

    /**
     * 封禁用户网络接口
     */
    @SaCheckLogin @SaCheckRole("admin")
    @SentinelResource(value = "userDisable", blockHandler = "blockExceptionHandle")
    @PostMapping("/disable")
    public BaseResponse<Boolean> userDisable(@RequestBody UserDisableRequest userDisableRequest) {
        Boolean result = userService.userDisable(userDisableRequest.getId(), userDisableRequest.getDisableTime());
        return TheResult.success(CodeBindMessage.SUCCESS, result);
    }

    /**
     * 用户注册网络接口
     */
    @SaIgnore
    @SentinelResource(value = "userRegister", blockHandler = "blockExceptionHandle")
    @PostMapping("/register")
    public BaseResponse<Boolean> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        Boolean result = userService.userRegister(userRegisterRequest.getAccount(), userRegisterRequest.getPasswd(), userRegisterRequest.getCheckPasswd());
        return TheResult.success(CodeBindMessage.SUCCESS, result);
    }

    /**
     * 用户登入网络接口
     */
    @SaIgnore
    @SentinelResource(value = "userLogin", blockHandler = "blockExceptionHandle")
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        User user = userService.userLogin(userLoginRequest.getAccount(), userLoginRequest.getPasswd(), DeviceUtils.getRequestDevice(request)); // 这里同时解析用户的设备, 以支持同端互斥
        UserVO userVo = UserVO.removeSensitiveData(user);
        return TheResult.success(CodeBindMessage.SUCCESS, userVo);
    }

    /**
     * 用户登出网络接口
     */
    @SaCheckLogin
    @SentinelResource(value = "userLogout", blockHandler = "blockExceptionHandle")
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        Boolean result = userService.userLogout(DeviceUtils.getRequestDevice(request));
        return TheResult.success(CodeBindMessage.SUCCESS, result);
    }

    /**
     * 获取状态网络接口
     */
    @SaIgnore
    @SentinelResource(value = "userStatus", blockHandler = "blockExceptionHandle")
    @GetMapping("/status")
    public BaseResponse<UserStatus> userStatus() {
        UserStatus userStatus = userService.userStatus();
        return TheResult.success(CodeBindMessage.SUCCESS, userStatus);
    }

}
