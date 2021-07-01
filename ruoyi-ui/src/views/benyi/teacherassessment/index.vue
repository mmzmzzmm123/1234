<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="70px"
    >
      <el-form-item label="教师编号" prop="jsid">
        <el-input
          v-model="queryParams.jsid"
          placeholder="请输入教师编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="班级编号" prop="classid">
        <el-input
          v-model="queryParams.classid"
          placeholder="请输入班级编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所在部门" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入所在部门"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核月份" prop="month">
        <el-input
          v-model="queryParams.month"
          placeholder="请输入考核月份"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="一日流程比例" prop="yrlcbl">
        <el-input
          v-model="queryParams.yrlcbl"
          placeholder="请输入一日流程比例"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教师考勤比例" prop="jskqbl">
        <el-input
          v-model="queryParams.jskqbl"
          placeholder="请输入教师考勤比例"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="幼儿考勤比例" prop="yekqbl">
        <el-input
          v-model="queryParams.yekqbl"
          placeholder="请输入幼儿考勤比例"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事故比例" prop="sgbl">
        <el-input
          v-model="queryParams.sgbl"
          placeholder="请输入事故比例"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="卫生比例" prop="wsbl">
        <el-input
          v-model="queryParams.wsbl"
          placeholder="请输入卫生比例"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总分" prop="zfbl">
        <el-input
          v-model="queryParams.zfbl"
          placeholder="请输入总分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
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
          v-hasPermi="['benyi:teacherassessment:add']"
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
          v-hasPermi="['benyi:teacherassessment:edit']"
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
          v-hasPermi="['benyi:teacherassessment:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['benyi:teacherassessment:export']"
          >导出</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="teacherassessmentList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="教师编号" align="center" prop="jsid" />
      <el-table-column label="班级编号" align="center" prop="classid" />
      <el-table-column label="所在部门" align="center" prop="deptId" />
      <el-table-column label="考核月份" align="center" prop="month" />
      <el-table-column label="一日流程比例" align="center" prop="yrlcbl" />
      <el-table-column label="教师考勤比例" align="center" prop="jskqbl" />
      <el-table-column label="幼儿考勤比例" align="center" prop="yekqbl" />
      <el-table-column label="事故比例" align="center" prop="sgbl" />
      <el-table-column label="卫生比例" align="center" prop="wsbl" />
      <el-table-column label="总分" align="center" prop="zfbl" />
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
            v-hasPermi="['benyi:teacherassessment:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:teacherassessment:remove']"
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

    <!-- 添加或修改教师月绩效考核对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="教师编号" prop="jsid">
          <el-input v-model="form.jsid" placeholder="请输入教师编号" />
        </el-form-item>
        <el-form-item label="班级编号" prop="classid">
          <el-input v-model="form.classid" placeholder="请输入班级编号" />
        </el-form-item>
        <el-form-item label="所在部门" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入所在部门" />
        </el-form-item>
        <el-form-item label="考核月份" prop="month">
          <el-input v-model="form.month" placeholder="请输入考核月份" />
        </el-form-item>
        <el-form-item label="一日流程比例" prop="yrlcbl">
          <el-input v-model="form.yrlcbl" placeholder="请输入一日流程比例" />
        </el-form-item>
        <el-form-item label="教师考勤比例" prop="jskqbl">
          <el-input v-model="form.jskqbl" placeholder="请输入教师考勤比例" />
        </el-form-item>
        <el-form-item label="幼儿考勤比例" prop="yekqbl">
          <el-input v-model="form.yekqbl" placeholder="请输入幼儿考勤比例" />
        </el-form-item>
        <el-form-item label="事故比例" prop="sgbl">
          <el-input v-model="form.sgbl" placeholder="请输入事故比例" />
        </el-form-item>
        <el-form-item label="卫生比例" prop="wsbl">
          <el-input v-model="form.wsbl" placeholder="请输入卫生比例" />
        </el-form-item>
        <el-form-item label="总分" prop="zfbl">
          <el-input v-model="form.zfbl" placeholder="请输入总分" />
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
  listTeacherassessment,
  getTeacherassessment,
  delTeacherassessment,
  addTeacherassessment,
  updateTeacherassessment,
  exportTeacherassessment,
} from "@/api/benyi/teacherassessment";

export default {
  name: "Teacherassessment",
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
      // 教师月绩效考核表格数据
      teacherassessmentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jsid: undefined,
        classid: undefined,
        deptId: undefined,
        month: undefined,
        yrlcbl: undefined,
        jskqbl: undefined,
        yekqbl: undefined,
        sgbl: undefined,
        wsbl: undefined,
        zfbl: undefined,
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
    /** 查询教师月绩效考核列表 */
    getList() {
      this.loading = true;
      listTeacherassessment(this.queryParams).then((response) => {
        this.teacherassessmentList = response.rows;
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
        jsid: undefined,
        classid: undefined,
        deptId: undefined,
        month: undefined,
        yrlcbl: undefined,
        jskqbl: undefined,
        yekqbl: undefined,
        sgbl: undefined,
        wsbl: undefined,
        zfbl: undefined,
        createTime: undefined,
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
      this.title = "添加教师月绩效考核";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTeacherassessment(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改教师月绩效考核";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTeacherassessment(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTeacherassessment(this.form).then((response) => {
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
        '是否确认删除教师月绩效考核编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delTeacherassessment(ids);
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
      this.$confirm("是否确认导出所有教师月绩效考核数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportTeacherassessment(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>