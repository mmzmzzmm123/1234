<template>
    <view class="result-page">
        <view class="line-box"></view>
        <view class="result-box">
            <view class="info-title">测试结果</view>
            <view class="info">
                <image mode="widthFix" class="img-item" src="/static/index/product/33.png"></image>
            </view>
        </view>
        <view class="submit-btn" @tap="getResult">保存结果</view>
        <view class="result-mask" v-show="maskShow" @tap="maskShow = false">
            <view class="mask-box">
                <view class="title">测试结果</view>
                <view class="img-box">
                    <image mode="widthFix" class="img-item" src="/static/index/product/33.png"></image>
                </view>
                <view class="user-info">
                    <view class="header">
                        <image mode="widthFix" class="img-item" src="/static/index/product/44.png"></image>
                    </view>
                    <view class="txt-box">
                        <view class="name">小赵爱测试</view>
                        <view class="cue">长按识别二维码进入测试乐园</view>
                    </view>
                    <view class="qr-code-box">
                        <image mode="widthFix" class="img-item" src="/static/index/product/55.png"></image>
                    </view>
                </view>
                <view class="bottom-cue">长按图片保存海报</view>
            </view>
        </view>
        <tab-bar></tab-bar>
    </view>
</template>
<script>
import tabBar from '@/components/tabBar.vue'
export default {
    components: { tabBar },
    data() {
        return {
            maskShow: false
        }
    },
    mounted() {
        uni.showTabBar();
    },
    methods: {
        getResult() {
            this.maskShow = true;
        }
    }
}
</script>
<style lang="scss">
page {
    background-color: #FFF9F7;

    .line-box {
        width: 718upx;
        height: 32upx;
        margin: 0 auto;
        background: #FF703F;
        box-shadow: 0px 1px 12px 0px rgba(255, 112, 63, 0.4);
        border-radius: 19upx;
        backdrop-filter: blur(20upx);
    }

    .result-box {
        width: 686upx;
        background: #FFFFFF;
        box-shadow: 0 4upx 16upx 0 rgba(255, 112, 63, 0.16);
        border-radius: 0px 0px 16upx 16upx;
        padding: 57upx 40upx 48upx;
        box-sizing: border-box;
        z-index: 100;
        position: absolute;
        left: 32upx;
        top: 16upx;
    }

    .info-title {
        margin-bottom: 32upx;
        font-size: 32upx;
        font-weight: bold;
        text-align: center;
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

    .img-item {
        width: 100%;
    }


    .submit-btn {
        width: 590upx;
        height: 88upx;
        background: #FF703F;
        border-radius: 44upx;
        text-align: center;
        line-height: 88upx;
        font-size: 32upx;
        font-weight: 600;
        color: #FFFFFF;
        position: absolute;
        bottom: 130upx;
        left: 80upx;
    }

    .result-mask {
        position: fixed;
        top: 0;
        left: 0;
        width: 100vw;
        height: 100vh;
        background: rgba(0, 0, 0, 0.45);
        z-index: 100000;

        .mask-box {
            width: 620upx;
            max-height: 90vh;
            position: absolute;
            left: 65upx;
            top: 5vh;
            background: linear-gradient(225deg, #FF6A47 0%, #FFA522 100%);
            border-radius: 16upx;
            padding: 48upx 30upx 104upx;
            box-sizing: border-box;

            .title {
                font-size: 32upx;
                font-weight: 600;
                color: #FFFFFF;
                line-height: 45upx;
                margin-bottom: 24upx;
                text-align: center;
            }

            .img-box {
                width: 560upx;
                background: #FFFFFF;
                border-radius: 8upx;
                padding: 24upx 28upx;
                box-sizing: border-box;
                margin-bottom: 24upx;
            }

            .user-info {
                display: flex;
                flex-direction: row;
                justify-content: space-between;

                .header {
                    width: 72upx;
                    height: 72upx;

                    .img-item {
                        border-radius: 100%;
                    }
                }

                .txt-box {
                    font-size: 24upx;
                    font-weight: 500;
                    color: #FFFFFF;
                    line-height: 72upx;

                    .cue {
                        font-size: 24upx;
                        font-weight: 400;
                        line-height: 24upx;
                        margin-top: 48upx;
                    }
                }

                .qr-code-box {
                    width: 142upx;
                    height: 142upx;
                    background: #FFFFFF;
                    border-radius: 8upx;
                }
            }

            .bottom-cue {
                height: 80upx;
                background: #FFFFFF;
                border-radius: 0 0 16upx 16upx;
                line-height: 80upx;
                font-size: 28upx;
                font-weight: 400;
                color: #777777;
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;
                text-align: center;
            }
        }
    }
}
</style>