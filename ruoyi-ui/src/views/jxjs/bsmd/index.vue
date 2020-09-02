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
      <el-form-item label="基地校" prop="jdxid">
        <el-select v-model="queryParams.jdxid" filterable placeholder="请选择基地校">
          <el-option v-for="item in jdxOptions" :key="item.id" :label="item.jdxmc" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="选择教师" prop="jsid">
        <el-input
          v-model="queryParams.jsid"
          placeholder="请输入教师"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
        >确认面试</el-button>
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
        >导出笔试名单</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jdcxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" :selectable="isShow" />
      <el-table-column label="方案名称" align="center" prop="faname" />
      <el-table-column label="基地校" align="center" prop="jdxmc" />
      <el-table-column label="教师姓名" align="center" prop="jsname" />
      <el-table-column label="区级审核意见" align="center" prop="qjshzt" :formatter="qjshztFormat" />
      <el-table-column label="性别" align="center" prop="xb" :formatter="xbFormat" />
      <el-table-column label="学段" align="center" prop="rjxd" :formatter="xdFormat" />
      <el-table-column label="学科" align="center" prop="rjxk" :formatter="xkFormat" />
      <el-table-column label="联系方式" align="center" prop="phone" />
      <el-table-column label="聘任校" align="center" prop="prdwmc" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxjs:jdcx:edit']"
            v-show="isShow(scope.row)"
          >确认面试</el-button>
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
        <el-form-item label="面试确认" prop="msqr">
          <el-radio-group v-model="form.msqr">
            <el-radio
              v-for="dict in msqrOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- excel导入对话框lu -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-link type="info" style="font-size:14px" @click="importTemplate">下载模板</el-link>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?faide=' + upload.faide"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <!-- <el-form-item label="评选方案" prop="faid"> -->
          <el-select v-model="upload.faide" placeholder="请选择方案">
            <el-option v-for="dict in faOptions" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
          </el-select>
          <br />
          <!-- </el-form-item> -->
          <!-- <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据 -->
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listJdcx,
  getJdcx,
  delJdcx,
  addJdcx,
  updateJdcx,
  exportJdcx,
  importTemplate,
} from "@/api/jxjs/jdcx";

import { getToken } from "@/utils/auth";
import { listJxzxpxfa } from "@/api/jxjs/jxzxpxfa";
import { listJdx } from "@/api/jxjs/jdx";

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
      // 性别
      xbOptions: [],
      // 学段
      xdOptions: [],
      // 学科
      xkOptions: [],
      //方案
      faOptions: [],
      //字典
      msqrOptions: [],
      //基地校
      jdxOptions: [],
      // 用户导入参数lu
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        //方案id
        faide: "",
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/jxjs/jdcx/importData",
      },
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
        qjshzt: "1",
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
      },
      // 查询参数
      queryParams_fa: {
        fazt: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        msqr: [
          { required: true, message: "面试确认不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getFaList();
    this.getJdxList();
    this.getList();
    this.getDicts("sys_dm_msqr").then((response) => {
      this.msqrOptions = response.data;
    });
    this.getDicts("sys_dm_shzt").then((response) => {
      this.dqztOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then((response) => {
      this.qjshztOptions = response.data;
    });
    this.getDicts("sys_user_sex").then((response) => {
      this.xbOptions = response.data;
    });
    this.getDicts("sys_dm_rjxd").then((response) => {
      this.xdOptions = response.data;
    });
    this.getDicts("sys_dm_rjxk").then((response) => {
      this.xkOptions = response.data;
    });
  },
  methods: {
    getJdxList() {
      listJdx(null).then((response) => {
        this.jdxOptions = response.rows;
      });
    },
    isShow(row) {
      if (row.msqr == "1") {
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
    getFaList() {
      this.queryParams_fa.fazt = "1";
      listJxzxpxfa(this.queryParams_fa).then((response) => {
        this.faOptions = response.rows;
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
    // 当前状态字典翻译
    dqztFormat(row, column) {
      return this.selectDictLabel(this.dqztOptions, row.dqzt);
    },
    // 区级审核状态字典翻译
    qjshztFormat(row, column) {
      return this.selectDictLabel(this.qjshztOptions, row.qjshzt);
    },
    // 性别字典翻译
    xbFormat(row, column) {
      return this.selectDictLabel(this.xbOptions, row.xb);
    },
    // 学段字典翻译
    xdFormat(row, column) {
      return this.selectDictLabel(this.xdOptions, row.rjxd);
    },
    // 学科字典翻译
    xkFormat(row, column) {
      return this.selectDictLabel(this.xkOptions, row.rjxk);
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
        this.title = "面试确认";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJdcx(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除基地区级审核编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delJdcx(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有笔试名单数据项?", "警告", {
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
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "成绩导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then((response) => {
        this.download(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("成功导入：" + response.msg + "条数据。", "导入结果", {
        dangerouslyUseHTMLString: true,
      });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      if (this.upload.faide == "") {
        this.$alert("请选择方案", "导入结果", {
          dangerouslyUseHTMLString: true,
        });
      } else {
        this.$refs.upload.submit();
      }
    },
  },
};
</script>
