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
            {{month}}
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
        <tr v-for="item in bodyData.weekplanitemList" :key="item.daytime">
          <td class="align-center">
            <span>{{item.month}}</span>
          </td>
          <td>{{item.theme}}</td>
          <td>{{item.info}}</td>
        </tr>
        <tr>
          <td colspan="3">
            <h2 class="title title2">下学期课程计划（2018年3月-2019年3月）</h2>
          </td>
        </tr>
        <tr class="align-center">
          <!-- <td v-for="h in headerData" :key="h.title">
            <b class="table-title">{{h.title}}</b>
            {{h.name}}
          </td>-->
          <td>
            <b class="table-title">班级：</b>
            {{classname}}
          </td>
          <td>
            <b class="table-title">学期：</b>
            {{month}}
          </td>
          <td>
            <b class="table-title">制定人：</b>
            {{tbr}}
          </td>
        </tr>
        <tr class="align-center table-bg">
          <td v-for="h in bodyData.title" :key="h.prop">
            <b>{{h.label}}</b>
          </td>
        </tr>
        <tr v-for="item in bodyData.weekplanitemList" :key="item.daytime">
          <td class="align-center">
            <span>{{item.month}}</span>
          </td>
          <td>{{item.theme}}</td>
          <td>{{item.info}}</td>
        </tr>
        <tr>
          <td class="align-center">教学主管审批</td>
          <td colspan="2">{{bz}}</td>
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
  listWeekplanitem,
  getWeekplanitem,
} from "@/api/benyi/themeweekplanitem";

import { listWeekplan, getWeekplan } from "@/api/benyi/themeweekplan";
import { listActivityByThemeId } from "@/api/benyi/activity";
import { listMonthplan } from "@/api/benyi/thememonthplan";
import { listMonthplanitem } from "@/api/benyi/thememonthplanitem";
import { listThemeByIds, listThemeByActivityIds } from "@/api/benyi/theme";

export default {
  name: "TermTable",
  data() {
    return {
      tableData: [],
      title: "主题整合课程学期教学计划表",
      zc: "",
      month: "",
      classname: "中二班",
      tbr: "",
      monththeme: "",
      bz: "",
      spyj: "",
      //根据活动id获取的theme名称
      themenamebyactivityids: "",
      //分组性质
      fzxzOptions: [],
      //主题活动
      themeactivityOptions: [],
      bodyData: {
        title: [
          {
            label: "月时间",
            prop: "theme",
          },
          {
            label: "主题名称",
            prop: "name",
          },
          {
            label: "备注",
            prop: "help",
          },
        ],
        weekplanitemList: [
          {
            month: '1月',
            theme: "春天的颜色",
            info: '时代峰峻的斯洛伐克'
          },
          // {
          //   day: "周二",
          //   name: "活动",
          //   term: "同质",
          // },
          // {
          //   day: "周三",
          //   name: "活动",
          //   term: "同质",
          // },
          // {
          //   day: "周四",
          //   name: "活动",
          //   term: "同质",
          // },
          // {
          //   day: "周五",
          //   name: "活动",
          //   term: "同质",
          // },
        ],
      },
      // 查询参数
      queryParams: {
        wpid: undefined,
      },
      // 查询参数
      queryParams_MonPlan: {
        xnxq: undefined,
        month: undefined,
        status: "2",
      },
      queryParams_MonPlanItem: {
        mpid: undefined,
        zc: undefined,
      },
    };
  },
  created() {
    const themeweekplanid = this.$route.params && this.$route.params.id;
    this.getThemeWeekPlan(themeweekplanid);
    this.getDicts("sys_dm_fzxz").then((response) => {
      this.fzxzOptions = response.data;
    });
  },
  methods: {
    //主题翻译
    async themeFormat(themeids) {
      var themename = "";
      var ids = themeids.split(";");
      var array = [];
      //console.log(ids);
      ids.forEach(function (value, key, arr) {
        //console.log(value); // 结果依次为1，2，3
        if (value != "") {
          array.push(parseInt(value));
        }
      });

      if (array.length > 0) {
        await listThemeByIds(array).then((response) => {
          response.rows.forEach(function (value1, key1, arr1) {
            themename = themename + value1.name + ";";
          });
        });
        //(themename);
        this.monththeme = themename;
      }
    },
    // 主题--字典状态字典翻译
    themeactivityFormat(activityid) {
      if (activityid != null) {
        var ilength = activityid.split(";").length - 1;
        var names = "";
        for (var i = 1; i < ilength; i++) {
          names =
            names +
            this.selectMoeDictLabel(
              this.themeactivityOptions,
              activityid.split(";")[i]
            ) +
            "；";
        }
        //this.selectDictLabel(this.scopeOptions, row.xnxq);
        return names;
      }
      return "";
    },
    // 分组性质类型--字典状态字典翻译
    fzxzFormat(fzxz) {
      return this.selectDictLabel(this.fzxzOptions, fzxz);
    },
    //主题活动
    getThemeActivityList(themeid) {
      listActivityByThemeId(themeid).then((response) => {
        //console.log(response.rows);
        this.themeactivityOptions = response.rows;
      });
    },
    //获取周教学计划详情
    getThemeWeekPlan(themeweekplanid) {
      getWeekplan(themeweekplanid).then((response) => {
        //(response);
        this.queryParams.wpid = response.data.id;
        this.title = response.data.name;
        this.zc = response.data.zc;
        this.month = response.data.month;
        this.classname = response.classname;
        this.tbr = response.createusername;
        this.bz = response.data.remark;
        this.spyj = response.data.shyj;

        //查找活动id
        this.queryParams_MonPlan.month = response.data.month;
        this.queryParams_MonPlan.xnxq = response.data.xnxq;
        listMonthplan(this.queryParams_MonPlan).then((resMonPlan) => {
          //console.log(resMonPlan.rows);
          //获取的月主题
          var monththemeids = resMonPlan.rows[0].themes;

          this.themeFormat(monththemeids);

          this.queryParams_MonPlanItem.mpid = resMonPlan.rows[0].id;
          this.queryParams_MonPlanItem.zc = response.data.zc;
          listMonthplanitem(this.queryParams_MonPlanItem).then(
            (resMonPlanItem) => {
              //console.log(resMonPlanItem.rows);
              var activityids = resMonPlanItem.rows[0].activityid.split(";");
              var array = [];
              //console.log(arr);
              activityids.forEach(function (value, key, arr) {
                //console.log(value); // 结果依次为1，2，3
                if (value != "") {
                  array.push(parseInt(value));
                }
              });
              this.getThemeActivityList(array);
            }
          );
        });

        this.getList();
      });
    },
    /** 查询主题整合周计划明细列表 */
    async getList() {
      //console.log(this.queryParams.wpid);
      await listWeekplanitem(this.queryParams).then((response) => {
        this.bodyData.weekplanitemList = response.rows;

        //获取所有的活动id
        var activityIds = "";
        response.rows.forEach(function (value, key, arr) {
          if (value.activityid != null) {
            activityIds = activityIds + value.activityid + ";";
          }
        });
        var array = [];
        activityIds.split(";").forEach(function (value, key, arr) {
          if (value != "") {
            array.push(parseInt(value));
          }
        });
        //获取所有的活动id截止
        this.getThemeByActivityIds(array);
      });
    },
    //根据活动id获取theme列表
    async getThemeByActivityIds(array) {
      if (array.length > 0) {
        var themename = "";
        await listThemeByActivityIds(array).then((response) => {
          response.rows.forEach(function (value, key, arr) {
            themename = themename + value.name + ";";
          });
        });
        //console.log(themename);
        this.themenamebyactivityids = themename;
      }

      this.bodyData.weekplanitemList[0].theme =
        this.themenamebyactivityids == ""
          ? "未设置"
          : this.themenamebyactivityids;
    },
    prints() {
      //console.log(this.$refs.printMe);
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