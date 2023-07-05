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
      <el-form-item  label="学期" prop="semesterId">
        <el-select v-model="queryParams.semesterId" placeholder="请选择学期" >
          <el-option  v-for="(item,index) in semesters" :key="index" :label="item.semesterName" :value="item.semesterId"></el-option>
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

    <el-table v-loading="loading" :data="coursePlanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" width="50">
        <template slot-scope="scope">
          {{queryParams.pageSize * (queryParams.pageNum - 1) + (scope.$index + 1)}}
        </template>
      </el-table-column>
      <el-table-column label="课程名称" align="center" prop="course.courseName" />
      <el-table-column label="学期" align="center" prop="semester.semesterName" />
      <el-table-column label="班级" align="center" prop="stus">
        <template slot-scope="scope">
            <el-tag size="medium" v-for="item in scope.row.stuCls" :key="item.clsId">{{ item.clsName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == 0">未开始</el-tag>
          <el-tag type="danger" v-if="scope.row.status == 1">已开始</el-tag>
          <el-tag type="warning" v-if="scope.row.status == 1">未录入</el-tag>
          <el-tag type="success" v-if="scope.row.status == 2">已完成</el-tag>
        </template>
      </el-table-column>
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['stu:score:list']"-->
<!--          >修改</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
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
        <el-form-item  label="学期" prop="semesterId">
          <el-select v-model="queryParams.semesterId" placeholder="请选择学期" >
            <el-option  v-for="item in semesters" :key="item.semesterId" :label="item.semesterName" :value="item.semesterId"/>
          </el-select>
        </el-form-item>
        <el-form-item  label="课程名" prop="courseId">
          <el-select v-model="queryParams.courseId" placeholder="请选择课程" >
            <el-option  v-for="item in courses" :key="item.courseId" :label="item.courseName" :value="item.courseId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="stuCls">
          <el-checkbox-group v-model="queryParams.stuClss">
            <el-checkbox v-for="cls in stuclsList" :key="cls.clsId" :label="cls.clsId">{{cls.clsName}}</el-checkbox>
          </el-checkbox-group>
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
import { planList,addPlans,delPlans } from "@/api/course/plan"
import { semesterList } from "@/api/semester/semester"
import { allCourse } from "@/api/course/base"
import { clsList } from  "@/api/stu/cls"

export default {
  name: "coursePlan",
  data() {
    return {
      checked1:false,
      //添加对话框可视
      courseOpen:false,
      // 遮罩层
      loading: true,
      // 选中的数据
      items: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 课程安排信息
      coursePlanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        courseName: null,
        semesterId: null,
        courseId: null,
        stuClss:[],
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        // courseName:[{ required: true, message: '请输入课程名称', trigger: 'blur' },]
      },
      //所有学期
      semesters:[],
      //所有课程
      courses:[],
      //所有班级
      stuclsList:[],
      //所有年级
      // levelList:[]
    };
  },
  created() {
    this.init()
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
      semesterList().then(response => {
        this.semesters = response.data
      })
      allCourse().then(response => {
        this.courses = response.data
      })
      clsList().then(response=>{
        let data = response.data
        for(let index in data){
          this.stuclsList.push(...data[index])
        }
      })
      this.getList()
    },
    /** 获取课程列表 */
    getList() {
      this.loading = true;
      planList(this.queryParams).then(response => {
        this.coursePlanList = response.rows
        this.coursePlanList.forEach((item)=>{
          item.stuCls.sort(this.sortBy('clsId',1))
        })
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
      this.queryParams.semesterId = null
      this.queryParams.courseId = null
      this.queryParams.courseName = null
      this.open = false
      this.reset()
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
      this.items = selection
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.title = "添加课程安排"
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
          addPlans(this.queryParams).then((response)=>{
            if(response.msg == '添加成功！'){
              this.$message.success(response.msg)
              this.cancel()
              this.getList()
            }else{
              this.$message.warning(response.msg)
            }
          })
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除被选中的数据项？','删除提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(()=>{
        delPlans(this.items).then(()=>{
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
      })
    },
    //attr：根据该属性排序；rev：升序1或降序-1，不填则默认为1
    sortBy(attr,rev){
      if( rev==undefined ){ rev=1 }else{ (rev)?1:-1; }
      return function (a,b){
        a=a[attr];
        b=b[attr];
        if(a<b){ return rev*-1}
        if(a>b){ return rev* 1 }
        return 0;
      }
    },

  }
};
</script>
