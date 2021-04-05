<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="90px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="班级名称" prop="classid">
            <el-select
              v-model="form.classid"
              size="small"
              placeholder="请选择班级"
            >
              <el-option
                v-for="dict in classOptions"
                :key="dict.bjbh"
                :label="dict.bjmc"
                :value="dict.bjbh"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="评估对象" prop="pgdx">
            <el-select
              v-model="form.pgdx"
              size="small"
              placeholder="请选择评估对象"
            >
              <el-option
                v-for="dict in pgdxOptions"
                :key="dict.userId"
                :label="dict.nickName"
                :value="dict.userId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="评估内容" prop="bzid">
            <el-select v-model="form.bzid" placeholder="请选择评估内容">
              <el-option
                v-for="dict in detailOptions"
                :key="dict.id"
                :label="dict.name"
                :value="dict.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="mb8 btn-list">
      <el-button
        type="primary"
        icon="el-icon-s-data"
        size="mini"
        @click="submitForm"
        v-hasPermi="['benyi:dayflowassessment:edit']"
        v-prevent-re-click
        >提交评估</el-button
      >
    </div>
    <el-tabs v-model="activeName" type="card">
      <div
        class="block"
        v-for="itemTask in dayflowtaskList"
        :key="itemTask.code"
      >
        <h2 class="block-item-title flex align-center">
          {{ itemTask.taskLable }}
        </h2>

        <div
          class="checkbox-content"
          v-for="itemBz in dayflowstandardList.filter(
            (p) => p.taskCode == itemTask.code
          )"
          :key="itemBz.id"
        >
          <p class="checkbox-item flex align-center">
            <el-row v-model="checkList">
              <div :label="itemBz.id" :key="itemBz.id"
                >{{ itemBz.standardTitle }}分值: {{ itemBz.score }}分
                <el-input-number
                  v-model="num"
                  :precision="1"
                  :step="0.1"
                  :min="-itemBz.score"
                  :max="itemBz.score"
                ></el-input-number
              ></div>
            </el-row>
          </p>
        </div>
      </div>
    </el-tabs>
  </div>
</template>
<script>
import {
  listDayflowassessment,
  getDayflowassessment,
  delDayflowassessment,
  addDayflowassessment,
  updateDayflowassessment,
} from "@/api/benyi/dayflowassessment";
import { listDayflowtask } from "@/api/benyi/dayflow/dayflowtask";
import { listDetail, getDetail } from "@/api/benyi/dayflow/dayflowmanger";
import { listStandard } from "@/api/benyi/dayflow/biaozhun/standard";
import { listDept, getDept } from "@/api/system/dept";
import { listClass, getUserList } from "@/api/system/class";
import { listUser } from "@/api/system/user";

export default {
  name: "Assessmentstudent",

  data() {
    return {
      num: 0,
      dayflowassessmentId: null,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 评估内容表格数据
      assessmentcontentList: [],

      // 根据一日流程id查到的名下任务列表
      dayflowtaskList: [],
      // 根据任务查询到名下标准
      dayflowstandardList: [],
      //班级
      classOptions: [],
      //评估对象
      pgdxOptions: [],
      // 一日流程表格数据
      detailOptions: [],
      // 表单参数
      form: {},
      // 查询一日流程任务
      queryParams_detail: {
        detailId: undefined,
      },
      // 查询一日流程标准
      queryParams_standard: {
        task_code: undefined,
      },
      // 查询参数
      queryParams: {
        parentid: undefined,
        name: undefined,
        iselement: undefined,
        scope: undefined,
        sort: undefined,
      },
      activeName: "一日流程评估",
      checked: false,
      checkList: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        classid: [{ required: true, message: "班级不能为空", trigger: "blur" }],
        pgdx: [
          { required: true, message: "评估对象不能为空", trigger: "blur" },
        ],
        bzid: [
          { required: true, message: "评估内容不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    // this.getList();
    const dayflowassessmentId = this.$route.params && this.$route.params.id;
    // this.dayflowassessmentId = dayflowassessmentId;
    this.getClassList();
    this.getDayFlowList();
  },
  watch: {
    "form.classid": function (val) {
      this.getUserListByBjbh(val);
    },
    "form.bzid": function (val) {
      this.dayflowassessmentId = val;
      this.queryParams_detail.detailId = val;
      this.getTaskList();
    },
  },
  methods: {
    /** 查询一日流程列表 */
    getDayFlowList() {
      listDetail(null).then((response) => {
        this.detailOptions = response.rows;
      });
    },
    // 班级字典翻译
    classFormat(classid) {
      var actions = [];
      var datas = this.classOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].bjbh == "" + classid) {
          actions.push(datas[key].bjmc);
          return false;
        }
      });
      return actions.join("");
    },
    //班级列表
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
      });
    },
    //班级列表
    getUserListByBjbh(val) {
      getUserList(val).then((response) => {
        this.pgdxOptions = response.rows;
      });
    },
    /** 查询一日流程任务列表 */
    getTaskList() {
      listDayflowtask(this.queryParams_detail).then((response) => {
        this.dayflowtaskList = response.rows;
      });
      listStandard(null).then((response) => {
        this.dayflowstandardList = response.rows;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        planid: undefined,
        deptId: undefined,
        classid: undefined,
        bzbh: undefined,
        bzxm: undefined,
        pbbh: undefined,
        pbxm: undefined,
        zlbh: undefined,
        zlxm: undefined,
        xnxq: undefined,
        bzid: undefined,
        bzmf: undefined,
        kfz: undefined,
        kfcs: undefined,
        zzdf: undefined,
        classdf: undefined,
        items: undefined,
        values: undefined,
        pgdx: undefined,
        pgdxxm: undefined,
        createUserid: undefined,
        createTime: undefined,
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$confirm("确认评估数据?评估后数据不能取消", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        callback: (action) => {
          if (action === "confirm") {
            var items = "";
            this.checkList.forEach((item) => {
              //当全选被选中的时候，循环遍历源数据，把数据的每一项加入到默认选中的数组去
              items = items + item + ",";
            });
            if (this.checkList.length == 0) {
              this.msgError("请至少选择一项数据");
            } else {
              this.form.id = this.dayflowassessmentId;
              // this.form.classid = this.classid;
              this.form.items = items;
              this.form.pgdx = this.teacherName;
              this.form.bzbh = this.zbjsxm;
              this.form.planid = this.planid;
              this.form.kfz = this.kfz;
              this.form.bzmf = this.bzmf;
              updateDayflowassessment(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("评估成功");
                  this.$router.go(-1);
                  this.$store.dispatch("tagsView/delView", this.$route);
                  // this.$router.push({
                  //   path:
                  //     "/benyi/dayflowassessment",
                  // });
                }
              });
            }
          } else {
          }
        },
      });
    },
  },
};
</script>
<style lang="scss" scoped>
// 禁止复制
div {
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}
.title {
  padding: 10px 0;
  font-weight: 600;
  flex-wrap: wrap;
  padding-right: 10px;
  line-height: 20px;
  span {
    font-size: 16px;
    padding: 0 4px;
    // &:nth-of-type(2) {
    //   font-size: 14px;
    //   font-weight: normal;
    // }
  }
}
.block {
  padding: 10px;
  color: #333;
  .block-item-title {
    padding: 10px 0;
    margin: 0;
    font-size: 16px;
    font-weight: 500;
    &::before {
      content: "";
      margin-right: 8px;
      width: 4px;
      height: 14px;
      background: #1890ff;
    }
  }

  .block-content {
    border-radius: 5px;
    padding: 10px;
    background: #fcfcfc;
    .block-content-title {
      font-size: 14px;
      font-weight: bold;
      line-height: 24px;
      padding-bottom: 5px;
      .num {
        font-size: 16px;
      }
    }
    .checkbox-content {
      padding-left: 20px;
      margin-bottom: 15px;
    }
    .checkbox-item {
      font-size: 14px;
      line-height: 22px;
    }
    .check-info {
      padding-left: 24px;
      line-height: 18px;
      font-size: 12px;
      color: #999;
      p {
        padding-left: 10px;
      }
    }
    .el-checkbox {
      display: flex;
      white-space: normal;
      .el-checkbox__input {
        margin-top: 2px;
      }
    }
  }
}
@media (max-width: 768.98px) {
  .title span {
    font-size: 12px;
  }
}
</style>
