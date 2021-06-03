<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <el-form-item label="考核年份" prop="nf">
        <el-select v-model="queryParams.nf" placeholder="请选择考核类型">
          <el-option
            v-for="dict in nfOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="考核类型" prop="khlx">
        <el-select v-model="queryParams.khlx" placeholder="请选择考核类型">
          <el-option
            v-for="dict in khlxOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="附件类型" prop="fjlx">
        <el-select v-model="queryParams.khlx" placeholder="请选择考核类型">
          <el-option
            v-for="dict in fjlxOptions"
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
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['qtjskhgl:qtjskhwj:add']"
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
          v-hasPermi="['qtjskhgl:qtjskhwj:edit']"
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
          v-hasPermi="['qtjskhgl:qtjskhwj:remove']"
          >删除</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="qtjskhwjList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="考核年份"
        align="center"
        prop="nf"
        :formatter="nfFormat"
      />
      <el-table-column
        label="考核类型"
        align="center"
        prop="khlx"
        :formatter="khlxFormat"
      />
      <el-table-column
        label="所属方案"
        align="center"
        prop="faid"
        :formatter="faFormat"
      />
      <el-table-column
        label="附件类型"
        align="center"
        prop="fjlx"
        :formatter="fjlxFormat"
      />
      <el-table-column label="学校名称" align="center" prop="dept.deptName" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['qtjskhgl:qtjskhwj:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['qtjskhgl:qtjskhwj:remove']"
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

    <!-- 添加或修改群体教师考核文件管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考核年份" prop="nf">
          <el-select v-model="form.nf" placeholder="请选择考核类型">
            <el-option
              v-for="dict in nfOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属方案" prop="faid">
          <el-select v-model="form.faid" >
            <el-option
              v-for="item in qtjskhfaOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="考核类型" prop="khlx">
          <el-select v-model="form.khlx" placeholder="请选择考核类型">
            <el-option
              v-for="dict in khlxOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="附件类型" prop="fjlx">
          <el-select v-model="form.fjlx" placeholder="请选择考核类型">
            <el-option
              v-for="dict in fjlxOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="文件路径" prop="filepath">
          <el-input v-model="form.filename" v-if="false" />
          <el-input v-model="form.filepath" v-if="false" />
          <el-upload
            class="upload-demo"
            :action="uploadFileUrl"
            :headers="headers"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            :limit="1"
            :on-exceed="handleExceed"
            :file-list="fileList"
            :on-success="handleAvatarSuccess"
          >
            <el-button size="small" type="primary">选择文件</el-button>
          </el-upload>
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
import {
  listQtjskhwj,
  getQtjskhwj,
  delQtjskhwj,
  addQtjskhwj,
  updateQtjskhwj,
  exportQtjskhwj,
} from "@/api/qtjskhgl/qtjskhwj";
import { getToken } from "@/utils/auth";
import { listQtjskhfa, getQtjskhfa } from "@/api/qtjskhgl/qtjskhfa";

export default {
  name: "Qtjskhwj",
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
      // 群体教师考核文件管理表格数据
      qtjskhwjList: [],
      // 年份选项
      nfOptions: [],
      // 考核类型
      khlxOptions: [],
      // 文件类型
      fjlxOptions: [],
      // 上传文件list
      fileList: [],
      // 方案列表
      qtjskhfaOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        nf: null,
        khlx: null,
        fjlx: null,
        filename: null,
        filepath: null,
        deptId: null,
        createUserid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        faid: [
          { required: true, message: "所属方案不能为空", trigger: "blur" },
        ],
        nf: [
          { required: true, message: "年份不能为空", trigger: "blur" },
        ],
        khlx: [
          { required: true, message: "考核类型不能为空", trigger: "blur" },
        ],
        fjlx: [
          { required: true, message: "附件类型不能为空", trigger: "blur" },
        ],
        filepath: [
          { required: true, message: "附件不能为空", trigger: "blur" },
        ],
      },
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
    };
  },
  created() {
    this.getList();
    this.getKhfa();
    this.getDicts("sys_gbxn").then((response) => {
      this.nfOptions = response.data;
    });
    this.getDicts("sys_dm_qtjskhlx").then((response) => {
      this.khlxOptions = response.data;
    });
    this.getDicts("sys_dm_qtjskhwjlx").then((response) => {
      this.fjlxOptions = response.data;
    });
  },
  methods: {
    /** 查询群体教师考核文件管理列表 */
    getList() {
      this.loading = true;
      listQtjskhwj(this.queryParams).then((response) => {
        this.qtjskhwjList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 字典翻译
    nfFormat(row, column) {
      return this.selectDictLabel(this.nfOptions, row.nf);
    },
    // 考核类型
    khlxFormat(row, column) {
      return this.selectDictLabel(this.khlxOptions, row.khlx);
    },
    fjlxFormat(row, column) {
      return this.selectDictLabel(this.fjlxOptions, row.fjlx);
    },
    // 文件上传
    handlePreview(file) {
      //console.log(file);
    },
    // 文件上传移除
    handleRemove(file, fileList) {
      // console.log(file);
      if (file.response.code == "200") {
        this.form.filepath = "";
      }
    },
    // 文件上传 弹窗
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    // 文件上传  限制
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    // 文件上传 成功钩子
    handleAvatarSuccess(res, file) {
      // console.log(res, file);
      if (res.code == "200") {
        this.form.filepath = res.fileName;
        this.form.filename = file.name;
      } else {
        this.msgError(res.msg);
      }
    },
    // 字典翻译
    faFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.qtjskhfaOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.faid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    //考核方案
    getKhfa() {
      listQtjskhfa(null).then((response) => {
        this.qtjskhfaOptions = response.rows;
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
        nf: null,
        khlx: null,
        fjlx: null,
        filename: null,
        filepath: null,
        deptId: null,
        createUserid: null,
        createTime: null,
      };
      this.resetForm("form");
      this.fileList = [];
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
      this.title = "添加群体教师考核文件管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getQtjskhwj(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改群体教师考核文件管理";
        this.fileList.push({
          name: response.data.filename,
          url: response.data.filepath,
        });
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateQtjskhwj(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addQtjskhwj(this.form).then((response) => {
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
        '是否确认删除群体教师考核文件管理编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delQtjskhwj(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
  },
};
</script>
