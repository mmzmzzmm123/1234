<template>
	<view>
		<view class="search-bar">
			<u-search placeholder="请输入套餐名称" :showAction="false" v-model="searchParam.name"
				@change="onSearchKeywordInput"></u-search>
			<view class="uni-btn-v">
				<button type="default" style="color:#ffffff;backgroundColor:#1AAD19;borderColor:#1AAD19"
					hover-class="is-hover" @click="add">新增专用套餐</button>
			</view>
		</view>
		<view class="card-list">
			<view class="card" v-for="(room, index) in packList" :key="room.id">
				<view class="card__content">
					<!-- <uo-image src="https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png"></uo-image> -->
					<view class="card__content__right">
						<view class="card__content__head">
							<navigator class="card-title" :url="'room?id='+room.id+'&name='+room.name">
								<view class="card-title">套餐名称: {{room.name}}</view>
							</navigator>
						</view>
						<view class="card__content__body">
							<view>套餐时长: {{room.minutes}} 分钟</view>
							<view>套餐价格: {{room.price}} 元</view>
							<view>
								<button type="default" hover-class="is-hover" @click="edit(room)">编辑</button>
								<button type="default" hover-class="is-hover" @click="del(room)">删除</button>
							</view>
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
					url: "add?roomId=" + this.roomId
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
				
				this.$api.delRoomPack(row.id).then(() => {
					this.refresh();
				})
				
				return ;
				uni.showModal({
					title: "确认删除？",
					content: "确认删除？",
					success: function(res) {
						if(res.confirm){
							var ids = [];
							ids.push(row.id);
							this.$api.delRoomPack(ids).then(() => {
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
	.is-hover {
		color: rgba(255, 255, 255, 0.6);
		background-color: #179b16;
		border-color: #179b16;
	}
</style>
