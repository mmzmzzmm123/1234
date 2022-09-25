<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-form
          :model="queryParams"
          ref="queryForm"
          size="small"
          :inline="true"
          v-show="showSearch"
          label-width="68px"
        >
          <el-form-item label="时间" prop="sj">
            <el-date-picker
              clearable
              v-model="queryParams.sj"
              type="date"
              size="mini"
              value-format="yyyy-MM-dd"
              placeholder="请选择时间"
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
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['invest:hsag:export']"
          >导出</el-button
        >
      </el-col>
      
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="hsagList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="时间" align="center" prop="sj" />
      <el-table-column label="收盘点位" align="center" prop="spdw" />
      <el-table-column label="pe数值" align="center" prop="peCv" />
      <el-table-column label="pb数值" align="center" prop="pbCv" />
      <el-table-column label="ps数值" align="center" prop="psCv" />
      <el-table-column label="pe10年百分位" align="center" prop="pePos10" />
      <el-table-column label="pb10年百分位" align="center" prop="pbPos10" />
      <el-table-column label="ps10年百分位" align="center" prop="psPos10" />
      <el-table-column label="pe20年百分位" align="center" prop="pePos20" />
      <el-table-column label="pb20年百分位" align="center" prop="pbPos20" />
      <el-table-column label="ps20年百分位" align="center" prop="psPos20" />
      <el-table-column label="信号" align="center" prop="zonghePos" />
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
  listHsag,
  getHsag,
  delHsag,
  addHsag,
  updateHsag,
} from "@/api/invest/hsag";

export default {
  name: "Hsag",
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
      // 信号与系统表格数据
      hsagList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        sj: null,
        spdw: null,
        peCv: null,
        pbCv: null,
        psCv: null,
        pePos10: null,
        pbPos10: null,
        psPos10: null,
        pePos20: null,
        pbPos20: null,
        psPos20: null,
        zonghePos: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询信号与系统列表 */
    getList() {
      this.loading = true;
      listHsag(this.queryParams).then((response) => {
        this.hsagList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        sj: null,
        spdw: null,
        peCv: null,
        pbCv: null,
        psCv: null,
        pePos10: null,
        pbPos10: null,
        psPos10: null,
        pePos20: null,
        pbPos20: null,
        psPos20: null,
        zonghePos: null,
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.sj);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "invest/hsag/export",
        {
          ...this.queryParams,
        },
        `hsag_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
