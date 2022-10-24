<template>
  <view class="user-page">
    <view class="user-info">
      <view class="info">
        <view class="header" @tap="getUserInfo"><img class="img" :src="userInfo.avatar||'/static/header.png'" /></view>
        <view class="txt">
          <view class="name">{{userInfo.name}}<img class="img" src="/static/user/refresh.png" v-show="userInfo.name" />
          </view>
          <view class="num"><img v-show="userInfo.phone" class="img" src="/static/user/phone.png" />{{userInfo.phone}}
          </view>
        </view>
      </view>
      <view class="link-box">
        <view class="item" @tap="loginWx">
          <view>我的报告</view>
          <view class="num">12</view>
        </view>
        <view class="item">
          <view>我的咨询</view>
          <view class="num">12</view>
        </view>
      </view>

    </view>
    <view class="class-box index-margin">
      <view class="item" v-for="item in classList">
        <img class="class-img" :src="item.classPic" />
        <view>{{ item.className }}</view>
      </view>
    </view>
    <view class="un-test-box">
      <view class="box-title">未完成的测评</view>
      <view class="order-item" v-for="order in orderList">
        <view class="title">{{order.title}}</view>
        <view class="price">￥{{order.price}}</view>
        <view class="buy-time">{{order.buyTime}}</view>
        <view class="order-no">{{order.orderNo}}<img class="img" src="/static/user/copy.png" /></view>
        <view class="btn">去测试</view>
      </view>
      <view class="footer" v-show="orderList.length>0">已经到底了</view>
    </view>
  </view>
</template>
<script>
import utils from '../../utils/common'
import { wxLoginCallBakc, wxLogin } from '../../server/wxApi'
export default {
  data() {
    return {
      userInfo: {},
      classList: [
        {
          classPic: "/static/user/order.png",
          className: "测评订单",
          id: 16,
        },
        {
          classPic: "/static/user/coupon.png",
          className: "优惠券",
          id: 36,
        },
        {
          classPic: "/static/user/customer-service.png",
          className: "客服帮助",
          id: 72,
        }
      ],
      orderList: [{
        title: '焦虑测试 (专业版)',
        price: '9.9',
        buyTime: '2022-08-09 12:24:12',
        orderNo: '218767823367836177313663',
        status: 1
      }, {
        title: '焦虑测试 (专业版)',
        price: '9.9',
        buyTime: '2022-08-09 12:24:12',
        orderNo: '218767823367836177313663',
        status: 2
      }, {
        title: '焦虑测试 (专业版)',
        price: '9.9',
        buyTime: '2022-08-09 12:24:12',
        orderNo: '218767823367836177313663',
        status: 1
      }, {
        title: '焦虑测试 (专业版)',
        price: '9.9',
        buyTime: '2022-08-09 12:24:12',
        orderNo: '218767823367836177313663',
        status: 1
      }, {
        title: '焦虑测试 (专业版)',
        price: '9.9',
        buyTime: '2022-08-09 12:24:12',
        orderNo: '218767823367836177313663',
        status: 1
      }, {
        title: '焦虑测试 (专业版)',
        price: '9.9',
        buyTime: '2022-08-09 12:24:12',
        orderNo: '218767823367836177313663',
        status: 1
      },],
    }
  },
  async mounted() {
    let code = utils.getParam(location.href, "code");
    if (code) {
      wxLoginCallBakc(code).then(res => {
        if (res.code == 200) {
          uni.setStorageSync("userInfo", { avatar: res.data.avatar, name: res.data.name, phone: res.data.phone });
          this.userInfo = res.data;
        }
      });
    }
  },
  methods: {
    loginWx() {
      wxLogin().then(res => {
        if (res.code == 200) {
          window.location.href = res.data;
        }
      });
    },
    // 点击授权按钮
    getUserInfo() {
      uni.navigateTo({
        url: "/pages/login/index",
      });
    },

  }
}
</script>
<style lang="scss">
@import "../../style/common.scss";

page {
  background-color: #f8f8f8;

  .user-info {
    width: 750upx;
    height: 386upx;
    background-color: #FFFFFF;
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
        border: 2upx solid #FFFFFF;
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

    .link-box {
      display: flex;
      flex-direction: row;

      .item {
        width: 336upx;
        height: 164upx;
        background-image: url('/static/user/consulting-service.png');
        background-size: 100% 100%;
        padding: 30upx 32upx;
        box-sizing: border-box;
        font-size: 32upx;
        font-weight: 500;
        line-height: 45upx;
        color: #40C2B7;

        &:first-child {
          margin-right: 26upx;
          background-image: url('/static/user/report.png');
          color: #E2724C;
        }

        .num {
          font-size: 30upx;
          font-weight: 400;
          line-height: 42upx;
        }
      }
    }
  }

  .class-box {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    text-align: center;
    font-size: 26upx;
    margin: 28upx auto 24upx;
    width: 690upx;
    background: #FFFFFF;
    border-radius: 16upx;
    padding: 32upx 0;

    .class-img {
      width: 88upx;
      height: 88upx;
    }
  }

  .un-test-box {
    width: 690upx;
    margin: 24upx auto;
    background: #FFFFFF;
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
        background: #FF703F;
        border-radius: 5upx;
        content: '';
        position: absolute;
        left: 0;
        top: 35upx;
      }
    }

    .order-item {
      border-bottom: 1px solid #ccc;
      margin: 30upx 0;

      .title {
        font-size: 32upx;
        font-weight: 600;
        color: #333333;
        line-height: 45upx;
        margin-bottom: 14upx;
      }

      .price {
        font-size: 32upx;
        font-weight: 600;
        color: #FF3F64;
        line-height: 45upx;
        margin-bottom: 16upx;
      }

      .buy-time,
      .order-no {
        font-size: 28upx;
        font-weight: 400;
        color: #AAAAAA;
        line-height: 40upx;
        margin-bottom: 12upx;
        align-items: center;
        display: flex;

        .img {
          width: 28upx;
          height: 28upx;
          border-radius: 0;
          margin-left: 12upx;
        }
      }

      .btn {
        width: 611upx;
        height: 88upx;
        text-align: center;
        line-height: 88upx;
        background: rgba(255, 112, 63, 0.08);
        border-radius: 44upx;
        font-size: 32upx;
        font-weight: 400;
        color: #FF703F;
        margin: 30upx auto 28upx;
      }
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
