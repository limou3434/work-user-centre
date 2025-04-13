package com.work.workusercentre.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除用户请求
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@Data
public class UserDeleteRequest implements Serializable {

    /**
     * 本用户唯一标识(业务层需要考虑使用雪花算法用户标识的唯一性)
     */
    private Long id;

    @Serial
    private static final long serialVersionUID = 1L;

}
