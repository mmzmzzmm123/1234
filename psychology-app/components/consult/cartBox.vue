<template>
  <view class="create-order-mask">
    <view class="order-info">
      <view class="close-icon" @tap="closeCart"></view>
      <view class="course-info">
        <view class="info-box">
          <img class="img left" :src="courseInfo.iconUrl" />
          <view class="right">
            <view class="name txt-overflow txt-overflow-line2">
              {{ courseInfo.name }}</view
            >
            <view class="bottom-box">
              <view class="num">{{ courseInfo.author }}</view>
              <view class="price">
                <span class="icon">￥</span>{{ courseInfo.price }}</view
              >
            </view>            
          </view>
        </view>
        <view class="coupon-box">
          <view class="coupon-info">
            <img class="icon" src="/static/icon/coupon.png" />
            <span>优惠券</span>
          </view>
          <view class="coupon-cue"
            >无可用优惠券<img class="img" src="/static/icon/more.png"
          /></view>
        </view>
      </view>
      <view class="price-box"
        >合计：<span class="price"
          ><span class="icon">￥</span>{{ courseInfo.price }}</span
        >
      </view>
      <view class="submit-btn" @tap="submitPay">确认支付</view>
    </view>
  </view>
</template>
<script>
import orderServer from "@/server/course/order";
import utils from "@/utils/common";
import { getPaySign, wxPay } from "@/server/wxApi";
export default {
  props: ["courseInfo","redirectUri"],
  data() {
    return {
      userInfo: {},
    };
  },
  methods: {
    closeCart() {
      this.$emit("closeCart");
    },
    async submitPay() {
      // this.userInfo = uni.getStorageSync("userInfo")
      this.userInfo = uni.getStorageSync("userInfo") ? JSON.parse(uni.getStorageSync("userInfo")) : undefined;
      if (this.userInfo && this.userInfo.userId) {
		let res = await getPaySign(
      this.userInfo.userId, 
      this.courseInfo.id, 
      this.courseInfo.price,
      {
        module: 'course'
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
					url: "/pages/course/order",
				});
			})
		}	  

      } else {
        utils.loginWx(this.redirectUri);
      }
    },
  },
};
</script>
<style lang="scss" scoped>
@import "@/style/common.scss";
.create-order-mask {
  width: 100vw;
  height: 100vh;
  background: rgba(51, 51, 51, 0.5);
  position: fixed;
  left: 0;
  top: 0;
  z-index: 10001;	
  .order-info {
    position: absolute;
    bottom: 0;
    left: 0;
    height: 580upx;
    width: 100vw;
    background-color: #fff;	
    padding: 0 24upx;

    .close-icon {
      background-image: url("/static/icon/close.png");
      background-size: 100% 100%;
      width: 36upx;
      height: 36upx;
      position: absolute;
      top: 23upx;
      right: 70upx;
    }

    .course-info {
      margin-top: 80upx;
      width: 702upx;
      height: 257upx;
      background: #ffffff;
      box-shadow: 0px 2px 10px 0px rgba(175, 45, 0, 0.1);
      border-radius: 12upx;
      padding: 24upx 32upx 0;
      box-sizing: border-box;

      .info-box {
        display: flex;
        flex-direction: row;

        .left {
          width: 148upx;
          height: 148upx;
          border-radius: 8upx;
        }

        .right {
          flex: 1;
          padding-left: 32upx;

          .name {
            height: 88upx;
            font-size: 30upx;
            font-weight: 600;
            color: #333333;
            line-height: 42upx;
            margin-bottom: 32upx;
          }
          
          .bottom-box {
            display: flex;
            align-items: center;
            justify-content: space-between;
          }

          .num {
            font-size: 24upx;
            font-weight: 400;
            color: #777777;
            line-height: 33upx;
          }

          .price {
            font-size: 28upx;
            font-weight: 500;
            color: #ff3f64;
            line-height: 30upx;
            text-align: right;
            margin-top: 0;

            .icon {
              font-size: 22upx;
            }
          }
        }
      }

      .coupon-box {
        width: 100%;
        height: 65upx;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        line-height: 65upx;

        .coupon-info {
          display: flex;
          flex-direction: row;
          font-size: 24upx;
          font-weight: 400;
          color: #777777;
          align-items: center;

          .icon {
            width: 27upx;
            height: 24upx;
            border-radius: 0;
            margin-right: 10upx;
          }
        }

        .coupon-cue {
          display: flex;
          align-items: center;
          font-size: 24upx;
          font-weight: 400;
          color: #333333;
          text-align: right;

          .img {
            margin-left: 12upx;
            width: 12upx;
            height: 18upx;
          }
        }
      }
    }

    .price-box {
      padding-right: 84upx;
      margin: 16upx 0 40upx;
      text-align: right;
      font-size: 24upx;
      font-weight: 500;
      color: #333333;

      .price {
        font-size: 36upx;
        color: #FF3F64;
      }
    }

    .submit-btn {
      width: 598upx;
      height: 80upx;
      background: #ff703f;
      border-radius: 40upx;
      font-size: 28upx;
      font-weight: 600;
      color: #ffffff;
      line-height: 80upx;
      text-align: center;
      margin: 0 auto;
    }

    .cue-txt {
      text-align: center;
      font-size: 22upx;
      font-weight: 400;
      color: #777777;
      line-height: 30upx;
      margin-top: 24upx;
    }
  }
}
</style>
