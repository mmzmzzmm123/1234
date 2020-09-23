<template>
  <div class="app-container">
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
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" style="width: 100%" border :data="tableData">
      <template v-for="(item,index) in tableHead">
        <el-table-column
          :prop=" item.column_name==''?'day'+(item.sort+1) : item.column_name"
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
      if (this.queryParams.month == "") {
        this.queryParams.month = this.month;
      }
      listClassCheck(this.queryParams).then((response) => {
        console.log(response.rows);
        this.tableData = response.rows;
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
  },
};
</script>