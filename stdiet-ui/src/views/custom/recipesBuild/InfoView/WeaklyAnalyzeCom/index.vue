<template>
  <div class="weakly_analyze_com_wrapper">
    <bar-chart
      :data="nutritionSource"
      :max="max"
      title="营养统计"
      type="nutrition"
      width="364px"
      @onClick="handleOnClick"
    />
    <bar-chart
      :data="caloriesSource"
      title="热量统计"
      type="calories"
      width="364px"
      @onClick="handleOnClick"
    />
    <bar-chart
      :data="weightSource"
      title="质量统计"
      type="weight"
      width="364px"
      @onClick="handleOnClick"
    />
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const { mapState, mapGetters, mapMutations } = createNamespacedHelpers(
  "recipes"
);
import VueScrollTo from "vue-scrollto";
import BarChart from "./BarChart";
export default {
  name: "WeaklyAnalyzeCom",
  components: {
    BarChart,
  },
  data() {
    return {};
  },
  props: ["data"],
  methods: {
    handleOnClick(currentDay) {
      this.setCurrentDay({ currentDay });
      VueScrollTo.scrollTo(`#recipes${currentDay}`, 500, {
        container: "#recipes_content",
      });
    },
    ...mapMutations(["setCurrentDay"]),
  },
  computed: {
    nutritionSource() {
      return this.analyseData.reduce(
        (arr, cur) => {
          arr.push([cur.name, cur.pCalories, cur.fCalories, cur.cCalories]);
          return arr;
        },
        [["日期", "蛋白质", "脂肪", "碳水"]]
      );
    },
    caloriesSource() {
      return this.analyseData.reduce(
        (arr, cur) => {
          arr.push([
            cur.name,
            cur.calories1,
            cur.calories2,
            cur.calories3,
            cur.calories4,
            cur.calories5,
            cur.calories6,
          ]);
          return arr;
        },
        [["日期", "早餐", "早加餐", "午餐", "午加餐", "晚餐", "晚加餐"]]
      );
    },
    weightSource() {
      return this.analyseData.reduce(
        (arr, cur) => {
          arr.push([
            cur.name,
            cur.weight1,
            cur.weight2,
            cur.weight3,
            cur.weight4,
            cur.weight5,
            cur.weight6,
          ]);
          return arr;
        },
        [["日期", "早餐", "早加餐", "午餐", "午加餐", "晚餐", "晚加餐"]]
      );
    },
    max() {
      const { basicBMR } = this.healthyData || {};
      return basicBMR
        ? parseFloat(basicBMR.substring(0, basicBMR.indexOf("千卡")))
        : 0;
    },
    ...mapState(["healthyData"]),
    ...mapGetters(["analyseData"]),
  },
};
</script>
<style lang="scss" scoped>
.weakly_analyze_com_wrapper {
  width: 100%;
  & > div:not(:nth-child(1)) {
    margin-top: 12px;
  }
}
</style>
