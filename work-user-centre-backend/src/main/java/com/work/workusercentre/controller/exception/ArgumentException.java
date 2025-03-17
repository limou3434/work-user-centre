package com.work.workusercentre.controller.exception;

import com.work.workusercentre.controller.response.ErrorCodeBindMessage;
import lombok.Getter;

/**
 * 参数异常类
 *
 * @author ljp
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
     * @param errorCodeBindMessage 错误-含义 枚举体
     * @param exceptionMessage 异常信息
     */
    public ArgumentException(ErrorCodeBindMessage errorCodeBindMessage, String exceptionMessage) {
        super(errorCodeBindMessage.getMessage() + ": " + exceptionMessage);
        this.code = errorCodeBindMessage.getCode();
    }
}