/**
 * AdvancedTable.tsx: 可拖拽排序的高级表格组件
 */

"use client";

import "./index.css";
import type {ProColumns} from '@ant-design/pro-components';
import {DragSortTable} from '@ant-design/pro-components';
import {message} from 'antd';
import {useEffect, useState} from 'react';
import {useTranslation} from 'react-i18next';

const defaultData: API.LoginUserVO[] = [
    {
        id: 12,
        userAccount: "limou",
        userWxUnion: "wx_union_limou",
        userMpOpen: "mp_open_limou",
        userEmail: "limou@example.com",
        userPhone: "13800138012",
        userAvatar:
            "https://avatars.githubusercontent.com/u/113878415?s=400&u=9f10b63e033c9504615bc475581441478424e04b&v=4",
        userTags:
            '["项目架构师", "后端程序员", "数学爱好者", "运维发烧者"]',
        userNick: "limou",
        userName: "li",
        userProfile: "这是李萌的个人简介",
        userBirthday: "2004-02-23",
        userCountry: "中国",
        userAddress: "广州市白云区",
        userRole: 1,
        userLevel: 1,
        userGender: 1,
    },
    {
        id: 4,
        userAccount: "dimou",
        userWxUnion: "",
        userMpOpen: "",
        userEmail: "",
        userPhone: "",
        userAvatar: "",
        userTags: "",
        userNick: "dimou",
        userName: "",
        userProfile: "",
        userBirthday: "",
        userCountry: "",
        userAddress: "",
        userRole: 0,
        userLevel: 0,
        userGender: 0,
    },
];

export default function AdvancedTable({
                                          data = defaultData,
                                      }: {
    data?: API.LoginUserVO[];
}) {

    // 定义表格的列结构
    const fixedWidth = 100;

    const {t} = useTranslation(); // 国际化

    const columns: ProColumns<API.LoginUserVO>[] = [
        {
            title: t("sort"),
            dataIndex: "sort",
            width: fixedWidth - 50,
            className: "drag-visible",
            fixed: "left",
        },
        {
            title: t("id"),
            width: fixedWidth,
            dataIndex: "id",
            fixed: "left",
        },
        {
            title: t("user_account"),
            width: fixedWidth,
            dataIndex: "userAccount",
            fixed: "left",
        },
        {
            title: t("user_wx_union"),
            dataIndex: "userWxUnion",
        },
        {
            title: t("user_mp_open"),
            dataIndex: "userMpOpen",
        },
        {
            title: t("user_email"),
            dataIndex: "userEmail",
        },
        {
            title: t("user_phone"),
            dataIndex: "userPhone",
        },
        {
            title: t("user_avatar"),
            dataIndex: "userAvatar",
        },
        {
            title: t("user_tags"),
            dataIndex: "userTags",
        },
        {
            title: t("user_nick"),
            dataIndex: "userNick",
        },
        {
            title: t("user_name"),
            dataIndex: "userName",
        },
        {
            title: t("user_profile"),
            dataIndex: "userProfile",
        },
        {
            title: t("user_birthday"),
            dataIndex: "userBirthday",
        },
        {
            title: t("user_country"),
            dataIndex: "userCountry",
        },
        {
            title: t("user_address"),
            dataIndex: "userAddress",
        },
        {
            title: t("user_role"),
            dataIndex: "userRole",
        },
        {
            title: t("user_level"),
            dataIndex: "userLevel",
        },
        {
            title: t("user_gender"),
            dataIndex: "userGender",
        },
    ];

    const [dataSource, setDataSource] = useState(data);

    // 监听 data 变化并更新 dataSource
    useEffect(() => {
        setDataSource(data);
    }, [data]);

    const handleDragSortEnd = (
        beforeIndex: number,
        afterIndex: number,
        newDataSource: API.LoginUserVO[]
    ) => {
        console.log("排序后的数据", newDataSource); // TODO: 不确定是否去掉
        setDataSource(newDataSource);
        message.success("修改列表排序成功").then(() => {
        });
    };

    return (
        <div className="advanced-table">
            <DragSortTable
                headerTitle="用户列表"
                columns={columns}
                rowKey="id"
                search={false}
                pagination={false}
                dataSource={dataSource}
                dragSortKey="sort"
                onDragSortEnd={handleDragSortEnd}
                scroll={{x: "max-content"}}
            />
        </div>
    );
}