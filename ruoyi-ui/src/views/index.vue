<template>
  <div class="dashboard-editor-container">
    <notice :message="message"></notice>
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
      message: `本一智慧平台可用手机微信登录啦！关注“本一智慧平台”公众号，点击左下角菜单“智慧平台”，进入2.0平台入口。首次登录输入账号密码，根据提示绑定微信号码！以后登录，可以使用手机直接微信登录，无需再输入账号密码，方便快捷！`,
      code: "",
      calendarData: [],
      value: new Date(),
      // 查询参数
      queryParams: {},
      // lineChartData: lineChartData.newVisitis,
    };
  },
  created() {
    this.code = this.$route.query.code;
    //console.log(this.code);
    this.bindWxInfo();
    //this.getSchoolCalendarList();
  },
  methods: {
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
  padding: 32px;
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
