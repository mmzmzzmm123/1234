<template>
  <div class="table-container" ref="printMe">
    <h2 class="title">主题整合课程周教学计划表</h2>
    <div class="table">
      <div class="print no-print">
        <el-button type="primary" plain size="mini" icon="el-icon-printer" @click="prints"></el-button>
      </div>
      <table>
        <tr class="align-center">
          <td v-for="h in headerData" :key="h.title">
            <b class="table-title">{{h.title}}</b>
            {{h.name}}
          </td>
        </tr>
        <tr class="align-center table-bg">
          <td v-for="h in bodyData.title" :key="h.prop">
            <b>{{h.label}}</b>
          </td>
        </tr>
        <tr v-for="item in bodyData.data" :key="item.day">
          <td v-if="item.theme" :rowspan="item.rowspan" class="align-center">
            <span>{{item.theme}}</span>
          </td>
          <td class="align-center">{{item.day}}</td>
          <td>{{item.name}}</td>
          <td class="align-center">{{item.term}}</td>
          <td v-if="item.help" :rowspan="item.rowspan">{{item.help}}</td>
        </tr>
        <tr>
          <td class="align-center">备注</td>
          <td colspan="4"></td>
        </tr>
        <tr>
          <td class="align-center">主管审批</td>
          <td colspan="4"></td>
        </tr>
      </table>
      <p
        class="warning"
      >注：此周计划表不需要发给家长，只需上报教学主管。制定班级一周教学与活动计划表，请使用班级管理模块中“教学与游戏活动周计划表”，以上报教学主管和作为周计划通知发给家长。</p>
    </div>
  </div>
</template>

<script>

export default {
  name: "WeekTable",
  data() {
    return {
      tableData: [],
      headerData: [
        {
          title: "班级：",
          name: "多多班",
        },
        {
          title: "月份：",
          name: "3月份",
        },
        {
          title: "周次：",
          name: "多多班",
        },
        {
          title: "月主题：",
          name: "多多班",
        },
        {
          title: "制表人：",
          name: "多多班",
        },
      ],
      bodyData: {
        title: [
          {
            label: "分主题",
            prop: "theme",
          },
          {
            label: "天",
            prop: "day",
          },
          {
            label: "活动名称",
            prop: "name",
          },
          {
            label: "分组性质",
            prop: "term",
          },
          {
            label: "家长支持",
            prop: "help",
          },
        ],
        data: [
          {
            theme: "春天的颜色",
            day: "周一",
            name: "活动",
            term: "同质",
            help: "1,sdfsdf,2sdfsd,3问问",
            rowspan: 5,
          },
          {
            day: "周二",
            name: "活动",
            term: "同质",
          },
          {
            day: "周三",
            name: "活动",
            term: "同质",
          },
          {
            day: "周四",
            name: "活动",
            term: "同质",
          },
          {
            day: "周五",
            name: "活动",
            term: "同质",
          },
        ],
      },
    };
  },
  created() {},
  methods: {
    prints () {
      this.$print(this.$refs.printMe);
    }
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
@page{
  size:  auto;   /* auto is the initial value */
  margin: 3mm;  /* this affects the margin in the printer settings */
}

</style>