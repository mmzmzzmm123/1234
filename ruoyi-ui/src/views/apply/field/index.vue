<template>
  <div class="transverse app-container">
    <el-table v-loading="loading" :data="projectList">
      <el-table-column label="序号" align="center" prop="id" width="100" />
      <el-table-column
        label="项目名称"
        align="center"
        prop="name"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="项目类型"
        align="center"
        prop="type"
        :formatter="(row)=>{return row.type == 0 ? '纵向项目' : '横向项目'}"
        width="100"
      />
      <el-table-column
        label="项目状态"
        align="center"
        prop="status"
        :formatter="(row)=>{return row.status == 0 ? '公告申请中' : '已立项'}"
        width="100"
      />
      <el-table-column
        label="项目预算"
        align="center"
        prop="budget"
        width="100"
      />
      <el-table-column label="开始申请时间" align="center" prop="startTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束申请时间" align="center" prop="endTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
    />
  </div>
</template>

<script>
export default {
  name: 'TransverseIndex',
  components: {
    
  },
  data() {
    return {
      //默认选项卡
      activeName: 'first',
      // 遮罩层
      loading: false,
      //项目表格数据
      projectList:[],
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
    }
  },
  methods: {
    handleClick(tab, event) {
      // console.log(tab, event);
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const noticeIds = row.noticeId || this.ids
      this.$confirm('是否确认删除公告编号为"' + noticeIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delNotice(noticeIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    }
  },
  mounted(){
    // console.log('mouted ...')
    for(let i = 1;i<=10;i++){
      this.projectList.push({
        id:i,
        name:`科研项目${i}`,
        type:(i%2 == 0) ? 0 : 1,
        status:(i%2 == 0) ? 0 : 1,
        budget:'100万',
        startTime:'2021/3/26',
        endTime:'2022/1/1'
      })
    }
    this.total = 10;
  }
}
</script>

<style lang="scss" scoped>
.science{
  // padding: 0 20px;
  .el-tabs{
    min-height: 636px;
  }
}
@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
