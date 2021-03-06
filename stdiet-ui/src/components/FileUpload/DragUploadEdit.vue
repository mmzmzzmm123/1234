<template>
  <div>
    <div v-for="(item, index) in oldCaseFileList">
      <span style="margin-right: 10px;">
        {{item.fileName ? (item.fileName.substring(0,15)+"..."): ""}}
      </span>
      <el-button style="margin-left: 10px;" type="danger" size="small" @click="removeOldFile(index)">移除该文件</el-button>
    </div>
    <el-upload class="upload-demo" style="margin-top: 10px;"
               ref="upload"
               drag
               :headers="upload.headers"
               :action="upload.url"
               :limit="upload.limit"
               :disabled="upload.isUploading"
               :file-list="upload.fileList"
               :multiple="upload.multiple"
               :on-remove="handleFileRemove"
               :on-change="handleFileChange"
               :on-exceed="handleFileexceed"
               :on-progress="handleFileUploadProgress"
               :on-success="handleFileSuccess"
               :on-error="handleFileFail"
               :data="upload.data"
               :auto-upload="false">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">已存在{{oldCaseFileList.length}}个文件，还可上传{{upload.limit}}个文件，且每个文件不超过{{upload.fileSize/(1024*1024)}}M</div>
    </el-upload>
  </div>
</template>
<script>
  import { getToken } from '@/utils/auth'
  import AutoHideMessage from "@/components/AutoHideMessage";
export default {
  name: "DragUploadEdit",
  components: {
      "AutoHideMessage":AutoHideMessage
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
        limit: 10 - this.caseFileList.length,
        //每个文件大小(单位：byte)
        fileSize: 1024 * 1024 * 10,
        //是否支持同时选择多张
        multiple: true
      },
      oldCaseFileList: this.caseFileList,
      uploadResult:{
        fileUrl:[],
        fileName:[]
      }
    };
  },
  methods: {
    uploadFile(){
      if(this.upload.fileList.length > 0 && this.uploadResult.fileUrl.length < this.upload.fileList.length){
        this.$refs.upload.submit();
      }else{
        //合并旧文件
        if(this.oldCaseFileList != null && this.oldCaseFileList.length > 0){
          this.oldCaseFileList.forEach((item, index) => {
            this.uploadResult.fileUrl.unshift(item.fileUrl);
            this.uploadResult.fileName.unshift(item.fileName);
          });
        }
        this.$emit('callbackMethod', this.uploadResult);
      }
    },
    removeOldFile(index){
      this.oldCaseFileList.splice(index,1);
      this.upload.limit = 10 - this.oldCaseFileList.length;
    },
    uploadReset(){
      this.upload.fileList = [];
      this.uploadResult["fileUrl"] = [];
      this.uploadResult["fileName"] = [];
      this.oldCaseFileList = [];
    },
    //移除文件
    handleFileRemove(file, fileList){
      this.upload.fileList = fileList;
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
          if(this.oldCaseFileList != null && this.oldCaseFileList.length > 0){
            this.oldCaseFileList.forEach((item, index) => {
              this.uploadResult.fileUrl.unshift(item.fileUrl);
              this.uploadResult.fileName.unshift(item.fileName);
            });
          }
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
    caseFileList:{
      type: Array,
      default: function () {
        return [];
      }
    }
  },
  created() {

  },

};
</script>

<style scoped>



</style>

