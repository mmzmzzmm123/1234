<template>
	<view>
		<ad-swiper></ad-swiper>
		<view class="u-p-h-20">
			<view class="menu">
				<view class="menu-item">
					<view class="menu-item_icon">
						<text class="iconfont icon-meituan"></text>
					</view>
					<view class="menu-item_title">美团验券</view>
				</view>
				<view class="menu-item">
					<view class="menu-item_icon">
						<text class="iconfont icon-huiyuan"></text>
					</view>
					<view class="menu-item_title">会员充值</view> 
				</view>
			</view>
			<view class="search-bar">
				<u-input prefix-icon="search" placeholder="搜索门店"></u-input>
				<view class="search-bar_more">
					更多门店
				</view>
				<u-icon name="arrow-right" size="26rpx"></u-icon>
			</view>
			<view>
				<shop-cell></shop-cell>
			</view>
		</view>
		<!-- <yto-authorize></yto-authorize> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		computed: {
			hasLogin(){
				return this.$store.state.hasLogin
			},
			loginUser(){
				return this.$store.state.loginUser
			}
		},
		watch: {
			
		},
		onLoad() {
			
		},
		onPullDownRefresh() {
			
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
	page{
		background-color: $u-bg-color;
	}
	.menu{
		display: flex;
		justify-content: space-between;
		&-item{
			display: flex;
			width: 340rpx;
			align-items: center;
			background: #fff;
			border-radius: 20rpx;
			margin-top: 20rpx;
			&_icon{
				margin: 20rpx;
				height: 100rpx;
				width: 100rpx;
				text-align: center;
				line-height: 100rpx;
				background: $u-primary-light;
				border-radius: 10rpx;
				.iconfont{
					font-size: 60rpx;
					color: $u-primary;
				}
			}
			&_title{
				flex: 1;
				color: $u-content-color;
			}
		}
	}
	.search-bar{
		display: flex;
		margin-top: 20rpx;
		align-items: center;
		&_more{
			margin-left: 20rpx;
			font-size: 26rpx;
			color: $u-content-color;
		}
	}
</style>
