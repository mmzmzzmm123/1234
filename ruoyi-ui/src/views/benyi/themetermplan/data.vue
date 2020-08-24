<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="所属计划" prop="tpid">
        <el-input
          v-model="queryParams.tpid"
          placeholder="请输入所属计划"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主题内容" prop="themeconent">
        <el-input
          v-model="queryParams.themeconent"
          placeholder="请输入主题内容"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="createuserid">
        <el-input
          v-model="queryParams.createuserid"
          placeholder="请输入创建人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="创建时间">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.创建时间"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建人"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="修改人" prop="updateuserid">
        <el-input
          v-model="queryParams.updateuserid"
          placeholder="请输入修改人"
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
          v-hasPermi="['benyi:themetermplan:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:themetermplan:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:themetermplan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['benyi:themetermplan:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="termplanitemList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="所属计划" align="center" prop="tpid" />
      <el-table-column label="主题内容" align="center" prop="themeconent" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="创建人" align="center" prop="createuserid" />
      <el-table-column label="创建人" align="center" prop="创建时间" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.创建时间, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改人" align="center" prop="updateuserid" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:themetermplan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:themetermplan:remove']"
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

    <!-- 添加或修改主题整合学期计划明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属计划" prop="tpid">
          <el-input v-model="form.tpid" placeholder="请输入所属计划" />
        </el-form-item>
        <el-form-item label="主题内容" prop="themeconent">
          <el-input v-model="form.themeconent" placeholder="请输入主题内容" />
        </el-form-item>
        <el-form-item label="创建人" prop="createuserid">
          <el-input v-model="form.createuserid" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="创建人" prop="创建时间">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.创建时间"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择创建人"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="修改人" prop="updateuserid">
          <el-input v-model="form.updateuserid" placeholder="请输入修改人" />
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
  listTermplanitem,
  getTermplanitem,
  delTermplanitem,
  addTermplanitem,
  updateTermplanitem,
  exportTermplanitem,
} from "@/api/benyi/themetermplanitem";

export default {
  name: "Termplanitem",
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
      // 主题整合学期计划明细表格数据
      termplanitemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tpid: undefined,
        themeconent: undefined,
        createuserid: undefined,
        创建时间: undefined,
        updateuserid: undefined,
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
    /** 查询主题整合学期计划明细列表 */
    getList() {
      this.loading = true;
      listTermplanitem(this.queryParams).then((response) => {
        this.termplanitemList = response.rows;
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
        tpid: undefined,
        themeconent: undefined,
        remark: undefined,
        createuserid: undefined,
        创建时间: undefined,
        updateuserid: undefined,
        updateTime: undefined,
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
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加主题整合学期计划明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTermplanitem(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改主题整合学期计划明细";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTermplanitem(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTermplanitem(this.form).then((response) => {
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
        '是否确认删除主题整合学期计划明细编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delTermplanitem(ids);
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
      this.$confirm("是否确认导出所有主题整合学期计划明细数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportTermplanitem(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>