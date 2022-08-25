<template>
	<view class="ib-wraper">
		<view class="box-hd" @click="login">
			<view v-if="!userinfo.nickName">
				<view class="avator">
					<img :src="avatar">
				</view>
				<view class="phone-number" style="color: red;">请先点我登录</view>
			</view>
			<view v-else>
				<view class="avator">
					<img :src="userinfo.avatarUrl">
				</view>
				<view class="phone-number">{{userinfo.nickName}}</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getConfig,
		setUserInfo,
		getUserInfo,
		setToken,
		getToken,
		removeUserInfo,
		removeToken
	} from '@/utils/authWeixin.js';
	  import { getTest,wxlogin } from "@/api/weixin/userWeixin"
	export default {
		data() {
			return {
				avatar: 'https://portrait.gitee.com/uploads/avatars/user/354/1062657_rahman_1649233036.png!avatar60',
				userinfo: {}
			}
		},
		onLoad() {
			this.userinfo = getUserInfo() || {};
			console.log("this.userinfo", this.userinfo);
		},
		methods: {
		async login(){
				uni.showToast({
					title:"准备login()请求"
				})
				var that = this;
				uni.getUserProfile({
					desc: '获取您的微信信息以授权小程序',
					lang: 'zh_CN',
					success: UserProfileRes => {  
						console.log("准备向服务器发送请求………………");
						uni.showLoading({title:"登录中....",mask:true})
						//设置多少稍后关，应该放在登陆程序代码后计时
						// setTimeout(function () {
						// 	uni.hideLoading();
						// }, 3000);
						console.log(UserProfileRes)
						uni.login({
							provider: 'weixin',
							success: function(loginRes) {
								 console.log("loginRes的值:",loginRes);
								let form = {};
								form.code = loginRes.code; //用户code  注:用户的code每次登录都是随机的，所以不需要进行存储
								form.avatarUrl = UserProfileRes.userInfo.avatarUrl; //用户头像
								form.nickName = UserProfileRes.userInfo.nickName; //用户微信名
								form.gender = UserProfileRes.userInfo.gender;//性别
								console.log("获取用户信息为:",form);
								wxlogin(form).then(wxloginres => {
										console.log("返回的结果res:",wxloginres);
									  // this.user = response.data
									  // this.roleGroup = response.roleGroup
									  // this.postGroup = response.postGroup
									  if(wxloginres.data.state){
									  	setTimeout(function () {
									  		uni.hideLoading();
									  	}, 3000);
									  	that.userinfo = wxloginres.data 
									  	setToken(wxloginres.data.openid)
									  	setUserInfo(wxloginres.data);  //模拟存储用户信息
									  }else{
									  	uni.showToast({
									  		icon:"none",
									  		title: "登录失败，请稍后试试！",
									  		duration:2000
									  	})
									  }
									})
									
								//测试请求test方式成功
								/* getTest().then(res => {
									console.log("返回的结果res:",res);
								  // this.user = response.data
								  // this.roleGroup = response.roleGroup
								  // this.postGroup = response.postGroup
								}) */
								
								// that.$store.dispatch('/wxapi/test').then((res) => {
								// 	// that.$modal.closeLoading()
								// 	// that.loginSuccess()
								// 	console.log("微信登陆请求返回结果:",res);
								// }).catch(() => {
								// 	console.log("微信登陆有误");
								// 	// if (that.captchaEnabled) {
								// 	// 	that.getCode()
								// 	// }
								// })
					/* 			that.request('wxlogin', form, 'POST').then(wxloginres=>{
									console.log("wxloginres: ", wxloginres)
									if(wxloginres.data.state){
										setTimeout(function () {
											uni.hideLoading();
										}, 3000);
										that.userinfo = wxloginres.data 
										setToken(wxloginres.data.openid)
										setUserInfo(wxloginres.data);  //模拟存储用户信息
									}else{
										uni.showToast({
											icon:"none",
											title: "登录失败，请稍后试试！",
											duration:2000
										})
									}
									
								}) */
							},
							fail(err) {
								console.log(err)
							}
						});
					},
					fail:err=>{
						console.log(err)
					}
				})
			}
		}
	}
</script>

<style lang="less">
	.ib-wraper {
		.box-hd {
			display: flex;
			flex-wrap: wrap;
			flex-direction: row;
			justify-content: center;
			margin-top: 15rpx;

			.avator {
				width: 160rpx;
				height: 160rpx;
				background: #fff;
				border: 5rpx solid #f1f1f1;
				border-radius: 50%;
				margin-top: -80rpx;
				overflow: hidden;
				box-shadow: 0 5rpx 20rpx 0rpx rgba(0, 0, 150, 0.2);

				img {
					width: 100%;
					height: 100%;
				}
			}

			.phone-number {
				width: 100%;
				text-align: center;
				margin: 20rpx auto;
			}
		}
	}
</style>
