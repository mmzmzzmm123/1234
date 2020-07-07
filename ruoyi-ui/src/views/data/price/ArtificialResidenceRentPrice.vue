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

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-bell"
          size="mini"
          @click="handleSync"
          v-hasPermi="['system:user:import']"
        >从作价导入</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:user:import']"
        >文件导入</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:user:export']"
        >导出</el-button>
      </el-col> -->
    </el-row>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column label="年月" align="center" prop="yearMonth" fixed />
      <el-table-column label="小区ID" align="center" prop="communityId" fixed />
      <el-table-column label="租金主力面积系数" align="center" prop="mainCoefficientRent" />
      <el-table-column label="平均租金" align="center" prop="rentPrice" />
      <el-table-column label="主力面积租金" align="center" prop="mainRentPrice" />
      <el-table-column label="平均租金（上周期）" align="center" prop="rentPrice_1" />
      <el-table-column label="价格涨跌幅类型-调整后" align="center" prop="voppat" />
      <el-table-column label="价格涨跌幅-调整后" align="center" prop="voppa" />
      <!-- <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:user:edit']"
          >修改</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageIndex"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 文件上传对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '/' + queryParams.yearMonth"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
        <div class="el-upload__tip" slot="tip">
          <el-alert :title="uploadTips" type="warning" effect="dark" :closable="false"></el-alert>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="updateRules" label-width="160px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="小区ID">
              <el-input v-model="form.communityId" disabled readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="租金主力面积系数">
              <el-input v-model="form.mainCoefficientRent" disabled readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="平均租金" prop="rentPrice">
              <el-input v-model="form.rentPrice" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主力面积租金" prop="mainRentPrice">
              <el-input v-model="form.mainRentPrice" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="平均租金（上周期）" prop="rentPrice_1">
              <el-input v-model="form.rentPrice_1" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="价格涨跌幅类型-调整后" prop="voppat">
              <el-input v-model="form.voppat" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="价格涨跌幅-调整后" prop="voppa">
              <el-input v-model="form.voppa" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="formSubmitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  getById,
  list,
  update,
  importSync,
  export2File,
  getYearMonthList
} from "@/api/data/artificialResidenceRentPrice";

export default {
  name: "artificialResidenceRentBasePrice",
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
    // 价格判断
    var priceValidator = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请输入价格"));
      } else if (value === "" || isNaN(parseFloat(value))) {
        callback(new Error("请输入价格"));
      } else {
        if (value <= 0) {
          callback(new Error("请输入合理价格"));
        } else {
          callback();
        }
      }
    };

    var numberValidator = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请输入数字"));
      } else if (value === "" || isNaN(parseFloat(value))) {
        callback(new Error("请输入数字"));
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
      yearMonthList: [],
      statusOptions: [
        { value: 1, text: "正常" },
        { value: 1, text: "失效" }
      ],
      uploadTips: "",
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
          "/data/rent-price/residence/artificial/importData"
      },
      // 表单参数
      form: {
        url: ""
      },
      // 表单校验
      rules: {
        yearMonth: [
          { validator: yearMonthValidator, trigger: "blur" },
          { validator: yearMonthValidator, trigger: "change" }
        ]
      },
      updateRules: {
        yearMonth: [
          { validator: yearMonthValidator, trigger: "blur" },
          { validator: yearMonthValidator, trigger: "change" }
        ],
        rentPrice: [
          { validator: priceValidator, trigger: "blur" },
          { validator: priceValidator, trigger: "change" }
        ],
        mainPriceRent: [
          { validator: priceValidator, trigger: "blur" },
          { validator: priceValidator, trigger: "change" }
        ],
        rentPrice_1: [
          { validator: priceValidator, trigger: "blur" },
          { validator: priceValidator, trigger: "change" }
        ],
        voppa: [
          { validator: numberValidator, trigger: "blur" },
          { validator: numberValidator, trigger: "change" }
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
    handleSync() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          // 清除原有数据，然后导入
          this.$refs["queryForm"].validate(valid => {
            if (valid) {
              this.$confirm("此操作会删除原数据，是否继续?", "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              })
                .then(() => {
                  return importSync(this.queryParams.yearMonth);
                })
                .then(response => {
                  this.$message({
                    type: "success",
                    message: "操作成功!"
                  });
                })
                .catch(() => {});
            }
          });
        }
      });
    },
    handleExport() {
      const queryParams = this.queryParams;
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.$confirm("是否确认导出住宅租赁基价数据项?", "警告", {
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
      });
    },
    handleImport() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.upload.title = "人工修正住宅租赁基价导入";
          this.uploadTips = "当前选中的基价月份：" + this.queryParams.yearMonth;
          this.upload.open = true;
        }
      });
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      const yearMonth = row.yearMonth;
      getById(yearMonth, id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改住宅租赁基价";
      });
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
    },
    formSubmitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true;
          update(this.form).then(response => {
            this.$message({
              type: "success",
              message: "操作成功!"
            });
            this.loading = false;
            this.open = false;
            this.getList();
          });
        }
      });
    }
  }
};
</script>
