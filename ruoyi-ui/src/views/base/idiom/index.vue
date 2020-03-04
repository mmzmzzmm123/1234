<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="成语" prop="idiomName">
        <el-input
          v-model="queryParams.idiomName"
          placeholder="请输入成语"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="难易程度" prop="idiomLevel">
        <el-input
          v-model="queryParams.idiomLevel"
          placeholder="请输入难易程度"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['base:idiom:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['base:idiom:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['base:idiom:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['base:idiom:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" stripe border fit highlight-current-row :data="idiomList" @selection-change="handleSelectionChange" @sort-change='tableSortChange' @row-dblclick="handleUpdate">
    <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="行号" width="80" align="center">
            <template slot-scope="scope">{{scope.$index + 1}}</template>
    </el-table-column>
      <el-table-column label="id" align="center" prop="id" v-if="false" />
      <el-table-column label="成语" align="center" prop="idiomName" min-width="25%" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="拼音" align="center" prop="idiomPinyin" min-width="25%" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="注释" align="center" prop="idiomDesc" min-width="88%" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="难易程度" align="center" prop="idiomLevel" min-width="10%" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="备注" align="center" prop="remark" min-width="10%" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="操作" align="center" min-width="20%" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            plain
            size="small"
            type="primary"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['base:idiom:edit']"
          >修改</el-button>
          <el-button
            plain
            size="small"
            type="primary"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['base:idiom:remove']"
          >删除</el-button>
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

    <!-- 添加或修改成语存放对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="成语" prop="idiomName">
          <el-input v-model="form.idiomName" placeholder="请输入成语" />
        </el-form-item>
        <el-form-item label="拼音" prop="idiomPinyin">
          <el-input v-model="form.idiomPinyin" placeholder="请输入拼音" />
        </el-form-item>
        <el-form-item label="注释" prop="idiomDesc">
          <el-input v-model="form.idiomDesc"  :rows="8" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="难易程度" prop="idiomLevel">
          <el-input v-model="form.idiomLevel" placeholder="请输入难易程度" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listIdiom, getIdiom, delIdiom, addIdiom, updateIdiom, exportIdiom } from "@/api/base/idiom";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 成语存放表格数据
      idiomList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderByColumn: undefined,
        isAsc: undefined,
        idiomName: undefined,
        idiomDesc: undefined,
        idiomLevel: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
      /** 通用成语存放排序 */
      tableSortChange(column) {
          this.queryParams.pageNum = 1;
          if (column.order === 'descending') {
              this.queryParams.orderByColumn = column.prop;
              this.queryParams.isAsc = 'desc';
          } else {
              this.queryParams.orderByColumn = column.prop;
              this.queryParams.isAsc = 'asc';
          }
          this.getList();
      },
    /** 查询成语存放列表 */
    getList() {
      this.loading = true;
      listIdiom(this.queryParams).then(response => {
        this.idiomList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        idiomName: undefined,
        idiomPinyin: undefined,
        idiomDesc: undefined,
        idiomLevel: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        remark: undefined
      };
      this.resetForm("form");
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加成语存放";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getIdiom(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改成语存放";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateIdiom(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addIdiom(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除成语存放编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delIdiom(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有成语存放数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportIdiom(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
