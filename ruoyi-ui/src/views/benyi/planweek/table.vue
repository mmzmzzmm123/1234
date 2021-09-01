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
          <td style="width:20%">
            <b class="table-title">班级：</b>
            {{classname}}
          </td>
          <td style="width:20%">
            <b class="table-title">月份：</b>
            {{month}}
          </td>
          <td style="width:20%">
            <b class="table-title">周次：</b>
            第{{zc}}周
          </td>
          <td colspan="3" style="width:40%">
            <b class="table-title">月主题：</b>
            {{monththeme}}
          </td>
        </tr>
        
        <tr class="align-center">
          <td rowspan="5">
            <span>周教学目标</span>
          </td>
          <td style="width: 140px;">社会</td>
          <td v-html="sh" colspan="5"></td>
        </tr>
        <tr class="align-center"> 
          <td>语言</td>
          <td v-html="yy" colspan="5"></td>
        </tr>
        <tr class="align-center">
          <td>健康</td>
          <td v-html="jk" colspan="5"></td>
        </tr>
        <tr class="align-center">
          <td>科学</td>
          <td v-html="kx" colspan="5"></td>
        </tr>
        <tr class="align-center">
          <td>艺术</td>
          <td v-html="ys" colspan="5"></td>
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
          <td class="align-center" v-html="item.content"></td>
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
  listPlanweekitem,
  getPlanweekitem,
  delPlanweekitem,
  addPlanweekitem,
  updatePlanweekitem,
  exportPlanweekitem
} from "@/api/benyi/planweekitem";

import { getPlanweek, listPlanweek } from "@/api/benyi/planweek";

export default {
  name: "PlayTable",
  data() {
    return {
      tableData: [],
      title: "教学与游戏活动周计划表",
      zc: "",
      month: "",
      classname: "",
      tbr: "",
      monththeme: "",
      bz: "",
      spyj: "",
      sh: "",
      yy: "",
      jk: "",
      kx: "",
      ys: "",
      //根据活动id获取的theme名称
      themenamebyactivityids: "",
      activitytypeOptions: [],
      //分组性质
      fzxzOptions: [],
      // item列表
      planweekitemList: [],
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
        wid: undefined,
      },
      
    };
  },
  created() {
    const planweekid = this.$route.params && this.$route.params.id;
    this.getWPlanweek(planweekid);
    this.getDicts("sys_dm_fzxz").then((response) => {
      this.fzxzOptions = response.data;
    });
    this.getDicts("sys_dm_qyhdxs").then(response => {
      this.activitytypeOptions = response.data;
    });
  },
  methods: {
    // 活动类型字典翻译
    activitytypeFormat(activitytype) {
      return this.selectDictLabel(this.activitytypeOptions, activitytype);
    },
    // 分组性质类型--字典状态字典翻译
    fzxzFormat(fzxz) {
      return this.selectDictLabel(this.fzxzOptions, fzxz);
    },
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
    
    //获取周教学计划详情
    getWPlanweek(planweekid) {
      getPlanweek(planweekid).then((response) => {
        //(response);
        this.queryParams.wid = response.data.id;
        this.defaultWeekType = response.data.id;
        this.month = response.data.month;
        this.zc = response.data.weekly;
        this.monththeme = response.data.themeofweek;
        this.sh = response.data.jxmbSh;
        this.yy = response.data.jxmbYy;
        this.jk = response.data.jxmbJk;
        this.kx = response.data.jxmbKx;
        this.ys = response.data.jxmbYs;
        // console.log(this.month);

        this.queryParams.wpid = response.data.id;
        this.title = response.data.name;
        
        //this.month = response.data.month;
        this.classname = response.classname;
        this.tbr = response.createusername;
        this.bz = response.data.remark;
        this.spyj = response.data.shyj;

        this.getList();
      });
    },
    /** 查询主题整合周计划明细列表 */
    async getList() {
      //console.log(this.queryParams.wpid);
      await listPlanweekitem(this.queryParams).then((response) => {
        this.bodyData.planweekitemList = response.rows;
        // console.log(this.bodyData.planweekitemList);

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
      });
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