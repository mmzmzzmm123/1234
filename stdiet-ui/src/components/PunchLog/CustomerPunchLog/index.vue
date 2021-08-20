<template>
  <el-drawer
    :title="title"
    :close-on-press-escape="false"
    :visible.sync="visible"
    @closed="handleOnClosed"
    size="45%"
  >
    <div class="punchLog_drawer_wrapper" v-loading="loading">
      <div v-if="data.length">
        <CursorChartView :data="getChartData('weight')" />

        <el-button
          icon="el-icon-view"
          size="mini"
          class="weigth_trend_button"
          :style="`left: ${getTextWidth(title) + 30}px`"
          @click="showPunchLogChart()"
          >体重趋势图</el-button
        >

        <div
          class="chart_zone_style"
          v-for="chart in chartList"
          :key="chart.keyVal"
        >
          <LineChartView
            v-if="chart.keyVal"
            v-bind="chart"
            :data="getChartData(chart.keyVal)"
            @onClick="handleOnChartClick"
          />
        </div>
      </div>
      <div v-else class="empty_style">暂无打卡记录</div>
    </div>
    <!-- 详情 -->
    <PunchLogDetail ref="punchLogDetailRef"></PunchLogDetail>
    <!-- 体重趋势图 -->
    <PunchLogChart ref="punchLogChartRef"></PunchLogChart>
  </el-drawer>
</template>
<script>
import { listWxUserLog } from "@/api/custom/wxUserLog";
import { getCustomerPhysicalSignsByCusId } from "@/api/custom/customer";
import LineChartView from "../LineChartView";
import CursorChartView from "../CursorChartView";
import PunchLogDetail from "@/components/PunchLog/PunchLogDetail";
import PunchLogChart from "@/components/PunchLog/PunchLogChart";

export default {
  name: "punchLog",
  components: {
    LineChartView,
    CursorChartView,
    PunchLogDetail,
    PunchLogChart,
  },
  data() {
    return {
      visible: false,
      title: "",
      loading: false,
      userObj: null,
      sex: 0,
      data: [],
      // prettier-ignore
      chartList: [
        {},
        { label: "体重", keyVal: "weight", unit: "斤", extra: undefined },
        { label: "饮水量", keyVal: "water", unit: "毫升", extra: undefined },
        { label: "入睡时间", keyVal: "sleepTime", unit: undefined, extra: undefined },
        { label: "起床时间", keyVal: "wakeupTime", unit: undefined, extra: undefined },
        { label: "熬夜失眠", keyVal: "insomnia", unit: undefined, extra: undefined },
        { label: "运动锻炼", keyVal: "sport", unit: undefined, extra: undefined },
        { label: "情绪状况", keyVal: "emotion", unit: undefined, extra: undefined },
        { label: "排便情况", keyVal: "defecation", unit: undefined, extra: undefined },
        { label: "按营养餐吃", keyVal: "diet", unit: undefined, extra: undefined },
      ],
    };
  },
  methods: {
    showDrawer(data) {
      if (!data) {
        return;
      }

      this.visible = true;
      this.userObj = data;
      this.title = `「${this.userObj.name}」打卡情况`;

      this.fetchLogDatas();
    },
    handleOnClosed() {
      this.userObj = undefined;
    },
    showPunchLogChart() {
      this.$refs.punchLogChartRef.showDialog(this.userObj);
    },
    getTextWidth(text) {
      const canvas = document.createElement("canvas");
      document.body.appendChild(canvas);
      const ctx = canvas.getContext("2d");
      ctx.font = "16px Arial";
      const { width } = ctx.measureText(text);
      document.body.removeChild(canvas);
      return Math.ceil(width);
    },
    fetchLogDatas() {
      this.loading = true;
      getCustomerPhysicalSignsByCusId(this.userObj.id).then((res) => {
        if (res.data.customerHealthy) {
          this.sex = res.data.customerHealthy.sex;
          this.chartList[0] = this.sex
            ? {
                label: "生理期",
                keyVal: "menstrualPeriod",
                unit: undefined,
                extra: undefined,
              }
            : {};
          this.chartList[1].extra = `初始体重：${res.data.customerHealthy.weight}斤`;
        }
        listWxUserLog({
          customerId: this.userObj.id,
        }).then((res) => {
          if (res.code === 200) {
            this.data = res.rows;
          }
          this.loading = false;
        });
      });
    },
    getChartData(key) {
      return this.data.map((obj) => {
        let value = obj[key];
        value === "Y" && (value = "是");
        value === "N" && (value = "否");
        return {
          id: obj.id,
          name: obj.logTime,
          value,
        };
      });
    },
    handleOnChartClick(id) {
      // console.log(id);
      const tarData = this.data.find((obj) => obj.id === id);
      tarData &&
        this.$refs.punchLogDetailRef.showDialog({
          sex: this.sex,
          ...tarData,
        });
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}

.punchLog_drawer_wrapper {
  overflow: auto;
  height: calc(100vh - 77px);

  .chart_zone_style {
    margin: 16px;
    border-radius: 8px;
    overflow: hidden;
  }

  .empty_style {
    text-align: center;
  }

  .weigth_trend_button {
    position: absolute;
    top: 16px;
  }
}
</style>
