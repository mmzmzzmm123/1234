<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";
import { listDayflowassessmentTeacherAvg } from "@/api/benyi/dayflowassessment";

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
      default: "350px",
    },
    queryParams: {},
    autoResize: {
      type: Boolean,
      default: true,
    },
    // chartData: {
    //   type: Object,
    //   required: true,
    // },
  },
  data() {
    return {
      chart: null,
      classOptions: null,
      expectedData: null,
    };
  },
  watch: {
    // chartData: {
    //   deep: true,
    //   handler(val) {
    //     this.setOptions(val);
    //   },
    // },
  },
  mounted() {
    this.$nextTick(() => {
      this.getClassList();
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
    /** 查询班级信息列表 */
    async getClassList() {
      await listDayflowassessmentTeacherAvg(this.queryParams).then(
        (response) => {
          //console.log(this.queryParams);
          var items = [];
          var childcounts = [];
          response.rows.forEach((element) => {
            items.push(element.pgdxxm);
            childcounts.push(element.bjpjf);
          });
          this.classOptions = items;
          this.expectedData = childcounts;
          // console.log(this.classOptions);
        }
      );
      this.initChart();
    },
    initChart() {
      this.chart = echarts.init(this.$el, "macarons");
      this.setOptions();
    },
    setOptions() {
      this.chart.setOption({
        title: {
          text: "一日流程评估教师平均分",
        },
        xAxis: {
          data: this.classOptions,
          boundaryGap: false,
          axisTick: {
            show: false,
          },
        },
        grid: {
          left: 10,
          right: 10,
          bottom: 20,
          top: 30,
          containLabel: true,
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
          },
          padding: [5, 10],
        },
        yAxis: {
          axisTick: {
            show: false,
          },
        },
        legend: {
          data: this.classOptions,
        },
        series: [
          {
            name: "教师平均分",
            itemStyle: {
              normal: {
                color: "#FF005A",
                lineStyle: {
                  color: "#FF005A",
                  width: 2,
                },
              },
            },
            smooth: true,
            type: "line",
            data: this.expectedData,
            animationDuration: 2800,
            animationEasing: "cubicInOut",
          },
        ],
      });
    },
  },
};
</script>
