<template>
	<view class="shop-cell">
		<view class="shop-cell_img">
			<u-image mode="aspectFill" width="200rpx" height="200rpx" src="https://www.baidu.com/img/flexible/logo/pc/result@2.png"></u-image>
			<view class="shop-cell_status">营业中</view>
		</view>
		<view style="flex: 1;">
			<view class="shop-cell_title">
				<view class="shop-cell_name">店铺名称</view>
				<view class="shop-cell_tag-wrapper">
					<view class="shop-cell_tag">棋牌</view>
					<view class="shop-cell_tag">台球</view>
				</view>
			</view>
			<view class="shop-cell_info">
				<u-icon name="map" custom-style="margin-right:10rpx" size="32rpx"></u-icon>
				地址
			</view>
			<view class="shop-cell_info">
				<u-icon name="clock" custom-style="margin-right:10rpx" size="30rpx"></u-icon>
				营业时间
			</view>
			<view class="shop-cell_info">
				<u-icon name="phone-fill" custom-style="margin-right:10rpx" size="32rpx"></u-icon>
				18814887553
			</view>
			<view class="shop-cell_info">
				<view class="shop-cell_distance">
					<text class="iconfont icon-daohang"></text>
					距我500m
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import { QQMapWX } from "../../common/map/qqmap-wx-jssdk.min.js"
	
	export default {
		name: '',
		props: {
			shopInfo: {
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
		created() {
	
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
	.shop-cell{
		display: flex;
		position: relative;
		padding: 0 10rpx;
		margin: 20rpx 0;
		background-color: #fff;
		border-radius: 10rpx;
		align-items: center;
		&_img{
			width: 200rpx;
			height: 200rpx;
			border-radius: 10rpx;
			margin-right: 10rpx;
			position: relative;
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
		&_title{
			height: 60rpx;
			line-height: 60rpx;
			border-bottom: 1rpx solid $u-border-color;
		}
		&_name{
			float: left;
			font-size: 30rpx;
			overflow: hidden;
		}
		&_info{
			display: flex;
			color: $u-content-color;
			font-size: 24rpx;
			margin: 10rpx 0;
		}
		&_tag-wrapper{
			flex: 1;
			text-align: right;
		}
		&_tag{
			display: inline-block;
			background: #ff3999;
			line-height: 40rpx;
			height: 40rpx;
			width: 60rpx;
			margin-left: 10rpx;
			border-radius: 10rpx;
			text-align: center;
			font-size: 16rpx;
			vertical-align: text-top;
		}
		&_distance{
			height: 40rpx;
			line-height: 40rpx;
			padding: 0 10rpx;
			border-radius: 5rpx;
			background: #edffe7;
			color: #ffcda0;
			font-size: 24rpx;
			.iconfont{
				font-size: 24rpx;
				color: #ffcda0;
			}
			
		}
	}
</style>