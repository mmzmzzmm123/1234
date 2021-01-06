<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="入选年份" prop="xnxq">
        <el-select v-model="queryParams.xnxq" placeholder="请选择入选年份">
          <el-option
            v-for="dict in nfOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="基地校" prop="jdxid">
        <el-select
          v-model="queryParams.jdxid"
          filterable
          placeholder="请选择基地校"
        >
          <el-option
            v-for="item in jdxOptions"
            :key="item.id"
            :label="item.jdxmc"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="教师姓名" prop="jsid">
        <el-select
          v-model="queryParams.jsid"
          filterable
          placeholder="请选择教师"
        >
          <el-option
            v-for="item in jsOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
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
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['jxjs:jxjscj:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxjs:jxjscj:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jxjs:jxjscj:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['jxjs:jxjscj:import']"
          >导入</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jxjs:jxjscj:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="jxjscjList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="方案编号" align="center" prop="faid" /> -->
      <el-table-column
        label="教师姓名"
        align="center"
        prop="tsbzJxjsjbxx.name"
      />
      <el-table-column label="基地校" align="center" prop="jdxmc" />
      <!-- <el-table-column label="基地排序" align="center" prop="jdpx" /> -->
      <el-table-column label="案例分析得分" align="center" prop="alfxdf" />
      <el-table-column label="教案设计得分" align="center" prop="jasjdf" />
      <el-table-column label="钢笔字得分" align="center" prop="gbzdf" />
      <el-table-column label="综合得分" align="center" prop="zhdf" />
      <el-table-column
        label="学年学期"
        align="center"
        prop="xnxq"
        :formatter="nfFormat"
      />
      <!-- <el-table-column label="创建人" align="center" prop="createuserid" /> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="180px"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxjs:jxjscj:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxjs:jxjscj:remove']"
            >删除</el-button
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

    <!-- 添加或修改见习教师成绩对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="教师姓名" prop="jsid">
          <el-select v-model="form.jsid" filterable placeholder="请选择教师">
            <el-option
              v-for="item in jsOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="案例分析得分" prop="alfxdf">
          <el-input-number
            v-model="form.alfxdf"
            :precision="2"
            :step="0.1"
            :max="100"
            placeholder="请输入案例分析得分"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="教案设计得分" prop="jasjdf">
          <el-input-number
            v-model="form.jasjdf"
            :precision="2"
            :step="0.1"
            :max="100"
            placeholder="请输入教案设计得分"
          />
        </el-form-item>
        <el-form-item label="钢笔字得分" prop="gbzdf">
          <el-input-number
            v-model="form.gbzdf"
            :precision="2"
            :step="0.1"
            :max="100"
            placeholder="请输入钢笔字得分"
          />
        </el-form-item>
        <!-- <el-form-item label="综合得分" prop="zhdf">
          <el-input v-model="form.zhdf" placeholder="请输入综合得分" />
        </el-form-item> -->
        <el-form-item label="入选年份" prop="xnxq">
          <el-select v-model="form.xnxq" placeholder="请选择入选年份">
            <el-option
              v-for="dict in nfOptions"
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

    <!-- 用户导入对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-link type="info" style="font-size: 14px" @click="importTemplate"
            >下载模板</el-link
          >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
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
        <div class="el-upload__tip" style="color: red" slot="tip">
          提示：仅允许导入“xls”或“xlsx”格式文件！
        </div>
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
  listJxjscj,
  getJxjscj,
  delJxjscj,
  addJxjscj,
  updateJxjscj,
  exportJxjscj,
  importTemplate,
} from "@/api/jxjs/jxjscj";
import { listJdx } from "@/api/jxjs/jdx";
import { getToken } from "@/utils/auth";
import { listJxjsjbxx, getJxjsjbxx } from "@/api/jxjs/jxjsjbxx";

export default {
  name: "Jxjscj",
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
      // 见习教师成绩表格数据
      jxjscjList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //字典
      nfOptions: [],
      //基地校
      jdxOptions: [],
      //教师
      jsOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        jsid: null,
        jdpx: null,
        alfxdf: null,
        jasjdf: null,
        gbzdf: null,
        zhdf: null,
        xnxq: null,
        createuserid: null,
      },
      // 表单参数
      form: {},
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/jxjs/jxjscj/importData",
      },
      // 表单校验
      rules: {
        jsid: [{ required: true, message: "教师不能为空", trigger: "blur" }],
        alfxdf: [
          { required: true, message: "案例分析得分不能为空", trigger: "blur" },
        ],
        jasjdf: [
          { required: true, message: "教案设计得分不能为空", trigger: "blur" },
        ],
        gbzdf: [
          { required: true, message: "钢笔字得分不能为空", trigger: "blur" },
        ],
        xnxq: [
          { required: true, message: "入选年份不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getJdxList();
    this.getJsList();
    this.getList();
    this.getDicts("sys_dm_rxnf").then((response) => {
      this.nfOptions = response.data;
    });
  },
  methods: {
    getJsList() {
      listJxjsjbxx(null).then((response) => {
        this.jsOptions = response.rows;
      });
    },
    getJdxList() {
      listJdx(null).then((response) => {
        this.jdxOptions = response.rows;
      });
    },
    // 字典翻译
    nfFormat(row, column) {
      return this.selectDictLabel(this.nfOptions, row.xnxq);
    },
    /** 查询见习教师成绩列表 */
    getList() {
      this.loading = true;
      listJxjscj(this.queryParams).then((response) => {
        this.jxjscjList = response.rows;
        console.log(this.jxjscjList);
        this.total = response.total;
        this.loading = false;
      });
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
        jdpx: null,
        alfxdf: null,
        jasjdf: null,
        gbzdf: null,
        zhdf: null,
        xnxq: null,
        createuserid: null,
        createTime: null,
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
      this.title = "添加见习教师成绩";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJxjscj(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改见习教师成绩";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJxjscj(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJxjscj(this.form).then((response) => {
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
        '是否确认删除见习教师成绩编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delJxjscj(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "教师成绩导入";
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
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有见习教师成绩数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportJxjscj(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
