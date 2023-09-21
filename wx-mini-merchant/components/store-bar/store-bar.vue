<template>
	<view class="store-bar">
		<view class="store-bar_current" @click="show = true">
			<view class="iconfont icon-shop"></view>
			<view>【{{currentStore.name}}】</view>
			<view class="store-bar_expend" :class="show ? 'store-bar_expend--up':''">
				<u-icon name="arrow-down-fill" color="#fff"></u-icon>
			</view>
		</view>
		<view v-if="show" class="store-bar_select" @click="show = false">
			<view class="store-bar_option" v-for="store in storeList" :key="store.id" @click.stop="onChoseStore(store)">
				{{store.name}}
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'store-bar',
		props: {
			
		},
		data() {
			return {
				show: false
			}
		},
		computed: {
			currentStore(){
				return this.$store.state.currentStore
			},
			storeList(){
				return this.$store.state.storeList
			}
		},
		created() {
	
		},
		methods: {
			onChoseStore(store){
				this.$store.commit('$uStore', {
					name: 'currentStore',
					value: store
				})
				this.show = false
			}
		}
	}
</script>

<style lang="scss">
	.store-bar{
		color: $u-bright;
		background: $u-primary;
		.iconfont{
			color: $u-bright;
		}
		&_current{
			padding: 0 20rpx;
			height: 80rpx;
			display: flex;
			align-items: center;
		}
		&_option{
			padding: 20rpx;
			color: $u-main-color;
			background: $u-bright;
			&:not(:last-child){
				border-bottom: 1rpx solid $u-border-color;
			}
		}
		&_expend{
			transition: all 300ms;
			&--up{
				transform: rotate(180deg);
			}
		}
		&_select{
			position: fixed;
			top: 80rpx;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 100;
		}
	}
</style>