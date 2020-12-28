<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="选择班级" prop="classid">
            <el-select v-model="queryParams.classid" placeholder="请选择班级">
              <el-option
                v-for="dict in classOptions"
                :key="dict.bjbh"
                :label="dict.bjmc"
                :value="dict.bjbh"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="选择月份" prop="month">
            <el-date-picker
              clearable
              size="small"
              v-model="queryParams.month"
              type="month"
              value-format="yyyy-MM"
              :default-value="new Date()"
              placeholder="选择计划月份"
              class="my-date-picker"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item class="no-margin">
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索</el-button
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
        </el-col>
      </el-row>
    </el-form>
    <div ref="printMe" class="print-me">
      <h2 style="text-align: center">
        {{ this.className }}收费明细({{ this.queryParams.month }})
      </h2>
      <el-table v-loading="loading" border :data="childchargeList">
        <!-- <el-table-column type="selection" width="55" align="center" /> -->
        <el-table-column
          label="班级类型"
          fixed=""
          align="center"
          prop="bjtype"
          :formatter="bjtypeFormat"
        />
        <el-table-column
          label="班级名称"
          fixed
          align="center"
          prop="classid"
          :formatter="classFormat"
        />
        <el-table-column label="幼儿名称" align="center" prop="name" />
        <el-table-column label="出勤天数" align="center" prop="days" />
        <el-table-column
          label="明细"
          align="center"
          :formatter="mingxiFormat"
        />
        <el-table-column label="总计" align="center" prop="zj" />
        <!-- <el-table-column label="保育费（大班）/月" align="center" prop="byf" />
      <el-table-column label="伙食费（大班）/天" align="center" prop="hsf" />
      <el-table-column label="保育费（中班）/月" align="center" prop="byfZ" />
      <el-table-column label="伙食费（中班）/天" align="center" prop="hsfZ" />
      <el-table-column label="保育费（小班）/月" align="center" prop="byfX" />
      <el-table-column label="伙食费（小班）/天" align="center" prop="hsfX" />
      <el-table-column label="保育费（拖班）/月" align="center" prop="byfT" />
      <el-table-column label="伙食费（拖班）/天" align="center" prop="hsfT" /> -->
      </el-table>
    </div>
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listChildcharge } from "@/api/benyi/schoolcharge";

import { listClass } from "@/api/system/class";

export default {
  name: "Schoolcharge",
  data() {
    return {
      className: "",
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
      // 幼儿收费标准表格数据
      childchargeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //班级
      classOptions: [],
      //班级类型
      bjtypeOptions: [],
      //月份
      month: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 50,
        month: "",
        classid: "",
      },
    };
  },
  created() {
    this.getClassList();
    this.getNowTime();
    this.getDicts("sys_yebjlx").then((response) => {
      this.bjtypeOptions = response.data;
    });
  },
  methods: {
    getNowTime() {
      var now = new Date();
      var year = now.getFullYear(); // 得到年份
      var month = now.getMonth(); // 得到月份
      month = month + 1;
      month = month.toString().padStart(2, "0");
      this.queryParams.month = `${year}-${month}`;
    },
    //翻译明细
    mingxiFormat(row, column) {
      if (row.bjtype == "1") {
        //托班
        return (
          "保育费(月/元):" +
          row.byfT +
          ";伙食费(天/元):" +
          row.days +
          "*" +
          row.hsfT
        );
      } else if (row.bjtype == "2") {
        //小班
        return (
          "保育费(月/元):" +
          row.byfX +
          ";伙食费(天/元):" +
          row.days +
          "*" +
          row.hsfX
        );
      } else if (row.bjtype == "3") {
        //中班
        return (
          "保育费(月/元):" +
          row.byfZ +
          ";伙食费(天/元):" +
          row.days +
          "*" +
          row.hsfZ
        );
      } else if (row.bjtype == "4") {
        //大班
        return (
          "保育费(月/元):" +
          row.byf +
          ";伙食费(天/元):" +
          row.days +
          "*" +
          row.hsf
        );
      } else {
        return "";
      }
    },
    // 班级类型--字典状态字典翻译
    bjtypeFormat(row, column) {
      return this.selectDictLabel(this.bjtypeOptions, row.bjtype);
    },
    // 字典翻译
    classFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.classOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].bjbh == "" + row.classid) {
          actions.push(datas[key].bjmc);
          return false;
        }
      });
      return actions.join("");
    },
    classTitle(classid) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.classOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].bjbh == "" + classid) {
          actions.push(datas[key].bjmc);
          return false;
        }
      });
      return actions.join("");
    },
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
        this.queryParams.classid = response.rows[0].bjbh;
        this.className = response.rows[0].bjmc;
      });

      this.getList();
    },
    /** 查询幼儿收费列表 */
    getList() {
      this.loading = true;
      if (this.queryParams.month == "") {
        this.queryParams.month = this.month;
      }
      listChildcharge(this.queryParams).then((response) => {
        if (response.rows.length == 0) {
          this.msgError("请检查当前班级是否存在幼儿或是否已经设置托费");
        }
        this.childchargeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.className = this.classTitle(this.queryParams.classid);
      this.getList();
    },
    //打印
    prints() {
      //console.log(this.$refs.printMe);
      this.$print(this.$refs.printMe);
    },
  },
};
</script>
<style lang="scss" scoped>
.el-select {
  width: 100%;
}
.my-date-picker {
  width: 100%;
}
.no-margin ::v-deep.el-form-item__content {
  margin: 0 !important;
}
</style>
