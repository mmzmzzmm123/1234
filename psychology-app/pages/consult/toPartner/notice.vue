<template>
  <view class="page" :style="{ height: pageHeight }">
    <view class="padding-xs">
      <view class="margin-top text-center text-xl text-black text-bold">
        壹加壹心理服务平台咨询师入驻流程
      </view>

      <view class="text-left padding-sm">
        欢迎您申请入驻壹加壹心理服务平台，请填写您的入驻信息。
      </view>
      <view class="text-left padding-sm margin-top-xs">
        <view class="padding-top-xs text-bold">第一步 为方便您的操作流程，建议提前将以下资料存入相册，便于上传</view>
        <view class="padding-top-xs padding-left-xs">1、证件照片：身份证（正反面）</view>
        <view class="padding-top-xs padding-left-xs">2、学历证书照片，学位证书照片（如果有的话请提供）</view>
        <view class="padding-top-xs padding-left-xs">3、执业资质照片：国内咨询师可以是国家二、三级咨询师证书；注册系统心理师、督导师，助理心理师；初级、中级心理治疗师（卫生部系统）；精神科医生；社工证；中科院培训合格基础证书；港澳台地区及海外咨询师需准备当地的资质证明照片</view>
        <view class="padding-top-xs padding-left-xs">4、已完成的及正在进行的系统长程培训（一年以上）或1-3个月的短程培训证明材料</view>
        <view class="padding-top-xs padding-left-xs">5、个案咨询经历证明材料</view>
        <view class="padding-top-xs padding-left-xs">6、接受督导经历证明材料</view>
        <view class="padding-top-xs padding-left-xs">7、个人形象照一张</view>
      </view>

      <view class="text-left padding-sm margin-top-xs">
        <view class="padding-top-xs text-bold">第二步 告知审核结果</view>
        <view class="padding-top-xs padding-left-xs">平台工作人员将在您提交资料后的3-5个工作日内与您微信联系告知您审核结果。</view>
        <view class="padding-top-xs padding-left-xs">入驻平台是一个双向选择的过程，需要您认真考虑并能为自己的选择承担责任。</view>
      </view>

      <view class="text-left padding-sm">
        <view class="padding-top-xs text-bold">第三步 完善咨询师展示界面信息</view>
        <view class="padding-top-xs padding-left-xs">平台入驻老师会为您分配一个专属的个人账号，用于您日常排班，信息维护，管理订单。</view>
        <view class="padding-top-xs padding-left-xs">以上流程您已知晓，点击下方“申请入驻”，进行下一步。</view>
      </view>
    </view>

<!--    <view class="margin-top block cu-btn bg-orange round lg btn" @tap="apply">申请入驻</view>-->
    <view class="cu-bar foot bg-white tabbar border shop" style="z-index: 1000">
      <view class="bg-orange round submit margin-xs" @tap="apply">申请入驻</view>
    </view>
  </view>
</template>

<script>

import serve from "@/server/consult/toPartner";
let app = getApp();

export default {
  name: "ConsultPartnerNotice",
  onLoad() {
    // this.getApply()
  },
  data() {
    return {
      pageHeight: app.globalData.windowHeight + 'px',
    }
  },
  methods: {
    async getApply() {
      const res = await serve.getInfo()
      if (res.code === 200 && res.data) {
        return uni.navigateTo({ url: "/pages/consult/toPartner/step1" })
      }
    },
    async apply() {
      const res = await serve.draft()
      if (res.code === 200) {
        return uni.navigateTo({ url: "/pages/consult/toPartner/step1" })
      }
    },
  }
}
</script>

<style lang="scss">
.page {
  //padding: 0 20upx;
  background: #FFF;
  padding-bottom: calc(constant(safe-area-inset-bottom) + 100px); /* 兼容 iOS 设备 */
  padding-bottom: calc(env(safe-area-inset-bottom) + 100px); /* 兼容 iPhone X 及以上设备 */

}
.btn {
  position: fixed;
  bottom: 60upx;
  width: 500upx;
  margin: 0 100upx;
}


</style>