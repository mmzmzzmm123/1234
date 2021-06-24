<template>
  <div class="app-container">
    <!-- <el-row :gutter="12">
      <el-col
        :span="4"
        v-for="(item, index) in filesList"
        :key="index"
        style="padding: 10px;"
      >
        <el-card shadow="hover">
          <a :href="apiurl + item.fileurl">{{ item.name }}</a>
        </el-card>
      </el-col>
    </el-row> -->
    <ul class="infinite-list" v-infinite-scroll="load" style="overflow: auto">
      <li
        v-for="(ele, i) in filesList"
        :key="i"
        class="flex align-center justify-between infinite-list-item"
      >
        <div class="left">
          <span>{{ i + 1 }}.</span>
          <i class="el-icon-document icon"></i>{{ ele.name }}
        </div>
        <a class="right" :href="apiurl + ele.fileurl">下载</a>
        <!-- <a class="right" @click="down(ele)">下载</a> -->
      </li>
    </ul>
  </div>
</template>

<script>
import { listFiles, getFiles } from "@/api/benyi/files";
import { downLoadUrl } from "@/utils/zipdownload";

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
        pageSize: 50,
        name: undefined,
        filetype: undefined,
        type: "5", //代表家长会系列文件
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
    load() {},
    down(row) {
      downLoadUrl(row.fileurl,row);
    },
  },
};
</script>
<style lang="scss" scoped>
.infinite-list-item {
  padding: 15px 15px;
  border-bottom: 1px solid #eee;
  &:hover {
    background: #f8f8f8;
  }
  .left {
    font-size: 14px;
    .icon {
      margin: 0 3px 0 5px;
    }
  }
  .right {
    font-size: 12px;
    color: #999;
    &:hover {
      color: rgb(64, 158, 255);
    }
  }
}
</style>
