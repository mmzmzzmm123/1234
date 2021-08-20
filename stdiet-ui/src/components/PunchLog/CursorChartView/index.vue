<template>
  <div
    class="chart_style_wrapper"
    ref="echart"
    :style="{ height: height, width: width }"
  />
</template>
<script>
import echarts from "echarts";
import _ from "lodash";
require("@/utils/echarts/myShine");
import resize from "@/views/dashboard/mixins/resize";
export default {
  mixins: [resize],
  name: "CursorChartView",
  data() {
    return {
      chart: undefined,
    };
  },
  props: {
    data: {
      type: Array,
      default: [],
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "30px",
    },
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
  },
  unmounted() {},
  methods: {
    initChart() {
      this.chart = echarts.init(this.$refs.echart, "myShine");
      this.chart.on("datazoom", _.debounce(this.handleOnChartDatazoom, 200));
      if (this.data.length > 0) {
        this.updateChart(this.data);
      }
    },
    updateChart(source) {
      const option = {
        yAxis: { show: false },
        grid: {
          top: 0,
          left: 10,
          right: 10,
          bottom: 7,
          containLabel: true,
        },
        xAxis: {
          type: "category",
          inverse: true,
          nameTextStyle: {
            color: "#fff",
          },
          axisLine: {
            show: false,
          },
          axisTick: {
            show: false,
          },
          axisLabel: {
            textStyle: {
              color: "#fff",
            },
            formatter: (val) => val.substr(5).replace("-", "/"),
          },
          data: source.map((obj) => obj.name),
        },
        dataZoom: {
          type: "slider",
          show: true,
          zoomLock: true,
          startValue: 0,
          endValue: 6,
          height: 16,
          bottom: 6,
          textStyle: {
            color: "#fff",
          },
          handleStyle: {
            borderColor: "#fff",
          },
        },
      };
      // console.log({ option });
      this.chart.clear();

      this.chart.setOption(option);
    },
    handleOnChartDatazoom(params) {
      window.postMessage({
        type: "PUNCH_LINE_CHART_MESSAGE_DATAZOOM",
        end: params.end,
        keyVal: "cursor",
        start: params.start,
      });
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
<style lang="scss" scoped>
.chart_style_wrapper {
  background: #4b8aff;
  position: absolute;
  top: 48px;
}
</style>
