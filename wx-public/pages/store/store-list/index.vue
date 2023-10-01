<template>
	<view>
		<view class="search-bar">
			<u-search placeholder="请输入门店名称" :showAction="false" v-model="searchParam.keyword" @change="onSearchKeywordInput"></u-search>
		</view>
		<view class="card-list">
			<view class="card" v-for="(store,index) in storeList" :key="store.id">
				<view class="card__content">
					<uo-image :src="store.logo"></uo-image>
					<view class="card__content__right">
						<view class="card-title">门店名称：{{store.name}}</view>
						<view class="card-more">
							查看详情
							<u-icon name="arrow-right"></u-icon>
						</view>
						<view class="card__content__body">
							<!-- <view>{{store}}</view> -->
							<view>门店地址：{{store.address}}</view>
							<view>联系电话：{{store.phone}}</view>
							<view>营业时间：{{store.startTime}}~{{store.stopTime}}</view>
						</view>
					</view>
				</view>
				<view class="card__op-list">
					<u-button plain>非保洁时间段设置</u-button>
					<!-- <u-button plain>商家相册</u-button> -->
					<u-button type="primary" @click="onEditClick(store,index)">编辑</u-button>
				</view>
				<edit ref="editDialog"></edit>
			</view>
			<!-- <view class="card">
				<view class="card__content">
					<uo-image src="https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png"></uo-image>
					<view class="card__content__right">
						<view class="card__content__head">
							<view class="card-title">店铺名称</view>
							<view class="card-more">
								查看详情
								<u-icon name="arrow-right"></u-icon>
							</view>
						</view>
						<view class="card__content__body">
							<view>门店编号</view>
							<view>门店地址</view>
							<view>门店电话</view>
						</view>
					</view>
				</view>
				<view class="card__op-list">
					<u-button plain>非保洁时间段设置</u-button>
					<u-button plain>商家相册</u-button>
					<u-button type="primary" @click="onEditClick()">编辑</u-button>
				</view>
			</view> -->
		</view>
	</view>
</template>

<script>
	import edit from '@/pages/store/store-edit/index.vue'
	export default {
		data() {
			return {
				searchParam: {
					keyword: '',
					pageSize: 20,
					pageNum: 1
				},
				storeList: [],
				isEdit:false
			}
		},
		components:{
			edit
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
			onEditClick(row,index){
				console.log(this.$refs.editDialog)
				// uni.navigateTo({
				// 	url: '/pages/store/store-edit/index?',
				// 	events: {
				// 		refresh: this.refresh
				// 	}
				// })
				//this.isEdit=true
				this.$refs.editDialog[index].switchShow(true, row)
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
				this.$api.getStoreList(this.searchParam).then(res=>{
					if(nextPage){
						this.storeList.push(...res.rows)
					}else{
						this.storeList = res.rows
					}
					uni.stopPullDownRefresh()
				})
			}
		}
	}
</script>

<style lang="scss">
	
</style>