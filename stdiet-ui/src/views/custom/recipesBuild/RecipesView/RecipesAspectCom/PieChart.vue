<template>
  <div
    :class="`aspect_pie_chart_wrapper ${className || ''}`"
    :style="{ height: height, width: width }"
  >
    <div ref="echart" :style="{ height: height, width: '200px' }" />
    <div>
      <el-table
        :data="mData"
        size="mini"
        border
        :cell-style="{ padding: '2px 0' }"
        :header-cell-style="{ padding: '4px 0', height: 'unset' }"
        class="small_table"
      >
        <el-table-column label="营养" prop="type" align="center" width="60" />
        <el-table-column
          label="重量(g)"
          prop="weight"
          align="center"
          width="80"
        />
        <el-table-column
          label="热量(Kcal)"
          prop="heat"
          align="center"
          width="90"
        />
        <el-table-column
          label="热量占比"
          prop="heatRate"
          align="center"
          width="80"
        />
      </el-table>
    </div>
  </div>
</template>

<script>
import echarts from "echarts";
require("@/utils/echarts/myShine");
import resize from "@/views/dashboard/mixins/resize";
import TextInfo from "@/components/TextInfo";

export default {
  mixins: [resize],
  components: {
    TextInfo,
  },
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
        p: "蛋白质",
        f: "脂肪",
        c: "碳水",
      },
    };
  },
  computed: {
    mData() {
      const [data] = this.data;
      let totalHeat = 0;
      return data
        ? ["p", "f", "c"].map((type) => {
            if (totalHeat === 0) {
              totalHeat = ["p", "f", "c"].reduce((heat, cur) => {
                heat += data[`${cur}Heat`];
                return heat;
              }, 0);
            }
            return {
              type: this.nameDict[type],
              weight: data[`${type}Weight`].toFixed(1),
              heat: data[`${type}Heat`].toFixed(1),
              heatRate: `${((data[`${type}Heat`] / totalHeat) * 100).toFixed(
                2
              )}%`,
            };
          })
        : [];
    },
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
      this.chart = echarts.init(this.$refs.echart, "myShine");
      this.updateChart(this.data.length > 0 ? this.data[0] : {});
    },
    updateChart(data) {
      this.chart.clear();
      this.chart.setOption({
        title: {
          text: `${data.name}营养统计`,
        },
        tooltip: {
          trigger: "item",
          appendToBody: true,
          formatter: (params) => {
            const {
              name,
              marker,
              percent,
              data: { value, oriData, dim },
            } = params;
            return [
              `${marker} ${name}`,
              `含量：${oriData[`${dim}Weight`].toFixed(1)}克`,
              `热量：${value.toFixed(1)}千卡`,
              `热量占比：${percent}%`,
            ].join("</br>");
          },
        },
        series: [
          {
            name: data.name,
            type: "pie",
            radius: [0, 50],
            center: ["50%", "50%"],
            data: ["p", "f", "c"].map((dim) => ({
              dim,
              value: data[`${dim}Heat`],
              name: this.nameDict[dim],
              oriData: data,
            })),
            labelLine: {
              length: 5,
              length2: 5,
            },
            // label: {
            //   show: true,
            //   position: "inside",
            //   color: '#fff'
            // },
            itemStyle: {
              borderWidth: 1,
              borderColor: "#fff",
            },
          },
        ],
      });
    },
  },
  watch: {
    data(newVal, oldVal) {
      if (newVal) {
        this.updateChart(newVal[0]);
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.aspect_pie_chart_wrapper {
  width: 100%;
  display: flex;

  & > div:nth-child(1) {
    // width: 200px
  }

  // & > div:nth-child(2) {
  .small_table {
    .my_cell {
      padding: 2px 0 !important;
    }
  }
  // }
}
</style>
