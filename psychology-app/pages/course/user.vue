<template>
	<view class="user-page">
		<view class="user-info">
			<view class="info">
				<view class="header" @tap="getUserInfo"><img class="img"
						:src="userInfo.avatar || '/static/course/header.png'" />
				</view>
				<view class="txt">
					<view class="name">{{ userInfo.name
            }}<img class="img" src="/static/icon/refresh.png" v-show="userInfo.name" @tap="refreshUser" />
					</view>
					<view class="num"
            ><img
              v-show="userInfo.phone"
              class="img"
              src="/static/course/user/phone.png"
            />{{ userInfo.phone }}
          </view>
				</view>
			</view>
		</view>
		<view class="class-box index-margin">
			<view class="item" v-for="item in classList" @tap="
          () => {
            item.callback && item.callback();
          }
        ">
				<img class="class-img" :src="item.classPic" />
				<view>{{ item.className }}</view>
			</view>
		</view>
		<view class="un-test-box">
			<view class="box-title">最近在学</view>
			<view class="item" v-for="item in courseList">
				<view class="title">{{ item.name }}</view>
				<view class="section">共{{ item.sectionList ? item.sectionList.length : 0 }}节</view>
				<view class="date" v-if="item.finishStatus == 1">已学完</view>
				<view class="date" v-if="item.finishStatus == 0 && item.studyDuration">已学习{{ item.studyDuration }}
				</view>
				<view class="date" v-if="item.finishStatus == 0 && !item.studyDuration">未开始学习</view>
				<view class="btn" @tap="toCourse(item.id)">{{item.studyDuration ? '继续学习' : '进入学习'}}</view>
			</view>
			<no-data v-if="courseList.length == 0" :showToLogin="true"></no-data>
			<view class="footer" v-else>已经到底了</view>
		</view>

		<course-tab-bar :currentIndex="2"></course-tab-bar>

		<uni-popup ref="popup" type="dialog">
			<uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true"
				@close="close" @confirm="confirm"></uni-popup-dialog>
		</uni-popup>
	</view>
</template>
<script>
	import utils, {
		clientTypeObj
	} from "@/utils/common";
	import noData from "@/components/course/noData";
	import userServer from "@/server/course/user";
	import formatTime from "@/utils/formatTime.js"
	import loginServer from "@/server/login"
  import {
  	uniPopup,
  	uniPopupDialog
  } from '@dcloudio/uni-ui'
	export default {
		components: {
			noData,
      uniPopup,
      uniPopupDialog
		},
		data() {
			return {
				userInfo: {},
				classList: [{
						classPic: "/static/course/user/course.png",
						className: "我的课程",
						id: 16,
						callback: this.toCourseList,
					},
					{
						classPic: "/static/course/user/order.png",
						className: "我的订单",
						id: 36,
						callback: this.toOrder,
					},
					{
						classPic: "/static/course/user/customer-service.png",
						className: "客服帮助",
						id: 72,
					},
				],
				courseList: [],
				clientType: "",
				clientTypeObj: clientTypeObj,
				//回调地址，防止微信登录失败后再次登录将code等参数带过去了
				redirectUri: location.origin + location.pathname + "?t=" + new Date().getTime(),
			};
		},
		async created() {
			//  this.clientType = utils.getClientType();
			// this.userInfo = uni.getStorageSync("userInfo")
      this.userInfo = utils.getUserInfo()

		},
		async mounted() {      
			if (!this.userInfo && await utils.loginCallback(this.redirectUri)) {
				// this.userInfo = uni.getStorageSync("userInfo")
        this.userInfo = utils.getUserInfo()
			}
      if (!utils.checkLogin()) {
        return this.openLoginConfirm()
      }

			if (this.userInfo) {
				this.courseList = await userServer.getCourseList(this.userInfo.userId);
				this.courseList.map(item => {
					if (item.studyDuration === 0) {
						return;
					}
					item.studyDuration = formatTime.formatSecondsCH(item.studyDuration)
				})
			}
		},
		methods: {
			refreshUser() {
				uni.setStorageSync("userInfo", null);
				utils.loginWx(this.redirectUri);
				//添加登录标志,为callback做返回判断
				uni.setStorageSync("wxLogining", true);
			},
			toCourseList() {
        // 判断是否已经登录
        if (!utils.checkLogin()) {
          return this.openLoginConfirm()
        }
				if (this.getUserInfo())
					uni.navigateTo({
						url: "/pages/course/courseList"
					});
			},
			toOrder() {
        // 判断是否已经登录
        if (!utils.checkLogin()) {
          return this.openLoginConfirm()
        }
				if (this.getUserInfo())
					uni.navigateTo({
						url: "/pages/course/order"
					});
			},
      toCourse(courseId) {
        uni.navigateTo({
          url: `/pages/course/courseDetail?courseId=${courseId}`,
        });
      },
			toLearningCourse(courseId) {
        // 判断是否已经登录
        if (!utils.checkLogin()) {
          return this.openLoginConfirm()
        }
				uni.navigateTo({
					url: `/pages/course/learningCourse?courseId=${courseId}`,
				});
			},
			copyOrderNo(orderNo) {
				var input = document.createElement("input");
				document.body.appendChild(input);
				input.setAttribute("value", orderNo);
				input.select();
				document.execCommand("copy"); // 执行浏览器复制命令
				if (document.execCommand("copy")) {
					document.execCommand("copy");
					uni.showToast({
						icon: "error",
						title: "内容复制成功",
					});
				}
				document.body.removeChild(input);
			},
			// 点击头像
			getUserInfo() {
				if (!uni.getStorageSync("userInfo")) {
					utils.loginWx(this.redirectUri);
					return false;
				}
				return true;
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
		background-color: #f8f8f8;

		.user-info {
			width: 750upx;
			height: 186upx;
			background-color: #ffffff;
			padding: 42upx 30upx;
			box-sizing: border-box;

			.info {
				display: flex;
				flex-direction: row;
				margin-bottom: 48upx;

				.header {
					width: 106upx;
					height: 106upx;
					box-shadow: 0 8upx 32upx 0 rgba(188, 167, 167, 0.4);
					border: 2upx solid #ffffff;
					border-radius: 100%;
					margin-right: 24upx;

					.img {
						border-radius: 100%;
					}
				}

				.txt {
					flex: 1;

					.name {
						font-size: 32upx;
						font-weight: 600;
						color: #333333;
						line-height: 45upx;
						display: flex;
						flex-direction: row;
						align-items: center;
						margin: 8upx 0 12upx;

						.img {
							width: 32upx;
							height: 27upx;
							margin-left: 16upx;
						}
					}

					.num {
						font-size: 28upx;
						font-weight: 400;
						color: #777777;
						line-height: 40upx;
						display: flex;
						flex-direction: row;
						align-items: center;

						.img {
							width: 40upx;
							height: 40upx;
							margin-right: 12upx;
						}
					}
				}
			}

			.link-box {
				display: flex;
				flex-direction: row;

				.item {
					width: 336upx;
					height: 164upx;
					background-image: url("/static/course/user/consulting-service.png");
					background-size: 100% 100%;
					padding: 30upx 32upx;
					box-sizing: border-box;
					font-size: 32upx;
					font-weight: 500;
					line-height: 45upx;
					color: #40c2b7;

					&:first-child {
						margin-right: 26upx;
						background-image: url("/static/course/user/report.png");
						color: #e2724c;
					}

					.num {
						font-size: 30upx;
						font-weight: 400;
						line-height: 42upx;
					}
				}
			}
		}

		.class-box {
			display: flex;
			flex-direction: row;
			justify-content: space-around;
			text-align: center;
			font-size: 26upx;
			margin: 16upx 30upx;
			width: 690upx;
			background: #ffffff;
			border-radius: 16upx;
			padding: 32upx 0;

			.class-img {
				width: 64upx;
				height: 64upx;
			}
		}

		.un-test-box {
			width: 690upx;
			margin: 0 auto;
			background: #ffffff;
			border-radius: 16px;
			padding: 0 40upx 36upx;
			box-sizing: border-box;

      .box-title {
				line-height: 102upx;
				font-size: 30upx;
				font-weight: 600;
				color: #333333;				
				position: relative;
				padding-left: 22upx;
        margin-bottom: -48upx;
				&::before {
					width: 10upx;
					height: 32upx;
					background: #ff703f;
					border-radius: 5upx;
					content: "";
					position: absolute;
					left: 0;
					top: 35upx;
				}
			}

			.item {
				width: 646upx;
				height: 291upx;
				background: #FFFFFF;
        border-top: 1px solid rgba(204, 204, 204, 0.6);	
				padding: 32upx;
				box-sizing: border-box;
				margin: 24upx auto;
        margin-top: 48upx;
				position: relative;

				// &::before {
				// 	content: '';
				// 	position: absolute;
				// 	left: 0;
				// 	top: 40upx;
				// 	width: 6upx;
				// 	height: 32upx;
				// 	background: #FF703F;
				// 	border-radius: 3upx;
				// }

				.title {
					font-size: 32upx;
					font-weight: 600;
					color: #333333;
					line-height: 45upx;
					margin-bottom: 16upx;
				}
        
        .section {
          color: #AAAAAA;
          margin-bottom: 12upx;
        }

				.date {
					font-size: 28upx;
					font-weight: 400;
					color: #AAAAAA;
					line-height: 40upx;
				}

				.btn {
          background: rgba(255,112,63,0.08);
          border-radius: 44rpx;
					height: 96upx;
					line-height: 96upx;
					font-size: 32upx;
					font-weight: 400;
					color: #FF703F;
					text-align: center;
					margin-top: 32upx;
          margin-bottom: 24upx;
				}
			}
		}

		.footer {
      margin: 80upx auto;

			&::before,
			&::after {
				left: 177upx;
			}

			&::after {
				left: unset;
				right: 177upx;
			}
		}
	}
</style>
