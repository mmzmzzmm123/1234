<template>
	<view>
		<view class="card">
			<block v-if="isPackagePay">
				<view class="price-title">选择套餐</view>
				<view v-for="price in roomInfo.packageList" :key="price.id"
					class="price-bar" :class="currentPackage.id == price.id ? 'price-bar--selected':''"
					@click="onPackageSelected(price)">
					<text>{{price.name}}</text>
					<text style="float: right;">{{price.price}}元/场</text>
				</view>
			</block>
			<block v-else>
				<view class="price-title">计费</view>
				<view v-for="price in roomInfo.priceList" :key="price.id" class="price-bar">
					<text>时段：{{price.startTime + ':00-' + (price.stopTime==24?0:price.stopTime+1) +':00'}}</text>
					<text style="float: right;">{{price.price}}元/小时</text>
				</view>
			</block>
		</view>
		<view class="card" v-if="promotionList && promotionList.length > 0">
			<view class="price-title">优惠套餐</view>
			<image v-for="item in promotionList" :key="item.id" :src="item.logo" mode="aspectFill"
				class="promotion-img" :class="item.id == currentPromotion.id ? 'promotion-img--selected':''"
				@click="onPromotionSelected(item)"></image>
		</view>
		<view class="card">
			<view>选择时间</view>
			<view>
				<view class="time-input">
					<view class="time-input_label">开始时间</view>
					<view class="time-input_value" @click="startShow = true">
						{{$u.timeFormat(order.startTime, 'yyyy-mm-dd hh:MM')}}
					</view>
				</view>
				<view v-if="isPackagePay" class="time-input time-input--readonly">
					<view class="time-input_label">结束时间</view>
					<view class="time-input_value">
						{{$u.timeFormat(order.endTime, 'yyyy-mm-dd hh:MM')}}
					</view>
				</view>
				<view v-else class="time-input">
					<view class="time-input_label">结束时间</view>
					<view class="time-input_value" @click="endShow = true">
						{{$u.timeFormat(order.endTime, 'yyyy-mm-dd hh:MM')}}
					</view>
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
					预定时间与已被预定时间重复
				</view>
			</view>
		</view>
		<view class="bottom">
			<view class="u-success">¥{{amount}}</view>
			<view style="width: 200rpx;">
				<u-button type="primary" text="立即预定" @click="onAddOrder"></u-button>
			</view>
		</view>
		<u-datetime-picker :value="order.startTime" :min-date="minDate" :max-date="maxDate" :show="startShow"
			@close="onClosePicker(true)" @cancel="onClosePicker(true)" @confirm="onConfirmPicker(true,$event)"></u-datetime-picker>
		<u-datetime-picker :value="order.endTime" :min-date="minDate" :max-date="maxDate" :show="endShow"
			@close="onClosePicker(false)" @cancel="onClosePicker(false)" @confirm="onConfirmPicker(false,$event)"></u-datetime-picker>
		
		<pay-bar v-model="payShow" :amount="amount" :storeId="roomInfo.storeId" @prepareOrder="onPrepareOrder" @success="onOrderSuccess"></pay-bar>
	</view>
</template>

<script>
	import HourStatusLegend from "../../../components/hour-status-bar/hour-status-legend"
	export default {
		components: {HourStatusLegend},
		data() {
			const now = new Date()
			const minDate = new Date(now.getFullYear(), now.getMonth(), now.getDate(), now.getHours(), 0, 0).getTime()
			return {
				roomInfo: {},
				minDate: minDate,
				maxDate: minDate + 7 * 24 * 60 * 60 * 1000,
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
				
				promotionList: [],
				currentPromotion: {}
			}
		},
		computed:{
			isPackagePay(){
				return this.roomInfo.roomPayType == 1
			}
		},
		onLoad(options) {
			if(options.scene){
				this.$api.getRoomList({id: options.scene}).then(res=>{
					if(res.rows.length == 1){
						this.onAcceptRoom(res.rows[0])
					}
				})
			}else{
				this.getOpenerEventChannel().on('acceptRoom', this.onAcceptRoom)
			}
		},
		methods: {
			onAcceptRoom(roomInfo){
				this.roomInfo = roomInfo
				this.period = roomInfo.period
				if(this.isPackagePay){
					this.onPackageSelected(this.roomInfo.packageList[0])
				}else{
					this.updateHourStatus()
				}
				this.$api.getPromotionList({storeId: roomInfo.storeId}).then(res=>{
					this.promotionList = res.rows
				})
			},
			calcAmount(){
				// 计算金额
				if(this.currentPromotion.id){
					this.amount = this.currentPromotion.standardPrice - this.currentPromotion.discountPrice
				}
				else if(this.currentPackage.id){
					this.amount = this.currentPackage.price
				}else{
					const start = new Date(this.order.startTime)
					const end = new Date(this.order.endTime)
					this.amount = 0
					if(start.getDate() < end.getDate()){
						for(let i = start.getHours(); i < 24; i++){
							this.amount += this.getPrice(i)
						}
						for(let i = 0; i < end.getHours(); i++){
							this.amount += this.getPrice(i)
						}
					}else{
						for(let i = start.getHours(); i < end.getHours(); i++){
							this.amount += this.getPrice(i)
						}
					}
				}
			},
			getPrice(hour){
				const priceList = this.roomInfo.priceList
				for(let i in priceList){
					const price = priceList[i]
					if(price.startTime <= hour && price.stopTime >= hour){
						return price.price
					}
				}
			},
			getHourStatus(){
				if(new Date(this.order.startTime).getDate() != new Date(this.period.date).getDate()){
					this.$api.getRoomAvailable({
						roomId: this.roomInfo.id,
						date: this.$u.timeFormat(this.order.startTime, 'yyyy-mm-dd')
					}).then(res=>{
						this.period = res
						this.updateHourStatus()
					})
				}
			},
			updateHourStatus(){
				this.hourRepeat = false
				const period = this.period
				const hourList = []
				const hourList2 = []
				for(let i = 0; i < 24; i++){
					hourList.push({name: i, status: period.canNotUseList.indexOf(i) >= 0})
				}
				for(let i = 0; i < 24; i++){
					hourList2.push({name: i, status: period.canNotUseList2.indexOf(i) >= 0})
				}
				this.hourList = hourList
				this.hourList2 = hourList2
				if(this.order.startTime < this.order.endTime){
					const start = new Date(this.order.startTime)
					const end = new Date(this.order.endTime)
					if(start.getDate() < end.getDate()){
						for(let i = start.getHours(); i < 24; i++){
							if(hourList[i].status){
								hourList[i].error = true
								this.hourRepeat = true
							}else{
								hourList[i].current = true
							}
						}
						for(let i = 0; i < end.getHours(); i++){
							if(hourList2[i].status){
								hourList[i].error = true
								this.hourRepeat = true
							}else{
								hourList2[i].current = true
							}
						}
					}else{
						for(let i = start.getHours(); i < end.getHours(); i++){
							if(hourList[i].status){
								hourList[i].error = true
								this.hourRepeat = true
							}else{
								hourList[i].current = true
							}
						}
					}
				}
			},
			onClosePicker(start){
				if(start){
					this.startShow = false
				}else{
					this.endShow = false
				}
			},
			onConfirmPicker(start, e){
				let startTime = this.order.startTime
				let endTime = this.order.endTime
				if(this.currentPromotion.id){ //优惠套装
					endTime = this.currentPromotion.maxMinutes * 60 * 1000
				}else if(this.currentPackage.id){ //套餐
					endTime = this.currentPackage.minutes * 60 * 1000
				}
				if(start){
					startTime = e.value
				}else{
					endTime = e.value
				}
				if(endTime - startTime > 24 * 60 * 60 * 1000){
					uni.showToast({
						icon: "none",
						title: "预定的时间不能超出24小时"
					})
					return
				}
				if(start){
					this.order.startTime = e.value
					if(this.currentPromotion.id || this.currentPackage.id){ //优惠套装，套装结束时间不用选
						this.order.endTime = endTime
					}
					this.startShow = false
					if(new Date(e.value).getDate() != new Date(this.period.date).getDate()){
						this.getHourStatus()
					}else{
						this.updateHourStatus()
					}
				}else{
					this.order.endTime = e.value
					this.endShow = false
					this.updateHourStatus()
				}
				this.calcAmount()
			},
			onAddOrder(){
				if(this.amount == 0){
					uni.showToast({
						icon: "none",
						title: "请选择预定时间"
					})
					return
				}
				if(this.hourRepeat){
					uni.showToast({
						icon: "none",
						title: "预定时间与已被预定时间重复，请重新选择时间"
					})
					return
				}
				wx.requestSubscribeMessage({
					tmplIds: ['58BvI5jDnq61I9slOIIjf89J9ionC95R14eUJ9rQLWA']
				})
				if(this.currentPromotion.id){ //优惠套餐支付
					this.$api.addPromotionOrder({
						roomId: this.roomInfo.id,
						startTime: this.$u.timeFormat(this.order.startTime, 'yyyy-mm-dd hh:MM'),
						// payType: 3 ,
						couponId: this.currentPromotion.id
					}).then(res=>{
						const param = res.jsapiResult
						param.package = param.packageValue
						delete param.packageValue
						param.success = ()=>{
							this.$api.wxPaySuccess(res).then((payRes)=>{
								this.onOrderSuccess()
							})
						}
						param.fail = function(){
							this.$toast('支付失败')
						}
						uni.requestPayment(param)
					})
				}else{
					this.payShow = true
				}
			},
			onPrepareOrder(e){
				if(this.currentPackage.id){
					e.order = {
						packId: this.currentPackage.id,
						startTime: this.$u.timeFormat(this.order.startTime, 'yyyy-mm-dd hh:MM'),
						payType: e.payType
					}	
				}else{
					e.order = {
						roomId: this.roomInfo.id,
						startTime: this.$u.timeFormat(this.order.startTime, 'yyyy-mm-dd hh:MM'),
						endTime: this.$u.timeFormat(this.order.endTime, 'yyyy-mm-dd hh:MM'),
						payType: e.payType
					}
				}
			},
			onOrderSuccess(){
				uni.showModal({
					title: '预定成功',
					showCancel: false,
					success: res=>{
						this.payShow = false
						uni.reLaunch({
							url: '/pages/order/list/index'
						})
					}
				})
			},
			onPackageSelected(pack){
				this.currentPromotion = {}
				this.currentPackage = pack
				this.order.endTime = this.order.startTime + pack.minutes * 60 * 1000
				this.calcAmount()
				this.updateHourStatus()
			},
			onPromotionSelected(promotion){
				this.currentPackage = {}
				this.currentPromotion = promotion
				this.order.endTime = this.order.startTime + promotion.maxMinutes * 60 * 1000
				this.calcAmount()
				this.updateHourStatus()
			}
		}
	}
</script>

<style lang="scss">
	page{
		background: $u-bg-color;
	}
	.card{
		margin: 20rpx;
		padding: 20rpx;
		border-radius: 10rpx;
		background: #fff;
	}
	.price-bar{
		color: $u-bright;
		background: $u-primary-light;
		margin-top: 20rpx;
		padding: 0 30rpx;
		height: 80rpx;
		line-height: 80rpx;
		border-radius: 5rpx;
		&--selected{
			background: $u-primary;
			// border: 1rpx solid $u-border-color;
		}
	}
	.time-input{
		display: flex;
		margin: 20rpx 0;
		align-items: center;
		&_label{
			margin-right: 20rpx;
			color: $u-tips-color;
		}
		&_value{
			flex: 1;
			border: 1rpx solid $u-border-color;
			border-radius: 10rpx;
			padding: 10rpx 20rpx;
		}
		&--readonly{
			.time-input_value{
				color: $u-content-color;
			}
		}
	}
	.u-m-v-20{
		margin: 20rpx 0;
	}
	.bottom{
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
	.promotion-img{
		display: block;
		width: 670rpx;
		height: 268rpx;
		margin: 20rpx 0;
		border-radius: 10rpx;
		&--selected{
			box-shadow: 0 0 16rpx 5rpx rgba(0,0,0,0.5);
		}
	}
</style>