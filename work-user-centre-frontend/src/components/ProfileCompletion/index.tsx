import React from "react";
import { Progress, List } from "antd";

// TODO: 这个组件有点问题

// 定义 API 类型
export type API = {
    LoginUserVO: {
        userAccount: string | null;
        userEmail: string | null;
        userPhone: string | null;
        userAvatar: string | null;
        userName: string | null;
        userProfile: string | null;
        userBirthday: string | null;
        userCountry: string | null;
        userGender: number | null;
    };
};

// ProfileCompletion 组件的 props
interface UserProfileCardProps {
    user: API.LoginUserVO;
}

// 计算信息完成度
const calculateCompletion = (data: Record<string, any>) => {
    const fields = [
        { key: "userAvatar", weight: 10 },
        { key: "userName", weight: 10 },
        { key: "userEmail", weight: 15 },
        { key: "userPhone", weight: 15 },
        { key: "userProfile", weight: 10 },
        { key: "userBirthday", weight: 10 },
        { key: "userCountry", weight: 10 },
        { key: "userGender", weight: 10 },
        { key: "userAccount", weight: 10 },
    ];

    const totalWeight = fields.reduce((sum, field) => sum + field.weight, 0);
    const completedWeight = fields.reduce(
        (sum, field) => sum + (data[field.key] !== null ? field.weight : 0),
        0
    );

    return Math.round((completedWeight / totalWeight) * 100);
};

// 获取未完成项
const getIncompleteFields = (data: Record<string, any>) => {
    const fieldLabels: Record<string, string> = {
        userAvatar: "上传头像",
        userName: "填写用户名",
        userEmail: "绑定邮箱",
        userPhone: "绑定手机号",
        userProfile: "填写个人介绍",
        userBirthday: "填写生日",
        userCountry: "填写国家",
        userGender: "填写性别",
        userAccount: "填写账户信息",
    };

    return Object.keys(data)
        .filter((key) => data[key] === null) // 检查字段是否为 null
        .map((key) => fieldLabels[key]);
};

const ProfileCompletion: React.FC<UserProfileCardProps> = ({ user }) => {
    const completion = calculateCompletion(user);
    const incompleteFields = getIncompleteFields(user);


    return (
        <div className="profile-completion" style={{ maxWidth: 400, margin: "20px auto" }}>
            <h3>当前登陆用户信息完成度</h3>
            <Progress percent={completion} status={completion < 100 ? "active" : "success"} />

            {incompleteFields.length > 0 && (
                <List
                    header={<b>完善以下信息可提升完成度：</b>}
                    dataSource={incompleteFields}
                    // @ts-ignore
                    renderItem={(item, index) => <List.Item key={index}>✗ {item}</List.Item>}
                />
            )}
        </div>
    );
};

export default ProfileCompletion;
