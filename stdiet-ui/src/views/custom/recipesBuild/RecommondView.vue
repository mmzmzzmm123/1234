
<template>
  <el-button @click="handleOnClick">推荐</el-button>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const {
  mapActions,
  mapState,
  mapMutations,
  mapGetters,
} = createNamespacedHelpers("recipes");
export default {
  name: "RecommondView",
  methods: {
    handleOnClick() {
      this.getRecipesInfo({ recipesId: 73 });
    },
    ...mapActions(["getRecipesInfo"]),
  },
};
</script>
