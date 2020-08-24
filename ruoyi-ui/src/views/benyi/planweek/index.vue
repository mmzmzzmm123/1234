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
      <el-form-item label="计划名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入计划名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="starttime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.starttime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择开始时间"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endtime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.endtime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择结束时间"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="本周主题" prop="themeofweek">
        <el-input
          v-model="queryParams.themeofweek"
          placeholder="请输入本周主题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教学目标(社会)" prop="jxmbSh">
        <el-input
          v-model="queryParams.jxmbSh"
          placeholder="请输入教学目标(社会)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教学目标(语言)" prop="jxmbYy">
        <el-input
          v-model="queryParams.jxmbYy"
          placeholder="请输入教学目标(语言)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教学目标(健康)" prop="jxmbJk">
        <el-input
          v-model="queryParams.jxmbJk"
          placeholder="请输入教学目标(健康)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教学目标(科学)" prop="jxmbKx">
        <el-input
          v-model="queryParams.jxmbKx"
          placeholder="请输入教学目标(科学)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教学目标(艺术)" prop="jxmbYs">
        <el-input
          v-model="queryParams.jxmbYs"
          placeholder="请输入教学目标(艺术)"
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
      <el-form-item label="当前状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择当前状态" clearable size="small">
          <el-option label="请选择字典生成" value />
        </el-select>
      </el-form-item>
      <el-form-item label="审核人" prop="shrid">
        <el-input
          v-model="queryParams.shrid"
          placeholder="请输入审核人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核时间" prop="shtime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.shtime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择审核时间"
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
          v-hasPermi="['system:planweek:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:planweek:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:planweek:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:planweek:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="planweekList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="学校id" align="center" prop="schoolid" />
      <el-table-column label="班级id" align="center" prop="classid" />
      <el-table-column label="计划名称" align="center" prop="name" />
      <el-table-column label="开始时间" align="center" prop="starttime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.starttime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="本周主题" align="center" prop="themeofweek" />
      <el-table-column label="教学目标(社会)" align="center" prop="jxmbSh" />
      <el-table-column label="教学目标(语言)" align="center" prop="jxmbYy" />
      <el-table-column label="教学目标(健康)" align="center" prop="jxmbJk" />
      <el-table-column label="教学目标(科学)" align="center" prop="jxmbKx" />
      <el-table-column label="教学目标(艺术)" align="center" prop="jxmbYs" />
      <el-table-column label="创建人" align="center" prop="createuserid" />
      <el-table-column label="当前状态" align="center" prop="status" />
      <el-table-column label="审核人" align="center" prop="shrid" />
      <el-table-column label="审核时间" align="center" prop="shtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.shtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:planweek:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:planweek:remove']"
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

    <!-- 添加或修改周计划(家长和教育部门)对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学校id" prop="schoolid">
          <el-input v-model="form.schoolid" placeholder="请输入学校id" />
        </el-form-item>
        <el-form-item label="班级id" prop="classid">
          <el-input v-model="form.classid" placeholder="请输入班级id" />
        </el-form-item>
        <el-form-item label="计划名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入计划名称" />
        </el-form-item>
        <el-form-item label="开始时间" prop="starttime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.starttime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开始时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endtime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.endtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="本周主题" prop="themeofweek">
          <el-input v-model="form.themeofweek" placeholder="请输入本周主题" />
        </el-form-item>
        <el-form-item label="教学目标(社会)" prop="jxmbSh">
          <el-input v-model="form.jxmbSh" placeholder="请输入教学目标(社会)" />
        </el-form-item>
        <el-form-item label="教学目标(语言)" prop="jxmbYy">
          <el-input v-model="form.jxmbYy" placeholder="请输入教学目标(语言)" />
        </el-form-item>
        <el-form-item label="教学目标(健康)" prop="jxmbJk">
          <el-input v-model="form.jxmbJk" placeholder="请输入教学目标(健康)" />
        </el-form-item>
        <el-form-item label="教学目标(科学)" prop="jxmbKx">
          <el-input v-model="form.jxmbKx" placeholder="请输入教学目标(科学)" />
        </el-form-item>
        <el-form-item label="教学目标(艺术)" prop="jxmbYs">
          <el-input v-model="form.jxmbYs" placeholder="请输入教学目标(艺术)" />
        </el-form-item>
        <el-form-item label="创建人" prop="createuserid">
          <el-input v-model="form.createuserid" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="当前状态">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核人" prop="shrid">
          <el-input v-model="form.shrid" placeholder="请输入审核人" />
        </el-form-item>
        <el-form-item label="审核时间" prop="shtime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.shtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择审核时间"
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
  listPlanweek,
  getPlanweek,
  delPlanweek,
  addPlanweek,
  updatePlanweek,
  exportPlanweek,
} from "@/api/benyi/planweek";

export default {
  name: "Planweek",
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
      // 周计划(家长和教育部门)表格数据
      planweekList: [],
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
        starttime: undefined,
        endtime: undefined,
        themeofweek: undefined,
        jxmbSh: undefined,
        jxmbYy: undefined,
        jxmbJk: undefined,
        jxmbKx: undefined,
        jxmbYs: undefined,
        createuserid: undefined,
        status: undefined,
        shrid: undefined,
        shtime: undefined,
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
    /** 查询周计划(家长和教育部门)列表 */
    getList() {
      this.loading = true;
      listPlanweek(this.queryParams).then((response) => {
        this.planweekList = response.rows;
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
        starttime: undefined,
        endtime: undefined,
        themeofweek: undefined,
        jxmbSh: undefined,
        jxmbYy: undefined,
        jxmbJk: undefined,
        jxmbKx: undefined,
        jxmbYs: undefined,
        createuserid: undefined,
        createTime: undefined,
        status: "0",
        shrid: undefined,
        shtime: undefined,
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
      this.title = "添加周计划(家长和教育部门)";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPlanweek(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改周计划(家长和教育部门)";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updatePlanweek(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addPlanweek(this.form).then((response) => {
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
        '是否确认删除周计划(家长和教育部门)编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delPlanweek(ids);
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
      this.$confirm("是否确认导出所有周计划(家长和教育部门)数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportPlanweek(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>