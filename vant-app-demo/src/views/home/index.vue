<template>
  <div class="home-container">
    <van-nav-bar class="page-nav-bar" fixed>
      <template #right>
        <van-button
          type="info"
          size="samll"
          round
          icon="search"
          class="search-button"
          >搜索</van-button
        >
      </template>
    </van-nav-bar>
    <van-tabs class="channels-tabs" v-model:active="active">
      <van-tab
        v-for="channel in channels"
        :title="channel.name"
        :key="channel.id"
      >
        <article-list :channel="channel"></article-list>
      </van-tab>

      <template #nav-right>
        <i class="placeholder"></i>
        <van-icon
          name="wap-nav"
          class="hamburger-btn"
          @click="isChannelEditShow = true"
        ></van-icon>
      </template>
    </van-tabs>
    <van-popup
      v-model:show="isChannelEditShow"
      closeable
      position="bottom"
      close-icon-position="top-left"
      :style="{ height: '100%' }"
    >
      <channel-edit
        :my-channels="channels"
        :active="active"
        @update-active="onUpdateActive"
      ></channel-edit>
    </van-popup>
  </div>
</template>

<script>
import { ref, reactive, onMounted, toRefs } from "vue";
import { getUserChannels, getCurrentChannels } from "../../api/user";
import ArticleList from "./components/article-list.vue";
import ChannelEdit from "./components/channel-edit.vue";
import { useStore } from "vuex";
import { getItem } from "../../utils/storage";
function useChannes() {
  const state = reactive({
    channels: [],
    isChannelEditShow: false,
  });
  const store = useStore();
  const loadChannels = async () => {
    // getUserChannels().then((res) => {
    //   state.channels = res.data;
    // });
    let channels = [];
    if (store.state.user) {
      //用户已经登录
      const { data } = await getCurrentChannels(store.state.user.data.id);
      channels = data;
    } else {
      //用户没有登录
      const localChannels = await getItem("TOUTIAO_CHANNELS");
      if (localChannels) {
        channels = localChannels;
      } else {
        //如果本地没有，则获取默认的频道列表数据。
        const { data } = await getUserChannels();
        channels = data;
      }
    }
    state.channels = channels;
  };
  onMounted(() => {
    loadChannels();
  });
  return {
    state,
  };
}
function useUpdateActive(active, state) {
  const onUpdateActive = (index, isChannelEditShow = true) => {
    active.value = index;
    state.isChannelEditShow = isChannelEditShow;
  };
  return {
    onUpdateActive,
  };
}
export default {
  components: {
    ArticleList,
    ChannelEdit,
  },
  setup() {
    const active = ref(0);
    const { state } = useChannes();
    return {
      active,
      ...toRefs(state),
      ...useUpdateActive(active, state),
    };
  },
};
</script>

<style>
.home-container .search-button {
  width: 300px;
  height: 30px;
  background-color: #5babfb;
  border: none;
  font-size: 18px;
  color: #fff;
}
.channels-tabs .van-tab {
  min-width: 120px;
  border-right: 1px solid #edeff3;
  font-size: 16px;
  color: #777;
}
.channels-tabs .van-tab--active {
  color: red;
}
.channels-tabs .van-tabs__line {
  background-color: #3296fa;
  width: 20px;
  height: 6px;
}
.channels-tabs .hamburger-btn {
  /* 固定定位 */
  position: fixed;
  right: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 66px;
  height: 32px;
  background-color: #fff;
  /* 设置透明度 */
  opacity: 0.902;
  /* 设置图标的大小 */
  font-size: 23px;
}
.channels-tabs .placeholder {
  flex-shrink: 0;
  width: 66px;
  height: 32px;
}
.channels-tabs .van-tabs__wrap {
  position: fixed;
  top: 46px;
  left: 0;
  right: 0;
  z-index: 1;
}
.home-container {
  padding-bottom: 100px;
}
</style>
