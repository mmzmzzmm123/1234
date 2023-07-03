<template>
  <view class="page">
    <view class="box-bg">
      <uni-nav-bar left-icon="closeempty" right-icon="more-filled" :border="false" title="确认订单" @clickLeft="back"/>
    </view>
    <view class="consult-info">
      <text class="consult-info-title">咨询</text>
      <view class="consult-info-group">
        <image :src="consult.avatar" class="consult-info-avatar"></image>
        <view class="consult-info-right">
          <text class="consult-name">{{ consult.userName }}</text>
          <text class="consult-work">{{ consult.workNum }}人次咨询 | {{ consult.workHours }}年从业经验</text>
          <view class="consult-price">
            <text class="consult-price-ant">¥</text>
            <text class="consult-price-num">{{ serve.price }}</text>
            <text class="consult-price-ext">/1次</text>
          </view>
        </view>
      </view>
      <view class="info-text-wrapper-2">
        <text class="info-text-1">单次咨询时长</text>
        <text class="info-text-2">50分钟</text>
      </view>
      <view class="info-text-wrapper-group2"></view>
      <view class="info-text-wrapper-3">
        <text class="info-text-3">咨询次数</text>
        <text class="info-text-4">{{ consult.workNum }}</text>
      </view>
    </view>
    <view class="section-time">
      <view class="section-time-group">
        <text class="section-time-group-text1">预约</text>
        <view class="section-time-group2">
          <text class="section-time-group-text2">预约时间</text>
          <view class="image-text_1">
            <text class="section-time-group-text3" @tap="open">{{ workName !== '' ? workName : '点击选择预约时间' }} ></text>
          </view>
        </view>
        <view class="group_5"></view>
        <view class="section-time-group3">
          <text class="section-time-text4">咨询方式</text>
          <text class="section-time-text5">{{ serve.name }}</text>
        </view>
      </view>
      <view class="section-reader">
        <checkbox-group @change="checkboxChange">
          <checkbox class="section-reader-box" value="ok" :checked="checkBox"/>
        </checkbox-group>
        <view class="section-reader-group">
          <text class="section-reader-group-text1">我同意</text>
          <text class="section-reader-group-text2">《心理咨询服务协议》</text>
          <text class="section-reader-group-text3" />
        </view>
      </view>
    </view>
    <view class="bottom-view">
      <button @tap="toBuy" class="bottom-btn" :disabled="flag">
        <text class="bottom-text">{{ type === '1' ? '预约' : '立即支付' }}</text>
        <view class="bottom-text-wrapper" v-if="type !== '1'">
          <text class="bottom-text-wrapper-1">¥</text>
          <text class="bottom-text-wrapper-2">{{ serve.price }}</text>
        </view>
      </button>
    </view>
    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>

    <cart-box ref="cartBox" :workList="workList" :workId="workId" @selectDay="selectDay" @doOk="doOk" @cancel="cancel"/>

  </view>
</template>

<script>

import utils from "@/utils/common";
import indexServer from '@/server/consult/index'
import orderServer from "@/server/consult/order";
import loginServer from '@/server/login'
import cartBox from '@/components/consult/cartBox'
import wxJS from "@/server/wxJS.js"
import { getPaySign, wxPay } from "@/server/wxApi"
export default {
  components: { cartBox },
  data() {
    return {
      workId: 0,
      workName: '',
      flag: false,
      checkBox: false,
      userInfo: {},
      tabs: [],
      works: [],
      dateList: [],
      workList: [],
      serve: {},
      consult: {},
      type: '0',// 0,支付 1,预约
      serveId: 0,
      orderId: 0,
      redirectUri: location.href,
      currentCatalogue: {},
    };
  },
  created() {
    this.serveId = utils.getParam(location.href, "id")
    this.orderId = utils.getParam(location.href, "orderId") ? utils.getParam(location.href, "orderId") : 0
    this.type = utils.getParam(location.href, "type") ? utils.getParam(location.href, "type") : 0
    console.log(this.serveId)
    console.log(this.orderId)
    console.log(this.type)
  },
  async mounted() {
    this.userInfo = uni.getStorageSync("userInfo") ? JSON.parse(uni.getStorageSync("userInfo")) : undefined;
    if (!this.userInfo) {
      return this.openLoginConfirm()
    }
    await this.getDates()
    await this.getConsultInfoByServe()
    this.getWorkList()
  },
  methods: {
    back() {
      if (this.orderId > 0) {
        uni.navigateTo({
          url: "/pages/consult/order?status=" + 0,
        });
      } else {
        uni.navigateTo({
          url: "/pages/consult/index",
        });
      }
    },
    async getDates() {
      this.dateList = await indexServer.getDates(7);
    },
    async getConsultInfoByServe() {
      const res = await orderServer.getConsultInfoByServe(this.serveId)
      this.consult = res.consult
      this.serve = res.serve
      this.works = res.works
    },
    getWorkList() {
      const arr = []
      this.dateList.forEach(a => {
        const item = {
          week: a.week,
          day:  a.date.substr(5, 10),
          flag: false,
          child: []
        }
        this.works.filter(b => a.date === b.day).forEach(c => {
          item.child.push({
            flag: false,
            id: c.id,
            s: c.timeStart.substr(11, 5),
            e: c.timeEnd.substr(11, 5)
          })
        })
        arr.push(item)
      })
      console.log(arr)
      this.workList = arr
    },
    // async getConsultServe() {
    //   this.serveList = await orderServer.getConsultServeById(this.serveId)
    // },
    confirmServe() {
      this.close()
    },
    open() {
      this.$refs.cartBox.open()
    },
    selectDay(id, day) {
      // 多选时候处理
      // const item = this.workList.find(a => a.day === day)
      // const cl = item.child.find(a => a.id === id)
      // cl.flag = !cl.flag
      // item.flag = item.child.filter(a => a.flag).length > 0

      this.workList.forEach(a => a.flag = false)

      if (this.workId === id) {
        this.workId = 0
      } else {
        this.workId = id
        this.workList.find(a => a.day === day).flag = true
      }
    },
    doOk() {
      const item = this.works.find(a => a.id === this.workId)
      this.workName = item.timeStart.substr(0, 16) + '-' + item.timeEnd.substr(11, 5)
    },
    cancel() {
      this.workId = 0
      this.workList.forEach(a => a.flag = false)
      this.workName = ''
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
    goHome() {
      uni.redirectTo({
        url: "/pages/consult/index",
      })
    },
    checkboxChange(e) {
      console.log(e.detail.value)
      this.checkBox = e.detail.value[0]
    },
    async doConsult() {
      const res = await orderServer.doConsult(this.orderId, this.workId)
      console.log(res)
      if (res === 1) {
        uni.showToast({
          icon: "success",
          title: "预约成功",
        });
        uni.navigateTo({
          url: "/pages/consult/order",
        });
      }
    },
    async toBuy() {
      if (!this.checkBox) {
        return uni.showToast({
          icon: 'none',
          title: '请勾选我同意后进行下一步操作',
          duration: 2000
        })
      }

      if (!this.userInfo) {
        return this.openLoginConfirm()
      }

      // serverId,workId,consultId
      console.log(this.consult.id)
      console.log(this.serve.id)
      console.log(this.workId)

      if (this.type === '1' && this.orderId !== '') {
        if (this.workId === 0) {
          return uni.showToast({
            icon: 'none',
            title: '请选择时间进行预约',
            duration: 2000
          })
        }
        await this.doConsult(this.orderId, this.workId)
        return true
      }

      let res = await getPaySign(
          this.userInfo.userId,
          this.serve.id,
          this.serve.price,
          {
            module: 'consult',
            workId: this.workId,
            orderId: this.orderId
          }
      )

      console.log(res)
      if (res.code == 200) {
        const { appId, timeStamp, nonceStr, packageInfo, paySign, signType } = res.data
        wxPay(res.data, () => {
          uni.showToast({
            icon: "success",
            title: "支付成功",
          });
          uni.navigateTo({
            // url: "/pages/course/courseDetail?id=" + this.courseInfo.id,
            url: "/pages/consult/order",
          });
        })
      }
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
  }
}
</script>

<style lang="scss" scoped>
.page {
  background-color: rgba(248,248,248,1.000000);
  position: relative;
  width: 100%;
  height: 1624upx;
  overflow: hidden;
  display: flex;
  flex-direction: column;

  .box-bg {
    background-color: #F5F5F5;
  }

  .consult-info {
    box-shadow: 0px 4px 28px 0px rgba(119,119,119,0.060000);
    background-color: #FFFFFF;
    border-radius: 12upx;
    width: 686upx;
    height: 416upx;
    display: flex;
    flex-direction: column;
    justify-content: flex-center;
    margin: 34upx 0 0 32upx;
  }
  .consult-info-title {
    width: 64upx;
    height: 45upx;
    overflow-wrap: break-word;
    color: rgba(51,51,51,1);
    font-size: 32upx;
    font-family: PingFangSC-Medium;
    font-weight: 500;
    text-align: center;
    white-space: nowrap;
    line-height: 45upx;
    margin: 24upx 0 0 32upx;
  }
  .consult-info-group {
    width: 494upx;
    height: 136upx;
    flex-direction: row;
    display: flex;
    justify-content: space-between;
    margin: 32upx 0 0 32upx;
  }
  .consult-info-avatar {
    border-radius: 100%;
    width: 130upx;
    height: 130upx;
  }
  .consult-info-right {
    width: 340upx;
    height: 136upx;
    display: flex;
    flex-direction: column;
  }
  .consult-name {
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
  }
  .consult-work {
    width: 340upx;
    height: 37upx;
    overflow-wrap: break-word;
    color: rgba(119,119,119,1);
    font-size: 26upx;

    font-weight: normal;
    text-align: left;
    white-space: nowrap;
    line-height: 37upx;
    margin-top: 4upx;
  }
  .consult-price {
    width: 160upx;
    height: 45upx;
    overflow-wrap: break-word;
    font-size: 0upx;
    font-weight: 600;
    text-align: left;
    line-height: 30upx;
    margin-top: 8upx;
  }
  .consult-price-ant {
    color: rgba(255,63,100,1.000000);
    font-size: 22upx;
  }
  .consult-price-num {
    color: rgba(255,63,100,1.000000);
    font-size: 32upx;
  }
  .consult-price-ext {
    color: rgba(119,119,119,1.000000);
    font-size: 22upx;
    line-height: 30upx;
  }
  .info-text-wrapper-2 {
    width: 622upx;
    height: 37upx;
    flex-direction: row;
    display: flex;
    justify-content: space-between;
    margin: 32upx 0 0 32upx;
  }
  .info-text-1 {
    width: 156upx;
    height: 37upx;
    overflow-wrap: break-word;
    color: rgba(119,119,119,1);
    font-size: 26upx;

    font-weight: normal;
    text-align: left;
    white-space: nowrap;
    line-height: 37upx;
  }
  .info-text-2 {
    width: 84upx;
    height: 37upx;
    overflow-wrap: break-word;
    color: rgba(51,51,51,1);
    font-size: 26upx;

    font-weight: normal;
    text-align: right;
    white-space: nowrap;
    line-height: 37upx;
  }
  .info-text-wrapper-group2 {
    background-color: rgba(230,230,230,1.000000);
    width: 622upx;
    height: 1upx;
    display: flex;
    flex-direction: column;
    margin: 24upx 0 0 32upx;
  }
  .info-text-wrapper-3 {
    width: 622upx;
    height: 37upx;
    flex-direction: row;
    display: flex;
    justify-content: space-between;
    margin: 24upx 0 24upx 32upx;
  }
  .info-text-3 {
    color: rgba(119,119,119,1);
    font-size: 26upx;
  }
  .info-text-4 {
    color: rgba(51,51,51,1);
    font-size: 26upx;
  }
  .section-time {
    width: 100%;
    height: 833upx;
    display: flex;
    flex-direction: column;
  }
  .section-time-group {
    box-shadow: 0px 4px 28px 0px rgba(119,119,119,0.060000);
    background-color: #FFFFFF;
    border-radius: 12upx;
    width: 686upx;
    height: 248upx;
    display: flex;
    flex-direction: column;
    justify-content: flex-center;
    margin: 16upx 0 0 32upx;
  }
  .section-time-group-text1 {
    width: 64upx;
    height: 45upx;
    overflow-wrap: break-word;
    color: rgba(51,51,51,1);
    font-size: 32upx;
    font-family: PingFangSC-Medium;
    font-weight: 500;
    text-align: center;
    white-space: nowrap;
    line-height: 45upx;
    margin: 24upx 0 0 32upx;
  }
  .section-time-group2 {
    width: 622upx;
    height: 37upx;
    flex-direction: row;
    display: flex;
    justify-content: space-between;
    margin: 32upx 0 0 32upx;
  }
  .section-time-group-text2 {
    width: 104upx;
    height: 37upx;
    overflow-wrap: break-word;
    color: rgba(119,119,119,1);
    font-size: 26upx;

    font-weight: normal;
    text-align: left;
    white-space: nowrap;
    line-height: 37upx;
  }
  .image-text_1 {
    flex-direction: row;
    display: flex;
    justify-content: space-between;
  }
  .section-time-group-text3 {
    color: rgba(51,51,51,1);
    font-size: 26upx;
    text-align: right;
    line-height: 37upx;
  }
  .group_5 {
    background-color: rgba(230,230,230,1.000000);
    width: 622upx;
    height: 1upx;
    display: flex;
    flex-direction: column;
    margin: 24upx 0 0 32upx;
  }
  .section-time-group3 {
    width: 622upx;
    height: 37upx;
    flex-direction: row;
    display: flex;
    justify-content: space-between;
    margin: 24upx 0 24upx 32upx;
  }
  .section-time-text4 {
    height: 37upx;
    color: rgba(119,119,119,1);
    font-size: 26upx;
    text-align: left;
    line-height: 37upx;
  }
  .section-time-text5 {
    height: 37upx;
    color: rgba(51,51,51,1);
    font-size: 26upx;
    text-align: right;
    line-height: 37upx;
  }
  .section-reader {
    width: 368upx;
    height: 44upx;
    flex-direction: row;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin: 32upx 0 493upx 32upx;
  }
  .section-reader-box {
    transform:scale(0.7);
    //width: 32upx;
    //height: 32upx;
    //margin-top: 6upx;
  }
  .section-reader-group {
    width: 320upx;
    height: 44upx;
    font-size: 0upx;
    text-align: justify;
    line-height: 44upx;
  }
  .section-reader-group-text1 {
    width: 320upx;
    height: 44upx;
    color: rgba(51,51,51,1.000000);
    font-size: 24upx;
    line-height: 44upx;
  }
  .section-reader-group-text2 {
    width: 320upx;
    height: 44upx;
    color: rgba(31,92,228,1.000000);
    font-size: 24upx;
    line-height: 44upx;
  }
  .section-reader-group-text3 {
    width: 320upx;
    height: 44upx;
    color: rgba(51,51,51,1.000000);
    font-size: 24upx;
    line-height: 44upx;
  }
  .bottom-view {
    padding: 10upx;
    position: fixed;
    bottom: 0;
    height: 98upx;
    width: 100%;
    background: #fff;
  }
  .bottom-btn {
    background-color: #ff703f;
    border-radius: 20px;
    height: 41px;
    line-height: 41px;
    width: 310px;
    display: flex;
    justify-content: center;
    margin: 4px 0 0 39px;
  }
  .bottom-text {
    color: rgba(255,255,255,1);
    font-size: 28upx;
    font-weight: 600;
  }
  .bottom-text-wrapper {
    margin-left: 10upx;
    color: #FFFFFF;
  }
  .bottom-text-wrapper-1 {
    font-size: 22upx;
  }
  .bottom-text-wrapper-2 {
    font-size: 32upx;
  }
  // sss
}
</style>