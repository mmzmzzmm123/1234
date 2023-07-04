<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="6" class="scoreInfo">
        课程:{{this.queryParams.courseName}}
      </el-col>
      <el-col :span="6" class="scoreInfo">
        班级:{{this.queryParams.stuCls}}
      </el-col>
    </el-row>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-s-grid"
          size="mini"
          @click="handleAvg"
          v-hasPermi="['stu:material:remove']"
        >平时成绩(历史记录版)</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-check"
          size="mini"
          @click="handleUpdate"
          v-hasPermi="['stu:material:edit']"
        >成绩录入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-close"
          size="mini"
          @click="handleCancel"
          v-hasPermi="['stu:material:edit']"
        >取消</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['stu:score:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['stu:score:export']"
        >成绩导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleShiJianExport"
          v-hasPermi="['stu:score:export']"
        >实践评分表</el-button>
      </el-col>
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="scoreList"
              @cell-click="handleCellEnter"
              @cell-mouse-leave="handleCellLeave">
<!--      <el-table-column label="编号" align="center" prop="stu.stuId" />-->
      <el-table-column label="学号" align="center" prop="stu.stuNo" />
      <el-table-column label="姓名" align="center" prop="stu.stuName" />
      <el-table-column label="平时成绩" align="center" prop="dailyScore" >
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEdit" class="item" v-model="scope.row.dailyScore" @keyup.enter.native="handleCellLeave"></el-input>
          <div v-else class="txt">{{scope.row.dailyScore}}</div>
        </template>
      </el-table-column>
      <el-table-column label="考试成绩" align="center" prop="finallyScore" >
        <template slot-scope="scope">
          <el-input v-if="scope.row.isEdit" class="item" v-model="scope.row.finallyScore" placeholder="请输入内容"></el-input>
          <div v-else class="txt">{{scope.row.finallyScore}}</div>
        </template>
      </el-table-column>
      <el-table-column label="最终成绩" align="center">
          <template slot-scope="scope">
            {{getTotalScore(scope.row.dailyScore,scope.row.finallyScore)}}
          </template>
      </el-table-column>
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleScoreCheck(scope.row)"-->
<!--            v-hasPermi="['stu:score:list']"-->
<!--          >编辑</el-button>-->
<!--          <el-button v-if="scope.row.status === 1"-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['stu:score:remove']"-->
<!--          >成绩录入</el-button>-->
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
  </div>
</template>

<script>
import { hisScoure,scoreInfo,setScore } from "@/api/stu/score";

export default {
  name: "stuScoreInfos",
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
      // 提交材料参数表格数据
      scoreList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 50,
        courseName: this.$route.params.courseName,
        courseId: this.$route.params.courseId,
        stuCls: this.$route.params.stuCls,
        semesterId: this.$route.params.semesterId
      },
      isConfirm:false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询提交材料参数列表 */
    getList() {
      this.loading = true;
      scoreInfo(this.queryParams).then(response => {
        response.rows.forEach((e)=>{
          e.isEdit = false
        })
        this.scoreList = response.rows
        this.total = response.total
        this.loading = false
      });
    },
    // 取消按钮
    handleCancel() {
      this.getList()
      this.isConfirm = false
    },
    getTotalScore(dailyScore,finalScore){
      return Math.round(dailyScore*0.3+finalScore*0.7)
    },
    /** 根据之前的表现自动生成平时成绩 **/
    handleAvg(){
      hisScoure(this.queryParams).then(response=>{
        response.data.forEach((stuAvg)=>{
          this.scoreList.forEach((sc)=>{
            if(stuAvg.stu.stuNo == sc.stu.stuNo){
              let score = stuAvg.avgScore
              score < 70 ? score=70 : score
              let num = Math.floor(Math.random()*5)
              Math.random()>0.5?score+=num:score-=num
              sc.dailyScore = score
            }
          })
        })
        this.isConfirm = true
      })
    },
    /** 修改按钮操作 */
    handleUpdate() {
      this.$confirm('确定录入成绩？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        setScore(this.scoreList).then(response=>{
          console.log(response)
          this.$message({
            type:'success',
            message:response.msg
          })
        })
        this.isConfirm = false
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    /** 鼠标移入cell */
    handleCellEnter (row, column, cell, event) {
      row.isEdit = true
    },
    /** 鼠标移出cell */
    handleCellLeave (row, column, cell, event) {
      row.isEdit = false
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('stu/score/export', {
        ...this.queryParams
      }, `${this.queryParams.stuCls}_${this.queryParams.courseName}成绩.xlsx`)
    },
    /** 导出按钮操作 */
    handleShiJianExport() {
      this.download('stu/score/shijianExport', {
        ...this.queryParams
      }, `${this.queryParams.stuCls}_${this.queryParams.courseName}_实践评分表.xlsx`)
    },
    handleImport(){

    }
  }
};
</script>

<style lang="scss">
.item{
  width: 100px;
  /* 调整elementUI中样式 如果不需要调整请忽略 */
  .el-input__inner{
    height: 24px!important;
  }
}
.txt{
  line-height: 24px;
  padding: 0 9px;
  box-sizing: border-box;
}
.scoreInfo{
  font-size: 20px;
}
</style>
