<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <el-form-item label="考核学年" prop="xn">
        <el-select v-model="queryParams.xn" placeholder="请选择学年">
          <el-option
            v-for="dict in xnOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="考核学期" prop="xq">
        <el-select v-model="queryParams.xq" placeholder="请选择学期">
          <el-option
            v-for="dict in xqOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任务名称" prop="rwmc">
        <el-input
          v-model="queryParams.rwmc"
          placeholder="请输入名称"
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

    <el-table v-loading="loading" :data="jyykhrwList">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column
        label="考核学年"
        align="center"
        prop="xn"
        :formatter="xnFormat"
      />
      <el-table-column
        label="考核学期"
        align="center"
        prop="xq"
        :formatter="xqFormat"
      />
      <el-table-column label="任务名称" align="center" prop="rwmc" />
      <el-table-column
        label="考核部门"
        align="center"
        prop="khbm"
        :formatter="khbmFormat"
      />
      <el-table-column label="任务说明" align="center" prop="rwsm" />
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

    <!-- 添加或修改教研员考核任务对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1024px"
      append-to-body
    >
      <el-table :data="jyykhrwDetailsList">
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
          label="考核周期"
          align="center"
          prop="khzqkssj"
          width="180"
        >
          <template slot-scope="scope">
            <span
              >{{ parseTime(scope.row.khzqkssj, "{y}/{m}/{d}") }} -
              {{ parseTime(scope.row.khzqjssj, "{y}/{m}/{d}") }}</span
            >
          </template>
        </el-table-column>
        <el-table-column label="数量要求" align="center" prop="slyq" />
        <el-table-column
          label="是否必选"
          align="center"
          prop="fsbx"
          :formatter="typeFormat"
        />
        <!-- <el-table-column label="是否删除" align="center" prop="isdel" /> -->
        <el-table-column label="任务说明" align="center" prop="rwsm" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listJyykhrw,
  getJyykhrw,
  delJyykhrw,
  addJyykhrw,
  updateJyykhrw,
  exportJyykhrw,
} from "@/api/jyykhgl/jyykhrw";
import { listJyyjhzj } from "@/api/jyykhgl/jyyjhzj";
import { listJyyyjhd } from "@/api/jyykhgl/jyyyjhd";
import { listJyydwjs } from "@/api/jyykhgl/jyydwjs";
import { listJyydyzd } from "@/api/jyykhgl/jyydyzd";
import { listJyyzlgl } from "@/api/jyykhgl/jyyzlgl";
import { listJyyfwjc } from "@/api/jyykhgl/jyyfwjc";
import { listJyygryx } from "@/api/jyykhgl/jyygryx";
import { listJyyqtkh } from "@/api/jyykhgl/jyyqtkh";

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
      jyykhrwDetailsList: [],
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
      //学年
      xnOptions: [],
      //学期
      xqOptions: [],
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
      // 查询参数
      queryDetailsParams: {
        parentId: null,
      },
      queryParams_fid: {
        rwid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_gbxn").then((response) => {
      this.xnOptions = response.data;
    });
    this.getDicts("sys_dm_xq").then((response) => {
      this.xqOptions = response.data;
    });
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
    // 学年字典翻译
    xnFormat(row, column) {
      return this.selectDictLabel(this.xnOptions, row.xn);
    },
    // 学期字典翻译
    xqFormat(row, column) {
      return this.selectDictLabel(this.xqOptions, row.xq);
    },
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
    //完成数量
    wcslFormat(row, colum) {
      //console.log(row);
      this.queryParams_fid.rwid = row.id;
      switch (row.rwlx) {
        case "01":
          this.getJhzjList();
          break;
        case "02":
          this.getYjhdList();
          break;
        case "03":
          this.getDwjsList();
          break;
        case "04":
          this.getDyzdList();
          break;
        case "05":
          this.getZlglList();
          break;
        case "06":
          this.getWfjcList();
          break;
        case "07":
          this.getGryxList();
          break;
        case "08":
          this.getQtkhList();
          break;
      }
      return this.wcqltotal;
    },
    /** 查询计划总结（教研员）列表 */
    getJhzjList() {
      listJyyjhzj(this.queryParams_fid).then((response) => {
        this.wcqltotal = response.total;
      });
    },
    /** 查询研究活动（教研员）列表 */
    getYjhdList() {
      listJyyyjhd(this.queryParams_fid).then((response) => {
        this.wcqltotal = response.total;
      });
    },
    /** 查询队伍建设（教研员）列表 */
    getDwjsList() {
      listJyydwjs(this.queryParams_fid).then((response) => {
        this.wcqltotal = response.total;
      });
    },
    /** 查询调研指导列表 */
    getDyzdList() {
      listJyydyzd(this.queryParams_fid).then((response) => {
        this.wcqltotal = response.total;
      });
    },
    /** 查询质量管理（教研员）列表 */
    getZlglList() {
      listJyyzlgl(this.queryParams_fid).then((response) => {
        this.wcqltotal = response.total;
      });
    },
    /** 查询服务基层（教研员）列表 */
    getWfjcList() {
      listJyyfwjc(this.queryParams_fid).then((response) => {
        this.wcqltotal = response.total;
      });
    },
    /** 查询个人研修（教研员）列表 */
    getGryxList() {
      listJyygryx(this.queryParams_fid).then((response) => {
        this.wcqltotal = response.total;
      });
    },
    /** 查询其他考核（教研员）列表 */
    getQtkhList() {
      listJyyqtkh(this.queryParams_fid).then((response) => {
        this.wcqltotal = response.total;
      });
    },
    /** 查询教研员考核任务列表 */
    getList() {
      this.loading = true;
      listJyykhrw(this.queryParams).then((response) => {
        this.jyykhrwList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getDetailsList() {
      listJyykhrw(this.queryDetailsParams).then((response) => {
        this.jyykhrwDetailsList = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
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
    handleView(row) {
      const id = row.id;
      this.queryDetailsParams.parentId = id;
      this.open = true;
      this.title = "查看教研员考核任务";
      this.getDetailsList();
    },
  },
};
</script>
<style lang="scss" scoped>
.el-select {
  width: 100%;
}
</style>