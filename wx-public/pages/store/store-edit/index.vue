<template>
	<view v-show="isShow">
		<view class="form">
			<u-form ref="form" label-width="auto" :rules="rules">
				<u-form-item label="门店名称" prop="name" required>
					<u-input v-model="model.name"></u-input>
				</u-form-item>
				<u-form-item label="电话" prop="name" required>
					<u-input v-model="model.phone"></u-input>
				</u-form-item>
				<u-form-item label="门店地址" prop="address" required>
					<u-input v-model="model.address"></u-input>
				</u-form-item>
				<u-form-item label="门店详情" prop="name">
					<u-input v-model="model.remark"></u-input>
				</u-form-item>
				<u-form-item label="wifi" prop="name">
					<u-input v-model="model.wifi"></u-input>
				</u-form-item>
				<u-form-item label="提前预约天数" prop="name">
					<u-input v-model="model.preDays"></u-input>
				</u-form-item>
				<u-form-item label="经度" prop="name" required>
					<u-input v-model="model.longitude"></u-input>
				</u-form-item>
				<u-form-item label="纬度" prop="name" required>
					<u-input v-model="model.latitude"></u-input>
				</u-form-item>
				<u-form-item label="门店图片" prop="logo">
					<uo-image-input v-model="model.logo"></uo-image-input>
				</u-form-item>
				<u-form-item label="门店状态" prop="status">
					<uo-select :options="statusOptions" v-model="model.status"></uo-select>
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
				model: {
					name: '店铺名称',
					logo: '',
					status: 1
				},
				isShow:false,
				store:[],
				rules: {
					name: {
						required: true,
						message: '请输入门店名称',
						trigger: ['blur', 'change']
					}
				},
				statusOptions: [{name: '状态1', value: 1},{name: '状态2', value: 2},{name: '状态3', value: 3}]
			}
		},
		onLoad(option) {
			console.log(this.store)
			if(option.id){
				
			}
		},
		onReady() {
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			onConfirmClick(){
				console.log(this.model)
				this.$api.updateStoreInfo(this.model).then(res=>{
					this.$u.toast('保存成功')
					this.isShow=false
					this.getOpenerEventChannel().emit('refresh')
					uni.navigateBack()
				}).catch(res=>{
					this.$u.toast('请完善门店信息')
				})
				
				// this.$refs.form.validate().then(res=>{
				// 	debugger
				// 	//保存
				// 	this.$api.updateStoreInfo(this.model).then(res=>{
				// 		this.$u.toast('保存成功')
				// 		this.isShow=false
				// 	})
				// 	this.getOpenerEventChannel().emit('refresh')
				// 	uni.navigateBack()

				// }).catch(res=>{
				// 	this.$u.toast('请完善门店信息')
				// })
			},
			onCancelClick(){
				this.isShow=false
			},
			switchShow(val,data){
				if(val){
					this.model=data
				}
				this.isShow=val
			}
		}
	}
</script>

<style lang="scss">
</style>