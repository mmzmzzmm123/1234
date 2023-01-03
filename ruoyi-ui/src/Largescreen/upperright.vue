<template>
  <div>
    <h2>手机型号</h2>
    <div ref="phoneChart" class="chart" />
    <div class="panel-footer" />
  </div>
</template>

<script>
import { listPhoneRation } from "@/api/system/phoneRation";
export default {
  name: "AppMain",
  data() {
    return {
      phoneList: [],
    };
  },
  mounted() {
    listPhoneRation().then((res) => {
      this.phoneList = res.rows;
      this.initCharts();
    });
  },
  methods: {
    initCharts() {
      const myChart = this.$echarts.init(this.$refs.phoneChart);
      // 指定配置和数据
      var option = {
        color: ["#2f89cf"],
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        grid: {
          left: "0%",
          top: "10px",
          right: "0%",
          bottom: "4%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            data: this.phoneList.map((item) => {
              return item.mPhoneType;
            }),
            axisTick: {
              alignWithLabel: true,
            },
            axisLabel: {
              textStyle: {
                color: "rgba(255,255,255,.6)",
                fontSize: "12",
              },
            },
            axisLine: {
              show: false,
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            axisLabel: {
              textStyle: {
                color: "rgba(255,255,255,.6)",
                fontSize: "12",
              },
            },
            axisLine: {
              lineStyle: {
                color: "rgba(255,255,255,.1)",
                // width: 1,
                // type: "solid"
              },
            },
            splitLine: {
              lineStyle: {
                color: "rgba(255,255,255,.1)",
              },
            },
          },
        ],
        series: [
          {
            name: "使用人数",
            type: "bar",
            barWidth: "35%",
            data: this.phoneList.map((item) => {
              return item.mPhoneNum;
            }),
            itemStyle: {
              barBorderRadius: 5,
            },
          },
        ],
      };
      myChart.setOption(option);
      window.addEventListener("resize", function () {
        myChart.resize();
      });
    },
  },
};
</script>
