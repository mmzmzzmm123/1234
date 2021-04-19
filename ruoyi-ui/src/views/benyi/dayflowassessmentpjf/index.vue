<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="70px"
    >
      <el-form-item label="选择月份" prop="createTime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.createTime"
          type="month"
          value-format="yyyy-MM-dd"
          placeholder="选择月份"
        >
        </el-date-picker>
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
        label="班级名称"
        align="center"
        fixed
        prop="classid"
        :formatter="classFormat"
      />
      <el-table-column label="班级综合平均分" align="center" prop="bjpjf" />
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
import { listDayflowassessmentPjf } from "@/api/benyi/dayflowassessment";
import { listClass } from "@/api/system/class";

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
        createTime: undefined,
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
    this.getClassList();
  },
  methods: {
    /** 查询幼儿园一日流程评估列表 */
    getList() {
      this.loading = true;
      listDayflowassessmentPjf(this.queryParams).then((response) => {
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