<template>
  <view class="order-page">
    <view class="tab">
      <span
        class="tab-item"
        v-for="tab in tabList"
        :class="{ active: tab.status == currentStatus }"
        @tap="changeTab(tab.status)"
        >{{ tab.title }}</span
      >
    </view>
    <view class="order-item" v-for="order in orderList">
      <view class="title">{{ order.gaugeTitle }}</view>
      <view class="price">{{ order.amount }}</view>
      <view class="buy-time">{{ order.createTime }}</view>
      <view class="order-no">{{ order.orderId }}</view>
      <view
        class="btn"
        v-show="order.status == 1"
        @tap="toLearningCourse(order)"
        >去学习</view
      >
      <view class="btn cancel" v-show="order.status != 1" @tap="toPay(order)"
        >取消</view
      >
      <view class="btn" v-show="order.status != 1" @tap="toCancel(order)"
        >支付</view
      >
    </view>
    <no-data v-if="orderList.length == 0"></no-data>
    <view class="footer" v-else>已经到底了</view>
    <curriculum-tab-bar :currentIndex="2"></curriculum-tab-bar>
  </view>
</template>
<script>
import noData from "@/components/curriculum/noData";
import userServer from "@/server/curriculum/user";
import courseServer from "@/server/curriculum/course";
export default {
  components: { noData },
  data() {
    return {
      currentStatus: "",
      tabList: [
        { title: "全部", status: "" },
        { title: "未完成", status: 2 },
        { title: "已完成", status: 1 },
      ],
      orderList: [],
    };
  },
  async created() {
    this.orderList = await userServer.getOrderList();
  },
  methods: {
    toPay() {},
    async toCancel(order) {
      let result = await userServer.changeOrderStatus(order.id, 0);
      if (result.code == 200) {
        order.status = 0;
      } else {
        uni.showToast({
          icon: "error",
          title: "取消失败",
        });
      }
    },
    toLearningCourse(order) {
      uni.navigateTo({
        url: "/pages/curriculum/learningCourse?productId=" + order.productId,
      });
    },
    async changeTab(status) {
      this.currentStatus = status;
      this.orderList = await userServer.getOrderList(status);
    },
    async toResult(order) {
      let result = await courseServer.setResult(order.orderId);
      if (result.code == 200) {
        uni.setStorageSync("result", result.data);
        uni.navigateTo({
          url: "/pages/curriculum/result/index?productId=" + order.gaugeId,
        });
      }
    },
  },
};
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
        color: #ff703f;

        &::after {
          width: 156upx;
          height: 4upx;
          background: #ff703f;
          border-radius: 3upx;
          content: "";
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
    background: #ffffff;
    box-shadow: 0 4upx 28upx 0 rgba(119, 119, 119, 0.06);
    border-radius: 16upx;
    margin: 24upx 32upx 0;
    padding: 32upx;
    box-sizing: border-box;
    position: relative;

    &::before {
      content: "";
      position: absolute;
      left: 0;
      top: 40upx;
      width: 6upx;
      height: 32upx;
      background: #ff703f;
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
      color: #ff3f64;
      line-height: 45upx;
      margin-bottom: 16upx;
    }

    .buy-time,
    .order-no {
      font-size: 28upx;
      font-weight: 400;
      color: #aaaaaa;
      line-height: 40upx;
      margin-bottom: 12upx;
    }

    .btn {
      margin-top: 12upx;
      line-height: 96upx;
      font-size: 32upx;
      font-weight: 400;
      color: #ff703f;
      text-align: center;
      border-top: 1px solid rgba(204, 204, 204, 0.5);
    }
  }
}
</style>
