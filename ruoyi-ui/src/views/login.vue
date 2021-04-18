<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">{{h3}}</h3>
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


      <el-form-item prop="phone" v-if="!loginFlag">
        <el-input
          v-model="loginForm.phone"
          auto-complete="off"
          placeholder="手机号码"
          style="width: 100%"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="phone" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <el-form-item prop="phoneCode" v-if="!loginFlag">
        <el-input
          v-model="loginForm.phoneCode"
          type="text"
          auto-complete="off"
          placeholder="验证码"
          style="width: 65%"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="validCode"  class="el-input__icon input-icon" />
        </el-input>

        <el-button  v-show="verShow"  @click="handleClick()">获取验证码</el-button>
        <el-button  :disabled="!verShow" style="width: 30%" v-show="!verShow" @click="handleClick()">{{timer}}</el-button>
      </el-form-item>




      <el-form-item prop="code" v-if="loginFlag">
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
      <a class="register" @click="switchLogin">{{switchLoginText}}>></a>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">{{loginButton}}</span>
          <span v-else>{{loginButtonMeg}}</span>
        </el-button>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2020-2021 CQY All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg,getregisterUser } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      loginFlag:true,//默认是登陆页面
      h3: "藏趣云",
      loginButton:"登 录",
      loginButtonMeg:"登 录 中...",
      switchLoginText:"前往注册",
      codeUrl: "",
      cookiePassword: "",
      verShow:true,//是否倒计时
      timer:60, //短信验证码
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        phone:"",
        phoneCode:"",
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
      registerRules: {
        username: [
          { required: true, trigger: "blur", message: "用户名不能为空" }
        ],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ],
        phone: [
          {required: true, trigger: "blur", message: "手机号不能为空"}
         ],
        phoneCode: [
          {required: true, trigger: "change", message: "手机验证码不能为空"}
         ]
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
    //发送验证码
    handleClick(){
      var phone=this.loginForm.phone;
      if (phone==null||phone==undefined||phone==''||phone.length!=11||!(/^1[34578]\d{9}$/.test(phone))){
        this.$message.error('手机号格式错误，请重填!');
        return false;
      }
      //发送验证码
      this.getPhoneCode();
      var _this=this;
      _this.verShow = false;
      var auth_timer = setInterval(()=>{
        _this.timer--;
        if(this.timer<=0){
          _this.verShow = true;
          _this.timer=60
          clearInterval(auth_timer)
        }
      },1000)
    },
    getPhoneCode() {
      var phone=this.loginForm.phone;
      getregisterUser(phone).then(res => {
        if (res.code==200){
          this.$message({
            message: res.msg,
            type: 'success'
          });
        }if (res.code==500) {
          this.$message.error(res.msg);
        }else{
          this.$message.error('系统错误，短信发送失败，请稍后再试!');
        }
      }).catch((e) => {});;
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

      if (this.loginFlag){
        this.loginUser();
      } else{
        this.register();
      }

    },
    //登陆
    loginUser() {
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
              this.$router.push({ path: this.redirect || "/content" });
            })
            .catch(() => {
              this.loading = false;
              this.getCode();
            });
        }
      });
    },
    //注册
    register(){
  this.$refs.loginForm.validate(valid => {
    if (valid) {
      this.loading = true;
      this.$store
        .dispatch("register", this.loginForm)
        .then(res => {
          if(res.code == 200){
            this.$message({
              message: res.msg,
              type: 'success'
            });
          }else {
            this.$message.error(res.msg);
          }
          this.loading = false;//注册加载中...
        })
        .catch(() => {
          this.loading = false;
        });
    }
  });

    },
    //切换状态
    switchLogin(){
      this.loginFlag=this.loginFlag?false:true;
      this.h3 = this.loginFlag?"藏趣云":"用户注册";
      this.loginButton = this.loginFlag?"登 录":"注册";
      this.loginButtonMeg = this.loginFlag?"登 录 中...":"注 册 中...";
      this.switchLoginText = this.loginFlag?"前往注册":"返回登陆";

      this.$message({
        message: this.loginFlag?"返回登陆":"前往注册",
        type: 'warning'
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">

  .register{
    float: right;color: #6a6a6a;
    font-size: 14px;
  }
  .register:hover{
    color: #7a6df0;
    font-weight: 600;
  }

.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/image/2043428.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
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
  opacity: 0.8;
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
</style>
