<template>
  <div>
    <RecipesCom v-for="item in data" :key="item.id" :data="item" />
  </div>
</template>
<script>
import RecipesCom from "@/components/RecipesCom";
export default {
  name: "RecipesView",
  components: {
    RecipesCom,
  },
  props: ["data"],
};
</script>
<style rel="stylesheet/scss" lang="scss">
</style>
