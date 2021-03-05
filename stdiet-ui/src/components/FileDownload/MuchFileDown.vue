<template>
  <!-- 多文件下载对话框 -->
  <el-dialog :title="title" :visible.sync="open" @closed="cancel" width="600px" append-to-body>
  <div class="app-container">

    <el-table v-loading="loading" :data="fileList">
      <el-table-column label="文件名" align="center" prop="fileName" />
      <el-table-column label="上传时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click=""
          >下载</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { downCaseFile } from "@/api/custom/customerCase";

export default {
  name: "MuchFileDown",
  components: {

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
      //文件列表
      fileList: []
    };
  },
  created() {

  },
  computed: {
  },
  methods: {
    showDialog(title, fileList) {
      this.title = title ? title : "文件列表";
      this.fileList = fileList;
      this.loading = false;
      this.open = true;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.caseId = null;
      this.fileList = [];
    },
    downloadFile(file){
      downCaseFile(file);
    }
  },
};
</script>
