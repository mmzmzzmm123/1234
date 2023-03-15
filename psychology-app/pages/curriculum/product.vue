<template>
  <view class="product">
    <image class="cover" :src="productInfo.headPicture"></image>
    <view class="title">{{ productInfo.title }}</view>
    <view class="sub-title">张三</view>
    <view class="price"
      ><span class="icon">￥</span>{{ productInfo.price }}</view
    >
    <view class="info">
      <view class="item">{{ productInfo.gaugeNum }}节课</view>
      <view class="item">7小时课时</view>
      <view class="item">{{ productInfo.num || 10 }}人已学</view>
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
        src="/static/curriculum/product/11.png"
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
    <cartBox @closeCart="cartShow" v-if="cartBoxShow" :productInfo="productInfo"></cartBox>

  </view>
</template>

<script>
import utils from "@/utils/common";
import productServer from "@/server/curriculum/product";
import cartTabBar from "@/components/curriculum/cartTabBar";
import cartBox from "@/components/curriculum/cartBox";
import customCatalogueList from "@/components/curriculum/catalogueList";
export default {
  components: { cartTabBar,cartBox,customCatalogueList },
  data() {
    return {
      productInfo: {},
      cartBoxShow: false,
      productId: 0,
      currentItemIndex: 0,
      catalogueList: [
        {
          title:
            "课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1",
          time: "10:33",
          enabled: true,
        },
        { title: "课程2", time: "15:12" },
        { title: "课程3", time: "09:48" },
      ],
    };
  },
  async created() {
    this.productId = parseInt(utils.getParam(location.href, "productId")||utils.getParam(location.href, "id"));
    this.productInfo = await productServer.getProductInfo(this.productId)||{};
    this.cartBoxShow = utils.getParam(location.href, "payOrder") == 1;
  },
  methods: {
    catalogueItemClick(item) {
      if (item.enabled) {
        uni.navigateTo({
          url: "/pages/curriculum/learningCourse?productId=" + this.productId,
        });
      }
    },
    async submitPay() {
      this.userInfo = uni.getStorageSync("userInfo");
      if (!this.userInfo) {
        uni.navigateTo({
          url:
            "/pages/curriculum/login?callbacktype=2&productId=" +
            this.productId,
        });
        return;
      }
      let res = await productServer.orderPay(
        this.productInfo.id,
        this.productInfo.price
      );
      if (res.code == 200) {
        uni.setStorageSync("gaugeDes", this.productInfo.gaugeDes);
        uni.navigateTo({
          url: `/pages/curriculum/testBefore?productId=${this.productId}&orderId=${res.data}`,
        });
      } else if (res == 401) {
        uni.navigateTo({
          url:
            "/pages/curriculum/login?callbacktype=2&productId=" +
            this.productId,
        });
      }
    },
    cartShow() {
      this.cartBoxShow = !this.cartBoxShow;
    },
    toHome() {
      uni.navigateTo({
        url: "/pages/curriculum",
      });
    },
  },
};
</script>

<style lang="scss">
@import "@/style/common.scss";

page {
  background: #fff;

  .product {
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
