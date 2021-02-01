<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="70px"
    >
      <el-form-item label="评估月份" prop="month">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.month"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择评估月份"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="学年学期" prop="xnxq">
        <el-input
          v-model="queryParams.xnxq"
          placeholder="请输入评估学年学期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评估班级" prop="classid">
        <el-input
          v-model="queryParams.classid"
          placeholder="请输入评估班级"
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
          v-hasPermi="['benyi:dayflowassessmentplan:add']"
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
          v-hasPermi="['benyi:dayflowassessmentplan:edit']"
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
          v-hasPermi="['benyi:dayflowassessmentplan:remove']"
          >删除</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="dayflowassessmentplanList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="评估学校" align="center" prop="deptId" />
      <el-table-column label="评估月份" align="center" prop="month" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.month, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="评估学年学期" align="center" prop="xnxq" />
      <el-table-column label="评估班级" align="center" prop="classid" />
      <el-table-column label="评估内容" align="center" prop="connent" />
      <el-table-column
        label="评估时间"
        align="center"
        prop="starttime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.starttime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
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
            v-hasPermi="['benyi:dayflowassessmentplan:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:dayflowassessmentplan:remove']"
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

    <!-- 添加或修改幼儿园一日流程评估计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="评估学校" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入评估学校" />
        </el-form-item>
        <el-form-item label="评估月份" prop="month">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.month"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择评估月份"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="学年学期" prop="xnxq">
          <el-input v-model="form.xnxq" placeholder="请输入评估学年学期" />
        </el-form-item>
        <el-form-item label="评估班级" prop="classid">
          <el-input v-model="form.classid" placeholder="请输入评估班级" />
        </el-form-item>
        <el-form-item label="评估内容" prop="connent">
          <el-input v-model="form.connent" placeholder="请输入评估内容" />
        </el-form-item>
        <el-form-item label="评估时间" prop="starttime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.starttime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择评估时间"
          >
          </el-date-picker>
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
  listDayflowassessmentplan,
  getDayflowassessmentplan,
  delDayflowassessmentplan,
  addDayflowassessmentplan,
  updateDayflowassessmentplan,
} from "@/api/benyi/dayflowassessmentplan";

export default {
  name: "Dayflowassessmentplan",
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
      // 幼儿园一日流程评估计划表格数据
      dayflowassessmentplanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined,
        month: undefined,
        xnxq: undefined,
        classid: undefined,
        connent: undefined,
        starttime: undefined,
        createUserid: undefined,
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
    /** 查询幼儿园一日流程评估计划列表 */
    getList() {
      this.loading = true;
      listDayflowassessmentplan(this.queryParams).then((response) => {
        this.dayflowassessmentplanList = response.rows;
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
        deptId: undefined,
        month: undefined,
        xnxq: undefined,
        classid: undefined,
        connent: undefined,
        starttime: undefined,
        createUserid: undefined,
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
      this.title = "添加幼儿园一日流程评估计划";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getDayflowassessmentplan(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改幼儿园一日流程评估计划";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateDayflowassessmentplan(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addDayflowassessmentplan(this.form).then((response) => {
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
        '是否确认删除幼儿园一日流程评估计划编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delDayflowassessmentplan(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
  },
};
</script>
