<template>
  <div class="template_view_wrapper">
    <div class="header">
      <div style="fontweight: bold">选择模板</div>
      <el-button size="mini" @click="handleOnBackClick">返回</el-button>
    </div>
    <div class="content">
      <!-- 筛选 -->
      <el-form :model="queryParams" ref="queryForm" :inline="true">
        <el-form-item label="搜索内容" prop="name">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入模板名称或备注"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="营养师" prop="nutritionistId">
          <el-select
            v-model="queryParams.nutritionistId"
            placeholder="请选择营养师"
            clearable
            size="small"
          >
            <el-option
              v-for="dict in nutritionistIdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="营养师助理" prop="nutriAssisId">
          <el-select
            v-model="queryParams.nutriAssisId"
            placeholder="请选择营养师助理"
            clearable
            size="small"
          >
            <el-option
              v-for="dict in nutriAssisIdOptions"
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
            >搜索</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
            >重置</el-button
          >
        </el-form-item>
      </el-form>
      <!-- 模板列表 -->
      <el-table
        v-loading="loading"
        :data="dataList"
        highlight-current-row
        @current-change="handleCurrentChange"
      >
        <el-table-column label="模板名称" align="center" prop="name" />
        <el-table-column label="营养师" align="center" prop="nutritionist" />
        <el-table-column label="营养师助理" align="center" prop="nutriAssis" />
        <el-table-column label="备注" prop="remark" align="center" />
      </el-table>

      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>
<script>
import { listRecipesTemplate } from "@/api/custom/recipesTemplate";
import { mapState } from "vuex";
export default {
  name: "TemplateView",
  data() {
    return {
      loading: false,
      dataList: [],
      total: 0,
      queryParams: {
        name: null,
        nutritionistId: null,
        nutriAssisId: null,
        pageNum: 1,
        pageSize: 10,
        reviewStatus: 2,
      },
    };
  },
  props: ["view"],
  computed: {
    ...mapState({
      //营养师
      nutritionistIdOptions: (state) =>
        state.global.nutritionistIdOptions.slice(1),
      //营养师助理
      nutriAssisIdOptions: (state) => state.global.nutriAssisIdOptions.slice(1),
    }),
  },
  methods: {
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
    handleOnBackClick() {
      this.$emit("update:view", 0);
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        reviewStatus: 2,
      };
    },
    getList() {
      this.loading = true;
      const params = JSON.parse(JSON.stringify(this.queryParams));
      params.keys = (params.name || "").split(" ");
      listRecipesTemplate(params).then((res) => {
        if (res.code === 200) {
          this.dataList = res.rows;
          this.total = res.total;
          this.loading = false;
        }
      });
    },
    handleCurrentChange(data) {
      this.$emit("onSelect", data);
      setTimeout(() => {
        this.$emit("update:view", 0);
      }, 500);
    },
  },
  watch: {
    view: function (val, oldVal) {
      if (val === 1) {
        this.getList();
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.template_view_wrapper {
  height: 100%;

  .header {
    height: 32px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 8px;
  }
  .content {
    height: calc(100% - 32px);
    overflow: auto;
  }
}
</style>
