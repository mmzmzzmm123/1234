<template>
	<view class="index">
		<view class="search-box index-margin" @tap="toSearch">
			<img class="icon" src="/static/icon/search.png" />
			<span class="txt">搜索</span>
		</view>
		<view class="banner-box index-margin">			
      <view v-for="(item, index) in bannerList" :key="index">
        <image class="banner-img" :src="item.bannerUrl" @tap="tocourse(item.linkUrl)" />
      </view>			
		</view>
		<view class="class-box index-margin">
			<view class="item" v-for="item in classList" @tap="toClass(item.id)">
				<img class="class-img" :src="item.url" />
				<view>{{ item.name }}</view>
			</view>
		</view>
    
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

    <login ref="loginModel"></login>
	</view>
</template>
<script>
	import courseListCom from '@/components/course/courseList'
	import indexServer from '@/server/course/index'
  import login from "@/components/common/login";
  import classServer from '@/server/course/class'
	export default {
		components: {
      login,
			courseListCom,
		},
		data() {
			return {
				bannerList: [],
				bannerList1: [],
				bannerList2: [],
				bannerList3: [],
				classList: [],
				courseList: [],
				userInfo: {}
			};
		},
		async created() {
      // this.userInfo = uni.getStorageSync("userInfo")
      this.userInfo = this.$utils.getUserInfo()
			this.bannerList = await this.getBanner(0);
			this.bannerList1 = await this.getBanner(2);
			this.bannerList2 = await this.getBanner(1);
			this.courseList = await this.getcourse(1);			
      const classData = await classServer.getClassList()
      this.classList = classData.slice(0, 8) // 取前8个类别
		},
		async mounted() {      
			if (!this.userInfo && await this.$utils.loginCallback(this.redirectUri)) {
        this.userInfo = this.$utils.getUserInfo()
			}
      // if (!this.$utils.checkLogin()) {
      //   return this.openLoginConfirm()
      // }
		},
		methods: {
			async getBanner(type) {
				return await indexServer.getBannerList(type);
			},
			async getcourse(type) {
				return await indexServer.getcourseByLabel(type);
			},
			async tocourse(url) {
				uni.navigateTo({
					url
				});
			},
      toClass(classId) {
        uni.navigateTo({
        	url: "/pages/course/class?classId=" + classId
        });
      },
			toSearch() {
				uni.navigateTo({
					url: "/pages/course/search",
				});
			},
			toMore() {
				uni.navigateTo({
					url: "/pages/course/class",
				});
			},
			openLoginConfirm() {
				this.$refs.loginModel.open();
			}
		},
	};
</script>
<style lang="scss">
	@import "@/style/common.scss";

	page {
		background-color: #F8F8F8;

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
				font-size: 12px;
				color: #aaaaaa;
			}
		}

		.banner-box {
			.banner-img {
				width: 100%;
				height: 320upx;
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
      flex-wrap: wrap;
			justify-content: space-around;
			text-align: center;
			font-size: 12px;
			margin-bottom: 32upx;
			margin-top: 21upx;
      .item {        
        width: 25%;
        .class-img {
        	width: 88upx;
        	height: 88upx;
        }
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
			font-size: 15px;
			font-weight: 600;
			color: #333333;
			line-height: 45upx;
			display: flex;
			justify-content: space-between;

			.more {
				font-size: 12px;
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
				//width: 682upx;
				display: flex;
				flex-direction: row;
				flex-wrap: wrap;

				.left-img {
					width: 343upx;
					height: 344upx;
					margin-right: 16upx;
				}

				.right-img {
					width: 343upx;
					height: 164upx;
					margin-bottom: 16upx;
				}

				.img {
					width: 343upx;
					height: 344upx;
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
