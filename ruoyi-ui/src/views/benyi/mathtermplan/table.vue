<template>
  <div class="table-container" ref="printMe">
    <h2 class="title">{{title}}</h2>
    <div class="table">
      <div class="print no-print">
        <el-button type="primary" plain size="mini" icon="el-icon-printer" @click="prints"></el-button>
      </div>
      <table>
        <tr class="align-center">
          <!-- <td v-for="h in headerData" :key="h.title">
            <b class="table-title">{{h.title}}</b>
            {{h.name}}
          </td>-->
          <td class="w140">
            <b class="table-title">班级：</b>
            {{classname}}
          </td>
          <td>
            <b class="table-title">学期：</b>
            {{xnxqFormat(xnxq)}}
          </td>
          <td class="w200">
            <b class="table-title">制定人：</b>
            {{tbr}}
          </td>
        </tr>
        <tr class="align-center table-bg">
          <td v-for="h in bodyData.title" :key="h.prop">
            <b>{{h.label}}</b>
          </td>
        </tr>
        <tr v-for="item in bodyData.termplanitemList" :key="item.id">
          <td class="align-center">
            <span>{{item.month}}</span>
          </td>
          <td class="align-center">{{mathFormat(item.mathconent)}}</td>
          <td>{{item.remark}}</td>
        </tr>
        <tr>
          <td class="align-center">教学主管审批</td>
          <td colspan="2">{{spyj}}</td>
        </tr>
      </table>
      <!-- <p
        class="warning"
      >注：此周计划表不需要发给家长，只需上报教学主管。制定班级一周教学与活动计划表，请使用班级管理模块中“教学与游戏活动周计划表”，以上报教学主管和作为周计划通知发给家长。</p>-->
    </div>
  </div>
</template>

<script>
import { listMathtermplan, getMathtermplan } from "@/api/benyi/mathtermplan";
import {
  listMathtermplanitem,
  getMathtermplanitem,
} from "@/api/benyi/mathtermplanitem";

import { listPlan } from "@/api/benyi/plan";

export default {
  name: "TermTable",
  data() {
    return {
      // 学年学期
      xnxqOptions: [],
      // 方案选项
      mathOptions: [],
      title: "",
      xnxq: "",
      classname: "",
      tbr: "",
      spyj: "",
      bodyData: {
        title: [
          {
            label: "月份",
            prop: "theme",
          },
          {
            label: "方案名称",
            prop: "name",
          },
          {
            label: "备注",
            prop: "help",
          },
        ],
        termplanitemList: [],
      },
      // 查询参数
      queryParams: {
        tpid: undefined,
      },
    };
  },
  created() {
    const mathtermplanid = this.$route.params && this.$route.params.id;
    this.queryParams.tpid = mathtermplanid;
    this.getMathTermPlan(mathtermplanid);
    this.getMathList();
    this.getDicts("sys_xnxq").then((response) => {
      this.xnxqOptions = response.data;
    });
  },
  methods: {
    // 学年学期类型--字典状态字典翻译
    xnxqFormat(xnxq) {
      return this.selectDictLabel(this.xnxqOptions, xnxq);
    },
    // 游戏方案--字典状态字典翻译
    mathFormat(mathids) {
      if (mathids != "" && mathids != null) {
        var ilength = mathids.split(";").length - 1;
        var names = "";
        for (var i = 1; i < ilength; i++) {
          names =
            names +
            this.selectMoeDictLabel(this.mathOptions, mathids.split(";")[i]) +
            " ";
        }
        //this.selectDictLabel(this.scopeOptions, row.xnxq);
        return names;
      }
      return "";
    },
    // 获取游戏数学方案
    getMathList() {
      listPlan(null).then((response) => {
        this.mathOptions = response.rows;
      });
    },
    //获取教学学期计划
    getMathTermPlan(mathtermplanid) {
      getMathtermplan(mathtermplanid).then((response) => {
        this.title = response.data.name;
        this.tbr = response.createusername;
        this.classname = response.classname;
        this.xnxq = response.data.xnxq;
        this.spyj = response.data.spyj;
      });
      this.listMathTermItemPlan();
    },
    //获取学期月份详细计划
    listMathTermItemPlan() {
      listMathtermplanitem(this.queryParams).then((response) => {
        this.bodyData.termplanitemList = response.rows;
      });
    },
    //打印
    prints() {
      this.$print(this.$refs.printMe);
    },
  },
};
</script>
<style lang="scss">
.table-container {
  padding: 30px 10%;
  .w140 {
    width: 140px;
  }
  .w200 {
    width: 200px;
  }
  .title {
    margin: 0;
    font-size: 18px;
    text-align: center;
    padding: 15px 0;
  }
  .title2 {
    padding: 0;
  }
  .align-center {
    text-align: center;
  }
  .table {
    font-size: 14px;
    .print {
      display: flex;
      justify-content: flex-end;
      padding-bottom: 10px;
    }
    p {
      margin: 0;
    }
    table {
      width: 100%;
      border-collapse: collapse;
    }
    table td {
      border: #ccc solid 1px;
      line-height: 24px;
      padding: 8px 5px;
    }
    .table-title {
      font-size: 16px;
    }
    .table-bg {
      background: #f8f8f8;
    }
  }
  .warning {
    padding-top: 20px;
    font-size: 12px;
    color: #666;
  }
}
@media print {
  .table-container {
    padding: 30px 0;
  }
  .print {
    opacity: 0;
  }
}
/*去除页眉页脚*/
@page {
  size: auto; /* auto is the initial value */
  margin: 3mm; /* this affects the margin in the printer settings */
}
</style>