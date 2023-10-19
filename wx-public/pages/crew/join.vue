<template>
	<view style="padding: 20rpx;">
		<view class="join-title" v-if="storeName">十三将自助竞技邀请您成为
			<text class="join-store-name">{{storeName}}</text>
		的保洁员</view>
		<view class="join-label">
			绑定手机号
		</view>
		<view>
			<u-input placeholder="请输入手机号" type="number" v-model="phone" font-size="40rpx"></u-input>
		</view>
		<view style="margin: 100rpx 40rpx 40rpx;">
			<u-button type="primary" size="large" @click="onJoinClick(true)">确定</u-button>
		</view>
		<view style="margin: 40rpx;">
			<u-button type="success" size="large" @click="onJoinClick(false)">跳过</u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				phone: '',
				storeName: '',
				roleName: '保洁员',
				id: 0
			}
		},
		onLoad(options) {
			if(options.id){
				this.$api.getToBindRole(options.id).then(res=>{
					this.storeName = res.createBy
					this.id = options.id
				})
			}
		},
		methods: {
			onJoinClick(valid){
				if(valid && !this.phone){
					uni.showModal({
						title: '请先输入手机号',
						showCancel: false
					})
					return
				}
				if(valid){
					this.$api.bindPhoneNumber(this.phone).then(res=>{
						this.bindRole()
					})
				}else{
					this.bindRole()
				}
			},
			bindRole(){
				this.$api.bindRole({id: this.id}).then(()=>{
					this.$store.dispatch("getUserInfo").then(res=>{
						uni.reLaunch({
							url: "/pages/clean/index"
						})
					})
				})
			}
		}
	}
</script>

<style lang="scss">
	.join-title{
		color: $u-main-color;
		font-size: 40rpx;
	}
	.join-store-name{
		color: $u-primary;
		font-weight: bold;
		margin: 0 20rpx;
	}
	.join-label{
		margin: 100rpx 0 20rpx;
		font-size: 40rpx;
		color: $u-content-color;
	}
	.u-button{
		font-size: 40rpx;
	}
</style>