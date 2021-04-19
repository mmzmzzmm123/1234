<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="70px"
    >
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

    <el-table v-loading="loading" border :data="dayflowassessmentList">
      <el-table-column
        label="学年学期"
        align="center"
        prop="xnxq"
        fixed
        :formatter="xnxqFormat"
      />
      <el-table-column
        label="班级名称"
        align="center"
        prop="classid"
        fixed
        :formatter="classFormat"
      />
      <el-table-column
        label="评估对象"
        align="center"
        prop="pgdx"
        fixed
        :formatter="pgdxFormat"
      />
      <el-table-column
        label="评估人"
        align="center"
        prop="createUserid"
        fixed
        :formatter="createUserFormat"
      />
      <el-table-column label="最终得分" align="center" prop="zzdf" />
      <el-table-column label="早间接待" align="center" prop="zjjdpjf" />
      <el-table-column label="用餐" align="center" prop="ycpjf" />
      <el-table-column label="早间坐圈" align="center" prop="zjzqpjf" />
      <el-table-column label="分组教学" align="center" prop="fzjxpjf" />
      <el-table-column label="点心时间" align="center" prop="dxsjpjf" />
      <el-table-column label="如厕洗手饮水" align="center" prop="rcxsyspjf" />
      <el-table-column label="活动过渡" align="center" prop="hdgdpjf" />
      <el-table-column label="户外活动" align="center" prop="hwhdpjf" />
      <el-table-column label="午睡" align="center" prop="wspjf" />
      <el-table-column label="离园再见" align="center" prop="lyzjpjf" />
      <el-table-column label="安全" align="center" prop="aqpjf" />
      <el-table-column label="自由活动" align="center" prop="zyhdpjf" />
      <el-table-column
        label="规则与纪律约束"
        align="center"
        prop="gzyjlyspjf"
      />
      <el-table-column label="微型课程" align="center" prop="wxkcpjf" />
      <el-table-column label="潜课程" align="center" prop="qkcpjf" />
      <el-table-column label="评估时间" align="center" prop="createTime" />
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
  },
};
</script>
