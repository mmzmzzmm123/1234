<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="room_charge.id" prop="chargePackId">
        <el-input
          v-model="queryParams.chargePackId"
          placeholder="请输入room_charge.id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="房间id" prop="roomId">
        <el-input
          v-model="queryParams.roomId"
          placeholder="请输入房间id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付人" prop="wxUserId">
        <el-input
          v-model="queryParams.wxUserId"
          placeholder="请输入支付人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="续费时长" prop="chargeMinute">
        <el-input
          v-model="queryParams.chargeMinute"
          placeholder="请输入续费时长"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="原订单结束时间" prop="endTime">
        <el-date-picker clearable
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择原订单结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="金额" prop="totalAmount">
        <el-input
          v-model="queryParams.totalAmount"
          placeholder="请输入金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="现金支付金额" prop="payAmount">
        <el-input
          v-model="queryParams.payAmount"
          placeholder="请输入现金支付金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="福利支付金额" prop="welfareAmount">
        <el-input
          v-model="queryParams.welfareAmount"
          placeholder="请输入福利支付金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="卡券金额" prop="couponAmount">
        <el-input
          v-model="queryParams.couponAmount"
          placeholder="请输入卡券金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="使用卡券" prop="couponId">
        <el-input
          v-model="queryParams.couponId"
          placeholder="请输入使用卡券"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="wx预支付id" prop="prepayId">
        <el-input
          v-model="queryParams.prepayId"
          placeholder="请输入wx预支付id"
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
          v-hasPermi="['office:ordercharge:add']"
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
          v-hasPermi="['office:ordercharge:edit']"
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
          v-hasPermi="['office:ordercharge:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['office:ordercharge:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderchargeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="订单号" align="center" prop="orderNo" />
      <el-table-column label="room_charge.id" align="center" prop="chargePackId" />
      <el-table-column label="房间id" align="center" prop="roomId" />
      <el-table-column label="支付人" align="center" prop="wxUserId" />
      <el-table-column label="续费时长" align="center" prop="chargeMinute" />
      <el-table-column label="原订单结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="支付方式" align="center" prop="payType" />
      <el-table-column label="金额" align="center" prop="totalAmount" />
      <el-table-column label="现金支付金额" align="center" prop="payAmount" />
      <el-table-column label="福利支付金额" align="center" prop="welfareAmount" />
      <el-table-column label="卡券金额" align="center" prop="couponAmount" />
      <el-table-column label="使用卡券" align="center" prop="couponId" />
      <el-table-column label="wx预支付id" align="center" prop="prepayId" />
      <el-table-column label="状态(待支付/已支付/已取消)" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['office:ordercharge:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['office:ordercharge:remove']"
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

    <!-- 添加或修改续费订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="room_charge.id" prop="chargePackId">
          <el-input v-model="form.chargePackId" placeholder="请输入room_charge.id" />
        </el-form-item>
        <el-form-item label="房间id" prop="roomId">
          <el-input v-model="form.roomId" placeholder="请输入房间id" />
        </el-form-item>
        <el-form-item label="支付人" prop="wxUserId">
          <el-input v-model="form.wxUserId" placeholder="请输入支付人" />
        </el-form-item>
        <el-form-item label="续费时长" prop="chargeMinute">
          <el-input v-model="form.chargeMinute" placeholder="请输入续费时长" />
        </el-form-item>
        <el-form-item label="原订单结束时间" prop="endTime">
          <el-date-picker clearable
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择原订单结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="金额" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="现金支付金额" prop="payAmount">
          <el-input v-model="form.payAmount" placeholder="请输入现金支付金额" />
        </el-form-item>
        <el-form-item label="福利支付金额" prop="welfareAmount">
          <el-input v-model="form.welfareAmount" placeholder="请输入福利支付金额" />
        </el-form-item>
        <el-form-item label="卡券金额" prop="couponAmount">
          <el-input v-model="form.couponAmount" placeholder="请输入卡券金额" />
        </el-form-item>
        <el-form-item label="使用卡券" prop="couponId">
          <el-input v-model="form.couponId" placeholder="请输入使用卡券" />
        </el-form-item>
        <el-form-item label="wx预支付id" prop="prepayId">
          <el-input v-model="form.prepayId" placeholder="请输入wx预支付id" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listOrdercharge, getOrdercharge, delOrdercharge, addOrdercharge, updateOrdercharge } from "@/api/office/ordercharge";

export default {
  name: "Ordercharge",
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
      // 续费订单表格数据
      orderchargeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        chargePackId: null,
        roomId: null,
        wxUserId: null,
        chargeMinute: null,
        endTime: null,
        payType: null,
        totalAmount: null,
        payAmount: null,
        welfareAmount: null,
        couponAmount: null,
        couponId: null,
        prepayId: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNo: [
          { required: true, message: "订单号不能为空", trigger: "blur" }
        ],
        roomId: [
          { required: true, message: "房间id不能为空", trigger: "blur" }
        ],
        wxUserId: [
          { required: true, message: "支付人不能为空", trigger: "blur" }
        ],
        chargeMinute: [
          { required: true, message: "续费时长不能为空", trigger: "blur" }
        ],
        endTime: [
          { required: true, message: "原订单结束时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询续费订单列表 */
    getList() {
      this.loading = true;
      listOrdercharge(this.queryParams).then(response => {
        this.orderchargeList = response.rows;
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
        chargePackId: null,
        roomId: null,
        wxUserId: null,
        chargeMinute: null,
        endTime: null,
        payType: null,
        totalAmount: null,
        payAmount: null,
        welfareAmount: null,
        couponAmount: null,
        couponId: null,
        prepayId: null,
        status: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
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
      this.title = "添加续费订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrdercharge(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改续费订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrdercharge(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrdercharge(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除续费订单编号为"' + ids + '"的数据项？').then(function() {
        return delOrdercharge(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('office/ordercharge/export', {
        ...this.queryParams
      }, `ordercharge_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
