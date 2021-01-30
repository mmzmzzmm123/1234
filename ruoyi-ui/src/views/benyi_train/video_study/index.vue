<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="视频类别" prop="type">
            <el-cascader
              placeholder="请选择视频类别"
              ref="example"
              v-model="queryParams.type"
              :options="optionTypes"
              :props="{ checkStrictly: true, value: 'id', label: 'name' }"
              clearable
              @change="exampleChange"
            ></el-cascader>
          </el-form-item>
          <!-- 博士要求注释掉，不需要该过滤条件 2020-05-30 zlp -->
          <!-- <el-form-item label="讲师姓名" prop="lecturer">
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
      </el-form-item>-->
        </el-col>
        <el-col :xs="24" :ms="12" :md="6">
          <el-form-item class="no-margin">
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索</el-button
            >
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置</el-button
            >
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row v-loading="loading">
      <el-col
        :xs="24"
        :sm="8"
        :md="6"
        v-for="(o, index) in videoList"
        :key="index"
        style="padding: 10px"
      >
        <el-card :body-style="{ padding: '2px' }">
          <video-player
            class="vjs-custom-skin"
            :playsinline="true"
            :options="playerOptions[index]"
            ref="videoPlayer"
          ></video-player>
          <div class="to-detail">
            <el-tooltip effect="dark" :content="o.title" placement="bottom">
              <div>
                <router-link
                  :to="'/video_study/study/detail/' + o.id"
                  class="link-type"
                >
                  <p class="info-title">{{ o.title }}</p>
                </router-link>
              </div>
            </el-tooltip>
            <p class="info-title info-title-name">讲师:{{ o.lecturername }}</p>
            <div class="bottom">
              <time class="time">{{ parseTime(o.createtime) }}</time>
              <!-- <el-button type="text" class="button">进入详情页</el-button> -->
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :page-sizes="[8, 16, 32, 64]"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {
  listVideo,
  getVideo,
  privateDownloadUrl,
} from "@/api/benyi_train/video";
import { listAllLecturer } from "@/api/benyi_train/lecturer";
import { listMoedata } from "@/api/system/moedata";
import { downLoadVideoUrl } from "@/utils/zipdownload";

export default {
  name: "VideoStudy",
  data() {
    return {
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
      playerOptions: [],
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        title: undefined,
        lecturer: undefined,
        type: undefined,
        status: "0",
        pageNum: 1,
        pageSize: 8,
      },
    };
  },
  created() {
    this.getList();
    listAllLecturer().then((response) => {
      //console.log(response.lecturer);
      this.lecturerOptions = response.lecturer;
    });
    listMoedata(this.queryParams).then((response) => {
      //第一步转换数组
      this.optionTypes = this.handleTree(response.data, "id", "pid");
      //第二步移除children为0的数组，也就是将children为0 设置为undefined
      this.optionTypes = this.getTreeData(this.optionTypes);
    });
  },
  methods: {
    exampleChange(e) {
      // console.log(e);
      // 目的是选择之后将下拉界面收起
      this.$refs.example.toggleDropDownVisible();
    },
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
      listVideo(this.queryParams).then((response) => {
        this.videoList = response.rows;
        this.playerOptions = response.rows.map((ele) => {
          return {
            autoplay: false,
            muted: true,
            language: "zh-CN",
            playbackRates: [0.7, 1.0, 1.5, 2.0],
            fluid: true,
            sources: [
              {
                type: ele.filetype,
                // mp4
                src: ele.videourl
                // src: downLoadVideoUrl(ele.videourl),
              },
            ],
            notSupportedMessage: "此视频暂无法播放，请稍后再试",
            poster: "",
            controlBar: {
              timeDivider: true,
              durationDisplay: false,
              remainingTimeDisplay: false,
              fullscreenToggle: true, //全屏按钮
            },
          };
        });
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
      //console.log(this.queryParams.type);
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
  },
};
</script>

<style>
.time {
  line-height: 12px;
  font-size: 12px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.to-detail {
  /*cursor: pointer;*/
  padding: 14px;
}

.info-title {
  width: 100%; /*根据自己项目进行定义宽度*/
  overflow: hidden; /*设置超出的部分进行影藏*/
  text-overflow: ellipsis; /*设置超出部分使用省略号*/
  white-space: nowrap; /*设置为单行*/
}

.info-title-name {
  padding-top: 5px;
  font-size: 12px;
}

.el-cascader {
  width: 100%;
}
.no-margin ::v-deep.el-form-item__content {
  margin: 0 !important;
}

@media (max-width: 768.98px) {
  .el-cascader-node__label {
    max-width: 200px;
  }
}
</style>
