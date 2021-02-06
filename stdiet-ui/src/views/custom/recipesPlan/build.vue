<template>
  <div class="app-container">
    <div class="content">
      <div class="left"></div>
      <div class="right"></div>
    </div>
  </div>
</template>
<script>
import { getRecipesPlan } from "@/api/custom/recipesPlan";
import { getOrder } from "@/api/custom/order";
import { getCustomerPhysicalSignsByCusId } from "@/api/custom/customer";
export default {
  name: "BuildRecipies",
  data() {
    return {
      healthyData: {},
    };
  },
  created() {
    getOrder(this.cusId).then((res) => {
      console.log(res);
      getCustomerPhysicalSignsByCusId(res.data.cusId).then((iRes) => {
        this.healthyData = iRes.data;
      });
    });
  },
  components: {},
  props: ["id", "cusId"],
  methods: {},
};
</script>
<style>
.content .left {
}
.content .right {
}
</style>
