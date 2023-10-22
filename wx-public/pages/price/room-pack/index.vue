<template>
	<view>
		<view class="search-bar">
			<u-search placeholder="请输入套餐名称" :showAction="false" v-model="searchParam.name"
				@change="onSearchKeywordInput"></u-search>
			<u-button type="primary" @click="add">新增专用套餐</u-button>
		</view>
		<view class="card-list">
			<view class="card" v-for="(room, index) in packList" :key="room.id">
				<view class="card__content">
					<!-- <uo-image src="https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png"></uo-image> -->
					<view class="card__content__right">
						<view class="card__content__head">
							<view class="card-title">
								套餐名称: {{room.name}}
							</view>
						</view>
						<view class="card__content__body">
							<view>套餐时长: {{room.minutes}} 分钟</view>
							<view>套餐价格: {{room.price}} 元</view>
							<view>

							</view>
						</view>
					</view>
				</view>
				<view class="card__op-list">
					<u-button type="primary" @click="edit(room)">编辑</u-button>
					<u-button plain @click="del(room)">删除</u-button>
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
				roomId: 0,
				packList: []
			}
		},
		onLoad(option) {
			this.roomId = option.id;
			this.refresh()
		},
		onPullDownRefresh() {
			this.refresh()
		},
		/* onReachBottom() {
			this.refresh(true)
		}, */
		methods: {
			onSearchKeywordInput() {
				this.$u.debounce(this.refresh, 400)
			},
			refresh(nextPage) {
				// debugger
				console.log(this.searchParam.keyword)
				if (nextPage) {
					this.searchParam.pageNum++
				} else {
					this.searchParam.pageNum = 1
				}
				this.searchParam.roomId = this.roomId;
				this.$api.getRoomPackList(this.searchParam).then(res => {
					if (nextPage) {
						this.packList.push(...res.rows)
					} else {
						this.packList = res.rows
					}
					uni.stopPullDownRefresh()
				})
			},
			add() {
				uni.navigateTo({
					url: "add?roomId=" + this.roomId,
					events: {
						refresh: this.refresh
					}
				})
			},
			edit(row) {
				console.log(row.id);
				uni.navigateTo({
					url: 'add?id=' + row.id,
					events: {
						refresh: this.refresh
					}
				})
			},
			del(row) {

				uni.showModal({
					title: "确认删除？",
					content: "确认删除？",
					success: (res) => {
						if (res.confirm) {
							this.$api.delRoomPack(row.id).then(() => {
								this.refresh();
							})
						}

					}
				})
			},
		}
	}
</script>

<style lang="scss">
</style>
