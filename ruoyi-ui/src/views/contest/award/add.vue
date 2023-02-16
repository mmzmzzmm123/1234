<template>
  <div class="app-container">
    <div>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="竞赛">
          <el-row>
            <el-col :span="12">
              <el-input v-model="form.name"></el-input>
            </el-col>
            <el-col :span="12">
              <el-button type="primary" style="margin-left: 5px" @click="openContestsDialog=true">选择</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-row>
              <el-col :span="12">
                <el-form-item label="获奖日期" prop="awardDate">
                  <el-date-picker clearable
                                  v-model="form.awardDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择获奖日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
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
              </el-col>
            </el-row>
            <el-row>
              <el-form-item label="备注" prop="remark">
                <el-input v-model="form.remark" placeholder="请输入备注"/>
              </el-form-item>
            </el-row>
          </el-col>
          <el-col :span="12">
            <el-form-item label="支撑材料" prop="attachmentUrl">
              <file-upload v-model="form.attachmentUrl"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="指导教师" prop="teacher">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <!--<span>指导教师</span>-->
              <el-button style="float: right; padding: 3px 0" type="text" @click="remGuideTeachers">删除</el-button>
              <el-button style="float: right; padding: 3px 0;margin-right: 5px" type="text" @click="showTeacherDialog">添加</el-button>
            </div>
            <div>
              <el-table :data="guideTeacherList" @selection-change="multiSelGuideTeacher">
                <el-table-column type="selection" width="55" align="center"/>
                <el-table-column label="姓名" align="center" prop="name"/>
                <el-table-column label="排序" align="center">
                  <template slot-scope="scope">
                    <el-input v-model.number="scope.row.orderNum"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="贡献度" align="center">
                  <template slot-scope="scope">
                    <el-input v-model.number="scope.row.conDegree"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="工作内容" align="center">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.workContent"></el-input>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-card>
        </el-form-item>
        <el-form-item label="参赛学生" prop="stus">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <el-button style="float: right; padding: 3px 0" type="text" @click="remContestStus">删除</el-button>
              <el-button style="float: right; padding: 3px 0;margin-right: 5px" type="text" @click="showStuDialog">添加</el-button>
            </div>
            <div>
              <el-table :data="contestStuList" @selection-change="multiSelContestStu">
                <el-table-column type="selection" width="55" align="center"/>
                <el-table-column label="姓名" align="center" prop="name"/>
                <el-table-column label="排序" align="center">
                  <template slot-scope="scope">
                    <el-input v-model.number="scope.row.orderNum"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="贡献度" align="center">
                  <template slot-scope="scope">
                    <el-input v-model.number="scope.row.conDegree"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="工作内容" align="center">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.workContent"></el-input>
                  </template>
                </el-table-column>
                <!--<el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                  <template slot-scope="scope">
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-delete"
                      @click="popStu(scope.row)"
                    >删除
                    </el-button>
                  </template>
                </el-table-column>-->
              </el-table>
            </div>
          </el-card>
          <!--<el-button @click="showStuDialog">添加</el-button>-->
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
      <!--<el-table :data="contestInfoList" @selection-change="multiSelContest" highlight-current-row @current-change="handleCurrentChange">-->
      <!--<el-table :data="contestInfoList" highlight-current-row @current-change="handleCurrentChange" @row-click="rowClick">-->
      <el-table :data="contestInfoList" highlight-current-row @current-change="handleCurrentChange" @row-click="rowClick">
        <!--<el-table-column type="selection" width="55" align="center"/>-->
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="竞赛名称">
                <span>{{ props.row.params.parentName }}</span>
              </el-form-item>
              <el-form-item label="子竞赛名称">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="竞赛时间">
                <span>{{ props.row.competitionDate }}</span>
              </el-form-item>
              <el-form-item label="竞赛通知网址">
                <span>{{ props.row.url }}</span>
              </el-form-item>
              <el-form-item label="年度">
                <span>{{ props.row.year }}</span>
              </el-form-item>
              <el-form-item label="报名费用">
                <span>{{ props.row.fee }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
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
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="姓名" align="center" prop="name"/>
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
        <el-table-column label="研究方向" align="center" prop="research"/>
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
      <el-form :model="queryStuParams" size="mini" :inline="true">
        <el-form-item label="学号" prop="stuNo">
          <el-input
            v-model="queryStuParams.stuNo"
            placeholder="请输入学号"
            clearable
            @keyup.enter.native="getStuList"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getStuList">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQueryStu">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="stuList" @selection-change="multiSelStu">
        <el-table-column type="selection" width="55" align="center"/>
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
  import {addAwardByVo, updateAward} from "@/api/contest/award";
  import {listTeacher} from "@/api/contest/teacher";
  import {listStu} from "@/api/contest/stu";

  export default {
    name: "AddAward",
    dicts: ['sys_normal_disable', 'award_grade', 'contest_grade', 'sub_contest_rank', 'contest_in_ministry', 'teacher_post', 'sys_user_sex', 'teacher_pro'],
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
        openTeacherDialog: false,
        openStuDialog: false,
        contestInfoList: [],
        teacherList: [],
        stuList: [],
        contestStuList: [],
        selectedTeacherList: [],
        remGuideTeacherList: [],
        selectedStuList: [],
        remContestStuList: [],
        guideTeacherList: [],
        contestTotal: 0,
        teacherTotal: 0,
        stuTotal: 0,
        orderTeacherNum: 0,
        orderStuNum: 0,
        contestNames: [],
        subContestIds: [],
        contestIds: [],
        queryContestParams: {
          pageSize: 10
        },
        queryTeacherParams: {
          pageSize: 10
        },
        queryStuParams: {
          pageNum: 1,
          pageSize: 10
        },
        currentRow: null
      }
    },
    /*
      http://localhost/dev-api/stuManage/stu/list?pageNum=1&pageSize=10&schoolAreaIds%5B0%5D=1&schoolAreaIds%5B1%5D=2&isMultiRoles=true&userRoles%5B0%5D=1-1&userRoles%5B1%5D=1-2
    */
    created() {
      this.getContestList()
      this.getTeacherList()
      this.getStuList()
    },
    methods: {
      submitForm() {
        this.form.guideTeacherList = this.guideTeacherList
        this.form.contestStuList = this.contestStuList
        console.log('this.form:', this.form)
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.awardId !== undefined) {
              updateAward(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.$tab.openPage("获奖登记", "/contest/award");
              });
            } else {
              addAwardByVo(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.$tab.openPage("添加获奖登记", "/contest/award");
              });
            }
          }
        });
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
          this.teacherList = this.arrDif(this.teacherList, this.guideTeacherList)
          this.teacherTotal = res.total;
        });
      },
      getStuList() {
        // this.queryStuParams.schoolAreaIds = [1,2]  // 新校区 老校区
        // this.queryStuParams.schoolAreaIds = [1]  // 新校区
        // this.queryStuParams.schoolAreaIds = [2]  // 老校区
        listStu(this.queryStuParams).then(res => {
          this.stuList = res.data.rows;
          this.stuList.forEach(item => {
            item.sex = item.sex + ''
          })
          this.stuList = this.arrDif2(this.stuList, this.contestStuList)
          this.stuTotal = res.data.total;
        });
      },
      addContest() {
        this.form.contest = this.contestNames.toString()
        this.queryContestParams = {}
        this.openContestsDialog = false
      },
      addTeacher() {
        this.selectedTeacherList.forEach(item => {
          const teacherObj = {
            teacherId: item.teacherId,
            personId: item.teacherId,
            name: item.name,
            // orderNum:this.guideTeacherList.length+1,
            orderNum: this.orderTeacherNum++,
            conDegree: 100,
            workContent: '',
            gender: item.gender,
            professional: item.professional,
            post: item.post,
            research: item.research,
          }
          this.guideTeacherList.push(teacherObj)
        })
        this.openTeacherDialog = false
      },
      addStu() {
        this.selectedStuList.forEach(item => {
          const stuObj = {
            id: item.id,
            personId: item.id,
            name: item.name,
            // collegeId: item.collegeId,
            // schoolArea: item.schoolArea,
            // buildingId: item.buildingId,
            // floorId: item.floorId,
            // roomId: item.roomId,
            // bedId: item.bedId,
            // status: item.status,
            // isInfoErr: item.isInfoErr,
            stuNo: item.stuNo,
            sex: item.sex,
            idNum: item.idNum,
            orderNum: this.orderStuNum++,
            conDegree: 100,
            workContent: '',
          }
          this.contestStuList.push(stuObj)
        })
        this.openStuDialog = false
      },
      /*cellClick(row, column, cell, event){
          console.log('row:',row)
      },*/
      arrDif(arr1, arr2) {
        for (let i = 0; i < arr1.length; i++) {
          for (let j = 0; j < arr2.length; j++) {
            if (arr1[i].teacherId === arr2[j].teacherId) {
              arr1 = arr1.filter(item => item.teacherId !== arr1[i].teacherId)
            }
          }
        }
        return arr1
      },
      arrDif2(arr1, arr2) {
        for (let i = 0; i < arr1.length; i++) {
          for (let j = 0; j < arr2.length; j++) {
            if (arr1[i].id === arr2[j].id) {
              arr1 = arr1.filter(item => item.id !== arr1[i].id)
            }
          }
        }
        return arr1
      },
      showTeacherDialog() {
        // this.teacherList=this.teacherList.concat(this.guideTeacherList).filter(item=>!this.guideTeacherList.includes(item))
        this.teacherList = this.arrDif(this.teacherList, this.guideTeacherList)
        this.openTeacherDialog = true
      },
      showStuDialog() {
        this.stuList = this.arrDif2(this.stuList, this.contestStuList)
        this.openStuDialog = true
      },
      cancelAddContest() {
        this.queryContestParams = {}
        this.openContestsDialog = false
      },
      cancelAddTeacher() {
        this.openTeacherDialog = false
      },
      cancelAddStu() {
        this.queryStuParams = {}
        this.openStuDialog = false
      },
      popTeacher(teacher) {
        let tOrderNum = 0
        this.guideTeacherList.forEach(item => {
          if (item.teacherId !== teacher.teacherId) {
            item.orderNum = tOrderNum
            tOrderNum++
          }
        })
        this.guideTeacherList = this.guideTeacherList.filter(item => item.teacherId !== teacher.teacherId)
        this.orderTeacherNum--
        let exist = false
        this.teacherList.forEach(item => {
          if (item.teacherId === teacher.teacherId) {
            exist = true
          }
        })
        if (!exist) {
          this.teacherList.push(teacher)
        }
      },
      popStu(stu) {
        let tOrderNum = 0
        this.contestStuList.forEach(item => {
          if (item.id !== stu.id) {
            item.orderNum = tOrderNum
            tOrderNum++
          }
        })
        this.contestStuList = this.contestStuList.filter(item => item.id !== stu.id)
        this.orderStuNum--
        let exist = false
        this.stuList.forEach(item => {
          if (item.id === stu.id) {
            exist = true
          }
        })
        if (!exist) {
          this.stuList.push(stu)
        }
      },
      multiSelContest(selection) {
        this.contestNames = selection.map(item => item.name)
        this.subContestIds = selection.map(item => item.subContestId)
        this.contestIds = selection.map(item => item.contestId)
      },
      multiSelTeacher(selection) {
        this.selectedTeacherList = selection
      },
      multiSelGuideTeacher(selection){
        this.remGuideTeacherList = selection
      },
      multiSelContestStu(selection){
        this.remContestStuList = selection
      },
      remGuideTeachers(){
        // console.log('this.remGuideTeacherList:',this.remGuideTeacherList)
        this.remGuideTeacherList.forEach(item=>{
          this.popTeacher(item)
        })
      },
      remContestStus(){
        // console.log('this.remContestStuList:',this.remContestStuList)
        this.remContestStuList.forEach(item=>{
          this.popStu(item)
        })
      },
      multiSelStu(selection) {
        this.selectedStuList = selection
      },
      resetQueryStu() {
        this.queryStuParams = {}
        this.getStuList()
      },
      resetQueryContest() {
        this.queryContestParams = {}
        this.getContestList()
      },
      cancel() {
        this.form = {}
        this.$tab.openPage("获奖登记", "/contest/award");
      },
      handleCurrentChange(val) {
        this.currentRow = val;
        this.form.contestId = val.contestId
        this.form.subContestId = val.subContestId
        this.form.name = val.name
      },
      rowClick(row, column, event){
        console.log('row:',row)
      }
    }
  }
</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 100%;
  }

</style>
