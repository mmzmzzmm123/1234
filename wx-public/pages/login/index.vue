<template>
	<view>
		<view class="navi-bar" :style="{height: statusBarHeight}"></view>
		<view class="navi-bar" style="height: 40px;">十三将</view>
		<view class="login-form">
			<u-form ref="loginForm" :model="loginParam" :rules="rules" label-position="top">
				<view class="login-title">登录十三将商户管理端</view>
				<u-form-item prop="username">
					<u-input placeholder="请输入用户名" v-model="loginParam.username"></u-input>
				</u-form-item>
				<u-form-item prop="password">
					<u-input placeholder="请输入用户密码" type="password" v-model="loginParam.password"></u-input>
				</u-form-item>
				<u-form-item prop="code">
					<u-input custom-style="margin-right:20rpx" placeholder="请输入验证码" type="number" v-model="loginParam.code"></u-input>
					<u-image height="78rpx" width="208rpx" :src="captchaImage" @click="getCaptchaImage"></u-image>
				</u-form-item>
				<view style="padding: 60rpx 20rpx 0;">
					<u-button type="primary" @click="onBindClick">登录</u-button>
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
				loginParam: {
					username: '',
					password: '',
					code: '',
					uuid: ''
				},
				captchaImage: '',
				rules: {
					username: {
						required: true,
						message: '请输入用户名',
						trigger: ['blur', 'change']
					},
					password: {
						required: true,
						message: '请输入用户密码',
						trigger: ['blur', 'change']
					},
					code: {
						required: true,
						message: '请输入验证码',
						trigger: ['blur', 'change']
					}
				}
			}
		},
		onLoad(options) {
			this.getCaptchaImage()
		},
		onReady() {
			this.$refs.loginForm.setRules(this.rules)
		},
		methods: {
			onBindClick(){
				this.$refs.loginForm.validate().then(res=>{
					this.$store.dispatch("login", this.loginParam).then(()=>{
						uni.reLaunch({
							url: '/pages/index/index'
						})
					}).catch(this.getCaptchaImage)
				}).catch(res=>{
					this.$u.toast('请完善用户密码')
				})
			},
			getCaptchaImage(){
				this.$api.getCaptchaImage().then(res=>{
					this.loginParam.uuid = res.uuid
					this.captchaImage = "data:image/gif;base64," + res.img
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