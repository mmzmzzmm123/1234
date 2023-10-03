<template>
<view>
	<view class="form">
		<u-form ref="roomForm" label-width="auto" :model="room">
			<u-form-item label="所属门店" prop="storeId" required>
				<uo-select placeholder="请选择所属门店" v-model="room.storeId" :options="storeList" value-field="id"></uo-select>
			</u-form-item>
			<u-form-item label="包厢名称" prop="name" required>
				<u-input v-model="room.name" placeholder="请输入包厢名称"></u-input>
			</u-form-item>
			<u-form-item label="使用状态" prop="status" required>
				<uo-select placeholder="请选择使用状态" v-model="room.status" :options="roomStatus"></uo-select>
			</u-form-item>
			<u-form-item label="附加标签" prop="remark" required>
				<u-checkbox-group v-model="remarkArray">
					<u-checkbox v-for="item in roomMark" :key="item.value" :name="item.value" :label="item.name"></u-checkbox>
				</u-checkbox-group>
			</u-form-item>
			<u-form-item label="包厢图片" prop="logo">
				<uo-image-input v-model="room.logo"></uo-image-input>
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
	export default{
		data(){
			return {
				room: {
					name: '',
					storeId: '',
					id: null,
					remark: '',
					status: '',
					wifi: ''
				},
				rules: {
					storeId: {
						type: 'number',
						required: true,
						message: '请选择所属门店',
						trigger: ['blur', 'change']
					},
					name: {
						required: true,
						message: '请输入包厢名称',
						trigger: ['blur', 'change']
					},
					status: {
						required: true,
						message: '请选择使用状态',
						trigger: ['blur', 'change']
					},
					remark: {
						validator: ()=>{
							return this.remarkArray.length != 0
						},
						message: '请选择附加标签',
						trigger: ['blur', 'change']
					}
				},
				remarkArray: []
			}
		},
		computed: {
			storeList(){
				return this.$store.state.storeList
			},
			roomStatus(){
				return this.$store.state.dicts.room_status
			},
			roomMark(){
				return this.$store.state.dicts.room_mark
			}
		},
		onLoad(options) {
			if(options.id){
				uni.setNavigationBarTitle({
					title: '编辑包厢'
				})
				this.getRoom(options.id)
			}
			this.$store.dispatch('loadDict', 'room_status')
			this.$store.dispatch('loadDict', 'room_mark')
		},
		onReady() {
			this.$refs.roomForm.setRules(this.rules)
		},
		methods:{
			getRoom(id){
				this.$api.getRoom(id).then(res=>{
					this.room = res
					this.remarkArray = res.remark.split(',')
				})
			},
			onConfirmClick(){
				this.room.remark = this.remarkArray.join(',')
				this.$refs.roomForm.validate().then(res=>{
					if(this.room.id){
						this.$api.editRoom(this.room).then(this.onSuccess)
					}else{
						this.$api.addRoom(this.room).then(this.onSuccess)
					}
				}).catch(()=>{
					debugger
					this.$u.toast('请完善包厢信息')
				})
			},
			onSuccess(){
				this.$u.toast('保存成功')
				this.getOpenerEventChannel().emit('refresh')
				uni.navigateBack()
			},
			onCancelClick(){
				uni.navigateBack()
			}
		}
	}
</script>

<style lang="scss">
</style>