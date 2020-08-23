<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="评选方案" prop="faid">
        <el-select v-model="queryParams.faid" placeholder="请选择方案">
          <el-option v-for="dict in faOptions" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="选择教师" prop="jsid">
        <el-select v-model="queryParams.jsid" filterable placeholder="请选择教师">
          <el-option v-for="item in jsOptions" :key="item.id" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="录取状态" prop="lqzt">
        <el-select v-model="queryParams.lqzt" filterable placeholder="请选择录取状态">
          <el-option
            v-for="item in lqztOptions"
            :key="item.dictValue"
            :label="item.dictLable"
            :value="item.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxjs:jdcx:edit']"
        >确认</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:user:import']"
        >成绩导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jxjs:jxjsjbxx:export']"
        >导出面试名单</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jdcxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" :selectable="isShow" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <!-- <el-table-column label="方案名称" align="center" prop="faid" :formatter="faFormat" /> -->
      <el-table-column label="教师姓名" align="center" prop="jsid" :formatter="jsFormat" />
      <el-table-column label="当前状态" align="center" prop="dqzt" :formatter="dqztFormat" />
      <el-table-column label="录取状态" align="center" prop="lqzt" :formatter="lqztFormat" />
      <el-table-column label="区级审核意见" align="center" prop="qjshzt" :formatter="qjshztFormat" />
      <!-- <el-table-column label="区级审核意见" align="center" prop="qjshyj" :formatter="qjshyjFormat" /> -->
      <el-table-column label="案例分析得分" align="center" prop="alfxdf" />
      <el-table-column label="教案设计得分" align="center" prop="jasjdf" />
      <el-table-column label="钢笔字得分" align="center" prop="gbzdf" />
      <el-table-column label="笔试综合得分" align="center" prop="zhdf" />
      <el-table-column label="基地排序" align="center" prop="jdpx" />
      <el-table-column label="模拟课堂教学得分" align="center" prop="msjgmnktjxdf" />
      <el-table-column label="演讲得分" align="center" prop="yjdf" />
      <el-table-column label="面试综合得分" align="center" prop="zhdf2" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxjs:jdcx:edit']"
            v-show="isShow(scope.row)"
          >确认</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改基地区级审核对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="录取状态" prop="lqzt">
          <el-select v-model="form.lqzt" placeholder="请选择录取状态">
            <el-option
              v-for="dict in lqztOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJdcx, getJdcx, updateJdcx, exportJdcx } from "@/api/jxjs/jdcx";

import { listJxzxpxfa } from "@/api/jxjs/jxzxpxfa";

import { listJxjsjbxx, getJxjsjbxx } from "@/api/jxjs/jxjsjbxx";

export default {
  name: "Jdcx",
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
      // 基地区级审核表格数据
      jdcxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前状态字典
      dqztOptions: [],
      // 区级审核状态字典
      qjshztOptions: [],
      // 录取状态字典
      lqztOptions: [],
      //方案
      faOptions: [],
      //教师
      jsOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        jsid: null,
        dqzt: null,
        createuserid: null,
        jdxshr: null,
        jdxshzt: null,
        sbly: null,
        qjshr: null,
        qjshzt: null,
        qjshyj: null,
        jdpx: null,
        alfxdf: null,
        jasjdf: null,
        gbzdf: null,
        zhdf: null,
        cjdrcreateTime: null,
        msqr: "1",
        msqrcreateTime: null,
        msjgmnktjxdf: null,
        yjdf: null,
        zhdf2: null,
        lqzt: null,
      },
      // 查询参数
      queryParams_fa: {
        fazt: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        lqzt: [
          { required: true, message: "录取状态不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getFaList();
    this.getJsList();
    this.getList();
    this.getDicts("sys_dm_shzt").then((response) => {
      this.dqztOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then((response) => {
      this.qjshztOptions = response.data;
    });
    this.getDicts("sys_dm_lqzt").then((response) => {
      this.lqztOptions = response.data;
    });
  },
  methods: {
    isShow(row) {
      console.log(row.dqzt);
      if (row.dqzt == "9") {
        return false;
      } else {
        return true;
      }
    },
    // 字典翻译
    faFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.faOptions;
      Object.keys(datas).map((key) => {
        console.log(row.faid);
        if (datas[key].id == "" + row.faid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    // 字典翻译
    jsFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.jsOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.jsid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    getFaList() {
      this.queryParams_fa.fazt = "1";
      listJxzxpxfa(this.queryParams_fa).then((response) => {
        this.faOptions = response.rows;
      });
    },
    getJsList() {
      listJxjsjbxx(null).then((response) => {
        this.jsOptions = response.rows;
      });
    },
    /** 查询基地区级审核列表 */
    getList() {
      this.loading = true;
      listJdcx(this.queryParams).then((response) => {
        this.jdcxList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 录取状态字典翻译
    lqztFormat(row, column) {
      return this.selectDictLabel(this.lqztOptions, row.lqzt);
    },
    // 当前状态字典翻译
    dqztFormat(row, column) {
      return this.selectDictLabel(this.dqztOptions, row.dqzt);
    },
    // 区级审核状态字典翻译
    qjshztFormat(row, column) {
      return this.selectDictLabel(this.qjshztOptions, row.qjshzt);
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
        faid: null,
        jsid: null,
        dqzt: null,
        createuserid: null,
        createTime: null,
        jdxshr: null,
        jdxshzt: null,
        sbly: null,
        qjshr: null,
        qjshzt: null,
        qjshyj: null,
        jdpx: null,
        alfxdf: null,
        jasjdf: null,
        gbzdf: null,
        zhdf: null,
        cjdrcreateTime: null,
        msqr: null,
        msqrcreateTime: null,
        msjgmnktjxdf: null,
        yjdf: null,
        zhdf2: null,
        lqzt: null,
      };
      this.resetForm("form");
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJdcx(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "面试结果审核";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.form.dqzt = "9";
          if (this.form.id != null) {
            updateJdcx(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("录取成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有基地区级审核数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportJdcx(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
