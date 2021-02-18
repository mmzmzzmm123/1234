<template>
  <div class="recipes_build_wrapper">
    <div class="left">
      <RecipesView
        :data="recipesData"
        :name="healthyData.name"
        :analyseData="analyseData"
      />
    </div>
    <div class="right">
      <HealthyView :data="healthyData" v-if="healthyDataType === 0" />
      <BodySignView :data="healthyData" v-else />
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

import HealthyView from "./HealthyView";
import BodySignView from "./BodySignView";
import RecipesView from "./RecipesView/index";

export default {
  name: "BuildRecipies",
  data() {
    return {};
  },
  mounted() {
    //
    // console.log({
    //   cusId: this.cusId,
    //   recipesId: this.recipesId,
    // });
    this.init({ cusId: this.cusId, recipesId: this.recipesId }).catch((err) => {
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
  },
  props: ["planId", "cusId", "recipesId"],
  computed: {
    ...mapState(["healthyData", "healthyDataType", "recipesData"]),
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
  height: calc(100vh - 86px);
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
