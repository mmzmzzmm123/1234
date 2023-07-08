<template>
	<view class="room-cell-wrapper">
		<view class="room-cell">
			<view class="room-cell_img">
				<u-image mode="aspectFill" width="200rpx" radius="10rpx" height="200rpx" src="https://cdn.uviewui.com/uview/resources/127901018.jpg" @click="onPreviewImage"></u-image>
				<view class="room-cell_status">空闲中</view>
				<view class="room-cell_img_count">
					<u-icon name="photo-fill" color="#fff" size="30rpx"></u-icon>2
				</view>
			</view>
			<view style="flex: 1;">
				<view class="room-cell_name">包厢名称</view>
				<view class="room-cell_package">
					<text>套餐名称</text>
					<text style="float: right;">套餐价格</text>
				</view>
				<view class="label-list">
					<view class="label-list_item">标签</view>
					<view class="label-list_item">标签一</view>
					<view class="label-list_item">标签一二</view>
					<view class="label-list_item">标签</view>
					<view class="label-list_item">标签一</view>
					<view class="label-list_item">标签一二</view>
					<view class="label-list_item">标签</view>
					<view class="label-list_item">标签一</view>
					<view class="label-list_item">标签一二</view>
				</view>
			</view>
		</view>
		<view style="margin-top: 20rpx;">
			<hour-status-bar></hour-status-bar>
			<view style="display: flex;align-items: center;margin-top: 20rpx;justify-content: space-between;">
				<hour-status-legend></hour-status-legend>
				<view class="order-btn">立即预定</view>
			</view>
		</view>
	</view>
</template>

<script>
	import HourStatusLegend from "../hour-status-bar/hour-status-legend.vue"
	export default {
		name: '',
		components: {HourStatusLegend},
		props: {
			
		},
		data() {
			return {
				
			}
		},
		created() {
	
		},
		methods: {
			onPreviewImage(){
				uni.previewImage({
					urls: ['https://www.baidu.com/img/flexible/logo/pc/result@2.png','https://cdn.uviewui.com/uview/resources/127901018.jpg']
				})
			}
		}
	}
</script>

<style lang="scss">
	.room-cell-wrapper{
		padding: 20rpx;
		margin: 20rpx 0;
		background-color: #fff;
		border-radius: 10rpx;
		font-size: 28rpx;
	}
	.room-cell{
		display: flex;
		// align-items: center;
		&_img{
			width: 200rpx;
			height: 200rpx;
			border-radius: 10rpx;
			margin-right: 10rpx;
			position: relative;
			&_count{
				display: flex;
				position: absolute;
				left: 10rpx;
				top: 10rpx;
				color: #fff;
				background: #0008;
				font-size: 26rpx;
				padding: 6rpx;
				border-radius: 5rpx;
			}
		}
		&_status{
			position: absolute;
			bottom: 0;
			left: 0;
			right: 0;
			height: 40rpx;
			line-height: 40rpx;
			background-color: #e0fffbc8;
			color: #1eb8ff;
			text-align: center;
			&--pre{
				background-color: #fffaf2;
				color: #3fff3c;
			}
			&--soft{
				background-color: #ffd8e8;
				color: #ff8961;
			}
		}
		&_name{
			
		}
		&_package{
			color: $u-bright;
			background: $u-primary-light;
			margin-top: 10rpx;
			padding: 0 20rpx;
			height: 46rpx;
			line-height: 46rpx;
			border-radius: 20rpx;
		}
	}
	.label-list{
		display: flex;
		flex-wrap: wrap;
		padding-top: 10rpx;
		&_item{
			padding: 0 10rpx;
			border-radius: 4rpx;
			border: 1rpx solid $u-border-color;
			height: 36rpx;
			line-height: 36rpx;
			font-size: 20rpx;
			margin: 0 10rpx 10rpx 0;
			color: $u-tips-color;
		}
	}
	.order-btn{
		width: 160rpx;
		text-align: center;
		background: $u-primary;
		color: $u-bright;
		border-radius: 10rpx;
		height: 60rpx;
		line-height: 60rpx;
	}
</style>