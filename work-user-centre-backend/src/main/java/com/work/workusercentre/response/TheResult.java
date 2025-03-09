package com.work.workusercentre.response;

import static com.work.workusercentre.response.ErrorCode.SUCCESS;

/**
 * 便捷响应体包装类
 *
 * @author ljp
 */
public class TheResult {
    /**
     * 构造成功响应体
     *
     * @param data
     * @param <T>
     * @return 通用响应体对象
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(SUCCESS, data);
    }

    /**
     * 构造成功响应体
     *
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return 通用响应体对象
     */
    public static <T> BaseResponse<T> success(int code, String message, T data) {
        return new BaseResponse<>(code, message, data);
    }

    /**
     * 构造失败响应体
     *
     * @param errorCode
     * @return 通用响应体对象
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 构造失败响应体
     *
     * @param code
     * @param message
     * @return 通用响应体对象
     */
    public static <T> BaseResponse<T> error(int code, String message) {
        return new BaseResponse<>(code, message);
    }
}
