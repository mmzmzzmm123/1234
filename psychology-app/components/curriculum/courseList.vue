<template>
    <view class="course-list-box">
        <view class="course-item" v-for="course in (courseList || [])" @tap="tocourse(course)">
            <view class="txt-box">
                <view class="title txt-overflow txt-overflow-line2">{{
                        course.name
                }}</view>
                <view class="sub-title txt-overflow">{{ course.author }}</view>
                <view class="price"><span class="icon">￥</span>{{ course.price }}</view>
            </view>
            <view class="img-box">
                <img :src="course.url" />
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
            console.log(course,'****************')
            uni.navigateTo({
                url: "/pages/curriculum/course?id=" + course.id,
            });
        }
    }
}
</script>
<style lang="scss">
@import "@/style/common.scss";

.course-item {
    display: flex;
    flex-direction: row;
    width: calc(100% -48upx);
    padding: 24upx;
    background: #ffffff;
    border-radius: 12upx;
    margin-bottom: 16upx;

    .txt-box {
        width: calc(100% - 198upx);
        flex: 1;
        margin-right: 15upx;

        .title {
            height: 84upx;
            font-size: 30upx;
            font-weight: 600;
            color: #333333;
            line-height: 42upx;
        }

        .sub-title {
            font-size: 26upx;
            font-weight: 400;
            color: #333333;
            height: 37upx;
            line-height: 37upx;
        }
    }

    .img-box {
        width: 183upx;
        height: 208upx;
        border-radius: 8upx;
    }

    .price {
        margin-top: 39upx;
    }
}
</style>