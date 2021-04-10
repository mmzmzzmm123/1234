<template>
  <div class="app-container ">
    <el-row :gutter="20" >
      <el-col :xs="24" :sm="{span: 16, push: 4}" :md="{span: 14, push: 5}"  :xl="{span: 10, push: 7}"  >
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
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
      </el-col>

    </el-row>
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


