<template>
  <div>
    <h2>运营商</h2>
    <div ref="perChart" class="chart" />
    <div class="panel-footer" />
  </div>
</template>

<script>
import { listOperatorRation } from "@/api/system/operatorRation";
export default {
  name: "AppMain",
  data() {
    return {
      operatorList: [],
    };
  },
  mounted() {
    listOperatorRation().then((res) => {
      this.operatorList = res.rows;
      this.initCharts();
    });
  },
  methods: {
    initCharts() {
      const myChart = this.$echarts.init(this.$refs.perChart);
      var data = [70, 34, 60, 78, 69];
      var titlename = ["HTML5", "CSS3", "javascript", "VUE", "NODE"];
      var valdata = [702, 350, 610, 793, 664];
      var myColor = ["#1089E7", "#F57474", "#56D0E3", "#F8B448", "#8B78F6"];
      var option = {
        // 图标位置
        grid: {
          top: "10%",
          left: "22%",
          bottom: "10%",
        },
        xAxis: {
          show: false,
        },
        yAxis: [
          {
            show: true,
            data: this.operatorList.map((item) => {
              return item.mOperatorType;
            }),
            inverse: true,
            axisLine: {
              show: false,
            },
            splitLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              color: "#fff",
              rich: {
                lg: {
                  backgroundColor: "#339911",
                  color: "#fff",
                  borderRadius: 15,
                  // padding: 5,
                  align: "center",
                  width: 15,
                  height: 15,
                },
              },
            },
          },
          {
            show: true,
            inverse: true,
            data: this.operatorList.map((item) => {
              return item.mOperatorNum;
            }),
            axisLabel: {
              textStyle: {
                fontSize: 10,
                color: "#fff",
              },
            },
          },
        ],
        series: [
          {
            name: "条",
            type: "bar",
            yAxisIndex: 0,
            data: this.operatorList.map((item) => {
              return Math.round(item.mOperatorNum / 10);
            }),
            barCategoryGap: 50,
            barWidth: 10,
            itemStyle: {
              normal: {
                barBorderRadius: 20,
                color: function (params) {
                  var num = myColor.length;
                  return myColor[params.dataIndex % num];
                },
              },
            },
            label: {
              normal: {
                show: true,
                position: "inside",
                formatter: "{c}%",
              },
            },
          },
          {
            name: "框",
            type: "bar",
            yAxisIndex: 1,
            barCategoryGap: 50,
            data: [100, 100, 100, 100, 100],
            barWidth: 15,
            itemStyle: {
              normal: {
                color: "none",
                borderColor: "#00c1de",
                borderWidth: 3,
                barBorderRadius: 15,
              },
            },
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
