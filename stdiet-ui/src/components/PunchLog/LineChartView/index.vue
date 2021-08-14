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
import { getOption } from "./chartUtils";
export default {
  mixins: [resize],
  name: "LineChartView",
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
      default: "200px",
    },
    label: {
      type: String,
      default: "",
    },
    keyVal: {
      type: String,
      default: "",
    },
    unit: {
      type: String,
      default: "",
    },
    extra: {
      type: String,
      default: "",
    },
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
    window.addEventListener("message", this.handleOnMessage);
  },
  unmounted() {
    window.removeEventListener("message", this.handleOnMessage);
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$refs.echart, "myShine");
      this.chart.on("mouseover", this.handleOnChartMouseover);
      this.chart.on("mouseout", this.handleOnChartMouseout);
      this.chart.on("click", this.handleOnChartClick);
      if (this.data.length > 0) {
        this.updateChart(this.data);
      }
    },
    updateChart(source) {
      const option = getOption(this.keyVal, source, this.label, this.unit, this.extra);
      // console.log({ option });
      this.chart.clear();
      this.chart.setOption(option);
    },
    handleOnChartClick(params) {
      // console.log({ params });
      this.$emit("onClick", params.data.id);
    },
    handleOnChartMouseover(params) {
      window.postMessage({
        type: "PUNCH_LINE_CHART_MESSAGE_MOUSEOVER",
        keyVal: this.keyVal,
        seriesIndex: params.seriesIndex,
        dataIndex: params.dataIndex,
      });
    },
    handleOnChartMouseout(params) {
      window.postMessage({
        type: "PUNCH_LINE_CHART_MESSAGE_MOUSEOUT",
        keyVal: this.keyVal,
        seriesIndex: params.seriesIndex,
        dataIndex: params.dataIndex,
      });
    },
    handleOnMessage({ data = {} }) {
      if (data.keyVal === this.keyVal) {
        return;
      }
      if (
        data.type &&
        (data.type === "PUNCH_LINE_CHART_MESSAGE_MOUSEOVER" ||
          data.type === "PUNCH_LINE_CHART_MESSAGE_MOUSEOUT")
      ) {
        this.chart &&
          this.chart.dispatchAction({
            type:
              data.type === "PUNCH_LINE_CHART_MESSAGE_MOUSEOVER"
                ? "showTip"
                : "hideTip",
            seriesIndex: data.seriesIndex,
            dataIndex: data.dataIndex,
          });
      } else if (
        data.type &&
        data.type === "PUNCH_LINE_CHART_MESSAGE_DATAZOOM"
      ) {
        this.chart &&
          this.chart.dispatchAction({
            type: "dataZoom",
            end: data.end,
            start: data.start,
          });
      }
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
}
</style>
