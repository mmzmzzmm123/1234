<template>
  <div class="dashboard-editor-container">
    <el-row :gutter="30">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart />
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="32">
      <!-- <el-calendar v-model="value" id="calendar">-->
      <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
      <!--<template slot="dateCell" slot-scope="{date, data}">-->
      <!--自定义内容-->
      <!--<div>
            <div class="calendar-day">{{ data.day.split('-').slice(2).join('-') }}</div>
            <div v-for="(item,index) in calendarData" :key="index">
              <div v-if="(item.years).indexOf(data.day.split('-').slice(0)[0])!=-1">
                <div v-if="(item.months).indexOf(data.day.split('-').slice(1)[0])!=-1">
                  <div v-if="(item.days).indexOf(data.day.split('-').slice(2).join('-'))!=-1">
                    <el-tooltip class="item" effect="dark" :content="item.things" placement="right">
                      <div class="is-selected">{{item.things}}</div>
                    </el-tooltip>
                  </div>
                  <div v-else></div>
                </div>
                <div v-else></div>
              </div>
              <div v-else></div>
            </div>
          </div>
        </template>
      </el-calendar> -->
    </el-row>
  </div>
</template>

<script>
import RaddarChart from "./dashboard/RaddarChart";
import PieChart from "./dashboard/PieChart";
import BarChart from "./dashboard/BarChart";
//import { getSchoolCalendars } from "@/api/benyi/schoolcalendar";
//备用进行班级园历显示操作import { getSchoolCalendarsClass } from "@/api/benyi/schoolcalendarclass";

export default {
  name: "Index",
  name: "calendar",
  components: {
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      calendarData: [],
      value: new Date(),
      // 查询参数
      queryParams: {}
    };
  },
  created() {
    //this.getSchoolCalendarList();
  },
  methods: {
    /** 查询园历列表 */
    // getSchoolCalendarList() {
    //   getSchoolCalendars(this.queryParams).then(response => {
    //     this.calendarData = response.calendarData;
    //   });
    // }
  }
};
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .el-card.is-always-shadow {
    box-shadow: none;
    border: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1366.98px) {
  .chart-wrapper {
    ::v-deep.fc-toolbar h2 {
      font-size: 1.2em;
    }
    ::v-deep.chart {
      overflow: hidden;
      overflow-y: auto;
    }
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
