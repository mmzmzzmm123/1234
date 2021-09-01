<template>
  <div class="table-container" ref="printMe">
    <h2 class="title">{{ title }}</h2>
    <div class="table">
      <div class="print no-print">
        <el-button
          type="primary"
          plain
          size="mini"
          icon="el-icon-printer"
          @click="prints"
        ></el-button>
      </div>
      <table>
        <tr class="align-center">
          <!-- <td v-for="h in headerData" :key="h.title">
            <b class="table-title">{{h.title}}</b>
            {{h.name}}
          </td>-->
          <td style="width: 20%">
            <b class="table-title">班级：</b>
            {{ classname }}
          </td>
          <td style="width: 15%">
            <b class="table-title">月份：</b>
            {{ month }}
          </td>
          <td colspan="2" style="width: 45%">
            <b class="table-title">月主题：</b>
            {{ monththeme }}
          </td>
          <td style="width: 20%">
            <b class="table-title">制表人：</b>
            {{ tbr }}
          </td>
        </tr>
        <tr class="align-center table-bg">
          <td v-for="h in bodyData.title" :key="h.prop">
            <b>{{ h.label }}</b>
          </td>
        </tr>
        <tr v-for="item in bodyData.monthplanitemList" :key="item.id">
          <td
            v-if="item.theme"
            :rowspan="bodyData.monthplanitemList.length"
            class="align-center"
          >
            <span>{{ monththeme }}</span>
          </td>
          <td class="align-center">{{ item.zc }}</td>
          <td class="align-center">{{ item.starttime }}至{{ item.endtime }}</td>
          <td class="align-center" v-if="item.activityid != undefined">
            <!-- {{ themeactivityFormat(item.activityid) }} -->
            <router-link
              style="color: blue"
              v-for="(index, item) in item.activityid.split(';')"
              :key="item"
              :to="url + index"
              ><p>{{ themeactivityFormat(index) }}</p></router-link
            >
          </td>
          <td>{{ item.jzzc }}</td>
        </tr>
        <tr>
          <td class="align-center">本月微型课程</td>
          <td colspan="4" v-html="wxkc"></td>
        </tr>
        <tr>
          <td class="align-center">备注</td>
          <td colspan="4">{{ bz }}</td>
        </tr>
        <tr>
          <td class="align-center">教学主管审批</td>
          <td colspan="4">{{ spyj }}</td>
        </tr>
      </table>
      <!-- <p
        class="warning"
      >注：此周计划表不需要发给家长，只需上报教学主管。制定班级一周教学与活动计划表，请使用班级管理模块中“教学与游戏活动周计划表”，以上报教学主管和作为周计划通知发给家长。</p>-->
    </div>
  </div>
</template>

<script>
import { listActivityById } from "@/api/benyi/activity";
import { listMonthplan, getMonthplan } from "@/api/benyi/thememonthplan";
import { listMonthplanitem } from "@/api/benyi/thememonthplanitem";
import { listThemeByIds, listThemeByActivityIds } from "@/api/benyi/theme";

export default {
  name: "MonthTable",
  data() {
    return {
      //url
      url: "/benyi_course/tremplan/themestudy/",
      tableData: [],
      title: "",
      month: "",
      classname: "",
      tbr: "",
      monththeme: "",
      wxkc: "",
      bz: "",
      spyj: "",
      //主题活动
      themeactivityOptions: [],
      //根据活动id获取的theme名称
      themenamebyactivityids: "",
      bodyData: {
        title: [
          {
            label: "主题",
            prop: "theme",
          },
          {
            label: "周次",
            prop: "day",
          },
          {
            label: "时间",
            prop: "name",
          },
          {
            label: "活动名称",
            prop: "term",
          },
          {
            label: "家长支持",
            prop: "help",
          },
        ],
        monthplanitemList: [
          {
            theme: "",
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
        mpid: undefined,
      },
    };
  },
  created() {
    const thememonthplanid = this.$route.params && this.$route.params.id;
    this.getThemeMonthPlan(thememonthplanid);
  },
  methods: {
    getThemeMonthPlan(thememonthplanid) {
      getMonthplan(thememonthplanid).then((response) => {
        this.queryParams.mpid = response.data.id;
        this.title = response.data.name;
        this.month = response.data.month;
        this.classname = response.classname;
        this.tbr = response.createusername;
        this.wxkc = response.data.wxkc;
        this.bz = response.data.remarks;
        this.spyj = response.data.spyj;

        this.themeFormat(response.data.themes);

        this.getList();
      });
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
    /** 查询主题整合周计划明细列表 */
    getList() {
      //console.log(this.queryParams.wpid);
      listMonthplanitem(this.queryParams).then((response) => {
        if (response.rows.length > 0) {
          this.bodyData.monthplanitemList = response.rows;

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

          if (response.rows.length > 0) {
            this.getThemeActivityList(array);
          }

          this.bodyData.monthplanitemList[0].theme = activityIds;
        } else {
          this.bodyData.monthplanitemList[0].theme = " ";
        }
      });
    },
    // 主题--字典状态字典翻译
    // themeactivityFormat(activityid) {
    //   if (activityid != null) {
    //     var ilength = activityid.split(";").length - 1;
    //     var names = "";
    //     for (var i = 1; i < ilength; i++) {
    //       // console.log(activityid.split(";")[i]);
    //       // console.log(this.themeactivityOptions);
    //       names =
    //         names +
    //         this.selectMoeDictLabel(
    //           this.themeactivityOptions,
    //           activityid.split(";")[i]
    //         ) +
    //         "；";
    //     }
    //     //this.selectDictLabel(this.scopeOptions, row.xnxq);
    //     // console.log(names);
    //     return names;
    //   }
    //   return "";
    // },
    themeactivityFormat(activityid) {
      if (activityid != "" && activityid != null) {
        var name = this.selectMoeDictLabel(
          this.themeactivityOptions,
          activityid
        );
        return name;
      }
      return "";
    },
    //主题活动
    getThemeActivityList(themeid) {
      listActivityById(themeid).then((response) => {
        // console.log("111" + themeid);
        // console.log("222" + response.rows);
        this.themeactivityOptions = response.rows;
      });
    },
    //打印
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