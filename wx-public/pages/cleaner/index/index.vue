<template>
	<view>
		<view class="top">
			<uo-select :options="storeList" value-field="id" mode="top" :value="currentStore.id"
				@change="onStoreChange"></uo-select>
			<view style="width: 140rpx;" v-if="loginUser.userId">
				<u-button size="mini" @click="toManager">切换角色</u-button>
			</view>
		</view>
		<view class="c-bar">
			<view class="c-bar-item" @click="callManager">
				<u-icon name="phone" size="80rpx"></u-icon>
				<view>商家电话</view>
			</view>
			<navigator class="c-bar-item c-bar-item--warning">
				<u-icon name="order" size="80rpx"></u-icon>
				<view>打扫记录</view>
			</navigator>
		</view>
		<view class="c-filter-bar">
			<view class="c-filter-item" :class="queryParam.status == null ? 'c-filter-item--selected':''"
				@click="onFilterClick(null)">全部</view>
			<view v-for="item in roomStatus" class="c-filter-item" :key="item.value"
				:class="queryParam.status == item.value ? 'c-filter-item--selected':''"
				@click="onFilterClick(item.value)">{{item.name}}</view>
		</view>
		<view style="margin: 30rpx 80rpx;">
			<u-button type="primary" size="large" @click="onOpenStoreClick(currentStore.id)">开大门</u-button>
		</view>
		<view class="card-list">
			<view class="card" v-for="(room, index) in roomList" :key="room.id">
				<view class="card__content">
					<!-- <uo-image src="https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png"></uo-image> -->
					<view class="card__content__right">
						<view class="card__content__head">
							<!-- <navigator class="card-title" :url="'room?id='+room.id+'&name='+room.name">
								<view class="card-title">{{room.storeName}}</view>
							</navigator> -->
							<view>包厢名称: {{room.roomName}}</view>
							<view>包厢状态: {{room.roomStatus}}</view>
						</view>
						<!-- <view>剩余打扫时间:</view> -->
						<!-- <view class="card__content__body">
						<view>开始时间: {{room.startTime}} </view>
						<view>结束时间: {{room.endTime}} </view>
					</view>
					<view class="card__content__footer">
						<view>打扫时长: {{room.duration}} 分钟</view>
					</view> -->
						<view class="card__btn-clean-list">
							<u-button size="large" type="success" @click="onStartCleanClick(room)">开始打扫</u-button>
							<u-button size="large" type="success" @click="onFinishCleanClick(room)">打扫完毕</u-button>
						</view>
						<view class="card__btn-room-list">
							<u-button size="large" type="success" @click="onOpenRoomClick(room)">临时开启包厢</u-button>
							<u-button size="large" type="error" @click="onCloseRoomClick(room)">临时关闭包厢</u-button>
						</view>
					</view>
				</view>

			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				queryParam: {
					status: null
				},
				searchParam: {
					name: '',
					startTime: '',
					pageSize: 20,
					pageNum: 1
				},
				orderList: [],
				roomList: [],
			}
		},
		computed: {
			currentStore() {
				return this.$store.state.currentStore
			},
			storeList() {
				return this.$store.state.storeList
			},
			roomStatus() {
				return this.$store.state.dicts.room_status
			},
			loginUser(){
				return this.$store.state.loginUser
			}
		},
		onLoad() {
			this.$store.dispatch("loadDict", 'room_status')
			this.refresh();

		},
		methods: {
			refresh(nextPage) {
				// debugger
				console.log(this.searchParam)
				if (nextPage) {
					this.searchParam.pageNum++
				} else {
					this.searchParam.pageNum = 1
				}
				this.searchParam.storeId = this.$store.state.currentStore.id;
				this.$api.getCleanerRoomList(this.searchParam).then(res => {
					if (nextPage) {
						this.roomList.push(...res.rows)
					} else {
						this.roomList = res.rows
					}
					uni.stopPullDownRefresh()
				})
			},
			getCleanRecordList(){
				// this.searchParam.roomId = this.roomId;
				this.searchParam.storeId = this.$store.state.currentStore.id;
				this.$api.getCleanerCleanRecordList(this.searchParam).then(res => {
					if (nextPage) {
						this.orderList.push(...res.rows)
					} else {
						this.orderList = res.rows
					}
					uni.stopPullDownRefresh()
				})
			},
			onStoreChange(store) {
				this.$store.commit("$uStore", {
					name: 'currentStore',
					value: store
				})
			},
			toManager() {
				uni.navigateTo({
					url: '/pages/index/index'
				})
			},
			callManager() {
				uni.makePhoneCall({
					phoneNumber: this.currentStore.phone
				})
			},
			onFilterClick(roomStatus) {
				this.searchParam.status = roomStatus
				this.refresh()
			},
			onStartCleanClick(room) {
				if (room.roomStatus == "清洁中") {
					this.$api.startCleanRoom(room.roomId).then(res => {
						this.$u.toast('请开包厢进行打扫')
					})
				} else {
					this.$u.toast('房间状态不允许保洁, 请联系商家')
				}
			},
			onFinishCleanClick(room) {
				if (room.roomStatus == "清洁中") {
					this.$api.finishCleanRoom(room.roomId).then(res => {
						this.refresh()
						this.$u.toast('已完成打扫')
					})
				} else {
					this.$u.toast('房间未在清洁中，请先开始打扫')
				}
			},
			onOpenRoomClick(room) {
				if (room.roomStatus == "清洁中") {
					var params = {
						roomId: room.roomId,
						storeId: this.currentStore.id
					}
					this.$api.cleanerOpenRoom(params).then(res => {
						this.$u.toast('包厢已打开')
					})
				} else {
					this.$u.toast('房间未在清洁中，无法打开')
				}
			},
			onCloseRoomClick(room) {
				var params = {
					roomId: room.roomId,
					storeId: this.currentStore.id
				}
				this.$api.cleanerCloseRoom(params).then(res => {
					this.$u.toast('包厢已关闭')
				})
			},
			onOpenStoreClick(storeId){
				var params = {
					roomId: room.roomId,
					storeId: this.currentStore.id
				}
				this.$api.cleanerOpenStore(params).then(res => {
					this.$u.toast('大门已打开')
				})
			}
		}
	}
</script>

<style lang="scss">
	.top {
		display: flex;
		background: $u-primary;
		padding: 20rpx;
		align-items: center;

		.uo-select {
			font-size: 40rpx;
			color: $u-bright;

			/deep/ .u-icon__icon {
				color: $u-bright !important;
			}
		}
	}

	.c-bar {
		display: flex;
		padding-left: 20rpx;
	}

	.c-bar-item {
		flex: 1;
		text-align: center;
		font-size: 40rpx;
		color: $u-bright;
		background: $u-success;
		margin: 20rpx 20rpx 20rpx 0;
		padding: 30rpx 0;
		border-radius: 20rpx;

		/deep/ .u-icon__icon {
			color: $u-bright !important;
		}

		/deep/ .u-icon {
			justify-content: center;
		}

		&--warning {
			background: $u-warning;
		}
	}

	.c-filter-bar {
		display: flex;
		flex-wrap: wrap;
		padding-left: 20rpx;
	}

	.c-filter-item {
		background: $u-bg-color;
		padding: 0 30rpx;
		font-size: 40rpx;
		height: 80rpx;
		line-height: 80rpx;
		border-radius: 40rpx;
		margin: 20rpx 20rpx 0 0;
		color: $u-tips-color;

		&--selected {
			color: $u-primary;
		}
	}

	.card__content__head {
		justify-content: space-between;
		margin-bottom: 30rpx;
	}

	.card__btn-clean-list {
		display: flex;
		margin-bottom: 30rpx;
		.u-button {
			margin: 0 20rpx;
		}
	}
	
	.card__btn-room-list {
		display: flex;
		.u-button {
			margin: 0 20rpx;
		}
	}

	.u-button--large {
		font-size: 40rpx;
	}
</style>
