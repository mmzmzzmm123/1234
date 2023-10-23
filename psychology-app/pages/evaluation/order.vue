<template>
  <view class="order-page">
    <view class="tab">
      <span class="tab-item" v-for="tab in tabList" :class="{ 'active': tab.status == currentStatus }"
        @tap="changeTab(tab.status)">{{ tab.title }}</span>
    </view>
    <view class="order-item" v-for="order in orderList">
      <view class="title">{{ order.gaugeTitle }}</view>
      <view class="price"><span class="icon">￥</span>{{ order.amount }}</view>
      <view class="buy-time">购买时间：{{ order.createTime }}</view>
      <view class="order-no">订单编号：{{ order.orderId }}
        <img class="img" src="/static/evaluation/user/copy.png" @tap="copyOrderNo(order.orderId)" />
      </view>
      <view class="btn" v-show="order.gaugeStatus == 2" @tap="toTest(order)">去测试</view>
      <view class="btn" v-show="order.gaugeStatus == 1" @tap="toResult(order)">查看报告</view>
    </view>
    <no-data v-if="orderList.length == 0"></no-data>
    <view class="footer" v-else>已经到底了</view>
    <evaluation-tab-bar :currentIndex="2"></evaluation-tab-bar>
  </view>
</template>
<script>
  import utils from "@/utils/common";
  import noData from '@/components/evaluation/noData'
  import userServer from '@/server/evaluation/user'
  import questionServer from '@/server/evaluation/question'
  export default {
    components: {
      noData
    },
    data() {
      return {
        userInfo: {},
        currentStatus: '',
        tabList: [{
          title: '全部',
          status: ''
        }, {
          title: '未完成',
          status: 2
        }, {
          title: '已完成',
          status: 1
        }],
        orderList: []
      }
    },
    async created() {
      this.userInfo = utils.getUserInfo()
      this.orderList = await userServer.getOrderList({
        userId: this.userInfo.userId,
        gaugeStatus: ""
      });
    },
    methods: {
      toTest(order) {
        uni.navigateTo({
          url: `/pages/evaluation/question?productId=${order.gaugeId}&orderId=${order.id}`,
        });
      },
      async changeTab(status) {
        this.currentStatus = status;
        this.orderList = await userServer.getOrderList({
          userId: this.userInfo.userId,
          gaugeStatus: status
        });
      },
      toResult(order) {
        uni.navigateTo({
          url: "/pages/evaluation/mResult?orderId=" + order.orderId,
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
    }
  }
</script>
<style lang="scss">
  @import "@/style/common.scss";

  page {
    background-color: #f8f8f8;

    .tab {
      height: 88upx;
      line-height: 88upx;
      display: flex;
      flex-direction: row;
      font-size: 32upx;
      font-weight: 400;
      color: #333333;

      .tab-item {
        flex: 1;
        text-align: center;
        position: relative;

        &.active {
          color: #FF703F;

          &::after {
            width: 156upx;
            height: 4upx;
            background: #FF703F;
            border-radius: 3upx;
            content: '';
            position: absolute;
            left: calc(50% - 78upx);
            bottom: 0;

          }
        }
      }
    }

    .order-item {
      width: 686upx;
      height: 364upx;
      background: #FFFFFF;
      box-shadow: 0 4upx 28upx 0 rgba(119, 119, 119, 0.06);
      border-radius: 16upx;
      margin: 24upx 32upx 0;
      padding: 32upx;
      box-sizing: border-box;
      position: relative;

      &::before {
        content: '';
        position: absolute;
        left: 0;
        top: 40upx;
        width: 6upx;
        height: 32upx;
        background: #FF703F;
        border-radius: 3upx;
      }

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
        font-size: 24upx;
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
        margin-top: 12upx;
        line-height: 96upx;
        font-size: 32upx;
        font-weight: 400;
        color: #FF703F;
        text-align: center;
        border-top: 1px solid rgba(204, 204, 204, 0.5);

      }
    }

  }
</style>
