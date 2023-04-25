<template>
  <view class="course">
    <image class="cover" :src="courseInfo.url"></image>
    <view class="title">{{ courseInfo.name }}</view>
    <view class="sub-title">{{ courseInfo.author }}</view>
    <view class="price"
      ><span class="icon">￥</span>{{ courseInfo.price }}</view
    >
    <view class="info">
      <view class="item">{{ catalogueList.length }}节课</view>
      <view class="item">{{ (courseInfo.totalDuration/3600).toFixed(2) }}小时课时</view>
      <view class="item">{{ courseInfo.studyNum  }}人已学</view>
    </view>
    <view class="bg-line"></view>
    <view class="info-title tab-title">
      <view
        class="tab-item"
        @tap="() => (currentItemIndex = 0)"
        :class="{ active: currentItemIndex == 0 }"
        >详情</view
      >
      <view
        class="tab-item"
        @tap="() => (currentItemIndex = 1)"
        :class="{ active: currentItemIndex == 1 }"
        >目录</view
      >
    </view>
    <view class="img-box" v-show="currentItemIndex == 0" v-html="courseInfo.detail">
    </view>
    <view class="img-box" v-if="currentItemIndex == 1">
      <customCatalogueList :catalogueList="catalogueList" :payType="this.courseInfo.payType" :isBuy="this.courseInfo.isBuy" @catalogueItemClick="catalogueItemClick"></customCatalogueList>
    </view>
    <view class="more-box">
      <navigator url="/pages/course/class" open-type="redirect" class="more"
        >查看更多课程 <img class="img" src="/static/icon/more.png" />
      </navigator>
    </view>
    <!-- 底部操作菜单 -->
    <cartTabBar @cartShow="cartShow" :payType="this.courseInfo.payType" :isBuy="this.courseInfo.isBuy"></cartTabBar>
    <cartBox @closeCart="cartShow" v-if="cartBoxShow && this.courseInfo.isBuy == 0" :courseInfo="courseInfo" 
      :redirectUri="redirectUri"></cartBox>
      
    <uni-popup ref="popup" type="dialog">
    	<uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true"
    		@close="close" @confirm="confirm"></uni-popup-dialog>
    </uni-popup>  
    
    <audio-box ref="audioRef" :courseId="courseId" :currentCatalogue="currentCatalogue" v-show="currentCatalogue.contentType == 1 && currentCatalogue.playing"></audio-box>
    <video-box ref="videoRef" :courseId="courseId" :currentCatalogue="currentCatalogue" @fullscreenchange="currentCatalogue.playing = !currentCatalogue.playing" v-show="currentCatalogue.contentType == 0 && currentCatalogue.playing"></video-box>
  </view>
</template>

<script>
import utils from "@/utils/common";
import courseServer from "@/server/course/course";
import cartTabBar from "@/components/course/cartTabBar";
import cartBox from "@/components/course/cartBox";
import customCatalogueList from "@/components/course/catalogueList";
import {
  uniPopup,
  uniPopupDialog
} from '@dcloudio/uni-ui'
import loginServer from '@/server/login'
import videoBox from "@/components/course/videoBox.vue"
import audioBox from "@/components/course/audioBox.vue"
export default {
  components: { cartTabBar,cartBox,customCatalogueList, videoBox, audioBox },
  data() {
    return {
	  userInfo: {},
      courseInfo: {},
      cartBoxShow: false,
      courseId: 0,
      currentItemIndex: 0,
      catalogueList: [],
      redirectUri:location.href,
      currentCatalogue: {},
    };
  },
  async created() {
    this.userInfo = uni.getStorageSync("userInfo");
    if (!this.userInfo) {
      this.openLoginConfirm()
      return
    }
    this.courseId = parseInt(utils.getParam(location.href, "courseId")||utils.getParam(location.href, "id"));

    this.courseInfo = await courseServer.getCourseInfo(this.userInfo.userId, this.courseId)||{};
    this.catalogueList = this.courseInfo.sectionList;
    this.courseInfo.totalDuration = 0;
    this.catalogueList.forEach(item => {
      this.courseInfo.totalDuration+= item.duration||0;
    });
    this.cartBoxShow = utils.getParam(location.href, "payOrder") == 1;
  },
  methods: {
    catalogueItemClick(item) {
      if (this.courseInfo.payType === 1 || this.courseInfo.isBuy || item.type==1) {
        // TODO: 跳转到课程学习界面
        //uni.navigateTo({
        //  url: "/pages/course/learningCourse?courseId=" + this.courseId,
        //}); 
        item.playing = true
        item.author = this.courseInfo.author
        this.currentCatalogue = item
        if (this.currentCatalogue.contentType === 0) {
          this.$refs.videoRef.chooseCatalogue(item)
        } else {          
          this.$refs.audioRef.chooseCatalogue(item)
        }
        
      }
    },
    cartShow() {		
      this.cartBoxShow = !this.cartBoxShow;
    },
    close() {
    	this.$refs.popup.close()
    },
    async confirm() {
    	await loginServer.login();
    	this.$refs.popup.close()
    },
    openLoginConfirm() {
    	this.$refs.popup.open();
    }
  },
};
</script>

<style lang="scss">
@import "@/style/common.scss";

page {
  background: #fff;

  .course {
    padding-bottom: 100upx;

    .cover {
      width: 100%;
      height: 422upx;
    }

    .title {
      font-weight: bold;
      text-align: center;
      font-size: 36upx;
      margin-top: 32upx;
      color: #333333;
      line-height: 50upx;
    }

    .sub-title {
      margin-top: 8upx;
      text-align: center;
      font-size: 24upx;
      color: #777777;
    }

    .price {
      text-align: center;
      margin-top: 16upx;
      font-size: 32upx;
    }

    .info {
      font-size: 24upx;
      font-weight: 400;
      color: #777777;
      line-height: 34upx;
      margin-top: 24upx;
      margin-bottom: 16upx;
      display: flex;
      flex-direction: row;
      justify-content: space-around;

      .item {
        text-align: left;
        position: relative;
        padding-left: 46upx;
        flex: 1;

        &::before {
          position: absolute;
          content: "";
          display: block;
          border-radius: 100%;
          width: 12upx;
          height: 12upx;
          top: 11upx;
          left: 24upx;
          background: #aaaaaa;
        }
      }
    }

    .info-title {
      height: 88upx;
      padding: 0 165upx;
      display: flex;
      flex-direction: row;
      .tab-item {
        flex: 1;
        text-align: center;
        font-size: 32upx;
        font-weight: 600;
        color: #333333;
        line-height: 88upx;
        &.active {
          color: #ff703f;
          &::after {
            display: block;
            width: 48upx;
            height: 4upx;
            background: #ff703f;
            border-radius: 2upx;
            content: "";
            margin: -10upx auto 0;
          }
        }
      }
    }

    .img-box {
      padding: 30upx 24upx;
      
      ::v-deep img {
        width: 100%;
      }
    }

    .img-item {
      width: 100%;
    }

    .more-box {
      background-color: #f8f8f8;
      padding: 24upx 25upx;

      .more {
        width: 702upx;
        height: 66upx;
        background: #ffffff;
        border-radius: 40upx;
        font-size: 26upx;
        color: #333;
        line-height: 66upx;
        text-align: center;
        justify-content: center;
        display: flex;
        align-items: center;

        .img {
          margin-left: 12upx;
          width: 12upx;
          height: 24upx;
        }
      }
    }
  }
}
</style>
