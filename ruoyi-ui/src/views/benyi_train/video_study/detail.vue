<template>
  <div class="app-container">
    <el-row :gutter="10">
      <el-col :xs="24" :sm="12">
        <el-card>
          <video-player
            class="vjs-custom-skin"
            :playsinline="true"
            :options="playerOptions"
            ref="videoPlayer"
          ></video-player>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span>内容介绍</span>
          </div>
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <div class="text item">
              <el-form-item label="标题">{{title}}</el-form-item>
            </div>
            <div class="text item">
              <el-form-item label="讲师">{{lecturername}}</el-form-item>
            </div>
            <div class="text item">
              <el-form-item label="简介">{{information}}</el-form-item>
            </div>
            <div class="text item">
              <el-form-item label="评分" prop="score">
                <el-rate v-model="form.score" :disabled="dis" :show-score="dis"></el-rate>
                <el-input v-model="form.videoid" v-if="false" />
                <el-input v-model="form.lecturerid" v-if="false" />
              </el-form-item>
            </div>
            <div class="text item">
              <el-form-item label="意见建议" prop="content">
                <el-input
                  type="textarea"
                  v-model="form.content"
                  :disabled="dis"
                  maxlength="500"
                  placeholder="请您对视频内容和讲师作出评价，并告诉我们你喜欢或不喜欢的理由，以便使我们改进对您的服务品质。谢谢您的支持！"
                />
              </el-form-item>
            </div>
            <div class="item">
              <el-form-item label="">
                <el-button
                class="sub-btn"
                type="primary"
                :disabled="dis"
                @click="submitForm"
                v-hasPermi="['benyi:feedback_score:add']"
              >提交</el-button>
              </el-form-item>
            </div>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getVideo } from "@/api/benyi_train/video";
import { getScoreByVideo, addScore } from "@/api/benyi_train/score";
import { addFeedback } from "@/api/benyi_train/feedback";

export default {
  name: "detail",
  data() {
    return {
      title: "",
      dis: false,
      lecturername: "",
      lecturerid: null,
      information: "",
      //视频播放参数
      playerOptions: {},
      // value: null,
      // content: "",
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        score: [
          { required: true, message: "请为讲师打个分数吧", trigger: "click" }
        ],
        content: [
          { required: true, message: "意见或建议不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    const id = this.$route.params && this.$route.params.id;
    //console.log(id);
    this.getVideoById(id);
    this.getScore(id);
    //this.value = 5;
    //this.dis=true;
  },
  methods: {
    //查询分数和评价
    getScore(id) {
      getScoreByVideo(id).then(res => {
        //console.log(res.data == undefined);
        if (res.msg == null) {
          this.dis = false;
        } else {
          this.dis = true;
          this.form.score = res.score;
          this.form.content = res.content;
        }
      });
    },
    /** 查询培训列表 */
    getVideoById(id) {
      getVideo(id).then(response => {
        this.title = response.data.title;
        this.lecturername = response.data.lecturername;
        this.lecturerid = response.data.lecturer;
        this.information = response.data.information;
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
              src: response.data.videourl
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
    },
    /** 提交按钮 */
    submitForm: function() {
      this.dis=true;
      const id = this.$route.params && this.$route.params.id;
      if (this.form.score == 0) {
        this.form.score = null;
      }
      this.form.content=this.form.content.trim();
      this.form.lecturerid = this.lecturerid;
      this.form.videoid = id;
      this.$refs["form"].validate(valid => {
        if (valid) {
          addScore(this.form).then(response => {
            if (response.code === 200) {
              addFeedback(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("感谢您的反馈，谢谢！");
                  this.getScore(id);
                } else {
                  this.msgError(response.msg);
                }
              });
            } else {
              this.msgError(response.msg);
              this.dis=false;
            }
          });
        }else{
           this.dis=false;
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.sub-btn {
  margin-top: 5px;
}
.el-rate {
  margin-top: 8px;
}
.item {
  ::v-deep.el-form-item {
    margin-bottom: 5px;
  }
}
</style>
