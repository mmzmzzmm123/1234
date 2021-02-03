<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="账号名称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入账号名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账号id" prop="accountId">
        <el-select
          v-model="queryParams.accountId"
          placeholder="请选择账号id"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in accountIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['custom:WxAccount:add']"
          >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:WxAccount:edit']"
          >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:WxAccount:remove']"
          >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:WxAccount:export']"
          >导出
        </el-button>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="WxAccountList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="手机号" align="center" prop="id" />
      <el-table-column label="账号名称" align="center" prop="nickName" />
      <el-table-column
        label="账号id"
        align="center"
        prop="accountId"
        :formatter="accountIdFormat"
      />
      <el-table-column label="微信号" align="center" prop="wxId" />
      <el-table-column label="手机号" align="center" prop="phone" />
      <el-table-column label="手机号" align="center" prop="remark" />
      <el-table-column label="二维码图片" align="center" prop="imgUrl" />
      <el-table-column label="使用次数" align="center" prop="count" />
      <el-table-column label="微信资源id" align="center" prop="mediaId" />
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
            v-hasPermi="['custom:WxAccount:edit']"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:WxAccount:remove']"
            >删除
          </el-button>
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

    <!-- 添加或修改微信销售账号对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      :close-on-click-modal="false"
      width="500px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="账号昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入账号名称" />
        </el-form-item>
        <el-form-item label="账号id" prop="accountId">
          <el-select v-model="form.accountId" placeholder="请选择账号id">
            <el-option
              v-for="dict in accountIdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="微信号" prop="wxId">
          <el-input v-model="form.wxId" placeholder="请输入微信号" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="二维码图片" prop="imgUrl">
          <el-upload
            :class="`upload-demo ${
              upload.fileList.length ? 'has-file' : ''
            }`"
            ref="upload"
            drag
            :action="upload.url"
            :limit="upload.limit"
            :headers="upload.headers"
            :disabled="upload.isUploading"
            :file-list="upload.fileList"
            :multiple="upload.multiple"
            :on-change="handleFileChange"
            :on-remove="handleFileRemove"
            :on-exceed="handleFileexceed"
            :on-progress="handleFileUploadProgress"
            :on-success="handleFileSuccess"
            :on-error="handleFileFail"
            :data="upload.data"
            :auto-upload="true"
            list-type="picture"
          >
            <i class="el-icon-upload" />
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <div class="el-upload__tip" slot="tip">
              只能上传jpg/png文件，且不超过500kb
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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
  addWxAccount,
  delWxAccount,
  exportWxAccount,
  getWxAccount,
  listWxAccount,
  updateWxAccount,
} from "@/api/custom/WxAccount";

import { getToken } from "@/utils/auth";

export default {
  name: "WxAccount",
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
      // 微信销售账号表格数据
      WxAccountList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 账号id字典
      accountIdOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        nickName: null,
        accountId: null,
        phone: null,
      },
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/custom/WxAccount/upload",
        // 设置上传的请求头部
        headers: {
          Authorization: "Bearer " + getToken(),
        },
        // 其他需要携带的数据
        data: {},
        //文件列表
        fileList: [],
        //同时上传文件上限
        limit: 1,
        //每个文件大小
        fileSize: 1024 * 500,
        //是否支持同时选择多张
        multiple: false,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getDicts("cus_account").then((response) => {
      this.accountIdOptions = response.data;
    });
  },
  methods: {
    /** 查询微信销售账号列表 */
    getList() {
      this.loading = true;
      listWxAccount(this.queryParams).then((response) => {
        this.WxAccountList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 账号id字典翻译
    accountIdFormat(row, column) {
      return this.selectDictLabel(this.accountIdOptions, row.accountId);
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
        nickName: null,
        accountId: null,
        wxId: null,
        phone: null,
        remark: null,
        imgUrl: null,
        count: null,
        mediaId: null,
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
      this.title = "添加微信销售账号";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWxAccount(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改微信销售账号";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateWxAccount(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addWxAccount(this.form).then((response) => {
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
        '是否确认删除微信销售账号编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delWxAccount(ids);
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
      this.$confirm("是否确认导出所有微信销售账号数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportWxAccount(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    //监控上传文件列表
    handleFileChange(file, fileList) {
      let sizeFlag = file.size > this.upload.fileSize;
      if (sizeFlag) {
        this.$message({
          message: "当前文件过大",
          type: "warning",
        });
        fileList.pop();
      }
      this.upload.fileList = fileList;
    },
    handleFileRemove(file, fileList) {
      this.upload.fileList = fileList;
    },
    // 文件数量超过限度
    handleFileexceed(file, fileList) {
      //console.log(this.upload.fileList.length);
      this.$message({
        message: "最多可上传" + this.upload.limit + "份文件",
        type: "warning",
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      if (response != null && response.code === 200) {
        this.form.imgUrl = response.fileName;
        console.log(response);
      } else {
        this.fail();
        this.$message.error(response.msg);
      }
    },
    // 文件上传失败处理
    handleFileFail(err, file, fileList) {
      if (err) {
        this.$message.error("文件上传失败，请检查文件格式");
        this.fail();
      }
    },
    fail() {
      this.submitFlag = false;
      this.upload.isUploading = false;
    },
  },
};
</script>

<style>
.has-file .el-upload--picture {
  display: none;
}

.has-file .el-upload__tip {
  display: none;
}

</style>
