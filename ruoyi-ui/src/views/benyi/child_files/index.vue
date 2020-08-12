<template>
  <div class="app-container">
    <el-row :gutter="12">
      <el-col :span="4" v-for="(item, index) in filesList" :key="index" style="padding: 10px;">
        <el-card shadow="hover">
          <a :href="apiurl+item.fileurl">{{item.name}}</a>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listFiles, getFiles } from "@/api/benyi/files";

export default {
  name: "childFiles",
  data() {
    return {
      // 遮罩层
      loading: true,
      apiurl: process.env.VUE_APP_BASE_API,
      filesList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        filetype: undefined,
        type: "1", //代表幼儿入园系列文件
        fileurl: undefined,
        createuserid: undefined,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询文件管理列表 */
    getList() {
      this.loading = true;
      listFiles(this.queryParams).then((response) => {
        this.filesList = response.rows;
        this.loading = false;
      });
    },
  },
};
</script>