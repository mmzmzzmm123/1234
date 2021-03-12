<template>
  <div class="recipes_build_wrapper" v-title :data-title="name">
    <div class="left"></div>
    <div class="content" v-loading="recipesDataLoading">
      <RecipesView
        v-if="!!recipesData.length"
        :data="recipesData"
        :name="healthyData.name"
      />
      <RecommendView v-else />
    </div>
    <div class="right" v-loading="healthDataLoading">
      <InfoView />
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
import RecipesView from "./RecipesView";
import RecommendView from "./RecommendView";
import InfoView from "./InfoView";

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
    RecipesView,
    RecommendView,
    InfoView,
  },
  props: ["name", "planId"],
  computed: {
    ...mapState([
      "healthyData",
      "recipesData",
      "recipesDataLoading",
      "healthDataLoading",
    ]),
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
  }
  .content {
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
  }
}
</style>
