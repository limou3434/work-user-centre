// ./src/layouts/BasicLayout/index.tsx: 基本布局组件

"use client";

import "./index.css";
import { LogoutOutlined } from "@ant-design/icons";
import { ProLayout } from "@ant-design/pro-components";
import { Dropdown } from "antd";
import React from "react";
import Image from "next/image";
import { usePathname } from "next/navigation";
import Link from "next/link";
import menus from "../../../menus";
import Footer from "@/layouts/BasicLayout/components/Footer";
import Header from "@/layouts/BasicLayout/components/Header";
import Actions from "@/layouts/BasicLayout/components/Actions";
import { useSelector } from "react-redux";
import { RootState } from "@/stores";
import accessControlDisplay from "@/access/accessControlDisplay";

interface Props {
  children: React.ReactNode;
}

export default function BasicLayout({ children }: Props) {
  const pathname = usePathname(); // 获取当前页面地址

  const loginUser = useSelector((state: RootState) => state.loginUser); // 获取用户登陆状态实例

  return (
    <div
      id="basicLayout"
      style={{
        height: "100vh",
        overflow: "auto",
      }}
    >
      {/* 核心组件 */}
      <ProLayout
        // 网站标题
        title="工作室用户中心"
        // 网站图标
        logo={
          <Image
            src="/assets/logo.svg"
            height={32}
            width={32}
            alt="logo"
            style={{ width: "auto", height: "auto" }} // 解决 Next.js 警告
          />
        }
        // 布局类型, 可选值 top 或 side
        layout="side"
        // 是否高亮
        location={{
          pathname,
        }}
        // 用户数据
        avatarProps={{
          src: loginUser.userAvatar || "/assets/not_login_user.svg",
          size: "small",
          title: loginUser.userName || "未登陆",
          // @ts-ignore
          render: (_, dom) => {
            return (
              <Dropdown
                menu={{
                  items: [
                    {
                      key: "logout",
                      icon: <LogoutOutlined />,
                      label: "退出登录",
                    },
                  ],
                }}
              >
                {dom}
              </Dropdown>
            );
          },
        }}
        // 监听标题
        // @ts-ignore
        onMenuHeaderClick={(e) => console.log(e)}
        // 菜单数据
        menuDataRender={() => {
          // return menus;
          return accessControlDisplay(loginUser, menus); // 这个函数会自动根据页面权限的设置来过滤菜单项, 以控制对应用户看到对应的菜单项
        }}
        // 菜单跳转
        // @ts-ignore
        menuItemRender={(item, dom) => (
          // 根据菜单进行页面跳转但需要约定路由的实际支持(也就是要编写对应的目录, 并且在这里可以拓展菜单的属性)
          <Link href={item.path || "/"} target={item.target}>
            {dom}
          </Link>
        )}
        // 渲染文档
        // @ts-ignore
        actionsRender={(props) => {
          return <Actions isMobile={props.isMobile} layout={props.layout} />;
        }}
        // 渲染头部
        // @ts-ignore
        headerTitleRender={(logo, title, _) => {
          return <Header logo={logo} title={title} />;
        }}
        // 渲染底部
        // @ts-ignore
        menuFooterRender={(props) => {
          if (props?.collapsed) return undefined;
          return (
              <div
                  style={{
                    textAlign: 'center',
                    paddingBlockStart: 12,
                  }}
              >
                  <Footer />
              </div>
          );
        }}
        // footerRender={() => {
        //   // return ;
        // }}
      >
        {children}
      </ProLayout>
    </div>
  );
}
