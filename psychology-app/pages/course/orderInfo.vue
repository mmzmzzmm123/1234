<template>
  <view >
    <view class="order-info-header">
      <image 
      src="/static/course/menu/arrow_left.png"
      class="order-back-icon"
      @tap="back"
      >
      </image>
      <view class="title">订单详情</view>
    </view>
    <view class="order-info-box">
      
    
      <view class="order-status">
        <image
          :src="
            orderInfo.status == 1
              ? '/static/course/user/success.png'
              : '/static/course/user/time.png'
          "
          class="order-status-icon"
        ></image>
        <span class="order-status-txt">{{
          orderInfo.status == 1 ? "订单已完成" : remainTime(orderInfo.createTime)
        }}</span>
      </view>
      <view class="course-info info-box">
        <view class="title">课程信息</view>
        <view class="course-content">
          <view class="course-img">
        <image :src="orderInfo.courseInfo && orderInfo.courseInfo.url"></image>
      </view>
          <view class="txt-box">
            <view class="course-title">{{ orderInfo.courseInfo && orderInfo.courseInfo.name }}</view>
            <view class="course-author">{{ orderInfo.courseInfo && orderInfo.courseInfo.author }}</view>
          </view>
        </view>
        <view class="course-price">
            <span class="txt">课程总价</span
            ><span class="price"
              ><span class="icon">￥</span>{{ orderInfo.amount }}</span
            >
          </view>
          <view class="order-price">
            <span class="txt">需付款</span
            ><span class="price">
              <span class="icon">￥</span>{{ orderInfo.amount }}
            </span>
          </view>
      </view>
      <view class="order-info info-box">
        <view class="title">订单信息</view>
        <view class="order-info-txt"
          ><span class="label">订单编号</span
          ><span class="value">{{ orderInfo.orderId }}</span></view
        >
        <view class="order-info-txt"
          ><span class="label">下单时间</span
          ><span class="value">{{ orderInfo.createTime }}</span></view>
      </view>
    </view>
  </view>
</template>
<script>
import orderServer from "@/server/course/order";
import formatTime from '@/utils/formatTime.js'
export default {
  data: () => {
    return {
      orderPayTime: "00:00",
      orderInfo: {},
    };
  },
  computed: {
  	remainTime() {
  		return (orderTime) => {
  			if (new Date().getTime() < new Date(orderTime).getTime() + 30 * 60 * 1000) {
  				// 下单不超过30分钟
  				const remainSeconds = parseInt((new Date(orderTime).getTime() + 30 * 60 * 1000 -  new Date().getTime()) / 1000)
  				
  				return "剩余" + formatTime.formatSecondsCH(remainSeconds)
  			} else {
  				return "订单已过期"
  			}
  		} 
  	},	  
  },
  async created() {
    this.orderInfo = await orderServer.getOrderDetail(this.$utils.getParam(location.href, "orderId"));
	console.log(this.orderInfo)
  },
  methods: {
  	back() {
  		uni.navigateTo({
  		  url: "/pages/course/order",
  		});
  	},
  },
};
</script>
<style lang="scss">
@import "@/style/common.scss";
page {
  background: #f8f8f8;
  .order-info-header {
  	display: flex;	
  	font-size: 48upx;
  	margin-bottom: 20upx;
    background: #FFFFFF;
    align-items: center;
  	.order-back-icon {
  		width: 30upx;
  		height: 30upx;
      position: absolute;
      left: 20px;
  	}
  	.title {
      margin-top: 22upx;
      margin-bottom: 18upx;
  		width: 100%;
  		text-align: center;
      height: 48rpx;
      font-size: 34rpx;
      font-weight: 600;
      color: #333333;
      line-height: 48rpx;
  	}
  }
  
  .order-info-box {
    padding: 20upx 32upx;
	
    .order-status {
      display: flex;		
      align-items: center;
      background-color: #fff;
      margin-bottom: 32upx;
      .order-status-icon {
        width: 30upx;
        height: 30upx;
        margin-left: 16upx;
        margin-right: 16upx;
        vertical-align: middle;
      }
      .order-status-txt {
        height: 50upx;
        font-size: 30upx;
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
