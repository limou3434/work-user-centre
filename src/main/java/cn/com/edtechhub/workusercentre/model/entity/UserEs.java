package cn.com.edtechhub.workusercentre.model.entity;

import cn.com.edtechhub.workusercentre.model.entity.User;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户索引
 */
@Document(indexName = "user")
@Data
public class UserEs implements Serializable {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 本用户唯一标识(业务层需要考虑使用雪花算法用户标识的唯一性)
     */
    @Id
    private Long id;

    /**
     * 账户号(业务层需要决定某一种或多种登录方式, 因此这里不限死为非空)
     */
    @Field(name = "account") // 显式指定 ES 字段名, 避免字段风格不一致
    private String account;

    /**
     * 微信号
     */
    @Field(name = "wx_union") // 显式指定 ES 字段名, 避免字段风格不一致
    private String wxUnion;

    /**
     * 公众号
     */
    @Field(name = "mp_open") // 显式指定 ES 字段名, 避免字段风格不一致
    private String mpOpen;

    /**
     * 邮箱号
     */
    @Field(name = "email") // 显式指定 ES 字段名, 避免字段风格不一致
    private String email;

    /**
     * 电话号
     */
    @Field(name = "phone") // 显式指定 ES 字段名, 避免字段风格不一致
    private String phone;

    /**
     * 身份证
     */
    @Field(name = "ident") // 显式指定 ES 字段名, 避免字段风格不一致
    private String ident;

    /**
     * 用户密码(业务层强制刚刚注册的用户重新设置密码, 交给用户时默认密码为 123456, 并且加盐密码)
     */
    @Field(name = "passwd") // 显式指定 ES 字段名, 避免字段风格不一致
    private String passwd;

    /**
     * 用户头像(业务层需要考虑默认头像使用 cos 对象存储)
     */
    @Field(name = "avatar") // 显式指定 ES 字段名, 避免字段风格不一致
    private String avatar;

    /**
     * 用户标签(业务层需要 json 数组格式存储用户标签数组)
     */
    @Field(name = "tags") // 显式指定 ES 字段名, 避免字段风格不一致
    private String tags;

    /**
     * 用户昵称
     */
    @Field(name = "nick") // 显式指定 ES 字段名, 避免字段风格不一致
    private String nick;

    /**
     * 用户名字
     */
    @Field(name = "name") // 显式指定 ES 字段名, 避免字段风格不一致
    private String name;

    /**
     * 用户简介
     */
    @Field(name = "profile") // 显式指定 ES 字段名, 避免字段风格不一致
    private String profile;

    /**
     * 用户生日
     */
    @Field(name = "birthday") // 显式指定 ES 字段名, 避免字段风格不一致
    private String birthday;

    /**
     * 用户国家
     */
    @Field(name = "country") // 显式指定 ES 字段名, 避免字段风格不一致
    private String country;

    /**
     * 用户地址
     */
    @Field(name = "address") // 显式指定 ES 字段名, 避免字段风格不一致
    private String address;

    /**
     * 用户角色(业务层需知 -1 为封号, 0 为用户, 1 为管理, ...)
     */
    @Field(name = "role") // 显式指定 ES 字段名, 避免字段风格不一致
    private Integer role;

    /**
     * 用户等级(业务层需知 0 为 level0, 1 为 level1, 2 为 level2, 3 为 level3, ...)
     */
    @Field(name = "level") // 显式指定 ES 字段名, 避免字段风格不一致
    private Integer level;

    /**
     * 用户性别(业务层需知 0 为未知, 1 为男性, 2 为女性)
     */
    @Field(name = "gender") // 显式指定 ES 字段名, 避免字段风格不一致
    private Integer gender;

    /**
     * 是否删除(0 为未删除, 1 为已删除)
     */
    @Field(name = "deleted") // 显式指定 ES 字段名, 避免字段风格不一致
    private Integer deleted; // 手动修改为逻辑删除

    /**
     * 创建时间(受时区影响)
     */
    @Field(name = "create_time", type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    // 显式指定 ES 字段名, 避免字段风格不一致
    private Date createTime;

    /**
     * 更新时间(受时区影响)
     */
    @Field(name = "update_time", type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    // 显式指定 ES 字段名, 避免字段风格不一致
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 对象转包装类
     */
    public static UserEs MappingToEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEs userEs = new UserEs();
        BeanUtils.copyProperties(user, userEs);

        return userEs;
    }

    /**
     * 包装类转对象
     */
    public static User EntityToMapping(UserEs userEs) {
        if (userEs == null) {
            return null;
        }

        User user = new User();
        BeanUtils.copyProperties(userEs, user);

        return user;
    }

}
