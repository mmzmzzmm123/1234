<template>
  <div id = "document">
    <el-row :gutter="20">
      <el-col :span="4">
        <div class="head-container">
          <el-input
            v-model="eleName"
            placeholder="请输入电子文件名"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="elTree"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            show-checkbox
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="20">
        <div v-if="showPDF" style="border: 1px solid #000000">
          <iframe :src="`/web/viewer.html?file=${encodeURIComponent(url)}`" width="100%" height="700px"></iframe>
        </div>
        <div  v-else-if="showPic" style="border: 1px solid #000;vertical-align:middle;background-color: #5a5e66;text-align:center;min-height:700px;overflow:auto;">
          <img id = "result" alt="图片" style="object-fit:scale-down;vertical-align:middle;margin-top: 45px">
          <br>
          <el-button-group style="position:absolute; bottom:20px;margin-left:-115px">
            <el-button @click="rotate(90)"> 左旋</el-button>
            <el-button @click="rotate(-90)">右旋</el-button>
            <el-button @click="recover">恢复</el-button>
            <el-button @click="resize(0.1)">放大</el-button>
            <el-button @click="resize(-0.1)">缩小</el-button>
<!--            <el-button ></el-button>-->
          </el-button-group>
        </div>
        <div v-else style="border: 1px solid #000000;min-height:75vh;width:80%" >
          <div style="text-align:center">
            <span style="color:#00afff;font-size:30px">
              暂无文件显示
            </span>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {listAttributes} from '@/api/system/attributes'
const { Image } = require('image-js');
export default {
  name: "documentView",
  props:{
    metadataId: {
      type: Number,
    },
  },
  data() {
    return{
      a:'',
      width:500,
      height:0,
      srcList: [],
      showPic:false,
      url: '',
      showPDF:false,
      eleName:'',
      elTree:[],
      id:'',
      base64:'',
      fileType:'',
      isFull: false,
      defaultProps:{
        label:'label',
        children:'children',
        id:'id'
      },
      query:{
        metadataId:this.metadataId,
        id:undefined
      },
    }
  },
  watch:{

  },
  created() {
    this.getEleTree()
  },
  methods:{
    recover(){
      let url = this.a;
      async function process(){
        let image = await Image.load(url);
        let url1 = image.toDataURL();
        document.getElementById('result').src = url1;
        return {url1}
      }
      process().then(res=>{
        this.url = res.url1;
      })
    },
    rotate(angle){
      let url = this.url;
      async function process(){
        let image = await Image.load(url);
        let rotate = image.rotate(angle);
        let url1 = rotate.toDataURL();
        document.getElementById('result').src = url1;
        return {url1}
      }
      process().then(res=>{
        this.url = res.url1;
      })
    },
    resize(factor){
      let url = this.url;
      async function process(){
        let image = await Image.load(url);
        let rotate = image.resize({factor:1*(1+factor)});
        let url1 = rotate.toDataURL();
        document.getElementById('result').src = url1;
        return {url1}
      }
      process().then(res=>{
        this.url = res.url1;
      })
    },
    createPicBox(){
      let url = this.url
      async function process() {
        let image = await Image.load(url);
        let grey = image.resize({width:500});
        let width =grey.width;
        let height = grey.height;
        let url1 = grey.toDataURL();
        document.getElementById('result').src = url1;
        return {width,height,url1}
      }
      let ss = process()
      ss.then((res) =>{
        this.width=res.width
        this.height=res.height
        this.url= res.url1
        this.a = res.url1
      })
    },
    getEleTree(){
      let that = this;
      listAttributes(this.query).then(res=>{
        let data= res.rows;
        data.forEach(item=>{
          this.elTree.push({id:item.id,label: item.computerFileName,children:[]})
        })
        if (this.elTree.length>0){
          this.id = this.elTree[0].id
          this.url=process.env.VUE_APP_BASE_API+'/ele/'+this.id
          let type = that.getType(this.elTree[0].label);
          if(type){
            if(type==='pdf'||type==='PDF'){
              that.showPDF=true;
              that.thisPic=false;
              this.createPicBox();
            }
            else if (type==='jpg'||type==='png'||type==='jpeg'||type==='tiff'){
              that.showPDF=false;
              that.thisPic=true;
            }
            else {
              that.showPDF=false;
              that.thisPic=false;
            }
          }

          //this.url='https://www.lactame.com/github/image-js/image-js/3073b80c7d626196cb669f9d617f491a8338ca66/test/img/taxi/original.jpeg'

        }
      })
    },
    getType(label){
      let startIndex = label.indexOf(".");
      if(startIndex !== -1)
        return label.substring(startIndex+1, label.length).toLowerCase();
      else return "";
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.id= data.id
      this.url=process.env.VUE_APP_BASE_API+'/ele/'+this.id
      this.createPicBox();
    },
  }
}
</script>

<style scoped>

</style>
