package com.work.workusercentre.controller.response;

import static com.work.workusercentre.controller.response.ErrorCodeBindMessage.SUCCESS;

/**
 * 便捷响应体包装类
 *
 * @author ljp
 */
public class TheResult {
    /**
     * 构造成功响应体
     *
     * @param data 数据
     * @param <T> data 的类型
     * @return 通用响应体对象
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(SUCCESS, data);
    }

    /**
     * 构造成功响应体
     *
     * @param code 状态
     * @param message 含义
     * @param data 数据
     * @param <T> data 的类型
     * @return 通用响应体对象
     */
    public static <T> BaseResponse<T> success(int code, String message, T data) {
        return new BaseResponse<>(code, message, data);
    }

    /**
     * 构造失败响应体
     *
     * @param errorCodeBindMessage 错误-含义 枚举体
     * @return 通用响应体对象
     */
    public static <T> BaseResponse<T> error(ErrorCodeBindMessage errorCodeBindMessage) {
        return new BaseResponse<>(errorCodeBindMessage);
    }

    /**
     * 构造失败响应体
     *
     * @param code 状态
     * @param message 含义
     * @return 通用响应体对象
     */
    public static <T> BaseResponse<T> error(int code, String message) {
        return new BaseResponse<>(code, message);
    }
}
