package com.work.workusercentre.exception;

import com.work.workusercentre.response.BaseResponse;
import com.work.workusercentre.response.ErrorCode;
import com.work.workusercentre.response.TheResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理方法类
 *
 * @author ljp
 */
@RestControllerAdvice(basePackages = "com.work.workusercentre.controller") // 使用 @RestControllerAdvice 可以拦截所有 @RestController 中抛出的异常, 并统一返回 JSON 格式的错误信息, 不过由于版本过新, 需要考虑问题 https://github.com/xiaoymin/knife4j/issues/884
public class GlobalExceptionHandler {
    /**
     * 参数异常处理方法
     *
     * @param e
     * @return 包含错误原因的通用响应体对象
     */
    @ExceptionHandler(ArgumentException.class)
    public BaseResponse<?> argumentExceptionHandler(ArgumentException e) {
        return TheResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 权限异常处理方法
     *
     * @param e
     * @return 包含错误原因的通用响应体对象
     */
    @ExceptionHandler(NotRoleException.class)
    public BaseResponse<?> notRoleExceptionHandler(NotRoleException e) {
        return TheResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 登录异常处理方法
     *
     * @param e
     * @return 包含错误原因的通用响应体对象s
     */
    @ExceptionHandler(NotLoginException.class)
    public BaseResponse<?> notLoginExceptionHandler(NotLoginException e) {
        return TheResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 系统异常处理方法
     *
     * @param e
     * @return 包含错误原因的通用响应体对象
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        return TheResult.error(ErrorCode.SYSTEM_ERROR);
    }
}