<template>
  <div>
  <!-- 多文件下载对话框 -->
  <el-dialog :title="title" :visible.sync="open" @closed="cancel" width="700px" append-to-body>
  <div class="app-container" style="height: 400px; overflow: auto">
    <div style="margin: -20px auto 15px auto">
      <el-button type="primary" @click="lazyShow = true" plain>滚动下拉预览</el-button>
      <el-button type="primary" @click="downZipFile()" plain>打包下载</el-button>
    </div>
    <el-table v-loading="loading" :data="fileList">
      <el-table-column label="文件预览" align="center" prop="fileUrl">
      <template slot-scope="scope">
        <el-image v-if="scope.row.imageFlag" title="点击大图预览"
          style="width: 80px; height: 80px"
          :src="scope.row.downUrl"
          :preview-src-list="imageList">
        </el-image>
        <span v-else>不可预览</span>
      </template>
      </el-table-column>
      <el-table-column label="文件名" align="center" prop="fileName"  width="200">
        <template slot-scope="scope">
          <AutoHideMessage :maxLength="10" :data="scope.row.fileName"></AutoHideMessage>
        </template>
      </el-table-column>

      <el-table-column label="上传时间" align="center" prop="createTime" >
        <template slot-scope="scope">
        <span>{{
            parseTime(scope.row.createTime, "{y}-{m}-{d}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="downloadFile(scope.row)"
          >下载</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>

  <el-dialog :title="'滚动下拉预览'" v-if="lazyShow" :visible.sync="lazyShow" width="600px" style="height: 90%; overflow: auto" append-to-body>
    <div class="demo-image__lazy">
      <el-image v-for="(url,index) in imageList" :class="index !=0 ? 'top-10' : ''" :key="url" :src="url" lazy></el-image>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="lazyShow = false">取 消</el-button>
    </div>
  </el-dialog>
  </div>
</template>

<script>
import { downCaseFile } from "@/api/custom/customerCase";
import AutoHideMessage from "@/components/AutoHideMessage";
import { downSigleFile,downFileByZip } from "@/utils/downFileByZip";
export default {
  name: "MuchFileDown",
  components: {
    AutoHideMessage
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //案例id
      caseId: null,
      //支持预览的文件类型
      imgArr: ['jpg', 'jpeg', 'png'],
      //文件列表
      fileList: [],
      //图片文件列表
      imageList:[],
      //全部文件列表
      fileUrlList:[],
      //全部文件名称列表
      fileNameList:[],
      //懒加载
      lazyShow: false,
      titlePrefix: "",
    };
  },
  created() {

  },
  computed: {
  },
  methods: {
    showDialog(titlePrefix, fileList) {
      this.title = titlePrefix ? ("「"+titlePrefix+"」文件") : "文件列表";
      this.titlePrefix = titlePrefix ? titlePrefix : "文件列表";
      fileList.forEach((item, index) => {
        item["imageFlag"] = this.isImage(item.fileName);
        if(item["imageFlag"]){
           this.imageList.push(item.downUrl);
        }
        this.fileUrlList.push(item.downUrl);
        this.fileNameList.push(item.fileName);
      });
      this.fileList = fileList;
      this.loading = false;
      this.open = true;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.caseId = null;
      this.fileList = [];
      this.imageList = [];
      this.fileUrlList = [];
      this.fileNameList = [];
      this.titlePrefix = "";
    },
    downloadFile(file){
      if(file == undefined || file == null || file.downUrl == null || file.downUrl == ""){
        this.$message({message: "文件不存在", type: "warning",});
        return;
      }
      downSigleFile(file.downUrl, file.fileName);
    },
    isImage(name){
      var idx = name.lastIndexOf(".");
      if (idx === -1) {
        return false;
      }
      var type = name.substr(idx + 1);
      return this.imgArr.indexOf(type) !== -1 ? true : false;
    },
    downZipFile(){
      downFileByZip(this.fileUrlList, this.fileNameList, this.titlePrefix);
    },
    //判断是否为Trident内核浏览器(IE等)函数
    browserIsIe: function () {
      if (!!window.ActiveXObject || "ActiveXObject" in window) {
        return true;
      }
      else {
        return false;
      }
    }
  },
};
</script>

<style scoped>
  .top-10{
    margin-top: 10px;
  }
</style>
