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
      <view class="margin text-gray">平台将在7个工作日内完成审核，请耐心等候。</view>
      <view class="margin padding-sm bg-gray">
        <view class="text-left margin-bottom-sm">请扫描以下二维码完成缴费，缴费成功后平台工作人员会向您发放管理后台地址、账号密码。</view>
        <image src="/static/consult/pass.png" class="card-img"></image>
        <view class="text-left padding-xs">
          <view>支付金额：¥700.00</view>
          <view class="margin-xs text-right text-sm text-gray">
            费用服务明细请参考
            <text @tap="toPage('4')" class="text-blue">《咨询师入驻协议》</text>
          </view>
          <view>收款方：壹加壹心理咨询</view>
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
  </view>
</template>

<script>
let app = getApp();

export default {
  name: "ConsultPartnerResult",
  data() {
    return {
      type: '1',
      pageHeight: app.globalData.windowHeight + 'px',
    }
  },
  onLoad(option) {
    if (option && option.type === '1') {
      this.type = option.type
    }else {
      this.getApply()
    }
  },
  methods: {
    async getApply() {
      const res = await serve.getInfo()
      if (res.code === 200 && res.data) {
        console.log(res.data)
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
    toPage(type) {
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
          uni.navigateTo({url: "/pages/consult/toPartner/notice"})
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