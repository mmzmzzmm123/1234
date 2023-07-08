<template>
	<view>
		<view class="top">
			<view class="top_bar">
				<view>
					<text class="iconfont icon-fuli"></text>
					<view>优惠券</view>
				</view>
				<view>
					<text class="iconfont icon-meituan"></text>
					<view>美团验券</view>
				</view>
				<view>
					<text class="iconfont icon-xufei"></text>
					<view>续费</view>
				</view>
				<view>
					<text class="iconfont icon-huiyuan"></text>
					<view>会员充值</view>
				</view>
			</view>
			<view style="border-radius: 20rpx;overflow: hidden;">
				<ad-swiper></ad-swiper>
			</view>
		</view>
		<view class="shop">
			<view class="shop-info shop-name">店铺名称</view>
			<view class="shop-desc">
				海客谈瀛洲 烟涛微茫信难求
				越人语天姥 云霞明灭或可睹
				天姥连天向天横 势拔五岳掩赤城
				天台四万八千丈 对此欲倒东南倾
				我欲因之梦吴越 一夜飞渡镜湖月
				湖月照我影 送我至剡溪
				谢工宿处今尚在 绿水荡漾清猿啼
				脚着谢工屐 身登青云梯
				半壁见海日 空中闻天鸡
				千岩万转路不定 迷花倚石忽已瞑
				熊咆龙吟殷岩泉 栗深林兮惊层巅
				云清清兮欲语 水澹澹兮生烟
				列缺霹雳 丘峦崩摧
				洞天石扉 訇然中开
				青冥浩荡不见底 日月照耀金银台
				霓为衣兮风为马 云之君兮纷纷而来下
				虎鼓瑟兮鸾回车 仙之人兮列如麻
				忽魂悸以魄动 恍惊起而长嗟
				为觉时之枕席 失向来之烟霞
				世间行乐亦如此 古来万事东流水
				别君去兮何时还 且放白鹿青崖间 需行即骑访名山
				安能摧眉折腰事权贵 使我不得开心颜
			</view>
			<view class="shop-info">
				<u-icon name="map" custom-style="margin-right:10rpx" size="40rpx"></u-icon>
				地址
			</view>
			<view class="shop-info">
				<u-icon name="clock" custom-style="margin-right:10rpx" size="36rpx"></u-icon>
				营业时间
			</view>
			<view class="shop-info" @click="makePhoneCall">
				<u-icon name="phone-fill" custom-style="margin-right:10rpx" size="36rpx"></u-icon>
				18814887553
			</view>
		</view>
		<view class="u-p-h-20">
			<room-cell></room-cell>
			<room-cell></room-cell>
			<room-cell></room-cell>
			<room-cell></room-cell>
			<room-cell></room-cell>
		</view>
		<view style="height: 100rpx;"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		onLoad() {
	
		},
		methods: {
			makePhoneCall(){
				uni.makePhoneCall({
					phoneNumber: "18814887553"
				})
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: $u-bg-color;
	}
	.top{
		background: linear-gradient($u-primary, $u-bg-color);
		padding: 20rpx;
	}
	.top_bar{
		display: flex;
		justify-content: space-around;
		padding: 20rpx 0;
		text-align: center;
		color: $u-bright;
		font-size: 26rpx;
		.iconfont{
			font-size: 70rpx;
			color: $u-bright;
			border: 4rpx solid $u-bright;
			border-radius: 50%;
			padding: 10rpx;
			margin-bottom: 10rpx;
		}
	}
	.shop{
		margin: 0 20rpx;
		padding: 20rpx;
		background: #fff;
		border-radius: 20rpx;
		color: $u-content-color;
	}
	.shop-desc{
		font-size: 28rpx;
	}
	.shop-info{
		display: flex;
		font-size: 32rpx;
		margin: 20rpx 0;
	}
	.shop-name{
		font-size: 36rpx;
		color: $u-main-color;
		margin: 0 0 10rpx 0;
	}
</style>