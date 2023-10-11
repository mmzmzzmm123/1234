<template>
  <view class="user-page">
    <view class="user-info">
      <view class="info">
        <view class="header" @tap="getUserInfo"
          ><img
            class="img"
            :src="userInfo.avatar || '/static/evaluation/header.png'"
          />
        </view>
        <view class="txt">
          <view class="name"
            >{{ userInfo.name
            }}<img
              class="img"
              src="/static/icon/refresh.png"
              v-show="userInfo.name"
              @tap="refreshUser"
            />
          </view>
          <view class="num"
            ><img
              v-show="userInfo.phone"
              class="img"
              src="/static/evaluation/user/phone.png"
            />{{ userInfo.phone }}
          </view>
        </view>
      </view>
      <view class="link-box">
        <view class="item" @tap="toReport">
          <view>我的报告</view>
          <view class="num">{{ reportNum }}</view>
        </view>
        <view class="item" @tap="toConsult">
          <view>我的咨询</view>
          <view class="num"></view>
        </view>
      </view>
    </view>
    <view class="class-box index-margin">
      <view
        class="item"
        v-for="item in classList"
        @tap="
          () => {
            item.callback && item.callback();
          }
        "
      >
        <img class="class-img" :src="item.classPic" />
        <view>{{ item.className }}</view>
      </view>
    </view>
    <view class="un-test-box">
      <view class="box-title">未完成的测评</view>
      <view
        class="order-item"
        v-for="(order, index) in orderList"
        :key="'order' + index"
      >
        <view class="header">
          <view class="title">{{ order.gaugeTitle }}</view>
          <view class="finish-status">{{ finishStatus(order) }}</view>
        </view>
        
        <view class="price">￥{{ order.amount }}</view>
        <view class="buy-time">购买时间：{{ order.createTime }}</view>
        <view class="order-no"
          >订单编号：{{ order.orderId }}
          <img
            class="img"
            src="/static/evaluation/user/copy.png"
            @tap="copyOrderNo(order.orderId)"
          />
        </view>
        <view class="btn" @tap="toTest(order)">去测试</view>
      </view>
      <no-data v-if="orderList.length == 0" :showToClass="true"></no-data>
      <view class="footer" v-else>已经到底了</view>
    </view>
    <evaluation-tab-bar :currentIndex="2"></evaluation-tab-bar>
		<uni-popup ref="popup" type="dialog">
			<uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true"
				@close="close" @confirm="confirm"></uni-popup-dialog>
		</uni-popup>

    <uni-popup ref="popupKf" type="center">
      <view class="box_9">
        <image src="/static/consult/order/card.png" class="box_10"></image>
        <view class="box_11">———— 长按图片保存图片 ————</view>
      </view>
    </uni-popup>
  </view>
</template>
<script>
import utils, { clientTypeObj } from "@/utils/common";
import noData from "@/components/evaluation/noData";
import userServer from "@/server/evaluation/user";
import {
  uniPopup,
  uniPopupDialog
} from '@dcloudio/uni-ui'
import loginServer from "@/server/login"
export default {
  components: { noData,  uniPopup, uniPopupDialog },
  data() {
    return {
      userInfo: {},
      classList: [
        {
          classPic: "/static/evaluation/user/order.png",
          className: "测评订单",
          id: 16,
          callback: this.toOrder,
        },
        {
          classPic: "/static/evaluation/user/coupon.png",
          className: "优惠券",
          id: 36,
        },
        {
          classPic: "/static/evaluation/user/customer-service.png",
          className: "客服帮助",
          id: 72,
          callback: this.toKf,
        },
      ],
      reportNum: 0,
      orderList: [],
      clientTypeObj: clientTypeObj,
      redirectUri:location.href+"?t="+new Date().getTime()
    };
  },
  computed: {
    finishStatus() {
      return (order) => {
        if (!order.finishedNum || !order.gaugeNum) {
          return "0%"
        }
        return (order.finishedNum / order.gaugeNum * 100).toFixed(0) + "%"
      }
    }
  },
  async mounted() {
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback(this.redirectUri)) {
      this.userInfo = utils.getUserInfo()
    }
    if (!await utils.checkLogin()) {
      return this.openLoginConfirm()
    }
    if (this.userInfo) {
      this.orderList = await userServer.getOrderList({
        userId: this.userInfo.userId,
        gaugeStatus: 2
      });
      this.reportNum = await userServer.getOrderListNum(this.userInfo.userId);
    }
  },
  methods: {
    refreshUser(){
      uni.setStorageSync("userInfo", null);
      utils.loginWx(this.redirectUri);
      //添加登录标志,为callback做返回判断
      uni.setStorageSync("wxLogining", true);
    },
    async toReport() {
      uni.navigateTo({url: "/pages/evaluation/report"});
    },
    async toConsult() {
      uni.navigateTo({url: "/pages/consult/index"});
    },
    async toOrder() {
      uni.navigateTo({url: "/pages/evaluation/order"});
    },
    toKf() {
      this.$refs.popupKf.open()
    },
    async toTest(order) {
      // 判断是否已经登录
      if (!await utils.checkLogin()) {
        return this.openLoginConfirm()
      }
      uni.navigateTo({
        url: `/pages/evaluation/question?productId=${order.gaugeId}&orderId=${order.id}`,
      });
    },
    copyOrderNo(orderNo) {
      var input = document.createElement("input");
      document.body.appendChild(input);
      input.setAttribute("value", orderNo);
      input.select();
      document.execCommand("copy"); // 执行浏览器复制命令
      if (document.execCommand("copy")) {
        document.execCommand("copy");
        uni.showToast({
          icon: "error",
          title: "内容复制成功",
        });
      }
      document.body.removeChild(input);
    },
    // 点击头像
    async getUserInfo() {
      if (!await utils.checkLogin()) {
        await utils.loginWx(this.redirectUri);
        return false;
      }
      return true;
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
  background-color: #f8f8f8;

  .user-info {
    width: 750upx;
    height: 386upx;
    background-color: #ffffff;
    padding: 42upx 30upx;
    box-sizing: border-box;

    .info {
      display: flex;
      flex-direction: row;
      margin-bottom: 48upx;

      .header {
        width: 106upx;
        height: 106upx;
        box-shadow: 0 8upx 32upx 0 rgba(188, 167, 167, 0.4);
        border: 2upx solid #ffffff;
        border-radius: 100%;
        margin-right: 24upx;

        .img {
          border-radius: 100%;
        }
      }

      .txt {
        flex: 1;

        .name {
          font-size: 32upx;
          font-weight: 600;
          color: #333333;
          line-height: 45upx;
          display: flex;
          flex-direction: row;
          align-items: center;
          margin: 8upx 0 12upx;

          .img {
            width: 32upx;
            height: 27upx;
            margin-left: 16upx;
          }
        }

        .num {
          font-size: 28upx;
          font-weight: 400;
          color: #777777;
          line-height: 40upx;
          display: flex;
          flex-direction: row;
          align-items: center;

          .img {
            width: 40upx;
            height: 40upx;
            margin-right: 12upx;
          }
        }
      }
    }

    .link-box {
      display: flex;
      flex-direction: row;

      .item {
        width: 336upx;
        height: 164upx;
        background-image: url("/static/evaluation/user/consulting-service.png");
        background-size: 100% 100%;
        padding: 30upx 32upx;
        box-sizing: border-box;
        font-size: 32upx;
        font-weight: 500;
        line-height: 45upx;
        color: #40c2b7;

        &:first-child {
          margin-right: 26upx;
          background-image: url("/static/evaluation/user/report.png");
          color: #e2724c;
        }

        .num {
          font-size: 30upx;
          font-weight: 400;
          line-height: 42upx;
        }
      }
    }
  }

  .class-box {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    text-align: center;
    font-size: 26upx;
    margin: 28upx auto 24upx;
    width: 690upx;
    background: #ffffff;
    border-radius: 16upx;
    padding: 32upx 0;

    .class-img {
      width: 56upx;
      height: 56upx;
    }
  }

  .un-test-box {
    width: 690upx;
    margin: 24upx auto;
    background: #ffffff;
    border-radius: 16px;
    padding: 0 40upx 36upx;
    box-sizing: border-box;

    .box-title {
      line-height: 102upx;
      font-size: 30upx;
      font-weight: 600;
      color: #333333;
      border-bottom: 1px solid rgba(204, 204, 204, 0.6);
      position: relative;
      padding-left: 22upx;

      &::before {
        width: 10upx;
        height: 32upx;
        background: #ff703f;
        border-radius: 5upx;
        content: "";
        position: absolute;
        left: 0;
        top: 35upx;
      }
    }

    .order-item {
      border-bottom: 1px solid #ccc;
      margin: 30upx 0;

      .header {
        display: flex;  
        justify-content: space-between;
        font-size: 32upx;
        font-weight: 600;
        color: #333333;
        .title {
          
          line-height: 45upx;
          margin-bottom: 14upx;
        }
      }
      

      .price {
        font-size: 32upx;
        font-weight: 600;
        color: #ff3f64;
        line-height: 45upx;
        margin-bottom: 16upx;
      }

      .buy-time,
      .order-no {
        font-size: 24upx;
        font-weight: 400;
        color: #aaaaaa;
        line-height: 40upx;
        margin-bottom: 12upx;
        align-items: center;
        display: flex;

        .img {
          width: 28upx;
          height: 28upx;
          border-radius: 0;
          margin-left: 12upx;
        }
      }

      .btn {
        width: 611upx;
        height: 88upx;
        text-align: center;
        line-height: 88upx;
        background: rgba(255, 112, 63, 0.08);
        border-radius: 44upx;
        font-size: 32upx;
        font-weight: 400;
        color: #ff703f;
        margin: 30upx auto 28upx;
      }
    }
  }

  .footer {
    &::before,
    &::after {
      left: 177upx;
    }

    &::after {
      left: unset;
      right: 177upx;
    }
  }
  .box_9 {
    text-align: center;
    .box_10 {
      width: 200px;
      height: 200px;
    }
    .box_11 {
      margin-top: 20upx;
      font-size: 28rpx;
      color: #FFFFFF;
    }
  }
}
</style>
