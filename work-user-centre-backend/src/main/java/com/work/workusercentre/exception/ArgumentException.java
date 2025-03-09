package com.work.workusercentre.exception;

import com.work.workusercentre.response.ErrorCode;
import lombok.Getter;

/**
 * 参数异常类
 *
 * @Author ljp
 */
@Getter
public class ArgumentException extends RuntimeException {
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
    public ArgumentException(ErrorCode errorCode, String exceptionMessage) {
        super(errorCode.getMessage() + ": " + exceptionMessage);
        this.code = errorCode.getCode();
    }
}