<template>
  <view class="learning-course">
    <view class="video-box">
      <video
        id="myVideo"
        ref="video1"
        :src="videoUrl"
        loop="true"
        :initial-time="startTime"
        enable-danmu
        danmu-btn
        controls
        style="width: 100%"
      ></video>
    </view>
    <view class="catalogue-title">我们每个人都有自我疗愈能力</view>
    <view class="catalogue-num">
      <span>课程列表(共12节)</span>
      <span @tap="showList"
        >全部<img class="img" src="/static/icon/more.png"
      /></span>
    </view>
    <view class="list-box">
      <view
        class="list-item"
        v-for="(courseItem, index) in catalogueList"
        :class="{ playing: courseItem.playing }"
      >
        <view class="status-box">
          <span class="try-txt">
            <span v-if="courseItem.enabled">试听</span>
            <image
              class="lock-img"
              v-if="!courseItem.enabled"
              src="/static/curriculum/course/lock-white.png"
            ></image>
          </span>
          <span class="time-txt">{{
            courseItem.playing ? "正在播放" : courseItem.time
          }}</span>
        </view>
        <view class="course-title txt-overflow txt-overflow-line2"
          >{{ index + 1 }}、{{ courseItem.title }}</view
        >
      </view>
    </view>
    <view class="bg-line"></view>
    <view class="current-content">
      <view class="title">本期内容</view>
      <view class="content">
        <p>一个需要重新体验出生的五岁男孩</p>
        · 男孩表面看是有智力缺陷，但实际上是儿童精神分裂症患者，是成熟
        发展的第三条路线出了问题，在情感方面有了障碍，整个人处在贵乏的状态;成熟发展的第四条路线“社会化”也受到了影响
        ·
        治疗匮乏与治疗心智障碍非常不同，因此诊断十分关键，相同的症状可能得出不同的诊断结果
        ·
        男孩创造了游戏，为了满足自身需求，玩这个游戏能满足男孩的存在性需求，因此温尼科特不去限制男孩的行为。男孩玩这个游戏的目的是为了体验出生的过程
        · 接下来的阶段，男孩破坏性地进食
        、索取，对食物的爱驱使男孩去破坏食物，进食的过程即对食物的破坏过程，在初始阶段，爱同时具备破坏性的冲动;孩子兴奋的本能、身体机能的本能都在温尼科特的设置中表现了出来
        ·
        下一阶段，男孩开始退行，退行到了非常初始的出生时的阶段，重新体验了早期的婴儿经历，获得了重生，看到一个新的温尼科特所提供的环境，从而允许男孩重新成长</view
      >
    </view>
    <!-- 底部操作菜单 -->
    <cartTabBar @cartShow="cartShow"></cartTabBar>
    <cartBox
      @closeCart="cartShow"
      v-if="cartBoxShow&&!this.courseInfo.isBuy"
      :courseInfo="courseInfo"
      :redirectUri="redirectUri"
    ></cartBox>
    <view class="mask-box" v-if="moreListShow">
      <view class="catalogue-list-box">
        <view class="catalogue-num-box">
          <span>课程列表(共{{catalogueList.length}}节)</span>
          <view class="close-icon" @tap="closeList"></view>
        </view>
        <customCatalogueList
          :catalogueList="catalogueList"
        ></customCatalogueList> </view
    ></view>
  </view>
</template>
<script>
import utils from "@/utils/common";
import courseServer from "@/server/curriculum/course";
import cartTabBar from "@/components/curriculum/cartTabBar";
import cartBox from "@/components/curriculum/cartBox";
import customCatalogueList from "@/components/curriculum/catalogueList";
export default {
  components: { cartTabBar, cartBox, customCatalogueList },
  data: () => {
    return {
      videoUrl: "",
      startTime: "",
      moreListShow: false,
      courseInfo: {},
      cartBoxShow: false,
      redirectUri:location.href,
      catalogueList: [
        {
          title:
            "课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1",
          time: "10:34",
          enabled: true,
          playing: true,
        },
        { title: "课程2", time: "07:34", enabled: true, playing: false },
        { title: "课程2", time: "07:34", enabled: false, playing: false },
        { title: "课程2", time: "07:34", enabled: false, playing: false },
        { title: "课程2", time: "07:34", enabled: false, playing: false },
        { title: "课程2", time: "07:34", enabled: false, playing: false },
      ],
    };
  },
  async created() {
    this.courseId =
      utils.getParam(location.href, "courseId") ||
      utils.getParam(location.href, "id");
    this.courseInfo = await courseServer.getCourseInfo(this.courseId);
    this.catalogueList = this.courseInfo.sectionList;
    this.cartBoxShow = utils.getParam(location.href, "payOrder") == 1;
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
