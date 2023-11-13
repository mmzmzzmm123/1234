<template>
	<view>
		<ad-swiper></ad-swiper>
		<view class="form">
			<u-form label-width="auto" :model="model" label-position="top">
				<u-form-item label="是否已选好场地" prop="placeReady" required :borderBottom="false">
					<u-radio-group v-model="model.placeReady">
						<u-radio label="是" name="是"></u-radio>
						<u-radio label="否" name="否"></u-radio>
					</u-radio-group>
				</u-form-item>
				<u-form-item label="预计开店预算" prop="budget" required :borderBottom="false">
					<u-radio-group v-model="model.budget">
						<u-radio label="2-5万" name="2-5万"></u-radio>
						<u-radio label="5-10万" name="5-10万"></u-radio>
						<u-radio label="10-20万" name="10-20万"></u-radio>
						<u-radio label="20-30万" name="20-30万"></u-radio>
						<u-radio label="30万以上" name="30万以上"></u-radio>
					</u-radio-group>
				</u-form-item>
				<u-form-item label="是否有来十三将体验或考察过" prop="isExperience" required>
					<u-radio-group v-model="model.isExperience">
						<u-radio label="是" name="是"></u-radio>
						<u-radio label="否" name="否"></u-radio>
					</u-radio-group>
				</u-form-item>
				<u-form-item label="希望开展哪些业务" prop="expBusiness" required>
					<u-radio-group v-model="model.expBusiness">
						<u-radio label="自助棋牌" name="自助棋牌"></u-radio>
						<u-radio label="自助台球" name="自助台球"></u-radio>
						<u-radio label="棋牌+台球" name="棋牌+台球"></u-radio>
					</u-radio-group>
				</u-form-item>
				<u-form-item label="之前是否有联系过其他十三将客服" prop="isContact" required>
					<u-radio-group v-model="model.isContact">
						<u-radio label="是" name="是"></u-radio>
						<u-radio label="否" name="否"></u-radio>
					</u-radio-group>
				</u-form-item>
				<u-form-item label="所在城市" prop="city" required>
					<u-input v-model="model.city" placeholder="请输入所在城市"></u-input>
				</u-form-item>
				<u-form-item label="姓名" prop="name" required>
					<u-input v-model="model.name" placeholder="请输入姓名"></u-input>
				</u-form-item>
				<u-form-item label="联系方式" prop="phone" required>
					<u-input v-model="model.phone" placeholder="请输入联系方式"></u-input>
				</u-form-item>
				<u-form-item label="详细地址" prop="address" required>
					<u-input v-model="model.address" placeholder="请输入详细地址"></u-input>
				</u-form-item>
				<u-form-item label="留言" prop="remark">
					<u-textarea v-model="model.remark" placeholder="请输入留言"></u-textarea> 
				</u-form-item>
			</u-form>
		</view>
		<view class="edit-bottom-bar-holder"></view>
		<view class="edit-bottom-bar">
			<u-button type="primary" @click="onConfirmClick">确&nbsp;&nbsp;&nbsp;&nbsp;定</u-button>
			<!-- <u-button type="info" @click="onCancelClick">取消</u-button> -->
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
.u-form-item{
	border-bottom: none!important;
}
</style>
