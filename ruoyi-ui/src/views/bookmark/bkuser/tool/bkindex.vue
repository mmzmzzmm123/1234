<template>
  <div class="app-container " style="width: 870px;margin-left: 50px">
        <div class="label-title">
            <span>用户配置</span>
        </div>

        <el-card class="box-card" style="background-color: #ffffff">
          <div>
            <div class="text-center">
              <userAvatar :user="user" />
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon-class="user" />用户名称
                <div class="pull-right">{{ user.userName }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="phone" />绑定手机
                <div class="pull-right">{{ user.phonenumber }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="email" />绑定邮箱
                <div class="pull-right">{{ user.email }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="peoples" />身份角色
                <div class="pull-right">{{ roleGroup }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="date" />注册日期
                <div class="pull-right">{{ user.createTime }}</div>
              </li>
<!--              <li class="list-group-item">-->
<!--                <svg-icon icon-class="date" />上次登陆时间-->
<!--                <div class="pull-right">{{ user.login_date }}</div>-->
<!--              </li>-->
<!--              <li class="list-group-item">-->
<!--                <svg-icon icon-class="date" />上次登陆IP-->
<!--                <div class="pull-right">{{ user.login_ip }}</div>-->
<!--              </li>-->
<!--              <li class="list-group-item">-->
<!--                <svg-icon icon-class="date" />登陆账号-->
<!--                <div class="pull-right">{{ user.user_name }}</div>-->
<!--              </li>-->
            </ul>
          </div>
        </el-card>

    <div class="label-title label-title-two">
      <span>用户配置</span>
    </div>
        <el-card style="margin-top: 8px">
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :user="user" />
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd :user="user" />
            </el-tab-pane>
          </el-tabs>
        </el-card>

  </div>
</template>

<script>
  import userAvatar from "../../../system/user/profile/userAvatar";
  import userInfo from "../../../system/user/profile/userInfo";
  import resetPwd from "../../../system/user/profile/resetPwd";
  import { getUserProfile } from "@/api/system/user";

  export default {
    name: "Profile",
    components: { userAvatar, userInfo, resetPwd },
    data() {
      return {
        user: {},
        roleGroup: {},
        postGroup: {},
        activeTab: "userinfo"
      };
    },
    created() {
      this.getUser();
    },
    methods: {
      getUser() {
        getUserProfile().then(response => {
          this.user = response.data;
          this.roleGroup = response.roleGroup;
          this.postGroup = response.postGroup;
        });
      }
    }
  };
</script>
<style scoped>
  .pull-right{

  }
  .label-title{
    margin-bottom: 25px;
    font-weight: 900;
    font-size: 22px;
  }
  .label-title-two{
    margin-top: 20px;
  }

</style>

