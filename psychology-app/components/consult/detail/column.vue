<template>
  <view class="list">
    <view class="item" v-for="(item, index) in list" @tap="toDetail(item)" v-if="limit && index < limit">
      <image :src="item.img" mode="widthFix" class="item-imgage"/>
      <view class="item-content">
        <view class="item-name txt-overflow">{{ item.title }}</view>
        <view class="item-info txt-overflow-line2">{{ item.info }}</view>
      </view>
    </view>
  </view>
</template>
<script>
export default {
  props: ['list', 'limit'],
  data() {
    return {
    }
  },
  methods: {
    toDetail(item) {
      if (item.url) {
        window.location.href = item.url
      }
    }
  }
}

</script>
<style lang="scss" scoped>
.list {
  //display: flex;
  padding-bottom: 12upx;
}
.item {
  margin: 24upx;
  width: 750upx;
  height: 140upx;
  background: #FFFFFF;
  //box-shadow: 0upx 2upx 10upx 0upx rgba(175,45,0,0.1);
  //border-radius: 12upx;
  //margin-right: 24upx;
  display: flex;
}
.item-content {
  //width: 366upx;
  //height: 282upx;
  //display: flex;
  //flex-direction: column;
  //justify-content: space-between;
  padding: 10upx 24upx;
  width: 546upx;
}
.item-imgage {
  border-radius: 12upx;
  width: 132upx;
  height: 130upx;
}
.item-name {
  width: 546upx;
  height: 37upx;
  font-size: 26upx;
  font-family: PingFangSC-Medium, PingFang SC;
  font-weight: 500;
  color: #333333;
  line-height: 37upx;
}
.item-info {
  margin-top: 12upx;
  width: 546upx;
  height: 74upx;
  font-size: 26upx;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #AAAAAA;
  line-height: 37upx;
}

</style>