<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="130px">
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="干部姓名" prop="name">
            <el-input
              v-model="form.name"
              placeholder="请输入干部姓名"
              :disabled="true"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="干部性别" prop="xb">
            <el-select
              v-model="form.xb"
              placeholder="请选择性别"
              :disabled="true"
            >
              <el-option
                v-for="dict in xbOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="干部民族" prop="mz">
            <el-select
              v-model="form.mz"
              placeholder="请选择民族"
              :disabled="true"
            >
              <el-option
                v-for="dict in mzOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="第一学历" prop="dyxl">
            <el-select
              v-model="form.dyxl"
              placeholder="请选择学历"
              :disabled="true"
            >
              <el-option
                v-for="dict in dyxlOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="干部学历" prop="xl">
            <el-select
              v-model="form.xl"
              placeholder="请选择学历"
              :disabled="true"
            >
              <el-option
                v-for="dict in xlOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="干部学位" prop="xw">
            <el-select
              v-model="form.xw"
              placeholder="请选择学位"
              :disabled="true"
            >
              <el-option
                v-for="dict in xwOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="干部职称" prop="zc">
            <el-select
              v-model="form.zc"
              placeholder="请选择职称"
              :disabled="true"
            >
              <el-option
                v-for="dict in zcOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="干部籍贯" prop="jg">
            <v-distpicker
              v-model="form.jg"
              :placeholders="placeholders"
              :province="diglogForm.province"
              :city="diglogForm.city"
              :area="diglogForm.area"
              :disabled="true"
              class="el-select"
            ></v-distpicker>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="干部出生地" prop="csd">
            <v-distpicker
              v-model="form.csd"
              :placeholders="placeholders"
              :province="diglogForm1.province"
              :city="diglogForm1.city"
              :area="diglogForm1.area"
              :disabled="true"
              class="el-select"
            ></v-distpicker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号码" prop="phone">
            <el-input
              v-model="form.phone"
              placeholder="请输入手机号码"
              :disabled="true"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="单位名称" prop="dwmc">
            <el-select
              v-model="form.deptId"
              placeholder="请选择教育类型"
              :disabled="true"
            >
              <el-option
                v-for="dict in deptOptions"
                :key="dict.deptId"
                :label="dict.deptName"
                :value="dict.deptId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <!-- <el-col :span="12">
            <el-form-item label="单位简称" prop="dwjc">
              <el-input v-model="form.dwjc" placeholder="请输入单位简称" :disabled="true"/>
            </el-form-item>
          </el-col> -->
        <el-col :span="12">
          <el-form-item label="单位地址" prop="dwdz">
            <el-input
              v-model="form.dwdz"
              placeholder="请输入单位地址"
              :disabled="true"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="家庭住址" prop="jtzz">
            <el-input
              v-model="form.jtzz"
              placeholder="请输入家庭住址"
              :disabled="true"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="毕业院校" prop="byyx">
            <el-input
              v-model="form.byyx"
              placeholder="请输入毕业院校"
              :disabled="true"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="教育类型" prop="jylx">
            <el-select
              v-model="form.jylx"
              placeholder="请选择教育类型"
              :disabled="true"
            >
              <el-option
                v-for="dict in jylxOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="身份证号" prop="sfzh">
            <el-input
              v-model="form.sfzh"
              placeholder="请输入身份证号"
              :disabled="true"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="出生日期" prop="csrq">
            <el-date-picker
              clearable
              size="small"
              class="my-date-picker"
              v-model="form.csrq"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择出生日期"
              :disabled="true"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="婚姻状况" prop="hyzk">
            <el-select
              v-model="form.hyzk"
              placeholder="请选择婚姻状况"
              :disabled="true"
            >
              <el-option
                v-for="dict in hyzkOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="政治面貌" prop="zzmm">
            <el-select
              v-model="form.zzmm"
              placeholder="请选择政治面貌"
              :disabled="true"
            >
              <el-option
                v-for="dict in zzmmOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入党时间" prop="rdsj">
            <el-date-picker
              clearable
              size="small"
              class="my-date-picker"
              v-model="form.rdsj"
              type="month"
              value-format="yyyy-MM"
              placeholder="选择入党时间"
              :disabled="true"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任教学科" prop="rjxk">
            <el-select
              v-model="form.rjxk"
              placeholder="请选择任教学科"
              :disabled="true"
            >
              <el-option
                v-for="dict in rjxkOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="健康状况" prop="jkzk">
            <el-select
              v-model="form.jkzk"
              placeholder="请选择健康状况"
              :disabled="true"
            >
              <el-option
                v-for="dict in jkzkOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="能否开车" prop="nfkc">
            <el-select
              v-model="form.nfkc"
              placeholder="请选择能否开车"
              :disabled="true"
            >
              <el-option
                v-for="dict in nfkcOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="驾照持有情况" prop="jzcyqk">
            <el-select
              v-model="form.jzcyqk"
              placeholder="请选择驾照持有情况"
              :disabled="true"
            >
              <el-option
                v-for="dict in jzcyqkOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="现任行政职务" prop="xrxzzw">
            <el-select
              v-model="form.xrxzzw"
              placeholder="请选择现任行政职务"
              :disabled="true"
            >
              <el-option
                v-for="dict in xrxzzwOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="现任党内职务" prop="xrdnzw">
            <el-select
              v-model="form.xrdnzw"
              placeholder="请选择现任党内职务"
              :disabled="true"
            >
              <el-option
                v-for="dict in xrdnzwOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="现任群团职务" prop="xrqtzw">
            <el-select
              v-model="form.xrqtzw"
              placeholder="请选择现任群团职务"
              :disabled="true"
            >
              <el-option
                v-for="dict in xrqtzwOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任现职务年月" prop="rxzwny">
            <el-date-picker
              clearable
              size="small"
              class="my-date-picker"
              v-model="form.rxzwny"
              type="month"
              value-format="yyyy-MM"
              placeholder="选择任现职务年月"
              :disabled="true"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="现任校长职级" prop="xrxzzj">
            <el-select
              v-model="form.xrxzzj"
              placeholder="请选择现任校长职级"
              :disabled="true"
            >
              <el-option
                v-for="dict in xrxzzjOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="校长职级确定年月" prop="xzzjqdny">
            <el-date-picker
              clearable
              size="small"
              class="my-date-picker"
              v-model="form.xzzjqdny"
              type="month"
              value-format="yyyy-MM"
              placeholder="选择校长职级确定年月"
              :disabled="true"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否后备干部" prop="sfhbgb">
            <el-select
              v-model="form.sfhbgb"
              placeholder="请选择是否后备干部"
              :disabled="true"
            >
              <el-option
                v-for="dict in sfhbgbOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="后备干部确定年月" prop="hbgbqdny">
            <el-date-picker
              clearable
              size="small"
              class="my-date-picker"
              v-model="form.hbgbqdny"
              type="month"
              value-format="yyyy-MM"
              placeholder="选择后备干部确定年月"
              :disabled="true"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参加工作年月" prop="cjgzny">
            <el-date-picker
              clearable
              size="small"
              class="my-date-picker"
              v-model="form.cjgzny"
              type="month"
              value-format="yyyy-MM"
              placeholder="选择参加工作年月"
              :disabled="true"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="办公室电话" prop="bgsdh">
            <el-input
              v-model="form.bgsdh"
              placeholder="请输入办公室电话"
              :disabled="true"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="任免情况" name="first">
        <el-table v-loading="loading" :data="gbxrzwList">
          <el-table-column
            label="干部姓名"
            align="center"
            prop="gbid"
            :formatter="gbmcFormat"
          />
          <el-table-column label="起始年月" align="center" prop="rzny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.rzny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="终止年月" align="center" prop="zzny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.zzny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="行政职务"
            align="center"
            prop="xzzw"
            :formatter="xzzwFormat"
          />
          <el-table-column
            label="行政职务级别"
            align="center"
            prop="xxzwjb"
            :formatter="xxzwjbFormat"
          />
          <el-table-column
            label="党内职务"
            align="center"
            prop="dnzw"
            :formatter="dnzwFormat"
          />
          <el-table-column
            label="党内职务属性"
            align="center"
            prop="dnzwsx"
            :formatter="dnzwsxFormat"
          />
          <el-table-column
            label="群团职务"
            align="center"
            prop="qtzw"
            :formatter="qtzwFormat"
          />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="社会职务" name="second">
        <el-table v-loading="loading" :data="gbshzwList">
          <el-table-column
            label="干部姓名"
            align="center"
            prop="gbid"
            :formatter="gbmcFormat"
          />
          <el-table-column label="任职起始年月" align="center" prop="rzqsny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.rzqsny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="任职终止年月" align="center" prop="rzzzny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.rzzzny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="届别"
            align="center"
            prop="jb"
            :formatter="jbFormat"
          />
          <el-table-column
            label="职务级别"
            align="center"
            prop="jibie"
            :formatter="jibieFormat"
          />
          <el-table-column label="职务名称" align="center" prop="name" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="工作经历" name="third">
        <el-table v-loading="loading" :data="gbgzjlList">
          <el-table-column
            label="干部姓名"
            align="center"
            prop="gbid"
            :formatter="gbmcFormat"
          />
          <el-table-column label="工作单位" align="center" prop="gzdwmc" />
          <el-table-column label="起始年月" align="center" prop="qsny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.qsny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="终止年月" align="center" prop="zzny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.zzny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="执行职务"
            align="center"
            prop="zzzw"
            :formatter="zzzwFormat"
          />
          <el-table-column
            label="党内职务"
            align="center"
            prop="dnzw"
            :formatter="dnzwFormat"
          />
          <el-table-column
            label="群团职务"
            align="center"
            prop="qtzw"
            :formatter="qtzwFormat"
          />
          <el-table-column
            label="任教学科"
            align="center"
            prop="rjxk"
            :formatter="rjxkFormat"
          />
          <el-table-column
            label="工作岗位"
            align="center"
            prop="gzgw"
            :formatter="gzgwFormat"
          />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="教育经历" name="fourth"></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { listGbjbqk, getGbjbqk } from "@/api/gbxxgl/gbjbqk";
import { listDept, getDept } from "@/api/system/dept";
//导入省市区三级联动库
import VDistpicker from "v-distpicker";
import { listGbxrzw } from "@/api/gbxxgl/gbxrzw";
import { listGbshzw } from "@/api/gbxxgl/gbshzw";
import { listGbgzjl } from "@/api/gbxxgl/gbgzjl";

export default {
  name: "Gbjbqk",
  data() {
    return {
      activeName: "first",
      placeholders: {
        province: "请选择省",
        city: "请选择市",
        area: "请选择区",
      },
      diglogForm: {
        province: null,
        city: null,
        area: null,
      },
      diglogForm1: {
        province: null,
        city: null,
        area: null,
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 干部基本情况表格数据
      gbjbqkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
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
      // 行政职务字典
      xzzwOptions: [],
      // 行政职务级别字典
      xxzwjbOptions: [],
      // 党内职务字典
      dnzwOptions: [],
      // 党内职务属性字典
      dnzwsxOptions: [],
      // 群团职务字典
      qtzwOptions: [],
      // 干部名称
      gbmcOptions: [],
      // 届别字典
      jbOptions: [],
      // 职务级别字典
      jibieOptions: [],
      // 表单参数
      form: {},
      // 干部任职情况-现任职务表格数据
      gbxrzwList: [],
      // 干部任职情况-社会职务表格数据
      gbshzwList: [],
      // 干部工作经历表格数据
      gbgzjlList: [],
      queryParams_gb: {
        gbid: null,
      },
    };
  },
  components: {
    //省市区三级联动全局组件
    VDistpicker,
  },
  created() {
    const id = this.$route.params && this.$route.params.id;
    this.getData(id);
    this.getGbjbqkList();
    this.queryParams_gb.gbid = id;
    this.getGbXrzwList();
    this.getGbShzwList();
    this.getGbGzjlList();
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
    ///////////////任免情况
    this.getDicts("sys_dm_xrxzzw").then((response) => {
      this.xzzwOptions = response.data;
    });
    this.getDicts("sys_dm_xzzwjb").then((response) => {
      this.xxzwjbOptions = response.data;
    });
    this.getDicts("sys_dm_xrdnzw").then((response) => {
      this.dnzwOptions = response.data;
    });
    this.getDicts("sys_dnzwsx").then((response) => {
      this.dnzwsxOptions = response.data;
    });
    this.getDicts("sys_dm_xrqtzw").then((response) => {
      this.qtzwOptions = response.data;
    });
    ///////////////社会职务
    this.getDicts("sys_dm_jiebie").then((response) => {
      this.jbOptions = response.data;
    });
    this.getDicts("sys_dm_jibie").then((response) => {
      this.jibieOptions = response.data;
    });
  },
  methods: {
    /** 查询干部工作经历列表 */
    getGbGzjlList() {
      this.loading = true;
      listGbgzjl(this.queryParams_gb).then((response) => {
        this.gbgzjlList = response.rows;
        this.loading = false;
      });
    },
    // 届别字典翻译
    jbFormat(row, column) {
      return this.selectDictLabel(this.jbOptions, row.jb);
    },
    // 职务级别字典翻译
    jibieFormat(row, column) {
      return this.selectDictLabel(this.jibieOptions, row.jibie);
    },
    /** 查询干部列表 */
    getGbjbqkList() {
      listGbjbqk(null).then((response) => {
        this.gbmcOptions = response.rows;
      });
    },
    // 干部字典翻译
    gbmcFormat(row, column) {
      var actions = [];
      var datas = this.gbmcOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.gbid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    // 行政职务字典翻译
    xzzwFormat(row, column) {
      return this.selectDictLabel(this.xzzwOptions, row.xzzw);
    },
    // 行政职务级别字典翻译
    xxzwjbFormat(row, column) {
      return this.selectDictLabel(this.xxzwjbOptions, row.xxzwjb);
    },
    // 党内职务字典翻译
    dnzwFormat(row, column) {
      return this.selectDictLabel(this.dnzwOptions, row.dnzw);
    },
    // 党内职务属性字典翻译
    dnzwsxFormat(row, column) {
      return this.selectDictLabel(this.dnzwsxOptions, row.dnzwsx);
    },
    // 群团职务字典翻译
    qtzwFormat(row, column) {
      return this.selectDictLabel(this.qtzwOptions, row.qtzw);
    },
    /** 查询干部任职情况-现任职务列表 */
    getGbXrzwList() {
      this.loading = true;
      listGbxrzw(this.queryParams_gb).then((response) => {
        this.gbxrzwList = response.rows;
        this.loading = false;
      });
    },
    /** 查询干部任职情况-社会职务列表 */
    getGbShzwList() {
      this.loading = true;
      listGbshzw(this.queryParams_gb).then((response) => {
        this.gbshzwList = response.rows;
        this.loading = false;
      });
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    getData(id) {
      getGbjbqk(id).then((response) => {
        this.form = response.data;
        this.diglogForm.province = response.data.jg;
        this.diglogForm.city = response.data.jgCityname;
        this.diglogForm.area = response.data.jgAreaname;

        this.diglogForm1.province = response.data.csd;
        this.diglogForm1.city = response.data.csdCityname;
        this.diglogForm1.area = response.data.csdAreaname;

        // this.open_look = true;
        // this.title_look = "查看干部详情";
      });
    },
    //所在省市区触发联动方法
    onSelected_jg(data) {
      // console.log("onSelected_brith=" + data);
      if (
        data.province.code == undefined ||
        data.city.code == undefined ||
        data.area.code == undefined
      ) {
        this.form.jg = undefined;
      } else {
        this.form.jg = data.province.value;
        this.form.jgProvince = data.province.code;
        this.form.jgCityname = data.city.value;
        this.form.jgCity = data.city.code;
        this.form.jgAreaname = data.area.value;
        this.form.jgArea = data.area.code;
      }
    },
    //所在省市区触发联动方法
    onSelected_csd(data) {
      // console.log("onSelected_brith=" + data);
      if (
        data.province.code == undefined ||
        data.city.code == undefined ||
        data.area.code == undefined
      ) {
        this.form.csd = undefined;
      } else {
        this.form.csd = data.province.value;
        this.form.csdProvince = data.province.code;
        this.form.csdCityname = data.city.value;
        this.form.csdCity = data.city.code;
        this.form.csdAreaname = data.area.value;
        this.form.csdArea = data.area.code;
      }
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
    // 民族字典翻译
    mzFormat(row, column) {
      return this.selectDictLabel(this.mzOptions, row.mz);
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
</style>
