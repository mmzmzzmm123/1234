<template>
	<view>
		<view>
			<view class="card-list">
				<navigator class="card" url="room-list/index">
					<view class="card-title">包厢专用套餐</view>
					<view class="card__content">
						<view class="card__content__body">
							设置单个包厢在全天时段或不同时段专用的套餐
						</view>
					</view>
				</navigator>
				<navigator class="card" url="../price/price-list/index">
					<view class="card-title">美团/抖音专用套餐</view>
					<view class="card__content">
						<view class="card__content__body">
							查看门店在美团/抖音商家的团购专用套餐
						</view>
					</view>
				</navigator>
				<navigator class="card" url="store-promotion/index">
					<view class="card-title">团购优惠券</view>
					<view class="card__content">
						<view class="card__content__body">
							设置使用门店的优惠券
						</view>
					</view>
				</navigator>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		computed: {
			currentStoreId() {
				return this.$store.state.currentStore.id
			}
		},
		watch: {
			currentStoreId: function() {
				this.refresh()
			}
		},
		onLoad() {
			this.refresh()
		},
		onPullDownRefresh() {
			this.refresh()
		},
		methods: {
			refresh() {
				if (this.currentStoreId) {

				}
			}
		}
	}
</script>

<style lang="scss">
</style>
