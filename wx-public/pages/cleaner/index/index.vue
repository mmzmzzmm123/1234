<template>
<view>
	<view class="top">
		<uo-select :options="storeList" value-field="id" mode="top" :value="currentStore.id" @change="onStoreChange"></uo-select>
		<view style="width: 140rpx;">
			<u-button size="mini" @click="toManager">切换角色</u-button>
		</view>
	</view>
	<view class="c-bar">
		<view class="c-bar-item" @click="callManager">
			<u-icon name="phone" size="80rpx"></u-icon>
			<view>商家电话</view>
		</view>
		<navigator class="c-bar-item c-bar-item--warning">
			<u-icon name="order" size="80rpx"></u-icon>
			<view>打扫记录</view>
		</navigator>
	</view>
	<view class="c-filter-bar">
		<view class="c-filter-item" :class="queryParam.status == null ? 'c-filter-item--selected':''"
			@click="onFilterClick(null)">全部</view>
		<view v-for="item in roomStatus"
			class="c-filter-item" :key="item.value" :class="queryParam.status == item.value ? 'c-filter-item--selected':''"
			@click="onFilterClick(item.value)">{{item.name}}</view>
	</view>
	<view style="margin: 30rpx 80rpx;">
		<u-button type="primary" size="large">开大门</u-button>
	</view>
	<view class="card-list">
		<view class="card">
			<view class="card__content">
				<uo-image src="https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png"></uo-image>
				<view class="card__content__right">
					<view class="card__content__head">
						<view>包厢名称</view>
						<view>包厢状态</view>
					</view>
					<view>剩余打扫时间</view>
				</view>
			</view>
			<view class="card__btn-list">
				<u-button size="large" type="success">开始打扫</u-button>
				<u-button size="large" type="error">临时开启包厢</u-button>
			</view>
		</view>
	</view>
</view>
</template>

<script>
	export default{
		data(){
			return {
				queryParam: {
					status: null
				}
			}
		},
		computed: {
			currentStore(){
				return this.$store.state.currentStore
			},
			storeList(){
				return this.$store.state.storeList
			},
			roomStatus(){
				return this.$store.state.dicts.room_status
			}
		},
		onLoad() {
			this.$store.dispatch("loadDict", 'room_status')
		},
		methods:{
			onStoreChange(store){
				this.$store.commit("$uStore", {
					name: 'currentStore',
					value: store
				})
			},
			toManager(){
				uni.navigateTo({
					url: '/pages/index/index'
				})
			},
			callManager(){
				uni.makePhoneCall({
					phoneNumber: this.currentStore.phone
				})
			},
			onFilterClick(roomStatus){
				this.queryParam.status = roomStatus
			}
		}
	}
</script>

<style lang="scss">
	.top{
		display: flex;
		background: $u-primary;
		padding: 20rpx;
		align-items: center;
		.uo-select{
			font-size: 40rpx;
			color: $u-bright;
			/deep/ .u-icon__icon{
				color: $u-bright!important;
			}
		}
	}
	.c-bar{
		display: flex;
		padding-left: 20rpx;
	}
	.c-bar-item{
		flex: 1;
		text-align: center;
		font-size: 40rpx;
		color: $u-bright;
		background: $u-success;
		margin: 20rpx 20rpx 20rpx 0;
		padding: 30rpx 0;
		border-radius: 20rpx;
		/deep/ .u-icon__icon{
			color: $u-bright!important;
		}
		/deep/ .u-icon{
			justify-content: center;
		}
		&--warning{
			background: $u-warning;
		}
	}
	.c-filter-bar{
		display: flex;
		flex-wrap: wrap;
		padding-left: 20rpx;
	}
	.c-filter-item{
		background: $u-bg-color;
		padding: 0 30rpx;
		font-size: 40rpx;
		height: 80rpx;
		line-height: 80rpx;
		border-radius: 40rpx;
		margin: 20rpx 20rpx 0 0;
		color: $u-tips-color;
		&--selected{
			color: $u-primary;
		}
	}
	.card__content__head{
		justify-content: space-between;
		margin-bottom: 30rpx;
	}
	.card__btn-list{
		display: flex;
		.u-button{
			margin: 0 20rpx;
		}
	}
	.u-button--large{
		font-size: 40rpx;
	}
</style>