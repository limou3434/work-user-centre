package com.work.workusercentre.response;

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

    /**
     * 构造方法
     *
     * @param errorCode
     * @param data
     */
    public BaseResponse(ErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }

    /**
     * 构造方法
     *
     * @param code
     * @param message
     * @param data
     */
    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造方法
     *
     * @param errorCode
     */
    public BaseResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = null;
    }

    /**
     * 构造方法
     *
     * @param code
     * @param message
     */
    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }
}