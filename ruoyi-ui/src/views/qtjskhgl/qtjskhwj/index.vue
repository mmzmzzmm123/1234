<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <el-form-item label="方案编号" prop="faid">
        <el-input
          v-model="queryParams.faid"
          placeholder="请输入方案编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核年份" prop="nf">
        <el-input
          v-model="queryParams.nf"
          placeholder="请输入考核年份"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核类型" prop="khlx">
        <el-input
          v-model="queryParams.khlx"
          placeholder="请输入考核类型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="附件类型" prop="fjlx">
        <el-input
          v-model="queryParams.fjlx"
          placeholder="请输入附件类型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件名称" prop="filename">
        <el-input
          v-model="queryParams.filename"
          placeholder="请输入文件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件路径" prop="filepath">
        <el-input
          v-model="queryParams.filepath"
          placeholder="请输入文件路径"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入部门"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="createUserid">
        <el-input
          v-model="queryParams.createUserid"
          placeholder="请输入创建人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['qtjskhgl:qtjskhwj:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['qtjskhgl:qtjskhwj:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['qtjskhgl:qtjskhwj:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['qtjskhgl:qtjskhwj:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="qtjskhwjList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标识" align="center" prop="id" />
      <el-table-column label="方案编号" align="center" prop="faid" />
      <el-table-column label="考核年份" align="center" prop="nf" />
      <el-table-column label="考核类型" align="center" prop="khlx" />
      <el-table-column label="附件类型" align="center" prop="fjlx" />
      <el-table-column label="文件名称" align="center" prop="filename" />
      <el-table-column label="文件路径" align="center" prop="filepath" />
      <el-table-column label="部门" align="center" prop="deptId" />
      <el-table-column label="创建人" align="center" prop="createUserid" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['qtjskhgl:qtjskhwj:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['qtjskhgl:qtjskhwj:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改群体教师考核文件管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="方案编号" prop="faid">
          <el-input v-model="form.faid" placeholder="请输入方案编号" />
        </el-form-item>
        <el-form-item label="考核年份" prop="nf">
          <el-input v-model="form.nf" placeholder="请输入考核年份" />
        </el-form-item>
        <el-form-item label="考核类型" prop="khlx">
          <el-input v-model="form.khlx" placeholder="请输入考核类型" />
        </el-form-item>
        <el-form-item label="附件类型" prop="fjlx">
          <el-input v-model="form.fjlx" placeholder="请输入附件类型" />
        </el-form-item>
        <el-form-item label="文件名称" prop="filename">
          <el-input v-model="form.filename" placeholder="请输入文件名称" />
        </el-form-item>
        <el-form-item label="文件路径" prop="filepath">
          <el-input v-model="form.filepath" placeholder="请输入文件路径" />
        </el-form-item>
        <el-form-item label="部门" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入部门" />
        </el-form-item>
        <el-form-item label="创建人" prop="createUserid">
          <el-input v-model="form.createUserid" placeholder="请输入创建人" />
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
import {
  listQtjskhwj,
  getQtjskhwj,
  delQtjskhwj,
  addQtjskhwj,
  updateQtjskhwj,
  exportQtjskhwj,
} from "@/api/qtjskhgl/qtjskhwj";

export default {
  name: "Qtjskhwj",
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 群体教师考核文件管理表格数据
      qtjskhwjList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        nf: null,
        khlx: null,
        fjlx: null,
        filename: null,
        filepath: null,
        deptId: null,
        createUserid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询群体教师考核文件管理列表 */
    getList() {
      this.loading = true;
      listQtjskhwj(this.queryParams).then((response) => {
        this.qtjskhwjList = response.rows;
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
        id: null,
        faid: null,
        nf: null,
        khlx: null,
        fjlx: null,
        filename: null,
        filepath: null,
        deptId: null,
        createUserid: null,
        createTime: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加群体教师考核文件管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getQtjskhwj(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改群体教师考核文件管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateQtjskhwj(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addQtjskhwj(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除群体教师考核文件管理编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delQtjskhwj(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有群体教师考核文件管理数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportQtjskhwj(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
