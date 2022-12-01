<template>
  <el-upload
    class="avatar-uploader"
    :action="uploadVideoUrl"
    :on-progress="uploadVideoProcess"
    :on-success="handleVideoSuccess"
    :before-upload="beforeUploadVideo"
    :show-file-list="false"
    :headers="headers"
  >
    <video
      v-if="videoForm.showVideoPath !== '' && !videoFlag"
      v-bind:src="videoForm.showVideoPath"
      class="avatar video-avatar"
      controls="controls"
    >
      您的浏览器不支持视频播放
    </video>
    <i
      v-else-if="videoForm.showVideoPath === '' && !videoFlag"
      class="el-icon-plus avatar-uploader-icon"
    ></i>
    <el-progress
      v-if="videoFlag === true"
      type="circle"
      v-bind:percentage="videoUploadPercent"
      style="margin-top: 7px"
    ></el-progress>
  </el-upload>
</template>
<script>
import { getToken } from "@/utils/auth";
import {Message} from "element-ui";

export default {
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadVideoUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      videoFlag: false,
      //是否显示进度条
      videoUploadPercent: "",
      //进度条的进度，
      isShowUploadVideo: false,
      //显示上传按钮
      videoForm: {
        showVideoPath: "", //回显的变量
      },
    };
  },
  methods: {
    //上传前回调
    beforeUploadVideo(file) {
      var fileSize = file.size / 1024 / 1024 < 1024; //控制大小  修改50的值即可
      if (
        [
          "video/mp4",
          "video/ogg",
          "video/flv",
          "video/avi",
          "video/wmv",
          "video/rmvb",
          "video/mov",
        ].indexOf(file.type) === -1 //控制格式
      ) {
        Message.error("请上传正确的视频格式");
        return false;
      }
      if (!fileSize) {
        Message.error("视频大小不能超过1024M(1G)");
        return false;
      }
      this.isShowUploadVideo = false;
    },
    //进度条
    uploadVideoProcess(event, file, fileList) {
      //注意在data中添加对应的变量名
      this.videoFlag = true;
      this.videoUploadPercent = file.percentage.toFixed(0) * 1;
    },
    //上传成功回调
    handleVideoSuccess(res, file) {
      this.isShowUploadVideo = true;
      this.videoFlag = false;
      this.videoUploadPercent = 0;
      console.log(res);
      this.videoForm.showVideoPath = res.url;
      this.$emit("input", res.url);
    },
  },
};
</script>
<style scoped>
.avatar-uploader-icon {
  border: 1px dashed #d9d9d9 !important;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9 !important;
  border-radius: 6px !important;
  position: relative !important;
  overflow: hidden !important;
}
.avatar-uploader .el-upload:hover {
  border: 1px dashed #d9d9d9 !important;
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 300px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 300px;
  height: 178px;
  display: block;
}
</style>

