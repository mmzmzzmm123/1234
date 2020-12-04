<template>
  <div class="login">
    <div class="site-content__wrapper">
      <div class="site-content">
        <div class="brand-info">
          <h3 class="brand-info__text">道路旅客运输企业安全风险防控系统</h3>
          <hr>
        </div>
        <div class="login-main">
          <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
            <h3 class="title">登录账户</h3>
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
                <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                auto-complete="off"
                placeholder="密码"
                @keyup.enter.native="handleLogin"
              >
                <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
              </el-input>
            </el-form-item>
            <el-form-item prop="code">
              <el-input
                v-model="loginForm.code"
                auto-complete="off"
                placeholder="验证码"
                style="width: 63%"
                @keyup.enter.native="handleLogin"
              >
                <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
              </el-input>
              <div class="login-code">
                <img :src="codeUrl" @click="getCode" class="login-code-img"/>
              </div>
            </el-form-item>
            <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
            <el-form-item style="width:100%;">
              <el-button
                class="login-btn-submit"
                :loading="loading"
                size="medium"
                type="primary"
                style="width:100%;"
                @click.native.prevent="handleLogin"
              >
                <span v-if="!loading">登 录</span>
                <span v-else>登 录 中...</span>
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <!--  底部  -->
      <!--<div class="el-login-footer">
        <span>Copyright © 2018-2020 ruoyi.vip All Rights Reserved.</span>
      </div>-->
    </div>
  </div>
</template>

<script>
  import { getCodeImg } from "@/api/login";
  import Cookies from "js-cookie";
  import { encrypt, decrypt } from '@/utils/jsencrypt'

  export default {
    name: "Login",
    data() {
      return {
        codeUrl: "",
        cookiePassword: "",
        loginForm: {
          username: "admin",
          password: "admin123",
          rememberMe: false,
          code: "",
          uuid: ""
        },
        loginRules: {
          username: [
            { required: true, trigger: "blur", message: "用户名不能为空" }
          ],
          password: [
            { required: true, trigger: "blur", message: "密码不能为空" }
          ],
          code: [{ required: true, trigger: "change", message: "验证码不能为空" }]
        },
        loading: false,
        redirect: undefined
      };
    },
    watch: {
      $route: {
        handler: function(route) {
          this.redirect = route.query && route.query.redirect;
        },
        immediate: true
      }
    },
    created() {
      this.getCode();
      this.getCookie();
    },
    methods: {
      getCode() {
        getCodeImg().then(res => {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        });
      },
      getCookie() {
        const username = Cookies.get("username");
        const password = Cookies.get("password");
        const rememberMe = Cookies.get('rememberMe')
        this.loginForm = {
          username: username === undefined ? this.loginForm.username : username,
          password: password === undefined ? this.loginForm.password : decrypt(password),
          rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
        };
      },
      handleLogin() {
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            this.loading = true;
            if (this.loginForm.rememberMe) {
              Cookies.set("username", this.loginForm.username, { expires: 30 });
              Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
              Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
            } else {
              Cookies.remove("username");
              Cookies.remove("password");
              Cookies.remove('rememberMe');
            }
            this.$store
              .dispatch("Login", this.loginForm)
              .then(() => {
                this.$router.push({ path: this.redirect || "/" });
              })
              .catch(() => {
                this.loading = false;
                this.getCode();
              });
          }
        });
      }
    }
  };
</script>

<style rel="stylesheet/scss" lang="scss">
  .login {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(38, 50, 56, .6);
    overflow: hidden;
    min-width: 1180px;
    &:before {
      position: fixed;
      top: 0;
      left: 0;
      z-index: -1;
      display: flex;
      justify-content: center;
      align-items: center;
      width: 100%;
      height: 100%;
      content: "";
      background-image: url(~@/assets/image/bg.jpg);
      background-size: cover;
    }
    .site-content__wrapper {
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      padding: 0;
      margin: 0;
      overflow-x: hidden;
      overflow-y: auto;
      min-height: 100%;
      & {
        background-color: rgba(0, 94, 160, 0.6);
      }
    }
    .site-content {
      min-height: 100%;
      padding: 30px 500px 30px 30px;
      position: relative;
    }
    .brand-info{
      margin: 100px 100px 0 90px;
      color: #fff;
      hr{
        background-color: #fff;
      }
    }
    .brand-info__text {
      font: normal 600 34px "黑体", "宋体", sans-serif;
      margin:  0 0 22px 0;
      padding-right: 30px;
      text-transform : uppercase;
    }
    .login-main {
      position: absolute;
      top: 50%;
      right: 0;
      padding: 0 20px;
      min-width: 320px;
      min-height: 20%;
      -webkit-transform: translateX(-20%) translateY(-50%);
    }
    .login-btn-submit {
      background-color: #1a698e;
      border-color: #065c84;
      &:hover{
        background-color: #165877;
      }
      &:active{
        background-color: #134d68;
      }
    }
  }
  .title {
    margin: 0px auto 30px auto;
  }
  .login-form {
    border-radius: 6px;
    background-color: #0892e2;
    width: 400px;
    padding: 25px 25px 5px 25px;
    .el-input {
      height: 38px;
      input {
        height: 38px;
      }
    }
    .input-icon {
      height: 39px;
      width: 14px;
      margin-left: 2px;
    }
  }
  .login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
  }
  .login-code {
    width: 33%;
    height: 38px;
    float: right;
    img {
      cursor: pointer;
      vertical-align: middle;
    }
  }
  .el-login-footer {
    height: 40px;
    line-height: 40px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    color: #fff;
    font-family: Arial;
    font-size: 12px;
    letter-spacing: 1px;
  }
  .login-code-img {
    height: 38px;
  }
  .el-checkbox__label{
    color: #c8c8c8;
  }
  .el-checkbox__input.is-checked + .el-checkbox__label {
    color: #ffffff;
  }
  .el-checkbox__input.is-checked .el-checkbox__inner {
    background-color: #87c5ff;
    border-color: #87c5ff;
  }
</style>
