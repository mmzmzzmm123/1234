<template>
    <view class="test-before-page">
        <view class="info-box">
            <view class="info-title">测评须知</view>
            <view class="img-box">
                {{ gaugeDes }}
            </view>
            <view class="begin-test" @tap="startTest">已了解，开始测试</view>
        </view>
        <view class="cue-txt">
            <view class="title">温馨提示</view>
            <view class="sub-title">所有的测评均可在“我的-测评报告”中查看报告和状态</view>
        </view>
        <evaluation-tab-bar></evaluation-tab-bar>
    </view>
</template>
<script>
import utils from '@/utils/common'
export default {
    data() {
        return {
            productId: '',
            orderId: '',
            gaugeDes: ''
        }
    },
    created() {
        this.gaugeDes = uni.getStorageSync("gaugeDes");
        this.productId = utils.getParam(location.href, "productId");
        this.orderId = utils.getParam(location.href, "orderId");

    },
    methods: {
        startTest() {
            uni.navigateTo({
                url: `/pages/evaluation/question/index?productId=${this.productId}&orderId=${this.orderId}`,
            });

        }
    },
}
</script>
<style lang="scss">
page {
    padding: 32upx;
    background-color: #F8F8F8;

    .info-box {
        background-color: #fff;
        padding-bottom: 64upx;
        border-radius: 16px;

        .info-title {
            margin-bottom: 32upx;
            font-size: 32upx;
            font-weight: bold;
            text-align: center;
            margin-top: 32upx;
            color: #333333;
            line-height: 50upx;

            &::after {
                display: block;
                width: 144upx;
                height: 8upx;
                background: #FF703F;
                border-radius: 2upx;
                content: '';
                margin: -10upx auto 0;
            }
        }

        .img-box {
            margin: 30upx 24upx;
            white-space: pre-wrap;
        }

        .img-item {
            width: 100%;
        }

        .begin-test {
            width: 590upx;
            height: 88upx;
            background: #FF703F;
            box-shadow: 0 8upx 32upx 0 rgba(255, 112, 63, 0.3);
            border-radius: 12upx;
            color: #fff;
            text-align: center;
            line-height: 88upx;
            margin: 50upx auto;
        }
    }

    .cue-txt {
        text-align: center;
        position: fixed;
        bottom: 42upx;
        width: 100%;

        .title {
            height: 40upx;
            font-size: 28upx;
            font-weight: 400;
            color: #777777;
            line-height: 40upx;
            margin-bottom: 18upx;
        }

        .sub-title {
            height: 33upx;
            font-size: 24upx;
            font-weight: 400;
            color: #AAAAAA;
            line-height: 33upx;
        }
    }
}
</style>