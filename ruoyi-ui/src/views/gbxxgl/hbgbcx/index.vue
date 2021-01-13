<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <!-- <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptTreeOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col> -->
      <!--教师数据-->
      <el-col :span="24" :xs="24">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          v-show="showSearch"
          label-width="70px"
        >
          <el-form-item label="学校类别" prop="xxlb">
            <el-select
              v-model="queryParams.xxlb"
              placeholder="请选择学校类别"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in xxlbmOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="学校办别" prop="xxbb">
            <el-select
              v-model="queryParams.xxbb"
              clearable
              placeholder="请选择学校办别"
              size="small"
            >
              <el-option
                v-for="dict in xxbbOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="学校名称" prop="deptId">
            <el-select
              v-model="queryParams.deptId"
              size="small"
              clearable
              filterable
              placeholder="请选择学校"
            >
              <el-option
                v-for="dict in deptOptions"
                :key="dict.deptId"
                :label="dict.deptName"
                :value="dict.deptId"
              ></el-option>
            </el-select>
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
            <el-date-picker
              clearable
              size="small"
              style="width: 200px"
              v-model="queryParams.hbgbqdny"
              type="year"
              value-format="yyyy-MM-dd"
              placeholder="选择后备干部确定年月"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="教师姓名" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入教师姓名"
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
          <right-toolbar
            :showSearch.sync="showSearch"
            @queryTable="getList"
          ></right-toolbar>
        </el-row>

        <el-table v-loading="loading" border :data="gbjbqkList">
          <!-- <el-table-column type="selection" width="55" align="center" /> -->
          <el-table-column
            fixed
            label="单位名称"
            align="center"
            prop="dept.deptName"
            width="120"
          />
          <el-table-column
            fixed
            label="教师姓名"
            align="center"
            width="120"
            prop="name"
          />
          <el-table-column
            label="性别"
            align="center"
            prop="xb"
            :formatter="xbFormat"
          />

          <el-table-column
            label="出生日期"
            width="140"
            align="center"
            prop="csrq"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.csrq, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="参加工作年月"
            width="140"
            align="center"
            prop="cjgzny"
          >
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
            width="140"
            :formatter="xrxzzwFormat"
          />
          <el-table-column
            label="现任职务年月"
            width="140"
            align="center"
            prop="rxzwny"
          />
          <el-table-column
            fixed="right"
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
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listGbjbqk, getGbjbqk } from "@/api/gbxxgl/gbjbqk";
import { listDept, getDept, treeselect } from "@/api/system/dept";

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
      //学校类别
      xxlbmOptions: [],
      // 学校办别选项
      xxbbOptions: [],
      // 部门选项
      deptOptions: [],
      // deptTreeOptions: [],
      // deptName: null,
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
        sfhbgb: "Y",
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
        dqzt: "00",
        xxlb: null,
        xxbb: null,
      },
      queryParams_dept: {
        dwlb: null,
        dwbb: null,
      },
      // 表单参数
      form: {},
      // defaultProps: {
      //   children: "children",
      //   label: "label",
      // },
      // 表单校验
      rules: {},
    };
  },
  watch: {
    // 根据名称筛选部门树
    // deptName(val) {
    //   this.$refs.tree.filter(val);
    // },
    // 监听xxlb
    "queryParams.xxlb": "handleDeptLbClick",
    "queryParams.xxbb": "handleDeptbbClick",
  },
  created() {
    this.getList();
    // this.getTreeselect();
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
    this.getDicts("sys_dm_dyxl").then((response) => {
      this.dyxlOptions = response.data;
    });
    this.getDicts("sys_dm_bxlx").then((response) => {
      this.xxlbmOptions = response.data;
    });
    this.getDicts("sys_dm_xxbb").then((response) => {
      this.xxbbOptions = response.data;
    });
  },
  methods: {
    handleDeptLbClick(value) {
      this.queryParams_dept.dwlb = value;
      // console.log(this.queryParams_dept.dwlb);
      // console.log(this.queryParams_dept.dwbb);
      listDept(this.queryParams_dept).then((response) => {
        // console.log(response.data);
        this.deptOptions = response.data;
      });
    },
    handleDeptbbClick(value) {
      this.queryParams_dept.dwbb = value;
      // console.log(this.queryParams_dept.dwlb);
      // console.log(this.queryParams_dept.dwbb);
      listDept(this.queryParams_dept).then((response) => {
        // console.log(response.data);
        this.deptOptions = response.data;
      });
    },
    /** 查询干部基本情况列表 */
    getList() {
      this.loading = true;
      listGbjbqk(this.queryParams).then((response) => {
        this.gbjbqkList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // /** 查询部门下拉树结构 */
    // getTreeselect() {
    //   treeselect().then((response) => {
    //     this.deptTreeOptions = response.data;
    //   });
    // },
    // // 筛选节点
    // filterNode(value, data) {
    //   if (!value) return true;
    //   return data.label.indexOf(value) !== -1;
    // },
    // // 节点单击事件
    // handleNodeClick(data) {
    //   this.queryParams.deptId = data.id;
    //   this.getList();
    // },
    // 查询部门
    getDeptList() {
      listDept(null).then((response) => {
        this.deptOptions = response.data;
      });
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
    /** 查看按钮操作 */
    handleCheck(row) {
      const id = row.id;
      this.$router.push({
        path: "/gbxxgl/xq/data/" + id,
      });
    },
  },
};
</script>
