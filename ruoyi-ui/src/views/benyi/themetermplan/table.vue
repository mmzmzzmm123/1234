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
          <td style="width: 60%">
            <b class="table-title">学期：</b>
            {{ xnxqFormat(xnxq) }}
          </td>
          <td style="width: 20%">
            <b class="table-title">制定人：</b>
            {{ tbr }}
          </td>
        </tr>
        <tr class="align-center table-bg">
          <td v-for="h in bodyData.title" :key="h.prop">
            <b>{{ h.label }}</b>
          </td>
        </tr>
        <tr v-for="item in bodyData.termplanitemList" :key="item.id">
          <td class="align-center">
            <span>{{ item.month }}</span>
          </td>
          <td class="align-center">
            <router-link
            style="margin:10px"
              v-for="(index, item) in item.themeconent.split(';')"
              :key="item"
              :to="url + (Number(index) + Number(99999))"
              >{{ themeFormat(index) }}</router-link
            >
          </td>
          <td>{{ item.remark }}</td>
        </tr>
        <tr>
          <td class="align-center">备注</td>
          <td colspan="2">{{ remark }}</td>
        </tr>
        <tr>
          <td class="align-center">教学主管审批</td>
          <td colspan="2">{{ spyj }}</td>
        </tr>
      </table>
      <!-- <p
        class="warning"
      >注：此周计划表不需要发给家长，只需上报教学主管。制定班级一周教学与活动计划表，请使用班级管理模块中“教学与游戏活动周计划表”，以上报教学主管和作为周计划通知发给家长。</p>-->
    </div>
  </div>
</template>

<script>
import { listTermplan, getTermplan } from "@/api/benyi/themetermplan";
import {
  listTermplanitem,
  getTermplanitem,
} from "@/api/benyi/themetermplanitem";
import { listTheme } from "@/api/benyi/theme";

export default {
  name: "TermTable",
  data() {
    return {
      //url
      url: "/benyi_course/tremplan/themestudy/",
      //学年学期
      xnxqOptions: [],
      //主题
      themeOptions: [],
      title: "",
      xnxq: "",
      classname: "",
      tbr: "",
      remark: "",
      spyj: "",
      bodyData: {
        title: [
          {
            label: "月份",
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
        termplanitemList: [],
      },
      // 查询参数
      queryParams: {
        tpid: undefined,
      },
    };
  },
  created() {
    const themetermplanid = this.$route.params && this.$route.params.id;
    this.queryParams.tpid = themetermplanid;
    this.getThemeTermPlan(themetermplanid);
    this.getThemeList();
    this.getDicts("sys_xnxq").then((response) => {
      this.xnxqOptions = response.data;
    });
  },
  methods: {
    // 学年学期类型--字典状态字典翻译
    xnxqFormat(xnxq) {
      return this.selectDictLabel(this.xnxqOptions, xnxq);
    },
    // 主题--字典状态字典翻译
    themeFormat(themeid) {
      if (themeid != "" && themeid != null) {
        //console.log(themeid);
        var name = this.selectMoeDictLabel(this.themeOptions, themeid);
        return name;
      }
      return "";
    },
    //主题
    getThemeList() {
      listTheme(null).then((response) => {
        //console.log(response.rows);
        this.themeOptions = response.rows;
      });
    },
    //获取教学学期计划
    getThemeTermPlan(themetermplanid) {
      getTermplan(themetermplanid).then((response) => {
        // console.log(response.data);
        this.title = response.data.name;
        this.tbr = response.createusername;
        this.classname = response.classname;
        this.xnxq = response.data.xnxq;
        this.spyj = response.data.shyj;
        this.remark = response.data.remark;
      });
      this.getThemeTermItemPlan();
    },
    //获取学期月份详细计划
    getThemeTermItemPlan() {
      listTermplanitem(this.queryParams).then((response) => {
        // console.log(response.rows);
        this.bodyData.termplanitemList = response.rows;
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