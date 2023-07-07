<template>
  <view class="audio-box">
    <movable-area class="movable-area">
      <movable-view class="movable-view" :x="x" :y="y" direction="all">
       <!-- <audio controls :src="current.contentUrl" :poster="current.poster" :name="current.topic"
          :action="audioAction" @play="play" @pause="pause" @ended="end"
          @timeupdate="audioTimeUpdateEvent"></audio> -->         
          
          <yz-audio ref="player1" @play="play" @pause="pause" @ended="end" @timeUpdate="audioTimeUpdateEvent"></yz-audio>
      </movable-view>
    </movable-area>

  </view>
</template>

<script>
  import utils from "@/utils/common";
  import courseServer from "@/server/course/course";
  import YZAudio from '@/components/yz-audio/yz-audio.vue'

  export default {
    props: ['courseId', 'currentCatalogue'],
    components: { YZAudio },
    data: () => {
      return {
        userInfo: {},
        player1: {}, // 用于绑定视频标签
        startTime: "",
        current: {
          poster: 'https://web-assets.dcloud.net.cn/unidoc/zh/music-a.png',
        },
        secondCount: 0,

        x: 320,		//x坐标
        y: 520,		//y坐标
        
        audioAction: {
          method: 'pause'
        },
      };
    },
    async mounted() {
      // this.userInfo = uni.getStorageSync("userInfo")
      this.userInfo = utils.getUserInfo()
      this.player1 = this.$refs.player1;
    },
    methods: {
      audioTimeUpdateEvent(detail) { // 播放进度改变
        // e.detail.currentTime为每次触发时,视频的当前播放时间
        
        this.secondCount++;
        let currentTime = Number(detail.currentTime);
        let duration = typeof dur === 'number' ? Number(detail.duration) : this.current.duration
        this.current.endTime = parseInt(currentTime)
        // 试看结束 this.class_info.freed_time为试看时间
        if (currentTime >= duration) {
          // 试看结束,在这做一些想做的操作,例如停止视频播放
          
          this.player1.pause();
          this.player1.seek(0);

          this.recordEndTime(duration, 1)
        } else {
          if (this.secondCount === 30) {
            this.recordEndTime(this.current.endTime)
            this.secondCount = 0
          }
        }
      },
      play() {
        this.current.playing = true
      },
      pause() {
        this.current.playing = false
        this.$forceUpdate()
      },
      end() {
        this.current.playing = false
        this.$forceUpdate()
        this.recordEndTime(this.current.duration, 1)
      },
      chooseCatalogue(catalogueItem) {
        // 记录旧章节的学习时间        
        if (this.current.id) {
          this.pause()
          this.recordEndTime(this.current.endTime)
        }        

        this.current = catalogueItem
        
        if (!this.player1) {
          this.player1 = this.$refs.player1; 
        }
        this.player1.setSrc(this.current.contentUrl);  
        this.player1.setPoster("https://vkceyugu.cdn.bspapp.com/VKCEYUGU-uni-app-doc/7fbf26a0-4f4a-11eb-b680-7980c8a877b8.png");//海报图片
        this.player1.setSinger(this.current.author);//设置歌手
        this.player1.setName(this.current.topic);//设置作品名字
        this.player1.seek(this.current.endTime)
        this.player1.play()
      },
      async recordEndTime(endTime, finishStatus) {
        const params = {
          userId: this.userInfo.userId,
          courseId: this.courseId,
          sectionId: this.current.id,
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
  .movable-area {
    height: 100%;
    width: 100%;
    top: 0%;
    left: 0%;
    position: fixed!important;
    pointer-events: none;		//此处要加，鼠标事件可以渗透
    .movable-view {
      width: auto;
      height: auto;
      pointer-events: auto;	//恢复鼠标事件      
      
    }
  }
  .audio-box {
    top: 80%;
    left: 10%;
    z-index: 99999;
  }
  
</style>
