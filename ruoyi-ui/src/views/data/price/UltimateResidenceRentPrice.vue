<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :rules="rules" :inline="true" label-width="100px">
      <el-form-item label="年月" prop="yearMonth">
        <el-select v-model="queryParams.yearMonth" placeholder="请选择年月">
          <el-option
            v-for="item in yearMonthList"
            :value="item.value"
            :label="item.label"
            :key="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="小区ID" prop="communityId" clearable>
        <el-input
          v-model="queryParams.communityId"
          placeholder="请输入案例小区名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称或地址" prop="nameOrAddress" clearable>
        <el-input
          v-model="queryParams.nameOrAddress"
          placeholder="请输入小区名称或地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.status" clearable>
          <el-option label="正常" value="1"></el-option>
          <el-option label="失效" value="0"></el-option>
        </el-select>
      </el-form-item>
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
      <el-table-column label="年月" align="center" prop="yearMonth" />
      <el-table-column label="小区ID" align="center" prop="communityId" />
      <el-table-column label="小区名称" align="center" prop="communityName" />
      <el-table-column label="小区地址" align="center" prop="communityAddress" />

      <el-table-column label="区域" align="center" prop="county" />
      <el-table-column label="板块" align="center" prop="block" />
      <el-table-column label="环线" align="center" prop="loop" />
      <el-table-column label="国际社区分" align="center" prop="loop" />
      <el-table-column label="重点小学名称" align="center" prop="loop" />
      <el-table-column label="物业档次" align="center" prop="loop" />
      <el-table-column label="室数量-AI" align="center" prop="loop" />

      <el-table-column label="标准基价（元/㎡·月）" align="center" prop="rentPrice" />
      <el-table-column label="平均租金（元/㎡·月）" align="center" prop="rentPrice" />
      <el-table-column label="散租挂牌案例" align="center" prop="散租挂牌案例" />
      <el-table-column label="租金涨跌幅" align="center" prop="voppa" />
      <el-table-column label="租金-工房1室" align="center" prop="gf_1Room" />
      <el-table-column label="租金-工房2室" align="center" prop="gf_2Room" />
      <el-table-column label="租金-公寓2室" align="center" prop="gy_2Room" />
      <el-table-column label="租金-公寓3室" align="center" prop="gy_3Room" />
      <el-table-column label="租金-1房" align="center" prop="one_Room" />
      <el-table-column label="租金-2房" align="center" prop="two_Room" />
      <el-table-column label="租金-3房" align="center" prop="three_Room" />
      <el-table-column label="整租比" align="center" prop="entireRentRatio" />
      <el-table-column label="整租案例" align="center" prop="entireRentNum" />
      <el-table-column label="分租比" align="center" prop="shareRentRatio" />
      <el-table-column label="分租案例" align="center" prop="shareRentNum" />
      <el-table-column label="租售比" align="center" prop="saleRentRatio" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormatter" />
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
  list,
  export2File,
  getYearMonthList
} from "@/api/data/ultimateResidenceRentPrice";

export default {
  name: "ultimateResidenceRentPrice",
  data() {
    // 年月
    var yearMonthValidator = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请输入年月"));
      } else if (value === "" || isNaN(parseInt(value))) {
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
        nameOrAddress: undefined,
        status: undefined,
        pageIndex: 1,
        pageSize: 10
      },
      yearMonthList: [],
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
          "/data/rentprice/residence/ultimate/importData"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        yearMonth: [
          { validator: yearMonthValidator, trigger: "blur" },
          { validator: yearMonthValidator, trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.loading = false;
    getYearMonthList().then(response => {
      this.yearMonthList = response.data;
    });
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
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有住宅租赁基价数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return export2File(queryParams);
        })
        .then(response => {
          this.download(response.msg);
        })
        .catch(function() {});
    }
  }
};
</script>
