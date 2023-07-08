<template>
	<view>
		<view class="top">
			<view class="top_bar">
				<view>
					<text class="iconfont icon-fuli"></text>
					<view>优惠券</view>
				</view>
				<view>
					<text class="iconfont icon-meituan"></text>
					<view>美团验券</view>
				</view>
				<view>
					<text class="iconfont icon-xufei"></text>
					<view>续费</view>
				</view>
				<view>
					<text class="iconfont icon-huiyuan"></text>
					<view>会员充值</view>
				</view>
			</view>
			<view style="border-radius: 20rpx;overflow: hidden;">
				<ad-swiper :imgList="[shopInfo.logo]"></ad-swiper>
			</view>
		</view>
		<view class="shop">
			<view class="shop-info shop-name">{{shopInfo.name}}</view>
			<view class="shop-desc">
				{{shopInfo.remark}}
			</view>
			<view class="shop-info">
				<u-icon name="map" custom-style="margin-right:10rpx" size="40rpx"></u-icon>
				{{shopInfo.address}}
			</view>
			<view class="shop-info">
				<u-icon name="clock" custom-style="margin-right:10rpx" size="36rpx"></u-icon>
				{{shopInfo.startTime + ' - ' + shopInfo.stopTime}}
			</view>
			<view class="shop-info" @click="makePhoneCall">
				<u-icon name="phone-fill" custom-style="margin-right:10rpx" size="36rpx"></u-icon>
				{{shopInfo.phone}}
			</view>
		</view>
		<view class="u-p-h-20">
			<room-cell v-for="room in roomList" :key="room.id" :roomInfo="room"></room-cell>
		</view>
		<view style="height: 100rpx;"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				shopInfo: {},
				roomList: []
			}
		},
		onLoad() {
			this.getOpenerEventChannel().on('acceptShop', shopInfo => {
				this.shopInfo = shopInfo
				this.refresh()
			})
		},
		methods: {
			makePhoneCall(){
				uni.makePhoneCall({
					phoneNumber: this.shopInfo.phone
				})
			},
			refresh(){
				this.$api.getRoomList({storeId: this.shopInfo.id}).then(res=>{
					this.roomList = res.rows
					// console.log(res)
				})
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: $u-bg-color;
	}
	.top{
		background: linear-gradient($u-primary, $u-bg-color);
		padding: 20rpx;
	}
	.top_bar{
		display: flex;
		justify-content: space-around;
		padding: 20rpx 0;
		text-align: center;
		color: $u-bright;
		font-size: 26rpx;
		.iconfont{
			font-size: 70rpx;
			color: $u-bright;
			border: 4rpx solid $u-bright;
			border-radius: 50%;
			padding: 10rpx;
			margin-bottom: 10rpx;
		}
	}
	.shop{
		margin: 0 20rpx;
		padding: 20rpx;
		background: #fff;
		border-radius: 20rpx;
		color: $u-content-color;
	}
	.shop-desc{
		font-size: 28rpx;
		overflow: hidden;
		word-break: break-word;
	}
	.shop-info{
		display: flex;
		font-size: 32rpx;
		margin: 20rpx 0;
	}
	.shop-name{
		font-size: 36rpx;
		color: $u-main-color;
		margin: 0 0 10rpx 0;
	}
</style>