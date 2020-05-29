<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item label="年月" prop="yearMonth" clearable>
        <el-date-picker
          v-model="queryParams.yearMonth"
          format="yyyyMM"
          value-format="yyyyMM"
          type="month"
          placeholder="选择年月"
          @keyup.enter.native="handleQuery"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="联城小区ID" prop="communityId" clearable>
        <el-input
          v-model="queryParams.communityId"
          placeholder="请输入案例小区名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="年月" align="center" prop="yearMonth" />
      <el-table-column label="小区ID" align="center" prop="communityId" />
      <el-table-column label="租金主力面积系数" align="center" prop="mainRentCoefficient" />
      <el-table-column label="平均租金" align="center" prop="rentPrice" />
      <el-table-column label="主力面积租金" align="center" prop="mainRentPrice" />
      <el-table-column label="平均租金（上周期）" align="center" prop="rentPrice_1" />
      <el-table-column label="价格涨跌幅类型-调整后" align="center" prop="voppat" />
      <el-table-column label="价格涨跌幅-调整后" align="center" prop="voppa" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageIndex"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  list
} from "@/api/data/artificialResidenceRentPrice";

export default {
  name: "artificialResidenceRentBasePrice",
  data() {
    // 年月
    var checkYearMonth = (rule, value, callback) => {
      console.log(value);
      if (value === "" || !isNaN(parseInt(value))) {
        callback(new Error("请输入年月"));
      } else {
        callback();
      }
    };

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
        yearMonth: undefined,
        communityId: undefined,
        communityName: undefined,
        pageIndex: 1,
        pageSize: 10
      },
      statusOptions: [
        { value: 1, text: "正常" },
        { value: 1, text: "失效" }
      ],
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url:
          process.env.VUE_APP_BASE_API +
          "/data/rentprice/residence/artificial/importData"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        yearMonth: [{ validator: checkYearMonth, trigger: "blur" }]
      }
    };
  },
  created() {
    this.loading = false;
    // this.getList();
  },
  methods: {
    yesOrNotFormatter: function(row, column, cellValue, index) {
      if (cellValue) return "是";
      return "否";
    },
    statusFormatter: function(row, column, cellValue, index) {
      if (cellValue) return "正常";
      return "失效";
    },
    dateFormatter: function(row, column, cellValue, index) {
      if (cellValue) {
        return cellValue.substring(0, 10);
      }
      return "";
    },
    /** 查询办公基价列表 */
    getList() {
      this.loading = true;
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
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
    }
  }
};
</script>
