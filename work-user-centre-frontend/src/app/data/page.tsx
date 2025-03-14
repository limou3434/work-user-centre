"use client";

import "./page.css";
import ProfileCompletion from "@/components/ProfileCompletion";
import {useSelector} from "react-redux";
import {RootState} from "@/stores";

export default function Admin() {
  // 状态工具
  // const loginUser = useSelector((state: RootState) => state.loginUser); // 获取用户登陆状态实例
  //
  // const mockUser: API.LoginUserVO = {
  //   ...loginUser
  // };

  // 模拟一个没有填写所有数据的用户
  const user: API.LoginUserVO = {
    userAccount: "limou3434",
    userGender: 0,
  };

    return (
    <div id="dataPage">
        <ProfileCompletion user={user}/>
    </div>
  );
}
