<template>
	<view>
		<view class="form">
			<u-form ref="roomForm" label-width="200rpx" :model="obj">
				<u-form-item label="所属门店" prop="storeId" required>
					<uo-select placeholder="请选择所属门店" v-model="obj.storeId" :options="storeList" value-field="id">
					</uo-select>
				</u-form-item>
				<u-form-item label="角色" prop="role" required>
					<uo-select placeholder="请选择角色" v-model="obj.role" :options="roles" value-field="role"></uo-select>
				</u-form-item>
			</u-form>
		</view>
		<view class="link-info" v-if="toBindUrl">
			<view class="link-info__tip">保洁员关注十三将自助竞技公众号后，将链接发送给保洁员完成添加</view>
			<view class="link-info__link">{{toBindUrl}}</view>
			<view class="link-info__copy">
				<u-button @click="onCopyClick">复制链接</u-button>
			</view>
		</view>
		<view class="edit-bottom-bar-holder"></view>
		<view class="edit-bottom-bar">
			<u-button type="primary" @click="onConfirmClick">生成添加链接</u-button>
			<!-- <u-button type="info" @click="onCancelClick">取消</u-button> -->
		</view>
	</view>
</template>

<script>
	import { LoginRedirectBase } from "@/common/config.js"
	import ROLES from "./roles.js"
		
	export default {
		data() {
			return {
				obj: {
					role: 'cleaner',
					storeId: 0
				},
				rules: {
					storeId: {
						type: 'number',
						required: true,
						message: '请选择所属门店',
						trigger: ['blur', 'change']
					},
					role: {
						required: true,
						message: '请选择角色',
						trigger: ['blur', 'change']
					},
					// password: {
					// 	required: true,
					// 	message: '请输入用户密码',
					// 	trigger: ['blur', 'change']
					// },
					// nickName: {
					// 	required: true,
					// 	message: '请输入用户姓名',
					// 	trigger: ['blur', 'change']
					// },
				},
				roles: ROLES,
				toBindId: '',
				toBindUrl: ''
			}
		},
		computed: {
			storeList() {
				return this.$store.state.storeList
			},
		},
		onLoad(options) {
			this.obj.storeId = this.$store.state.currentStore.id
		},
		onReady() {
			this.$refs.roomForm.setRules(this.rules)
		},
		methods: {
			onConfirmClick() {
				this.$refs.roomForm.validate().then(res => {
					this.$api.toBindRole(this.obj).then(res=>{
						this.toBindId = res
						this.toBindUrl = `${LoginRedirectBase}/pages/crew/join?id=${res}`
					})
				}).catch((e) => {
					this.$u.toast('请完善门店和角色信息')
				})
			},
			onSuccess() {
				this.$u.toast('保存成功')
				this.getOpenerEventChannel().emit('refresh')
				uni.navigateBack()
			},
			onCancelClick() {
				uni.navigateBack()
			},
			onCopyClick(){
				uni.setClipboardData({
					data: this.toBindUrl
				})
			}
		}
	}
</script>

<style lang="scss">
	.u-form-item{
		padding: 10rpx;
	}
	.link-info{
		text-align: center;
		margin-top: 80rpx;
		&__link{
			padding: 5rpx 0;
			line-height: 60rpx;
			display: inline;
			color: #3c9cff;
			border-bottom: 1rpx solid #3c9cff;
		}
		&__copy{
			margin: 30rpx 200rpx;
		}
		&__tip{
			padding-left: 20rpx;
			text-align: left;
			color: $u-primary;
			margin-bottom: 20rpx;
		}
	}
</style>
