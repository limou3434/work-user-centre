// ./src/stores/loginUser.ts: 用户登陆状态(相当于一个数据表)

import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { DEFAULT_USER } from "@/constants";

// 可以理解为 configureStore() 创建了一个数据库，createSlice() 创建了一个数据表
export const loginUserSlice = createSlice({
  name: "loginUser", // 表名
  initialState: DEFAULT_USER, // 初始
  reducers: {
    // 操作(会自动把返回值修改进 initialState)
    setLoginUser: (state, action: PayloadAction<API.UserLoginRequest>) => {
      // state 是还没修改之前的状态, 这里暂时没用到, 可以考虑使用 _ 替代, 也可以考虑使用 { ...state, ...action.payload } 达到部分或全部的字段覆盖
      // 用户登陆后就需要使用 setLoginUser() 修改登陆状态
      return {
        ...action.payload,
      }; // 使用 {} 代表返回链一个副本, 避免污染状态
    },
  },
}); // 导出数据表

export const { setLoginUser } = loginUserSlice.actions; // 导出操作

export default loginUserSlice.reducer; // 导出注册
