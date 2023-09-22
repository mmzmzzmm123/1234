<template>
	<view class="room-cell-wrapper">
		<view class="room-cell">
			<view class="room-cell_img">
				<u-image mode="aspectFill" width="200rpx" radius="10rpx" height="200rpx" :src="roomInfo.logo" @click="onPreviewImage"></u-image>
				<view class="room-cell_status">{{roomInfo.status}}</view>
				<view class="room-cell_img_count">
					<u-icon name="photo-fill" color="#fff" size="30rpx"></u-icon>{{roomInfo.logoList.length}}
				</view>
			</view>
			<view style="flex: 1;">
				<view class="room-cell_name">{{roomInfo.name}}</view>
				<block v-if="roomInfo.roomPayType == 1">
					<view v-for="price in roomInfo.packageList" :key="price.id" class="room-cell_package">
						<text>{{price.name}}</text>
						<text style="float: right;">{{price.price}}元/场</text>
					</view>
				</block>
				<block v-else>
					<view v-for="price in roomInfo.priceList" :key="price.id" class="room-cell_package">
						<text>时段：{{price.startTime + ':00-' + (price.stopTime==24?0:price.stopTime+1) +':00'}}</text>
						<text style="float: right;">{{price.price}}元/小时</text>
					</view>
				</block>
				<view class="label-list">
					<view v-for="label in roomInfo.labelList" :key="label" class="label-list_item">{{label}}</view>
				</view>
			</view>
		</view>
		<view style="margin-top: 20rpx;">
			<hour-status-bar :hours="hourList"></hour-status-bar>
			<view style="display: flex;align-items: center;margin-top: 20rpx;justify-content: space-between;">
				<hour-status-legend></hour-status-legend>
				<view class="order-btn" @click="onToAddOrder">立即预定</view>
			</view>
		</view>
	</view>
</template>

<script>
	import HourStatusLegend from "../hour-status-bar/hour-status-legend.vue"
	export default {
		name: 'room-cell',
		components: {HourStatusLegend},
		props: {
			roomInfo: {
				type: Object,
				default: ()=>{
					return {}
				}
			}
		},
		data() {
			return {
				
			}
		},
		computed:{
			hourList(){
				if(this.roomInfo && this.roomInfo.period){
					const period = this.roomInfo.period
					const now = new Date()
					const hour = now.getHours()
					const hourList = []
					for(let i = hour; i < 24; i++){
						hourList.push({name: i, status: period.canNotUseList.indexOf(i) >= 0})
					}
					if(hourList.length < 24){
						hourList.push({name: '次日', status: period.canNotUseList2.indexOf(0) >= 0})
					}
					for(let i = 1; i < hour; i++){
						hourList.push({name: i, status: period.canNotUseList2.indexOf(i) >= 0})
					}
					return hourList
				}
				return []
			}
		},
		created() {
	
		},
		methods: {
			onPreviewImage(){
				uni.previewImage({
					urls: this.roomInfo.logoList
				})
			},
			onToAddOrder(){
				uni.navigateTo({
					url: "/pages/order/add/index",
					success: res=>{
						res.eventChannel.emit('acceptRoom', this.roomInfo)
					}
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