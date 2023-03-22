<template>
  <view class="order-page">
    <view class="tab">
      <span
        class="tab-item"
        v-for="tab in tabList"
        :class="{ active: tab.status === currentStatus }"
        @tap="changeTab(tab.status)"
        >{{ tab.title }}</span
      >
    </view>
    <view
      class="order-item"
      v-for="order in orderList"
      @tap="toOrderInfo(order)"
    >
      <view class="buy-time"
        ><span>{{ order.createTime }} </span>
        <span class="order-status-txt">
          <span v-if="order.status==0">剩余 09:00</span>
          <span>{{statusTxt[order.status]}}</span>
        </span>
      </view>
      <view class="course-content">
        <view class="course-img"
          ><image :src="order.courseInfo.url"></image
        ></view>
        <view class="txt-box">
          <view class="course-title">{{ order.courseInfo.name }}</view>
          <view class="course-author">
            <span>{{ order.courseInfo.author }}</span>

            <view class="price"
              ><span class="icon">￥</span>{{ order.amount }}</view
            >
          </view>
        </view>
      </view>
      <view class="btn-box">
        <view
          class="btn"
          v-show="order.status == 1"
          @tap="toLearningCourse(order)"
          >去学习</view
        >
        <view class="btn cancel" v-show="order.status == 0" @tap="toCancel(order)"
          >取消</view
        >
        <view class="btn" v-show="order.status == 0" @tap="toPay(order)"
          >支付</view
        >
      </view>
    </view>
    <no-data v-if="orderList.length == 0"></no-data>
    <view class="footer" v-else>已经到底了</view>
    <curriculum-tab-bar :currentIndex="2"></curriculum-tab-bar>
    <cartBox
      @closeCart="cartShow"
      v-if="cartBoxShow"
      :courseInfo="courseInfo"
    ></cartBox>
  </view>
</template>
<script>
import noData from "@/components/curriculum/noData";
import orderServer from "@/server/curriculum/order";
export default {
  components: { noData },
  data() {
    return {
      currentStatus: "",
      tabList: [
        { title: "全部", status: "" },
        { title: "待付款", status: 0 },
        { title: "已完成", status: 1 },
      ],
      statusTxt: ["待付款", "已完成", "已取消"],
      orderList: [
        {
          createTime: "123123123",
          status: 0,
          amount: 222,
          courseInfo: { url: "234234", name: "23d", author: "33" },
        },
        {
          createTime: "123123123",
          status: 2,
          amount: 222,
          courseInfo: { url: "234234", name: "23d", author: "33" },
        },
        {
          createTime: "123123123",
          status: 1,
          amount: 222,
          courseInfo: { url: "234234", name: "23d", author: "33" },
        },
      ],
      courseInfo: {},
      cartBoxShow: false,
    };
  },
  async created() {
    // this.orderList = await orderServer.getOrderList();
  },
  methods: {
    cartShow() {
      this.cartBoxShow = !this.cartBoxShow;
    },
    toOrderInfo(order) {
      uni.navigateTo({
        url: "/pages/curriculum/order?orderId=" + order.orderId,
      });
    },
    toPay(order) {
      this.courseInfo = order;
      this.cartShow();
    },
    async toCancel(order) {
      let result = await orderServer.changeOrderStatus(order.id, 0);
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
        url: "/pages/curriculum/learningCourse?courseId=" + order.courseId,
      });
    },
    async changeTab(status) {
      this.currentStatus = status;
      this.orderList = await orderServer.getOrderList(status);
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

    .buy-time {
      font-size: 24upx;
      font-weight: 400;
      color: #777777;
      line-height: 33upx;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      .order-status-txt {
        font-size: 24upx;
        font-weight: 400;
        color: #ff703f;
        line-height: 33upx;
        span {
          margin-left: 24upx;
        }
      }
    }

    .course-content {
      display: flex;
      flex-direction: row;
      margin-bottom: 14upx;
      border-bottom: 1px solid #e6e6e6;
      .course-img {
        width: 130upx;
        height: 130upx;
        border-radius: 7upx;
        margin-right: 24upx;
        image {
          width: 100%;
          height: 100%;
        }
      }
      .txt-box {
        flex: 1;
        .course-title {
          font-size: 30upx;
          font-weight: 600;
          color: #333333;
          line-height: 42upx;
          margin-bottom: 50upx;
        }
        .course-author {
          font-size: 26upx;
          font-weight: 400;
          color: #777777;
          line-height: 37upx;
          display: flex;
          flex-direction: row;
          justify-content: space-between;
        }
      }
    }
    .btn-box {
      display: flex;
      flex-direction: row;
      justify-content: right;
      .btn {
        width: 176upx;
        height: 72upx;
        background: #ff703f;
        border-radius: 36upx;
        color: #fff;
        font-size: 28upx;
        text-align: center;
        line-height: 72upx;
        &.cancel {
          border: 1px solid #777777;
          background-color: #fff;
          color: #333;
          margin-right: 16upx;
        }
      }
    }
  }
}
</style>
