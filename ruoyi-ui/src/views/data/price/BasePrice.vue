<template>
  <div class="app-container">
    <el-form :model="queryParams" :rules="rules" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item label="小区ID" prop="communityId" clearable>
        <el-input
          v-model="queryParams.communityId"
          placeholder="请输入案例小区ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目类型" prop="communityType" clearable>
        <el-select v-model="queryParams.communityType" placeholder="请选择项目类型">
          <el-option
            v-for="item in communityTypeOptions"
            :value="item.value"
            :label="item.label"
            :key="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="价格类型" prop="priceType" clearable>
        <el-select v-model="queryParams.priceType" placeholder="请选择价格类型">
          <el-option
            v-for="item in priceTypeOptions"
            :value="item.value"
            :label="item.label"
            :key="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="价值时点" prop="valuePoint" clearable>
        <el-date-picker
          v-model="queryParams.valuePoint"
          format="yyyy-MM"
          value-format="yyyy-MM"
          type="month"
          placeholder="选择年月"
          @keyup.enter.native="handleQuery"
        ></el-date-picker>
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
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="小区ID" align="center" prop="communityId" />
      <el-table-column label="项目类型" align="center" prop="communityType">
        <template slot-scope="scope">
          <el-tag size="big" v-if="1 == scope.row.communityType">住宅</el-tag>
          <el-tag size="big" type="success" v-if="2 == scope.row.communityType">办公</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="价格类型" align="center" prop="priceType">
        <template slot-scope="scope">
          <el-tag size="big" v-if="1 == scope.row.priceType">售价</el-tag>
          <el-tag size="big" type="success" v-if="2 == scope.row.priceType">租金</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="价值时点" align="center" prop="valuePoint" />
      <el-table-column label="标准基价" align="center" prop="standardPrice" />
      <el-table-column label="主力面积基价" align="center" prop="mainAreaPrice" />
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
  get,
  update,
  export2File,
  getYearMonthList
} from "@/api/data/basePrice";

export default {
  name: "basePrice",
  data() {
    // 年月
    var checkYearMonth = (rule, value, callback) => {
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
        communityId: undefined,
        communityType: undefined,
        priceType: undefined,
        valuePoint: undefined,
        pageIndex: 1,
        pageSize: 10
      },
      yearMonthList: [],
      priceTypeOptions: [
        { value: 1, label: "售价" },
        { value: 2, label: "租金" }
      ],
      communityTypeOptions: [
        { value: 1, label: "住宅" },
        { value: 2, label: "办公" }
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
          "/data/compute/rentprice/residence/importData"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        yearMonth: [
          { validator: checkYearMonth, trigger: "blur" },
          { validator: checkYearMonth, trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.loading = false;
  },
  methods: {
    priceTypeFormatter: function(row, column, cellValue, index) {
      if (1 === cellValue) {
        return "售价";
      } else if (2 === cellValue) {
        return "租金";
      } else {
        return "";
      }
    },
    communityTypeFormatter: function(row, column, cellValue, index) {
      if (1 === cellValue) {
        return "住宅";
      } else if (2 === cellValue) {
        return "办公";
      } else {
        return "";
      }
    },
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      get(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改办公基价";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            update(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("确认导出物业基价?", "警告", {
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
    },
    handleImport() {
      this.upload.title = "办公基价导入";
      this.upload.open = true;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
