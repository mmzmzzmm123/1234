<template>
  <div class="app-container">
    <el-row>
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="70px"
      >
        <el-col :span="6">
          <el-form-item label="客户信息" prop="customer">
            <el-input
              v-model="queryParams.customer"
              placeholder="请输入客户姓名或手机号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="收款方式" prop="payTypeId">
            <el-select
              v-model="queryParams.payTypeId"
              placeholder="请选择收款方式"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in payTypeIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="收款账号" prop="accountId">
            <el-select
              v-model="queryParams.accountId"
              placeholder="请选择账号"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in accountIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="审核状态" prop="reviewStatus">
            <el-select
              v-model="queryParams.reviewStatus"
              placeholder="请选择审核状态"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in reviewStatusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="销售" prop="preSaleId">
            <el-select
              v-model="queryParams.preSaleId"
              placeholder="请选择售前"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in preSaleIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="售后" prop="afterSaleId">
            <el-select
              v-model="queryParams.afterSaleId"
              placeholder="请选择售后"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in afterSaleIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="主营养师" prop="nutritionistId">
            <el-select
              v-model="queryParams.nutritionistId"
              placeholder="请选择主营养师"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in nutritionistIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            label="营养师助理"
            prop="nutriAssisId"
            label-width="90px"
          >
            <el-select
              v-model="queryParams.nutriAssisId"
              placeholder="请选择营养师助理"
              clearable
              filterable
              style="width: 170px"
              size="small"
            >
              <el-option
                v-for="dict in nutriAssisIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="策划" prop="plannerId">
            <el-select
              v-model="queryParams.plannerId"
              placeholder="请选择策划"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in plannerIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="策划助理" prop="plannerAssisId">
            <el-select
              v-model="queryParams.plannerAssisId"
              placeholder="请选择策划助理"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in plannerAssisIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="运营" prop="operatorId">
            <el-select
              v-model="queryParams.operatorId"
              placeholder="请选择运营"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in operatorIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="运营助理" prop="operatorAssisId">
            <el-select
              v-model="queryParams.operatorAssisId"
              placeholder="请选择运营助理"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in operatorAssisIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="服务时长" prop="serveTimeId">
            <el-select
              v-model="queryParams.serveTimeId"
              placeholder="请选服务时长"
            >
              <el-option
                v-for="dict in serveTimeIdOption"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="订单金额" prop="amountFlag">
            <el-select
              v-model="queryParams.amountFlag"
              placeholder="请选金额状态"
            >
              <el-option :key="0" label="全部订单" :value="null" />
              <el-option :key="1" label="正常订单" :value="0" />
              <el-option :key="2" label="退款订单" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="成交日期" prop="orderTime">
            <el-date-picker
              v-model="daterange"
              type="daterange"
              size="small"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>

        <el-col>
          <el-form-item>
            <el-button
              type="cyan"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索
            </el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">
              重置
            </el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['custom:order:add']"
          >新增
        </el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:order:export']"
          >导出
        </el-button>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="orderList"
      :row-class-name="tableRowClassName"
    >
      <el-table-column label="审核状态" align="center" prop="reviewStatus">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.reviewStatus === 'yes' ? 'success' : 'danger'"
            disable-transitions
          >
            {{ scope.row.reviewStatus === "yes" ? "已审核" : "未审核" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="成交时间"
        align="center"
        prop="orderTime"
        width="160"
      >
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.orderTime, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="客户姓名" align="center" prop="customer" />
      <el-table-column label="手机号" align="center" prop="phone" width="100" />
      <el-table-column label="成交金额" align="center" prop="amount">
        <template slot-scope="scope">
          {{ toThousands(scope.row.amount) }}
        </template>
      </el-table-column>
      <el-table-column
        label="收款账号"
        align="center"
        prop="account"
        width="90"
      />
      <el-table-column label="服务时长" align="center" prop="serveTime" />
      <el-table-column label="销售" align="center" prop="preSale" />
      <el-table-column label="售后" align="center" prop="afterSale" />
      <el-table-column label="主营养师" align="center" prop="nutritionist" />
      <el-table-column label="营养师助理" align="center" prop="nutriAssis" />
      <el-table-column label="策划" align="center" prop="planner" />
      <el-table-column label="策划助理" align="center" prop="plannerAssis" />
      <el-table-column label="运营" align="center" prop="operator" />
      <el-table-column label="运营助理" align="center" prop="operatorAssis" />
      <el-table-column label="备注" align="center" prop="remark">
        <template slot-scope="scope">
          <auto-hide-message
            :data="scope.row.remark == null ? '' : scope.row.remark + ''"
            :maxLength="20"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="160px"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="handleDetail(scope.row)"
            >详情</el-button
          >
          <el-button
            v-if="
              checkPermi(['custom:order:review']) ||
              (checkPermi(['custom:order:edit']) &&
                userId === scope.row.preSaleId &&
                scope.row.reviewStatus === 'no')
            "
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-data"
            @click="orderPauseManage(scope.row)"
            v-hasPermi="['orderPause:pause:query']"
            >暂停记录管理
          </el-button>
          <el-button
            size="mini"
            v-if="
              checkPermi(['custom:order:review']) ||
              (checkPermi(['custom:order:remove']) &&
                userId === scope.row.preSaleId &&
                scope.row.reviewStatus === 'no')
            "
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      layout="total, slot, sizes, prev, pager, next, jumper"
      @pagination="getList"
    >
      <span style="margin-right: 12px"
        >总计：{{ toThousands(this.totalAmount) }} 元</span
      >
    </pagination>

    <!-- 暂停记录管理 -->
    <el-dialog
      :title="pauseTitle"
      v-if="openPause"
      :visible.sync="openPause"
      width="900px"
      append-to-body
    >
      <span style="color: #e6a23c; font-family: PingFang SC">
        注意事项：
        <br />1、日期包含当天，如：2021-01-01到2021-01-07，总共暂停七天，2021-01-08继续服务
        <br />2、每条暂停记录的时间范围不能重叠</span
      >
      <orderPause v-bind:orderPauseId="orderPauseId"></orderPause>
    </el-dialog>

    <!-- 订单详情 -->
    <order-detail ref="orderDetailRef" />
    <!-- 订单修改 -->
    <order-edit ref="orderEditRef" />
  </div>
</template>

<script>
import { delOrder, exportOrder, listOrder } from "@/api/custom/order";
import dayjs from "dayjs";
import orderPause from "./orderPause";

import OrderDetail from "@/components/OrderDetail";
import OrderEdit from "@/components/OrderEdit";
import AutoHideMessage from "@/components/AutoHideMessage";
import { mapGetters } from "vuex";
import { checkPermi } from "@/utils/permission";

const beginTime = dayjs().startOf("month").format("YYYY-MM-DD");
const endTime = dayjs().format("YYYY-MM-DD");
// const beginTime = dayjs().format('YYYY-MM-DD');
// const endTime = dayjs().format('YYYY-MM-DD');
export default {
  name: "Order",
  components: {
    orderPause,
    "auto-hide-message": AutoHideMessage,
    "order-detail": OrderDetail,
    "order-edit": OrderEdit,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 销售订单表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示暂停记录弹窗
      openPause: false,
      pauseTitle: "暂停记录",
      orderPauseId: null,
      //
      totalAmount: 0,
      //
      daterange: [beginTime, endTime],
      // 收款方式字典
      payTypeIdOptions: [],
      // 账号
      accountIdOptions: [],
      // 服务时长
      serveTimeIdOption: [],
      //调理项目
      conditioningProjectIdOption: [],
      // 审核状态
      reviewStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        customer: null,
        phone: null,
        payTypeId: null,
        preSaleId: null,
        afterSaleId: null,
        nutritionistId: null,
        nutriAssisId: null,
        accountId: null,
        plannerId: null,
        plannerAssisId: null,
        operatorId: null,
        operatorAssisId: null,
        recommender: null,
        reviewStatus: null,
        serveTimeId: null,
      },
    };
  },
  computed: {
    ...mapGetters([
      // 售前字典
      "preSaleIdOptions",
      // 售后字典
      "afterSaleIdOptions",
      // 主营养师字典
      "nutritionistIdOptions",
      // 助理营养师字典
      "nutriAssisIdOptions",
      // 策划字典
      "plannerIdOptions",
      // 策划助理字典
      "plannerAssisIdOptions",
      // 运营字典
      "operatorIdOptions",
      // 运营助理字典
      "operatorAssisIdOptions",
      //
      "userId",
    ]),
  },
  created() {
    this.getList();
    this.getDicts("cus_pay_type").then((response) => {
      this.payTypeIdOptions = response.data;
    });
    this.getDicts("cus_account").then((response) => {
      this.accountIdOptions = response.data;
    });
    this.getDicts("cus_serve_time").then((response) => {
      this.serveTimeIdOption = response.data;
    });
    this.getDicts("conditioning_project").then((response) => {
      this.conditioningProjectIdOption = response.data;
    });
    this.getDicts("cus_review_status").then((response) => {
      this.reviewStatusOptions = response.data;
    });
  },
  methods: {
    checkPermi,
    /** 查询销售订单列表 */
    getList() {
      this.loading = true;
      listOrder(this.addDateRange(this.queryParams, this.daterange)).then(
        (response) => {
          this.orderList = response.rows;
          this.total = response.total;
          this.loading = false;
          this.totalAmount = response.totalAmount;
        }
      );
    }, // 收款方式字典翻译
    payTypeIdFormat(row, column) {
      return this.selectDictLabel(this.payTypeIdOptions, row.payTypeId);
    },
    // 售前字典翻译
    preSaleIdFormat(row, column) {
      return this.selectDictLabel(this.preSaleIdOptions, row.preSaleId);
    },
    // 售后字典翻译
    afterSaleIdFormat(row, column) {
      return this.selectDictLabel(this.afterSaleIdOptions, row.afterSaleId);
    },
    // 主营养师字典翻译
    nutritionistIdFormat(row, column) {
      return this.selectDictLabel(
        this.nutritionistIdOptions,
        row.nutritionistId
      );
    },
    // 助理营养师字典翻译
    nutriAssisIdFormat(row, column) {
      return this.selectDictLabel(this.nutriAssisIdOptions, row.nutriAssisId);
    },
    // 策划字典翻译
    plannerIdFormat(row, column) {
      return this.selectDictLabel(this.plannerIdOptions, row.plannerId);
    },
    // 账号字典翻译
    accountIdFormat(row, column) {
      return this.selectDictLabel(this.accountIdOptions, row.accountId);
    },
    // 策划助理字典翻译
    plannerAssisIdFormat(row, column) {
      return this.selectDictLabel(
        this.plannerAssisIdOptions,
        row.plannerAssisId
      );
    },
    // 运营字典翻译
    operatorIdFormat(row, column) {
      return this.selectDictLabel(this.operatorIdOptions, row.operatorId);
    },
    // 运营助理字典翻译
    operatorAssisIdFormat(row, column) {
      return this.selectDictLabel(
        this.operatorAssisIdOptions,
        row.operatorAssisId
      );
    },
    handleDetail(data) {
      this.$refs.orderDetailRef.showDialog(data.orderId);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterange = [beginTime, endTime];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.title = "添加销售订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$refs.orderEditRef.showDialog(row, () => {
        this.getList();
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm(
        '是否确认删除销售订单编号为"' + row.orderId + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delOrder(row.orderId);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有销售订单数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportOrder(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    handleStatusClick(data) {
      // console.log(data);
    },
    orderPauseManage(order) {
      // console.log(order.orderId);
      this.pauseTitle = order.customer;
      this.orderPauseId = order.orderId;
      this.openPause = true;
      //this.$router.push({ name: 'orderPause', params: { 'orderId': order.orderId }})
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2) {
        return "warning-row";
      }
      return "success-row";
    },
  },
  watch: {},
};
</script>

<style lang="scss" scoped>
.s_success {
  color: #1ab394;
  font-size: 22px;
}

.s_pause {
  color: #f56c6c;
  font-size: 22px;
  cursor: pointer;
}

.s_play {
  color: #1c84c6;
  font-size: 22px;
  cursor: pointer;
}

// .warning-row {
//   background: oldlace !important;
// }

// .success-row {
//   background: #f0f9eb !important;
// }
</style>
