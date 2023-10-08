<template>
	<view class="uo-select">
		<view @click="showOptions = true">
			<slot>
				<view class="uo-select__content" :class="value ? '':'uo-select__placeholder'">
					{{displayName}}
					<u-icon name="arrow-down-fill"></u-icon>
				</view>
			</slot>
		</view>
		<u-popup :show="showOptions" :mode="mode" close-on-click-overlay @close="showOptions = false">
			<view v-for="(option,i) in options" :key="i" @click="onOptionClick(i)" class="uo-select__option">
				{{option[nameField]}}
			</view>
			<view class="uo-select__option uo-select__option-cancel" @click="onCancelClick">
				{{clearable ? '清除' : '取消'}}
			</view>
		</u-popup>
	</view>
</template>

<script>
	export default {
		name: 'uo-select',
		props: {
			options: {
				type: Array,
				default(){
					return []
				}
			},
			nameField: {
				type: String,
				default: 'name'
			},
			valueField: {
				type: String,
				default: 'value'
			},
			value: {
				type: [String, Number],
				default: ''
			},
			placeholder: {
				type: String,
				default: ''
			},
			mode: {
				type: String,
				default: 'bottom'
			},
			clearable: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				showOptions: false
			}
		},
		computed: {
			displayName(){
				const {valueField, value} = this
				const option = this.options.find(x=>x[valueField] == value)
				if(option){
					return option[this.nameField]
				}
				return this.placeholder
			}
		},
		created() {
	
		},
		methods: {
			onOptionClick(i){
				const option = this.options[i]
				this.showOptions = false
				this.$emit('input', option[this.valueField])
				this.$emit('change', option)
			},
			onCancelClick(){
				this.showOptions = false
				if(this.clearable){
					this.$emit('input', null)
					// this.$emit('change', null)
				}
			}
		}
	}
</script>

<style lang="scss">
	.uo-select{
		color: $u-main-color;
		width: 100%;
		flex: 1;
		&__option{
			text-align: center;
			padding: 30rpx;
			border-bottom: 1rpx solid $u-border-color;
			color: $u-main-color;
			&-cancel{
				border-top: 20rpx solid $u-bg-color;
			}
		}
		&__content{
			display: flex;
			align-items: center;
			.u-icon{
				margin-left: 10rpx;
			}
		}
		&__placeholder{
			color: $u-tips-color;
		}
	}
</style>