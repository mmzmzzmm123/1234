<template>
	<u-swiper v-if="adList && adList.length" :list="adList" keyName="img" indicator indicatorMode="line" height="300rpx" bgColor="#fff" imgMode="aspectFill"
		radius="0"
		@click="onAdClick">
	</u-swiper>
</template>

<script>
	export default {
		props: {
			shopId: {
				type: [Number,String],
				default: null
			},
			imgList: {
				type: Array,
				default: null
			}
		},
		data() {
			return {
				adList: [
					{img: "https://cdn.uviewui.com/uview/swiper/swiper1.png"},
					// {img: "https://cdn.uviewui.com/uview/swiper/swiper2.png"},
					// {img: "https://cdn.uviewui.com/uview/swiper/swiper3.png"}
				]
			}
		},
		computed: {
			// adList(){
			// 	return this.$store.state.adList
			// }
		},
		created() {
			if(this.imgList){
				this.adList = this.imgList.map(x=>{
					return {
						img: x
					}
				})
			}
		},
		methods: {
			// onAdClick(index){
			// 	const ad = this.adList[index]
			// 	if(ad.content){
			// 		this.$store.commit("$uStore", {name: 'currentHtml', value: ad.content})
			// 		uni.navigateTo({
			// 			url: '/pages/common/html'
			// 		})
			// 	}else{
			// 		uni.previewImage({
			// 			urls: [ad.img]
			// 		})
			// 	}
			// }
		}
	}
</script>

<style lang="scss">
</style>