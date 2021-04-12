<template>
  <div class="app-container">

    <el-table
      v-loading="loading"
      :data="dayflowassessmentList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="所属计划" align="center" prop="planid" :formatter="planFormat"/> -->
      <el-table-column
        label="班级名称"
        align="center"
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
      <el-table-column label="规则与纪律约束" align="center" prop="gzyjlyspjf" />
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
import {
  listDayflowassessmentPjf
} from "@/api/benyi/dayflowassessment";
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
  },
};
</script>