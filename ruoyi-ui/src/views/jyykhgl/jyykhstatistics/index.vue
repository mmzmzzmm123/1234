<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <!-- <el-form-item label="名称" prop="rwmc">
        <el-input
          v-model="queryParams.rwmc"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="任务名称" prop="rwid">
        <el-select
          v-model="queryParams.rwid"
          size="small"
          clearable
          placeholder="请选择任务名称"
        >
          <el-option
            v-for="dict in jyykhrwOption"
            :key="dict.id"
            :label="dict.rwmc"
            :value="dict.id"
          ></el-option>
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

    <el-row :gutter="10" class="mb8">
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jyykhrwList">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <!-- <el-table-column label="任务名称" align="center" prop="rwmc" /> -->
      <el-table-column
        label="任务类型"
        align="center"
        prop="rwlx"
        :formatter="xrlxFormat"
      />
      <el-table-column
        label="任务内容"
        align="center"
        prop="rwnr"
        :formatter="rwnrFormat"
      />
      <el-table-column
        label="考核部门"
        align="center"
        prop="khbm"
        :formatter="khbmFormat"
      />
      <el-table-column label="考核人员" align="center" prop="nickName" />
      <!-- <el-table-column label="考核周期" align="center" prop="khzq" /> -->
      <!-- <el-table-column label="数量要求" align="center" prop="slyq" /> -->
      <!-- <el-table-column
        label="是否必选"
        align="center"
        prop="fsbx"
        :formatter="typeFormat"
      /> -->
      <el-table-column label="总体完成情况" align="center" prop="wcsl" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['jyykhgl:jyykhrw:edit']"
            >查看</el-button
          >
        </template>
      </el-table-column>
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
  listJyykhrw,
  listJyykhrwstatistics,
  getJyykhrw,
  exportJyykhrw,
} from "@/api/jyykhgl/jyykhrw";

export default {
  name: "Jyykhrw",
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
      //完成情况
      wcqltotal: 0,
      // 教研员考核任务表格数据
      jyykhrwList: [],
      // 教研员考核任务表格数据
      jyykhrwOption: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //任务类型
      rwlxOptions: [],
      //任务内容
      rwnrOptions: [],
      //考核部门
      khbmOptions: [],
      //是否必选
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rwmc: null,
        rwlx: null,
        rwnr: null,
        khbm: null,
        khzq: null,
        slyq: null,
        fsbx: null,
        isdel: null,
        rwsm: null,
        khzqkssj: null,
        khzqjssj: null,
        parentId: null,
        xn: null,
        xq: null,
      },
      queryParams_fid: {
        rwid: null,
        createUserid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_jyykhrwlx").then((response) => {
      this.rwlxOptions = response.data;
    });
    this.getDicts("sys_dm_jyykhrwnr").then((response) => {
      this.rwnrOptions = response.data;
    });
    this.getDicts("sys_dm_jyykhbm").then((response) => {
      this.khbmOptions = response.data;
    });
    this.getDicts("sys_yes_no").then((response) => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    // 任务类型字典翻译
    xrlxFormat(row, column) {
      return this.selectDictLabel(this.rwlxOptions, row.rwlx);
    },
    // 任务内容字典翻译
    rwnrFormat(row, column) {
      return this.selectDictLabel(this.rwnrOptions, row.rwnr);
    },
    // 考核部门字典翻译
    khbmFormat(row, column) {
      return this.selectDictLabel(this.khbmOptions, row.khbm);
    },
    // 是否字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.fsbx);
    },
    /** 查询教研员考核任务列表 */
    getList() {
      listJyykhrw(null).then((response) => {
        this.jyykhrwOption = response.rows;
      });
      this.getTableList();
    },
    getTableList() {
      this.loading = true;
      listJyykhrwstatistics(this.queryParams).then((response) => {
        this.jyykhrwList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        rwmc: null,
        rwlx: null,
        rwnr: null,
        khbm: null,
        khzq: null,
        slyq: null,
        fsbx: null,
        isdel: null,
        createTime: null,
        rwsm: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.parentId = this.queryParams.rwid;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset();
      const id = row.id;
      getJyykhrw(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "查看教研员考核任务";
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.el-select {
  width: 100%;
}
</style>
