package com.work.workusercentre.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询用户请求
 *
 * @author <a href="https://github.com/xiaogithuboo">limou3434</a>
 */
@EqualsAndHashCode(callSuper = true) // 自动生成 equals() 和 hashCode(), callSuper = true 使得 equals() 和 hashCode() 方法同时考虑 PageRequest 和 UserSearchRequest 的字段, 避免出现意料之外的情况
@Data
public class UserSearchRequest extends PageRequest implements Serializable {

    /**
     * 本用户唯一标识(业务层需要考虑使用雪花算法用户标识的唯一性)
     */
    private Long id;

    /**
     * 账户号(业务层需要决定某一种或多种登录方式, 因此这里不限死为非空)
     */
    private String account;

    /**
     * 用户名字
     */
    private String name;

    /**
     * 用户角色(业务层需知 -1 为封号, 0 为用户, 1 为管理, ...)
     */
    private Integer role;

    /**
     * 用户等级(业务层需知 0 为 level0, 1 为 level1, 2 为 level2, 3 为 level3, ...)
     */
    private Integer level;

    @Serial
    private static final long serialVersionUID = 1L;

}
