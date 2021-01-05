<template>
  <div v-loading='uploading'
       :element-loading-text='loadingText'
       element-loading-spinner="el-icon-loading">
    <el-upload
      multiple
      class="upload-block"
      drag action=""
      :before-upload="beforeExcelUpload"
      :http-request="handleUploadRequest"
      :show-file-list='true'
    >
      <div class="el-upload__text">
        {{uploadText}}
      </div>
    </el-upload>
  </div>
</template>

<script>
import {uploadByPieces} from '@/utils/utils'
export default {
  name: "FileUploader",
  props: ['uploadText'],
  data () {
    return {
      uploading: false,
      loadingText: '',
      fileList: []
    }
  },
  computed: {
  },
  watch: {
    fileList (fileList) {
      this.$nextTick(() => {
        this.dealUpload()
      })
    }
  },
  methods: {
    // 上传前
    beforeExcelUpload () {
      this.fileList = []
      //console.log('this.fileList', this.fileList)
    },
    // 上传请求
    handleUploadRequest (back) {
      if (this.fileList.indexOf(back.file)===-1){
        this.fileList.push(back.file)
      }
      else {
        this.$message.error(back.file.name + '已存在于上传列表中!')
      }
    },
    // 处理上传文件
    dealUpload () {
      this.uploading = true
      uploadByPieces({
        files: this.fileList,
        pieceSize: 5,
        chunkUrl: '/system/attributes/chunk',
        fileUrl: '/system/attributes/full',
        progress: (num) => {
          this.loadingText = '上传进度' + num + '%'
        },
        success: (data) => {
          this.fileList=[]
          this.uploading = false
          this.$emit('uploaded', data)
        },
        error: (e) => {
          this.uploading = false
        }
      })
    }
  },
  created () {
  }
}
</script>

<style lang="less">
.upload-block {
  text-align: center;
  line-height: 80px;
  height: 80px;
  .el-upload {
    width: 100%;
    height: 100%;
    .el-upload-dragger {
      width: 100%;
      height: 100%;
      .el-upload__text {
        position: relative;
        font-size: 1.2em;
        color: #409EFF;
        i {
          font-size: 1.5em;
          margin-right: 10px;
        }
      }
    }
  }
}
</style>
