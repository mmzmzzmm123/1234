<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "../mixins/resize";

const animationDuration = 6000;

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "254px",
    },
    data: {
      type: Array,
      default: [{name: 'test', heng: 100, zong: 100, xiao: 100}],
    },
  },
  data() {
    return {
      chart: null,
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
    initChart() {
      this.chart = echarts.init(this.$el, "macarons");

      this.chart.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        grid: {
          top: 10,
          left: "2%",
          right: "2%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            data: this.data.map(v => v.name),
            axisTick: {
              alignWithLabel: true,
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            axisTick: {
              show: false,
            },
          },
        ],
        series: [
          {
            name: "横向",
            type: "bar",
            stack: "vistors",
            barWidth: "60%",
            data: this.data.map(v => v.heng),
            animationDuration,
          },
          {
            name: "纵向",
            type: "bar",
            stack: "vistors",
            barWidth: "60%",
            data: this.data.map(v => v.zong),
            animationDuration,
          },
          {
            name: "pageC",
            type: "bar",
            stack: "vistors",
            barWidth: "60%",
            data: this.data.map(v => v.xiao),
            animationDuration,
          },
        ],
      });
    },
  },
};
</script>
