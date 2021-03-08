<template>
  <div class="recipes_build_wrapper" v-title :data-title="name">
    <div class="left" v-loading="recipesDataLoading">
      <RecipesView
        v-if="!!recipesData.length"
        :data="recipesData"
        :name="healthyData.name"
        :analyseData="analyseData"
      />
      <RecommendView v-else />
    </div>
    <div class="right" v-loading="healthDataLoading">
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
  mapMutations,
  mapGetters,
} = createNamespacedHelpers("recipes");

import HealthyView from "@/components/HealthyView";
import BodySignView from "@/components/BodySignView";
import RecipesView from "./RecipesView/index";
import RecommendView from "./RecommendView";
import TemplateInfoView from "./TemplateInfoView";

export default {
  name: "BuildRecipies",
  data() {
    const { temId } = this.$route.query;
    return {
      temId,
    };
  },
  mounted() {
    this.$store.dispatch("global/init", {});
    //
    this.init({
      planId: this.planId,
      name: this.name,
      temId: this.temId,
    }).catch((err) => {
      this.$message.error(err.message);
    });
  },
  destroyed() {
    this.clean();
  },
  created() {},
  components: {
    HealthyView,
    BodySignView,
    RecipesView,
    RecommendView,
    TemplateInfoView,
  },
  props: ["name", "planId"],
  computed: {
    ...mapState([
      "healthyData",
      "healthyDataType",
      "templateInfo",
      "recipesData",
      "recipesDataLoading",
      "healthDataLoading",
    ]),
    ...mapGetters(["analyseData"]),
  },
  methods: {
    ...mapActions(["init"]),
    ...mapMutations(["clean"]),
  },
};
</script>
<style lang="scss" scoped>
.recipes_build_wrapper {
  padding: 16px;
  display: flex;
  height: 100vh;
  .left {
    flex: 4;
    border-right: 1px solid #e6ebf5;
    height: 100%;
    overflow: hidden;
    padding-right: 20px;
  }
  .right {
    flex: 1;
    height: 100%;
    padding-left: 20px;
    overflow: auto;
  }
}
</style>
