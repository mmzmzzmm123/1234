<template>
	<view>
		<view class="product">
			<image class="cover" src="/static/index/product/cover.jpg"></image>
			<view class="title">潜意识投射测试</view>
			<view class="sub-title">潜意识投射测试</view>
			<view class="price">￥19.99</view>
			<view class="info">
				<view class="item">15道精选题</view>
				<view class="item">7页专业报告</view>
				<view class="item">2222测试过</view>
				<view class="clear"></view>
			</view>
			<view style="height: 20upx;margin:40upx 20upx;background-color:#b4b4b4;clear:both"></view>
			
			<view class="title">测评介绍</view>
			<image style="width: 100%;"  mode="widthFix" src="/static/index/product/product.jpg"></image>
			
			<!-- 底部操作菜单 -->
			<view class="page-bottom">
			
				<navigator url="/pages/index/index" open-type="switchTab" class="p-b-btn">
					<image class="yticon" src="/static/tab-home.png"></image>
					<text>首页</text>
				</navigator>
				<button class="p-b-btn btn" open-type="share">
					<image class="yticon" src="/static/icon-share.png"></image>
					<text>分享</text>
				</button>
				<view class="p-b-btn" @click="">
					<image class="yticon" src="/static/icon-cart.png"></image>
					<text>购物车</text>
				</view>
			
			
				<view class="action-btn-group">
					<view class=" add-cart-btn" @click="">加入购物车</view>
					<view class=" buy-now-btn" @click="">立即购买</view>
			
				</view>
			</view>
			
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {


		}
	}
</script>

<style lang="scss">
	page {
		background: #fff;

		.product {
			
			.cover {
				width:750upx;
				height: 450upx;
			}

			.title {
				font-weight: bold;
				text-align: center;
				font-size: 32upx;
			}
			.sub-title {
				margin-top: 20upx;
				text-align: center;
				font-size: 28upx;
				color: #b4b4b4;
			}

			.price {
				font-size: 32upx;
				color: #f59808;
				margin-top: 40upx;
				text-align: center;
			}
			.info{
				color: #b4b4b4;
				font-size: 28upx;
				margin-top: 20upx;
				margin-bottom: 20upx;
				
				.item{
					text-align: center;
					float:left;
					width: 33.333%;
					
				}
				.clear{clear: both;}
			}
			.introduce{
				
				
			}
		}
		/* 底部操作菜单 */
		.page-bottom {
			position: fixed;
			/* left: 30upx; */
			bottom: 0upx;
			z-index: 95;
			display: flex;
			justify-content: left;
			align-items: center;
			width: 750upx;
			height: 98upx;
			background: rgba(255, 255, 255, 1);
			box-shadow: 0 0 20upx 0 rgba(0, 0, 0, .5);
		
			.p-b-btn {
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
				font-size: 24upx;
				color: #F5A623;
				width: 96upx;
				height: 80upx;
		
				.yticon {
					width: 50upx;
					height: 50upx;
					color: #909399;
				}
		
				&.btn {
					border: 0upx !important;
					margin-top: 15upx;
				}
		
				.icon-shoucang {
					font-size: 46upx;
				}
			}
		
			.action-btn-group {
				margin-left: 20upx;
				height: 98upx;
				line-height: 98upx;
				font-size: 28upx;
				text-align: center;
				color: #fff;
				width: 440upx;
				position: absolute;
				right: 0;
		
				.add-cart-btn {
					height: 98upx;
					line-height: 98upx;
					width: 220upx;
					background: #F5A623;
					float: left;
				}
		
				.buy-now-btn {
					height: 98upx;
					line-height: 98upx;
					width: 220upx;
					float: left;
					background: #FE314A;
		
				}
		
		
			}
		}
	}
</style>
