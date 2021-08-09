<template>
  <div class="dashboard-editor-container">
    <notice :message="message"></notice>
    <el-row :gutter="30" style="margin-top: 15px">
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
    <el-row :gutter="30">
      <el-col :xs="24" :sm="24" :lg="24">
        <div class="chart-wrapper">
          <line-chart />
        </div>
      </el-col>
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
import notice from "@/components/rolling-notice";
import RaddarChart from "./dashboard/RaddarChart";
import PieChart from "./dashboard/PieChart";
import BarChart from "./dashboard/BarChart";
import LineChart from "./dashboard/LineChart";
import { bindwx, isbindwx, wxlogin } from "@/api/system/user";
import { listNotice, getNotice } from "@/api/system/notice";
//import { getSchoolCalendars } from "@/api/benyi/schoolcalendar";
//备用进行班级园历显示操作import { getSchoolCalendarsClass } from "@/api/benyi/schoolcalendarclass";

export default {
  name: "Index",
  name: "calendar",
  components: {
    notice,
    RaddarChart,
    PieChart,
    BarChart,
    LineChart,
  },
  data() {
    return {
      message: ``,
      code: "",
      calendarData: [],
      value: new Date(),
      // 查询参数
      queryParams: {},
      // 查询参数
      queryParams_note: {
        status: "0",
      },
      // lineChartData: lineChartData.newVisitis,
    };
  },
  created() {
    this.getList();
    this.code = this.$route.query.code;
    //console.log(this.code);
    this.bindWxInfo();
    //this.getSchoolCalendarList();
  },
  methods: {
    /** 查询公告列表 */
    getList() {
      listNotice(this.queryParams_note).then((response) => {
        console.log(response.rows);
        if (response.rows.length > 0) {
          var msg = "";
          response.rows.forEach((element) => {
            msg = msg + element.noticeContent;
          });
          this.message = msg;
        } else {
          this.message = "暂无公告";
        }
      });
    },
    async bindWxInfo() {
      await isbindwx().then((res) => {
        if (this.code != "" && this.code != null && res.msg == "1") {
          //console.log("绑定微信");
          this.$confirm("当前账号要绑定微信登录吗？", "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              return bindwx(this.code);
            })
            .then(() => {
              this.msgSuccess("绑定成功");
            })
            .catch(function () {});
        }
      });
    },
    /** 查询园历列表 */
    // getSchoolCalendarList() {
    //   getSchoolCalendars(this.queryParams).then(response => {
    //     this.calendarData = response.calendarData;
    //   });
    // }
    // handleSetLineChartData(type) {
    //   this.lineChartData = lineChartData[type];
    // },
  },
};
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 20px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .el-card.is-always-shadow {
    box-shadow: none;
    border: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px;
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
  .dashboard-editor-container .chart-wrapper {
    padding: 8px;
  }
}

@media (max-width: 768.98px) {
  .dashboard-editor-container {
    padding: 10px;
  }
}
</style>
