/**
 *  ./src/components/ThemeProvider/index.tsx: 主题提供者
 */
"use client";

import "./index.css";
import React, { createContext, useContext, useState } from "react";
import { ConfigProvider, theme, Button } from "antd";
import { BulbOutlined, MoonOutlined } from "@ant-design/icons";

// 创建主题上下文
const ThemeContext = createContext({
    isDark: false,
    toggleTheme: () => {},
});

// 主题提供者组件
export const ThemeProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [isDark, setIsDark] = useState(false);

    const toggleTheme = () => {
        setIsDark((prev) => !prev);
    };

    return (
        <ThemeContext.Provider value={{ isDark, toggleTheme }}>
            <ConfigProvider theme={{ algorithm: isDark ? theme.darkAlgorithm : theme.defaultAlgorithm }}>
                {/* 右下角主题切换按钮 */}
                <Button
                    type="primary"
                    shape="circle"
                    icon={isDark ? <BulbOutlined /> : <MoonOutlined />}
                    onClick={toggleTheme}
                    style={{
                        position: "fixed",
                        bottom: 20,
                        right: 20,
                        zIndex: 1000,
                    }}
                />
                {children}
            </ConfigProvider>
        </ThemeContext.Provider>
    );
};

// 自定义 Hook 方便使用
export const useTheme = () => useContext(ThemeContext);
