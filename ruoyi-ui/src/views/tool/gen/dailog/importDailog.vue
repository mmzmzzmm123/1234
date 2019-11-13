<template>
  <!-- 导入表 -->
    <el-dialog title="导入表" :visible.sync="visible" width="800px">
      <el-form :model="queryParams" ref="importQueryForm" :inline="true" label-width="68px">
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
        <el-button type="text" style="margin-left: 20px" @click="handleImportSearch">搜索</el-button>
        <el-button type="text" style="margin-left: 20px" @click="handleImportReset">重置</el-button>
      </el-form>
      <el-row>
        <el-table
          :data="dbTableList"
          @selection-change="handleSelectionChange"
          style="width: 100%;">
          <el-table-column
            type="selection"
            width="55"></el-table-column>
          <el-table-column
            prop="tableName"
            label="表名称"
            >
          </el-table-column>
          <el-table-column
            prop="tableComment"
            label="表描述"
            >
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="创建时间"
          >
          </el-table-column>
          <el-table-column
            prop="updateTime"
            label="更新时间"
          >
          </el-table-column>
        </el-table>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="handleDbList"
        />
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleImportTable">确 定</el-button>
        <el-button @click="handleImportCancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { listdbTable,importTable } from '@/api/tool/gen'
export default {
  data() {
    return {
      visible: false,
      center: true,
      // 选中数组
      ids: [],
      tables: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      queryParams:
        {
          tableName:'',
          tableComment:'',
          pageNum: 1,
          pageSize: 10
        },
      dbTableList:[],
      total:0
    };
  },
  created() {
    this.handleImportSearch()
  },
  methods: {
    show(){
      this.visible=true
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tableId)
      this.tables = selection.map(item => item.tableName)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    handleImportSearch()
    {
      this.queryParams.pageNum=1
      this.handleDbList()
    },
    handleDbList()
    {
      listdbTable(this.queryParams).then(res => {
        if(res.code===200){
          this.dbTableList = res.rows
          this.total=res.total
        }
      })
    },
    handleImportReset(){
      this.resetForm("importQueryForm");
    },
    handleImportTable()//导入table
    {
      importTable({tables:this.tables.join(",")}).then(res => {
        this.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible=false
          this.$emit('ok');
        }
      })
    },
    handleImportCancel(){
      this.visible=false
    },
  }
};
</script>
