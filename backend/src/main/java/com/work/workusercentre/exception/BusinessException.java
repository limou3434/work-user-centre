package com.work.workusercentre.exception;

import com.work.workusercentre.enums.CodeBindMessage;
import lombok.Getter;

/**
 * 业务内异常类
 *
 * @author <a href="https://github.com/xiaogithuboo">limou3434</a>
 */
@Getter
public class BusinessException extends RuntimeException {

    CodeBindMessage codeBindMessage;

    String exceptionMessage;

    /**
     * 构造异常对象
     *
     * @param codeBindMessage 错误-含义 枚举体
     * @param exceptionMessage 异常信息
     */
    public BusinessException(CodeBindMessage codeBindMessage, String exceptionMessage) {
        this.codeBindMessage = codeBindMessage;
        this.exceptionMessage = exceptionMessage;
    }

}
