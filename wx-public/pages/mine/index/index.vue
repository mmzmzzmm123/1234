<template>
<view>
	<view class="personal-info">
		<uo-image :src="loginUser.avatar"></uo-image>
		<view class="personal-info__body">
			<view class="personal-info__name">{{loginUser.nickName}}</view>
			<view class="personal-info__phone">{{phonenumber}}</view>
		</view>
		<view style="width: 140rpx;">
			<u-button type="primary">角色切换</u-button>
		</view>
	</view>
	<view class="card-list">
		<view class="card account">
			<view class="account__title">我的账户</view>
			<view class="account__item">
				<view>门店经营收益</view>
				<view class="account__item__val">4830</view>
				<view class="account__item__btn">
					<u-button plain type="primary" @click="comingSoon" size="mini" shape="circle">查看</u-button>
				</view>
			</view>
			<view class="account__item">
				<view>推广流水收益</view>
				<view class="account__item__val">200</view>
				<view class="account__item__btn">
					<u-button plain type="primary" @click="comingSoon" size="mini" shape="circle">查看</u-button>
				</view>
			</view>
		</view>
	</view>
	<view class="logout">
		<u-button type="primary" @click="onLogoutClick">退出登录</u-button>
	</view>
</view>
</template>

<script>
	export default{
		data(){
			return {
				
			}
		},
		computed: {
			loginUser(){
				return this.$store.state.loginUser
			},
			phonenumber(){
				const phonenumber = this.loginUser.phonenumber
				return phonenumber.substr(0, 3) + '****' + phonenumber.substr(7, 4)
			}
		},
		methods:{
			comingSoon(){
				uni.showToast({
					icon:"none",
					title:"即将上线"
				})
			},
			onLogoutClick(){
				this.$store.dispatch('logout').then(()=>{
					uni.reLaunch({
						url: '/pages/login/index'
					})
				})
			}
		}
	}
</script>

<style lang="scss">
page{
	background: $u-bg-color;
}
.personal-info{
	display: flex;
	padding: 20rpx;
	align-items: center;
	background: $u-bright;
	.uo-image{
		border-radius: 50%;
		margin-right: 20rpx;
		height: 140rpx;
		width: 140rpx;
	}
	&__body{
		padding: 20rpx 0;
		line-height: 50rpx;
		flex: 1;
	}
	&__name{
		font-size: 36rpx;
	}
}
.card.account{
	background-color: $u-bright;
	padding-bottom: 30rpx;
}
.account{
	&__title{
		padding-bottom: 20rpx;
		font-weight: bold;
		border-bottom: 1rpx solid $u-border-color;
	}
	&__item{
		display: flex;
		padding-top: 30rpx;
		&__val{
			flex: 1;
			text-align: center;
		}
		&__btn{
			width: 100rpx;
		}
	}
}
.logout{
	margin: 100rpx 50rpx;
}	
</style>