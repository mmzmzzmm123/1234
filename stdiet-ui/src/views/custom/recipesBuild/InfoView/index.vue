<template>
  <div class="recipes_build_info_view_wrapper">
    <el-tabs v-model="activeName" @tab-click="handleOnTabClick">
      <el-tab-pane label="食谱分析" name="0" v-if="showChart">
        <div class="content">
          <WeaklyAnalyzeCom v-if="analyseData.length > 1" />
          <DailyAnalyzeCom v-else />
        </div>
      </el-tab-pane>
      <el-tab-pane label="模板信息" name="1" v-if="!!temId">
        <div class="content">
          <TemplateInfoView :data="templateInfo" />
        </div>
      </el-tab-pane>
      <el-tab-pane label="客户信息" name="2" v-else>
        <div class="content">
          <HealthyView :data="healthyData" v-if="healthyDataType === 0" dev />
          <BodySignView :data="healthyData" v-else dev />
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const { mapActions, mapState, mapGetters } = createNamespacedHelpers("recipes");
import TemplateInfoView from "./TemplateInfoView";
import HealthyView from "@/components/HealthyView";
import BodySignView from "@/components/BodySignView";
import WeaklyAnalyzeCom from "./WeaklyAnalyzeCom";
import DailyAnalyzeCom from "./DailyAnalyzeCom";
export default {
  name: "InfoView",
  data() {
    const { temId } = this.$route.query;
    return { temId, activeName: "0" };
  },
  components: {
    HealthyView,
    BodySignView,
    TemplateInfoView,
    WeaklyAnalyzeCom,
    DailyAnalyzeCom,
  },
  computed: {
    max() {
      const { basicBMR } = this.healthyData || {};
      return basicBMR
        ? parseFloat(basicBMR.substring(0, basicBMR.indexOf("千卡")))
        : 0;
    },
    showChart() {
      return !!this.recipesData.length;
    },
    ...mapState([
      "recipesData",
      "healthyData",
      "healthyDataType",
      "templateInfo",
    ]),
    ...mapGetters(["analyseData"]),
  },
  methods: {
    handleOnTabClick(tab) {
      this.activeName = tab.name;
    },
  },
};
</script>
<style lang="scss" scoped>
.recipes_build_info_view_wrapper {
  .content {
    overflow: auto;
    height: calc(100vh - 88px);
  }
}
</style>
