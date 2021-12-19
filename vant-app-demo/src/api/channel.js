//频道请求的模块
import request from "../utils/request";
//获取所有的频道
export const getAllChannels = () => {
  return request({
    method: "GET",
    url: "/channels",
  });
};
//添加用户频道
export const addUserChannel = (channel) => {
  return request({
    method: "POST",
    url: "/user/addChannels",
    data: channel,
  });
};
//删除用户指定频道
export const deleteUserChannel = (channelId, userId) => {
  return request({
    method: "DELETE",
    url: "/user/channels",
    data: {
      channelId,
      userId,
    },
  });
};
