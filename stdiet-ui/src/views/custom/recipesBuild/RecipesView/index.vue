<template>
  <div class="recipes_view_wrapper">
    <RecipesHeaderCom />
    <div id="recipes_content" class="recipes_content">
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
import RecipesHeaderCom from "./RecipesHeaderCom";
export default {
  name: "RecipesView",
  components: {
    RecipesCom,
    RecipesHeaderCom,
  },
  computed: {},
  data() {
    return {};
  },
  props: ["data", "name"],
};
</script>
<style lang="scss" scoped >
.recipes_view_wrapper {
  // padding-right: 20px;

  .recipes_content {
    overflow: auto;
    height: calc(100vh - 48px);
    background: white;
  }
}
</style>
