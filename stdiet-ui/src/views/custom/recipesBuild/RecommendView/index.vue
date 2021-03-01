
<template>
  <div class="recommend_view_wrapper">
    <div class="selector_view" v-if="curView === 0">
      <div class="card_content" @click="handleOnClick(1)">
        <em class="el-icon-brush" />
        <span>模板</span>
      </div>
      <div class="card_content" @click="handleOnClick(2)" v-if="false">
        <em class="el-icon-star-off" />
        <span>推荐</span>
      </div>
    </div>

    <TemplateView :view.sync="curView" @onSelect="handleOnSelect" />
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
import TemplateView from "./TemplateView";
import RecommendView from "./RecommendView";
const {
  mapActions,
  mapState,
  mapMutations,
  mapGetters,
} = createNamespacedHelpers("recipes");
export default {
  name: "RecommondView",
  components: {
    RecommendView,
    TemplateView,
  },
  data() {
    return {
      curView: 0,
    };
  },
  methods: {
    handleOnClick(type) {
      this.curView = type;
    },
    handleOnSelect(data) {
      const { recipesId } = data;
      this.getRecipesInfo({ recipesId });
    },
    ...mapActions(["getRecipesInfo"]),
  },
};
</script>
<style lang="scss" scoped>
.recommend_view_wrapper {
  height: 100%;
  width: 100%;
  .selector_view {
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;

    .card_content {
      cursor: pointer;
      margin: 6px;
      padding: 100px 80px;
      border: 3px solid #e6ebf5;
      border-radius: 6px;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      font-weight: bold;

      & > em {
        font-size: 48px;
      }

      & > span {
        margin-top: 12px;
        font-size: 32px;
      }

      &:hover {
        border-color: #409eff;
        color: #1890ff;
      }
    }
  }
}
</style>
