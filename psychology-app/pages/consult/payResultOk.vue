<template>
  <view class="page">
    <view class="header">
      <view class="header-bg"></view>
    </view>
    <view class="info" v-if="order && order.id">
      <view class="info-1">
        <image :src="order.avatar" class="info-avatar"></image>
        <view class="info-content">
          <text class="name">{{ order.consultName }}</text>
          <text class="info-desc">{{ order.serve.name }}</text>
          <text class="info-mode">{{ order.serve.modeName }}&nbsp;|&nbsp;{{ order.serve.time }}分钟/每次</text>
        </view>
        <text class="info-price">{{ order.serve.price.toFixed(2) }}/</text>
        <text class="info-num">{{ order.serve.num }}次</text>
      </view>
      <view class="info-2">
        <view class="info-image">
          <image :src="order.wxCard" class="info-section"></image>
          <view class="info-code">
            <view class="info-code-left"></view>
            <text class="info-code-text">扫码添加咨询师</text>
            <view class="info-code-right"></view>
          </view>
        </view>
      </view>
    </view>
    <view class="btn">
      <button @tap="toDetail" class="btn-1">
        <text>查看订单</text>
      </button>
      <button @tap="goHome" class="btn-2">
        <text>返回首页</text>
      </button>
    </view>
    <view class="status">
      <image src="/static/consult/pay.png" class="status-bg"></image>
      <view class="status-view">
        <image src="/static/consult/payOk.png" class="status-icon"></image>
        <text class="status-text">订单支付成功</text>
      </view>
    </view>

    <login ref="loginModel" :isNav="1"></login>
  </view>
</template>

<script>
import orderServer from "@/server/consult/order";
import login from "@/components/common/login";

export default {
  components: { login },
  data() {
    return {
      orderNo: '',
      order: {}
    }
  },
  async created() {
    this.orderNo = this.$utils.getParam(location.href, "orderNo")
    console.log(this.orderNo)
    await this.getOrderDetail()
  },
  async mounted() {
    this.userInfo = this.$utils.getUserInfo()
    if (!this.userInfo && await this.$utils.loginCallback()) {
      this.userInfo = this.$utils.getUserInfo()
    }

    if (!await this.$utils.checkLogin()) {
      return this.openLoginConfirm()
    }
  },
  methods: {
    async getOrderDetail() {
      this.order = await orderServer.getOrderDetailByNo(this.orderNo);
      console.log(this.order)
    },
    async toDetail() {
      if (!await this.$utils.checkLogin()) {
        return this.openLoginConfirm()
      }

      uni.navigateTo({
        url: "/pages/consult/orderInfo?id=" + this.order.id,
      });
    },
    goHome() {
      uni.navigateTo({
        url: '/pages/consult/index'
      });
    },
    // 登录
    openLoginConfirm() {
      this.$refs.loginModel.open()
    }
  }
}
</script>

<style scoped>
.page {
  background-color: rgba(248,248,248,1.000000);
  position: relative;
  width: 750upx;
  height: 1624upx;
  overflow: hidden;
  text-align: center;
}
.header {
  width: 750upx;
  height: 90upx;
  background: linear-gradient(133deg, #FF8C65 0%, #FF6C39 100%);
  display: flex;
  flex-direction: column;
}
.header-bg {
  background-color: rgba(221,68,17,1.000000);
  border-radius: 10upx;
  width: 686upx;
  height: 20upx;
  display: flex;
  flex-direction: column;
  margin: 50upx 0 20upx 32upx;
}
.info {
  background-color: rgba(255,255,255,1.000000);
  border-radius: 4upx 4upx 0upx 0upx;
  height: 540upx;
  width: 654upx;
  display: flex;
  flex-direction: column;
  margin: 304upx 48upx 0 48upx;
}
.info-1 {
  /*width: 590upx;*/
  height: 130upx;
  display: flex;
  margin: 23upx 0 0 32upx;
}
.info-avatar {
  width: 130upx;
  height: 130upx;
  border-radius: 84upx;
}
.info-content {
  width: 270upx;
  height: 127upx;
  display: flex;
  flex-direction: column;
  /*justify-content: space-between;*/
  margin: 2upx 0 0 16upx;
  text-align: left;
}
.name {
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-weight: 600;
  margin-left: 4upx;
}
.info-desc {
  width: 300upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 26upx;
  margin-top: 8upx;
}
.info-mode {
  width: 300upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 26upx;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
  margin-top: 8upx;
}
.info-price {
  color: rgba(255,63,100,1);
  font-size: 32upx;
  font-weight: 600;
  text-align: right;
  margin-left: 16upx;
}
.info-num {
  color: rgba(119,119,119,1);
  font-size: 22upx;
  text-align: right;
  margin-top: 11upx;
}
.info-2 {
  height: 275upx;
  display: flex;
  margin-top: 64upx;
  justify-content: center;
}
.info-image {
}
.info-section {
  width: 226upx;
  height: 226upx;
}
.info-code {
  height: 33upx;
  margin-top: 16upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.info-code-left {
  width: 25upx;
  height: 1upx;
  border: 1upx solid #D8D8D8;
}
.info-code-text {
  height: 33upx;
  color: rgba(119,119,119,1);
  font-size: 24upx;
  margin-left: 4upx;
}
.info-code-right {
  width: 25upx;
  height: 1upx;
  border: 1upx solid #D8D8D8;
  margin-left: 4upx;
}
.btn {
  width: 750upx;
  height: 479upx;
  display: flex;
  flex-direction: column;
}
.btn-1 {
  background-color: rgba(255,112,63,1.000000);
  border-radius: 40upx;
  height: 80upx;
  line-height: 80upx;
  display: flex;
  flex-direction: column;
  width: 654upx;
  margin: 40upx 0 0 48upx;
  color: rgba(255,255,255,1);
  font-size: 28upx;
  font-weight: 600;
}
.btn-2 {
  border-radius: 40upx;
  height: 80upx;
  border: 1px solid rgba(119,119,119,1);
  display: flex;
  flex-direction: column;
  width: 654upx;
  margin: 32upx 0 247upx 48upx;
  line-height: 80upx;
  color: rgba(51,51,51,1);
  font-size: 28upx;
}
.status {
  position: absolute;
  left: 0upx;
  top: 232upx;
  width: 750upx;
  height: 307upx;
  border-radius: 4upx 4upx 0upx 0upx;
}
.status-bg {
  width: 654upx;
  height: 40upx;
  margin-top: 126upx;
}
.status-view {
  background-color: rgba(255,255,255,1.000000);
  position: absolute;
  left: 48upx;
  top: -170upx;
  width: 654upx;
  height: 296upx;
  display: flex;
  flex-direction: column;
}
.status-icon {
  width: 100upx;
  height: 100upx;
  margin: 80upx 0 0 277upx;
}
.status-text {
  width: 230upx;
  height: 50upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 36upx;
  font-weight: 500;
  line-height: 50upx;
  margin: 32upx 0 34upx 219upx;
}
</style>