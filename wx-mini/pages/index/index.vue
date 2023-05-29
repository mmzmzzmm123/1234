<template>
	<view>
		
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
	.header{
		font-size: $y-font-m;
		border-bottom: 1rpx solid $u-border-color;
		padding: 10rpx 0;
	}
	.content{
		font-size: $y-font-s;
		margin: 10rpx 0;
		line-height: 1.5em;
	}
	.bar{
		padding-top: 20rpx;
		border-top: 1rpx solid $u-border-color;
		.u-button{
			width: 150rpx;
		}
	}
</style>
