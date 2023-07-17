<template>
  <div>
    <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="服务信息" align="center" prop="name" width="150px"/>
      <el-table-column label="售价(元)" align="center" prop="price"/>
      <el-table-column label="服务类型" align="center" prop="type">
        <template slot-scope="scope">
          {{ typeList.find(i => i.value === scope.row.type).label }}
        </template>
      </el-table-column>
      <el-table-column label="服务次数" align="center" prop="num"/>
      <el-table-column label="服务时长(分)" align="center" prop="time"/>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listServeConfig } from "@/api/psychology/serveConfig";

export default {
  props: {
    id: {
      type: String
    },
  },
  data() {
    return {
      ids: [],
      list: [],
      total: 0,
      loading: false,
      typeList: this.$constants.typeList,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderByColumn: 'z_index',
        isAsc: 'descending',
        nId: ''
      },
    }
  },
  created() {
    console.log(this.id)
    this.getList()
  },
  methods: {
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
      this.$emit('setServe', this.ids)
    },
    /** 查询咨询服务配置列表 */
    getList() {
      this.loading = true;
      this.queryParams.nId = this.id
      listServeConfig(this.queryParams).then(response => {
        this.list = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    }
  }
}
</script>

<style scoped>

</style>
