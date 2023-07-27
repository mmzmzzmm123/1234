<template>
  <scroll-view scroll-x scroll-with-animation>
    <view class="list">
      <view class="item" v-for="item in courseList">
        <view class="item-content">
          <image :src="item.iconUrl" class="item-imgage"></image>
          <text class="item-name" @tap="toDetail(item.id)">{{ item.name }}</text>
        </view>
      </view>
    </view>
  </scroll-view>
</template>
<script>
export default {
  props: ['courseList'],
  data() {
    return {
    }
  },
  methods: {
    toDetail(id) {
      console.log(id)
      uni.navigateTo({
        url: "/pages/course/courseDetail?id=" + id,
      });
    }
  }
}

</script>
<style lang="scss" scoped>
.list {
  display: flex;
  padding: 24upx;
}
.item {
  width: 366upx;
  height: 302upx;
  background: #FFFFFF;
  box-shadow: 0upx 2upx 10upx 0upx rgba(175,45,0,0.1);
  border-radius: 12upx;
  margin-right: 24upx;
}
.item-content {
  width: 366upx;
  height: 282upx;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.item-imgage {
  border-radius: 12upx 12upx 0 0;
  width: 366upx;
  height: 192upx;
}
.item-name {
  width: 318upx;
  height: 74upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  line-height: 37upx;
  margin: 16upx 0 0 24upx;
}

</style>