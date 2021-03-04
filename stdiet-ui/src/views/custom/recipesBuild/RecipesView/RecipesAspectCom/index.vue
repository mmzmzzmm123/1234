<template>
  <div
    class="recipes_aspect_wrapper"
    :style="`height: ${collapse ? 30 : 200}px`"
  >
    <div class="header">
      <el-button size="mini" v-if="!recipesId" @click="handleOnBack"
        >返回</el-button
      >
      <el-popover
        placement="bottom"
        trigger="click"
        title="修改审核状态"
        style="margin-right: 12px"
        v-hasPermi="['recipes:plan:review']"
      >
        <div>
          <el-button size="mini" type="success" @click="hanldeOnReveiwChange(2)"
            >审核通过</el-button
          >
          <el-button size="mini" type="danger" @click="hanldeOnReveiwChange(1)"
            >未审核通过</el-button
          >
        </div>
        <el-button
          slot="reference"
          size="mini"
          v-if="reviewStatus"
          :type="reviewStatus === 1 ? 'danger' : 'success'"
        >
          {{ reviewStatus === 1 ? "未审核" : "已审核" }}
        </el-button>
      </el-popover>
      <el-button
        v-if="!recipesId"
        size="mini"
        type="primary"
        @click="handleOnSave"
        >生成食谱</el-button
      >
      <el-button size="mini" type="text" @click="handleCollapseClick">
        {{ `${collapse ? "展开" : "收起"}` }}
        <em
          class="el-icon-arrow-down arrow_icon"
          :style="
            collapse ? 'transform: rotate(-180deg);' : 'transform: unset;'
          "
        />
      </el-button>
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
        :max="
          healthyData.basicBMR
            ? parseFloat(
                healthyData.basicBMR.substring(
                  0,
                  healthyData.basicBMR.indexOf('千卡')
                )
              )
            : 0
        "
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
const { mapActions, mapState, mapMutations } = createNamespacedHelpers(
  "recipes"
);
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
    ...mapState(["recipesId", "reviewStatus", "healthyData"]),
  },
  methods: {
    handleCollapseClick() {
      this.$emit("update:collapse", !this.collapse);
    },
    handleOnSave() {
      this.saveRecipes({
        callback: (query) => {
          // console.log(query);
          this.$router.replace({
            path: "/recipes/build/" + query.name + "/" + query.planId,
          });
        },
      });
    },
    hanldeOnReveiwChange(reviewStatus) {
      this.updateReviewStatus({ reviewStatus });
    },
    handleOnBack() {
      this.updateStateData({ recipesData: [] });
    },
    ...mapActions(["saveRecipes", "updateReviewStatus"]),
    ...mapMutations(["updateStateData"]),
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

    .arrow_icon {
      transition: all 0.3s;
      transform-origin: center center;
    }
  }

  .content {
  }
}
</style>
