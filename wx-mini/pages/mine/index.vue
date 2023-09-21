<template>
	<view>
		<view class="top">
			<navigator class="mine-card" url="/pages/recharge/shop-list/index?type=yu-e">
				<view class="mine-card_value">¥{{amount}}</view>
				<view>我的余额</view>
			</navigator>
			<navigator class="mine-card" url="../coupon/list/index">
				<view class="mine-card_value">0</view>
				<view>我的卡券</view>
			</navigator>
		</view>
		<navigator class="recharge" url="/pages/recharge/shop-list/index">
			<text class="iconfont icon-liwu"></text>
			充值有礼
		</navigator>
		<view class="bottom">
			<u-cell title="美团验券" is-link url="/pages/receipt-consume/index">
				<text slot="icon" class="iconfont icon-meituan"></text>
			</u-cell>
			<u-cell title="全部门店" is-link url="/pages/shop/shop-list">
				<text slot="icon" class="iconfont icon-shop"></text>
			</u-cell>
			<!-- <u-cell title="服务协议" is-link>
				<text slot="icon" class="iconfont icon-fuwuxieyi"></text>
			</u-cell>
			<u-cell title="隐私声明" is-link>
				<text slot="icon" class="iconfont icon-yinsixieyi"></text>
			</u-cell> -->
		</view>
		<require-authorize></require-authorize>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		computed: {
			amount(){
				return this.$store.state.amount
			},
			validConponCount(){
				return this.$store.state.validConponCount
			}
		},
		onLoad() {
			this.$store.dispatch("getAmount")
			this.$store.dispatch("getValidConponCount")
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
	.top{
		display: flex;
		justify-content: space-between;
		background: #e6fff4;
		padding-bottom: 70rpx;
	}
	.bottom{
		.iconfont{
			color: $u-primary;
		}
	}
	.mine-card{
		background: linear-gradient($u-primary-dark,$u-primary-light);
		padding: 30rpx;
		border-radius: 20rpx;
		margin: 30rpx;
		box-shadow: 5rpx 10rpx 10rpx $u-primary-dark;
		color: #FFF;
		text-align: center;
		flex: 1;
		&_value{
			font-size: 40rpx;
			margin-bottom: 20rpx;
		}
	}
	.recharge{
		margin: -60rpx 80rpx 20rpx;
		height: 100rpx;
		line-height: 100rpx;
		border-radius: 100rpx;
		background: $u-warning;
		color: #FFF;
		text-align: center;
		font-size: 40rpx;
		.iconfont{
			color: #fff;
			font-size: 50rpx;
			vertical-align: bottom;
			margin-right: 20rpx;
		}
	}
</style>