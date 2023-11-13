<template>
	<view>
		<view class="card">
			<view>
				<text>续单门店：</text>
				<text style="color: #000;">{{storeInfo.name}}</text>
			</view>
			<view class="u-p-v-10">
				<text>续单包间：</text>
				<text style="color: #000;">{{roomInfo.name}}</text>
			</view>
			<view>
				<text>包间状态：</text>
				<text style="color: #000;">{{roomStatusDesc}}</text>
			</view>
		</view>
		<view class="card">
			<view class="price-title">选择续单套餐</view>
			<view v-for="price in chargeList" :key="price.id" class="price-bar"
				:class="currentCharge.id == price.id ? 'price-bar--selected':''" @click="onChargeSelected(price)">
				<text>{{price.name}}</text>
				<text style="float: right;">
					<text class="price-bar_price">{{price.price}}</text>
					元/场
				</text>
			</view>
		</view>
		<view class="card">
			<view class="day-select">
				<view v-for="(item,i) in dayOptions" :key="i" class="day-option"
					:class="i == selectedDayIndex ? 'day-option--selected':''" @click="onDayOptionSelected(i)">
					<view>{{item.dateStr}}</view>
					<view>{{item.weekDayStr}}</view>
				</view>
			</view>
			<view style="display: flex;align-items: center;">
				<view>续单时间：</view>
				<view class="time-input">
					{{$u.timeFormat(order.startTime, 'hh:MM')}}
				</view>
				<view style="margin: 0 30rpx;">到</view>
				<view class="time-input time-input--readonly">
					{{$u.timeFormat(order.endTime, 'hh:MM')}}
				</view>
			</view>
			<view>
				<view class="u-m-v-20">{{$u.timeFormat(order.startTime, 'yyyy-mm-dd')}}</view>
				<view class="u-m-v-20">
					<hour-status-bar :hours="hourList"></hour-status-bar>
				</view>
				<view class="u-m-v-20">{{$u.timeFormat(order.startTime + 24 * 60 * 60 * 1000, 'yyyy-mm-dd')}}</view>
				<view class="u-m-v-20">
					<hour-status-bar :hours="hourList2"></hour-status-bar>
				</view>
				<view class="u-m-v-20">
					<hour-status-legend :showCurrent="true"></hour-status-legend>
				</view>
				<view v-if="hourRepeat" class="u-m-v-20 u-error">
					续单时间与已被预定时间重复
				</view>
			</view>
		</view>
		<view class="bottom">
			<view class="u-success">¥{{amount}}</view>
			<view style="width: 200rpx;">
				<u-button type="primary" text="立即续单" @click="onAddOrder"></u-button>
			</view>
		</view>
		<view style="height: 100rpx;"></view>
		<pay-bar v-model="payShow" :amount="amount" :storeId="roomInfo.storeId" @prepareOrder="onPrepareOrder"
			@success="onOrderSuccess"></pay-bar>
	</view>
</template>

<script>
	import HourStatusLegend from "../../../components/hour-status-bar/hour-status-legend"
	export default {
		components: {
			HourStatusLegend
		},
		data() {
			const minDate = new Date().getTime()
			return {
				roomInfo: {},
				order: {
					startTime: minDate,
					endTime: minDate
				},
				period: null,
				hourList: [],
				hourList2: [],
				hourRepeat: false,
				amount: 0,
				payShow: false,
				chargeList: [],
				currentCharge: {},

				storeInfo: {
					name: '...'
				}
			}
		},
		computed: {
			roomStatusDesc() {
				let desc = ''
				if (this.roomInfo) {
					if (this.roomInfo.status) {
						desc = this.roomInfo.status
					}
					if (this.roomInfo.estEndTime) {
						desc += ' ' + this.roomInfo.estEndTime.substr(11, 5) + '结束'
					}
				}
				return desc
			}
		},
		onLoad(options) {
			if (options.scene) {
				this.$api.getRoomList({
					id: options.scene
				}).then(res => {
					if (res.rows.length == 1) {
						this.onAcceptRoom(res.rows[0])
					}
				})
			} else {
				const eventChannel = this.getOpenerEventChannel()
				if (eventChannel) {
					eventChannel.on('acceptRoom', this.onAcceptRoom)
				}
			}
		},
		methods: {
			onAcceptRoom(roomInfo) {
				const now = new Date()
				roomInfo.period.date = Date.fromString(roomInfo.period.date)
				roomInfo.period.orderList.forEach(x=>{
					x.startTime = Date.fromString(x.startTime)
					x.endTime = Date.fromString(x.endTime)
					if(x.startTime <= now && x.endTime >= now){
						this.order.startTime = x.endTime.getTime()
					}
				})
				
				this.roomInfo = roomInfo
				this.period = roomInfo.period
				this.$api.getSimpleStore(roomInfo.storeId).then(storeInfo => this.storeInfo = storeInfo)
				this.$api.getChargeList({roomId: this.roomInfo.id}).then(res=>{
					this.chargeList = res.rows
					if(res.rows.length){
						this.currentCharge = res.rows[0]
						this.onChargeSelected(res.rows[0])
					}
				})
			},
			calcAmount() {
				// 计算金额
				if (this.currentCharge && this.currentCharge.price) {
					this.amount = this.currentCharge.price
				}else{
					this.amount = 0
				}
			},
			getPrice(hour) {
				const priceList = this.roomInfo.priceList
				for (let i in priceList) {
					const price = priceList[i]
					if (price.startTime <= hour && price.stopTime >= hour) {
						return price.price
					}
				}
			},
			getHourStatus() {
				if (new Date(this.order.startTime).getDate() != new Date(this.period.date).getDate()) {
					this.$api.getRoomAvailable({
						roomId: this.roomInfo.id,
						date: this.$u.timeFormat(this.order.startTime, 'yyyy-mm-dd')
					}).then(res => {
						this.period = res
						this.updateHourStatus()
					})
				}
			},
			updateHourStatus() {
				this.hourRepeat = false
				const hourList = []
				const hourList2 = []
				const period = this.period
				for (let i = 0; i < 24; i++) {
					hourList.push({
						name: i,
						status: period.canNotUseList.indexOf(i) >= 0
					})
				}
				for (let i = 0; i < 24; i++) {
					hourList2.push({
						name: i,
						status: period.canNotUseList2.indexOf(i) >= 0
					})
				}
				const orderList = this.period.orderList
				const orderEndTime = this.order.endTime
				const date = period.date.getDate()
				orderList.forEach(x=>{
					if(x.startTime.getTime() < this.order.endTime && x.endTime.getTime() > orderEndTime){
						this.hourRepeat = true
					}
					// let i = x.startTime.getHours()
					// let currentHourList = x.startTime.getDate() == date ? hourList : hourList2
					// if(x.endTime.getDate() > x.startTime.getDate()){
					// 	while(i < 24){
					// 		hourList[i]
					// 	}
					// }
				})
				
				this.hourList = hourList
				this.hourList2 = hourList2
				if (this.order.startTime < this.order.endTime) {
					const start = new Date(this.order.startTime)
					const end = new Date(this.order.endTime)
					if (start.getDate() < end.getDate()) {
						for (let i = start.getHours(); i < 24; i++) {
							if (hourList[i].status) {
								hourList[i].error = true
							} else {
								hourList[i].current = true
							}
						}
						for (let i = 0; i < end.getHours(); i++) {
							if (hourList2[i].status) {
								hourList[i].error = true
							} else {
								hourList2[i].current = true
							}
						}
					} else {
						for (let i = start.getHours(); i < end.getHours(); i++) {
							if (hourList[i].status) {
								hourList[i].error = true
							} else {
								hourList[i].current = true
							}
						}
					}
				}
			},
			onClosePicker(start) {
				if (start) {
					this.startShow = false
				} else {
					this.endShow = false
				}
			},
			onAddOrder() {
				if (!this.currentCharge.id) {
					uni.showToast({
						icon: "none",
						title: "请先选择套餐"
					})
					return
				}
				if (this.hourRepeat) {
					uni.showToast({
						icon: "none",
						title: "续单时间与已被预定时间重复，请选择其它套餐"
					})
					return
				}
				wx.requestSubscribeMessage({
					tmplIds: ['58BvI5jDnq61I9slOIIjf89J9ionC95R14eUJ9rQLWA']
				})
				this.payShow = true
			},
			onPrepareOrder(e) {
				e.order = {
					chargePackId: this.currentCharge.id,
					payType: e.payType,
					roomId: this.roomInfo.id
				}
			},
			onOrderSuccess() {
				uni.showModal({
					title: '预定成功',
					showCancel: false,
					success: res => {
						this.payShow = false
						uni.reLaunch({
							url: '/pages/order/list/index'
						})
					}
				})
			},
			onChargeSelected(charge) {
				this.currentCharge = charge
				this.order.endTime = this.order.startTime + charge.minutes * 60 * 1000
				this.calcAmount()
				this.updateHourStatus()
			}
		}
	}
</script>

<style lang="scss">
	page {
		background: $u-bg-color;
	}

	.card {
		margin: 20rpx;
		padding: 20rpx;
		border-radius: 10rpx;
		background: #fff;
		color: $u-main-color;
	}

	.price-bar {
		color: $u-bright;
		background: $u-primary-light;
		margin-top: 20rpx;
		padding: 0 30rpx;
		height: 80rpx;
		line-height: 80rpx;
		border-radius: 5rpx;

		&--selected {
			background: $u-primary;
			// border: 1rpx solid $u-border-color;
		}

		&_price {
			font-size: 38rpx;
			font-weight: bold;
			margin-right: 10rpx;
			color: #f9e82a;
		}
	}

	.time-input {
		display: flex;
		align-items: center;
		justify-content: center;
		border: 1rpx solid $u-primary;
		border-radius: 10rpx;
		padding: 10rpx 20rpx;
		width: 140rpx;
		color: $u-primary;

		&--readonly {
			color: $u-content-color;
		}

		/deep/ .u-icon__icon {
			color: $u-primary !important;
			font-size: 40rpx !important;
		}
	}

	.u-m-v-20 {
		margin: 20rpx 0;
	}

	.bottom {
		box-sizing: border-box;
		padding: 0 20rpx;
		position: fixed;
		height: 120rpx;
		background: #fff;
		display: flex;
		width: 100%;
		align-items: center;
		justify-content: space-between;
		bottom: 0;
	}

	.promotion-img {
		display: block;
		width: 670rpx;
		height: 268rpx;
		margin: 20rpx 0;
		border-radius: 10rpx;

		&--selected {
			box-shadow: 0 0 16rpx 5rpx rgba(0, 0, 0, 0.5);
		}
	}

	.day-select {
		display: flex;
		margin-right: -20rpx;
		padding: 20rpx 0;
	}

	.day-option {
		margin-right: 20rpx;
		flex: 1;
		text-align: center;
		padding: 10rpx;

		&--selected {
			border: 1rpx solid $u-primary;
			color: $u-primary;
			border-radius: 10rpx;
		}
	}
</style>