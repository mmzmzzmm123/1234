<template>
	<view>
		<view class="uni-padding-wrap">
			<view class="uni-title">当前包厢：{{roomName}}</view>
		</view>
		<view>
			<view class="card-list">
				<view class="card">
					<navigator class="card-title" :url="'../room-pack/index?id='+roomId">包厢专用套餐</navigator>
				</view>
				<view class="card">
					<navigator class="card-title" :url="'../room-charge/index?id='+roomId">包厢续费套餐<u-icon name="arrow-right">
						</u-icon>
					</navigator>
				</view>

			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				roomId: 0,
				roomName: "",
			}
		},
		computed: {
			currentStoreId() {
				return this.$store.state.currentStore.id
			}
		},
		watch: {
			currentStoreId: function() {
				this.refresh()
			}
		},
		onLoad(option) {
			this.refresh()
			this.roomId = option.id;
			this.roomName = option.name;
		},
		onPullDownRefresh() {
			this.refresh()
		},
		methods: {
			refresh() {
				if (this.roomId) {
					// this.$api.getStroeRoomList(param).then(resp => {
					// 	if (nextPage) {
					// 		this.roomList.push(...resp)
					// 	} else {
					// 		this.roomList = resp
					// 	}
					// 	uni.stopPullDownRefresh()
					// })
				}
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: $u-bg-color;
	}
</style>
