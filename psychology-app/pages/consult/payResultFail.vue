<template>
  <view class="page">
    <view class="header">
      <view class="header-bg"></view>
    </view>
    <view class="content">
      <button @tap="toOrder" class="btn">
        <text>查看订单</text>
      </button>
      <view class="status">
        <image src="/static/consult/payFail.png" class="status-icon"></image>
        <view class="status-text">
          <text class="status-title">支付失败</text>
          <text class="status-info">您可前往“我的-我的订单”再次发起支付</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import utils from "@/utils/common";
import loginServer from '@/server/login';

export default {
  async mounted() {
    // this.userInfo = uni.getStorageSync("userInfo")
    this.userInfo = utils.getUserInfo()

    if (!this.userInfo && await utils.loginCallback(this.redirectUri)) {
      this.userInfo = utils.getUserInfo()
    }
    if (!utils.checkLogin()) {
      return this.openLoginConfirm()
    }
  },
  methods: {
    toOrder() {
      if (!utils.checkLogin()) {
        return this.openLoginConfirm()
      }

      uni.navigateTo({
        url: "/pages/consult/order?status=1",
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
    }
  }}
</script>

<style scoped>
.page {
  background-color: rgba(248,248,248,1.000000);
  position: relative;
  width: 750upx;
  height: 1624upx;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.header {
  width: 750upx;
  height: 232upx;
  background: url(https://lanhu.oss-cn-beijing.aliyuncs.com/SketchPngf11697c30c16890ccc7b85465ec486b5272483ba63a5553b06df2423c8f8da5d) 100% no-repeat;
  background-size: 100% 100%;
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
  margin: 192upx 0 20upx 32upx;
}
.content {
  position: absolute;
  left: 0upx;
  top: 232upx;
  width: 750upx;
  height: 1325upx;
  display: flex;
  flex-direction: column;
}
.btn {
  background-color: rgba(255,112,63,1.000000);
  border-radius: 40upx;
  height: 80upx;
  line-height: 80upx;
  width: 654upx;
  margin: 381upx 0 0 48upx;
  color: rgba(255,255,255,1);
  font-size: 28upx;
  font-weight: 600;
}
.status {
  background-color: rgba(255,255,255,1.000000);
  border-radius: 4upx 4upx 0upx 0upx;
  position: absolute;
  left: 48upx;
  top: -30upx;
  width: 654upx;
  height: 371upx;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.status-icon {
  width: 100upx;
  height: 100upx;
  margin-top: 80upx;
}
.status-text {
  width: 460upx;
  height: 111upx;
  display: flex;
  flex-direction: column;
  text-align: center;
  /*justify-content: space-between;*/
  margin-top: 32upx;
}
.status-title {
  color: rgba(51,51,51,1);
  font-size: 36upx;
  font-weight: 500;
}
.status-info {
  color: rgba(119,119,119,1);
  font-size: 26upx;
  margin-top: 24upx;
}
</style>