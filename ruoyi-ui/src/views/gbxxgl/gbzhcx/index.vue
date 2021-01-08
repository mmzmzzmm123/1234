<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
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
      </el-col>
      <!--教师数据-->
      <el-col :span="20" :xs="24">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          v-show="showSearch"
          label-width="70px"
        >
          <el-form-item label="干部姓名" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入干部姓名"
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
          <el-form-item label="第一学历" prop="dyxl">
            <el-select
              v-model="queryParams.dyxl"
              placeholder="请选择第一学历"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in dyxlOptions"
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
          <el-form-item label="职称" prop="zc">
            <el-select
              v-model="queryParams.zc"
              placeholder="请选择职称"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in zcOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="单位名称" prop="deptId">
        <el-select v-model="form.deptId" placeholder="请选择单位名称">
                <el-option
                  v-for="dict in deptOptions"
                  :key="dict.deptId"
                  :label="dict.deptName"
                  :value="dict.deptId"
                ></el-option>
              </el-select>
      </el-form-item> -->
          <el-form-item label="行政职务" prop="xrxzzw">
            <el-select
              v-model="queryParams.xrxzzw"
              placeholder="请选择现任行政职务"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in xrxzzwOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="党内职务" prop="xrdnzw">
            <el-select
              v-model="queryParams.xrdnzw"
              placeholder="请选择现任党内职务"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in xrdnzwOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="群团职务" prop="xrqtzw">
            <el-select
              v-model="queryParams.xrqtzw"
              placeholder="请选择现任群团职务"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in xrqtzwOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="校长职级" prop="xrxzzj">
            <el-select
              v-model="queryParams.xrxzzj"
              placeholder="请选择现任校长职级"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in xrxzzjOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="身份证号" prop="sfzh">
            <el-input
              v-model="queryParams.sfzh"
              placeholder="请输入身份证号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <!-- <el-form-item label="出生日期" prop="csrq">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.csrq"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择出生日期"
        >
        </el-date-picker>
      </el-form-item> -->
          <el-form-item label="教育类型" prop="jylx">
            <el-select
              v-model="queryParams.jylx"
              placeholder="请选择教育类型"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in jylxOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="驾照情况" prop="jzcyqk">
            <el-select
              v-model="queryParams.jzcyqk"
              placeholder="请选择驾照持有情况"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in jzcyqkOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="能否开车" prop="nfkc">
            <el-select
              v-model="queryParams.nfkc"
              placeholder="请选择能否开车"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in nfkcOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="婚姻状况" prop="hyzk">
            <el-select
              v-model="queryParams.hyzk"
              placeholder="请选择婚姻状况"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in hyzkOptions"
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
          <el-form-item label="手机号码" prop="phone">
            <el-input
              v-model="queryParams.phone"
              placeholder="请输入手机号码"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
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
          <el-form-item label="健康状况" prop="jkzk">
            <el-select
              v-model="queryParams.jkzk"
              placeholder="请选择健康状况"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in jkzkOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="参加工作年月" prop="cjgzny">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.cjgzny"
          type="month"
          value-format="yyyy-MM"
          placeholder="选择参加工作年月"
        >
        </el-date-picker>
      </el-form-item> -->
          <!-- <el-form-item label="是否后备干部" prop="sfhbgb">
            <el-select
              v-model="queryParams.sfhbgb"
              placeholder="请选择是否后备干部"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in sfhbgbOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item> -->
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
          <el-table-column fixed label="教师姓名" align="center" prop="name" width="120" />
          <el-table-column
            label="性别"
            align="center"
            prop="xb"
            :formatter="xbFormat"
          />
          <el-table-column
            label="现任行政职务"
            align="center"
            prop="xrxzzw"
             width="140"
            :formatter="xrxzzwFormat"
          />
          <el-table-column label="出生日期" width="140" align="center" prop="csrq">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.csrq, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="参加工作年月" width="140" align="center" prop="cjgzny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.cjgzny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="职称"
            align="center"
            prop="zc"
            :formatter="zcFormat"
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
      // 民族字典
      mzOptions: [],
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
      deptTreeOptions: [],
      deptName: null,
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
        sfhbgb: null,
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
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 表单校验
      rules: {},
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    this.getList();
    this.getTreeselect();
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
    this.getDicts("sys_dm_mz").then((response) => {
      this.mzOptions = response.data;
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
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then((response) => {
        this.deptTreeOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
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
    // 民族字典翻译
    mzFormat(row, column) {
      return this.selectDictLabel(this.mzOptions, row.mz);
    },
    // 职称字典翻译
    zcFormat(row, column) {
      return this.selectDictLabel(this.zcOptions, row.zc);
    },
    // 政治面貌字典翻译
    zzmmFormat(row, column) {
      return this.selectDictLabel(this.zzmmOptions, row.zzmm);
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
