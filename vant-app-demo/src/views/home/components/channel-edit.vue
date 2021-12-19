<template>
  <div class="channel-edit">
    <van-cell>
      <template #title>
        <span class="title-text">我的频道</span>
      </template>
      <template #value>
        <van-button
          type="danger"
          round
          size="mini"
          plain
          class="edit-btn"
          @click="isEdit = !isEdit"
          >{{ isEdit ? "完成" : "编辑" }}</van-button
        ></template
      >
    </van-cell>
    <van-grid :gutter="10" class="my-grid">
      <van-grid-item
        v-for="(channel, index) in myChannels"
        :key="channel.id"
        class="grid-item"
        @click="onMyChannelClick(channel, index)"
      >
        <template #default>
          <van-icon
            name="clear"
            v-show="isEdit && !fixChannels.includes(channel.id)"
          ></van-icon>
          <span class="text" :class="{ active: index === active }">{{
            channel.name
          }}</span>
        </template>
      </van-grid-item>
    </van-grid>
    <van-cell>
      <template #title>
        <span class="title-text">频道推荐</span>
      </template>
    </van-cell>
    <van-grid :gutter="10" class="recommend-grid">
      <van-grid-item
        v-for="channel in recommendChannels"
        :key="channel.id"
        icon="plus"
        :text="channel.name"
        class="grid-item"
        @click="onAddChannel(channel)"
      />
    </van-grid>
  </div>
</template>

<script>
import { onMounted, reactive, computed, toRefs } from "vue";
import {
  getAllChannels,
  addUserChannel,
  deleteUserChannel,
} from "../../../api/channel";
import { useStore } from "vuex";
import { setItem } from "../../../utils/storage";
function useChannels(props) {
  const state = reactive({
    allChannels: [],
  });
  const loadAllChannels = async () => {
    const { data } = await getAllChannels();
    state.allChannels = data;
  };
  const recommendChannels = computed(() => {
    const channels = []; //存储推荐频道中的数据
    state.allChannels.forEach((channel) => {
      const result = props.myChannels.find((myChannel) => {
        return myChannel.id === channel.id;
      });
      if (!result) {
        channels.push(channel);
      }
    });
    return channels;
  });
  onMounted(() => {
    loadAllChannels();
  });
  return {
    // loadAllChannels,
    recommendChannels,
  };
}
// 添加频道
function useAddChannel(props, store) {
  const onAddChannel = async (channel) => {
    props.myChannels.push(channel);
    //数据持久化的处理
    if (store.state.user) {
      // 已经登录，把数据存储到服务端
      const channelContent = {
        userId: store.state.user.data.id,
        channel,
      };
      await addUserChannel(channelContent);
    } else {
      // 未登录，把数据存储到本地
      setItem("TOUTIAO_CHANNELS", props.myChannels);
    }
  };
  return {
    onAddChannel,
  };
}
function useMyChannelClick(state, emit, { myChannels, active }, store) {
  const onMyChannelClick = async (channel, index) => {
    if (state.isEdit) {
      //如果是推荐频道，则禁止删除
      if (state.fixChannels.includes(channel.id)) {
        return;
      }
      // 编辑状态，执行删除频道操作
      if (index <= active) {
        emit("update-active", active - 1, true);
      }
      myChannels.splice(index, 1);
      //判断用户是否已经登录
      if (store.state.user) {
        //用户已经登录
        await deleteUserChannel(channel.id, store.state.user.data.id);
      } else {
        //未登录
        setItem("TOUTIAO_CHANNELS", myChannels);
      }
    } else {
      // 非编辑状态，执行频道切换。
      emit("update-active", index, false);
    }
  };
  return {
    onMyChannelClick,
  };
}
export default {
  props: {
    myChannels: {
      type: Array,
      required: true,
    },
    active: {
      type: Number,
      required: true,
    },
  },
  emits: ["update-active"],
  setup(props, { emit }) {
    const state = reactive({
      isEdit: false,
      fixChannels: [1], //不允许删除的频道编号存储到该数组中
    });
    const store = useStore();
    return {
      ...useChannels(props),
      ...useAddChannel(props, store),
      ...toRefs(state),
      ...useMyChannelClick(state, emit, props, store),
    };
  },
};
</script>

<style>
.channel-edit {
  padding: 85px 0;
}
.channel-edit .title-text {
  font-size: 20px;
  color: #333;
}
.channel-edit .edit-btn {
  width: 85px;
  height: 38px;
  font-size: 16px;
}
.channel-edit .grid-item {
  width: 86px;
  height: 66px;
  background-color: #f4f5f6;
}
.channel-edit .van-grid-item__text {
  font-size: 14px;
  color: #222;
  margin-top: 0;
}
.channel-edit .recommend-grid .van-grid-item__content {
  flex-direction: row;
  white-space: nowrap;
}
.channel-edit .recommend-grid .van-icon {
  font-size: 16px;
  margin-right: 5px;
}
.channel-edit .my-grid .van-icon-clear {
  position: absolute;
  right: -5px;
  top: -5px;
  font-size: 17px;
  color: #cacaca;
  z-index: 2;
}
.channel-edit .text {
  font-size: 14px;
  color: #222;
  margin-top: 0;
}
.channel-edit .grid-item .active {
  color: red;
}
</style>
