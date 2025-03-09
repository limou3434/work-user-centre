// ./src/stores/index.ts: 状态管理库(相当于一个数据库)

import { configureStore } from "@reduxjs/toolkit";
import loginUser from "@/stores/loginUser";

// 可以理解为而 configureStore() 创建了一个数据库，createSlice() 创建了一个数据表
const store = configureStore({
  // 在这里存放状态注册
  reducer: {
    loginUser,
  },
});

export type RootState = ReturnType<typeof store.getState>; // 用于类型推断和提示

export type AppDispatch = typeof store.dispatch; // 用于类型推断和提示

export default store; // 导出数据库

/*
引入数据表并且配置好数据库后, 就可以获取状态实例, 如果需要获取状态内的字段就像使用类对象一样使用
import { useSelector } from "react-redux";
import { RootState } from "@/stores";
const 数据表名 = useSelector((state: RootState) => {state.<数据表名>})

如果需要对状态做操作就需要获得触发器, 然后使用之前定义的操作进行触发
const dispatch = useDispatch<AppDispatch>();
dispatch(操作名(值对象));
 */
