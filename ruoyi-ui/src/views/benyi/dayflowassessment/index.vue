<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="70px"
    >
      <el-form-item label="所属计划" prop="planid">
        <el-input
          v-model="queryParams.planid"
          placeholder="请输入所属计划"
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
      <el-form-item label="学年学期" prop="xnxq">
        <el-input
          v-model="queryParams.xnxq"
          placeholder="请输入评估学年学期"
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
          v-hasPermi="['benyi:dayflowassessment:add']"
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
          v-hasPermi="['benyi:dayflowassessment:edit']"
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
          v-hasPermi="['benyi:dayflowassessment:remove']"
          >删除</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="dayflowassessmentList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="所属计划" align="center" prop="planid" />
      <!-- <el-table-column label="学校编号" align="center" prop="deptId" /> -->
      <el-table-column label="班级编号" align="center" prop="classid" />
      <!-- <el-table-column label="班长编号" align="center" prop="bzbh" /> -->
      <el-table-column label="班长姓名" align="center" prop="bzxm" />
      <!-- <el-table-column label="配班教师" align="center" prop="pbbh" /> -->
      <el-table-column label="配班教师姓名" align="center" prop="pbxm" />
      <!-- <el-table-column label="助理教师" align="center" prop="zlbh" /> -->
      <el-table-column label="助理教师姓名" align="center" prop="zlxm" />
      <el-table-column label="学年学期" align="center" prop="xnxq" />
      <el-table-column label="评估标准编号" align="center" prop="bzid" />
      <el-table-column label="扣分值" align="center" prop="kfz" />
      <el-table-column label="扣分次数" align="center" prop="kfcs" />
      <el-table-column label="评估对象" align="center" prop="pgdx" />
      <!-- <el-table-column label="执行人" align="center" prop="createUserid" /> -->
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
            v-hasPermi="['benyi:dayflowassessment:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:dayflowassessment:remove']"
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

    <!-- 添加或修改幼儿园一日流程评估对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属计划" prop="planid">
          <el-input v-model="form.planid" placeholder="请输入所属计划" />
        </el-form-item>
        <el-form-item label="学校编号" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入学校编号" />
        </el-form-item>
        <el-form-item label="班级编号" prop="classid">
          <el-input v-model="form.classid" placeholder="请输入班级编号" />
        </el-form-item>
        <el-form-item label="班长编号" prop="bzbh">
          <el-input v-model="form.bzbh" placeholder="请输入班长编号" />
        </el-form-item>
        <el-form-item label="班长姓名" prop="bzxm">
          <el-input v-model="form.bzxm" placeholder="请输入班长姓名" />
        </el-form-item>
        <el-form-item label="配班教师" prop="pbbh">
          <el-input v-model="form.pbbh" placeholder="请输入配班教师" />
        </el-form-item>
        <el-form-item label="配班教师姓名" prop="pbxm">
          <el-input v-model="form.pbxm" placeholder="请输入配班教师姓名" />
        </el-form-item>
        <el-form-item label="助理教师" prop="zlbh">
          <el-input v-model="form.zlbh" placeholder="请输入助理教师" />
        </el-form-item>
        <el-form-item label="助理教师姓名" prop="zlxm">
          <el-input v-model="form.zlxm" placeholder="请输入助理教师姓名" />
        </el-form-item>
        <el-form-item label="评估学年学期" prop="xnxq">
          <el-input v-model="form.xnxq" placeholder="请输入评估学年学期" />
        </el-form-item>
        <el-form-item label="评估标准编号" prop="bzid">
          <el-input v-model="form.bzid" placeholder="请输入评估标准编号" />
        </el-form-item>
        <el-form-item label="扣分值" prop="kfz">
          <el-input v-model="form.kfz" placeholder="请输入扣分值" />
        </el-form-item>
        <el-form-item label="扣分次数" prop="kfcs">
          <el-input v-model="form.kfcs" placeholder="请输入扣分次数" />
        </el-form-item>
        <el-form-item label="评估对象" prop="pgdx">
          <el-input v-model="form.pgdx" placeholder="请输入评估对象" />
        </el-form-item>
        <el-form-item label="执行人" prop="createUserid">
          <el-input v-model="form.createUserid" placeholder="请输入执行人" />
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
  listDayflowassessment,
  getDayflowassessment,
  delDayflowassessment,
  addDayflowassessment,
  updateDayflowassessment,
} from "@/api/benyi/dayflowassessment";

export default {
  name: "Dayflowassessment",
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
      // 幼儿园一日流程评估表格数据
      dayflowassessmentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planid: undefined,
        deptId: undefined,
        classid: undefined,
        bzbh: undefined,
        bzxm: undefined,
        pbbh: undefined,
        pbxm: undefined,
        zlbh: undefined,
        zlxm: undefined,
        xnxq: undefined,
        bzid: undefined,
        kfz: undefined,
        kfcs: undefined,
        pgdx: undefined,
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
    /** 查询幼儿园一日流程评估列表 */
    getList() {
      this.loading = true;
      listDayflowassessment(this.queryParams).then((response) => {
        this.dayflowassessmentList = response.rows;
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
        planid: undefined,
        deptId: undefined,
        classid: undefined,
        bzbh: undefined,
        bzxm: undefined,
        pbbh: undefined,
        pbxm: undefined,
        zlbh: undefined,
        zlxm: undefined,
        xnxq: undefined,
        bzid: undefined,
        kfz: undefined,
        kfcs: undefined,
        pgdx: undefined,
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
      this.title = "添加幼儿园一日流程评估";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getDayflowassessment(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改幼儿园一日流程评估";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateDayflowassessment(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addDayflowassessment(this.form).then((response) => {
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
        '是否确认删除幼儿园一日流程评估编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delDayflowassessment(ids);
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