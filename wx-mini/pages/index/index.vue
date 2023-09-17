<template>
	<view>
		<ad-swiper></ad-swiper>
		<view class="u-p-h-20">
			<view class="menu">
				<navigator class="menu-item" url="../receipt-consume/index">
					<view class="menu-item_icon">
						<text class="iconfont icon-meituan"></text>
					</view>
					<view class="menu-item_title">美团验券</view>
				</navigator>
				<navigator class="menu-item" url="../recharge/shop-list/index">
					<view class="menu-item_icon">
						<text class="iconfont icon-huiyuan"></text>
					</view>
					<view class="menu-item_title">会员充值</view> 
				</navigator>
			</view>
			<view class="search-bar">
				<u-input prefix-icon="search" placeholder="搜索门店" v-model="queryParam.keyword" @input="onKeywordInput"></u-input>
				<view class="search-bar_more" @click="onToShopList">
					更多门店
				</view>
				<u-icon name="arrow-right" size="26rpx" @click="onToShopList"></u-icon>
			</view>
			<view>
				<shop-cell v-for="store in storeList" :key="store.id" :shopInfo="store"></shop-cell>
			</view>
		</view>
		<tool-bar :hidden="hiddenToolBar"></tool-bar>
		<require-authorize></require-authorize>
		<u-modal :show="showTongzhi" title="公告" @confirm="tongzhiReaded = true" confirmText="我知道了">
			<rich-text :nodes="tongzhi"></rich-text>
		</u-modal>
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
				total: 0,
				
				tongzhiReaded: false
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
			},
			tongzhi(){
				const value = this.$store.state.shouyetongzhi
				return value
			},
			showTongzhi(){
				return this.tongzhi && !this.tongzhiReaded
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
			},
			onToShopList(){
				uni.navigateTo({
					url: '/pages/shop/shop-list'
				})
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: $u-bg-color;
	}
	.menu{
		display: flex;
		justify-content: space-between;
		&-item{
			display: flex;
			width: 340rpx;
			align-items: center;
			background: #fff;
			border-radius: 20rpx;
			margin-top: 20rpx;
			&_icon{
				margin: 20rpx;
				height: 100rpx;
				width: 100rpx;
				text-align: center;
				line-height: 100rpx;
				background: $u-primary-light;
				border-radius: 10rpx;
				.iconfont{
					font-size: 60rpx;
					color: $u-bright;
				}
			}
			&_title{
				flex: 1;
				color: $u-content-color;
			}
		}
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
	rich-text{
		max-height: 750rpx;
		overflow: auto;
	}
</style>
