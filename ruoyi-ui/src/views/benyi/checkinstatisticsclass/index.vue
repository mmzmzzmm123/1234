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
            {{classid}}
          </td>
          <td>
            <b class="table-title">班级人数：</b>
            {{30}}
          </td>
          <td>
            <b class="table-title">月份：</b>
            第{{9}}月
          </td>
          <td>
            <b class="table-title">学期：</b>
            第{{1}}学期
          </td>
          <td>
            <b class="table-title">学年：</b>
            {{2020}}年
          </td>
          <td>
            <b class="table-title">班主任：</b>
            {{month}}
          </td>
          <td>
            <b class="table-title">本月出勤率：</b>
            {{month}}%
          </td>
          <td colspan="3">
            <b class="table-title">本月出勤率：</b>
            {{month}}%
          </td>
        </tr>
        
        <tr class="align-center">
          <td rowspan="5">
            <span>周教学目标</span>
          </td>
          <td style="width: 140px;">社会</td>
          <td>{{sh}}</td>
          <td  colspan="4"></td>
        </tr>
        <tr class="align-center"> 
          <td>语言</td>
          <td>{{yy}}</td>
          <td  colspan="4"></td>
        </tr>
        <tr class="align-center">
          <td>健康</td>
          <td>{{jk}}</td>
          <td colspan="4"></td>
        </tr>
        <tr class="align-center">
          <td>科学</td>
          <td>{{kx}}</td>
          <td colspan="4"></td>
        </tr>
        <tr class="align-center">
          <td>艺术</td>
          <td>{{ys}}</td>
          <td colspan="4"></td>
        </tr>

        <tr v-for="item in planweekitemList" :key="item.id">
          <td v-if="item.activitytime" :rowspan="bodyData.planweekitemList.length" class="align-center">
            <span>{{item.activitytime}}</span>
          </td>
          <td class="align-center">{{item.activitytime}}</td>
          <td colspan="4">{{item.activitytime}}</td>
        </tr>
        <tr class="align-center table-bg">
          <td v-for="h in bodyData.title" :key="h.prop">
            <b>{{h.label}}</b>
          </td>
        </tr>
        <tr v-for="item in bodyData.planweekitemList" :key="item.id">
          <td v-if="item.theme" :rowspan="bodyData.planweekitemList.length" class="align-center">
            <span>{{item.theme}}</span>
          </td>
          <td class="align-center">{{item.activitytime}}</td>
          <td class="align-center">{{item.day}}</td>
          <td class="align-center">{{activitytypeFormat(item.activitytype)}}</td>
          <td class="align-center">{{(item.content)}}</td>
        </tr>
      </table>
      <!-- <p
        class="warning"
      >注：此周计划表不需要发给家长，只需上报教学主管。制定班级一周教学与活动计划表，请使用班级管理模块中“教学与游戏活动周计划表”，以上报教学主管和作为周计划通知发给家长。</p>-->
    </div>
  </div>
</template>

<script>
import {
  listDetail,
  getDetail,
  delDetail,
  addDetail,
  updateDetail,
  exportDetail
} from "@/api/benyi/checkindetail";

import { listByCheck, listChild } from "@/api/benyi/child";

import { listClass } from "@/api/system/class";

export default {
  name: "Detail",
  data() {
    return {
      tableData: [],
      title: "教学与游戏活动周计划表",
      schoolid: "",
      classid: "",
      childid: "",
      childname: "",
      type: "",
      createuserid: "",
      month: "",
      bodyData: {
        title: [
          {
            label: "日期",
            prop: "activitytime",
          },
          {
            label: "星期",
            prop: "day",
          },
          {
            label: "区域活动形式",
            prop: "activitytype",
          },
          {
            label: "活动详情",
            prop: "content",
          },
          
          // {
          //   label: "周四",
          //   prop: "help",
          // },
          // {
          //   label: "周五",
          //   prop: "help",
          // },
        ],
        planweekitemList: [
          {
            theme: "春天的颜色",
          },
        ],
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        schoolid: undefined,
        classid: undefined,
        childid: undefined,
        childname: undefined,
        type: undefined,
        createuserid: undefined,
        createTime: undefined
      },
    };
  },
  created() {
    this.getList();
    this.getClassList();
    this.getDicts("sys_dm_cqzt").then(response => {
      this.checkinOptions = response.data;
    });
  },
  methods: {
    //控制按钮可用
    checkSelectable(row) {
      var date = new Date();
      //console.log(date.toLocaleDateString());
      return this.CompareDate(row.createTime, date.toLocaleDateString());
    },
    //比较日期大小
    CompareDate(d1, d2) {
      return new Date(d1.replace(/-/g, "/")) > new Date(d2.replace(/-/g, "/"));
    },
    //班级列表
    getClassList() {
      listClass(null).then(response => {
        this.classOptions = response.rows;
      });
    },
    // 字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.checkinOptions, row.type);
    },
    // 字典翻译
    classFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.classOptions;
      Object.keys(datas).map(key => {
        if (datas[key].bjbh == "" + row.classid) {
          actions.push(datas[key].bjmc);
          return false;
        }
      });
      return actions.join("");
    },
    /** 查询幼儿考勤列表 */
    getList() {
      this.loading = true;
      listDetail(this.queryParams).then(response => {
        this.detailList = response.rows;
        // console.log(response.rows);
        this.total = response.total;
        this.loading = false;
      });
    },
    prints() {
      //console.log(this.$refs.printMe);
      this.$print(this.$refs.printMe);
    },
  }
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