<template>
	<view>
		<view class="top">
			<view class="store-info">
				<view class="store-info_desc">
					<view>当前门店：<text class="store-info_name">{{store.name}}</text></view>
					<view>
						<u-icon name="map" size="24rpx"></u-icon>
						<text>{{store.address}}</text>
					</view>
				</view>
				<view class="store-info_icon">
					<text class="iconfont icon-daohang" @click="toMap"></text>
					<text class="iconfont icon-call" @click="makePhoneCall"></text>
				</view>
			</view>
			<view class="yu-e">
				<view class="yu-e_title">可用余额(元)</view>
				<view class="yu-e_value">{{storeAmount}}</view>
				<!-- <view>
					<view>充值记录</view>
					<view>余额使用明细</view>
				</view> -->
			</view>
		</view>
		<view class="package-list">
			<view><text class="icon">¥</text>充值就送</view>
			<view style="display: flex;flex-wrap: wrap;margin-top: 20rpx;">
				<view v-for="pack in store.storePackageList" :key="pack.id" class="package-item"
					:class="currentPackage.id == pack.id ? 'package-item--selected':''"
					@click="onPackageSelected(pack)">
					<view style="font-size: 28rpx;">充值</view>
					<view style="font-size: 36rpx;">{{pack.payAmount}}</view>
					<view  style="font-size: 28rpx;">赠送{{pack.giftAmont}}</view>
				</view>
			</view>
		</view>
		<view style="padding: 0 20rpx;">
			<rich-text :nodes="chongzhishuoming"></rich-text>
		</view>
		<view style="height: 160rpx;"></view>
		<view class="check-bottom">
			<text class="iconfont" :class="checked ? 'icon-checkbox':'icon-checkbox-blank'" @click="checked = !checked"></text>
			请仔细阅读并同意<text class="xieyi-link" @click="showGuize = true">《充值规则》</text>和<text class="xieyi-link" @click="showXieyi = true">《充值协议》</text>
		</view>
		<view class="bottom">
			<view class="u-success">¥{{currentPackage.payAmount}}</view>
			<view style="width: 200rpx;">
				<u-button type="primary" text="确认并支付" @click="onPayClick"></u-button>
			</view>
		</view>
		
		<u-modal title="充值规则" :show="showGuize" @confirm="showGuize=false">
			<rich-text :nodes="chongzhiguize"></rich-text>
		</u-modal>
		<u-modal title="充值协议" :show="showXieyi" @confirm="showXieyi=false">
			<rich-text :nodes="chongzhixieyi"></rich-text>
		</u-modal>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				store: {},
				currentPackage: {},
				checked: true,
				showGuize: false,
				showXieyi: false,
				storeAmount: 0
			}
		},
		computed: {
			chongzhishuoming(){
				return this.$store.state.chongzhishuoming.replace('{{storeName}}', this.store.name)
			},
			chongzhiguize(){
				return this.$store.state.chongzhiguize
			},
			chongzhixieyi(){
				return this.$store.state.chongzhixieyi
			}
		},
		onLoad(options) {
			if(options.scene){
				// this.$api.getRoomList({id: options.scene}).then(res=>{
				// 	if(res.rows.length == 1){
				// 		this.onAcceptStore(res.rows[0])
				// 	}
				// })
			}else{
				this.getOpenerEventChannel().on('acceptStore', this.onAcceptStore)
			}
		},
		methods: {
			onAcceptStore(store){
				this.store = store
				this.currentPackage = store.storePackageList[0]
				this.$api.getStoreAmount(store.id).then(res=>{
					this.storeAmount = res
				})
			},
			onPackageSelected(pack){
				this.currentPackage = pack
				
			},
			onPayClick(){
				if(!this.checked){
					uni.showToast({
						icon: "none",
						title: "请先勾选同意《充值规则》和《充值协议》！"
					})
					return
				}
				this.$api.buyStorePackage({packId: this.currentPackage.id}).then(res=>{
					const param = res.jsapiResult
					param.package = param.packageValue
					delete param.packageValue
					param.success = ()=>{
						this.$api.buyStorePackageSuccess(res).then((payRes)=>{
							uni.navigateBack()
						})
					}
					param.fail = function(){
						this.$toast('支付失败')
					}
					uni.requestPayment(param)
				})
			},
			makePhoneCall(){
				uni.makePhoneCall({
					phoneNumber: this.store.phone
				})
			},
			toMap(){
				uni.openLocation({
					latitude: new Number(this.store.latitude),
					longitude: new Number(this.store.longitude),
					address: this.store.address
				})
			}
		}
	}
</script>

<style lang="scss">
	page{
		background: $u-bg-color;
		color: $u-main-color;
	}
	.top{
		background: linear-gradient($u-primary, $u-bg-color);
		padding: 20rpx;
	}
	.store-info{
		display: flex;
		color: $u-bright;
		font-size: 24rpx;
		align-items: center;
		justify-content: space-between;
		.u-icon{
			display: inline-block!important;
			.u-icon__icon{
				color: $u-bright!important;
			}
		}
		&_desc{
			line-height: 45rpx;
		}
		&_name{
			font-size: 32rpx;
		}
		.iconfont{
			color: $u-primary;
			background: $u-bright;
			font-size: 32rpx;
			border-radius: 50%;
			padding: 5rpx;
			margin-left: 20rpx;
		}
	}
	.yu-e{
		background: #fff;
		border-radius: 16rpx;
		text-align: center;
		&_title{
			font-size: 24rpx;
			padding: 20rpx 0 0;
		}
		&_value{
			color: $u-primary;	
			font-size: 50rpx;
			padding: 20rpx 0;
		}
	}
	.package-list{
		margin: 20rpx;
		background: #fff;
		border-radius: 16rpx;
		padding: 20rpx;
		.icon{
			display: inline-block;
			background: $u-primary;
			color: $u-bright;
			height: 40rpx;
			width: 40rpx;
			border-radius: 50%;
			margin-right: 10rpx;
			text-align: center;
		}
	}
	.package-item{
		box-sizing: border-box;
		text-align: center;
		border-radius: 16rpx;
		background: $u-primary-light;
		color: $u-bright;
		width: 210rpx;
		height: 210rpx;
		margin-right: 20rpx;
		&--selected{
			background: $u-primary;
		}
		view{
			height: 70rpx;
			line-height: 70rpx;
		}
	}
	.check-bottom{
		position: fixed;
		bottom: 120rpx;
		width: 100%;
		padding: 20rpx;
		font-size: 28rpx;
		.iconfont{
			margin-right: 20rpx;
			font-size: 30rpx;
		}
		.icon-checkbox{
			color: $u-success;
		}
	}
	.bottom{
		box-sizing: border-box;
		padding: 0 20rpx;
		position: fixed;
		height: 120rpx;
		background: #fff;
		display: flex;
		width: 100%;
		align-items: center;
		justify-content: space-between;
		bottom: 0;
	}
	.xieyi-link{
		color: #2979ff;
	}
	rich-text{
		max-height: 750rpx;
		overflow: auto;
	}
</style>