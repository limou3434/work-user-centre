/**
 *  ./src/app/page.tsx: 应用页面
 */
"use client";

import React from 'react';
import UserProfileCard from "@/components/UserProfileCard";
import {useSelector} from "react-redux";
import {RootState} from "@/stores";

export default function Home() {
    // 状态工具
    const loginUser = useSelector((state: RootState) => state.loginUser); // 获取用户登陆状态实例

    const mockUser: API.LoginUserVO = {
        ...loginUser
    };

    return (
        <main
            id="mainPage"
            className="flex min-h-screen flex-col items-center justify-between p-24"
        >
            <div style={{display: "flex", justifyContent: "center", marginTop: 50}}>
                <UserProfileCard user={mockUser}/>
            </div>
        </main>
    );
}
