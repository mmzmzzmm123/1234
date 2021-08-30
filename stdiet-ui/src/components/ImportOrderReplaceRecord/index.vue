<template>
  <!-- 批量添加订单的营养师、助理、售后更换记录 -->
    <el-dialog :title="title" :visible.sync="visible" width="400px" append-to-body>
      
           <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
       <!--<div class="el-upload__tip" slot="tip">
          <el-link type="info" style="font-size: 14px" @click="importTemplate"
            >下载模板</el-link
          >
        </div>-->
        <div class="el-upload__tip" style="color: red" slot="tip">
          温馨提示：
          <div style="margin-top:5px" v-for="(item,index) in tips" :key="'tip'+index">
              {{index+1 + '、' + item}}
          </div>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button @click="importTemplate">下载模板</el-button>
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>
<script>
import {
  downImportTemplate
} from "@/api/custom/OrderNutritionistReplaceRecord";
import { getToken } from "@/utils/auth";

export default {
  name: "ImportOrderReplaceRecord",
  components: {
      
  },
  data() {
    return {
      visible: false,
      title: "批量导入订单营养师售后更换记录",
       // 用户导入参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/custom/orderNutritionistReplaceRecord/addMuchReplaceRecord",
      },
      tips:[
        '仅允许导入“xls”或“xlsx”格式文件',
        '用户和手机号不能都为空，建议都填写手机号',
        '营养师、助理、售后不能都为空，至少存在一个',
        '开始时间不能为空'
      ]
    };
  },
  created(){
        
  },
  methods: {
    showDialog() {
       this.visible = true;
    },
    onClosed() {
      
    },
    cancel(){
        this.$refs.upload.clearFiles();
        this.visible = false
    },
     /** 下载模板操作 */
    importTemplate() {
      downImportTemplate().then((response) => {
        this.download(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.isUploading = false;
      if(response.code == 200){
          this.msgSuccess("导入成功");
          this.cancel();
      }else{
          this.msgError(response.msg);
      }
    },
    submitFileForm() {
      this.$refs.upload.submit();
    },
  },
};
</script>

<style lang="scss" scoped>

</style>
