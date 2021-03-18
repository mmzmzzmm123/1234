<template>
  <div
    :class="`aspect_pie_chart_wrapper ${className || ''}`"
    :style="{ height: height, width: width }"
  >
    <div
      ref="echart"
      :style="{ height: height, width: !view ? '100px' : '100%' }"
    />
    <div class="icon_btns">
      <el-tooltip effect="dark" content="营养分析" placement="top">
        <em
          :class="['iconfont', 'icon-03', { sel_icon: view === 0 }]"
          @click="handleOnViewChange(0)"
        />
      </el-tooltip>
      <el-tooltip effect="dark" content="热量分析" placement="top">
        <em
          :class="['iconfont', 'icon-fenxi', { sel_icon: view === 1 }]"
          @click="handleOnViewChange(1)"
        />
      </el-tooltip>
    </div>
    <div v-if="view === 0" class="table_zone">
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
          {{ data[0] ? data[0].totalHeat.toFixed(1) : 0 }}千卡
        </div>
      </div>
    </div>
    <el-button size="mini" type="text" @click="backToAll" class="see_all"
      >查看全部</el-button
    >
  </div>
</template>

<script>
import echarts from "echarts";
require("@/utils/echarts/myShine");
import resize from "@/views/dashboard/mixins/resize";
import TextInfo from "@/components/TextInfo";
import { createNamespacedHelpers } from "vuex";
const { mapMutations, mapState } = createNamespacedHelpers("recipes");

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
      menuDict: {
        1: "早餐",
        2: "早加餐",
        3: "午餐",
        4: "午加餐",
        5: "晚餐",
        6: "晚加餐",
      },
      typeDict: {
        Weight: "摄入量",
        Rate: "供能比",
      },
      view: 0,
    };
  },
  computed: {
    mData() {
      const [data] = this.data;
      if (!data) {
        return [];
      }
      const mData = ["Weight", "Rate"].map((t, idx) => ({
        type: this.typeDict[t],
        ...["p", "f", "c"].reduce((obj, cur) => {
          obj[cur] = idx
            ? data.totalHeat === 0
              ? 0
              : `${((data[`${cur}Heat`] / data.totalHeat) * 100).toFixed(2)}%`
            : `${data[`${cur}Weight`].toFixed(1)}克`;
          return obj;
        }, {}),
      }));
      // console.log(mData);
      return mData;
    },
    ...mapState(["recipesData"]),
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
      this.updateChart(this.data[0] || {});
    },
    backToAll() {
      this.resetCurrentDay({ currentDay: -1 });
    },
    updateChart(data) {
      // console.log(data);
      this.chart.clear();
      const option = {
        title: {
          text: !this.view ? "营养分析" : "热量分析",
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
            return !this.view
              ? [
                  `${marker} ${name}`,
                  `摄入量：${oriData[`${dim}Weight`].toFixed(1)}克`,
                  `摄入热量：${value.toFixed(1)}千卡`,
                  `供能比：${percent}%`,
                ].join("</br>")
              : [
                  `${marker} ${name}`,
                  `热量：${data[`heat${dim}`].toFixed(1)}千卡`,
                  `供能比：${percent}%`,
                ].join("</br>");
          },
        },
        graphic:
          this.view === 1
            ? [
                {
                  type: "group",
                  top: 60,
                  left: 10,
                  silent: true,
                  children: [
                    {
                      type: "text",
                      style: {
                        text: "总热量约",
                        fill: "#606266",
                      },
                    },
                    {
                      type: "text",
                      top: 18,
                      left: 8,
                      style: {
                        text: `${
                          data.totalHeat ? data.totalHeat.toFixed(1) : 0
                        }千卡`,
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
                  children: Object.keys(this.menuDict).reduce((arr, cur) => {
                    const tarData = data[`heat${cur}`];
                    if (tarData) {
                      arr.push({
                        type: "text",
                        top: arr.length * 20,
                        right: 10,
                        style: {
                          text: `${this.menuDict[cur]}：${tarData.toFixed(1)}`,
                          fill: "#606266",
                        },
                      });
                    }
                    return arr;
                  }, []),
                },
              ]
            : [],
        series: [
          {
            name: data.name,
            type: "pie",
            radius: [0, !this.view ? 40 : 60],
            center: ["50%", "55%"],
            data: (!this.view
              ? Object.keys(this.nameDict)
              : Object.keys(this.menuDict)
            ).reduce((arr, dim) => {
              if (!this.view || data[`heat${dim}`]) {
                arr.push({
                  dim,
                  value: !this.view ? data[`${dim}Heat`] : data[`heat${dim}`],
                  name: (!this.view ? this.nameDict : this.menuDict)[dim],
                  oriData: data,
                });
              }
              return arr;
            }, []),
            // labelLine: {
            //   length: 5,
            //   length2: 5,
            // },
            label: {
              show: true,
              position: "inside",
              color: "#fff",
              fontSize: !this.view ? 10 : 12,
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
    handleOnViewChange(view) {
      this.view = view;
      this.chart.resize({
        width: !this.view ? 100 : 364,
      });
      this.updateChart(this.data[0] || {});
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
