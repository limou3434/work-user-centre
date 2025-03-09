// ./src/app/layout.tsx: 应用全局初始, 无论访问什么页面都会执行这段代码(逻辑初始化和渲染初始化)

"use client";

import "./globals.css";
import BasicLayout from "@/layouts/BasicLayout";
import { AntdRegistry } from "@ant-design/nextjs-registry";
import React, { useCallback, useEffect } from "react";
import { Provider } from "react-redux";
import store from "@/stores";
import Access from "@/layouts/BasicLayout/components/Access";

const Init: React.FC<
  Readonly<{
    children: React.ReactNode;
  }>
> = ({ children }) => {
  // 设置测试初始逻辑
  const doInit = useCallback(() => {
    console.log("Do init.");
  }, []);

  // 调用一次
  useEffect(() => {
    doInit();
    // 如果有的页面不需要初始化则可以考虑使用 userPathname() 来判断当前路由再根据条件判断进行屏蔽
    // 这里等接口文档服务导入接口后, 就可以书写在用户没有登陆时要求登陆的初始化逻辑
    // eslint-disable-next-line
  }, []);

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
              {/* 渲染初始化 */}
              <BasicLayout>
                {/* 页面权限化 */}
                <Access>{children}</Access>
              </BasicLayout>
            </Init>
          </Provider>
        </AntdRegistry>
      </body>
    </html>
  );
}
