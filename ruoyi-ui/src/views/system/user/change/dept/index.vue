<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>所属园所信息</span>
          </div>
          <div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon-class="user" />用户名称
                <div class="pull-right">{{ user.userName }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="tree" />所属部门
                <div class="pull-right" v-if="user.dept">{{ user.dept.deptName }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="tree" />选择部门
                <div class="pull-right" v-for="item in deptsOptions" :key="item.deptId">
                  <input
                    type="radio"
                    name="dept"
                    :id="item.deptId"
                    :value="item.deptId"
                    v-model="checkedValue"
                  />
                  <label :for="item.deptId">{{ item.deptName }}</label>
                </div>
              </li>
              <li class="list-group-item">
                <el-button type="primary" size="mini" @click="submit">保存</el-button>
                <el-button type="danger" size="mini" @click="close">关闭</el-button>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getUserProfile } from "@/api/system/user";
import { getDeptsInfo,changeDept } from "@/api/system/dept";

export default {
  name: "changedept",
  data() {
    return {
      checkedValue: null,
      oldValue: null,
      user: {},
      deptsOptions: []
    };
  },
  created() {
    this.getUser();
    this.getDepts();
  },
  methods: {
    getDepts() {
      getDeptsInfo().then(response => {
        // console.log("school=" + response.total);
        // console.log( response);
        if (response.total <= 1) {
        } else {
          console.log(response);
          this.deptsOptions = response.rows;
        }
      });
    },
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;
        this.checkedValue = response.data.dept.deptId;
        this.oldValue = response.data.dept.deptId;
      });
    },
    submit() {
      if (this.checkedValue == this.oldValue) {
        this.msgError("幼儿园无变化");
        return;
      } else {
        //切换岗位
        changeDept(this.checkedValue).then(
            response => {
              if (response.code === 200) {
                this.msgSuccess(response.msg);
                this.getUser();
                this.getDepts();
              } else {
                this.msgError(response.msg);
              }
            }
          )
      }
    },
    close() {
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/index" });
    }
  }
};
</script>