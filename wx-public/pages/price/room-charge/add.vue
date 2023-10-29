<template>
	<view>
		<view class="form">
			<u-form ref="form" :model="model" label-width="auto">
				<u-form-item label="套餐名称" prop="name" required>
					<u-input v-model="model.name" placeholder="请输入套餐名称"></u-input>
				</u-form-item>
				<u-form-item label="套餐价格" prop="price" required>
					<u-input type="digit" v-model="model.price" placeholder="请输入套餐价格"></u-input>
				</u-form-item>
				<u-form-item label="可用时长" prop="minutes" required>
					<u-input type="digit" v-model="model.minutes"  placeholder="请输入套餐可用时长"></u-input>(分钟)
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
					id: null,
					name: '',
					price: '',
					minutes: '',
					roomId: null,
				},
				rules: {
					name: {
						required: true,
						message: '请输入套餐名称',
						trigger: ['blur']
					},
					price: {
						type: 'number',
						required: true,
						message: '请输入套餐价格',
						trigger: ['blur']
					},
					minutes: {
						type: 'number',
						required: true,
						message: '请输入可用时长',
						trigger: ['blur']
					}
				},
			}
		},
		onLoad(option) {
			if (option.roomId) {
				this.model.roomId = option.roomId;
			}
			if (option.id) {
				this.model.id = option.id;
				this.$api.getRoomChargePrice(this.model.id).then(res => {
					this.model = res;
				})
			}
		},
		onReady() {
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			onConfirmClick() {
				this.$refs.form.validate().then(res => {
					if (this.model.id) {
						this.$api.updateRoomChargePrice(this.model).then(this.gobackAndRefresh);
					} else {
						this.$api.addRoomChargePrice(this.model).then(this.gobackAndRefresh);
					}

				}).catch(res => {
					this.$u.toast("请先完善套餐信息")
				})
			},
			gobackAndRefresh() {
				this.getOpenerEventChannel().emit('refresh')
				uni.navigateBack()
			}
		}
	}
</script>

<style lang="scss">
</style>
