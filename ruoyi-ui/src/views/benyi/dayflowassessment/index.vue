<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="70px"
    >
      <!-- <el-form-item label="所属计划" prop="planid">
        <el-select v-model="queryParams.planid" placeholder="请选择评估计划">
          <el-option
            v-for="dict in dayflowassessmentplanOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          ></el-option>
        </el-select>
      </el-form-item> -->
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
      :data="dayflowassessmentList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="所属计划" align="center" prop="planid" :formatter="planFormat"/> -->
      <el-table-column
        label="学年学期"
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
      <!-- <el-table-column label="班级平均分" align="center" prop="classdf" /> -->
      <!-- <el-table-column
        label="主班教师"
        align="center"
        prop="bzbh"
        :formatter="bzbhFormat"
      />
      <el-table-column
        label="配班教师"
        align="center"
        prop="pbbh"
        :formatter="pbbhFormat"
      />
      <el-table-column
        label="助理教师"
        align="center"
        prop="zlbh"
        :formatter="zlbhFormat"
      /> -->
      <el-table-column
        label="评估对象"
        align="center"
        prop="pgdx"
        :formatter="pgdxFormat"
      />
      <!-- <el-table-column
        label="评估环节"
        align="center"
        prop="bzid"
        :formatter="dayFlowFormat"
      />
      <el-table-column label="环节满分" align="center" prop="bzmf" /> -->
      <!-- <el-table-column label="扣分值" align="center" prop="kfz" /> -->
      <!-- <el-table-column label="扣分次数" align="center" prop="kfcs" /> -->
      <el-table-column label="最终得分" align="center" prop="zzdf" />
      <el-table-column
        label="评估人"
        align="center"
        prop="createUserid"
        :formatter="createUserFormat"
      />
      <el-table-column label="评估时间" align="center" prop="createTime" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
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
            icon="el-icon-delete"
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
import {
  listDayflowassessmentplan,
  getDayflowassessmentplan,
} from "@/api/benyi/dayflowassessmentplan";
import { listClass } from "@/api/system/class";
import { listUser } from "@/api/system/user";
import { listDetail, getDetail } from "@/api/benyi/dayflow/dayflowmanger";

export default {
  name: "Dayflowassessment",
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
      this.$router.push({ path: "/benyi/dayflowassessments/details/" + id });
    },
    handleAdd() {
      this.$router.push({ path: "/benyi/dayflowassessment/teacher" });
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
  },
};
</script>