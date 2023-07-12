<template>
	<view class="order-cell">
		<view class="order-cell_name">
			<view>{{orderInfo.storeName}}</view>
			<view>{{orderInfo.roomName}}</view>
		</view>
		<view class="order-cell_info">
			<u-icon name="rmb-circle" custom-style="margin-right:10rpx" size="30rpx"></u-icon>
			<text>{{orderInfo.payAmount}}</text>
			<view style="flex: 1;text-align: right;">
				<text class="pay-type-label">{{payTypeList[orderInfo.payType]}}</text>
			</view>
		</view>
		<view class="order-cell_info">
			<u-icon name="clock" custom-style="margin-right:10rpx" size="30rpx"></u-icon>
			时间: {{orderInfo.startTime}} - {{orderInfo.endTime}}
		</view>
		<view class="order-cell_info" @click.stop="makePhoneCall">
			<u-icon name="phone-fill" custom-style="margin-right:10rpx" size="32rpx"></u-icon>
			<text>{{orderInfo.phone}}</text>
		</view>
		<view class="order-cell_info" @click="toMap">
			<u-icon name="map" custom-style="margin-right:10rpx" size="32rpx"></u-icon>
			<text>{{orderInfo.storeAdress}}</text>
		</view>
		<view class="btn-bar">
			<view class="btn-bar_btn" @click="onOpenStore">开大门</view>
			<view class="btn-bar_btn" @click="onOpenRoom">开包厢</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'order-cell',
		props: {
			orderInfo: {
				type: Object,
				default(){
					return {}
				}
			}
		},
		data() {
			return {
				payTypeList:[null, "微信支付", "余额支付", null, "优惠券支付"]
			}
		},
		created() {
	
		},
		methods: {
			makePhoneCall(){
				uni.makePhoneCall({
					phoneNumber: this.orderInfo.phone
				})
			},
			toMap(){
				uni.openLocation({
					latitude: new Number(this.orderInfo.latitude),
					longitude: new Number(this.orderInfo.longitude),
					address: this.orderInfo.storeAdress,
					fail(res) {
						console.log(res)
					}
				})
			},
			onOpenStore(){
				this.$api.openStore(this.orderInfo.storeId).then(()=>{
					uni.showToast({
						icon: "none",
						title: "已发送开大门请求"
					})
				})
			},
			onOpenRoom(){
				this.$api.openRoom(this.orderInfo.roomId).then(()=>{
					uni.showToast({
						icon: "none",
						title: "已发送开包厢请求"
					})
				})
			}
		}
	}
</script>

<style lang="scss">
	.order-cell{
		padding: 20rpx;
		margin: 20rpx 0;
		background-color: #fff;
		border-radius: 10rpx;
		&_name{
			display: flex;
			justify-content: space-between;
			margin-bottom: 20rpx;
			color: $u-main-color;
		}
		&_info{
			display: flex;
			color: $u-content-color;
			font-size: 24rpx;
			margin: 10rpx 0;
		}
		.btn-bar{
			display: flex;
			justify-content: space-around;
			border-top: 1rpx solid $u-border-color;
			padding-top: 20rpx;
			&_btn{
				width: 200rpx;
				text-align: center;
				background: $u-primary;
				color: $u-bright;
				border-radius: 10rpx;
				height: 60rpx;
				line-height: 60rpx;
			}
		}
		.pay-type-label{
			padding: 5rpx 10rpx;
			border-radius: 5rpx;
			border: 1rpx solid $u-success;
			color: $u-success;
		}
	}
</style>