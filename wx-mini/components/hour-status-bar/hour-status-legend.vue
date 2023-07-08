<template>
	<view class="hour-status-legend">
		<view class="rect rect--fill"></view>
		<text>已预定</text>
		<view class="rect"></view>
		<text>可预订</text>
		<template v-if="showCurrent">
			<view class="rect rect--current"></view>
			<text>当前预定</text>
		</template>
	</view>
</template>

<script>
	export default {
		name: 'hour-status-legend',
		props: {
			showCurrent: {
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
	@import "hour-status-bar.scss";
	.hour-status-legend{
		display: flex;
		align-items: center;
		text{
			margin: 0 30rpx 0 10rpx;
			font-size: 28rpx;
		}
	}
	.rect--current{
		background: $u-success;
	}
</style>