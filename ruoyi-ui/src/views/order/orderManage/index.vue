<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="订单模式" prop="orderSchema">
        <el-select v-model="queryParams.orderSchema">
          <el-option v-for="item in dicOrderSchema" :key="item.dictCode" :label="item.dictLabel"
            :value="item.dictCode" />
        </el-select>
      </el-form-item>
      <el-form-item label="城市" prop="city">
        <el-input v-model="queryParams.city" placeholder="请输入城市" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="经销商" prop="dealerId">
        <el-input v-model="queryParams.dealerId" placeholder="请输入经销商" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="订单编号" prop="orderNumber">
        <el-input v-model="queryParams.orderNumber" placeholder="请输入订单编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="银行" prop="bank">
        <el-input v-model="queryParams.bank" placeholder="请输入银行" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="内勤" prop="innerOfficer">
        <el-input v-model="queryParams.innerOfficer" placeholder="请输入内勤" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="客户姓名" prop="customerName">
        <el-input v-model="queryParams.customerName" placeholder="请输入客户姓名" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="申请编号" prop="applyNumber">
        <el-input v-model="queryParams.applyNumber" placeholder="请输入申请编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="是否新能源" prop="newEnergy">
        <el-select v-model="queryParams.newEnergy">
          <el-option v-for="item in dicYesNo" :key="item.dictCode" :label="item.dictLabel" :value="item.dictCode" />
        </el-select>
      </el-form-item>
      <el-form-item label="开票价格" prop="invoicePrice">
        <el-input v-model="queryParams.invoicePrice" placeholder="请输入开票价格" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="贷款金额" prop="loanAmount">
        <el-input v-model="queryParams.loanAmount" placeholder="请输入贷款金额" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="首付金额" prop="downPayment">
        <el-input v-model="queryParams.downPayment" placeholder="请输入首付金额" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="贷款比率" prop="loanRatio">
        <el-input v-model="queryParams.loanRatio" placeholder="请输入贷款比率" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="期数" prop="periodNumber">
        <el-select v-model="queryParams.periodNumber">
          <el-option v-for="item in dicPeriodNumber" :key="item.dictCode" :label="item.dictLabel"
            :value="item.dictCode" />
        </el-select>
      </el-form-item>
      <el-form-item label="客户联系方式" prop="customerPhoneNumber">
        <el-input v-model="queryParams.customerPhoneNumber" placeholder="请输入客户联系方式" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="客户工作单位" prop="customerWorkUnion">
        <el-input v-model="queryParams.customerWorkUnion" placeholder="请输入客户工作单位" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="是否贴息" prop="isDiscount">
        <el-select v-model="queryParams.isDiscount">
          <el-option v-for="item in dicYesNo" :key="item.dictCode" :label="item.dictLabel" :value="item.dictCode" />
        </el-select>
      </el-form-item>
      <el-form-item label="总费率" prop="feeRatio">
        <el-input v-model="queryParams.feeRatio" placeholder="请输入总费率" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="是否惠享融" prop="isHxr">
        <el-select v-model="queryParams.isHxr">
          <el-option v-for="item in dicYesNo" :key="item.dictCode" :label="item.dictLabel" :value="item.dictCode" />
        </el-select>
      </el-form-item>
      <el-form-item label="业务编号" prop="businessNumber">
        <el-input v-model="queryParams.businessNumber" placeholder="请输入业务编号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="信用卡号" prop="creditCardNumber">
        <el-input v-model="queryParams.creditCardNumber" placeholder="请输入信用卡号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="客户年龄" prop="customerAge">
        <el-select v-model="queryParams.customerAge">
          <el-option v-for="item in dicAgeStage" :key="item.dictCode" :label="item.dictLabel" :value="item.dictCode" />
        </el-select>
      </el-form-item>
      <el-form-item label="婚姻状况" prop="married">
        <el-select v-model="queryParams.married">
          <el-option v-for="item in dicMarriedStatus" :key="item.dictCode" :label="item.dictLabel" :value="item.dictCode" />
        </el-select>
      </el-form-item>
      <el-form-item label="有无驾照" prop="existsDriveLicense">
        <el-select v-model="queryParams.existsDriveLicense">
          <el-option v-for="item in dicYesNo" :key="item.dictCode" :label="item.dictLabel" :value="item.dictCode" />
        </el-select>
      </el-form-item>
      <el-form-item label="工作所在地" prop="workPlace">
        <el-input v-model="queryParams.workPlace" placeholder="请输入工作所在地" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="行业" prop="workField">
        <el-select v-model="queryParams.workField">
          <el-option v-for="item in dicWorkField" :key="item.dictCode" :label="item.dictLabel" :value="item.dictCode" />
        </el-select>
      </el-form-item>
      <el-form-item label="工作性质" prop="workCategory">
        <el-select v-model="queryParams.workCategory">
          <el-option v-for="item in dicWorkCategory" :key="item.dictCode" :label="item.dictLabel" :value="item.dictCode" />
        </el-select>
      </el-form-item>
      <el-form-item label="学历" prop="education">
        <el-input v-model="queryParams.education" placeholder="请输入学历" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="陪同人员关系" prop="companionRelationship">
        <el-select v-model="queryParams.companionRelationship">
          <el-option v-for="item in dicCompanionRelationship" :key="item.dictCode" :label="item.dictLabel" :value="item.dictCode" />
        </el-select>
      </el-form-item>
      <el-form-item label="陪同人员数量" prop="companionNumber">
        <el-input v-model="queryParams.companionNumber" placeholder="请输入陪同人员数量" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['order:orderManage:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['order:orderManage:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['order:orderManage:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['order:orderManage:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderManageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="订单模式" align="center" prop="orderSchema" />
      <el-table-column label="城市" align="center" prop="city" />
      <el-table-column label="经销商类型" align="center" prop="dealerType" />
      <el-table-column label="经销商" align="center" prop="dealerId" />
      <el-table-column label="是否新车" align="center" prop="carType" />
      <el-table-column label="贷款类型" align="center" prop="loanType" />
      <el-table-column label="订单编号" align="center" prop="orderNumber" />
      <el-table-column label="银行" align="center" prop="bank" />
      <el-table-column label="内勤" align="center" prop="innerOfficer" />
      <el-table-column label="客户姓名" align="center" prop="customerName" />
      <el-table-column label="申请编号" align="center" prop="applyNumber" />
      <el-table-column label="是否新能源" align="center" prop="newEnergy" />
      <el-table-column label="开票价格" align="center" prop="invoicePrice" />
      <el-table-column label="贷款金额" align="center" prop="loanAmount" />
      <el-table-column label="首付金额" align="center" prop="downPayment" />
      <el-table-column label="贷款比率" align="center" prop="loanRatio" />
      <el-table-column label="期数" align="center" prop="periodNumber" />
      <el-table-column label="进件材料类型" align="center" prop="supplyMaterialType" />
      <el-table-column label="客户联系方式" align="center" prop="customerPhoneNumber" />
      <el-table-column label="客户工作单位" align="center" prop="customerWorkUnion" />
      <el-table-column label="是否贴息" align="center" prop="isDiscount" />
      <el-table-column label="总费率" align="center" prop="feeRatio" />
      <el-table-column label="是否惠享荣" align="center" prop="isHxr" />
      <el-table-column label="业务编号" align="center" prop="businessNumber" />
      <el-table-column label="信用卡号" align="center" prop="creditCardNumber" />
      <el-table-column label="客户年龄" align="center" prop="customerAge" />
      <el-table-column label="婚否" align="center" prop="married" />
      <el-table-column label="有无驾照" align="center" prop="existsDriveLicense" />
      <el-table-column label="户籍类型" align="center" prop="domicileType" />
      <el-table-column label="工作所在地" align="center" prop="workPlace" />
      <el-table-column label="行业" align="center" prop="workField" />
      <el-table-column label="工作性质" align="center" prop="workCategory" />
      <el-table-column label="学历" align="center" prop="education" />
      <el-table-column label="陪同人员关系" align="center" prop="companionRelationship" />
      <el-table-column label="陪同人员数量" align="center" prop="companionNumber" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['order:orderManage:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['order:orderManage:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单模式" prop="orderSchema">
          <el-input v-model="form.orderSchema" placeholder="请输入订单模式" />
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-input v-model="form.city" placeholder="请输入城市" />
        </el-form-item>
        <el-form-item label="经销商" prop="dealerId">
          <el-input v-model="form.dealerId" placeholder="请输入经销商" />
        </el-form-item>
        <el-form-item label="订单编号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="银行" prop="bank">
          <el-input v-model="form.bank" placeholder="请输入银行" />
        </el-form-item>
        <el-form-item label="内勤" prop="innerOfficer">
          <el-input v-model="form.innerOfficer" placeholder="请输入内勤" />
        </el-form-item>
        <el-form-item label="客户姓名" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入客户姓名" />
        </el-form-item>
        <el-form-item label="申请编号" prop="applyNumber">
          <el-input v-model="form.applyNumber" placeholder="请输入申请编号" />
        </el-form-item>
        <el-form-item label="是否新能源" prop="newEnergy">
          <el-input v-model="form.newEnergy" placeholder="请输入是否新能源" />
        </el-form-item>
        <el-form-item label="开票价格" prop="invoicePrice">
          <el-input v-model="form.invoicePrice" placeholder="请输入开票价格" />
        </el-form-item>
        <el-form-item label="贷款金额" prop="loanAmount">
          <el-input v-model="form.loanAmount" placeholder="请输入贷款金额" />
        </el-form-item>
        <el-form-item label="首付金额" prop="downPayment">
          <el-input v-model="form.downPayment" placeholder="请输入首付金额" />
        </el-form-item>
        <el-form-item label="贷款比率" prop="loanRatio">
          <el-input v-model="form.loanRatio" placeholder="请输入贷款比率" />
        </el-form-item>
        <el-form-item label="期数" prop="periodNumber">
          <el-input v-model="form.periodNumber" placeholder="请输入期数" />
        </el-form-item>
        <el-form-item label="客户联系方式" prop="customerPhoneNumber">
          <el-input v-model="form.customerPhoneNumber" placeholder="请输入客户联系方式" />
        </el-form-item>
        <el-form-item label="客户工作单位" prop="customerWorkUnion">
          <el-input v-model="form.customerWorkUnion" placeholder="请输入客户工作单位" />
        </el-form-item>
        <el-form-item label="是否贴息" prop="isDiscount">
          <el-input v-model="form.isDiscount" placeholder="请输入是否贴息" />
        </el-form-item>
        <el-form-item label="总费率" prop="feeRatio">
          <el-input v-model="form.feeRatio" placeholder="请输入总费率" />
        </el-form-item>
        <el-form-item label="是否惠享荣" prop="isHxr">
          <el-input v-model="form.isHxr" placeholder="请输入是否惠享荣" />
        </el-form-item>
        <el-form-item label="业务编号" prop="businessNumber">
          <el-input v-model="form.businessNumber" placeholder="请输入业务编号" />
        </el-form-item>
        <el-form-item label="信用卡号" prop="creditCardNumber">
          <el-input v-model="form.creditCardNumber" placeholder="请输入信用卡号" />
        </el-form-item>
        <el-form-item label="客户年龄" prop="customerAge">
          <el-input v-model="form.customerAge" placeholder="请输入客户年龄" />
        </el-form-item>
        <el-form-item label="婚否" prop="married">
          <el-input v-model="form.married" placeholder="请输入婚否" />
        </el-form-item>
        <el-form-item label="有无驾照" prop="existsDriveLicense">
          <el-input v-model="form.existsDriveLicense" placeholder="请输入有无驾照" />
        </el-form-item>
        <el-form-item label="工作所在地" prop="workPlace">
          <el-input v-model="form.workPlace" placeholder="请输入工作所在地" />
        </el-form-item>
        <el-form-item label="行业" prop="workField">
          <el-input v-model="form.workField" placeholder="请输入行业" />
        </el-form-item>
        <el-form-item label="工作性质" prop="workCategory">
          <el-input v-model="form.workCategory" placeholder="请输入工作性质" />
        </el-form-item>
        <el-form-item label="学历" prop="education">
          <el-input v-model="form.education" placeholder="请输入学历" />
        </el-form-item>
        <el-form-item label="陪同人员关系" prop="companionRelationship">
          <el-input v-model="form.companionRelationship" placeholder="请输入陪同人员关系" />
        </el-form-item>
        <el-form-item label="陪同人员数量" prop="companionNumber">
          <el-input v-model="form.companionNumber" placeholder="请输入陪同人员数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOrderManage, getOrderManage, delOrderManage, addOrderManage, updateOrderManage } from "@/api/order/orderManage";
import { getDicts } from "@/api/system/dict/data";
export default {
  name: "OrderManage",
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 订单表格数据
      orderManageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 类型数据字典
      dicOrderSchema: [],
      dicYesNo: [],
      dicPeriodNumber: [],
      dicAgeStage: [],
      dicWorkField: [],
      dicWorkCategory:[],
      dicMarriedStatus:[],
      dicCompanionRelationship:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderSchema: null,
        city: null,
        dealerType: null,
        dealerId: null,
        carType: null,
        loanType: null,
        orderNumber: null,
        bank: null,
        innerOfficer: null,
        customerName: null,
        applyNumber: null,
        newEnergy: null,
        invoicePrice: null,
        loanAmount: null,
        downPayment: null,
        loanRatio: null,
        periodNumber: null,
        supplyMaterialType: null,
        customerPhoneNumber: null,
        customerWorkUnion: null,
        isDiscount: null,
        feeRatio: null,
        isHxr: null,
        businessNumber: null,
        creditCardNumber: null,
        customerAge: null,
        married: null,
        existsDriveLicense: null,
        domicileType: null,
        workPlace: null,
        workField: null,
        workCategory: null,
        education: null,
        companionRelationship: null,
        companionNumber: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("bl_order_schema").then(response => {
      this.dicOrderSchema = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.dicYesNo = response.data;
    });
    this.getDicts("bl_loan_period_size").then(response => {
      this.dicPeriodNumber = response.data;
    });
    this.getDicts("bl_age_stage").then(response => {
      this.dicAgeStage = response.data;
    });
    this.getDicts("bl_work_field").then(response => {
      this.dicWorkField = response.data;
    });
    this.getDicts("bl_work_catetory").then(response => {
      this.dicWorkCategory = response.data;
    });
    this.getDicts("bl_married_status").then(response => {
      this.dicMarriedStatus = response.data;
    });
    this.getDicts("bl_companion_relationship").then(response => {
      this.dicCompanionRelationship = response.data;
    });    
  },
  methods: {
    /** 查询订单列表 */
    getList() {
      this.loading = true;
      listOrderManage(this.queryParams).then(response => {
        this.orderManageList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        id: null,
        orderSchema: null,
        city: null,
        dealerType: null,
        dealerId: null,
        carType: null,
        loanType: null,
        orderNumber: null,
        bank: null,
        innerOfficer: null,
        customerName: null,
        applyNumber: null,
        newEnergy: null,
        invoicePrice: null,
        loanAmount: null,
        downPayment: null,
        loanRatio: null,
        periodNumber: null,
        supplyMaterialType: null,
        customerPhoneNumber: null,
        customerWorkUnion: null,
        isDiscount: null,
        feeRatio: null,
        isHxr: null,
        businessNumber: null,
        creditCardNumber: null,
        customerAge: null,
        married: null,
        existsDriveLicense: null,
        domicileType: null,
        workPlace: null,
        workField: null,
        workCategory: null,
        education: null,
        companionRelationship: null,
        companionNumber: null,
        createTime: null,
        updateTime: null,
        createBy: null,
        updateBy: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrderManage(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrderManage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrderManage(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除订单编号为"' + ids + '"的数据项？').then(function () {
        return delOrderManage(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('order/orderManage/export', {
        ...this.queryParams
      }, `orderManage_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
