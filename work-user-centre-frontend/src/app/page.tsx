/**
 *  ./src/app/page.tsx: 应用页面
 */
"use client";

import React from 'react';
import UserProfileCard from "@/components/UserProfileCard";
import {useSelector} from "react-redux";
import {RootState} from "@/stores";
import LetterGlitch from "../components/LetterGlitch";

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
            <div style={{width: "100%", height: "200px"}}>
                <LetterGlitch
                    glitchSpeed={50}
                    centerVignette={false}
                    outerVignette={true}
                    smooth={true}
                />
            </div>
            <div style={{display: "flex", justifyContent: "center", marginTop: 50}}>
                <UserProfileCard user={mockUser}/>
            </div>
        </main>
    );
}
