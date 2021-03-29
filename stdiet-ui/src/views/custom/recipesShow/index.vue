<template>
  <div class="recipes_show_wrapper" v-title data-title="健康食谱">
    <div class="header">
      <el-button
        icon="el-icon-date"
        type="text"
        class="icon_btn"
        @click="handleOnPlanClick"
      />
      <!-- <img :src="logo" style="width: auto; height: 32px" alt="logo" /> -->
      <span>{{ curDate }}</span>
      <el-button
        icon="el-icon-user"
        type="text"
        class="icon_btn"
        @click="handleUserInfoClick"
      />
    </div>
    <div v-loading="loading">
      <div class="content">
        <MenuDetail :value="menuData" :date="curDate" />
      </div>
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
  getPlanPause,
} from "@/api/custom/recipesShow";
import { dealHealthy } from "@/utils/healthyData";
import UserInfoDrawer from "./UserInfoDrawer";
import PlanDrawer from "./PlanDrawer";
import MenuDetail from "./MenuDetail";
import dayjs from "dayjs";
import { getProcessMenuData } from "./utils";

import { createNamespacedHelpers } from "vuex";
const {
  mapActions,
  mapState,
  mapMutations,
  mapGetters,
} = createNamespacedHelpers("recipesShow");

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
      // logo: require("@/assets/logo/st_logo.png"),
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
      pauseDays: [],
    };
  },
  async created() {
    //
    this.init();
    //
    const planPauseRes = await getPlanPause(this.id);
    // console.log(planPauseRes.data);
    if (planPauseRes.code === 200) {
      this.pauseDays = planPauseRes.data.reduce((arr, cur) => {
        let startDate = cur.pauseStartDate;
        while (dayjs(startDate) <= dayjs(cur.pauseEndDate)) {
          arr.push(startDate);
          startDate = dayjs(startDate).add(1, "days").format("YYYY-MM-DD");
        }
        return arr;
      }, []);
      // console.log(this.pauseDays);
    }

    const plansRes = await getRecipesPlans(this.id);

    if (plansRes.code === 200) {
      let curPlanId, curMenuId, curDate;
      const toDay = dayjs().format("YYYY-MM-DD");
      this.planList = plansRes.data.reduce((arr, plan) => {
        if (plan.menus) {
          let tmpDate = dayjs(plan.startDate).subtract(1, "days");
          arr.push({
            menus: plan.menus.map((menu) => {
              tmpDate = tmpDate.add(1, "days");
              // 跳过暂停日子
              while (
                this.pauseDays.includes(tmpDate.clone().format("YYYY-MM-DD"))
              ) {
                tmpDate = tmpDate.add(1, "days");
              }

              const date = tmpDate.format("YYYY-MM-DD");
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
            recipesId: plan.recipesId,
            label: `第${plan.startNumDay} 至 ${plan.endNumDay}天`,
            id: plan.id,
          });
        }
        return arr;
      }, []);
      if (!curMenuId) {
        curMenuId = this.planList[0].menus[0].id;
        curPlanId = this.planList[0].id;
        curDate = this.planList[0].menus[0].date;
      }
      this.curMenuId = curMenuId;
      this.curPlanId = curPlanId;
      this.curDate = curDate;

      // console.log({
      //   planList: this.planList,
      // });

      this.fetchRecipesInfo(this.curMenuId);
    }

    const healthyRes = await getHealthyInfo(this.id);
    if (healthyRes.code === 200) {
      this.healthyData = {
        type: healthyRes.data.type,
        data: dealHealthy(healthyRes.data.customerHealthy),
      };
    }
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
    ...mapActions(["init"]),
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
