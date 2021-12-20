<template>
  <div>
    <van-nav-bar title="登录" class="page-nav-bar">
      <template #left>
        <van-icon name="cross" size="18" @click="this.$router.back()" />
      </template>
    </van-nav-bar>
    <van-form @submit="onSubmit">
      <van-field
        name="userName"
        placeholder="请输入用户名"
        left-icon="manager"
        v-model="userName"
        :rules="userFormRules.userName"
      />
      <van-field
        type="password"
        name="userPwd"
        placeholder="请输入密码"
        left-icon="lock"
        v-model="userPwd"
        :rules="userFormRules.userPwd"
      />
      <div style="margin: 16px">
        <van-button block type="primary" native-type="submit">
          登录
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
import { reactive, toRefs } from "vue";
import { login } from "../../api/user";
import { Toast } from "vant";
import { useStore } from "vuex";
import router from "../../router";
// 1、构建登录请求
// 2、获取用户名和密码发送到服务端
function useSubmit(user) {
  const store = useStore();
  const onSubmit = async () => {
    //console.log("=================")
    Toast.loading({
      message: "登录中...",
      forbidClick: true,
    });

    const res = await login(user);
   // console.log(res.data)
    if (res.data.code === 200) {
      store.commit("setUser", res.data);
      Toast.success("用户登录成功");
      router.push("/my");
    } else {
      Toast.fail("用户名或密码错误");
    }
  };
  // 定义表单校验规则
  const userFormRules = {
    userName: [{ required: true, message: "请输入用户名" }],
    userPwd: [
      { required: true, message: "请输入密码" },
      { pattern: /^\d{6}$/, message: "密码格式错误" },
    ],
  };
  return {
    onSubmit,
    userFormRules,
  };
}
export default {
  setup() {
    const user = reactive({
      userName: "",
      userPwd: "",
    });
    return {
      ...toRefs(user),
      ...useSubmit(user),
    };
  },
};
</script>

<style></style>
