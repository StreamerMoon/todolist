<template>
  <div class="article-list">
    <van-pull-refresh
      v-model="state.isreFreshLoading"
      @refresh="onRefresh"
      :success-text="state.refreshSuccessText"
      success-duration="1500"
    >
      <van-list
        v-model:loading="state.loading"
        :finished="state.finished"
        finished-text="没有更多了"
        v-model:error="state.error"
        error-text="请求失败，点击重新加载"
        @load="onLoad"
      >
        <!-- <van-cell
          v-for="item in state.list"
          :key="item.id"
          :title="item.title"
          class="cell-list"
        /> -->
        <article-item
          v-for="article in state.list"
          :key="article.id"
          :article="article"
          class="cell-list"
        ></article-item>
      </van-list>
    </van-pull-refresh>
  </div>
</template>

<script>
import { reactive } from "vue";
import { getArticles } from "../../../api/article";
import ArticleItem from "../../../components/article-item/index.vue";
function useLoad(id) {
  const state = reactive({
    list: [],
    loading: false,
    finished: false,
    pageNumber: null,
    error: false,
    isreFreshLoading: false, //控制下拉刷新的loading状态
    refreshSuccessText: "刷新成功",
  });
  //下拉刷新
  const onRefresh = async () => {
    try {
      const params = {
        channel_id: id,
        pageNumber: 1,
        referrer: "load",
      };
      const { data } = await getArticles(params);
      //模拟随机请求失败的情况
      // if (Math.random() > 0.5) {
      //   JSON.parse("aaaa");
      // }

      const results = data.list;
      //将数据追加到列表的顶部
      state.list.unshift(...results);
      //关闭下拉刷新的loading状态
      state.isreFreshLoading = false;
      state.refreshSuccessText = `刷新成功，更新了${results.length}条数据`;
    } catch (err) {
      //   console.log("请求失败" + err);
      state.isreFreshLoading = false;
      state.refreshSuccessText = "刷新失败";
    }
  };
  const onLoad = async () => {
    //   console.log("load");
    // 异步更新数据
    try {
      //1、发送异步请求获取数据
      const params = {
        channel_id: id,
        pageNumber: state.pageNumber || 1,
      };
      const { data } = await getArticles(params);
      //模拟随机请求失败的情况
      // if (Math.random() > 0.5) {
      //   JSON.parse("aaaa");
      // }

      const results = data.list;
      // 2、将服务端返回的数据填充到list数组
      state.list.push(...results);
      // 3、本次数据加载完成后，要把加载状态设置为结束，loading设置为false以后，才能够触发下一次的加载更多的操作
      state.loading = false;
      // 4、当数据全部加载完成后，发finished设置为true
      if (results.length > 0) {
        state.pageNumber = data.p_num;
      } else {
        state.finished = true;
      }
    } catch (err) {
      state.error = true;
      state.loading = false;
    }
  };
  return {
    onRefresh,
    onLoad,
    state,
  };
}
export default {
  components: {
    ArticleItem,
  },
  props: {
    channel: {
      type: Object,
      required: true,
    },
  },
  setup(props) {
    // const onLoad = () => {
    //   console.log("load");
    //   // 异步更新数据
    //   // setTimeout 仅做示例，真实场景中一般为 ajax 请求
    //   //1、发送异步请求获取数据
    //   setTimeout(() => {
    //     // 2、获取到服务端返回的数据，将其填充到list数组
    //     for (let i = 0; i < 10; i++) {
    //       state.list.push(state.list.length + 1);
    //     }
    //     // 3、本次数据加载完成后，要把加载状态设置为结束，loading设置为false以后，才能够触发下一次的加载更多的操作
    //     // 加载状态结束
    //     state.loading = false;

    //     // 数据全部加载完成
    //     // 4、当数据全部加载完成后，发finished设置为true
    //     if (state.list.length >= 40) {
    //       state.finished = true;
    //     }
    //   }, 1000);
    // };

    return {
      ...useLoad(props.channel.id),
    };
  },
};
</script>

<style>
.cell-list {
  min-height: 100px;
}
.article-list {
  margin-top: 80px;
  height: 530px;
  overflow-y: auto;
}
</style>
