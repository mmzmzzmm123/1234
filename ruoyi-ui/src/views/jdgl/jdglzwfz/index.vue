<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="任务名称" prop="rwmc">
        <el-select v-model="queryParams.rwmc" placeholder="请选择任务名称" clearable size="small">
          <el-option
            v-for="dict in rwmcOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上传时间" prop="sctime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.sctime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择上传时间">
        </el-date-picker>
      </el-form-item>
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
          v-hasPermi="['jdgl:jdglzwfz:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jdgl:jdglzwfz:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jdgl:jdglzwfz:remove']"
        >删除</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jdglzwfzList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="附件名称" align="center" prop="name" />
      <el-table-column label="任务名称" align="center" prop="rwmc" :formatter="rwmcFormat" />
      <el-table-column label="上传时间" align="center" prop="sctime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sctime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jdgl:jdglzwfz:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jdgl:jdglzwfz:remove']"
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

    <!-- 添加或修改基地管理自我发展对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务名称" prop="rwmc">
          <el-select v-model="form.rwmc" placeholder="请选择任务名称">
            <el-option
              v-for="dict in rwmcOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上传文件" prop="scpath">
          <el-input v-model="form.name" v-if="false" />
          <el-input v-model="form.scpath" v-if="false" />
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
import { listJdglzwfz, getJdglzwfz, delJdglzwfz, addJdglzwfz, updateJdglzwfz, exportJdglzwfz } from "@/api/jdgl/jdglzwfz";
import { getToken } from "@/utils/auth";

export default {
  name: "Jdglzwfz",
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
      // 基地管理自我发展表格数据
      jdglzwfzList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 任务名称字典
      rwmcOptions: [],
      // 上传文件list
      fileList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jdid: null,
        name: null,
        rwmc: null,
        sctime: null,
        scpath: null,
        createUserid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_jdglzwfzrwmc").then(response => {
      this.rwmcOptions = response.data;
    });
  },
  methods: {
    /** 查询基地管理自我发展列表 */
    getList() {
      this.loading = true;
      listJdglzwfz(this.queryParams).then(response => {
        this.jdglzwfzList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 任务名称字典翻译
    rwmcFormat(row, column) {
      return this.selectDictLabel(this.rwmcOptions, row.rwmc);
    },
    // 文件上传
    handlePreview(file) {
      //console.log(file);
    },
    // 文件上传移除
    handleRemove(file, fileList) {
      // console.log(file);
      if (file.status == "success") {
        this.form.scpath = "";
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
        this.form.scpath = res.fileName;
        this.form.name = file.name;
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
        jdid: null,
        name: null,
        rwmc: null,
        sctime: null,
        scpath: null,
        createUserid: null,
        createTime: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加基地管理自我发展";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getJdglzwfz(id).then(response => {
        this.form = response.data;
        this.fileList.push({
          name: response.data.name,
          url: response.data.scpath,
        });
        this.open = true;
        this.title = "修改基地管理自我发展";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateJdglzwfz(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJdglzwfz(this.form).then(response => {
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
      this.$confirm('是否确认删除基地管理自我发展编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delJdglzwfz(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
  }
};
</script>
