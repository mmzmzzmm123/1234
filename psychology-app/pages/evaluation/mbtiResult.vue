<template>
  <view class="page" v-if="report && report.order && report.setting">
    <view class="header">
      <text class="header-title">{{ report.order.gaugeTitle }}</text>
      <text class="header-report">报告编号：{{ report.order.orderId }}</text>
      <view class="header-wrapper">
        <text class="header-name">测试者：{{ report.order.userName }}</text>
        <text class="header-time">测试时间：{{ report.order.createTime }}</text>
      </view>
    </view>
    <view class="result">
      <text class="memo1">{{ report.setting.memo1 }}</text>
      <text class="memo2">{{ report.setting.memo2 }}</text>
      <view class="result-header">
        <view class="result-left"></view>
        <view class="result-text">您的测评结果</view>
        <view class="result-right"></view>
      </view>
    </view>
    <view class="block-1">
      <block-header title="综合维度解析"/>
      <range down="外向（E)" high="内向（I)" lineWidth="348upx" :percentage="perList[0]" />
      <range down="实感（S)" high="直觉（N)" lineBg="#D4E3FF" proBg="#7596DB"  lineWidth="348upx" :percentage="perList[1]" />
      <range down="思维（T)" high="情感（F)" lineBg="#FFEDB2" proBg="#F8CF67"  lineWidth="348upx" :percentage="perList[2]" />
      <range down="判断（J)" high="知觉（P)" lineBg="#C9E8C9" proBg="#96D18D"  lineWidth="348upx" :percentage="perList[3]" />
      <text class="desc-1">{{ report.setting.memo3 }}</text>
    </view>
    <view class="block-1">
      <block-header title="精力支配"/>
      <range down="外向（E)" high="内向（I)" lineWidth="348upx" :percentage="perList[0]" />
      <text class="desc-1">{{ report.setting.memo4 }}</text>
    </view>
    <view class="block-1">
      <block-header title="认识世界"/>
      <range down="实感（S)" high="直觉（N)" lineBg="#D4E3FF" proBg="#7596DB"  lineWidth="348upx" :percentage="perList[1]" />
      <text class="desc-1">{{ report.setting.memo5 }}</text>
    </view>
    <view class="block-1">
      <block-header title="判断事物"/>
      <range down="思维（T)" high="情感（F)" lineBg="#FFEDB2" proBg="#F8CF67"  lineWidth="348upx" :percentage="perList[2]" />
      <text class="desc-1">{{ report.setting.memo6 }}</text>
    </view>
    <view class="block-1">
      <block-header title="生活态度"/>
      <range down="判断（J)" high="知觉（P)" lineBg="#C9E8C9" proBg="#96D18D"  lineWidth="348upx" :percentage="perList[3]" />
      <text class="desc-1">{{ report.setting.memo7 }}</text>
    </view>

    <view class="block-1">
      <block-header title="综合维度建议"/>
      <view class="img-box" v-html="report.setting.result"/>
    </view>

    <recommend/>

    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true"
                        @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>
  </view>
</template>
<script>
import utils from "@/utils/common";
import loginServer from '@/server/login'
import serve from '@/server/evaluation/question'
import range from '@/components/common/range'
import recommend from '@/components/consult/recommend'
import blockHeader from '@/components/common/blockHeader'

export default {
  components: {
    range,
    recommend,
    blockHeader
  },
  data() {
    return {
      orderId: null,
      userInfo: {},
      report: {},
      perList: ['0%', '0%', '0%','0%'],
      lats: {E: 0, I: 0, S: 0, N: 0, T: 0, F: 0, J: 0, P: 0}
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

    const count = this.report.lats.reduce((counts, item) => {
      counts[item] = (counts[item] || 0) + 1;
      return counts;
    }, Object.create(null));

    this.lats = Object.assign({}, this.lats, count);
    this.perList[0] = this.lats.E === 0 ? '100%' : (this.lats.I * 100 / (this.lats.E + this.lats.I)).toFixed(0) + '%'
    this.perList[1] = this.lats.S === 0 ? '100%' : (this.lats.N * 100 / (this.lats.S + this.lats.N)).toFixed(0) + '%'
    this.perList[2] = this.lats.T === 0 ? '100%' : (this.lats.F * 100 / (this.lats.T + this.lats.F)).toFixed(0) + '%'
    this.perList[3] = this.lats.J === 0 ? '100%' : (this.lats.P * 100 / (this.lats.J + this.lats.P)).toFixed(0) + '%'

    console.log(this.lats);
    console.log(this.perList);

    utils.share(this.report.order.gaugeTitle, '', this.report.order.headPicture, 'https://wx.ssgpsy.com/pages/evaluation/product?id=' + this.report.order.gaugeId)
  },
  methods: {
    // 登录
    async confirmLogin() {
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
  //width: 280upx;
  height: 56upx;

  color: rgba(255, 255, 255, 1);
  font-size: 40upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  text-align: left;
  line-height: 56upx;
  margin: 41upx 0 0 32upx;
}

.header-report {
  height: 37upx;
  color: rgba(255, 255, 255, 1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  text-align: left;
  line-height: 37upx;
  margin: 24upx 0 0 32upx;
}

.header-wrapper {
  height: 37upx;
  margin: 20upx 0 450upx 32upx;
}

.header-name {
  width: 200upx;
  height: 37upx;
  color: rgba(255, 255, 255, 1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  line-height: 37upx;
}

.header-time {
  height: 37upx;
  color: rgba(255, 255, 255, 1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  line-height: 37upx;
  margin-left: 60upx;
}

.result {
  box-shadow: 0px 4px 16px 0px rgba(255, 112, 63, 0.160000);
  background-color: rgba(255, 255, 255, 1.000000);
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
  background-color: rgba(255, 168, 82, 1.000000);
  border-radius: 47upx;
  position: absolute;
  left: 175upx;
  top: -45upx;
  width: 366upx;
  height: 90upx;
  border: 2px solid #fff9f7;
  display: flex;
  justify-content: center;
  align-items: center;
}

.result-left {
  background-color: rgba(255, 255, 255, 1.000000);
  border-radius: 50%;
  width: 12upx;
  height: 12upx;
  display: flex;
  flex-direction: column;
}

.result-text {
  //width: 216upx;
  height: 50upx;
  color: rgba(255, 255, 255, 1);
  font-size: 36upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  margin-left: 10upx;
}

.result-right {
  background-color: rgba(255, 255, 255, 1.000000);
  border-radius: 50%;
  width: 12upx;
  height: 12upx;
  margin-left: 10upx;
}

.block-1 {
  box-shadow: 0px 4px 16px 0px rgba(255, 112, 63, 0.160000);
  background-color: rgba(255, 255, 255, 1.000000);
  border-radius: 20upx;
  position: relative;
  width: 686upx;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 32upx;
  padding-bottom: 32upx;
}

.memo1 {
  color: rgba(51, 51, 51, 1);
  font-size: 36upx;
  font-family: PingFangSC-Medium;
  font-weight: 500;
  margin-top: 80upx;
  padding: 0 10upx;
}

.memo2 {
  width: 622upx;
  color: rgba(119, 119, 119, 1);
  font-size: 32upx;
  font-family: PingFangSC-Regular;
  text-align: justify;
  margin-top: 32upx;
}

.desc-1 {
  width: 622upx;
  //height: 180upx;
  color: rgba(51, 51, 51, 1);
  font-size: 32upx;
  font-family: PingFangSC-Regular;
  text-align: justify;
  line-height: 45upx;
  margin-top: 80upx;
}

.img-box {
  width: calc(100% - 48upx);
  padding-left: 24upx;
  padding-right: 24upx;
  padding-bottom: 24upx;
  overflow: scroll;
  //height: 800upx;
  margin-top: 80upx;
  font-size: 32upx;

  ::v-deep img {
    width: 100%;
  }
}
</style>