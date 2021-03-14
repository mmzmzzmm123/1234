<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="xb">
        <el-select
          v-model="queryParams.xb"
          placeholder="请选择性别"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in xbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="政治面貌" prop="zzmm">
        <el-select
          v-model="queryParams.zzmm"
          placeholder="请选择政治面貌"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in zzmmOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="民族" prop="mz">
        <el-select
          v-model="queryParams.mz"
          placeholder="请选择民族"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in mzOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任教学段" prop="rjxd">
        <el-select
          v-model="queryParams.rjxd"
          placeholder="请选择任教学段"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in rjxdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任教学科" prop="rjxk">
        <el-select
          v-model="queryParams.rjxk"
          placeholder="请选择任教学科"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in rjxkOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任教年级" prop="rjnj">
        <el-select
          v-model="queryParams.rjnj"
          placeholder="请选择任教年级"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in rjnjOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="学历" prop="xl">
        <el-select
          v-model="queryParams.xl"
          placeholder="请选择学历"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in xlOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="学位" prop="xw">
        <el-select
          v-model="queryParams.xw"
          placeholder="请选择学位"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in xwOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="师范生" prop="sfsfs">
        <el-select
          v-model="queryParams.sfsfs"
          placeholder="请选择是否师范生"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in sfsfsOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="录取年份" prop="lqnf">
        <el-select
          v-model="queryParams.lqnf"
          placeholder="请选择录取年份"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in lqnfOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          >导出
        </el-button>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="jxjsjbxxList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="进修编号" align="center" prop="jxbh" />
      <el-table-column
        label="性别"
        align="center"
        prop="xb"
        :formatter="xbFormat"
      />
      <el-table-column
        label="政治面貌"
        align="center"
        prop="zzmm"
        :formatter="zzmmFormat"
      />
      <el-table-column
        label="民族"
        align="center"
        prop="mz"
        :formatter="mzFormat"
      />
      <el-table-column
        label="学历"
        align="center"
        prop="xl"
        :formatter="xlFormat"
      />
      <el-table-column
        label="学位"
        align="center"
        prop="xw"
        :formatter="xwFormat"
      />
      <el-table-column
        label="是否师范生"
        align="center"
        prop="sfsfs"
        :formatter="sfsfsFormat"
      />
      <el-table-column
        label="录取年份"
        align="center"
        prop="lqnf"
        :formatter="lqnfFormat"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxjs:jxjsjbxx:remove']"
            >移除</el-button
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
  listJxjsjbxx,
  getJxjsjbxx,
  clearJxjsjdx,
  exportJxjsjbxx,
} from "@/api/jxjs/jxjsjbxx";

export default {
  name: "Jxjsjbxx",
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
      // 见习教师基本信息表格数据
      jxjsjbxxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 性别字典
      xbOptions: [],
      // 政治面貌字典
      zzmmOptions: [],
      // 民族字典
      mzOptions: [],
      // 任教学段字典
      rjxdOptions: [],
      // 任教学科字典
      rjxkOptions: [],
      // 任教年级字典
      rjnjOptions: [],
      // 学历字典
      xlOptions: [],
      // 学位字典
      xwOptions: [],
      // 是否师范生字典
      sfsfsOptions: [],
      // 录取年份字典
      lqnfOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        otherid: null,
        name: null,
        jxbh: null,
        xb: null,
        csrq: null,
        email: null,
        phone: null,
        yzbm: null,
        zzmm: null,
        mz: null,
        prdwid: null,
        prdwmc: null,
        jdxid: null,
        rjxd: null,
        rjxk: null,
        rjnj: null,
        byyx: null,
        xl: null,
        xw: null,
        sfsfs: null,
        lqnf: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "教师名称不能为空", trigger: "blur" },
        ],
        jxbh: [
          { required: true, message: "进修编号不能为空", trigger: "blur" },
        ],
        xb: [{ required: true, message: "教师性别不能为空", trigger: "blur" }],
        csrq: [
          { required: true, message: "出生日期不能为空", trigger: "blur" },
        ],
        phone: [{ required: true, message: "电话不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    const jdxId = this.$route.params && this.$route.params.id;
    //console.log(jdxId);
    this.queryParams.jdxid = jdxId;
    this.getList();
    this.getDicts("sys_user_sex").then((response) => {
      this.xbOptions = response.data;
    });
    this.getDicts("sys_dm_zzmm").then((response) => {
      this.zzmmOptions = response.data;
    });
    this.getDicts("sys_dm_mz").then((response) => {
      this.mzOptions = response.data;
    });
    this.getDicts("sys_dm_rjxd").then((response) => {
      this.rjxdOptions = response.data;
    });
    this.getDicts("sys_dm_rjxk").then((response) => {
      this.rjxkOptions = response.data;
    });
    this.getDicts("sys_dm_rjnj").then((response) => {
      this.rjnjOptions = response.data;
    });
    this.getDicts("sys_dm_xl").then((response) => {
      this.xlOptions = response.data;
    });
    this.getDicts("sys_dm_xw").then((response) => {
      this.xwOptions = response.data;
    });
    this.getDicts("sys_dm_sfsfs").then((response) => {
      this.sfsfsOptions = response.data;
    });
    this.getDicts("sys_dm_rxnf").then((response) => {
      this.lqnfOptions = response.data;
    });
  },
  methods: {
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所选用户数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportJxjsjbxx(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** 查询见习教师基本信息列表 */
    getList() {
      this.loading = true;
      listJxjsjbxx(this.queryParams).then((response) => {
        this.jxjsjbxxList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 性别字典翻译
    xbFormat(row, column) {
      return this.selectDictLabel(this.xbOptions, row.xb);
    },
    // 政治面貌字典翻译
    zzmmFormat(row, column) {
      return this.selectDictLabel(this.zzmmOptions, row.zzmm);
    },
    // 民族字典翻译
    mzFormat(row, column) {
      return this.selectDictLabel(this.mzOptions, row.mz);
    },
    // 任教学段字典翻译
    rjxdFormat(row, column) {
      return this.selectDictLabel(this.rjxdOptions, row.rjxd);
    },
    // 任教学科字典翻译
    rjxkFormat(row, column) {
      return this.selectDictLabel(this.rjxkOptions, row.rjxk);
    },
    // 任教年级字典翻译
    rjnjFormat(row, column) {
      return this.selectDictLabel(this.rjnjOptions, row.rjnj);
    },
    // 学历字典翻译
    xlFormat(row, column) {
      return this.selectDictLabel(this.xlOptions, row.xl);
    },
    // 学位字典翻译
    xwFormat(row, column) {
      return this.selectDictLabel(this.xwOptions, row.xw);
    },
    // 是否师范生字典翻译
    sfsfsFormat(row, column) {
      return this.selectDictLabel(this.sfsfsOptions, row.sfsfs);
    },
    // 录取年份字典翻译
    lqnfFormat(row, column) {
      return this.selectDictLabel(this.lqnfOptions, row.lqnf);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id;
      this.$confirm("是否确认移除分配的见习教师信息?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return clearJxjsjdx(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("移除成功");
        })
        .catch(function () {});
    },
  },
};
</script>
