<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考试名称" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入考试名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考试状态" prop="state">
        <el-input
          v-model="queryParams.state"
          placeholder="请输入考试状态"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否限时" prop="timeLimit">
        <el-select v-model="queryParams.timeLimit" placeholder="请选择是否限时" clearable>
          <el-option
            v-for="dict in dict.type.common_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker clearable
          v-model="queryParams.startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker clearable
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="总分数" prop="totalScore">
        <el-input
          v-model="queryParams.totalScore"
          placeholder="请输入总分数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总时长" prop="totalTime">
        <el-input
          v-model="queryParams.totalTime"
          placeholder="请输入总时长"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="及格分数" prop="qualifyScore">
        <el-input
          v-model="queryParams.qualifyScore"
          placeholder="请输入及格分数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门ID" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入部门ID"
          clearable
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['exammange:exam:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['exammange:exam:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['exammange:exam:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['exammange:exam:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="examList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="考试名称" align="center" prop="title" />
      <el-table-column label="考试描述" align="center" prop="content" />
      <el-table-column label="1公开2部门3定员" align="center" prop="openType" />
      <el-table-column label="考试状态" align="center" prop="state" />
      <el-table-column label="是否限时" align="center" prop="timeLimit">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.common_yes_no" :value="scope.row.timeLimit"/>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="总分数" align="center" prop="totalScore" />
      <el-table-column label="总时长" align="center" prop="totalTime" />
      <el-table-column label="及格分数" align="center" prop="qualifyScore" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="部门ID" align="center" prop="deptId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['exammange:exam:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['exammange:exam:remove']"
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

    <!-- 添加或修改考试管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考试名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入考试名称" />
        </el-form-item>
        <el-form-item label="考试描述">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="考试状态" prop="state">
          <el-input v-model="form.state" placeholder="请输入考试状态" />
        </el-form-item>
        <el-form-item label="是否限时" prop="timeLimit">
          <el-select v-model="form.timeLimit" placeholder="请选择是否限时">
            <el-option
              v-for="dict in dict.type.common_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="总分数" prop="totalScore">
          <el-input v-model="form.totalScore" placeholder="请输入总分数" />
        </el-form-item>
        <el-form-item label="总时长" prop="totalTime">
          <el-input v-model="form.totalTime" placeholder="请输入总时长" />
        </el-form-item>
        <el-form-item label="及格分数" prop="qualifyScore">
          <el-input v-model="form.qualifyScore" placeholder="请输入及格分数" />
        </el-form-item>
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="部门ID" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入部门ID" />
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
import { listExam, getExam, delExam, addExam, updateExam } from "@/api/exammanage/exam";

export default {
  name: "Exam",
  dicts: ['common_yes_no'],
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
      // 考试管理表格数据
      examList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        content: null,
        openType: null,
        state: null,
        timeLimit: null,
        startTime: null,
        endTime: null,
        totalScore: null,
        totalTime: null,
        qualifyScore: null,
        userId: null,
        deptId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "考试名称不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "考试描述不能为空", trigger: "blur" }
        ],
        openType: [
          { required: true, message: "1公开2部门3定员不能为空", trigger: "change" }
        ],
        state: [
          { required: true, message: "考试状态不能为空", trigger: "blur" }
        ],
        timeLimit: [
          { required: true, message: "是否限时不能为空", trigger: "change" }
        ],
        totalScore: [
          { required: true, message: "总分数不能为空", trigger: "blur" }
        ],
        totalTime: [
          { required: true, message: "总时长不能为空", trigger: "blur" }
        ],
        qualifyScore: [
          { required: true, message: "及格分数不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询考试管理列表 */
    getList() {
      this.loading = true;
      listExam(this.queryParams).then(response => {
        this.examList = response.rows;
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
        title: null,
        content: null,
        openType: null,
        state: null,
        timeLimit: null,
        startTime: null,
        endTime: null,
        createTime: null,
        updateTime: null,
        totalScore: null,
        totalTime: null,
        qualifyScore: null,
        userId: null,
        deptId: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
 /*     this.open = true;
      this.title = "添加考试管理";*/
      this.$router.push("/exammanage/exam-add/addExam");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const examId = row.id || this.ids
      this.$router.push(`/exammanage/exam-update/updateExam/${examId}`);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateExam(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addExam(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除考试管理编号为"' + ids + '"的数据项？').then(function() {
        return delExam(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('exammange/exam/export', {
        ...this.queryParams
      }, `exam_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
