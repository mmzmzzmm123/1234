<template>
  <view class="page padding-xs" :style="{ height: pageHeight }">
    <view class="margin-top text-center text-xl text-black text-bold">
      欢迎入驻壹加壹心理咨询服务平台
    </view>

    <view class="text-left padding-sm">
      壹加壹心理线上预约咨询平台上线于2023年8月，平台业务专注于个人心理成长，帮助每位前来寻求专业咨询师帮助的来访者探索自我价值，找到自我的人生意义，实现心灵的自由，活出自己想要的样子。
    </view>

    <view class="text-left padding-sm">
      在过去的几年里，壹加壹心理服务中心已帮助成千上万位有心理困扰的来访者实现了心灵成长，找到了适合自己独自前行的人生轨迹。
    </view>

    <view class="text-left padding-sm">
      成长内容包括：对过去创伤的疗愈、对情绪的认识与相处、更能觉知与把握当下、持续地更加了解自己、稳定信任自己的价值、与家人/伴侣/朋友建立安全而自在的关系、更善于选择等等。
    </view>

    <view class="text-left padding-sm">
      为进一步发展平台业务，为更多需要专业力量帮助的来访者提供更优质的服务，壹加壹心理诚邀各位咨询同道入驻，让我们一起维护心理服务行业的健康生态，一起燃烧助人自助的初心。
    </view>

    <view class="text-left padding-sm">
      建议您在填写申请入驻信息之前，先与平台入驻工作人员了解入驻条件。
    </view>

    <view class="text-center margin">
      <image src="/static/consult/order/card.png" style="width: 192upx;height: 189upx;" mode="widthFix"></image>
    </view>

    <view class="margin-top">
      <checkbox-group class="block" @change="checkboxChange">
        <label class="block flex align-center">
          <checkbox class="round orange" :class="checkBox === 'ok'?'checked':''"  value="ok" :checked="checkBox === 'ok'"/>
          <text class="margin-left">已沟通，并知晓平台入驻规划<text class="text-gray">（如未沟通，请先联系入驻工作人员）</text></text>
        </label>
      </checkbox-group>
    </view>

    <view class="btn block cu-btn bg-orange round lg" @tap="apply">申请入驻</view>
  </view>
</template>

<script>

import serve from "@/server/consult/toPartner";
let app = getApp();

export default {
  name: "ConsultPartnerStart",
  data() {
    return {
      checkBox: '',
      pageHeight: app.globalData.windowHeight + 'px',
    }
  },
  onLoad(options) {
    console.log(options)
    if (options && options.type === 'back') {

    } else {
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
            // return uni.navigateTo({ url: "/pages/consult/toPartner/result?type=2" })
          case '2':
            // return uni.navigateTo({ url: "/pages/consult/toPartner/result?type=4" })
          case '3':
            // return uni.navigateTo({ url: "/pages/consult/toPartner/result?type=3" })
          case '4':
            return uni.navigateTo({ url: "/pages/consult/toPartner/result" })
        }
      }
    },
    checkboxChange(e) {
      this.checkBox = e.detail.value[0]
    },
    apply() {
      if (this.checkBox !== 'ok') {
        return uni.showToast({
          icon: 'none',
          title: '请勾选 已沟通，并知晓平台入驻规划 后进行下一步操作',
          duration: 2000
        })
      }
      uni.navigateTo({ url: "/pages/consult/toPartner/notice" })
    },
  }
}
</script>

<style lang="scss">
.page {
  //padding: 0 20upx;
  //padding-bottom: 100upx;
  position: relative;
  background: #FFF;
}

.btn {
  position: fixed;
  bottom: 60upx;
  width: 500upx;
  margin: 0 100upx;
}


</style>