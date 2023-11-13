<template>
  <view class="content">
    <view class="list" v-if="list.length > 0">
      <view class="item" v-for="(item,index) in list">
        <view class="item-icon"></view>
        <view class="item-tiem">{{ [2,3].includes(type) ? item.param1 : item.startTime + ' ~ ' + item.endTime }}</view>
        <view class="item-info" v-if="[1,2,4,5,6].includes(type)">
          <template v-if="type === 1">
            {{ item.param1 + '|' + item.param2 + '|' + item.param3 }}
          </template>
          <template v-else-if="type === 2">
            编号：{{ item.param2 }}
          </template>
          <template v-else-if="type === 4">
            {{ item.param1 + '|' + item.param2 }}
          </template>
          <template v-else-if="type === 5">
            个人咨询 （共{{ item.num }}个小时）
          </template>
          <template v-else>
            {{ item.param2 + '|' + item.param3 }}
          </template>
        </view>
        <view class="item-info" v-if="type === 6">
          {{ item.param1 }} （共{{ item.num }}个小时）
        </view>
        <view class="margin-top-xs" v-if="item.img">
          <image v-for="it in item.img.split(',')" style="width: 188upx;height: 182upx;margin-right: 20upx" :src="it" mode="widthFix"></image>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: "TimeLine",
  props: {
    type: {
      type: Number,
      default: 1,
    },
    list: {
      type: Array,
      default: [],
    },
  },
}
</script>

<style scoped>
.list {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.item {
  position: relative;
  height: 100%;
  width: 100%;
  padding-left: 64upx;
  padding-bottom: 16upx;
}
.item-icon {
  background-color: rgba(255,255,255,1.000000);
  border-radius: 100%;
  position: absolute;
  left: 20upx;
  top: 0upx;
  width: 20upx;
  height: 20upx;
  border: 2px solid #FF703F;
  display: block;
  z-index: 9;
  text-align: center;
}
.item:not(:last-child)::after {
  content: "";
  display: block;
  position: absolute;
  left: 34upx;
  top: 0;
  z-index: 8;
  height: 100%;
  width: 1upx;
  background: linear-gradient(to bottom, #AAAAAA 0%, #AAAAAA 50%,transparent 50%);
  background-size: 2upx 12upx;
  background-repeat: repeat-y;
}
.item-tiem {
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 24upx;
  font-family: PingFangSC-Regular;
  text-align: left;
}
.item-info {
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  text-align: left;
  margin-top: 8upx;
  width: 650upx;
}
</style>