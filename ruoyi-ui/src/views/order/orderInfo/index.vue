<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
      <el-form-item label="订单状态" prop="orderState">
        <el-select v-model="queryParams.orderState" placeholder="请选择订单状态" clearable>
          <el-option
            v-for="dict in dict.type.order_stater"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否续单" prop="ifContinuous">
        <el-select v-model="queryParams.ifContinuous" placeholder="请选择是否续单" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="员工标识" prop="staffUserId">
        <el-input
          v-model="queryParams.staffUserId"
          placeholder="请输入员工标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户标识" prop="customUserId">
        <el-input
          v-model="queryParams.customUserId"
          placeholder="请输入客户标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账号类型" prop="accountServiceProvider">
        <el-select v-model="queryParams.accountServiceProvider" placeholder="请选择账号服务商" clearable>
          <el-option
            v-for="dict in dict.type.account_service_provider"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户账号" prop="customNum">
        <el-input
          v-model="queryParams.customNum"
          placeholder="请输入客户账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="卡券标识" prop="couponId">
        <el-input
          v-model="queryParams.couponId"
          placeholder="请输入卡券标识"
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
      <el-form-item label="自动过期时间" label-width="100px">
        <el-date-picker
          v-model="daterangeAutoExpireTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="自动完成时间" label-width="100px">
        <el-date-picker
          v-model="daterangeAutoFinshTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="接单时间">
        <el-date-picker
          v-model="daterangeOrderReceivingTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="开始服务时间" label-width="100px">
        <el-date-picker
          v-model="daterangeOrderServiceTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="订单完成时间" label-width="100px">
        <el-date-picker
          v-model="daterangeOrderFinshTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="订单取消时间" label-width="100px">
        <el-date-picker
          v-model="daterangeOrderCancelTime"
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
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['order:orderInfo:add']"-->
      <!--        >新增</el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['order:orderInfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['order:orderInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['order:orderInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键" align="center" prop="id" :show-overflow-tooltip="true"/>
      <el-table-column label="订单编号" align="center" prop="orderNo" :show-overflow-tooltip="true"/>
      <el-table-column label="订单类型" align="center" prop="orderType" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_type" :value="scope.row.orderType"/>
        </template>
      </el-table-column>
      <el-table-column label="订单状态" align="center" prop="orderState" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_stater" :value="scope.row.orderState"/>
        </template>
      </el-table-column>
      <el-table-column label="是否续单" align="center" prop="ifContinuous" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.ifContinuous"/>
        </template>
      </el-table-column>
      <el-table-column label="员工标识" align="center" prop="staffUserId" :show-overflow-tooltip="true"/>
      <el-table-column label="员工等级" align="center" prop="staffLevel" :show-overflow-tooltip="true"/>
      <el-table-column label="提成比例" align="center" prop="commissionRatio" :show-overflow-tooltip="true"/>
      <el-table-column label="客户标识" align="center" prop="customUserId" :show-overflow-tooltip="true"/>
      <el-table-column label="账号服务商" align="center" prop="accountServiceProvider" :show-overflow-tooltip="true"
                       width="100px">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.account_service_provider" :value="scope.row.accountServiceProvider"/>
        </template>
      </el-table-column>
      <el-table-column label="客户账号" align="center" prop="customNum" :show-overflow-tooltip="true"/>
      <el-table-column label="订单金额" align="center" prop="amount" :show-overflow-tooltip="true"/>
      <!--      <el-table-column label="卡券标识" align="center" prop="couponId" :show-overflow-tooltip="true"/>-->
      <!--      <el-table-column label="优惠金额" align="center" prop="discountAmount" :show-overflow-tooltip="true"/>-->
      <el-table-column label="支付方式" align="center" prop="payWay" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pay_way" :value="scope.row.payWay"/>
        </template>
      </el-table-column>
      <el-table-column label="支付金额" align="center" prop="payAmount" :show-overflow-tooltip="true"/>
      <el-table-column label="自动过期时间" align="center" prop="autoExpireTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.autoExpireTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="自动完成时间" align="center" prop="autoFinshTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.autoFinshTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="接单时间" align="center" prop="orderReceivingTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderReceivingTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开始服务时间" align="center" prop="orderServiceTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderServiceTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单完成时间" align="center" prop="orderFinshTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderFinshTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单取消时间" align="center" prop="orderCancelTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderCancelTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="取消者" align="center" prop="canceller" :show-overflow-tooltip="true"/>
      <el-table-column label="取消留言" align="center" prop="cancelRemark" :show-overflow-tooltip="true"/>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="200px">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['order:orderInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['order:orderInfo:remove']"
          >删除
          </el-button>
          <el-button
            v-if="scope.row.orderState === '4'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="orderFinish(scope.row)"
            v-hasPermi="['order:orderInfo:edit']"
          >订单完成
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="lookOrderDetails(scope.row.id)"
            v-hasPermi="['order:orderDetails:query']"
          >订单详情
          </el-button>
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

    <!-- 添加或修改订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单编号"/>
        </el-form-item>
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
        <el-form-item label="订单状态" prop="orderState">
          <el-select v-model="form.orderState" placeholder="请选择订单状态">
            <el-option
              v-for="dict in dict.type.order_stater"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否续单" prop="ifContinuous">
          <el-select v-model="form.ifContinuous" placeholder="请选择是否续单">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="员工标识" prop="staffUserId">
          <el-input v-model="form.staffUserId" placeholder="请输入员工标识"/>
        </el-form-item>
        <el-form-item label="员工等级" prop="staffLevel">
          <el-input v-model="form.staffLevel" placeholder="请输入员工等级"/>
        </el-form-item>
        <el-form-item label="提成比例" prop="commissionRatio">
          <el-input v-model="form.commissionRatio" placeholder="请输入提成比例"/>
        </el-form-item>
        <el-form-item label="客户标识" prop="customUserId">
          <el-input v-model="form.customUserId" placeholder="请输入客户标识"/>
        </el-form-item>
        <el-form-item label="账号服务商" prop="accountServiceProvider">
          <el-select v-model="form.accountServiceProvider" placeholder="请选择账号服务商">
            <el-option
              v-for="dict in dict.type.account_service_provider"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户账号" prop="customNum">
          <el-input v-model="form.customNum" placeholder="请输入客户账号"/>
        </el-form-item>
        <el-form-item label="订单金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入订单金额"/>
        </el-form-item>
        <el-form-item label="卡券标识" prop="couponId">
          <el-input v-model="form.couponId" placeholder="请输入卡券标识"/>
        </el-form-item>
        <el-form-item label="优惠金额" prop="discountAmount">
          <el-input v-model="form.discountAmount" placeholder="请输入优惠金额"/>
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
        <el-form-item label="支付金额" prop="payAmount">
          <el-input v-model="form.payAmount" placeholder="请输入支付金额"/>
        </el-form-item>
        <el-form-item label="自动过期时间" prop="autoExpireTime">
          <el-date-picker clearable
                          v-model="form.autoExpireTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择自动过期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="自动完成时间" prop="autoFinshTime">
          <el-date-picker clearable
                          v-model="form.autoFinshTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择自动完成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="接单时间" prop="orderReceivingTime">
          <el-date-picker clearable
                          v-model="form.orderReceivingTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择接单时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="开始服务时间" prop="orderServiceTime">
          <el-date-picker clearable
                          v-model="form.orderServiceTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择开始服务时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单完成时间" prop="orderFinshTime">
          <el-date-picker clearable
                          v-model="form.orderFinshTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择订单完成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单取消时间" prop="orderCancelTime">
          <el-date-picker clearable
                          v-model="form.orderCancelTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择订单取消时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="取消者" prop="canceller">
          <el-input v-model="form.canceller" placeholder="请输入取消者"/>
        </el-form-item>
        <el-form-item label="取消留言" prop="cancelRemark">
          <el-input v-model="form.cancelRemark" placeholder="请输入取消留言"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--  订单详情查看容器  -->
    <el-dialog title="订单详情" :visible.sync="orderDetailsopen" width="500px" append-to-body>
      <el-table :data="orderDetails" >
        <el-table-column label="明细" align="center" prop="detailsTitle" :show-overflow-tooltip="true"/>
        <el-table-column label="数量" align="center" prop="num" :show-overflow-tooltip="true"/>
        <el-table-column label="总价" align="center" prop="totalPrice" :show-overflow-tooltip="true"/>
        <el-table-column label="状态" align="center" prop="state" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.order_stater" :value="scope.row.state"/>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeOrderDetailsOpen">关 闭</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import {
  listOrderInfo,
  getOrderInfo,
  delOrderInfo,
  addOrderInfo,
  updateOrderInfo,
  orderFinish
} from "@/api/order/orderInfo";
import {listOrderDetails} from "@/api/order/orderDetails";

export default {
  name: "OrderInfo",
  dicts: ['order_stater', 'sys_yes_no', 'account_service_provider', 'order_type', 'pay_way'],
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
      // 订单信息表格数据
      orderInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示订单详情弹出框
      orderDetailsopen: false,
      // 备注时间范围
      daterangeAutoExpireTime: [],
      // 备注时间范围
      daterangeAutoFinshTime: [],
      // 备注时间范围
      daterangeOrderReceivingTime: [],
      // 备注时间范围
      daterangeOrderServiceTime: [],
      // 备注时间范围
      daterangeOrderFinshTime: [],
      // 备注时间范围
      daterangeOrderCancelTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        orderType: null,
        orderState: null,
        ifContinuous: null,
        staffUserId: null,
        customUserId: null,
        accountServiceProvider: null,
        customNum: null,
        couponId: null,
        payWay: null,
        autoExpireTime: null,
        autoFinshTime: null,
        orderReceivingTime: null,
        orderServiceTime: null,
        orderFinshTime: null,
        orderCancelTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 订单详情数据
      orderDetails: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询订单信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeAutoExpireTime && '' != this.daterangeAutoExpireTime) {
        this.queryParams.params["beginAutoExpireTime"] = this.daterangeAutoExpireTime[0];
        this.queryParams.params["endAutoExpireTime"] = this.daterangeAutoExpireTime[1];
      }
      if (null != this.daterangeAutoFinshTime && '' != this.daterangeAutoFinshTime) {
        this.queryParams.params["beginAutoFinshTime"] = this.daterangeAutoFinshTime[0];
        this.queryParams.params["endAutoFinshTime"] = this.daterangeAutoFinshTime[1];
      }
      if (null != this.daterangeOrderReceivingTime && '' != this.daterangeOrderReceivingTime) {
        this.queryParams.params["beginOrderReceivingTime"] = this.daterangeOrderReceivingTime[0];
        this.queryParams.params["endOrderReceivingTime"] = this.daterangeOrderReceivingTime[1];
      }
      if (null != this.daterangeOrderServiceTime && '' != this.daterangeOrderServiceTime) {
        this.queryParams.params["beginOrderServiceTime"] = this.daterangeOrderServiceTime[0];
        this.queryParams.params["endOrderServiceTime"] = this.daterangeOrderServiceTime[1];
      }
      if (null != this.daterangeOrderFinshTime && '' != this.daterangeOrderFinshTime) {
        this.queryParams.params["beginOrderFinshTime"] = this.daterangeOrderFinshTime[0];
        this.queryParams.params["endOrderFinshTime"] = this.daterangeOrderFinshTime[1];
      }
      if (null != this.daterangeOrderCancelTime && '' != this.daterangeOrderCancelTime) {
        this.queryParams.params["beginOrderCancelTime"] = this.daterangeOrderCancelTime[0];
        this.queryParams.params["endOrderCancelTime"] = this.daterangeOrderCancelTime[1];
      }
      listOrderInfo(this.queryParams).then(response => {
        this.orderInfoList = response.rows;
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
        orderNo: null,
        orderType: null,
        orderState: null,
        ifContinuous: null,
        staffUserId: null,
        staffLevel: null,
        commissionRatio: null,
        customUserId: null,
        accountServiceProvider: null,
        customNum: null,
        amount: null,
        couponId: null,
        discountAmount: null,
        payWay: null,
        payAmount: null,
        autoExpireTime: null,
        autoFinshTime: null,
        orderReceivingTime: null,
        orderServiceTime: null,
        orderFinshTime: null,
        orderCancelTime: null,
        canceller: null,
        cancelRemark: null,
        remark: null,
        createTime: null,
        updateTime: null
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
      this.daterangeAutoExpireTime = [];
      this.daterangeAutoFinshTime = [];
      this.daterangeOrderReceivingTime = [];
      this.daterangeOrderServiceTime = [];
      this.daterangeOrderFinshTime = [];
      this.daterangeOrderCancelTime = [];
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
      this.title = "添加订单信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrderInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrderInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrderInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除订单信息编号为"' + ids + '"的数据项？').then(function () {
        return delOrderInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('order/orderInfo/export', {
        ...this.queryParams
      }, `orderInfo_${new Date().getTime()}.xlsx`)
    },
    /**
     * 订单完成操作
     * */
    orderFinish(row) {
      this.$modal.loading("正在操作");
      orderFinish(row.id).then(res => {
        this.$modal.notifySuccess("操作完成，等待结算成功");
        this.getList();
      }).finally(res => {
        this.$modal.closeLoading();
      })
    },
    /**
     * 查看订单详情
     * */
    lookOrderDetails(id) {
      this.$modal.loading("正在加载");
      listOrderDetails({orderId: id}).then(res => {
        this.orderDetails = res.rows;
        this.orderDetailsopen = true;
      }).finally(res => {
        this.$modal.closeLoading();
      })
    },
    /**
     * 关闭订单详情容器
     * */
    closeOrderDetailsOpen(){
      this.orderDetailsopen = false;
    }
  }
};
</script>
