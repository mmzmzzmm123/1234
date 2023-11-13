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
				adList: [{img: 'https://foreverjade.cn/prod-api/profile/upload/2023/10/13/%E5%9B%BD%E6%BD%AE%E5%B0%8F_20231013110752A007.jpg'}]
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
			//this.refresh()
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
			},
			onAdClick(e){
				console.log(e)
			}
		}
	}
</script>

<style lang="scss">
</style>