package cn.com.edtechhub.workusercentre.exception;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.com.edtechhub.workusercentre.response.BaseResponse;
import cn.com.edtechhub.workusercentre.enums.CodeBindMessage;
import cn.com.edtechhub.workusercentre.response.TheResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理方法类
 * 截获异常, 把异常的 "错误-含义:消息" 作为响应传递给前端, 本质时为了避免让服务层抛异常而不涉及报文相关的东西, 让全局异常处理器来代做
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@RestControllerAdvice(basePackages = "cn.com.edtechhub.workusercentre.controller") // 使用 @RestControllerAdvice 可以拦截所有 @RestController 中抛出的异常, 并统一返回 JSON 格式的错误信息, 不过由于版本过新, 需要考虑问题 https://github.com/xiaoymin/knife4j/issues/884
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局所有异常处理方法
     * @param e 参数异常对象
     */
    @ExceptionHandler // 直接拦截 Throwable
    public void handlerException(Exception e) {
        e.printStackTrace();
    }

    /**
     * 业务内部异常处理方法(服务层手动使用)
     *
     * @param e 参数异常对象
     * @return 包含错误原因的通用响应体对象
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.debug("触发业务内部异常处理方法");
         return TheResult.error(e.getCodeBindMessage(), e.exceptionMessage);
    }

    /**
     * 系统内部异常处理方法(兜底把所有运行时异常拦截后进行处理)
     *
     * @param e 系统异常对象
     * @return 包含错误原因的通用响应体对象
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.debug("触发系统内部异常处理方法");
        log.error(e.getMessage());
        return TheResult.error(CodeBindMessage.SYSTEM_ERROR, "请联系管理员");
    }

    /**
     * 登录认证异常处理方法(由 Sa-token 框架自己来触发)
     *
     * @param e 登录异常对象
     * @return 包含错误原因的通用响应体对象s
     */
    @ExceptionHandler(NotLoginException.class)
    public BaseResponse<?> notLoginExceptionHandler(NotLoginException e) {
        log.debug("触发登录认证异常处理方法");
        return TheResult.error(CodeBindMessage.NO_LOGIN_ERROR, "请先进行登录");
    }

    /**
     * 权限认证异常处理方法(权限码值认证)
     *
     * @param e 权限异常对象
     * @return 包含错误原因的通用响应体对象
     */

    @ExceptionHandler(NotPermissionException.class)
    public BaseResponse<?> notPermissionExceptionHandler(NotPermissionException e) {
        log.debug("触发权限认证异常处理方法(权限码值认证)");
        return TheResult.error(CodeBindMessage.NO_AUTH_ERROR, "用户当前权限不允许使用该功能");
    }

    /**
     * 权限认证异常处理方法(角色标识认证)
     *
     * @param e 权限异常对象
     * @return 包含错误原因的通用响应体对象
     */
    @ExceptionHandler(NotRoleException.class)
    public BaseResponse<?> notRoleExceptionHandler(NotRoleException e) {
        log.debug("触发权限认证异常处理方法(角色标识认证)");
        return TheResult.error(CodeBindMessage.NO_ROLE_ERROR, "用户当前角色不允许使用该功能");
    }

    /**
     * 用户封禁异常处理方法
     *
     * @param e 权限异常对象
     * @return 包含错误原因的通用响应体对象
     */
    @ExceptionHandler(DisableServiceException.class)
    public BaseResponse<?> disableServiceExceptionHandler(DisableServiceException e) {
        log.debug("触发用户封禁异常处理方法");
        return TheResult.error(CodeBindMessage.USER_DISABLE_ERROR, "当前用户因为违规被封禁"); // TODO: 可以考虑告知用户封禁时间
    }

}
