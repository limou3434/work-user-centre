package com.work.workusercentre.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    /**
     * 用户角色枚举实例
     */
    USER_ROLE(0, "user"),

    /**
     * 管理角色枚举实例
     */
    ADMIN_ROLE(1, "admin"),

    /**
     * 封号角色枚举实例
     */
    BAN_ROLE(2, "ban");

    /**
     * 角色码值
     */
    private final int code;

    /**
     * 角色描述
     */
    private final String description;

    UserRoleEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据角色码值获取枚举实例
     *
     * @param code 角色码值
     * @return 枚举实例
     */
    public static UserRoleEnum getEnumByCode(int code) {
        for (UserRoleEnum role : UserRoleEnum.values()) {
            if (role.getCode() == code) {
                return role;
            }
        }
        return null;
    }

    /**
     * 根据角色描述获取枚举实例
     *
     * @param description 角色描述
     * @return 枚举实例
     */
    public static UserRoleEnum getEnumByDescription(String description) {
        for (UserRoleEnum role : UserRoleEnum.values()) {
            if (role.getDescription().equals(description)) {
                return role;
            }
        }
        return null;
    }
}
