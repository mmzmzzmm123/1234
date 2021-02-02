<template>
  <div>
    <p class="p_title_1">{{healthyData['titleArray'][8]}}</p>
    <p class="p_title_2">1、体检报告</p>
    <el-form-item label="(1) 请上传相应的体检报告" prop="fileList" class="margin-left">
      <el-upload style="margin-left: 20px;"
                 class="upload-demo"
                 ref="upload"
                 :action="upload.url"
                 :limit="upload.limit"
                 :disabled="upload.isUploading"
                 :file-list="upload.fileList"
                 :multiple="upload.multiple"
                 :on-change="handleFileChange"
                 :on-exceed="handleFileexceed"
                 :on-progress="handleFileUploadProgress"
                 :on-success="handleFileSuccess"
                 :on-error="handleFileFail"
                 :data="upload.data"
                 :auto-upload="false">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;" size="small" @click="removeFileList()">移除文件</el-button>
        <div slot="tip" class="el-upload__tip">1、最多可上传三份，且每份文件不超过20M
          <br>2、仅支持bmp，gif, jpg, jpeg, png, doc, docx, xls, xlsx, ppt, pptx, html, htm, txt, rar, zip, gz, bz2, pdf格式文件
        </div>
      </el-upload>
    </el-form-item>
  </div>
</template>
<script>
import * as healthyData from "@/utils/healthyData";

export default {
  name: "Form9",
  components: {

  },
  data() {
    return {
      healthyData:healthyData,
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/common/customerUploadFile",
        // 设置上传的请求头部
        headers: {},
        // 其他需要携带的数据
        data:{},
        //文件列表
        fileList:[],
        //同时上传文件上限
        limit: 3,
        //每个文件大小
        fileSize: 1024 * 1024 * 20,
        //是否支持同时选择多张
        multiple: true
      },
    };
  },
  methods: {
    uploadFile(){
      if(this.upload.fileList.length > 0 && this.form.medicalReport.length != this.upload.fileList.length){
        this.$refs.upload.submit();
      }else{
        this.$emit('addOrEditHealthy');
      }
    },
    removeFileList(){
      this.upload.fileList = [];
      this.form.medicalReport = [];
      this.form.medicalReportName = [];
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
    // 文件数量超过限度
    handleFileexceed(file, fileList){
      //console.log(this.upload.fileList.length);
      this.$message({
        message: "最多可上传"+ this.upload.limit +"份文件",
        type: "warning",
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      //this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      if(response != null && response.code === 200){
        this.form.medicalReport.push(response.fileName);
        this.form.medicalReportName.push(file.name);
        if(this.form.medicalReport.length === this.upload.fileList.length){
          //文件全部上传成功，则调用添加客户信息方法
          this.$emit('addOrEditHealthy');
        }
      }else{
        this.upload.fileList = fileList.pop();
        this.$message.error('文件上传失败，请检查文件格式');
      }
    },
    // 文件上传失败处理
    handleFileFail(err, file, fileList){
      this.$message.error('文件上传失败，请检查文件格式');
      this.upload.fileList = fileList.pop();
    }
  },
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  created() {

  },

};
</script>

<style scoped>

  .el-form-item {
    margin-bottom: 8px;
  }

  .p_title_1{
    font-size: 18px;
    font-weight: bold;
    margin-top: 30px;
  }
  .p_title_2{
    font-size: 16px;
    font-weight: bold;
    margin-top: 30px;
  }
  .p_title_3{
    font-size: 14px;
    font-weight: bold;
    margin-top: 30px;
  }
  .margin-left{
    margin-left: 14px;
  }
  .el-input__inner{
    width: 30%;
  }
  .margin-top-10{
    margin-top: 10px;
  }
  .width-50-left-8-right-5{
    width:50%;
    margin-left: 8px;
    margin-right: 5px;
  }
  .width-70-left-8-right-5{
    width:70%;
    margin-left: 8px;
    margin-right: 5px;
  }


</style>

