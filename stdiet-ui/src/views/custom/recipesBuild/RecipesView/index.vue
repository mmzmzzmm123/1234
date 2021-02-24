<template>
  <div class="recipes_view_wrapper">
    <RecipesAspectCom :collapse.sync="collapse" :data="analyseData" />
    <div
      id="recipes_content"
      class="recipes_content"
      :style="`height: calc(100vh - ${collapse ? 62 : 232}px)`"
    >
      <RecipesCom
        v-for="(item, index) in data"
        :id="`recipes${index}`"
        :key="item.id"
        :data="item"
        :name="name"
        :num="index"
        :numDay="item.numDay"
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
