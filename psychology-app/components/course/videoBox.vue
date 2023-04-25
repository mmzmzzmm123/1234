<template>
  <view class="video-box">
    <video id="myVideo" ref="video1" :src="currentCatalogue.contentUrl" loop="false"
      :initial-time="currentCatalogue.endTime" :duration="currentCatalogue.duration" controls
      enable-play-gesture="true" play-btn-position="center" style="width: 100%" @timeupdate="videoTimeUpdateEvent" @play="play" @pause="pause"
      @ended="end" @fullscreenchange="fullscreenchange"></video>
  </view>
</template>

<script>
  import courseServer from "@/server/course/course";

  export default {
    props: ['courseId', 'currentCatalogue'],
    data: () => {
      return {
        userInfo: {},
        videoContext: {}, // 用于绑定视频标签
        startTime: "",
      
        secondCount: 0,
      };
    },
    async mounted() {
      this.userInfo = uni.getStorageSync("userInfo")

      this.videoContext = uni.createVideoContext('myVideo');
      // initial-time属性不生效，需要直接设置
      this.videoContext.seek(this.currentCatalogue.endTime)
    },
    methods: {
      videoTimeUpdateEvent(e) { // 播放进度改变
        // e.detail.currentTime为每次触发时,视频的当前播放时间
        this.secondCount++;
        let currentTime = Number(e.detail.currentTime);
        this.currentCatalogue.endTime = parseInt(currentTime)
        // 试看结束 this.class_info.freed_time为试看时间
        if (currentTime >= this.currentCatalogue.duration) {
          // 试看结束,在这做一些想做的操作,例如停止视频播放
          this.videoContext.exitFullScreen();
          this.videoContext.pause();
          this.videoContext.seek(0);

          this.recordEndTime(this.currentCatalogue.duration, 1)
        } else {
          if (this.secondCount === 30) {
            this.recordEndTime(this.currentCatalogue.endTime)
            this.secondCount = 0
          }
        }
      },
      play() {
        this.currentCatalogue.playing = true
        this.$set(this.currentCatalogue, "playing", true)
        this.videoContext.requestFullScreen()        
      },
      pause() {
        this.currentCatalogue.playing = false
        this.$forceUpdate()
      },
      end() {
        this.currentCatalogue.playing = false
        this.$forceUpdate()
        this.recordEndTime(this.currentCatalogue.duration, 1)
      },
      chooseCatalogue(courseItem) {
        // 记录旧章节的学习时间
        this.pause()
        this.recordEndTime(this.currentCatalogue.endTime)  

        this.$nextTick(() => {
          this.videoContext.seek(this.currentCatalogue.endTime)
          this.videoContext.play()
          console.log('chooseCatalogue endTime: ' + this.currentCatalogue.endTime)
          this.play()
          
        })

      },      
      //退出全屏时暂停
      fullscreenchange(e) {
      	if (!e.detail.fullScreen) {
      		this.videoContext.pause();
          this.$emit('fullscreenchange')
      	} 
      },
      async recordEndTime(endTime, finishStatus) {
        const params = {
          userId: this.userInfo.userId,
          courseId: this.courseId,
          sectionId: this.currentCatalogue.id,
          endTime: endTime
        }
        if (finishStatus === 1) {
          params.finishStatus = finishStatus
        }
        await courseServer.recordEndTime(params)
      }
    },
  }
</script>

<style lang="scss">
  .video-box {
    position: absolute;
    top: 0;
    z-index: 99999;
    width: 100%;
    height: 225px;
  }
</style>
