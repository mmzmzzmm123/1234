<template>
  <view class="course">
    <view class="page">
      <view class="header">
        <uni-icons type="closeempty" size="24" class="header-icon-left" @tap="goHome"/>
        <text class="header-title">咨询师详情</text>
        <uni-icons  type="more-filled" size="24" class="header-icon-right"/>
      </view>
      <view class="info">
        <view class="info-avatar">
          <image :src="consultInfo.avatar" class="info-avatar-img"/>
          <view class="info-content">
            <view class="info-content-view">
              <text class="info-content-userName">{{ consultInfo.userName }}</text>
              <view class="info-content-image">
                <uni-icons  type="location" size="14" class="icon_location"/>
                <text class="info-content-gps">四川-成都</text>
              </view>
            </view>
            <view class="info-content-tags">
              <view class="info-content-tag" v-for="tag in tabs">
                <text class="info-content-tag-text">{{ tag }}</text>
              </view>
            </view>
            <text class="info-content-info">{{ consultInfo.info }}</text>
          </view>
        </view>
        <view class="info-nums">
          <view class="info-nums-item">
            <view class="info-nums-item-num">{{ consultInfo.workNum }}人</view>
            <view class="info-nums-item-title">咨询人数</view>
          </view>
          <view class="info-nums-item">
            <text class="info-nums-item-num">{{ consultInfo.workTime }}小时</text>
            <text class="info-nums-item-title">服务时长</text>
          </view>
          <view class="info-nums-item">
            <text class="info-nums-item-num">{{ consultInfo.workHours }}小时</text>
            <text class="info-nums-item-title">服务时长</text>
          </view>
        </view>
      </view>
      <view class="serve">
        <text class="serve-title">咨询服务</text>
        <view class="serve-conten">
          <view class="serve-conten-item" v-for="item in serveList">
            <view class="serve-conten-item-header">
              <image v-if="item.name === '面对面咨询'" src="/static/consult/detail/serve1.png" class="serve-conten-item-icon"/>
              <image v-else-if="item.name === '视频咨询'" src="/static/consult/detail/serve2.png" class="serve-conten-item-icon"/>
              <image v-else-if="item.name === '语音咨询'" src="/static/consult/detail/serve3.png" class="serve-conten-item-icon"/>
              <text class="serve-conten-item-name">{{ item.name }}</text>
              <view class="serve-conten-item-price">
                <text class="serve-conten-item-price-text">¥</text>
                <text class="serve-conten-item-price-text">{{ item.price }}</text>
                <text class="serve-conten-item-price-text">元/次</text>
              </view>
            </view>
            <view class="serve-info">
              <text class="serve-info-text">{{ item.info }}</text>
              <button @tap="toBuy(item)" class="serve-info-btn">
                立即预约
              </button>
            </view>
          </view>
        </view>
        <view class="serve-more">
          <text class="serve-more-title" @tap="goHome">更多服务 > </text>
        </view>
      </view>
      <view class="consult-desc" v-html="consultInfo.detail"/>
      <view class="page-bottom">
        <view class="page-bottom-image" @tap="goHome">
          <image src="/static/course/menu/index.png" class="page-bottom-icon"/>
          <text class="page-bottom-home">首页</text>
        </view>
        <button @tap="buy" class="page-bottom-btn">
          <text class="text_26">立即预约</text>
        </button>
      </view>

      <uni-popup ref="popup" type="dialog">
        <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="closeLoginConfirm" @confirm="confirmLogin"/>
      </uni-popup>

      <uni-popup ref="selectServe" type="bottom">
        <view class="serve serve-model">
          <uni-icons type="closeempty" size="24" class="serve-title-ext" @tap="closeServe"/>
          <view class="serve-conten">
            <view class="serve-conten-item" v-for="item in serveList">
              <view class="serve-conten-item-header">
                <image v-if="item.name === '面对面咨询'" src="/static/consult/detail/serve1.png" class="serve-conten-item-icon"/>
                <image v-else-if="item.name === '视频咨询'" src="/static/consult/detail/serve2.png" class="serve-conten-item-icon"/>
                <image v-else-if="item.name === '语音咨询'" src="/static/consult/detail/serve3.png" class="serve-conten-item-icon"/>
                <text class="serve-conten-item-name">{{ item.name }}</text>
                <view class="serve-conten-item-price">
                  <text class="serve-conten-item-price-text">¥</text>
                  <text class="serve-conten-item-price-text">{{ item.price }}</text>
                  <text class="serve-conten-item-price-text">元/次</text>
                </view>
              </view>
              <view class="serve-info">
                <text class="serve-info-text">{{ item.info }}</text>
                <button @tap="toBuy(item)" class="serve-info-btn">
                  立即预约
                </button>
              </view>
            </view>
          </view>
          <view class="serve-more">
            <text class="serve-more-title" @tap="goHome">更多服务 > </text>
          </view>
        </view>
      </uni-popup>
    </view>
  </view>
</template>

<script>
import utils from "@/utils/common";
import consultServer from "@/server/consult/consult";
import loginServer from '@/server/login'
import wxJS from "@/server/wxJS.js"
export default {
  data() {
    return {
      userInfo: {},
      tabs: [],
      serveList: [],
      consultInfo: {},
      consultId: 0,
      redirectUri:location.href,
      currentCatalogue: {},
    };
  },
  async created() {
    this.consultId = utils.getParam(location.href, "id")
    await this.getConsultInfo()
    await this.getConsultServe()
    if (this.consultInfo.tabs) {
      this.tabs = this.consultInfo.tabs.split(',')
    }
    // this.share()
  },
  mounted() {
    // this.userInfo = uni.getStorageSync("userInfo")
    this.userInfo = utils.getUserInfo()
    console.log(this.consultId)
    if (!utils.checkLogin()) {
      return this.openLoginConfirm()
    }
  },
  methods: {
    async getConsultInfo() {
      this.consultInfo = await consultServer.getConsultInfo(this.consultId)
    },
    async getConsultServe() {
      this.serveList = await consultServer.getConsultServe(this.consultId)
    },
    goHome() {
      uni.redirectTo({
        url: "/pages/consult/index",
      })
    },
    buy() {
      this.$refs.selectServe.open('bottom')
    },
    toBuy(item) {
      console.log(item)
      this.confirmServe()
      uni.navigateTo({
        url: "/pages/consult/orderConfirm?type=0&id=" + item.id,
      });
    },
    closeServe() {
      this.$refs.selectServe.close()
    },
    confirmServe() {
      this.$refs.selectServe.close()
    },
    // 登录
    async confirmLogin () {
      await loginServer.login();
      this.$refs.popup.close()
    },
    closeLoginConfirm() {
      this.$refs.popup.close()
    },
    openLoginConfirm() {
      this.$refs.popup.open()
    },
    share() {
      const title = this.consultInfo.name
      const desc = this.consultInfo.detail
      const link = window.location.href
      const img = this.consultInfo.iconUrl
      const url = window.location.href
      console.log('consultInfo: ', this.consultInfo)
      wxJS.getConfig(title, desc, link, img, url);
    }
  },
};
</script>

<style lang="scss">
.page {
  background-color: rgba(248,248,248,1.000000);
  position: relative;
  width: 750upx;
  height: 2692upx;
  //overflow: hidden;
  display: flex;
  flex-direction: column;
}
.header {
  position: fixed;
  top: 0upx;
  background-color: #FFFFFF;
  width: 750upx;
  height: 88upx;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}
.header-icon-left {
  position: absolute;
  left: 16upx;
}
.header-title {
  color: rgba(51,51,51,1);
  font-size: 34upx;
  font-weight: 500;
}
.header-icon-right {
  position: absolute;
  right: 16upx;
}
.info {
  margin-top: 98upx;
  background-color: #fff;
  width: 750upx;
  height: 402upx;
  display: flex;
  flex-direction: column;
}
.info-avatar {
  width: 702upx;
  height: 174upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin: 32upx 0 0 24upx;
}
.info-avatar-img {
  width: 172upx;
  height: 172upx;
}
.info-content {
  width: 498upx;
  height: 174upx;
  display: flex;
  flex-direction: column;
}
.info-content-view {
  width: 498upx;
  height: 42upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
}
.info-content-userName {
  height: 42upx;
  color: rgba(51,51,51,1);
  font-size: 30upx;
  font-weight: 600;
  text-align: left;
  white-space: nowrap;
  line-height: 42upx;
}
.info-content-image {
  margin-top: 10upx;
  display: flex;
}
.icon_location {
  width: 16upx;
  height: 18upx;
  margin-right: 12upx;
}
.info-content-gps {
  color: rgba(119,119,119,1);
  font-size: 22upx;
  height: 30upx;
  line-height: 30upx;
}
.info-content-tags {
  width: 248upx;
  height: 34upx;
  margin-top: 8upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
}
.info-content-tag {
  background-color: rgba(255,112,63,0.100000);
  border-radius: 4upx;
  height: 34upx;
  display: flex;
  flex-direction: column;
  width: 120upx;
}
.info-content-tag-text {
  width: 88upx;
  height: 30upx;
  overflow-wrap: break-word;
  color: rgba(255,112,63,1);
  font-size: 22upx;
  
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 30upx;
  margin: 2upx 0 0 16upx;
}
.tag_2 {
  background-color: rgba(255,112,63,0.100000);
  border-radius: 4upx;
  height: 34upx;
  display: flex;
  flex-direction: column;
  width: 120upx;
}
.text_5 {
  width: 88upx;
  height: 30upx;
  overflow-wrap: break-word;
  color: rgba(255,112,63,1);
  font-size: 22upx;
  
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 30upx;
  margin: 2upx 0 0 16upx;
}
.info-content-info {
  width: 498upx;
  height: 74upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 26upx;
  
  font-weight: normal;
  text-align: left;
  line-height: 37upx;
  margin-top: 16upx;
}
.info-nums {
  line-height: 76upx;
  height: 76upx;
  display: flex;
  margin-top: 20upx;
  margin-bottom: 20upx;
  justify-content: space-evenly;
}
.info-nums-item {
  width: 96upx;
  height: 76upx;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
.info-nums-item-num {
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  
  font-weight: 500;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
  margin-left: 2upx;
}
.info-nums-item-title {
  height: 31upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 22upx;
  
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 30upx;
  margin-top: 8upx;
}
.serve {
  background-color: #FFFFFF;
  width: 750upx;
  //height: 751upx;
  margin-top: 16upx;
  display: flex;
  flex-direction: column;
  justify-content: flex-center;
}
.serve-model {
  position: relative;
  padding-top: 80upx;
}
.serve-title {
  width: 120upx;
  height: 42upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 30upx;
  font-weight: 500;
  text-align: center;
  white-space: nowrap;
  line-height: 42upx;
  margin: 32upx 0 0 24upx;
}
.serve-title-ext {
  position: absolute;
  right: 24upx;
  top: 24upx;
}
.serve-conten {
  width: 702upx;
  max-height: 536upx;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin: 32upx 0 0 24upx;
}
.serve-conten-item {
  box-shadow: 0px 2px 10px 0px rgba(175,45,0,0.100000);
  background-color: #FFFFFF;
  border-radius: 12upx;
  height: 188upx;
  margin-bottom: 16upx;
}
.serve-conten-item-header {
  width: 654upx;
  height: 40upx;
  flex-direction: row;
  display: flex;
  margin: 23upx 0 0 24upx;
}
.serve-conten-item-icon {
  width: 38upx;
  height: 38upx;
  margin-top: 1upx;
}
.serve-conten-item-name {
  width: 130upx;
  height: 38upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  
  font-weight: 500;
  text-align: center;
  white-space: nowrap;
  line-height: 37upx;
  margin: 1upx 0 0 16upx;
}
.serve-conten-item-price {
  width: 119upx;
  height: 40upx;
  overflow-wrap: break-word;
  font-size: 0upx;
  font-weight: 500;
  text-align: right;
  white-space: nowrap;
  line-height: 30upx;
  margin-left: 377upx;
}
.serve-conten-item-price-text {
  color: rgba(255,63,100,1.000000);
  font-size: 22upx;
  text-align: left;
  white-space: nowrap;
}
.serve-info {
  margin-top: 6upx;
  height: 66upx;
  position: relative;
}
.serve-info-text {
  width: 446upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 24upx;
  position: absolute;
  left: 10px;
}
.serve-info-btn {
  border-radius: 25upx;
  height: 50upx;
  line-height: 50upx;
  border: 1px solid rgba(255,112,63,1);
  color: rgba(255,112,63,1);
  font-size: 24upx;
  text-align: center;
  position: absolute;
  right: 10px;
}

.serve-more {
  width: 136upx;
  height: 37upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin: 40upx 0 32upx 307upx;
}
.serve-more-title {
  width: 104upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-weight: normal;
  text-align: right;
  white-space: nowrap;
  line-height: 37upx;
}
.consult-desc {
  background-color: #FFFFFF;
  margin-top: 16upx;
  margin-bottom: 16upx;
  width: calc(100% - 48upx);
  padding-left: 24upx;
  padding-right: 24upx;
  padding-bottom: 150upx;
  ::v-deep img {
    width: 100%;
  }
}
.page-bottom {
  position: fixed;
  bottom: 0upx;
  background-color: #FFFFFF;
  width: 750upx;
  height: 120upx;
  margin-top: 40upx;
  display: flex;
  align-items: center;
}
.page-bottom-image {
  width: 48upx;
  height: 80upx;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 10upx 0 0 40upx;
}
.page-bottom-icon {
  width: 48upx;
  height: 48upx;
}
.page-bottom-home {
  color: rgba(51,51,51,1);
  font-size: 20upx;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 28upx;
  margin: 4upx 0 0 4upx;
}
.page-bottom-btn {
  background-color: rgba(255,112,63,1.000000);
  color: rgba(255,255,255,1);
  border-radius: 40upx;
  font-size: 28upx;
  font-weight: 600;
  height: 80upx;
  line-height: 80upx;
  width: 598upx;
  margin: 9upx 24upx 0 40upx;
  text-align: center;
}
</style>
