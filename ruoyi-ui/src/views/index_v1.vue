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
     * 获取本周日期
     */
    getWeekDataList() {
      let weekList = [];
      let date = new Date();
      //判断本日期是否为周日，获取本周一日期
      if (date.getDay() === 0) {
        date.setDate(date.getDate() - 6);
      } else {
        date.setDate(date.getDate() - date.getDay() + 1);
      }
      let myDate = date.getDate();
      let myMonth = date.getMonth() + 1;
      if (date.getDate() < 10) {
        myDate = '0' + myDate;
      }
      if (date.getMonth() + 1 < 10) {
        myMonth = '0' + myMonth;
      }
      weekList.push(new Date(date.getFullYear() + "-" + myMonth + "-" + myDate));
      // 获取周二以后日期
      for (let i = 0; i < 6; i++) {
        date.setDate(date.getDate() + 1);
        myDate = date.getDate();
        myMonth = date.getMonth() + 1;
        if (date.getDate() < 10) {
          myDate = '0' + myDate;
        }
        if (date.getMonth() + 1 < 10) {
          myMonth = '0' + myMonth;
        }
        if (i === 5) {
          weekList.push(new Date(date.getFullYear() + "-" + myMonth + "-" + myDate));
        }
      }
      this.dateRange = weekList;
      return weekList;
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
