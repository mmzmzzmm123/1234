<template>
  <view class="user-page">
    <view class="user-info">
      <view class="info">
        <view class="header" @tap="getUserInfo">
          <img class="img" :src="userInfo.avatar || '/static/evaluation/header.png'"/>
        </view>
        <view class="txt">
          <view class="name">{{ userInfo.name }}
            <img
                class="img"
                src="/static/consult/my/load.png"
                v-show="userInfo.name"
                @tap="refreshUser"
            />
          </view>
          <view class="num">
            <img
              class="img"
              src="/static/consult/my/phone.png"
            />{{ userInfo.phone }}
          </view>
        </view>
      </view>
    </view>
    <view class="un-test-box">
      <view class="box-title">
        我的咨询
        <view class="box-more" @tap="toPage(0)">查看全部 ></view>
      </view>
      <uni-row class="class-box">
        <uni-col :span="8" v-for="item in classList" class="item">
          <view @tap="toPage(item.id)">
            <img class="class-img" :src="item.classPic" />
            <view>{{ item.className }}</view>
          </view>
        </uni-col>
      </uni-row>
    </view>
    <view class="un-test-box">
      <view class="box-title">其他服务</view>
      <uni-row class="class-box">
        <uni-col :span="8" v-for="item in serveList" class="item">
          <view @tap="toPage(item.id)">
            <img class="class-img" :src="item.classPic" />
            <view>{{ item.className }}</view>
          </view>
        </uni-col>
      </uni-row>
    </view>
    <view class="un-test-box">
      <view class="box-title">申请合作</view>
      <uni-row class="class-box">
        <uni-col :span="8" v-for="item in apps" class="item">
          <view @tap="toApp(item)">
            <img class="class-img" :src="item.classPic" />
            <view>{{ item.className }}</view>
          </view>
        </uni-col>
      </uni-row>
    </view>

    <consult-tab-bar :currentIndex="1"></consult-tab-bar>
    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>

    <uni-popup ref="popupKf" type="center">
      <view class="box_9">
        <image src="/static/consult/order/card.png" class="box_10"></image>
        <view class="box_11">———— 长按图片保存图片 ————</view>
      </view>
    </uni-popup>
  </view>
</template>
<script>
import utils, { clientTypeObj } from "@/utils/common";
import noData from "@/components/evaluation/noData";
import {
  uniPopup,
  uniPopupDialog
} from '@dcloudio/uni-ui'
import loginServer from "@/server/login"
export default {
  components: { noData,  uniPopup, uniPopupDialog },
  data() {
    return {
      userInfo: {},
      classList: [
        {
          classPic: "/static/consult/my/pay.png",
          className: "待付款",
          id: 1,
        },
        {
          classPic: "/static/consult/my/yy.png",
          className: "进行中",
          id: 2,
        },
        {
          classPic: "/static/consult/my/finish.png",
          className: "已完成",
          id: 3,
        }
      ],
      serveList: [
        {
          classPic: "/static/consult/my/like.png",
          className: "我的关注",
          id: 10,
        },
        {
          classPic: "/static/consult/my/course.png",
          className: "我的课程",
          id: 11,
        },
        {
          classPic: "/static/consult/my/cp.png",
          className: "测评中心",
          id: 12,
        },
        {
          classPic: "/static/consult/my/integral.png",
          className: "我的积分",
          id: 13,
        },
        {
          classPic: "/static/consult/my/coupon.png",
          className: "我的卡券",
          id: 14,
        },
        {
          classPic: "/static/consult/my/kf.png",
          className: "客服帮助",
          id: 15,
        }
      ],
      apps: [
        {
          classPic: "/static/consult/my/app1.png",
          className: "成为经营者",
          url: '',
          id: 20,
        },
        {
          classPic: "/static/consult/my/app2.png",
          className: "成为咨询师",
          url: 'https://mp.weixin.qq.com/s/ti6O0zZ07iF7so6F4ubN3w',
          id: 21,
        },
        {
          classPic: "/static/consult/my/app3.png",
          className: "企业合作",
          url: '',
          id: 22,
        }
      ],
      clientTypeObj: clientTypeObj,
      redirectUri:location.href+"?t="+new Date().getTime()
    };
  },
  async mounted() {
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback()) {
      this.userInfo = utils.getUserInfo()
    }
    if (!await utils.checkLogin()) {
      return this.openLoginConfirm()
    }
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
    async toPage(id) {
      console.log(id)
      if (!await utils.checkLogin()) {
        return this.openLoginConfirm()
      }

      switch (id) {
        case 0:
        case 1:
        case 2:
        case 3:
          uni.navigateTo({ url: "/pages/consult/order?status=" + id });
          break
        case 10:
          uni.navigateTo({ url: "/pages/consult/likes" })
          break
        case 11:
          uni.navigateTo({ url: "/pages/course/index" })
          break
        case 12:
          uni.navigateTo({ url: "/pages/evaluation/index" })
          break
        case 13:
          uni.navigateTo({ url: "/pages/consult/integral" })
          break
        case 14:
          // uni.redirectTo({ url: "/pages/evaluation/index" })
          uni.showToast({
            icon: "none",
            title: "功能开发中",
          });
          break
        case 15:
          console.log('客服帮助')
          this.$refs.popupKf.open()
          break
      }

    },
    toApp(item) {
      if (item.url) {
        return window.location.href = item.url
      }
      uni.showToast({
        icon: "none",
        title: "功能开发中",
      });
    },
    refreshUser(){
      uni.setStorageSync("userInfo", null);
      utils.loginWx(this.redirectUri);
      //添加登录标志,为callback做返回判断
      uni.setStorageSync("wxLogining", true);
    },
    // 点击头像
    async getUserInfo() {
      if (!await utils.checkLogin()) {
        utils.loginWx(this.redirectUri);
        return false;
      }
      return true;
    }
  },
};
</script>
<style lang="scss">
@import "@/style/common.scss";

page {
  background-color: #f8f8f8;
  padding-bottom: calc(constant(safe-area-inset-bottom) + 50px); /* 兼容 iOS 设备 */
  padding-bottom: calc(env(safe-area-inset-bottom) + 50px); /* 兼容 iPhone X 及以上设备 */

  .user-info {
    width: 750upx;
    height: 236upx;
    background-color: #ffffff;
    padding: 42upx 30upx;
    box-sizing: border-box;

    .info {
      display: flex;
      flex-direction: row;
      margin-bottom: 48upx;

      .header {
        width: 106upx;
        height: 106upx;
        box-shadow: 0 8upx 32upx 0 rgba(188, 167, 167, 0.4);
        border: 2upx solid #ffffff;
        border-radius: 100%;
        margin-right: 24upx;

        .img {
          border-radius: 100%;
        }
      }

      .txt {
        flex: 1;

        .name {
          font-size: 32upx;
          font-weight: 600;
          color: #333333;
          line-height: 45upx;
          display: flex;
          flex-direction: row;
          align-items: center;
          margin: 8upx 0 12upx;

          .img {
            width: 32upx;
            height: 27upx;
            margin-left: 16upx;
          }
        }

        .num {
          font-size: 28upx;
          font-weight: 400;
          color: #777777;
          line-height: 40upx;
          display: flex;
          flex-direction: row;
          align-items: center;

          .img {
            width: 40upx;
            height: 40upx;
            margin-right: 12upx;
          }
        }
      }
    }
  }

  .class-box {
    text-align: center;
    font-size: 26upx;
    padding-bottom: 32upx;

    .class-img {
      width: 56upx;
      height: 56upx;
    }
    .item {
      margin-top: 32upx;
    }
  }

  .un-test-box {
    width: 690upx;
    margin: 24upx auto;
    background: #ffffff;
    border-radius: 16px;
    padding: 0 36upx;
    box-sizing: border-box;

    .box-title {
      line-height: 102upx;
      font-size: 30upx;
      font-weight: 600;
      color: #333333;
      border-bottom: 1px solid rgba(204, 204, 204, 0.6);
      position: relative;
      padding-left: 22upx;

      &::before {
        width: 10upx;
        height: 32upx;
        background: #ff703f;
        border-radius: 5upx;
        content: "";
        position: absolute;
        left: 0;
        top: 35upx;
      }
    }

    .box-more {
      position: absolute;
      right: 10upx;
      top: 0upx;
      color: #777777;
      font-size: 24upx;
      text-align: left;
    }
  }

  .footer {
    &::before,
    &::after {
      left: 177upx;
    }

    &::after {
      left: unset;
      right: 177upx;
    }
  }
  .box_9 {
    text-align: center;
    .box_10 {
      width: 200px;
      height: 200px;
    }
    .box_11 {
      margin-top: 20upx;
      font-size: 28rpx;
      color: #FFFFFF;
    }
  }
}
</style>
