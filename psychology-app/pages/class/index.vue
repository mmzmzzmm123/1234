<template>
	<div>
		<scroll-view scroll-y="true" class="left-class-view">

			<view v-for="productClass in classList" v-bind:key="productClass.id">
				<view class="left-class" :class="productClassSelectedIndex===productClass.id?'selected':''"
					@tap="productClassSelected(productClass.id)">{{productClass.name}}</view>
			</view>
		</scroll-view>
		<scroll-view scroll-y="true" class="class-div">
			<view class="ad-view">
				<swiper class="ad-swiper" circular>
					<swiper-item v-for="(item, index) in adList" :key="index">
						<image :src="item.imgUrl" />
					</swiper-item>
				</swiper>
			</view>

			<view class="item-list">
				<view class="item" v-for="(item,index) in hotList">
					<view class="left">
						<view class="title">{{item.title}}</view>
						<view class="subTitle">{{item.title}}</view>
						<view class="price">￥{{item.price}}</view>
						<view class="num">{{item.num}}人已测</view>
					</view>
					<image :src="item.img" class="right"></image>
				</view>

			</view>
		</scroll-view>
	</div>
</template>

<script>
	export default {

		data() {
			return {
				productClassSelectedIndex: 1,
				classList: [{
						id: 1,
						name: "趣味评测"
					},
					{
						id: 2,
						name: "趣味评测"
					},
					{
						id: 3,
						name: "趣味评测"
					},
					{
						id: 4,
						name: "趣味评测"
					},
					{
						id: 5,
						name: "趣味评测"
					},
					{
						id: 6,
						name: "趣味评测"
					},
					{
						id: 7,
						name: "趣味评测"
					},
					{
						id: 8,
						name: "趣味评测"
					},
					{
						id: 9,
						name: "趣味评测"
					},
				],
				adList: [{
					imgUrl: '/static/index/banner1.png'
				}, {
					imgUrl: '/static/index/banner1.png'
				}],
				hotList: [{
						title: "潜意识测试",
						num: 200,
						price: 19.99,
						img: "/static/index/hot/1.jpg"
					},
					{
						title: "潜意识测试",
						num: 200,
						price: 19.99,
						img: "/static/index/hot/1.jpg"
					},
					{
						title: "潜意识测试",
						num: 200,
						price: 19.99,
						img: "/static/index/hot/1.jpg"
					},
					{
						title: "潜意识测试",
						num: 200,
						price: 19.99,
						img: "/static/index/hot/1.jpg"
					}
				]
			}
		},
		async onLoad() {

		},
		methods: {

			productClassSelected(productClassId) {
				this.productClassSelectedIndex = productClassId
				//广告图片
				this.$Request.post(this.$api.product.adList, {
					productClassId: productClassId
				}).then(res => {
					if (res == [] || !res || res.length < 1)
						this.adList = [{
							imgUrl: '/static/product/ad.jpg'
						}];
					else
						this.adList = res;
				})
			},
			toProductList(productClassId) {
				uni.navigateTo({
					url: '/pages/product/productList?productClassId=' + productClassId
				})
			}

		},

	}
</script>

<style lang='scss'>
	.left-class-view {
		background: #f6f6f6;
		width: 174upx;
		height: 100vh;
		float: left;

		.left-class {
			height: 100upx;
			line-height: 100upx;
			color: #444444;
			font-size: 26upx;
			text-align: center;
			background: #f6f6f6;

			&.selected {
				font-size: 28upx;
				font-weight: bold;
				background: #fff;

				&::before {
					content: '';
					background-color: red;
					display: inline-block;
					width: 8upx;
					height: 28upx;
					left: 0upx;
					margin-top: 37upx;
					position: absolute;
				}
			}
		}
	}

	.ad-view {
		margin-bottom: 5upx;

		.ad-swiper,
		image {
			width: 558upx;
			height: 220upx;
		}
	}

	.class-title {
		color: #212121;
		font-size: 32upx;
		font-weight: bold;
		padding: 50upx 0 30upx 5upx;
		clear: both;
	}

	.class-list {
		padding-bottom: 10upx;
		padding-top: 10upx;
	}







	.class-div {

		background: #f6f6f6;
		width: 558upx;
		height: 100vh;
		float: left;

		

		.item-list {
			margin: 0 0upx;

			.item {
				width: 568upx;
				height: 220upx;
				flex-shrink: 0;
				font-size: 18upx;
				
				border-radius: 15upx;
				margin-bottom: 20upx;
				background-color: #fff;

				.left {
					height: 220upx;
					width: 330upx;
					margin: 10upx 10upx;
					float: left;

					.num {
						margin-top: -35upx;
						margin-left: 200upx;
					}

					.title {
						font-weight: bold;
						text-align: left;
						font-size: 32upx;
					}

					.price {
						font-size: 28upx;
						color: #f59808;
						margin-top: 50upx;

					}
				}

				.right {
					width: 200upx;
					height: 200upx;
					margin: 10upx;
				}
			}
		}


	}
</style>
