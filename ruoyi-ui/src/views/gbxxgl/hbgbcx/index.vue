<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <el-form-item label="教师姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入教师姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教师性别" prop="xb">
        <el-select
          v-model="queryParams.xb"
          placeholder="请选择教师性别"
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
      <el-form-item label="评选年度" prop="hbgbqdny">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.hbgbqdny"
          type="year"
          value-format="yyyy-MM-dd"
          placeholder="选择后备干部确定年月">
        </el-date-picker>
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

    <el-table
      v-loading="loading"
      :data="gbjbqkList"
      @selection-change="handleSelectionChange"
    >
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column
        label="单位名称"
        align="center"
        prop="deptId"
        :formatter="deptFormat"
      />
      <el-table-column label="教师姓名" align="center" prop="name" />
      <el-table-column
        label="性别"
        align="center"
        prop="xb"
        :formatter="xbFormat"
      />

      <el-table-column label="出生日期" align="center" prop="csrq">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.csrq, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="参加工作年月" align="center" prop="cjgzny">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.cjgzny, "{y}-{m}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="政治面貌"
        align="center"
        prop="zzmm"
        :formatter="zzmmFormat"
      />
      <el-table-column
        label="职称"
        align="center"
        prop="zc"
        :formatter="zcFormat"
      />
      <el-table-column
        label="现任行政职务"
        align="center"
        prop="xrxzzw"
        :formatter="xrxzzwFormat"
      />
       <el-table-column
        label="现任职务年月"
        align="center"
        prop="rxzwny"
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
            icon="el-icon-view"
            @click="handleCheck(scope.row)"
            v-hasPermi="['gbxxgl:gbjbqk:query']"
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
  listGbjbqk,
  getGbjbqk,
} from "@/api/gbxxgl/gbjbqk";
import { listDept, getDept } from "@/api/system/dept";

export default {
  name: "Gbjbqk",
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
      // 干部基本情况表格数据
      gbjbqkList: [],
      // 性别字典
      xbOptions: [],
      // 现任行政职务字典
      xrxzzwOptions: [],
      // 现任党内职务字典
      xrdnzwOptions: [],
      // 现任群团职务字典
      xrqtzwOptions: [],
      // 现任校长职级字典
      xrxzzjOptions: [],
      // 是否后备干部字典
      sfhbgbOptions: [],
      // 学历字典
      xlOptions: [],
      // 第一学历字典
      dyxlOptions: [],
      // 学位字典
      xwOptions: [],
      // 教育类型字典
      jylxOptions: [],
      // 职称字典
      zcOptions: [],
      // 驾照持有情况字典
      jzcyqkOptions: [],
      // 能否开车字典
      nfkcOptions: [],
      // 婚姻状况字典
      hyzkOptions: [],
      // 政治面貌字典
      zzmmOptions: [],
      // 任教学科字典
      rjxkOptions: [],
      // 健康状况字典
      jkzkOptions: [],
      // 部门选项
      deptOptions: [],
      // 详情页title
      title_look: "",
      // 详情页显示
      open_look: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        otherid: null,
        name: null,
        xb: null,
        deptId: null,
        dwmc: null,
        dwjc: null,
        xrxzzw: null,
        xrdnzw: null,
        xrqtzw: null,
        rxzwny: null,
        xrxzzj: null,
        xzzjqdny: null,
        sfhbgb: 'Y',
        hbgbqdny: null,
        sfzh: null,
        updatetime: null,
        mz: null,
        jg: null,
        jgProvince: null,
        jgCity: null,
        jgCityname: null,
        jgArea: null,
        jgAreaname: null,
        csd: null,
        csdProvince: null,
        csdCity: null,
        csdCityname: null,
        csdArea: null,
        csdAreaname: null,
        csrq: null,
        cjgzny: null,
        jtzz: null,
        dwdz: null,
        dyxl: null,
        xl: null,
        xw: null,
        byyx: null,
        jylx: null,
        zc: null,
        jzcyqk: null,
        nfkc: null,
        hyzk: null,
        zzmm: null,
        rdsj: null,
        bgsdh: null,
        phone: null,
        rjxk: null,
        jkzk: null,
        createUser: null,
        createTime: null,
        dqzt:'00',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
    };
  },
  created() {
    this.getList();
    this.getDeptList();
    this.getDicts("sys_user_sex").then((response) => {
      this.xbOptions = response.data;
    });
    this.getDicts("sys_dm_xrxzzw").then((response) => {
      this.xrxzzwOptions = response.data;
    });
    this.getDicts("sys_dm_xrdnzw").then((response) => {
      this.xrdnzwOptions = response.data;
    });
    this.getDicts("sys_dm_xrqtzw").then((response) => {
      this.xrqtzwOptions = response.data;
    });
    this.getDicts("sys_dm_xrxzzj").then((response) => {
      this.xrxzzjOptions = response.data;
    });
    this.getDicts("sys_yes_no").then((response) => {
      this.sfhbgbOptions = response.data;
    });
    this.getDicts("sys_dm_xl").then((response) => {
      this.xlOptions = response.data;
    });
    this.getDicts("sys_dm_xw").then((response) => {
      this.xwOptions = response.data;
    });
    this.getDicts("sys_dm_jylx").then((response) => {
      this.jylxOptions = response.data;
    });
    this.getDicts("sys_dm_gbzc").then((response) => {
      this.zcOptions = response.data;
    });
    this.getDicts("sys_dm_jzcyqk").then((response) => {
      this.jzcyqkOptions = response.data;
    });
    this.getDicts("sys_yes_no").then((response) => {
      this.nfkcOptions = response.data;
    });
    this.getDicts("sys_dm_hyzk").then((response) => {
      this.hyzkOptions = response.data;
    });
    this.getDicts("sys_dm_zzmm").then((response) => {
      this.zzmmOptions = response.data;
    });
    this.getDicts("sys_dm_rjxk").then((response) => {
      this.rjxkOptions = response.data;
    });
    this.getDicts("sys_dm_jkzk").then((response) => {
      this.jkzkOptions = response.data;
    });
    this.getDicts("sys_dm_dyxl").then(response => {
      this.dyxlOptions = response.data;
    });
  },
  methods: {
    /** 查询干部基本情况列表 */
    getList() {
      this.loading = true;
      listGbjbqk(this.queryParams).then((response) => {
        this.gbjbqkList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 查询部门
    getDeptList() {
      listDept(null).then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 部门字典翻译
    deptFormat(row, column) {
      var actions = [];
      var datas = this.deptOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].deptId == "" + row.deptId) {
          actions.push(datas[key].deptName);
          return false;
        }
      });
      return actions.join("");
    },
    // 性别字典翻译
    xbFormat(row, column) {
      return this.selectDictLabel(this.xbOptions, row.xb);
    },
    // 现任行政职务字典翻译
    xrxzzwFormat(row, column) {
      return this.selectDictLabel(this.xrxzzwOptions, row.xrxzzw);
    },
    // 现任党内职务字典翻译
    xrdnzwFormat(row, column) {
      return this.selectDictLabel(this.xrdnzwOptions, row.xrdnzw);
    },
    // 现任群团职务字典翻译
    xrqtzwFormat(row, column) {
      return this.selectDictLabel(this.xrqtzwOptions, row.xrqtzw);
    },
    // 现任校长职级字典翻译
    xrxzzjFormat(row, column) {
      return this.selectDictLabel(this.xrxzzjOptions, row.xrxzzj);
    },
    // 是否后备干部字典翻译
    sfhbgbFormat(row, column) {
      return this.selectDictLabel(this.sfhbgbOptions, row.sfhbgb);
    },
    // 学历字典翻译
    xlFormat(row, column) {
      return this.selectDictLabel(this.xlOptions, row.xl);
    },
    // 学位字典翻译
    xwFormat(row, column) {
      return this.selectDictLabel(this.xwOptions, row.xw);
    },
    // 教育类型字典翻译
    jylxFormat(row, column) {
      return this.selectDictLabel(this.jylxOptions, row.jylx);
    },
    // 职称字典翻译
    zcFormat(row, column) {
      return this.selectDictLabel(this.zcOptions, row.zc);
    },
    // 驾照持有情况字典翻译
    jzcyqkFormat(row, column) {
      return this.selectDictLabel(this.jzcyqkOptions, row.jzcyqk);
    },
    // 能否开车字典翻译
    nfkcFormat(row, column) {
      return this.selectDictLabel(this.nfkcOptions, row.nfkc);
    },
    // 婚姻状况字典翻译
    hyzkFormat(row, column) {
      return this.selectDictLabel(this.hyzkOptions, row.hyzk);
    },
    // 政治面貌字典翻译
    zzmmFormat(row, column) {
      return this.selectDictLabel(this.zzmmOptions, row.zzmm);
    },
    // 任教学科字典翻译
    rjxkFormat(row, column) {
      return this.selectDictLabel(this.rjxkOptions, row.rjxk);
    },
    // 健康状况字典翻译
    jkzkFormat(row, column) {
      return this.selectDictLabel(this.jkzkOptions, row.jkzk);
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
    /** 查看按钮操作 */
    handleCheck(row) {
      const id = row.id ;
      this.$router.push({
        path: "/gbxxgl/xq/data/" + id,
      });
    },
  },
};
</script>
