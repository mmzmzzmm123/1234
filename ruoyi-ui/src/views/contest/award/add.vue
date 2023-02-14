<template>
  <div class="app-container">
    <div>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="竞赛" prop="contest">
          <el-row>
            <el-col :span="12">
              <el-input></el-input>
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
          <el-button>添加</el-button>
        </el-form-item>
        <el-form-item label="参赛学生" prop="stus">
          <el-button>添加</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </div>

    <!--添加竞赛对话框-->
    <el-dialog title="选择竞赛" :visible.sync="openContestsDialog"  fullscreen append-to-body>
      <el-form :model="queryContestParams" ref="queryForm" size="small" :inline="true"  label-width="75px">
        <el-form-item label="57项赛事" prop="inMinistry">
          <el-select v-model="queryContestParams.inMinistry"  clearable>
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
          <el-button type="primary" icon="el-icon-search" size="mini" @click="queryContest">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQueryContest">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table  :data="contestInfoList" @selection-change="multiSelContest">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="竞赛名称" align="center" prop="params.parentName" />
        <el-table-column label="子竞赛名称" align="center" prop="name" />
        <el-table-column label="57项赛事" align="center" prop="params.inMinistry" />
        <el-table-column label="竞赛等次" align="center" prop="rank">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sub_contest_rank" :value="scope.row.rank"/>
          </template>
        </el-table-column>
        <el-table-column label="竞赛级别" align="center" prop="grade">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.contest_grade" :value="scope.row.params.grade"/>
          </template>
        </el-table-column>
        <el-table-column label="竞赛时间" align="center" prop="competitionDate" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addContest">确 定</el-button>
        <el-button @click="cancelAddContest">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { addType, updateType } from "@/api/system/dict/type";
  import { listWithParentContest} from "@/api/contest/subContest";


  export default {
    name: "AddAward",
    dicts: ['sys_normal_disable','award_grade','contest_grade','sub_contest_rank','contest_in_ministry'],
    data() {
      return {
        form: {},
        // 表单校验
        rules: {
          dictName: [
            { required: true, message: "字典名称不能为空", trigger: "blur" }
          ],
          dictType: [
            { required: true, message: "字典类型不能为空", trigger: "blur" }
          ]
        },
        openContestsDialog:false,
        contestInfoList:[],
        queryContestParams:{}
      }
    },
    created() {
      this.getContestList()
    },
    methods:{
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.dictId != undefined) {
              updateType(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addType(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      getContestList(){
        listWithParentContest(this.queryParams).then(res => {
          this.contestInfoList = res.rows;
          this.total = res.total;
        });
      },
      addContest(){

      },
      cancelAddContest(){},
      multiSelContest(){},
      queryContest(){

      },
      resetQueryContest(){

      },
      cancel(){

      }
    }
  }
</script>

<style scoped>

</style>
