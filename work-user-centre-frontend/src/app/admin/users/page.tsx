"use client";

import "./page.css";
import AdvancedTable from "@/components/AdvancedTable";
import { userSearch } from "@/api/userController";
import { message } from "antd";
import { useEffect, useState } from "react";

export default function AdminUsersPage() {
    const [data, setData] = useState<API.LoginUserVO[]>([]);

    useEffect(() => {
        (async () => {
            try {
                const res = await userSearch({});

                console.log("查看访问到的数据"); // TODO: 不确定是否需要屏蔽
                console.log(res.data); // TODO: 不确定是否需要屏蔽

                if (Array.isArray(res?.data)) {
                    setData(res.data ?? []);
                    message.success("访问数据库成功", 2);
                } else {
                    message.error("未知错误", 2);
                }
            } catch (e) {
                message.error("未知错误", 2);
            }
        })();
    }, []);

    return (
        <div id="adminUsersPage">
            <AdvancedTable data={data} />
        </div>
    );
}
