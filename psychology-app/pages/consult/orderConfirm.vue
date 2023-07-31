<template>
  <view class="page">
    <view class="box-bg">
      <uni-nav-bar left-icon="closeempty" right-icon="more-filled" :border="false" title="确认订单" @clickLeft="back"/>
    </view>
    <view class="consult-info">
      <text class="consult-info-title">咨询</text>
      <view class="consult-info-group">
        <image :src="consult.avatar" class="consult-info-avatar"></image>
        <view class="consult-info-right">
          <text class="consult-name">{{ consult.userName }}</text>
          <text class="consult-work">{{ consult.workNum }}人次咨询 | {{ consult.workHours }}年从业经验</text>
          <view class="consult-price">
            <text class="consult-price-ant">¥</text>
            <text class="consult-price-num">{{ serve.price }}</text>
            <text class="consult-price-ext">/1次</text>
          </view>
        </view>
      </view>
      <view class="info-text-wrapper-2">
        <text class="info-text-1">单次咨询时长</text>
        <text class="info-text-2">50分钟</text>
      </view>
      <view class="info-text-wrapper-group2"></view>
      <view class="info-text-wrapper-3">
        <text class="info-text-3">服务数量({{ serve.num }}次咨询)</text>
        <text class="info-text-4">1次</text>
      </view>
    </view>
    <view class="section-time">
      <view class="section-time-group">
        <text class="section-time-group-text1">预约</text>
        <view class="section-time-group3">
          <text class="section-time-text4">咨询方式</text>
          <text class="section-time-text5">{{ serve.name }}</text>
        </view>
        <view class="group_5"></view>
        <view class="section-time-group2">
          <text class="section-time-group-text2">预约时间</text>
          <view class="image-text_1">
            <text class="section-time-group-text3" @tap="open">{{ workName !== '' ? workName : '点击选择预约时间' }} ></text>
          </view>
        </view>
      </view>
      <view class="section-reader">
        <checkbox-group @change="checkboxChange">
          <checkbox class="section-reader-box" value="ok" :checked="checkBox"/>
        </checkbox-group>
        <view class="section-reader-group">
          <text class="section-reader-group-text1">我同意</text>
          <text class="section-reader-group-text2" @tap="openNotice">《心理咨询服务协议》</text>
          <text class="section-reader-group-text3" />
        </view>
      </view>
      <view class="notice">
        <view>预约须知：</view>
        <view>·变更预约说明</view>
        <view>预约人一旦确定预约后，咨询前24小时内不可修改或取消，请预约人提前24小时做好时间安排。咨询师确认咨询后，若有时间变动，同样需提前24小时与预约人沟通确认。</view>
        <view>·爽约/迟到说明</view>
        <view>确定预约后，预约人需准时赴约。若迟到或爽约，将正常收取本咨询费用，咨询服务按原定时间开始，原定时间结束。若咨询师迟到或爽约，咨询师在完成原定时长心理咨询服务基础上，赔偿预约人同等时长的心理咨询服。</view>
        <view>·知情同意</view>
        <view>咨询师为来访者提供的仅为心理健康咨询服务，非医疗服务。如您需要专业医生的诊断，请前往当地精神卫生中心寻求精神科医生的帮助；如果您已在精神卫生医疗机构被诊断精神疾病，您需要知晓心理咨询不能替代药物治疗，您支付的咨询费用是为咨询师在和您咨询过程中所投入的时间和精力付费，并不是为保证疾病治疗效果付费。</view>

      </view>
      
    </view>
    <view class="bottom-view">
      <button @tap="toBuy" class="bottom-btn" :disabled="flag">
        <text class="bottom-text">立即支付</text>
        <view class="bottom-text-wrapper">
          <text class="bottom-text-wrapper-1">¥</text>
          <text class="bottom-text-wrapper-2">{{ serve.price }}</text>
        </view>
      </button>
    </view>

    <uni-popup ref="popupNotice" :mask-click="false">
      <view class="popup-notice">
        <scroll-view class="popup-notice-desc" scroll-y scroll-with-animation>
          <view v-html="notice.noticeContent"></view>
        </scroll-view>
        <button class="popup-notice-close" @tap="closeNotice">我已阅读并同意上述协议</button>
      </view>
    </uni-popup>

    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>

    <cart-box ref="cartBox" :dateList="dateList" :works="works" @doOk="doOk" @cancel="cancel"/>
  </view>
</template>

<script>

import utils from "@/utils/common";
import indexServer from '@/server/consult/index'
import orderServer from "@/server/consult/order";
import loginServer from '@/server/login'
import cartBox from '@/components/consult/cartBox'
import wxJS from "@/server/wxJS.js"
import { getPaySign, wxPay } from "@/server/wxApi"
export default {
  components: { cartBox },
  data() {
    return {
      time: -1,
      workId: 0,
      workName: '',
      flag: false,
      checkBox: false,
      notice: {},
      userInfo: {},
      tabs: [],
      works: [],
      dateList: [],
      serve: {},
      consult: {},
      serveId: 0,
      consultId: 0,
      redirectUri: location.href,
      currentCatalogue: {},
    };
  },
  created() {
    this.serveId = utils.getParam(location.href, "sId")
    this.consultId = utils.getParam(location.href, "cId")
  },
  async mounted() {
    // this.userInfo = uni.getStorageSync("userInfo")
    this.userInfo = utils.getUserInfo()
    if (!utils.checkLogin()) {
      return this.openLoginConfirm()
    }

    if (utils.get('time') && utils.get('workId') && utils.get('workName')) {
      this.time = utils.get('time')
      this.workId = utils.get('workId')
      this.workName = utils.get('workName')
      this.$refs.cartBox.time = this.time
      this.$refs.cartBox.workId = this.workId
      // cartBox
      utils.remove('time')
      utils.remove('workId')
      utils.remove('workName')
    }

    this.getNotice()
    await this.getDates()
    await this.getConsultInfoByServe()
  },
  methods: {
    back() {
      uni.navigateTo({
        url: "/pages/consult/index",
      });
    },
    async getConsultInfoByServe() {
      const res = await orderServer.getConsultInfoByServe(this.consultId, this.serveId)
      this.consult = res.consult
      this.serve = res.serve
      this.works = res.works
    },
    async getNotice() {
      this.notice = await orderServer.getNotice(3);
    },
    async getDates() {
      this.dateList = await indexServer.getDates(7);
    },
    openNotice() {
      this.$refs.popupNotice.open()
    },
    closeNotice() {
      this.checkBox = 'ok'
      this.$refs.popupNotice.close()
    },
    // cartBox start
    open() {
      this.$refs.cartBox.open()
    },
    doOk(workId, time, workName) {
      this.time = time
      this.workId = workId
      this.workName = workName
      this.$refs.cartBox.close()
    },
    cancel() {
      this.workId = 0
      this.workName = ''
    },
    // cartBox end
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
    goHome() {
      uni.redirectTo({
        url: "/pages/consult/index",
      })
    },
    checkboxChange(e) {
      console.log(e.detail.value)
      this.checkBox = e.detail.value[0]
    },
    async toBuy() {
      if (!utils.checkLogin()) {
        return this.openLoginConfirm()
      }

      if (!this.checkBox) {
        return uni.showToast({
          icon: 'none',
          title: '请勾选我同意后进行下一步操作',
          duration: 2000
        })
      }

      if (!utils.checkLogin()) {
        return this.openLoginConfirm()
      }

      // serverId,workId,consultId
      console.log(this.consult.id)
      console.log(this.serve.id)
      console.log(this.workId)

      let res = await getPaySign(
          this.userInfo.userId,
          this.serve.id,
          this.serve.price,
          {
            module: 'consult',
            time: this.time,
            workId: this.workId,
            consultId: this.consultId
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
          uni.navigateTo({
            url: "/pages/consult/order",
          });
        }, (msg) => {
          console.log(msg)
          uni.showToast({
            icon: "error",
            title: "支付失败",
          });
        })
      }
    },
    share() {
      const title = this.consultInfo.name
      const desc = this.consultInfo.detail
      const link = window.location.href
      const img = this.consultInfo.iconUrl
      const url = window.location.href
      console.log('consultInfo: ', this.consultInfo)
      wxJS.getConfig(title, desc, link, img, url);
    }
  }
}
</script>

<style lang="scss" scoped>
.page {
  background-color: rgba(248,248,248,1.000000);
  position: relative;
  width: 100%;
  height: 1624upx;
  overflow: hidden;
  display: flex;
  flex-direction: column;

  .box-bg {
    background-color: #F5F5F5;
  }

  .consult-info {
    box-shadow: 0px 4px 28px 0px rgba(119,119,119,0.060000);
    background-color: #FFFFFF;
    border-radius: 12upx;
    width: 686upx;
    height: 416upx;
    display: flex;
    flex-direction: column;
    justify-content: flex-center;
    margin: 34upx 0 0 32upx;
  }
  .consult-info-title {
    width: 64upx;
    height: 45upx;
    overflow-wrap: break-word;
    color: rgba(51,51,51,1);
    font-size: 32upx;
    font-family: PingFangSC-Medium;
    font-weight: 500;
    text-align: center;
    white-space: nowrap;
    line-height: 45upx;
    margin: 24upx 0 0 32upx;
  }
  .consult-info-group {
    width: 494upx;
    height: 136upx;
    flex-direction: row;
    display: flex;
    justify-content: space-between;
    margin: 32upx 0 0 32upx;
  }
  .consult-info-avatar {
    border-radius: 100%;
    width: 130upx;
    height: 130upx;
  }
  .consult-info-right {
    width: 340upx;
    height: 136upx;
    display: flex;
    flex-direction: column;
  }
  .consult-name {
    width: 60upx;
    height: 42upx;
    overflow-wrap: break-word;
    color: rgba(51,51,51,1);
    font-size: 30upx;
    font-family: PingFangSC-Semibold;
    font-weight: 600;
    text-align: left;
    white-space: nowrap;
    line-height: 42upx;
  }
  .consult-work {
    width: 340upx;
    height: 37upx;
    overflow-wrap: break-word;
    color: rgba(119,119,119,1);
    font-size: 26upx;

    font-weight: normal;
    text-align: left;
    white-space: nowrap;
    line-height: 37upx;
    margin-top: 4upx;
  }
  .consult-price {
    width: 160upx;
    height: 45upx;
    overflow-wrap: break-word;
    font-size: 0upx;
    font-weight: 600;
    text-align: left;
    line-height: 30upx;
    margin-top: 8upx;
  }
  .consult-price-ant {
    color: rgba(255,63,100,1.000000);
    font-size: 22upx;
  }
  .consult-price-num {
    color: rgba(255,63,100,1.000000);
    font-size: 32upx;
  }
  .consult-price-ext {
    color: rgba(119,119,119,1.000000);
    font-size: 22upx;
    line-height: 30upx;
  }
  .info-text-wrapper-2 {
    width: 622upx;
    height: 37upx;
    flex-direction: row;
    display: flex;
    justify-content: space-between;
    margin: 32upx 0 0 32upx;
  }
  .info-text-1 {
    width: 156upx;
    height: 37upx;
    overflow-wrap: break-word;
    color: rgba(119,119,119,1);
    font-size: 26upx;

    font-weight: normal;
    text-align: left;
    white-space: nowrap;
    line-height: 37upx;
  }
  .info-text-2 {
    width: 84upx;
    height: 37upx;
    overflow-wrap: break-word;
    color: rgba(51,51,51,1);
    font-size: 26upx;

    font-weight: normal;
    text-align: right;
    white-space: nowrap;
    line-height: 37upx;
  }
  .info-text-wrapper-group2 {
    background-color: rgba(230,230,230,1.000000);
    width: 622upx;
    height: 1upx;
    display: flex;
    flex-direction: column;
    margin: 24upx 0 0 32upx;
  }
  .info-text-wrapper-3 {
    width: 622upx;
    height: 37upx;
    flex-direction: row;
    display: flex;
    justify-content: space-between;
    margin: 24upx 0 24upx 32upx;
  }
  .info-text-3 {
    color: rgba(119,119,119,1);
    font-size: 26upx;
  }
  .info-text-4 {
    color: rgba(51,51,51,1);
    font-size: 26upx;
  }
  .section-time {
    width: 100%;
    height: 833upx;
    display: flex;
    flex-direction: column;
  }
  .section-time-group {
    box-shadow: 0px 4px 28px 0px rgba(119,119,119,0.060000);
    background-color: #FFFFFF;
    border-radius: 12upx;
    width: 686upx;
    height: 248upx;
    display: flex;
    flex-direction: column;
    justify-content: flex-center;
    margin: 16upx 0 0 32upx;
  }
  .section-time-group-text1 {
    width: 64upx;
    height: 45upx;
    overflow-wrap: break-word;
    color: rgba(51,51,51,1);
    font-size: 32upx;
    font-family: PingFangSC-Medium;
    font-weight: 500;
    text-align: center;
    white-space: nowrap;
    line-height: 45upx;
    margin: 24upx 0 0 32upx;
  }
  .section-time-group2 {
    width: 622upx;
    height: 37upx;
    flex-direction: row;
    display: flex;
    justify-content: space-between;
    margin: 24upx 0 32upx 32upx;
  }
  .section-time-group-text2 {
    width: 104upx;
    height: 37upx;
    overflow-wrap: break-word;
    color: rgba(119,119,119,1);
    font-size: 26upx;

    font-weight: normal;
    text-align: left;
    white-space: nowrap;
    line-height: 37upx;
  }
  .image-text_1 {
    flex-direction: row;
    display: flex;
    justify-content: space-between;
  }
  .section-time-group-text3 {
    color: rgba(51,51,51,1);
    font-size: 26upx;
    text-align: right;
    line-height: 37upx;
  }
  .group_5 {
    background-color: rgba(230,230,230,1.000000);
    width: 622upx;
    height: 1upx;
    display: flex;
    flex-direction: column;
    margin-left: 32upx;
  }
  .section-time-group3 {
    width: 622upx;
    height: 37upx;
    flex-direction: row;
    display: flex;
    justify-content: space-between;
    margin: 24upx 0 24upx 32upx;
  }
  .section-time-text4 {
    height: 37upx;
    color: rgba(119,119,119,1);
    font-size: 26upx;
    text-align: left;
    line-height: 37upx;
  }
  .section-time-text5 {
    height: 37upx;
    color: rgba(51,51,51,1);
    font-size: 26upx;
    text-align: right;
    line-height: 37upx;
  }
  .section-reader {
    width: 368upx;
    height: 44upx;
    flex-direction: row;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin: 32upx 0 16upx 32upx;
  }
  .section-reader-box {
    transform:scale(0.7);
    //width: 32upx;
    //height: 32upx;
    //margin-top: 6upx;
  }
  .section-reader-group {
    width: 580upx;
    height: 44upx;
    font-size: 0upx;
    text-align: justify;
    line-height: 44upx;
  }
  .section-reader-group-text1 {
    width: 320upx;
    height: 44upx;
    color: rgba(51,51,51,1.000000);
    font-size: 24upx;
    line-height: 44upx;
  }
  .section-reader-group-text2 {
    width: 320upx;
    height: 44upx;
    color: rgba(31,92,228,1.000000);
    font-size: 24upx;
    line-height: 44upx;
  }
  .section-reader-group-text3 {
    width: 320upx;
    height: 44upx;
    color: rgba(51,51,51,1.000000);
    font-size: 24upx;
    line-height: 44upx;
  }
  .notice {
    margin: 0 64upx 16upx 80upx;
    font-size: 22rpx;
    font-family: PingFangSC-Regular, PingFang SC;
    color: #777777;
  }
  .bottom-view {
    padding: 10upx;
    position: fixed;
    bottom: 0;
    height: 98upx;
    width: 100%;
    background: #fff;
  }
  .bottom-btn {
    background-color: #ff703f;
    border-radius: 20px;
    height: 41px;
    line-height: 41px;
    width: 310px;
    display: flex;
    justify-content: center;
    margin: 4px 0 0 39px;
  }
  .bottom-text {
    color: rgba(255,255,255,1);
    font-size: 28upx;
    font-weight: 600;
  }
  .bottom-text-wrapper {
    margin-left: 10upx;
    color: #FFFFFF;
  }
  .bottom-text-wrapper-1 {
    font-size: 22upx;
  }
  .bottom-text-wrapper-2 {
    font-size: 32upx;
  }
  // sss
  .popup-notice {
    background-color: #FFFFFF;
    width: 700upx;
    border-radius: 12upx;
    margin-left: 16upx;
    margin-right: 16upx;
    position: relative;
    display: flex;
    justify-content: center;
    padding-bottom: 80upx;
    .popup-notice-desc {
      /deep/ .ql-align-center {
        text-align: center;
        margin-bottom: 10upx;
      }
      height: 1000upx;
      padding: 24upx;
      ::v-deep img {
        width: 100%;
      }
    }
    .popup-notice-close {
      border-radius: 20px;
      height: 41px;
      width: 260px;
      line-height: 41px;
      position: absolute;
      bottom: 10upx;
      color: #fff;
      background-color: #ff703f;
    }
  }
}
</style>