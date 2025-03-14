/**
 *  ./src/components/UserProfileCard/index.tsx: 用户信息卡片
 */
"use client";

import React from "react";
import { Card, Avatar, Typography, Descriptions, Tag } from "antd";

const { Title, Paragraph } = Typography;

interface UserProfileCardProps {
    user: API.LoginUserVO;
}

const UserProfileCard: React.FC<UserProfileCardProps> = ({ user }) => {
    // 角色映射
    const roleMap = {
        0: "普通用户",
        1: "管理员",
        2: "超级管理员",
    };

    // 性别映射
    const genderMap = {
        0: "未知",
        1: "男",
        2: "女",
    };

    return (
        <div className="user-profile-card" style={{ width: "100%", margin: "0 auto" }}>
            <Card variant="borderless" style={{ width: "100%" }}>
                <div style={{ textAlign: "center", marginTop: -50 }}>
                    <Avatar size={80} src={user.userAvatar || "/assets/logo.svg"}/>
                    <Title level={3} style={{ marginTop: 10 }}>{user.userNick || "未填写昵称"}</Title>
                    <Paragraph type="secondary">{user.userProfile || "这个人很神秘，什么都没写。"}</Paragraph>
                </div>
                <Descriptions bordered column={1} size="small" style={{ marginTop: 20 }}>
                    <Descriptions.Item label="账号">{user.userAccount || "N/A"}</Descriptions.Item>
                    <Descriptions.Item label="邮箱">{user.userEmail || "N/A"}</Descriptions.Item>
                    <Descriptions.Item label="手机">{user.userPhone || "N/A"}</Descriptions.Item>
                    <Descriptions.Item label="生日">{user.userBirthday || "xxxx-xx-xx"}</Descriptions.Item>
                    <Descriptions.Item label="性别">
                        <Tag color={ user.userGender === 0 ? "gray" : user.userGender === 1 ? "blue" : user.userGender === 2 ? "pink" : "gray"}>
                            {/*@ts-ignore*/}
                            {genderMap[user.userGender ?? 0]}
                        </Tag>
                    </Descriptions.Item>
                    <Descriptions.Item label="角色">
                        <Tag color={user.userRole === 2 ? "green" : user.userRole === 1 ? "gold" : user.userRole === 0 ? "red" : "gray"}>
                            {/*@ts-ignore*/}
                            {roleMap[user.userRole ?? 0]}
                        </Tag>
                    </Descriptions.Item>
                    <Descriptions.Item label="国家">{user.userCountry || "未知"}</Descriptions.Item>
                    <Descriptions.Item label="地址">{user.userAddress || "未知"}</Descriptions.Item>
                </Descriptions>
            </Card>
        </div>
    );
};

export default UserProfileCard;
