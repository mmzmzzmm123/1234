<template>
  <scroll-view scroll-y scroll-with-animation class="serve-list">
    <view class="serve-item" v-for="(item, index) in serveList" v-if="index < num">
      <view class="serve-left">
        <view class="serve-name">{{ item.name }}</view>
        <view class="serve-tip" v-if="item.bound === 1">
          新人特惠
        </view>
        <view class="serve-mode">
          {{ item.modeName }} | {{ item.time }}分钟
        </view>
      </view>
      <view class="serve-right">
        <view class="serve-price">
          <text class="price-one" v-if="item.type === 2">单次{{ parseFloat(item.price/item.num).toFixed(2) }}</text>
          <text v-if="item.type === 2">/</text>
          <text>¥</text>
          <text class="price-two">{{ item.price.toFixed(2) }}</text>
          <text>元</text>
        </view>
        <button @tap="toBuy(item)" class="serve-btn">
          立即预约
        </button>
      </view>
    </view>
  </scroll-view>
</template>
<script>
export default {
  props: ['serveList', 'num'],
  data() {
    return {
    }
  },
  methods: {
    toBuy(item) {
      this.$emit('toBuy', item)
    }
  }
}
</script>
<style lang="scss" scoped>
.serve-list {
  margin-top: 24upx;
  max-height: 800upx;
}
.serve-item {
  margin-bottom: 16upx;
  height: 160upx;
  background: #FFFFFF;
  //box-shadow: 0rpx 2rpx 10rpx 0rpx rgba(175,45,0,0.1);
  border-radius: 12rpx;
  border: 1upx solid rgba(175,45,0,0.1);
  box-shadow: 0rpx 2rpx 10rpx 0rpx rgba(175,45,0,0.1);
  padding: 24upx;
  position: relative;
  display: flex;
}
.serve-left {
  width: 400upx;
  //height: 40upx;
  //flex-direction: row;
  //display: flex;
}
.serve-name {
  //width: 398upx;
  height: 37upx;
  font-size: 26upx;
  overflow-wrap: break-word;
  font-weight: 500;
  color: #333333;
}
.serve-tip {
  margin-top: 8upx;
  width: 120upx;
  height: 34upx;
  background: rgba(255,63,100,0.1);
  border-radius: 4upx;
  border: 1upx solid #FF3F64;
  font-size: 22upx;
  color: #FF3F64;
  line-height: 34upx;
  text-align: center;
}
.serve-mode {
  position: absolute;
  bottom: 16upx;
  font-size: 24upx;
  color: #777777;
}
.serve-right {
  //margin-top: 6upx;
  //height: 66upx;
  //position: relative;
  text-align: right;
  width: 380upx;
}
.serve-price {
  font-weight: 500;
  font-size: 22upx;
  color: #FF3F64;
  .price-one {
    color: #777777;
  }
  .price-two {
    font-size: 28upx;
  }
}
.serve-info-text {
  width: 446upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 24upx;
  position: absolute;
  left: 10px;
}
.serve-btn {
  border-radius: 25upx;
  height: 50upx;
  line-height: 50upx;
  border: 1px solid rgba(255,112,63,1);
  color: rgba(255,112,63,1);
  font-size: 24upx;
  text-align: center;
  position: absolute;
  right: 20upx;
  bottom: 16upx;
}

</style>