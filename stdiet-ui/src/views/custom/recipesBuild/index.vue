<template>
  <div class="recipes_build_wrapper" v-title :data-title="name">
    <div
      :class="`left ${leftShow ? 'left_show' : ''}`"
      :style="`width: ${leftShow ? 260 : 0}px`"
    >
      <div v-show="leftShow">
        <VerifyView />
      </div>
    </div>
    <div class="content" v-loading="recipesDataLoading" id="center_content">
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
import VerifyView from "./VerifyView";

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
    VerifyView,
  },
  props: ["name", "planId"],
  computed: {
    ...mapState([
      "leftShow",
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
    // transition: all 0.5s;
  }

  .left_show {
    margin-right: 16px;
    border-right: 1px solid #e6ebf5;
  }

  .content {
    flex: 1;
    border-right: 1px solid #e6ebf5;
    height: 100%;
    overflow: hidden;
    padding-right: 16px;
  }
  .right {
    width: 380px;
    height: 100%;
    padding-left: 16px;
  }
}
</style>
