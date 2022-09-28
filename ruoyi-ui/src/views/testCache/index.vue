<template>
  <el-form ref="loginForm" :model="loginForm" class="test-cache">
    测试缓存
    <el-form-item prop="username">
      <el-input
        v-model="loginForm.username"
        type="text"
        auto-complete="off"
        placeholder="账号"
      >
        <svg-icon slot="prefix" icon-class="user" />
      </el-input>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: "page-1",
  data () {
    return {
      loginForm: {
        username: ''
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .test-cache{
    margin: 50px;
    width: 300px;
  }
</style>
