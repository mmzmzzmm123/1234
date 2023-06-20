<template>
	<view>
		<ad-swiper></ad-swiper>
		<view class="u-p-h-20">
			<shop-cell></shop-cell>
		</view>
		<!-- <yto-authorize></yto-authorize> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		computed: {
			hasLogin(){
				return this.$store.state.hasLogin
			},
			loginUser(){
				return this.$store.state.loginUser
			}
		},
		watch: {
			
		},
		onLoad() {
			
		},
		onPullDownRefresh() {
			
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
	page{
		background-color: $u-bg-color;
	}
</style>
