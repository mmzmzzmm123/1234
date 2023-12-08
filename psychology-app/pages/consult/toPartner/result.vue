<template>
  <view class="page padding-xs" :style="{ height: pageHeight }">
    <template v-if="type === '1'">
      <image src="/static/consult/approve.png" class="empty-img"></image>
      <view class="margin-top text-bold text-lg">入驻资料提交成功</view>
      <view class="margin-top-sm text-gray">平台将在7个工作日内完成审核，请耐心等候。</view>
      <view class="flex justify-center margin-top-xl">
        <view class="cu-btn line-orange round lg btn-next" @tap="toPage('1')">返回首页</view>
        <view class="cu-btn line-orange round lg btn-next" @tap="toPage('2')">查看进度</view>
      </view>
    </template>
    <template v-else-if="type === '2'">
      <image src="/static/consult/approve.png" class="empty-img"></image>
      <view class="margin-top text-bold text-lg">入驻资料提交成功</view>
      <view class="margin-top-sm text-gray">平台将在7个工作日内完成审核，请耐心等候。</view>
      <view class="flex justify-center margin-top-xl">
        <view class="cu-btn line-orange round lg padding" @tap="toPage('3')">查看已提交资料</view>
      </view>
    </template>
    <template v-else-if="type === '3'">
      <image src="/static/consult/pass.png" class="empty-img"></image>
      <view class="margin-top text-bold text-lg">恭喜，您的申请已通过！</view>
      <view class="margin text-gray">欢迎您加入壹加壹心理服务平台，成为我们的一员。</view>
      <view class="margin padding-sm bg-gray">
        <view class="text-left margin-bottom-sm">请使用微信扫一扫或长按图片识别以下二维码完成缴费，缴费成功后平台工作人员会向您发放管理后台地址、账号密码。</view>
        <image src="/static/consult/wePay.png" class="card-img"></image>
        <view class="text-left padding-xs" v-if="order">
          <view>支付金额：¥{{ order.money.toFixed(2) }}</view>
          <view>收款方：壹加壹心理服务平台</view>
          <view>主体：武汉仁心网络科技有限公司</view>
          <view class="margin-xs text-sm text-gray">
            缴费成功即表示您同意并接受
            <text @tap="toPage('4')" class="text-blue">《咨询师入驻协议》</text>
            中的全部条款和相关设置。
          </view>
        </view>
      </view>
    </template>
    <template v-else-if="type === '4'">
      <image src="/static/consult/pass.png" class="empty-img"></image>
      <view class="margin-top text-lg">壹加壹心理欢迎你的加入。</view>
    </template>
    <template v-else-if="type === '5'">
      <image src="/static/consult/back.png" class="empty-img"></image>
      <view class="margin-top text-lg">很遗憾，您的资料未通过。</view>
      <view class="margin padding-sm bg-gray">
        <view class="text-left">原因：</view>
        <view class="text-left text-gray padding-xs">
          这里展示驳回时填写的信息。
        </view>
      </view>
      <view class="flex justify-center margin-top-xl">
        <view class="cu-btn line-orange round lg padding" @tap="toPage('5')">修改资料</view>
      </view>
    </template>

    <login ref="loginModel" :isNav="1"></login>
  </view>
</template>

<script>
import login from "@/components/common/login";
import serve from "@/server/consult/toPartner";
let app = getApp();

export default {
  name: "ConsultPartnerResult",
  components: { login },
  data() {
    return {
      type: '1',
      order: {},
      pageHeight: app.globalData.windowHeight + 'px',
    }
  },
  async onLoad(option) {
    // #ifdef H5
    this.$utils.share('壹加壹心理入驻申请', '欢迎入驻壹加壹心理咨询服务平台', '', 'https://wx.ssgpsy.com/pages/consult/toPartner/start')
    // #endif

    console.log(options)
    this.userInfo = this.$utils.getUserInfo()
    if (!this.userInfo && await this.$utils.loginCallback()) {
      this.userInfo = this.$utils.getUserInfo()
    }
    if (!await this.$utils.checkLogin()) {
      return this.openLoginConfirm()
    }

    if (option && option.type === '1') {
      this.type = option.type
    } else {
      this.getApply()
    }
  },
  methods: {
    openLoginConfirm() {
      this.$refs.loginModel.open()
    },
    async getApply() {
      const res = await serve.getInfo()
      if (res.code === 200 && res.data) {
        console.log(res.data)
        this.order = res.data
        switch (res.data.status) {
          case '0':
            return uni.navigateTo({ url: "/pages/consult/toPartner/step1" })
          case '1':
            this.type = '2'
                break
          case '2':
            this.type = '4'
                break
          case '3':
            this.type = '3'
                break
          case '4':
            this.type = '5'
                break
        }
      }
    },
    async toPage(type) {
      if (!await this.$utils.checkLogin()) {
        return this.openLoginConfirm()
      }

      switch (type) {
        case '1':
          uni.navigateTo({url: "/pages/consult/index"})
          break
        case '2':
          this.type = '2'
          break
        case '3':
          uni.navigateTo({url: "/pages/consult/toPartner/step4?type=read"})
          break
        case '4':
          uni.navigateTo({url: "/pages/consult/toPartner/compact"})
          break
        case '5':
          uni.navigateTo({url: "/pages/consult/toPartner/step1"})
          break
      }
    },
  }
}
</script>

<style lang="scss">
.page {
  background: #FFF;
  text-align: center;
  padding-bottom: calc(constant(safe-area-inset-bottom) + 100px); /* 兼容 iOS 设备 */
  padding-bottom: calc(env(safe-area-inset-bottom) + 100px); /* 兼容 iPhone X 及以上设备 */
}

.empty-img {
  margin-top: 100upx;
  width: 400upx;
  height: 400upx;
}

.card-img {
  width: 300upx;
  height: 300upx;
  margin: 40upx;
}

.btn-next {
  min-width: 250upx;
  margin-right: 30upx;
}


</style>