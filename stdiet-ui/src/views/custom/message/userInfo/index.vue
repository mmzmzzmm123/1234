<template>
  <el-tabs v-model="activeName" class="message_userinfo_wrapper">
    <el-tab-pane label="客户信息" name="health">
      <div
        v-loading="healthDataLoading"
        :style="{ height: getTabContentHeight(), overflow: 'auto' }"
      >
        <HealthyView
          dev
          :data="healthyDataType === 0 ? healthyData : {}"
          v-show="healthyDataType === 0"
        />
        <BodySignView
          dev
          :data="healthyDataType === 1 ? healthyData : {}"
          v-show="healthyDataType === 1"
        />
        <div v-if="customerData.id" class="customer_service_info">
          <div class="info_item">
            <span>主任营养师：</span>
            <span>
              {{ customerData.dietitianName || "无" }}
            </span>
          </div>
          <div class="info_item">
            <span>营养师助理：</span>
            <span>{{ customerData.assDietitianName || "无" }}</span>
          </div>
          <div class="info_item">
            <span>售后营养师：</span>
            <span>{{ customerData.afterDietitianName || "无" }}</span>
          </div>
        </div>
      </div>
    </el-tab-pane>
    <el-tab-pane label="食谱计划" name="plan">
      <div :style="{ height: getTabContentHeight(), overflow: 'auto' }">
        <RecipesPlan />
      </div>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
import HealthyView from "@/components/HealthyView";
import BodySignView from "@/components/BodySignView";
import RecipesPlan from "./recipesPlan";
const { mapActions, mapState, mapMutations, mapGetters } =
  createNamespacedHelpers("message");
export default {
  name: "SignUserInfo",
  components: {
    HealthyView,
    BodySignView,
    RecipesPlan,
  },
  data() {
    return {
      activeName: "health",
    };
  },
  methods: {
    getTabContentHeight() {
      const tabPanelElm = document.querySelector(".el-tabs");
      if (tabPanelElm) {
        return `${tabPanelElm.clientHeight - 68}px`;
      }
      return "";
    },
  },
  computed: {
    ...mapState([
      "healthyData",
      "healthyDataType",
      "healthDataLoading",
      "customerData",
    ]),
  },
};
</script>
<style lang="scss" scoped>
.message_userinfo_wrapper {
  .customer_service_info {
    position: absolute;
    right: 30%;
    top: 68px;

    .info_item {
      margin-bottom: 10px;
      font-size: 14px;

      & > span:nth-child(1) {
        color: #8c8c8c;
      }
    }
  }
}
</style>
