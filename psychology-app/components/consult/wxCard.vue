<template>
  <view class="section_7">
    <view class="image-wrapper_1">
      <image @tap="close" src="/static/consult/order/guanbi.png" class="label_8"></image>
    </view>
    <view class="group_9">
      <image src="/static/consult/order/bg.png"  class="group_10"></image>
      <text class="text_29">{{ data.nickName }}</text>
      <text class="text_30">{{ data.info }}</text>
      <image :src="data.avatar" class="image_2"></image>
    </view>
    <view class="group_11">
      <view class="box_8"></view>
    </view>
    <view class="group_12">
      <text class="text_31">擅长领域</text>
      <text class="paragraph_1">{{ data.wayStr }}</text>
      <text class="paragraph_1">{{ data.tabs }}</text>
      <text class="text_32">咨询寄语</text>
      <text class="text_33">{{ data.zxWord }}</text>
      <view class="section_8">
        <image :src="data.wxCard"  class="box_9"></image>
        <image src="/static/consult/order/card.png" class="box_10"></image>
      </view>
    </view>
    <view class="group_13">
      <view class="box_11">
        <view class="box_12"></view>
        <text class="text_34">长按图片保存图片</text>
        <view class="box_13"></view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: "wxCard",
  props: ["data"],
  data() {
    return {
      workList: [],
    }
  },
  methods: {
    close() {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>
.section_7 {
  background-color: rgba(51,51,51,0.500000);
  position: absolute;
  left: 0upx;
  top: 0upx;
  width: 750upx;
  height: 1624upx;
  display: flex;
  flex-direction: column;
  z-index: 99999;
}
.image-wrapper_1 {
  width: 750upx;
  height: 319upx;
  display: flex;
  flex-direction: column;
}
.label_8 {
  width: 40upx;
  height: 40upx;
  margin: 265upx 0 0 686upx;
}
.group_9 {
  border-radius: 16upx 16upx 0 0;
  background-color: rgba(255,255,255,1.000000);
  position: relative;
  width: 620upx;
  height: 478upx;
  display: flex;
  flex-direction: column;
  margin: -1upx 0 0 65upx;
}
.group_10 {
  border-radius: 16upx 16upx 0 0;
  background-image: url('/static/consult/order/bg.png');
  width: 620upx;
  height: 240upx;
}
.text_29 {
  width: 60upx;
  height: 42upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 30upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  text-align: left;
  white-space: nowrap;
  line-height: 42upx;
  margin: 97upx 0 0 280upx;
}
.text_30 {
  width: 524upx;
  height: 74upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: center;
  line-height: 37upx;
  margin: 9upx 0 16upx 48upx;
}
.image_2 {
  position: absolute;
  left: 245upx;
  top: 175upx;
  width: 130upx;
  height: 130upx;
  border-radius: 100%;
}
.group_11 {
  height: 40upx;
  background: url('/static/consult/order/bg2.png') 100% no-repeat;
  background-size: 100% 100%;
  margin-left: 65upx;
  display: flex;
  flex-direction: column;
  width: 620upx;
}
.box_8 {
  background-color: rgba(216,216,216,1.000000);
  width: 524upx;
  height: 1upx;
  display: flex;
  flex-direction: column;
  margin: 20upx 0 0 48upx;
}
.group_12 {
  background-color: rgba(255,255,255,1.000000);
  border-radius: 0 0 16upx 16upx;
  width: 620upx;
  height: 470upx;
  margin-left: 65upx;
  display: flex;
  flex-direction: column;
}
.text_31 {
  width: 524upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
  margin: 16upx 0 0 48upx;
}
.paragraph_1 {
  width: 524upx;
  height: 66upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 24upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  line-height: 33upx;
  margin: 11upx 0 0 48upx;
}
.text_32 {
  width: 524upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
  margin: 29upx 0 0 48upx;
}
.text_33 {
  width: 524upx;
  height: 99upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 24upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  line-height: 33upx;
  margin: 11upx 0 0 48upx;
}
.section_8 {
  width: 239upx;
  height: 100upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin: 24upx 0 40upx 191upx;
}
.box_9 {
  width: 100upx;
  height: 100upx;
  display: flex;
  flex-direction: column;
}
.box_10 {
  border-radius: 6upx;
  width: 99upx;
  height: 99upx;
  display: flex;
  flex-direction: column;
}
.group_13 {
  width: 750upx;
  height: 251upx;
  display: flex;
  flex-direction: column;
}
.box_11 {
  width: 524upx;
  height: 40upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin: 40upx 0 0 113upx;
}
.box_12 {
  background-color: rgba(216,216,216,1.000000);
  width: 118upx;
  height: 1upx;
  margin-top: 19upx;
  display: flex;
  flex-direction: column;
}
.text_34 {
  width: 224upx;
  height: 40upx;
  overflow-wrap: break-word;
  color: rgba(255,255,255,1);
  font-size: 28upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: center;
  white-space: nowrap;
  line-height: 40upx;
  margin-left: 32upx;
}
.box_13 {
  background-color: rgba(216,216,216,1.000000);
  width: 118upx;
  height: 1upx;
  display: flex;
  flex-direction: column;
  margin: 19upx 0 0 32upx;
}
.image_3 {
  width: 750upx;
  height: 68upx;
  margin-top: -1upx;
}
</style>