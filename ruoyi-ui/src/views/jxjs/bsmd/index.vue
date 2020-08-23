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
      <el-form-item label="基地校" prop="faid">
        <el-select v-model="queryParams.faid" placeholder="请选择基地校">
          <el-option v-for="dict in faOptions" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
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
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jdcxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="方案名称" align="center" prop="faname" />
      <el-table-column label="基地校" align="center" prop="jdxmc" />
      <el-table-column label="教师姓名" align="center" prop="jsname" />
      <el-table-column label="性别" align="center" prop="xb" :formatter="xbFormat" />
      <el-table-column label="学段" align="center" prop="rjxd" :formatter="xdFormat" />
      <el-table-column label="学科" align="center" prop="rjxk" :formatter="xkFormat" />
      <el-table-column label="联系方式" align="center" prop="phone" />
      <el-table-column label="聘任校" align="center" prop="prdwmc" />
      <el-table-column label="评审状态" align="center" prop="qjshzt" :formatter="qjshztFormat" />



      <!-- <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="方案名称" align="center" prop="faid" :formatter="faFormat" />
      <el-table-column label="教师姓名" align="center" prop="jsid" />
      <el-table-column label="当前状态" align="center" prop="dqzt" :formatter="dqztFormat" />
      <el-table-column label="基地校审核状态" align="center" prop="jdxshzt" :formatter="jdxshztFormat" />
      <el-table-column label="区级审核状态" align="center" prop="qjshzt" :formatter="qjshztFormat" />
      <el-table-column label="区级审核意见" align="center" prop="qjshyj" :formatter="qjshyjFormat" />
      <el-table-column label="综合得分" align="center" prop="zhdf" /> -->
      <!-- <el-table-column label="综合得分2" align="center" prop="zhdf2" /> -->

      <!-- <el-table-column label="创建人" align="center" prop="createuserid" />
      <el-table-column label="基地校审核人" align="center" prop="jdxshr" />
      <el-table-column label="上报理由" align="center" prop="sbly" />
      <el-table-column label="区级审核人" align="center" prop="qjshr" />
      <el-table-column label="基地排序" align="center" prop="jdpx" />
      <el-table-column label="案例分析得分" align="center" prop="alfxdf" />
      <el-table-column label="教案设计得分" align="center" prop="jasjdf" />
      <el-table-column label="钢笔字得分" align="center" prop="gbzdf" />
      <el-table-column label="成绩导入创建时间" align="center" prop="cjdrcreateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.cjdrcreateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="面试确认" align="center" prop="msqr" />
      <el-table-column label="面试确认时间" align="center" prop="msqrcreateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.msqrcreateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="面试结果模拟课堂教学" align="center" prop="msjgmnktjxdf" />
      <el-table-column label="演讲得分" align="center" prop="yjdf" />-->

      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxjs:jdcx:view']"
          >详情</el-button>
        </template>
      </el-table-column> -->
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
        <el-form-item label="方案编号" prop="faid">
          <el-input v-model="form.faid" placeholder="请输入方案编号" />
        </el-form-item>
        <el-form-item label="教师编号" prop="jsid">
          <el-input v-model="form.jsid" placeholder="请输入教师编号" />
        </el-form-item>
        <el-form-item label="当前状态" prop="dqzt">
          <el-select v-model="form.dqzt" placeholder="请选择当前状态">
            <el-option
              v-for="dict in dqztOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建人" prop="createuserid">
          <el-input v-model="form.createuserid" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="基地校审核人" prop="jdxshr">
          <el-input v-model="form.jdxshr" placeholder="请输入基地校审核人" />
        </el-form-item>
        <el-form-item label="基地校审核状态" prop="jdxshzt">
          <el-select v-model="form.jdxshzt" placeholder="请选择基地校审核状态">
            <el-option
              v-for="dict in jdxshztOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上报理由" prop="sbly">
          <el-input v-model="form.sbly" placeholder="请输入上报理由" />
        </el-form-item>
        <el-form-item label="区级审核人" prop="qjshr">
          <el-input v-model="form.qjshr" placeholder="请输入区级审核人" />
        </el-form-item>
        <el-form-item label="区级审核状态" prop="qjshzt">
          <el-select v-model="form.qjshzt" placeholder="请选择区级审核状态">
            <el-option
              v-for="dict in qjshztOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="区级审核意见" prop="qjshyj">
          <el-select v-model="form.qjshyj" placeholder="请选择区级审核意见">
            <el-option
              v-for="dict in qjshyjOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="基地排序" prop="jdpx">
          <el-input v-model="form.jdpx" placeholder="请输入基地排序" />
        </el-form-item>
        <el-form-item label="案例分析得分" prop="alfxdf">
          <el-input v-model="form.alfxdf" placeholder="请输入案例分析得分" />
        </el-form-item>
        <el-form-item label="教案设计得分" prop="jasjdf">
          <el-input v-model="form.jasjdf" placeholder="请输入教案设计得分" />
        </el-form-item>
        <el-form-item label="钢笔字得分" prop="gbzdf">
          <el-input v-model="form.gbzdf" placeholder="请输入钢笔字得分" />
        </el-form-item>
        <el-form-item label="综合得分" prop="zhdf">
          <el-input v-model="form.zhdf" placeholder="请输入综合得分" />
        </el-form-item>
        <el-form-item label="成绩导入创建时间" prop="cjdrcreateTime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.cjdrcreateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择成绩导入创建时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="面试确认" prop="msqr">
          <el-input v-model="form.msqr" placeholder="请输入面试确认" />
        </el-form-item>
        <el-form-item label="面试确认时间" prop="msqrcreateTime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.msqrcreateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择面试确认时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="面试结果模拟课堂教学" prop="msjgmnktjxdf">
          <el-input v-model="form.msjgmnktjxdf" placeholder="请输入面试结果模拟课堂教学" />
        </el-form-item>
        <el-form-item label="演讲得分" prop="yjdf">
          <el-input v-model="form.yjdf" placeholder="请输入演讲得分" />
        </el-form-item>
        <el-form-item label="综合得分2" prop="zhdf2">
          <el-input v-model="form.zhdf2" placeholder="请输入综合得分2" />
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
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers" :action="upload.url + '?faide=' + upload.faide" :disabled="upload.isUploading" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
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
            <br/>
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
      // 基地校审核状态字典
      jdxshztOptions: [],
      // 区级审核状态字典
      qjshztOptions: [],
      // 区级审核意见字典
      qjshyjOptions: [],
      // 性别
      xbOptions: [],
      // 学段
      xdOptions: [],
      // 学科
      xkOptions: [],
      //方案
      faOptions: [],
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
      },
      // 查询参数
      queryParams_fa: {
        fazt: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        faid: [
          { required: true, message: "方案编号不能为空", trigger: "blur" },
        ],
        jsid: [
          { required: true, message: "教师编号不能为空", trigger: "blur" },
        ],
        dqzt: [
          { required: true, message: "当前状态不能为空", trigger: "blur" },
        ],
        jdxshzt: [
          {
            required: true,
            message: "基地校审核状态不能为空",
            trigger: "blur",
          },
        ],
        qjshzt: [
          { required: true, message: "区级审核状态不能为空", trigger: "blur" },
        ],
        qjshyj: [
          { required: true, message: "区级审核意见不能为空", trigger: "blur" },
        ],
        zhdf: [
          { required: true, message: "综合得分不能为空", trigger: "blur" },
        ],
        zhdf2: [
          { required: true, message: "综合得分2不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getFaList();
    this.getList();
    this.getDicts("sys_dm_shzt").then((response) => {
      this.dqztOptions = response.data;
    });
    this.getDicts("sys_dm_shzt").then((response) => {
      this.jdxshztOptions = response.data;
    });
    this.getDicts("sys_dm_shzt").then((response) => {
      this.qjshztOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then((response) => {
      this.qjshyjOptions = response.data;
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
    // 基地校审核状态字典翻译
    jdxshztFormat(row, column) {
      return this.selectDictLabel(this.jdxshztOptions, row.jdxshzt);
    },
    // 区级审核状态字典翻译
    qjshztFormat(row, column) {
      return this.selectDictLabel(this.qjshztOptions, row.qjshzt);
    },
    // 区级审核意见字典翻译
    qjshyjFormat(row, column) {
      return this.selectDictLabel(this.qjshyjOptions, row.qjshyj);
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加基地区级审核";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJdcx(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基地区级审核";
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
          } else {
            addJdcx(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
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
      this.$alert("成功导入："+response.msg+"条数据。", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      if(this.upload.faide==""){
        this.$alert("请选择方案", "导入结果", { dangerouslyUseHTMLString: true });
      }else{
        this.$refs.upload.submit();
      }
      
    },
  },
};
</script>
