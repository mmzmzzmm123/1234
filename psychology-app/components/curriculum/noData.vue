<template>
    <view class="no-data">
        <img class="img" src="/static/nothing/list-nothing.png" />
        <view class="txt">暂无数据</view>
        <view class="link-btn" @tap="toClass" v-show="showToClass">查看更多课程></view>
    </view>
</template>
<script>
export default {
    props: ['showToClass'],
    methods: {
        toClass() {
            uni.navigateTo({ url: "/pages/curriculum/class" });
        }
    }
}
</script>
<style lang="scss">
.no-data {
    text-align: center;

    .img {
        width: 220upx;
        height: 211upx;
        margin: 88upx auto 0;
    }

    .txt {
        font-size: 28upx;
        font-weight: 400;
        color: rgba(0, 0, 0, 0.35);
        line-height: 40upx;
    }

    .link-btn {
        width: 272upx;
        height: 72upx;
        background: rgba(255, 112, 63, 0.06);
        border-radius: 36upx;
        margin: 24upx auto;
        font-size: 28upx;
        font-weight: 400;
        color: #FF703F;
        line-height: 72upx;
    }
}
</style>