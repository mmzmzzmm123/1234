<template>
  <div class="menu_detail_wrapper">
    <!-- 营养分析 -->
    <NutriComputeCom :date="date" :value="value" />
    <!-- 食谱详细 -->
    <el-card v-for="obj in menus" :key="obj.type" style="margin-top: 12px">
      <div slot="header">
        <span>{{ obj.typeName }}</span>
      </div>
      <div v-for="mObj in obj.values" :key="mObj.id">
        <div class="dishes_item">
          <div v-if="!mObj.methods" class="simple_dishes">
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
import NutriComputeCom from "./NutriComputeCom";
import DishesDetailDialog from "./DishesDetailDialog";
import { getDicts } from "@/api/custom/recipesShow";
export default {
  name: "menuDetail",
  props: ["value", "date"],
  components: {
    NutriComputeCom,
    DishesDetailDialog,
  },
  created() {
    getDicts("cus_cus_unit").then((response) => {
      this.curUnitDict = response.data.reduce((obj, cur) => {
        obj[cur.dictValue] = cur.dictLabel;
        return obj;
      }, {});
    });
    getDicts("cus_cus_weight").then((response) => {
      this.cusWeightDict = response.data.reduce((obj, cur) => {
        obj[cur.dictValue] = cur.dictLabel;
        return obj;
      }, {});
    });
    getDicts("cus_dishes_type").then((response) => {
      this.menuTypeDict = response.data.reduce((obj, cur) => {
        obj[cur.dictValue] = cur.dictLabel;
        return obj;
      }, {});
    });
  },
  data() {
    return {
      menuTypeDict: {},
      curUnitDict: {},
      cusWeightDict: {},
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
            this.curUnitDict[tarMenu.cusUnit] || ""
          }`;
        } else {
          tarMenu.igdList.forEach((igd) => {
            igd.cusStr = `${this.cusWeightDict[igd.cusWeight] || ""}${
              this.curUnitDict[igd.cusUnit] || ""
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
