<template>
	<view>
		<view class="form">
			<u-form ref="form" label-width="auto" :model="order">
				<u-form-item label="所属门店" prop="storeId">
					<uo-select placeholder="请选择所属门店" :value="storeId" :options="storeList" value-field="id">
					</uo-select>
				</u-form-item>
				<u-form-item label="包厢名称" prop="roomId">
					<uo-select placeholder="请选择所属门店" v-model="order.roomId" :options="roomList" value-field="id">
					</uo-select>
				</u-form-item>
				<u-form-item label="手机号码" prop="userId" required>
					<input class="uni-input" type="number" v-model="order.userId" placeholder="请输入手机号码" />
				</u-form-item>
				<u-form-item label="开始时间" prop="startTime" required @click="startShow = true">
					<uo-datetime :show="startShow" mode="datetime" v-model="order.startTime">
					</uo-datetime>
				</u-form-item>
				<u-form-item label="结束时间" prop="endTime" required @click="endShow = true">
					<uo-datetime :show="startShow" v-model="order.endTime"  mode="datetime">
					</uo-datetime>
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
				show: false,
				order: {
					storeId: '',
					roomId: null,
					startTime: Number(new Date()),
					endTime: Number(new Date()),
				},
				rules: {
					roomId: {
						required: true,
						message: '请选择包厢',
						trigger: ['blur', 'change']
					},
					userId:{
						required: true,
						message: '请输入客人手机号码',
						trigger: ['blur', 'change']
					},
					startTime: {
						required: true,
						message: '请选择开始时间',
						trigger: ['blur', 'change']
					},
					endTime: {
						required: true,
						message: '请选择结束时间',
						trigger: ['blur', 'change']
					}
				},
				roomList: [],
				timeParam: {
					year: true,
					month: true,
					day: true,
					hour: true,
					minute: true,
				},
				startShow: false,
				endShow: false,

			}
		},
		computed: {
			storeList() {
				return this.$store.state.storeList
			},
			storeId() {
				return this.$store.state.currentStore.id
			}
		},
		onLoad(options) {
			var searchParam = {};
			searchParam.storeId = this.$store.state.currentStore.id
			this.$api.getRoomList(searchParam).then(res => {
				this.roomList = res.rows
				this.order.roomId = options.roomId;
			})
		},
		onReady() {
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			onConfirmClick() {
				this.$refs.form.validate().then(res => {
					this.$api.addRoomOrder(this.order).then(this.onSuccess)
				}).catch((e) => {
					this.$u.toast(e[0].message)
				})
			},
			onSuccess() {
				this.$u.toast('预约成功')
				// this.getOpenerEventChannel().emit('refresh')
				// uni.navigateBack()
			},
			onCancelClick() {
				uni.navigateBack()
			}
		}
	}
</script>

<style lang="scss">
</style>
