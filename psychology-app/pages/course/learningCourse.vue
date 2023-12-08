<template>
  <view class="learning-course">
    <view class="video-box">
      <video
        id="myVideo"
        ref="video1"
        :src="currentCatalogue.contentUrl"
        loop="false"
        :initial-time="currentCatalogue.endTime"
		:duration="currentCatalogue.duration"
        enable-danmu
        danmu-btn
        controls
		enable-play-gesture="true"
        style="width: 100%"
		@timeupdate="videoTimeUpdateEvent"
		@play="play"
		@pause="pause"
		@ended="stop"
      ></video>
    </view>
    <view class="catalogue-title">{{ courseInfo.name }}</view>
    <view class="catalogue-num">
      <span>章节列表(共12节)</span>
      <div class="all" @tap="showList">
        <div>全部</div>
        <img class="img" src="/static/icon/more.png" />
      </div>
    </view>
    <view class="list-box">
      <view
        class="list-item"
        v-for="(courseItem, index) in catalogueList"
        :class="[{ playing: courseItem.playing }]"
        @tap="chooseCatalogue(courseItem, index)"
      >
        <view class="status-box">
          <span class="try-txt">
            <span v-if="courseInfo.payType === 0 && courseInfo.isBuy === 1">已购</span>
            <span v-if="courseInfo.payType === 0 && courseInfo.isBuy === 0 && courseItem.type === 1">试听</span>            
            <image
              class="lock-img"
              v-if="courseInfo.payType === 0 && courseInfo.isBuy === 0 && courseItem.type === 0"
              src="/static/course/course/lock-white.png"
            ></image>
            <span v-if="courseInfo.payType === 1">免费</span>
          </span>
          <span class="time-txt">{{
            courseItem.playing ? "正在播放" : formatDuration(courseItem.duration)
          }}</span>
        </view>
        <view class="course-title txt-overflow txt-overflow-line2"
          >{{ index + 1 }}、{{ courseItem.topic }}</view
        >
      </view>
    </view>
    <view class="bg-line"></view>
    <view class="current-content">
      <view class="title">本期内容</view>
      <view class="content" v-html="currentCatalogue.content"></view>
    </view>
    <!-- 底部操作菜单 -->
    <cartTabBar @cartShow="cartShow" :isBuy="courseInfo.isBuy"></cartTabBar>
    <cartBox
      @closeCart="cartShow"
      v-if="cartBoxShow&&!this.courseInfo.isBuy"
      :courseInfo="courseInfo"
      :redirectUri="redirectUri"
    ></cartBox>
    <view class="mask-box" v-if="moreListShow">
      <view class="catalogue-list-box">
        <view class="catalogue-num-box">
          <span>章节列表(共{{catalogueList.length}}节)</span>
          <view class="close-icon" @tap="closeList"></view>
        </view>
        <customCatalogueList
          :catalogueList="catalogueList"
          :payType="this.courseInfo.payType"
          :isBuy="courseInfo.isBuy"
          @catalogueItemClick="(item, index) => chooseCatalogue(item, index)"
        ></customCatalogueList> </view
    ></view>
  </view>
</template>
<script>
import courseServer from "@/server/course/course";
import cartTabBar from "@/components/course/cartTabBar";
import cartBox from "@/components/course/cartBox";
import customCatalogueList from "@/components/course/catalogueList";
import formatTime from "@/utils/formatTime.js"
export default {
  components: { cartTabBar, cartBox, customCatalogueList },
  data: () => {
    return {
	  userInfo: {},
	  videoContext: {},  // 用于绑定视频标签
    videoUrl: "",
    startTime: "",
    moreListShow: false,
    courseInfo: {},
    cartBoxShow: false,
    redirectUri:location.href,
    catalogueList: [],
	  currentIndex: 0, //
	  currentCatalogue: {}, 
	  secondCount: 0,
    };
  },
  async created() {
    // this.userInfo = uni.getStorageSync("userInfo")
    this.userInfo = this.$utils.getUserInfo()
    this.courseId =
      this.$utils.getParam(location.href, "courseId") ||
      this.$utils.getParam(location.href, "id");
    this.courseInfo = await courseServer.getCourseInfo(this.userInfo.userId, this.courseId);
    this.catalogueList = this.courseInfo.sectionList;
    this.cartBoxShow = this.$utils.getParam(location.href, "payOrder") == 1;
	
    this.currentIndex = 0;
    this.currentCatalogue = this.catalogueList[0] || {}
    
    this.videoContext = uni.createVideoContext('myVideo');
    // initial-time属性不生效，需要直接设置
    this.videoContext.seek(this.currentCatalogue.endTime)
  },
  beforeDestroy() {
	// 记录当前章节学习的结束时间点, 用戶直接关闭页面感知不到，不可靠
	  console.log('beforeDestroy')
	  this.recordEndTime(this.currentCatalogue.endTime)
  },
  methods: {
    closeList() {
      this.moreListShow = !this.moreListShow;
    },
    showList() {
      this.moreListShow = true;
    },
    cartShow() {
      this.cartBoxShow = !this.cartBoxShow;
    },
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
    chooseCatalogue(courseItem, index) {
      // 记录旧章节的学习时间
      this.pause()
      this.recordEndTime(this.currentCatalogue.endTime)
      
      // 选中章节
      this.currentIndex = index;
      this.currentCatalogue = this.catalogueList[index] || {}		
      this.scrollMove()
      this.moreListShow = !this.moreListShow
      this.$nextTick(() => {
        this.videoContext.seek(this.currentCatalogue.endTime)
        console.log('chooseCatalogue endTime: ' + this.currentCatalogue.endTime)
      })
      
    },
    scrollMove() {
      let scrollElement = document.querySelector(".list-box")
      scrollElement.scrollLeft = 160 * this.currentIndex
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
    }, 
    formatDuration(seconds) {
      return formatTime.formatDuration(seconds)
    }   
  },
};
</script>
<style lang="scss">
@import "@/style/common.scss";
.learning-course {
  .video-box {
    margin-bottom: 32upx;
  }
  .catalogue-title {
    font-size: 36upx;
    font-weight: 500;
    color: #333333;
    line-height: 50upx;
    padding: 0 24upx;
    margin-bottom: 8upx;
  }
  .catalogue-num {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    padding: 0 24upx;
    font-size: 26upx;
    font-weight: 400;
    color: #777777;
    line-height: 37upx;
    align-items: center;
    margin-bottom: 32upx;
    .all {
      display: flex;
      align-items: center;
    }
    .img {
      width: 12upx;
      height: 24upx;
      margin-left: 10upx;
      vertical-align: middle;
    }
  }
  .list-box {
    padding: 0 24upx;
    display: flex;
    flex-direction: row;
    overflow-y: auto;
    margin-bottom: 32upx;

    .list-item {
      color: #333333;
      min-width: 308upx;
      max-width: 308upx;
      box-sizing: border-box;
      height: 172upx;
      background: #f2f3f7;
      border-radius: 8upx;
      margin-right: 12upx;
      border: 1px solid #8990a7;
      padding: 16upx 16upx 32upx 0;
      .course-title {
        margin-left: 16upx;
        font-size: 26upx;
      }
      .status-box {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        .try-txt {
          width: 76upx;
          height: 34upx;
          background: #8990a7;
          color: #fff;
          display: inline-block;
          font-size: 22upx;
          text-align: center;
          line-height: 34upx;
          margin-bottom: 18upx;
          .lock-img {
            width: 20upx;
            height: 24upx;
            vertical-align: middle;
          }
        }
        .time-txt {
          height: 30upx;
          font-size: 22upx;
          font-weight: 400;
          color: #777777;
          line-height: 30upx;
        }
      }
      &.playing {
        color: #ff703f;
        border-color: #ff703f;
        .try-txt {
          background-color: #ff703f;
        }
        .time-txt {
          color: #ff703f;
        }
      }
    }
  }
  .current-content {
    padding: 32upx 24upx 114upx;
    .title {
      font-size: 36upx;
      font-weight: 500;
      color: #333333;
      line-height: 50upx;
      margin-bottom: 32upx;
    }
    .content {
      font-size: 26upx;
      font-weight: 400;
      color: #333333;
      line-height: 37upx;
    }
  }
  .mask-box {
    width: 100%;
    height: 100%;
    background: #33333380;
    position: fixed;
    bottom: 0;
    left: 0;
    z-index: 1000;
    .catalogue-list-box {
      background-color: #fff;
      position: absolute;
      bottom: 0;
      left: 0;
      padding: 0 24upx;
      width: 100%;
      box-sizing: border-box;
      .catalogue-num-box {
        line-height: 130upx;
        margin-bottom: 8upx;
        text-align: center;
        font-size: 36upx;
        font-weight: 500;
        color: #333333;
        .close-icon {
          background-image: url("/static/icon/close.png");
          background-size: 100% 100%;
          width: 30upx;
          height: 30upx;
          position: absolute;
          top: 18upx;
          right: 24upx;
        }
      }
    }
  }
}
</style>
