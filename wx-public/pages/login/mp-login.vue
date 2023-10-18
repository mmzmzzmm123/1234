<template>
	<view>
		<view @click="onCodeClick">{{code}}</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				code: ''
			}
		},
		onLoad(options) {
			// this.code = options.code
			if(options.code){
				this.$store.dispatch("login", {code: options.code}).then(()=>{
					if(this.$store.state.loginUser.userId){
						uni.reLaunch({
							url: '/pages/index/index'
						})
					}else if(this.$store.state.storeRoles.length){
						uni.reLaunch({
							url: '/pages/clean/index'
						})
					}else{
						uni.reLaunch({
							url: "/pages/login/bind-merchant"
						})
					}
				})
			}else{
				uni.navigateTo({
					url: "/pages/login/index"
				})
			}
		},
		methods: {
			onCodeClick(){
				uni.setClipboardData({
					data: this.code,
					success() {
						this.$u.toast("已复制")
					}
				})
			}
		}
	}
</script>

<style lang="scss">
</style>