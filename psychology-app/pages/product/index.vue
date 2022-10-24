<template>
	<view class="product">
		<image class="cover" src="/static/index/product/cover.jpg" @tap="toHome"></image>
		<view class="title">潜意识投射测试</view>
		<view class="sub-title">测评简介</view>
		<view class="price"><span class="icon">￥</span>19.99</view>
		<view class="info">
			<view class="item">15道精选题</view>
			<view class="item">7页专业报告</view>
			<view class="item">2222测试过</view>
		</view>
		<view class="bg-line"></view>
		<view class="info-title title">测评介绍</view>
		<view class="img-box">
			<image mode="widthFix" class="img-item" src="/static/1.png"></image>
			<image mode="widthFix" class="img-item" src="/static/1.png"></image>
		</view>
		<view class="bg-line"></view>
		<view class="info-title title">测评须知</view>
		<view class="img-box">
			<image mode="widthFix" class="img-item" src="/static/index/product/11.png"></image>
		</view>
		<view class="more-box">
			<view class="more">查看更多测试 <img class="img" src="/static/index/more.png" /></view>
		</view>
		<!-- 底部操作菜单 -->
		<view class="page-bottom">
			<navigator url="/pages/index/index" open-type="switchTab" class="p-b-btn">
				<image class="index-icon" src="/static/menu/index.png"></image>
				<text>首页</text>
			</navigator>
			<view class="start-test" @tap="startTest">开始测试</view>
		</view>

	</view>
</template>

<script>
import utils from '../../utils/common'
import productServer from '@/server/product'
export default {
	data() {
		return {
			productInfo: {}
		}
	},
	async created() {
		let id = utils.getParam(location.href, "id");
		console.log(id, '111111111111111')
		this.productInfo = await productServer.getProductInfo(parseInt(id));
	},
	methods: {
		startTest() {
			uni.navigateTo({
				url: "/pages/questionTemplate/index?productId=" + utils.getParam(location.href, "productId"),
			});
		},
		toHome() {
			uni.switchTab({
				url: "/pages/index/index",
			});
		}

	}
}
</script>

<style lang="scss">
@import "../../style/common.scss";

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

		.bg-line {
			height: 16upx;
			background-color: #F8F8F8;
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


}
</style>
