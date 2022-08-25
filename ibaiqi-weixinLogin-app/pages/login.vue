<template>
	<view class="normal-login-container">
		<!-- 1:页头 -->
		<view class="ib-header">
			<view class="ib-header-banner">
				<!-- 轮播放图 -->
				<swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000">
					<swiper-item>
						<view class="swiper-item">
							<image
								src="https://img.alicdn.com/imgextra/i3/134158560/O1CN01A1djT42D6X4pIKGmW_!!134158560.jpg">
							</image>

						</view>
					</swiper-item>
					<swiper-item>
						<view class="swiper-item">
							<image src="https://res.wx.qq.com/wxdoc/dist/assets/img/0.4cb08bb4.jpg"></image>
						</view>
					</swiper-item>
					<swiper-item>
						<view class="swiper-item">
							<image
								src="https://img.alicdn.com/imgextra/i2/134158560/O1CN01ltukZo2D6X4kR3BUA_!!134158560.jpg">
							</image>
						</view>
					</swiper-item>
				</swiper>
			</view>
			<!-- 向上偏移导航 -->

		</view>
		<!-- 主体部份 -->
		<view class="ib-content">
			<view class="ib-login-btn">
				<button type="primary" @click="handleLoginWeixin">微信授权登陆</button>
				<button type="primary" @click="goToHome" class="ib-btn-gray">返回主页查看</button>
			</view>
			<view class="ib-xieyi text-center">
				<text class="text-grey1">登录即代表同意</text>
				<text @click="handleUserAgrement" class="text-blue">《用户协议》</text>
				<text @click="handlePrivacy" class="text-blue">《隐私协议》</text>
			</view>
			<view class="ib-login-userPwd">
				<navigator url="/pages/loginAccount">账号密码登陆</navigator>
			</view>

		</view>
	</view>
</template>

<script>
	import {
		getCodeImg
	} from '@/api/login'
	import {
		getTest,
		wxlogin
	} from "@/api/weixin/userWeixin"

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
				userInfo_weixin: {}
			}
		},
		created() {
			// this.getCode()			
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
			//跳转到主页
			goToHome(){
				this.$tab.navigateTo(`/pages/index`)
			},
			// 登录成功后，处理函数
			loginSuccess(result) {
				// 设置用户信息 
				this.$store.dispatch('GetInfo').then(res => {
					this.$tab.reLaunch('/pages/index')
				})
			},
			//以下是微信登陆编码开始---------------------------begin
			handleLoginWeixin() {
				console.log("准备微信登陆");
				var that = this;
				uni.getUserProfile({
					desc: "获取您的微信信息以及授权小程序",
					lang: 'zh_CN',
					success: UserProfileRes => {
						console.log("UserProfileRes返回的值为:");
						console.log(UserProfileRes)

						uni.showLoading({
							title: "授权中....",
							mask: true
						})

						uni.login({
							provider: 'weixin',
							success: function(loginRes) {
								console.log("loginRes的值", loginRes);
								let form = {};
								form.code = loginRes.code; //用户code  注:用户的code每次登录都是随机的，所以不需要进行存储
								form.avatarUrl = UserProfileRes.userInfo.avatarUrl; //用户头像
								form.nickName = UserProfileRes.userInfo.nickName; //用户微信名
								form.gender = UserProfileRes.userInfo.gender; //性别
								console.log("获取用户信息为:", form);
								//userInfo_weixin

								wxlogin(form).then(weiXinRes => {
									console.log("weiXinRes的值:", weiXinRes);
									//获取数据成功，3000毫秒后隐藏uni.showLoading()
									if (weiXinRes.code == 200) {
										setTimeout(function() {
											uni.hideLoading();
										}, 3000);
									}
									console.log("weiXinRes的值:", weiXinRes);
									that.weixinLogin(weiXinRes);

								})
							}
						})

					}
				})
			},
			// 通过微信open获取信息传递进来登陆
			async weixinLogin(weiXinRes) {
				console.log("weiXinRes的值在weixinLogin里为:", weiXinRes);
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
		background-color: #f5f5f5;
	}

	.normal-login-container {

		width: 100%;

		.ib-header {
			background-color: pink;
			height: 750rpx;

			swiper-item {
				image {
					width: 100%;
					height: 750rpx;
				}
			}

			uni-swiper .uni-swiper-dots-horizontal {
				background-color: red;
				border: 2px solid red;
			}
		}

		.ib-content {
			padding: 20rpx;
			background-color: #f5f5f5;
			border-radius: 20rpx 20rpx 0 0;
			position: relative;
			top:-15rpx;

			.ib-login-btn {
				width: 75%;
				margin: 0 auto;

				.ib-btn-gray {
					background: #f2f2f2;
					color: #1aad19;
					margin-top: 30rpx;
				}
			}

			.ib-xieyi {
				line-height: 90rpx;
			}

			.ib-login-userPwd {
				text-align: center;
				line-height: 260rpx;
			}
		}

	}


	swiper {
		height: 750rpx;
	}
</style>
