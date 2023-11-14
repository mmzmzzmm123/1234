<template>
	<view class="hour-status-bar">
		<view class="hour-status-bar_item" v-for="item in hours" :key="item.name">
			<view :class="item.error?'rect rect--error':item.current?'rect rect--current':item.status?'rect rect--fill':'rect'"></view>
			<view>{{item.name}}</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: '',
		props: {
			hours: {
				type: Array,
				default(){
					return []
				}
			}
		},
		data() {
			return {
				// hours: [
				// 	{name: '0', status: 1},
				// 	{name: '1', status: 1},
				// 	{name: '2', status: 0},
				// 	{name: '3', status: 0},
				// 	{name: '4', status: 0},
				// 	{name: '5', status: 0},
				// 	{name: '6', status: 1},
				// 	{name: '7', status: 1},
				// 	{name: '8', status: 1},
				// 	{name: '9', status: 1},
				// 	{name: '10', status: 1},
				// 	{name: '11', status: 1},
				// 	{name: '12', status: 1},
				// 	{name: '13', status: 0},
				// 	{name: '14', status: 0},
				// 	{name: '15', status: 0},
				// 	{name: '16', status: 0},
				// 	{name: '17', status: 0},
				// 	{name: '18', status: 1},
				// 	{name: '19', status: 1},
				// 	{name: '20', status: 1},
				// 	{name: '21', status: 1},
				// 	{name: '22', status: 1},
				// 	{name: '23', status: 1}
				// ]
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
	.hour-status-bar{
		display: flex;
		&_item{
			flex: 1;
			width: 25rpx;
			text-align: center;
			font-size: 16rpx;
		}
		.rect{
			margin-bottom: 5rpx;
		}
	}
</style>