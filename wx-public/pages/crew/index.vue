<template>
	<view>
		<view>
			<view class="top-shop-select">当前门店：
				<uo-select :value="storeId" :options="storeList" valueField="id" @change="selectStore"></uo-select>
			</view>
		</view>

		<view class="search-bar">
			<u-search placeholder="请输入手机号码" :showAction="false" v-model="searchParam.phone"
				@change="onSearchKeywordInput"></u-search>
		</view>
		
		<view class="card-list">
			<view class="card" v-for="(room, index) in orderList" :key="room.id">
				<view class="card__content">
					<!-- <uo-image src="https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png"></uo-image> -->
					<view class="card__content__right">
						<view class="card__content__head">
							<navigator class="card-title" :url="'room?id='+room.id+'&name='+room.name">
								<view class="card-title">{{room.storeName}}</view>
							</navigator>
							<!-- <view class="card-title">{{room.status}}</view> -->
						</view>
						<view class="card__content__body">
							<view>包间: {{room.roomName}}</view>
							<view>订单金额: {{room.totalAmount}} 元</view>
							<view>开始时间: {{room.startTime}} </view>
							<view>结束时间: {{room.endTime}} </view>
							<view>支付方式: {{room.payTypeName}} </view>
							<view>下单时间: {{room.createTime}} </view>
						</view>
						<view class="card__content__footer">
							<view>支付金额: {{room.payAmount}}</view>
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
				searchParam: {
					name: '',
					startTime: '',
					pageSize: 20,
					pageNum: 1
				},
				showStoreList: false,
				storeList: [],
			}
		},
		onLoad(option) {
			this.getStoreList();
			// this.getRoomList();
			this.refresh();

		},
		computed: {
			
			storeId() {
				return this.$store.state.currentStore.id
			}
		},

		onPullDownRefresh() {
			this.refresh()
		},
		// onReachBottom() {
		// 	this.refresh(true)
		// },
		methods: {
			onSearchKeywordInput() {
				this.$u.debounce(this.refresh, 400)
			},
			refresh(nextPage) {
				// debugger
				console.log(this.searchParam)
				if (nextPage) {
					this.searchParam.pageNum++
				} else {
					this.searchParam.pageNum = 1
				}
				this.searchParam.storeId = this.$store.state.currentStore.id;
				this.$api.getStoreCrewList(this.searchParam).then(res => {
					if (nextPage) {
						this.orderList.push(...res.rows)
					} else {
						this.orderList = res.rows
					}
					uni.stopPullDownRefresh()
				})
			},
			getStoreList() {
				this.$api.getStoreList().then(res => {
					this.storeList = res.rows
					this.searchParam.storeId = this.storeId;
					this.getStoreRoom(this.searchParam, false)
				})
			},
			selectStore(event) {
				this.$store.commit("$uStore", {
					name: 'currentStore',
					value: event
				})
				this.searchParam.storeId = this.storeId;
				this.getStoreRoom(this.searchParam, false);
				this.refresh();
			},
			
		}
	}
</script>

<style lang="scss">
	.search-clause {
		display: flex;
		flex-direction: row-reverse;
		margin: 20rpx;
	}

	.filter-bar {
		display: flex;
		align-items: center;

		.uo-select {
			display: flex;
			padding: 0 10rpx;
			align-items: center;
			background-color: $u-primary-bg-color;
			border-radius: 10rpx;
			margin: 10rpx;
			box-sizing: border-box;
			height: 60rpx;
			flex: 1;

			/deep/ .uo-select__content {
				width: 100%;
				justify-content: space-between;
			}
		}

		.u-button {
			height: 60rpx;
			margin: 10rpx;
			width: 6em;
		}
	}
</style>
