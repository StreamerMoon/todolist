import axios from "axios";
import store from "../store";
const request = axios.create({
  baseURL: import.meta.env.VITE_APP_URL,
});
//请求拦截器
request.interceptors.request.use(
  function (config) {
    //请求发送会经过这里
    //config:本次请求的请求配置对象
    const user = store.state.user;
    if (user && user.myToken) {
      config.headers.Authorization = `Bearer ${user.myToken}`;
    }
    return config;
  },
  function (err) {
    //如果请求出错了，会被执行
    return Promise.reject(err);
  }
);
//响应拦截器
export default request;
