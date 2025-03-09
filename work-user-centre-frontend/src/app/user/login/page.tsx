// src/app/user/login/page.tsx

/* 渲染 */
"use client"; // 注释本行则默认服务端渲染

/* 样式 */
import "./page.css";

/* 引入 */
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { LoginForm, ProFormText } from "@ant-design/pro-components";
import React from "react";
import Image from "next/image";
import Link from "next/link";
import { useDispatch } from "react-redux";
import { AppDispatch } from "@/stores";
import { ProForm } from "@ant-design/pro-form/lib";
import { useRouter } from "next/navigation";
import axios from "axios";
import {message} from "antd";
import {setLoginUser} from "@/stores/loginUser";
import {userLogin} from "@/api/userController";

/* 定义 */
const UserLoginPage: React.FC = () => {
    // 项目名称
    const projectName = "工作室用户中心"; // TODO: 从文件中读取

    // 项目信息
    const projectInfo = "科教平台工作室用户中心系统"; // TODO: 从文件中读取

    // 登入状态
    const dispatch = useDispatch<AppDispatch>();

    // 表单实例
    const [form] = ProForm.useForm();

    // 重定向页面
    const router = useRouter();

    // 登入接口
    const doSubmit = async (values: API.UserLoginRequest): Promise<void> => {
        // 提交登入表单
        try {
            const res = await userLogin(values);
            console.log(res);
            console.log(res.data);
            if (res.data) {
                message.success("登入成功"); // 提示登入成功
                // @ts-ignore
                // dispatch(setLoginUser(res.data)); // 保存用户登入状态
                router.replace("/"); // 跳转页面
                form.resetFields(); // 重置表单
            }
        } catch (e) {
            // @ts-ignore
            message.error("登入失败: " + e.message); // 提示登入失败
        }
    };

    return (
        <div id="userLoginPage" className="max-width-content">
            {/* 登入组件 */}
            <LoginForm
                form={form}
                logo={
                    <Image
                        src="/assets/logo.svg"
                        alt={projectName}
                        height={45}
                        width={45}
                    />
                }
                title={projectName + " - 登入"}
                subTitle={projectInfo}
                onFinish={doSubmit}
            >
                {/* 用户名称 */}
                <ProFormText
                    name="userAccount"
                    fieldProps={{
                        size: "large",
                        prefix: <UserOutlined />,
                    }}
                    placeholder={"请输入正确的帐号"}
                    rules={[
                        {
                            required: true,
                            message: "请输入正确的帐号!",
                        },
                    ]}
                />
                {/* 用户密码 */}
                <ProFormText.Password
                    name="userPasswd"
                    fieldProps={{
                        size: "large",
                        prefix: <LockOutlined />,
                    }}
                    placeholder={"请输入正确的密码"}
                    rules={[
                        {
                            required: true,
                            message: "请输入正确的密码！",
                        },
                    ]}
                />
                {/* 注册帐号 */}
                <div
                    style={{
                        marginBlockEnd: 24,
                    }}
                >
                    <div
                        style={{
                            float: "right",
                            marginBlockEnd: 24,
                        }}
                    >
                        还没有帐号？
                        <Link href={"/user/register"}>注册帐号</Link>
                    </div>
                </div>
            </LoginForm>
        </div>
    );
};

/* 导出 */
export default UserLoginPage;
