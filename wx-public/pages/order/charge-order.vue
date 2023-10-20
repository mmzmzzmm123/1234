<template>
	<view>
		<view class="form">
			<u-form ref="roomForm" label-width="auto" :model="order">
				<u-form-item label="续费时长" prop="minutes" required>
					<u-input v-model="order.minutes" placeholder="请输入包厢名称"></u-input>
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
				order: {
					id: null,
					minutes: 0
				},
				rules: {
					minutes: {
						required: true,
						message: '请录入续费时长',
						trigger: ['blur', 'change']
					}
				},
			}
		},
		onLoad(options) {
			if (options.id) {
				this.order.id = option.id;
			}
		},
		onReady() {
			this.$refs.roomForm.setRules(this.rules)
		},
		methods: {
			onConfirmClick() {
				this.$refs.roomForm.validate().then(res => {
					this.$api.chargeOrder(this.order).then(res => {
						console.log(order);
					})
				})
			},
			onSuccess() {
				this.$u.toast('保存成功')
				this.getOpenerEventChannel().emit('refresh')
				uni.navigateBack()
			},
			onCancelClick() {
				uni.navigateBack()
			}
		}
	}
</script>

<style lang="scss">
</style>
