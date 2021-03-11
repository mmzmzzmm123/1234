<template>
  <div
    :class="`aspect_pie_chart_wrapper ${className || ''}`"
    :style="{ height: height, width: width }"
  >
    <div ref="echart" :style="{ height: height, width: '100px' }" />
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
        <el-table-column label="蛋白质" prop="p" align="center" width="65" />
        <el-table-column label="脂肪" prop="f" align="center" width="65" />
        <el-table-column label="碳水" prop="c" align="center" width="65" />
      </el-table>
      <div class="summary">
        <div style="font-size: 12px; color: #606266">总热量约等于</div>
        <div style="color: #515a6e; font-weight: bold">
          {{ totalHeat.toFixed(1) }}千卡
        </div>
      </div>
      <div style="text-align: right; margin-top: 4px">
        <el-button size="mini" type="text" @click="backToAll"
          >查看全部</el-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import echarts from "echarts";
require("@/utils/echarts/myShine");
import resize from "@/views/dashboard/mixins/resize";
import TextInfo from "@/components/TextInfo";
import { createNamespacedHelpers } from "vuex";
const { mapMutations } = createNamespacedHelpers("recipes");

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
      totalHeat: 0,
      nameDict: {
        p: "蛋白质",
        f: "脂肪",
        c: "碳水",
      },
      typeDict: {
        Weight: "摄入量",
        Rate: "供能比",
      },
    };
  },
  computed: {
    mData() {
      const [data] = this.data;
      if (!data) {
        this.totalHeat = 0;
        return [];
      }
      this.totalHeat = data.cHeat + data.fHeat + data.pHeat;
      const mData = ["Weight", "Rate"].map((t, idx) => ({
        type: this.typeDict[t],
        ...["p", "f", "c"].reduce((obj, cur) => {
          obj[cur] = idx
            ? `${((data[`${cur}Heat`] / this.totalHeat) * 100).toFixed(2)}%`
            : `${data[`${cur}Weight`].toFixed(1)}克`;
          return obj;
        }, {}),
      }));
      // console.log(mData);
      return mData;
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
    backToAll() {
      this.resetCurrentDay({ currentDay: -1 });
    },
    updateChart(data) {
      this.chart.clear();
      this.chart.setOption({
        title: {
          text: "营养分析",
          subtext: data.name,
        },
        tooltip: {
          position: "right",
          trigger: "item",
          // appendToBody: true,
          formatter: (params) => {
            const {
              name,
              marker,
              percent,
              data: { value, oriData, dim },
            } = params;
            return [
              `${marker} ${name}`,
              `摄入量：${oriData[`${dim}Weight`].toFixed(1)}克`,
              `摄入热量：${value.toFixed(1)}千卡`,
              `供能比：${percent}%`,
            ].join("</br>");
          },
        },
        series: [
          {
            name: data.name,
            type: "pie",
            radius: [0, 40],
            center: ["50%", "55%"],
            data: ["p", "f", "c"].map((dim) => ({
              dim,
              value: data[`${dim}Heat`],
              name: this.nameDict[dim],
              oriData: data,
            })),
            // labelLine: {
            //   length: 5,
            //   length2: 5,
            // },
            label: {
              show: true,
              position: "inside",
              color: "#fff",
              fontSize: 10,
              fontWeight: "bold",
            },
            itemStyle: {
              borderWidth: 1,
              borderColor: "#fff",
            },
          },
        ],
      });
    },
    ...mapMutations(["resetCurrentDay"]),
  },
  watch: {
    data(newVal, oldVal) {
      if (newVal) {
        this.$nextTick(() => {
          this.updateChart(newVal[0]);
        });
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
}
</style>
