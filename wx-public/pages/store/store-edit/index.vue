<template>
	<view>
		<view class="form">
			<u-form ref="form" label-width="auto" :rules="rules" :model="store">
				<u-form-item label="门店名称" prop="name" required>
					<u-input v-model="store.name"></u-input>
				</u-form-item>
				<u-form-item label="电话" prop="phone" required>
					<u-input v-model="store.phone"></u-input>
				</u-form-item>
				<u-form-item label="门店地址" prop="address" required>
					<u-input v-model="store.address"></u-input>
				</u-form-item>
				<u-form-item label="门店详情" prop="remark">
					<u-input v-model="store.remark"></u-input>
				</u-form-item>
				<u-form-item label="wifi" prop="wifi">
					<u-input v-model="store.wifi"></u-input>
				</u-form-item>
				<u-form-item label="提前预约天数" prop="preDays">
					<u-input v-model="store.preDays"></u-input>
				</u-form-item>
				<u-form-item label="经度" prop="longitude" required>
					<u-input v-model="store.longitude"></u-input>
				</u-form-item>
				<u-form-item label="纬度" prop="latitude" required>
					<u-input v-model="store.latitude"></u-input>
				</u-form-item>
				<u-form-item label="门店图片" prop="logo">
					<uo-image-input v-model="store.logo"></uo-image-input>
				</u-form-item>
				<u-form-item label="门店状态" prop="status">
					<uo-select :options="statusOptions" v-model="store.status"></uo-select>
				</u-form-item>
			</u-form>
		</view>
		
		<view class="edit-bottom-bar-holder"></view>
		<view class="edit-bottom-bar">
			<u-button type="primary" @click="onConfirmClick">保存</u-button>
			<u-button type="info" @click="onCancelClick">取消</u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				store: {
					name: '店铺名称',
					phone: '',
					address: '',
					remark: '',
					wifi: '',
					preDays: 0,
					logo: '',
					status: 0
				},
				rules: {
					name: {
						required: true,
						message: '请输入门店名称',
						trigger: ['blur', 'change']
					},
					// status: {
					// 	required: true,
					// 	message: '请选择门店状态',
					// 	trigger: ['blur', 'change']
					// },
					longitude: {
						required: true,
						message: '请输入门店经度',
						trigger: ['blur', 'change']
					},
					latitude: {
						required: true,
						message: '请输入门店纬度',
						trigger: ['blur', 'change']
					},
				},
				statusOptions: [{name: '启用', value: 0},{name: '停用', value: 1}]
			}
		},
		onLoad(option) {
			if (option.id) {
				this.store.id = option.id;
				this.$api.getStore(this.store.id).then(res => {
					this.store = res;
				})
			}
		},
		onReady() {
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			getStore(id){
				this.$api.getStore(id).then(res=>{
					this.store = res
				})
			},
			onConfirmClick(){
				this.$refs.form.validate().then(res=>{
					if(this.store.id){
						this.$api.updateStoreInfo(this.store).then(this.onSuccess)
					}else{
						//this.$api.addRoom(this.store).then(this.onSuccess)
					}
				}).catch(res=>{
					this.$u.toast('请完善门店信息')
				})
			},
			onSuccess(){
				this.$u.toast('保存成功')
				this.getOpenerEventChannel().emit('refresh')
				uni.navigateBack()
			},
			onCancelClick(){
				uni.navigateBack()
			},
		}
	}
</script>

<style lang="scss">
</style>