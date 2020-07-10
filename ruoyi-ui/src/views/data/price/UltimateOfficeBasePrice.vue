<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px" :rules="rules">
      <el-form-item label="年月" prop="yearMonth">
        <el-select
          v-model="queryParams.yearMonth"
          placeholder="请选择年月"
          @change="yearMonthChange(queryParams.yearMonth)"
        >
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
          placeholder="请输入案例小区ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称或地址" prop="nameOrAddress" clearable>
        <el-input
          v-model="queryParams.nameOrAddress"
          placeholder="请输入名称或地址"
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
      <el-form-item label="标准楼栋">
        <el-select v-model="queryParams.standardBuilding" clearable>
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
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
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:user:import']"
        >文件导入</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList">
      <el-table-column label="年月" align="center" prop="yearMonth" fixed />
      <el-table-column label="小区ID" align="center" prop="communityId" width="110" fixed />
      <el-table-column label="楼栋ID" align="center" prop="buildingId" width="110" fixed />
      <el-table-column label="项目名称" align="center" prop="communityName" />
      <el-table-column label="办公项目地址" align="center" prop="communityAddress" />
      <el-table-column label="楼栋地址" align="center" prop="buildingAddress" />
      <el-table-column label="区域-板块-环线-街道" align="center" :formatter="regionFormatter" width="300" />
      <el-table-column label="建成年代" align="center" prop="year" />
      <el-table-column label="平均面积" align="center" prop="avgArea" />
      <el-table-column label="总层数" align="center" prop="totalFloorSum" />
      <el-table-column label="地上层数" align="center" prop="upperFloorSum" />
      <el-table-column label="办公分类" align="center" prop="officeClass" />
      <el-table-column label="办公等级" align="center" prop="officeLevel" />
      <el-table-column label="主力基价(上期)" align="center" prop="mainPrice_1" />
      <el-table-column label="主力租金(上期)" align="center" prop="mainPriceRent_1" />
      <el-table-column label="主力基价" align="center" prop="mainPrice" />
      <el-table-column label="主力租金" align="center" prop="mainPriceRent" />
      <el-table-column label="主力基价涨跌幅" align="center" prop="mainPricePst" />
      <el-table-column label="主力租金涨跌幅" align="center" prop="mainPriceRentPst" />
      <el-table-column label="主力基价类型" align="center" prop="mainPriceType" />
      <el-table-column label="主力租金类型" align="center" prop="mainPriceRentType" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormatter" />
      <el-table-column
        label="标准楼栋"
        align="center"
        prop="standardBuilding"
        :formatter="yesOrNotFormatter"
      />
      <el-table-column label="价格更改说明" align="center" prop="adjustPriceComment" />
      <el-table-column label="更新日期" align="center" prop="updateDate" :formatter="dateFormatter" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
      :before-close="handleClose"
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url+ '/' + queryParams.yearMonth "
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
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  list,
  getById,
  update,
  export2File,
  getYearMonthList
} from "@/api/data/ultimateOfficeBasePrice";

export default {
  name: "Ultimate",
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
    var checkPrice = (rule, value, callback) => {
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
      // 上传提示
      uploadTips: "",
      // 年月列表
      yearMonthList: [],
      // 查询参数
      queryParams: {
        yearMonth: undefined,
        communityId: undefined,
        nameOrAddress: undefined,
        status: undefined,
        standardBuilding: undefined,
        pageNum: 1,
        pageSize: 10
      },
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
          process.env.VUE_APP_BASE_API + "/data/compute/price/office/importData"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        yearMonth: [
          { validator: checkYearMonth, trigger: "blur" },
          { validator: checkYearMonth, trigger: "change" }
        ]
      },
      updateRules: {
        yearMonth: [
          { validator: checkYearMonth, trigger: "blur" },
          { validator: checkYearMonth, trigger: "change" }
        ],
        mainPrice: [
          { validator: checkPrice, trigger: "blur" },
          { validator: checkPrice, trigger: "change" }
        ],
        mainPriceRent: [
          { validator: checkPrice, trigger: "blur" },
          { validator: checkPrice, trigger: "change" }
        ],
        mainPrice_1: [
          { validator: checkPrice, trigger: "blur" },
          { validator: checkPrice, trigger: "change" }
        ],
        mainPriceRent_1: [
          { validator: checkPrice, trigger: "blur" },
          { validator: checkPrice, trigger: "change" }
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
    yearMonthChange: function(yearMonth) {
      // this.upload.url += "/" + yearMonth;
      // console.log(this.upload.url);
    },
    regionFormatter: function(row, column, cellValue, index) {
      // 区域-板块-环线-街道
      return (
        row["countyName"] +
        "-" +
        row["blockName"] +
        "-" +
        row["loopName"] +
        "-" +
        row["streetName"]
      );
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    // handleUpdate(row) {
    //   this.reset();
    //   const id = row.id || this.ids;
    //   const yearMonth = row.yearMonth;
    //   getById(yearMonth, id).then(response => {
    //     // 复合信息
    //     response.data.complexRegion =
    //       response.data.countyName +
    //       "-" +
    //       response.data.blockName +
    //       "-" +
    //       response.data.loopName +
    //       "-" +
    //       response.data.streetName +
    //       "-" +
    //       response.data.buildingAddress;
    //     // 办公信息
    //     response.data.complexOfficeInfo =
    //       response.data.officeClass + "-" + response.data.officeLevel;
    //     // 层数信息
    //     response.data.complexFloorInfo =
    //       response.data.upperFloorSum + "-" + response.data.totalFloorSum;
    //     this.form = response.data;
    //     this.open = true;
    //     this.title = "修改办公基价";
    //   });
    // },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
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
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有办公基价数据项?", "警告", {
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
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.upload.title = "办公基价导入";
          this.uploadTips = "当前选中的基价月份：" + this.queryParams.yearMonth;
          this.upload.open = true;
        }
      });
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
    handleClose: function(done) {
      this
        .$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    }
  }
};
</script>
