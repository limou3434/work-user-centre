package com.work.workusercentre.utils;

import com.work.workusercentre.common.BaseResponse;
import com.work.workusercentre.common.ErrorCode;

import static com.work.workusercentre.common.ErrorCode.SUCCESS;

/**
 * 便捷响应体包装工具
 *
 * @author ljp
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(SUCCESS, data);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }
}
