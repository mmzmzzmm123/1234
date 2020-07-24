<template>
  <div class="app-container">
    <el-form :model="queryParams" :rules="rules" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item label="表名" prop="tableName" clearable>
        <el-input
          v-model="queryParams.tableName"
          placeholder="请输入表名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="字段名称" prop="columnName" clearable>
        <el-input
          v-model="queryParams.columnName"
          placeholder="请输入字段名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="字段类型" prop="fieldType" clearable>
        <el-select v-model="queryParams.fieldType" placeholder="请选字段类型">
          <el-option
            v-for="item in fieldTypeOptions"
            :value="item.value"
            :label="item.label"
            :key="item.value"
          ></el-option>
        </el-select>
      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:user:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column label="中文表名" align="center" prop="tableChineseName" />
      <el-table-column label="数据库表" align="center" prop="tableDbName" />
      <el-table-column label="中文字段名" align="center" prop="columnChineseName" />
      <el-table-column label="数据库字段名" align="center" prop="columnDbName" />
      <el-table-column label="字段类型" align="center" prop="fieldType" />
      <el-table-column label="字典类型" align="center" prop="dictType" />
      <el-table-column label="异常数据比(%)" align="center" prop="stat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleStat(scope.row)"
            v-hasPermi="['monitor:teemlink:list']"
          >数据分析</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageIndex"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import { list, get } from "@/api/monitor/teemlink";

export default {
  name: "teemLinkStat",
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
      // 办公基价表格数据
      dataList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        tableName: undefined,
        columnName: undefined,
        pageIndex: 1,
        pageSize: 10
      },
      yearMonthList: [],
      fieldTypeOptions: [
        { value: "字符型", label: "字符型" },
        { value: "数值型", label: "数值型" },
        { value: "日期型", label: "日期型" },
        { value: "字典型", label: "字典型" }
      ],
      // 表单参数
      form: {}
    };
  },
  created() {
    this.loading = false;
  },
  methods: {
    /** 查询办公基价列表 */
    getList() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.loading = true;
          list(this.queryParams).then(response => {
            this.dataList = response.rows;
            this.total = response.total;
            this.loading = false;
          });
        }
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
        id: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageIndex = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 统计 */
    handleStat(row) {
      this.reset();
      this.loading = true;
      get(row.tableDbName, row.columnDbName).then(response => {
        this.loading = false;
        if(response.data.count === response.data.validCount) {
          row.stat = '无';
          return;
        }
        row.stat =
          parseFloat(
            ((response.data.count - response.data.validCount) /
              response.data.count) *
              100
          ).toFixed(2) + "%";
      });
    }
  }
};
</script>
