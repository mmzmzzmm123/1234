<template>
	<view>
		<image class="promotion" mode="aspectFill" v-for="item in list" :key="item.id" :src="item.logo" @click="toStore(item.storeId)"></image>
		<require-authorize></require-authorize>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				queryParam: {
					pageSize: 20,
					pageNum: 1
				},
				list: []
			}
		},
		onLoad() {
			this.refresh()
		},
		onPullDownRefresh() {
			this.refresh()
		},
		methods: {
			refresh(nextPage){
				if(nextPage){
					this.queryParam.pageNum++
				}else{
					this.queryParam.pageNum = 1
				}
				this.$api.getPromotionList(this.queryParam).then(res=>{
					if(nextPage){
						this.list.push(...res.rows)
					}else{
						this.list = res.rows
					}
					uni.stopPullDownRefresh()
				})
			},
			toStore(storeId){
				uni.navigateTo({
					url: '/pages/shop/index?scene=' + storeId
				})
			}
		}
	}
</script>

<style lang="scss">
	.promotion{
		height: 284rpx;
		width: 710rpx;
		margin: 20rpx;
		border-radius: 20rpx;
	}
</style>