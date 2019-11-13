<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="表名称" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="请输入表名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="表描述" prop="tableComment">
        <el-input
          v-model="queryParams.tableComment"
          placeholder="请输入表描述"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间">
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-download"
          size="mini"
          @click="handleBatchGenTable"
          v-hasPermi="['system:post:add']"
        >生成</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-upload"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:post:add']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleEditTable"
          v-hasPermi="['system:post:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:post:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table
      :data="tableList"
      style="width: 100% ;margin-top: 10px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="30"></el-table-column>
      <el-table-column label="序号" align="center" prop="tableId" width="50px"/>
      <el-table-column label="表名称" align="center" prop="tableName" />
      <el-table-column label="表描述" align="center" prop="tableComment" />
      <el-table-column label="实体" align="center" prop="className" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="更新时间" align="center" prop="updateTime" />
      <el-table-column
        label="操作"
        align="center"
        min-width="180px"
      >
        <template slot-scope="scope">
          <el-button type="text" size="small" icon="el-icon-view" @click="handlepreView(scope.row)">预览</el-button>
          <el-button type="text" size="small" icon="el-icon-edit" @click="handleEditTable(scope.row)">编辑</el-button>
          <el-button type="text" size="small" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          <el-button type="text" size="small" icon="el-icon-download"@click="handleGenTable(scope.row.tableName)">生成代码</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 预览界面 -->
    <el-dialog :title="title" :visible.sync="preDialog.open" width="900px" lock-scroll>
      <el-tabs v-model="vm.activeName" type="card">
        <el-tab-pane :label="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))" v-for="(value, key) in preDialog.preData" :key="key">
          <pre>{{ value }}</pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
    <import-dailog ref="import" @ok="handleQuery"/>
  </div>
</template>

<script>
import { listTable,listdbTable,importTable,previewTable,removeTable } from '@/api/tool/gen'
import importDailog from './dailog/importDailog';
import { downLoadZip } from '@/utils/zipdownload'
export default {
  components: { importDailog },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      tableNames: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 岗位表格数据
      tableList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态数据字典
      statusOptions: [],
      // 日期范围
      dateRange: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: undefined,
        tableComment: undefined
      },
      // 表单参数
      form: {},
      vm:{
        activeName:"0"
      },
      preDialog:{
        open: false,
        preData:{}
      },
    };
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询岗位列表 */
    getList() {
      this.loading = true;
      listTable(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.tableList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      console.log("handleQuery")
      this.queryParams.pageNum = 1;
      this.getList();
    },
    handleGenTable(tabelname){
      downLoadZip('tool/gen/genCode/'+tabelname, 'ruoyi')
    },
    handleBatchGenTable(tabelname){
      downLoadZip('tool/gen/batchGenCode?tables='+this.tableNames.join(','), 'ruoyi')
    },
    handleImport(){
      this.$refs.import.show()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handlepreView(row){
      previewTable(row.tableId).then(response => {
        this.title="代码预览"
        this.preDialog.preData = response.data
        this.preDialog.open=true
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tableId)
      this.tableNames = selection.map(item => item.tableName)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleEditTable(row) {
      const tableId = row.tableId || this.ids[0]
      console.log(tableId)
      this.$router.push({ path: '/gen/edit',query: { tableId: tableId } });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const tableIds = row.tableId || this.ids.join(',');
      this.$confirm('是否确认删除表编号为"' + tableIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return removeTable({ids:tableIds});
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    }
  }
};
</script>
