<template>
	<view>
		<u-subsection :list="typeList"></u-subsection>
		<u-input prefix-icon="search" placeholder="请输入门店名称"></u-input>
		<u-tabs :list="statusList"></u-tabs>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				typeList: ["棋牌", "台球"],
				statusList: [{
					name: '全部'
				},{
					name: '待开始'
				},{
					name: '进行中'
				},{
					name: '已结束'
				},{
					name: '退款中'
				},{
					name: '已退款'
				}]
			}
		},
		onLoad() {
	
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
</style>