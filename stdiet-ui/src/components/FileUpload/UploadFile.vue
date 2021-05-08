<template>
  <el-upload
  class="avatar-uploader"
  :headers="upload.headers"
  :action="upload.url"
  :show-file-list="false"
  :on-success="handleFileSuccess"
  :accept="'.png,.jpg'"
  :before-upload="beforeAvatarUpload">
  <img v-if="imageUrl || coverUrl" :src="imageUrl || coverUrl" class="avatar">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  <div class="el-upload__tip" slot="tip" style="color:#1890ff">
      <div>1、只能上传png、jpg文件，且每个文件不超过{{
        upload.fileSize / (1024 * 1024)
      }}M</div>
      <div style="margin-top:-10px;">{{tips ? ('2、'+tips) : ''}}</div>
    </div>
</el-upload>
</template>
<script>
import { getToken } from "@/utils/auth";
export default {
  name: "DragUpload",
  components: {},
  data() {
    return {
      upload: {
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/custom/fileUpload/" + this.prefix,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 其他需要携带的数据
        data: {},
        //每个文件大小(单位：byte)
        fileSize: 1024 * 1024 * 10,
      },
      //上传之后的路径
      imageUrl: null,
      fileUrl: null
    };
  },
  methods: {
    resetUpload(){
        this.imageUrl = null;
        this.fileUrl = null
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      if (response != null && response.code === 200) {
        this.fileUrl = response.fileUrl;
        this.imageUrl = response.previewUrl;
        //this.imageUrl = URL.createObjectURL(file.raw);
        //文件全部上传成功，则调用回调方法
        this.$emit("callbackMethod", this.fileUrl);
      } else {
        this.$message.error("文件上传失败");
      }
    },
    // 文件上传失败处理
    handleFileFail(err, file, fileList) {
      this.$message.error("文件上传失败");
    },
    beforeAvatarUpload(file) {
        if(file.type != 'image/jpeg' && file.type != 'image/png') {
          this.$message.error('文件格式错误');
          return false;
        }
        if (file.size > this.upload.fileSize) {
          this.$message.error('文件大小超过最大限制');
          return false;
        }
        return true;
    }
  },
  props: {
    prefix: {
      type: String,
      default: "videoCover",
    },
    coverUrl:{
      type: String,
      default: ""
    },
    tips: {
      type: String,
      default: ""
    }
  }
};
</script>

<style scoped>

.avatar-uploader .el-upload {
    border: 1px dashed #d7e236;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 120px;
    line-height: 120px;
    text-align: center;
  }
  .avatar {
    width: 200px;
    height: 120px;
    display: block;
  }
</style>

