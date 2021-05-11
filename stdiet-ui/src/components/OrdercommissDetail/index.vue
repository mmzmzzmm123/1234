<template>
  <div>
    <el-drawer
      :title="title"
      :close-on-press-escape="false"
      :visible.sync="visible"
      @closed="handleOnClosed"
      size="65%"
    >
      <div class="order_drawer_wrapper" >
        <div style="margin-left:40px">
          <el-form
            ref="queryForm"
            :inline="true"
            v-show="true"
            label-width="100px"
          >
            <el-form-item label="服务时间范围" prop="dateScope">
              <el-date-picker
                v-model="serverDateScope"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
              
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="cyan" icon="el-icon-search" size="small" @click="fetchOrderList">搜索</el-button>
              <el-button icon="el-icon-refresh" size="small" @click="resetQueryForm">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div
          class="order_total_data"
        >
        
       <span class="order_total_data_span_right20">
            当前页总服务金额：{{totalServerAmount}}元
          </span>
          <span class="order_total_data_span_right20"
            >当前页总提成：{{ totalCommission }}元</span
          >
          <span class="order_total_data_span_right20"
            >已发放总提成：{{ totalSendCommission }}元</span
          >
          <span class="order_total_data_span"
            >未发放总提成：{{ totalNotSendCommission }}元</span
          >  
        </div>
        <el-row :gutter="10" class="mb8" style="margin-top: 10px;margin-left:10px">
          <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['commisionDay:detail:export']"
          >导出
        </el-button>
      </el-col>
    </el-row>
        <div style="width:100%;height:80%;overflow: auto">
        <el-table :data="orderList" v-loading="loading" stripe>
          <el-table-column
            label="订单成交时间"
            prop="orderTime"
            align="center"
            width="160"
          ></el-table-column>
          <el-table-column
            label="客户姓名"
            prop="name"
            align="center"
            width="100"
          ></el-table-column>
          <el-table-column
            label="服务金额"
            prop="orderAmount"
            align="center"
            width="100"
          ></el-table-column>

          <el-table-column
            label="开始时间"
            prop="serverStartDate"
            align="center"
            width="100"
          ></el-table-column>
          <el-table-column
            label="结束时间"
            prop="serverEndDate"
            align="center"
            width="100"
          ></el-table-column>
          <el-table-column
            label="服务天数"
            prop="serverDay"
            align="center"
            width="100"
          >
            <template slot-scope="scope">
              {{ scope.row.serverDay }}
              <el-popover
                placement="top-start"
                width="200"
                popper-class="autohideinfo_detial"
                trigger="hover"
              >
                <div
                  v-for="(item, index) in scope.row.everyYearMonthServerDay"
                  :key="index"
                >
                  {{ item }}
                </div>
                <el-button type="text" slot="reference">详情</el-button>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column
            label="暂停天数"
            prop="pauseTotalDay"
            align="center"
            width="80"
          ></el-table-column>

          <el-table-column
            label="每天金额"
            prop="dayMoney"
            align="center"
            width="100"
          ></el-table-column>
          <el-table-column
            label="当月成交额"
            prop="monthOrderTotalAmount"
            align="center"
            width="100"
          ></el-table-column>
          <el-table-column
            label="提成比例"
            prop="commissionRate"
            align="center"
            width="80"
          >
            <template slot-scope="scope">
              {{ scope.row.commissionRate + "%" }}
            </template>
          </el-table-column>
          <el-table-column
            label="订单提成"
            prop="orderCommission"
            align="center"
            width="100"
          >
            <template slot-scope="scope">
              {{ scope.row.orderCommission }}
              <el-popover
                placement="top-start"
                width="200"
                popper-class="autohideinfo_detial"
                trigger="hover"
              >
                <div
                  v-for="(item, index) in scope.row
                    .everyYearMonthServerCommission"
                  :key="index"
                >
                  {{ item }}
                </div>
                <el-button type="text" slot="reference">详情</el-button>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="100"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                @click="handleOnDetailClick(scope.row)"
                >订单详情</el-button
              >
            </template>
          </el-table-column> 
        </el-table>
        </div>
        <!--<div style="float: right; margin-right: 40px">
          <span style="font-size：14px;margin-top:-200px;margin-right:20px">
            当前页总服务金额：{{totalServerAmount}}元
          </span>
          <span style="font-size：14px;margin-top:-200px;margin-right:20px"
            >当前页总提成：{{ totalCommission }}元</span
          >
          <span style="font-size：14px;margin-top:-200px;margin-right:20px"
            >已发放总提成：{{ totalSendCommission }}元</span
          >
          <span style="font-size：14px;margin-top:-200px"
            >未发放总提成：{{ totalNotSendCommission }}元</span
          >  
        </div>-->
        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParam.pageNum"
          :limit.sync="queryParam.pageSize"
          @pagination="fetchOrderList"
          :pageSizes="[10, 15, 30, 50, 100]"
        >
        </pagination>
        
      </div>
    </el-drawer>

    <order-detail ref="orderDetailRef" />
  </div>
</template>
<script>
import { orderDetailDay,exportOrderDetailDay } from "@/api/custom/commision";
import OrderDetail from "@/components/OrderDetail";

export default {
  name: "OrdercommissDetail",
  components: {
    "order-detail": OrderDetail,
  },
  data() {
    return {
      visible: false,
      loading: false,
      title: "",
      data: undefined,
      orderList: [],
      queryParam: {},
      total: 0,
      totalServerAmount: 0,
      totalCommission: 0,
      totalSendCommission: 0,
      totalNotSendCommission: 0,
      serverDateScope: null,
    };
  },
  computed: {},
  methods: {
    showDrawer(data) {
      this.data = data;
      this.serverDateScope = null;
      if (!this.data) {
        return;
      }
      this.queryParam = {
        pageNum: 1,
        pageSize: 10,
      },
      this.queryParam.userId = this.data.userId;
      this.queryParam.reviewStatus = this.data.reviewStatus;
      this.queryParam.endTime = this.data.endTime;
      this.title = `「${this.data.name}`;
      if (this.data.yearMonth) {
        this.title += " 截止" + `${this.data.yearMonth}`;
      }
      if (this.queryParam.reviewStatus) {
        this.title +=
          this.queryParam.reviewStatus == "yes" ? " 已审核" : " 未审核";
      }
      this.title += " 订单提成列表」";
      this.visible = true;
      this.fetchOrderList();
    },
    fetchOrderList() {
      this.loading = true;
      this.queryParam.serverScopeStartTime = this.serverDateScope && this.serverDateScope.length > 0 ? this.serverDateScope[0] : null;
      this.queryParam.serverScopeEndTime = this.serverDateScope && this.serverDateScope.length > 0 ? this.serverDateScope[1] : null;
      orderDetailDay(this.queryParam).then((res) => {
        //console.log(res);
        if (res.code == 200) {
          this.orderList = this.dealOrderList(res.list);
          this.total = res.total;
          this.totalServerAmount = res.totalServerAmount;
          this.totalCommission = res.totalCommission;
          this.totalSendCommission = res.totalSendCommission;
          this.totalNotSendCommission = res.totalNotSendCommission;
        }
        this.loading = false;
      });
    },
    handleOnClosed() {
      this.data = undefined;
      this.serverDateScope = null;
    },
    dealOrderList(orderList) {
      //处理每个月的服务天数
      orderList.forEach((item, index) => {
        let everyYearMonthServerDayArray = [];
        for (let yearMonth in item.everyYearMonthServerDay) {
          everyYearMonthServerDayArray.push(
            yearMonth.slice(0, 4) +
              "-" +
              yearMonth.slice(4) +
              "：" +
              item.everyYearMonthServerDay[yearMonth] +
              "天"
          );
        }
        item.everyYearMonthServerDay = everyYearMonthServerDayArray;
        let everyYearMonthServerCommissionArray = [];
        for (let commissYearMonth in item.everyYearMonthServerCommission) {
          everyYearMonthServerCommissionArray.push(
            commissYearMonth.slice(0, 4) +
              "-" +
              commissYearMonth.slice(4) +
              "：" +
              item.everyYearMonthServerCommission[commissYearMonth]
              + (item.everyYearMonthCommissionSendFlag[commissYearMonth] ? '  已发放' : '  未发放')
          );
        }
        item.everyYearMonthServerCommission = everyYearMonthServerCommissionArray;
      });
      return orderList;
    },
    resetQueryForm(){
      this.serverDateScope = null;
      this.fetchOrderList();
    },
    handleOnDetailClick(data) {
      this.$refs.orderDetailRef.showDialog(data.orderId);
    },
    handleExport(){
      this.queryParam.serverScopeStartTime = this.serverDateScope && this.serverDateScope.length > 0 ? this.serverDateScope[0] : null;
      this.queryParam.serverScopeEndTime = this.serverDateScope && this.serverDateScope.length > 0 ? this.serverDateScope[1] : null;
      const queryParams = this.queryParam;
      this.$confirm("是否确认导出所有订单提成比例数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportOrderDetailDay(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    }
  },
};
</script>
<style  lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}

.order_drawer_wrapper {
  height: calc(100vh - 77px);
}

.order_total_data {
  float: right; 
  margin-right: 40px; 
  margin-bottom:15px
}

.order_total_data_span_right20 {
  font-size:16px;
  margin-top:-200px;
  margin-right:20px;
}

.order_total_data_span {
  font-size:16px;
  margin-top:-200px;
}


</style>
