<template>
  <view class="page">
    <image class="bg" src="https://zx-1316267898.cos.ap-beijing.myqcloud.com/class_1694882558057168898_zxlc.jpeg"/>
  </view>
</template>

<script>
export default {
  name: "invitation"
}
</script>

<style lang="scss">
.page {
  //text-align: center;
  width: 750upx;
  //height: 2000upx;
  padding-bottom: calc(constant(safe-area-inset-bottom) + 50px); /* 兼容 iOS 设备 */
  padding-bottom: calc(env(safe-area-inset-bottom) + 50px); /* 兼容 iPhone X 及以上设备 */
  .bg {
    width: 750upx;
    height: 2000upx;
  }
}
</style>