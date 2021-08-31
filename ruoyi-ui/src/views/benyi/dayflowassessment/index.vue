<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="学年学期" prop="xnxq">
            <el-select v-model="queryParams.xnxq" placeholder="请选择学年学期">
              <el-option
                v-for="dict in xnxqOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="班级名称" prop="classid">
            <el-select
              v-model="queryParams.classid"
              clearable
              size="small"
              placeholder="请选择班级"
            >
              <el-option
                v-for="dict in classOptions"
                :key="dict.bjbh"
                :label="dict.bjmc"
                :value="dict.bjbh"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索</el-button
            >
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置</el-button
            >
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <div class="mb8 btn-list">
      <el-button
        type="primary"
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['benyi:dayflowassessment:add']"
        >评估</el-button
      >
    </div>

    <el-table
      v-loading="loading"
      border
      :data="dayflowassessmentList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="所属计划" align="center" prop="planid" :formatter="planFormat"/> -->
      <el-table-column
        label="学年学期"
        fixed
        align="center"
        prop="xnxq"
        :formatter="xnxqFormat"
      />
      <el-table-column
        label="班级名称"
        align="center"
        prop="classid"
        :formatter="classFormat"
      />
      <el-table-column
        label="评估对象"
        align="center"
        prop="pgdx"
        :formatter="pgdxFormat"
      />
      <el-table-column label="最终扣分" align="center" prop="zzdf" />
      <el-table-column
        label="评估人"
        align="center"
        prop="createUserid"
        :formatter="createUserFormat"
      />
      <el-table-column label="评估时间" align="center" prop="createTime" />
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        width="60"
        class-name="small-padding fixed-width edit-btns"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleAssessment(scope.row)"
            v-hasPermi="['benyi:dayflowassessment:query']"
            >详情</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-show="!checkUpdateable(scope.row)"
            @click="handleUpdateAssessment(scope.row)"
            v-hasPermi="['benyi:dayflowassessment:query']"
            >评估</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-show="!checkRemoveable(scope.row)"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:dayflowassessment:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {
  listDayflowassessment,
  getDayflowassessment,
  delDayflowassessment,
} from "@/api/benyi/dayflowassessment";
import { listClass } from "@/api/system/class";
import { listUser } from "@/api/system/user";
import { listDetail, getDetail } from "@/api/benyi/dayflow/dayflowmanger";

export default {
  name: "Dayflowassessment_1",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 幼儿园一日流程评估表格数据
      dayflowassessmentList: [],
      // 班级
      classOptions: [],
      // 学年学期
      xnxqOptions: [],
      // 所有教师
      userOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planid: undefined,
        deptId: undefined,
        classid: undefined,
        bzbh: undefined,
        bzxm: undefined,
        pbbh: undefined,
        pbxm: undefined,
        zlbh: undefined,
        zlxm: undefined,
        xnxq: undefined,
        bzid: undefined,
        kfz: undefined,
        bzmf: undefined,
        zzdf: undefined,
        kfcs: undefined,
        pgdx: undefined,
        classdf: undefined,
        createUserid: undefined,
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
    this.getClassList();
    this.getUserList();
    // 获取学年学期
    this.getDicts("sys_xnxq").then((response) => {
      this.xnxqOptions = response.data;
    });
  },
  methods: {
    /** 查询幼儿园一日流程评估列表 */
    getList() {
      this.loading = true;
      listDayflowassessment(this.queryParams).then((response) => {
        this.dayflowassessmentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 获取班级列表
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
      });
    },
    // 班级字典翻译
    classFormat(row, column) {
      var actions = [];
      var datas = this.classOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].bjbh == "" + row.classid) {
          actions.push(datas[key].bjmc);
          return false;
        }
      });
      return actions.join("");
    },
    // 学年学期类型--字典状态字典翻译
    xnxqFormat(row, column) {
      return this.selectDictLabel(this.xnxqOptions, row.xnxq);
    },
    /** 查询用户列表 */
    getUserList() {
      listUser(null).then((response) => {
        this.userOptions = response.rows;
      });
    },
    // 教师字典翻译
    pgdxFormat(row, column) {
      var actions = [];
      var datas = this.userOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].userId == "" + row.pgdx) {
          actions.push(datas[key].nickName);
          return false;
        }
      });
      return actions.join("");
    },
    // 教师字典翻译
    createUserFormat(row, column) {
      var actions = [];
      var datas = this.userOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].userId == "" + row.createUserid) {
          actions.push(datas[key].nickName);
          return false;
        }
      });
      return actions.join("");
    },
    // 学年学期类型--字典状态字典翻译
    xnxqFormat(row, column) {
      return this.selectDictLabel(this.xnxqOptions, row.xnxq);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    handleAssessment(row) {
      const id = row.id;
      this.$router.push({
        path: "/benyi/dayflowassessments/details/" + id,
        query: {
          dayflowassessmentteacherdetails:
            "一日流程评估详情（" + row.pgdxxm + "）",
        },
      });
    },
    handleUpdateAssessment(row) {
      const id = row.id;
      //console.log(id);
      this.$router.push({
        path: "/benyi/dayflowassessmentupdate/edit/" + id,
      });
    },
    handleAdd() {
      this.$router.push({
        path: "/benyi/dayflowassessment/teacher/" + Date.now(),
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm("是否确认删除当前班级的评估数据?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delDayflowassessment(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    //控制按钮可用
    checkUpdateable(row) {
      //console.log(row.createTime);
      var status = row.status;
      var nowdate = new Date();
      var y = nowdate.getFullYear();
      var m = (nowdate.getMonth() + 1).toString().padStart(2, "0");
      //console.log(date.toLocaleDateString());

      return status == "1" || !this.CompareDate(row.createTime, y + "/" + m);
    },
    //控制按钮可用
    checkRemoveable(row) {
      //console.log(row.remark);
      var remark = row.remark;
      if (remark == "" || remark == null) {
        return false;
      } else {
        return true;
      }
    },
    //比较日期大小
    CompareDate(d1, d2) {
      //return new Date(d1.replace(/-/g, "/")) > new Date(d2.replace(/-/g, "/"));
      //console.log(d1.replace(/-/g, "/").substring(0, 7));
      //console.log(d2.replace(/-/g, "/").substring(0, 7));
      var d3 = d1.replace(/-/g, "/").substring(0, 7);
      var d4 = d2.replace(/-/g, "/").substring(0, 7);
      return d3 == d4;
    },
  },
};
</script>
<style lang="scss" scoped>
.el-select {
  width: 100%;
}
.my-date-picker {
  width: 100%;
}
.edit-btns {
  .el-button {
    display: block;
    margin: 0 auto;
  }
}
.no-margin ::v-deep.el-form-item__content {
  margin: 0 !important;
}
</style>