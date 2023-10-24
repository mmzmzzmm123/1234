<template>
	<view>
		<view :class="{'tool-bar': true, 'tool-bar--hidden': hidden}">
			<button v-for="(tool, i) in tools" :key="i" class="empty-btn tool-bar_item" :class="tool.style" @click="onToolClick(i)" :open-type="tool.openType">
				<text class="iconfont" :class="tool.icon"></text>
				<view>{{tool.name}}</view>
			</button>
		</view>
		<u-popup :show="show" @close="show=false" round="10">
			<view class="tool-bar_title">请选择订单</view>
			<view class="tool-bar_order-list">
				<order-cell v-for="(order, i) in orderList" :key="order.id" :orderInfo="order" :show-btn="false"
					@click.native="onChoseOrder(i)"></order-cell>
			</view>
		</u-popup>
	</view>
</template>

<script>
	export default {
		name: 'tool-bar',
		props: {
			hidden: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				tools: [
					{
						name: '开大门',
						icon: 'icon-kaimen',
						style: 'tool-bar_item--dark',
						showList: true,
						onChoseOrder: order=>{
							this.$api.openStore(order.id).then(res=>{
								this.showToast("开门成功")
								this.show = false
							}).finally(res=>{
								this.show = false
							})
						}
					},
					{
						name: '开包厢',
						icon: 'icon-kaimen',
						style: '',
						showList: true,
						onChoseOrder: order=>{
							this.$api.openRoom(order.id).then(res=>{
								this.showToast("开门成功")
								this.show = false
							}).finally(res=>{
								this.show = false
							})
						}
					},
					{
						name: '续单',
						icon: 'icon-xushi',
						style: 'tool-bar_item--light',
						showList: false,
						onChoseOrder: ()=>{
							this.$api.getCurrentOrder().then(res=>{
								uni.navigateTo({
									url: "/pages/order/renew/index?roomId=" + res.roomId
								})
							})
							
						}
					},
					{
						name: '分享',
						icon: 'icon-fenxiang',
						style: 'tool-bar_item--light',
						showList: false,
						openType: 'share'
					}
				],
				tooIndex: 0,
				orderList: [],
				show: false
			}
		},
		created() {
	
		},
		methods: {
			onToolClick(index){
				this.toolIndex = index
				const tool = this.tools[index]
				if(tool.showList){
					this.$api.getOpenOrderList().then(res=>{
						if(res.rows.length){
							this.orderList = res.rows
							this.show = true
						}else{
							this.showToast("暂无进行中的订单")
						}
					})
				}else{
					tool.onChoseOrder()
				}
				
			},
			onChoseOrder(i){
				const tool = this.tools[this.toolIndex]
				tool.onChoseOrder(this.orderList[i])
			},
			showToast(title){
				uni.showToast({
					icon: "none",
					title: title
				})
			}
		}
	}
</script>

<style lang="scss">
	.empty-btn{
		padding: 0;
		text-align: initial;
		line-height: initial;
		font-size: initial;
		border-radius: 0;
		color: initial;
		background-color: initial;
		border: none;
	}
	.empty-btn::after{
		content: "";
		border: none;
		border-radius: 0;
	}
	.tool-bar{
		position: fixed;
		// padding: 10rpx;
		right: 0;
		bottom: 40rpx;
		transition: all 300ms;
		&--hidden{
			right: -100%;
		}
		&_item{
			display: flex;
			flex-direction: column;
			justify-content: center;
			margin: 20rpx;
			background: $u-primary;
			width: 100rpx;
			height: 100rpx;
			border-radius: 50rpx;
			text-align: center;
			font-size: 22rpx;
			color: $u-bright;
			.iconfont{
				color: $u-bright;
				font-size: 30rpx;
			}
			&--dark{
				background: $u-primary-dark;
			}
			&--light{
				background: $u-primary-light;
			}
		}
	}
	.tool-bar_title{
		text-align: center;
		padding: 20rpx;
	}
	.tool-bar_order-list{
		max-height: 800rpx;
		overflow-y: scroll;
		background: $u-bg-color;
		padding: 0 20rpx;
	}
</style>