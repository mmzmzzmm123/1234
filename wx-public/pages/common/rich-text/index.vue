<template>
<view>
	<rich-text :nodes="text"></rich-text>
</view>
</template>

<script>
	export default{
		data(){
			return {
				text: ''
			}
		},
		onLoad(options) {
			if(options.title){
				uni.setNavigationBarTitle({
					title: options.title
				})
			}
			this.getOpenerEventChannel().on('setText', text=>{
				this.text = text
			})
		},
		methods:{
			
		}
	}
</script>

<style>
</style>