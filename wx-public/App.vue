<script>
	const indexPath = 'pages/index/index'
	export default {
		onLaunch(options) {
			debugger
			if(options.path != indexPath){
				let query = ''
				for(let i in options.query){
					query += i + "=" + options.query[i]
				}
				this.$api.setAfterLoginToGo(query ? options.path + '?' + query : options.path)
			}
			if(options.path != 'pages/login/mp-login'){
				if(!this.$store.state.wxUser){
					
					this.$api.toLogin()
				}else {
					this.$store.dispatch("getUserInfo")
					if(options.path == indexPath){
						if(!this.$store.state.loginUser.userId){
							if(this.$store.state.storeRoles.length){
								uni.reLaunch({
									url: "/pages/clean/index"
								})
							}else{
								uni.reLaunch({
									url: "/pages/login/bind-merchant"
								})
							}
						}
					}
				}
			}
		}
	}
</script>

<style lang="scss">
	@import "@/uni_modules/uview-ui/index.scss";
	@import "/static/common/css/iconfont.css"
</style>