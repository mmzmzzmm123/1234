<template>
  <div>
    <h2>饼形图-地区分布</h2>
    <div class="chart" />
    <div class="panel-footer" />
  </div>
</template>

<script>
export default {
  name: "AppMain",
  data() {
    return {
      dataAll: [
        { year: "2019", data: [200, 300, 300, 900, 1500, 1200, 600] },
        { year: "2020", data: [300, 400, 350, 800, 1800, 1400, 700] },
      ],
    };
  },
  mounted() {},
  methods: {
    initCharts(index) {
      var list = this.dataAll[index].data;
      const myChart = this.$echarts.init(this.$refs.barChart);
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
            data: [
              "旅游行业",
              "教育培训",
              "游戏行业",
              "医疗行业",
              "电商行业",
              "社交行业",
              "金融行业",
            ],
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
            name: "直接访问",
            type: "bar",
            barWidth: "35%",
            data: list,
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
