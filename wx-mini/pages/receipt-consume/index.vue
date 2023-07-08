<template>
	<view>
		<view>
			<text :class="option.icon"></text>
		</view>
	</view>
</template>

<script>
	const PageOptions = {
		meituan: {
			title: '美团验券',
			icon: 'iconfont icon-meituan'
		},
		douyin: {
			title: '抖音验券'
		}
	}
	export default {
		data() {
			return {
				option: PageOptions.meituan
			}
		},
		onLoad(options) {
			if(options.meituan){
				this.option = PageOptions.meituan
			}else if(options.douyin){
				this.option = PageOptions.douyin
			}
			uni.setNavigationBarTitle({
				title: this.option.title
			})
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
</style>