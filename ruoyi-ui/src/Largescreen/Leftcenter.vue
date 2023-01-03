<template>
  <div>
    <h2>人数占比</h2>
    <div ref="lineChart" class="chart" />
    <div class="panel-footer" />
  </div>
</template>

<script>
import { listNewpeopleRation } from "@/api/system/newpeopleRation";
export default {
  name: "AppMain",
  data() {
    return {
      peopleList: [],
    };
  },
  mounted() {
    listNewpeopleRation().then((res) => {
      this.peopleList = res.rows;
      this.initCharts();
    });
  },
  methods: {
    initCharts() {
      const myChart = this.$echarts.init(this.$refs.lineChart);
      var option = {
        tooltip: {
          trigger: "axis",
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
          show: true,
          borderColor: "#012f4a",
          containLabel: true,
        },

        xAxis: {
          type: "category",
          boundaryGap: false,
          axisLabel: {
            textStyle: {
              color: "rgba(255,255,255,.6)",
              fontSize: 12,
            },
          },
          axisTick: {
            show: false,
          },
          axisLine: {
            lineStyle: {
              color: "rgba(255,255,255,.2)",
            },
          },

          data: this.peopleList.map((item) => {
            return item.mTimeQuantum.match(/(\S*)-/)[1] + "时";
          }),
        },

        yAxis: {
          type: "value",
          axisTick: { show: false },
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
        series: [
          {
            name: "总人数",
            type: "line",
            smooth: true,
            symbol: "circle",
            // symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                color: "#0184d5",
                width: 2,
              },
            },
            data: this.peopleList.map((item) => {
              return item.mTotalPeople;
            }),
          },
          {
            name: "新增人数",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                color: "#00f2f1",
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
            data: this.peopleList.map((item) => {
              return item.mNewpeople;
            }),
          },
        ],
      };
      // 3. 把配置和数据给实例对象
      myChart.setOption(option);

      // 重新把配置好的新数据给实例对象
      myChart.setOption(option);
      window.addEventListener("resize", function () {
        myChart.resize();
      });
    },
  },
};
</script>
