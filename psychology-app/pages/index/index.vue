<template>
	<view>
		<view class="ad-view">
			<swiper class="ad-swiper" indicator-dots circular style="margin:0 20upx;width:710upx;height: 390upx;">
				<swiper-item v-for="(item, index) in adList" :key="index">
					<image :src="item.imgUrl" style="width:710upx;height: 390upx;" @tap="toProduct(1)" />
				</swiper-item>
			</swiper>
		</view>
		<view style="height: 20upx;"></view>
		<!-- 分类 -->
		<view class="class-view">
			<view class="item" v-for="item in classList" v-bind:key="item.id" @click="">
				<image class="img" :src="item.classPic"></image>
				<view class="name">{{item.className}}</view>
			</view>
		</view>
		<view style="height: 20upx;"></view>
		<view style="">
			<image style="margin: 0 20upx;width:710upx;height: 200upx;" src="/static/index/index1.jpg"></image>
		</view>
		<view style="height: 20upx;"></view>
		<!--热卖爆款-->
		<view style="">
			<image style="margin: 0 20upx;width:210upx;height: 80upx;" src="/static/index/index2.jpg"></image>
		</view>
		<view style="height: 20upx;"></view>

		<view class="hot">
			<view class="col" v-for="(item,index) in hotList">
				<view class="head">
					<view class="num">{{item.num}}人已测</view>
					<view class="vip"></view>
					<image :src="item.img" class="img"></image>
				</view>
				<view class="title">{{item.title}}</view>
				<view class="price">￥{{item.price}}</view>
			</view>

		</view>
		<view style="height: 20upx;"></view>

		<!--全面测评-->
		<view style="">
			<image style="margin: 0 20upx;width:230upx;height: 80upx;" src="/static/index/overall.jpg"></image>
		</view>
		<view style="height: 20upx;"></view>
		<view style="margin: 0 20upx;">
			<image style="width:345upx;height: 180upx;float: left;" src="/static/index/index3.jpg"></image>
			<image style="margin-left: 20upx;width:345upx;height:180upx;" src="/static/index/index4.jpg"></image>
		</view>

		<view style="height: 20upx;"></view>
		<!--轮播-->
		<view class="ad-view">
			<swiper class="ad-swiper" indicator-dots circular style="margin:0 20upx;width:710upx;height: 180upx;">
				<swiper-item v-for="(item, index) in adList2" :key="index">
					<image :src="item.imgUrl" style="width:710upx;height: 180upx;" />
				</swiper-item>
			</swiper>
		</view>
		<view style="height: 20upx;"></view>
		<!--精选测评-->
		<view style="">
			<image style="margin: 0 20upx;width:230upx;height: 80upx;" src="/static/index/index6.jpg"></image>
		</view>

		<view style="height: 20upx;"></view>
		<view class="itemList">
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

	</view>
</template>

<script>
	export default {


		data() {
			return {
				adList: [{
					imgUrl: '/static/index/banner1.png'
				}, {
					imgUrl: '/static/index/banner1.png'
				}],
				adList2: [{
						imgUrl: '/static/index/index5.jpg'
					},
					{
						imgUrl: '/static/index/index5.jpg'
					}
				],
				classList: [{
						classPic: '/static/index/class/class9.png',
						className: '趣味测试',
						id: 16
					},
					{
						classPic: '/static/index/class/class7.png',
						className: '全面评估',
						id: 36
					},
					{
						classPic: '/static/index/class/class0.png',
						className: '专业测评',
						id: 72
					}

				],
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
		onLoad() {

		},
		methods: {
	
			toProduct(id) {
				uni.navigateTo({
					url: "/pages/product/index"
				})
			}
		}

	}
</script>

<style lang="scss">
	page {
		background: #f3f3f3;

		.class-view {
			height: 170upx;
			border-radius: 15upx;
			background: #fff;
			margin: 0 20upx;

			.item {
				margin: 20upx 0 0 110upx;
				float: left;
				width: 88upx;

				.img {
					margin-left: 8upx;
					width: 80upx;
					height: 80upx;
				}

				.name {
					height: 34upx;
					width: 120upx;
					margin-top: 4upx;
					font-size: 24upx;
					color: #333;
				}
			}
		}

		.itemList {
			margin: 0 20upx;
			.item {
				width: 710upx;
				height: 220upx;
				flex-shrink: 0;
				font-size: 18upx;
				margin-left: 20upx;
				background-color: #fff;
				border-radius: 15upx;
				margin-bottom: 20upx;
				.left {
					height: 220upx;
					width: 470upx;
					margin: 10upx 10upx;
					float: left;
					.num {
						margin-top: -35upx;
						margin-left: 350upx;
					}
					.title {
						font-weight: bold;
						text-align: left;
						font-size: 42upx;
					}
					.price {
						font-size: 38upx;
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

		.hot {
			display: flex;
			overflow-y: hidden;
			overflow-x: scroll;
			width: 100%;
			flex-wrap: nowrap;
			margin: 0 20upx;

			.col {
				width: 260upx;
				height: 380upx;
				flex-shrink: 0;
				font-size: 18upx;

				margin-left: 20upx;
				background-color: #fff;
				border-radius: 15upx;

				.head {
					height: 220upx;
					margin: 20upx 20upx 10upx 20upx;

					.img {
						width: 220upx;
						height: 220upx;
					}

					.num {
						position: fixed;
						margin-top: 180upx;
						margin-left: 30upx;
						z-index: 1000;
						color: #fff;
					}

					.vip {}
				}

				.title {
					font-weight: bold;
					text-align: center;
					font-size: 32upx;
				}

				.price {
					font-size: 28upx;
					color: #f59808;
					margin-top: 40upx;
					margin-left: 20upx;
				}
			}
		}

	}
</style>
