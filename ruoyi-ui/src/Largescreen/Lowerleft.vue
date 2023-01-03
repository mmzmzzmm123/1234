<template>
  <div>
    <h2>手机系统比例</h2>
    <div ref="perChart" class="chart" />
    <div class="panel-footer" />
  </div>
</template>

<script>
import { listIosRation } from "@/api/system/iosRation";
export default {
  name: "AppMain",
  data() {
    return {
      operatorList: [],
    };
  },
  mounted() {
    listIosRation().then((res) => {
      this.operatorList = res.rows;
      this.initCharts();
    });
  },
  methods: {
    initCharts() {
      const myChart = this.$echarts.init(this.$refs.perChart);
      // 指定配置和数据
      var option = {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)",
          position: function (p) {
            // 其中p为当前鼠标的位置
            return [p[0] + 10, p[1] - 10];
          },
        },
        legend: {
          top: "90%",
          itemWidth: 10,
          itemHeight: 10,
          data: this.operatorList.map((item) => {
            return item.mIosType;
          }),
          textStyle: {
            color: "rgba(255,255,255,.5)",
            fontSize: "12",
          },
        },
        series: [
          {
            name: "占比",
            type: "pie",
            center: ["50%", "42%"],
            radius: ["40%", "60%"],
            color: [
              "#065aab",
              "#066eab",
              "#0682ab",
              "#0696ab",
              "#06a0ab",
              "#06b4ab",
              "#06c8ab",
              "#06dcab",
              "#06f0ab",
            ],
            label: { show: false },
            labelLine: { show: false },
            data: this.operatorList.map((item) => {
              return {
                value: item.mIosNum,
                name: item.mIosType,
              };
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
