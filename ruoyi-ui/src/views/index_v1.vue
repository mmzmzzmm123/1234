<template>
  <div class="dashboard-editor-container">
    <el-date-picker
      v-model="dateRange"
      type="daterange"
      range-separator="至"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      @change="dateRangeChange">
    </el-date-picker>
    <!--  订单基本情况  -->
    <panel-group :chart-data="dateRangeOrderDate" @handleSetLineChartData="handleSetLineChartData"/>
    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="dateRangeOrderDate" :date-range="dateRange" :show-type="dateRangeOrderShowType"/>
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart/>
        </div>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import {formatDate_date} from '@/utils'
import {
  listOrderInfo
} from "@/api/order/orderInfo";
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      dateRange: null, // 时间范围
      dateRangeOrderDate: [], // 时间范围订单数据
      dateRangeOrderShowType: '0', // 时间段订单展示类型
    }
  },
  created() {
    this.getWeekDataList();
    this.loadDateRangeOrderData();
  },
  methods: {
    handleSetLineChartData(type) {
      this.dateRangeOrderShowType = type;
    },
    /**
     * 获取本周一到周日日期
     * */
    getWeekDataList() {
      let today = new Date();
      let currentDay = today.getDay();
      let diff = today.getDate() - currentDay + (currentDay === 0 ? -6 : 1); // Adjust when today is Sunday
      let currentMonday = new Date(today.setDate(diff));
      let currentSunday = new Date(today.setDate(diff + 6));
      this.dateRange = [currentMonday, currentSunday];
      return this.dateRange;
    },
    /**
     * 时间范围改变时间
     * */
    dateRangeChange() {
      let dateRange = this.dateRange;
      if (dateRange == null || dateRange == '' || dateRange.length <= 0) {
        dateRange = this.getWeekDataList();
      }
      this.dateRangeOrderShowType = '0';
      this.loadDateRangeOrderData();
    },
    /**
     * 加载时间范围订单数据
     * */
    loadDateRangeOrderData() {
      // 开始封装数据
      let dateRange = this.dateRange;
      let params = {
        beginCreateTime: formatDate_date(dateRange[0]) + " 00:00:00",
        endCreateTime: formatDate_date(dateRange[1]) + " 23:59:59"
      }
      listOrderInfo({pageNum: 1, pageSize: 10000, params: params}).then(res => {
        this.dateRangeOrderDate = res.rows;
        this.handleDateRangeOrderInfo();
      }).finally(res => {

      })
    },
    /**
     * 处理订单数据
     * */
    handleDateRangeOrderInfo() {
      let dataArr = this.dateRangeOrderDate;
    }
  }
}
</script>

<style lang="scss" scoped>

.dashboard-editor-container {
  padding: 32px;
  background-color: #100B2B;
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
