<template>
  <div ref="echart" :style="{ height: height, width: width }" />
</template>

<script>
import echarts from "echarts";
require("@/utils/echarts/myShine");
import resize from "@/views/dashboard/mixins/resize";
import { createNamespacedHelpers } from "vuex";
const { mapMutations, mapState } = createNamespacedHelpers("recipes");

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
      default: "200px",
    },
    data: {
      type: Array,
      default: [],
    },
    subTitle: {
      type: String,
      default: "",
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
  computed: {},
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
      this.chart = echarts.init(this.$refs.echart, "myShine");
      if (this.data.length > 0) {
        this.updateChart(this.data);
      }
    },
    backToAll() {
      this.resetCurrentDay({ currentDay: -1 });
    },
    getTooltipArr(params) {
      console.log(params);
      let tooltips;
      const { name, marker, percent, value } = params;
      switch (this.type) {
        case "nutrition":
          const weight = value[1] / (name === "脂肪" ? 9 : 4);
          tooltips = [
            `${marker} ${name}`,
            `摄入量：${weight.toFixed(1)}克`,
            `摄入热量：${value[1].toFixed(1)}千卡`,
            `供能比：${percent}%`,
          ];
          break;
        case "calories":
          tooltips = [
            `${marker} ${name}`,
            `热量：${value[1].toFixed(1)}千卡`,
            `占比：${percent}%`,
          ];
          break;
        case "weight":
          tooltips = [
            `${marker} ${name}`,
            `质量：${value[1].toFixed(1)}克`,
            `占比：${percent}%`,
          ];
          break;
      }
      return tooltips.join("</br>");
    },
    updateChart(source) {
      console.log(source);
      const total = source.reduce((acc, cur) => acc + cur[1], 0);
      this.chart.clear();
      const option = {
        title: {
          text: this.title,
          subtext: this.subTitle,
        },
        legend: {
          left: 100,
          itemWidth: 8,
          itemHeight: 8,
          pageIconSize: 10,
          textStyle: {
            fontSize: 10,
          },
        },
        dataset: {
          source,
        },
        tooltip: {
          trigger: "item",
          appendToBody: true,
          formatter: this.getTooltipArr,
        },
        graphic: [
          {
            type: "group",
            top: 60,
            left: 10,
            silent: true,
            children: [
              {
                type: "text",
                style: {
                  text: this.type === "weight" ? "总质量" : "总热量约",
                  fill: "#606266",
                },
              },
              {
                type: "text",
                top: 18,
                left: 8,
                style: {
                  text: `${total.toFixed(1)}${
                    this.type === "weight" ? "克" : "千卡"
                  }`,
                  font: '14px "Microsoft YaHei", sans-serif',
                },
              },
            ],
          },
          {
            type: "group",
            top: 36,
            right: 10,
            silent: true,
            children: source.map((item, idx) => {
              const data =
                this.type == "nutrition"
                  ? item[1] / (idx === 1 ? 9 : 4)
                  : item[1];
              return {
                type: "text",
                top: idx * 20,
                right: 10,
                style: {
                  text: `${item[0]}：${data.toFixed(1)}${
                    this.type === "calories" ? "千卡" : "克"
                  }`,
                  fill: "#606266",
                },
              };
            }),
          },
        ],
        series: [
          {
            type: "pie",
            radius: [0, 60],
            center: ["50%", "55%"],
            // labelLine: {
            //   length: 5,
            //   length2: 5,
            // },
            label: {
              show: true,
              position: "inside",
              color: "#fff",
              fontSize: 12,
              fontWeight: "bold",
            },
            itemStyle: {
              borderWidth: 1,
              borderColor: "#fff",
            },
          },
        ],
      };
      // console.log(option);
      this.chart.setOption(option);
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
/deep/ :focus {
  outline: 0;
}

.aspect_pie_chart_wrapper {
  width: 100%;
  display: flex;
  position: relative;

  & > div:nth-child(1) {
    // width: 200px
  }

  .small_table {
    .my_cell {
      padding: 2px 0 !important;
    }
  }

  .summary {
    padding: 2px;
    border-bottom: 1px solid #dfe6ec;
    border-left: 1px solid #dfe6ec;
    border-right: 1px solid #dfe6ec;

    & > div {
      padding: 3px;
      text-align: center;
    }
  }

  .see_all {
    position: absolute;
    bottom: 4px;
    left: 24px;
    padding: 0;
  }

  .icon_btns {
    position: absolute;
    right: 0;

    em {
      display: inline-block;
      padding: 4px;
      cursor: pointer;
    }

    .sel_icon {
      color: #1890ff;
    }
  }

  .table_zone {
    margin-top: 26px;
  }
}
</style>
