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
          <td class="w140">
            <b class="table-title">班级：</b>
            {{ classname }}
          </td>
          <td>
            <b class="table-title">学期：</b>
            {{ xnxqFormat(xnxq) }}
          </td>
          <td class="w200">
            <b class="table-title">幼儿姓名：</b>
            {{ childFormat(childid) }}
          </td>
        </tr>
        <tr>
          <td class="align-center">观察记录</td>
          <td colspan="2"><div v-html="gcjl"></div></td>
        </tr>
        <tr>
          <td class="align-center">备注</td>
          <td colspan="2">{{ gcjlremarks }}</td>
        </tr>
        <tr>
          <td class="align-center">问题与方案</td>
          <td colspan="2"><div v-html="wtyfa"></div></td>
        </tr>
        <tr>
          <td class="align-center">备注</td>
          <td colspan="2">{{ wtyfaremarks }}</td>
        </tr>
        <tr>
          <td class="align-center">评估结果</td>
          <td colspan="2"><div v-html="pgjg"></div></td>
        </tr>
        <tr>
          <td class="align-center">备注</td>
          <td colspan="2">{{ pgjgremarks }}</td>
        </tr>
        <tr>
          <td class="align-center">教育计划</td>
          <td colspan="2"><div v-html="jyjh"></div></td>
        </tr>
        <tr>
          <td class="align-center">备注</td>
          <td colspan="2">{{ jyjhremarks }}</td>
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
  listTeacher,
  getTeacher,
} from "@/api/benyi/learndevelopmentteacher";

import { listChild } from "@/api/benyi/child";

import Editor from "@/components/Editor";

export default {
  name: "Teacher",
  components: {
    Editor,
  },
  data() {
    return {
      title: "幼儿学习与发展档案(教师)",
      classname: "",
      childid: "",
      xnxq: "",
      gcjl: "",
      gcjlremarks: "",
      wtyfa: "",
      wtyfaremarks: "",
      pgjg: "",
      pgjgremarks: "",
      jyjh: "",
      jyjhremarks: "",
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 儿童学习与发展档案（教师）表格数据
      teacherList: [],
      //幼儿
      childOptions: [],
      //学年学期
      xnxqOptions: [],
      // 弹出层标题
      // title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      queryParams: {
        id: "",
      },
    };
  },
  created() {
    const childId = this.$route.params && this.$route.params.id;
    this.queryParams.id = childId;
    //console.log(childId);
    this.getList();
    this.getChildList();
    this.getDicts("sys_xnxq").then((response) => {
      this.xnxqOptions = response.data;
    });
  },
  methods: {
    // 字典翻译
    childFormat(childid) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.childOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + childid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    // 学年学期类型--字典状态字典翻译
    xnxqFormat(xnxq) {
      return this.selectDictLabel(this.xnxqOptions, xnxq);
    },
    //打印
    prints() {
      //console.log(this.$refs.printMe);
      this.$print(this.$refs.printMe);
    },
    //获取幼儿列表
    getChildList() {
      listChild(null).then((response) => {
        this.childOptions = response.rows;
        this.classname = response.rows[0].bjmc;
        // console.log(response.rows);
      });
    },
    /** 查询儿童学习与发展档案（教师）列表 */
    getList() {
      this.loading = true;
      listTeacher(this.queryParams).then((response) => {
        this.teacherList = response.rows;
        this.xnxq = response.rows[0].xnxq;
        this.childid = response.rows[0].childid;
        this.gcjl = response.rows[0].gcjl;
        this.gcjlremarks = response.rows[0].gcjlremarks;
        this.jyjh = response.rows[0].jyjh;
        this.jyjhremarks = response.rows[0].jyjhremarks;
        this.pgjg = response.rows[0].pgjg;
        this.pgjgremarks = response.rows[0].pgjgremarks;
        this.wtyfa = response.rows[0].wtyfa;
        this.wtyfaremarks = response.rows[0].wtyfaremarks;
        //console.log(response.rows);
        this.total = response.total;
        this.loading = false;
      });
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
