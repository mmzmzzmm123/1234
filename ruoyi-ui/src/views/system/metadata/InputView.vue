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
          <document-view :metadata-id="mdId" ></document-view>
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
import {listJson,getId} from '@/api/system/json'
import { addMetadata, getMetadata } from '@/api/system/metadata'
import DocumentView from '@/views/components/documentView'
import Uploadwindow from '@/views/components/uploadwindow'
export default {
  props:{
    formconf: {
      type: Object,
    },
    type:{
      type: String,
    }
    ,metadataid:{
      type:Number
    }
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
      mdId:1,
      currentPage:1,
      dialogVisible:false,
      //metadataId:1,
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
      formdata1:{},
      formdata2:{},
      formData:{

      }
    }
  },
  mounted() {
    this.init();
  },
  created() {

  },
  methods:{
    init(){
      this.loading=true;
      let query = {id:'',parentName: '文书',node:'内容描述'}
      listJson(query).then(res => {
        this.content=JSON.parse(res.rows[0].formData)
      }).catch(err=>{
        console.log(err)
      })
      if(this.type==='create'){
        getId().then(res=>{
          this.mdId = res.data
        })
        this.formData={}
      }
      else if(this.type==='modify'){
        getMetadata(this.metadataid).then(response => {
          this.formData = response.data;
          console.log(this.formData)
          this.fillFormData(this.formconf,response.data)
        });
        this.mdId=this.metadataid
      }
    },
    fillFormData(form, data) {
      form.fields.forEach(item => {
        const val = data[item.__vModel__]
        if (val) {
          item.__config__.defaultValue = val
        }
      })
    },
    nextPage(data){
      if (this.currentPage===1){
        this.formdata1=data
        this.formData=Object.assign(this.formData,this.formdata1)
      }
      if (this.currentPage===2){
        this.formdata2=data
        this.formData=Object.assign(this.formData,this.formdata2,{id:this.mdId})
        let obj=this.formData;
        for(let key  in obj){
          if(typeof obj[key]==='object'&& obj[key].constructor===Array){
            if(obj[key].length===0){
              obj[key]=''
            }
            else if(obj[key].length===1){
              obj[key]=obj[key][0]
            }
            else{
              //console.log(key,obj[key])
              obj[key]=obj[key].join(',')
            }
          }
        }
        addMetadata(obj).then()
      }
      if (this.currentPage!==3){
        this.currentPage+=1
      }
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
    ,currentPage:function(nv,ov){
      if(nv===1){
        this.fillFormData(this.formconf,this.formData)
      }
      else if (nv===2){
        this.fillFormData(this.content,this.formData)
      }
    },
    metadataid:function(nv,ov){
      this.metadataid=nv
    },
  }

}
</script>

<style scoped>

</style>
