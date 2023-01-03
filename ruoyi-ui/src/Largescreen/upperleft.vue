<template>
  <div>
    <h2>各个时间段人数</h2>
    <div ref="barChart" class="chart" />
    <div class="panel-footer" />
  </div>
</template>

<script>
import { listEnterTimeRation } from "@/api/system/enterTimeRation";
export default {
  name: "AppMain",
  data() {
    return {
      enterTimelist: [],
    };
  },
  mounted() {
    listEnterTimeRation().then((res) => {
      this.enterTimelist = res.rows;
      this.initCharts();
    });
  },
  methods: {
    initCharts() {
      const myChart = this.$echarts.init(this.$refs.barChart);
      // 指定配置和数据
      var option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            lineStyle: {
              color: "#dddc6b",
            },
          },
        },
        legend: {
          top: "0%",
          textStyle: {
            color: "rgba(255,255,255,.5)",
            fontSize: "12",
          },
        },
        grid: {
          left: "10",
          top: "30",
          right: "10",
          bottom: "10",
          containLabel: true,
        },

        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            axisLabel: {
              textStyle: {
                color: "rgba(255,255,255,.6)",
                fontSize: 12,
              },
            },
            axisLine: {
              lineStyle: {
                color: "rgba(255,255,255,.2)",
              },
            },

            data: this.enterTimelist.map((item) => {
              return item.mTimeQuantum.match(/(\S*)-/)[1] + "时";
            }),
          },
          {
            axisPointer: { show: false },
            axisLine: { show: false },
            position: "bottom",
            offset: 20,
          },
        ],

        yAxis: [
          {
            type: "value",
            axisTick: { show: false },
            axisLine: {
              lineStyle: {
                color: "rgba(255,255,255,.1)",
              },
            },
            axisLabel: {
              textStyle: {
                color: "rgba(255,255,255,.6)",
                fontSize: 12,
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
            name: "人数",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                color: "#00d887",
                width: 2,
              },
            },
            areaStyle: {
              normal: {
                color: this.$echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: "rgba(0, 216, 135, 0.4)",
                    },
                    {
                      offset: 0.8,
                      color: "rgba(0, 216, 135, 0.1)",
                    },
                  ],
                  false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)",
              },
            },
            itemStyle: {
              normal: {
                color: "#00d887",
                borderColor: "rgba(221, 220, 107, .1)",
                borderWidth: 12,
              },
            },
            data: this.enterTimelist.map((item) => {
              return item.mTimeQuantuNum;
            }),
          },
        ],
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
      window.addEventListener("resize", function () {
        myChart.resize();
      });
    },
  },
};
</script>
