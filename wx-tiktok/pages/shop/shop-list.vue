<template>
	<view>
		<view class="u-p-h-20">
			<view class="search-bar">
				<u-input prefix-icon="search" placeholder="搜索门店" v-model="queryParam.keyword" @input="onKeywordInput"></u-input>
				<!-- <view class="search-bar_more">
					更多门店
				</view>
				<u-icon name="arrow-right" size="26rpx"></u-icon> -->
			</view>
			<view>
				<shop-cell v-for="store in storeList" :key="store.id" :shopInfo="store"></shop-cell>
			</view>
		</view>
		<!-- <tool-bar :hidden="hiddenToolBar"></tool-bar> -->
		<!-- <yto-authorize></yto-authorize> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				hiddenToolBar: false,
				queryParam: {
					keyword: '',
					pageNum: 1,
					pageSize: 10,
					lon: 0,
					lat: 0
				},
				storeList: [],
				total: 0
			}
		},
		computed: {
			hasLogin(){
				return this.$store.state.hasLogin
			},
			loginUser(){
				return this.$store.state.loginUser
			},
			canLocation(){
				return this.$store.state.canLocation
			}
		},
		// watch: {
		// 	canLocation(newVal){
		// 		if(newVal){
		// 			this.refresh()
		// 		}
		// 	}
		// },
		onLoad() {
			this.$store.dispatch("getLocation").then(location=>{
				this.refresh()
			})
		},
		onPullDownRefresh() {
			if(this.$store.state.canLocation){
				this.$store.dispatch("getLocation").then(location=>{
					this.refresh()
				})
			}else{
				uni.showModal({
					title: "授权提示",
					content: "未授权小程序获取定位信息，点击确定手动授权",
					success: res=>{
						if(res.confirm){
							uni.openSetting({
								success: setRes=>{
									if(setRes.authSetting['scope.userFuzzyLocation']){
										this.$store.commit("setCanLocation", true)
										this.$store.dispatch("getLocation").then(location=>{
											this.refresh()
										})
									}
								},
								fail(res) {
									this.refresh()
									console.log(res)
								}
							})
						}else{
							this.refresh()
						}
					}
				})
			}
		},
		onReachBottom() {
			if(this.storeList.length < this.total){
				this.refresh(true)
			}
		},
		onPageScroll() {
			this.$u.throttle(()=>{
				this.hiddenToolBar = true
			}, 400, true)
			this.$u.debounce(()=>{
				this.hiddenToolBar = false
			}, 200)
		},
		methods: {
			refresh(nextPage){
				console.log('refreshShopList')
				const location = this.$store.state.location
				if(location){
					this.queryParam.lat = location.latitude
					this.queryParam.lon = location.longitude
				}
				if(nextPage){
					this.queryParam.pageNum++
				}else{
					this.queryParam.pageNum = 1
				}
				this.$api.getStoreList(this.queryParam).then(res=>{
					uni.stopPullDownRefresh()
					if(!location){
						res.rows.forEach(x=>x.meters=null)
					}
					if(nextPage){
						this.storeList.push(...res.rows)
					}else{
						this.storeList = res.rows
					}
					this.total = res.total
				})
			},
			onKeywordInput(keyword){
				this.$u.debounce(this.refresh, 400)
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: $u-bg-color;
	}
	.search-bar{
		display: flex;
		margin-top: 20rpx;
		align-items: center;
		&_more{
			margin-left: 20rpx;
			font-size: 26rpx;
			color: $u-content-color;
		}
	}
</style>
