<template>
  <div>
    <el-upload
      class="upload-demo"
      ref="upload"
      action=""
      :on-change="handleChange"
      :http-request="handleUploadRequest"
      :file-list="fileList"
      :auto-upload=true
    >
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
      <div slot="tip" class="el-upload__tip">不超过500mb</div>
    </el-upload>
  </div>
</template>

<script>
import { uploadByPieces } from '@/utils/utils'

export default {
  name: "uploadwindow",
  data(){
    return{
      fileList:[],
    }
  },
  methods:{
    handleChange(file, fileList) {
      // this.fileList.push(file)

    },
    handleUploadRequest(back){
      console.log(back)
      this.fileList.push(back.file)
      this.param = back
    },
    submitUpload() {
      //this.$refs.upload.submit();
      this.dealUpload(this.param)
    },
    // 处理上传文件
    dealUpload (param) {
      uploadByPieces({
        metadataId:this.metadataId,
        files: this.fileList,
        pieceSize: 5,
        chunkUrl: '/system/attributes/chunk',
        fileUrl: '/system/attributes/full',
        progress: (num) => {
          param.file.percent = num
          param.onProgress(param.file)
        },
        success: (data) => {
          this.uploading = false
          this.$emit('uploaded', data)
          //this.fileList = []
        },
        error: (e) => {
          this.uploading = false
        }
      })
    },
  }
}
</script>

<style scoped>

</style>
