<template>
  <el-drawer
    :title="title"
    :visible.sync="open"
    :wrapperClosable="false"
    :before-close="handleClose"
    size="50%"
    :close-on-press-escape="false"
  >
    <div class="edit_dishes_drawer_wrapper">
      <el-row class="content_detail" v-loading="entireLoading">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-col :span="12">
            <el-form-item label="菜品名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入菜品名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审核状态" prop="reviewStatus">
              <el-select
                v-model="form.reviewStatus"
                placeholder="请选择审核状态"
                clearable
              >
                <el-option
                  v-for="dict in reviewStatusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="菜品种类" prop="dishClass">
              <el-cascader
                filterable
                v-model="form.dishClass"
                :options="dishClassOptions"
                :props="{ expandTrigger: 'hover' }"
                placeholder="请选择菜品种类"
              ></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="菜品类型" prop="type">
              <el-select
                v-model="form.type"
                placeholder="请选择菜品类型"
                multiple
              >
                <el-option
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否主食" prop="isMain">
              <el-radio-group v-model="form.isMain">
                <el-radio :label="0">是</el-radio>
                <el-radio :label="1">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="待选食材">
              <IngredientListView
                ref="igdRef"
                :typeOptions="ingTypeOptions"
                @onSelect="handleOnIgdSelect"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="已选食材" prop="igdList">
              <el-table
                :data="form.igdList"
                border
                show-summary
                :cell-style="{ padding: '2px 0' }"
                :header-cell-style="{ padding: 0, height: 'unset' }"
                :summary-method="getSummaries"
              >
                <el-table-column prop="name" label="食材" align="center" />
                <el-table-column label="通俗计量" align="center">
                  <template slot-scope="scope">
                    <span class="cus-unit">
                      <el-select size="mini" v-model="scope.row.cusWeight">
                        <el-option
                          v-for="dict in cusWeightOptions"
                          :key="dict.dictValue"
                          :label="dict.dictLabel"
                          :value="parseInt(dict.dictValue)"
                        />
                      </el-select>
                      <el-select size="mini" v-model="scope.row.cusUnit">
                        <el-option
                          v-for="dict in cusUnitOptions"
                          :key="dict.dictValue"
                          :label="dict.dictLabel"
                          :value="parseInt(dict.dictValue)"
                        />
                      </el-select>
                    </span>
                  </template>
                </el-table-column>
                <el-table-column prop="weight" label="重量(g)" align="center">
                  <template slot-scope="scope">
                    <el-input-number
                      class="weight"
                      v-model="scope.row.weight"
                      size="mini"
                      controls-position="right"
                      :min="0"
                      :step="5"
                    />
                  </template>
                </el-table-column>
                <el-table-column
                  prop="proteinRatio"
                  label="蛋白质/100g"
                  align="center"
                />
                <el-table-column
                  prop="fatRatio"
                  label="脂肪/100g"
                  align="center"
                />
                <el-table-column
                  prop="carbonRatio"
                  label="碳水/100g"
                  align="center"
                />
                <el-table-column label="热量(kcal)" align="center">
                  <template slot-scope="scope">
                    {{
                      `${(
                        ((scope.row.proteinRatio * scope.row.weight) / 100) *
                          4 +
                        ((scope.row.fatRatio * scope.row.weight) / 100) * 9 +
                        ((scope.row.carbonRatio * scope.row.weight) / 100) * 4
                      ).toFixed(1)}`
                    }}
                  </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                  <template slot-scope="scope">
                    <el-button
                      type="text"
                      size="mini"
                      @click="handleOnDelete(scope.row)"
                      >删除</el-button
                    >
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="推荐人群" v-if="!!selRec.length">
              <el-tag
                style="margin-right: 4px"
                v-for="rec in selRec"
                :key="rec"
                type="success"
              >
                {{ rec }}
              </el-tag>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="忌口人群" v-if="!!selNotRec.length">
              <el-tag
                style="margin-right: 4px"
                v-for="notRec in selNotRec"
                :key="notRec"
                type="danger"
              >
                {{ notRec }}
              </el-tag>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="做法" prop="methods">
              <el-input
                v-model="form.methods"
                type="textarea"
                placeholder="请输入内容"
                rows="4"
              />
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </div>
  </el-drawer>
</template>
<script>
import { addDishes, updateDishes, getDishes } from "@/api/custom/dishes";
import IngredientListView from "./IngredientListView";

export default {
  name: "EditDishesDrawer",
  components: {
    IngredientListView,
  },
  created() {
    this.getDicts("cus_ing_type").then((response) => {
      this.ingTypeOptions = response.data;
    });
    this.getDicts("cus_cus_unit").then((response) => {
      this.cusUnitOptions = response.data;
    });
    this.getDicts("cus_cus_weight").then((response) => {
      this.cusWeightOptions = response.data;
    });
  },
  data() {
    return {
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 通俗单位
      cusUnitOptions: [],
      //
      cusWeightOptions: [],
      // 食材类别字典
      ingTypeOptions: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "菜品名称不能为空", trigger: "blur" },
        ],
        type: [
          { required: true, message: "菜品类型不能为空", trigger: "blur" },
        ],
        igdList: [{ required: true, message: "食材不能为空", trigger: "blur" }],
      },
      //
      entireLoading: false,
      // 选中的食材分量列表
      //
      selRec: [],
      selNotRec: [],
      selRecIds: [],
      selNotRecIds: [],
    };
  },
  props: [
    "typeOptions",
    "reviewStatusOptions",
    "dishClassBigOptions",
    "dishClassOptions",
    "dishClassSmallOptions",
  ],
  watch: {
    "form.igdList": function (val) {
      const selRec = [];
      const selNotRec = [];
      const selRecIds = [];
      const selNotRecIds = [];
      val.forEach((obj) => {
        if (obj.rec) {
          obj.rec
            .split(",")
            .forEach((rec) => !selRec.includes(rec) && selRec.push(rec));
        }
        if (obj.notRec) {
          obj.notRec
            .split(",")
            .forEach(
              (notRec) => !selNotRec.includes(notRec) && selNotRec.push(notRec)
            );
        }
        if (obj.recIdsStr) {
          obj.recIdsStr
            .split(",")
            .forEach(
              (recId) => !selRecIds.includes(recId) && selRecIds.push(recId)
            );
        }
        if (obj.notRecIdsStr) {
          obj.notRecIdsStr
            .split(",")
            .forEach(
              (notRecId) =>
                !selNotRecIds.includes(notRecId) && selNotRecIds.push(notRecId)
            );
        }
      });
      this.selRec = selRec;
      this.selNotRec = selNotRec;
      this.selRecIds = selRecIds;
      this.selNotRecIds = selNotRecIds;
    },

    showNotRec() {
      return !!this.selNotRec.length;
    },
  },
  methods: {
    showDrawer(id) {
      this.open = true;
      this.title = id ? "修改菜品" : "新建菜品";
      this.reset();
      if (id) {
        this.fetchDishesDetail(id);
      }
      this.$nextTick(() => {
        this.$refs.igdRef.getList();
      });
    },
    fetchDishesDetail(id) {
      getDishes(id).then((response) => {
        this.form = response.data;
        this.form.dishClass = [this.form.bigClass, this.form.smallClass];
        this.form.type = this.form.type ? this.form.type.split(",") : [];
      });
    },
    handleOnIgdSelect(data) {
      if (this.form.igdList.some((obj) => obj.id === data.id)) {
        this.$message.error(`已存在食材${data.name}`);
        return;
      }
      this.form.igdList.push({
        ...data,
        weight: 100,
        cusUnit: 1,
        cusWeight: 1,
      });
    },
    handleOnDelete(data) {
      this.form.igdList = this.form.igdList.filter((obj) => obj.id !== data.id);
    },
    cusUnitFormat(row, column) {
      return this.selectDictLabel(this.cusUnitOptions, row.type);
    },
    cusWeightFormat(row, column) {
      return this.selectDictLabel(this.cusWeightOptions, row.cusWeight);
    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        type: [],
        dishClass: [],
        methods: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        igdList: [],
        isMain: 1,
      };
      this.selRec = [];
      this.selNotRec = [];
      this.resetForm("form");
      this.$nextTick(() => {
        this.$refs.igdRef.reset();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          const data = JSON.parse(JSON.stringify(this.form));
          // console.log({ data });
          data.type = data.type.join(",");
          data.recIds = this.selRecIds;
          data.notRecIds = this.selNotRecIds;
          if (data.id != null) {
            updateDishes(data).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.$emit("onSuccess");
              }
            });
          } else {
            addDishes(data).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.$emit("onSuccess");
              }
            });
          }
        }
      });
    },

    getSummaries(param) {
      const { columns, data } = param;
      // console.log(data);
      return columns.reduce(
        (arr, cur, idx) => {
          if (idx > 1 && idx !== 7) {
            if (idx === 6) {
              arr[6] = (arr[3] * 4 + arr[4] * 9 + arr[5] * 4).toFixed(1);
            } else {
              arr[idx] = data.reduce((acc, dAcc) => {
                if (idx === 2) {
                  return acc + parseFloat(dAcc.weight);
                }
                return parseFloat(
                  (
                    acc +
                    (dAcc[cur.property] * parseFloat(dAcc.weight)) / 100
                  ).toFixed(1)
                );
              }, 0);
            }
          }
          return arr;
        },
        ["合计"]
      );
    },
    handleClose(done) {
      this.reset();
      done();
    },
  },
};
</script>
<style lang="scss" scoped>
.edit_dishes_drawer_wrapper {
  height: 100%;
  display: flex;
  flex-direction: column;

  .content_detail {
    flex: 1 1 0;
    padding: 12px;
    overflow: auto;
  }

  .dialog-footer {
    flex: 0 0 45px;
    display: inline-flex;
    align-items: center;
    justify-content: flex-end;
    padding: 0 12px;
  }
}
</style>

<style lang="scss">
#el-drawer__title {
  & > span:focus {
    outline: 0;
  }
}

.el-transfer-panel__filter {
  margin: 2px;
}

.el-transfer-panel__list.is-filterable {
  padding-bottom: 28px;
}

.cus-unit {
  display: inline-flex;
  .el-input-number--mini {
    width: 38px;
  }

  .el-input-number {
    .el-input-number__decrease {
      display: none;
    }
    .el-input-number__increase {
      display: none;
    }

    .el-input {
      width: 38px;
    }

    .el-input .el-input__inner {
      padding: 0;
      border-radius: 0;
      border: unset;
      border-bottom: 1px solid #dcdfe6;
    }
  }

  .el-select {
    .el-input__suffix {
      display: none;
    }

    .el-input__inner {
      padding: 0 4px;
      /* border: unset; */
      text-align: center;
    }
  }
}

.weight {
  width: 70px;

  .el-input .el-input__inner {
    padding: 0 32px 0 4px;
  }
}

.pagination-container {
  margin-top: 0;
}
</style>
