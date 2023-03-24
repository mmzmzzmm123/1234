<template>
  <view class="course">
    <image class="cover" :src="courseInfo.headPicture"></image>
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
    <view class="img-box" v-show="currentItemIndex == 0">
      <image
        mode="widthFix"
        class="img-item"
        src="/static/curriculum/course/11.png"
      ></image>
    </view>
    <view class="img-box" v-if="currentItemIndex == 1">
      <customCatalogueList :catalogueList="catalogueList"></customCatalogueList>
    </view>
    <view class="more-box">
      <navigator url="/pages/curriculum/class" open-type="redirect" class="more"
        >查看更多课程 <img class="img" src="/static/icon/more.png" />
      </navigator>
    </view>
    <!-- 底部操作菜单 -->
    <cartTabBar @cartShow="cartShow"></cartTabBar>
    <cartBox @closeCart="cartShow" v-if="cartBoxShow && !this.courseInfo.isBuy " :courseInfo="courseInfo" 
      :redirectUri="redirectUri"></cartBox>
  </view>
</template>

<script>
import utils from "@/utils/common";
import courseServer from "@/server/curriculum/course";
import cartTabBar from "@/components/curriculum/cartTabBar";
import cartBox from "@/components/curriculum/cartBox";
import customCatalogueList from "@/components/curriculum/catalogueList";
export default {
  components: { cartTabBar,cartBox,customCatalogueList },
  data() {
    return {
	  userInfo: {},
      courseInfo: {},
      cartBoxShow: false,
      courseId: 0,
      currentItemIndex: 0,
      catalogueList: [],
      redirectUri:location.href
    };
  },
  async created() {
	this.userInfo = uni.getStorageSync("userInfo");
    this.courseId = parseInt(utils.getParam(location.href, "courseId")||utils.getParam(location.href, "id"));
	uni.showToast({
	  icon: "success",
	  title: "courseId: " + this.courseId + " userId: " + this.userInfo.userId	  
	});
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
      if (item.type==1) {
        uni.navigateTo({
          url: "/pages/curriculum/learningCourse?courseId=" + this.courseId,
        });
      }
    },
    cartShow() {
      this.cartBoxShow = !this.cartBoxShow;
    },
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
