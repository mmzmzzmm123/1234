<template>
  <el-upload class="upload-demo"
             ref="upload"
             drag
             :headers="upload.headers"
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
    <i class="el-icon-upload"></i>
    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
    <div class="el-upload__tip" slot="tip">每个案例最多上传5个文件，且每个文件不超过50M</div>
  </el-upload>
</template>
<script>
  import { getToken } from '@/utils/auth'
export default {
  name: "DragUpload",
  components: {

  },
  data() {
    return {
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/custom/customerCase/uploadCaseFile",
        // 设置上传的请求头部
        headers: {Authorization: 'Bearer ' + getToken()},
        // 其他需要携带的数据
        data:{},
        //文件列表
        fileList:[],
        //同时上传文件上限
        limit: 5,
        //每个文件大小
        fileSize: 1024 * 1024 * 50,
        //是否支持同时选择多张
        multiple: true
      },
      uploadResult:{
        fileUrl:[],
        fileName:[]
      }
    };
  },
  methods: {
    uploadFile(){
      if(this.upload.fileList.length > 0 && this.uploadResult.fileUrl.length != this.upload.fileList.length){
        this.$refs.upload.submit();
      }else{
        this.$emit('callbackMethod', this.uploadResult);
      }
    },
    uploadReset(){
      this.upload.fileList = [];
      this.uploadResult["fileUrl"] = [];
      this.uploadResult["fileName"] = [];
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
        this.uploadResult.fileUrl.push(response.fileUrl);
        this.uploadResult.fileName.push(response.fileName);
        if(this.uploadResult.fileUrl.length === this.upload.fileList.length){
          //文件全部上传成功，则调用回调方法
          this.$emit('callbackMethod', this.uploadResult);
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
  },
  created() {

  },

};
</script>

<style scoped>



</style>

