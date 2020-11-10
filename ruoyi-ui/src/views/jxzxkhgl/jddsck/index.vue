<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="见习之星" prop="jxjsid">
        <el-select
          v-model="queryParams.jxjsid"
          placeholder="请选择见习之星"
          filterable
          size="small"
        >
          <el-option
            v-for="dict in jxzxOptions"
            :key="dict.jsid"
            :label="dict.tsbzJxjsjbxx.name"
            :value="dict.jsid"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="见习导师" prop="dsid">
        <el-select
          v-model="queryParams.dsid"
          placeholder="请选择见习导师"
          filterable
          size="small"
        >
          <el-option
            v-for="dict in dsOptions"
            :key="dict.id"
            :label="dict.jsxm"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-table
      v-loading="loading"
      :data="jxzxdsfpList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="基地校" align="center" prop="jdxmc" />
      <el-table-column
        label="见习之星"
        align="center"
        prop="jxjsid"
        :formatter="jxzxFormat"
      />
      <el-table-column
        label="见习导师"
        align="center"
        prop="dsid"
        :formatter="dsFormat"
      />
    </el-table>

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
import {
  listJxzxdsfp,
  getJxzxdsfp,
} from "@/api/jxzxkhgl/jxzxdsfp";
import { listDsjbxx } from "@/api/jxzxkhgl/dsjbxx";
import { listJxzxmd, getJxzxmd } from "@/api/jxjs/jxzxmd";

export default {
  name: "Jxzxdsfp",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 见习导师分配表格数据
      jxzxdsfpList: [],
      //导师
      dsOptions: [],
      //导师
      jxzxOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jxjsid: null,
        dsid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
    };
  },
  created() {
    this.getJxzxList();
    this.getDsList();
    this.getList();
  },
  methods: {
    // 见习教师字典翻译
    jxzxFormat(row, column) {
      var actions = [];
      var datas = this.jxzxOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].jsid == "" + row.jxjsid) {
          actions.push(datas[key].tsbzJxjsjbxx.name);
          return false;
        }
      });
      return actions.join("");
    },
    // 导师字典翻译
    dsFormat(row, column) {
      var actions = [];
      var datas = this.dsOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.dsid) {
          actions.push(datas[key].jsxm);
          return false;
        }
      });
      return actions.join("");
    },
    /** 查询导师基本信息列表 */
    getJxzxList() {
      listJxzxmd(null).then((response) => {
        // console.log(response.rows);
        this.jxzxOptions = response.rows;
      });
    },
    /** 查询导师基本信息列表 */
    getDsList() {
      listDsjbxx(null).then((response) => {
        this.dsOptions = response.rows;
      });
    },
    /** 查询见习导师分配列表 */
    getList() {
      this.loading = true;
      listJxzxdsfp(this.queryParams).then((response) => {
        this.jxzxdsfpList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
 
  },
};
</script>
