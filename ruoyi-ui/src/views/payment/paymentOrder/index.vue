<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单类型" prop="orderType">
        <el-select v-model="queryParams.orderType" placeholder="请选择订单类型" clearable>
          <el-option
            v-for="dict in dict.type.order_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="订单标识" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交易订单号" prop="outTradeNo">
        <el-input
          v-model="queryParams.outTradeNo"
          placeholder="请输入交易订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付订单号" prop="transactionId">
        <el-input
          v-model="queryParams.transactionId"
          placeholder="请输入支付订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付方式" prop="payWay">
        <el-select v-model="queryParams.payWay" placeholder="请选择支付方式" clearable>
          <el-option
            v-for="dict in dict.type.pay_way"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="支付状态" prop="payState">
        <el-select v-model="queryParams.payState" placeholder="请选择支付状态" clearable>
          <el-option
            v-for="dict in dict.type.pay_state"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="订单失效时间">
        <el-date-picker
          v-model="daterangeExpireTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="支付成功时间">
        <el-date-picker
          v-model="daterangeSuccessTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['payment:paymentOrder:add']"
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
          v-hasPermi="['payment:paymentOrder:edit']"
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
          v-hasPermi="['payment:paymentOrder:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['payment:paymentOrder:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="paymentOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" :show-overflow-tooltip="true" />
      <el-table-column label="订单类型" align="center" prop="orderType" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_type" :value="scope.row.orderType"/>
        </template>
      </el-table-column>
      <el-table-column label="订单标识" align="center" prop="orderId" :show-overflow-tooltip="true"/>
      <el-table-column label="交易订单号" align="center" prop="outTradeNo" :show-overflow-tooltip="true"/>
      <el-table-column label="支付订单号" align="center" prop="transactionId" :show-overflow-tooltip="true"/>
      <el-table-column label="商品标题" align="center" prop="subject" :show-overflow-tooltip="true"/>
      <el-table-column label="商品描述" align="center" prop="body" :show-overflow-tooltip="true"/>
      <el-table-column label="支付方式" align="center" prop="payWay" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pay_way" :value="scope.row.payWay"/>
        </template>
      </el-table-column>
      <el-table-column label="支付金额" align="center" prop="amount" :show-overflow-tooltip="true"/>
      <el-table-column label="支付状态" align="center" prop="payState" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pay_state" :value="scope.row.payState"/>
        </template>
      </el-table-column>
      <el-table-column label="用户ip" align="center" prop="userIp" :show-overflow-tooltip="true"/>
      <el-table-column label="订单失效时间" align="center" prop="expireTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expireTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="回调数据" align="center" prop="channelNotifyData" :show-overflow-tooltip="true"/>
      <el-table-column label="支付成功时间" align="center" prop="successTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.successTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['payment:paymentOrder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['payment:paymentOrder:remove']"
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

    <!-- 添加或修改支付订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单类型" prop="orderType">
          <el-select v-model="form.orderType" placeholder="请选择订单类型">
            <el-option
              v-for="dict in dict.type.order_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单标识" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单标识" />
        </el-form-item>
        <el-form-item label="交易订单号" prop="outTradeNo">
          <el-input v-model="form.outTradeNo" placeholder="请输入交易订单号" />
        </el-form-item>
        <el-form-item label="支付订单号" prop="transactionId">
          <el-input v-model="form.transactionId" placeholder="请输入支付订单号" />
        </el-form-item>
        <el-form-item label="商品标题" prop="subject">
          <el-input v-model="form.subject" placeholder="请输入商品标题" />
        </el-form-item>
        <el-form-item label="商品描述" prop="body">
          <el-input v-model="form.body" placeholder="请输入商品描述" />
        </el-form-item>
        <el-form-item label="支付方式" prop="payWay">
          <el-select v-model="form.payWay" placeholder="请选择支付方式">
            <el-option
              v-for="dict in dict.type.pay_way"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="支付金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入支付金额" />
        </el-form-item>
        <el-form-item label="支付状态" prop="payState">
          <el-select v-model="form.payState" placeholder="请选择支付状态">
            <el-option
              v-for="dict in dict.type.pay_state"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户ip" prop="userIp">
          <el-input v-model="form.userIp" placeholder="请输入用户ip" />
        </el-form-item>
        <el-form-item label="订单失效时间" prop="expireTime">
          <el-date-picker clearable
            v-model="form.expireTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择订单失效时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="回调数据" prop="channelNotifyData">
          <el-input v-model="form.channelNotifyData" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="支付成功时间" prop="successTime">
          <el-date-picker clearable
            v-model="form.successTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择支付成功时间">
          </el-date-picker>
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
import { listPaymentOrder, getPaymentOrder, delPaymentOrder, addPaymentOrder, updatePaymentOrder } from "@/api/payment/paymentOrder";

export default {
  name: "PaymentOrder",
  dicts: ['pay_state', 'order_type', 'pay_way'],
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
      // 支付订单表格数据
      paymentOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 支付成功时间时间范围
      daterangeExpireTime: [],
      // 支付成功时间时间范围
      daterangeSuccessTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderType: null,
        orderId: null,
        outTradeNo: null,
        transactionId: null,
        payWay: null,
        payState: null,
        expireTime: null,
        successTime: null,
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
    /** 查询支付订单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeExpireTime && '' != this.daterangeExpireTime) {
        this.queryParams.params["beginExpireTime"] = this.daterangeExpireTime[0];
        this.queryParams.params["endExpireTime"] = this.daterangeExpireTime[1];
      }
      if (null != this.daterangeSuccessTime && '' != this.daterangeSuccessTime) {
        this.queryParams.params["beginSuccessTime"] = this.daterangeSuccessTime[0];
        this.queryParams.params["endSuccessTime"] = this.daterangeSuccessTime[1];
      }
      listPaymentOrder(this.queryParams).then(response => {
        this.paymentOrderList = response.rows;
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
        orderType: null,
        orderId: null,
        outTradeNo: null,
        transactionId: null,
        subject: null,
        body: null,
        payWay: null,
        amount: null,
        payState: null,
        userIp: null,
        expireTime: null,
        channelNotifyData: null,
        successTime: null,
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
      this.daterangeExpireTime = [];
      this.daterangeSuccessTime = [];
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
      this.title = "添加支付订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPaymentOrder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改支付订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePaymentOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPaymentOrder(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除支付订单编号为"' + ids + '"的数据项？').then(function() {
        return delPaymentOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('payment/paymentOrder/export', {
        ...this.queryParams
      }, `paymentOrder_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
