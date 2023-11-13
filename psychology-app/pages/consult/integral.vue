<template>
  <view class="page">
    <view class="header">
      <view class="nav" v-if="false">
        <uni-nav-bar fixed="true" left-icon="closeempty" :border="false" title="我的积分" @clickLeft="back"/>
      </view>
      <view class="info">
        <view class="info-box">
          <view class="usable">
            <text class="usable-title">可用积分</text>
            <text class="usable-val">{{ balance }}</text>
          </view>
          <view class="rule">
            <text class="rule-title">积分规则</text>
            <image src="/static/icon/help.png" class="rule-icon"></image>
          </view>
        </view>
        <view class="tab">
          <view class="tab-items">
            <view :class="tab.val === tabCur ? 'tab-active' : 'tab-default'" v-for="tab in tabList" @tap="tabSelect(tab.val)">{{ tab.label }}
              <view class="tab-section" v-if="tab.val === tabCur"/>
            </view>
          </view>
        </view>
      </view>
    </view>

    <scroll-view class="list" scroll-y scroll-with-animation @scrolltolower="loadMore">
      <view class="item" v-for="item in list">
        <view class="item-left">
          <view class="item-name">{{ item.title }}</view>
          <view class="item-time">处理时间：{{ item.createTime }}</view>
        </view>
        <view class="item-right">{{ item.type === 1 ? '+' : '-' }}{{ item.integral }}</view>
        <view class="item-divider"></view>
      </view>

      <view class="footer">
        <view class="footer-left"></view>
        <text class="footer-text">已经到底了</text>
        <view class="footer-right"></view>
      </view>
    </scroll-view>
    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>
  </view>

</template>

<script>
import utils from "@/utils/common";
import server from "@/server/consult/user";
import loginServer from "@/server/login"

export default {
  data() {
    return {
      total: 0,
      queryParams: {
        uid: 0,
        type: 0,
        pageNum: 1,
        pageSize: 10,
      },
      balance: 0,
      tabCur: 0,
      tabList: [
        {
          label: "全部",
          val: 0,
        },
        {
          label: "收入",
          val: 1,
        },
        {
          label: "支出",
          val: 2,
        }
      ],
      list: [],
    }
  },
  async mounted() {
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback()) {
      this.userInfo = utils.getUserInfo()
    }
    if (!await utils.checkLogin()) {
      return this.openLoginConfirm()
    }
    this.queryParams.uid = this.userInfo.userId
    await this.getIntegral()
    await this.getUserIntegral()
  },
  methods: {
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
    async getUserIntegral() {
      const res = await server.getUserIntegral()
      if (res.code === 200) {
        this.balance = res.data
      }
    },
    async getIntegral() {
      const res = await server.getIntegral(this.queryParams)
      if (res.code == 200) {
        this.total = res.total
        this.list = [...this.list, ...res.rows]
      }
      console.log(res)
    },
    loadMore() {
      // console.log(this.list)
      if (this.list.length < this.total) {
        this.queryParams.pageNum += 1
        this.getIntegral()
      }
    },
    back() {
      uni.navigateTo({
        url: "/pages/consult/user",
      });
    },
    tabSelect(val) {
      this.tabCur = val
      this.queryParams.type = val
      this.queryParams.pageNum = 1
      this.list = []
      this.getIntegral()
    },
  }
}
</script>

<style scoped>
.page {
  background-color: rgba(248,248,248,1.000000);
  position: relative;
  width: 750upx;
  /*height: 100%;*/
  height: 1624upx;
  /*overflow: hidden;*/
  display: flex;
  flex-direction: column;
}
.header {
  width: 750upx;
  height: 242upx;
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 0;
  z-index: 99;
}
.nav {
  width: 750upx;
  height: 88upx;
}
.info {
  background-color: rgba(255,255,255,1.000000);
  width: 750upx;
  height: 223upx;
  margin-bottom: 19upx;
  display: flex;
  flex-direction: column;
}
.info-box {
  width: 686upx;
  height: 105upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin: 16upx 0 0 32upx;
}
.usable {
  width: 104upx;
  height: 105upx;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.usable-title {
  width: 104upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
}
.usable-val {
  width: 89upx;
  height: 56upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 40upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  text-align: left;
  white-space: nowrap;
  line-height: 56upx;
  margin-top: 12upx;
}
.rule {
  width: 136upx;
  height: 37upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
}
.rule-title {
  width: 104upx;
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
}
.rule-icon {
  width: 24upx;
  height: 24upx;
  margin-top: 7upx;
}
.tab {
  height: 88upx;
  width: 750upx;
  margin: 16upx 0 2upx 0;
  position: relative;
}
.tab-items {
  height: 45upx;
  display: flex;
  justify-content: space-evenly;
  margin-top: 22upx;
  font-size: 32upx;
}
.tab-active {
  /*width: 64upx;*/
  height: 45upx;
  color: #FF703F;
  font-size: 32upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  text-align: center;
}
.tab-default {
  /*width: 64upx;*/
  height: 45upx;
  color: rgba(51,51,51,1);
  font-family: PingFangSC-Regular;
}
.tab-section {
  background-color: rgba(255,112,63,1.000000);
  border-radius: 3upx;
  width: 48upx;
  height: 4upx;
  margin-left: 8upx;
  margin-top: 20upx;
}
.list {
  background-color: rgba(255,255,255,1.000000);
  width: 750upx;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  margin-top: 242upx;
  padding-bottom: 40upx;
}
.item {
  position: relative;
  width: 686upx;
  height: 94upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin: 20upx 32upx;
}
.item-left {
  width: 353upx;
  height: 78upx;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.item-name {
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
}
.item-time {
  color: rgba(119,119,119,1);
  font-size: 24upx;
  font-family: PingFangSC-Regular;
  margin-top: 8upx;
}
.item-right {
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  position: absolute;
  top: 40upx;
  right: 0;
}
.item-divider {
  position: absolute;
  bottom: 0;
  background-color: rgba(216,216,216,1.000000);
  width: 686upx;
  height: 1upx;
}
.footer {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 40upx;
}
.footer-left {
  background-color: #CCCCCC;
  width: 52upx;
  height: 1upx;
}
.footer-text {
  color: #777777;
  font-size: 20upx;
  font-family: PingFangSC-Regular;
  margin-left: 16upx;
  margin-right: 16upx;
}
.footer-right {
  background-color: #CCCCCC;
  width: 52upx;
  height: 1upx;
}
</style>