<template>
  <el-drawer
    :visible.sync="visible"
    :with-header="false"
    direction="rtl"
    size="100%"
    append-to-body
    class="shopping_plan_drawer_wrapper"
  >
    <div class="header">
      <el-button
        type="text"
        icon="el-icon-back"
        @click="handleOnBackClick"
        class="icon_btn"
      />
      <span>{{ label }}</span>
      <span class="hold" />
    </div>
    <div class="content" v-loading="loading">
      <div class="top">
        <!-- <img :src="logo" style="width: auto; height: 32px" alt="logo" /> -->
        {{ title }}
      </div>
      <div class="notice">
        <div>注：1. 以下采购计划仅供参考</div>
        <div>2. 主食和油类可按斤购买</div>
        <div>3. 调味食材可按半斤购买</div>
      </div>
      <el-card
        v-for="typeName in Object.keys(shoppingCart)"
        :key="typeName"
        style="margin-top: 12px"
      >
        <div slot="header" class="clearfix">
          <span>{{ typeName }}</span>
        </div>
        <div
          v-for="igdObj in shoppingCart[typeName]"
          :key="igdObj.name"
          class="item_style"
        >
          <span>{{ igdObj.name }}</span>
          <span>
            <span style="margin-right: 2px">{{ igdObj.weight }}</span>
            <span>{{ igdObj.unit }}</span>
          </span>
        </div>
      </el-card>
    </div>
  </el-drawer>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const {
  mapActions,
  mapState,
  mapMutations,
  mapGetters,
} = createNamespacedHelpers("recipesShow");
export default {
  name: "ShoppingPlanDrawer",
  data() {
    return {
      visible: false,
      loading: false,
      title: "",
      logo: require("@/assets/logo/st_logo.png"),
      shoppingCart: {},
      igdUnitDict: {
        鸡蛋: "个",
        牛奶: "盒",
      },
    };
  },
  methods: {
    showDrawer(obj) {
      const { recipesId, num, label, data } = obj;
      console.log(obj);
      this.label = label;
      // num < 0 全部计算
      this.title = `${
        num === 3 ? "前3" : num === 4 ? "后4" : Math.abs(num)
      }天采购计划`;
      this.visible = true;
      if (data) {
        this.processShoppingCart(num, data);
      } else if (this.recipes[recipesId]) {
        this.processShoppingCart(num, this.recipes[recipesId]);
      } else {
        this.loading = true;
        this.fetchFullRecipes({
          recipesId,
        }).then(() => {
          this.loading = false;
          setTimeout(() => {
            this.processShoppingCart(num, this.recipes[recipesId]);
          }, 0);
        });
      }
    },
    processShoppingCart(num, data = []) {
      this.shoppingCart = data.reduce((obj, cur, idx) => {
        if (
          num < 0 || // 全部计算
          num === 7 ||
          (num === 3 && num > idx) ||
          (num === 4 && idx > 2)
        ) {
          cur.dishes.forEach((dObj) => {
            dObj.igdList.forEach((iObj) => {
              const tarTypeName = this.idgTypeDict[iObj.type];
              const tarObj = obj[tarTypeName];
              if (tarObj) {
                const tarIObj = tarObj.find((zObj) => zObj.name === iObj.name);
                if (tarIObj) {
                  tarIObj.weight += this.igdUnitDict[iObj.name]
                    ? iObj.cusWeight
                    : iObj.weight;
                } else {
                  tarObj.push({
                    type: iObj.type,
                    name: iObj.name,
                    weight: this.igdUnitDict[iObj.name]
                      ? iObj.cusWeight
                      : iObj.weight,
                    unit: this.igdUnitDict[iObj.name] || "g",
                  });
                }
              } else {
                obj[tarTypeName] = [
                  {
                    type: iObj.type,
                    name: iObj.name,
                    weight: this.igdUnitDict[iObj.name]
                      ? iObj.cusWeight
                      : iObj.weight,
                    unit: this.igdUnitDict[iObj.name] || "g",
                  },
                ];
              }
            });
          });
        }
        return obj;
      }, {});
      // console.log(this.shoppingCart);
    },
    handleOnBackClick() {
      this.visible = false;
      this.shoppingCart = {};
    },
    ...mapActions(["fetchFullRecipes"]),
  },
  computed: {
    ...mapState(["recipes", "idgTypeDict"]),
  },
};
</script>
<style lang="scss" scoped>
.shopping_plan_drawer_wrapper {
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

    .hold {
      display: block;
      width: 39px;
    }
  }

  .content {
    height: calc(100vh - 44px);
    overflow: auto;
    padding: 12px;

    .top {
      text-align: center;
      padding-bottom: 10px;
    }

    .notice {
      font-size: 12px;
      color: #d96969;

      & > div {
        padding: 2px 0;
      }

      & > div:not(:first-child) {
        margin-left: 24px;
      }
    }

    .item_style {
      display: flex;
      padding: 0 12px;
      height: 28px;
      font-size: 14px;
      align-items: center;
      justify-content: space-between;
      border-bottom: 1px solid #e6ebf5;
    }
  }
}
</style>
