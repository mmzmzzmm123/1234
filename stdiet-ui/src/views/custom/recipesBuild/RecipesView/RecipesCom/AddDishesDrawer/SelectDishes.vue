<template>
  <div>
    <el-row :gutter="15">
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        label-width="68px"
        @submit.native.prevent
      >
        <el-col :span="5">
          <el-form-item label="菜品名称" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入菜品名称"
              clearable
              size="mini"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="包含食材" prop="physical">
            <el-input
              v-model="queryParams.igdName"
              placeholder="请输入食材名称"
              clearable
              size="mini"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="菜品种类" prop="dishClass">
            <el-cascader
              filterable
              clearable
              size="mini"
              v-model="dishClassQueryParam"
              :options="dishClassOptions"
              :props="{ expandTrigger: 'hover' }"
              :show-all-levels="true"
              placeholder="请选择菜品种类"
            ></el-cascader>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="菜品类型" prop="type">
            <el-select
              :disabled="lockType"
              v-model="queryParams.type"
              placeholder="请选择菜品类型"
              clearable
              size="mini"
              width="120px"
            >
              <el-option
                v-for="dict in typeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="推荐体征" prop="physical">
            <el-cascader
              :disabled="lockType"
              v-model="queryParams.physical"
              placeholder="请选择推荐体征"
              clearable
              :options="physicalSignsOptions"
              size="mini"
              width="120px"
              @change="handleOnPhysicalSignsChange"
            />
          </el-form-item>
        </el-col>
        <el-form-item>
          <el-button
            type="cyan"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
            >搜索
          </el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
            >重置
          </el-button>
        </el-form-item>
      </el-form>
    </el-row>

    <el-table
      v-loading="loading"
      size="mini"
      :data="dishesList"
      height="550"
      highlight-current-row
      @current-change="handleCurrentChange"
    >
      <el-table-column label="菜品名称" align="center" prop="name" />
      <el-table-column label="菜品种类" align="center" prop="bigClass">
        <template slot-scope="scope">
          <AutoHideMessage :data="dishClassFormat(scope.row)" :maxLength="10" />
        </template>
      </el-table-column>
      <el-table-column label="菜品类型" align="center" prop="type">
        <template slot-scope="scope">
          <AutoHideInfo :data="typeFormat(scope.row)" />
        </template>
      </el-table-column>
      <el-table-column label="包含食材" align="center">
        <template slot-scope="scope">
          <div v-for="igd in scope.row.igdList" :key="igd.id">
            {{ igd.name }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="推荐人群" align="center">
        <template slot-scope="scope">
          <AutoHideInfo :data="scope.row.recTags" :line="0" />
        </template>
      </el-table-column>
      <el-table-column label="忌口人群" align="center">
        <template slot-scope="scope">
          <AutoHideInfo :data="scope.row.notRecTags" :line="0" />
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      layout="total, prev, pager, next"
      @pagination="getList"
    />
  </div>
</template>
<script>
import AutoHideInfo from "@/components/AutoHideInfo";
import AutoHideMessage from "@/components/AutoHideMessage";
import { listDishes } from "@/api/custom/dishes";
import { createNamespacedHelpers } from "vuex";
const { mapState, mapGetters } = createNamespacedHelpers("recipes");
export default {
  name: "SelectDishes",
  props: [],
  data() {
    return {
      loading: false,
      lockType: false,
      total: 0,
      dishesList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        smallClass: null,
        bigClass: null,
        reviewStatus: "yes",
        physical: null,
      },
      //菜品种类查询参数
      dishClassQueryParam: [],
    };
  },
  components: {
    AutoHideInfo,
    AutoHideMessage,
  },
  computed: {
    ...mapState([
      "typeOptions",
      "dishBigClassOptions",
      "dishSmallClassOptions",
      "physicalSignsOptions",
    ]),
    ...mapGetters(["dishClassOptions"]),
  },
  methods: {
    getList({ type }) {
      // console.log('getList')
      if (type) {
        this.lockType = true;
        this.queryParams.type = type;
      }
      if (
        this.dishClassQueryParam != null &&
        this.dishClassQueryParam.length > 0
      ) {
        this.queryParams.smallClass = this.dishClassQueryParam[1];
      } else {
        this.queryParams.smallClass = null;
      }
      this.loading = true;
      const qParams = {
        ...this.queryParams,
      };
      if (this.queryParams.physical) {
        qParams.physical = this.queryParams.physical[1];
      }
      listDishes(qParams).then((result) => {
        this.dishesList = result.rows.map((d) => {
          const recTags = [],
            notRecTags = [];
          d.igdList.forEach((igd) => {
            if (igd.rec) {
              igd.rec.split(",").forEach((rec) => {
                if (!recTags.includes(rec)) {
                  recTags.push(rec);
                }
              });
            }
            if (igd.notRec) {
              igd.notRec.split(",").forEach((notRec) => {
                if (!notRecTags.includes(notRec)) {
                  notRecTags.push(notRec);
                }
              });
            }
          });
          return {
            ...d,
            recTags,
            notRecTags,
          };
        });
        this.total = result.total;
        this.loading = false;
      });
    },
    clean() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        igdName: null,
        reviewStatus: "yes",
      };
      this.dishesList = [];
      this.lockType = false;
    },
    handleCurrentChange(data) {
      this.$emit("onChange", data);
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList({});
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.dishClassQueryParam = [];
      this.handleQuery();
    },
    typeFormat(row, column) {
      return !row.type
        ? ""
        : row.type
            .split(",")
            .map((type) => this.selectDictLabel(this.typeOptions, type));
    },
    //菜品种类翻译
    dishClassFormat(row) {
      if (row.bigClass > 0 && row.smallClass > 0) {
        let bigClassName = this.selectDictLabel(
          this.dishBigClassOptions,
          row.bigClass
        );
        let smallClassName = this.selectDictLabel(
          this.dishSmallClassOptions,
          row.smallClass
        );
        return bigClassName + "/" + smallClassName;
      }
      return "";
    },
    handleOnPhysicalSignsChange(val) {
      const [typeId, id] = val;
      console.log(val);
    },
  },
};
</script>
