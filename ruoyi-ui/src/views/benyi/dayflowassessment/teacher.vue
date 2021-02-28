<template>
  <div class="app-container">
    <div class="flex align-center justify-between student-main-title">
      <p class="title flex align-center">
        <span>幼儿园：{{ deptFormat(deptId) }} </span>
        <span>行为人：{{ teacherFormat(teacherName)  }} </span>
        <span>班级：{{ classFormat(classid) }} </span>
        <span>学期：{{ xnxqFormat(xnxq) }} </span>
        <span>班长：{{ teacherFormat(zbjsxm) }} </span>
        <span>配班教师：{{ teacherFormat(pbjs) }} </span>
        <span>助理教师：{{ teacherFormat(zljs) }} </span>
      </p>
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
            <el-checkbox-group v-model="checkList">
              <el-checkbox :label="itemBz.id" :key="itemBz.id"
                >{{ itemBz.standardTitle }}分值: {{ itemBz.score }}分
              </el-checkbox>
            </el-checkbox-group>
          </p>
          <div class="check-info" v-if="itemBz.score">
            <!-- {{ itemBz.score }} -->
          </div>
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
import { listStandard } from "@/api/benyi/dayflow/biaozhun/standard";
import { listDept, getDept } from "@/api/system/dept";
import { listClass } from "@/api/system/class";
import { listUser } from "@/api/system/user";

export default {
  name: "Assessmentstudent",

  data() {
    return {
      teacherName: "",
      planid: "",
      xnxq: "",
      classid: "",
      zbjsxm: "",
      pbjs: "",
      zljs: "",
      deptId: "",
      bzmf: "",
      kfz: "",
      classdf: "",
      assessmentsCode: "",
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
      // 学校列表
      deptOptions: [],
      //班级
      classOptions: [],
      // 所有教师
      userOptions: [],
      // 学年学期
      xnxqOptions: [],
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
    };
  },
  created() {
    // this.getList();
    const dayflowassessmentId = this.$route.params && this.$route.params.id;
    this.dayflowassessmentId = dayflowassessmentId;
    this.getDayflowassessmentContent(dayflowassessmentId);
    this.getDeptList();  
    this.getClassList();
    this.getUserList();
    this.getDicts("sys_xnxq").then((response) => {
      this.xnxqOptions = response.data;
    });
  },
  methods: {
    getDayflowassessmentContent(dayflowassessmentId) {
      getDayflowassessment(dayflowassessmentId).then((response) => {
        if (response.code == "200") {
          this.teacherName = response.data.pgdx;
          this.xnxq = response.data.xnxq;
          this.classid = response.data.classid;
          this.bzmf = response.data.bzmf;
          this.kfz = response.data.kfz;
          this.zbjsxm = response.data.bzbh;
          this.pbjs = response.data.pbbh;
          this.zljs = response.data.zlbh;
          this.deptId = response.data.deptId;
          this.planid = response.data.planid;
          this.classdf = response.data.classdf;
          this.queryParams_detail.detailId = response.data.bzid;
          this.assessmentsCode = response.data.code;
          this.getTaskList();
        }
      });
    },
    // 学校字典翻译
    deptFormat(deptId) {
      var actions = [];
      var datas = this.deptOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].deptId == "" + deptId) {
          actions.push(datas[key].deptName);
          return false;
        }
      });
      return actions.join("");
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
    // 教师字典翻译
    teacherFormat(val) {
      var actions = [];
      var datas = this.userOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].userId == "" + val) {
          actions.push(datas[key].nickName);
          return false;
        }
      });
      return actions.join("");
    },
    // 学年学期类型--字典状态字典翻译
    xnxqFormat(xnxq) {
      return this.selectDictLabel(this.xnxqOptions, xnxq);
    },
    /** 查询用户列表 */
    getUserList() {
      listUser(null).then(
        (response) => {
          this.userOptions = response.rows;
        }
      );
    },
    /** 查询幼儿园机构列表 */
    getDeptList() {
      listDept(null).then(response => {
        this.deptOptions = response.data;
      });
    },
    //班级列表
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
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
                  this.$router.go(-1)
                  this.$store.dispatch('tagsView/delView', this.$route)
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
