<template>
  <div class="recipes_com_wrapper">
    <el-table
      :data="mData"
      border
      :span-method="spanMethod"
      :cell-style="{ padding: '2px 0' }"
      :header-cell-style="{ padding: '4px 0', height: 'unset' }"
      size="mini"
      :style="`outline: ${
        currentDay + 1 === num ? '1px solid #d96969' : 'none'
      }`"
    >
      <el-table-column prop="type" :width="100" align="center">
        <template slot="header">
          <span class="num_day" @click="handleOnOneDayAnalysis">{{
            `${name}第${num}天`
          }}</span>
        </template>
        <template slot-scope="scope">
          <span style="font-weight: bold; font-size: 14px">{{
            typeFormatter(scope.row)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="菜品" prop="name" align="center">
        <template slot="header">
          <el-tooltip
            class="item"
            effect="dark"
            content="点击添加菜品"
            placement="top"
          >
            <span class="num_day" @click="handleOnAdd">菜品</span>
          </el-tooltip>
        </template>
        <template slot-scope="scope">
          <el-popover placement="right" trigger="hover">
            <div>
              <el-button
                type="danger"
                size="mini"
                icon="el-icon-delete"
                class="fun_button"
                @click="handleOnDelete(scope.row)"
                >删除</el-button
              >
            </div>
            <span class="num_day" slot="reference">{{ scope.row.name }}</span>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="食材" prop="igdName" align="center" />
      <el-table-column label="分量估算" :width="80" align="center">
        <template slot-scope="scope">
          <EditableUnit
            :weight="scope.row.cusWeight"
            :unit="scope.row.cusUnit"
            @onChange="(val) => handleOnCustomUnitChange(scope.row, val)"
          />
        </template>
      </el-table-column>
      <el-table-column label="重量(g)" prop="weight" :width="80" align="center">
        <template slot-scope="scope">
          <EditableText
            :value="scope.row.weight"
            @onChange="(val) => handleOnWeightChange(scope.row, val)"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="蛋白质/100g"
        prop="proteinRatio"
        :width="100"
        align="center"
      />
      <el-table-column
        label="脂肪/100g"
        prop="fatRatio"
        :width="90"
        align="center"
      />
      <el-table-column
        label="碳水/100g"
        prop="carbonRatio"
        :width="90"
        align="center"
      />
      <el-table-column
        label="蛋白质含量"
        prop="proteinRatio"
        :width="90"
        align="center"
        :formatter="nutriFormatter"
      />
      <el-table-column
        label="脂肪含量"
        prop="fatRatio"
        :width="90"
        align="center"
        :formatter="nutriFormatter"
      />
      <el-table-column
        label="碳水含量"
        prop="carbonRatio"
        :width="90"
        align="center"
        :formatter="nutriFormatter"
      />
      <el-table-column label="做法" prop="methods" />
    </el-table>
    <AddDishesDrawer ref="drawerRef" @onConfirm="handleOnDishesConfirm" />
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const {
  mapActions,
  mapGetters,
  mapState,
  mapMutations,
} = createNamespacedHelpers("recipes");

import EditableText from "./EditableText";
import EditableUnit from "./EditableUnit";
import AddDishesDrawer from "./AddDishesDrawer";

export default {
  name: "RecipesCom",
  props: {
    data: {
      type: Object,
      default: [],
      required: true,
    },
    name: {
      type: String,
      default: "",
    },
    num: {
      type: Number,
      default: 0,
    },
  },
  components: {
    EditableText,
    EditableUnit,
    AddDishesDrawer,
  },
  mounted() {
    // console.log(this.data);
  },
  data() {
    return {};
  },
  computed: {
    mData() {
      if (!this.data.dishes) {
        return [];
      }
      const mData = this.data.dishes
        .sort((a, b) => a.type - b.type)
        .reduce((arr, cur, idx) => {
          if (cur.dishesId > 0 && cur.type !== "0") {
            cur.igdList.forEach((igd) => {
              let lastTypeHit = false,
                lastNameHit = false;
              if (arr.length > 0) {
                // 倒推，找到第一个出现的位置
                lastTypeHit = arr[arr.length - 1].type === cur.type;
                if (lastTypeHit) {
                  let typePos = arr.length - 1;
                  for (let i = typePos; i >= 0; i--) {
                    if (arr[i].type !== cur.type) {
                      break;
                    }
                    typePos = i;
                  }
                  arr[typePos].typeSpan.rowspan += 1;
                }
                lastNameHit = arr[arr.length - 1].name === cur.name;
                if (lastNameHit) {
                  let namePos = arr.length - 1;
                  for (let i = namePos; i >= 0; i--) {
                    if (arr[i].name !== cur.name) {
                      break;
                    }
                    namePos = i;
                  }
                  arr[namePos].nameSpan.rowspan += 1;
                  arr[namePos].methodsSpan.rowspan += 1;
                }
              }

              arr.push({
                id: cur.id,
                dishesId: cur.dishesId,
                menuId: cur.menuId,
                name: cur.name,
                type: cur.type,
                isMain: cur.isMain,
                methods: cur.methods,
                igdId: igd.id,
                igdName: igd.name,
                proteinRatio: igd.proteinRatio,
                fatRatio: igd.fatRatio,
                carbonRatio: igd.carbonRatio,
                rec: igd.rec,
                notRec: igd.notRec,
                weight: igd.weight,
                cusWeight: igd.cusWeight,
                cusUnit: igd.cusUnit,
                typeSpan: lastTypeHit
                  ? {
                      rowspan: 0,
                      colspan: 0,
                    }
                  : {
                      rowspan: 1,
                      colspan: 1,
                    },
                nameSpan: lastNameHit
                  ? {
                      rowspan: 0,
                      colspan: 0,
                    }
                  : {
                      rowspan: 1,
                      colspan: 1,
                    },
                methodsSpan: lastNameHit
                  ? {
                      rowspan: 0,
                      colspan: 0,
                    }
                  : {
                      rowspan: 1,
                      colspan: 1,
                    },
              });
            });
          }
          return arr;
        }, []);
      // console.log(mData);
      return mData;
    },
    ...mapGetters(["typeDict"]),
    ...mapState(["currentDay"]),
  },
  methods: {
    spanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        return row.typeSpan;
      } else if (columnIndex === 1) {
        return row.nameSpan;
      } else if (columnIndex === 11) {
        return row.methodsSpan;
      }
    },
    typeFormatter(row) {
      return this.typeDict[row.type];
    },
    nutriFormatter(row, col) {
      return ((row.weight / 100) * row[col.property]).toFixed(1);
    },
    handleOnOneDayAnalysis(e) {
      // 校验某天
      this.setCurrentDay({ currentDay: this.num - 1 });
    },
    handleOnAdd() {
      // console.log(this.num);
      this.$refs.drawerRef.showDrawer();
    },
    handleOnDelete(data) {
      // console.log(data);
      this.deleteDishes({ num: this.num - 1, id: data.id });
    },
    handleOnWeightChange(data, weight) {
      // console.log({ data, weight });
      this.updateDishes({
        num: this.num - 1,
        dishesId: data.dishesId,
        igdId: data.igdId,
        weight,
      });
    },
    handleOnCustomUnitChange(data, { cusWeight, cusUnit }) {
      // console.log({ data, cusWeight, cusUnit });
      this.updateDishes({
        num: this.num - 1,
        dishesId: data.dishesId,
        igdId: data.igdId,
        cusWeight,
        cusUnit,
      });
    },
    handleOnDishesConfirm(data) {
      // console.log(data);
      this.addDishes({
        num: this.num - 1,
        data,
      });
    },
    ...mapActions(["updateDishes", "addDishes", "deleteDishes"]),
    ...mapMutations(["setCurrentDay"]),
  },
};
</script>
<style lang="scss" scoped>
.recipes_com_wrapper {
  margin-bottom: 24px;
  padding: 1px;

  .num_day {
    cursor: pointer;
    outline: none;
  }
}
</style>
<style lang="scss">
.fun_button {
  font-size: 12px;
  padding: 4px 8px;
}
</style>
