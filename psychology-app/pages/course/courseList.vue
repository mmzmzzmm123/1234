<template>
    <view class="report-list">
        <view class="item" v-for="item in courseList">
            <view class="title">{{ item.name }}</view>
			<view class="section">共{{ item.sectionList ? item.sectionList.length : 0 }}节</view>
			<view class="date" v-if="item.finishStatus == 1">已学完</view>
            <view class="date" v-if="item.finishStatus == 0 && item.studyDuration">已学习{{ item.studyDuration }}</view>
			<view class="date" v-if="item.finishStatus == 0 && !item.studyDuration">未开始学习</view>
            <view class="btn" @tap="toCourse(item.id)">{{item.studyDuration ? '继续学习' : '进入学习'}}</view>
        </view>
        <no-data v-if="courseList.length == 0"></no-data>
        <view class="footer" v-else>已经到底了</view>
        <course-tab-bar></course-tab-bar>
    </view>
</template>
<script>
import noData from '@/components/course/noData'
import userServer from '@/server/course/user'
import formatTime from '@/utils/formatTime.js'
export default {
    components: { noData },
    data() {
        return {
			userInfo: {},
            courseList: []
        }
    },
    async created() {
		// this.userInfo = uni.getStorageSync("userInfo")
      this.userInfo = uni.getStorageSync("userInfo")
		if (this.userInfo && this.userInfo.userId) {			
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
      toCourse(courseId) {
        uni.navigateTo({
          url: `/pages/course/courseDetail?courseId=${courseId}`,
        });
      },
        async toLearningCourse(courseId) {
            uni.navigateTo({
                url: "/pages/course/learningCourse?courseId=" + courseId,
            });
            
        }
    },
}
</script>
<style lang="scss">
@import "@/style/common.scss";

page {
    background-color: #f8f8f8;

    .report-list {
        padding-top: 32upx;

        .item {
            width: 686upx;
            background: #FFFFFF;
            box-shadow: 0px 4upx 28upx 0px rgba(119, 119, 119, 0.06);
            border-radius: 16upx;
            padding: 32upx;
            box-sizing: border-box;
            margin: 0 auto 24upx;
            position: relative;

            &::before {
                content: '';
                position: absolute;
                left: 0;
                top: 40upx;
                width: 6upx;
                height: 32upx;
                background: #FF703F;
                border-radius: 3upx;
            }

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
                font-size: 32upx;
                font-weight: 400;
                color: #FF703F;
                text-align: center;
                margin-top: 32upx;
                padding-top: 12px;
                border-top: 1px solid rgba(204, 204, 204, 0.5);
            }
        }
    }
}
</style>