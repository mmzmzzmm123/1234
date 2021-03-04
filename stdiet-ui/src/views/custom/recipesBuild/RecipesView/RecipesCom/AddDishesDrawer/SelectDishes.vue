<template>
  <div>
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="菜品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入菜品名称"
          clearable
          size="mini"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="菜品类型" prop="type">
        <el-select
          :disabled="lockType"
          v-model="queryParams.type"
          placeholder="请选择菜品类型"
          clearable
          size="mini"
        >
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
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

    <el-table
      v-loading="loading"
      size="mini"
      :data="dishesList"
      height="600"
      highlight-current-row
      @current-change="handleCurrentChange"
    >
      <el-table-column label="菜品名称" align="center" prop="name" />
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
import { listDishes } from "@/api/custom/dishes";
import { createNamespacedHelpers } from "vuex";
const { mapState } = createNamespacedHelpers("recipes");
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
        reviewStatus: "yes",
      },
    };
  },
  components: {
    AutoHideInfo,
  },
  computed: {
    ...mapState(["typeOptions"]),
  },
  methods: {
    getList({ type }) {
      // console.log('getList')
      if (type) {
        this.lockType = true;
        this.queryParams.type = type;
      }
      this.loading = true;
      listDishes(this.queryParams).then((result) => {
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
      this.handleQuery();
    },
    typeFormat(row, column) {
      return !row.type
        ? ""
        : row.type
            .split(",")
            .map((type) => this.selectDictLabel(this.typeOptions, type));
    },
  },
};
</script>
