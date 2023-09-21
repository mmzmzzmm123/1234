<template>
	<view>
		<store-bar></store-bar>
		<view class="card-list">
			<view class="card">
				<view class="flex-row">
					<view class="overview-title">用户概况</view>
					<view class="overview-count">100人</view>
				</view>
				<view class="flex-row">
					<view class="overview-detail">
						<view class="overview-detail_label">今日新增</view>
						<view class="overview-detail_value">0</view>
					</view>
					<view class="overview-detail">
						<view class="overview-detail_label">近7日新增</view>
						<view class="overview-detail_value">0</view>
					</view>
					<view class="overview-detail">
						<view class="overview-detail_label">近30日新增</view>
						<view class="overview-detail_value">0</view>
					</view>
				</view>
				<view class="flex-row">
					<view class="overview-detail">
						<view class="overview-detail_label">今日活跃</view>
						<view class="overview-detail_value">0</view>
					</view>
					<view class="overview-detail">
						<view class="overview-detail_label">近7日活跃</view>
						<view class="overview-detail_value">0</view>
					</view>
					<view class="overview-detail">
						<view class="overview-detail_label">近30日活跃</view>
						<view class="overview-detail_value">0</view>
					</view>
				</view>
			</view>
			<view class="card">
				<u-search placeholder="输入用户昵称或手机号" :showAction="false" v-model="queryParam.keyword" @change="onKeywordInput"></u-search>
				<view class="sort-bar">
					<text v-for="option in sortOptions" :key="option.sort"
						class="sort-item" :class="selectedSort == option.sort ? 'sort-item--selected':''"
						@click="onSortSelected(option)">{{option.text}}</text>
				</view>
				<view v-for="user in userList" :key="user.id" class="user">
					<view class="flex-row">
						<view>
							<text class="user_name">{{user.nickName}}</text>
							<text class="user_split"></text>
							<text>{{user.phone}}</text>
						</view>
						<view>{{user.lastestDate}}</view>
					</view>
					<view>
						{{user.count}}次<text class="user_split"></text>
						{{amount.amount}}元<text class="user_split"></text>
						到店{{user.hours}}小时
					</view>
				</view>
				<view class="no-user" v-if="!userList || userList.length == 0">
					<text class="iconfont icon-wuyonghu"></text>
					<view>没有用户数据</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				sortOptions:[
					{text: '按注册时间', sort: 'getUserListReg'},
					{text: '按活跃时间', sort: 'getUserListAct'},
					{text: '按访问次数', sort: 'getUserListTimes'},
					{text: '按到店时长', sort: 'getUserListHour'},
				],
				selectedSort: 1,
				userList: [],
				queryParam: {
					keyword: '',
					pageNum: 1,
					pageSize: 20
				}
			}
		},
		onPullDownRefresh() {
			
		},
		methods: {
			onSortSelected(option){
				this.selectedSort = option.sort
			},
			onKeywordInput(){
				this.$u.debounce(this.refresh, 400)
			},
			refresh(nextPage){
				if(nextPage){
					this.queryParam.pageNum++
				}else{
					this.queryParam.pageNum = 1
				}
				this.$api[this.selectedSort.sort](this.queryParam).then(res=>{
					uni.stopPullDownRefresh()
					if(nextPage){
						this.userList.push(...res.rows)
					}else{
						this.userList = res.rows
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	page{
		background: $u-bg-color;
	}
	.flex-row{
		display: flex;
		align-items: center;
		justify-content: space-between;
	}
	.overview-title{
		font-weight: bold;
		position: relative;
		padding-left: 20rpx;
		&::before{
			position: absolute;
			content: " ";
			width: 6rpx;
			left: 0;
			top: 8rpx;
			bottom: 6rpx;
			background: $u-primary;
		}
	}
	.overview-count{
		padding: 2rpx 10rpx 4rpx;
		font-size: 24rpx;
		color: $u-success;
		border: 1rpx solid $u-success;
		border-radius: 6rpx;
		background: $u-success-light;
	}
	.overview-detail{
		text-align: center;
		line-height: 50rpx;
		margin-top: 20rpx;
		&_label{
			color: $u-tips-color;
			font-size: 24rpx;
		}
		&_value{
			font-weight: bold;
		}
	}
	.sort-bar{
		display: flex;
		justify-content: space-between;
		margin-top: 20rpx;
	}
	.sort-item{
		font-size: 24rpx;
		&--selected{
			color: $u-success;
		}
	}
	.no-user{
		margin: 100rpx;
		text-align: center;
		color: $u-tips-color;
		.iconfont{
			color: $u-tips-color;
			font-size: 200rpx;
			margin-bottom: 40rpx;
		}
	}
	.user{
		padding: 20rpx 0;
		border-bottom: 1rpx solid $u-border-color;
		font-size: 28rpx;
		line-height: 50rpx;
		color: $u-content-color;
		&_name{
			color: $u-main-color;
			font-size: 30rpx;
		}
		&_split{
			display: inline-block;
			margin: 0 10rpx;
			vertical-align: middle;
			height: 28rpx;
			border-left: 2rpx solid $u-content-color;
		}
	}
</style>
