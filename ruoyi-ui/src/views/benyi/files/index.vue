<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="文件名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入文件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属类型" prop="type">
        <el-select
          v-model="queryParams.type"
          placeholder="请选择文件所属类型"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
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
          v-hasPermi="['benyi:files:add']"
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
          v-hasPermi="['benyi:files:edit']"
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
          v-hasPermi="['benyi:files:remove']"
          >删除</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="filesList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="文件名称" align="center" prop="name" />
      <el-table-column label="文件类型" align="center" prop="filetype" />
      <el-table-column
        label="文件所属类型"
        align="center"
        prop="type"
        :formatter="typeFormat"
      />
      <el-table-column
        label="适用角色"
        align="center"
        :formatter="rolesFormat"
        prop="roles"
      />
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
            v-hasPermi="['benyi:files:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:files:remove']"
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

    <!-- 添加或修改文件管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="文件名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入文件名称" />
        </el-form-item>
        <el-form-item label="所属类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择文件所属类型">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="选择文件" prop="fileurl">
          <el-input v-model="form.fileurl" v-if="false" />
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
        <el-form-item label="适用角色" prop="roles">
          <el-select
            v-model="form.roles"
            multiple
            placeholder="请选择适用角色"
            clearable
          >
            <el-option
              v-for="dict in rolesOptions"
              :key="dict.roleId"
              :label="dict.roleName"
              :value="dict.roleId"
            />
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
import {
  listFiles,
  getFiles,
  delFiles,
  addFiles,
  updateFiles,
} from "@/api/benyi/files";
import { listRole } from "@/api/system/role";

import { getToken } from "@/utils/auth";

export default {
  name: "Files",
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
      // 总条数
      total: 0,
      // 文件管理表格数据
      filesList: [],
      fileList: [],
      //角色
      rolesOptions: [],
      //文件类型
      typeOptions: [],
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        filetype: undefined,
        type: undefined,
        fileurl: undefined,
        createuserid: undefined,
      },
      queryParams_roles: {
        roleKey: "school",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "文件名称不能为空", trigger: "blur" },
        ],
        type: [
          { required: true, message: "所属类型不能为空", trigger: "blur" },
        ],
        fileurl: [{ required: true, message: "文件不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_filecenter").then((response) => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    getRoles() {
      listRole(this.queryParams_roles).then((response) => {
        this.rolesOptions = response.rows;
      });
    },
    // 适用role--字典状态字典翻译
    rolesFormat(row, column) {
      //alert(row.scope.split(';').length);
      if (row.roles == null) {
        return "";
      }
      var ilength = row.roles.split(";").length;
      var names = "";
      for (var i = 0; i < ilength - 1; i++) {
        names =
          names +
          this.selectRolesLabel(this.rolesOptions, row.roles.split(";")[i]) +
          ";";
      }
      //this.selectDictLabel(this.scopeOptions, row.xnxq);
      return names;
    },
    // 回显数据字典
    selectRolesLabel(datas, value) {
      var actions = [];
      Object.keys(datas).map((key) => {
        if (datas[key].roleId == "" + value) {
          actions.push(datas[key].roleName);
          return false;
        }
      });
      return actions.join("");
    },
    handleAvatarSuccess(res, file) {
      //console.log(res);
      if (res.code == "200") {
        this.form.fileurl = res.fileName;
        this.form.filetype = res.fileName.split(".")[1];
      } else {
        this.msgError(res.msg);
      }
    },
    handleRemove(file, fileList) {
      //console.log(file, fileList);
      if (file.response.code == "200") {
        this.form.fileurl = "";
      }
    },
    handlePreview(file) {
      //console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    // 类型--字典状态字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    /** 查询文件管理列表 */
    getList() {
      this.loading = true;
      listFiles(this.queryParams).then((response) => {
        this.filesList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      this.getRoles();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        filetype: undefined,
        type: undefined,
        fileurl: undefined,
        createuserid: undefined,
        createTime: undefined,
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
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加文件管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getFiles(id).then((response) => {
        console.log(response);
        this.form = response.data;
        var roleArr = [];
        if (response.roles != null) {
          response.roles.forEach((element) => {
            roleArr.push(parseInt(element));
          });
        }
        this.form.roles = roleArr;
        this.open = true;
        this.title = "修改文件管理";
        this.fileList.push({
          name: response.data.name,
          url: response.data.fileurl,
        });
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          var arrscope = this.form.roles;
          this.form.roles = arrscope.join(";") + ";";
          if (this.form.id != undefined) {
            updateFiles(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addFiles(this.form).then((response) => {
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
        '是否确认删除文件管理编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delFiles(ids);
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