<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="选择月份" prop="month">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.month"
          type="month"
          value-format="yyyy-MM"
          :default-value="new Date()"
          placeholder="选择计划月份"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
        <el-button
          type="primary"
          plain
          size="mini"
          icon="el-icon-printer"
          @click="prints"
          >打印</el-button
        >
      </el-form-item>
    </el-form>

    <div ref="printMe">
      <h2 style="text-align: center">{{ this.deptName }}班级儿童考勤表</h2>
      <h4 style="text-align: left">
        考勤月份：{{ this.month }} ------ 班级总数：{{ this.classcount }} ------
        幼儿总数：{{ this.chilidcount }}
      </h4>
      <el-table
        v-loading="loading"
        style="width: 100%"
        border
        :data="tableData"
      >
        <template v-for="(item, index) in tableHead">
          <el-table-column
            :prop="
              item.column_name == ''
                ? 'day' + (item.sort + 1)
                : item.column_name
            "
            :fixed="item.column_name != ''"
            :label="item.column_name == '' ? item.sort + 1 + '' : item.sort"
            :key="index"
          ></el-table-column>
        </template>
      </el-table>
    </div>
    <!-- <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
    />-->
  </div>
</template>

<script>
import { listDatetime } from "@/api/benyi/checkindetail";
import { listClassCheck } from "@/api/system/class";
import { listChild } from "@/api/benyi/child";
import { getUserProfile } from "@/api/system/user";
export default {
  name: "checkinstatisticsschool",
  data() {
    return {
      //用户信息
      deptName: "",
      //幼儿总数
      chilidcount: "",
      //班级数量
      classcount: "",
      //月份
      month: "",
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      //javascript
      // 表头数据
      tableHead: [
        // {
        //   column_name: "column_name",
        //   column_comment: "姓名",
        // },
        // {
        //   column_name: "column_age",
        //   column_comment: "年龄",
        // },
        // {
        //   column_name: "column_sex",
        //   column_comment: "性别",
        // },
      ],
      // 表格数据
      tableData: [
        // {
        //   column_age: "3",
        //   column_name: "鞠婧祎",
        //   column_sex: "女",
        // },
        // {
        //   column_age: "25",
        //   column_name: "魏大勋",
        //   column_sex: "男",
        // },
        // {
        //   column_age: "18",
        //   column_name: "关晓彤",
        //   column_sex: "女",
        // },
      ],
      // 查询参数
      queryParams: {
        month: "",
      },
    };
  },
  created() {
    this.getNowTime(); //得到月份
    this.getHeadList();
    this.getUser();
  },
  methods: {
    getUser() {
      getUserProfile().then((response) => {
        // console.log(response.data);
        this.deptName = response.data.dept.deptName;
      });
    },
    getNowTime() {
      var now = new Date();
      var year = now.getFullYear(); // 得到年份
      var month = now.getMonth(); // 得到月份
      month = month + 1;
      month = month.toString().padStart(2, "0");
      this.month = `${year}-${month}`;
    },
    async getHeadList() {
      this.tableHead = [];
      if (this.queryParams.month == "") {
        this.queryParams.month = this.month;
      }
      await listDatetime(this.queryParams).then((response) => {
        console.log(response.rows);
        this.tableHead.push({
          column_name: "bjmc",
          sort: "班级",
        });
        this.tableHead.push({
          column_name: "zj",
          sort: "总计",
        });
        response.rows.forEach((res) => {
          this.tableHead.push({
            column_name: "",
            sort: res.sort,
          });
        });
      });

      this.getList();
      this.getChildCount();
    },
    getChildCount() {
      listChild(null).then((response) => {
        this.chilidcount = response.rows.length;
      });
    },
    getList() {
      this.loading = true;
      this.tableData = [];
      if (this.queryParams.month == "") {
        this.queryParams.month = this.month;
      }
      listClassCheck(this.queryParams).then((response) => {
        console.log(response.rows);
        this.classcount = response.rows.length - 1;
        // this.tableData = response.rows;
        response.rows.forEach((res) => {
          this.tableData.push({
            bjmc: res.bjmc,
            zj: this.avg([
              res.day1,
              res.day2,
              res.day3,
              res.day4,
              res.day5,
              res.day6,
              res.day7,
              res.day8,
              res.day9,
              res.day10,
              res.day11,
              res.day12,
              res.day13,
              res.day14,
              res.day15,
              res.day16,
              res.day17,
              res.day18,
              res.day19,
              res.day20,
              res.day21,
              res.day22,
              res.day23,
              res.day24,
              res.day25,
              res.day26,
              res.day27,
              res.day28,
              res.day29,
              res.day30,
              res.day31,
            ]),
            day1:
              res.day1 == "0"
                ? ""
                : parseFloat((res.day1 * 100).toFixed(2)) + "%",
            day2:
              res.day2 == "0"
                ? ""
                : parseFloat((res.day2 * 100).toFixed(2)) + "%",
            day3:
              res.day3 == "0"
                ? ""
                : parseFloat((res.day3 * 100).toFixed(2)) + "%",
            day4:
              res.day4 == "0"
                ? ""
                : parseFloat((res.day4 * 100).toFixed(2)) + "%",
            day5:
              res.day5 == "0"
                ? ""
                : parseFloat((res.day5 * 100).toFixed(2)) + "%",
            day6:
              res.day6 == "0"
                ? ""
                : parseFloat((res.day6 * 100).toFixed(2)) + "%",
            day7:
              res.day7 == "0"
                ? ""
                : parseFloat((res.day7 * 100).toFixed(2)) + "%",
            day8:
              res.day8 == "0"
                ? ""
                : parseFloat((res.day8 * 100).toFixed(2)) + "%",
            day9:
              res.day9 == "0"
                ? ""
                : parseFloat((res.day9 * 100).toFixed(2)) + "%",
            day10:
              res.day10 == "0"
                ? ""
                : parseFloat((res.day10 * 100).toFixed(2)) + "%",
            day11:
              res.day11 == "0"
                ? ""
                : parseFloat((res.day11 * 100).toFixed(2)) + "%",
            day12:
              res.day12 == "0"
                ? ""
                : parseFloat((res.day12 * 100).toFixed(2)) + "%",
            day13:
              res.day13 == "0"
                ? ""
                : parseFloat((res.day13 * 100).toFixed(2)) + "%",
            day14:
              res.day14 == "0"
                ? ""
                : parseFloat((res.day14 * 100).toFixed(2)) + "%",
            day15:
              res.day15 == "0"
                ? ""
                : parseFloat((res.day15 * 100).toFixed(2)) + "%",
            day16:
              res.day16 == "0"
                ? ""
                : parseFloat((res.day16 * 100).toFixed(2)) + "%",
            day17:
              res.day17 == "0"
                ? ""
                : parseFloat((res.day17 * 100).toFixed(2)) + "%",
            day18:
              res.day18 == "0"
                ? ""
                : parseFloat((res.day18 * 100).toFixed(2)) + "%",
            day19:
              res.day19 == "0"
                ? ""
                : parseFloat((res.day19 * 100).toFixed(2)) + "%",
            day20:
              res.day20 == "0"
                ? ""
                : parseFloat((res.day20 * 100).toFixed(2)) + "%",
            day21:
              res.day21 == "0"
                ? ""
                : parseFloat((res.day21 * 100).toFixed(2)) + "%",
            day22:
              res.day22 == "0"
                ? ""
                : parseFloat((res.day22 * 100).toFixed(2)) + "%",
            day23:
              res.day23 == "0"
                ? ""
                : parseFloat((res.day23 * 100).toFixed(2)) + "%",
            day24:
              res.day24 == "0"
                ? ""
                : parseFloat((res.day24 * 100).toFixed(2)) + "%",
            day25:
              res.day25 == "0"
                ? ""
                : parseFloat((res.day25 * 100).toFixed(2)) + "%",
            day26:
              res.day26 == "0"
                ? ""
                : parseFloat((res.day26 * 100).toFixed(2)) + "%",
            day27:
              res.day27 == "0"
                ? ""
                : parseFloat((res.day27 * 100).toFixed(2)) + "%",
            day28:
              res.day28 == "0"
                ? ""
                : parseFloat((res.day28 * 100).toFixed(2)) + "%",
            day29:
              res.day29 == "0"
                ? ""
                : parseFloat((res.day29 * 100).toFixed(2)) + "%",
            day30:
              res.day30 == "0"
                ? ""
                : parseFloat((res.day30 * 100).toFixed(2)) + "%",
            day31:
              res.day31 == "0"
                ? ""
                : parseFloat((res.day31 * 100).toFixed(2)) + "%",
          });
        });
        this.total = response.total;
        this.loading = false;
      });
    },
    avg(a1) {
      console.log("a" + a1.length);
      //bug 这个地方的分母a 应该取值所有班级的最大值
      var a = 0;
      var sum = 0;
      a1.forEach((res) => {
        if (res > 0) {
          a = a + 1;
          sum = sum + res;
        }
      });
      if (a == 0) {
        return "";
      }
      return parseFloat(((sum / a) * 100).toFixed(2)) + "%";
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.month = this.queryParams.month;
      this.getHeadList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //打印
    prints() {
      //console.log(this.$refs.printMe);
      this.$print(this.$refs.printMe);
    },
  },
};
</script>