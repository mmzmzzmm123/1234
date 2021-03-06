<template>
  <div>
    <p class="p_title_1">{{healthyData['titleArray'][8]}}</p>
    <p class="p_title_2">1、体检报告</p>
    <el-form-item label="(1) 请上传相应的体检报告" prop="fileList" class="margin-left">
        <div>
          <div v-for="(item, index) in form.medicalReport">
            <span style="margin-right: 10px;">{{getReportName(index)}}</span>
            <el-button style="margin-left: 10px;" type="danger" size="small" @click="removeOldFile(index)">移除文件</el-button>
          </div>
          <div>
            <el-upload style="margin-top: 15px;"
                       class="upload-demo"
                       ref="upload"
                       :action="upload.url"
                       :limit="upload.limit"
                       :file-list="upload.fileList"
                       :multiple="upload.multiple"
                       :before-remove="beforeRemove"
                       :on-change="handleFileChange"
                       :on-exceed="handleFileexceed"
                       :on-success="handleFileSuccess"
                       :on-error="handleFileFail">
              <el-button slot="trigger" size="small" type="primary">新增文件</el-button>
              <el-button style="margin-left: 10px;" size="small" @click="removeFileList()">移除文件</el-button>
              <div slot="tip" class="el-upload__tip">1、已存在{{form.medicalReport.length}}份，还可以上传{{upload.limit}}份，且每份文件不超过20M
                <br>2、仅支持bmp，gif, jpg, jpeg, png, doc, docx, xls, xlsx, ppt, pptx, html, htm, txt, rar, zip, gz, bz2, pdf格式文件
              </div>
            </el-upload>
          </div>

        </div>


    </el-form-item>
  </div>
</template>
<script>
import * as healthyData from "@/utils/healthyData";
import AutoHideMessage from "@/components/AutoHideMessage";
export default {
  name: "EditHealthyFile",
  components: {

  },
  data() {
    return {
      healthyData:healthyData,
      upload: {
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/common/customerUploadFile",
        //文件列表
        fileList:[],
        //同时上传文件上限
        limit: 3 - this.form.medicalReport.length,
        //每个文件大小
        fileSize: 1024 * 1024 * 20,
        //是否支持同时选择多张
        multiple: true,
        //新增的文件路径
        medicalReport:[],
        //新增的文件名称
        medicalReportName:[]
      },
    };
  },
  methods: {
    removeOldFile(index){
      this.form.medicalReport.splice(index,1);
      this.form.medicalReportName.splice(index,1);
      this.upload.limit = 3 - this.form.medicalReport.length;
    },
    getReportName(index){
       if(this.form.medicalReportName && this.form.medicalReportName.length > index){
          return this.form.medicalReportName[index];
       }
       return "体检报告（"+ (index+1) + "）";
    },
    //更新form对象中的文件路径、名称
    addUploadNewFile(){
       this.form.medicalReport = this.form.medicalReport.concat(this.upload.medicalReport);
       this.form.medicalReportName = this.form.medicalReportName.concat(this.upload.medicalReportName);
    },
    removeFileList(){
      this.upload.fileList = [];
      this.upload.medicalReport = [];
      this.upload.medicalReportName = [];
    },
    //监控上传文件列表
    handleFileChange(file, fileList) {
      let sizeFlag = file.size > this.upload.fileSize;
      if (sizeFlag) {
        this.$message({
          message: "当前文件过大",
          type: "warning",
        });
        fileList.pop();
      }
      this.upload.fileList = fileList;

    },
    beforeRemove(file, fileList) {
      return false;
    },
    // 文件数量超过限度
    handleFileexceed(file, fileList){
      //console.log(this.upload.fileList.length);
      this.$message({
        message: "最多可上传"+ this.upload.limit +"份文件",
        type: "warning",
      });
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      if(response != null && response.code === 200){
        this.upload.medicalReport.push(response.fileName);
        this.upload.medicalReportName.push(file.name);
      }else{
        this.upload.fileList = fileList.pop();
        this.$message.error('文件上传失败，请检查文件格式');
      }
    },
    // 文件上传失败处理
    handleFileFail(err, file, fileList){
      this.$message.error('文件上传失败，请检查文件格式');
      this.upload.fileList = fileList.pop();
    }
  },
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  created() {

  },

};
</script>

<style scoped>

  .el-form-item {
    margin-bottom: 8px;
  }

  .p_title_1{
    font-size: 18px;
    font-weight: bold;
    margin-top: 30px;
  }
  .p_title_2{
    font-size: 16px;
    font-weight: bold;
    margin-top: 30px;
  }
  .p_title_3{
    font-size: 14px;
    font-weight: bold;
    margin-top: 30px;
  }
  .margin-left{
    margin-left: 14px;
  }
  .el-input__inner{
    width: 30%;
  }
  .margin-top-10{
    margin-top: 10px;
  }
  .width-50-left-8-right-5{
    width:50%;
    margin-left: 8px;
    margin-right: 5px;
  }
  .width-70-left-8-right-5{
    width:70%;
    margin-left: 8px;
    margin-right: 5px;
  }


</style>

