<template>
  <view class="page">
    <view class="page-content">
      <view class="nav-bar">
        <uni-nav-bar fixed="true" left-icon="closeempty" right-icon="more-filled" :border="false" title="我的咨询" @clickLeft="back"/>
      </view>
      <view class="tabs">
        <view class="tabs-list">
          <view v-for="i in classList" class="tab" :class="{ selected: status === i.id }" @tap="changeStatus(i.id)">
            <view>{{ i.name }}</view>
          </view>
        </view>
      </view>
      <view class="order-list">
        <view class="order-item" v-for="item in orderList">
          <view class="item-header">
            <view class="item-header-block"></view>
            <text class="item-header-timer">{{ item.createTime }}</text>
            <view class="item-header-countdown" v-if="item.status === 0">剩余<uni-countdown color="#FF703F" splitorColor="#FF703F" :show-day="false" :minute="14" :second="59"/>
            </view>
            <text class="item-header-status">{{ item.statusName }}</text>
          </view>
          <view class="item-info">
            <image :src="item.avatar" class="item-info-avatar"></image>
            <view class="item-info-group" @tap="toDetail(item.id)">
              <text class="item-name">{{ item.consultName }}</text>
              <text class="item-desc">{{ item.serveName }} X1</text>
            </view>
            <view class="item-price">
              <text class="item-price-unit">¥</text>
              <text class="item-price-num">{{ item.amount }}</text>
            </view>
          </view>
          <view class="item-info-box">
            <view class="box_2"></view>
          </view>
          <view class="item-info-bottom">
            <view class="bottom-title" v-if="item.status === 2 || item.status === 3">咨询时间</view>
            <button bindtap="onClick" class="button_1" v-if="item.status === 0">
              取消
            </button>
            <button @tap="toBuy(item)" class="button_2" v-if="item.status === 0 || item.status === 1">
              <text class="text_16">{{ item.status === 0 ? '支付' : '去预约'}}</text>
            </button>
            <view class="bottom-time" v-if="item.timeStart && (item.status === 2 || item.status === 3)">{{ item.timeStart.substr(0, 16) + '-' + item.timeEnd.substr(11, 5) }}</view>
          </view>
        </view>
      </view>
      <view class="footer-main">
        <view class="box_7"></view>
        <text class="footer-text">已经到底了</text>
        <view class="box_8"></view>
      </view>
    </view>

    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>
  </view>
</template>
<script>
import utils, { clientTypeObj } from "@/utils/common";
import orderServer from "@/server/consult/order";
import loginServer from "@/server/login"
export default {
  data() {
    return {
      status: 0,
      userInfo: {},
      orderList: [],
      classList: [
        {
          name: "全部",
          id: 0,
        },
        {
          name: "待付款",
          id: 1,
        },
        {
          name: "待预约",
          id: 2,
        },
        {
          name: "待咨询",
          id: 3,
        },
        {
          name: "已完成",
          id: 4,
        }
      ],
      clientTypeObj: clientTypeObj,
      redirectUri:location.href+"?t="+new Date().getTime()
    };
  },
  async created() {
    this.status = parseInt(utils.getParam(location.href, "status"))
  },
  async mounted() {
    // this.userInfo = uni.getStorageSync("userInfo")
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback(this.redirectUri)) {
      this.userInfo = uni.getStorageSync("userInfo")
    }
    if (!this.userInfo) {
      this.openLoginConfirm()
    }

    await this.getOrderList()
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
    back() {
      uni.navigateTo({
        url: "/pages/consult/user",
      });
    },
    changeStatus(status) {
      this.status = status
      this.getOrderList()
    },
    async getOrderList() {
      console.log(this.status)
      const status = this.status > 0 ? this.status - 1 : null
      const data = {
        status: status,
        userId: this.userInfo.userId
      }
      this.orderList = await orderServer.getOrderList(data);
    },
    toBuy(item) {
      uni.navigateTo({
        url: "/pages/consult/orderConfirm?id=" + item.serveId + "&orderId=" + item.id + "&type=" + item.status,
      });
    },
    toDetail(id) {
      uni.navigateTo({
        url: "/pages/consult/orderInfo?id=" + id,
      });
    }
  },
};
</script>
<style lang="scss">
page {
  background-color: rgba(248,248,248,1.000000);
  position: relative;
  //width: 100%;
  //height: 100%;
  display: flex;
  flex-direction: column;
  .page-content {
    background-color: rgba(248,248,248,1.000000);
    display: flex;
    flex-direction: column;
  }
  .nav-bar {
    height: 88upx;
  }
  .tabs {
    margin-top: 6upx;
    height: 88upx;
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    background-color: #FFFFFF;
    .tabs-list {
      height: 60upx;
      display: flex;
      justify-content: space-between;
      margin: 0upx 51upx;
      .tab {
        color: rgba(51,51,51,1);
        font-size: 32upx;
        &.selected {
          border-bottom: 8upx solid;
          font-weight: 600;
          color: #FF703F;
        }
      }
    }
  }
  .order-list {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin: 30upx 32upx 0 32upx;
  }
  .order-item {
    box-shadow: 0px 4px 28px 0px rgba(119,119,119,0.060000);
    background-color: rgba(255,255,255,1.000000);
    border-radius: 16upx;
    margin-bottom: 16upx;
    display: flex;
    flex-direction: column;
    justify-content: flex-center;
  }
  .item-header {
    position: relative;
    height: 33upx;
    margin-top: 24upx;
    flex-direction: row;
    display: flex;
  }
  .item-header-block {
    background-color: rgba(255,112,63,1.000000);
    border-radius: 3upx;
    width: 6upx;
    height: 32upx;
  }
  .item-header-timer {
    color: rgba(119,119,119,1);
    font-size: 24upx;
    margin-left: 26upx;
  }
  .item-header-countdown {
    display: flex;
    color: #FF703F;
    font-size: 24upx;
    margin-left: 106upx;
  }
  .item-header-status {
    position: absolute;
    right: 26upx;
    color: #FF703F;
    font-size: 24upx;
  }
  .item-info {
    position: relative;
    width: 622upx;
    height: 130upx;
    flex-direction: row;
    display: flex;
    margin: 24upx 0 0 32upx;
  }
  .item-info-avatar {
    width: 130upx;
    height: 130upx;
    border-radius: 100%;
  }
  .item-info-group {
    width: 201upx;
    height: 82upx;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin: 24upx 0 0 24upx;
  }
  .item-name {
    color: rgba(51,51,51,1);
    font-size: 26upx;
    font-weight: 600;
  }
  .item-desc {
    color: rgba(119,119,119,1);
    font-size: 26upx;
    margin-top: 8upx;
  }
  .item-price {
    position: absolute;
    right: 32upx;
    bottom: 32upx;
    font-size: 0upx;
    font-weight: 600;
    text-align: right;
  }
  .item-price-unit {
    color: rgba(255,63,100,1.000000);
    font-size: 22upx;
    font-weight: 600;
  }
  .item-price-num {
    color: rgba(255,63,100,1.000000);
    font-size: 32upx;
    font-weight: 600;
  }
  .item-info-box {
    height: 1upx;
    display: flex;
    flex-direction: row;
    margin: 24upx 0 0 32upx;
  }
  .box_2 {
    background-color: rgba(230,230,230,1.000000);
    width: 622upx;
    height: 1upx;
    display: flex;
    flex-direction: column;
  }
  .item-info-bottom {
    position: relative;
    height: 72upx;
    line-height: 72upx;
    //display: flex;
    //flex-direction: row;
    //text-align: right;
    //justify-content: flex-end;
    padding: 24upx;
    .bottom-title {
      font-size: 26rpx;
      color: #333333;
      position: absolute;
      left: 32upx;
    }
    .bottom-time {
      font-size: 26rpx;
      color: #FF703F;
      position: absolute;
      right: 32upx;
    }
    .button_1 {
      text-align: center;
      border-radius: 36upx;
      border: 1px solid #777777;
      width: 176upx;
      font-size: 28upx;
      //height: 72upx;
      //line-height: 72upx;
      position: absolute;
      right: 232upx;
      bottom: 24upx;
    }
    .button_2 {
      background-color: rgba(255,112,63,1.000000);
      border-radius: 36upx;
      //height: 72upx;
      //line-height: 72upx;
      font-weight: 600;
      color: rgba(255,255,255,1);
      font-size: 28upx;
      width: 176upx;
      position: absolute;
      right: 32upx;
      bottom: 24upx;
    }
  }
  .footer-main {
    //width: 236upx;
    //height: 28upx;
    //flex-direction: row;
    display: flex;
    justify-content: center;
    //margin: 40upx 0 41upx 257upx;
    padding: 40upx;
  }
  .box_7 {
    background-color: rgba(204,204,204,1.000000);
    width: 52upx;
    height: 1upx;
    margin-top: 14upx;
    display: flex;
    flex-direction: column;
  }
  .footer-text {
    width: 100upx;
    height: 28upx;
    overflow-wrap: break-word;
    color: rgba(119,119,119,1);
    font-size: 20upx;
    font-family: PingFangSC-Regular;
    font-weight: normal;
    text-align: left;
    white-space: nowrap;
    line-height: 28upx;
    margin-left: 16upx;
  }
  .box_8 {
    background-color: rgba(204,204,204,1.000000);
    width: 52upx;
    height: 1upx;
    display: flex;
    flex-direction: column;
    margin: 14upx 0 0 16upx;
  }
}
</style>
