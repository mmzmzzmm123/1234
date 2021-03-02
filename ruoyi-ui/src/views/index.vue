<template>
  <div class="app-container home">
    <h3>年度项目资金 TOP5</h3>
    <el-row :gutter="10" class="mb8">
      <el-col :span="12">
        <el-table v-loading="loading" :data="topList">
          <el-table-column label="排名" align="center" prop="no" />
          <el-table-column label="院系名称" align="center" prop="name" />
          <el-table-column label="横向" align="center" prop="heng" />
          <el-table-column label="纵向" align="center" prop="zong" />
          <el-table-column label="校内" align="center" prop="xiao" />
          <el-table-column label="总额" align="center" prop="total" />
        </el-table>
      </el-col>
      <el-col :span="12">
        <bar-chart :data="topList" />
      </el-col>
    </el-row>
    <h3>核心论文发表趋势</h3>
    <line-chart :chart-data="lineChart" />
  </div>
</template>
<script>
import BarChart from "./dashboard/index/Top5";
import LineChart from "./dashboard/LineChart";

export default {
  components: { BarChart, LineChart },
  name: "index",
  data() {
    return {
      // 版本号
      loading: false,
      version: "3.4.0",
      topList: [
        { no: 1, name: "法学院", heng: 100, zong: 100, xiao: 100, total: 300 },
        { no: 2, name: "材料学院", heng: 100, zong: 90, xiao: 80, total: 270 },
        {
          no: 3,
          name: "信息工程学院",
          heng: 90,
          zong: 70,
          xiao: 70,
          total: 230,
        },
        { no: 4, name: "外语学院", heng: 70, zong: 50, xiao: 50, total: 170 },
        {
          no: 5,
          name: "土木工程学院",
          heng: 80,
          zong: 20,
          xiao: 40,
          total: 140,
        },
      ],
      lineChart: {
        expectedData: [80, 100, 150, 120, 110, 160, 40],
        actualData: [20, 30, 40, 32, 50, 43, 10],
      },
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

