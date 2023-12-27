<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="发票号" prop="invoiceId">
        <el-input
          v-model="queryParams.invoiceId"
          placeholder="请输入发票号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报账日期" prop="stoDate">
        <!-- <el-date-picker clearable
          v-model="queryParams.stoDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择入库日期">
        </el-date-picker> -->
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
       <el-form-item label="订单金额" prop="minStoMoney">
        <el-input
          style="width: 135px"
          v-model.number="queryParams.minStoMoney"
          type="number"
          placeholder="请输入金额"
          clearable
          @keyup.enter.native="handleQuery"
        /><span>&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;</span><el-input
          style="width: 135px"
          v-model.number="queryParams.maxStoMoney"
          placeholder="请输入金额"
           type="number"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报账金额" prop="minInvoiceMoney">
        <el-input
          style="width: 135px"
          v-model.number="queryParams.minInvoiceMoney"
          placeholder="请输入金额"
           type="number"
          clearable
          @keyup.enter.native="handleQuery"
        /><span>&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;</span><el-input
          style="width: 135px"
          v-model.number="queryParams.maxInvoiceMoney"
          placeholder="请输入金额"
           type="number"
          clearable
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
          v-hasPermi="['system:invoice:add']"
        >报账</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:invoice:edit']"
        >修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:invoice:remove']"
        >取消报账</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-money"
          size="mini"
          :disabled="multipleFen"
          @click="handleFen"
          v-hasPermi="['system:invoice:remove']"
        >分期付款</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:invoice:export']"
        >导出</el-button>
      </el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="invoiceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="是否分期" align="center" prop="kpStatus">
      <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.isFen == 1">分期</el-tag>
          <el-tag type="success" v-if="scope.row.isFen == 0">不分期</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="开票状态" align="center" prop="kpStatus">
      <template slot-scope="scope">
          <el-tag v-if="scope.row.kpStatus == 1">已开票</el-tag>
          <el-tag type="warning" v-if="scope.row.kpStatus == 0">未开票</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="stoUnit" width="190" />
      <el-table-column label="名称" align="center" prop="stoName" />
      <el-table-column label="规格型号" align="center" prop="stoType" />
      <el-table-column label="订单金额" align="center" prop="stoMoney" />
      <el-table-column label="已付金额" align="center" prop="invoicePrice" />
      <el-table-column label="报账单类型" align="center" prop="invoiceType">
      <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.invoiceType == 1">现金</el-tag>
          <el-tag type="warning" v-if="scope.row.invoiceType == 2">挂账</el-tag>
          <el-tag type="success" v-if="scope.row.invoiceType == 3">电汇</el-tag>

        </template>
      </el-table-column>
      <el-table-column label="发票号" align="center" prop="invoiceId" />
      <el-table-column label="报账日期" align="center" prop="invoiceDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.invoiceDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:invoice:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:invoice:remove']"
          >删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
         <el-form-item label="订单金额" prop="stoMoney" >
          <el-input v-model="form.stoMoney" disabled="disabled" :value="form.stoMoney" />
        </el-form-item>
        <el-form-item label="报账类型" prop="invoiceId">
          <template>
            <el-radio v-model="radio" label="1" @input="xj()">现金</el-radio>
            <el-radio v-model="radio" label="2" @input="gz()">挂账</el-radio>
            <el-radio v-model="radio" label="3" @input="gz()">电汇</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="发票号" prop="invoiceId"  v-if="inputStatus">
          <el-input v-model="form.invoiceId" placeholder="请输入发票号" />
        </el-form-item>
        <!-- <el-form-item label="报账金额" prop="invoicePrice" >
          <el-input v-model.number="form.invoicePrice" type="number" :max="form.stoMoney" placeholder="请输入报账金额" required/>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInvoice, getInvoice, delInvoice, addInvoice, updateInvoice } from "@/api/system/invoice";

export default {
  name: "Invoice",
  data() {
    return {
      radio: '1',
      dateRange: [],
      inputStatus:false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      stoIds:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      multipleFen:true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 【请填写功能名称】表格数据
      invoiceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        stoId: null,
        stoMoney: null,
        stoRemark: null,
        stoDate: null,
        invoiceDate: null,
        invoiceId: null,
        userId: null,
        status: null,
        deptId: null,
        kpStatus: null,
        invoicePrice: null,
        invoiceType: null
      },
      queryParamsTwo: {
        stoId: null,
      },
      checked:{},
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        stoId: [
          { required: true, message: "入库单id不能为空", trigger: "blur" }
        ],
        invoiceId: [
          { required: false, message: "发票号不能为空", trigger: "blur" }
        ],
        invoicePrice: [
          { required: true, message: "报账金额不能大于总金额", trigger: "blur" }
        ],
      },
      invoiceFen111: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleFen(){
      this.queryParams.id = this.ids[0];
      for (var inv in this.invoiceList) {
        if (this.invoiceList[inv].id == this.ids[0]) {
          this.invoiceFen111 = this.invoiceList[inv]
        }
    }
    console.log(this.invoiceFen111);
    this.$router.push("/system/pay-auth/role/" + this.invoiceFen111.id);
    },
    xj(){
      this.inputStatus = false
      this.rules.invoiceId[0].required =false
      console.log(this.radio);
      console.log(parseInt(this.radio));
    },
    gz(){
      this.rules.invoiceId[0].required =true
      this.inputStatus = true
      console.log(this.radio);
      console.log(parseInt(this.radio));
    },
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listInvoice(this.queryParams,this.dateRange).then(response => {
        this.invoiceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.form.stoMoney = null;
      this.form.invoicePrice = null;
      this.radio = 1;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        minInvoiceMoney:null,
        maxInvoiceMoney:null,
        minStoMoney:null,
        maxStoMoney:null,
        dateRange:null,
        id: null,
        stoId: null,
        stoMoney: null,
        stoRemark: null,
        stoDate: null,
        invoiceDate: null,
        invoiceId: null,
        userId: null,
        status: null,
        deptId: null,
        kpStatus: null,
        invoicePrice: null,
        invoiceType: null
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
      this.queryParams.maxStoMoney = null;
      this.queryParams.maxInvoiceMoney = null;
      this.dateRange=[];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.checked = selection.map(item => item)
      this.ids = selection.map(item => item.id)
      // this.stoIds = selection.map(item => item.stoId)
      this.single = selection.length!==1
      this.multiple = !selection.length
      if (this.ids.length>1) {
        this.multipleFen = true;
      }
      if (selection.map(item => item.isFen)[0]==1) {
        this.multipleFen = false;
      }else{
        this.multipleFen = true;
      }
      if (selection.map(item => item.invoiceType)[0]==2) {
        this.multipleFen = false;
      }else{
        this.multipleFen = true;
      }
    },
    open3() {
        this.$message({
          message: '请选择一条数据',
          type: 'warning'
        });
      },
    /** 新增按钮操作 */
    handleAdd() {
      if (this.ids.length!=1) {
        this.open3()
      }else{
        this.reset();
        console.log(this.checked);
        this.radio='1';
        this.rules.invoiceId[0].required =false
        this.inputStatus = false
        this.form.stoMoney = this.checked[0].stoMoney
        this.form.invoiceType = parseInt(this.radio)
        this.open = true;
        this.title = "报账";
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInvoice(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改【请填写功能名称】";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if (parseInt(this.form.stoMoney)<parseInt(this.form.invoicePrice)) {
        this.form.invoicePrice = this.form.stoMoney
        alert("报账金额不能大于总金额，已自动更改为总金额。")
      }
      if (this.form.invoiceType==1||this.form.invoiceType==3) {
        this.form.invoicePrice = this.form.stoMoney
      }
      this.form.stoId = this.ids[0]
      this.form.stoMoney = this.checked[0].stoMoney
      this.form.invoiceType = parseInt(this.radio)
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInvoice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.form.stoMoney = null;
              this.form.invoicePrice = null;
              this.xj();
              this.getList();
            });
          } else {
            addInvoice(this.form).then(response => {
              this.$modal.msgSuccess("报账成功");
              this.open = false;
              this.form.stoMoney = null;
              this.form.invoicePrice = null;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认取消报账？').then(function() {
        return delInvoice(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("取消成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/invoice/export', {
        ...this.queryParams
      }, `invoice_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
