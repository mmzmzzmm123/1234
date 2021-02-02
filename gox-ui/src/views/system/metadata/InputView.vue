<template>
  <div id="InputView" :loading="loading" style="min-height:75vh;">
    <transition name="el-fade-in-linear">
      <parser v-if="currentPage===1" :form-conf="firstForm" class="transition-box" @submit="nextPage" @cancel="returnLastPage" @close="close"/>
    </transition>
    <transition v-if="currentPage===2" name="el-fade-in-linear">
      <parser v-if="currentPage===2" :form-conf="content" class="transition-box" @submit="nextPage" @cancel="returnLastPage" @close="close"/>
    </transition>
    <transition v-if="currentPage===3" name="el-fade-in-linear">
      <div>
        <el-button-group>
          <el-button @click="dialogVisible=true">上传文件</el-button>
          <el-button @click="deleteEle">删除</el-button>
          <el-button @click="print">打印</el-button>
        </el-button-group>
        <div style="text-align: center">
          <el-dialog title="上传文件" v-if="dialogVisible" :visible.sync="dialogVisible" width="30%" :before-close="beforeClose" style="z-index: 2999">
<!--            <uploadwindow :metadata-id="mdId" @addFile="addFile"></uploadwindow>-->
            <uploads :url="url"></uploads>
          </el-dialog>
          <document-view  :metadata-id="mdId" ref="dv"></document-view>
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
import { addMetadata, getMetadata, updateMetadata } from '@/api/system/metadata'
import DocumentView from '@/views/components/documentView'
import md from '@/views/system/metadata/md'
import Uploads from '@/views/components/Uploads'
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
    },
    metadata:{
      type:Object,
    },
    deptid:{
      type:Number,
    }
  },
  name: "InputView",
  components: {
    DocumentView,
    Parser,
    Uploads,
    md
  },
  computed: {
    content:{
      get:function(){
        if(this.type==='create'){
          return this.createContent
        }
        else{
          if(JSON.stringify(this.modifyContent) !== '{}'){
            setTimeout(() => {
              return this.modifyContent
            },1000)
            return this.createContent
          }
          return this.modifyContent
        }
      },
      set:function(newValue){
        this.modifyContent=newValue
      }
    },
    firstForm:{
      get:function(){
        if(this.type==='create'){
          if(JSON.stringify(this.newForm) !== '{}'){
            return this.newForm
          }
          this.newForm=this.formconf
          return this.formconf
        }
        else{
          if(JSON.stringify(this.modifyForm) !== '{}'){
            return this.modifyForm
          }
          this.modifyForm = this.formconf
          return this.modifyForm
        }
      },
      set:function(newValue){
        this.modifyForm=newValue
      }
    }
  },
  data() {
    return{
      newForm:{},
      modifyForm:{},
      mdId:1,
      url:process.env.VUE_APP_BASE_API +'/system/attributes/fileUpload?metadataId='+this.metadataId,
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
      createContent:{},
      modifyContent:{},
      showUpload:true,
      param:{},
      formdata1:{},
      formdata2:{},
      formData:{},
      formdataC:{},
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
        this.createContent=JSON.parse(res.rows[0].formData)
        this.modifyContent= this.createContent
      }).catch(err=>{
        console.log(err)
      })
      if(this.type==='create'){
        getId().then(res=>{
          this.mdId = res.data
        })
        this.formData={}
        this.formDataC={}
      }
      else if(this.type==='modify'){
        // getMetadata(this.metadataid).then(response => {
        //   this.formData = response.data;
        //   this.fillFormData(this.modifyForm,response.data)
        // });
        this.formData=this.metadata
        this.formDataC=this.formData
        this.fillFormData(this.modifyForm,this.metadata)
        this.mdId=this.metadataid
      }
    },
    fillFormData(form, data) {
      form.fields.forEach(item => {
        const val = data[item.__vModel__]
        if (val) {
          if(typeof val==='string'&&val.indexOf('——')>-1){
            return
          }
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
        this.formData=Object.assign(this.formData,this.formdata2,{id:this.mdId},{deptId:this.deptid})
        let obj=this.formData;
        if (obj!==this.formDataC){
          for(let key  in obj){
            if(typeof obj[key]==='object'&&obj[key]!==null&& obj[key].constructor===Array){
              if(obj[key].length===0){
                obj[key]=''
              }
              else if(obj[key].length===1){
                obj[key]=obj[key][0]
              }
              else{
                obj[key]=obj[key].join(',')
              }
            }
          }
          if(this.type==='create'){
            addMetadata(obj).then(()=>{
              console.log("创建 文书档案")
            })
          }else if(this.type==='modify'){
            updateMetadata(obj).then(()=>{
              console.log("修改文书档案")
            })
          }
        }
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
      this.$refs.dv.getEleTree()
      done()
    },
    returnLastPage(){
      if (this.currentPage!==1){
        this.currentPage+=-1
      }
    },
    save(){},
    //上传成功 添加文件到文件树
    addFile(file){
      this.$refs.dv.addFile(file)
    },
    close(){
      this.$emit('close')
    },
    deleteEle(){
      this.$refs.dv.remove()
    },
    print(){
      this.$refs.dv.printEle()
    },
  },
  watch:{
    formconf:function(nv,ov) {
      //this.open=true
      this.newForm=nv
      this.modifyForm=nv
      this.loading = false
    }
    ,currentPage:function(nv,ov){
      if(nv===1){
        this.fillFormData(this.firstForm,this.formData)
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
