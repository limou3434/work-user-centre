// ./menus.tsx: 配置菜单

import {MenuDataItem} from "@ant-design/pro-layout";
import {BarChartOutlined, BulbOutlined, CrownOutlined, HomeOutlined} from "@ant-design/icons";
import {ACCESS_ENUM} from "@/constants";

// 配置菜单的时候也必须保证具有对应约定路由

const menus = [
    {
        path: "/",
        name: "主页",
        icon: <HomeOutlined/>,
        access: ACCESS_ENUM.NOT_LOGIN,
    },
    {
        path: "/admin",
        name: "管理",
        icon: <CrownOutlined/>,
        // TODO: access: ACCESS_ENUM.ADMIN,
        children: [
            {
                path: "/admin/role",
                name: "按角色划分",
            },
            {
                path: "/admin/level",
                name: "按等级划分",
            },
        ],
    },
    {
        path: "/data",
        name: "数据",
        icon: <BarChartOutlined />,
        access: ACCESS_ENUM.NOT_LOGIN,
    },
    {
        path: "/other",
        name: "其他",
        icon: <BulbOutlined/>,
        access: ACCESS_ENUM.NOT_LOGIN,
    },
] as MenuDataItem[]; // 这样写会提供编写本菜单配置的智能提示

export default menus;
