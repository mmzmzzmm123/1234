<template>
  <div>
    <el-drawer
      :title="title"
      :close-on-press-escape="false"
      :visible.sync="visible"
      @closed="handleOnClosed"
      size="63%"
    >
      <div class="order_drawer_wrapper" height="84%">
        <div
          class="header"
          style="float: right; margin-bottom: 20px; margin-right: 60px"
        >
          <span style="font-size：14px;margin-top:-200px"
            >当前页总提成：{{ totalCommission }}元</span
          >
        </div>

        <el-table :data="orderList" v-loading="loading">
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
            label="订单金额"
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
            width="100"
          ></el-table-column>

          <el-table-column
            label="每天金额"
            prop="dayMoney"
            align="center"
            width="100"
          ></el-table-column>
          <el-table-column
            label="当月总额"
            prop="monthOrderTotalAmount"
            align="center"
            width="100"
          ></el-table-column>
          <el-table-column
            label="提成比例"
            prop="commissionRate"
            align="center"
            width="100"
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
                  v-for="(item, index) in scope.row.everyYearMonthServerCommission"
                  :key="index"
                >
                  {{ item }}
                </div>
                <el-button type="text" slot="reference">详情</el-button>
              </el-popover>
            </template>
          </el-table-column>

          
        </el-table>

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
  </div>
</template>
<script>
import { orderDetailDay } from "@/api/custom/commision";

export default {
  name: "OrdercommissDetail",
  components: {},
  data() {
    return {
      visible: false,
      loading: false,
      title: "",
      data: undefined,
      orderList: [],
      queryParam: {},
      total: 0,
      totalCommission: 0,
      serverDayList: ["1月：30", "2月：20"],
    };
  },
  computed: {},
  methods: {
    showDrawer(data) {
      this.data = data;
      if (!this.data) {
        return;
      }
      (this.queryParam = {
        pageNum: 1,
        pageSize: 10,
      }),
        (this.queryParam.userId = this.data.userId);
      this.queryParam.reviewStatus = this.data.reviewStatus;
      this.queryParam.endTime = this.data.endTime;
      this.title = `「${this.data.name}」订单提成列表`;
      this.visible = true;

      this.fetchOrderList();
    },
    fetchOrderList() {
      this.loading = true;
      orderDetailDay(this.queryParam).then((res) => {
        //console.log(res);
        if (res.code == 200) {
          this.orderList = this.dealOrderList(res.list);
          this.total = res.total;
          this.totalCommission = res.totalCommission;
        }
        this.loading = false;
      });
    },
    handleOnClosed() {
      this.data = undefined;
    },
    dealOrderList(orderList) {
      //处理每个月的服务天数
      orderList.forEach((item, index) => {
        let everyYearMonthServerDayArray = [];
        for (let yearMonth in item.everyYearMonthServerDay) {
          everyYearMonthServerDayArray.push(yearMonth.slice(0, 4) +"-" + yearMonth.slice(4) + "：" + item.everyYearMonthServerDay[yearMonth] + "天");
        }
        item.everyYearMonthServerDay = everyYearMonthServerDayArray;
        let everyYearMonthServerCommissionArray = [];
        for (let commissYearMonth in item.everyYearMonthServerCommission) {
          everyYearMonthServerCommissionArray.push(commissYearMonth.slice(0, 4) +"-" +commissYearMonth.slice(4) +"：" + item.everyYearMonthServerCommission[commissYearMonth]);
        }
        item.everyYearMonthServerCommission = everyYearMonthServerCommissionArray;
      });
      return orderList;
    },
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
</style>
