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
import { getRecipesPlan } from "@/api/custom/recipesPlan";
import { getOrder } from "@/api/custom/order";
import { getCustomerPhysicalSignsByCusId } from "@/api/custom/customer";
import { dealHealthy } from "@/utils/healthyData";

import HealthyView from "./HealthyView";
import BodySignView from "./BodySignView";

export default {
  name: "BuildRecipies",
  data() {
    return {
      healthyData: {},
      healthyDataType: 0,
    };
  },
  created() {
    getOrder(this.cusId).then((res) => {
      if (!res.data.cusId) {
        this.$message.error("未找到用户id");
        return;
      }
      getCustomerPhysicalSignsByCusId(res.data.cusId).then((iRes) => {
        this.healthyDataType = iRes.data.type;
        this.healthyData = dealHealthy(iRes.data.customerHealthy);
        // console.log(this.healthyData);
      });
    });
  },
  components: {
    HealthyView,
    BodySignView,
  },
  props: ["id", "cusId"],
  methods: {},
};
</script>
<style>
.content {
  display: flex;
  height: calc(100vh - 124px);
}
.content .left {
  flex: 4;
  border-right: 1px solid rgb(0 21 41 / 8%);
  height: 100%;
  overflow: auto;
}
.content .right {
  flex: 1;
  height: 100%;
  padding-left: 20px;
  overflow: auto;
}
</style>
