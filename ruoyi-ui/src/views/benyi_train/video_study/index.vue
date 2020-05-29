<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="视频类别" prop="type">
        <el-cascader
          placeholder="请选择视频类别"
          v-model="queryParams.type"
          :options="optionTypes"
          :props="{ checkStrictly: true, value: 'id', label: 'name' }"
          clearable
        ></el-cascader>
      </el-form-item>
      <el-form-item label="讲师姓名" prop="lecturer">
        <el-select v-model="queryParams.lecturer" filterable placeholder="请选择讲师">
          <el-option
            v-for="item in lecturerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="视频标题" prop="title">
        <el-input
          v-model="queryParams.title"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
          placeholder="请输入培训视频标题"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row v-loading="loading">
      <el-col :span="6" v-for="(o, index) in videoList" :key="index" style="padding: 10px;">
        <el-card :body-style="{ padding: '2px' }">
          <video
            controls="controls"
            controlslist="nodownload"
            oncontextmenu="return false"
            :src="[qiniuUrl + '/' + o.videourl]"
            class="avatar"
          ></video>
          <div style="padding: 14px;">
            <span class="word-space-ellipsis" :title="o.title">{{o.title+" (讲师:"+o.lecturer+")"}}</span>
            <div class="bottom clearfix">
              <time class="time">{{ parseTime(o.createtime) }}</time>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :page-sizes="[8, 16, 32, 64]"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listVideo, getVideo } from "@/api/benyi_train/video";
import { listAllLecturer } from "@/api/benyi_train/lecturer";
import { listMoedata } from "@/api/system/moedata";

export default {
  name: "VideoStudy",
  data() {
    return {
      qiniuUrl: "https://files.benyiedu.com", // 个人七牛访问前缀
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 培训表格数据
      videoList: [],
      //讲师列表
      lecturerOptions: [],
      //视频类别
      optionTypes: [],
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 8,
        title: undefined,
        lecturer: undefined,
        type: undefined
      }
    };
  },
  created() {
    this.getList();
    listAllLecturer().then(response => {
      //console.log(response.lecturer);
      this.lecturerOptions = response.lecturer;
    });
    listMoedata(this.queryParams).then(response => {
      //第一步转换数组
      this.optionTypes = this.handleTree(response.data, "id", "pid");
      //第二步移除children为0的数组，也就是将children为0 设置为undefined
      this.optionTypes = this.getTreeData(this.optionTypes);
    });
  },
  methods: {
    // 递归判断列表，把最后的children设为undefined
    getTreeData(data) {
      for (var i = 0; i < data.length; i++) {
        if (data[i].children.length < 1) {
          // children若为空数组，则将children设为undefined
          data[i].children = undefined;
        } else {
          // children若不为空数组，则继续 递归调用 本方法
          this.getTreeData(data[i].children);
        }
      }
      //console.log(data);
      return data;
    },
    /** 查询培训列表 */
    getList() {
      this.loading = true;
      listVideo(this.queryParams).then(response => {
        this.videoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      if (this.queryParams.type == null) {
        this.queryParams.type = "";
      } else {
        this.queryParams.type = this.queryParams.type.toString();
      }
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    }
  }
};
</script>

<style>
.time {
  font-size: 13px;
  color: #999;
}

.avatar {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>