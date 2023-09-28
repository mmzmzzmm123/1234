<template>
	<view>
		<view class="form">
			<u-form ref="form" :model="model" label-width="auto" :rules="rules">
				<u-form-item label="门店名称" prop="name" required>
					<u-input v-model="model.name"></u-input>
				</u-form-item>
				<u-form-item label="门店地址" prop="address" required>
					<u-input v-model="model.address"></u-input>
				</u-form-item>
				<u-form-item label="详细地址" prop="name">
					<u-input v-model="model.name"></u-input>
				</u-form-item>
				<u-form-item label="门店图片" prop="logo">
					<uo-image-input v-model="model.logo"></uo-image-input>
				</u-form-item>
			</u-form>
		</view>
		
		<view class="edit-bottom-bar-holder"></view>
		<view class="edit-bottom-bar">
			<u-button type="primary" @click="onConfirmClick">保存</u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				model: {
					name: '店铺名称',
					logo: ''
				},
				rules: {
					name: {
						required: true,
						message: '请输入门店名称',
						trigger: ['blur', 'change']
					}
				}
			}
		},
		onLoad(option) {
			if(option.id){
				
			}
		},
		onReady() {
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			onConfirmClick(){
				console.log(this.model)
				this.$refs.form.validate().then(res=>{
					debugger
					this.getOpenerEventChannel().emit('refresh')
					uni.navigateBack()
				}).catch(res=>{
					this.$u.toast('请完善门店信息')
				})
			}
		}
	}
</script>

<style lang="scss">
	.edit-bottom-bar-holder{
		height: 80rpx;
	}
	.edit-bottom-bar{
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		padding: 20rpx;
	}
</style>