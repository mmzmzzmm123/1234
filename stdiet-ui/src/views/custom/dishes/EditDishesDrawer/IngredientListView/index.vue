<template>
  <div>
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入食材名称"
          clearable
          size="mini"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类别" prop="type">
        <el-select
          v-model="queryParams.type"
          placeholder="请选择食材类别"
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
      border
      :data="ingredientList"
      :cell-style="{ padding: 0 }"
      :header-cell-style="{ padding: 0, height: 'unset' }"
      @current-change="handleCurrentChange"
    >
      <el-table-column label="食材名称" align="center" prop="name" />
      <el-table-column
        label="食材类别"
        align="center"
        prop="type"
        :formatter="typeFormat"
      />
      <el-table-column prop="proteinRatio" label="蛋白质/100g" align="center" />
      <el-table-column prop="fatRatio" label="脂肪/100g" align="center" />
      <el-table-column prop="carbonRatio" label="碳水/100g" align="center" />
    </el-table>

    <pagination
      v-show="total > 0"
      :background="false"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
      layout="total, prev, pager, next"
    />
  </div>
</template>
<script>
import { listIngredient } from "@/api/custom/ingredient";
export default {
  name: "IngredientListView",
  data() {
    return {
      loading: false,
      total: 0,
      ingredientList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        area: null,
        reviewStatus: "yes",
      },
    };
  },
  props: ["typeOptions", "visible"],
  watch: {},
  methods: {
    getList() {
      this.loading = true;
      listIngredient(this.queryParams).then((response) => {
        this.ingredientList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    reset() {
      this.resetForm("queryForm");
    },
    // 食材类别字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleCurrentChange(data) {
      this.$emit("onSelect", data);
    },
  },
};
</script>
