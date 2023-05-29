<template>
	<view class="mask mask--show" v-if="!jobNumber">
		<view class="modal" @click.stop>
			<view class="title">{{$t("authorize.title")}}</view>
			<u-input type="number" customStyle="margin: 50rpx 0" :placeholder="$t('authorize.jobNumberPlh')"
				v-model="inputJobNumber"></u-input>
			<u-button type="primary" @click="onConfirm">{{$t("confirm")}}</u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				inputJobNumber: ''
			}
		},
		computed: {
			jobNumber(){
				const loginUser = this.$store.state.loginUser
				if(loginUser){
					return loginUser.jobNumber
				}
				return ''
			}
		},
		created() {
	
		},
		methods: {
			onConfirm(){
				const jobNumber = this.inputJobNumber.trim()
				if(!jobNumber){
					uni.showToast({
						icon: "none",
						title: this.$t("authorize.jobNumberEmpty")
					})
					return
				}
				this.$api.bindJobNumber(jobNumber).then(res=>{
					this.$store.dispatch("getUserInfo")
					this.$emit("jobNumberBound")
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
	}
</style>