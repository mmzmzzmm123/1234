<template>
	<view>

		<view>
			<view class="roomlist-bar">当前门店：
				<uo-select :value="storeId" :options="storeList" valueField="id" @change="selectStore"></uo-select>
			</view>
		</view>

		<view class="card-list">
			<view class="card" v-for="(room, index) in roomList" :key="room.id">
				<view class="card__content">
					<!-- <uo-image src="https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png"></uo-image> -->
					<view class="card__content__right">
						<view class="card__content__head">
							<navigator class="card-title" :url="'room?id='+room.id+'&name='+room.name">
								<view class="card-title">{{room.name}}</view>
							</navigator>
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
				searchParam: {
					keyword: '',
					pageSize: 20,
					pageNum: 1
				},
				showStoreList: false,
				storeList: [],
				values: [], //这个是数组
				roomList: [],
			}
		},
		computed: {
			storeId() {
				return this.$store.state.currentStore.id
			}
		},
		onLoad() {
			this.getStoreList()
			// this.refresh()
		},
		onPullDownRefresh() {
			this.refresh()
		},
		onReachBottom() {
			this.refresh(true)
		},
		methods: {
			onEditClick() {
				uni.navigateTo({
					url: '/pages/store/store-edit/index?',
					events: {
						refresh: this.refresh
					}
				})
			},
			onSearchKeywordInput() {
				this.$u.debounce(this.refresh, 400)
			},
			refresh(nextPage) {
				console.log(this.searchParam.keyword)
				if (nextPage) {
					this.searchParam.pageNum++
				} else {
					this.searchParam.pageNum = 1
				}

				this.searchParam.storeId = this.storeId;
				this.getStoreRoom(this.searchParam, nextPage)

			},
			getStoreList() {
				this.$api.getStoreList().then(res => {
					this.storeList = res.rows
					this.searchParam.storeId = this.storeId;
					this.getStoreRoom(this.searchParam, false)
				})
			},
			selectStore(event) {
				this.$store.commit("$uStore", {
					name: 'currentStore',
					value: event
				})
				this.searchParam.storeId = this.storeId;
				this.getStoreRoom(this.searchParam, false);

			},
			getStoreRoom(param, nextPage) {
				this.$api.getStroeRoomList(param).then(resp => {
					if (nextPage) {
						this.roomList.push(...resp.rows)
					} else {
						this.roomList = resp.rows
					}
					uni.stopPullDownRefresh()
				})
				this.showStoreList = false;
			},
			showStores() {
				this.showStoreList = true;
			},
			okChange() {
				debugger
				this.refresh()
			},
			cancellChange() {
				this.showStoreList = false;
			}
		}
	}
</script>

<style lang="scss">
	.picker-view {
		width: 750rpx;
		height: 600rpx;
		margin-top: 20rpx;
	}

	.item {
		line-height: 100rpx;
		text-align: center;
	}

	.roomlist-bar {
		display: flex;

	}
</style>
