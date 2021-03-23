<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import echarts from "echarts";
require("@/utils/echarts/myShine");
import resize from "@/views/dashboard/mixins/resize";
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
      default: "180px",
    },
    data: {
      type: Array,
      default: [],
    },
    max: {
      type: Number,
      default: 0,
    },
    title: {
      type: String,
      default: "",
    },
    type: {
      type: String,
      default: "",
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
  computed: {},
  updated() {
    // console.log("updated");
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, "myShine");
      this.chart.on("click", this.handleOnClick);
      if (this.data.length > 0) {
        this.updateChart(this.data);
      }
    },
    getTooltipArr(params) {
      // console.log(params);
      const [param] = params;
      const { name } = param;
      let tooltips;
      let total = 0;
      switch (this.type) {
        case "nutrition":
          tooltips = params.reduce(
            (arr, cur) => {
              const { value, seriesName, seriesIndex, marker } = cur;
              const calories = value[seriesIndex + 1];
              total += calories;
              const weight = (
                calories / (seriesName === "脂肪" ? 9 : 4)
              ).toFixed(1);
              arr.push(
                `${marker} ${seriesName}：${weight}克（${calories.toFixed(
                  1
                )}千卡）`
              );
              return arr;
            },
            [name]
          );
          tooltips[0] += ` - 约${total.toFixed(1)}千卡`;
          break;
        case "calories":
          tooltips = params.reduce(
            (arr, cur) => {
              const { value, seriesName, seriesIndex, marker } = cur;
              const calories = value[seriesIndex + 1];
              total += calories;
              if (calories) {
                arr.push(`${marker} ${seriesName}：${calories.toFixed(1)}千卡`);
              }
              return arr;
            },
            [name]
          );
          tooltips[0] += ` - 约${total.toFixed(1)}千卡`;
          break;
        case "weight":
          tooltips = params.reduce(
            (arr, cur) => {
              const { value, seriesName, seriesIndex, marker } = cur;
              const weight = value[seriesIndex + 1];
              total += weight;
              if (weight) {
                arr.push(`${marker} ${seriesName}：${weight.toFixed(1)}克`);
              }
              return arr;
            },
            [name]
          );
          tooltips[0] += ` - 共${total.toFixed(1)}克`;
          break;
      }
      return tooltips.join("</br>");
    },
    updateChart(source) {
      // console.log(this.max);
      // console.log(source);
      this.chart.clear();
      const option = {
        title: {
          text: this.title,
        },
        legend: {
          // type: "scroll",
          left: 100,
          itemWidth: 8,
          itemHeight: 8,
          pageIconSize: 10,
          textStyle: {
            fontSize: 10,
          },
        },
        tooltip: {
          trigger: "axis",
          appendToBody: true,
          formatter: this.getTooltipArr,
        },
        dataset: {
          source,
        },
        grid: {
          top: 50,
          left: 10,
          right: 50,
          bottom: 10,
          containLabel: true,
        },
        xAxis: {
          type: "category",
          // axisLabel: {
          //   rotate: 45,
          // },
        },
        yAxis: {
          type: "value",
          name: this.type === "weight" ? "质量/克" : "热量/千卡",
          nameTextStyle: {
            color: "#262626",
            fontSize: 10,
          },
        },
        series: source[0].slice(1).map((name, idx) => ({
          name,
          type: "bar",
          barWidth: 24,
          stack: "bar",
          encode: {
            y: idx + 1,
            x: 0,
          },
          itemStyle: {
            borderWidth: 2,
            borderColor: "#fff",
          },
        })),
      };
      if (this.max) {
        option.series[0].markLine = {
          data: [{ name: "BMR", yAxis: this.max ? this.max - 400 : 0 }],
          symbol: "none",
          lineStyle: {
            color: "#d96969",
          },
        };
      }
      this.chart.setOption(option);
    },
    handleOnClick(params) {
      // console.log(params);
      const { dataIndex } = params;
      this.$emit("onClick", dataIndex);
    },
  },
  watch: {
    data(newVal, oldVal) {
      if (newVal) {
        this.$nextTick(() => {
          this.updateChart(newVal);
        });
      }
    },
  },
};
</script>
