<template>
	<view class="mask mask--show" v-if="show">
		<view class="modal" @click.stop>
			<view class="title">商家申请获取您的手机号</view>
			<view class="btn-list">
				<u-button type="primary" open-type="getPhoneNumber" @getphonenumber="onGetPhoneNumber">确定</u-button>
				<u-button type="info" @click="onCancelClick">取消</u-button>
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
			show(){
				const loginUser = this.$store.state.loginUser
				if(loginUser){
					return this.$store.state.askPhoneNumber && !loginUser.phone
				}
				return false
			}
		},
		created() {
	
		},
		methods: {
			onGetPhoneNumber(e){
				if(e.detail.code){
					this.$api.bindPhoneNumber(e.detail.code).then(res=>{
						this.onCancelClick()
						this.$store.dispatch("getUserInfo")
					})
				}else{
					this.onCancelClick()
				}
			},
			onCancelClick(){
				this.$store.commit('$uStore', {
					name: 'askPhoneNumber',
					value: false
				})
			},
			onSubClick(){
				uni.requestSubscribeMessage({
					tmplIds: ['58BvI5jDnq61I9slOIIjf89J9ionC95R14eUJ9rQLWA'],
					fail(res){
						debugger
						console.log(res)
					},
					success(res){
						debugger
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	.mask{
		position: fixed;
		display: flex;
		align-items: center;
		justify-content: center;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 9999;
		&--show{
			background: #0000007a
		}
	}
	.modal{
		box-sizing: border-box;
		background: #fff;
		border-radius: 20rpx;
		width: 680rpx;
		padding: 50rpx 30rpx;
	}
	.title{
		text-align: center;
		color: $u-main-color;
		margin-bottom: 50rpx;
	}
	.btn-list{
		display: flex;
		button:not(:last-child){
			margin-right: 30rpx;
		}
	}
</style>