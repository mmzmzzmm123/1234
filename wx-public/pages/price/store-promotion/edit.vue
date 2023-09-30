<template>
	<view>
		<view class="form">
			<u-form ref="form" :model="model" label-width="auto" :rules="rules">
				<u-form-item label="优惠券名称" prop="name" required>
					<u-input v-model="model.name"></u-input>
				</u-form-item>
				<u-form-item label="优惠券价格" prop="standardPrice" required>
					<u-input type="number" v-model="model.standardPrice"></u-input>
				</u-form-item>
				<u-form-item label="可用时长" prop="maxMinute">
					<u-input type="number" v-model="model.maxMinute"></u-input>(分钟)
				</u-form-item>
				<u-form-item label="周内可用日期" prop="weekDays">
					<checkbox-group @change="handleChange">
						<label v-for="(week, index) in weekOptions">
							<checkbox :value="week.value" :checked="week.checked" style="transform:scale(0.8)" />
							{{week.label}}
						</label>
					</checkbox-group>
				</u-form-item>
				<u-form-item label="有效期从" prop="startDate">
					<picker mode="date" v-model="model.startDate" :value="startDate" :start="pickStartDate"
						:end="pickEndDate" @change="bindDateChange">
						<view class="uni-input">{{model.startDate==null?startDate:model.startDate}}</view>
					</picker>
				</u-form-item>
				<u-form-item label="有效期至" prop="endDate">
					<picker mode="date" v-model="model.endDate" :start="pickStartDate" :end="pickEndDate"
						@change="bindEndDateChange">
						<view class="uni-input">{{model.endDate==null?endDate:model.endDate}}</view>
					</picker>
				</u-form-item>
				<u-form-item label="适用门店">
					<uo-select :value="model.storeId" :options="storeList" valueField="id" @change="selectStore">  <!--bug : 新增时，model.storeId没有值，点击无法弹出选项 -->
					</uo-select>
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
				startDate: this.getDate({
					format: true
				}),
				endDate: this.getDate({
					format: true
				}),
				model: {
					name: '优惠券名称',
					logo: '',
					price: '',
					minutes: '',
					storeId: 0,
				},
				rules: {
					name: {
						required: true,
						message: '请输入名称',
						trigger: ['blur', 'change']
					},
					standardPrice: {
						required: true,
						message: '请输入价格',
						trigger: ['blur', 'change']
					},
					maxMinute: {
						required: true,
						message: '请输入可用时长',
						trigger: ['blur', 'change']
					}
				},
				storeList: [],
				weekDays: [],
				weekOptions: [{
					label: '周一',
					value: '1'
				}, {
					label: '周二',
					value: '2'
				}, {
					label: '周三',
					value: '3'
				}, {
					label: '周四',
					value: '4'
				}, {
					label: '周五',
					value: '5'
				}, {
					label: '周六',
					value: '6'
				}, {
					label: '周日',
					value: '7'
				}],
			}
		},
		onLoad(option) {
			this.getStoreList();
			if (option.id) {
				this.model.id = option.id;
				this.$api.getStorePromotion(this.model.id).then(res => {
					this.model = res;
					for (var i = 0; i < this.weekOptions.length; i++) {
						if (this.model.weekDays.includes(this.weekOptions[i].value)) {
							this.weekOptions[i].checked = true;
						}
					}
				})
			}
		},
		computed: {
			pickStartDate() {
				return this.getDate('start');
			},
			pickEndDate() {
				return this.getDate('end');
			}
		},
		onReady() {
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			onConfirmClick() {
				console.log(this.model)
				// console.log(this.weekDays)
				// return;
				this.$refs.form.validate().then(res => {
					if (this.model.id) {
						this.model.weekDays = this.weekDays.join(',');
						this.$api.updateStorePromotion(this.model).then(res => {
							this.gobackAndRefresh();
						});
					} else {
						this.model.weekDays = this.weekDays.join(',');
						this.$api.addStorePromotion(this.model).then(res => {
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
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();

				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year + 2;
				}
				month = month > 9 ? month : '0' + month;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			bindDateChange: function(e) {
				this.model.startDate = e.detail.value
			},
			bindEndDateChange: function(e) {
				debugger
				this.model.endDate = e.detail.value
			},
			getStoreList() {
				this.$api.getStoreList().then(res => {
					this.storeList = res.rows
				})
			},
			selectStore(event) {
				// debugger
				this.model.storeId = event.id;
			},
			handleChange(e) {
				console.log(e.detail.value)
				this.weekDays = e.detail.value;
			}
		}
	}
</script>

<style lang="scss">
	.edit-bottom-bar-holder {
		height: 80rpx;
	}

	.edit-bottom-bar {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		padding: 20rpx;
	}
</style>
