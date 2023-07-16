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
			// adList(){
			// 	return this.$store.state.adList
			// }
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
				}else{
					this.$api.getNoticeList().then(res=>{
						this.adList = res.rows.map(x=>{
							return {
								img:  x.remark
							}
						})
					})
				}
			}
		}
	}
</script>

<style lang="scss">
</style>