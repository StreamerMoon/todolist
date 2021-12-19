<template>
  <van-cell class="article-item">
    <template #title>
      <span class="title van-multi-ellipsis--l2"
        >{{
          article.title
        }}士大夫撒留点饭打扫房间啊士大夫撒留点饭打扫房间啊</span
      >
    </template>
    <template #label>
      <div v-if="article.type === 3" class="cover-wrap">
        <div
          v-for="(img, index) in article.images"
          :key="index"
          class="cover-item"
        >
          <van-image width="100" height="100" :src="`${url}` + img"></van-image>
        </div>
      </div>
      <span>{{ article.aut_name }}</span
      >&nbsp;&nbsp; <span>{{ article.comm_count }}</span
      >&nbsp;&nbsp;
      <span>{{ dayjs().to(dayjs(article.pubdate)) }}</span>
    </template>
    <template #right-icon>
      <van-image
        v-if="article.type === 1"
        width="100"
        height="100"
        :src="`${url}` + article.images[0]"
      ></van-image>
    </template>
  </van-cell>
</template>

<script>
import dayjs from "../../utils/day.js";
export default {
  props: {
    article: {
      type: Object,
      required: true,
    },
  },
  setup() {
    const url = import.meta.env.VITE_APP_URL;
    return { url, dayjs };
  },
};
</script>

<style>
.article-item .title {
  font-size: 20px;
  color: #3a3a3a;
}
.article-item .van-cell__value {
  flex: unset;
  width: 232px;
  height: 146px;
  padding-left: 25px;
}
.article-item .right-cover {
  width: 132px;
  height: 146px;
}

.article-item .label-info-wrap span {
  font-size: 14px;
  color: #b4b4b4;
  margin-right: 25px;
}
.article-item .cover-wrap {
  display: flex;
}
.article-item .cover-wrap .cover-item {
  /* 平分宽度 */
  flex: 1;
  padding-right: 4px;
}
</style>
