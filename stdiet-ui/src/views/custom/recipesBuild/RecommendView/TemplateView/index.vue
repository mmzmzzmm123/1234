<template>
  <div class="template_view_wrapper">
    <div class="header">
      <el-button size="mini" @click="handleOnBackClick">返回</el-button>
    </div>
    <div class="content">
      <h2>选择模板</h2>
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
export default {
  name: "TemplateView",
  data() {
    return {
      loading: false,
      dataList: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        reviewStatus: 2,
      },
    };
  },
  props: ["view"],
  methods: {
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
      listRecipesTemplate(this.queryParams).then((res) => {
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
    justify-content: flex-end;
    align-items: center;
  }
  .content {
    height: calc(100% - 32px);
    padding-top: 12px;
  }
}
</style>
