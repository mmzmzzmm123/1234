<template>
  <div class="app-container">
    <el-row>
      <el-col :xs="24" :sm="12" style="padding: 10px;">
        <el-card>
          <video-player
            class="vjs-custom-skin"
            :playsinline="true"
            :options="playerOptions"
            ref="videoPlayer"
          ></video-player>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" style="padding: 10px;">
        <el-card>
          内容介绍
          <br />标题：{{title}}
          <br />讲师：{{lecturername}}
          <br />简介：{{information}}
          <br />评分：
          <el-rate v-model="value"></el-rate>留言：
          <el-input type="textarea" placeholder="请输入内容" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getVideo } from "@/api/benyi_train/video";

export default {
  name: "detail",
  data() {
    return {
      title: "",
      lecturername: "",
      information: "",
      //视频播放参数
      playerOptions: {},
      value: null
    };
  },
  created() {
    const id = this.$route.params && this.$route.params.id;
    //console.log(id);
    this.getVideoById(id);
  },
  methods: {
    /** 查询培训列表 */
    getVideoById(id) {
      getVideo(id).then(response => {
          this.title=response.data.title;
          this.lecturername=response.data.lecturername;
          this.information=response.data.information;
        //console.log(response.data);
        this.playerOptions = {
          autoplay: true,
          muted: true,
          language: "zh-CN",
          playbackRates: [0.7, 1.0, 1.5, 2.0],
          fluid: true,
          sources: [
            {
              type: response.data.filetype,
              // mp4
              src: "https://files.benyiedu.com/" + response.data.videourl
            }
          ],
          notSupportedMessage: "此视频暂无法播放，请稍后再试",
          poster: "",
          controlBar: {
            timeDivider: true,
            durationDisplay: false,
            remainingTimeDisplay: false,
            fullscreenToggle: true //全屏按钮
          }
        };
      });
    }
  }
};
</script>