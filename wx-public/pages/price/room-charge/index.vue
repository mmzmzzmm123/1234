<template>
	<view>
		<view class="search-bar">
			<u-search placeholder="请输入包厢名称" :showAction="false" v-model="searchParam.keyword" @change="onSearchKeywordInput"></u-search>
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
				storeList: []
			}
		},
		onLoad() {
			this.refresh()
		},
		onPullDownRefresh() {
			this.refresh()
		},
		onReachBottom() {
			this.refresh(true)
		},
		methods: {
			onEditClick(){
				uni.navigateTo({
					url: '/pages/store/store-edit/index?',
					events: {
						refresh: this.refresh
					}
				})
			},
			onSearchKeywordInput(){
				this.$u.debounce(this.refresh, 400)
			},
			refresh(nextPage){
				console.log(this.searchParam.keyword)
				if(nextPage){
					this.searchParam.pageNum++
				}else{
					this.searchParam.pageNum = 1
				}
				// this.$api.getStoreList(this.searchParam).then(res=>{
				// 	if(nextPage){
				// 		this.storeList.push(...res.rows)
				// 	}else{
				// 		this.storeList = res.rows
				// 	}
				// 	uni.stopPullDownRefresh()
				// })
			}
		}
	}
</script>

<style lang="scss">
	
</style>