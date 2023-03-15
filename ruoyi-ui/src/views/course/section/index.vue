<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="章节编号" prop="sectionId">
        <el-input
          v-model="queryParams.sectionId"
          placeholder="请输入章节编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="章节题目" prop="topic">
        <el-input
          v-model="queryParams.topic"
          placeholder="请输入章节题目"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="章节时长" prop="duration">
        <el-input
          v-model="queryParams.duration"
          placeholder="请输入章节时长"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="章节类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择章节类型" clearable>
          <el-option
            v-for="dict in dict.type.course_section_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程编号" prop="courseid">
        <el-input
          v-model="queryParams.courseid"
          placeholder="请输入课程编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="内容类型" prop="contentType">
        <el-select v-model="queryParams.contentType" placeholder="请选择内容类型" clearable>
          <el-option
            v-for="dict in dict.type.course_section_content_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['course:section:add']"
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
          v-hasPermi="['course:section:edit']"
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
          v-hasPermi="['course:section:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['course:section:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sectionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="章节编号" align="center" prop="sectionId" />
      <el-table-column label="章节题目" align="center" prop="topic" />
      <el-table-column label="章节时长" align="center" prop="duration" />
      <el-table-column label="章节类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.course_section_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="内容链接" align="center" prop="contentUrl" />
      <el-table-column label="课程编号" align="center" prop="courseid" />
      <el-table-column label="内容类型" align="center" prop="contentType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.course_section_content_type" :value="scope.row.contentType"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['course:section:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['course:section:remove']"
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

    <!-- 添加或修改章节对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="章节编号" prop="sectionId">
          <el-input v-model="form.sectionId" placeholder="请输入章节编号" />
        </el-form-item>
        <el-form-item label="章节题目" prop="topic">
          <el-input v-model="form.topic" placeholder="请输入章节题目" />
        </el-form-item>
        <el-form-item label="章节时长" prop="duration">
          <el-input v-model="form.duration" placeholder="请输入章节时长" />
        </el-form-item>
        <el-form-item label="章节类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择章节类型">
            <el-option
              v-for="dict in dict.type.course_section_type"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="章节内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="内容链接" prop="contentUrl">
          <el-input v-model="form.contentUrl" placeholder="请输入内容链接" />
        </el-form-item>
        <el-form-item label="课程编号" prop="courseid">
          <el-input v-model="form.courseid" placeholder="请输入课程编号" />
        </el-form-item>
        <el-form-item label="内容类型" prop="contentType">
          <el-select v-model="form.contentType" placeholder="请选择内容类型">
            <el-option
              v-for="dict in dict.type.course_section_content_type"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
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
import { listSection, getSection, delSection, addSection, updateSection } from "@/api/course/section";

export default {
  name: "Section",
  dicts: ['course_section_type', 'course_section_content_type'],
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
      // 章节表格数据
      sectionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sectionId: null,
        topic: null,
        duration: null,
        type: null,
        courseid: null,
        contentType: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sectionId: [
          { required: true, message: "章节编号不能为空", trigger: "blur" }
        ],
        topic: [
          { required: true, message: "章节题目不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "章节类型不能为空", trigger: "change" }
        ],
        courseid: [
          { required: true, message: "课程编号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询章节列表 */
    getList() {
      this.loading = true;
      listSection(this.queryParams).then(response => {
        this.sectionList = response.rows;
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
        sectionId: null,
        topic: null,
        duration: null,
        type: null,
        content: null,
        contentUrl: null,
        courseid: null,
        contentType: null
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
      this.open = true;
      this.title = "添加章节";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSection(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改章节";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSection(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSection(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除章节编号为"' + ids + '"的数据项？').then(function() {
        return delSection(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('course/section/export', {
        ...this.queryParams
      }, `section_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
