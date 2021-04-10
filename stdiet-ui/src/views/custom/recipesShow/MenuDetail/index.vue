<template>
  <div class="menu_detail_wrapper">
    <!-- 营养分析 -->
    <!-- <NutriComputeCom :date="date" :value="value" /> -->
    <div class="top">
      <img
        class="logo"
        :src="logo"
        style="width: auto; height: 32px"
        alt="logo"
      />
      <em
        class="el-icon-shopping-cart-full icon_style shopping_cart"
        @click="handleOnShoppingPlanClick"
      />
    </div>
    <!-- 食谱详细 -->
    <el-card v-for="obj in menus" :key="obj.type" style="margin-top: 12px">
      <div slot="header">
        <div class="header_style">
          <span style="font-weight: bold">{{ obj.typeName }}</span>
          <span class="time">{{ menuTypeTimeDict[obj.type] }}</span>
        </div>
      </div>
      <div v-for="mObj in obj.values" :key="mObj.id">
        <div class="dishes_item">
          <div
            v-if="mObj.igdList.length === 1"
            class="simple_dishes"
            @click="handleOnDetailClick(mObj)"
          >
            <span>
              <span>
                {{ mObj.igdList[0].name }}
              </span>
              <em
                v-if="mObj.remark || mObj.methods"
                class="el-icon-collection-tag"
                style="color: #1890ff; margin-left: 8px"
              />
            </span>
            <span class="weight_style">
              <span style="margin-right: 20px">{{
                mObj.igdList[0].cusStr
              }}</span>
              <span>{{ mObj.igdList[0].weight }}克</span>
            </span>
          </div>
          <div v-else style="padding: 8px 0" @click="handleOnDetailClick(mObj)">
            <span>
              <span style="color: #1890ff">{{ mObj.name }}</span>
              <em
                class="el-icon-collection-tag"
                style="color: #1890ff; margin-left: 8px"
              />
            </span>
            <div
              class="simple_dishes"
              style="margin-left: 16px; height: 30px"
              v-for="igd in mObj.igdList"
              :key="igd.id"
            >
              <span>{{ igd.name }} </span>
              <span class="weight_style">
                <span style="margin-right: 20px">{{ igd.cusStr }}</span>
                <span>{{ igd.weight }}克</span>
              </span>
            </div>
          </div>
        </div>
      </div>
    </el-card>
    <!-- 复杂菜品展示 -->
    <DishesDetailDialog ref="detailDialogRef" />
    <!-- 当天采购计划 -->
    <TodayShoppingPlanDrawer ref="shoppingPlanRef" />
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const {
  mapActions,
  mapState,
  mapMutations,
  mapGetters,
} = createNamespacedHelpers("recipesShow");
// import NutriComputeCom from "./NutriComputeCom";
import DishesDetailDialog from "./DishesDetailDialog";
import TodayShoppingPlanDrawer from "../PlanDrawer/ShoppingPlanDrawer";
export default {
  name: "menuDetail",
  props: ["value", "date"],
  components: {
    // NutriComputeCom,
    DishesDetailDialog,
    TodayShoppingPlanDrawer,
  },
  data() {
    return {
      logo: require("@/assets/logo/st_logo.png"),
      menuTypeTimeDict: {
        2: "10:00 - 10:30",
        4: "15:00 - 15:30",
        6: "21:00 - 22:00",
      },
    };
  },
  computed: {
    menus() {
      const mData = this.value.reduce((obj, cur) => {
        if (!obj[cur.type]) {
          obj[cur.type] = [];
        }
        // let tarMenu = cur;
        // if (
        //   !tarMenu.methods &&
        //   !tarMenu.remark &&
        //   tarMenu.igdList.length === 1
        // ) {
        //   tarMenu = tarMenu.igdList[0];
        //   tarMenu.cusStr = `${this.cusWeightDict[tarMenu.cusWeight] || ""}${
        //     this.cusUnitDict[tarMenu.cusUnit] || ""
        //   }`;
        // } else {
        cur.igdList.forEach((igd) => {
          igd.cusStr = `${this.cusWeightDict[igd.cusWeight] || ""}${
            this.cusUnitDict[igd.cusUnit] || ""
          }`;
        });
        // }
        obj[cur.type].push(cur);
        return obj;
      }, {});
      const mMenus = Object.keys(mData).map((type) => ({
        type,
        typeName: this.menuTypeDict[type],
        values: mData[type],
      }));
      console.log(mMenus);
      return mMenus;
    },
    ...mapState(["cusUnitDict", "cusWeightDict", "menuTypeDict"]),
  },
  methods: {
    handleOnDetailClick(data) {
      if (data.remark || data.methods) {
        this.$refs["detailDialogRef"].showDialog(data);
      }
    },
    handleOnShoppingPlanClick() {
      this.$refs["shoppingPlanRef"].showDrawer({
        num: -1,
        data: [
          {
            dishes: this.value,
          },
        ],
        label: this.date,
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.menu_detail_wrapper {
  padding: 0 12px 12px 12px;
  .top {
    height: 50px;
    position: relative;
    .logo {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
    }

    .shopping_cart {
      position: absolute;
      right: 5px;
      top: 10px;
      display: flex;
      align-items: center;
      &::after {
        margin-left: 2px;
        content: "采购";
        font-size: 12px;
      }
    }
  }

  .header_style {
    display: flex;
    justify-content: space-between;

    .time {
      color: #8c8c8c;
    }
  }

  .dishes_item {
    padding: 0 8px;
    border-bottom: 1px solid #dfe6ec;

    &:active {
      background: #409eff;
      color: white;
    }

    .simple_dishes {
      display: flex;
      height: 38px;
      align-items: center;
      justify-content: space-between;

      .weight_style {
        width: 40%;

        display: flex;
        align-items: center;
        justify-content: space-between;
      }
    }

    .complex_dishes {
      // display: flex;
      // align-items: center;
      // justify-content: space-between;
    }
  }
}
</style>
