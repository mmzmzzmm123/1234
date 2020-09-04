<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="102px"
    >
      <el-form-item label="考核方案名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入考核方案名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核方案状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择考核方案状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="考核方案年份" prop="khnf">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.khnf"
          type="year"
          value-format="yyyy"
          placeholder="选择考核方案年份"
        ></el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="考核年份" prop="khnf">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.khnf"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择考核年份">
        </el-date-picker>
      </el-form-item>-->
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['jxzxkhgl:jxzxkhfa:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxzxkhgl:jxzxkhfa:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jxzxkhgl:jxzxkhfa:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jxzxkhfaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="考核方案名称" align="center" prop="name" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <router-link :to="'/jxzxkhgl/jxzxkhfa/data/' + scope.row.id" class="link-type">
            <span>{{ scope.row.name }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="考核方案状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="考核方案年份" align="center" prop="khnf" />
      <el-table-column label="考核开始时间" align="center" prop="starttime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.starttime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="考核结束时间" align="center" prop="endtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="考核文件" align="center" prop="filename" />
      <!-- <el-table-column label="考核文件名称" align="center" prop="filename" />
      <el-table-column label="文件路径" align="center" prop="filepath" />
      <el-table-column label="考核年份" align="center" prop="khnf" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.khnf, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createuserid" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxzxkhgl:jxzxkhfa:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxzxkhgl:jxzxkhfa:remove']"
          >删除</el-button>
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

    <!-- 添加或修改见习之星考核方案对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="考核方案名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入考核方案名称" />
        </el-form-item>
        <el-form-item label="考核方案状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择考核方案状态">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考核年份" prop="khnf">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.khnf"
            type="year"
            value-format="yyyy"
            placeholder="选择考核年份"
            :disabled="isEdit"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="考核开始时间" prop="starttime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.starttime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择考核开始时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="考核结束时间" prop="endtime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.endtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择考核结束时间"
          ></el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="考核文件名称" prop="filename">
          <el-input v-model="form.filename" placeholder="请输入考核文件名称" />
        </el-form-item>-->
        <el-form-item label="考核文件" prop="filepath">
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
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
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
  listJxzxkhfa,
  getJxzxkhfa,
  delJxzxkhfa,
  addJxzxkhfa,
  updateJxzxkhfa,
} from "@/api/jxzxkhgl/jxzxkhfa";
import { getToken } from "@/utils/auth";

export default {
  name: "Jxzxkhfa",
  data() {
    return {
      //是否可以修改
      isEdit: false,
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
      // 见习之星考核方案表格数据
      jxzxkhfaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 考核方案状态
      statusOptions: [],
      // 上传文件list
      fileList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        status: null,
        starttime: null,
        endtime: null,
        filename: null,
        filepath: null,
        khnf: null,
        createuserid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "考核方案名称不能为空", trigger: "blur" },
        ],
        status: [
          { required: true, message: "考核方案状态不能为空", trigger: "blur" },
        ],
        starttime: [
          { required: true, message: "考核开始时间不能为空", trigger: "blur" },
        ],
        endtime: [
          { required: true, message: "考核结束时间不能为空", trigger: "blur" },
        ],
        filepath: [
          { required: true, message: "文件不能为空", trigger: "blur" },
        ],
        khnf: [
          { required: true, message: "考核年份不能为空", trigger: "blur" },
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
    this.getDicts("sys_dm_khfazt").then((response) => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询见习之星考核方案列表 */
    getList() {
      this.loading = true;
      listJxzxkhfa(this.queryParams).then((response) => {
        this.jxzxkhfaList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 考核方案状态
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 文件上传
    handlePreview(file) {
      //console.log(file);
    },
    // 文件上传移除
    handleRemove(file, fileList) {
      //console.log(file, fileList);
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
      console.log(res, file);
      if (res.code == "200") {
        this.form.filepath = res.fileName;
        this.form.filename = file.name;
      } else {
        this.msgError(res.msg);
      }
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
        name: null,
        status: null,
        starttime: null,
        endtime: null,
        filename: null,
        filepath: null,
        khnf: null,
        createuserid: null,
        createTime: null,
        remark: null,
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
      this.title = "添加见习之星考核方案";
      this.isEdit = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJxzxkhfa(id).then((response) => {
        this.isEdit = true;
        this.form = response.data;
        this.open = true;
        this.title = "修改见习之星考核方案";
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
            updateJxzxkhfa(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJxzxkhfa(this.form).then((response) => {
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
      this.$confirm("是否确认删除见习之星考核方案数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delJxzxkhfa(ids);
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
