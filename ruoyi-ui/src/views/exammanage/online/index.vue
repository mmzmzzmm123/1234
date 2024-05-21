<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考试名称" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入考试名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="examList" @selection-change="handleSelectionChange">
      <el-table-column label="考试名称" align="center" prop="title" />
      <el-table-column label="考试时间" align="center" prop="startTime" width="180">
        <template v-slot="scope">
            <span v-if="scope.row.timeLimit">
              {{ scope.row.startTime }} ~ {{ scope.row.endTime }}
            </span>
          <span v-else>不限时</span>
        </template>

<!--        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>-->
      </el-table-column>
      <el-table-column label="总分数" align="center" prop="totalScore" />
      <el-table-column label="总时长" align="center" prop="totalTime" />
      <el-table-column label="及格分数" align="center" prop="qualifyScore" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handlePre(scope.row)" v-hasPermi="['exammanage:exam:edit']">去考试</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {listExam } from "@/api/exammanage/exam";

export default {
  name: "Exam",
  dicts: ['common_yes_no'],
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 考试管理表格数据
      examList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        content: null,
        openType: null,
        state: null,
        timeLimit: null,
        startTime: null,
        endTime: null,
        totalScore: null,
        totalTime: null,
        qualifyScore: null,
        userId: null,
        deptId: null
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询考试管理列表 */
    getList() {
      this.loading = true;
      listExam(this.queryParams).then(response => {
        this.examList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        title: null,
        content: null,
        openType: null,
        state: null,
        timeLimit: null,
        startTime: null,
        endTime: null,
        createTime: null,
        updateTime: null,
        totalScore: null,
        totalTime: null,
        qualifyScore: null,
        userId: null,
        deptId: null
      };
      this.resetForm("form");
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

    /** 去考试按钮 */
    handlePre(row) {
      this.reset();
      const examId = row.id || this.ids
      this.$router.push(`/exammanage/prepare-exam/prepareExam/${examId}`);
    },


  }
};
</script>
