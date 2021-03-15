<template>
  <div class="recipes_com_wrapper" @click="handleParentClick">
    <el-table
      :data="mData"
      border
      :span-method="spanMethod"
      :cell-style="{ padding: '2px 0' }"
      :header-cell-style="{ padding: '4px 0', height: 'unset' }"
      size="mini"
      header-row-class-name="recipes_header"
      :cell-class-name="cellClassName"
      :style="`outline: ${currentDay === num ? '1px solid #d53950' : 'none'}`"
    >
      <el-table-column prop="type" :width="80" align="center">
        <template slot="header">
          <div class="pointer_style" @click="handleOnResetCurrentDay">
            {{ `第${numDay}天` }}
          </div>
        </template>
        <template slot-scope="scope">
          <el-popover
            placement="top"
            trigger="hover"
            :title="typeFormatter(scope.row)"
          >
            <div>
              <el-button
                size="mini"
                type="primary"
                @click="handleOnMenuTypeClick(scope.row)"
                >添加
              </el-button>
              <el-button
                size="mini"
                type="primary"
                @click="handleOnPaste(scope.row.type)"
                v-if="canCopyMenuTypes.includes(scope.row.type)"
                >粘贴</el-button
              >
            </div>
            <div
              slot="reference"
              style="font-weight: bold; font-size: 14px; cursor: pointer"
            >
              {{ typeFormatter(scope.row) }}
            </div>
          </el-popover>
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
            <div class="pointer_style" @click="handleOnAdd">菜品</div>
          </el-tooltip>
        </template>
        <template slot-scope="scope">
          <el-popover placement="right" trigger="hover" :title="scope.row.name">
            <div>
              <div style="margin-bottom: 8px">
                <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-refresh"
                  class="fun_button"
                  @click="handleOnReplace(scope.row)"
                >
                  替换
                </el-button>
                <el-button
                  type="danger"
                  size="mini"
                  icon="el-icon-delete"
                  class="fun_button"
                  @click="handleOnDelete(scope.row)"
                >
                  删除
                </el-button>
              </div>
              <div>
                <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-document-copy"
                  class="fun_button"
                  @click="handleOnCopy(scope.row)"
                >
                  复制
                </el-button>
                <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-document-copy"
                  class="fun_button"
                  @click="handleOnSetting(scope.row)"
                >
                  修改餐类
                </el-button>
              </div>
            </div>
            <div class="pointer_style" slot="reference">
              {{ scope.row.name }}
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="食材" prop="igdName" align="center">
        <template slot-scope="scope">
          <span
            v-if="
              (scope.row.nameSpan.colspan === 1 &&
                scope.row.nameSpan.rowspan === 1) ||
              (scope.row.igdType !== '15' && scope.row.igdType !== '14')
            "
          >
            {{ scope.row.igdName }}
          </span>
          <el-popover
            v-else
            placement="right"
            trigger="hover"
            :title="scope.row.igdName"
          >
            <el-button
              type="danger"
              size="mini"
              icon="el-icon-delete"
              class="fun_button"
              @click="handleOnIgdDelete(scope.row)"
              >删除</el-button
            >
            <div class="pointer_style" slot="reference">
              {{ scope.row.igdName }}
            </div>
          </el-popover>
        </template>
      </el-table-column>
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
        :width="60"
        align="center"
      >
        <template slot="header">
          <div class="pointer_style">
            <div>蛋白质</div>
            <div>/100g</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="脂肪/100g"
        prop="fatRatio"
        :width="60"
        align="center"
      >
        <template slot="header">
          <div class="pointer_style">
            <div>脂肪</div>
            <div>/100g</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="碳水/100g"
        prop="carbonRatio"
        :width="60"
        align="center"
      >
        <template slot="header">
          <div class="pointer_style">
            <div>碳水</div>
            <div>/100g</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="蛋白质含量"
        prop="proteinRatio"
        :width="60"
        align="center"
        :formatter="nutriFormatter"
      >
        <template slot="header">
          <div class="pointer_style">
            <div>蛋白质</div>
            <div>含量</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="脂肪含量"
        prop="fatRatio"
        :width="60"
        align="center"
        :formatter="nutriFormatter"
      >
        <template slot="header">
          <div class="pointer_style">
            <div>脂肪</div>
            <div>含量</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="碳水含量"
        prop="carbonRatio"
        :width="60"
        align="center"
        :formatter="nutriFormatter"
      >
        <template slot="header">
          <div class="pointer_style">
            <div>碳水</div>
            <div>含量</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="做法" prop="methods" />
      <el-table-column label="备注" prop="remark">
        <template slot-scope="scope">
          <div
            v-if="!scope.row.remark"
            class="empty_remark"
            @click="handleOnEditRemark(scope.row)"
          >
            <em class="el-icon-plus" />
          </div>
          <span
            class="pointer_style"
            @click="handleOnEditRemark(scope.row)"
            v-else
            >{{ scope.row.remark }}</span
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 添加菜品抽屉 -->
    <AddDishesDrawer ref="drawerRef" @onConfirm="handleOnDishesConfirm" />
    <!-- 菜品复用菜单 -->
    <DishesSettingDialog
      ref="settingDialogRef"
      @onConfirm="handleOnSettingConfirm"
    />
    <!-- 备注弹窗 -->
    <RemarkDialog ref="remarkDialogRef" @onConfirm="handleOnRemarkConfirm" />
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
import DishesSettingDialog from "./DishesSettingDialog";
import VueScrollTo from "vue-scrollto";
import RemarkDialog from "./RemarkDialog";

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
    numDay: {
      type: Number,
      default: 0,
    },
  },
  components: {
    EditableText,
    EditableUnit,
    AddDishesDrawer,
    DishesSettingDialog,
    RemarkDialog,
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
                lastNameHit =
                  arr[arr.length - 1].name === cur.name &&
                  arr[arr.length - 1].type === cur.type;
                if (lastNameHit) {
                  let namePos = arr.length - 1;
                  for (let i = namePos; i >= 0; i--) {
                    if (arr[i].name !== cur.name) {
                      break;
                    }
                    namePos = i;
                  }
                  arr[namePos].nameSpan.rowspan += 1;
                }
              }

              // console.log(cur);
              arr.push({
                id: cur.id,
                dishesId: cur.dishesId,
                menuId: cur.menuId,
                name: cur.name,
                type: cur.type,
                isMain: cur.isMain,
                methods: cur.methods,
                remark: cur.remark,
                igdId: igd.id,
                igdType: igd.type,
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
              });
            });
          }
          return arr;
        }, []);
      // console.log(mData);

      return mData;
    },
    ...mapGetters(["typeDict"]),
    ...mapState([
      "currentDay",
      "copyData",
      "fontSize",
      "canCopyMenuTypes",
      "recipesId",
      "notRecIgds",
    ]),
  },
  methods: {
    cellClassName({ row, column, rowIndex, columnIndex }) {
      // console.log({ row, column, rowIndex, columnIndex });
      if (!columnIndex) {
        return "recipes_first_col";
      } else {
        return `recipes_cell recipes_cell_${this.fontSize} ${
          columnIndex === 2 && this.notRecIgds.includes(row.igdId)
            ? "warning_heightlight"
            : ""
        }`;
      }
    },
    handleParentClick(e) {
      // 校验某天
      this.setCurrentDay({ currentDay: this.num });
    },
    spanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        return row.typeSpan;
      } else if (
        columnIndex === 1 ||
        columnIndex === 11 ||
        columnIndex === 12
      ) {
        return row.nameSpan;
      }
    },
    typeFormatter(row) {
      return this.typeDict[row.type];
    },
    nutriFormatter(row, col) {
      return ((row.weight / 100) * row[col.property]).toFixed(1);
    },
    handleOnResetCurrentDay(e) {
      e.stopPropagation();
      // 取消高亮
      this.resetCurrentDay({ currentDay: this.num });
      VueScrollTo.scrollTo(`#recipes${this.num}`, 500, {
        container: "#recipes_content",
      });
    },
    handleOnAdd() {
      // console.log(this.num);
      this.$refs.drawerRef.showDrawer({});
    },
    handleOnReplace(data) {
      this.$refs.drawerRef.showDrawer({ data, numDay: this.numDay });
    },
    handleOnDelete(data) {
      // console.log(data);
      this.deleteDishes({ num: this.num, id: data.id });
    },
    handleOnCopy(data) {
      // console.log(data);
      this.setCopyData({ num: this.num, id: data.id })
        .then((msg) => {
          this.$message.success(msg);
        })
        .catch((err) => {
          this.$message.error(err);
        });
    },
    handleOnPaste(type) {
      // console.log(this.copyData);
      if (this.copyData) {
        const data = {
          ...JSON.parse(JSON.stringify(this.copyData)),
          type,
        };
        if (!this.recipesId) {
          // 未生成食谱时拷贝
          data.id = new Date().getTime();
        }
        this.addDishes({
          num: this.num,
          data,
        }).catch((err) => {
          this.$message.error(err);
        });
      }
    },
    handleOnWeightChange(data, weight) {
      // console.log({ data, weight });
      this.updateDishes({
        num: this.num,
        id: data.id,
        dishesId: data.dishesId,
        igdId: data.igdId,
        weight,
        actionType: "weight",
      });
    },
    handleOnCustomUnitChange(data, { cusWeight, cusUnit }) {
      // console.log({ data, cusWeight, cusUnit });
      const { id, dishesId, igdId } = data;
      this.updateDishes({
        num: this.num,
        id,
        dishesId,
        igdId,
        cusWeight,
        cusUnit,
        actionType: "unit",
      });
    },
    handleOnDishesConfirm({ type, data }) {
      // console.log(data);
      if (type === "add") {
        this.addDishes({
          num: this.num,
          data,
        }).catch((err) => {
          this.$message.error(err);
        });
      } else if (type === "replace") {
        this.updateDishes({
          num: this.num,
          data,
          actionType: type,
        }).catch((err) => {
          this.$message.error(err);
        });
      }
    },
    handleOnSetting(data) {
      this.$refs.settingDialogRef.showDialog({ numDay: this.numDay, data });
    },
    handleOnSettingConfirm({ type, id }) {
      this.updateDishes({
        num: this.num,
        id,
        type,
        actionType: "menuType",
      });
    },
    handleOnMenuTypeClick(data) {
      this.$refs.drawerRef.showDrawer({ type: data.type, numDay: this.numDay });
    },
    handleOnIgdDelete(data) {
      // console.log(data);
      const { id, igdId } = data;
      this.updateDishes({
        num: this.num,
        id,
        igdId,
        actionType: "delIgd",
      });
    },
    handleOnEditRemark(data) {
      this.$refs.remarkDialogRef.showDialog(data);
    },
    handleOnRemarkConfirm(data) {
      // console.log(data);
      const { id, remark } = data;
      this.updateDishes({
        num: this.num,
        id,
        remark,
        actionType: "remark",
      });
    },
    ...mapActions([
      "updateDishes",
      "addDishes",
      "deleteDishes",
      "replaceDishes",
      "setCopyData",
    ]),
    ...mapMutations(["setCurrentDay", "resetCurrentDay"]),
  },
};
</script>
<style lang="scss" scoped>
.recipes_com_wrapper {
  margin-bottom: 24px;
  padding: 1px;

  .pointer_style {
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

.empty_remark {
  cursor: pointer;
  text-align: center;
  em {
    visibility: hidden;
    font-size: 14px;
    font-weight: bold;
    &:hover {
      color: #1890ff;
    }
  }

  &:hover {
    em {
      visibility: visible;
    }
  }
}

.el-table .cell {
  line-height: 18px;
}

.recipes_header {
  & > th {
    background: #d53950 !important;
    color: white !important;
    font-weight: bold;
  }
}

.recipes_first_col {
  background: #d53950 !important;
  color: white !important;
  font-weight: bold;
}

.recipes_cell {
  font-weight: bold;
  color: #595959;
}

.recipes_cell_8 {
  font-size: 8px;
}
.recipes_cell_10 {
  font-size: 10px;
}
.recipes_cell_12 {
  font-size: 12px;
}
.recipes_cell_14 {
  font-size: 14px;
}
.recipes_cell_16 {
  font-size: 16px;
}
.recipes_cell_18 {
  font-size: 18px;
}

.warning_heightlight {
  background: #d66969;
  color: blue;
}
</style>
