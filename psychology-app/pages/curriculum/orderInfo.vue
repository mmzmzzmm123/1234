<template>
  <view class="order-info-box">
	<view class="order-info-header">
	  <image 
		src="/static/curriculum/menu/arrow_left.png"
		class="order-back-icon"
		@tap="back"
	  >
	  </image>
	  <view class="title">订单详情</view>
	</view>
    <view class="order-status">
      <image
        :src="
          orderInfo.status == 1
            ? '/static/curriculum/user/success.png'
            : '/static/curriculum/user/time.png'
        "
        class="order-status-icon"
      ></image>
      <span class="order-status-txt">{{
        orderInfo.status == 1 ? "订单已完成" : "订单待支付，剩余" + orderPayTime
      }}</span>
    </view>
    <view class="course-info info-box">
      <view class="title">课程信息</view>
      <view class="course-content">
        <view class="course-img"
          ><image :src="orderInfo.courseInfo.url"></image
        ></view>
        <view class="txt-box">
          <view class="course-title">{{ orderInfo.courseInfo.name }}</view>
          <view class="course-author">{{ orderInfo.courseInfo.author }}</view>
        </view>
      </view>
      <view class="course-price">
          <span class="txt">课程总价</span
          ><span class="price"
            ><span class="icon">￥</span>{{ orderInfo.price }}</span
          >
        </view>
        <view class="order-price">
          <span class="txt">需付款</span
          ><span class="price">
            <span class="icon">￥</span>{{ orderInfo.price }}
          </span>
        </view>
    </view>
    <view class="order-info info-box">
      <view class="title">订单信息</view>
      <view class="order-info-txt"
        ><span class="label">订单编号</span
        ><span class="value">{{ orderInfo.orderNo }}</span></view
      >
      <view class="order-info-txt"
        ><span class="label">下单时间</span
        ><span class="value">{{ orderInfo.time }}</span></view
      >
    </view>
  </view>
</template>
<script>
import utils from "@/utils/common";
import orderServer from "@/server/curriculum/order";
export default {
  data: () => {
    return {
      orderPayTime: "00:00",
      orderInfo: {
        price: 600,
        status: 1,
        orderNo: "324234253465475678",
        time: "2023-02-18 12:44:12",
        courseInfo: {
          name: "课程课程课程",
          price: 3,
          teacher: "张三",
          url: "/static/curriculum/1.png",
        },
      },
    };
  },
  async created() {
    this.orderInfo = await orderServer.getOrderDetail(utils.getParam(location.href, "orderId"));
  },
  methods: {
  	back() {
  		uni.navigateTo({
  		  url: "/pages/curriculum/order",
  		});
  	}
  },
};
</script>
<style lang="scss">
@import "@/style/common.scss";
page {
  background: #f8f8f8;
  .order-info-box {
    padding: 40upx 32upx;
	.order-info-header {
		display: flex;
		height: 36upx;
		font-size: 48upx;
		margin-bottom: 20upx;
		.order-back-icon {
			width: 36upx;
			height: 36upx;
			position: absolute;
			left: 30upx;
			top: 50upx;
			
		}
		.title {
			width: 100%;
			text-align: center;
		}
	}
    .order-status {
      margin-bottom: 32upx;
      .order-status-icon {
        width: 36upx;
        height: 36upx;
        margin-right: 16upx;
        vertical-align: middle;
      }
      .order-status-txt {
        height: 50upx;
        font-size: 36upx;
        font-weight: 500;
        color: #333333;
        line-height: 50upx;
      }
    }
    .course-info {
      margin-bottom: 16upx;
      .course-content {
        display: flex;
        flex-direction: row;
        margin-bottom: 14upx;
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
          }
        }
      }
      .course-price,
      .order-price {
        line-height: 75upx;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        .txt {
          font-size: 26upx;
          font-weight: 400;
          color: #777777;
        }
        .price{
          line-height: 75upx;
        }
      }
      .course-price {
        border-bottom: 1px solid #E6E6E6;

        .price {
          font-size: 22upx;
          font-weight: 400;
          color: #333333;
        }
      }
      .order-price {
        .price {
          font-size: 32upx;
        }
        .icon{
          font-size: 22upx;
        }
      }
    }
    .order-info {
      .order-info-txt {
        height: 37upx;
        font-size: 26upx;
        font-weight: 400;
        color: #777777;
        line-height: 37upx;
        margin-bottom: 24upx;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        .value {
          color: #333333;
        }
      }
    }
    .info-box {
      padding: 24upx 32upx;
      background: #ffffff;
      box-shadow: 0px 4upx 28upx 0px rgba(119, 119, 119, 0.06);
      border-radius: 12upx;
      .title {
        height: 45upx;
        font-size: 32upx;
        font-weight: 600;
        color: #333333;
        line-height: 45upx;
        margin-bottom: 32upx;
      }
    }
  }
}
</style>
