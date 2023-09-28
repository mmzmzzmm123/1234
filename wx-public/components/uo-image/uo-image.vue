<template>
	<view class="uo-image" :class="isError?'uo-image--error':''">
		<u-icon v-if="isError" name="photo"></u-icon>
		<image v-else class="uo-image_image" :src="innerSrc" :mode="mode" @error="isError = true"></image>
	</view>
</template>

<script>
	import { BaseApiUrl } from "@/common/config.js"
	
	export default {
		name: 'uo-image',
		props: {
			// 图片地址
			src: {
			    type: String,
			    default: ''
			},
			// 裁剪模式
			mode: {
			    type: String,
			    default: 'aspectFill'
			},
		},
		data() {
			return {
				isError: false,
				innerSrc: ''
			}
		},
		watch: {
			src: {
				immediate: true,
				handler(n) {
					if (!n) {
						this.isError = true
					}else{
						if(n.indexOf('http') >= 0 || n.indexOf('data:') >= 0){
							this.innerSrc = n
						}else{
							this.innerSrc = BaseApiUrl + n
						}
					}
				}
			}
		},
		created() {
	
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
	.uo-image{
		width: 200rpx;
		height: 200rpx;
		background: $u-bg-color;
		&--error{
			display: flex;
			align-items: center;
			justify-content: center;
		}
	}
	.uo-image_image{
		width: 100%;
		height: 100%;
	}
</style>