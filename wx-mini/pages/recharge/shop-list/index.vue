<template>
	<view>
		<view class="u-p-h-20">
			<view class="search-bar">
				<u-input prefix-icon="search" placeholder="搜索门店" v-model="queryParam.keyword" @input="onKeywordInput"></u-input>
			</view>
			<view>
				<!-- <shop-cell v-for="store in storeList" :key="store.id" :shopInfo="store"></shop-cell> -->
				<view v-for="store in storeList" :key="store.id" class="store" @click="toRecharge(store)">
					<view class="store_info">
						<view class="store_img">
							<u-image mode="aspectFill" width="130rpx" height="130rpx" radius="10rpx" :src="store.logo" @click="onPreviewImage"></u-image>
						</view>
						<view style="flex: 1;">
							<view class="store_title">
								<view class="store_name">{{store.name}}</view>
								<view class="store_tag-wrapper">
									<view class="store_tag">棋牌</view>
									<!-- <view class="shop-cell_tag">台球</view> -->
								</view>
							</view>
							<view class="store_info_desc">
								<u-icon name="map" custom-style="margin-right:10rpx" size="32rpx"></u-icon>
								{{store.address}}
							</view>
							<view class="store_info_desc">
								<u-icon name="clock" custom-style="margin-right:10rpx" size="30rpx"></u-icon>
								营业时间: {{store.startTime + ' - ' + store.stopTime}}
							</view>
						</view>
					</view>
					<view class="store_package-list">
						<view v-for="pack in store.storePackageList" :key="pack.id" class="store-package">
							充¥{{pack.payAmount}}送¥{{pack.giftAmont}}
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
			toRecharge(store){
				uni.navigateTo({
					url: '/pages/recharge/shop/index',
					success: res=>{
						res.eventChannel.emit('acceptStore', store)
					}
				})
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
	.store{
		padding: 0 20rpx 20rpx;
		margin: 20rpx 0;
		background-color: #fff;
		border-radius: 10rpx;
		&_info{
			display: flex;
			align-items: center;
		}
		&_img{
			width: 130rpx;
			height: 130rpx;
			border-radius: 10rpx;
			margin-right: 10rpx;
			position: relative;
		}
		&_title{
			height: 60rpx;
			line-height: 60rpx;
		}
		&_name{
			float: left;
			font-size: 30rpx;
			overflow: hidden;
		}
		&_info_desc{
			display: flex;
			color: $u-content-color;
			font-size: 24rpx;
			margin: 10rpx 0;
		}
		&_tag-wrapper{
			flex: 1;
			text-align: right;
		}
		&_tag{
			display: inline-block;
			background: $u-primary;
			color: $u-bright;
			line-height: 40rpx;
			height: 40rpx;
			width: 60rpx;
			margin-left: 10rpx;
			border-radius: 10rpx;
			text-align: center;
			font-size: 16rpx;
			vertical-align: text-top;
			box-shadow: 2rpx 2rpx 6rpx #000;
		}
	}
	.store_package-list{
		display: flex;
		flex-wrap: wrap;
	}
	.store-package{
		margin: 10rpx 20rpx 0 0;
		background: $u-primary-light;
		padding: 5rpx 10rpx;
		border-radius: 8rpx;
		color: $u-bright;
		font-size: 24rpx;
	}
</style>
