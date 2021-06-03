<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <el-form-item label="考核年份" prop="nf" size="small">
        <el-date-picker
          v-model="queryParams.nf"
          type="year"
          value-format="yyyy-MM-dd"
          placeholder="请选择考核年份"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="考核方案" prop="faid">
        <el-select v-model="queryParams.faid" size="small">
          <el-option
            v-for="item in qtjskhfaOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="考核类型" prop="khlx" size="small">
        <el-select v-model="queryParams.khlx" placeholder="请选择考核类型">
          <el-option
            v-for="dict in khlxOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="附件类型" prop="fjlx" size="small">
        <el-select v-model="queryParams.fjlx" placeholder="请选择考核类型">
          <el-option
            v-for="dict in khfjlxOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="文件名称" prop="filename" size="small">
        <el-input
          v-model="queryParams.filename"
          placeholder="请输入文件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-download"
          size="mini"
          @click="handleDownloads"
          v-hasPermi="['qtjskhgl:qtjskhwj:list']"
          >下载</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="qtjskhwjList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="标识" align="center" prop="id" /> -->
      <el-table-column
        label="所属方案"
        align="center"
        prop="faid"
        :formatter="faFormat"
      />
      <el-table-column label="学校名称" align="center" prop="dept.deptName" />
      <el-table-column label="考核年份" align="center" prop="nf" />
      <el-table-column
        label="考核类型"
        align="center"
        prop="khlx"
        :formatter="khlxFormat"
      />
      <el-table-column
        label="附件类型"
        align="center"
        prop="fjlx"
        :formatter="khfjlxFormat"
      />
      <el-table-column label="文件名称" align="center" prop="filename" />
      <!-- <el-table-column label="文件路径" align="center" prop="filepath" /> -->
      <!-- <el-table-column label="创建人" align="center" prop="createUserid" /> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleDownload(scope.row)"
            v-hasPermi="['qtjskhgl:qtjskhwj:query']"
            >下载</el-button
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
import { listQtjskhwj, getQtjskhwj } from "@/api/qtjskhgl/qtjskhwj";
import { listQtjskhfa } from "@/api/qtjskhgl/qtjskhfa";

export default {
  name: "Qtjskhwjzh",
  data() {
    return {
      // 遮罩层
      loading: true,
      //默认方案id
      defaultFaid: "",
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
      // 群体教师考核文件管理表格数据
      qtjskhwjList: [],
      // 群体教师考核过程数据表格数据
      qtjskhfaOptions: [],
      //考核类型
      khlxOptions: [],
      //考核附件类型
      khfjlxOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        nf: null,
        khlx: null,
        fjlx: null,
        filename: null,
        filepath: null,
        deptId: null,
        createUserid: null,
      },
      // 查询参数
      queryParams_fa: {
        status: "1",
      },
    };
  },
  created() {
    this.getKhfa();
    this.getDicts("sys_dm_qtjskhlx").then((response) => {
      this.khlxOptions = response.data;
    });
    this.getDicts("sys_dm_qtjskhwjlx").then((response) => {
      this.khfjlxOptions = response.data;
    });
  },
  methods: {
    //考核方案
    async getKhfa() {
      await listQtjskhfa(this.queryParams_fa).then((response) => {
        this.qtjskhfaOptions = response.rows;
        this.getList();
      });
    },
    /** 查询群体教师考核文件管理列表 */
    getList() {
      this.loading = true;
      listQtjskhwj(this.queryParams).then((response) => {
        this.qtjskhwjList = response.rows;
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
    // 字典翻译
    khlxFormat(row, column) {
      return this.selectDictLabel(this.khlxOptions, row.khlx);
    },
    // 字典翻译
    khfjlxFormat(row, column) {
      return this.selectDictLabel(this.khfjlxOptions, row.fjlx);
    },
    // 字典翻译
    faFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.qtjskhfaOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.faid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    handleDownload(row) {
      window.open(process.env.VUE_APP_BASE_API + row.filepath);
      //console.log(row.filepath);
    },
  },
};
</script>
