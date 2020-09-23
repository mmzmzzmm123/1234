<template>
  <div class="app-container" ref="printMe">
    <div class="print no-print">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
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
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          <el-button type="primary" plain size="mini" icon="el-icon-printer" @click="prints">打印</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table v-loading="loading" style="width: 100%" border :data="tableData">
      <template v-for="(item,index) in tableHead">
        <el-table-column
          :prop=" item.column_name==''?'day'+(item.sort+1) : item.column_name"
          :fixed="item.column_name!=''"
          :label=" item.column_name==''?(item.sort+1)+'' : item.sort"
          :key="index"
        ></el-table-column>
      </template>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listDatetime } from "@/api/benyi/checkindetail";
import { listClassCheck } from "@/api/system/class";
export default {
  name: "checkinstatisticsschool",
  data() {
    return {
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
  },
  methods: {
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
          sort: "班级名称",
        });
        response.rows.forEach((res) => {
          this.tableHead.push({
            column_name: "",
            sort: res.sort,
          });
        });
      });

      this.getList();
    },
    getList() {
      this.loading = true;
      this.tableData = [];
      if (this.queryParams.month == "") {
        this.queryParams.month = this.month;
      }
      listClassCheck(this.queryParams).then((response) => {
        console.log(response.rows);
        // this.tableData = response.rows;
        response.rows.forEach((res) => {
          this.tableData.push({
            bjmc: res.bjmc,
            day1: res.day1 == "0" ? "" : res.day1 * 100 + "%",
            day2: res.day2 == "0" ? "" : res.day2 * 100 + "%",
            day3: res.day3 == "0" ? "" : res.day3 * 100 + "%",
            day4: res.day4 == "0" ? "" : res.day4 * 100 + "%",
            day5: res.day5 == "0" ? "" : res.day5 * 100 + "%",
            day6: res.day6 == "0" ? "" : res.day6 * 100 + "%",
            day7: res.day7 == "0" ? "" : res.day7 * 100 + "%",
            day8: res.day8 == "0" ? "" : res.day8 * 100 + "%",
            day9: res.day9 == "0" ? "" : res.day9 * 100 + "%",
            day10: res.day10 == "0" ? "" : res.day10 * 100 + "%",
            day11: res.day11 == "0" ? "" : res.day11 * 100 + "%",
            day12: res.day12 == "0" ? "" : res.day12 * 100 + "%",
            day13: res.day13 == "0" ? "" : res.day13 * 100 + "%",
            day14: res.day14 == "0" ? "" : res.day14 * 100 + "%",
            day15: res.day15 == "0" ? "" : res.day15 * 100 + "%",
            day16: res.day16 == "0" ? "" : res.day16 * 100 + "%",
            day17: res.day17 == "0" ? "" : res.day17 * 100 + "%",
            day18: res.day18 == "0" ? "" : res.day18 * 100 + "%",
            day19: res.day19 == "0" ? "" : res.day19 * 100 + "%",
            day20: res.day20 == "0" ? "" : res.day20 * 100 + "%",
            day21: res.day21 == "0" ? "" : res.day21 * 100 + "%",
            day22: res.day22 == "0" ? "" : res.day22 * 100 + "%",
            day23: res.day23 == "0" ? "" : res.day23 * 100 + "%",
            day24: res.day24 == "0" ? "" : res.day24 * 100 + "%",
            day25: res.day25 == "0" ? "" : res.day25 * 100 + "%",
            day26: res.day26 == "0" ? "" : res.day26 * 100 + "%",
            day27: res.day27 == "0" ? "" : res.day27 * 100 + "%",
            day28: res.day28 == "0" ? "" : res.day28 * 100 + "%",
            day29: res.day29 == "0" ? "" : res.day29 * 100 + "%",
            day30: res.day30 == "0" ? "" : res.day30 * 100 + "%",
            day31: res.day31 == "0" ? "" : res.day31 * 100 + "%",
          });
        });
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
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