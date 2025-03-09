-- ./sql/init_structure.sql: 数据源整体结构
-- 项目数库
DROP DATABASE IF EXISTS work_user_centre;
CREATE DATABASE work_user_centre COLLATE = utf8mb4_unicode_ci;
USE work_user_centre;

-- 项目用户
DROP USER IF EXISTS 'work_uc'@'%';
CREATE USER 'work_uc'@'%' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON work_user_centre.* TO 'work_uc'@'%';
FLUSH PRIVILEGES;

-- 项目数表
CREATE TABLE user (
      id              BIGINT UNSIGNED    AUTO_INCREMENT                                        COMMENT '本用户唯一标识(业务层需要考虑使用雪花算法用户标识的唯一性)',
      user_account    VARCHAR(256)       NULL                                                  COMMENT '账户号(业务层需要决定某一种或多种登录方式, 因此这里不限死为非空)', -- 采用下划线风格, 采用表名前缀标识业务中的重要字段避免关键字冲突问题
      user_wx_union   VARCHAR(256)       NULL                                                  COMMENT '微信号',
      user_mp_open    VARCHAR(256)       NULL                                                  COMMENT '公众号',
      user_email      VARCHAR(256)       NULL                                                  COMMENT '邮箱号',
      user_phone      VARCHAR(20)        NULL                                                  COMMENT '电话号',
      user_ident      VARCHAR(50)        NULL                                                  COMMENT '身份证',
      user_passwd     VARCHAR(512)       NOT NULL                                              COMMENT '用户密码(业务层强制刚刚注册的用户重新设置密码, 交给用户时默认密码为 1234567890, 并且加盐密码)',
      user_avatar     VARCHAR(1024)      NULL                                                  COMMENT '用户头像(业务层需要考虑默认头像使用 cos 对象存储)',
      user_tags       VARCHAR(1024)      NULL                                                  COMMENT '用户标签(业务层需要 json 数组格式存储用户标签数组)',
      user_nick       VARCHAR(256)       NULL                                                  COMMENT '用户昵称',
      user_name       VARCHAR(256)       NULL                                                  COMMENT '用户名字',
      user_profile    VARCHAR(512)       NULL                                                  COMMENT '用户简介',
      user_birthday   VARCHAR(512)       NULL                                                  COMMENT '用户生日',
      user_country    VARCHAR(50)        NULL                                                  COMMENT '用户国家',
      user_address    TEXT               NULL                                                  COMMENT '用户地址',
      user_role       TINYINT            DEFAULT 0                                             COMMENT '用户角色(业务层需知 0 为用户, 1 为管理, 2 为封号, ...)',
      user_level      TINYINT            DEFAULT 0                                             COMMENT '用户等级(业务层需知 0 为普通, 1 为高级, 2 为特权, 3 为企业, ...)',
      user_gender     TINYINT            DEFAULT 0                                             COMMENT '用户性别(业务层需知 0 为未知, 1 为男性, 2 为女性)',
      deleted         TINYINT            DEFAULT 0                                             COMMENT '是否删除(0 为未删除, 1 为已删除)',
      create_time     TIMESTAMP          DEFAULT CURRENT_TIMESTAMP                             COMMENT '创建时间(受时区影响)',
      update_time     TIMESTAMP          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间(受时区影响)',
      PRIMARY KEY (id), -- 主键
      INDEX idx_email (user_email), -- 根据数据是否具备区分度来建立索引
      INDEX idx_user_nick (user_nick)
) COLLATE = utf8mb4_unicode_ci                                                                 COMMENT '用户信息表';