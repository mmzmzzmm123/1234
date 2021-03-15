<template>
  <el-form @submit.native.prevent>
    <el-form-item label="菜品名">
      <span style="color: #262626; font-size: 16px; font-weight: bold">{{
        name
      }}</span>
    </el-form-item>
    <el-form-item label="菜品类型">
      <el-radio-group v-model="mType" @change="handleOnTypeChange">
        <el-radio
          v-for="item in typeOptions"
          :key="item.dictValue"
          :label="item.dictValue"
          >{{ item.dictLabel }}</el-radio
        >
      </el-radio-group>
    </el-form-item>
    <el-form-item label="食材分量">
      <el-table
        :data="igdList"
        border
        show-summary
        size="mini"
        :summary-method="getSummaries"
      >
        <el-table-column prop="name" label="食材" align="center" />
        <el-table-column label="分量估算" align="center">
          <template slot-scope="scope">
            <EditableUnit
              :weight="scope.row.cusWeight"
              :unit="scope.row.cusUnit"
              @onChange="(val) => handleOnCustomUnitChange(scope.row, val)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="weight" label="重量(g)" align="center">
          <template slot-scope="scope">
            <EditableText
              :value="scope.row.weight"
              @onChange="(val) => handleOnWeightChange(scope.row, val)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="proteinRatio" label="P/100g" align="center" />
        <el-table-column prop="fatRatio" label="F/100g" align="center" />
        <el-table-column prop="carbonRatio" label="C/100g" align="center" />
      </el-table>
    </el-form-item>
    <el-form-item label="推荐人群">
      <el-tag
        style="margin-right: 4px"
        v-for="rec in recTags"
        :key="rec"
        type="success"
      >
        {{ rec }}
      </el-tag>
    </el-form-item>
    <el-form-item label="忌口人群">
      <el-tag
        style="margin-right: 4px"
        v-for="notRec in notRecTags"
        :key="notRec"
        type="danger"
      >
        {{ notRec }}
      </el-tag>
    </el-form-item>
  </el-form>
</template>
<script>
import produce from "immer";
import EditableText from "../EditableText";
import EditableUnit from "../EditableUnit";
export default {
  name: "ConfigDishes",
  data() {
    return {
      mType: this.type,
    };
  },
  props: {
    name: {
      type: String,
      default: "",
    },
    type: {
      type: String,
      default: "",
    },
    igdList: {
      type: Array,
      default: [],
    },
    typeOptions: {
      type: Array,
      default: [],
    },
    notRecTags: {
      type: Array,
      default: [],
    },
    recTags: {
      type: Array,
      default: [],
    },
  },
  components: {
    EditableText,
    EditableUnit,
  },
  watch: {
    type(val) {
      this.mType = val;
    },
  },
  computed: {},
  methods: {
    handleOnTypeChange(type) {
      this.$emit("onChange", { type });
    },
    handleOnWeightChange(data, val) {
      // console.log({ data, val });
      this.$emit("onChange", {
        igdList: produce(this.igdList, (draftState) => {
          const tarIgd = draftState.find((obj) => obj.id === data.id);
          if (tarIgd) {
            tarIgd.weight = val;
          }
        }),
      });
    },
    handleOnCustomUnitChange(data, val) {
      // console.log({ data, val });
      this.$emit("onChange", {
        igdList: produce(this.igdList, (draftState) => {
          const tarIgd = draftState.find((obj) => obj.id === data.id);
          if (tarIgd) {
            tarIgd.cusWeight = val.cusWeight;
            tarIgd.cusUnit = val.cusUnit;
          }
        }),
      });
    },
    getSummaries(param) {
      const { columns, data } = param;
      return columns.reduce(
        (arr, cur, idx) => {
          if (idx > 1) {
            arr[idx] = data.reduce((acc, dAcc) => {
              if (idx === 2) {
                return acc + parseFloat(dAcc.weight);
              }
              return parseFloat(
                (acc + (dAcc[cur.property] * dAcc.weight) / 100).toFixed(1)
              );
            }, 0);
          }
          return arr;
        },
        ["合计"]
      );
    },
  },
};
</script>
