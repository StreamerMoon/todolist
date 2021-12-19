//封装用户请求的模块
import request from "../utils/request";
import store from "../store";
export const login = (data) => {
  return request({
    method: "POST",
    url: "/user/login",
    data,
  });
};
//获取用户自己完整的信息
export const getUserInfo = (id) => {
  return request({
    method: "GET",
    url: "/userInfo/get",
    params: { userId: id },
    // headers: {
    //   Authorization: `Bearer ${store.state.user.myToken}`,
    // },
  });
};
//获取用户频道列表数据
export const getUserChannels = () => {
  return request({
    method: "GET",
    url: "/user/channels",
  });
};
export const getCurrentChannels = (id) => {
  return request({
    method: "GET",
    url: "/user/currentChannels",
    params: { userId: id },
  });
};
