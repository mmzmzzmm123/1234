<template>
	<view>
		<view class="form">
			<u-form ref="form" :model="model" label-width="auto">
				<u-form-item label="套餐名称" prop="name" required>
					<u-input v-model="model.name"></u-input>
				</u-form-item>
				<u-form-item label="套餐价格" prop="price" required>
					<u-input type="digit" v-model="model.price"></u-input>
				</u-form-item>
				<u-form-item label="可用时长" prop="minutes">
					<u-input type="digit" v-model="model.minutes"></u-input>(分钟)
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
					name: '套餐名称',
					logo: '',
					price: '',
					minutes: ''
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
				roomId: 0,
			}
		},
		onLoad(option) {
			if (option.roomId) {
				this.roomId = option.roomId;
			}
			if (option.id) {
				this.model.id = option.id;
				this.$api.getRoomPack(this.model.id).then(res => {
					this.model = res;
				})
			}
		},
		onReady() {
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			onConfirmClick() {
				console.log(this.model)
				this.$refs.form.validate().then(res => {
					if (this.model.id) {
						this.$api.updateRoomPack(this.model).then(res => {
							this.gobackAndRefresh();
						});
					} else {
						this.model.roomId = this.roomId;
						this.$api.addRoomPack(this.model).then(res => {
							this.gobackAndRefresh();
						});
					}

				}).catch(res => {
					this.$u.toast(res.msg)
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
