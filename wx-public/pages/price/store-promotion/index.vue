<template>
	<view>
		<view class="search-bar">
			<u-search placeholder="请输入优惠券名称" :showAction="false" v-model="searchParam.keyword"
				@change="onSearchKeywordInput"></u-search>
			<u-button type="primary" @click="add">新增优惠券</u-button>
		</view>
		<view class="card-list">
			<view class="card" v-for="(room, index) in packList" :key="room.id">
				<view class="card__content">
					<!-- <uo-image src="https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png"></uo-image> -->
					<!-- {{room.logo}} -->
					<view class="card__content__right">
						<view class="card__content__head">
							<navigator class="card-title" :url="'room?id='+room.id+'&name='+room.name">
								<view class="card-title">优惠券名称: {{room.name}}</view>
							</navigator>
						</view>
						<view class="card__content__body">
							<view>抵扣时长: {{room.maxMinute}} 分钟</view>
							<view>价格: {{room.standardPrice}} 元</view>
							<view>周内可用日期: {{room.weekDays}}</view>
							<view v-if="room.validType==2">有效期: {{room.startDate}}-{{room.endDate}} </view>
							<view v-if="room.validType==1">有效期: 子购买日起{{room.validDays}}天 </view>
							<view>适用门店: {{room.storeName}} </view>
						</view>
						<view class="card__op-list">
							<u-button type="primary" @click="edit(room)">编辑</u-button>
							<u-button plain @click="del(room)">删除</u-button>
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
				storeId: this.$store.state.currentStore.id,
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
		// onReachBottom() {
		// 	this.refresh(true)
		// },
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
				this.$api.getStorePromotionList(this.searchParam).then(res => {
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
					url: "edit",
					events: {
						refresh: this.refresh
					}
				})
			},
			edit(row) {
				console.log(row.id);
				uni.navigateTo({
					url: 'edit?id=' + row.id,
					events: {
						refresh: this.refresh
					}
				})
			},
			del(row) {
				uni.showModal({
					title: "确认删除？",
					content: "确认删除？",
					success: res => {
						if (res.confirm) {
							this.$api.delStorePromotion(row.id).then(() => {
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
