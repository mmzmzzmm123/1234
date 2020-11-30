<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";
import { listClass } from "@/api/system/class";

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
      actualData: null,
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
      await listClass(this.queryParams).then((response) => {
        // console.log(response.rows);
        var items = [];
        var childcounts = [];
        var childcheckincounts = [];
        response.rows.forEach((element) => {
          items.push(element.bjmc);
          childcounts.push(element.childcount);
          childcheckincounts.push(element.childcheckincount);
        });
        this.classOptions = items;
        this.expectedData = childcounts;
        this.actualData = childcheckincounts;
        // console.log(this.classOptions);
      });
      this.initChart();
    },
    initChart() {
      this.chart = echarts.init(this.$el, "macarons");
      this.setOptions();
    },
    setOptions() {
      this.chart.setOption({
        title: {
          text: "考勤统计", 
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
          data: ["幼儿总数", "幼儿出勤人数"],
        },
        series: [
          {
            name: "幼儿总数",
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
          {
            name: "幼儿出勤人数",
            smooth: true,
            type: "line",
            itemStyle: {
              normal: {
                color: "#3888fa",
                lineStyle: {
                  color: "#3888fa",
                  width: 2,
                },
                areaStyle: {
                  color: "#f3f8ff",
                },
              },
            },
            data: this.actualData,
            animationDuration: 2800,
            animationEasing: "quadraticOut",
          },
        ],
      });
    },
  },
};
</script>
