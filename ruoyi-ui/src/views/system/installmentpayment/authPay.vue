<template>
  <div class="app-container">

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:installmentpayment:add']"
        >新增</el-button>
      </el-col>
    </el-row>


    <h4 class="form-header h4">基本信息</h4>
    <el-form :model="queryParams" ref="queryForm" label-width="80px">
      <el-row>
        <el-col :span="8" :offset="2">
          <el-form-item label="供货单位" prop="supplier">
            <el-input v-model="invoiceList[0].stoUnit" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="8" :offset="2">
          <el-form-item label="票据号" prop="billnumber">
            <el-input  v-model="invoiceList[0].invoiceId" disabled />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <h4 class="form-header h4">分期信息</h4>
    <el-table v-loading="loading" :data="srchecksheetList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="收款人" align="center" prop="payee"/>
      <el-table-column label="支付期限" align="center" prop="payment"/>
      <el-table-column label="支付金额" align="center" prop="amountpaid"/>
      <el-table-column label="创建时间" align="center" prop="paytime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.paytime) }}</span>
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

    <!-- 添加或修改报销单数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        
        <el-form-item label="总金额">
          <el-input v-model="stoMoney" placeholder="请输入总金额" />
        </el-form-item>

        <el-form-item label="已付金额">
          <el-input v-model="paySum" placeholder="请输入已付金额" />
        </el-form-item>

        <el-form-item label="未付金额">
          <el-input v-model="pay" placeholder="请输入未付金额" />
        </el-form-item>

        <el-form-item label="收款人" prop="payee">
          <el-input v-model="form.payee" placeholder="请输入收款人" />
        </el-form-item>

        <el-form-item label="支付期限" prop="payment">
          <el-input v-model="payment" placeholder="请输入支付期限" />
        </el-form-item>

        <el-form-item label="支付金额" prop="amountpaid">
          <el-input v-model="form.amountpaid" placeholder="请输入支付金额" />
        </el-form-item>
        
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">提 交</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInstallmentpayment, addInstallmentpayment, getSrcheck, getInstall, getAuthPay, updateAuthRole } from "@/api/system/installmentpayment";
import { listInvoice,} from "@/api/system/invoice";

export default {
  name: "Installmentpayment",
  data() {
    return {
      stoMoney:0,
      paySum:0,
      pay:0,
      payment:0,
       // 遮罩层
      loading: true,
      // 报销单数据表格数据
      srchecksheetList: [],
      // 分页信息
      total: 0,
      // 选中角色编号
      roleIds:[],
      // 角色信息
      roles: [],
      // 用户信息
      form: {},
      // 显示搜索条件
      showSearch: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        payee: null,
        payment: 0,
        amountpaid: null,
        paytime: null,
        srchecksheet_id: null,
        reserve1: null,
        reserve2: null,
        reserve3: null,
        reserve4: null
      },
      // 角色信息
      id: [],
      // 表单校验
      rules: {
      },
      invoiceList:[],
    };
  },

  created() {
    const id = this.$route.params && this.$route.params.id;
    this.queryParams.id = id;
    listInvoice(this.queryParams).then(response => {
      this.invoiceList = response.rows;
      this.queryParams.id = null
    });
    // this.queryParams.srchecksheet_id = id;
    // listInstallmentpayment(this.queryParams).then(response => {
    //   this.srchecksheetList = response.rows;
    //   this.total = response.total;
    //   this.loading = false;
    // });
    this.queryParams.id = id;
    getInstall(id).then((response) => {
      this.srchecksheetList = response.rows;
      this.total = response.total;
      this.loading = false;
    });
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {
        payee: null
      };
      this.resetForm("form");
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增分期付款信息";
      this.stoMoney = this.invoiceList[0].stoMoney;
      this.paySum = this.srchecksheetList[0].reserve1;
      this.pay = parseInt(this.stoMoney)-parseInt(this.paySum);
      this.payment = parseInt(this.total)+1;
    },
    /** 单击选中行数据 */
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // 保存选中的数据编号
    getRowKey(row) {
      return row.roleId;
    },
    /** 提交按钮 */
    submitForm() {
      this.form.srchecksheetId = this.invoiceList[0].id
      this.form.payment = this.payment
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSrchecksheet(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInstallmentpayment(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 查询报销单数据列表 */
    getList() {
      this.loading = true;
      getInstall(this.queryParams).then(response => {
        this.srchecksheetList = response.rows;
          this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    /** 关闭按钮 */
    close() {
      const obj = { path: "/system/srchecksheet/index" };
      this.$tab.closeOpenPage(obj);
    },
  },
};
</script>