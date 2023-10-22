<template>
	<view>
		<view class="search-bar">
			<u-search placeholder="请输入套餐名称" :showAction="false" v-model="searchParam.name" @change="onSearchKeywordInput"></u-search>
			<u-button type="primary" @click="onAddClick">新增</u-button>
		</view>
		<view class="card-list">
			<view class="card" v-for="price in priceList" :key="price.id">
				<view class="card__content">
					<view class="card__content__right">
						<view class="card__content__head">
							<view class="card-title">
								套餐名称: {{price.name}}
							</view>
						</view>
						<view class="card__content__body">
							<view>套餐时长: {{price.minutes}} 分钟</view>
							<view>套餐价格: {{price.price}} 元</view>
						</view>
					</view>
				</view>
				<view class="card__op-list">
					<u-button type="primary" @click="onEditClick(price)">编辑</u-button>
					<u-button plain @click="onDeleteClick(price)">删除</u-button>
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
					name: '',
					pageSize: 100,
					pageNum: 1,
					roomId: 0
				},
				priceList: []
			}
		},
		onLoad(options) {
			this.roomId = options.id
			this.refresh()
		},
		onPullDownRefresh() {
			this.refresh()
		},
		methods: {
			onAddClick(){
				uni.navigateTo({
					url: '/pages/price/room-charge/add?roomId=' + this.roomId,
					events: {
						refresh: this.refresh
					}
				})
			},
			onEditClick(price){
				uni.navigateTo({
					url: '/pages/price/room-charge/add?id=' + price.id,
					events: {
						refresh: this.refresh
					}
				})
			},
			onDeleteClick(price){
				uni.showModal({
					title: '确认删除?',
					content: '是否删除' + price.name + '?',
					success: res=>{
						if(res.confirm){
							this.$api.deleteRoomChargePrice(price.id).then(this.refresh)
						}
					}
				})
			},
			onSearchKeywordInput(){
				this.$u.debounce(this.refresh, 400)
			},
			refresh(){
				this.$api.getRoomChargePriceList(this.searchParam).then(res=>{
					this.priceList = res.rows
					uni.stopPullDownRefresh()
				})
			}
		}
	}
</script>

<style lang="scss">
</style>