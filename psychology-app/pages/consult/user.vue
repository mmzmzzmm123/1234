<template>
  <view class="user-page">
    <view class="user-info">
      <view class="info">
        <view class="header" @tap="getUserInfo"
        ><img
            class="img"
            :src="userInfo.avatar || '/static/evaluation/header.png'"
        />
        </view>
        <view class="txt">
          <view class="name"
          >{{ userInfo.name
            }}<img
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
        <uni-col :span="6" v-for="item in classList" class="item">
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
        <uni-col :span="6" v-for="item in serveList" class="item">
          <view @tap="toPage(item.id)">
            <img class="class-img" :src="item.classPic" />
            <view>{{ item.className }}</view>
          </view>
        </uni-col>
      </uni-row>
    </view>
    <consult-tab-bar :currentIndex="2"></consult-tab-bar>
    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="closeLoginConfirm" @confirm="confirmLogin"/>
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
          className: "待预约",
          id: 2,
        },
        {
          classPic: "/static/consult/my/dzx.png",
          className: "待咨询",
          id: 3,
        },
        {
          classPic: "/static/consult/my/finish.png",
          className: "已完成",
          id: 4,
        }
      ],
      serveList: [
        {
          classPic: "/static/consult/my/cp.png",
          className: "测评中心",
          id: 5,
        },
        {
          classPic: "/static/consult/my/kf.png",
          className: "客服帮助",
          id: 6,
        }
      ],
      clientTypeObj: clientTypeObj,
      redirectUri:location.href+"?t="+new Date().getTime()
    };
  },
  async mounted() {
    // this.userInfo = uni.getStorageSync("userInfo") ? JSON.parse(uni.getStorageSync("userInfo")) : {}
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback(this.redirectUri)) {
      this.userInfo = utils.getUserInfo()
    }
    if (!utils.checkLogin()) {
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
    toPage(id) {
      console.log(id)
      switch (id) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
          uni.navigateTo({ url: "/pages/consult/order?status=" + id });
          break
        case 5:
          uni.redirectTo({ url: "/pages/evaluation/index" })
          break
        case 6:
          console.log('客服帮助')
          break
      }

    },
    refreshUser(){
      uni.setStorageSync("userInfo", null);
      utils.loginWx(this.redirectUri);
      //添加登录标志,为callback做返回判断
      uni.setStorageSync("wxLogining", true);
    },
    toReport() {
      // 判断是否已经登录
      if (!utils.checkLogin()) {
        return this.openLoginConfirm()
      }
      if (this.getUserInfo())
        uni.navigateTo({ url: "/pages/evaluation/report" });
    },
    toOrder() {
      // 判断是否已经登录
      if (!utils.checkLogin()) {
        return this.openLoginConfirm()
      }
      if (this.getUserInfo())
        uni.navigateTo({ url: "/pages/evaluation/order" });
    },
    toTest(order) {
      // 判断是否已经登录
      if (!utils.checkLogin()) {
        return this.openLoginConfirm()
      }
      uni.setStorageSync("gaugeDes", order.gaugeDes);
      uni.navigateTo({
        url: `/pages/evaluation/testBefore?productId=${order.gaugeId}&&orderId=${order.id}`,
      });
    },
    copyOrderNo(orderNo) {
      var input = document.createElement("input");
      document.body.appendChild(input);
      input.setAttribute("value", orderNo);
      input.select();
      document.execCommand("copy"); // 执行浏览器复制命令
      if (document.execCommand("copy")) {
        document.execCommand("copy");
        uni.showToast({
          icon: "error",
          title: "内容复制成功",
        });
      }
      document.body.removeChild(input);
    },
    // 点击头像
    getUserInfo() {
      if (!utils.checkLogin()) {
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
    padding: 32upx 0;

    .class-img {
      width: 56upx;
      height: 56upx;
    }
  }

  .un-test-box {
    width: 690upx;
    margin: 24upx auto;
    background: #ffffff;
    border-radius: 16px;
    padding: 0 40upx 36upx;
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
}
</style>
