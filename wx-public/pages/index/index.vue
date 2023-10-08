<template>
	<view>
		<view class="top">
			<view class="store-bar">
				<uo-select :options="storeList" value-field="id" mode="top" :value="currentStore.id" @change="onStoreChange">
					<view class="store-bar__title">
						<text>{{currentStore.name}}</text>
						<u-icon name="arrow-down"></u-icon>
					</view>
				</uo-select>
			</view>
			<view class="personal-info">
				<uo-image :src="loginUser.avatar"></uo-image>
				<view class="personal-info__body">
					<view class="personal-info__name">{{loginUser.nickName}}</view>
					<view class="personal-info__phone">{{phonenumber}}</view>
				</view>
				<navigator class="person-center" url="/pages/mine/index/index">
					<text>个人中心</text>
					<u-icon name="arrow-right"></u-icon>
				</navigator>
			</view>
		</view>
		<view class="card-list top-next">
			<view class="notice-bar">
				<u-notice-bar :text="noticeTextList" direction="column" @click="onNoticeClick"></u-notice-bar>
			</view>
			<view class="card menu">
				<navigator class="menu__item" url="../store/store-list/index">
					<image class="menu__item__icon" src="/static/icon/mendian.png"></image>
					<view>门店管理</view>
				</navigator>
				<navigator class="menu__item" url="/pages/room/room-list/index">
					<image class="menu__item__icon" src="/static/icon/baoxiang.png"></image>
					<view>包厢管理</view>
				</navigator>
				<navigator class="menu__item" url="../price/index">
					<image class="menu__item__icon" src="/static/icon/dingjia.png"></image>
					<view>定价管理</view>
				</navigator>
				<navigator class="menu__item" url="../order/index">
					<image class="menu__item__icon" src="/static/icon/dingdan.png"></image>
					<view>订单管理</view>
				</navigator>
				<navigator class="menu__item" @click="comingSoon">
					<image class="menu__item__icon" src="/static/icon/yingxiao.png"></image>
					<view>营销管理</view>
				</navigator>
				<navigator class="menu__item" @click="comingSoon">
					<image class="menu__item__icon" src="/static/icon/jingying.png"></image>
					<view>经营参谋</view>
				</navigator>
				<navigator class="menu__item" @click="comingSoon">
					<image class="menu__item__icon" src="/static/icon/shebei.png"></image>
					<view>设备管理</view>
				</navigator>
				<navigator class="menu__item" @click="comingSoon">
					<image class="menu__item__icon" src="/static/icon/yuangong.png"></image>
					<view>员工管理</view>
				</navigator>
				<navigator class="menu__item" @click="comingSoon">
					<image class="menu__item__icon" src="/static/icon/tuandui.png"></image>
					<view>团队管理</view>
				</navigator>
				<navigator class="menu__item" @click="comingSoon">
					<image class="menu__item__icon" src="/static/icon/chongzhi.png"></image>
					<view>充值管理</view>
				</navigator>
				<navigator class="menu__item" url="../clean/index">
					<image class="menu__item__icon" src="/static/icon/dasao.png"></image>
					<view>打扫记录</view>
				</navigator>
				<navigator class="menu__item" @click="comingSoon">
					<image class="menu__item__icon" src="/static/icon/fenxiang.png"></image>
					<view>分享海报</view>
				</navigator>
				<navigator class="menu__item" @click="comingSoon">
					<image class="menu__item__icon" src="/static/icon/tixian.png"></image>
					<view>提现管理</view>
				</navigator>
				<navigator class="menu__item" @click="comingSoon">
					<image class="menu__item__icon" src="/static/icon/jingying.png"></image>
					<view>经营分析</view>
				</navigator>
				<navigator class="menu__item" @click="comingSoon">
					<image class="menu__item__icon" src="/static/icon/shangcheng.png"></image>
					<view>商城</view>
				</navigator>
			</view>
			<view class="card income-card">
				<view class="card__content__head">
					<view style="flex: 1;font-weight: bold;">数据概况</view>
					<view class="card-more" @click="comingSoon">
						数据详情
						<u-icon name="arrow-right"></u-icon>
					</view>
				</view>
				<view class="income">
					<view class="income-item">
						<view class="income-item__head">
							今日收益（元）
							<u-icon name="arrow-upward"></u-icon>
						</view>
						<view class="income-item__val">1200</view>
						<view class="income-item__sum">
							本月:
							<text class="income-item__val">4830</text>
						</view>
						<view class="income-item__sum">
							累计:
							<text class="income-item__val">4830</text>
						</view>
					</view>
					<view class="income-item">
						<view class="income-item__head">
							今日流水（元）
							<u-icon name="arrow-upward"></u-icon>
						</view>
						<view class="income-item__val">1400</view>
						<view class="income-item__sum">
							本月:
							<text class="income-item__val">5660</text>
						</view>
						<view class="income-item__sum">
							累计:
							<text class="income-item__val">5660</text>
						</view>
					</view>
					<view class="income-item">
						<view class="income-item__head">
							成功订单（笔）
							<u-icon name="arrow-upward"></u-icon>
						</view>
						<view class="income-item__val">10</view>
						<view class="income-item__sum">
							本月:
							<text class="income-item__val">43</text>
						</view>
						<view class="income-item__sum">
							累计:
							<text class="income-item__val">43</text>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				noticeList: []
			}
		},
		computed: {
			currentStore(){
				return this.$store.state.currentStore
			},
			storeList(){
				return this.$store.state.storeList
			},
			loginUser(){
				return this.$store.state.loginUser
			},
			noticeTextList(){
				return this.noticeList.map(x=>x.noticeTitle)
			},
			phonenumber(){
				const phonenumber = this.loginUser.phonenumber
				return phonenumber.substr(0, 3) + '****' + phonenumber.substr(7, 4)
			}
		},
		watch: {
			currentStore: function(){
				this.refresh()
			}
		},
		onLoad() {
			this.refresh()
		},
		onPullDownRefresh() {
			this.refresh()
		},
		methods: {
			refresh(){
				if(this.currentStore && this.currentStore.id){
					this.getNoticeList()
				}
			},
			onStoreChange(store){
				this.$store.commit("$uStore", {
					name: 'currentStore',
					value: store
				})
			},
			getNoticeList(){
				this.$api.getNoticeList({noticeType: 1}).then(res=>{
					this.noticeList = res.rows
					uni.stopPullDownRefresh()
				})
			},
			onNoticeClick(i){
				const notice = this.noticeList[i]
				uni.navigateTo({
					url: '/pages/common/rich-text/index?title=' + notice.noticeTitle,
					success: res=>{
						res.eventChannel.emit('setText', notice.noticeContent)
					}
				})
			},
			comingSoon(){
				uni.showToast({
					icon:"none",
					title:"即将上线"
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: $u-bg-color;
	}
	.top{
		height: 300rpx;
		color: $u-bright;
		background: linear-gradient($u-primary, $u-bg-color);
		padding: 20rpx;
		/deep/ .u-icon__icon{
			color: $u-bright!important;
			margin-left: 10rpx;
		}
	}
	.top-next{
		margin-top: -100rpx;
	}
	.store-bar{
		&__title{
			display: flex;
			align-items: center;
			color: $u-bright;
		}
	}
	.personal-info{
		position: relative;
		display: flex;
		padding: 30rpx 0;
		.uo-image{
			border-radius: 50%;
			margin-right: 20rpx;
			height: 140rpx;
			width: 140rpx;
		}
		&__body{
			padding: 20rpx 0;
			line-height: 50rpx;
		}
		&__name{
			font-size: 36rpx;
		}
	}
	.person-center{
		display: flex;
		position: absolute;
		right: 0;
		padding: 10rpx;
		background: #fff1;
	}
	.notice-bar{
		margin: 0 20rpx;
		border-radius: 16rpx 16rpx 0 0;
		overflow: hidden;
	}
	.notice-bar+.card{
		border-radius: 0 0 16rpx 16rpx;
	}
	.card{
		background: $u-bright;
	}
	.menu.card{
		margin-top: 0;
		padding: 40rpx 0 0;
	}
	.menu{
		display: flex;
		flex-wrap: wrap;
		&__item{
			text-align: center;
			width: 20%;
			margin-bottom: 40rpx;
			font-size: 24rpx;
			color: $u-main-color;
			&__icon{
				width: 70rpx;
				height: 70rpx;
				border-radius: 50%;
			}
		}
	}
	.income{
		display: flex;
		padding: 20rpx 20rpx 0;
		background: $u-primary-bg-color;
		&-item{
			flex: 1;
			font-size: 24rpx;
			line-height: 50rpx;
			&__head{
				display: flex;
				align-items: center;
				// margin-bottom: 20rpx;
			}
			&__val{
				font-size: 36rpx;
				color: $u-primary;
			}
			&__sum{
				.income-item__val{
					font-size: 24rpx;
				}
			}
		}
	}
	.income-card{
		padding: 0;
		overflow: hidden;
		.card__content__head{
			padding: 20rpx;
		}
	}
</style>
