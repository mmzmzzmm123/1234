<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";
import { listTeacherGroupXw } from "@/api/system/teacher";

export default {
  names: [],
  values: [],
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart"
    },
    width: {
      type: String,
      default: "100%"
    },
    height: {
      type: String,
      default: "300px"
    }
  },
  data() {
    return {
      chart: null
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    async getData() {
      await listTeacherGroupXw().then(response => {
        //console.log(response);
        let name = [];
        let value = [];
        let len = response.rows;
        for (var j = 0; j < len.length; j++) {
          name.push(len[j].xl);
          value.push({'name':len[j].xl,'value':len[j].byyx});
        }
        this.names = name;
        this.values = value;
        //console.log(this.names);
        //console.log(this.values);
      });
    },
    async initChart() {
      await this.getData();
      this.chart = echarts.init(this.$el, "macarons");

      this.chart.setOption({
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          left: "center",
          bottom: "10",
          data: this.names
        },
        series: [
          {
            name: "幼儿园教职工学历统计",
            type: "pie",
            roseType: "radius",
            radius: [15, 95],
            center: ["50%", "38%"],
            data: this.values,
            animationEasing: "cubicInOut",
            animationDuration: 2600
          }
        ]
      });
    }
  }
};
</script>
