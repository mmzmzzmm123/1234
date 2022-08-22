<template>
	<view class="normal-login-container">
		<view class="logo-content align-center justify-center flex">
			<image style="width: 100rpx;height: 100rpx;" :src="globalConfig.appInfo.logo" mode="widthFix">
			</image>
			<text class="title">若依移动端登录</text>
		</view>
		<view style="line-height:280rpx;font-size: 50rpx;font-weight: 600;text-align: center;">
			<navigator url="/pages/loginWeiXin">微信授权登陆（点击）</navigator>	
					<button type="primary" @click="handleLoginWeixin">微信授权登陆(本页实现)</button>
		</view>
		<view class="login-form-content">
			<view class="input-item flex align-center">
				<view class="iconfont icon-user icon"></view>
				<input v-model="loginForm.username" class="input" type="text" placeholder="请输入账号" maxlength="30" />
			</view>
			<view class="input-item flex align-center">
				<view class="iconfont icon-password icon"></view>
				<input v-model="loginForm.password" type="password" class="input" placeholder="请输入密码" maxlength="20" />
			</view>
			<view class="input-item flex align-center" v-if="captchaEnabled">
				<view class="iconfont icon-code icon"></view>
				<input v-model="loginForm.code" type="number" class="input" placeholder="请输入验证码" maxlength="4" />
				<image :src="codeUrl" @click="getCode" class="login-code-img"></image>
			</view>
			<view class="action-btn">
				<button @click="handleLogin" class="login-btn cu-btn block bg-blue lg round">登录</button>
			</view>
		</view>

		<view class="xieyi text-center">
			<text class="text-grey1">登录即代表同意</text>
			<text @click="handleUserAgrement" class="text-blue">《用户协议》</text>
			<text @click="handlePrivacy" class="text-blue">《隐私协议》</text>
		</view>
	</view>
</template>

<script>
	import {
		getCodeImg
	} from '@/api/login'
	  import { getTest,wxlogin } from "@/api/weixin/userWeixin"

	export default {
		data() {
			return {
				codeUrl: "",
				captchaEnabled: true,
				globalConfig: getApp().globalData.config,
				loginForm: {
					username: "admin",
					password: "admin123",
					code: "",
					uuid: ''
				},
				userInfo_weixin:{}
			}
		},
		created() {
			this.getCode()			
		},
		methods: {
			// 隐私协议
			handlePrivacy() {
				let site = this.globalConfig.appInfo.agreements[0]
				this.$tab.navigateTo(`/pages/common/webview/index?title=${site.title}&url=${site.url}`)
			},
			// 用户协议
			handleUserAgrement() {
				let site = this.globalConfig.appInfo.agreements[1]
				this.$tab.navigateTo(`/pages/common/webview/index?title=${site.title}&url=${site.url}`)
			},
			// 获取图形验证码
			getCode() {
				getCodeImg().then(res => {
					this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
					if (this.captchaEnabled) {
						this.codeUrl = 'data:image/gif;base64,' + res.img
						this.loginForm.uuid = res.uuid
					}
				})
			},
			// 登录方法
			async handleLogin() {
				if (this.loginForm.username === "") {
					this.$modal.msgError("请输入您的账号")
				} else if (this.loginForm.password === "") {
					this.$modal.msgError("请输入您的密码")
				} else if (this.loginForm.code === "" && this.captchaEnabled) {
					this.$modal.msgError("请输入验证码")
				} else {
					this.$modal.loading("登录中，请耐心等待...")
					this.pwdLogin()
				}
			},
			// 密码登录
			async pwdLogin() {
				this.$store.dispatch('Login', this.loginForm).then(() => {
					this.$modal.closeLoading()
					console.log("准备this.loginSuccess()如下");
					this.loginSuccess()
				}).catch(() => {
					if (this.captchaEnabled) {
						this.getCode()
					}
				})
			},
			// 登录成功后，处理函数
			loginSuccess(result) {
				// 设置用户信息 
				this.$store.dispatch('GetInfo').then(res => {
					this.$tab.reLaunch('/pages/index')
				})
			},
			//以下是微信登陆编码开始---------------------------begin
			handleLoginWeixin(){
				console.log("准备微信登陆");
				var that = this;
				uni.getUserProfile({
					desc:"获取您的微信信息以及授权小程序",
					lang:'zh_CN',
					success:UserProfileRes => {  
						console.log("UserProfileRes返回的值为:");
						console.log(UserProfileRes)
						
						uni.showLoading({title:"授权中....",mask:true})
						
						uni.login({
							provider:'weixin',
							success: function(loginRes) {
								console.log("loginRes的值",loginRes);
								let form = {};
								form.code = loginRes.code; //用户code  注:用户的code每次登录都是随机的，所以不需要进行存储
								form.avatarUrl = UserProfileRes.userInfo.avatarUrl; //用户头像
								form.nickName = UserProfileRes.userInfo.nickName; //用户微信名
								form.gender = UserProfileRes.userInfo.gender;//性别
								console.log("获取用户信息为:",form);
								//userInfo_weixin
								
								wxlogin(form).then(weiXinRes => {
									console.log("weiXinRes的值:",weiXinRes);
									//获取数据成功，3000毫秒后隐藏uni.showLoading()
									if(weiXinRes.code==200){
										setTimeout(function () {
											uni.hideLoading();
										}, 3000);
									}
									console.log("weiXinRes的值:",weiXinRes);
									that.weixinLogin(weiXinRes);
							
								})
							}
						})
						
					}
				})
			},
			// 通过微信open获取信息传递进来登陆
			async weixinLogin(weiXinRes) {
				console.log("weiXinRes的值在weixinLogin里为:",weiXinRes);
				//把登陆信息写入vue的store里
				this.$store.dispatch('LoginByWeixin', weiXinRes).then(() => {
					// this.$modal.closeLoading()
					// console.log("准备this.loginSuccess()如下");
					this.loginSuccessByWeixin()
				}).catch(() => {
					if (this.captchaEnabled) {
						this.getCode()
					}
				})
			},
			// 登录成功后，处理函数
			loginSuccessByWeixin(result) {
				// 设置用户信息 
				/* this.$store.dispatch('GetInfo').then(res => {
					this.$tab.reLaunch('/pages/index')
				}) */
				//没有设置用户信息，直接跳转
				this.$tab.reLaunch('/pages/index')
			},
			//以下是微信登陆编码开始---------------------------end
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #ffffff;
	}

	.normal-login-container {
		width: 100%;

		.logo-content {
			width: 100%;
			font-size: 21px;
			text-align: center;
			padding-top: 15%;

			image {
				border-radius: 4px;
			}

			.title {
				margin-left: 10px;
			}
		}

		.login-form-content {
			text-align: center;
			margin: 20px auto;
			margin-top: 15%;
			width: 80%;

			.input-item {
				margin: 20px auto;
				background-color: #f5f6f7;
				height: 45px;
				border-radius: 20px;

				.icon {
					font-size: 38rpx;
					margin-left: 10px;
					color: #999;
				}

				.input {
					width: 100%;
					font-size: 14px;
					line-height: 20px;
					text-align: left;
					padding-left: 15px;
				}

			}

			.login-btn {
				margin-top: 40px;
				height: 45px;
			}

			.xieyi {
				color: #333;
				margin-top: 20px;
			}
		}

		.easyinput {
			width: 100%;
		}
	}

	.login-code-img {
		height: 45px;
	}
</style>
