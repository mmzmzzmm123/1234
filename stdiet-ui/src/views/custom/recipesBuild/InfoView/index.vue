<template>
  <div class="recipes_build_info_view_wrapper">
    <div class="top" v-if="!!recipesData.length">
      <BarChart
        v-if="analyseData.length > 1"
        :data="analyseData"
        height="160px"
        width="100%"
        :max="max"
      />
      <PieChart
        v-if="analyseData.length === 1"
        :data="analyseData"
        height="160px"
        width="100%"
      />
    </div>
    <div class="content">
      <TemplateInfoView v-if="!!temId" :data="templateInfo" />
      <HealthyView :data="healthyData" v-else-if="healthyDataType === 0" dev />
      <BodySignView :data="healthyData" v-else dev />
    </div>
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const {
  mapActions,
  mapState,
  mapGetters,
} = createNamespacedHelpers("recipes");
import BarChart from "./BarChart";
import PieChart from "./PieChart";
import TemplateInfoView from "./TemplateInfoView";
import HealthyView from "@/components/HealthyView";
import BodySignView from "@/components/BodySignView";
export default {
  name: "InfoView",
  data() {
    const { temId } = this.$route.query;
    return { temId };
  },
  components: {
    BarChart,
    PieChart,
    HealthyView,
    BodySignView,
    TemplateInfoView,
  },
  computed: {
    max() {
      const { basicBMR } = this.healthyData || {};
      return basicBMR
        ? parseFloat(basicBMR.substring(0, basicBMR.indexOf("千卡")))
        : 0;
    },
    ...mapState([
      "recipesData",
      "healthyData",
      "healthyDataType",
      "templateInfo",
    ]),
    ...mapGetters(["analyseData"]),
  },
};
</script>
<style lang="scss" scoped>
.recipes_build_info_view_wrapper {
  .top {
    height: 160px;
  }

  .content {
    overflow: auto;
    height: calc(100vh - 192px);
  }
}
</style>
