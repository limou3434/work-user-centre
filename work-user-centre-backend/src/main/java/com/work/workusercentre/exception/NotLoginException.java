package com.work.workusercentre.exception;

import com.work.workusercentre.response.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 权限异常类
 *
 * @Author ljp
 */
@Getter
@Slf4j
public class NotLoginException extends RuntimeException {
    /**
     * 错误码
     */
    private final int code;

    /**
     * 构造异常对象
     *
     * @param errorCode
     * @param exceptionMessage
     */
    public NotLoginException(ErrorCode errorCode, String exceptionMessage) {
        super(errorCode.getMessage() + ": " + exceptionMessage);
        this.code = errorCode.getCode();
    }
}