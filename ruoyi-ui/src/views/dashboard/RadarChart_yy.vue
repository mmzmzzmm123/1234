<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";
import {
  getAssessmentStatisticsmb,
  getAssessmentStatisticsbymb,
} from "@/api/benyi/assessmentcontent";

const animationDuration = 3000;

export default {
  mbvalues: [],
  values: [],
  names: [],
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
    psMsg: {
      type: String,
      default: "",
    }, //接收psMsg值  适用范围
  },
  data() {
    return {
      chart: null,
      childId: "",
    };
  },
  mounted() {
    var childId = this.$route.params && this.$route.params.id;
    this.childId = childId;
    // console.log("child-chart:" + childId);
    // console.log("psMsg:" + this.psMsg);
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
      await getAssessmentStatisticsbymb(this.childId, this.psMsg, 1).then(
        (response) => {
          let value = [];
          let len = response.statistics;
          for (var j = 0; j < len.length; j++) {
            // console.log(len[j]);
            if (len[j] == "NaN") {
              value.push(0);
            } else {
              value.push(len[j]);
            }
          }
          this.values = value;
          //console.log(this.names);
          //console.log(this.values);
        }
      );
    },
    async getNameData() {
      await getAssessmentStatisticsmb(this.childId, this.psMsg, 1).then(
        (response) => {
          let value = [];
          let mbvalues=[];
          let len = response.mb;
          for (var j = 0; j < len.length; j++) {
            // console.log(len[j]);
            if (this.psMsg == "1") {
              len[j].max = 48;
            } else if (this.psMsg == "2") {
              len[j].max = 60;
            } else if (this.psMsg == "3") {
              len[j].max = 72;
            }
            mbvalues.push(len[j].max);
            value.push(len[j]);
          }
          this.mbvalues=mbvalues;
          this.names = value;
          //console.log(this.names);
          //console.log(this.values);
        }
      );
    },
    async initChart() {
      await this.getData();
      await this.getNameData();
      this.chart = echarts.init(this.$el, "macarons");

      if (this.psMsg == "3") {
        this.chart.setOption({
          tooltip: {
            trigger: "axis",
            axisPointer: {
              // 坐标轴指示器，坐标轴触发有效
              type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
            },
          },
          radar: {
            radius: "66%",
            center: ["50%", "42%"],
            splitNumber: 8,
            splitArea: {
              areaStyle: {
                color: "rgba(127,95,132,.3)",
                opacity: 1,
                shadowBlur: 45,
                shadowColor: "rgba(0,0,0,.5)",
                shadowOffsetX: 0,
                shadowOffsetY: 15,
              },
            },
            indicator: this.names,
          },
          legend: {
            left: "center",
            bottom: "10",
            data: ["60-72个月幼儿测评范围值", "幼儿实际测评值"],
          },
          series: [
            {
              type: "radar",
              symbolSize: 0,
              areaStyle: {
                normal: {
                  shadowBlur: 13,
                  shadowColor: "rgba(0,0,0,.2)",
                  shadowOffsetX: 0,
                  shadowOffsetY: 10,
                  opacity: 1,
                },
              },
              data: [
                {
                  value: this.mbvalues,
                  name: "60-72个月幼儿测评范围值",
                },
                {
                  value: this.values,
                  name: "幼儿实际测评值",
                },
              ],
              animationDuration: animationDuration,
            },
          ],
        });
      } else if (this.psMsg == "2") {
        this.chart.setOption({
          tooltip: {
            trigger: "axis",
            axisPointer: {
              // 坐标轴指示器，坐标轴触发有效
              type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
            },
          },
          radar: {
            radius: "66%",
            center: ["50%", "42%"],
            splitNumber: 8,
            splitArea: {
              areaStyle: {
                color: "rgba(127,95,132,.3)",
                opacity: 1,
                shadowBlur: 45,
                shadowColor: "rgba(0,0,0,.5)",
                shadowOffsetX: 0,
                shadowOffsetY: 15,
              },
            },
            indicator: this.names,
          },
          legend: {
            left: "center",
            bottom: "10",
            data: ["48-60个月幼儿测评范围值", "幼儿实际测评值"],
          },
          series: [
            {
              type: "radar",
              symbolSize: 0,
              areaStyle: {
                normal: {
                  shadowBlur: 13,
                  shadowColor: "rgba(0,0,0,.2)",
                  shadowOffsetX: 0,
                  shadowOffsetY: 10,
                  opacity: 1,
                },
              },
              data: [
                {
                  value: this.mbvalues,
                  name: "48-60个月幼儿测评范围值",
                },
                {
                  value: this.values,
                  name: "幼儿实际测评值",
                },
              ],
              animationDuration: animationDuration,
            },
          ],
        });
      } else if (this.psMsg == "1") {
        this.chart.setOption({
          tooltip: {
            trigger: "axis",
            axisPointer: {
              // 坐标轴指示器，坐标轴触发有效
              type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
            },
          },
          radar: {
            radius: "66%",
            center: ["50%", "42%"],
            splitNumber: 8,
            splitArea: {
              areaStyle: {
                color: "rgba(127,95,132,.3)",
                opacity: 1,
                shadowBlur: 45,
                shadowColor: "rgba(0,0,0,.5)",
                shadowOffsetX: 0,
                shadowOffsetY: 15,
              },
            },
            indicator: this.names,
          },
          legend: {
            left: "center",
            bottom: "10",
            data: ["36-48个月幼儿测评范围值", "幼儿实际测评值"],
          },
          series: [
            {
              type: "radar",
              symbolSize: 0,
              areaStyle: {
                normal: {
                  shadowBlur: 13,
                  shadowColor: "rgba(0,0,0,.2)",
                  shadowOffsetX: 0,
                  shadowOffsetY: 10,
                  opacity: 1,
                },
              },
              data: [
                {
                  value: this.mbvalues,
                  name: "36-48个月幼儿测评范围值",
                },
                {
                  value: this.values,
                  name: "幼儿实际测评值",
                },
              ],
              animationDuration: animationDuration,
            },
          ],
        });
      }
    },
  },
};
</script>
