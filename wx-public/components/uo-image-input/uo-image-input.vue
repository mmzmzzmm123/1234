<template>
	<view>
		<u-upload multiple :max-count="maxCount" :preview-full-image="true" :file-list="fileList"
			@afterRead="imageReaded" @delete="imageRemove"></u-upload>
	</view>
</template>

<script>
	import { BaseApiUrl } from "@/common/config.js"
	
	export default {
		name: '',
		props: {
			value: {
				type: String,
				default: null
			},
			maxCount: {
				type: Number,
				default: 9
			}
		},
		data() {
			return {
				
			}
		},
		computed: {
			fileList(){
				if(this.value){
					return this.value.split(',').map(x=>{
						return {
							url: BaseApiUrl + x,
							path: x
						}
					})
				}
				return []
			}
		},
		created() {
	
		},
		methods: {
			imageReaded(e){
				let files = e.file
				this.$api.uploadImage(files.map(x=>x.url)).then(res=>{
					this.$emit('input', this.value ? this.value + ',' + res.join(',') : res.join(','))
				})
			},
			imageRemove(e){
				this.$emit('input', this.fileList.splice(e.index, 1).map(x=>x.path).join(','))
			}
		}
	}
</script>

<style lang="scss">
</style>