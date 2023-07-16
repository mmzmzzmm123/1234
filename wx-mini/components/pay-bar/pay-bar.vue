<template>
	<u-popup :show="value" @close="onPopupClose">
		<view style="text-align: center;">
			<view class="pay-label">支付金额</view>
			<view class="pay-amount">¥{{amount}}</view>
		</view>
		<view class="pay-label">选择支付方式</view>
	    <view>
			<view class="pay-type-option" @click="onOptionClick(1)">
				<u-icon name="weixin-circle-fill" color="#2aae67" size="50rpx" custom-style="margin-right:20rpx"></u-icon>
				<text>微信支付</text>
				<view class="pay-type-option-checkbox">
					<text class="iconfont" :class="payType == 1 ? 'icon-checkbox' : 'icon-checkbox-blank'"></text>
				</view>
			</view>
	        <view class="pay-type-option" @click="onOptionClick(2)">
				<u-icon name="rmb-circle-fill" color="#a771ae" size="50rpx" custom-style="margin-right:20rpx"></u-icon>
				<text>商户余额支付</text>
				<text>(¥{{storeAmount}})</text>
				<view class="pay-type-option-checkbox">
					<text class="iconfont" :class="payType == 2 ? 'icon-checkbox' : 'icon-checkbox-blank'"></text>
				</view>
			</view>
	    </view>
		<view style="margin: 0 40rpx;">
			<u-button type="primary" text="确定支付" @click="onConfirm"></u-button>
		</view>
	</u-popup>
</template>

<script>
	// payType 1微信支付 2商户余额支付
	export default {
		name: 'pay-bar',
		props: {
			amount: {
				type: Number,
				default: 0
			},
			storeId: {
				type: Number,
				default: 0
			},
			value: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				storeAmount: 0,
				payType: 1
			}
		},
		watch: {
			storeId: {
				immediate: true,
				handler(newVal){
					if(newVal){
						this.$api.getStoreAmount(newVal).then(res=>{
							this.storeAmount = res
						})
					}else{
						this.storeAmount = 0
					}
				}
			}
		},
		created() {
	
		},
		methods: {
			onConfirm(){
				const event = {payType: this.payType}
				this.$emit('prepareOrder', event)
				const order = event.order
				const doPay = order.orderId ? this.$api.reOrder : this.$api.addOrder
				doPay(order).then(res=>{
					if(order.payType == 1){
						const param = res.jsapiResult
						param.package = param.packageValue
						delete param.packageValue
						param.success = ()=>{
							this.$api.wxPaySuccess(param).then((payRes)=>{
								this.$emit("success")
							})
						}
						param.fail = function(){
							this.$toast('支付失败')
						}
						uni.requestPayment(param)
					}else{
						this.$emit("success")
					}
				})
			},
			onOptionClick(payType){
				if(payType == 2 && this.storeAmount < this.amount){
					uni.showToast({
						icon: "none",
						title: "商户余额不足"
					})
				}else{
					this.payType = payType
				}
			},
			onPopupClose(){
				this.$emit('input', false)
			}
		}
	}
</script>

<style lang="scss">
	.pay-label{
		font-size: 30rpx;
		color: $u-tips-color;
		margin: 20rpx;
	}
	.pay-amount{
		font-size: 50rpx;
		margin-bottom: 40rpx;
	}
	.pay-type-option{
		display: flex;
		align-items: center;
		margin: 20rpx;
	}
	.pay-type-option-checkbox{
		flex: 1;
		text-align: right;
		.iconfont{
			font-size: 40rpx;
		}
	}
</style>