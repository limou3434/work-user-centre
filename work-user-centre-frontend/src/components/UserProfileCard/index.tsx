/**
 *  ./src/components/UserProfileCard/index.tsx: 用户信息卡片
 */
"use client";

import "./index.css";
import React from "react";
import {Avatar, Card, Descriptions, Tag, Typography} from "antd";
import ClickSpark from "@/components/ClickSpark";
import {useTranslation} from "react-i18next";

const {Title, Paragraph} = Typography;

interface UserProfileCardProps {
    user: API.LoginUserVO;
}

const UserProfileCard: React.FC<UserProfileCardProps> = ({user}) => {
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

    const {t} = useTranslation();

    return (
        <div className="user-profile-card" style={{width: "100%", height: "80vh", margin: "0 auto"}}>
            <Card variant="borderless" hoverable={true} style={{width: "100%"}}>
                <ClickSpark>
                    <div style={{textAlign: "center", marginTop: -50}}>
                        <Avatar size={80} src={user.userAvatar || "/assets/logo.svg"}/>
                        <Title level={3} style={{marginTop: 10}}>{user.userNick || "未填写昵称"}</Title>
                        <Paragraph type="secondary">{user.userProfile || "这个人很神秘，什么都没写。"}</Paragraph>
                    </div>
                    <Descriptions bordered column={1} size="small" style={{marginTop: 20}}>
                        <Descriptions.Item label={t("user_account")}>{user.userAccount || "N/A"}</Descriptions.Item>
                        <Descriptions.Item label={t("user_email")}>{user.userEmail || "N/A"}</Descriptions.Item>
                        <Descriptions.Item label={t("user_phone")}>{user.userPhone || "N/A"}</Descriptions.Item>
                        <Descriptions.Item label={t("user_birthday")}>{user.userBirthday || "xxxx-xx-xx"}</Descriptions.Item>
                        <Descriptions.Item label={t("user_gender")}>
                            <Tag
                                color={user.userGender === 0 ? "gray" : user.userGender === 1 ? "blue" : user.userGender === 2 ? "pink" : "gray"}>
                                {/*@ts-ignore*/}
                                {genderMap[user.userGender ?? 0]}
                            </Tag>
                        </Descriptions.Item>
                        <Descriptions.Item label={t("user_role")}>
                            <Tag
                                color={user.userRole === 2 ? "green" : user.userRole === 1 ? "gold" : user.userRole === 0 ? "red" : "gray"}>
                                {/*@ts-ignore*/}
                                {roleMap[user.userRole ?? 0]}
                            </Tag>
                        </Descriptions.Item>
                        <Descriptions.Item label={t("user_country")}>{user.userCountry || "未知"}</Descriptions.Item>
                        <Descriptions.Item label={t("user_address")}>{user.userAddress || "未知"}</Descriptions.Item>
                    </Descriptions>
                </ClickSpark>
            </Card>
        </div>
    );
};

export default UserProfileCard;
