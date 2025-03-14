/**
 *  ./src/app/layout.tsx: 应用全局初始, 无论访问什么页面都会执行这段代码(逻辑初始化和渲染初始化)
 */
"use client";

import "./globals.css";
import BasicLayout from "../components/BasicLayout";
import {AntdRegistry} from "@ant-design/nextjs-registry";
import React, {useCallback, useEffect} from "react";
import {Provider, useDispatch} from "react-redux";
import store, {AppDispatch} from "@/stores";
import Access from "@/components/BasicLayout/components/Access";
import {userGetLoginState} from "@/api/userController";
import {setLoginUser} from "@/stores/loginUser";
import {WaterMark} from "@ant-design/pro-layout";
import {ThemeProvider} from "@/components/ThemeProvider";

const Init: React.FC<
    Readonly<{
        children: React.ReactNode;
    }>
> = ({children}) => {
    const dispath = useDispatch<AppDispatch>();

    // 逻辑: 设置测试初始
    const doInitTest = useCallback(() => {
        console.log("Do init.");
    }, []);

    // 逻辑: 读取已经登录用户信息
    const doInitLoginUser = useCallback(async () => {
        const res = await userGetLoginState(); // 尝试获取到当前登陆用户的信息
        if (res.data) {
            dispath(setLoginUser(res.data as API.LoginUserVO)); // 获取得到则说明用户短期内已经登陆了, 更新用户信息全局状态即可
        } else {
            // TODO: 处理出错操作, 直接跳转到登录页面要求用户登陆即可
        }
        // eslint-disable-next-line
    }, []); // 这里使用可以缓存回调函数的 useCallback(), 只有在依赖项发生改变时才重新创建函数, 而不是重新挂载本组件时创建函数, 可以优化性能
    // TODO: 考虑去掉对 [] 的警告

    // 调用一次
    useEffect(() => {
        doInitTest();
        doInitLoginUser().then(r => {
        }); // TODO: 考虑处理错误
        // 如果有的页面不需要初始化则可以考虑使用 userPathname() 来判断当前路由再根据条件判断进行屏蔽
        // 这里等接口文档服务导入接口后, 就可以书写在用户没有登陆时要求登陆的初始化逻辑
        // eslint-disable-next-line
    }, []);
    // TODO: 考虑去掉对 [] 的警告

    return children;
};

export default function RootLayout({
                                       children,
                                   }: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="zh">
        <body>
        {/* 引入组件库 */}
        <AntdRegistry>
            {/* 状态管理器 */}
            <Provider store={store}>
                {/* 逻辑初始化 */}
                <Init>
                    {/* 主题提供者 */}
                    <ThemeProvider>
                        {/* 渲染初始化 */}
                        <BasicLayout>
                            {/* 页面权限化 */}
                            <Access>
                                {/* 加页面水印 */}
                                <WaterMark content="工作室用户中心" fontSize={18}> {/* TODO: 可以加上用户的 account */}
                                    {children}
                                </WaterMark>
                            </Access>
                        </BasicLayout>
                    </ThemeProvider>
                </Init>
            </Provider>
        </AntdRegistry>
        </body>
        </html>
    );
}
