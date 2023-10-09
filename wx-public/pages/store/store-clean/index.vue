<template>
	<view>
		<view class="form">
			<u-text size="20" text="非保洁时间段设置"></u-text>
			<u-form ref="form" label-width="auto">
				<u-form-item label="开始时间" prop="startTime" required>
					<u-input v-model="store.startTime"></u-input>
				</u-form-item>
				<u-form-item label="结束时间" prop="stopTime" required>
					<u-input v-model="store.stopTime"></u-input>
				</u-form-item>
			</u-form>
			<u-text text="提示" size="20"></u-text>
			<u-textarea v-text="tip"></u-textarea>
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
				tip:"非保洁时间段设置成功后，用户预约订单时间在非保洁时间段时会弹窗提示[该包厢未打扫，是否继续预约订单]当非保洁时间段设置为00:00-00:00时，则表示非保洁时间段已重置，保洁端[暂停打扫]和[恢复打扫]状态将失效",
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
			
		},
		methods: {
			getStore(id){
				this.$api.getStore(id).then(res=>{
					this.store = res
				})
			},
			onConfirmClick(){
				if(this.store.id){
					this.$api.updateStoreInfo(this.store).then(this.onSuccess).catch(res=>{
						this.$u.toast('请检查非保洁时间')
					})
				}
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

<style>
</style>