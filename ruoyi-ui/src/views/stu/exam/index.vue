<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item  label="年级" prop="clsYear">
        <el-select v-model="queryParams.clsYear" placeholder="请选择年级">
          <el-option  v-for="(item,index) in clsYears" :key="index" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item  label="学期" prop="semesterName">
        <el-select v-model="queryParams.semesterName" placeholder="请选择学期" >
          <el-option  v-for="(item,index) in semesters" :key="index" :label="item.semesterName" :value="item.semesterId"></el-option>
<!--          <el-option label="22级" value="22级"></el-option>-->
        </el-select>
      </el-form-item>
      <el-form-item label="课程名字" prop="stuCls">
        <el-input
          v-model="queryParams.courseName"
          placeholder="请输入课程名字"
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
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['stu:material:edit']"
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
          v-hasPermi="['stu:material:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['stu:material:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="scoreCourseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" width="50">
        <template slot-scope="scope">
          {{queryParams.pageSize * (queryParams.pageNum - 1) + (scope.$index + 1)}}
        </template>
      </el-table-column>
      <el-table-column label="课程名" align="center" prop="course.courseName" />
      <el-table-column label="年级" align="center" prop="stuCls.clsYear" />
<!--      <el-table-column label="班级类型" align="center" prop="stuCls.cls" />-->
      <el-table-column label="学期" align="center" prop="semester.semesterName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleSetExamConstruction(scope.row)"
            v-hasPermi="['stu:score:list']"
          >试卷结构设置</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-eye"
            @click="handleDelete(scope.row)"
            v-hasPermi="['stu:score:remove']"
          >试卷结构查看</el-button>
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

    <!-- 设置试卷结构对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row v-for="(item,index) in this.examConstruction" :key="index">
          <el-col :span="8">
            <el-form-item label="题型">
              <el-input placeholder="题型" v-model="item.title" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分数" >
              <el-input placeholder="分数" v-model.number="item.point" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="优先级" >
              <el-input placeholder="优先级" v-model="item.priority" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-row>
        <el-col :span="8" :offset="18">
          <span><b>总分：{{this.totalPoint}}</b></span>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addRow">添 加</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 添加课程对话框 -->
    <el-dialog title="添加课程" :visible.sync="courseOpen" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :offset="4" :span="18">
            <el-form-item  label="学期" prop="semesterName">
              <el-select v-model="queryParams.semesterId" placeholder="请选择学期" >
                <el-option  v-for="(item,index) in semesters" :key="index" :label="item.semesterName" :value="item.semesterId"></el-option>
                <!--          <el-option label="22级" value="22级"></el-option>-->
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :offset="4" :span="18">
            <el-form-item label="年级">
              <el-select v-model="queryParams.clsYear" placeholder="请选择年级">
                <el-option  v-for="(item,index) in clsYears" :key="index" :label="item" :value="item"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :offset="4" :span="18">
            <el-form-item label="课程">
              <el-select v-model="queryParams.courseId" placeholder="请选择课程">
                <el-option  v-for="(item,index) in courses" :key="index" :label="item.courseName" :value="item.courseId"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="courseCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { coursePlanInfo,addCoursePlan,allSemester,allCourses } from "@/api/stu/score";
import { allClsYear } from "@/api/stu/base"
import { construct,addConstruct,updateConstruct } from "@/api/stu/exam"

export default {
  name: "exam",
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
      // 提交材料参数表格数据
      scoreCourseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        courseName: null,
        courseId:null,
        clsYear: null,
        semesterName:null,
        semesterId:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      examConstruction:[{title:'选择题',point:0,priority:0}],
      clsYears:[],
      semesters:[],
      courses:[]
    };
  },
  created() {
    this.getList();
    this.init();
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
    init(){
      allClsYear().then(response => {
        response.data.forEach((cls)=>{
          this.clsYears.push(cls.clsYear)
        })
      })
      allSemester().then(response => {
        this.semesters = response.data
      })
      allCourses().then(response => {
        this.courses = response.data
      })
    },
    /** 查询提交材料参数列表 */
    getList() {
      this.loading = true;
      coursePlanInfo(this.queryParams).then(response => {
        this.scoreCourseList = response.rows
        this.total = response.total
        this.loading = false
      });
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
    //添加一行
    addRow(){
      this.examConstruction.push({title:'',point:0,priority: 0})
    },
    // 表单重置
    reset() {
      this.form = {
        stu:null,
        teacher:null,
        course:null
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
      this.courseOpen = true;

    },
    /** 设置试卷结构  **/
    handleSetExamConstruction(row){
      this.title = '试卷结构设置'
      construct({
        'semesterId':row.semester.semesterId,
        'courseId':row.course.courseId
      }).then(response=>{console.log(response)
        if(response.data.length>0){
          let examTitles = response.data[0].examTitles.split(',')
          let examPoints = response.data[0].examPoints.split(',')
          let examPriorities = response.data[0].examPriorities.split(',')
          this.examConstruction = []
          for (let i = 0; i < examTitles.length; i++) {
            this.examConstruction.push({
              title: examTitles[i],
              point: Number.parseInt(examPoints[i]),
              priority: Number.parseInt(examPriorities[i])
            })
          }
        }else{
          this.examConstruction = [{title:'选择题',point:0,priority:0}]
        }
        this.open = true
      })

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      console.log(row)
      this.reset();
      const id = row.id || this.ids
      getMaterial(id).then(response => {
        this.scoreList.push(
          response.data
        )
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addCoursePlan({
            semesterId: this.queryParams.semesterId,
            clsYear: this.queryParams.clsYear,
            courseId: this.queryParams.courseId
          }).then(()=>{
              this.$message({
                type: "success",
                message:"添加成功"
              })
              this.getList()
            }
          )
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除提交材料参数编号为"' + ids + '"的数据项？').then(function() {
        return delMaterial(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      console.log('成绩导出')
      this.download('stu/score/export', {
        ...this.queryParams
      }, `${this.queryParams.stuCls}_${this.queryParams.courseName}成绩.xlsx`)
    }
  }
};
</script>
