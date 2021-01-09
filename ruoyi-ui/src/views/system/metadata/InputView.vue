<template>
  <div id="InputView" :loading="loading" >
    <transition name="el-fade-in-linear">
      <parser v-if="open" :form-conf="formconf" class="transition-box" @submit="nextPage" />
    </transition>
    <transition v-if="desc" name="el-fade-in-linear">
      <parser v-if="desc" :form-conf="content" class="transition-box" @submit="nextPage1" />
    </transition>
    <transition v-if="showUpload" name="el-fade-in-linear">
      <el-row>
        <el-col :span="4">
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
        </el-col>
        <el-col :span="20">
          <document-view :metadata-id="metadataId"></document-view>
        </el-col>
      </el-row>
    </transition>
  </div>
</template>
<script>
import Parser from '@/gene/components/parser/Parser'
import {listJson} from '@/api/system/json'
import { uploadByPieces } from '@/utils/utils'
import FileUpload from '@/components/commonuploader/FileUploader'
import DocumentView from '@/views/components/documentView'
export default {
  props:{
    formconf: {
      type: Object,
    },
  },
  name: "InputView",
  components: {
    DocumentView,
    Parser,
    FileUpload,
  },
  computed: {
  },
  data() {
    return{
      metadataId:'',
      desc:false,
      uploadText:'点击或拖入文件上传',
      uploading:false,
      open:false,
      loading:false,
      files:[],
      fileList:[],
      loadingText:'',
      content:{},
      showUpload:true,
      param:{},
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
    },
    nextPage1(){
      this.open=false
      this.desc=false
      this.showUpload=true
    }
  },
  watch:{
    formconf:function(nv,ov) {
      //this.open=true
      this.formconf=nv
      this.loading = false
    }
  }

}
</script>

<style scoped>

</style>
