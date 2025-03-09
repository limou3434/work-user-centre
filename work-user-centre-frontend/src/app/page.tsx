// ./src/app/page.tsx: 主页页面

"use client";

import {useState} from "react";

export default function Home() {
    const [text, setText] = useState<string>("");

    return (
        <main
            id="mainPage"
            className="flex min-h-screen flex-col items-center justify-between p-24"
        >
            <h1>mainPage</h1>
        </main>
    );
}
