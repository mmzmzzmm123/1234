<template>
	<view class="product">
		<image class="cover" :src="productInfo.headPicture"></image>
		<view class="title">{{ productInfo.title }}</view>
		<view class="sub-title">测评简介</view>
		<view class="price"><span class="icon">￥</span>{{ productInfo.price }}</view>
		<view class="info">
			<view class="item">{{ productInfo.gaugeNum }}道精选题</view>
			<view class="item">7页专业报告</view>
			<view class="item">{{ productInfo.num || 10 }}人已测</view>
		</view>
		<view class="bg-line"></view>
		<view class="info-title title">测评介绍</view>
		<view class="img-box">
			<image mode="widthFix" class="img-item" :src="productInfo.introduce"></image>
		</view>
		<view class="bg-line"></view>
		<view class="info-title title">测评须知</view>
		<view class="img-box">
			<image mode="widthFix" class="img-item" src="/static/evaluation/product/11.png"></image>
		</view>
		<view class="more-box">
			<navigator url="/pages/evaluation/class" open-type="redirect" class="more">查看更多测试 <img class="img"
					src="/static/icon/more.png" />
			</navigator>
		</view>
		<!-- 底部操作菜单 -->
		<view class="page-bottom">
			<navigator url="/pages/evaluation/index" open-type="redirect" class="p-b-btn">
				<image class="index-icon" src="/static/evaluation/menu/index.png"></image>
				<text>首页</text>
			</navigator>
			<view class="start-test" @tap="startTest">开始测试</view>
		</view>
		<view class="create-order-mask" v-show="payBoxShow">
			<view class="order-info">
				<view class="close-icon" @tap="payBoxShow = !payBoxShow"></view>
				<view class="product-info">
					<view class="info-box">
						<img class="img left" :src="productInfo.listShowPicture" />
						<view class="right">
							<view class="name txt-overflow txt-overflow-line2">
								{{ productInfo.title }}</view>
							<view class="num">{{ productInfo.gaugeNum }}人已测</view>
							<view class="price"><span class="icon">￥</span>{{ productInfo.price }}</view>
						</view>
					</view>
					<view class="coupon-box">
						<view class="coupon-info">
							<img class="icon" src='/static/icon/coupon.png' />
							<span>优惠券</span>
						</view>
						<view class="coupon-cue">无可用优惠券<img class="img" src="/static/icon/icon/more.png" /></view>
					</view>
				</view>
				<view class="price-box">合计：<span class="price"><span class="icon">￥</span>{{ productInfo.price }}</span>
				</view>
				<view class="submit-btn" @tap="submitPay">确认支付</view>
				<view class="cue-txt">该款项仅供当次测试；可重复购买，购买成功后不予退款</view>
			</view>
		</view>
	</view>
</template>

<script>
import utils from '@/utils/common'
import productServer from '@/server/evaluation/product'
import { getPaySign, wxPay } from "@/server/wxApi";
export default {
	data() {
		return {
			productInfo: {},
			payBoxShow: false,
			productId: 0
		}
	},
	async created() {
		this.productId = parseInt(utils.getParam(location.href, "id"));
		this.productInfo = await productServer.getProductInfo(this.productId);
		this.payBoxShow = utils.getParam(location.href, "payOrder") == 1;
	},
	methods: {
		async submitPay() {
		  this.userInfo = uni.getStorageSync("userInfo")
		  if (this.userInfo && this.userInfo.userId) {
			let res = await getPaySign(this.userInfo.userId, this.productId, {
				module: 'gauge'
			})
			console.log(res)
			if (res.code == 200) {
				const { appId, timeStamp, nonceStr, packageInfo, paySign, signType } = res.data
				wxPay(res.data, () => {
					uni.showToast({
					  icon: "success",
					  title: "支付成功",
					});					
					uni.navigateTo({
						url: "/pages/evaluation/product?id=" + this.productId,
					});
					location.reload()
				})
			}	  
		
		  } else {
		    utils.loginWx(this.redirectUri);
		  }
		},
		startTest() {
			if (this.productInfo.isBuy == 0) { // 未支付
				this.payBoxShow = true;
			} else {
				uni.navigateTo({
					url: `/pages/evaluation/question?productId=${this.productId}&orderId=${this.productInfo.orderId}`,
				});
			}
			
		},
		toHome() {
			uni.navigateTo({
				url: "/pages/evaluation",
			});
		}

	}
}
</script>

<style lang="scss">
@import "@/style/common.scss";

page {
	background: #fff;

	.product {
		padding-bottom: 100upx;

		.cover {
			width: 100%;
			height: 422upx;
		}

		.title {
			font-weight: bold;
			text-align: center;
			font-size: 36upx;
			margin-top: 32upx;
			color: #333333;
			line-height: 50upx;
		}

		.sub-title {
			margin-top: 8upx;
			text-align: center;
			font-size: 24upx;
			color: #777777;
		}

		.price {
			text-align: center;
			margin-top: 16upx;
			font-size: 32upx;
		}

		.info {
			font-size: 24upx;
			font-weight: 400;
			color: #777777;
			line-height: 34upx;
			margin-top: 24upx;
			margin-bottom: 16upx;
			display: flex;
			flex-direction: row;
			justify-content: space-around;

			.item {
				text-align: left;
				position: relative;
				padding-left: 46upx;
				flex: 1;

				&::before {
					position: absolute;
					content: '';
					display: block;
					border-radius: 100%;
					width: 12upx;
					height: 12upx;
					top: 11upx;
					left: 24upx;
					background: #AAAAAA;

				}
			}
		}

		.info-title {
			margin-bottom: 32upx;

			&::after {
				display: block;
				width: 144upx;
				height: 8upx;
				background: #FF703F;
				border-radius: 2upx;
				content: '';
				margin: -10upx auto 0;
			}
		}

		.img-box {
			margin: 30upx 24upx;
		}

		.img-item {
			width: 100%;
		}

		.more-box {
			background-color: #F8F8F8;
			padding: 24upx 25upx;

			.more {
				width: 702upx;
				height: 66upx;
				background: #FFFFFF;
				border-radius: 40upx;
				font-size: 26upx;
				color: #333;
				line-height: 66upx;
				text-align: center;
				justify-content: center;
				display: flex;
				align-items: center;

				.img {
					margin-left: 12upx;
					width: 12upx;
					height: 24upx;
				}
			}
		}

		/* 底部操作菜单 */
		.page-bottom {
			position: fixed;
			bottom: 0upx;
			display: flex;
			justify-content: left;
			align-items: center;
			width: 750upx;
			height: 100upx;
			background-color: #fff;

			.p-b-btn {
				width: 128upx;
				display: flex;
				flex-direction: column;
				align-items: center;
				font-size: 20upx;

				.index-icon {
					width: 48upx;
					height: 48upx;
				}
			}

			.start-test {
				width: 600upx;
				height: 80upx;
				line-height: 80upx;
				background: #FF703F;
				border-radius: 40upx;
				text-align: center;
				font-size: 28upx;
				color: #FFFFFF;
				margin: 9upx 24upx;
				margin-left: 0;
			}


		}
	}

	.create-order-mask {
		width: 100vw;
		height: 100vh;
		background: rgba(51, 51, 51, 0.5);
		position: fixed;
		left: 0;
		top: 0;

		.order-info {
			position: absolute;
			bottom: 0;
			left: 0;
			height: 580upx;
			width: 100vw;
			background-color: #fff;

			.close-icon {
				background-image: url('/static/icon/close.png');
				background-size: 100% 100%;
				width: 30upx;
				height: 30upx;
				position: absolute;
				top: 23upx;
				right: 29upx;
			}

			.product-info {
				margin-top: 80upx;
				width: 702upx;
				height: 257upx;
				background: #FFFFFF;
				box-shadow: 0px 2px 10px 0px rgba(175, 45, 0, 0.1);
				border-radius: 12upx;
				padding: 24upx 32upx 0;
				box-sizing: border-box;

				.info-box {
					display: flex;
					flex-direction: row;

					.left {
						width: 148upx;
						height: 168upx;
						border-radius: 8upx;
					}

					.right {
						flex: 1;
						padding-left: 32upx;

						.name {
							height: 84upx;
							font-size: 30upx;
							font-weight: 600;
							color: #333333;
							line-height: 42upx;
							margin-bottom: 16upx;
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
							color: #FF3F64;
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
						font-size: 24upx;
						font-weight: 400;
						color: #333333;
						text-align: right;

						.img {
							margin-left: 12upx;
							width: 12upx;
							height: 24upx;
						}
					}
				}
			}

			.price-box {
				padding-right: 56upx;
				margin: 16upx 0 40upx;
				text-align: right;
				font-size: 24upx;
				font-weight: 500;
				color: #333333;

				.price {
					font-size: 36upx;
				}
			}

			.submit-btn {
				width: 598upx;
				height: 80upx;
				background: #FF703F;
				border-radius: 40upx;
				font-size: 28upx;
				font-weight: 600;
				color: #FFFFFF;
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

}
</style>
