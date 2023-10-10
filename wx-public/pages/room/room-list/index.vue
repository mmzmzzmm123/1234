<template>
	<view>
		<view class="top-shop-select">
			<text>所属门店：</text>
			<uo-select :value="searchParam.storeId" :options="storeList" valueField="id" @change="onStoreChange">
			</uo-select>
		</view>
		<view class="filter-bar">
			<uo-select placeholder="包厢状态"></uo-select>
			<uo-select placeholder="使用状态" v-model="searchParam.status" :options="roomStatus" clearable></uo-select>
			<u-button plain type="primary" @click="onEditRoomClick()">添加包厢</u-button>
		</view>
		<view style="padding: 0 10rpx 10rpx;">
			<u-search placeholder="请输入包厢名称" :showAction="false" v-model="searchParam.name"
				@change="onSearchKeywordInput"></u-search>
		</view>
		<view class="card-list">
			<view class="card" v-for="room in roomList" :key="room.id">
				<view class="card__content">
					<uo-image :src="room.logo"></uo-image>
					<view class="card__content__right">
						<view class="card__content__head">
							<view class="card-title">{{room.name}}</view>
							<view class="card-more">
								查看详情
								<u-icon name="arrow-right"></u-icon>
							</view>
						</view>
						<view class="card__content__body">
							<view>包厢模式：</view>
							<view>绑定门店：{{storeList.find(x=>x.id == room.storeId).name}}</view>
							<view>状态：{{roomStatus.find(x=>x.value == room.status).name}}</view>
						</view>
					</view>
				</view>
				<view class="card__op-list">
					<u-button plain @click="onMoreClick(room)">更多</u-button>
					<u-button plain>解绑设备</u-button>
					<!-- <u-button plain>商家相册</u-button> -->
					<u-button type="primary" @click="onEditRoomClick(room.id)">编辑</u-button>
				</view>
			</view>
		</view>
		<u-popup :show="showMore" @close="showMore = false">
			<view class="more-btn" @click="onOpenStoreClick">开房间大门</view>
			<view class="more-btn" @click="onOpenRoomClick">开包厢大门</view>
			<view class="more-btn" @click="onCloseRoomClick">关包厢大门</view>
			<!-- <view class="more-btn" @click="showMore = false">开包厢门</view> -->
			<view class="more-btn" @click="onOrderClick">立即下订单</view>
			<view class="more-btn" @click="onDeleteRoomClick">删除包厢</view>
			<view class="more-btn more-btn--cancel" @click="showMore = false">取消</view>
		</u-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				searchParam: {
					name: '',
					status: null,
					pageSize: 1000,
					pageNum: 1,
					storeId: null
				},
				roomList: [],
				showMore: false,
				currentRoom: null
			}
		},
		computed: {
			roomStatus() {
				return this.$store.state.dicts.room_status
			},
			storeList() {
				return this.$store.state.storeList
			}
		},
		onLoad() {
			this.searchParam.storeId = this.$store.state.currentStore.id
			this.$store.dispatch("loadDict", 'room_status')
			this.refresh()
		},
		onPullDownRefresh() {
			this.refresh()
		},
		// onReachBottom() {
		// 	this.refresh(true)
		// },
		methods: {
			onStoreChange(store) {
				this.searchParam.storeId = store.id
				this.$store.commit("$uStore", {
					name: 'currentStore',
					value: store
				})
			},
			onSearchKeywordInput() {
				this.$u.debounce(this.refresh, 400)
			},
			refresh(nextPage) {
				if (nextPage) {
					this.searchParam.pageNum++
				} else {
					this.searchParam.pageNum = 1
				}
				this.$api.getRoomList(this.searchParam).then(res => {
					console.log(res)
					if (nextPage) {
						this.roomList.push(...res.rows)
					} else {
						this.roomList = res.rows
					}
					uni.stopPullDownRefresh()
				})
			},
			onEditRoomClick(id) {
				const url = id ? "/pages/room/room-edit/index?id=" + id : "/pages/room/room-edit/index"
				uni.navigateTo({
					url,
					events: {
						refresh: this.refresh
					}
				})
			},
			onMoreClick(room) {
				this.currentRoom = room
				this.showMore = true
			},
			onOpenStoreClick() {
				this.showMore = false
				uni.showModal({
					content: "是否打开大门",
					success: res => {
						if (res.confirm) {
							this.$api.openStore(this.currentRoom.storeId).then(() => {
								this.$u.toast('开门指令已发送成功')
							})
						}
					}
				})
			},
			onOpenRoomClick() {
				this.showMore = false
				uni.showModal({
					content: "是否打开包厢" + this.currentRoom.name,
					success: res => {
						if (res.confirm) {
							var param = {
								roomId: this.currentRoom.id,
								equipType: 'door,light,aircondition'
							}
							this.$api.openRoom(param).then(() => {
								this.$u.toast('打开包厢指令已发送成功')
							})
							// Promise.all(this.$api.openStore(param1),this.$api.openStore(param2)).then(()=>{
							// 	this.$u.toast('打开包厢指令已发送成功')
							// })
						}
					}
				})
			},
			onCloseRoomClick() {
				this.showMore = false
				uni.showModal({
					content: "是否关闭包厢" + this.currentRoom.name,
					success: res => {
						if (res.confirm) {
							var param = {
								roomId: this.currentRoom.id,
								equipType: 'door,light,aircondition'
							}
							this.$api.closeRoom(param).then(() => {
								this.$u.toast('打开包厢指令已发送成功')
							})
						}
					}
				})
			},
			onDeleteRoomClick() {
				this.showMore = false
				uni.showModal({
					content: "是否删除包厢" + this.currentRoom.name,
					success: res => {
						if (res.confirm) {
							this.$api.deleteRoom(this.currentRoom.id).then(() => {
								this.refresh()
							})
						}
					}
				})
			},
			onOrderClick() {
				const url = "/pages/room/order/index?roomId=" + this.currentRoom.id;
				uni.navigateTo({
					url,
					events: {
						refresh: this.refresh
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	.filter-bar {
		display: flex;
		align-items: center;

		.uo-select {
			display: flex;
			padding: 0 10rpx;
			align-items: center;
			background-color: $u-primary-bg-color;
			border-radius: 10rpx;
			margin: 10rpx;
			box-sizing: border-box;
			height: 60rpx;
			flex: 1;

			/deep/ .uo-select__content {
				width: 100%;
				justify-content: space-between;
			}
		}

		.u-button {
			height: 60rpx;
			margin: 10rpx;
			width: 6em;
		}
	}

	.more-btn {
		padding: 30rpx;
		border-bottom: 1rpx solid $u-border-color;
		color: $u-main-color;
		text-align: center;

		&--cancel {
			border-top: 20rpx solid $u-border-color;
			background: $u-bg-color;
		}
	}
</style>
