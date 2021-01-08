<template>
  <div id="InputView" :loading="loading" >
    <transition name="el-fade-in-linear">
      <parser v-if="open" :form-conf="formconf" class="transition-box" @submit="nextPage" />
    </transition>
    <transition v-if="desc" name="el-fade-in-linear">
      <parser v-if="desc" :form-conf="content" class="transition-box" @submit="nextPage" />
    </transition>
<!--    <FileUpload :upload-text="uploadText">-->
<!--    </FileUpload>-->
  </div>
</template>
<script>
import Parser from '@/gene/components/parser/Parser'
import {listJson} from '@/api/system/json'
import { uploadByPieces } from '@/utils/utils'
import FileUpload from '@/components/commonuploader/FileUploader'
export default {
  props:{
    formconf: {
      type: Object,
    },
  },
  name: "InputView",
  components: {
    Parser,
    FileUpload,
  },
  computed: {
  },
  data() {
    return{
      desc:false,
      uploadText:'点击或拖入文件上传',
      uploading:false,
      open:false,
      loading:false,
      files:[],
      loadingText:'',
      content:{},
    }
  },
  mounted() {

  },
  created() {
    this.loading=true;
    let query = {id:'',parentName: '文书',node:'内容描述'}
    listJson(query).then(res => {
      this.content=JSON.parse(res.rows[0].formData)
    }).catch(err=>{
      console.log(err)
    })
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
    nextPage(){

      this.open=false
      this.desc=true
    }
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
