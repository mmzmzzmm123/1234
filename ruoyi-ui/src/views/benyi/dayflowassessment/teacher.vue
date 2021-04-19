<template>
  <div class="app-container" v-loading="loading">
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
      <el-tab-pane
        v-for="item in detailOptions"
        :key="item.id"
        :label="item.name"
        :name="item.name"
      >
        <div
          class="block"
          v-for="itemTask in dayflowtaskList.filter(
            (p) => p.detailId == item.id
          )"
          :key="itemTask.code"
        >
          <h2 class="block-item-title flex align-center">
            {{ itemTask.taskLable }}
          </h2>
          <div
            class="checkbox-content"
            v-for="(itemBz, index) in dayflowstandardList.filter(
              (p) => p.taskCode == itemTask.code
            )"
            :key="itemBz.id"
          >
            <div
              class="checkbox-item flex align-center justify-between"
              :class="{ line: index !== 0 }"
            >
              <p class="left-info">
                {{ itemBz.standardTitle }}
              </p>
              <div class="right-number flex align-center justify-end">
                <span>分值: {{ itemBz.score }}分</span>
                <el-input-number
                  class="number-input"
                  v-model="itemBz.mrz"
                  :precision="2"
                  :step="0.1"
                  :max="0"
                ></el-input-number>
              </div>
            </div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import {
  listDayflowassessment,
  getDayflowassessment,
  addDayflowassessment,
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
      // 遮罩层
      loading: false,
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
      activeName: "早间接待",
      // 表单校验
      rules: {
        classid: [{ required: true, message: "班级不能为空", trigger: "blur" }],
        pgdx: [
          { required: true, message: "评估对象不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getClassList();
    this.getDayFlowList();
    this.getTaskList();
  },
  watch: {
    "form.classid": function (val) {
      this.getUserListByBjbh(val);
      this.getTaskList();
    },
    "form.pgdx": function (val) {
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
      listDayflowtask(null).then((response) => {
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
        list: [],
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm: function () {
      //console.log(this.dayflowstandardList);
      this.$refs["form"].validate((valid) => {
        if (valid) {
          // console.log(this.form);
          this.$confirm("确认提交评估数据?评估后数据不能取消", "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
            callback: (action) => {
              if (action === "confirm") {
                this.loading = true;
                this.form.list = this.dayflowstandardList;
                addDayflowassessment(this.form).then((response) => {
                  if (response.code === 200) {
                    this.msgSuccess("评估成功");
                    this.loading = false;
                  }
                });
              }
            },
          });
        }
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
.number-input {
  width: 120px;
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
  .checkbox-item {
    padding: 6px 0;
    &.line {
      border-top: 1px solid #dadada;
    }
    .left-info {
      line-height: 22px;
    }
    .right-number {
      flex: 0 0 205px;
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
