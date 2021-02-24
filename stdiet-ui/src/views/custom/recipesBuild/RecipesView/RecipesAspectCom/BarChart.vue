<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import echarts from "echarts";
import VueScrollTo from "vue-scrollto";
require("@/utils/echarts/myShine");
import resize from "@/views/dashboard/mixins/resize";
import { createNamespacedHelpers } from "vuex";
const { mapMutations } = createNamespacedHelpers("recipes");
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
      default: "300px",
    },
    data: {
      type: Array,
      default: [],
    },
  },
  data() {
    return {
      chart: null,
      nameDict: {
        pHeat: "蛋白质",
        fHeat: "脂肪",
        cHeat: "碳水",
      },
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
  updated() {
    // console.log("updated");
  },
  methods: {
    ...mapMutations(["setCurrentDay"]),
    initChart() {
      this.chart = echarts.init(this.$el, "myShine");
      this.chart.on("click", this.handleOnClick);
      this.updateChart(this.data.length > 0 ? this.data : {});
    },
    updateChart(source) {
      this.chart.clear();
      this.chart.setOption({
        title: {
          text: "营养统计",
        },
        tooltip: {
          trigger: "axis",
          appendToBody: true,
          formatter: (params) => {
            // console.log(params);
            const [param] = params;
            const { name } = param;
            let totalHeat = 0;
            const tooltips = params.reduce(
              (arr, cur) => {
                const { value, seriesName } = cur;
                const nutriName = this.nameDict[seriesName];
                totalHeat += value[seriesName];
                const heatVal = value[seriesName].toFixed(1);
                const weightVal = value[
                  `${seriesName.substring(0, 1)}Weight`
                ].toFixed(1);
                arr.push(
                  `${cur.marker} ${nutriName}：${heatVal}千卡（${weightVal}克）`
                );
                return arr;
              },
              [name]
            );
            tooltips[0] += ` - 共${totalHeat.toFixed(1)}千卡`;
            return tooltips.join("</br>");
          },
        },
        dataset: {
          dimensions: [
            "name",
            "pWeight",
            "pHeat",
            "fWeight",
            "fHeat",
            "cWeight",
            "cHeat",
          ],
          source,
        },
        grid: {
          top: 40,
          left: 20,
          right: 20,
          bottom: 10,
          containLabel: true,
        },
        xAxis: {
          type: "category",
        },
        yAxis: {
          type: "value",
        },
        series: ["pHeat", "fHeat", "cHeat"].map((dim, idx) => ({
          name: dim,
          type: "bar",
          barWidth: 26,
          stack: "bar",
          encode: {
            y: dim,
            x: 0,
          },
          itemStyle: {
            borderWidth: 2,
            borderColor: "#fff",
          },
        })),
      });
    },
    handleOnClick(params) {
      // console.log(params);
      const { dataIndex } = params;
      this.setCurrentDay({ currentDay: dataIndex });
      VueScrollTo.scrollTo(`#recipes${dataIndex}`, 500, {
        container: "#recipes_content",
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
