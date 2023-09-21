<template>
  <view class="page" v-if="report">
    <view class="header">
      <text class="header-title">抑郁表现测试题</text>
      <text class="header-report">报告编号：{{ report.order.orderId }}</text>
      <view class="header-wrapper">
        <text class="header-name">测试者：{{ report.order.userName }}</text>
        <text class="header-time">测试时间：{{ report.order.createTime }}</text>
      </view>
    </view>
    <view class="result">
      <view class="circular">
        <circular-progress :fontShow="false" :percentage="percentage" :diam="190" :lineWidth="20" :fontSize="20" progressColor="#FF703F" fontColor="#FF703F" defaultColor="#ffe4db">
          <view class="progress-text">
            <view class="text-score">{{ score }}</view>
            <view class="text-score-btn">综合得分</view>
          </view>
        </circular-progress>
      </view>
      <text class="memo1">{{ report.setting.memo1 }}</text>
      <text class="memo2">{{ report.setting.memo2 }}</text>
      <view class="result-header">
        <view class="result-left"></view>
        <view class="result-text">您的测评结果</view>
        <view class="result-right"></view>
      </view>
    </view>
    <view class="understand">
      <view class="btn-block">
        <view class="btn-bottom"></view>
        <view class="btn-text">得分解读</view>
      </view>
      <text class="understand-top">综合</text>
      <view class="understand-range">
        <view class="understand-range-block" :style="{ top: (percentage-10) + '%' }">
          <view class="understand-range-item"></view>
          <view class="understand-range-item"></view>
        </view>
      </view>
      <text class="understand-down">抑郁表现</text>
      <text class="understand-desc">{{ report.setting.memo3 }}</text>
    </view>
    <view class="gloomy">
      <view class="btn-block">
        <view class="btn-bottom"></view>
        <text class="btn-text">抑郁表现</text>
      </view>
      <view class="gloomy-range">
        <text class="gloomy-range-down">低</text>
        <view class="gloomy-pro">
          <view class="gloomy-progress" :style="{ width: percentage + '%' }"></view>
        </view>
        <text class="gloomy-range-top">高</text>
      </view>
      <text class="gloomy-desc">{{ report.setting.memo4 }}</text>
    </view>
    <view class="suggest">
      <view class="suggest-btn">
        <view class="suggest-bg"></view>
        <text class="suggest-text">您的得分建议</text>
      </view>
      <view class="img-box" v-html="report.setting.result"/>
    </view>

    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>
  </view>
</template>
<script>
import utils from "@/utils/common";
import loginServer from '@/server/login'
import serve from '@/server/evaluation/question'
import circularProgress from '@/components/circular-progress/circular-progress'

export default {
  components: {
    circularProgress
  },
  data() {
    return {
      score: 0,
      percentage: 0,
      orderId: null,
      userInfo: {},
      report: {},
      maskShow: false,
    }
  },
  created() {
    this.orderId = utils.getParam(location.href, "orderId")
  },
  async mounted() {
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback()) {
      this.userInfo = utils.getUserInfo()
    }
    if (!await utils.checkLogin()) {
      return this.openLoginConfirm()
    }
    this.report = await serve.getReport(this.orderId)
    this.score = parseInt(this.report.order.score)
    this.percentage = parseInt(this.score * 1.25)
    utils.share(this.report.order.gaugeTitle, '', this.report.order.headPicture, 'https://wx.ssgpsy.com/pages/evaluation/product?id=' + this.report.order.gaugeId)
  },
  methods: {
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
  }
}
</script>
<style lang="scss">
.page {
  background-color: #fff9f7;
  position: relative;
  width: 750upx;
}
.header {
  width: 750upx;
  height: 665upx;
  background: url('/static/evaluation/report.png') 0upx 0upx no-repeat;
  background-size: 750upx 666upx;
  display: flex;
  flex-direction: column;
}
.header-title {
  width: 280upx;
  height: 56upx;

  color: rgba(255,255,255,1);
  font-size: 40upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  text-align: left;
  line-height: 56upx;
  margin: 41upx 0 0 32upx;
}
.header-report {
  height: 37upx;
  color: rgba(255,255,255,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  text-align: left;
  line-height: 37upx;
  margin: 24upx 0 0 32upx;
}
.header-wrapper {
  width: 595upx;
  height: 37upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin: 20upx 0 450upx 32upx;
}
.header-name {
  width: 156upx;
  height: 37upx;
  color: rgba(255,255,255,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  text-align: left;
  line-height: 37upx;
}
.header-time {
  //width: 382upx;
  height: 37upx;
  color: rgba(255,255,255,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  text-align: left;
  line-height: 37upx;
}
.result {
  box-shadow: 0px 4px 16px 0px rgba(255,112,63,0.160000);
  background-color: rgba(255,255,255,1.000000);
  border-radius: 20upx;
  width: 686upx;
  margin-top: -380upx;
  margin-left: 32upx;
  padding-bottom: 32upx;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}
.result-header {
  background-color: rgba(255,168,82,1.000000);
  border-radius: 47upx;
  position: absolute;
  left: 175upx;
  top: -45upx;
  width: 336upx;
  height: 90upx;
  border: 2px solid rgba(255,255,255,1);
  display: flex;
  justify-content: center;
  align-items: center;
}
.result-left {
  background-color: rgba(255,255,255,1.000000);
  border-radius: 50%;
  width: 12upx;
  height: 12upx;
  display: flex;
  flex-direction: column;
}
.result-text {
  width: 216upx;
  height: 50upx;
  color: rgba(255,255,255,1);
  font-size: 36upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  margin-left: 10upx;
}
.result-right {
  background-color: rgba(255,255,255,1.000000);
  border-radius: 50%;
  width: 12upx;
  height: 12upx;
  margin-left: 10upx;
}
.circular {
  margin-top: 80upx;
  position: relative;
  .progress-text {
    width: 100%;
    height: 130px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    padding-top: 40upx;
    position: absolute;
    left: 0;
    top: 0;
  }
  .text-score {
    width: 57upx;
    height: 78upx;

    color: rgba(255,112,63,1);
    font-size: 56upx;
    font-family: PingFangSC-Semibold;
    font-weight: 600;
    text-align: left;

    line-height: 78upx;
  }
  .text-score-btn {
    width: 160upx;
    height: 50upx;
    line-height: 50upx;
    background: #FFE4DB;
    border-radius: 25upx;
    color: rgba(255,112,63,1);
    font-size: 30upx;
    text-align: center;
    margin-top: 4upx;
  }
}
.memo1 {
  color: rgba(51,51,51,1);
  font-size: 36upx;
  font-family: PingFangSC-Medium;
  font-weight: 500;
  margin-top: 80upx;
}
.memo2 {
  width: 622upx;
  color: rgba(119,119,119,1);
  font-size: 32upx;
  font-family: PingFangSC-Regular;
  text-align: justify;
  margin-top: 32upx;
}
.understand {
  box-shadow: 0px 4px 16px 0px rgba(255,112,63,0.160000);
  background-color: rgba(255,255,255,1.000000);
  border-radius: 20upx;
  position: relative;
  width: 686upx;
  //height: 982upx;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 32upx;
  padding-bottom: 32upx;
  text-align: center;
}
.btn-block {
  border-radius: 20upx;
  position: relative;
  width: 240upx;
  height: 90upx;
  border: 2px solid rgba(255,112,63,1);
  display: flex;
  flex-direction: column;
  margin-top: 40upx;
}
.btn-bottom {
  background-color: rgba(255,228,219,1.000000);
  width: 162upx;
  height: 17upx;
  display: flex;
  flex-direction: column;
  margin: 57upx 39upx 0 39upx;
}
.btn-text {
  position: absolute;
  left: 48upx;
  top: 16upx;
  height: 50upx;
  color: rgba(255,112,63,1);
  font-size: 36upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
}
.understand-top {
  width: 56upx;
  height: 40upx;
  color: rgba(119,119,119,1);
  font-size: 28upx;
  font-family: PingFangSC-Regular;
  text-align: justify;
  margin-top: 80upx;
}
.understand-img {
  width: 18upx;
  height: 320upx;
  margin-top: 32upx;
}
.understand-range {
  position: relative;
  margin-top: 32upx;
  display: block;
  width: 1upx;
  height: 160px;
  background: linear-gradient(to bottom, #AAAAAA 0%, #AAAAAA 50%,transparent 50%);
  background-size: 2upx 12upx;
  background-repeat: repeat-y;
}
.understand-range-block {
  position: absolute;
  left: -5px;
}
.understand-range-item {
  width: 10px;
  height: 10px;
  border-radius: 100%;
  background: #FF703F;
}
.understand-down {
  width: 112upx;
  height: 40upx;
  color: rgba(119,119,119,1);
  font-size: 28upx;
  font-family: PingFangSC-Regular;
  text-align: justify;
  line-height: 40upx;
  margin-top: 32upx;
}
.understand-desc {
  width: 622upx;
  height: 180upx;
  color: rgba(51,51,51,1);
  font-size: 32upx;
  font-family: PingFangSC-Regular;
  text-align: justify;
  line-height: 45upx;
  margin-top: 80upx;
}
.gloomy {
  box-shadow: 0px 4px 16px 0px rgba(255,112,63,0.160000);
  background-color: rgba(255,255,255,1.000000);
  border-radius: 20upx;
  width: 686upx;
  //height: 554upx;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 32upx;
  text-align: center;
}
.gloomy-range {
  width: 622upx;
  height: 40upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin-top: 76upx;
}
.gloomy-range-down {
  width: 28upx;
  height: 40upx;
  color: rgba(119,119,119,1);
  font-size: 28upx;
  font-family: PingFangSC-Regular;
  text-align: justify;
  line-height: 40upx;
}
.gloomy-pro {
  background-color: rgba(255,228,219,1.000000);
  border-radius: 16upx;
  height: 32upx;
  display: flex;
  flex-direction: column;
  width: 518upx;
  margin: 4upx 0 0 24upx;
}
.gloomy-progress {
  background-color: rgba(255,112,63,1.000000);
  border-radius: 16upx;
  width: 66upx;
  height: 32upx;
  display: flex;
  flex-direction: column;
}
.gloomy-range-top {
  width: 28upx;
  height: 40upx;
  color: rgba(119,119,119,1);
  font-size: 28upx;
  font-family: PingFangSC-Regular;
  text-align: justify;
  line-height: 40upx;
  margin-left: 24upx;
}
.gloomy-desc {
  width: 622upx;
  height: 180upx;
  color: rgba(51,51,51,1);
  font-size: 32upx;
  font-family: PingFangSC-Regular;
  text-align: justify;
  line-height: 45upx;
  margin: 80upx 32upx 32upx 32upx;
}
.suggest {
  box-shadow: 0px 4px 16px 0px rgba(255,112,63,0.160000);
  background-color: rgba(255,255,255,1.000000);
  border-radius: 20upx;
  width: 686upx;
  display: flex;
  flex-direction: column;
  margin: 32upx 0 0 32upx;
}
.suggest-btn {
  border-radius: 20upx;
  position: relative;
  width: 334upx;
  height: 90upx;
  border: 2px solid rgba(255,112,63,1);
  display: flex;
  flex-direction: column;
  margin: 0 auto;
  margin-top: 40upx;
}
.suggest-bg {
  background-color: rgba(255,228,219,1.000000);
  width: 226upx;
  height: 17upx;
  display: flex;
  flex-direction: column;
  margin: 57upx 0 0 54upx;
}
.suggest-text {
  position: absolute;
  left: 59upx;
  top: 16upx;
  width: 216upx;
  height: 50upx;
  color: rgba(255,112,63,1);
  font-size: 36upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  text-align: center;
  line-height: 50upx;
}
.img-box {
  width: calc(100% - 48upx);
  padding-left: 24upx;
  padding-right: 24upx;
  padding-bottom: 24upx;
  overflow: scroll;
  //height: 800upx;
  margin-top: 80upx;

  ::v-deep img {
    width: 100%;
  }
}
.suggest-title {
  width: 238upx;
  height: 45upx;
  color: rgba(51,51,51,1);
  font-size: 32upx;
  font-family: PingFangSC-Medium;
  font-weight: 500;
  text-align: justify;
  line-height: 45upx;
  margin: 80upx 0 0 32upx;
}
.suggest-desc {
  width: 622upx;
  height: 180upx;
  color: rgba(51,51,51,1);
  font-size: 32upx;
  font-family: PingFangSC-Regular;
  text-align: justify;
  line-height: 45upx;
  margin: 16upx 0 0 32upx;
}
</style>