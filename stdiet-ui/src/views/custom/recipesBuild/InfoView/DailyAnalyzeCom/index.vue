<template>
  <div class="daily_analyze_com_wrapper">
    <pie-chart
      title="营养统计"
      :data="nutritionSource"
      type="nutrition"
      width="364px"

      :subTitle="subTitle"
    />
    <pie-chart
      title="热量统计"
      :data="caloriesSource"
      type="calories"
      width="364px"
      :subTitle="subTitle"
    />
    <pie-chart
      title="质量统计"
      :data="weightSource"
      type="weight"
      width="364px"
      :subTitle="subTitle"
    />
    <el-button size="mini" type="text" @click="backToAll" class="see_all"
      >查看全部</el-button
    >
  </div>
</template>
<script>
import PieChart from "./PieChart";
import { createNamespacedHelpers } from "vuex";
const { mapGetters, mapMutations } = createNamespacedHelpers("recipes");
export default {
  name: "DailyAnalyzeCom",
  data() {
    return {
      subTitle: "",
    };
  },
  components: {
    PieChart,
  },
  methods: {
    backToAll() {
      this.resetCurrentDay({ currentDay: -1 });
    },
    ...mapMutations(["resetCurrentDay"]),
  },
  computed: {
    nutritionSource() {
      const [data] = this.analyseData;
      this.subTitle = data.name;
      return [
        ["蛋白质", data.pCalories],
        ["脂肪", data.fCalories],
        ["碳水", data.cCalories],
      ];
    },
    caloriesSource() {
      const [data] = this.analyseData;
      const source = [
        ["早餐", data.calories1],
        ["午餐", data.calories3],
        ["晚餐", data.calories5],
      ];
      if (data.calories2) {
        source.push(["早加餐", data.calories2]);
      }
      if (data.calories4) {
        source.push(["午加餐", data.calories4]);
      }
      if (data.calories6) {
        source.push(["晚加餐", data.calories6]);
      }
      return source;
    },
    weightSource() {
      const [data] = this.analyseData;
      const source = [
        ["早餐", data.weight1],
        ["午餐", data.weight3],
        ["晚餐", data.weight5],
      ];
      if (data.weight2) {
        source.push(["早加餐", data.weight2]);
      }
      if (data.weight4) {
        source.push(["午加餐", data.weight4]);
      }
      if (data.weight6) {
        source.push(["晚加餐", data.weight6]);
      }
      return source;
    },
    ...mapGetters(["analyseData"]),
  },
};
</script>
<style lang="scss" scoped>
.daily_analyze_com_wrapper {
  width: 100%;
  position: relative;
  & > div:not(:nth-child(1)) {
    margin-top: 12px;
  }

  .see_all {
    position: absolute;
    top: 0;
    right: 0;
  }
}
</style>

