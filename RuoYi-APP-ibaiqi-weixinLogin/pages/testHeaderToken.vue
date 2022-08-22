<template>
	<view>
		<view>测试header是否携带Authorization: </view>
	</view>
</template>

<script>
	import {
		getUserProfile
	} from "@/api/weixin/userWeixin"
	export default {
		data() {
			return {

			}
		},
		onLoad() {
			console.log("准备读取远程数据，同时查看是还带header过去");
			//请求服务器，查看请求header是否携带Authorization:
			let userProfile = getUserProfile();            Authorization:
			console.log("userProfile", userProfile);
		},

		methods: {

		}
	}
</script>

<style>

</style>
