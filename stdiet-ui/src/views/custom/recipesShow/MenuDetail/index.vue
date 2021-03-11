<template>
  <div class="menu_detail_wrapper">
    <!-- 营养分析 -->
    <!-- <NutriComputeCom :date="date" :value="value" /> -->
    <div class="top">
      <img :src="logo" style="width: auto; height: 32px" alt="logo" />
    </div>
    <!-- 食谱详细 -->
    <el-card v-for="obj in menus" :key="obj.type" style="margin-top: 12px">
      <div slot="header">
        <div class="header_style">
          <span>{{ obj.typeName }}</span>
          <span class="time">{{ menuTypeTimeDict[obj.type] }}</span>
        </div>
      </div>
      <div v-for="mObj in obj.values" :key="mObj.id">
        <div class="dishes_item">
          <div v-if="!mObj.methods && !mObj.remark" class="simple_dishes">
            <span>{{ mObj.name }}</span>
            <span class="weight_style">
              <span style="margin-right: 20px">{{ mObj.cusStr }}</span>
              <span>{{ mObj.weight }}克</span>
            </span>
          </div>
          <div v-else class="complex_dishes" @click="handleOnDetailClick(mObj)">
            <span>{{ mObj.name }}</span>
            <em class="el-icon-arrow-right" />
          </div>
        </div>
      </div>
    </el-card>
    <!-- 复杂菜品展示 -->
    <DishesDetailDialog ref="detailDialogRef" />
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
export default {
  name: "menuDetail",
  props: ["value", "date"],
  components: {
    // NutriComputeCom,
    DishesDetailDialog,
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
        let tarMenu = cur;
        if (!tarMenu.methods && tarMenu.igdList.length === 1) {
          tarMenu = tarMenu.igdList[0];
          tarMenu.cusStr = `${this.cusWeightDict[tarMenu.cusWeight] || ""}${
            this.cusUnitDict[tarMenu.cusUnit] || ""
          }`;
        } else {
          tarMenu.igdList.forEach((igd) => {
            igd.cusStr = `${this.cusWeightDict[igd.cusWeight] || ""}${
              this.cusUnitDict[igd.cusUnit] || ""
            }`;
          });
        }
        obj[cur.type].push(tarMenu);
        return obj;
      }, {});
      const mMenus = Object.keys(mData).map((type) => ({
        type,
        typeName: this.menuTypeDict[type],
        values: mData[type],
      }));
      // console.log(mMenus);
      return mMenus;
    },
    ...mapState(["cusUnitDict", "cusWeightDict", "menuTypeDict"]),
  },
  methods: {
    handleOnDetailClick(data) {
      this.$refs["detailDialogRef"].showDialog(data);
    },
  },
};
</script>
<style lang="scss" scoped>
.menu_detail_wrapper {
  padding: 0 12px 12px 12px;
  .top {
    text-align: center;
    padding: 10px 14px;
  }

  .header_style {
    display: flex;
    justify-content: space-between;

    .time {
      color: #8c8c8c;
    }
  }

  .dishes_item {
    height: 38px;
    padding: 0 8px;
    border-bottom: 1px solid #dfe6ec;

    &:active {
      background: #409eff;
      color: white;
    }

    .simple_dishes {
      display: flex;
      height: 100%;
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
      display: flex;
      height: 100%;
      align-items: center;
      justify-content: space-between;
    }
  }
}
</style>
