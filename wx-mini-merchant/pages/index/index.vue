<template>
	<view>
		<store-bar></store-bar>
		<view class="card-list">
			<view class="card" v-for="room in roomList" :key="room.id">
				<view class="room-header">
					<text class="room-title">{{room.name}}</text>
					<view class="room-status" :class="statusClass[room.status]">{{statusDesc[room.status]}}</view>
					<!-- <u-icon name="arrow-right-double"></u-icon> -->
				</view>
				<view class="room-content">
					<view class="room-desc">
						<view class="room-total">
							<view>累计时长：{{room.sumHour}}小时</view>
							<view>累计消费：{{room.sumAmount}}元</view>
						</view>
						<view class="room-order">
							<view>
								{{room.status == 3 ? '订单结束时间：' : '上一订单结束：'}}
								<text>{{room.orderEndTime ? room.orderEndTime : '暂无人预定'}}</text>
							</view>
							<view>下一订单开始：{{room.nextOrderStart ? room.nextOrderStart : '暂无人预定'}}</view>
						</view>
					</view>
					<view class="room-btn-list">
						<u-button plain type="primary" @click="onOpenRoom(room.room.id, 'door', '开门')">开门</u-button>
						<u-button plain type="primary" @click="onOpenRoom(room.room.id, 'light,aircondition', '开启电源')">开启电源</u-button>
					</view>
				</view>
			</view>
			
			<!-- <view class="card">
				<view class="room-header">
					<view class="room-title">房间名称</view>
					<text class="room-status room-status--empty">空闲中</text>
				</view>
				<view class="room-content">
					<view class="room-desc">
						<view class="room-total">
							<view>累计时长：xxxx小时</view>
							<view>累计消费：xxxx元</view>
						</view>
						<view class="room-order">
							<view>上一订单结束：yyyy-MM-dd hh:mm</view>
							<view>下一订单开始：暂无人预定</view>
						</view>
					</view>
					<view class="room-btn-list">
						<u-button plain type="primary">开门</u-button>
						<u-button plain type="primary">开启电源</u-button>
					</view>
				</view>
			</view> -->
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				roomList: [],
				statusDesc: ['空闲中', '不可用', '清洁中', '使用中'],
				statusClass: ['room-status--empty', 'room-status--disabled', '', '']
			}
		},
		computed: {
			currentStoreId(){
				return this.$store.state.currentStore.id
			}
		},
		watch: {
			currentStoreId: function(){
				this.refresh()
			}
		},
		onLoad() {
			this.refresh()
		},
		onPullDownRefresh() {
			this.refresh()
		},
		methods: {
			refresh(){
				if(this.currentStoreId){
					this.$api.getRoomList({storeId: this.currentStoreId}).then(res=>{
						this.roomList = res.rows
					})
				}
			},
			onOpenRoom(roomId, equipType, tips){
				this.$api.openRoom({roomId, equipType}).then(()=>{
					this.$u.toast('已发送' + tips + '请求')
				})
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: $u-bg-color;
	}
	.room-header{
		display: flex;
		align-items: center;
		margin: 10rpx 0 30rpx;
	}
	.room-title{
		font: 34rpx;
		font-weight: bold;
		margin-right: 20rpx;
	}
	.room-content{
		display: flex;
		align-items: center;
	}
	.room-desc{
		flex: 1;
		font-size: 24rpx;
		line-height: 50rpx;
	}
	.room-order{
		color: $u-tips-color;
		margin-top: 30rpx;
	}
	.room-status{
		display: inline-block;
		font-size: 24rpx;
		border: 1rpx solid $u-primary;
		color: $u-primary;
		padding: 0 10rpx;
		border-radius: 8rpx;
		&--empty{
			border-color: $u-success;
			color: $u-success;
		}
		&--disabled{
			border-color: $u-tips-color;
			color: $u-tips-color;
		}
	}
	.room-btn-list{
		width: 180rpx;
		.u-button{
			margin-bottom: 10rpx;
			height: 70rpx!important;
			font-size: 28rpx!important;
		}
	}
</style>
