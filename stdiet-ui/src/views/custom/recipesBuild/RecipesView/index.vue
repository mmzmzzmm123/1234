<template>
  <div class="recipes_view_wrapper">
    <RecipesAspectCom :collapse.sync="collapse" :data="analyseData" />
    <div
      class="recipes_content"
      :style="`height: calc(100vh - ${collapse ? 142 : 312}px)`"
    >
      <RecipesCom
        v-for="(item, index) in data"
        :key="item.id"
        :data="item"
        :name="name"
        :num="index + 1"
      />
    </div>
  </div>
</template>
<script>
import RecipesCom from "./RecipesCom";
import RecipesAspectCom from "./RecipesAspectCom";
export default {
  name: "RecipesView",
  components: {
    RecipesCom,
    RecipesAspectCom,
  },
  computed: {
    mCollapse() {
      return analyseData.length ? this.collapse : false;
    },
  },
  data() {
    return {
      collapse: false,
    };
  },
  props: ["data", "analyseData", "name", "numRange"],
};
</script>
<style lang="scss" scoped >
.recipes_view_wrapper {
  // padding-right: 20px;

  .recipes_content {
    overflow: auto;
    background: white;
  }
}
</style>
