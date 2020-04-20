<template>
  <div class="dashboard-editor-container">
    <el-row :gutter="32">
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
      <el-calendar v-model="value" id="calendar">
        <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
        <template slot="dateCell" slot-scope="{date, data}">
          <!--自定义内容-->
          <div>
            <div class="calendar-day">{{ data.day.split('-').slice(2).join('-') }}</div>
            <div v-for="item in calendarData">
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
          </div>
        </template>
      </el-calendar>
    </el-row>
  </div>
</template>

<script>
import RaddarChart from "./dashboard/RaddarChart";
import PieChart from "./dashboard/PieChart";
import BarChart from "./dashboard/BarChart";

export default {
  name: "Index",
  name: "calendar",
  components: {
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {calendarData: [
                    { months: ['09', '11'],days: ['15'],things: '看电影' },
                    { months: ['10', '11'], days: ['02'],things: '去公园野炊' },
                    { months: ['11'], days: ['02'],things: '看星星' },
                    { months: ['11'], days: ['02'],things: '看月亮' }   
                ],
      value: new Date()
    };
  },
  methods: {}
};
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
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
