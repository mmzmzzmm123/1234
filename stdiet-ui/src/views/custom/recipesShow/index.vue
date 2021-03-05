<template>
  <div class="recipes_show_wrapper">
    <div class="header">
      <el-button
        icon="el-icon-date"
        type="text"
        class="icon_btn"
        @click="handleOnPlanClick"
      />
      <img :src="logo" style="width: auto; height: 32px" alt="logo" />
      <el-button
        icon="el-icon-user"
        type="text"
        class="icon_btn"
        @click="handleUserInfoClick"
      />
    </div>
    <div class="content" v-loading="loading">
      <MenuDetail :value="menuData" :date="curDate" />
    </div>

    <!-- 食谱计划 -->
    <PlanDrawer
      ref="planRef"
      :data="planList"
      :planId="curPlanId"
      :menuId="curMenuId"
      @plan-change="handleOnPlanChange"
    />
    <!-- 用户信息 -->
    <UserInfoDrawer ref="userInfoRef" :healthyData="healthyData" />
  </div>
</template>
<script>
import {
  getRecipesPlans,
  getHealthyInfo,
  getRecipesMenuInfoApi,
} from "@/api/custom/recipesShow";
import { dealHealthy } from "@/utils/healthyData";
import UserInfoDrawer from "./UserInfoDrawer";
import PlanDrawer from "./PlanDrawer";
import MenuDetail from "./MenuDetail";
import dayjs from "dayjs";
import { getProcessMenuData } from "./utils";
export default {
  name: "recipesShow",
  components: {
    UserInfoDrawer,
    PlanDrawer,
    MenuDetail,
  },
  props: ["id"],
  data() {
    return {
      logo: require("@/assets/logo/st_logo.png"),
      planList: [],
      curPlanId: 0,
      curMenuId: 0,
      curDate: "",
      loading: false,
      healthyData: {
        type: 0,
        data: {},
      },
      menuData: [],
    };
  },
  created() {
    getRecipesPlans(this.id).then((response) => {
      if (response.code === 200) {
        let curPlanId, curMenuId, curDate;
        const toDay = dayjs().format("YYYY-MM-DD");
        this.planList = response.data.map((plan) => ({
          menus: plan.menus.map((menu, idx) => {
            const date = dayjs(plan.startDate)
              .add(idx, "day")
              .format("YYYY-MM-DD");
            if (toDay === date) {
              curPlanId = plan.id;
              curMenuId = menu.id;
              curDate = date;
            }
            return {
              date,
              id: menu.id,
            };
          }),
          label: `第${plan.startNumDay} 至 ${plan.endNumDay}天`,
          id: plan.id,
        }));
        if (!curMenuId) {
          curMenuId = this.planList[0].menus[0].id;
          curPlanId = this.planList[0].id;
          curDate = this.planList[0].menus[0].date;
        }
        this.curMenuId = curMenuId;
        this.curPlanId = curPlanId;
        this.curDate = curDate;

        this.fetchRecipesInfo(this.curMenuId);
      }
    });
    getHealthyInfo(this.id).then((response) => {
      if (response.code === 200) {
        this.healthyData = {
          type: response.data.type,
          data: dealHealthy(response.data.customerHealthy),
        };
      }
    });
  },
  methods: {
    handleOnPlanClick() {
      this.$refs["planRef"].showDrawer();
    },
    handleUserInfoClick() {
      this.$refs["userInfoRef"].showDrawer();
    },
    fetchRecipesInfo(id) {
      this.loading = true;
      getRecipesMenuInfoApi(id).then((response) => {
        if (response.code === 200) {
          this.loading = false;
          this.menuData = getProcessMenuData(response.data);
          // console.log(this.menuData);
        }
      });
    },
    handleOnPlanChange({ date, id }) {
      this.curDate = date;
      this.fetchRecipesInfo(id);
    },
  },
  watch: {},
};
</script>
<style lang="scss" scoped>
.recipes_show_wrapper {
  height: 100vh;

  .header {
    padding: 2px 6px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 44px;

    .icon_btn {
      font-size: 24px;
      padding: 6px;
      color: #696969;
    }
  }

  .content {
    height: calc(100vh - 44px);
    overflow: auto;
  }
}
</style>
