<template>
  <scroll-view scroll-x scroll-with-animation class="page" :style="{ height: pageHeight }">
    <view class="list">
      <view class="item" v-for="item in teamList">
        <image :src="item.img" mode="widthFix" class="item-imgage"></image>
        <view class="item-content" @tap="toDetail(item)">
          <view class="item-name txt-overflow">{{ item.title }}文章简介啦啦啦啦啦啦啦啦啦啦啦啦啦啦文章简介啦啦啦啦啦啦啦啦啦啦啦啦啦啦</view>
          <view class="item-info txt-overflow">{{ item.info }}文章简介啦啦啦啦啦啦啦啦啦啦啦啦啦啦文章简介啦啦啦啦啦啦啦啦啦啦啦啦啦啦</view>
          <view class="item-time">
            <image src="/static/consult/time.png" class="icon-time"></image>
            <text class="time">{{ item.createTime }}</text>
          </view>
        </view>
      </view>
    </view>
    <view class="footer">—— 已经到底啦 ——</view>
  </scroll-view>
</template>

<script>
import team from '@/components/consult/detail/team.vue'
import consultServer from "@/server/consult/consult";
import utils from "@/utils/common";
let app = getApp();

export default {
  components: { team },
  data() {
    return {
      pageHeight: app.globalData.windowHeight + 'px',
      consultId: null,
      teamList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 999,
        orderByColumn: 'createTime',
        isAsc: 'descending',
      },
    }
  },
  onShow() {
    this.consultId = utils.getParam(location.href, "id")
    this.getConsultTeam()
  },
  methods: {
    async getConsultTeam() {
      this.teamList = await consultServer.getConsultColumn('0', this.consultId, this.queryParams)
    },
    toDetail(item) {
      if (item.url) {
        window.location.href = item.url
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.page {
  background: #F8F8F8;
  padding: 24upx;
}
.list {
  //display: flex;
  //padding: 24upx;
}
.item {
  //width: 702upx;
  //height: 200upx;
  background: #FFFFFF;
  border-radius: 12upx;
  display: flex;
  margin: 16upx 0;
  padding: 24upx;
  display: flex;
  align-items: center;
}
.item-content {
  margin-left: 24upx;
  height: 130upx;
  position: relative;
}
.item-imgage {
  width: 132upx;
  height: 130upx;
  border-radius: 12upx;
}
.item-name {
  width: 500upx;
  font-size: 26upx;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: #333333;
}
.item-info {
  margin-top: 12upx;
  width: 500upx;
  font-size: 26upx;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #AAAAAA;
}
.item-time {
  //height: 40upx;
  width: 400upx;
  display: flex;
  align-items: center;
  position: absolute;
  bottom: 0;
}
.icon-time {
  width: 24upx;
  height: 24upx;
}
.time {
  //width: 206upx;
  margin-left: 16upx;
  height: 33upx;
  font-size: 24upx;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #AAAAAA;
  line-height: 33upx;
}
.footer {
  margin-top: 24upx;
  text-align: center;
  height: 28upx;
  font-size: 20upx;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #777777;
  line-height: 28upx;
}

</style>