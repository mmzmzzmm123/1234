<template>
  <el-card :class="className" :style="{ height: height, width: width }">
    <div slot="header" class="clearfix">
      <span>欢迎来到 {{ deptName }} 管理系统</span>
    </div>
    <div class="text item">班级总数：{{ classTotal }}</div>
    <div class="text item">教职工总数：{{ userTotal }}</div>
    <div class="text item">幼儿总数：{{ childTotal }}</div>
    <!-- <div class="text item">今日园历活动数：0</div> -->
    <div class="text item">学习视频数：{{ videoTotal }}</div>
  </el-card>
</template>

<script>
import { listUser, getUserProfile } from "@/api/system/user";
import { listClass } from "@/api/system/class";
import { info } from "@/api/system/home";

export default {
  props: {
    className: {
      type: String,
      default: "chart",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "480px",
    },
  },
  data() {
    return {
      deptName: {},
      classTotal: 0,
      userTotal: 0,
      childTotal: 0,
      videoTotal: 0,
    };
  },
  created() {
    this.getDeptName();
    this.getClassCount();
    this.getUserCount();
    this.getInfo();
  },
  methods: {
    getInfo() {
      info().then((response) => {
        this.childTotal = response.childcount;
        this.videoTotal = response.videocount;
      });
    },
    getDeptName() {
      getUserProfile().then((response) => {
        this.deptName = response.data.dept.deptName;
      });
    },
    /** 查询班级信息列表 */
    getClassCount() {
      listClass(null).then((response) => {
        this.classTotal = response.total;
      });
    },
    /** 查询用户列表 */
    getUserCount() {
      listUser(null).then((response) => {
        this.userTotal = response.total;
      });
    },
  },
};
</script>

<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}
</style>
