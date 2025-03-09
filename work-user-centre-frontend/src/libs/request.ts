// ./libs/request.ts: 封装 Axios 请求后得到的一个请求库(可能需要立刻进行部分改动)
import axios from "axios";

// 注意后端接口在用户接口上需要符合下面的代码(不过工作室有自己的登陆接口因此不必担心)

// 创建 Axios 实例
const myAxios = axios.create({
  baseURL: "http://localhost:8000/work_user_centre_api", // 请求后端(IP+PORT)
  timeout: 10000, // 响应时间(10s)
  withCredentials: true, // 凭证携带(开启)
});

// 创建请求拦截器
myAxios.interceptors.request.use(
  // 请求执行前执行
  function (config) {
    // console.log(config);
    return config;
  },
  // 处理请求错误
  function (error) {
    // console.log(error);
    return Promise.reject(error);
  },
);

// 创建响应拦截器
// TODO: 等后端封装返回体
// myAxios.interceptors.response.use(
//   // 响应成功(2xx 响应触发), 整体为一个回调函数
//   function (response) {
//     // 解析数据
//     const { data } = response;
//
//     // 尚未登录
//     if (data.code === 40100) {
//       // 不是获取用户信息接口, 或者不是登录页面, 则重定向跳转到登录页面)
//       if (
//         !response.request.responseURL.includes("user/get/login") && // 判断之前的请求中不包含获取登录接口
//         !window.location.pathname.includes("/user/login") // 判断当前页面不是处于登录页面
//       ) {
//         window.location.href = `/user/login?redirect=${window.location.href}`; // 这一句代码的作用是将用户重定向到登录页面, 并在登录页面的 URL 中添加一个 redirect 参数, 方便用户登陆请求成功后切回原来的界面, 做到无缝体验， redirect 保存了当前页面的 URL
//       }
//     }
//
//     // 其他错误
//     else if (data.code !== 0) {
//       throw new Error(data.message ?? "服务器错误");
//     }
//
//     // 允许返回
//     return data;
//   },
//
//   // 响应失败(非 2xx 响应触发), 整体为一个回调函数
//   function (error) {
//     // 处理响应错误
//     return Promise.reject(error); // 返回一个被拒绝的 Promise, 配合后续 async/await 日志打印
//   },
// );

export default myAxios;
