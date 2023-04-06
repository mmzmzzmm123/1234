<template>
	<view class="index">
		<view class="search-box index-margin" @tap="toSearch">
			<img class="icon" src="/static/icon/search.png" />
			<span class="txt">搜索</span>
		</view>
		<view class="banner-box index-margin">
			<swiper class="ad-swiper" indicator-dots circular indicator-color="rgb(255, 255, 255, .5)"
       indicator-active-color="#FFFFFF">
				<swiper-item v-for="(item, index) in bannerList" :key="index">
					<image class="banner-img" :src="item.bannerUrl" @tap="tocourse(item.linkUrl)" />
				</swiper-item>
			</swiper>
		</view>
		<view class="class-box index-margin">
			<view class="item" v-for="item in classList">
				<img class="class-img" :src="item.classPic" />
				<view>{{ item.className }}</view>
			</view>
		</view>
		<!-- <view class="banner-box banner-box1 index-margin">
			<swiper class="ad-swiper" indicator-dots circular indicator-color="rgb(255, 255, 255, .5)"
       indicator-active-color="#FFFFFF">
				<swiper-item v-for="(item, index) in bannerList1" :key="index">
					<image class="banner-img" :src="item.bannerUrl" @tap="tocourse(item.linkUrl)" />
				</swiper-item>
			</swiper>
		</view> -->
		<view class="img-box index-margin">
			<view class="index-title">
				限时福利
				<span class="more" @tap="toMore">
					更多
					<img class="img" src="/static/icon/more.png" />
				</span>
			</view>
			<view class="img-item-box">
				<view class="left-img" v-show="bannerList2.length > 0"
					@tap="tocourse(bannerList2.length > 0 ? bannerList2[0].linkUrl : '')">
					<img :src="bannerList2.length > 0 ? bannerList2[0].bannerUrl : ''" />
				</view>
				<view class="right-img-box">
					<view class="right-img" v-if="bannerList2.length > 1"
						@tap="tocourse(bannerList2.length > 1 ? bannerList2[1].linkUrl : '')">
						<img :src="bannerList2.length > 1 ? bannerList2[1].bannerUrl : ''" />
					</view>
					<view class="right-img" v-if="bannerList2.length > 2"
						@tap="tocourse(bannerList2.length > 2 ? bannerList2[2].linkUrl : '')">
						<img :src="bannerList2.length > 2 ? bannerList2[2].bannerUrl : ''" />
					</view>
				</view>
			</view>
		</view>
		<view class="course-box index-margin">
			<view class="index-title" @tap="toMore">
				精选好课
				<span class="more">
					更多
					<img class="img" src="/static/icon/more.png" /></span>
			</view>
			<course-list-com :courseList="courseList"></course-list-com>
		</view>
		<course-tab-bar :currentIndex="0"></course-tab-bar>

		<uni-popup ref="popup" type="dialog">
			<uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true"
				@close="close" @confirm="confirm"></uni-popup-dialog>
		</uni-popup>
	</view>
</template>
<script>
	import courseListCom from '@/components/course/courseList'
	import indexServer from '@/server/course/index'
	import loginServer from '@/server/login'
	import {
		uniPopup,
		uniPopupDialog
	} from '@dcloudio/uni-ui'
	import utils from "@/utils/common";
	export default {
		components: {
			courseListCom,
			uniPopup,
			uniPopupDialog
		},
		data() {
			return {
				bannerList: [],
				bannerList1: [],
				bannerList2: [],
				bannerList3: [],
				classList: [{
						classPic: "/static/course/index/1.png",
						className: "能力提升",
						id: 1,
					},
					{
						classPic: "/static/course/index/2.png",
						className: "情感困扰",
						id: 2,
					},
					{
						classPic: "/static/course/index/3.png",
						className: "自我探索",
						id: 3,
					}, {
						classPic: "/static/course/index/4.png",
						className: "超值课程",
						id: 4,
					},
				],
				courseList: [],
				userInfo: {}
			};
		},
		async created() {
      this.userInfo = uni.getStorageSync("userInfo")
			this.bannerList = await this.getBanner(0);
			this.bannerList1 = await this.getBanner(2);
			this.bannerList2 = await this.getBanner(1);
			this.courseList = await this.getcourse(1);			
		},
		async mounted() {      
			if (!this.userInfo && await utils.loginCallback(this.redirectUri)) {
				this.userInfo = uni.getStorageSync("userInfo")			  
			}
      if (!this.userInfo) {
        this.openLoginConfirm()
      }
     
		},
		methods: {
			async getBanner(type) {
				return await indexServer.getBannerList(type);
			},
			async getcourse(type) {
				return await indexServer.getcourseByLabel(type);
			},
			async tocourse(url) {
				// 判断是否已经登录
				if (!this.userInfo) {
					this.openLoginConfirm()
					return
				}
				uni.navigateTo({
					url
				});
			},
			toSearch() {
        // 判断是否已经登录
        if (!this.userInfo) {
        	this.openLoginConfirm()
        	return
        }
				uni.navigateTo({
					url: "/pages/course/search",
				});
			},
			toMore() {
        // 判断是否已经登录
        if (!this.userInfo) {
        	this.openLoginConfirm()
        	return
        }
				uni.navigateTo({
					url: "/pages/course/class",
				});
			},
			close() {
				this.$refs.popup.close()
			},
			async confirm() {
				await loginServer.login();
				this.$refs.popup.close()
			},
			openLoginConfirm() {
				this.$refs.popup.open();
			}
		},
	};
</script>
<style lang="scss">
	@import "@/style/common.scss";

	page {
		background-color: #F8F8F8;
		padding-left: 24upx;

		.search-box {
			margin: 32upx 24upx;
			height: 64upx;
			background: #ffffff;
			border-radius: 32upx;
			line-height: 64upx;
			display: flex;
			flex-direction: row;
			justify-content: center;
			align-items: center;

			.icon {
				width: 32upx;
				height: 32upx;
				margin-right: 28upx;
			}

			.txt {
				font-size: 24upx;
				color: #aaaaaa;
			}
		}

		.banner-box {
			.banner-img {
				width: 100%;
				height: 260upx;
			}

			uni-swiper {
				height: 260upx;
			}
		}

		.banner-box1 {
			uni-swiper {
				height: 200upx;
			}

			.banner-img {
				height: 200upx;
			}
		}

		.class-box {
			display: flex;
			flex-direction: row;
			justify-content: space-around;
			text-align: center;
			font-size: 26upx;
			margin-bottom: 32upx;
			margin-top: 21upx;

			.class-img {
				width: 88upx;
				height: 88upx;
			}
		}

		.hot-box {
			margin-left: 24upx;

			.list-box {
				display: flex;
				flex-direction: row;
				overflow-x: auto;
				min-height: 350upx;

				.item {
					display: flex;
					flex-direction: column;
					margin-right: 16upx;
					width: 194upx;
					position: relative;

					.ranking {
						position: absolute;
						top: 16upx;
						left: 0;
						color: #f4a200;
						line-height: 30upx;
						width: 81upx;
						height: 32upx;
						background: #ffee23;
						border-radius: 0 100upx 100upx 0;
						font-size: 22upx;
						padding-left: 10upx;
					}

					.img {
						width: 194upx;
						height: 240upx;
						margin-bottom: 16upx;
					}

					.txt {
						height: 80upx;
						line-height: 40upx;
					}
				}
			}
		}

		.index-title {
			margin-top: 32upx;
			margin-bottom: 16upx;
			font-size: 32upx;
			font-weight: 600;
			color: #333333;
			line-height: 45upx;
			display: flex;
			justify-content: space-between;

			.more {
				font-size: 24upx;
				font-weight: 400;
				color: #333333;
				align-items: center;
				display: flex;

				.img {
					margin-left: 12upx;
					width: 12upx;
					height: 24upx;
				}
			}
		}

		.img-box {
			min-height: 344upx;

			.img-item-box {
				width: 682upx;
				display: flex;
				flex-direction: row;
				flex-wrap: wrap;

				.left-img {
					width: 333upx;
					height: 344upx;
					margin-right: 16upx;
				}

				.right-img {
					width: 333upx;
					height: 164upx;
					margin-bottom: 16upx;
				}

				.img {
					width: 333upx;
					height: 334upx;
					margin-right: 16upx;
					margin-bottom: 16upx;
				}

				.img:nth-child(even) {
					margin-right: 0;
				}
			}
		}



	}
</style>
