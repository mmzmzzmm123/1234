<template>
  <view class="catalogue-list">
    <view
      class="catalogue-item"
      @tap="catalogueItemClick(catalogueItem, index)"
      :class="{ 'play-item': catalogueItem.enabled }"
      v-for="(catalogueItem, index) in catalogueList"
    >
      <view class="item-content">
        <view class="content-title"
          ><span v-if="payType === 0 && isBuy == 0 && catalogueItem.type == 1" class="try">试听</span
          ><span class="text txt-overflow"
            >{{ index + 1 }}、{{ catalogueItem.topic }}</span
          ></view
        >
        <view class="content-time">
          <image
            class="headset-icon"
            src="/static/course/product/headset.png"
          ></image
          >{{ formatDuration(catalogueItem.duration) }}</view
        >
      </view>
      <view class="item-play">
        <image
          class="play-icon"
          :src="
            payType === 1 || isBuy == 1 || catalogueItem.type == 1
              ? '/static/course/product/play.png'
              : '/static/course/product/lock.png'
          "
        ></image>
      </view>
    </view>
  </view>
</template>
<script>
import formatTime from "@/utils/formatTime.js"
export default {
  props: ["catalogueList", "isBuy", "payType"],
  methods: {
    catalogueItemClick(item, index) {		
      this.$emit("catalogueItemClick", item, index);
    },
	formatDuration(seconds) {
	  return formatTime.formatDuration(seconds)
	}   
  },
};
</script>
<style lang="scss">
@import "@/style/common.scss";

.catalogue-item {
  height: 134upx;
  border-bottom: 1px solid #ccc;
  font-size: 28upx;
  display: flex;
  flex-direction: row;
  align-items: center;
  color: #777777;
  overflow: hidden;
  &.play-item {
    color: #000;
    font-weight: 600;
  }
  .item-content {
    flex: 1;
    max-width: calc(100% - 48upx);
    .content-title {
      margin-bottom: 19upx;
      display: flex;
      flex-direction: row;
      .text {
        flex: 1;
      }
    }
    .content-time {
      .headset-icon {
        width: 24upx;
        height: 24upx;
        margin-right: 8upx;
      }
    }
  }
  .item-play {
    width: 32upx;
    height: 32upx;
    margin-right: 16upx;
    .play-icon {
      width: 32upx;
      height: 32upx;
    }
  }
  .try {
    margin-right: 16upx;
    text-align: center;
    line-height: 34upx;
    display: inline-block;
    width: 76upx;
    height: 34upx;
    background: #8990a7;
    border-radius: 4upx;
    font-size: 22upx;
    color: #fff;
  }
}

</style>
