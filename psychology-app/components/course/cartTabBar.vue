<template>
  <view class="page-bottom">
    <navigator
      url="/pages/course/index"
      open-type="redirect"
      class="p-b-btn"
    >
      <image class="index-icon" src="/static/course/menu/index.png"></image>
      <text>首页</text>
    </navigator>
    <view v-if="payType === 0">
      <view class="start-test" @tap="cartShow" v-if="!isBuy">立即购买</view>
      <view class="start-test" v-else>已购买</view>
    </view>
    <view class="start-test" @tap="courseShow" v-else>限时免费</view>
  </view>
</template>
<script>
export default {
  props: ['payType', 'isBuy'],
  methods: {
    cartShow() {
      this.$emit("cartShow");
    },
    courseShow() {
      this.$emit("courseShow");
    }
  },
};
</script>
<style lang="scss">
/* 底部操作菜单 */
.page-bottom {
  position: fixed;
  bottom: 0upx;
  display: flex;
  justify-content: left;
  align-items: center;
  width: 750upx;
  height: 100upx;
  background-color: #fff;
  padding-bottom: constant(safe-area-inset-bottom); /* 兼容 iOS 设备 */
  padding-bottom: env(safe-area-inset-bottom); /* 兼容 iPhone X 及以上设备 */

  .p-b-btn {
    width: 128upx;
    display: flex;
    flex-direction: column;
    align-items: center;
    font-size: 20upx;

    .index-icon {
      width: 48upx;
      height: 48upx;
    }
  }

  .start-test {
    width: 600upx;
    height: 80upx;
    line-height: 80upx;
    background: #ff703f;
    border-radius: 40upx;
    text-align: center;
    font-size: 28upx;
    color: #ffffff;
    margin: 9upx 24upx;
    margin-left: 0;
  }
}
</style>
