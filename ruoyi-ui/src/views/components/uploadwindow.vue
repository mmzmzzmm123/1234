<template>
  <div>
    <el-upload
      class="upload-demo"
      ref="upload"
      action=""
      :on-remove="handleRemove"
      :on-change="handleChange"
      :http-request="handleUploadRequest"
      :file-list="fileList"
      :auto-upload=true
      multiple
    >
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
      <div slot="tip" class="el-upload__tip">不超过500mb,上传已包含数据会上传不成功</div>
    </el-upload>
  </div>
</template>

<script>
import { uploadByPieces } from '@/utils/utils'


export default {
  name: "uploadwindow",
  props:['metadataId'],
  data(){
    return{
      fileList:[],
    }
  },
  methods:{
    handleRemove(file, fileList){
      this.fileList=fileList
    },
    handleChange(file, fileList) {
    },
    handleUploadRequest(back){
      //console.log(back)
      if(this.fileList.indexOf(back.file)!==-1){
        $.message.warning('已含有相同的文件在列表中')
      }
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
          this.$message.success('文件上传成功!')
          //文件列表添加
          this.$emit('addFile',this.fileList)
          //当前上传列表置空
          this.fileList = []
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
