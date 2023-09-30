<template>
	<view>
		<view>

			<view class="card-list">
				<view class="card">
					<view class="card__content">
						<view class="card__content__right">
							<view class="card__content__head">
								<navigator url="../store/store-list/index">门店管理</navigator>
							</view>
						</view>
					</view>
				</view>
				<view class="card">
					<view class="card__content">
						<view class="card__content__right">
							<view class="card__content__head">
								<navigator url="../price/index">定价管理</navigator>
							</view>
						</view>
					</view>
				</view>
				<view class="card">
					<view class="card__content">
						<view class="card__content__right">
							<view class="card__content__head">

								<navigator url="../order/index">订单管理</navigator>
							</view>
						</view>
					</view>
				</view>
				<view class="card">
					<view class="card__content">
						<view class="card__content__right">
							<view class="card__content__head">
								<navigator url="../clean/index">打扫记录</navigator>
							</view>
						</view>
					</view>
				</view>
			</view>


			<view></view>
			<text></text>

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
	page {
		background-color: $u-bg-color;
	}
</style>
