<template>
  <div class="app-container">
    <el-form :model="queryParams" :rules="rules" ref="queryForm" :inline="true" label-width="100px">
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
      <el-form-item label="联城小区名称" prop="communityName" clearable>
        <el-input
          v-model="queryParams.communityName"
          placeholder="请输入小区名称"
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
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:user:edit']"
        >修改</el-button>
      </el-col>
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
        >导入</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="年月" align="center" prop="yearMonth" />
      <el-table-column label="小区ID" align="center" prop="communityId" />
      <el-table-column label="小区名称" align="center" prop="communityName" />
      <el-table-column label="小区地址" align="center" prop="communityAddress" />

      <el-table-column label="区域" align="center" prop="county" />
      <el-table-column label="板块" align="center" prop="block" />
      <el-table-column label="环线" align="center" prop="loop" />

      <el-table-column
        label="是否生成索引"
        align="center"
        prop="isIndxGen"
        :formatter="yesOrNotFormatter"
      />
      <el-table-column
        label="参与涨幅计算"
        align="center"
        prop="isPstCalc"
        :formatter="yesOrNotFormatter"
      />
      <el-table-column label="运行状态" align="center" prop="statusRun" />
      <el-table-column label="物业类型" align="center" prop="propertyType" />
      <el-table-column label="小区类型" align="center" prop="projectType" />
      <el-table-column label="小区类型细分" align="center" prop="projectTypeDtl" />
      <el-table-column label="物业档次" align="center" prop="projectLevel" />
      <el-table-column label="物业开发期数" align="center" prop="propertyDevPeriod" />
      <el-table-column label="绑定聚类ID" align="center" prop="bindClassID" />
      <el-table-column label="租金主力面积系数" align="center" prop="mainCoefficientRent" />
      <el-table-column label="AI租金(草稿)" align="center" prop="rentPriceDft" />
      <el-table-column label="主力面积租金(草稿)" align="center" prop="mainRentPriceDft" />
      <el-table-column label="上月AI租金" align="center" prop="rentPrice_1" />
      <el-table-column label="成交均价(上周期)" align="center" prop="priceDealMean_1" />
      <el-table-column label="成交最大价(上周期)" align="center" prop="priceDealMax_1" />
      <el-table-column label="成交数量(上周期)" align="center" prop="sumDeal_1" />
      <el-table-column label="成交均价相对AI租金涨跌幅(上周期)" align="center" prop="priceDeal_1_ToAI_Pst" />
      <el-table-column label="成交均价" align="center" prop="priceDealMean" />
      <el-table-column label="成交最大价" align="center" prop="priceDealMax" />
      <el-table-column label="成交数量" align="center" prop="sumDeal" />
      <el-table-column label="成交均价相对AI租金涨跌幅" align="center" prop="priceDeal_ToAI_Pst" />
      <el-table-column label="当月比上月成交案例价调整比例" align="center" prop="priceDeal_ToLst_Pst" />
      <el-table-column label="挂牌下架案例均价" align="center" prop="priceCaseOff" />
      <el-table-column label="当月比上月挂牌下架案例价调整比例" align="center" prop="priceCaseOff_ToLst_Pst" />
      <el-table-column label="挂牌最低价" align="center" prop="priceListedMin" />
      <el-table-column label="挂牌最低价相对AI租金涨跌幅" align="center" prop="priceLstMn_ToAI_Pst" />
      <el-table-column label="与上月AI租金比链家1案例价格价调整比例" align="center" prop="priceCase1_ToAI_Pst" />
      <el-table-column label="与上月AI租金比链家2案例价格价调整比例" align="center" prop="priceCase2_ToAI_Pst" />
      <el-table-column label="当月比上月链家1案例价调整比例" align="center" prop="priceCase1_ToLst_Pst" />
      <el-table-column label="当月比上月链家2案例价调整比例" align="center" prop="priceCase2_ToLst_Pst" />
      <el-table-column label="链家1案例价" align="center" prop="priceCase1" />
      <el-table-column label="链家1调价幅度" align="center" prop="priceCase1AdjPst" />
      <el-table-column label="链家1案例总量" align="center" prop="sumCase1" />
      <el-table-column label="链家2案例价" align="center" prop="priceCase2" />
      <el-table-column label="链家2调价幅度" align="center" prop="priceCase2AdjPst" />
      <el-table-column label="链家2案例总量" align="center" prop="sumCase2" />
      <el-table-column label="价格涨跌幅类型-调整前" align="center" prop="voppbt" />
      <el-table-column label="价格涨跌幅-调整前" align="center" prop="voppb" />
      <el-table-column label="绑定小区编号" align="center" prop="bindProjID" />
      <el-table-column label="绑定小区涨跌幅" align="center" prop="bind_Proj_Pst" />
      <el-table-column label="绑定板块+聚类ID" align="center" prop="bind_Block_Class" />
      <el-table-column label="绑定板块+聚类ID的涨跌幅" align="center" prop="bind_Block_Class_Pst" />
      <el-table-column label="绑定板块+物业档次" align="center" prop="bind_Block_Plevel" />
      <el-table-column label="绑定板块+物业档次的涨跌幅" align="center" prop="bind_Block_Plevel_Pst" />
      <el-table-column label="绑定板块+小区类型" align="center" prop="bind_Block_PType" />
      <el-table-column label="绑定板块+小区类型的涨跌幅" align="center" prop="bind_Block_Ptype_Pst" />
      <el-table-column label="绑定区县+小区类型" align="center" prop="bind_County_PType" />
      <el-table-column label="绑定区县+小区类型的涨跌幅" align="center" prop="bind_County_Ptype_Pst" />
      <el-table-column label="绑定混合小区ID" align="center" prop="bind_MixProject_PType" />
      <el-table-column label="绑定混合小区涨跌幅" align="center" prop="bind_MixProject_Pst" />
      <el-table-column label="价格涨跌幅类型-调整后" align="center" prop="voppat" />
      <el-table-column label="价格涨跌幅-调整后" align="center" prop="voppa" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:user:edit']"
          >修改</el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:user:remove']"
          >删除</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageIndex"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改办公基价对话框 -->
    <!-- <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="160px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="小区ID">
              <el-input v-model="form.communityId" disabled="true" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="楼栋ID">
              <el-input v-model="form.buildingId" disabled="true" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="主力基价（元/㎡）">
              <el-input v-model="form.mainPrice" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主力租金（元/月·㎡）">
              <el-input v-model="form.mainPriceRent" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="主力基价涨跌幅">
              <el-input v-model="form.mainPricePst" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主力租金涨跌幅">
              <el-input v-model="form.mainPriceRentPst" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="主力基价类型">
              <el-input v-model="form.mainPriceType" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主力租金类型">
              <el-input v-model="form.mainPriceRentType" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
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
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>-->
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
} from "@/api/data/computeResidenceRentPrice";

export default {
  name: "computeResidenceRentBasePrice",
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
      this.$refs['queryForm'].validate(valid => {
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
    /** 新增按钮操作 */
    // handleAdd() {
    //   this.reset();
    //   this.open = true;
    //   this.title = "添加办公基价";
    // },
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
          } else {
            // addUltimate(this.form).then(response => {
            //   if (response.code === 200) {
            //     this.msgSuccess("新增成功");
            //     this.open = false;
            //     this.getList();
            //   } else {
            //     this.msgError(response.msg);
            //   }
            // });
          }
        }
      });
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
