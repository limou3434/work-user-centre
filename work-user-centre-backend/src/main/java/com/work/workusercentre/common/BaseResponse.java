package com.work.workusercentre.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 通用响应体描述类
 *
 * @author ljp
 */
@Data
public class BaseResponse<T> implements Serializable {
    /**
     * 状态
     */
    private int code;

    /**
     * 含义
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public BaseResponse(ErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }

    public BaseResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = null;
    }
}