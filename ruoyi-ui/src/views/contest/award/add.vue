<template>
  <div class="app-container">
    <div>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="竞赛" prop="contest">
          <el-row>
            <el-col :span="12">
              <el-input v-model="form.contest"></el-input>
            </el-col>
            <el-col :span="12">
              <el-button type="primary" @click="openContestsDialog=true">选择</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="获奖日期" prop="awardDate">
          <el-date-picker clearable
                          v-model="form.awardDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择获奖日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="获奖等级" prop="awardGrade">
          <el-select v-model="form.awardGrade" placeholder="请选择获奖等级">
            <el-option
              v-for="dict in dict.type.award_grade"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="支撑材料" prop="attachmentUrl">
          <file-upload v-model="form.attachmentUrl"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
        <el-form-item label="指导教师" prop="teacher">
          <el-button @click="showTeacherDialog">添加</el-button>
          <el-table :data="guideTeacherList">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="姓名" align="center" prop="name"/>
            <el-table-column label="排序" align="center">
              <template slot-scope="scope">
                <el-input  v-model.number="scope.row.orderNum"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="贡献度" align="center">
              <template slot-scope="scope">
                <el-input  v-model.number="scope.row.conDegree"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="工作内容" align="center">
              <template slot-scope="scope">
                <el-input  v-model="scope.row.workContent"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="popTeacher(scope.row)"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item label="参赛学生" prop="stus">
          <el-button @click="openStuDialog=true">添加</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </div>

    <!--添加竞赛对话框-->
    <el-dialog title="选择竞赛" :visible.sync="openContestsDialog" fullscreen append-to-body>
      <el-form :model="queryContestParams" ref="queryForm" size="small" :inline="true" label-width="75px">
        <el-form-item label="57项赛事" prop="inMinistry">
          <el-select v-model="queryContestParams.inMinistry" clearable>
            <el-option
              v-for="dict in dict.type.contest_in_ministry"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="竞赛等次" prop="grade">
          <el-select v-model="queryContestParams.grade" placeholder="请选择竞赛等次" clearable>
            <el-option
              v-for="dict in dict.type.contest_grade"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="竞赛级别" prop="type">
          <el-select v-model="queryContestParams.rank" placeholder="请选择竞赛类别" clearable>
            <el-option
              v-for="dict in dict.type.sub_contest_rank"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="竞赛名称" prop="name">
          <el-input v-model="queryContestParams.name" placeholder="请输入竞赛名称"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getContestList()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQueryContest">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="contestInfoList" @selection-change="multiSelContest">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="竞赛名称" align="center" prop="params.parentName"/>
        <el-table-column label="子竞赛名称" align="center" prop="name"/>
        <el-table-column label="57项赛事" align="center" prop="params.inMinistry">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.contest_in_ministry" :value="scope.row.params.inMinistry"/>
          </template>
        </el-table-column>
        <el-table-column label="竞赛等次" align="center" prop="rank">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.contest_grade" :value="scope.row.params.grade"/>
          </template>
        </el-table-column>
        <el-table-column label="竞赛级别" align="center" prop="grade">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sub_contest_rank" :value="scope.row.rank"/>
          </template>
        </el-table-column>
        <el-table-column label="竞赛时间" align="center" prop="competitionDate"/>
      </el-table>
      <pagination
        v-show="contestTotal>0"
        :total="contestTotal"
        :page.sync="queryContestParams.pageNum"
        :limit.sync="queryContestParams.pageSize"
        @pagination="getContestList"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addContest">确 定</el-button>
        <el-button @click="cancelAddContest">取 消</el-button>
      </div>
    </el-dialog>

    <!--添加指导教师对话框-->
    <el-dialog title="添加指导教师" :visible.sync="openTeacherDialog" fullscreen append-to-body>
      <el-table :data="teacherList" @selection-change="multiSelTeacher">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="姓名" align="center" prop="name" />
        <el-table-column label="性别" align="center" prop="gender">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.gender"/>
          </template>
        </el-table-column>
        <el-table-column label="职称" align="center" prop="professional">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.teacher_pro" :value="scope.row.professional"/>
          </template>
        </el-table-column>
        <el-table-column label="职务" align="center" prop="post">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.teacher_post" :value="scope.row.post"/>
          </template>
        </el-table-column>
        <el-table-column label="研究方向" align="center" prop="research" />
      </el-table>
      <pagination
        v-show="teacherTotal>0"
        :total="teacherTotal"
        :page.sync="queryTeacherParams.pageNum"
        :limit.sync="queryTeacherParams.pageSize"
        @pagination="getTeacherList"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addTeacher">确 定</el-button>
        <el-button @click="cancelAddTeacher">取 消</el-button>
      </div>
    </el-dialog>

    <!--添加参赛学生对话框-->
    <el-dialog title="添加参赛学生" :visible.sync="openStuDialog" fullscreen append-to-body>
      <el-table :data="stuList" @selection-change="multiSelStu">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="学号" align="center" prop="stuNo" width="130"/>
        <el-table-column label="姓名" align="center" prop="name" width="70"/>
        <el-table-column label="性别" align="center" prop="sex">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
          </template>
        </el-table-column>
        <el-table-column label="身份证号" align="center" prop="idNum"/>
      </el-table>
      <pagination
        v-show="stuTotal>0"
        :total="stuTotal"
        :page.sync="queryStuParams.pageNum"
        :limit.sync="queryStuParams.pageSize"
        @pagination="getStuList"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addStu">确 定</el-button>
        <el-button @click="cancelAddStu">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {listWithParentContest} from "@/api/contest/subContest";
  import axios from 'axios';
  import { listTeacher} from "@/api/contest/teacher";
  import { listStu} from "@/api/contest/stu";

  export default {
    name: "AddAward",
    dicts: ['sys_normal_disable', 'award_grade', 'contest_grade', 'sub_contest_rank', 'contest_in_ministry','teacher_post', 'sys_user_sex', 'teacher_pro'],
    data() {
      return {
        form: {},
        // 表单校验
        rules: {
          dictName: [
            {required: true, message: "字典名称不能为空", trigger: "blur"}
          ],
          dictType: [
            {required: true, message: "字典类型不能为空", trigger: "blur"}
          ]
        },
        openContestsDialog: false,
        openTeacherDialog:false,
        openStuDialog:false,
        contestInfoList: [],
        teacherList: [],
        stuList: [],
        contestStuList:[],
        selectedTeacherList: [],
        guideTeacherList: [],
        contestTotal:0,
        teacherTotal:0,
        stuTotal:0,
        orderNum:0,
        contestNames:[],
        subContestIds:[],
        queryContestParams: {
          pageSize:10
        },
        queryTeacherParams:{
          pageSize:10
        },
        queryStuParams:{
          pageNum:1,
          pageSize:10,
          schoolArea:1
        }
      }
    },
    created() {
      this.getContestList()
      this.getTeacherList()
      this.getStuList()
    },
    methods: {
      submitForm() {
        this.form.guideTeacherList=this.guideTeacherList
        this.form.contestStuList=this.contestStuList
        console.log('this.form:',this.form)
        // this.$refs["form"].validate(valid => {
        //   if (valid) {
        //     if (this.form.dictId != undefined) {
        //       updateType(this.form).then(response => {
        //         this.$modal.msgSuccess("修改成功");
        //         this.open = false;
        //         this.getList();
        //       });
        //     } else {
        //       addType(this.form).then(response => {
        //         this.$modal.msgSuccess("新增成功");
        //         this.open = false;
        //         this.getList();
        //       });
        //     }
        //   }
        // });
      },
      getContestList() {
        listWithParentContest(this.queryContestParams).then(res => {
          this.contestInfoList = res.rows;
          this.contestTotal = res.total;
        });
      },
      getTeacherList() {
        listTeacher().then(res => {
          this.teacherList = res.rows;
          this.teacherTotal = res.total;
        });
      },
      getStuList(){
        // const {data:res}=await axios.get('http://140.210.209.124/prod-api/stuManage/stu/list?pageSize='+this.queryStuParams.pageSize+'&pageNum='+this.queryStuParams.pageNum,{})
        // this.stuList = res.rows;
        // this.stuList.forEach(item=>{
        //   item.sex=item.sex+''
        // })
        // this.stuTotal = res.total;
        // // console.log('res:',res)
        listStu(this.queryStuParams).then(res => {
          this.stuList = res.rows;
          this.stuTotal = res.total;
        });
      },
      addContest() {
        this.form.contest=this.contestNames.toString()
        this.queryContestParams = {}
        this.openContestsDialog=false
      },
      addTeacher(){
        this.selectedTeacherList.forEach(item=>{
          const teacherObj={
            teacherId:item.teacherId,
            name:item.name,
            // orderNum:this.guideTeacherList.length+1,
            orderNum:this.orderNum++,
            conDegree:100,
            workContent:'',
            gender:item.gender,
            professional:item.professional,
            post:item.post,
            research:item.research,
          }
          this.guideTeacherList.push(teacherObj)
        })
        this.openTeacherDialog=false
      },
      addStu(){

      },
      /*cellClick(row, column, cell, event){
          console.log('row:',row)
      },*/
      arrDif(arr1,arr2){
        for (let i = 0; i < arr1.length; i++) {
          for (let j = 0; j < arr2.length; j++) {
            if (arr1[i].teacherId === arr2[j].teacherId) {
              arr1 = arr1.filter(item => item.teacherId !== arr1[i].teacherId)
            }
          }
        }
        return arr1
      },
      showTeacherDialog(){
        // this.teacherList=this.teacherList.concat(this.guideTeacherList).filter(item=>!this.guideTeacherList.includes(item))
        this.teacherList=this.arrDif(this.teacherList,this.guideTeacherList)
        this.openTeacherDialog=true
      },
      cancelAddContest() {
        this.queryContestParams = {}
        this.openContestsDialog=false
      },
      cancelAddTeacher(){
        this.openTeacherDialog=false
      },
      cancelAddStu(){
        this.openStuDialog=false
      },
      popTeacher(teacher){
        let tOrderNum=0
        this.guideTeacherList.forEach(item=>{
          if (item.teacherId!==teacher.teacherId){
            item.orderNum=tOrderNum
            tOrderNum++
          }
        })
        this.guideTeacherList=this.guideTeacherList.filter(item=>item.teacherId!==teacher.teacherId)
        this.orderNum--
        let exist=false
        this.teacherList.forEach(item=>{
          if (item.teacherId===teacher.teacherId){
            exist=true
          }
        })
        if (!exist){
          this.teacherList.push(teacher)
        }
      },
      multiSelContest(selection) {
        this.contestNames = selection.map(item => item.name)
        this.subContestIds = selection.map(item => item.subContestId)
      },
      multiSelTeacher(selection){
        this.selectedTeacherList=selection
      },
      multiSelStu(selection){

      },
      resetQueryContest() {
        this.queryContestParams = {}
        this.getContestList()
      },
      cancel() {

      }
    }
  }
</script>

<style scoped>

</style>
