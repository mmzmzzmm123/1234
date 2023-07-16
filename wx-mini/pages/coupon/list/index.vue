<template>
	<view>
		<view class="u-p-h-20">
			<coupon-cell v-for="coupon in couponList" :key="coupon.id" :coupon="coupon"></coupon-cell>
		</view>
		<u-empty custom-style="padding-top: 400rpx;" v-if="!couponList || !couponList.length" mode="coupon" text="暂无卡券"></u-empty>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				queryParam: {
					storeId: 0,
					storeName: '',
					pageSize: 1000,
					pageNo: 1
				},
				couponList: null
			}
		},
		onLoad() {
			this.refresh()
		},
		methods: {
			refresh(){
				this.$api.getValidCouponList(this.queryParam).then(res=>{
					this.couponList = res.rows
				})
			}
		}
	}
</script>

<style lang="scss">
	page{
		background: $u-bg-color;
	}
</style>