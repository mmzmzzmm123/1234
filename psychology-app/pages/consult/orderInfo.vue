<template>
  <view class="page">
    <view class="header">
      <view class="nav">
        <uni-nav-bar fixed="true" left-icon="closeempty" right-icon="more-filled" :border="false" title="订单详情" @clickLeft="back"/>
      </view>
      <view class="status-icon">
        <template v-if="order.status === '0'">
          <image src="/static/consult/order/countdown.png" class="status-img"></image>
          <text class="status-text">订单待支付，剩余</text>
          <uni-countdown :font-size="20" class="countdown" color="#FF703F" splitorColor="#FF703F" :show-day="false" :minute="order.endPay" :second="59"/>
        </template>
        <template v-if="order.status === '1'">
          <image src="/static/consult/order/padding.png" class="status-img"></image>
          <text class="status-text">订单进行中</text>
        </template>
        <template v-if="order.status === '2'">
          <image src="/static/consult/order/ok.png" class="status-img"></image>
          <text class="status-text">订单已完成</text>
        </template>
      </view>
    </view>

    <template v-if="order && order.serve">
      <view class="info">
        <view class="info-header">
          <view class="block-icon"></view>
          <text class="info-header-desc">{{ order.serve.modeName }}&nbsp;|&nbsp;{{ order.serve.time }}分钟</text>
          <text class="info-header-title">服务详情</text>
        </view>
        <view class="info-content">
          <image :src="order.avatar" class="info-avatar"></image>
          <view class="info-box">
            <view class="info-box-1">
              <text class="info-name">{{ order.consultName }}</text>
              <image v-if="order.status !== '0'" @tap="openWx" src="/static/consult/order/wx.png" class="info-wx"></image>
              <text v-if="order.status !== '0'" @tap="openWx" class="info-card">名片</text>
            </view>
            <text class="info-serve">{{ order.serveName }}</text>
            <text class="info-end">有效期至：{{ order.serve.end ? order.serve.end : '永久有效'}}</text>
          </view>
          <view class="info-box-2">
            <text class="info-unit">¥</text>
            <text class="info-price">{{ order.amount.toFixed(2) }}/</text>
            <text class="info-times">{{ order.serve.num }}次</text>
          </view>
        </view>
        <view class="info-divide">
          <view class="info-divide-view"></view>
        </view>
        <view class="info-bottom" v-if="order.status !== '0'">
          <view class="info-last">
            <text class="info-last-title">剩余咨询次数：</text>
            <text class="info-last-val">{{ order.num }}&nbsp;次</text>
          </view>
          <view class="info-last" v-if="order.status === '1' && order.orderTime">
            <text class="info-last-title">下次咨询时间：</text>
            <text class="info-last-val">{{ order.orderTime }}</text>
          </view>
          <button @tap="toBuy" class="pay-btn" v-if="order.status === '1' && order.num > 0 && !order.orderTime">
            去预约
          </button>
        </view>
      </view>
      <view class="serve" v-if="order.status !== '0'">
        <view class="serve-header">
          <view class="block-icon"></view>
          <text class="serve-header-title">服务详情</text>
        </view>
        <view class="serve-item" v-if="order.items && order.items.length > 0">
          <view class="cu-timeline">
            <view class="cu-item" v-for="(item, idx) in order.items">
              <view class="cu-item-icon"></view>
              <view class="cu-item-content">
                <view class="cu-item-time">{{ item.day + ' ' + item.timeStart + ' ' + item.timeEnd }}</view>
                <view class="cu-item-name">第{{ idx + 1 }}次咨询</view>
                <view class="cu-item-status">{{ item.status === '0' ? '待咨询' : '已完成' }}</view>
              </view>
            </view>
          </view>
        </view>
        <view class="empty" v-else>
          <image src="/static/consult/order/empty.png" class="empty-img"></image>
          <text class="empty-text">暂无预约记录</text>
        </view>
      </view>
      <view class="order">
        <view class="order-header">
          <view class="block-icon"></view>
          <text class="order-header-title">订单信息</text>
        </view>
        <view class="order-info">
          <text class="order-info-label">订单编号</text>
          <view class="order-info-wapper">
            <text>{{ order.orderNo }}</text>
            <image @tap="copy" src="https://lanhu.oss-cn-beijing.aliyuncs.com/SketchPngfe34227c184a7a0a93487a4e8799ddb4ba3ae96b8725b0a6a59e1676a99644b3" class="order-copy"></image>
          </view>
        </view>
        <view class="order-info">
          <text class="order-info-label">下单时间</text>
          <text class="order-info-wapper">{{ order.createTime }}</text>
        </view>
        <view class="order-info" v-if="order.payTime">
          <text class="order-info-label">支付时间</text>
          <text class="order-info-wapper">{{ order.payTime }}</text>
        </view>
        <view class="order-info">
          <text class="order-info-label">订单总价</text>
          <text class="order-info-wapper">¥{{ order.amount.toFixed(2) }}</text>
        </view>
        <view class="order-info" v-if="order.status !== '0'">
          <text class="order-info-label">实际付款</text>
          <text class="order-info-wapper">¥{{ order.pay.toFixed(2) }}</text>
        </view>
        <view class="order-info" v-if="order.status === '0'">
          <text class="order-info-label">需付款</text>
          <text class="order-info-wapper">¥{{ order.pay.toFixed(2) }}</text>
        </view>
      </view>

      <view class="footer" v-if="order.status === '0'">
        <button @tap="toBuy" class="button-buy">
          <text class="button-text">立即支付</text>
          <text class="button-price">
            <text class="button-price-unit">¥</text>
            <text class="button-price-num">{{ order.amount && order.amount.toFixed(2) }}</text>
          </text>
        </button>
      </view>
    </template>

    <cart-box ref="cartBox" :dateList="dateList" :works="works" @doOk="doOk" @cancel="cancel"/>

    <wx-card v-if="showCard" :data="consult" @close="closeWx"/>

    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>
  </view>
</template>
<script>
import utils from "@/utils/common";
import loginServer from '@/server/login'
import formatTime from '@/utils/formatTime.js'
import indexServer from '@/server/consult/index'
import orderServer from "@/server/consult/order";
import cartBox from '@/components/consult/cartBox'
import wxCard from '@/components/consult/wxCard'
import { getPaySign, wxPay } from "@/server/wxApi"

export default {
  components: { cartBox, wxCard },
  data() {
    return {
      showCard: false,
      orderId: 0,
      serveId: 0,
      // car
      time: -1,
      workId: 0,
      workName: '',
      works: [],
      dateList: [],
      // car end
      orderPayTime: "00:00",
      order: {},
      consult: {},
    };
  },
  async created() {
    this.orderId = utils.getParam(location.href, "id")
    await this.getOrderDetail()
    await this.getConsultInfoByServe()
    this.getDates()
  },
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
    openWx() {
      this.showCard = true
    },
    closeWx() {
      this.showCard = false
    },
    copy() {
      uni.setClipboardData({
        data: this.order.orderNo,
        success: () => {
          uni.showToast({
            icon: "none",
            title: "复制成功",
          });
        }
      })
    },
    // order
    async getOrderDetail() {
      this.order = await orderServer.getOrderDetail(this.orderId);
      if (this.order.status === '0') {
        this.order.endPay = this.remainTime(this.order.updateTime)
      }
    },
    remainTime(orderTime) {
      if (new Date().getTime() < new Date(orderTime).getTime() + 15 * 60 * 1000) {
        // 下单不超过15分钟
        return formatTime.getMinutes(orderTime)
      } else {
        return 0
      }
    },
    async getDates() {
      this.dateList = await indexServer.getDates(7);
    },
    // cartBox start
    async getConsultInfoByServe() {
      const res = await orderServer.getConsultInfoByServe(this.order.consultId, this.order.serveId)
      this.consult = res.consult
      this.works = res.works
    },
    toBuy() {
      console.log(this.order)
      if (this.order.payStatus === '2' && this.order.serve.end && new Date(this.order.serve.end) < new Date()) {
        return uni.showToast({
          icon: "none",
          title: '订单已超时',
        });
      }
      this.$refs.cartBox.open()
    },
    cancel() {
      this.workId = 0
    },
    // cartBox end
    async doOk(workId, time, workName) {
      if (!utils.checkLogin()) {
        return this.openLoginConfirm()
      }

      console.log(workName)
      this.time = time
      this.workId = workId
      this.workName = workName
      if (this.order.status === '1') { // 去预约
        if (workId === 0) {
          return uni.showToast({
            icon: "none",
            title: "请选择时间进行预约",
          })
        } else {
          // 去预约
          await this.doConsult()
        }
      }

      if (this.order.status === '0') {
        // 支付
        await this.doPay()
      }

      await this.getOrderDetail()
      await this.getConsultInfoByServe()
      this.$refs.cartBox.close()
    },
    async doConsult() {
      const res = await orderServer.doConsult(this.order.id, this.workId, this.time)
      console.log(res)
      if (res === 1) {
        uni.showToast({
          icon: "success",
          title: "预约成功",
        });
      }
    },
    async doPay() {
      console.log(this.order)
      console.log(2222)
      let res = await getPaySign(
          this.userInfo.userId,
          this.order.serve.serveId,
          this.order.serve.price,
          {
            module: 'consult',
            workId: this.workId,
            time: this.time,
            consultId: this.order.consultId,
            orderId: this.order.id
          }
      )

      console.log(res)
      if (res.code == 200) {
        const { appId, timeStamp, nonceStr, packageInfo, paySign, signType } = res.data
        wxPay(res.data, () => {
          uni.showToast({
            icon: "success",
            title: "支付成功",
          });
        }, (msg) => {
          console.log(msg)
          uni.showToast({
            icon: "error",
            title: "支付失败",
          });
        })
      } else {
        uni.showToast({
          icon: "none",
          title: res.msg,
        });
      }
    },
    async toCancel() {
      const res = await orderServer.cancel(this.order.id);
      if (res === 1) {
        uni.showToast({
          icon: "success",
          title: "订单已超时",
        });
      }
      this.back()
    },
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
    }
  },
};
</script>
<style lang="scss">
.page {
  background-color: rgba(248,248,248,1.000000);
  position: relative;
  width: 750upx;
  height: 1624upx;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}
.status-icon {
  //width: 232upx;
  height: 50upx;
  flex-direction: row;
  display: flex;
  //justify-content: space-between;
  margin: 40upx 0 20upx 32upx;
}
.status-img {
  width: 36upx;
  height: 36upx;
  margin-top: 7upx;
}
.status-text {
  color: rgba(51,51,51,1);
  font-size: 36upx;
  font-family: PingFangSC-Medium;
  font-weight: 500;
  display: flex;
  margin-left: 32upx;
  margin-right: 12upx;
}
.info {
  box-shadow: 0px 4px 28px 0px rgba(119,119,119,0.060000);
  background-color: rgba(255,255,255,1.000000);
  border-radius: 16upx;
  //height: 384upx;
  width: 686upx;
  display: flex;
  flex-direction: column;
  margin: -1upx 0 0 32upx;
}
.info-header {
  position: relative;
  width: 654upx;
  height: 45upx;
  margin-top: 32upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
}
.block-icon {
  background-color: rgba(255,112,63,1.000000);
  border-radius: 3upx;
  width: 6upx;
  height: 32upx;
  margin-top: 7upx;
}
.info-header-desc {
  color: rgba(119,119,119,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  margin-top: 4upx;
}
.info-header-title {
  position: absolute;
  left: 32upx;
  top: 0upx;
  width: 622upx;
  color: rgba(51,51,51,1);
  font-size: 32upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
}
.info-content {
  position: relative;
  width: 622upx;
  height: 132upx;
  flex-direction: row;
  display: flex;
  margin: 30upx 0 0 32upx;
}
.info-avatar {
  width: 130upx;
  height: 130upx;
  margin-top: 2upx;
  border-radius: 100%;
}
.info-box {
  width: 432upx;
  height: 131upx;
  margin-left: 24upx;
  display: flex;
  flex-direction: column;
}
.info-box-1 {
  flex-direction: row;
  display: flex;
}
.info-name {
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  margin-top: 4upx;
}
.info-wx {
  width: 28upx;
  height: 28upx;
  margin: 9upx 0 0 20upx;
}
.info-card {
  width: 44upx;
  height: 30upx;
  overflow-wrap: break-word;
  color: rgba(170,170,170,1);
  font-size: 22upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 30upx;
  margin-top: 8upx;
}
.info-box-2 {
  position: absolute;
  right: 10upx;
}
.info-unit {
  width: 14upx;
  color: rgba(255,63,100,1);
  font-size: 22upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  margin-top: 11upx;
}
.info-price {
  color: rgba(255,63,100,1);
  font-size: 32upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
}
.info-times {
  color: rgba(119,119,119,1);
  font-size: 22upx;
  font-family: PingFangSC-Regular;
  margin-top: 11upx;
}
.info-serve {
  width: 270upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
  margin-top: 4upx;
}
.info-end {
  width: 354upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
  margin-top: 8upx;
}
.info-divide {
  width: 622upx;
  height: 1upx;
  display: flex;
  flex-direction: row;
  margin: 24upx 0 0 32upx;
}
.info-divide-view {
  background-color: rgba(230,230,230,1.000000);
  width: 622upx;
  height: 1upx;
  display: flex;
  flex-direction: column;
}
.info-bottom {
  position: relative;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  align-content: flex-start;
  justify-content: center;
  padding-left: 32upx;
  height: 120upx;
  .info-last {
    height: 33upx;
    //width: 215upx;
    flex-direction: row;
    display: flex;
    justify-content: space-between;
  }
  .info-last-title {
    width: 168upx;
    color: rgba(51,51,51,1);
    font-size: 24upx;
    font-family: PingFangSC-Regular;
  }
  .info-last-val {
    width: 47upx;
    overflow-wrap: break-word;
    color: rgba(255,112,63,1);
    font-size: 24upx;
    font-family: PingFangSC-Regular;
    font-weight: normal;
    text-align: left;
    white-space: nowrap;
    line-height: 33upx;
  }
  .pay-btn {
    position: absolute;
    right: 32upx;
    color: #FFFFFF;
    width: 176rpx;
    height: 72rpx;
    line-height: 72upx;
    background: #FF703F;
    border-radius: 36rpx;
    font-size: 28rpx;
    font-family: PingFangSC-Semibold, PingFang SC;
    font-weight: 600;
  }
}
.text-wrapper_2 {
  width: 449upx;
  height: 33upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin: 8upx 0 24upx 32upx;
}
.text_14 {
  width: 168upx;
  height: 33upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 24upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 33upx;
}
.text_15 {
  width: 281upx;
  height: 33upx;
  overflow-wrap: break-word;
  color: rgba(255,112,63,1);
  font-size: 24upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: right;
  white-space: nowrap;
  line-height: 33upx;
}
.serve {
  box-shadow: 0px 4px 28px 0px rgba(119,119,119,0.060000);
  background-color: rgba(255,255,255,1.000000);
  border-radius: 16upx;
  //height: 317upx;
  width: 686upx;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: flex-center;
  margin: 16upx 0 0 32upx;
  padding-bottom: 32upx;
}
.serve-header {
  width: 654upx;
  height: 45upx;
  margin-top: 32upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
}
.serve-header-title {
  width: 622upx;
  height: 45upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 32upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  text-align: left;
  white-space: nowrap;
  line-height: 45upx;
}
.serve-item {
  width: 614upx;
  //height: 188upx;
  flex-direction: row;
  display: flex;
  margin: 20upx 0 32upx 40upx;
}

.empty {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.empty-img {
  width: 220upx;
  height: 211upx;
}
.empty-text {
  color: rgba(0,0,0,0.35);
  font-size: 28rpx;
  font-family: PingFangSC-Regular;
  text-align: center;
  margin-top: 6upx;
}

.cu-timeline {
  position: relative;
  display: block;
  background-color: #ffffff;
}
.cu-item {
  padding: 0upx 16upx 16upx 64upx;
  position: relative;
  display: block;
  z-index: 0;
}
.cu-item:not(:last-child)::after {
  content: "";
  display: block;
  position: absolute;
  left: 14upx;
  top: 0;
  z-index: 8;
  width: 1upx;
  height: 100%;
  //border: 1upx dashed #AAAAAA;
  background: linear-gradient(to bottom, #AAAAAA 0%, #AAAAAA 50%,transparent 50%);
  background-size: 2upx 12upx;
  background-repeat: repeat-y;
}
.cu-item-content {
  position: relative;
  //width: 638upx;
  width: 554upx;
}
.cu-item-time {
  color: rgba(119,119,119,1);
  font-size: 24upx;
  font-family: PingFangSC-Regular;
}
.cu-item-name {
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  margin-top: 8upx;
}
.cu-item-status {
  position: absolute;
  right: 10upx;
  top: 10upx;
  color: rgba(119,119,119,1);
  font-size: 24upx;
  font-family: PingFangSC-Regular;
}
.cu-item-icon {
  background-color: rgba(255,255,255,1.000000);
  border-radius: 50%;
  position: absolute;
  left: 0upx;
  top: 0upx;
  width: 20upx;
  height: 20upx;
  border: 2px solid rgba(255,112,63,1);
  display: flex;
  flex-direction: column;
  z-index: 9;
}
.order {
  box-shadow: 0px 4px 28px 0px rgba(119,119,119,0.060000);
  background-color: rgba(255,255,255,1.000000);
  border-radius: 16upx;
  //height: 410upx;
  //width: 686upx;
  display: flex;
  flex-direction: column;
  justify-content: flex-center;
  margin: 16upx 32upx 0 32upx;
}
.order-header {
  margin-top: 32upx;
  display: flex;
}
.order-header-title {
  color: rgba(51,51,51,1);
  font-size: 32upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  text-align: left;
  margin-left: 32upx;
  margin-bottom: 20upx;
}
.order-info {
  height: 37upx;
  line-height: 37upx;
  display: flex;
  justify-content: space-between;
  margin: 0upx 24upx 24upx 24upx;
  font-size: 26upx;
}
.order-info-label {
  color: rgba(119,119,119,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  text-align: left;
}
.order-info-wapper {
  color: #333333;
  text-align: right;
}
.order-copy {
  width: 26upx;
  height: 26upx;
  margin-left: 4upx;
}
.group_4 {
  width: 622upx;
  height: 98upx;
  display: flex;
  flex-direction: row;
  margin: 24upx 0 0 32upx;
}
.list_1 {
  width: 622upx;
  height: 98upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
}
.text-wrapper_4-0 {
  width: 104upx;
  height: 98upx;
  margin-right: 266upx;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.text_25-0 {
  width: 252upx;
  height: 37upx;
  overflow-wrap: break-word;
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
  color: rgba(119,119,119,1);
}
.text_26-0 {
  width: 252upx;
  height: 37upx;
  overflow-wrap: break-word;
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
  margin-top: 24upx;
  color: rgba(119,119,119,1);
}
.text-wrapper_4-1 {
  width: 104upx;
  height: 98upx;
  margin-right: 266upx;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.text_25-1 {
  width: 252upx;
  height: 37upx;
  overflow-wrap: break-word;
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
  color: rgba(51,51,51,1);
}
.text_26-1 {
  width: 252upx;
  height: 37upx;
  overflow-wrap: break-word;
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
  margin-top: 24upx;
  color: rgba(51,51,51,1);
}
.text-wrapper_5 {
  width: 622upx;
  height: 37upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin: 24upx 0 0 32upx;
}
.text_27 {
  width: 104upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
}
.text_28 {
  width: 101upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: right;
  white-space: nowrap;
  line-height: 37upx;
}
.text-wrapper_6 {
  width: 622upx;
  height: 37upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin: 24upx 0 32upx 32upx;
}
.text_29 {
  width: 104upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
}
.info-header-desc0 {
  width: 101upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: right;
  white-space: nowrap;
  line-height: 37upx;
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
