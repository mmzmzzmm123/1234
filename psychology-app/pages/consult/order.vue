<template>
  <view class="page">
    <view class="page-content">
      <view class="nav-bar" v-if="false">
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
            <view class="item-header-countdown" v-if="item.status === '0' && item.endPay > 0">
              <view>剩余</view>
              <uni-countdown color="#FF703F" splitorColor="#FF703F" :show-day="false" :minute="item.endPay" :second="0"/>
<!--            <view class="item-header-countdown" v-if="item.status === 0">{{ item.endPay }}-->
            </view>
            <text class="item-header-status">{{ item.statusName }}</text>
          </view>
          <view class="item-info">
            <image :src="item.avatar" class="item-info-avatar"></image>
            <view class="item-info-group" @tap="toDetail(item.id)">
              <text class="item-name">{{ item.consultName }}</text>
              <text class="item-desc">{{ item.serveName }}</text>
              <text class="item-desc">有效期至：{{ item.end ? item.end : '永久有效'}}</text>
            </view>
            <view class="item-price">
              <text class="item-price-unit">¥</text>
              <text class="item-price-num">{{ item.amount}}/</text>
              <text class="item-price-time">{{ item.serveNum }}次</text>
            </view>
          </view>
          <view class="item-info-box">
            <view class="box_2"></view>
          </view>
          <view class="item-info-bottom">
            <view class="bottom-info">
              <view class="bottom-title" v-if="item.status === '1' || item.status === '2'">剩余咨询次数：<text class="bottom-title-val">{{ item.num + ' 次' }}</text></view>
              <view class="bottom-title" v-if="item.status === '1' && item.orderTime">下次咨询时间：<text class="bottom-title-val">{{ item.orderTime }}</text></view>
            </view>
            <button @tap="toCancel(item.id)" class="button_1" v-if="item.status === '0'">
              取消
            </button>
            <button @tap="open(item)" class="button_2" v-if="item.status === '0' || (item.status === '1' && item.num > 0 && !item.orderTime)">
              <text class="text_16">{{ item.status === '0' ? '支付' : '去预约'}}</text>
            </button>
          </view>
        </view>
      </view>
      <view class="footer-main">
        <view class="box_7"></view>
        <text class="footer-text">已经到底了</text>
        <view class="box_8"></view>
      </view>
    </view>

    <cart-box ref="cartBox" :dateList="dateList" :works="works" @doOk="doOk" @cancel="cancel"/>

    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>
  </view>
</template>
<script>
import utils, { clientTypeObj } from "@/utils/common";
import indexServer from '@/server/consult/index'
import orderServer from "@/server/consult/order";
import loginServer from "@/server/login"
import formatTime from '@/utils/formatTime.js'
import cartBox from '@/components/consult/cartBox'
import { getPaySign, wxPay } from "@/server/wxApi"

export default {
  components: { cartBox },
  data() {
    return {
      status: 0,
      userInfo: {},
      orderList: [],
      // car
      time: -1,
      workId: 0,
      workName: '',
      order: {},
      works: [],
      dateList: [],
      // car end
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
          name: "进行中",
          id: 2,
        },
        {
          name: "已完成",
          id: 3,
        }
      ],
      clientTypeObj: clientTypeObj,
    };
  },
  async created() {
    this.status = parseInt(utils.getParam(location.href, "status"))
  },
  async mounted() {
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback()) {
      this.userInfo = utils.getUserInfo()
    }
    if (!await utils.checkLogin()) {
      return this.openLoginConfirm()
    }
    await this.getDates()
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
    async getDates() {
      this.dateList = await indexServer.getDates(7);
    },
    // cartBox start
    async getConsultInfoByServe() {
      const res = await orderServer.getConsultInfoByServe(this.order.consultId, this.order.serveId)
      this.consult = res.consult
      this.works = res.works
    },
    async open(order) {
      this.order = order
      if (order.payStatus === '2' && order.end && new Date(order.end) < new Date()) {
        return uni.showToast({
          icon: "none",
          title: '订单已超时',
        });
      }

      await this.getConsultInfoByServe()
      this.$refs.cartBox.open()
    },
    cancel() {
      this.workId = 0
    },
    // cartBox end
    async doOk(workId, time, workName) {
      if (!await utils.checkLogin()) {
        return this.openLoginConfirm()
      }

      console.log(workName)
      this.time = time
      this.workId = workId
      this.workName = workName
      if (this.order.status === '1') { // 去预约
        if (workId === 0) {
          return uni.showToast({
            icon: "none",
            title: "请选择时间进行预约",
          })
        } else {
          // 去预约
          await this.doConsult()
        }
      }

      if (this.order.status === '0') {
        // 支付
        await this.doPay()
      }

      await this.getOrderList()
      this.$refs.cartBox.close()
    },
    async doConsult() {
      const res = await orderServer.doConsult(this.order.id, this.workId, this.time)
      console.log(res)
      if (res === 1) {
        uni.showToast({
          icon: "success",
          title: "预约成功",
        });
      }
    },
    async doPay() {
      console.log(this.order)
      uni.showLoading({
        title: '支付中...'
      });

      let res = await getPaySign(
          this.userInfo.userId,
          this.order.serveId,
          this.order.price,
          {
            module: 'consult',
            workId: this.workId,
            time: this.time,
            consultId: this.order.consultId,
            orderId: this.order.id
          }
      )

      console.log(res)
      uni.hideLoading()
      if (res.code == 200) {
        const { appId, timeStamp, nonceStr, packageInfo, paySign, signType, out_trade_no } = res.data
        wxPay(res.data, () => {
          uni.showToast({
            icon: "success",
            title: "支付成功",
          });

          uni.navigateTo({
            url: "/pages/consult/payResultOk?orderNo=" + out_trade_no,
          });
        }, (msg) => {
          console.log(msg)
          uni.showToast({
            icon: "error",
            title: "支付失败",
          });

          uni.navigateTo({
            url: "/pages/consult/payResultFail"
          });
        })
      } else {
        uni.showToast({
          icon: "none",
          title: res.msg,
        });

        uni.navigateTo({
          url: "/pages/consult/payResultFail"
        });
      }
    },
    // car end
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
      const list = await orderServer.getOrderList(data);
      if (list && list.length > 0) {
        list.forEach(i => {
          i.endPay = 0
          if (i.status === '0') {
            i.endPay = this.remainTime(i.updateTime)
          }
        })
      }
      console.log(list)
      this.orderList = list
    },
    remainTime(orderTime) {
      if (new Date().getTime() < new Date(orderTime).getTime() + 15 * 60 * 1000) {
        // 下单不超过15分钟
        return formatTime.getMinutes(orderTime)
      } else {
        return 0
      }
    },
    async toCancel(id) {
      const res = await orderServer.cancel(id);
      if (res === 1) {
        uni.showToast({
          icon: "success",
          title: "操作成功",
        });
      }
      await this.getOrderList()
    },
    async toDetail(id) {
      if (!await utils.checkLogin()) {
        return this.openLoginConfirm()
      }

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
    position: fixed;
    top: 0;
    z-index: 99;
    //margin-top: 6upx;
    width: 100%;
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
    margin: 118upx 32upx 0 32upx;
  }
  .order-item {
    box-shadow: 0px 4px 28px 0px rgba(119,119,119,0.060000);
    background-color: rgba(255,255,255,1.000000);
    border-radius: 16upx;
    margin-bottom: 16upx;
    //display: flex;
    //flex-direction: column;
    //justify-content: flex-center;
  }
  .item-header {
    position: relative;
    height: 33upx;
    margin-top: 24upx;
    flex-direction: row;
    align-items: center;
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
    align-items: center;
    position: absolute;
    right: 126upx;
    color: #FF703F;
    font-size: 24upx;
  }
  .item-header-status {
    position: absolute;
    right: 26upx;
    color: #FF703F;
    font-size: 24upx;
  }
  .item-info {
    position: relative;
    //width: 622upx;
    height: 130upx;
    flex-direction: row;
    display: flex;
    margin: 24upx 0 0 32upx;
  }
  .item-info-avatar {
    width: 130upx;
    height: 130upx;
    border-radius: 100%;
    background-color: #EFEFF9;
  }
  .item-info-group {
    //width: 201upx;
    height: 130upx;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin-left: 24upx;
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
    top: 22upx;
    font-size: 22upx;
    text-align: right;
    color: #777777;
    .item-price-unit {
      color: rgba(255,63,100,1.000000);
    }
    .item-price-num {
      color: rgba(255,63,100,1.000000);
      font-size: 32upx;
      font-weight: 600;
    }
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
    height: 120upx;
    display: flex;
    align-items: center;
    position: relative;
    .bottom-info {
      margin-left: 32upx;
      font-size: 24upx;
      .bottom-title {
        color: #333333;
      }
      .bottom-title-val {
        color: #FF703F;
      }
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
