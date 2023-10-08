<template>
	<view>
		<view class="form">
			<u-form label-width="auto" :model="model">
				<u-form-item label="所在城市" prop="city" required>
					<u-input v-model="model.city" placeholder="请输入所在城市"></u-input>
				</u-form-item>
				<u-form-item label="姓名" prop="name" required>
					<u-input v-model="model.name" placeholder="请输入姓名"></u-input>
				</u-form-item>
				<u-form-item label="联系方式" prop="phone" required>
					<u-input v-model="model.phone" placeholder="请输入联系方式"></u-input>
				</u-form-item>
				<u-form-item label="留言" prop="remark">
					<u-textarea v-model="model.remark" placeholder="请输入留言"></u-textarea> 
				</u-form-item>
				
			</u-form>
		</view>
		<view class="edit-bottom-bar-holder"></view>
		<view class="edit-bottom-bar">
			<u-button type="primary" @click="onConfirmClick">发送</u-button>
			<u-button type="info" @click="onCancelClick">取消</u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				model: {
					city:'',
					name: '',
					phone: '',
					remark: '',
				},
			}
		},
		methods: {
			onConfirmClick(){
					this.$api.addJoinUs(this.model).then(this.onSuccess).catch(()=>{
						this.$u.toast('请完善加盟信息')
				})
			},
			onSuccess(){
				this.$u.toast('发送成功')
				// this.getOpenerEventChannel().emit('refresh')
				uni.navigateBack()
			},
			onCancelClick(){
				uni.navigateBack()
			},
		}
	}
</script>

<style>

</style>
