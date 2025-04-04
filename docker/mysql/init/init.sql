-- 清理残留
DROP DATABASE IF EXISTS work_user_centre;
CREATE DATABASE work_user_centre CHARACTER SET utf8mb4 COLLATE = utf8mb4_unicode_ci;
USE work_user_centre;

-- 项目用户
DROP USER IF EXISTS 'wuc'@'%';
CREATE USER 'wuc'@'%' IDENTIFIED BY 'Qwe54188_';
GRANT ALL PRIVILEGES ON work_user_centre.* TO 'wuc'@'%';
FLUSH PRIVILEGES;

-- 项目数表
CREATE TABLE user_role (
    id              TINYINT            NOT NULL                                              COMMENT '本角色唯一标识(业务层需要考虑使用雪花算法用户标识的唯一性)',
    user_role_name  VARCHAR(50)        NULL                                                  COMMENT '角色名称',
    PRIMARY KEY (id) -- 主键
) COLLATE = utf8mb4_unicode_ci                                                               COMMENT '用户角色表';

INSERT INTO user_role (id, user_role_name) VALUES
    (0, '用户'),
    (1, '管理'),
    (2, '封号')
;

CREATE TABLE user (
    id              BIGINT UNSIGNED    AUTO_INCREMENT                                        COMMENT '本用户唯一标识(业务层需要考虑使用雪花算法用户标识的唯一性)',
    user_account    VARCHAR(256)       NULL                                                  COMMENT '账户号(业务层需要决定某一种或多种登录方式, 因此这里不限死为非空)', -- 采用下划线风格, 采用表名前缀标识业务中的重要字段避免关键字冲突问题
    user_wx_union   VARCHAR(256)       NULL                                                  COMMENT '微信号',
    user_mp_open    VARCHAR(256)       NULL                                                  COMMENT '公众号',
    user_email      VARCHAR(256)       NULL                                                  COMMENT '邮箱号',
    user_phone      VARCHAR(20)        NULL                                                  COMMENT '电话号',
    user_ident      VARCHAR(50)        NULL                                                  COMMENT '身份证',
    user_passwd     VARCHAR(512)       NOT NULL                                              COMMENT '用户密码(业务层强制刚刚注册的用户重新设置密码, 交给用户时默认密码为 123456, 并且加盐密码)',
    user_avatar     VARCHAR(1024)      NULL                                                  COMMENT '用户头像(业务层需要考虑默认头像使用 cos 对象存储)',
    user_tags       VARCHAR(1024)      NULL                                                  COMMENT '用户标签(业务层需要 json 数组格式存储用户标签数组)',
    user_nick       VARCHAR(256)       NULL                                                  COMMENT '用户昵称',
    user_name       VARCHAR(256)       NULL                                                  COMMENT '用户名字',
    user_profile    VARCHAR(512)       NULL                                                  COMMENT '用户简介',
    user_birthday   VARCHAR(512)       NULL                                                  COMMENT '用户生日',
    user_country    VARCHAR(50)        NULL                                                  COMMENT '用户国家',
    user_address    TEXT               NULL                                                  COMMENT '用户地址',
    user_role       TINYINT            DEFAULT 0                                             COMMENT '用户角色(业务层需知 0 为用户, 1 为管理, 2 为封号, ...)',
    user_level      TINYINT            DEFAULT 0                                             COMMENT '用户等级(业务层需知 0 为 level0, 1 为 level1, 2 为 level2, 3 为 level3, ...)',
    user_gender     TINYINT            DEFAULT 0                                             COMMENT '用户性别(业务层需知 0 为未知, 1 为男性, 2 为女性)',
    deleted         TINYINT            DEFAULT 0                                             COMMENT '是否删除(0 为未删除, 1 为已删除)',
    create_time     TIMESTAMP          DEFAULT CURRENT_TIMESTAMP                             COMMENT '创建时间(受时区影响)',
    update_time     TIMESTAMP          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间(受时区影响)',
    PRIMARY KEY (id), -- 主键
    FOREIGN KEY (user_role) REFERENCES user_role (id)  ON DELETE SET NULL  ON UPDATE CASCADE, -- 外键
    INDEX idx_email (user_email), -- 根据数据是否具备区分度来建立索引
    INDEX idx_user_nick (user_nick)
) COLLATE = utf8mb4_unicode_ci                                                               COMMENT '用户信息表';
-- ON DELETE SET NULL: 如果 user_role 表中的某个 id 被删除, 则 user 表中所有引用该 id 的 user_role 字段会被自动设置为 NULL, 这样可以避免删除 user_role 表的数据时导致 user 表中的数据失效或出错
-- ON UPDATE CASCADE: 如果 user_role 表中的某个 id 被修改(例如 ID 1 被改成 5), 则 user 表中所有引用该 id 的 user_role 字段会自动更新为新的值, 这样可以保持数据一致性，避免 user 表中的外键值失效

INSERT INTO user (user_account, user_wx_union, user_mp_open, user_email, user_phone, user_ident, user_passwd, user_avatar, user_tags, user_nick, user_name, user_profile, user_birthday, user_country, user_address, user_role, user_level, user_gender, deleted)
VALUES
    ('aimou', 'wx_union_aimou', 'mp_open_aimou', 'aimou@example.com', '13800138001', '370101198701012345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_aimou1.jpg', '["tag1"]', 'aimou', '艾梦', '这是艾梦的个人简介', '1987-01-01', '中国', '北京市朝阳区', 0, 1, 1, 0),
    ('bimou', 'wx_union_bimou', 'mp_open_bimou', 'bimou@example.com', '13800138002', '370101198802022345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_bimou2.jpg', '["tag2"]', 'bimou', '白萌', '这是白萌的个人简介', '1988-02-02', '中国', '上海市浦东新区', 0, 2, 2, 0),
    ('cimou', 'wx_union_cimou', 'mp_open_cimou', 'cimou@example.com', '13800138003', '370101198903032345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_cimou3.jpg', '["tag3"]', 'cimou', '陈明', '这是陈明的个人简介', '1989-03-03', '中国', '广州市天河区', 0, 1, 1, 0),
    ('dimou', 'wx_union_dimou', 'mp_open_dimou', 'dimou@example.com', '13800138004', '370101199004042345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_dimou4.jpg', '["tag4"]', 'dimou', '邓梅', '这是邓梅的个人简介', '1990-04-04', '中国', '深圳市福田区', 0, 1, 2, 0),
    ('eimou', 'wx_union_eimou', 'mp_open_eimou', 'eimou@example.com', '13800138005', '370101199105052345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_eimou5.jpg', '["tag5"]', 'eimou', '易萌', '这是易萌的个人简介', '1991-05-05', '中国', '天津市和平区', 0, 2, 1, 0),
    ('fimou', 'wx_union_fimou', 'mp_open_fimou', 'fimou@example.com', '13800138006', '370101199206062345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_fimou6.jpg', '["tag6"]', 'fimou', '范敏', '这是范敏的个人简介', '1992-06-06', '中国', '北京市海淀区', 0, 1, 2, 0),
    ('gimou', 'wx_union_gimou', 'mp_open_gimou', 'gimou@example.com', '13800138007', '370101199307072345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_gimou7.jpg', '["tag7"]', 'gimou', '高梅', '这是高梅的个人简介', '1993-07-07', '中国', '上海市黄浦区', 0, 1, 1, 0),
    ('himou', 'wx_union_himou', 'mp_open_himou', 'himou@example.com', '13800138008', '370101199408082345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_himou8.jpg', '["tag8"]', 'himou', '黄敏', '这是黄敏的个人简介', '1994-08-08', '中国', '广州市越秀区', 0, 2, 2, 0),
    ('iimou', 'wx_union_iimou', 'mp_open_iimou', 'iimou@example.com', '13800138009', '370101199509092345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_iimou9.jpg', '["tag9"]', 'iimou', '冯萌', '这是冯萌的个人简介', '1995-09-09', '中国', '深圳市南山区', 0, 1, 1, 0),
    ('jimou', 'wx_union_jimou', 'mp_open_jimou', 'jimou@example.com', '13800138010', '370101199610102345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_jimou10.jpg', '["tag10"]', 'jimou', '贾梅', '这是贾梅的个人简介', '1996-10-10', '中国', '天津市南开区', 0, 2, 2, 0),
    ('kimou', 'wx_union_kimou', 'mp_open_kimou', 'kimou@example.com', '13800138011', '370101199711112345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_kimou11.jpg', '["tag11"]', 'kimou', '康铭', '这是康铭的个人简介', '1997-11-11', '中国', '上海市静安区', 0, 1, 1, 0),
    ('limou', 'wx_union_limou', 'mp_open_limou', 'limou@example.com', '13800138012', '370101199812122345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://avatars.githubusercontent.com/u/113878415?s=400&u=9f10b63e033c9504615bc475581441478424e04b&v=4', '["项目架构师, 后端程序员, 数学爱好者, 运维发烧者"]', 'limou', '李陌', '这是李萌的个人简介', '2004-02-23', '中国', '广州市白云区', 1, 1, 1, 0),
    ('mimou', 'wx_union_mimou', 'mp_open_mimou', 'mimou@example.com', '13800138016', '370101200204162345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_mimou16.jpg', '["tag16"]', 'mimou', '莫敏', '这是莫敏的个人简介', '2002-04-16', '中国', '北京市西城区', 0, 1, 1, 0),
    ('nimou', 'wx_union_nimou', 'mp_open_nimou', 'nimou@example.com', '13800138017', '370101200305172345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_nimou17.jpg', '["tag17"]', 'nimou', '牛梅', '这是牛梅的个人简介', '2003-05-17', '中国', '上海市徐汇区', 0, 1, 2, 0),
    ('oimou', 'wx_union_oimou', 'mp_open_oimou', 'oimou@example.com', '13800138018', '370101200406182345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_oimou18.jpg', '["tag18"]', 'oimou', '欧阳敏', '这是欧阳敏的个人简介', '2004-06-18', '中国', '深圳市龙华区',0, 2, 1, 0),
    ('pimou', 'wx_union_pimou', 'mp_open_pimou', 'pimou@example.com', '13800138019', '370101200507192345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_pimou19.jpg', '["tag19"]', 'pimou', '潘萌', '这是潘萌的个人简介', '2005-07-19', '中国', '广州市花都区', 0, 1, 1, 0),
    ('qimou', 'wx_union_qimou', 'mp_open_qimou', 'qimou@example.com', '13800138020', '370101200608202345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_qimou20.jpg', '["tag20"]', 'qimou', '曲敏', '这是曲敏的个人简介', '2006-08-20', '中国', '上海市杨浦区', 0, 2, 2, 0),
    ('rimou', 'wx_union_rimou', 'mp_open_rimou', 'rimou@example.com', '13800138021', '370101200709212345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_rimou21.jpg', '["tag21"]', 'rimou', '任梅', '这是任梅的个人简介', '2007-09-21', '中国', '天津市武清区', 0, 1, 1, 0),
    ('simou', 'wx_union_simou', 'mp_open_simou', 'simou@example.com', '13800138022', '370101200810222345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_simou22.jpg', '["tag22"]', 'simou', '孙萌', '这是孙萌的个人简介', '2008-10-22', '中国', '北京市昌平区', 0, 1, 2, 0),
    ('timou', 'wx_union_timou', 'mp_open_timou', 'timou@example.com', '13800138023', '370101200911232345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_timou23.jpg', '["tag23"]', 'timou', '陶敏', '这是陶敏的个人简介', '2009-11-23', '中国', '上海市宝山区', 0, 1, 1, 0),
    ('uimou', 'wx_union_uimou', 'mp_open_uimou', 'uimou@example.com', '13800138024', '370101201012242345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_uimou24.jpg', '["tag24"]', 'uimou', '吴梅', '这是吴梅的个人简介', '2010-12-24', '中国', '深圳市龙岗区', 0, 2, 2, 0),
    ('vimou', 'wx_union_vimou', 'mp_open_vimou', 'vimou@example.com', '13800138025', '370101201112252345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_vimou25.jpg', '["tag25"]', 'vimou', '韦敏', '这是韦敏的个人简介', '2011-12-25', '中国', '广州市荔湾区', 0, 2, 1, 0),
    ('wimou', 'wx_union_wimou', 'mp_open_wimou', 'wimou@example.com', '13800138026', '370101201212262345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_wimou26.jpg', '["tag26"]', 'wimou', '魏萌', '这是魏萌的个人简介', '2012-12-26', '中国', '上海市虹口区', 0, 1, 1, 0),
    ('ximou', 'wx_union_ximou', 'mp_open_ximou', 'ximou@example.com', '13800138027', '370101201312272345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_ximou27.jpg', '["tag27"]', 'ximou', '谢敏', '这是谢敏的个人简介', '2013-12-27', '中国', '深圳市南山区', 0, 1, 2, 0),
    ('yimou', 'wx_union_yimou', 'mp_open_yimou', 'yimou@example.com', '13800138028', '370101201412282345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_yimou28.jpg', '["tag28"]', 'yimou', '杨萌', '这是杨萌的个人简介', '2014-12-28', '中国', '北京市朝阳区', 0, 1, 1, 0),
    ('zimou', 'wx_union_zimou', 'mp_open_zimou', 'zimou@example.com', '13800138029', '370101201512292345', 'ff5e16596809393e26ad6af86b9cc72e', 'https://example.com/avatar_zimou29.jpg', '["tag29"]', 'zimou', '张敏', '这是张敏的个人简介', '2015-12-29', '中国', '上海市浦东新区', 0, 2, 2, 0)
;

