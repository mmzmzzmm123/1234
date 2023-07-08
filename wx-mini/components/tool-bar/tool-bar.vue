<template>
	<view :class="{'tool-bar': true, 'tool-bar--hidden': hidden}">
		<view class="tool-bar_item tool-bar_item--dark" tool-bar_item--dark>
			<text class="iconfont icon-kaimen"></text>
			<view>开大门</view>
		</view>
		<view class="tool-bar_item">
			<text class="iconfont icon-kaimen"></text>
			<view>开包厢</view>
		</view>
		<view class="tool-bar_item">
			<text class="iconfont icon-zhuo"></text>
			<view>开台</view>
		</view>
		<view class="tool-bar_item tool-bar_item--light">
			<text class="iconfont icon-xushi"></text>
			<view>续单</view>
		</view>
		<view class="tool-bar_item tool-bar_item--light">
			<text class="iconfont icon-fenxiang"></text>
			<view>分享</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'tool-bar',
		props: {
			hidden: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				
			}
		},
		created() {
	
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
	.tool-bar{
		position: fixed;
		// padding: 10rpx;
		right: 0;
		bottom: 40rpx;
		transition: all 300ms;
		&--hidden{
			right: -100%;
		}
		&_item{
			display: flex;
			flex-direction: column;
			justify-content: center;
			margin: 20rpx;
			background: $u-primary;
			width: 100rpx;
			height: 100rpx;
			border-radius: 50rpx;
			text-align: center;
			font-size: 22rpx;
			color: $u-bright;
			.iconfont{
				color: $u-bright;
				font-size: 30rpx;
			}
			&--dark{
				background: $u-primary-dark;
			}
			&--light{
				background: $u-primary-light;
			}
		}
	}
</style>