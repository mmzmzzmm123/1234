<template>
  <div class="app-container">
    <div class="content">
      <div class="left">left</div>
      <div class="right">
        <HealthyView :data="healthyData" v-if="healthyDataType === 0" />
        <BodySignView :data="healthyData" v-else />
      </div>
    </div>
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";

const { mapActions, mapState, mapMutations } = createNamespacedHelpers(
  "recipes"
);

import HealthyView from "./HealthyView";
import BodySignView from "./BodySignView";

export default {
  name: "BuildRecipies",
  data() {
    return {};
  },
  mounted() {
    //
    console.log({
      cusId: this.cusId,
      recipesId: this.recipesId,
    });
    this.init({ cusId: this.cusId }).catch((err) => {
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
  },
  props: ["planId", "cusId", "recipesId"],
  computed: {
    ...mapState({
      healthyData: (state) => state.healthyData,
      healthyDataType: (state) => state.healthyDataType,
    }),
  },
  methods: {
    ...mapActions(["init"]),
    ...mapMutations(["clean"]),
  },
};
</script>
<style rel="stylesheet/scss" lang="scss">
.content {
  display: flex;
  height: calc(100vh - 124px);
  .left {
    flex: 4;
    border-right: 1px solid #e6ebf5;
    height: 100%;
    overflow: auto;
  }
  .right {
    flex: 1;
    height: 100%;
    padding-left: 20px;
    overflow: auto;
  }
}
</style>
