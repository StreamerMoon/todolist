<template>
  <div class="my-container">
    <!-- 未登录布局 -->
    <div v-if="!userState" class="header not-login">
      <div class="login-btn" @click="this.$router.push('/login')">
<!--        <img class="mobile-img" src="../../assets/ mobile.png" alt="" />-->
        <img class="mobile-img" src="https://img.yzcdn.cn/vant/cat.jpeg" alt="" />
        <span class="text">注册 / 登录</span>
      </div>
    </div>
    <!-- 登录后的布局 -->
    <div v-else class="header user-info">
      <div class="base-info">
        <div class="left">
          <van-image
            :src="`${url}` + userInfo.photo"
            class="avatar"
            round
            fit="cover"
          ></van-image>
          <span class="name">{{ userInfo.userName }}</span>
        </div>
        <div class="right">
          <van-button size="mini" round>编辑资料</van-button>
        </div>
      </div>
<!--      <div class="data-stats">-->
<!--        <div class="data-item">-->
<!--          <span class="count">{{ userInfo.art_count }}</span>-->
<!--          <span class="text">头条</span>-->
<!--        </div>-->
<!--        <div class="data-item">-->
<!--          <span class="count">{{ userInfo.follow_count }}</span>-->
<!--          <span class="text">关注</span>-->
<!--        </div>-->
<!--        <div class="data-item">-->
<!--          <span class="count">{{ userInfo.fans_count }}</span>-->
<!--          <span class="text">粉丝</span>-->
<!--        </div>-->
<!--        <div class="data-item">-->
<!--          <span class="count">{{ userInfo.like_count }}</span>-->
<!--          <span class="text">获赞</span>-->
<!--        </div>-->
<!--      </div>-->
    </div>
    <!-- 导航 -->
<!--    <van-grid :column-num="2" clickable>-->
<!--      <van-grid-item icon="star-o" text="收藏" />-->
<!--      <van-grid-item icon="clock-o" text="历史" />-->
<!--    </van-grid>-->
    <!-- 消息通知与退出登录布局 -->
    <van-cell title="消息通知" is-link />
    <van-cell title="小智同学" is-link />
    <van-cell
      title="退出登录"
      class="logout-cell"
      v-if="userState"
      @click="onLogout"
    />
  </div>
</template>

<script>
import { useStore } from "vuex";
import { computed, onMounted, reactive, toRefs } from "vue";
import { Dialog } from "vant";
import { getUserInfo } from "../../api/user";
function useLogout(store) {
  const onLogout = () => {
    Dialog.confirm({
      title: "确认退出吗",
    })
      .then(() => {
        // on confirm
        // console.log("确认执行");
        store.commit("setUser", null);
      })
      .catch(() => {
        // on cancel
        console.log("取消执行");
      });
  };
  return {
    onLogout,
  };
}
function useLoadUserInfo() {
  const state = reactive({
    userInfo: {
      userName: "",
      art_count: 0,
      follow_count: 0,
      fans_count: 0,
      like_count: 0,
      photo: "",
    },
  });
  const store = useStore();
  const userState = computed(() => store.state.user);
  const url = import.meta.env.VITE_APP_URL;
  const loadUserInfo = () => {
    if (userState.value !== null) {
      getUserInfo(userState.value.data.id).then((res) => {
        state.userInfo = res.data;
      });
    }
  };
  onMounted(() => {
    loadUserInfo();
  });
  return {
    store,
    userState,
    state,
    url,
  };
}
export default {
  setup() {
    const { store, userState, state, url } = useLoadUserInfo();
    return {
      ...toRefs(state),
      userState,
      ...useLogout(store),
      url,
    };
  },
};
</script>

<style>
.my-container .header {
  height: 261px;
  background: url("../../assets/banner.png");
  background-size: cover;
}
.my-container .not-login {
  /* 没有登录的效果样式*/
  display: flex;
  justify-content: center;
  align-items: center;
}
.my-container .not-login .login-btn {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.mobile-img {
  width: 132px;
  height: 132px;
  margin-bottom: 15px;
}
.text {
  font-size: 18px;
  color: #fff;
}
.user-info .base-info {
  height: 131px;
  padding: 70px 32px 23px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.user-info .left {
  display: flex;
  align-items: center;
}
.user-info .left .avatar {
  width: 132px;
  height: 132px;
  margin-right: 13px;
  border: 1px solid #fff;
}
.user-info .left .name {
  font-size: 16px;
  color: #fff;
}
.user-info .data-stats {
  /* background-color: #ccc; */
  display: flex;
  /* height: 130px; */
}
.user-info .data-stats .data-item {
  height: 130px;
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: #fff;
}
.user-info .data-stats .count {
  font-size: 18px;
}
.user-info .data-stats .text {
  font-size: 18px;
}
.van-grid-item__icon {
  color: #eb5253;
}
.logout-cell {
  text-align: center;
  color: #d86262;
  height: 120px;
  margin-top: 9px;
}
</style>
