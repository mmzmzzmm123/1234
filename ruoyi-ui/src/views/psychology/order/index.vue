<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="订单编号" prop="orderId">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择订单状态" clearable>
          <el-option
            v-for="dict in orderStatus"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="服务名称" prop="serveName">
        <el-input
          v-model="queryParams.serveName"
          placeholder="请输入服务名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下单时间" prop="dateLimit">
        <el-date-picker
          v-model="timeVal"
          style="width: 280px;"
          size="small"
          value-format="yyyy-MM-dd"
          format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="onchangeTime"
        />
      </el-form-item>
      <el-form-item label="所属咨询师" prop="consultId">
        <el-select v-model="queryParams.consultId" clearable :disabled="consultList.length === 1">
          <el-option
            v-for="item in consultList"
            :key="item.id"
            :label="item.nickName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="支付状态" prop="pay">
        <el-select v-model="queryParams.pay" placeholder="请选择支付状态" clearable>
          <el-option
            v-for="dict in payStatus"
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" style="width: 100%" :data="orderList" @selection-change="handleSelectionChange">
<!--      <el-table-column fixed type="selection" width="55" align="center" />-->
      <el-table-column fixed label="订单编号" width="300" align="center" prop="orderNo" />
      <el-table-column label="服务名称" width="180" align="center" prop="serveName" />
      <el-table-column label="咨询师" width="100" align="center" prop="consultName" />
      <el-table-column label="用户名称" width="100" align="center" prop="nickName" />
      <el-table-column label="用户手机号" width="180" align="center" prop="phone" />
      <el-table-column label="订单总价" width="100" align="center" prop="amount">
        <template slot-scope="scope">
          {{ scope.row.amount.toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column label="订单实付" width="100" align="center" prop="pay">
        <template slot-scope="scope">
          {{ scope.row.pay.toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column label="服务次数" width="130" align="center" prop="num">
        <template slot="header" slot-scope="{ column, $index }">
          <span>服务次数</span>
          <el-tooltip class="item" effect="dark" content="剩余次数/用户购买次数" placement="top-start">
            <i class="el-icon-question"/>
          </el-tooltip>
        </template>
        <template slot-scope="scope">
          {{ scope.row.num }} / {{ scope.row.num + scope.row.buyNum }}
        </template>
      </el-table-column>
      <el-table-column label="下次预约时间" width="180" align="center" prop="orderTime" />
      <el-table-column label="支付状态" width="130" align="center" prop="payStatusName" />
      <el-table-column label="订单状态" width="130" align="center" prop="statusName">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '3'" type="info">{{ scope.row.statusName }}</el-tag>
          <el-tag v-else-if="scope.row.status === '2'" type="success">{{ scope.row.statusName }}</el-tag>
          <el-tag v-else>{{ scope.row.statusName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="下单时间" align="center" prop="createTime" width="180"/>
      <el-table-column fixed="right" label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleDetail(scope.row.id, '1')"
            v-hasPermi="['psychology:order:edit']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            :disabled="scope.row.num === 0 || ['0', '3'].includes(scope.row.status)"
            @click="handleHx(scope.row.id)"
            v-hasPermi="['psychology:order:edit']"
          >核销次数</el-button>
          <el-button
            size="mini"
            type="text"
            :disabled="['0', '3'].includes(scope.row.status)"
            @click="handleRemark(scope.row)"
            v-hasPermi="['psychology:order:remove']"
          >备注</el-button>
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

    <!-- 添加或修改咨询订单对话框 -->
    <el-dialog title="订单详情" :visible.sync="open" width="1000px" append-to-body>
      <order-detail v-if="open" :order="order" :items="items" @handleDetail="handleDetail"/>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="openRm" width="700px" append-to-body>
      <el-form ref="formRm" :model="formRm" :rules="rulesRm" label-width="80px">
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" :rows="4" v-model="formRm.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRm">确 定</el-button>
        <el-button @click="cancelRm">关 闭</el-button>
      </div>
    </el-dialog>

    <times ref="formHx" @hxOk="hxOk" />

<!--    <el-dialog title="订单编辑" :visible.sync="openHx" width="700px" append-to-body>-->
<!--      <times v-if="openHx" :order="order" />-->
<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button type="primary" @click="submitHx">确 定</el-button>-->
<!--        <el-button @click="cancelHx">关 闭</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->
  </div>
</template>

<script>
import { listOrder, getOrder, addOrder, updateOrder, getInfo } from "@/api/psychology/order";
import { getConsultAll } from "@/api/psychology/consult";
import orderDetail from "./detail";
import times from "./times";

export default {
  name: "Order",
  components: { orderDetail, times },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      order: {},
      items: {},
      orderId: '',
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 咨询订单表格数据
      payStatus: this.$constants.payStatus,
      orderStatus: this.$constants.orderStatus,
      timeVal: [],
      orderList: [],
      consultList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openRm: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        nickName: null,
        consultId: null,
        serveName: null,
        dateLimit: null,
        status: null,
        pay: null,
      },
      // 表单参数
      form: {},
      formRm: {},
      rulesRm: {
        remark: [
          { required: true, message: "备注不能为空", trigger: "blur" }
        ],
      }
    };
  },
  async created() {
    await this.getConsultServeRef();
    this.getList();
  },
  methods: {
    onchangeTime (e) {
      this.timeVal = e;
      this.queryParams.dateLimit = e ? this.timeVal.join(',') : ''
      this.queryParams.pageNum = 1
      // this.getList();
    },
    async getConsultServeRef() {
      const res = await getConsultAll()
      this.consultList = res.data
      if (this.consultList.length === 1) {
        this.queryParams.consultId = this.consultList[0].id
      }
    },
    /** 查询咨询订单列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
          orderNo: null,
          nickName: null,
          consultId: this.consultList.length === 1 ? this.consultList[0] : null ,
          serveName: null,
          dateLimit: null,
          status: null,
          pay: null,
      }

      this.timeVal = []
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleDetail(id, type) {
      getOrder(id).then(response => {
        this.open = true;
        this.order = response.data
        const items = []

        if (["1", "2"].includes(this.order.status) ) {
          if (this.order.items && this.order.items.length > 0) {
            this.order.items.forEach((i, idx) => {
              const it = {
                num: idx + 1,
                time: i.day + ' ' + i.timeStart + '-' + i.timeEnd,
                realTime: i.realTime,
                status: i.status,
                updateBy: i.updateBy
              }
              items.push(it)
            })
          }

          if (this.order.num > 0) {
            for (let i = 1; i <= this.order.num; i++) {
              const tt = {
                num: items.length + i,
                time: '用户未预约',
                realTime: '',
                status: '',
                updateBy: ''
              }
              items.push(tt)
            }
          }
        }

        this.items = items
      });

      if (type === '2') {
        this.getList()
      }
    },
    /** 修改按钮操作 */
    handleHx(id) {
      getOrder(id).then(response => {
        if (response.data) {
          this.$refs.formHx.setForm(response.data)
        }
      })
    },
    hxOk() {
      this.getList()
    },
    cancelRm() {
      this.openRm = false
      this.formRm = {}
    },
    submitRm() {
      this.$refs["formRm"].validate(valid => {
        if (valid) {
          if (this.formRm.id != null) {
            updateOrder(this.formRm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.openRm = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleRemark(row) {
      const id = row.id
      getInfo(id).then(response => {
        this.formRm = response.data;
        this.openRm = true;
        this.title = "修改咨询订单";
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('psychology/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style lang="scss" scoped>
.amount {
  ::v-deep {
    .el-form-item__content {
      display: inline-flex;
      align-items: center;
    }
    .el-input__inner {
      width: 150px;
    }
  }
}
</style>
