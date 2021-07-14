<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <el-form-item label="优秀导师" prop="dsid">
        <el-select
          v-model="queryParams.dsid"
          placeholder="请选择见习导师"
          filterable
          size="small"
        >
          <el-option
            v-for="dict in dsOptions"
            :key="dict.id"
            :label="dict.jsxm"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jxjs:yxjxdstjb:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="yxjxdstjbList">
      <!-- <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="优秀导师" align="center" prop="dsxm" />
      <!-- <el-table-column label="所属部门" align="center" prop="deptId" />
      <el-table-column label="推荐人" align="center" prop="createuserid" /> -->
      <el-table-column label="推荐理由" align="center" prop="tjly" />
      <el-table-column label="推荐年份" align="center" prop="nf" />
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
  listYxjxdstjbexport,
  getYxjxdstjb,
  exportYxjxdstjb,
} from "@/api/jxjs/yxjxdstjb";
import { listDsjbxx } from "@/api/jxzxkhgl/dsjbxx";

export default {
  name: "Yxjxdstjb",
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
      // 优秀见习导师推荐表格数据
      yxjxdstjbList: [],
      //导师
      dsOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dsid: null,
        deptId: null,
        createuserid: null,
        nf: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getDsList();
    this.getList();
  },
  methods: {
    /** 查询导师基本信息列表 */
    getDsList() {
      listDsjbxx(null).then((response) => {
        this.dsOptions = response.rows;
      });
    },
    /** 查询优秀见习导师推荐列表 */
    getList() {
      this.loading = true;
      listYxjxdstjbexport(this.queryParams).then((response) => {
        this.yxjxdstjbList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        dsid: null,
        deptId: null,
        createuserid: null,
        nf: null,
        createTime: null,
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
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有优秀见习导师推荐数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportYxjxdstjb(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
