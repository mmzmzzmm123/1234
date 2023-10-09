<template>
	<view>
		<view style="width: 100%;" @click="show = true">
			<slot>
				<view class="uo-select__content">
					<text class="uo-select__content__text">{{timeStr}}</text>
					<u-icon name="arrow-down-fill"></u-icon>
				</view>
			</slot>
		</view>
		<u-datetime-picker :show="show" :mode="mode" closeOnClickOverlay @confirm="confirm" @cancel="cancel"
			@change="change" @close="close"></u-datetime-picker>
	</view>
</template>

<script>
	export default {
		name: "uo-datetime",
		props: {
			mode: {
				type: String,
				default: 'datetime'
			},
			value: {
				type: [String, Number],
				default: ''
			},
		},
		data() {
			return {
				show: false,
			};
		},
		computed: {
			timeStr() {
				return this.result(this.value, this.mode);
			}
		},
		methods: {
			close() {
				this.show = false
			},
			cancel() {
				this.show = false
			},
			confirm(e) {
				this.value = this.result(e.value, e.mode)
				this.close()
			},
			change(e) {
				// console.log('change', e)
			},
			filter(mode, options) {
				if(mode==='year')
				return options.filter((option) => option % 2 === 0);
				if (this.filter) {
					debugger
					if(mode==='year')
					return options.filter((option) => option % 2 === 0);
				}
				return options;
			},
			result(time, mode) {
				const timeFormat = uni.$u.timeFormat
				switch (mode) {
					case 'datetime':
						return timeFormat(time, 'yyyy-mm-dd hh:MM')
					case 'date':
						return timeFormat(time, 'yyyy-mm-dd')
					case 'year-month':
						return timeFormat(time, 'yyyy-mm')
					case 'time':
						return time
					default:
						return ''
				}
			},
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
			&__text{
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
			}
		}
		&__placeholder{
			color: $u-tips-color;
		}
	}
</style>
