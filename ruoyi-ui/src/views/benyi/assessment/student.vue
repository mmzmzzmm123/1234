<template>
  <div class="app-container">
    <el-table
      v-loading="loading"
      :data="assessmentcontentList"
      row-key="id"
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="name" label="评估内容"></el-table-column>
      <el-table-column label="是否达标" align="center">
        <template slot-scope="scope">
          <el-checkbox v-if="scope.row.iselement == 'Y'" v-model="checked"
            >是</el-checkbox
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import {
  listAssessmentcontent,
  getAssessmentcontent,
  delAssessmentcontent,
  addAssessmentcontent,
  updateAssessmentcontent,
  exportAssessmentcontent,
} from "@/api/benyi/assessmentcontent";

export default {
  name: "Assessmentstudent",

  data() {
    return {
      checked: true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 评估内容表格数据
      assessmentcontentList: [],
      // 查询参数
      queryParams: {
        parentid: undefined,
        name: undefined,
        iselement: undefined,
        scope: undefined,
        sort: undefined,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询评估内容列表 */
    getList() {
      this.loading = true;
      listAssessmentcontent(this.queryParams).then((response) => {
        this.assessmentcontentList = this.handleTree(response.rows, "id");
        this.loading = false;
      });
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
  },
};
</script>
<style lang="scss" scoped>
// 禁止复制
div {
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}
</style>