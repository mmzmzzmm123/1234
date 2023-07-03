<template>
  <view class="page">
    <view class="header-main">
      <view class="header-nav">
        <uni-nav-bar left-icon="closeempty" right-icon="more-filled" :border="false" title="订单详情" @clickLeft="back"/>
      </view>
      <view class="header-time" v-if="order.status === 0">
        <image src="/static/consult/order/time.png" class="header-icon"/>
        <view class="title">订单待支付，剩余<uni-countdown class="countdown" color="#FF703F" splitorColor="#FF703F" :show-day="false" :minute="14" :second="59"/></view>
      </view>
      <view class="header-time" v-else-if="order.status === 1">
        <image src="/static/consult/order/dzx.png" class="header-icon"/>
        <view class="title">订单待咨询</view>
      </view>
    </view>
    <view class="info">
      <view class="info-content">
        <text class="info-title">订单编号</text>
        <text class="info-col">{{ order.orderNo }}</text>
      </view>
      <view class="info-content">
        <text class="info-title">下单时间</text>
        <text class="info-col">{{ order.createTime }}</text>
      </view>
      <view class="info-content">
        <text class="info-title">咨询师姓名</text>
        <text class="info-col">{{ order.userName }}</text>
      </view>
      <view class="info-content">
        <text class="info-title">咨询次数</text>
        <text class="info-col">1</text>
      </view>
      <view class="info-content">
        <text class="info-title">价格</text>
        <text class="info-col">¥{{ order.amount }}</text>
      </view>
      <view class="info-content">
        <text class="info-title">需付款</text>
        <text class="info-col">¥{{ order.amount }}</text>
      </view>
      <view class="info-content">
        <text class="info-title">咨询时长</text>
        <text class="info-col">{{ order.time }}分钟</text>
      </view>
      <view class="info-content">
        <text class="info-title">咨询方式</text>
        <text class="info-col">{{ order.serveName }}</text>
      </view>
      <view class="info-content">
        <text class="info-title">预约记录</text>
        <text class="info-col">{{ order.timeStart ? order.timeStart.substr(0, 16) + '-' + order.timeEnd.substr(11, 5) : '-'}}</text>
      </view>
    </view>
    <view class="footer" v-if="order.status === 0 || order.status === 1">
      <button @tap="toBuy" class="button-buy">
        <text class="button-text">{{ order.status === 0 ? '立即支付' : '去预约'}}</text>
        <text class="button-price" v-if="order.status === 0">
          <text class="button-price-unit">¥</text>
          <text class="button-price-num">{{ order.amount }}</text>
        </text>
      </button>
    </view>
  </view>
</template>
<script>
import utils from "@/utils/common";
import loginServer from '@/server/login'
import orderServer from "@/server/consult/order";
export default {
  data() {
    return {
      orderId: 0,
      orderPayTime: "00:00",
      order: {},
    };
  },
  async created() {
    this.orderId = utils.getParam(location.href, "id")
    await this.getOrderInfo()
  },
  async mounted() {
    this.userInfo = uni.getStorageSync("userInfo") ? JSON.parse(uni.getStorageSync("userInfo")) : undefined;

    if (!this.userInfo && await utils.loginCallback(this.redirectUri)) {
      this.userInfo = uni.getStorageSync("userInfo") ? JSON.parse(uni.getStorageSync("userInfo")) : undefined;
    }
    if (!this.userInfo) {
      this.openLoginConfirm()
    }
  },
  methods: {
  	back() {
  		uni.navigateTo({
  		  url: "/pages/consult/order?status=" + 0,
  		});
  	},
    // 登录
    async confirmLogin () {
      await loginServer.login();
      this.$refs.popup.close()
    },
    closeLoginConfirm() {
      this.$refs.popup.close()
    },
    openLoginConfirm() {
      this.$refs.popup.open()
    },
    // order
    async getOrderInfo() {
      this.order = await orderServer.getOrderInfo(this.orderId);
    },
    toBuy() {
      uni.navigateTo({
        url: "/pages/consult/orderConfirm?id=" + this.order.serveId + "&orderId=" + this.order.id + "&type=" + this.order.status,
      });
    },
  },
};
</script>
<style lang="scss">
.page {
  background-color: rgba(248,248,248,1.000000);
  height: 1624upx;
  position: relative;
}
.header-main {
  display: flex;
  flex-direction: column;
}
.header-nav {
  height: 88upx;
}
.header-time {
  margin: 32upx 32upx 16upx 32upx;
  height: 50upx;
  display: flex;
}
.header-icon {
  width: 36upx;
  height: 36upx;
  margin-top: 7upx;
  margin-right: 14upx;
}
.title {
  display: flex;
  color: rgba(51,51,51,1);
  font-size: 36upx;
  font-weight: 500;
}
.countdown {
  margin-left: 20upx;
}
.info {
  box-shadow: 0px 4px 28px 0px rgba(119,119,119,0.060000);
  background-color: rgba(255,255,255,1.000000);
  border-radius: 12upx;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 16upx;
  padding: 32upx;
}
.info-content {
  height: 60upx;
  display: flex;
  justify-content: space-between;
}
.info-title {
  color: rgba(119,119,119,1);
  font-size: 26upx;
}
.info-col {
  color: rgba(51,51,51,1);
  font-size: 26upx;
}
.footer {
  text-align: center;
  width: 100%;
  bottom: 20upx;
  position: fixed;
}
.button-buy {
  background-color: rgba(255,112,63,1.000000);
  color: rgba(255,255,255,1.000000);
  font-weight: 600;
  border-radius: 40upx;
  height: 80upx;
  width: 598upx;
  display: flex;
  align-items: center;
  justify-content: center;
}
.button-text {
  color: rgba(255,255,255,1);
  font-size: 28upx;
}
.button-price {
  margin-left: 16upx;
}
.button-price-unit {
  font-size: 22upx;
}
.button-price-num {
  color: rgba(255,255,255,1.000000);
  font-size: 32upx;
}
</style>
