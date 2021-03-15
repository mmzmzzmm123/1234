<template>
<div align="center" >
  <div style="width:400px;margin-top:40px">
<el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
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
          提示：仅允许导入“xls”或“xlsx”格式文件，最大10M
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer" style="margin-top:20px">
        <el-button @click="importTemplate">下载模板</el-button>
        <el-button type="primary" @click="submitFileForm">提交并下载结果</el-button>
      </div>

  </div>

</div>
</template>

<script>
import {importTemplate} from "@/api/custom/salaryRate";
import { getToken } from "@/utils/auth";
export default {
  name: "SalaryRate",
  data() {
    return {
       // 薪资导入参数
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
        url: process.env.VUE_APP_BASE_API + "/custom/salaryRate/importSalary",
      },
    };
  },
  created() {
    this.handleImport();
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "薪资导入";
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
      if(response.code == 200){
        this.download(response.msg);
        this.$refs.upload.clearFiles();
      }else{
        this.msgError(response.msg);
        this.$refs.upload.clearFiles();
      }
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    
  }
};
</script>