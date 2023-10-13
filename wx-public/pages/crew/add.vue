<template>
	<view>
		<view class="form">
			<u-form ref="roomForm" label-width="auto" :model="obj">
				<u-form-item label="用户账号" prop="userName" required>
					<u-input v-model="obj.userName" placeholder="请输入用户账号"></u-input>
				</u-form-item>
				<u-form-item label="用户密码" prop="password" required>
					<u-input v-model="obj.password" type="password" placeholder="请输入用户密码"></u-input>
				</u-form-item>
				<u-form-item label="用户姓名" prop="nickName" required>
					<u-input v-model="obj.nickName" placeholder="请输入用户姓名"></u-input>
				</u-form-item>
				<u-form-item label="手机号码" prop="phonenumber" required>
					<u-input v-model="obj.phonenumber" placeholder="请输入手机号码"></u-input>
				</u-form-item>
				<u-form-item label="所属门店" prop="storeId" required>
					<uo-select placeholder="请选择所属门店" v-model="obj.storeId" :options="storeList" value-field="id">
					</uo-select>
				</u-form-item>
				<u-form-item label="角色" prop="roleName" required>
					<u-checkbox-group v-model="roleArray">
						<u-checkbox v-for="item in roles" :key="item.roleId" :name="item.roleId+''" :label="item.roleName">
						</u-checkbox>
					</u-checkbox-group>
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
				obj: {
					userName: '',
					nickName: '',
					password: '',
					phonenumber: null,
					roleName: '',
					storeId: 0,
					id: 0
				},
				rules: {
					storeId: {
						type: 'number',
						required: true,
						message: '请选择所属门店',
						trigger: ['blur', 'change']
					},
					userName: {
						required: true,
						message: '请输入用户账号',
						trigger: ['blur', 'change']
					},
					password: {
						required: true,
						message: '请输入用户密码',
						trigger: ['blur', 'change']
					},
					nickName: {
						required: true,
						message: '请输入用户姓名',
						trigger: ['blur', 'change']
					},
				},
				roles: [],
				roleArray: []
			}
		},
		computed: {
			storeList() {
				return this.$store.state.storeList
			},
		},
		onLoad(options) {
			this.getH5Roles();
			this.obj.storeId = this.$store.state.currentStore.id
			if (options.id) {
				uni.setNavigationBarTitle({
					title: '编辑员工'
				})
				this.$api.getStoreUser(options.id).then(res => {
					this.obj = res;
					this.roleArray = res.roleName.split(",");
				})
			}

		},
		onReady() {
			this.$refs.roomForm.setRules(this.rules)
		},
		methods: {
			getH5Roles() {
				this.$api.getH5Roles().then(res => {
					this.roles = res.rows;
				})
			},
			onConfirmClick() {
				this.obj.roleName = this.roleArray.join(',')
				this.$refs.roomForm.validate().then(res => {
					if (this.options.id) {
						this.obj.id = this.options.id;
						this.$api.editStoreCrew(this.obj).then(this.onSuccess)
					} else {
						this.$api.addStoreCrew(this.obj).then(this.onSuccess)
					}
				}).catch((e) => {
					debugger
					this.$u.toast('请完善包厢信息')
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
