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
    <view class="img-box" v-show="currentItemIndex == 1">
      <view
        class="catalogue-item" @tap="catalogueItemClick(catalogueItem)"
        :class="{ 'play-item': catalogueItem.enabled }"
        v-for="(catalogueItem, index) in catalogueList"
      >
        <view class="item-content">
          <view class="content-title"
            ><span v-if="catalogueItem.enabled" class="try">试听</span
            ><span class="text txt-overflow">{{ index + 1 }}、{{ catalogueItem.title }}</span></view
          >
          <view class="content-time">
            <image
              class="headset-icon"
              src="/static/curriculum/product/headset.png"
            ></image
            >{{ catalogueItem.time }}</view
          >
        </view>
        <view class="item-play">
          <image
            class="play-icon"
            :src="
              catalogueItem.enabled
                ? '/static/curriculum/product/play.png'
                : '/static/curriculum/product/lock.png'
            "
          ></image>
        </view>
      </view>
    </view>
    <view class="more-box">
      <navigator url="/pages/curriculum/class" open-type="redirect" class="more"
        >查看更多课程 <img class="img" src="/static/more.png" />
      </navigator>
    </view>
    <!-- 底部操作菜单 -->
    <view class="page-bottom">
      <navigator
        url="/pages/curriculum/index"
        open-type="redirect"
        class="p-b-btn"
      >
        <image class="index-icon" src="/static/curriculum/menu.png"></image>
        <text>首页</text>
      </navigator>
      <view class="start-test" @tap="startTest">立即购买</view>
    </view>
    <view class="create-order-mask" v-show="payBoxShow">
      <view class="order-info">
        <view class="close-icon" @tap="payBoxShow = !payBoxShow"></view>
        <view class="product-info">
          <view class="info-box">
            <img class="img left" :src="productInfo.listShowPicture" />
            <view class="right">
              <view class="name txt-overflow txt-overflow-line2">
                {{ productInfo.title }}</view
              >
              <view class="num">{{ productInfo.gaugeNum }}人已测</view>
              <view class="price"
                ><span class="icon">￥</span>{{ productInfo.price }}</view
              >
            </view>
          </view>
          <view class="coupon-box">
            <view class="coupon-info">
              <img class="icon" src="/static/curriculum/icon/coupon.png" />
              <span>优惠券</span>
            </view>
            <view class="coupon-cue"
              >无可用优惠券<img
                class="img"
                src="/static/curriculum/icon/more.png"
            /></view>
          </view>
        </view>
        <view class="price-box"
          >合计：<span class="price"
            ><span class="icon">￥</span>{{ productInfo.price }}</span
          >
        </view>
        <view class="submit-btn" @tap="submitPay">确认支付</view>
        <view class="cue-txt"
          >该款项仅供当次测试；可重复购买，购买成功后不予退款</view
        >
      </view>
    </view>
  </view>
</template>

<script>
import utils from "@/utils/common";
import productServer from "@/server/curriculum/product";
export default {
  data() {
    return {
      productInfo: {},
      payBoxShow: false,
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
    this.productId = parseInt(utils.getParam(location.href, "id"));
    this.productInfo = await productServer.getProductInfo(this.productId);
    this.payBoxShow = utils.getParam(location.href, "payOrder") == 1;
  },
  methods: {
    catalogueItemClick(item){
      if(item.enabled){
        uni.navigateTo({
          url:
            "/pages/curriculum/learningCourse?productId=" +
            this.productId,
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
    startTest() {
      this.payBoxShow = true;
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

    .bg-line {
      height: 20upx;
      background-color: #f8f8f8;
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
      margin: 30upx 24upx;
      .catalogue-item {
        height: 134upx;
        border-bottom: 1px solid #ccc;
        font-size: 28upx;
        display: flex;
        flex-direction: row;
        align-items: center;
        color: #777777;
        overflow: hidden;
        &.play-item {
          color: #000;
          font-weight: 600;
        }
        .item-content {
          flex: 1;
          .content-title {
            margin-bottom: 19upx;
			display: flex;
			flex-direction: row;
			.text{
				flex: 1;
			}
          }
          .content-time {
            .headset-icon {
              width: 24upx;
              height: 24upx;
              margin-right: 8upx;
            }
          }
        }
        .item-play {
          width: 32upx;
          height: 32upx;
          margin-right: 16upx;
          .play-icon {
            width: 32upx;
            height: 32upx;
          }
        }
        .try {
          margin-right: 16upx;
          text-align: center;
          line-height: 34upx;
          display: inline-block;
          width: 76upx;
          height: 34upx;
          background: #8990a7;
          border-radius: 4upx;
          font-size: 22upx;
          color: #fff;
        }
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

    /* 底部操作菜单 */
    .page-bottom {
      position: fixed;
      bottom: 0upx;
      display: flex;
      justify-content: left;
      align-items: center;
      width: 750upx;
      height: 100upx;
      background-color: #fff;

      .p-b-btn {
        width: 128upx;
        display: flex;
        flex-direction: column;
        align-items: center;
        font-size: 20upx;

        .index-icon {
          width: 48upx;
          height: 48upx;
        }
      }

      .start-test {
        width: 600upx;
        height: 80upx;
        line-height: 80upx;
        background: #ff703f;
        border-radius: 40upx;
        text-align: center;
        font-size: 28upx;
        color: #ffffff;
        margin: 9upx 24upx;
        margin-left: 0;
      }
    }
  }

  .create-order-mask {
    width: 100vw;
    height: 100vh;
    background: rgba(51, 51, 51, 0.5);
    position: fixed;
    left: 0;
    top: 0;

    .order-info {
      position: absolute;
      bottom: 0;
      left: 0;
      height: 580upx;
      width: 100vw;
      background-color: #fff;

      .close-icon {
        background-image: url("/static/curriculum/icon/close.png");
        background-size: 100% 100%;
        width: 30upx;
        height: 30upx;
        position: absolute;
        top: 23upx;
        right: 29upx;
      }

      .product-info {
        margin-top: 80upx;
        width: 702upx;
        height: 257upx;
        background: #ffffff;
        box-shadow: 0px 2px 10px 0px rgba(175, 45, 0, 0.1);
        border-radius: 12upx;
        padding: 24upx 32upx 0;
        box-sizing: border-box;

        .info-box {
          display: flex;
          flex-direction: row;

          .left {
            width: 148upx;
            height: 168upx;
            border-radius: 8upx;
          }

          .right {
            flex: 1;
            padding-left: 32upx;

            .name {
              height: 84upx;
              font-size: 30upx;
              font-weight: 600;
              color: #333333;
              line-height: 42upx;
              margin-bottom: 16upx;
            }

            .num {
              font-size: 24upx;
              font-weight: 400;
              color: #777777;
              line-height: 33upx;
            }

            .price {
              font-size: 28upx;
              font-weight: 500;
              color: #ff3f64;
              line-height: 30upx;
              text-align: right;
              margin-top: 0;

              .icon {
                font-size: 22upx;
              }
            }
          }
        }

        .coupon-box {
          width: 100%;
          height: 65upx;
          display: flex;
          flex-direction: row;
          justify-content: space-between;
          align-items: center;
          line-height: 65upx;

          .coupon-info {
            display: flex;
            flex-direction: row;
            font-size: 24upx;
            font-weight: 400;
            color: #777777;
            align-items: center;

            .icon {
              width: 27upx;
              height: 24upx;
              border-radius: 0;
              margin-right: 10upx;
            }
          }

          .coupon-cue {
            font-size: 24upx;
            font-weight: 400;
            color: #333333;
            text-align: right;

            .img {
              margin-left: 12upx;
              width: 12upx;
              height: 24upx;
            }
          }
        }
      }

      .price-box {
        padding-right: 56upx;
        margin: 16upx 0 40upx;
        text-align: right;
        font-size: 24upx;
        font-weight: 500;
        color: #333333;

        .price {
          font-size: 36upx;
        }
      }

      .submit-btn {
        width: 598upx;
        height: 80upx;
        background: #ff703f;
        border-radius: 40upx;
        font-size: 28upx;
        font-weight: 600;
        color: #ffffff;
        line-height: 80upx;
        text-align: center;
        margin: 0 auto;
      }

      .cue-txt {
        text-align: center;
        font-size: 22upx;
        font-weight: 400;
        color: #777777;
        line-height: 30upx;
        margin-top: 24upx;
      }
    }
  }
}
</style>
