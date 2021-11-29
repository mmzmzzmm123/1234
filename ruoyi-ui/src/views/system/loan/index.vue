<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="企业名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入企业名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="统一社会信用代码" prop="companyCreditCode">
        <el-input
          v-model="queryParams.companyCreditCode"
          placeholder="请输入统一社会信用代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系人姓名" prop="contactName">
        <el-input
          v-model="queryParams.contactName"
          placeholder="请输入联系人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系人手机" prop="contactPhone">
        <el-input
          v-model="queryParams.contactPhone"
          placeholder="请输入联系人手机"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="贷款对象类型" prop="loanObjectType">
        <el-select v-model="queryParams.loanObjectType" placeholder="请选择贷款对象类型" clearable size="small">
          <el-option
            v-for="dict in loanObjectTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户经理" prop="customerManager">
        <el-input
          v-model="queryParams.customerManager"
          placeholder="请输入客户经理"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:loan:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:loan:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:loan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
		  :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:loan:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="loanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="企业id" align="center" prop="companyId" />
      <el-table-column label="企业名称" align="center" prop="companyName" />
      <el-table-column label="统一社会信用代码" align="center" prop="companyCreditCode" />
      <el-table-column label="企业地址" align="center" prop="companyAddress" />
      <el-table-column label="意向银行" align="center" prop="loanBand" :formatter="loanBandFormat" />
      <el-table-column label="贷款金额" align="center" prop="loanAmount" />
      <el-table-column label="是否首次贷款" align="center" prop="loanFirst" :formatter="loanFirstFormat" />
      <el-table-column label="贷款用途" align="center" prop="loanPurpose" />
      <el-table-column label="联系人姓名" align="center" prop="contactName" />
      <el-table-column label="联系人手机" align="center" prop="contactPhone" />
      <el-table-column label="银行网点" align="center" prop="bankBranch" />
      <el-table-column label="贷款对象类型" align="center" prop="loanObjectType" :formatter="loanObjectTypeFormat" />
      <el-table-column label="客户经理" align="center" prop="customerManager" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleReport(scope.row)"
            v-hasPermi="['system:loan:report']"
          >查看报告</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:loan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:loan:remove']"
          >删除</el-button>
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

    <!-- 添加或修改企业贷款信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="闽政通用户ID" prop="mztUserId">
          <el-input v-model="form.mztUserId" placeholder="请输入闽政通用户ID" />
        </el-form-item>
        <el-form-item label="企业名称" prop="companyName">
          <el-input v-model="form.companyName" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="companyCreditCode">
          <el-input v-model="form.companyCreditCode" placeholder="请输入统一社会信用代码" />
        </el-form-item>
        <el-form-item label="省份" prop="companyProvince">
          <el-input v-model="form.companyProvince" placeholder="请输入省份" />
        </el-form-item>
        <el-form-item label="企业地址" prop="companyAddress">
          <el-input v-model="form.companyAddress" placeholder="请输入企业地址" />
        </el-form-item>
        <el-form-item label="市" prop="companyCity">
          <el-input v-model="form.companyCity" placeholder="请输入市" />
        </el-form-item>
        <el-form-item label="区或县" prop="companyArea">
          <el-input v-model="form.companyArea" placeholder="请输入区或县" />
        </el-form-item>
        <el-form-item label="企业划型" prop="companyType">
          <el-input v-model="form.companyType" placeholder="请输入企业划型" />
        </el-form-item>
        <el-form-item label="所在行业" prop="companyIndustry">
          <el-input v-model="form.companyIndustry" placeholder="请输入所在行业" />
        </el-form-item>
        <el-form-item label="主营业务" prop="companyBusiness">
          <el-input v-model="form.companyBusiness" placeholder="请输入主营业务" />
        </el-form-item>
        <el-form-item label="意向银行">
          <el-checkbox-group v-model="form.loanBand">
            <el-checkbox
              v-for="dict in loanBandOptions"
              :key="dict.dictValue"
              :label="dict.dictValue">
              {{dict.dictLabel}}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="贷款金额" prop="loanAmount">
          <el-input v-model="form.loanAmount" placeholder="请输入贷款金额" />
        </el-form-item>
        <el-form-item label="是否首次贷款" prop="loanFirst">
          <el-select v-model="form.loanFirst" placeholder="请选择是否首次贷款">
            <el-option
              v-for="dict in loanFirstOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="贷款用途" prop="loanPurpose">
          <el-input v-model="form.loanPurpose" placeholder="请输入贷款用途" />
        </el-form-item>
        <el-form-item label="备注说明" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注说明" />
        </el-form-item>
        <el-form-item label="联系人姓名" prop="contactName">
          <el-input v-model="form.contactName" placeholder="请输入联系人姓名" />
        </el-form-item>
        <el-form-item label="联系人手机" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系人手机" />
        </el-form-item>
        <el-form-item label="银行网点" prop="bankBranch">
          <el-input v-model="form.bankBranch" placeholder="请输入银行网点" />
        </el-form-item>
        <el-form-item label="贷款对象类型" prop="loanObjectType">
          <el-select v-model="form.loanObjectType" placeholder="请选择贷款对象类型">
            <el-option
              v-for="dict in loanObjectTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户经理" prop="customerManager">
          <el-input v-model="form.customerManager" placeholder="请输入客户经理" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 征信报告对话框 -->
    <el-dialog :title="titleReport" :visible.sync="openReport" width="800px" append-to-body>
      <el-collapse v-model="activeNames" @change="handleChange">
          <el-collapse-item title="主题库-评分模型" name="1">
            <div v-if="formReport.ztkZbhZrjgb == null">暂无数据</div>
            <el-form v-else ref="formReport" :model="formReport" :rules="rules" label-width="200px">
                <el-form-item label="来源应用系统编号" prop="lyyyxtid">
                  {{formReport.ztkZbhZrjgb.lyyyxtid}}
                </el-form-item>
                <el-form-item label="来源表" prop="dataFrom">
                  {{formReport.ztkZbhZrjgb.dataFrom}}
                </el-form-item>
                <el-form-item label="信息入库时间" prop="xxrksj">
                  <el-input v-model="form.xxrksj" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="信息入库时间戳" prop="xxrksjTmsp">
                  <el-input v-model="form.xxrksjTmsp" placeholder="请输入信息入库时间戳" />
                </el-form-item>
                <el-form-item label="标准库_备注" prop="bz">
                  <el-input v-model="form.bz" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="来源表信息入库时间" prop="sourceXxrksj">
                  <el-input v-model="form.sourceXxrksj" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="来源表主记录ID" prop="sourceZjlid">
                  <el-input v-model="form.sourceZjlid" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="业务标签ID" prop="businessFlagId">
                  <el-input v-model="form.businessFlagId" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="业务标签值" prop="businessFlagValue">
                  <el-input v-model="form.businessFlagValue" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="首次采集时间" prop="firstCollectTime">
                  <el-input v-model="form.firstCollectTime" placeholder="请输入首次采集时间" />
                </el-form-item>
                <el-form-item label="最近采集时间" prop="lastCollectTime">
                  <el-input v-model="form.lastCollectTime" placeholder="请输入最近采集时间" />
                </el-form-item>
                <el-form-item label="发现次数" prop="foundTimes">
                  <el-input v-model="form.foundTimes" placeholder="请输入发现次数" />
                </el-form-item>
                <el-form-item label="错误数据标识" prop="errorDataFlag">
                  <el-input v-model="form.errorDataFlag" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="错误数据" prop="errorData">
                  <el-input v-model="form.errorData" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="异常字段编码" prop="errorElement">
                  <el-input v-model="form.errorElement" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="数据批次标识" prop="batchDataFlag">
                  <el-input v-model="form.batchDataFlag" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="数据安全级别代码" prop="sjaqjbdm">
                  <el-input v-model="form.sjaqjbdm" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="采集地" prop="cjd">
                  <el-input v-model="form.cjd" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="统一社会信用代码" prop="tyshxydm">
                  <el-input v-model="form.tyshxydm" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="申报个体工商户姓名" prop="sbgtgshxm">
                  <el-input v-model="form.sbgtgshxm" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="证件类型" prop="zjlx">
                  <el-input v-model="form.zjlx" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="证件号码" prop="zjhm">
                  <el-input v-model="form.zjhm" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="经营年限" prop="jynx">
                  <el-input v-model="form.jynx" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="法人年龄" prop="frnl">
                  <el-input v-model="form.frnl" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="注册地址" prop="zcdz">
                  <el-input v-model="form.zcdz" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="重大税收违法案件" prop="zdsffwaj">
                  <el-input v-model="form.zdsffwaj" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="失信被执行人" prop="sxbzxr">
                  <el-input v-model="form.sxbzxr" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="失信黑名单" prop="sfhmd">
                  <el-input v-model="form.sfhmd" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="国家下发工商异常名录" prop="gjxfgsycml">
                  <el-input v-model="form.gjxfgsycml" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="不动产查封" prop="bdccf">
                  <el-input v-model="form.bdccf" type="textarea" placeholder="请输入内容" />
                </el-form-item>
            </el-form>
          </el-collapse-item>
          <el-collapse-item title="标准库-评分模型明细" name="2">
            <div v-if="formReport.ztkZbhZrjgb == null">暂无数据</div>
            <div v-else>控制反馈：通过界面样式和交互动效让用户可以清晰的感知自己的操作；</div>
          </el-collapse-item>
          <el-collapse-item title="主题库-准入监管评分" name="3">
            <div>简化流程：设计简洁直观的操作流程；</div>
            <div>清晰明确：语言表达清晰且表意明确，让用户快速理解进而作出决策；</div>
            <div>帮助用户识别：界面简单直白，让用户快速识别而非回忆，减少用户记忆负担。</div>
          </el-collapse-item>
          <el-collapse-item title="标准库-准入监管明细" name="4">
            <div>用户决策：根据场景可给予用户操作建议或安全提示，但不能代替用户进行决策；</div>
            <div>结果可控：用户可以自由的进行操作，包括撤销、回退和终止当前操作等。</div>
          </el-collapse-item>
      </el-collapse>
    </el-dialog>
  </div>
</template>

<script>
import { listLoan, getLoan, delLoan, addLoan, updateLoan, exportLoan,getReport } from "@/api/system/loan";

export default {
  name: "Loan",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
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
      // 企业贷款信息表格数据
      loanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示征信报告
      openReport: false,
      // 意向银行字典
      loanBandOptions: [],
      // 是否首次贷款字典
      loanFirstOptions: [],
      // 贷款对象类型字典
      loanObjectTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyName: null,
        companyCreditCode: null,
        contactName: null,
        contactPhone: null,
        loanObjectType: null,
        customerManager: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mztUserId: [
          { required: true, message: "闽政通用户ID不能为空", trigger: "blur" }
        ],
        companyName: [
          { required: true, message: "企业名称不能为空", trigger: "blur" }
        ],
        companyCreditCode: [
          { required: true, message: "统一社会信用代码不能为空", trigger: "blur" }
        ],
        loanAmount: [
          { required: true, message: "贷款金额不能为空", trigger: "blur" }
        ],
      },

      activeNames: ['1'],
      // 报告弹出层标题
      titleReport: "",
      formReport:{},
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_user_band").then(response => {
      this.loanBandOptions = response.data;
    });
    this.getDicts("data_is_loan_first").then(response => {
      this.loanFirstOptions = response.data;
    });
    this.getDicts("data_loan_object_type").then(response => {
      this.loanObjectTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询企业贷款信息列表 */
    getList() {
      this.loading = true;
      listLoan(this.queryParams).then(response => {
        this.loanList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 意向银行字典翻译
    loanBandFormat(row, column) {
      return this.selectDictLabels(this.loanBandOptions, row.loanBand);
    },
    // 是否首次贷款字典翻译
    loanFirstFormat(row, column) {
      return this.selectDictLabel(this.loanFirstOptions, row.loanFirst);
    },
    // 贷款对象类型字典翻译
    loanObjectTypeFormat(row, column) {
      return this.selectDictLabel(this.loanObjectTypeOptions, row.loanObjectType);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        companyId: null,
        mztUserId: null,
        companyName: null,
        companyCreditCode: null,
        companyProvince: null,
        companyAddress: null,
        companyCity: null,
        companyArea: null,
        companyType: null,
        companyIndustry: null,
        companyBusiness: null,
        loanBand: [],
        loanAmount: null,
        loanFirst: null,
        loanPurpose: null,
        remark: null,
        contactName: null,
        contactPhone: null,
        delFlag: null,
        createTime: null,
        updateTime: null,
        bankBranch: null,
        loanObjectType: null,
        customerManager: null
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
      this.ids = selection.map(item => item.companyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加企业贷款信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const companyId = row.companyId || this.ids
      getLoan(companyId).then(response => {
        this.form = response.data;
        this.form.loanBand = this.form.loanBand.split(",");
        this.open = true;
        this.title = "修改企业贷款信息";
      });
    },
    /** 查看报告按钮操作 */
    handleReport(row) {
      const tyshxydm = row.companyCreditCode
      getReport(tyshxydm).then(response => {
        this.formReport = response.data;
        this.openReport = true;
        this.titleReport = "查看企业征信信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.loanBand = this.form.loanBand.join(",");
          if (this.form.companyId != null) {
            updateLoan(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLoan(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const companyIds = row.companyId || this.ids;
      this.$confirm('是否确认删除企业贷款信息编号为"' + companyIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delLoan(companyIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有企业贷款信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportLoan(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    },

    handleChange(val) {
      console.log(val);
    }
  }
};
</script>
