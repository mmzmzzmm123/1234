<template>
  <view class="page padding-xs" :style="{ height: pageHeight }">
    <view class="margin-top text-center text-xl text-black text-bold">
      壹加壹心理咨询师入驻平台服务协议
    </view>

    <view class="text-left padding-sm">
      【注意】壹加壹心理提醒您，请仔细阅读以下全部内容，特别以加粗或其他合理方式提示 您注意，并请您重点阅读的条款。如果您不同意本协议的任一或全部条款内容，请不要以 确认的形式（包括但不限于：点击确认、进入入驻程序等）进行下一步操作或使用本协议 项下服务。
    </view>

    <view class="text-left padding-sm">
      当您确认接受本协议或同意入驻甲方运营的壹加壹心理咨询预约平台（以下简称“平台”） 成为入驻心理咨询师时，即表示您作为乙方（以下简称“乙方”“咨询师”）与壹加壹心理（平台运营主体：武汉仁心网络科技有限公司，以下简称“甲方”、“壹加壹心理”）已达成协议关系，并自愿接受并遵守壹加壹心理提供服务涉及的包括本协议在内的所有服务规范及相关子协议、管理规范、咨询师管理说明等。
    </view>

    <view class="text-left padding-sm">
      壹加壹心理有权就本协议及相关子协议、管理规范、咨询师管理说明等文件（以下合称“服 务协议”）进行更新，包括但不限于不定时发布或修订壹加壹心理服务规则、使用规则、公告、 提示、通知等内容，更新将会通过系统提示、站内信息推送和／或您在壹加壹心理留下的联系 方式通知和提醒您注意，更新后依法生效。如您继续使用服务，即意味着您同意接受我方 更新后的服务协议，您的权利以及义务的表述、服务
    </view>

    <view class="btn block cu-btn bg-orange round lg" @tap="back">返回</view>
  </view>
</template>

<script>

import serve from "@/server/consult/toPartner";
let app = getApp();

export default {
  name: "ConsultPartnerCompact",
  data() {
    return {
      pageHeight: app.globalData.windowHeight + 'px',
    }
  },
  onLoad() {
    // this.getApply()
  },
  methods: {
    async getApply() {
      const res = await serve.getInfo()
      if (res.code === 200 && res.data) {
        return uni.navigateTo({ url: "/pages/consult/toPartner/step1" })
      }
    },
    back() {
      uni.navigateTo({ url: "/pages/consult/toPartner/step4" })
    },
  }
}
</script>

<style lang="scss">
.page {
  //padding: 0 20upx;
  padding-bottom: 20upx;
  background: #FFF;
}

.btn {
  position: fixed;
  bottom: 60upx;
  width: 500upx;
  margin: 0 100upx;
}


</style>