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
			<block>
				<view class="price-title">选择续单套餐</view>
				<view v-for="price in roomInfo.packageList" :key="price.id" class="price-bar"
					:class="currentPackage.id == price.id ? 'price-bar--selected':''" @click="onPackageSelected(price)">
					<text>{{price.name}}</text>
					<text style="float: right;">
						<text class="price-bar_price">{{price.price}}</text>
						元/场
					</text>
				</view>
			</block>
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
				<view>选择时间：</view>
				<view class="time-input" @click="startShow = true">
					{{$u.timeFormat(order.startTime, 'hh:MM')}}
					<u-icon name="edit-pen"></u-icon>
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
		<u-datetime-picker :value="order.startTime" :min-date="minDate" :max-date="maxDate" :show="startShow"
			:formatter="timeFormatter" @close="onClosePicker(true)" @cancel="onClosePicker(true)"
			@confirm="onConfirmPicker(true,$event)"></u-datetime-picker>
		<u-datetime-picker :value="order.endTime" :min-date="minDate" :max-date="maxDate" :show="endShow"
			:formatter="timeFormatter" @close="onClosePicker(false)" @cancel="onClosePicker(false)"
			@confirm="onConfirmPicker(false,$event)"></u-datetime-picker>

		<view style="height: 100rpx;"></view>
		<pay-bar v-model="payShow" :amount="amount" :storeId="roomInfo.storeId" @prepareOrder="onPrepareOrder"
			@success="onOrderSuccess"></pay-bar>
	</view>
</template>

<script>
	const WEEKDAYS = ['周日', '周一', '周二', '周三', '周四', '周五', '周六']
	import HourStatusLegend from "../../../components/hour-status-bar/hour-status-legend"
	export default {
		components: {
			HourStatusLegend
		},
		data() {
			const minDate = new Date().getTime()
			return {
				roomInfo: {},
				minDate: minDate,
				maxDate: minDate + 4 * 24 * 60 * 60 * 1000,
				order: {
					startTime: minDate,
					endTime: minDate
				},
				startShow: false,
				endShow: false,
				period: null,
				hourList: [],
				hourList2: [],
				hourRepeat: false,
				amount: 0,
				payShow: false,
				currentPackage: {},

				dayOptions: this.getDayOptions(),
				storeInfo: {
					name: '...'
				}
			}
		},
		computed: {
			selectedDayIndex() {
				for (let i = 4; i >= 0; i--) {
					if (this.dayOptions[i].date.getTime() <= this.order.startTime) {
						return i
					}
				}
				return 0
			},
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
				this.roomInfo = roomInfo
				this.period = roomInfo.period
				this.$api.getSimpleStore(roomInfo.storeId).then(storeInfo => this.storeInfo = storeInfo)
			},
			calcAmount() {
				// 计算金额
				if (this.currentPackage.id) {
					this.amount = this.currentPackage.price
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
				const period = this.period
				const hourList = []
				const hourList2 = []
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
				this.hourList = hourList
				this.hourList2 = hourList2
				if (this.order.startTime < this.order.endTime) {
					const start = new Date(this.order.startTime)
					const end = new Date(this.order.endTime)
					if (start.getDate() < end.getDate()) {
						for (let i = start.getHours(); i < 24; i++) {
							if (hourList[i].status) {
								hourList[i].error = true
								this.hourRepeat = true
							} else {
								hourList[i].current = true
							}
						}
						for (let i = 0; i < end.getHours(); i++) {
							if (hourList2[i].status) {
								hourList[i].error = true
								this.hourRepeat = true
							} else {
								hourList2[i].current = true
							}
						}
					} else {
						for (let i = start.getHours(); i < end.getHours(); i++) {
							if (hourList[i].status) {
								hourList[i].error = true
								this.hourRepeat = true
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
			onConfirmPicker(start, e) {
				let startTime = this.order.startTime
				let endTime = this.order.endTime
				if (this.currentPackage.id) { //套餐
					endTime = this.currentPackage.minutes * 60 * 1000
				}
				if (start) {
					startTime = e.value
					endTime = startTime + endTime
				} else {
					endTime = e.value
				}
				if (endTime - startTime > 24 * 60 * 60 * 1000) {
					uni.showToast({
						icon: "none",
						title: "预定的时间不能超出24小时"
					})
					return
				}
				if (start) {
					this.order.startTime = e.value
					if (this.currentPackage.id) { //优惠套装，套装结束时间不用选
						this.order.endTime = endTime
					}
					this.startShow = false
					if (new Date(e.value).getDate() != new Date(this.period.date).getDate()) {
						this.getHourStatus()
					} else {
						this.updateHourStatus()
					}
				} else {
					this.order.endTime = e.value
					this.endShow = false
					this.updateHourStatus()
				}
				this.calcAmount()
			},
			onAddOrder() {
				if (this.amount == 0) {
					uni.showToast({
						icon: "none",
						title: "请选择续单时间"
					})
					return
				}
				if (this.hourRepeat) {
					uni.showToast({
						icon: "none",
						title: "续单时间与已被预定时间重复，请重新选择时间"
					})
					return
				}
				wx.requestSubscribeMessage({
					tmplIds: ['58BvI5jDnq61I9slOIIjf89J9ionC95R14eUJ9rQLWA']
				})
				if (this.currentPromotion.id) { //优惠套餐支付
					this.$api.addPromotionOrder({
						roomId: this.roomInfo.id,
						startTime: this.$u.timeFormat(this.order.startTime, 'yyyy-mm-dd hh:MM'),
						// payType: 3 ,
						couponId: this.currentPromotion.id
					}).then(res => {
						const param = res.jsapiResult
						param.package = param.packageValue
						delete param.packageValue
						param.success = () => {
							this.$api.wxPaySuccess(res).then((payRes) => {
								this.onOrderSuccess()
							})
						}
						param.fail = function() {
							this.$toast('支付失败')
						}
						uni.requestPayment(param)
					})
				} else {
					this.payShow = true
				}
			},
			onPrepareOrder(e) {
				if (this.currentPackage.id) {
					e.order = {
						packId: this.currentPackage.id,
						startTime: this.$u.timeFormat(this.order.startTime, 'yyyy-mm-dd hh:MM'),
						payType: e.payType
					}
				} else {
					e.order = {
						roomId: this.roomInfo.id,
						startTime: this.$u.timeFormat(this.order.startTime, 'yyyy-mm-dd hh:MM'),
						endTime: this.$u.timeFormat(this.order.endTime, 'yyyy-mm-dd hh:MM'),
						payType: e.payType
					}
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
			onPackageSelected(pack) {
				this.currentPromotion = {}
				this.currentPackage = pack
				this.order.endTime = this.order.startTime + pack.minutes * 60 * 1000
				this.calcAmount()
				this.updateHourStatus()
			},
			onPromotionSelected(promotion) {
				this.currentPackage = {}
				this.currentPromotion = promotion
				this.order.endTime = this.order.startTime + promotion.maxMinutes * 60 * 1000
				this.calcAmount()
				this.updateHourStatus()
			},
			timeFormatter(type, value) {
				if (type === 'year') {
					return `${value}年`
				}
				if (type === 'month') {
					return `${value}月`
				}
				if (type === 'day') {
					return `${value}日`
				}
				if (type === 'hour') {
					return `${value}时`
				}
				if (type === 'minute') {
					return `${value}分`
				}
				return value
			},
			getDayOptions() {
				const now = new Date()
				const start = new Date(now.getFullYear(), now.getMonth(), now.getDate())
				const dayOptions = [{
					date: start,
					dateStr: this.getDayOptionsDateStr(start),
					weekDayStr: '今天'
				}]

				for (let i = 1; i < 5; i++) {
					const curDate = new Date(start.getTime() + i * 24 * 60 * 60 * 1000)
					dayOptions.push({
						date: curDate,
						dateStr: this.getDayOptionsDateStr(curDate),
						weekDayStr: WEEKDAYS[curDate.getDay()]
					})
				}
				return dayOptions
			},
			getDayOptionsDateStr(date) {
				return date.getMonth() + 1 + '.' + date.getDate()
			},
			onDayOptionSelected(index) {
				if (index == this.selectedDayIndex) return
				const dayOption = this.dayOptions[index]
				this.order.startTime = this.changeDate(this.order.startTime, dayOption.date).getTime()
				this.order.endTime = this.changeDate(this.order.endTime, dayOption.date).getTime()
				if (dayOption.getDate() != new Date(this.period.date).getDate()) {
					this.getHourStatus()
				} else {
					this.updateHourStatus()
				}
			},
			changeDate(old, toChange) {
				old = new Date(old)
				return new Date(toChange.getFullYear(), toChange.getMonth(), toChange.getDate(), old.getHours(), old
					.getMinutes())
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