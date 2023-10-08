<template>
	<view>
		<view>
			<view class="card-list">
				<view class="card">
					<navigator class="card-title" url="room-list/index">包厢专用套餐</navigator>
					<view class="card__content">
						<!-- <uo-image src="https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png"></uo-image> -->
						<view class="card__content__right">
							<view class="card-more">
								
								设置单个包厢在全天时段或不同时段专用的套餐
								<!-- <u-icon name="arrow-right"></u-icon> -->
							</view>
						</view>
					</view>
				</view>
				<view class="card">
					<navigator class="card-title" url="../price/price-list/index">美团/抖音专用套餐</navigator>
					<view class="card__content">
						<view class="card__content__right">
							<view class="card-more">
								查看门店在美团/抖音商家的团购专用套餐
								<!-- <u-icon name="arrow-right"></u-icon> -->
							</view>
						</view>
					</view>
				</view>
				<view class="card">
					<navigator class="card-title" url="store-promotion/index">团购优惠券</navigator>
					<view class="card__content">
						<view class="card__content__right">
							<view class="card-more">
								设置使用门店的优惠券
								<!-- <u-icon name="arrow-right"></u-icon> -->
							</view>
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
