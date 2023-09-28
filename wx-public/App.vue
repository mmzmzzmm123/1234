<script>
	export default {
		onLaunch() {
			const loginUser = this.$store.state.loginUser
			if(!loginUser || !loginUser.userId){
				uni.reLaunch({
					url: '/pages/login/index'
				})
			}else{
				this.$store.dispatch("getUserInfo")
			}
		}
	}
</script>

<style lang="scss">
	@import "@/uni_modules/uview-ui/index.scss";
	@import "/static/common/css/iconfont.css"
</style>