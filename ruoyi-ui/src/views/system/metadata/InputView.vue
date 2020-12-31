<template>
  <div id="InputView" :loading="loading" >
    <transition name="el-fade-in-linear">
      <parser v-if="open" :form-conf="formconf" class="transition-box" />
    </transition>
    <FileUpload :upload-text="uploadText">
    </FileUpload>
  </div>
</template>
<script>
import Parser from '@/gene/components/parser/Parser'
import { uploadByPieces } from '@/utils/utils'
import FileUpload from '@/components/commonuploader/FileUploader'
export default {
  props:['formconf'],
  name: "InputView",
  components: {
    Parser,
    FileUpload,
  },
  computed: {
  },
  data() {
    return{
      uploadText:'1231231',
      uploading:false,
      open:false,
      loading:false,
      files:[],
      loadingText:'',
    }
  },
  mounted() {

  },
  created() {
    this.loading=true;
    // console.log(this.formconf)
  },
  methods:{
    handleUploadRequest(back){
      this.files.push(back.file)
    },
    beforeUpload(){
      this.fileList=[]
    },
    // 处理上传文件
    dealUpload () {
      this.uploading = true
      uploadByPieces({
        files: this.files,
        pieceSize: 5,
        chunkUrl: '/system/attributes/chunk',
        fileUrl: '/system/attributes/full',
        progress: (num) => {
          this.loadingText = '上传进度' + num + '%'
        },
        success: (data) => {
          this.uploading = false
          this.$emit('uploaded', data)
        },
        error: (e) => {
          this.uploading = false
        }
      })
    },
    fillFormData(forms, data) {
      forms.forEach(form=>{
        form.fields.forEach(item => {
          const val = data[item.__vModel__]
          if (val) {
            item.__config__.defaultValue = val
          }
        })
      })
    },
  },
  watch:{
    formconf:function(nv,ov) {
      this.open=true
      this.formconf=nv
      this.loading = false
    }
    ,
    files:function(nv,ov){
      this.$nextTick(() => {
        this.dealUpload()
      })
    }
  }

}
</script>

<style scoped>

</style>
