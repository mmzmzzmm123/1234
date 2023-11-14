<template>
	<view>
		<!-- <u-subsection :list="typeList"></u-subsection> -->
		<u-input prefix-icon="search" placeholder="请输入门店名称" v-model="queryParam.keyword" @input="onKeywordInput"></u-input>
		<u-tabs :list="statusList" @change="onStatusChange"></u-tabs>
		<view class="u-p-h-20">
			<order-cell v-for="order in orderList" :key="order.id" :orderInfo="order" @payOrder="onPayOrder"></order-cell>
		</view>
		<pay-bar v-model="showPay" :amount="currentOrder.payAmount" :storeId="currentOrder.storeId" @prepareOrder="onPrepareOrder" @success="onPaySuccess"></pay-bar>
		<u-empty custom-style="padding-top: 400rpx;" v-if="!orderList || !orderList.length" text="暂无订单"></u-empty>
		<require-authorize></require-authorize>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				typeList: ["棋牌", "台球"],
				statusList: [{
					name: '全部',
					status: -1
				},{
					name: '待支付',
					status: 1
				},{
					name: '已预约',
					status: 2
				},{
					name: '使用中',
					status: 3
				},{
					name: '过期',
					status: 4
				},{
					name: '取消',
					status: 9
				}],
				orderList: [],
				queryParam: {
					keyword: '',
					status: -1,
					pageNum: 1,
					pageSize: 10
				},
				total: 0,
				showPay: false,
				currentOrder: null
			}
		},
		onPullDownRefresh() {
			this.refresh()
		},
		onReachBottom() {
			if(this.orderList.length < this.total){
				this.refresh(true)
			}
		},
		onLoad() {
			this.refresh()
		},
		methods: {
			refresh(nextPage){
				if(nextPage){
					this.queryParam.pageNum++
				}else{
					this.queryParam.pageNum = 1
				}
				this.$api.getOrderList(this.queryParam).then(res=>{
					if(nextPage){
						this.orderList.push(...res.rows)
					}else{
						this.orderList = res.rows
					}
					this.total = res.total
					uni.stopPullDownRefresh()
				})
			},
			onStatusChange(e){
				this.queryParam.status = e.status
				this.refresh()
			},
			onKeywordInput(){
				this.$u.debounce(this.refresh, 400)
			},
			onPayOrder(order){
				this.currentOrder = order
				this.showPay = true
			},
			onPrepareOrder(e){
				e.order = {
					orderId: this.currentOrder.id,
					payType: e.payType
				}
			},
			onPaySuccess(){
				uni.showToast({
					icon: "none",
					title: "支付成功"
				})
				this.refresh()
				this.showPay = false
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: $u-bg-color;
	}
</style>