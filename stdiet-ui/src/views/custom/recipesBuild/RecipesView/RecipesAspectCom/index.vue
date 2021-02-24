<template>
  <div
    class="recipes_aspect_wrapper"
    :style="`height: ${collapse ? 30 : 200}px`"
  >
    <div class="header">
      <el-button
        v-if="!recipesId"
        size="mini"
        type="primary"
        @click="handleOnSave"
        >生成食谱</el-button
      >
      <el-button size="mini" type="text" @click="handleCollapseClick">{{
        `${collapse ? "展开分析" : "收起分析"}`
      }}</el-button>
    </div>
    <div
      class="content"
      :style="`visibility: ${collapse ? 'hidden' : 'visible'};`"
    >
      <BarChart
        v-if="data.length > 1"
        :data="data"
        height="170px"
        width="500px"
      />
      <PieChart
        v-if="data.length === 1"
        :data="data"
        height="170px"
        width="500px"
      />
    </div>
  </div>
</template>
<script>
import BarChart from "./BarChart";
import PieChart from "./PieChart";
import { createNamespacedHelpers } from "vuex";
const { mapActions, mapState } = createNamespacedHelpers("recipes");
export default {
  name: "RecipesAspectCom",
  components: {
    BarChart,
    PieChart,
  },
  data() {
    return {};
  },
  updated() {
    // console.log(this.data);
  },
  props: ["collapse", "data"],
  computed: {
    ...mapState(["recipesId"]),
  },
  methods: {
    handleCollapseClick() {
      this.$emit("update:collapse", !this.collapse);
    },
    handleOnSave() {
      this.saveRecipes({
        callback: (query) => {
          console.log(query);
          this.$router.replace({ path: "/recipes/build", query });
        },
      });
    },
    ...mapActions(["saveRecipes"]),
  },
};
</script>
<style rel="stylesheet/scss" lang="scss" scope>
.recipes_aspect_wrapper {
  transition: all 0.3s;
  padding-bottom: 12px;

  .header {
    text-align: right;
    height: 30px;
  }

  .content {
  }
}
</style>
