<template>
	<view class="coupon-cell">
		<view class="coupon-cell_left">
			<view class="coupon-cell_title">
				<view>{{coupon.couponName}}</view>
				<view>{{hourStr}}{{timesStr}}</view>
			</view>
			<view class="coupon-cell_tips">{{dateStr}}</view>
			<view class="coupon-cell_tips">{{coupon.storeId ? coupon.storeName : '不限门店'}}</view>
		</view>
		<view class="coupon-cell_right">
			<view class="coupon-cell_price">¥100</view>
			<view class="coupon-cell_type">{{coupon.cardType == 1 ? '时长卡' : '次数卡'}}</view>
			<view class="coupon-cell_btn" @click="$toast('该功能即将上线')">去使用</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: '',
		props: {
			coupon: {
				type: Object,
				default(){
					return {}
				}
			}
		},
		data() {
			return {
				
			}
		},
		computed: {
			hourStr(){
				if(this.coupon.maxMinuteOnce){
					return this.coupon.maxMinuteOnce / 60 + '小时'
				}
				return ''
			},
			timesStr(){
				if(this.coupon.payType == 2){
					return '|' + this.coupon.numCanUse + '次'
				}
				return ''
			},
			dateStr(){
				if(this.coupon.endDate){
					return this.coupon.startDate.substr(0, 10) + '至' + this.coupon.endDate.substr(0, 10)
				}
				return '不限期'
			}
		},
		created() {
	
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
	.coupon-cell{
		display: flex;
		align-items: center;
		margin: 20rpx 0;
		padding: 20rpx;
		border-radius: 10rpx;
		background: #fff;
		&_left{
			flex: 1;
			padding-right: 20rpx;
		}
		&_right{
			text-align: center;
			padding-left: 20rpx;
			border-left: 1rpx dashed $u-border-color;
		}
		&_btn{
			display: inline-block;
			height: 46rpx;
			line-height: 46rpx;
			width: 120rpx;
			border-radius: 20rpx;
			background: $u-primary;
			color: #fff;
			font-size: 24rpx;
		}
		&_price{
			font-size: 40rpx;
			color: $u-primary-dark;
		}
		&_type{
			color: $u-tips-color;
			font-size: 20rpx;
			margin: 5rpx 0;
		}
		&_tips{
			color: $u-tips-color;
			font-size: 20rpx;
			margin: 5rpx 0;
		}
		&_title{
			display: flex;
			justify-content: space-between;
			color: $u-content-color;
			margin-bottom: 20rpx;
		}
	}
</style>