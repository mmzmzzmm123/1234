<template>
  <div id="InputView" :loading="loading" style="min-height:75vh;">
    <transition name="el-fade-in-linear">
      <parser v-if="currentPage===1" :form-conf="formconf" class="transition-box" @submit="nextPage" @cancel="returnLastPage" @close="close"/>
    </transition>
    <transition v-if="currentPage===2" name="el-fade-in-linear">
      <parser v-if="currentPage===2" :form-conf="content" class="transition-box" @submit="nextPage1" @cancel="returnLastPage" @close="close"/>
    </transition>
    <transition v-if="currentPage===3" name="el-fade-in-linear">
      <div>
        <el-button @click="dialogVisible=true">上传文件</el-button>
        <div style="text-align: center">
          <el-dialog title="上传文件" :visible.sync="dialogVisible" width="30%" :before-close="beforeClose" style="z-index: 2999">
            <uploadwindow></uploadwindow>
          </el-dialog>
          <document-view :metadata-id="metadataId" ></document-view>
          <el-button-group>
            <el-button @click="returnLastPage">返回</el-button>
            <el-button @click="save">保存</el-button>
            <el-button @click="close">关闭</el-button>
          </el-button-group>
        </div>  
      </div>
    </transition>
  </div>
</template>
<script>
import Parser from '@/gene/components/parser/Parser'
import {listJson} from '@/api/system/json'
import DocumentView from '@/views/components/documentView'
import Uploadwindow from '@/views/components/uploadwindow'
export default {
  props:{
    formconf: {
      type: Object,
    },
  },
  name: "InputView",
  components: {
    Uploadwindow,
    DocumentView,
    Parser,
  },
  computed: {
  },
  data() {
    return{
      currentPage:3,
      dialogVisible:false,
      metadataId:1,
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
      formData:{

      }
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
    // handleChange(file, fileList) {
    //   // this.fileList.push(file)
    //
    // },
    // handleUploadRequest(back){
    //   console.log(back)
    //   this.fileList.push(back.file)
    //   this.param = back
    // },
    // submitUpload() {
    //   //this.$refs.upload.submit();
    //   this.dealUpload(this.param)
    // },
    // // 处理上传文件
    // dealUpload (param) {
    //   uploadByPieces({
    //     metadataId:this.metadataId,
    //     files: this.fileList,
    //     pieceSize: 5,
    //     chunkUrl: '/system/attributes/chunk',
    //     fileUrl: '/system/attributes/full',
    //     progress: (num) => {
    //       param.file.percent = num
    //       param.onProgress(param.file)
    //     },
    //     success: (data) => {
    //       this.uploading = false
    //       this.$emit('uploaded', data)
    //       //this.fileList = []
    //     },
    //     error: (e) => {
    //       this.uploading = false
    //     }
    //   })
    // },
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
    },
    firstCancel(){
      this.open=false
      this.desc=false
    },
    secondCancel(){
      this.open=true
      this.desc=false
    },
    beforeClose(done){
      console.log('1123')
      done()
    },
    returnLastPage(){
      if (this.currentPage!==1){
        this.currentPage+=-1
      }
    },
    save(){},
    close(){
      this.$emit('close')
    },
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
