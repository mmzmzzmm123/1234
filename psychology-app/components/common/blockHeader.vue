<template>
  <view class="btn-block">
    <view class="btn-text">{{ title }}</view>
  </view>
</template>
<script>
export default {
  props: {
    title: {
      type: String,
      default: '标题',
    }
  }
}
</script>
<style lang="scss">
.btn-block {
  border-radius: 20upx;
  border: 2px solid #FF703F;
  margin-top: 40upx;
  padding: 16upx 52upx;
  text-align: center;
}
.btn-text {
  height: 40upx;
  color: rgba(255, 112, 63, 1);
  font-size: 36upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  border-bottom: 17upx solid #FFE4DB;
}
</style>