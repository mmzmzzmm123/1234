<template>
    <view class="course-list-box">
        <view class="course-item" v-for="course in (courseList || [])" @tap="tocourse(course)">
            <view class="img-box">
                <img :src="course.iconUrl" />
            </view>
            <view class="txt-box">
                <view class="title txt-overflow txt-overflow-line2">{{
                        course.name
                }}</view>
                <view class="bottom-box">
                  <view class="author txt-overflow">{{ course.author }}</view>
                  <view class="price"><span class="icon">￥</span>{{ course.price }}</view>
                </view>                
            </view>
            
        </view>
        <view class="footer" v-show="courseList.length > 0">已经到底了</view>
    </view>
</template>
<script>
export default {
    props: ['courseList'],
    data() {
        return {}
    },
    methods: {
        tocourse(course) {
            uni.navigateTo({
                url: "/pages/course/courseDetail?id=" + course.id,
            });
        }
    }
}
</script>
<style lang="scss" scoped>
@import "@/style/common.scss";

.course-item {
    display: flex;
    flex-direction: row;
    width: calc(100% -48upx);
    padding: 24upx;
    background: #ffffff;
    border-radius: 12upx;
    margin-bottom: 16upx;
    .img-box {
        width: 172rpx;
        height: 172rpx;
        border-radius: 8upx;
    }
    .txt-box {
        width: calc(100% - 198upx);
        flex: 1;
        margin-left: 32upx;

        .title {
            height: 84upx;
            font-size: 14px;
            font-weight: 600;
            color: #333333;
            line-height: 42upx;
        }
        .bottom-box {
          margin-top: 51upx;
          display: flex;
          align-items: center;
          justify-content: space-between;
          .author {
              font-size: 12px;
              font-weight: 400;
              color: #777777;
              height: 37upx;
              line-height: 37upx;
          }
          .price {
              font-size: 12px;
          }
        }
        
    }
    

    
}
</style>