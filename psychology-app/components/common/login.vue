<template>
  <view>
    <view class="cu-modal" :class="show==='login'?'show':''">
      <view class="cu-dialog">
        <view class="bg-white padding-xl">
          <image src="/static/logo.jpg" class="cu-avatar xl round"/>
          <view class="text-lg text-black text-bold margin-top">口袋心理</view>
          <view class="margin-top-sm">您尚未登录, 是否使用微信静默登录</view>

          <view class="flex justify-center margin-top-xl">
            <view class="cu-btn line-black lg btn-submit" @tap="close">取消</view>
            <view class="cu-btn bg-orange lg btn-submit" @tap="confirm">确认</view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import loginServer from '@/server/login'

export default {
  name: "psyLogin",
  props: {
    canIUseGetUserProfile: {
      type: Boolean,
      default: false
    },
    //是否跳转 1 咨询 2 课程 3 评价
    isNav: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      show: ""
    }
  },
  methods: {
    open() {
      this.show = 'login'
    },
    async confirm() {
      await loginServer.login()
      this.close()
    },
    close() {
      this.show = ""

      if (this.isNav > 0) {
        let url = ''
        switch (this.isNav) {
          case 1:
            url = '/pages/consult/index'
            break;
          case 2:
            url = '/pages/course/index'
            break;
          case 3:
            url = '/pages/evaluation/index'
            break;
        }
        uni.redirectTo({
          url: url,
        })
      }
    }
  }
}
</script>

<style scoped>
.btn-submit {
  width: 300upx;
  margin: 10upx;
}
</style>