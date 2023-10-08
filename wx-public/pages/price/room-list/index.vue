<template>
	<view>

		<view>
			<view class="top-shop-select">当前门店：
				<uo-select :value="storeId" :options="storeList" valueField="id" @change="selectStore"></uo-select>
			</view>
		</view>
		
		<view class="card-list">
			<u-cell v-for="(room, index) in roomList" :key="room.id" icon="coupon" :title="room.name" isLink :url="'room?id='+room.id+'&name='+room.name" :border="false"></u-cell>
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
</style>
