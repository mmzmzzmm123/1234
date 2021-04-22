<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="12" :md="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon-class="user" />用户名称
                <div class="pull-right">{{ user.userName }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="phone" />手机号码
                <div class="pull-right">{{ user.phonenumber }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="email" />用户邮箱
                <div class="pull-right">{{ user.email }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="tree" />所属部门
                <div class="pull-right" v-if="user.dept">
                  {{ user.dept.deptName }} / {{ postGroup }}
                </div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="peoples" />所属角色
                <div class="pull-right">{{ roleGroup }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="date" />创建日期
                <div class="pull-right">{{ user.createTime }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="star" />是否绑定微信
                <div class="pull-right">{{ wxOpenId }}</div>
              </li>
            </ul>
            <el-button
              v-if="!wxOpenId"
              type="primary"
              size="mini"
              @click="submit"
              >绑定</el-button
            >
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getUserProfile } from "@/api/system/user";

export default {
  name: "Profile",
  data() {
    return {
      code: "",
      user: {},
      roleGroup: {},
      postGroup: {},
      wxOpenId: "",
    };
  },
  created() {
    this.getUser();
  },
  methods: {
    getUser() {
      getUserProfile().then((response) => {
        this.user = response.data;
        // console.log(this.user);
        this.wxOpenId = this.user.openId;
        this.roleGroup = response.roleGroup;
        this.postGroup = response.postGroup;
      });
    },
    submit() {},
  },
};
</script>
