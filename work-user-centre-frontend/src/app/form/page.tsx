"use client";

import "./page.css";
import React, {useEffect} from "react";
import {Button, Col, Form, Input, message, Popconfirm, Row} from "antd";
import {useTranslation} from "react-i18next";
import {userStatus, userUpdateSelf} from "@/api/userController";

export default function FormPage() {
    const [form] = Form.useForm(); // 用于存储表单数据
    const {t} = useTranslation();
    const [data, setData] = React.useState<API.LoginUserVO>();

    useEffect(() => {
        (async () => {
            try {
                const res = await userStatus();
                // @ts-ignore
                form.setFieldsValue(res.data); // 填充表单数据
                message.success("访问数据库成功", 2);
            } catch (error) {
                message.error("未知错误", 2);
            }
        })();
    }, []);

    const handleConfirm = async () => {
        try {
            const values = await form.validateFields(); // 先获取表单数据 // TODO: 这里需要考虑校验的问题

            delete values.id; // 删除 id，不让它提交
            delete values.userRole; // 删除 userRole，不让它提交
            delete values.userLevel; // 删除 userLevel，不让它提交

            const res = await userUpdateSelf(values); // 传入表单数据
            if (res?.data) {
                // @ts-ignore
                setData(res.data); // 更新子项数据
                message.success(t("push_update_success"));
            } else {
                message.error(t("push_update_failed"));
            }
        } catch (e) {
            message.error(t("unknown_error"));
        }
    };

    return (
        <div id="formPage">
            <Form
                form={form}
                layout="vertical">
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item label={t("id")} name="id">
                            <Input disabled/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_account")} name="userAccount">
                            <Input disabled/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_wx_union")} name="userWxUnion">
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_mp_open")} name="userMpOpen">
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_email")} name="userEmail">
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_phone")} name="userPhone">
                            <Input/>
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item label={t("user_avatar")} name="userAvatar">
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_tags")} name="userTags">
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_nick")} name="userNick">
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_name")} name="userName">
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_profile")} name="userProfile">
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_birthday")} name="userBirthday">
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_country")} name="userCountry">
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_address")} name="userAddress">
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_role")} name="userRole">
                            <Input disabled/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item label={t("user_level")} name="userLevel">
                            <Input disabled/>
                        </Form.Item> {/* TODO: 渲染 Tag 和权限 */}
                    </Col>
                </Row>
            </Form>
            <Popconfirm
                placement="top"
                title={t("are_you_sure_to_submit_the_changes")}
                description={t("confirm_submission")}
                okText="Yes"
                cancelText="No"
                onConfirm={handleConfirm}
            >
                <Button
                    color="primary"
                    variant="outlined"
                >
                    {t("push_update")}
                </Button>
            </Popconfirm>
        </div>
    );
}
