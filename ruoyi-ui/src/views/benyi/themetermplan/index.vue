<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="学校id" prop="schoolid">
        <el-input
          v-model="queryParams.schoolid"
          placeholder="请输入学校id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="班级id" prop="classid">
        <el-input
          v-model="queryParams.classid"
          placeholder="请输入班级id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="月份" prop="month">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.month"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择月份"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="学年学期" prop="xnxq">
        <el-input
          v-model="queryParams.xnxq"
          placeholder="请输入学年学期"
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
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option label="请选择字典生成" value />
        </el-select>
      </el-form-item>
      <el-form-item label="审批人" prop="spr">
        <el-input
          v-model="queryParams.spr"
          placeholder="请输入审批人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审批时间" prop="sptime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.sptime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择审批时间"
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
    </el-row>

    <el-table v-loading="loading" :data="termplanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="学校id" align="center" prop="schoolid" />
      <el-table-column label="班级id" align="center" prop="classid" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="月份" align="center" prop="startmonth" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startmonth, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束月份" align="center" prop="endmonth" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endmonth, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="学年学期" align="center" prop="xnxq" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="创建人" align="center" prop="createuserid" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="审批人" align="center" prop="spr" />
      <el-table-column label="审批时间" align="center" prop="sptime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sptime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
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

    <!-- 添加或修改主题整合学期计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学校id" prop="schoolid">
          <el-input v-model="form.schoolid" placeholder="请输入学校id" />
        </el-form-item>
        <el-form-item label="班级id" prop="classid">
          <el-input v-model="form.classid" placeholder="请输入班级id" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="月份" prop="startmonth">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.startmonth"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择月份"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束月份" prop="endmonth">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.endmonth"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束月份"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="学年学期" prop="xnxq">
          <el-input v-model="form.xnxq" placeholder="请输入学年学期" />
        </el-form-item>
        <el-form-item label="创建人" prop="createuserid">
          <el-input v-model="form.createuserid" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审批人" prop="spr">
          <el-input v-model="form.spr" placeholder="请输入审批人" />
        </el-form-item>
        <el-form-item label="审批时间" prop="sptime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.sptime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择审批时间"
          ></el-date-picker>
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
  listTermplan,
  getTermplan,
  delTermplan,
  addTermplan,
  updateTermplan,
} from "@/api/benyi/themetermplan";

export default {
  name: "Termplan",
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
      // 主题整合学期计划表格数据
      termplanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        schoolid: undefined,
        classid: undefined,
        name: undefined,
        startmonth: undefined,
        endmonth: undefined,
        xnxq: undefined,
        createuserid: undefined,
        status: undefined,
        spr: undefined,
        sptime: undefined,
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
    /** 查询主题整合学期计划列表 */
    getList() {
      this.loading = true;
      listTermplan(this.queryParams).then((response) => {
        this.termplanList = response.rows;
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
        schoolid: undefined,
        classid: undefined,
        name: undefined,
        startmonth: undefined,
        endmonth: undefined,
        xnxq: undefined,
        remark: undefined,
        createuserid: undefined,
        createTime: undefined,
        status: "0",
        spr: undefined,
        sptime: undefined,
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
      this.title = "添加主题整合学期计划";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTermplan(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改主题整合学期计划";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTermplan(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTermplan(this.form).then((response) => {
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
        '是否确认删除主题整合学期计划编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delTermplan(ids);
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