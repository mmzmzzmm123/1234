<template>
	<view>
		<view class="top">
			<text :class="option.icon"></text>
		</view>
		<view class="op-item">
			<u-input placeholder="点击输入卡券兑换码" v-model="receiptCode" type="number">
				<u-icon name="scan" slot="suffix" @click="onScanClick"></u-icon>
			</u-input>
		</view>
		<view class="op-item">
			<u-button type="primary" text="兑换" @click="onConsumeClick"></u-button>
		</view>
	</view>
</template>

<script>
	const PageOptions = {
		meituan: {
			title: '美团验券',
			icon: 'iconfont icon-meituan'
		},
		douyin: {
			title: '抖音验券'
		}
	}
	export default {
		data() {
			return {
				option: PageOptions.meituan,
				receiptCode: ''
			}
		},
		onLoad(options) {
			if(options.meituan){
				this.option = PageOptions.meituan
			}else if(options.douyin){
				this.option = PageOptions.douyin
			}
			uni.setNavigationBarTitle({
				title: this.option.title
			})
		},
		methods: {
			onScanClick(){
				uni.scanCode({
					success: res=>{
						this.receiptCode = res.result
					}
				})
			},
			onConsumeClick(){
				this.$api.meiTuanConsume(this.receiptCode).then(res=>{
					uni.showModal({
						title: "兑换成功",
						content: "兑换成功是否前往使用",
						success: res=>{
							if(res.confirm){
								// uni.reLaunch({
								// 	url: ""
								// })
							}
						}
					})
				})
			}
		}
	}
</script>

<style lang="scss">
	.top{
		margin: 100rpx;
		text-align: center;
		.iconfont{
			font-size: 160rpx;
		}
		.icon-meituan{
			color: #ffd100;
			border: 1rpx solid #ffd100;
			padding: 20rpx;
			border-radius: 50%;
		}
	}
	.op-item{
		margin: 30rpx 60rpx;
	}
</style>