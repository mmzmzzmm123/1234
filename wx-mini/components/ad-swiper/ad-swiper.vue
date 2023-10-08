<template>
	<u-swiper v-if="showAdList && showAdList.length" :list="showAdList" keyName="img" indicator indicatorMode="line" height="300rpx" bgColor="#fff" imgMode="aspectFill"
		radius="0" :interval="imgList?2000:3000"
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
		watch: {
			imgList(newVal){
				this.refresh()
			}
		},
		data() {
			return {
				adList: null
			}
		},
		computed: {
			showAdList(){
				if(this.adList){
					return this.adList
				}else{
					return this.$store.state.shouyeguanggao
				}
			}
		},
		created() {
			this.refresh()
		},
		methods: {
			refresh(){
				if(this.imgList){
					this.adList = this.imgList.map(x=>{
						return {
							img: x
						}
					})
				}
			}
		}
	}
</script>

<style lang="scss">
</style>