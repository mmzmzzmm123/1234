<template>
  <div >
    <div style="width:360px;height:260px;margin: 0 auto;margin-top: 40px">
      <el-upload
        ref="upload"
        :limit="1"
        accept=".html"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + 222"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        name="htmlFile"
        drag
        class="upload-demo"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>

        <div class="el-upload__tip title"   slot="tip">提示：仅允许导入".html"格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <button @click="submitFileForm"  class="isbutton mdui-btn mdui-btn-block mdui-btn-raised mdui-btn-dense  mdui-color-blue-100  mdui-ripple">导 入 书 签</button>
      </div>

    </div>

    <div class="structure">
      <el-radio v-model="structure" label="0">按原目录导入</el-radio>
      <el-radio v-model="structure" label="1" disabled>全部导入到一个新目录</el-radio>
    </div>
    <el-button type="primary" size="small" @click="submitUpload" plain><i
    class="el-icon-upload el-icon--right">开始导入书签</i></el-button>




    <div class="text">
      <ul>
        <li>注意事项</li>
        <li>
          1、导入的方法是将浏览器里面收藏的网站导出<span style="color: red">HTML文件后缀</span>。然后将导出的HTML文件点击上面的<span style="color: red">开始导入书签</span>。

        <li>2、目前只测试过谷歌浏览器书签导入。但是因为浏览器的标签是可以支持互相导入的。如果不成功，可以先将其他浏览器的书签导入谷歌浏览器再导出。</li>

        <li>3、浏览器导出书签方法请百度</li>

        <li>4、支持浏览器的目录结构,本站目录支持无限级别分类</li>
      </ul>
    </div>


  </div>

</template>

<script >

 import { getToken } from "@/utils/auth";
  export default {
    name: "",

    data() {
      return {
        // 用户导入参数
        upload: {
          // 是否显示弹出层（用户导入）
          open: false,
          // 弹出层标题（用户导入）
          title: "书签导入",
          // 是否禁用上传
          isUploading: false,
          // 是否更新已经存在的用户数据
          updateSupport: 0,
          // 设置上传的请求头部
          headers: { Authorization: "Bearer " + getToken() },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/IO/import"
        },

      }
    },

    methods: {

      /** 导入按钮操作 */
      handleImport() {
        this.upload.title = "书签导入";
        this.upload.open = true;
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        this.upload.open = false;
        this.upload.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
        this.getList();
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      }

    }
  }
</script>

<style scoped>
  .text{
    border: #cecece 1px solid;
    border-radius: 10px;
    margin: 20px;
    background-color: #fafcff;
    margin-top:  30px;
  }

  .structure {
    margin-top: 15px;
  }

  .Import {
    margin-top: 15px;
  }

  .Import el-button {
    width: 200px;
  }
  ul{
    margin-left: 5px;
  }
   li{
    list-style:none;
  }
  .main{

  }
  .upload-demo{
  }
  .title {
    color: red;
    width: 360px;
    height: 10px;
    text-align: center;
    margin-top: 7px;
  }
  .isbutton{
  margin-top: 10px;

  }
</style>
