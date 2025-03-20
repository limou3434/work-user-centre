"use client";

import "./page.css";
import React, {useEffect, useState} from "react";
import {Button, Card, Form, Input, message, Popconfirm, Popover} from "antd";
import {useTranslation} from "react-i18next";
import {userStatus, userUpdateSelf} from "@/api/userController";

/**
 * 表单页面
 */
export default function FormPage() {

    // NOTE: Data
    const {t} = useTranslation();
    const [form] = Form.useForm();
    const [data, setData] = useState<API.LoginUserVO>();
    const [avatar, setAvatar] = useState<string>("");

    // NOTE: Func
    const handleConfirm = async () => {
        try {
            const values = await form.validateFields();
            delete values.id;
            delete values.userRole;
            delete values.userLevel;

            const res = await userUpdateSelf(values);
            if (res?.data) {
                // @ts-ignore
                setData(res.data || []);
                message.success(t("push_update_success"));
            } else {
                message.error(t("push_update_failed"));
            }
        } catch (e) {
            message.error(t("unknown_error"));
        }
    };

    // NOTE: Hook
    useEffect(() => {
        (async () => {
            try {
                const res = await userStatus();
                form.setFieldsValue(res.data);
                // @ts-ignore
                setAvatar(res.data.userAvatar || ""); // 设置头像预览
                message.success("访问数据库成功", 2);
            } catch (error) {
                message.error("未知错误", 2);
            }
        })();
    }, []);

    // NOTE: Render
    return (
        <div id="formPage">
            <Card variant="borderless" hoverable={true} style={{width: "100%", height: "100%"}}>
                <Form
                    form={form}
                    layout="horizontal"
                    initialValues={{remember: true}}
                    autoComplete="off"
                >
                    <Form.Item label={t("id")} name="id">
                        <Input disabled/>
                    </Form.Item>
                    <Form.Item label={t("user_account")} name="userAccount">
                        <Input disabled/>
                    </Form.Item>
                    <Form.Item label={t("user_wx_union")} name="userWxUnion">
                        <Input/>
                    </Form.Item>
                    <Form.Item label={t("user_mp_open")} name="userMpOpen">
                        <Input/>
                    </Form.Item>
                    <Form.Item label={t("user_email")} name="userEmail">
                        <Input/>
                    </Form.Item>
                    <Form.Item label={t("user_phone")} name="userPhone">
                        <Input/>
                    </Form.Item>
                    <Form.Item label={t("user_avatar")} name="userAvatar">
                        <Popover
                            content={
                                avatar ? (
                                    <img
                                        src={avatar}
                                        alt="Avatar Preview"
                                        style={{
                                            width: 100,
                                            height: 100,
                                            objectFit: "cover",
                                            borderRadius: 8,
                                        }}
                                    />
                                ) : (
                                    "请输入头像 URL"
                                )
                            }
                            trigger="hover"
                        >
                            <Input
                                value={avatar}
                                onChange={(e) => setAvatar(e.target.value)}
                                placeholder="输入头像 URL，悬停预览"
                            />
                        </Popover>
                    </Form.Item>
                    <Form.Item label={t("user_tags")} name="userTags">
                        <Input/>
                    </Form.Item>

                    <Form.Item label={t("user_nick")} name="userNick">
                        <Input/>
                    </Form.Item>
                    <Form.Item label={t("user_name")} name="userName">
                        <Input/>
                    </Form.Item>
                    <Form.Item label={t("user_profile")} name="userProfile">
                        <Input/>
                    </Form.Item>
                    <Form.Item label={t("user_birthday")} name="userBirthday">
                        <Input/>
                    </Form.Item>
                    <Form.Item label={t("user_country")} name="userCountry">
                        <Input/>
                    </Form.Item>
                    <Form.Item label={t("user_address")} name="userAddress">
                        <Input/>
                    </Form.Item>
                    <Form.Item label={t("user_role")} name="userRole">
                        <Input disabled/>
                    </Form.Item>
                    <Form.Item label={t("user_level")} name="userLevel">
                        <Input disabled/>
                    </Form.Item>
                    <Form.Item label={null}>
                        <Popconfirm
                            placement="top"
                            title={t("are_you_sure_to_submit_the_changes")}
                            description={t("confirm_submission")}
                            okText="Yes"
                            cancelText="No"
                            onConfirm={handleConfirm}
                        >
                            <Button color="primary" variant="outlined">
                                {t("push_update")}
                            </Button>
                        </Popconfirm>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    );

}
