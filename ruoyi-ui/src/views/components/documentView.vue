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
        <div v-if="pdf">
          <pdf v-for="i in numPages" :key="i" :src="src" :page="i" ref="myPdfComponent"></pdf>
        </div>
        <div v-if="pdf"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {listAttributes,getAttributesBase64} from '@/api/system/attributes'
import pdf from 'vue-pdf'
// 解决部分文字不显示的问题
import CMapReaderFactory from 'vue-pdf/src/CMapReaderFactory.js'
export default {
  components:{pdf},
  name: "documentView",
  props:{
    metadataId: {
      type: String
    },
  },
  data() {
    return{
      src: '',
      numPages: 0,
      page: 1,
      currentPage: 0,
      pdf:true,
      eleName:'',
      elTree:{},
      id:'',
      base64:'',
      fileType:'',
      defaultProps:{
        label:'label',
        children:'children',
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
    pdfLoad(){
      let da = this.base64
      let datas = 'data:application/pdf;base64,' + da
      this.src = pdf.createLoadingTask({ url: datas, CMapReaderFactory });
      this.src.promise.then(pdf => {
        this.numPages = pdf.numPages;
      });
    },
    getEleBase64(){
      getAttributesBase64(this.query).then(res=>{
        console.log(res)
      })
    },
    getEleTree(){
      listAttributes(this.query).then(res=>{
        console.log(res.data)
      })
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.id= data.id
    },
  }
}
</script>

<style scoped>

</style>
