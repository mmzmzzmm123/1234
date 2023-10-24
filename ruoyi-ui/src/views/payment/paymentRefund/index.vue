<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户标识" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单标识" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单明细标识" prop="orderDetailsId">
        <el-input
          v-model="queryParams.orderDetailsId"
          placeholder="请输入订单明细标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退款编号" prop="refundNo">
        <el-input
          v-model="queryParams.refundNo"
          placeholder="请输入退款编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退款路径" prop="refundRoute">
        <el-select v-model="queryParams.refundRoute" placeholder="请选择退款路径" clearable>
          <el-option
            v-for="dict in dict.type.pay_way"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="start">
        <el-select v-model="queryParams.start" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.refund_state"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['payment:paymentRefund:add']"
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
          v-hasPermi="['payment:paymentRefund:edit']"
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
          v-hasPermi="['payment:paymentRefund:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['payment:paymentRefund:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="paymentRefundList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" :show-overflow-tooltip="true" />
      <el-table-column label="用户标识" align="center" prop="userId" :show-overflow-tooltip="true"/>
      <el-table-column label="订单标识" align="center" prop="orderId" :show-overflow-tooltip="true"/>
      <el-table-column label="订单明细标识" align="center" prop="orderDetailsId" :show-overflow-tooltip="true"/>
      <el-table-column label="退款编号" align="center" prop="refundNo" :show-overflow-tooltip="true"/>
      <el-table-column label="退款路径" align="center" prop="refundRoute" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pay_way" :value="scope.row.refundRoute"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="start" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.refund_state" :value="scope.row.start"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['payment:paymentRefund:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['payment:paymentRefund:remove']"
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

    <!-- 添加或修改支付单退款对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户标识" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户标识" />
        </el-form-item>
        <el-form-item label="订单标识" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单标识" />
        </el-form-item>
        <el-form-item label="订单明细标识" prop="orderDetailsId">
          <el-input v-model="form.orderDetailsId" placeholder="请输入订单明细标识" />
        </el-form-item>
        <el-form-item label="退款编号" prop="refundNo">
          <el-input v-model="form.refundNo" placeholder="请输入退款编号" />
        </el-form-item>
        <el-form-item label="退款路径" prop="refundRoute">
          <el-select v-model="form.refundRoute" placeholder="请选择退款路径">
            <el-option
              v-for="dict in dict.type.pay_way"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="start">
          <el-select v-model="form.start" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.refund_state"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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
import { listPaymentRefund, getPaymentRefund, delPaymentRefund, addPaymentRefund, updatePaymentRefund } from "@/api/payment/paymentRefund";

export default {
  name: "PaymentRefund",
  dicts: ['refund_state', 'pay_way'],
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
      // 支付单退款表格数据
      paymentRefundList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        orderId: null,
        orderDetailsId: null,
        refundNo: null,
        refundRoute: null,
        start: null,
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
  },
  methods: {
    /** 查询支付单退款列表 */
    getList() {
      this.loading = true;
      listPaymentRefund(this.queryParams).then(response => {
        this.paymentRefundList = response.rows;
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
        userId: null,
        orderId: null,
        orderDetailsId: null,
        refundNo: null,
        refundRoute: null,
        start: null,
        remark: null,
        createTime: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加支付单退款";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPaymentRefund(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改支付单退款";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePaymentRefund(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPaymentRefund(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除支付单退款编号为"' + ids + '"的数据项？').then(function() {
        return delPaymentRefund(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('payment/paymentRefund/export', {
        ...this.queryParams
      }, `paymentRefund_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
