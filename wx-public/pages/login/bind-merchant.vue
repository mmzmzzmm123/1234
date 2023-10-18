<template>
	<view>
		<view class="navi-bar" :style="{height: statusBarHeight}"></view>
		<view class="navi-bar" style="height: 40px;">十三将</view>
		<view class="login-form">
			<u-form ref="bindForm" :model="bindParam" :rules="rules" label-position="top">
				<view class="login-title">绑定十三将商家账号</view>
				<u-form-item prop="userName">
					<u-input placeholder="请输入用户名" v-model="bindParam.userName"></u-input>
				</u-form-item>
				<u-form-item prop="pwd">
					<u-input placeholder="请输入用户密码" type="password" v-model="bindParam.pwd"></u-input>
				</u-form-item>
				<view style="padding: 60rpx 20rpx 0;">
					<u-button type="primary" @click="onBindClick">绑定</u-button>
				</view>
			</u-form>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				statusBarHeight: uni.getSystemInfoSync().statusBarHeight + 'px',
				bindParam: {
					userName: '',
					pwd: ''
				},
				captchaImage: '',
				rules: {
					userName: {
						required: true,
						message: '请输入用户名',
						trigger: ['blur', 'change']
					},
					pwd: {
						required: true,
						message: '请输入用户密码',
						trigger: ['blur', 'change']
					}
				}
			}
		},
		onLoad(options) {
			
		},
		onReady() {
			this.$refs.bindForm.setRules(this.rules)
		},
		methods: {
			onBindClick(){
				this.$refs.bindForm.validate().then(res=>{
					this.$api.bindMerchant(this.bindParam).then(()=>{
						this.$store.dispatch("getUserInfo").then(()=>{
							uni.reLaunch({
								url: '/pages/index/index'
							})
						})
					})
				}).catch(res=>{
					this.$u.toast('请完善用户名和密码')
				})
			}
		}
	}
</script>

<style lang="scss">
	.navi-bar{
		height: 40px;
		background: $u-primary;
		color: #fff;
		text-align: center;
		line-height: 40px;
	}
	.login-form{
		padding: 200rpx 30rpx;
	}
	.login-title{
		margin-bottom: 40rpx;
		text-align: center;
		color: $u-primary;
		font-size: 40rpx;
		font-weight: bold;
	}
</style>