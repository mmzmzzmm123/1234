<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程名称" prop="courseName">
        <el-input
          v-model="queryParams.courseName"
          placeholder="请输入课程名称"
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
          v-hasPermi="['stu:material:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['stu:material:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" width="50">
        <template slot-scope="scope">
          {{queryParams.pageSize * (queryParams.pageNum - 1) + (scope.$index + 1)}}
        </template>
      </el-table-column>
      <el-table-column label="课程编号" align="center" prop="courseNo" />
      <el-table-column label="课程名称" align="center" prop="courseName" />
      <el-table-column label="课程类型" align="center" prop="courseType" />
      <el-table-column label="计划课时" align="center" prop="coursePlan" />
      <el-table-column label="实践课时" align="center" prop="courseExec" />
      <el-table-column label="学分" align="center" prop="courseScore" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['stu:score:list']"
          >修改</el-button>
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

    <!-- 添加&修改课程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程编号" prop="courseNo">
          <el-input v-model="form.courseNo" placeholder="请输入课程编号" />
        </el-form-item>
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="form.courseName" placeholder="请输入课程名" />
        </el-form-item>
        <el-form-item label="课程类型" prop="courseType">
          <el-input v-model="form.courseType" placeholder="请输入班级" />
        </el-form-item>
        <el-form-item label="计划课时" prop="coursePlan">
          <el-input v-model="form.coursePlan" placeholder="请输入计划课时" />
        </el-form-item>
        <el-form-item label="实践课时" prop="courseExec">
          <el-input v-model="form.courseExec" placeholder="请输入实践课时" />
        </el-form-item>
        <el-form-item label="学分" prop="courseScore">
          <el-input v-model="form.courseScore" placeholder="请输入学分" />
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
import {courseList,addCourse,updateCourse,delCourse} from "@/api/course/base"


export default {
  name: "course",
  data() {
    return {
      checked1:false,
      //添加对话框可视
      courseOpen:false,
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
      // 课程信息
      courseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        courseName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        courseName:[{ required: true, message: '请输入课程名称', trigger: 'blur' },]
      },
      isAdd: false
    };
  },
  created() {
    this.getList();
  },
  computed:{
    totalPoint(){
      let total = 0
      this.examConstruction.forEach((e)=>{
        total+=e.point
      })
      return total
    }
  },
  methods: {
    /** 获取课程列表 */
    getList() {
      this.loading = true;
      courseList(this.queryParams).then(response => {
        console.log(response)
        this.courseList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    courseCancel(){
      this.courseOpen = false;
      this.reset();
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {};
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
      this.ids = selection.map(item => item.courseId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.title = "添加课程信息"
      this.isAdd = true
      this.reset();
      this.open = true
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.title = "修改课程信息"
      this.isAdd = false
      this.reset();
      this.form = row
      this.open = true
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if(this.form.courseId > 0){
            updateCourse(this.form).then(()=>{
              this.$message({
                type: "success",
                message:"修改成功"
              })
              this.reset()
              this.open = false
              this.getList()
            })
          }else{
            addCourse(this.form).then(()=>{
              this.$message({
                type: "success",
                message:"添加成功"
              })
              this.reset()
              this.open = false
              this.getList()
            })
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除编号为"' + this.ids + '"的数据项？','删除提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(()=>{
        delCourse(this.ids).then(()=>{
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
      })
    },
  }
};
</script>
