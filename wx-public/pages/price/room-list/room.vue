<template>
	<view>
		<view class="top-shop-select">
			<view>当前包厢：{{roomName}}</view>
		</view>
		<view>
			<view class="card-list">
				<u-cell isLink title="包厢专用套餐" :url="'../room-pack/index?id='+roomId" :border="false">
					<text class="iconfont icon-dingdan" slot="icon"></text>
				</u-cell>
				<u-cell icon="" isLink title="包厢续费套餐" :url="'../room-charge/index?id='+roomId" :border="false"></u-cell>
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
</style>
