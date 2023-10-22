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
		<view class="filter-bar">
			<uo-select placeholder="请选择包厢" v-model="searchParam.roomId" :options="roomList" valueField="id"
				@change="selectRoom" clearable></uo-select>
			<picker class="uo-select" mode="date" :value="startDate" :start="pickStartDate" :end="pickEndDate"
				@change="bindDateChange">
				<view class="uni-input">{{startDate}}</view>
			</picker>
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
				<view class="card__op-list">
					<!-- <u-button v-if="room.payType==9" @click="shareOrder(room)" plain>分享</u-button> -->
					<u-button type="primary" plain v-if="room.status==2" @click="startOrder(room)">立即开始</u-button>
					<!-- 订单提前开始 订单开始结束时间前移多少分钟 -->
					<u-button type="primary" plain v-if="room.status<3" @click="changeRoom(room)">换包厢</u-button>
					<!-- 截至时间后移多少分钟，费用线下收取 -->
					<u-button type="primary" @click="renewOrder(room)">续单</u-button>
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
				roomList: [],
				orderList: [],
				startDate: '请选择日期',
			}
		},
		onLoad(option) {
			this.getStoreList();
			// this.getRoomList();
			this.refresh();

		},
		computed: {
			pickStartDate() {
				return this.getDate('start');
			},
			pickEndDate() {
				return this.getDate('end');
			},
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
				this.searchParam.roomId = this.roomId;
				this.searchParam.storeId = this.$store.state.currentStore.id;
				this.$api.getRoomOrderList(this.searchParam).then(res => {
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
			getStoreRoom() {
				var param = {};
				param.storeId = this.$store.state.currentStore.id; //是否默认一个系统门店
				this.$api.getRoomList(param).then(res => {
					this.roomList = res.rows;
				});
			},
			selectRoom(event) {
				console.log(event.id);
				this.roomId = event.id;
				this.refresh()
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();

				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year + 2;
				}
				month = month > 9 ? month : '0' + month;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			bindDateChange: function(e) {
				this.startDate = this.searchParam.startTime = e.detail.value
				this.refresh();
			},
			shareOrder(order) {
				this.$api.shareOrder(order).then(res => {
					console.log(order);
				})
			},
			changeRoom(order) {
				var params = {
					orderId: order.id,
					roomId: 34 //order.roomId 需要选择房间
				}
				this.$api.changeRoom(params).then(res => {
					console.log(params);
					this.refresh()
				})
			},
			chargeOrder(order) {
				const url = "charge-order?id=" + order.id;
				uni.navigateTo({
					url,
					events: {
						refresh: this.refresh
					}
				})
			},
			startOrder(room) {
				this.$api.startOrder(order).then(res => {
					console.log(order);
				})
			},
			renewOrder(order) {
				var param = {
					id: order.id,
					minutes: 60 //选择续费时间控件
				}
				this.$api.renewOrder(param).then(res => {
					console.log(order);
					this.refresh()
				})
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