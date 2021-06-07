<template>
  <el-tabs v-model="activeName">
    <el-tab-pane label="客户信息" name="health">
      <div
        v-loading="healthDataLoading"
        :style="{ height: getTabContentHeight(), overflow: 'auto' }"
      >
        <HealthyView
          dev
          :data="healthyDataType === 0 ? healthyData : {}"
          v-show="healthyDataType === 0"
        />
        <BodySignView
          dev
          :data="healthyDataType === 1 ? healthyData : {}"
          v-show="healthyDataType === 1"
        />
      </div>
    </el-tab-pane>
    <el-tab-pane label="食谱计划" name="plan">
      <div :style="{ height: getTabContentHeight(), overflow: 'auto' }">
        <RecipesPlan />
      </div>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
import HealthyView from "@/components/HealthyView";
import BodySignView from "@/components/BodySignView";
import RecipesPlan from "./recipesPlan";
const { mapActions, mapState, mapMutations, mapGetters } =
  createNamespacedHelpers("message");
export default {
  name: "SignUserInfo",
  components: {
    HealthyView,
    BodySignView,
    RecipesPlan,
  },
  data() {
    return {
      activeName: "health",
    };
  },
  methods: {
    getTabContentHeight() {
      const tabPanelElm = document.querySelector(".el-tabs");
      if (tabPanelElm) {
        return `${tabPanelElm.clientHeight - 68}px`;
      }
      return "";
    },
  },
  computed: {
    ...mapState(["healthyData", "healthyDataType", "healthDataLoading"]),
  },
};
</script>
<style lang="scss" scoped>
</style>
